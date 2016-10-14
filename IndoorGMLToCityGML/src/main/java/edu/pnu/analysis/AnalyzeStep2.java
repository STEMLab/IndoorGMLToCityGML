package edu.pnu.analysis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.opengis.indoorgml.v_1_0.vo.core.CellSpace;
import net.opengis.indoorgml.v_1_0.vo.core.CellSpaceBoundary;
import net.opengis.indoorgml.v_1_0.vo.core.IndoorFeatures;
import net.opengis.indoorgml.v_1_0.vo.core.PrimalSpaceFeatures;

import org.geotools.geometry.iso.primitive.PrimitiveFactoryImpl;
import org.geotools.geometry.iso.primitive.RingImpl;
import org.opengis.geometry.DirectPosition;
import org.opengis.geometry.Envelope;
import org.opengis.geometry.primitive.Ring;
import org.opengis.geometry.primitive.Surface;

public class AnalyzeStep2 {
	private IndoorFeatures indoorFeatures;

	private List<Double> floorZOrdinates;
	private Map<Double, List<CellSpace>> floorCellSpaceMap;

	private PrimitiveFactoryImpl pf = null;

	public AnalyzeStep2(IndoorFeatures indoorFeatures, List<Double> floorZOrdinates, Map<Double, List<CellSpace>> floorCellSpaceMap,
			PrimitiveFactoryImpl pf) {
		this.indoorFeatures = indoorFeatures;
		this.floorZOrdinates = floorZOrdinates;
		this.floorCellSpaceMap = floorCellSpaceMap;
		this.pf = pf;
	}

	// Cell의 CellBoundary의 Semantic(Door, Wall, Virtual Wall)을 알아낸다.
	
	public void analyzeStep2() {
		// CellSpaceBoundary 분석
		PrimalSpaceFeatures psf = indoorFeatures.getPrimalSpaceFeatures();
		ArrayList<CellSpace> cellSpaceMember = psf.getCellSpace();
		
		for (Double z : floorZOrdinates) {
			List<CellSpace> floorCellSpace = floorCellSpaceMap.get(z);

			for (CellSpace cellSpace : floorCellSpace) {
				findBoundarySemantic(cellSpace);
			}
		}
	}
	
	private void findBoundarySemantic(CellSpace cellSpace) {
		int transitionSize = cellSpace.getDuality().getConnects().size();
		int connectionCnt = 0;
		
		Map<Surface, List<CellSpaceBoundary>> facetBoundaryMap = cellSpace.getFacetBoundaryMap();
		for (Entry<Surface, List<CellSpaceBoundary>> entry : facetBoundaryMap.entrySet()) {
			Surface facet = entry.getKey();
			List<CellSpaceBoundary> boundaryList = entry.getValue();
			
			// duality로 transition이 있는 CellBoundary부터 찾는다.
			for (CellSpaceBoundary boundary : boundaryList) {
				if (boundary.getDuality() != null) {
					// virtual wall, door
					distinguishDoorVirtualWall(facet, boundary);
					connectionCnt++;
				}				
			}
			
			
		}
		
		// cellspace semantic 찾아낸 후 해야한다.
		// duality가 있는 cellboundary의 개수가 transition 개수와 같다면 나머지 boundary는 모두 벽으로 처리한다.
		// 아니라면 문에 해당하는 boundary를 찾는다.
		for (Entry<Surface, List<CellSpaceBoundary>> entry : facetBoundaryMap.entrySet()) {
			Surface facet = entry.getKey();
			List<CellSpaceBoundary> boundaryList = entry.getValue();
			
			for (CellSpaceBoundary boundary : boundaryList) {
				if (boundary.getDuality() != null) continue;
				if (connectionCnt == transitionSize) {
					boundary.setEstimatedType("WALL");
				} else {
					// duality가 없는 boundary중에서 door가 될 수 것을 찾는다.
					if (isDoor(facet, boundary)) {
						boundary.setEstimatedType("DOOR");
					} else {
						boundary.setEstimatedType("WALL");
					}
				}
			}
		}
		
	}
	
	private boolean distinguishDoorVirtualWall(Surface facet, CellSpaceBoundary boundary) {
		if (isWall(facet, boundary)) { // boundary의 기하가 벽과 같으면 가상벽으로 본다.
			boundary.setEstimatedType("VIRTUALWALL");
			return true;
		} else if (isDoor(facet, boundary)) { // 다르면 문
			boundary.setEstimatedType("DOOR");
			return true;
		}
		
		return false;
	}
	
	private boolean isWall(Surface facet, CellSpaceBoundary boundary) {
		Surface boundaryGeometry = (Surface) boundary.getGeometry3D().getGeometry();
		
		if ( Math.abs(facet.getArea() - boundaryGeometry.getArea()) <= 0.001 ) {
			return true;
		}
		return false;
	}
	
	private boolean isDoor(Surface facet, CellSpaceBoundary boundary) {
		Surface boundaryGeometry = (Surface) boundary.getGeometry3D().getGeometry();
				
		// 문인지 검사
		// 1. 바닥면과 붙어있어야 한다.
		// 2. 보통 천장과는 붙어있지 않다.
		// 3. 넓이가 벽면보다 작다.
		// 바닥면과 붙어 있는지, 형태 넓이?
		Envelope facetEnv = facet.getEnvelope();
		Envelope boundaryEnv = boundaryGeometry.getEnvelope();
		
		Ring exteriorRing = boundaryGeometry.getBoundary().getExterior();
		List<DirectPosition> positions = ((RingImpl) exteriorRing).asDirectPositions();
		if (positions.size() == 5) {
			int lowerCnt = 0;
			int upperCnt = 0;
			
			for (int i = 0; i < positions.size() - 1; i++) {
				DirectPosition position = positions.get(i);
				double z = position.getOrdinate(2);
				
				if (z == boundaryEnv.getMinimum(2)) {
					lowerCnt++;
				}
				if (z == boundaryEnv.getMaximum(2)) {
					upperCnt++;
				}
			}
			
			if (lowerCnt == 2 && upperCnt == 2) {
				return true;
			}
		}
		
		return false;
	}
}
