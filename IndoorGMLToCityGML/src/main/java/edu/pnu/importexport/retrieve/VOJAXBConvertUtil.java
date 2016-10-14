package edu.pnu.importexport.retrieve;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.opengis.gml.v_3_2_1.CodeType;
import net.opengis.gml.v_3_2_1.CurvePropertyType;
import net.opengis.gml.v_3_2_1.FeaturePropertyType;
import net.opengis.gml.v_3_2_1.PointPropertyType;
import net.opengis.gml.v_3_2_1.SolidPropertyType;
import net.opengis.gml.v_3_2_1.SurfacePropertyType;
import net.opengis.indoorgml.core.v_1_0.CellSpaceBoundaryPropertyType;
import net.opengis.indoorgml.core.v_1_0.CellSpaceBoundaryType;
import net.opengis.indoorgml.core.v_1_0.CellSpacePropertyType;
import net.opengis.indoorgml.core.v_1_0.CellSpaceType;
import net.opengis.indoorgml.core.v_1_0.EdgesType;
import net.opengis.indoorgml.core.v_1_0.ExternalObjectReferenceType;
import net.opengis.indoorgml.core.v_1_0.ExternalReferenceType;
import net.opengis.indoorgml.core.v_1_0.IndoorFeaturesType;
import net.opengis.indoorgml.core.v_1_0.InterEdgesType;
import net.opengis.indoorgml.core.v_1_0.InterLayerConnectionMemberType;
import net.opengis.indoorgml.core.v_1_0.InterLayerConnectionType;
import net.opengis.indoorgml.core.v_1_0.MultiLayeredGraphType;
import net.opengis.indoorgml.core.v_1_0.NodesType;
import net.opengis.indoorgml.core.v_1_0.PrimalSpaceFeaturesPropertyType;
import net.opengis.indoorgml.core.v_1_0.PrimalSpaceFeaturesType;
import net.opengis.indoorgml.core.v_1_0.SpaceLayerMemberType;
import net.opengis.indoorgml.core.v_1_0.SpaceLayerPropertyType;
import net.opengis.indoorgml.core.v_1_0.SpaceLayerType;
import net.opengis.indoorgml.core.v_1_0.SpaceLayersType;
import net.opengis.indoorgml.core.v_1_0.StateMemberType;
import net.opengis.indoorgml.core.v_1_0.StatePropertyType;
import net.opengis.indoorgml.core.v_1_0.StateType;
import net.opengis.indoorgml.core.v_1_0.TransitionMemberType;
import net.opengis.indoorgml.core.v_1_0.TransitionPropertyType;
import net.opengis.indoorgml.core.v_1_0.TransitionType;
import net.opengis.indoorgml.navigation.v_1_0.PathType;
import net.opengis.indoorgml.navigation.v_1_0.RouteNodeMemberType;
import net.opengis.indoorgml.navigation.v_1_0.RouteNodePropertyType;
import net.opengis.indoorgml.navigation.v_1_0.RouteNodeType;
import net.opengis.indoorgml.navigation.v_1_0.RouteNodesType;
import net.opengis.indoorgml.navigation.v_1_0.RouteSegmentMemberType;
import net.opengis.indoorgml.navigation.v_1_0.RouteSegmentType;
import net.opengis.indoorgml.navigation.v_1_0.RouteType;
import net.opengis.indoorgml.v_1_0.vo.core.CellSpace;
import net.opengis.indoorgml.v_1_0.vo.core.CellSpaceBoundary;
import net.opengis.indoorgml.v_1_0.vo.core.Edges;
import net.opengis.indoorgml.v_1_0.vo.core.ExternalReference;
import net.opengis.indoorgml.v_1_0.vo.core.IndoorFeatures;
import net.opengis.indoorgml.v_1_0.vo.core.InterEdges;
import net.opengis.indoorgml.v_1_0.vo.core.InterLayerConnection;
import net.opengis.indoorgml.v_1_0.vo.core.MultiLayeredGraph;
import net.opengis.indoorgml.v_1_0.vo.core.Nodes;
import net.opengis.indoorgml.v_1_0.vo.core.PrimalSpaceFeatures;
import net.opengis.indoorgml.v_1_0.vo.core.SpaceLayer;
import net.opengis.indoorgml.v_1_0.vo.core.SpaceLayers;
import net.opengis.indoorgml.v_1_0.vo.core.State;
import net.opengis.indoorgml.v_1_0.vo.core.Transition;
import net.opengis.indoorgml.v_1_0.vo.navigation.Path;
import net.opengis.indoorgml.v_1_0.vo.navigation.Route;
import net.opengis.indoorgml.v_1_0.vo.navigation.RouteNode;
import net.opengis.indoorgml.v_1_0.vo.navigation.RouteNodes;
import net.opengis.indoorgml.v_1_0.vo.navigation.RouteSegment;

public class VOJAXBConvertUtil {
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(VOJAXBConvertUtil.class);
	
	private static final net.opengis.indoorgml.core.v_1_0.ObjectFactory coreOf = new net.opengis.indoorgml.core.v_1_0.ObjectFactory();
	private static final net.opengis.indoorgml.navigation.v_1_0.ObjectFactory naviOf = new net.opengis.indoorgml.navigation.v_1_0.ObjectFactory();
	private static final net.opengis.gml.v_3_2_1.ObjectFactory gmlOf = new net.opengis.gml.v_3_2_1.ObjectFactory();
	
	public static IndoorFeaturesType createIndoorFeaturesType(IndoorFeaturesType target, IndoorFeatures vo){
		if(target == null){
			target = coreOf.createIndoorFeaturesType();
		}
		
		target = (IndoorFeaturesType) GMLJAXBConvertUtil.createAbstractFeatureType(target, vo);
		
		// PrimalSpaceFeatures
		if(vo.getPrimalSpaceFeatures() != null){
			PrimalSpaceFeaturesPropertyType primalSpaceFeaturesPropertyType = coreOf.createPrimalSpaceFeaturesPropertyType();
			primalSpaceFeaturesPropertyType.setPrimalSpaceFeatures(createPrimalSpaceFeaturesType(coreOf.createPrimalSpaceFeaturesType(), vo.getPrimalSpaceFeatures()));
			target.setPrimalSpaceFeatures(primalSpaceFeaturesPropertyType);
				
		}
		// MultiLayeredGraph
		if(vo.getMultiLayeredGraph() != null){
			MultiLayeredGraphType multiLayeredGraphType = coreOf.createMultiLayeredGraphType();
			multiLayeredGraphType = createMultiLayeredGraphType(multiLayeredGraphType, vo.getMultiLayeredGraph());
			target.setMultiLayeredGraph(multiLayeredGraphType);
		}
		
		return target;
	}

	private static PrimalSpaceFeaturesType createPrimalSpaceFeaturesType(
			PrimalSpaceFeaturesType target,
			PrimalSpaceFeatures vo) {
		if(target != null){
			target = (PrimalSpaceFeaturesType) GMLJAXBConvertUtil.createAbstractFeatureType(target, vo);
			
			// CellSpace
			if(vo.getCellSpace() != null){
				List<FeaturePropertyType> cellSpaceMember = target.getCellSpaceMember();
				for(CellSpace cs : vo.getCellSpace()){
					FeaturePropertyType featurePropertyType = gmlOf.createFeaturePropertyType();
					JAXBElement<CellSpaceType> cellSpaceType = coreOf.createCellSpace(createCellSpaceType(coreOf.createCellSpaceType(), cs));
					featurePropertyType.setAbstractFeature(cellSpaceType);
					cellSpaceMember.add(featurePropertyType);
				}
				target.setCellSpaceMember(cellSpaceMember);	
			}
			// CellSpaceBoundary
			if(vo.getCellSpaceBoundary() != null){
				List<FeaturePropertyType> cellSpaceBoundaryMember = target.getCellSpaceBoundaryMember();
				for(CellSpaceBoundary csb: vo.getCellSpaceBoundary()){
					FeaturePropertyType featurePropertyType = gmlOf.createFeaturePropertyType();
					JAXBElement<CellSpaceBoundaryType> cellSpaceBoundaryType = coreOf.createCellSpaceBoundary(createCellSpaceBoundaryType(coreOf.createCellSpaceBoundaryType(), csb));
					featurePropertyType.setAbstractFeature(cellSpaceBoundaryType);
					cellSpaceBoundaryMember.add(featurePropertyType);
				}
				target.setCellSpaceBoundaryMember(cellSpaceBoundaryMember);	
			}
		}
		return target;
	}

	private static CellSpaceType createCellSpaceType(CellSpaceType target, CellSpace vo) {
		if(target != null){
			target = (CellSpaceType) GMLJAXBConvertUtil.createAbstractFeatureType(target, vo);
			
			// Geometry2D
			if(vo.getGeometry2D() != null){
				SurfacePropertyType surfacePropertyType = GMLJAXBConvertUtil.createSurfacePropertyType(vo.getGeometry2D());
				target.setGeometry2D(surfacePropertyType);
			}
			// Geometry3D
			if(vo.getGeometry3D() != null){
				SolidPropertyType solidPropertyType = GMLJAXBConvertUtil.createSolidPropertyType(vo.getGeometry3D());
				target.setGeometry3D(solidPropertyType);
			}
			// Duality	
			if(vo.getDuality() != null){
				StatePropertyType statePropertyType = coreOf.createStatePropertyType();
				statePropertyType.setHref("#" + vo.getHrefDuality());
				target.setDuality(statePropertyType);
			}
			// PartialBoundedBy
			if(vo.getPartialBoundedBy() != null){
				List<CellSpaceBoundaryPropertyType> partialBoundedBy = target.getPartialboundedBy();
				for(String cellSpaceBoundaryGMLID : vo.getHrefPartialBoundedBy()){
					CellSpaceBoundaryPropertyType cellSpaceBoundaryPropertyType = coreOf.createCellSpaceBoundaryPropertyType();
					cellSpaceBoundaryPropertyType.setHref("#" + cellSpaceBoundaryGMLID);
					partialBoundedBy.add(cellSpaceBoundaryPropertyType);
				}
				target.setPartialboundedBy(partialBoundedBy);
			}
			// ExternalReference
			if(vo.getExternalReference() != null){
				List<ExternalReferenceType> externalReference = target.getExternalReference();
				for(ExternalReference externalReferenceVO : vo.getExternalReference()){
					ExternalReferenceType externalReferenceType = coreOf.createExternalReferenceType();
					ExternalObjectReferenceType externalObjectReferenceType = coreOf.createExternalObjectReferenceType();
					externalReferenceType.setInformationSystem(externalReferenceVO.getInformationSystem());
					externalObjectReferenceType.setName(externalReferenceVO.getName());
					externalObjectReferenceType.setUri(externalReferenceVO.getUri());
					externalReferenceType.setExternalObject(externalObjectReferenceType);
					externalReference.add(externalReferenceType);
				}
				target.setExternalReference(externalReference);
			}
		}
		return target;
	}

	private static CellSpaceBoundaryType createCellSpaceBoundaryType(CellSpaceBoundaryType target, CellSpaceBoundary vo) {
		if(target != null){
			target = (CellSpaceBoundaryType) GMLJAXBConvertUtil.createAbstractFeatureType(target, vo);
			
			// Geometry2D
			if(vo.getGeometry2D() != null){
				CurvePropertyType curvePropertyType = GMLJAXBConvertUtil.createCurvePorpertyType(vo.getGeometry2D());
				target.setGeometry2D(curvePropertyType);
			}
			// Geometry3D
			if(vo.getGeometry3D() != null){
				SurfacePropertyType surfacePropertyType = GMLJAXBConvertUtil.createSurfacePropertyType(vo.getGeometry3D());
				target.setGeometry3D(surfacePropertyType);
			}
			// Duality	
			if(vo.getDuality() != null){
				TransitionPropertyType transitionPropertyType = coreOf.createTransitionPropertyType();
				transitionPropertyType.setHref("#" + vo.getDuality());
				target.setDuality(transitionPropertyType);
			}
			// ExternalReference
			if(vo.getExternalReference() != null){
				List<ExternalReferenceType> externalReference = target.getExternalReference();
				for(ExternalReference externalReferenceVO : vo.getExternalReference()){
					ExternalReferenceType externalReferenceType = coreOf.createExternalReferenceType();
					ExternalObjectReferenceType externalObjectReferenceType = coreOf.createExternalObjectReferenceType();
					externalReferenceType.setInformationSystem(externalReferenceVO.getInformationSystem());
					externalObjectReferenceType.setName(externalReferenceVO.getName());
					externalObjectReferenceType.setUri(externalReferenceVO.getUri());
					externalReferenceType.setExternalObject(externalObjectReferenceType);
					externalReference.add(externalReferenceType);
				}
				target.setExternalReference(externalReference);
			}
		}
		return target;
	}
	
	private static MultiLayeredGraphType createMultiLayeredGraphType(MultiLayeredGraphType target,
			MultiLayeredGraph vo) {
		if(target != null){
			target = (MultiLayeredGraphType) GMLJAXBConvertUtil.createAbstractFeatureType(target, vo);
			
			// InterEdges
			if(vo.getInterEdges() != null){
				List<InterEdgesType> interEdges = target.getInterEdges();
				for(InterEdges ie : vo.getInterEdges()){
					InterEdgesType interEdgesType = createInterEdgesType(coreOf.createInterEdgesType(), ie);
					interEdges.add(interEdgesType);
				}
				target.setInterEdges(interEdges);
					
			}
			// SpaceLayers
			if(vo.getSpaceLayers() != null){
				List<SpaceLayersType> spaceLayers = target.getSpaceLayers();
				for(SpaceLayers sl : vo.getSpaceLayers()){
					SpaceLayersType spaceLayersType = createSpaceLayersType(coreOf.createSpaceLayersType(), sl);
					spaceLayers.add(spaceLayersType);
				}
				target.setSpaceLayers(spaceLayers);	
			}
		}
		return target;
	}

	private static InterEdgesType createInterEdgesType(InterEdgesType target, InterEdges vo) {
		if(target != null){
			target = (InterEdgesType) GMLJAXBConvertUtil.createAbstractFeatureType(target, vo);

			// InterLayerConnection
			if(vo.getInterLayerConnectionMember() != null){
				List<InterLayerConnectionMemberType> interLayerConnection = target.getInterLayerConnectionMember();
				for(InterLayerConnection ilc : vo.getInterLayerConnectionMember()){
					InterLayerConnectionMemberType interLayerConnectionMemberType = coreOf.createInterLayerConnectionMemberType();
					InterLayerConnectionType interLayerConnectionType = createInterLayerConnectionType(coreOf.createInterLayerConnectionType(), ilc);
					interLayerConnectionMemberType.setInterLayerConnection(interLayerConnectionType);
					interLayerConnection.add(interLayerConnectionMemberType);
				}
				target.setInterLayerConnectionMember(interLayerConnection);	
			}
		}
		return target;
	}
	
	private static InterLayerConnectionType createInterLayerConnectionType(InterLayerConnectionType target, InterLayerConnection vo) {
		if(target != null){
			target = (InterLayerConnectionType) GMLJAXBConvertUtil.createAbstractFeatureType(target, vo);
			
			// TypeOfTopoExpression
			if(vo.getTypeOfTopoExpression() != null){
				target.setTypeOfTopoExpression(vo.getTypeOfTopoExpression());	
			}
			// Comment
			if(vo.getComment() != null){
				target.setComment(vo.getComment());
			}
			// InterConnects
			if(vo.getHrefInterConnects() != null){
				List<StatePropertyType> interConnects = target.getInterConnects();
				for(String interConnectsGMLID : vo.getHrefInterConnects()){
					StatePropertyType statePropertyType = coreOf.createStatePropertyType();
					statePropertyType.setHref("#" + interConnectsGMLID);
					interConnects.add(statePropertyType);
				}
				target.setInterConnects(interConnects);	
			}
			// ConnectedLayer
			if(vo.getHrefConnectedLayers() != null){
				List<SpaceLayerPropertyType> connectedLayer = target.getConnectedLayers();
				for(String connectedLayerGMLID : vo.getHrefConnectedLayers()){
					SpaceLayerPropertyType spaceLayerPropertyType = coreOf.createSpaceLayerPropertyType();
					spaceLayerPropertyType.setHref("#" + connectedLayerGMLID);
					connectedLayer.add(spaceLayerPropertyType);
				}
				target.setConnectedLayers(connectedLayer);	
			}
		}
		return target;
	}

	private static SpaceLayersType createSpaceLayersType(SpaceLayersType target, SpaceLayers vo) {
		if(target != null){
			target = (SpaceLayersType) GMLJAXBConvertUtil.createAbstractFeatureType(target, vo);
			
			// SpaceLayers
			if(vo.getSpaceLayerMember() != null){
				List<SpaceLayerMemberType> spaceLayers = target.getSpaceLayerMember();
				for(SpaceLayer sl : vo.getSpaceLayerMember()){
					SpaceLayerMemberType spaceLayerMemberType = coreOf.createSpaceLayerMemberType();
					SpaceLayerType spaceLayerType = createSpaceLayerType(coreOf.createSpaceLayerType(), sl);
					spaceLayerMemberType.setSpaceLayer(spaceLayerType);
					spaceLayers.add(spaceLayerMemberType);
				}
				target.setSpaceLayerMember(spaceLayers);	
			}
		}
		return target;
	}

	private static SpaceLayerType createSpaceLayerType(SpaceLayerType target, SpaceLayer vo) {
		if(target != null){
			target = (SpaceLayerType) GMLJAXBConvertUtil.createAbstractFeatureType(target, vo);
			
			/* SET ATTRIBUTES */
			// TODO SpaceLayerClassTypeType
			/*
			String clazz = vo.getClazz();
			String classCodeSpace = vo.getClassCodeSpace();
			List<CodeType> clazzType = GMLJAXBConvertUtil.createCodeType(clazz, classCodeSpace);
			SpaceLayerClassTypeType classType = 
			if(clazzType != null) target.set(clazzType);
			*/
			
			//TODO : consider multiple functions
			String func = vo.getFunction();
			String funcCodeSpace = vo.getFunctionCodeSpace();
			List<CodeType> funcType = GMLJAXBConvertUtil.createCodeType(target.getFunction(), func, funcCodeSpace);
			if(funcType != null) target.setFunction(funcType);
			
			//TODO : consider multiple usages
			String usage = vo.getUsage();
			String usageCodeSpace = vo.getUsageCodeSpace();
			List<CodeType> usageType = GMLJAXBConvertUtil.createCodeType(target.getUsage(), usage, usageCodeSpace);
			if(usageType != null) target.setUsage(usageType);
			if(vo.getCreationDate() != null) target.setCreationDate(createXMLGregorianCalendar(vo.getCreationDate()));
			if(vo.getTeminationDate() != null) target.setTerminationDate(createXMLGregorianCalendar(vo.getTeminationDate()));
			
			// Nodes
			if(vo.getNodes() != null){
				List<NodesType> nodes = target.getNodes();
				for(Nodes node : vo.getNodes()){
					NodesType nodesType = createNodesType(coreOf.createNodesType(), node);
					nodes.add(nodesType);
				}
				target.setNodes(nodes);
					
			}
			// Edges
			if(vo.getEdges() != null){
				List<EdgesType> edges = target.getEdges();
				for(Edges edge : vo.getEdges()){
					EdgesType edgesType = createEdgesType(coreOf.createEdgesType(), edge);
					edges.add(edgesType);
				}
				target.setEdges(edges);	
			}
		}
		return target;
	}

	private static NodesType createNodesType(NodesType target, Nodes vo) {
		if(target != null){
			target = (NodesType) GMLJAXBConvertUtil.createAbstractFeatureType(target, vo);
			
			// State
			if(vo.getStateMember() != null){
				List<StateMemberType> stateMember = target.getStateMember();
				for(State s : vo.getStateMember()){
					StateMemberType stateMemberType = coreOf.createStateMemberType();
					StateType state = createStateType(coreOf.createStateType(), s);
					stateMemberType.setState(state);
					stateMember.add(stateMemberType);
				}
				target.setStateMember(stateMember);	
			}
		}
		return target;
	}
	
	private static StateType createStateType(StateType target, State vo) {
		if(target != null){
			target = (StateType) GMLJAXBConvertUtil.createAbstractFeatureType(target, vo);
			
			// Geometry
			if(vo.getGeometry() != null){
				PointPropertyType geometry = GMLJAXBConvertUtil.creatPointPropertyType(vo.getGeometry());
				target.setGeometry(geometry);
			}
			// Duality	
			if(vo.getDuality() != null){
				CellSpacePropertyType cellSpacePropertyType = coreOf.createCellSpacePropertyType();
				cellSpacePropertyType.setHref("#" + vo.getHrefDuality());
				target.setDuality(cellSpacePropertyType );
			}
			// Connects
			if(vo.getHrefConnects() != null){
				List<TransitionPropertyType> connects = target.getConnects();
				for(String transitionGMLID : vo.getHrefConnects()){
					TransitionPropertyType transitionPropertyType = coreOf.createTransitionPropertyType();
					transitionPropertyType.setHref("#" + transitionGMLID);
					connects.add(transitionPropertyType);
				}
				target.setConnects(connects);	
			}
		}
		return target;
	}

	private static EdgesType createEdgesType(EdgesType target, Edges vo) {
		if(target != null){
			target = (EdgesType) GMLJAXBConvertUtil.createAbstractFeatureType(target, vo);
			
			// State
			if(vo.getTransitionMember() != null){
				List<TransitionMemberType> transitionMember = target.getTransitionMember();
				for(Transition t : vo.getTransitionMember()){
					TransitionMemberType transitionMemberType = coreOf.createTransitionMemberType();
					TransitionType transitionType = createTransitionType(coreOf.createTransitionType(), t);
					transitionMemberType.setTransition(transitionType);
					transitionMember.add(transitionMemberType);
				}
				target.setTransitionMember(transitionMember);	
			}
		}
		return target;
	}

	private static TransitionType createTransitionType(TransitionType target, Transition vo) {
		if(target != null){
			target = (TransitionType) GMLJAXBConvertUtil.createAbstractFeatureType(target, vo);
			
			// Weight
			if(vo.getWeight() != null){
				target.setWeight(vo.getWeight());	
			}
			// Geometry
			if(vo.getGeometry() != null){
				CurvePropertyType geometry = GMLJAXBConvertUtil.createCurvePorpertyType(vo.getGeometry());
				target.setGeometry(geometry);
			}
			// Duality	
			if(vo.getHrefDuality() != null){
				CellSpaceBoundaryPropertyType cellSpaceBoundaryPropertyType = coreOf.createCellSpaceBoundaryPropertyType();
				cellSpaceBoundaryPropertyType.setHref("#" + vo.getHrefDuality());
				target.setDuality(cellSpaceBoundaryPropertyType);
			}
			// Connects
			if(vo.getHrefConnects() != null){
				List<StatePropertyType> connects = target.getConnects();
				for(String stateGMLID : vo.getHrefConnects()){
					StatePropertyType statePropertyType = coreOf.createStatePropertyType();
					statePropertyType.setHref("#" + stateGMLID);
					connects.add(statePropertyType);
				}
				target.setConnects(connects);	
			}
		}
		return target;
	}

	public static XMLGregorianCalendar createXMLGregorianCalendar(Date date) {
		
		GregorianCalendar gregory = new GregorianCalendar();
		gregory.setTime(date);

		XMLGregorianCalendar calendar;
		try {
			calendar = DatatypeFactory.newInstance()
			        .newXMLGregorianCalendar(
			            gregory);
			calendar.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
			calendar.setTime(DatatypeConstants.FIELD_UNDEFINED, DatatypeConstants.FIELD_UNDEFINED, DatatypeConstants.FIELD_UNDEFINED);
			calendar.setDay(DatatypeConstants.FIELD_UNDEFINED);
			calendar.setMonth(DatatypeConstants.FIELD_UNDEFINED);
			return calendar;
		} catch (DatatypeConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static RouteType createRouteType(RouteType target, Route vo) {
		if(target == null){
			target = naviOf.createRouteType();
		}
		target = (RouteType) GMLJAXBConvertUtil.createAbstractFeatureType(target, vo);
		
		// StartRouteNode, EndRouteNode
		if(vo.getStartRouteNode() != null){
			RouteNodePropertyType routeNodePropertyType = naviOf.createRouteNodePropertyType();
			routeNodePropertyType.setRouteNode(createRouteNodeType(naviOf.createRouteNodeType(), vo.getStartRouteNode(), vo));
			target.setStartRouteNode(routeNodePropertyType);
		}
		if(vo.getEndRouteNode() != null){
			RouteNodePropertyType routeNodePropertyType = naviOf.createRouteNodePropertyType();
			routeNodePropertyType.setRouteNode(createRouteNodeType(naviOf.createRouteNodeType(), vo.getEndRouteNode(), vo));
			target.setEndRouteNode(routeNodePropertyType);
		}
		// RouteNodes
		if(vo.getRouteNodes() != null){
			RouteNodesType routeNodesType = naviOf.createRouteNodesType();
			routeNodesType = createRouteNodesType(routeNodesType, vo.getRouteNodes());
			target.setRouteNodes(routeNodesType);
		}
		// Path
		if(vo.getPath() != null){
			PathType pathType = naviOf.createPathType();
			pathType = createPathType(pathType, vo.getPath());
			target.setPath(pathType);
		}
		
		return target;
	}

	private static RouteNodeType createRouteNodeType(RouteNodeType target, RouteNode vo, Object parents) {
		if(target != null){
			target = (RouteNodeType) GMLJAXBConvertUtil.createAbstractFeatureType(target, vo);
			
			// ReferencedState
			if(vo.getReferencedState() != null){
				StatePropertyType statePropertyType = coreOf.createStatePropertyType();
				if(parents instanceof Route){
					statePropertyType.setHref("#" + vo.getReferencedState().getGmlID());
				}
				else if(parents instanceof RouteNodes){
					statePropertyType.setState(createStateType(coreOf.createStateType(), vo.getReferencedState()));
				}	
				target.setReferencedState(statePropertyType);
			}
			
			// Geometry
			if(vo.getGeometry() != null){
				PointPropertyType pointPropertyType = gmlOf.createPointPropertyType();
				pointPropertyType.setHref("#" + vo.getGeometry().getGmlId());
				target.setGeometry(pointPropertyType);
			}
		}
		return target;
	}

	private static RouteNodesType createRouteNodesType(RouteNodesType target, RouteNodes vo) {
		if(target != null){
			target = (RouteNodesType) GMLJAXBConvertUtil.createAbstractFeatureType(target, vo);
			
			// NodeMember
			if(vo.getNodeMember() != null){
				List<RouteNodeMemberType> nodeMember = target.getNodeMember();
				for(RouteNode routeNode : vo.getNodeMember()){
					RouteNodeMemberType routeNodeMemberType = naviOf.createRouteNodeMemberType();
					RouteNodeType routeNodeType = createRouteNodeType(naviOf.createRouteNodeType(), routeNode, vo);
					routeNodeMemberType.setRouteNode(routeNodeType);
					nodeMember.add(routeNodeMemberType);
				}
				target.setNodeMember(nodeMember);	
			}
		}
		return target;
	}
	
	private static PathType createPathType(PathType target, Path vo) {
		if(target != null){
			target = (PathType) GMLJAXBConvertUtil.createAbstractFeatureType(target, vo);
			
			// RouteMember
			if(vo.getRouteMember() != null){
				List<RouteSegmentMemberType> routeMember = target.getRouteMember();
				for(RouteSegment routeSegment : vo.getRouteMember()){
					RouteSegmentMemberType routeSegmentMemberType = naviOf.createRouteSegmentMemberType();
					RouteSegmentType routeSegmentType = createRouteSegmentType(naviOf.createRouteSegmentType(), routeSegment);
					routeSegmentMemberType.setRouteSegment(routeSegmentType );
					routeMember.add(routeSegmentMemberType);
				}
				target.setRouteMember(routeMember);	
			}
		}
		return target;
	}

	private static RouteSegmentType createRouteSegmentType(RouteSegmentType target, RouteSegment vo) {
		if(target != null){
			target = (RouteSegmentType) GMLJAXBConvertUtil.createAbstractFeatureType(target, vo);
			// Weight
			target.setWeight(vo.getWeight());
			// Connects
			if(vo.getConnects() != null){
				List<RouteNodePropertyType> connects = target.getConnects();
				for(String routeNodeGMLID : vo.getConnects()){
					RouteNodePropertyType routeNodePropertyType = naviOf.createRouteNodePropertyType();
					routeNodePropertyType.setHref("#" + routeNodeGMLID);
					connects.add(routeNodePropertyType);
				}	
			}
			// ReferencedTransition
			if(vo.getReferencedTransition() != null){
				TransitionPropertyType transitionPropertyType = coreOf.createTransitionPropertyType();
				transitionPropertyType.setTransition(createTransitionType(coreOf.createTransitionType(), vo.getReferencedTransition()));
				target.setReferencedTransition(transitionPropertyType);
			}
			
			// Geometry
			if(vo.getGeometry() != null){
				CurvePropertyType curvePropertyType = gmlOf.createCurvePropertyType();
				curvePropertyType.setHref("#" + vo.getGeometry().getGmlId());
				target.setGeometry(curvePropertyType);
			}
		}
		return target;
	}

}
