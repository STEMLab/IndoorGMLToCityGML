/**
 * 
 */
package edu.pnu.importexport.store;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.geotools.factory.GeoTools;
import org.geotools.factory.Hints;
import org.geotools.geometry.GeometryBuilder;
import org.geotools.geometry.iso.primitive.PrimitiveFactoryImpl;
import org.geotools.geometry.iso.primitive.RingImplUnsafe;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.opengis.geometry.DirectPosition;
import org.opengis.geometry.Geometry;
import org.opengis.geometry.complex.CompositeSurface;
import org.opengis.geometry.coordinate.Polygon;
import org.opengis.geometry.coordinate.PolyhedralSurface;
import org.opengis.geometry.primitive.Curve;
import org.opengis.geometry.primitive.OrientableSurface;
import org.opengis.geometry.primitive.Point;
import org.opengis.geometry.primitive.Ring;
import org.opengis.geometry.primitive.Shell;
import org.opengis.geometry.primitive.Solid;
import org.opengis.geometry.primitive.SolidBoundary;
import org.opengis.geometry.primitive.Surface;
import org.opengis.geometry.primitive.SurfaceBoundary;

import edu.pnu.importexport.BindingNode;

/**
 * @author hgryoo
 * @author DongUk Seo
 *
 */
public class ISOGeometryConvertUtil {
	private static Hints hints = null;
	private static GeometryBuilder builder = null;
	private static PrimitiveFactoryImpl pf = null;
	
	static {		
		hints = GeoTools.getDefaultHints();
        hints.put(Hints.CRS, DefaultGeographicCRS.WGS84_3D);
        hints.put(Hints.GEOMETRY_VALIDATE, false);
        builder = new GeometryBuilder(hints);
        pf = (PrimitiveFactoryImpl) builder.getPrimitiveFactory();
	}
	
	public static Geometry convertGeometry(BindingNode node) {
		Geometry geom = null;
		/*
		String geometryType = (String) node.getAttribute("GEOMETRYTYPE");
		
		if("MultiSurfaceType".equalsIgnoreCase(geometryType)) {
			geom = convertMultiSurface(node); 
		}
		else if("PolygonType".equalsIgnoreCase(geometryType)) {
			geom = convertPolygon(node);
		}
		*/
		//TODO
		
		return geom;
	}
	
/*	public static STGeometryCollection convertGeometryCollection(BindingNode node) {
	
	}*/
	/*
	public static STGeometryCollection wrapGeometries(STGeometry[] geoms) {
		STGeometryCollection geomCollection = gf.createGeometryCollection(geoms);
		return geomCollection;
	}
	*/
	public static Solid convertSolid(BindingNode node) {
		
		//CompositeSolid
		List<BindingNode> solidMember = node.getCollection("SOLIDMEMBER");
		if(!solidMember.isEmpty()) {
			throw new UnsupportedOperationException();
		}
		
		//Solid
		BindingNode exteriorNode = node.getAssociation("EXTERIOR");
		Shell exterior = null;
		if (exteriorNode != null) {
			BindingNode exteriorShellNode = exteriorNode.getAssociation("SHELLTYPE");
			if (exteriorShellNode != null) {
				exterior = createShell(exteriorShellNode);
			}
		}
		
		List<BindingNode> interiorNode = node.getCollection("INTERIOR");
		List<Shell> interiors = new ArrayList<Shell>();
		for (BindingNode n : interiorNode) {
			BindingNode interiorShellNode = n.getAssociation("SHELLTYPE");
			Shell interior = null;
			if (interiorShellNode != null) {
				interior = createShell(n);
			}
			if (interior != null) interiors.add(interior);
		}
		SolidBoundary solidBoundary = pf.createSolidBoundary(exterior, interiors);
		Solid solid = pf.createSolid(solidBoundary);
				
		return solid;
	}

	/*
	public static STMultiSurface convertMultiSurface(BindingNode node) {
		STMultiSurface multisurface = null;
		
		List<BindingNode> surfaceMemberNodes = node.getCollection("SURFACEMEMBER");
		List<STSurface> surfaceMember = new ArrayList<STSurface>();
		for(int i = 0; i < surfaceMemberNodes.size(); i++) {
			STSurface s = createASurface(surfaceMemberNodes.get(i));
			surfaceMember.add(s);
		}
		
		STSurface[] surfaces = new STSurface[surfaceMember.size()];
		surfaceMember.toArray(surfaces);
		
		multisurface = gf.createMultiSurface(surfaces);
		return multisurface;
	}
	*/
	
	public static Shell createShell(BindingNode node) {
		Shell shell = null;
		
		List<BindingNode> surfaceMemberNodes = node.getCollection("SURFACEMEMBER");
		List<OrientableSurface> surfaceMember = new ArrayList<OrientableSurface>();
		
		//
		for(int i = 0; i<surfaceMemberNodes.size(); i++) {
			OrientableSurface sm = createASurface(surfaceMemberNodes.get(i));
			
			// only consider that surface type is polygon
			surfaceMember.add(sm);
			
			/*
			if(sm.geometryType().equalsIgnoreCase("Polygon")) {
				STPolygon p = (STPolygon) sm;
				surfaceMember.add(p);
			} 
			else if(sm.geometryType().equalsIgnoreCase("Triangle")) {
				STPolygon p = (STPolygon) sm;
				surfaceMember.add(p);
			}
			else if(sm.geometryType().equalsIgnoreCase("PolyhedralSurface")) {
				STPolyhedralSurface p = (STPolyhedralSurface) sm;
				for(int j = 0; j < p.numPatches(); j++) {
					surfaceMember.add(p.PatchN(j));
				}
			}
			else if(sm.geometryType().equalsIgnoreCase("TIN")) {
				STTIN p = (STTIN) sm;
				for(int j = 0; j < p.numPatches(); j++) {
					surfaceMember.add(p.PatchN(j));
				}
			}
			*/
		}
		
		shell = pf.createShell(surfaceMember);
		return shell;
	}
	
	public static OrientableSurface createASurface(BindingNode node) {
		OrientableSurface surface = null;

		// Only consider the situation that the number of surface patch is 1
		if(node.getAssociation("POLYGONTYPE") != null) {
			BindingNode polygonNode = node.getAssociation("POLYGONTYPE");
			Polygon polygon = convertPolygon(polygonNode);
			SurfaceBoundary boundary = polygon.getBoundary();
			 
			surface = pf.createSurface(boundary);
		} else if(node.getAssociation("COMPOSITESURFACETYPE") != null) {
			BindingNode compositeSurfaceNode = node.getAssociation("COMPOSITESURFACETYPE");
			surface = convertCompositeSurface(compositeSurfaceNode);
			
		} else if(node.getAssociation("ORIENTABLESURFACETYPE") != null) {
			BindingNode orientableSurfaceNode = node.getAssociation("ORIENTABLESURFACETYPE");
			String orientation = (String) orientableSurfaceNode.getAttribute("ORIENTATION");
			
			BindingNode baseSurfaceNode = orientableSurfaceNode.getAssociation("BASESURFACE");
			if(baseSurfaceNode != null) {
				surface = createASurface(baseSurfaceNode);
				if("-".equalsIgnoreCase(orientation)) {
					reverseSurface(surface);
				}
			} else {
				throw new IllegalArgumentException("BaseSurface is empty");
			}
			
		} else if(node.getAssociation("SURFACETYPE") != null) {
			//TODO
			throw new UnsupportedOperationException();
		}
		
		return surface;
	}
	
	private static void reverseSurface(OrientableSurface surface) {

		// Only consider the situation that the number of surface patch is 1
		if (surface instanceof Surface) {
			SurfaceBoundary boundary = surface.getBoundary();
			Ring exterior = boundary.getExterior();
			List<Ring> interiors = boundary.getInteriors();
			
			Ring rExterior = null;
			List<Ring> rInteriors = null;
			
			List<DirectPosition> positions = ((RingImplUnsafe) exterior).asDirectPositions();		
			List<DirectPosition> reverse = reverseList(positions);
			rExterior = pf.createRingByDirectPositions(reverse);
			
			if (interiors != null && interiors.size() > 0) {
				rInteriors = new ArrayList<Ring>();
				
				for (Ring interior : interiors) {
					List<DirectPosition> interiorPositions = ((RingImplUnsafe) interior).asDirectPositions();
					List<DirectPosition> reverseInteriorPositions = reverseList(interiorPositions);
					Ring rInterior = pf.createRingByDirectPositions(reverseInteriorPositions);
					
					rInteriors.add(rInterior);
				}
			}
			
			SurfaceBoundary rBoundary = pf.createSurfaceBoundary(rExterior, rInteriors);
			surface = pf.createSurface(rBoundary);
		}
		/*
		else if(surface instanceof STPolyhedralSurface) {
			STPolyhedralSurface ph = (STPolyhedralSurface) surface;
			
			for(int i = 0 ; i < ph.numPatches(); i ++) {
				STPolygon p = ph.PatchN(i);
				reverseSurface(p);
			}
		}
		*/
		else {
			throw new UnsupportedOperationException();
		}
	}
	
	public static CompositeSurface convertCompositeSurface(BindingNode node) {
		/*
		List<BindingNode> surfaceMemberNodes = node.getCollection("SURFACEMEMBER");
		List<STPolygon> surfaceMember = new ArrayList<STPolygon>();
		for(int i = 0; i<surfaceMemberNodes.size(); i++) {
			STSurface sm = createASurface(surfaceMemberNodes.get(i));
			
			if(sm.geometryType().equalsIgnoreCase("Polygon")) {
				STPolygon p = (STPolygon) sm;
				surfaceMember.add(p);
			} 
			else if(sm.geometryType().equalsIgnoreCase("Triangle")) {
				STPolygon p = (STPolygon) sm;
				surfaceMember.add(p);
			}
			else if(sm.geometryType().equalsIgnoreCase("PolyhedralSurface")) {
				STPolyhedralSurface p = (STPolyhedralSurface) sm;
				for(int j = 0; j < p.numPatches(); j++) {
					surfaceMember.add(p.PatchN(j));
				}
			}
			else if(sm.geometryType().equalsIgnoreCase("TIN")) {
				STTIN p = (STTIN) sm;
				for(int j = 0; j < p.numPatches(); j++) {
					surfaceMember.add(p.PatchN(j));
				}
			}
		}
		
		STPolygon[] patches = new STPolygon[surfaceMember.size()];
		surfaceMember.toArray(patches);
		
		STPolyhedralSurface polyhedralSurface = gf.createPolyhedralSurface(patches);
		return polyhedralSurface;
		*/
		throw new UnsupportedOperationException();
	}
	
	//TODO : surfacePatch or TIN
	public static PolyhedralSurface convertSurface(BindingNode node) {
		throw new UnsupportedOperationException();
	}
	
	public static Polygon convertPolygon(BindingNode node) {
		Polygon polygon = null;
		BindingNode exteriorNode = node.getAssociation("EXTERIOR");
		if(exteriorNode != null) {
			Ring exterior = convertRing(exteriorNode);
			
			List<Ring> interiors = null;
			List<BindingNode> interiorNode = node.getCollection("INTERIOR");
			if(interiorNode != null && interiorNode.size() > 0) {
				interiors = new ArrayList<Ring>();
				for(int i = 0; i < interiorNode.size(); i++) {
					interiors.add(convertRing(interiorNode.get(i)));
				}
			}
			
			if(exterior != null) {
				SurfaceBoundary boundary = builder.createSurfaceBoundary(exterior, interiors);
				polygon = builder.createPolygon(boundary);
				//System.out.println(polygon.asText());
			}
		}
		return polygon;
	}
	
	public static Ring convertRing(BindingNode node) {
		Ring ring = null;

		//TODO : consider another node types
		BindingNode directPositionList = node.getAssociation("DIRECTPOSITIONLIST");
		if(directPositionList != null) {
			List<DirectPosition> positions = createPositionsFromDirectPositionList(directPositionList);
			if(positions != null) {
				ring = pf.createRingByDirectPositions(positions);
				//System.out.println("inserted Coodinates = " + lineString.asText());
			}
		}
		
		List<BindingNode> directPositionNode = node.getCollection("DIRECTPOSITION");
		if(directPositionNode != null && directPositionNode.size() > 0) {
			Point[] points = new Point[directPositionNode.size()];
			for (int i = 0; i < directPositionNode.size(); i++) {
				points[i] = createPointFromDirectPostion(directPositionNode.get(i));
			}
			List<DirectPosition> positions = new ArrayList<DirectPosition>();
			for (int i = 0; i < points.length; i++) {
				positions.add(points[i].getDirectPosition());				
			}
			ring = pf.createRingByDirectPositions(positions);
		}
		
		return ring;
	}
	
	public static Curve convertCurve(BindingNode node) {
		Curve curve = null;

		//TODO : consider another node types
		BindingNode directPositionList = node.getAssociation("DIRECTPOSITIONLIST");
		if(directPositionList != null) {
			List<DirectPosition> positions = createPositionsFromDirectPositionList(directPositionList);
			if(positions != null) {
				curve = pf.createCurveByDirectPositions(positions);
				//System.out.println("inserted Coodinates = " + lineString.asText());
			}
		}
		
		List<BindingNode> directPositionNode = node.getCollection("DIRECTPOSITION");
		if(directPositionNode != null && directPositionNode.size() > 0) {
			Point[] points = new Point[directPositionNode.size()];
			for (int i = 0; i < directPositionNode.size(); i++) {
				points[i] = createPointFromDirectPostion(directPositionNode.get(i));
			}
			List<DirectPosition> positions = new ArrayList<DirectPosition>();
			for (int i = 0; i < points.length; i++) {
				positions.add(points[i].getDirectPosition());				
			}
			curve = pf.createCurveByDirectPositions(positions);
		}
		
		return curve;
	}
	
	public static Point convertPoint(BindingNode node) {
		Point point = null;
		
		BindingNode posNode = node.getAssociation("POS");
		if(posNode != null) {
			point = createPointFromDirectPostion(posNode);
		}
		
		//TODO
		//Coordinates Type
		
		//TODO
		//Coord Type
		return point;
	}
	
	public static List<DirectPosition> createPositionsFromDirectPositionList(BindingNode node) {		
		List<DirectPosition> positionList = null;
		
		Object o = node.getAttribute("VALUE");
		if(o != null) {
			positionList = new ArrayList<DirectPosition>();
			
			List<Double> doubleList = (List<Double>) o;
			double[] ordinates = null;
			for (int i = 0; i < doubleList.size(); i = i + 3) {
				ordinates = new double[3];
				ordinates[0] = doubleList.get(i);
				ordinates[1] = doubleList.get(i + 1);
				ordinates[2] = doubleList.get(i + 2);
				
				DirectPosition position = builder.createDirectPosition(ordinates);
				positionList.add(position);
			}
		}
		
		return positionList;
	}
	
	public static Point createPointFromDirectPostion(BindingNode node) {
		List<Double> value = (List<Double>) node.getAttribute("VALUE");
		
		//remove nulls from list
		value.removeAll(Collections.singleton(null));
		
		Point point = createPointFromDoubleList(value);
		
		return point;
	}
	
	public static Point createPointFromDoubleArray(double[] coords) {
		Point point = pf.createPoint(coords);
		
		return point;
	}
	
	public static Point createPointFromDoubleList(List<Double> dList) {
		Double[] dwarr = new Double[3];
		dList.toArray(dwarr);
		double[] darr = toDoublePrimitive(dwarr);
		
		Point point = createPointFromDoubleArray(darr);
		return point;
	}
	
	private static double[] toDoublePrimitive(Double[] array) {
		if (array == null) {
			return null;
		} else if (array.length == 0) {
		    return new double[0];
		}
		final double[] result = new double[array.length];
		for (int i = 0; i < array.length; i++) {
			result[i] = array[i].doubleValue();
		}
		return result;
	}
	
	private static List<DirectPosition> reverseList(List<DirectPosition> list) {
		 if (list == null) {
			 return null;
		 } else if (list.size() == 0) {
			 return list;
		 }
		 
		 List<DirectPosition> reverse = new ArrayList<DirectPosition>();
		 for (int i = list.size() - 1; i >= 0; i--) {
			 reverse.add(list.get(i));
		 }
		 
		 return reverse;
	}
}
