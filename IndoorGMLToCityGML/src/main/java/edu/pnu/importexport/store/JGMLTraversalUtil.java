/**
 * 
 */
package edu.pnu.importexport.store;

import java.math.BigInteger;
import java.util.List;

import javax.xml.bind.JAXBElement;

import net.opengis.gml.v_3_2_1.AbstractCurveType;
import net.opengis.gml.v_3_2_1.AbstractFeatureCollectionType;
import net.opengis.gml.v_3_2_1.AbstractFeatureType;
import net.opengis.gml.v_3_2_1.AbstractGMLType;
import net.opengis.gml.v_3_2_1.AbstractGeometricAggregateType;
import net.opengis.gml.v_3_2_1.AbstractGeometricPrimitiveType;
import net.opengis.gml.v_3_2_1.AbstractGeometryType;
import net.opengis.gml.v_3_2_1.AbstractRingPropertyType;
import net.opengis.gml.v_3_2_1.AbstractRingType;
import net.opengis.gml.v_3_2_1.AbstractSolidType;
import net.opengis.gml.v_3_2_1.AbstractSurfaceType;
import net.opengis.gml.v_3_2_1.BoundingShapeType;
import net.opengis.gml.v_3_2_1.CodeType;
import net.opengis.gml.v_3_2_1.CompositeCurveType;
import net.opengis.gml.v_3_2_1.CompositeSolidType;
import net.opengis.gml.v_3_2_1.CompositeSurfaceType;
import net.opengis.gml.v_3_2_1.CoordinatesType;
import net.opengis.gml.v_3_2_1.CurveArrayPropertyType;
import net.opengis.gml.v_3_2_1.CurvePropertyType;
import net.opengis.gml.v_3_2_1.DirectPositionListType;
import net.opengis.gml.v_3_2_1.DirectPositionType;
import net.opengis.gml.v_3_2_1.EnvelopeType;
import net.opengis.gml.v_3_2_1.FeaturePropertyType;
import net.opengis.gml.v_3_2_1.GeometryPropertyType;
import net.opengis.gml.v_3_2_1.LineStringType;
import net.opengis.gml.v_3_2_1.LinearRingType;
import net.opengis.gml.v_3_2_1.LocationPropertyType;
import net.opengis.gml.v_3_2_1.MultiCurvePropertyType;
import net.opengis.gml.v_3_2_1.MultiCurveType;
import net.opengis.gml.v_3_2_1.MultiPointPropertyType;
import net.opengis.gml.v_3_2_1.MultiPointType;
import net.opengis.gml.v_3_2_1.MultiSurfacePropertyType;
import net.opengis.gml.v_3_2_1.MultiSurfaceType;
import net.opengis.gml.v_3_2_1.ObjectFactory;
import net.opengis.gml.v_3_2_1.OrientableSurfaceType;
import net.opengis.gml.v_3_2_1.PointArrayPropertyType;
import net.opengis.gml.v_3_2_1.PointPropertyType;
import net.opengis.gml.v_3_2_1.PointType;
import net.opengis.gml.v_3_2_1.PolygonType;
import net.opengis.gml.v_3_2_1.RingType;
import net.opengis.gml.v_3_2_1.ShellPropertyType;
import net.opengis.gml.v_3_2_1.ShellType;
import net.opengis.gml.v_3_2_1.SignType;
import net.opengis.gml.v_3_2_1.SolidPropertyType;
import net.opengis.gml.v_3_2_1.SolidType;
import net.opengis.gml.v_3_2_1.StringOrRefType;
import net.opengis.gml.v_3_2_1.SurfaceArrayPropertyType;
import net.opengis.gml.v_3_2_1.SurfacePropertyType;
import net.opengis.gml.v_3_2_1.SurfaceType;
import edu.pnu.common.XLinkSymbolMap;
import edu.pnu.importexport.BindingNode;

/**
 * @author hgryoo
 *
 */
@SuppressWarnings("restriction")
public class JGMLTraversalUtil extends JAXBTraversalUtil{	
	public static BindingNode convertAbstractGMLType(AbstractGMLType target, BindingNode node, XLinkSymbolMap symbolMap) {
		/* ========== */
	    /* Attributes */
	    /* ========== */
		/* = GML Id, Name, Description ========== */
		node.addAttribute("GMLID", target.getId());
		//TODO: consider multiple name values.
		List<CodeType> name = target.getName();
		if(!name.isEmpty()) {
			CodeType nameCodeType = name.get(0);
			if(nameCodeType != null) node.addAssociation("NAME", convertCodeType(nameCodeType, new BindingNode(), symbolMap));
		}
		StringOrRefType description = target.getDescription();
		if(description != null) {
			node.addAssociation("DESCRIPTION", convertStringOrRefType(target.getDescription(), new BindingNode(), symbolMap));
		}
		return node;
	}
	
	public static BindingNode convertAbstractFeatureType(AbstractFeatureType target, BindingNode node, XLinkSymbolMap symbolMap) {
		/* =========== */
	    /* Super Class */
	    /* =========== */
		node = convertAbstractGMLType(target, node, symbolMap);
		/* ============ */
	    /* Association  */
	    /* ============ */
		/* = Boundedby Envelope ========== */
		BoundingShapeType boundedBy = target.getBoundedBy();
		if(boundedBy != null) {
			node.addAssociation("BOUNDEDBY", convertBoundingShapeType(boundedBy, new BindingNode(), symbolMap));
		}
		//TODO
		//location
		JAXBElement<? extends LocationPropertyType> je = target.getLocation();
		if(je != null) {
			LocationPropertyType lp = je.getValue();
			if(lp != null) {
				node.addAssociation("LOCATION", convertLocationPropertyType(lp, new BindingNode(), symbolMap));
			}
		}
		return node;
	}
	
	public static BindingNode convertAbstractFeatureCollectionType(AbstractFeatureCollectionType target, BindingNode node, XLinkSymbolMap symbolMap) {
		/* =========== */
	    /* Super Class */
	    /* =========== */
		node = convertAbstractFeatureType(target, node, symbolMap);
		/* ============ */
	    /* Association  */
	    /* ============ */
		List<FeaturePropertyType> featureMembers = target.getFeatureMember();
		//List<FeaturePropertyType> featureMembers = target.getFeatureMember();
		for(FeaturePropertyType fp : featureMembers) {
			BindingNode fpNode = new BindingNode();
			fpNode = convertFeaturePropertyType(fp, fpNode, symbolMap);
			node.addCollection("FEATURES", fpNode);
		}
		
		return node;
	}
	
	public static BindingNode convertLocationPropertyType(LocationPropertyType target, BindingNode node, XLinkSymbolMap symbolMap) {
		/* ========== */
	    /* Attributes */
	    /* ========== */
		/* = XLink attributes ========== */
		node.addAttribute("REMOTESCHEMA", target.getRemoteSchema());
		node.addAttribute("HREF", target.getHref());
		node.addAttribute("ROLE", target.getRole());
		node.addAttribute("ARCROLE", target.getArcrole());
		node.addAttribute("TITLE", target.getTitle());
		if(target.getTYPE() != null) {
			node.addAttribute("TYPE", target.getTYPE().value());
		}
		if(target.getShow() != null) {
			node.addAttribute("SHOW", target.getShow().value());
		}
		if(target.getActuate() != null) {
			node.addAttribute("ACTUATE", target.getActuate().value());
		}
		
		if(target.getHref() != null) {
			//TODO
		}
		
		// TODO
		
		return node;
	}

	@SuppressWarnings("rawtypes")
	public static BindingNode convertFeaturePropertyType(FeaturePropertyType target, BindingNode node, XLinkSymbolMap symbolMap) {
		/* ========== */
	    /* Attributes */
	    /* ========== */
		/* = XLink attributes ========== */
		node.addAttribute("REMOTESCHEMA", target.getRemoteSchema());
		node.addAttribute("HREF", target.getHref());
		node.addAttribute("ROLE", target.getRole());
		node.addAttribute("ARCROLE", target.getArcrole());
		node.addAttribute("TITLE", target.getTitle());
		if(target.getTYPE() != null) {
			node.addAttribute("TYPE", target.getTYPE().value());
		}
		if(target.getShow() != null) {
			node.addAttribute("SHOW", target.getShow().value());
		}
		if(target.getActuate() != null) {
			node.addAttribute("ACTUATE", target.getActuate().value());
		}
		
		Object feature = null;
		if(target.getHref() != null) {
			feature = symbolMap.getObjectById(target.getHref().replaceAll("#", ""));
		} else {
			feature = target.getAbstractFeature();
		}
		/* ============ */
	    /* Association  */
	    /* ============ */
		//deal with polymorphism
		if(feature != null) {
			if(feature instanceof JAXBElement) {
				feature = ((JAXBElement)feature).getValue();
			}
			/*
			BindingNode featureNode = new BindingNode();
			if(feature instanceof AbstractBuildingType) {
				node.addAttribute("FEATURETYPE", "Building");
				featureNode = JBuildingTraversalUtil.convertAbstractBuildingType((BuildingType) feature, featureNode, symbolMap);
			} else if( feature instanceof ReliefFeatureType) {
				node.addAttribute("FEATURETYPE", "ReliefFeature");
			}
			node.addAssociation("FEATURE", featureNode);
			*/
		}		
		return node;
	}
	
	public static BindingNode convertStringOrRefType(StringOrRefType target, BindingNode node, XLinkSymbolMap symbolMap) {
		/* ========== */
	    /* Attributes */
	    /* ========== */
		/* = XLink attributes ========== */
		node.addAttribute("REMOTESCHEMA", target.getRemoteSchema());
		node.addAttribute("HREF", target.getHref());
		node.addAttribute("ROLE", target.getRole());
		node.addAttribute("ARCROLE", target.getArcrole());
		node.addAttribute("TITLE", target.getTitle());
		if(target.getTYPE() != null) {
			node.addAttribute("TYPE", target.getTYPE().value());
		}
		if(target.getShow() != null) {
			node.addAttribute("SHOW", target.getShow().value());
		}
		if(target.getActuate() != null) {
			node.addAttribute("ACTUATE", target.getActuate().value());
		}
		
		//Value
		String value = null;
		if(target.getHref() != null) {
			value = (String) symbolMap.getObjectById(target.getHref().replaceAll("#", ""));
		} else {
			value = target.getValue();
		}
		node.addAttribute("VALUE", value);
		
		return node;
	}

	public static BindingNode convertCodeType(CodeType target, BindingNode node, XLinkSymbolMap symbolMap) {
		/* ========== */
	    /* Attributes */
	    /* ========== */
		node.addAttribute("VALUE", target.getValue());
		node.addAttribute("CODESPACE", target.getCodeSpace());
		return node;
	}
	
	/* ========================= */
	/* Geometry Property Types   */
	/* ========================= */
	public static BindingNode convertAbstractGeometryType(AbstractGeometryType target, BindingNode node, XLinkSymbolMap symbolMap) {
		/* =========== */
	    /* Super Class */
	    /* =========== */
		node = convertAbstractGMLType(target, node, symbolMap);
		/* ========== */
	    /* Attributes */
	    /* ========== */
		node.addAttribute("GID", target.getId());
		node.addAttribute("SRSNAME", target.getSrsName());
		if(target.getSrsDimension() != null) {
			node.addAttribute("SRSDIMENSION", target.getSrsDimension().intValue());
		}
		//TODO : axisLabels, uomLabels
		return node;
	}
	
	public static BindingNode convertAbstractGeometricPrimitiveType(AbstractGeometricPrimitiveType target, BindingNode node, XLinkSymbolMap symbolMap) {
		/* =========== */
	    /* Super Class */
	    /* =========== */
		node = convertAbstractGeometryType(target, node, symbolMap);
		return node;
	}
	
	public static BindingNode convertAbstractGeometricAggregateType(AbstractGeometricAggregateType target, BindingNode node, XLinkSymbolMap symbolMap) {
		/* =========== */
	    /* Super Class */
	    /* =========== */
		node = convertAbstractGeometryType(target, node, symbolMap);
		return node;
	}
	
	
	//GeometryProperty (Polymorphism)
	@SuppressWarnings("unchecked")
	public static BindingNode convertGeometryPropertyType(GeometryPropertyType target, BindingNode node, XLinkSymbolMap symbolMap) {
		/* ========== */
	    /* Attributes */
	    /* ========== */
		/* = XLink attributes ========== */
		node.addAttribute("REMOTESCHEMA", target.getRemoteSchema());
		node.addAttribute("HREF", target.getHref());
		node.addAttribute("ROLE", target.getRole());
		node.addAttribute("ARCROLE", target.getArcrole());
		node.addAttribute("TITLE", target.getTitle());
		if(target.getTYPE() != null) {
			node.addAttribute("TYPE", target.getTYPE().value());
		}
		if(target.getShow() != null) {
			node.addAttribute("SHOW", target.getShow().value());
		}
		if(target.getActuate() != null) {
			node.addAttribute("ACTUATE", target.getActuate().value());
		}
		
		
		JAXBElement<? extends AbstractGeometryType> jGeometry = null;
		if(target.getHref() != null) {
			String href = target.getHref().replaceAll("#", "");
			Object refObject = symbolMap.getObjectById(href);
			if(refObject instanceof JAXBElement) {
				jGeometry = (JAXBElement<? extends AbstractGeometryType>) symbolMap.getObjectById(href);
			} else if(refObject instanceof AbstractSurfaceType) {
				jGeometry = new ObjectFactory().createAbstractGeometry((AbstractGeometryType) refObject);
			}
		} else {
			jGeometry = target.getAbstractGeometry();
		}
		if(jGeometry != null) {
			AbstractGeometryType abstractGeom = jGeometry.getValue();
			node.addAttribute("GEOMETRYTYPE", abstractGeom.getClass().getSimpleName());
			if(abstractGeom instanceof MultiSurfaceType) {
				node = convertMultiSurfaceType((MultiSurfaceType) abstractGeom, node, symbolMap);
			}
		}
		return node;
	}
	
	@SuppressWarnings("unused")
	public static BindingNode convertDirectPositionType(DirectPositionType target, BindingNode node, XLinkSymbolMap symbolMap) {
	    //VALUE
		List<Double> value = target.getValue();
		node.addAttribute("VALUE", value);
		//SRSNAME
	    String srsName = target.getSrsName();
	    node.addAttribute("SRSNAME", srsName);
	    //SRSDIMENSION
	    BigInteger srsDimension = target.getSrsDimension();
	    if(srsDimension != null) {
			node.addAttribute("SRSDIMENSION", srsDimension.intValue());
		}
	    //TODO
	    List<String> axisLabels;
	    List<String> uomLabels;
		return node;
	}
	
	@SuppressWarnings("unused")
	public static BindingNode convertDirectPositionListType(DirectPositionListType target, BindingNode node, XLinkSymbolMap symbolMap) {
		//VALUE
		List<Double> value = target.getValue();
		node.addAttribute("VALUE", value);
		//Count
		BigInteger count = target.getCount();
		if(count != null) {
			node.addAttribute("COUNT", count.intValue());
		}
		//SRSNAME
	    String srsName = target.getSrsName();
	    node.addAttribute("SRSNAME", srsName);
	    //SRSDIMENSION
	    BigInteger srsDimension = target.getSrsDimension();
	    if(srsDimension != null) {
			node.addAttribute("SRSDIMENSION", srsDimension.intValue());
		}
	    //TODO
	    List<String> axisLabels;
	    List<String> uomLabels;
		return node;
	}
	
	public static BindingNode convertCoordinatesType(CoordinatesType target, BindingNode node, XLinkSymbolMap symbolMap) {
		node.addAttribute("COORDINATESVALUE", target.getValue());
		node.addAttribute("DECIMAL", target.getDecimal());
		node.addAttribute("CS", target.getCs());
		node.addAttribute("TS", target.getTs());
		return node;
	}
	
	
	public static BindingNode convertPointType(PointType target, BindingNode node, XLinkSymbolMap symbolMap) {
		/* =========== */
		/* Super Class */
		/* =========== */
		node = convertAbstractGeometricPrimitiveType(target, node, symbolMap);
		/* ========== */
	    /* Geometries */
	    /* ========== */
		DirectPositionType pos = target.getPos();
		if(pos != null) {
			BindingNode posNode = new BindingNode();
			posNode = convertDirectPositionType(pos, posNode, symbolMap);
			node.addAssociation("POS", posNode);		
		}
		
		CoordinatesType coordinates = target.getCoordinates();
		if(coordinates != null) {
			BindingNode coordinatesNode = new BindingNode();
			coordinatesNode = convertCoordinatesType(coordinates, coordinatesNode, symbolMap);
			node.addAssociation("COORDNATES", coordinatesNode);
		}
		return node;
	}
	
	//PointProperty
	public static BindingNode convertPointPropertyType(PointPropertyType target, BindingNode node, XLinkSymbolMap symbolMap) {
		/* ========== */
	    /* Attributes */
	    /* ========== */
		/* = XLink attributes ========== */
		node.addAttribute("REMOTESCHEMA", target.getRemoteSchema());
		node.addAttribute("HREF", target.getHref());
		node.addAttribute("ROLE", target.getRole());
		node.addAttribute("ARCROLE", target.getArcrole());
		node.addAttribute("TITLE", target.getTitle());
		if(target.getTYPE() != null) {
			node.addAttribute("TYPE", target.getTYPE().value());
		}
		if(target.getShow() != null) {
			node.addAttribute("SHOW", target.getShow().value());
		}
		if(target.getActuate() != null) {
			node.addAttribute("ACTUATE", target.getActuate().value());
		}
		
		//PointType Property
		PointType pointType = null;
		if(target.getHref() != null) {
			pointType = (PointType) symbolMap.getObjectById(target.getHref().replaceAll("#", ""));
		} else {
			pointType = target.getPoint();
		}
		
		if(pointType != null) {
			node = convertPointType(pointType, node, symbolMap);
		}
		return node;
	}
	
	//MultiPointProperty
	public static BindingNode convertMultiPointPropertyType(MultiPointPropertyType target,
			BindingNode node, XLinkSymbolMap symbolMap) {
		/* ========== */
	    /* Attributes */
	    /* ========== */
		/* = XLink attributes ========== */
		node.addAttribute("REMOTESCHEMA", target.getRemoteSchema());
		node.addAttribute("HREF", target.getHref());
		node.addAttribute("ROLE", target.getRole());
		node.addAttribute("ARCROLE", target.getArcrole());
		node.addAttribute("TITLE", target.getTitle());
		if(target.getTYPE() != null) {
			node.addAttribute("TYPE", target.getTYPE().value());
		}
		if(target.getShow() != null) {
			node.addAttribute("SHOW", target.getShow().value());
		}
		if(target.getActuate() != null) {
			node.addAttribute("ACTUATE", target.getActuate().value());
		}
		
		//MultiPointType Property
		MultiPointType multiPointType = null;
		if(target.getHref() != null) {
			multiPointType = (MultiPointType) symbolMap.getObjectById(target.getHref().replaceAll("#", ""));
			//System.out.println("Catched Xlink : " + target.getHref());
		} else {
			multiPointType = target.getMultiPoint();
		}
		
		if(multiPointType != null) {
			/* =========== */
			/* Super Class */
			/* =========== */
			node = convertAbstractGeometricAggregateType(multiPointType, node, symbolMap);
			/* ========== */
		    /* Geometries */
		    /* ========== */
			//PointMember
			List<PointPropertyType> pointMember = multiPointType.getPointMember();
			if(pointMember != null) {
				for(PointPropertyType p : pointMember) {
					BindingNode ppNode = new BindingNode();
					ppNode = convertPointPropertyType(p, ppNode, symbolMap);
					node.addCollection("POINTMEMBER", ppNode);
				}
			}
			
			//TODO
		    PointArrayPropertyType pointMembers = multiPointType.getPointMembers();
		    if(pointMembers != null) {
				throw new UnsupportedOperationException("PointArrayPropertyType is not supported");
			}
		}
		return node;
	}
	
	@SuppressWarnings("unchecked")
	public static BindingNode convertCurvePropertyType(CurvePropertyType target, BindingNode node, XLinkSymbolMap symbolMap) {
		/* ========== */
	    /* Attributes */
	    /* ========== */
		/* = XLink attributes ========== */
		node.addAttribute("REMOTESCHEMA", target.getRemoteSchema());
		node.addAttribute("HREF", target.getHref());
		node.addAttribute("ROLE", target.getRole());
		node.addAttribute("ARCROLE", target.getArcrole());
		node.addAttribute("TITLE", target.getTitle());
		if(target.getTYPE() != null) {
			node.addAttribute("TYPE", target.getTYPE().value());
		}
		if(target.getShow() != null) {
			node.addAttribute("SHOW", target.getShow().value());
		}
		if(target.getActuate() != null) {
			node.addAttribute("ACTUATE", target.getActuate().value());
		}
		
		//JAXBElement<? extends AbstractCurveType>
		JAXBElement<? extends AbstractCurveType> jCurve = null;
		if(target.getHref() != null) {
			jCurve = (JAXBElement<? extends AbstractCurveType>) symbolMap.getObjectById(target.getHref().replaceAll("#", ""));
			//System.out.println("Catched Xlink : " + target.getHref());
		} else {
			jCurve = target.getAbstractCurve();
		}
		
		//TODO
		if(jCurve != null) {
			AbstractCurveType aCurve = jCurve.getValue();
			if(aCurve != null) {
				/* =========== */
				/* Super Class */
				/* =========== */
				node = convertAbstractGeometricPrimitiveType(aCurve, node, symbolMap);
				/* ========== */
			    /* Geometries */
			    /* ========== */
				if(aCurve instanceof LineStringType) {
					LineStringType lineStringType = (LineStringType) aCurve; 
					
				    List<JAXBElement<?>> posOrPointPropertyOrPointRep = lineStringType.getPosOrPointPropertyOrPointRep();
				    for(JAXBElement<?> jObj : posOrPointPropertyOrPointRep) {
				    	
				    	Object o = jObj.getValue();
				    	
				    	if(o instanceof DirectPositionType) {
				    		DirectPositionType directPosition = (DirectPositionType) o;
				    		BindingNode directPositionNode = new BindingNode();
				    		directPositionNode = convertDirectPositionType(directPosition, directPositionNode, symbolMap);
				    		node.addCollection("DIRECTPOSITION", directPositionNode);
				    	} else if(o instanceof PointPropertyType) {
				    		PointPropertyType pointProperty = (PointPropertyType) o;
				    		BindingNode pointPropertyNode = new BindingNode();
				    		pointPropertyNode = convertPointPropertyType(pointProperty, pointPropertyNode, symbolMap);
				    		node.addCollection("POINTPROPERTYTYPE", pointPropertyNode);
				    	} 
				    }
				    
				    DirectPositionListType posList = lineStringType.getPosList();
				    if(posList != null) {
				    	BindingNode posListNode = new BindingNode();
				    	posListNode = convertDirectPositionListType(posList, posListNode, symbolMap);
				    	node.addAssociation("DIRECTPOSITIONLIST", posListNode);
				    }
				    
				    CoordinatesType coordinates = lineStringType.getCoordinates();
				    if(coordinates != null) {
				    	BindingNode coordinatesNode = new BindingNode();
						coordinatesNode = convertCoordinatesType(coordinates, coordinatesNode, symbolMap);
						node.addAssociation("COORDNATES", coordinatesNode);
					}
				}
				else if(aCurve instanceof CompositeCurveType) {
					CompositeCurveType compositeCurveType = (CompositeCurveType) aCurve;
					List<CurvePropertyType> curveMember = compositeCurveType.getCurveMember();
					if(curveMember != null) {
						for(CurvePropertyType c : curveMember) {
							BindingNode cpNode = new BindingNode();
							cpNode = convertCurvePropertyType(c, cpNode, symbolMap);
							node.addCollection("CURVEMEMBER", cpNode);
						}
					}
					
				}
				//NOTE : CityGML have only LineStringType and CompositeCurveType.
				/*
				else if(aCurve instanceof CurveType) {
					
				} else if(aCurve instanceof OrientableCurveType) {
				
				}
				*/
			}
		}
		
		return node;
	}
	
	//MultiCurveProperty
	public static BindingNode convertMultiCurvePropertyType(MultiCurvePropertyType target, BindingNode node, XLinkSymbolMap symbolMap) {
		/* ========== */
	    /* Attributes */
	    /* ========== */
		/* = XLink attributes ========== */
		node.addAttribute("REMOTESCHEMA", target.getRemoteSchema());
		node.addAttribute("HREF", target.getHref());
		node.addAttribute("ROLE", target.getRole());
		node.addAttribute("ARCROLE", target.getArcrole());
		node.addAttribute("TITLE", target.getTitle());
		if(target.getTYPE() != null) {
			node.addAttribute("TYPE", target.getTYPE().value());
		}
		if(target.getShow() != null) {
			node.addAttribute("SHOW", target.getShow().value());
		}
		if(target.getActuate() != null) {
			node.addAttribute("ACTUATE", target.getActuate().value());
		}
	
		//MultiCurveType
		MultiCurveType multiCurveType = null;
		if(target.getHref() != null) {
			multiCurveType = (MultiCurveType) symbolMap.getObjectById(target.getHref().replaceAll("#", ""));
			//System.out.println("Catched Xlink : " + target.getHref());
		} else {
			multiCurveType = target.getMultiCurve();
		}
		
		if(multiCurveType != null) {
			/* =========== */
			/* Super Class */
			/* =========== */
			node = convertAbstractGeometricAggregateType(multiCurveType, node, symbolMap);
			/* ========== */
		    /* Geometries */
		    /* ========== */
			//CurveMember
			List<CurvePropertyType> curveMember = multiCurveType.getCurveMember();
			if(curveMember != null) {
				for(CurvePropertyType c : curveMember) {
					BindingNode cpNode = new BindingNode();
					cpNode = convertCurvePropertyType(c, cpNode, symbolMap);
					node.addCollection("CURVEMEMBER", cpNode);
				}
			}
			
			//TODO
			CurveArrayPropertyType curveMembers = multiCurveType.getCurveMembers();
			if(curveMembers != null) {
				throw new UnsupportedOperationException("CurveArrayPropertyType is not supported");
			}
		}
		return node;
	}
	
	//AbstractRingProperty
	public static BindingNode convertAbstractRingPropertyType(AbstractRingPropertyType target, BindingNode node, XLinkSymbolMap symbolMap) {
		
		JAXBElement<? extends AbstractRingType> jRing = target.getAbstractRing();
		if(jRing != null) {
			AbstractRingType aRing = jRing.getValue();
			//node = convertAbstractGeometryType(aRing, node, symbolMap);
			
			if(aRing instanceof RingType) {
				RingType ringType = (RingType) aRing;
				List<CurvePropertyType> curveMember = ringType.getCurveMember();
				if(curveMember != null) {
					for(CurvePropertyType c : curveMember) {
						BindingNode cpNode = new BindingNode();
						cpNode = convertCurvePropertyType(c, cpNode, symbolMap);
						node.addCollection("CURVEMEMBER", cpNode);
					}
				}
			} else if(aRing instanceof LinearRingType) {
				LinearRingType linearRingType = (LinearRingType) jRing.getValue();
				List<JAXBElement<?>> posOrPointPropertyOrPointRep = linearRingType.getPosOrPointPropertyOrPointRep();
			    for(JAXBElement<?> jObj : posOrPointPropertyOrPointRep) {
			    	
			    	Object o = jObj.getValue();
			    	
			    	if(o instanceof DirectPositionType) {
			    		DirectPositionType directPosition = (DirectPositionType) o;
			    		BindingNode directPositionNode = new BindingNode();
			    		directPositionNode = convertDirectPositionType(directPosition, directPositionNode, symbolMap);
			    		node.addCollection("DIRECTPOSITION", directPositionNode);
			    	} else if(o instanceof PointPropertyType) {
			    		PointPropertyType pointProperty = (PointPropertyType) o;
			    		BindingNode pointPropertyNode = new BindingNode();
			    		pointPropertyNode = convertPointPropertyType(pointProperty, pointPropertyNode, symbolMap);
			    		node.addCollection("POINTPROPERTY", pointPropertyNode);
			    	}
			    }
			    
			    DirectPositionListType posList = linearRingType.getPosList();
			    if(posList != null) {
			    	BindingNode posListNode = new BindingNode();
			    	posListNode = convertDirectPositionListType(posList, posListNode, symbolMap);
			    	node.addAssociation("DIRECTPOSITIONLIST", posListNode);
			    }
			    
			    CoordinatesType coordinates = linearRingType.getCoordinates();
			    if(coordinates != null) {
			    	BindingNode coordinatesNode = new BindingNode();
					coordinatesNode = convertCoordinatesType(coordinates, coordinatesNode, symbolMap);
					node.addAssociation("COORDNATES", coordinatesNode);
				}
			}
		}
		
		
		return node;
	}
	
	//SurfaceProperty
	@SuppressWarnings("unchecked")
	public static BindingNode convertSurfacePropertyType(SurfacePropertyType target, BindingNode node, XLinkSymbolMap symbolMap) {
		/* ========== */
	    /* Attributes */
	    /* ========== */
		/* = XLink attributes ========== */
		node.addAttribute("REMOTESCHEMA", target.getRemoteSchema());
		node.addAttribute("HREF", target.getHref());
		node.addAttribute("ROLE", target.getRole());
		node.addAttribute("ARCROLE", target.getArcrole());
		node.addAttribute("TITLE", target.getTitle());
		if(target.getTYPE() != null) {
			node.addAttribute("TYPE", target.getTYPE().value());
		}
		if(target.getShow() != null) {
			node.addAttribute("SHOW", target.getShow().value());
		}
		if(target.getActuate() != null) {
			node.addAttribute("ACTUATE", target.getActuate().value());
		}
		/* ========================= */
	    /* AbstractSurfaceType Type  */
	    /* ========================= */
		
		//JAXBElement<? extends AbstractSurfaceType>
		JAXBElement<? extends AbstractSurfaceType> jSurface = null;
		if(target.getHref() != null) {
			String href = target.getHref().replaceAll("#", "");
			Object refObject = symbolMap.getObjectById(href);
			if(refObject instanceof JAXBElement) {
				jSurface = (JAXBElement<? extends AbstractSurfaceType>) symbolMap.getObjectById(href);
			} else if(refObject instanceof AbstractSurfaceType) {
				jSurface = new ObjectFactory().createAbstractSurface((AbstractSurfaceType) refObject);
			}
			//System.out.println("Catched Xlink : " + target.getHref());
		} else {
			jSurface = target.getAbstractSurface();
		}
		
		if(jSurface != null) {
			AbstractSurfaceType aSurface = jSurface.getValue();
			if(aSurface != null) {
				node = convertAbstractSurfaceType(aSurface, node, symbolMap);
			}
		}
		return node;
	}
	
	public static BindingNode convertAbstractSurfaceType(AbstractSurfaceType target, BindingNode node, XLinkSymbolMap symbolMap) {
		/* =========== */
		/* Super Class */
		/* =========== */
		node = convertAbstractGeometricPrimitiveType(target, node, symbolMap);
		if(target instanceof PolygonType) {
			PolygonType polygon = (PolygonType) target;
			BindingNode polygonNode = new BindingNode();
			polygonNode = convertPolygonType(polygon, polygonNode, symbolMap);
			node.addAssociation("POLYGONTYPE", polygonNode);
		} else if(target instanceof OrientableSurfaceType) {
			OrientableSurfaceType orientableSurface = (OrientableSurfaceType) target;
			BindingNode orientableSurfaceNode = new BindingNode();
			orientableSurfaceNode = convertOrientableSurfaceType(orientableSurface, orientableSurfaceNode, symbolMap);
			node.addAssociation("ORIENTABLESURFACETYPE", orientableSurfaceNode);
		} else if(target instanceof SurfaceType) {
			BindingNode surfaceNode = new BindingNode();
			//TODO
			node.addAssociation("SURFACETYPE", surfaceNode);
			throw new UnsupportedOperationException();
		} else if(target instanceof CompositeSurfaceType) {
			CompositeSurfaceType compositeSurface = (CompositeSurfaceType) target;
			BindingNode compositeSurfaceNode = new BindingNode();
			compositeSurfaceNode = convertCompositeSurfaceType(compositeSurface, compositeSurfaceNode, symbolMap);
			node.addAssociation("COMPOSITESURFACETYPE", compositeSurfaceNode);
		}
		return node;
	}
	
	//PolygonType
	public static BindingNode convertPolygonType(PolygonType target, BindingNode node, XLinkSymbolMap symbolMap) {
	    AbstractRingPropertyType exterior = target.getExterior();
	    if(exterior != null) {
	    	BindingNode extNode = new BindingNode();
	    	extNode = convertAbstractRingPropertyType(exterior, extNode, symbolMap);
	    	node.addAssociation("EXTERIOR", extNode);
	    }
	    List<AbstractRingPropertyType> interiorList = target.getInterior();
	    for(AbstractRingPropertyType interior : interiorList) {
	    	BindingNode intNode = new BindingNode();
	    	intNode = convertAbstractRingPropertyType(interior, intNode, symbolMap);
	    	node.addCollection("INTERIOR", intNode);
	    }
	    return node;
	}
	
	//OrientableSurfaceType
	public static BindingNode convertOrientableSurfaceType(OrientableSurfaceType target, BindingNode node, XLinkSymbolMap symbolMap) {
		SurfacePropertyType baseSurface = target.getBaseSurface();
		if(baseSurface != null) {
	    	BindingNode baseSurfaceNode = new BindingNode();
	    	baseSurfaceNode = convertSurfacePropertyType(baseSurface, baseSurfaceNode, symbolMap);
	    	node.addAssociation("BASESURFACE", baseSurfaceNode);
		}
		
		SignType orientation = target.getOrientation();
		if(orientation != null) {
			node.addAttribute("ORIENTATION", orientation.value());
		}
		return node;
	}
	
	//CompositeSurfaceType
	public static BindingNode convertCompositeSurfaceType(CompositeSurfaceType target, BindingNode node, XLinkSymbolMap symbolMap) {
		List<SurfacePropertyType> surfaceMember = target.getSurfaceMember();
		for(SurfacePropertyType surface : surfaceMember) {
			BindingNode surfaceNode = new BindingNode();
			surfaceNode = convertSurfacePropertyType(surface, surfaceNode, symbolMap);
			node.addCollection("SURFACEMEMBER", surfaceNode);
		}
		return node;
	}
	
	public static BindingNode convertShellType(ShellType target, BindingNode node, XLinkSymbolMap symbolMap) {
		List<SurfacePropertyType> surfaceMember = target.getSurfaceMember();
		for(SurfacePropertyType surface : surfaceMember) {
			BindingNode surfaceNode = new BindingNode();
			surfaceNode = convertSurfacePropertyType(surface, surfaceNode, symbolMap);
			node.addCollection("SURFACEMEMBER", surfaceNode);
		}
		return node;
	}
	
	public static BindingNode convertSolidType(SolidType target, BindingNode node, XLinkSymbolMap symbolMap) throws Exception {		
		ShellPropertyType exterior = target.getExterior();
		if(exterior != null) {
			node.addAssociation("EXTERIOR", convertShellPropertyType(exterior, new BindingNode(), symbolMap));
		}
		
		List<ShellPropertyType> interior = target.getInterior();
		for(ShellPropertyType sp : interior) {
	    	BindingNode spNode = convertShellPropertyType(sp, new BindingNode(), symbolMap);
	    	node.addCollection("INTERIOR", spNode);
	    }
		
		return node;
	}
	
	public static BindingNode convertMultiSurfaceType(MultiSurfaceType target, BindingNode node,  XLinkSymbolMap symbolMap) {
		/* =========== */
		/* Super Class */
		/* =========== */
		node = convertAbstractGeometricAggregateType(target, node, symbolMap);
		
		//SurfaceMember
		List<SurfacePropertyType> surfaceMember = target.getSurfaceMember();
		if(surfaceMember != null) {
			for(SurfacePropertyType s : surfaceMember) {
				BindingNode spNode = new BindingNode();
				spNode = convertSurfacePropertyType(s, spNode, symbolMap);
				node.addCollection("SURFACEMEMBER", spNode);
			}
		}
		
		//SurfaceArrayPropertyType
		SurfaceArrayPropertyType sufaceArrayProperty = target.getSurfaceMembers();
		if(sufaceArrayProperty != null) {
			List<JAXBElement<? extends AbstractSurfaceType>> surfaces = sufaceArrayProperty.getAbstractSurface();
			for(JAXBElement<? extends AbstractSurfaceType> jSurface : surfaces) {
				AbstractSurfaceType aSurface = jSurface.getValue();
				BindingNode sNode = new BindingNode();
				sNode = convertAbstractSurfaceType(aSurface, sNode, symbolMap);
				node.addCollection("SURFACEMEMBER", sNode);
			}
		}
		
		return node;
	}
	
	//MultiSurfaceProperty
	public static BindingNode convertMultiSurfacePropertyType(MultiSurfacePropertyType target, BindingNode node, XLinkSymbolMap symbolMap) {
		/* ========== */
	    /* Attributes */
	    /* ========== */
		/* = XLink attributes ========== */
		node.addAttribute("REMOTESCHEMA", target.getRemoteSchema());
		node.addAttribute("HREF", target.getHref());
		node.addAttribute("ROLE", target.getRole());
		node.addAttribute("ARCROLE", target.getArcrole());
		node.addAttribute("TITLE", target.getTitle());
		if(target.getTYPE() != null) {
			node.addAttribute("TYPE", target.getTYPE().value());
		}
		if(target.getShow() != null) {
			node.addAttribute("SHOW", target.getShow().value());
		}
		if(target.getActuate() != null) {
			node.addAttribute("ACTUATE", target.getActuate().value());
		}
		/* ====================== */
	    /* MultiSurfaceType Type  */
	    /* ====================== */
		MultiSurfaceType multiSurfaceType = target.getMultiSurface();
		if(multiSurfaceType != null) {
			/* =========== */
			/* Super Class */
			/* =========== */
			node = convertAbstractGeometricAggregateType(multiSurfaceType, node, symbolMap);
			
			//SurfaceMember
			List<SurfacePropertyType> surfaceMember = multiSurfaceType.getSurfaceMember();
			if(surfaceMember != null) {
				for(SurfacePropertyType s : surfaceMember) {
					BindingNode spNode = new BindingNode();
					spNode = convertSurfacePropertyType(s, spNode, symbolMap);
					node.addCollection("SURFACEMEMBER", spNode);
				}
			}
			
			//SurfaceArrayPropertyType
			SurfaceArrayPropertyType sufaceArrayProperty = multiSurfaceType.getSurfaceMembers();
			if(sufaceArrayProperty != null) {
				List<JAXBElement<? extends AbstractSurfaceType>> surfaces = sufaceArrayProperty.getAbstractSurface();
				for(JAXBElement<? extends AbstractSurfaceType> jSurface : surfaces) {
					AbstractSurfaceType aSurface = jSurface.getValue();
					BindingNode sNode = new BindingNode();
					sNode = convertAbstractSurfaceType(aSurface, sNode, symbolMap);
					node.addCollection("SURFACEMEMBER", sNode);
				}
			}
			
			
		}
		return node;
	}
	
	//SolidProperty
	public static BindingNode convertSolidPropertyType(SolidPropertyType target, BindingNode node, XLinkSymbolMap symbolMap) throws Exception {
		/* ========== */
	    /* Attributes */
	    /* ========== */
		/* = XLink attributes ========== */
		node.addAttribute("REMOTESCHEMA", target.getRemoteSchema());
		node.addAttribute("HREF", target.getHref());
		node.addAttribute("ROLE", target.getRole());
		node.addAttribute("ARCROLE", target.getArcrole());
		node.addAttribute("TITLE", target.getTitle());
		if(target.getTYPE() != null) {
			node.addAttribute("TYPE", target.getTYPE().value());
		}
		if(target.getShow() != null) {
			node.addAttribute("SHOW", target.getShow().value());
		}
		if(target.getActuate() != null) {
			node.addAttribute("ACTUATE", target.getActuate().value());
		}
		/* ======================= */
	    /* AbstractSolidType Type  */
	    /* ======================= */
		JAXBElement<? extends AbstractSolidType> jSolid = target.getAbstractSolid();
		
		if(jSolid != null) {
			AbstractSolidType aSolid = jSolid.getValue();
			/* =========== */
			/* Super Class */
			/* =========== */
			node = convertAbstractGeometricPrimitiveType(aSolid, node, symbolMap);
			/* ====================== */
		    /* AbstractSolidType Type  */
		    /* ====================== */
			node.addAttribute("GEOMETRYTYPE", target.getClass().getSimpleName());
			if(aSolid != null) {
				/* =============== */
			    /* SolidType Type  */
			    /* =============== */
				if(aSolid instanceof SolidType) {
					SolidType solid = (SolidType) aSolid;
					node = convertSolidType(solid, node, symbolMap);
				/* ======================== */
			    /* CompositeSolidType Type  */
			    /* ======================== */
				} else if(aSolid instanceof CompositeSolidType) {
					CompositeSolidType compositeSolid = (CompositeSolidType) aSolid;
					List<SolidPropertyType> solidMember = compositeSolid.getSolidMember();
					for(SolidPropertyType sp : solidMember) {
				    	BindingNode spNode = convertSolidPropertyType(sp, new BindingNode(), symbolMap);
				    	node.addCollection("SOLIDMEMBER", spNode);
				    }
				}
			}
		}
		return node;
	}
	
	private static BindingNode convertShellPropertyType(ShellPropertyType target, BindingNode node, XLinkSymbolMap symbolMap) {
		/* =================== */
	    /* SurfacePropertyType */
		/* =================== */
		ShellType shell = (ShellType) target.getShell();
		BindingNode shellNode = new BindingNode();
		shellNode = convertShellType(shell, shellNode, symbolMap);
		node.addAssociation("SHELLTYPE", shellNode);
				
		return node;
	}
	
	//BoundingShapeType, EnvelopeType
	public static BindingNode convertBoundingShapeType(BoundingShapeType target, BindingNode node, XLinkSymbolMap symbolMap) {
		/* ================== */
	    /* EnvelopeType Type  */
	    /* ================== */
		JAXBElement<? extends EnvelopeType> jEnvelope = target.getEnvelope();
		if( jEnvelope != null) {
			EnvelopeType env = jEnvelope.getValue();
			if(env != null) {
				/* ========== */
			    /* Attributes */
			    /* ========== */
				String srsName = env.getSrsName();
				node.addAttribute("SRSNAME", srsName);
				if(env.getSrsDimension() != null) {
					node.addAttribute("SRSDIMENSION", env.getSrsDimension().intValue());
				}
				/* ========== */
			    /* Geometries */
			    /* ========== */
				DirectPositionType lowerCorner = env.getLowerCorner();
				if(lowerCorner != null) {
					BindingNode posNode = new BindingNode();
					posNode = convertDirectPositionType(lowerCorner, posNode, symbolMap);
					node.addAssociation("LOWERCORNER", posNode);		
				}
				DirectPositionType upperCorner = env.getUpperCorner();
				if(upperCorner != null) {
					BindingNode posNode = new BindingNode();
					posNode = convertDirectPositionType(upperCorner, posNode, symbolMap);
					node.addAssociation("UPPERCORNER", posNode);		
				}
			}
		}
		return node;
	}
}
