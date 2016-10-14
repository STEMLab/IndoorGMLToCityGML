/**
 * 
 */
package edu.pnu.importexport.retrieve;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBElement;

import edu.pnu.common.geometry.model.STLineString;
import edu.pnu.common.geometry.model.STPoint;
import edu.pnu.common.geometry.model.STPolygon;
import edu.pnu.common.geometry.utils.WKBGeometryParser;
import edu.pnu.common.geometry.utils.WKBGeometryParsingUtil;
import net.opengis.gml.v_3_2_1.AbstractCurveType;
import net.opengis.gml.v_3_2_1.AbstractFeatureType;
import net.opengis.gml.v_3_2_1.AbstractGeometryType;
import net.opengis.gml.v_3_2_1.AbstractRingPropertyType;
import net.opengis.gml.v_3_2_1.AbstractRingType;
import net.opengis.gml.v_3_2_1.AbstractSolidType;
import net.opengis.gml.v_3_2_1.AbstractSurfaceType;
import net.opengis.gml.v_3_2_1.CodeType;
import net.opengis.gml.v_3_2_1.CompositeCurveType;
import net.opengis.gml.v_3_2_1.CompositeSurfaceType;
import net.opengis.gml.v_3_2_1.CurvePropertyType;
import net.opengis.gml.v_3_2_1.DirectPositionListType;
import net.opengis.gml.v_3_2_1.DirectPositionType;
import net.opengis.gml.v_3_2_1.GeometryPropertyType;
import net.opengis.gml.v_3_2_1.LineStringType;
import net.opengis.gml.v_3_2_1.LinearRingType;
import net.opengis.gml.v_3_2_1.MultiSurfacePropertyType;
import net.opengis.gml.v_3_2_1.MultiSurfaceType;
import net.opengis.gml.v_3_2_1.ObjectFactory;
import net.opengis.gml.v_3_2_1.OrientableSurfaceType;
import net.opengis.gml.v_3_2_1.PointPropertyType;
import net.opengis.gml.v_3_2_1.PointType;
import net.opengis.gml.v_3_2_1.PolygonType;
import net.opengis.gml.v_3_2_1.ShellPropertyType;
import net.opengis.gml.v_3_2_1.ShellType;
import net.opengis.gml.v_3_2_1.SignType;
import net.opengis.gml.v_3_2_1.SolidPropertyType;
import net.opengis.gml.v_3_2_1.SolidType;
import net.opengis.gml.v_3_2_1.StringOrRefType;
import net.opengis.gml.v_3_2_1.SurfacePropertyType;
import net.opengis.indoorgml.v_1_0.dao.DAO;
import net.opengis.indoorgml.v_1_0.vo.core.IndoorObject;
import net.opengis.indoorgml.v_1_0.vo.spatial.Curve;
import net.opengis.indoorgml.v_1_0.vo.spatial.Geometry;
import net.opengis.indoorgml.v_1_0.vo.spatial.MultiSurface;
import net.opengis.indoorgml.v_1_0.vo.spatial.Point;
import net.opengis.indoorgml.v_1_0.vo.spatial.Solid;
import net.opengis.indoorgml.v_1_0.vo.spatial.Surface;

public class GMLJAXBConvertUtil {
	
	private static final ObjectFactory gmlOf = new ObjectFactory();
	
	@SuppressWarnings("unchecked")
	public static List<CodeType> createCodeType(List<CodeType> target, String name, String nameCodeSpace) {
		if(name == null && nameCodeSpace == null) {
			return null;
		}
		
		CodeType codeType = gmlOf.createCodeType();
		codeType.setValue(name);
		codeType.setCodeSpace(nameCodeSpace);
		
		target.add(codeType);
		
		return target;
	}
	
	public static AbstractFeatureType createAbstractFeatureType(AbstractFeatureType target, IndoorObject vo) {
		if(vo != null){
			target.setId(vo.getGmlID());
			target.setName(createCodeType(target.getName(), vo.getName(), vo.getNameCodeSpace()));
			if(vo.getDescription() != null){
				StringOrRefType descriptionType = gmlOf.createStringOrRefType();
				descriptionType.setValue(vo.getDescription());
				target.setDescription(descriptionType);
			}
		}
		return target;
	}
	
	public static GeometryPropertyType createGeometryProperty(Geometry vo) {
		GeometryPropertyType target = gmlOf.createGeometryPropertyType();
		if(vo.getIsXLink() == true) {
			target.setHref("#" + vo.getGmlId());
		} else {
			JAXBElement<? extends AbstractGeometryType> geometry = createAbstractGeometryType(vo);
			if(geometry != null) {
				target.setAbstractGeometry(geometry);
			}
		}
		return target;
	}
	
	public static JAXBElement<? extends AbstractGeometryType> createAbstractGeometryType(Geometry vo) {
		
		AbstractGeometryType target = null;
		JAXBElement<? extends AbstractGeometryType> jGeometry = null;
		
		if(vo instanceof MultiSurface) {
			target = createMultiSurfaceType((MultiSurface) vo);
			jGeometry = gmlOf.createMultiSurface((MultiSurfaceType) target);
		} else {
			throw new UnsupportedOperationException("createAbstractGeometryType : unknown geometry");
		}
		
		return jGeometry;
	}


	public static DirectPositionType createDirectPositionType(STPoint vo) {
		DirectPositionType target = gmlOf.createDirectPositionType();
		
		List<Double> dList = target.getValue();
		dList.add(vo.X());
		dList.add(vo.Y());
		dList.add(vo.Z());
		
		return target;
	}
	
	public static SolidPropertyType createSolidPropertyType(Solid vo) {
		
		SolidPropertyType target = gmlOf.createSolidPropertyType();
		if(vo.getIsXLink() == true) {
			target.setHref("#" + vo.getGmlId());
		} else {
			JAXBElement<? extends AbstractSolidType> solid = createAbstractSolidType(vo);
			if(solid != null) {
				target.setAbstractSolid(solid);
			}
		}
		return target;
	}
	
	public static JAXBElement<? extends AbstractSolidType> createAbstractSolidType(Solid vo) {
		AbstractSolidType target = null;
		//TODO : always SolidType in this time.
		target = gmlOf.createSolidType();
		
		SolidType solid = (SolidType) target;
		
		//GML ID
		String gmlId = vo.getGmlId();
		target.setId(gmlId);
		
		//Description //TODO
		/*String description = vo.getDescription();
		if(description != null) {
			StringOrRefType descriptionType = gmlOf.createStringOrRefType();
			descriptionType.setValue(description);
			target.setDescription(descriptionType);
		}*/
		
		String srsName = vo.getSrsName();
		if(srsName != null) {
			target.setSrsName(srsName);
		}
		Integer srsDimension = vo.getSrsDimension();
		if(srsDimension != null) {
			target.setSrsDimension( new BigInteger(String.valueOf(srsDimension)));
		}
		
		//Exterior, Interior
		Surface exterior = vo.getExterior();
		if(exterior != null) {
			ShellPropertyType shellPropertyType = createShellPropertyType(exterior);
			if(shellPropertyType != null) {
				solid.setExterior(shellPropertyType);
			}
		}
		
		//TODO
		@SuppressWarnings("unused")
		List<Surface> interior = vo.getInterior();

		JAXBElement<? extends AbstractSolidType> jSolid = null;
		if(target instanceof SolidType) {
			jSolid = gmlOf.createSolid((SolidType) target);
		} else {
			throw new UnsupportedOperationException();
		}
		
		return jSolid;
	}
	
	private static ShellPropertyType createShellPropertyType(Surface vo) {
		ShellPropertyType target = gmlOf.createShellPropertyType();
		ShellType shellType = gmlOf.createShellType();
		List<SurfacePropertyType> sufaceMember = shellType.getSurfaceMember();
		
		if(vo.getChild() != null){
			for(Surface surface : vo.getChild()){
				sufaceMember.add(createSurfacePropertyType(surface));
			}
		}
		shellType.setSurfaceMember(sufaceMember);
		target.setShell(shellType);
		
		return target;
	}

	@SuppressWarnings("restriction")
	public static SurfacePropertyType createSurfacePropertyType(Surface vo) {
		SurfacePropertyType target = gmlOf.createSurfacePropertyType();
		if(vo.getIsXLink() == true) {
			target.setHref("#" + vo.getGmlId());
		} else {
			JAXBElement<? extends AbstractSurfaceType> surface = createAbstractSurfaceType(vo);
			if(surface != null) {
				target.setAbstractSurface(surface);
			}
		}
		return target;
	}
	
	@SuppressWarnings("restriction")
	public static JAXBElement<? extends AbstractSurfaceType> createAbstractSurfaceType(Surface vo) {
		AbstractSurfaceType target = null;
			
		//TODO : 
		if(vo.isComposite()) {
			target = gmlOf.createCompositeSurfaceType();
		} else if(vo.isReverse()) {
			target = gmlOf.createOrientableSurfaceType();
		} else {
			target = gmlOf.createPolygonType();
		}
		
		/* Common Attributes */
		//GML ID
		String gmlId = vo.getGmlId();
		target.setId(gmlId);
		
		//Description //TODO
		/*String description = vo.getDescription();
		if(description != null) {
			StringOrRefType descriptionType = gmlOf.createStringOrRefType();
			descriptionType.setValue(description);
			target.setDescription(descriptionType);
		}*/
		
		String srsName = vo.getSrsName();
		if(srsName != null) {
			target.setSrsName(srsName);
		}
		Integer srsDimension = vo.getSrsDimension();
		if(srsDimension != null) {
			System.out.println(srsDimension);
			target.setSrsDimension( new BigInteger(String.valueOf(srsDimension)));
		}
		
		if(target instanceof CompositeSurfaceType) {
			CompositeSurfaceType s = (CompositeSurfaceType) target;
			
			List<SurfacePropertyType> surfaceMember = s.getSurfaceMember();
			List<Surface> child = vo.getChild();
			for(Surface surface : child) {
				SurfacePropertyType surfaceProp = createSurfacePropertyType(surface);
				surfaceMember.add(surfaceProp);
			}
		} else if(target instanceof OrientableSurfaceType) {
			OrientableSurfaceType s = (OrientableSurfaceType) target;
			
			//TODO
			SurfacePropertyType surfaceProp = createSurfacePropertyType(vo.getChild().get(0));
			s.setBaseSurface(surfaceProp);
			s.setOrientation(SignType.VALUE_1);
			
			
		} else if(target instanceof PolygonType) {
			PolygonType s = (PolygonType) target;
			
			byte[] polygonGeometry = vo.getPolygonGeometry();
			if(polygonGeometry != null) {
				System.out.println(DAO.byteArrayToHex(polygonGeometry));
				
				STPolygon polygon = WKBGeometryParsingUtil.createPolygon(polygonGeometry);
				System.out.println(polygon.asText());
				//Exterior
				STLineString exterior = polygon.exteriorRing();
				if(exterior != null) {
					AbstractRingPropertyType value = createAbstractRingPropertyType(exterior);
					s.setExterior(value);
				}
				
				//Interior
				List<AbstractRingPropertyType> interiorProps = s.getInterior();
				for(int i = 0; i < polygon.numInteriorRing(); i++) {
					STLineString interior = polygon.interiorRingN(i);
					AbstractRingPropertyType value = createAbstractRingPropertyType(interior);
					interiorProps.add(value);
				}
			}
			
		}
		
		JAXBElement<? extends AbstractSurfaceType> jSurface = null;
		if(target instanceof CompositeSurfaceType) {
			jSurface = gmlOf.createCompositeSurface((CompositeSurfaceType) target);
		} else if(target instanceof OrientableSurfaceType) {
			jSurface = gmlOf.createOrientableSurface((OrientableSurfaceType) target);
		} else if(target instanceof PolygonType) {
			jSurface = gmlOf.createPolygon((PolygonType) target);
		}
		else {
			throw new UnsupportedOperationException();
		}
		
		return jSurface;
	}

	@SuppressWarnings("restriction")
	public static AbstractRingPropertyType createAbstractRingPropertyType(STLineString ring) {
		AbstractRingPropertyType target = gmlOf.createAbstractRingPropertyType();
		
		JAXBElement<? extends AbstractRingType> aRing = createRingType(ring);
		
		target.setAbstractRing(aRing);
		
		return target;
	}

	@SuppressWarnings("restriction")
	public static JAXBElement<? extends AbstractRingType> createRingType(STLineString ring) {
		
		AbstractRingType target = null;
		//TODO : citygml only use linearRing
		target = gmlOf.createLinearRingType();
		LinearRingType lRing = (LinearRingType) target;
		
		DirectPositionListType directPosition = gmlOf.createDirectPositionListType();
		List<Double> dList = directPosition.getValue();
		
		for(int i=0; i<ring.numPoints(); i++) {
			STPoint point = ring.PointN(i);
			dList.add(point.X());
			dList.add(point.Y());
			dList.add(point.Z());
		}
		lRing.setPosList(directPosition);
		
		JAXBElement<? extends AbstractRingType> jRing = null;
		if(target instanceof LinearRingType) {
			jRing = gmlOf.createLinearRing((LinearRingType) target);
		}
		
		return jRing;
	}

	public static MultiSurfacePropertyType createMultiSurfacePropertyType(MultiSurface vo) {
		MultiSurfacePropertyType target = gmlOf.createMultiSurfacePropertyType();
		if(vo.getIsXLink() == true) {
			target.setHref("#" + vo.getGmlId());
		} else {
			MultiSurfaceType multiSurface = createMultiSurfaceType(vo);
			if(multiSurface != null) {
				target.setMultiSurface(multiSurface);
			}
		}
		return target;
	}
	
	public static MultiSurfaceType createMultiSurfaceType(MultiSurface vo) {
		MultiSurfaceType target = gmlOf.createMultiSurfaceType();
		
		//GML ID
		String gmlId = vo.getGmlId();
		target.setId(gmlId);
		
		//Description //TODO
		/*String description = vo.getDescription();
		if(description != null) {
			StringOrRefType descriptionType = gmlOf.createStringOrRefType();
			descriptionType.setValue(description);
			target.setDescription(descriptionType);
		}*/
		
		String srsName = vo.getSrsName();
		if(srsName != null) {
			target.setSrsName(srsName);
		}
		Integer srsDimension = vo.getSrsDimension();
		if(srsDimension != null) {
			target.setSrsDimension( new BigInteger(String.valueOf(srsDimension)));
		}
		
		List<SurfacePropertyType> surfaceMember = target.getSurfaceMember();
		List<Surface> surfaces = vo.getFacets();
		for(Surface s : surfaces) {
			SurfacePropertyType surfaceProp = createSurfacePropertyType(s);
			if(surfaceProp != null) surfaceMember.add(surfaceProp);
		}
		
		return target;
	}

	public static PointPropertyType creatPointPropertyType(Point vo) {
		PointPropertyType pointPropertyType = gmlOf.createPointPropertyType();
		PointType target = gmlOf.createPointType();
		
		String gmlId = vo.getGmlId();
		target.setId(gmlId);
		String srsName = vo.getSrsName();
		if(srsName != null) {
			target.setSrsName(srsName);
		}
		Integer srsDimension = vo.getSrsDimension();
		if(srsDimension != null) {
			target.setSrsDimension( new BigInteger(String.valueOf(srsDimension)));
		}
		DirectPositionType directPositionType = createDirectPositionType(vo);
		target.setPos(directPositionType);
		
		pointPropertyType.setPoint(target);
		
		return pointPropertyType;
	}

	private static DirectPositionType createDirectPositionType(Point vo) {
		DirectPositionType target = gmlOf.createDirectPositionType();
		
		target.setValue(createDirectPositionValue(vo.getGeometry()));
		
		return target;
	}
	
	private static List<Double> createDirectPositionValue(byte[] wkb) {
		STPoint point = (STPoint) WKBGeometryParser.getParser().parseWKB(wkb);
		
		List<Double> values = new ArrayList<Double>();
		values.add(point.X());
		values.add(point.Y());
		values.add(point.Z());
				
		return values;
	}

	public static CurvePropertyType createCurvePorpertyType(Curve geometry) {
		CurvePropertyType target = gmlOf.createCurvePropertyType();
		
		target.setAbstractCurve(createAbstractCurveType(geometry));
		
		return target;
	}

	@SuppressWarnings("restriction")
	private static JAXBElement<? extends AbstractCurveType> createAbstractCurveType(Curve vo) {
		
		AbstractCurveType target = null;
		
		//TODO : 
		if(vo.isComposite()) {
			target = gmlOf.createCompositeCurveType();
		} else {
			target = gmlOf.createLineStringType();
		}
		
		/* Common Attributes */
		//GML ID
		String gmlId = vo.getGmlId();
		target.setId(gmlId);
		
		//Description //TODO
		/*String description = vo.getDescription();
		if(description != null) {
			StringOrRefType descriptionType = gmlOf.createStringOrRefType();
			descriptionType.setValue(description);
			target.setDescription(descriptionType);
		}*/
		
		String srsName = vo.getSrsName();
		if(srsName != null) {
			target.setSrsName(srsName);
		}
		Integer srsDimension = vo.getSrsDimension();
		if(srsDimension != null) {
			System.out.println(srsDimension);
			target.setSrsDimension( new BigInteger(String.valueOf(srsDimension)));
		}
		
		if(target instanceof CompositeCurveType){
			
		} else if (target instanceof LineStringType){
			LineStringType lineStringType = (LineStringType) target;
			
			byte[] lineStringGeometry = vo.getLineStringGeometry();
			if(lineStringGeometry != null) {
				STLineString lineString = WKBGeometryParsingUtil.createLineString(lineStringGeometry);
				
				DirectPositionListType directPosition = gmlOf.createDirectPositionListType();
				List<Double> dList = directPosition.getValue();
				
				for(int i = 0; i < lineString.numPoints(); i++) {
					STPoint point = lineString.PointN(i);
					dList.add(point.X());
					dList.add(point.Y());
					dList.add(point.Z());
				}
				
				lineStringType.setPosList(directPosition);
			}
		}
		
		JAXBElement<? extends AbstractCurveType> jCurve = null;
		if(target instanceof CompositeCurveType) {
			jCurve = gmlOf.createCompositeCurve((CompositeCurveType) target);
		} else if(target instanceof LineStringType) {
			jCurve = gmlOf.createLineString((LineStringType) target);
		} else {
			throw new UnsupportedOperationException();
		}
		
		return jCurve;
	}
}
