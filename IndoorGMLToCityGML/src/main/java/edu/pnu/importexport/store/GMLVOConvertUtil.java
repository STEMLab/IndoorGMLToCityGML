/**
 * 
 */
package edu.pnu.importexport.store;

import net.opengis.indoorgml.v_1_0.vo.spatial.VOCurve;
import net.opengis.indoorgml.v_1_0.vo.spatial.VOGeometry;
import net.opengis.indoorgml.v_1_0.vo.spatial.VOMultiCurve;
import net.opengis.indoorgml.v_1_0.vo.spatial.VOMultiSurface;
import net.opengis.indoorgml.v_1_0.vo.spatial.VOPoint;
import net.opengis.indoorgml.v_1_0.vo.spatial.VOSolid;
import net.opengis.indoorgml.v_1_0.vo.spatial.VOSurface;

import org.opengis.geometry.coordinate.Polygon;
import org.opengis.geometry.primitive.Curve;
import org.opengis.geometry.primitive.OrientableSurface;
import org.opengis.geometry.primitive.Point;
import org.opengis.geometry.primitive.Solid;

import edu.pnu.importexport.BindingNode;

/**
 * @author hgryoo
 *
 */
public class GMLVOConvertUtil {

	public static VOGeometry createGeometry(BindingNode node) {
		VOGeometry geometry = null;
		
		return geometry;
	}
	public static VOPoint createPoint(BindingNode node) {
		VOPoint voPoint = new VOPoint();
		
		voPoint.setGmlId((String)node.getAttribute("GMLID"));
		voPoint.setSrsName((String)node.getAttribute("SRSNAME"));
		voPoint.setSrsDimension((Integer)node.getAttribute("SRSDIMENSION"));
		voPoint.setGeometryType(voPoint.getClass().getSimpleName());
		
		String XLinkValue = (String) node.getAttribute("HREF");
		if(XLinkValue != null) voPoint.setIsXLink(true);
		
		if(node.getAssociation("POS") != null) {
			Point point = ISOGeometryConvertUtil.convertPoint(node);
			voPoint.setGeometry(point);
		}
		
		return voPoint;
	}
	public static VOCurve createCurve(BindingNode node) {
		VOCurve voCurve = new VOCurve();

		voCurve.setGmlId((String)node.getAttribute("GMLID"));
		voCurve.setSrsName((String)node.getAttribute("SRSNAME"));
		voCurve.setSrsDimension((Integer)node.getAttribute("SRSDIMENSION"));
		voCurve.setGeometryType(voCurve.getClass().getSimpleName());
		
		String XLinkValue = (String) node.getAttribute("HREF");
		if(XLinkValue != null) voCurve.setIsXLink(true);

		//TODO Another Geometry Type
		if(node.getCollection("DIRECTPOSITIONTYPE") != null) {
			//STLineString stLineString = GeometryConvertUtil.createRingFromAbstractRingPropertyType(node);
			Curve curve = ISOGeometryConvertUtil.convertCurve(node);
			voCurve.setGeometry(curve);
		}
		
		return voCurve;
	}
	
	public static VOSurface createSurface(BindingNode node) {
		//TODO Maybe needs to exchange create STSurface Type
		VOSurface voSurface = new VOSurface();
		
		voSurface.setGmlId((String)node.getAttribute("GMLID"));
		voSurface.setSrsName((String)node.getAttribute("SRSNAME"));
		voSurface.setSrsDimension((Integer)node.getAttribute("SRSDIMENSION"));
		voSurface.setGeometryType(voSurface.getClass().getSimpleName());
		
		String XLinkValue = (String) node.getAttribute("HREF");
		if(XLinkValue != null) voSurface.setIsXLink(true);

		
		if(node.getAssociation("POLYGONTYPE") != null) {
			BindingNode polygonNode = node.getAssociation("POLYGONTYPE");
			//STPolygon polygon = GeometryConvertUtil.createPolygonFromPolygonType(polygonNode);
			Polygon polygon = ISOGeometryConvertUtil.convertPolygon(polygonNode);
			if(polygon != null) {
				voSurface.setPolygonGeometry(polygon);
			}
			
			// PolygonType으로 생성하지 말고 Surface
		} else if (node.getAssociation("ORIENTABLESURFACETYPE") != null) {
			OrientableSurface surface = ISOGeometryConvertUtil.createASurface(node);
			if (surface != null) {
				voSurface.setGeometry(surface);
			}
		}
		/*
		if(node.getCollection("SHELL") != null){
			
			List<VOSurface> child = voSurface.getChild();
			List<BindingNode> surfaceMember = node.getCollection("SHELL");
			for(BindingNode surfaceNode : surfaceMember) {
				VOSurface s = createSurface(surfaceNode);
				
				if(voSurface.getRoot() == null) {
					s.setRoot(voSurface);
				} else {
					s.setRoot(voSurface.getRoot());
				}
				s.setParent(voSurface);
				
				child.add(s);
			}
		}
		*/
		//TODO Geometry
		
		return voSurface;
	}
	
	public static VOSolid createSolid(BindingNode node) {
		VOSolid voSolid = new VOSolid();
		
		//Non Geometry Attributes
		voSolid.setGmlId((String)node.getAttribute("GMLID"));
		voSolid.setSrsName((String)node.getAttribute("SRSNAME"));
		voSolid.setSrsDimension((Integer)node.getAttribute("SRSDIMENSION"));
		voSolid.setGeometryType(voSolid.getClass().getSimpleName());
		
		Solid solid = ISOGeometryConvertUtil.convertSolid(node);
		voSolid.setGeometry(solid);
		
		return voSolid;
	}
	
	public static VOMultiCurve createMultiCurve(BindingNode node) {
		VOMultiCurve voMultiCurve = new VOMultiCurve();
		
		voMultiCurve.setGmlId((String)node.getAttribute("GMLID"));
		voMultiCurve.setSrsName((String)node.getAttribute("SRSNAME"));
		voMultiCurve.setSrsDimension((Integer)node.getAttribute("SRSDIMENSION"));
		voMultiCurve.setGeometryType(voMultiCurve.getClass().getSimpleName());
		
		String XLinkValue = (String) node.getAttribute("HREF");
		if(XLinkValue != null) voMultiCurve.setIsXLink(true);
		
		//Geometry Attributes
		/*
		List<VOCurve> curveMember = voMultiCurve.getCurves();
		List<BindingNode> curveMemberNodes = node.getCollection("CURVEMEMBER");
		for(BindingNode curveNode : curveMemberNodes) {
			VOCurve curve = createCurve(curveNode);
			curveMember.add(curve);
		}
		*/
		//GEOMETRY
		
		return voMultiCurve;
	}
	
	public static VOMultiSurface createMultiSurface(BindingNode node) {
		VOMultiSurface multiSurface = new VOMultiSurface();
		//Non Geometry Attributes
		multiSurface.setGmlId((String)node.getAttribute("GMLID"));
		multiSurface.setSrsName((String)node.getAttribute("SRSNAME"));
		multiSurface.setSrsDimension((Integer)node.getAttribute("SRSDIMENSION"));
		multiSurface.setGeometryType(multiSurface.getClass().getSimpleName());
		
		String XLinkValue = (String) node.getAttribute("HREF");
		if(XLinkValue != null) multiSurface.setIsXLink(true);
		
		//Geometry Attributes
		/*
		List<VOSurface> surfaceMember = multiSurface.getSurfaces();
		List<BindingNode> surfaceMemberNodes = node.getCollection("SURFACEMEMBER");
		for(BindingNode surfaceNode : surfaceMemberNodes) {
			VOSurface surface = createSurface(surfaceNode);
			surfaceMember.add(surface);
		}
		*/
		//TODO (make surface)
		
		
		return multiSurface;
	}
}
