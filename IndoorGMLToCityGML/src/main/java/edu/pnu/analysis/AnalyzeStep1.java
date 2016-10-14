package edu.pnu.analysis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.opengis.indoorgml.v_1_0.vo.core.CellSpace;
import net.opengis.indoorgml.v_1_0.vo.core.CellSpaceBoundary;
import net.opengis.indoorgml.v_1_0.vo.core.IndoorFeatures;
import net.opengis.indoorgml.v_1_0.vo.core.PrimalSpaceFeatures;
import net.opengis.indoorgml.v_1_0.vo.core.State;
import net.opengis.indoorgml.v_1_0.vo.core.Transition;

import org.geotools.geometry.iso.primitive.PrimitiveFactoryImpl;
import org.opengis.geometry.primitive.OrientableSurface;
import org.opengis.geometry.primitive.Primitive;
import org.opengis.geometry.primitive.Shell;
import org.opengis.geometry.primitive.Solid;
import org.opengis.geometry.primitive.SolidBoundary;
import org.opengis.geometry.primitive.Surface;
import org.opengis.geometry.primitive.SurfaceBoundary;

public class AnalyzeStep1 {
	private IndoorFeatures indoorFeatures;

	private List<Double> floorZOrdinates;
	private Map<Double, List<CellSpace>> floorCellSpaceMap;

	private PrimitiveFactoryImpl pf = null;

	public AnalyzeStep1(IndoorFeatures indoorFeatures, List<Double> floorZOrdinates, Map<Double, List<CellSpace>> floorCellSpaceMap,
			PrimitiveFactoryImpl pf) {
		this.indoorFeatures = indoorFeatures;
		this.floorZOrdinates = floorZOrdinates;
		this.floorCellSpaceMap = floorCellSpaceMap;
		this.pf = pf;
	}

	// Cell이 가지는 있는 CellBoundary가 기하의 어떤 벽면에 붙어있는지 찾아냄
	// Cell c1이 인접한 Cell c2과 어느 벽면에서 어떤 Boundary를 가지고 있는지 분석
	public void analyzeStep1() {
		System.out.println("number of z : " + floorZOrdinates.size());
		for (Double z : floorZOrdinates) {
			List<CellSpace> floorCellSpace = floorCellSpaceMap.get(z);

			System.out.println("number of cellspace of floor : " + floorCellSpace.size());
			for (CellSpace cellSpace : floorCellSpace) {
				System.out.println(cellSpace.getGmlID());
				setSurfaceList(cellSpace);
				//System.out.println("setSurfaceList");
			}

			System.out.println("makeMap");
			for (CellSpace cellSpace : floorCellSpace) {
				if (cellSpace.getPartialBoundedBy() != null) {
					System.out.println(cellSpace.getGmlID());
					makeFacetBoundaryMap(cellSpace);
					//System.out.println("makeFacetBoundaryMapEnd");
					//makeBoundaryCellSpaceMap(cellSpace, (ArrayList<CellSpace>) floorCellSpace);
				}
			}
		}
	}

	private void setSurfaceList(CellSpace cellSpace) {
		List<Surface> surfaces = new ArrayList<Surface>();

		Solid solid = cellSpace.getGeometry3D().getGeometry();
		SolidBoundary boundary = solid.getBoundary();

		Shell exterior = boundary.getExterior();
		Shell[] interiors = boundary.getInteriors();

		Collection<? extends Primitive> elements = exterior.getElements();
		if (ArrayList.class.isAssignableFrom(elements.getClass())) {
			List<OrientableSurface> exteriorSurfaces = (ArrayList<OrientableSurface>) exterior.getElements();
			for (OrientableSurface oSurface : exteriorSurfaces) {
				if (oSurface instanceof Surface) {
					Surface surface = (Surface) oSurface;
					surfaces.add(surface);
				}
			}
		}

		cellSpace.setFacets(surfaces);
	}

	private void makeFacetBoundaryMap(CellSpace cellSpace) {
		Map<Surface, List<CellSpaceBoundary>> facetBoundaryMap = new HashMap<Surface, List<CellSpaceBoundary>>();
		List<Surface> facets = cellSpace.getFacets();
		ArrayList<CellSpaceBoundary> partialBoundedBy = cellSpace.getPartialBoundedBy();

		for (CellSpaceBoundary boundary : partialBoundedBy) {

			Surface closedFacet = null;
			double minDistance = Double.MAX_VALUE;
			for (Surface facet : facets) {
				OrientableSurface boundaryGeometry = boundary.getGeometry3D().getGeometry();

				if (boundaryGeometry == null) {
					if (boundary.getGeometry3D().getPolygonGeometry() != null) {
						SurfaceBoundary polygonBoundary = boundary.getGeometry3D().getPolygonGeometry().getBoundary();
						boundaryGeometry = pf.createSurface(polygonBoundary);
					} else {
						throw new UnsupportedOperationException("Not exist geometry object of CellSpaceBoundary");
					}
				}

				if (facet.contains(boundaryGeometry)) { // Solid 의 면에 포함된 Boundary를 찾는다.
					addToFacetBoundaryMap(facetBoundaryMap, facet, boundary);
					closedFacet= null;
					break;
				} else { // 소수점 등의 문제로 contains 연산이 안될 경우 거리가 가장 가까운 면을 찾는다.
					double dist = facet.distance(boundaryGeometry);
					if (dist < minDistance) {
						minDistance = dist;
						closedFacet = facet;
					}
				}
			}

			if (closedFacet != null) {
				addToFacetBoundaryMap(facetBoundaryMap, closedFacet, boundary);
			}
		}
		cellSpace.setFacetBoundaryMap(facetBoundaryMap);
	}

	private Map<Surface, List<CellSpaceBoundary>> addToFacetBoundaryMap(Map<Surface, List<CellSpaceBoundary>> facetBoundaryMap,
			Surface facet, CellSpaceBoundary boundary) {
		List<CellSpaceBoundary> boundaryList = null;
		if (!facetBoundaryMap.containsKey(facet)) {
			boundaryList = new ArrayList<CellSpaceBoundary>();
			facetBoundaryMap.put(facet, boundaryList);
		}
		boundaryList = facetBoundaryMap.get(facet);

		if (!boundaryList.contains(boundary)) {
			boundaryList.add(boundary);
		}

		return facetBoundaryMap;
	}

	private void makeBoundaryCellSpaceMap(CellSpace cellSpace, ArrayList<CellSpace> cellSpaceMember) {
		Map<CellSpaceBoundary, List<CellSpace>> boundaryCellSpaceMap = new HashMap<CellSpaceBoundary, List<CellSpace>>();
		ArrayList<CellSpaceBoundary> partialBoundedBy = cellSpace.getPartialBoundedBy();
		ArrayList<CellSpaceBoundary> handledBoundary = new ArrayList<CellSpaceBoundary>();

		ArrayList<CellSpace> adjacencyCell = new ArrayList<CellSpace>();
		State duality = cellSpace.getDuality();
		ArrayList<Transition> connects = duality.getConnects();
		for (Transition transition : connects) {
			ArrayList<State> states = transition.getConnects();
			for (State state : states) {
				if (state.equals(duality)) continue;

				CellSpace otherStateDuality = state.getDuality();
				if (!adjacencyCell.contains(otherStateDuality)) {
					adjacencyCell.add(otherStateDuality); // transition으로 연결된 cell 추가
				}

				// transition의 duality인 Boundary를 찾는다.
				CellSpaceBoundary dualityBoundary = transition.getDuality();
				// CellSpaceBoundary를 복사하여 만들어서 동일한 Transition이 생기는데
				// 복사된 후에는 C1-CB1(T1) C2-CB2(T2)로 되는데 C1과 C2의 connects로 T1, T2 둘다생겨
				// C1의 partialBoundeBy에 CB2가 들어가지 않는데 찾아지는 문제 발생
				if (dualityBoundary != null && partialBoundedBy.contains(dualityBoundary)) {
					addToBoundaryCellSpaceMap(boundaryCellSpaceMap, dualityBoundary, otherStateDuality);
					handledBoundary.add(dualityBoundary);
				}
			}

		}
		System.out.println("not handled size : " + (partialBoundedBy.size() - handledBoundary.size()));
		//System.out.println("adjacencyCell found");

		// 문 또는 가상벽의 CellSpaceBoundary는 Transition의 개수만큼 있다.
		// 문에 대한 Boundary를 찾고나면 나머지는 모두 벽으로 가정
		// 문에 대한 Boundary가 있어야되는 벽에서 Boundary를 찾지 못하면?

		// X 그냥 인접 Cell만 찾는다. 후보는 나중에-> transition의 duality가 될 후보 CellSpaceBoundary를 찾아야한다. (Boundary semantic)
		// 후보 Boundary를 찾고 남은 나머지 Boundary는 모두 벽으로 한다.

		List<CellSpace> remain = (List<CellSpace>) adjacencyCell.clone();
		List<CellSpace> deleted = new ArrayList<CellSpace>();
		for (CellSpaceBoundary boundary : partialBoundedBy) {
			if (handledBoundary.contains(boundary)) continue;

			// CellSpaceBoundary에 Transition Duality가 있으면 맞닿은 Cell을 바로 찾아낸다.
			Transition transitionDulaity = boundary.getDuality();
			if (transitionDulaity != null) {
				ArrayList<State> connectedStates = transitionDulaity.getConnects();
				for (State connectedState : connectedStates) {
					if (connectedState.equals(duality)) continue;
					else {
						CellSpace target = connectedState.getDuality();
						addToBoundaryCellSpaceMap(boundaryCellSpaceMap, boundary, target);
						remain.remove(target);
						deleted.add(target);
					}
				}
			} else {
				OrientableSurface boundaryGeometry = boundary.getGeometry3D().getGeometry();

				if (boundaryGeometry == null) {
					if (boundary.getGeometry3D().getPolygonGeometry() != null) {
						SurfaceBoundary polygonBoundary = boundary.getGeometry3D().getPolygonGeometry().getBoundary();
						boundaryGeometry = pf.createSurface(polygonBoundary);
					} else {
						throw new UnsupportedOperationException("Not exist geometry object of CellSpaceBoundary");
					}
				}

				// 아직 소거하지 않은 연결된 cell 중에서 붙어있는지 찾음
				CellSpace target = findAdjacencyCellSpace(boundaryGeometry, remain);
				if (target != null) {
					addToBoundaryCellSpaceMap(boundaryCellSpaceMap, boundary, target);
					remain.remove(target); // 소거
					deleted.add(target);
				} else {
					// 소거된 cell 중에서 붙어있는지 찾음
					target = findAdjacencyCellSpace(boundaryGeometry, deleted);
					if (target != null) { // 붙어있는 cell이 있을 경우
						addToBoundaryCellSpaceMap(boundaryCellSpaceMap, boundary, target);
					}
					// 붙어있는 cell이 없을 경우 나중에 벽으로 처리한다.
				}
			}

		}
		//System.out.println("facetCellSpaceMap created");

		cellSpace.setBoundaryCellSpaceMap(boundaryCellSpaceMap);
	}

	private CellSpace findAdjacencyCellSpace(OrientableSurface boundaryGeometry, List<CellSpace> cells) {
		for (CellSpace other : cells) {
			Surface lowerFacet = other.getFacets().get(other.getFacets().size() - 1);
			Solid otherSolid = other.getGeometry3D().getGeometry();

			// Solid에 boundaryGeometry가 포함되는지 알아보기 위해 contians를 했을 때 모두 false가 나옴
			// 소수점 차이로 인한 문제인것 같음
			if (lowerFacet.distance(boundaryGeometry) <= 0.05) {
				return other;
			}
		}

		return null;
	}

	private Map<CellSpaceBoundary, List<CellSpace>> addToBoundaryCellSpaceMap(Map<CellSpaceBoundary, List<CellSpace>> boundaryCellSpaceMap,
			CellSpaceBoundary boundary, CellSpace cell) {
		List<CellSpace> cellSpaceList = null;
		if (!boundaryCellSpaceMap.containsKey(boundary)) {
			cellSpaceList = new ArrayList<CellSpace>();
			boundaryCellSpaceMap.put(boundary, cellSpaceList);
		}
		cellSpaceList = boundaryCellSpaceMap.get(boundary);

		if (!cellSpaceList.contains(cell)) {
			cellSpaceList.add(cell);
		}

		return boundaryCellSpaceMap;
	}
}
