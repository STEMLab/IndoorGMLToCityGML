package edu.pnu.importexport.store;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBElement;
import javax.xml.datatype.XMLGregorianCalendar;

import edu.pnu.common.XLinkSymbolMap;
import edu.pnu.importexport.BindingNode;
import net.opengis.gml.v_3_2_1.CodeType;
import net.opengis.gml.v_3_2_1.CurvePropertyType;
import net.opengis.gml.v_3_2_1.FeaturePropertyType;
import net.opengis.gml.v_3_2_1.PointPropertyType;
import net.opengis.gml.v_3_2_1.SolidPropertyType;
import net.opengis.gml.v_3_2_1.SurfacePropertyType;
import net.opengis.indoorgml.core.v_1_0.*;
import net.opengis.indoorgml.navigation.v_1_0.AnchorBoundaryType;
import net.opengis.indoorgml.navigation.v_1_0.AnchorSpaceType;
import net.opengis.indoorgml.navigation.v_1_0.ConnectionBoundaryType;
import net.opengis.indoorgml.navigation.v_1_0.ConnectionSpaceType;
import net.opengis.indoorgml.navigation.v_1_0.GeneralSpaceType;
import net.opengis.indoorgml.navigation.v_1_0.NavigableBoundaryType;
import net.opengis.indoorgml.navigation.v_1_0.NavigableSpaceType;
import net.opengis.indoorgml.navigation.v_1_0.TransferBoundaryType;
import net.opengis.indoorgml.navigation.v_1_0.TransferSpaceType;
import net.opengis.indoorgml.navigation.v_1_0.TransitionSpaceType;

public class JIndoorGMLCoreTraversalUtil extends JAXBTraversalUtil{
	
	public static BindingNode convertIndoorFeatureType(IndoorFeaturesType target, BindingNode node, XLinkSymbolMap symbolMap) throws Exception {
		/* =========== */
	    /* Super Class */
	    /* =========== */
		node = JGMLTraversalUtil.convertAbstractFeatureType(target, node, symbolMap);
		
		/* ============ */
	    /* Associations */
	    /* ============ */
		PrimalSpaceFeaturesPropertyType primalSpaceFeaturesPropertyType = target.getPrimalSpaceFeatures();
		if(primalSpaceFeaturesPropertyType != null){
			BindingNode psfBN = convertPrimalSpaceFeaturesType(primalSpaceFeaturesPropertyType.getPrimalSpaceFeatures(), new BindingNode(), symbolMap);
			node.addAssociation("PRIMALSPACEFEATURE", psfBN);
		}
		MultiLayeredGraphType multiLayeredGraphType = target.getMultiLayeredGraph();
		if(multiLayeredGraphType != null){
			BindingNode mlgBN = convertMultiLayeredGraphType(multiLayeredGraphType, new BindingNode(), symbolMap);
			node.addAssociation("MULTILAYEREDGRAPH", mlgBN);
		}
		
		return node;
	}

	@SuppressWarnings({ "unchecked" })
	public static BindingNode convertPrimalSpaceFeaturesType(PrimalSpaceFeaturesType target, BindingNode node, XLinkSymbolMap symbolMap) throws Exception{
		/* =========== */
	    /* Super Class */
	    /* =========== */
		node = JGMLTraversalUtil.convertAbstractFeatureType(target, node, symbolMap);
		
		/* ========== */
	    /* Attributes */
	    /* ========== */
		//target.getAggregationType();
		/* ============ */
	    /* Associations */
	    /* ============ */
		List<FeaturePropertyType> cellSpaceMember = target.getCellSpaceMember();
		if(checkListValidation(cellSpaceMember)){
			for(FeaturePropertyType fp : cellSpaceMember){
				JAXBElement<CellSpaceType> cs = (JAXBElement<CellSpaceType>) fp.getAbstractFeature();	
				BindingNode csBN = convertCellSpaceType(cs.getValue(), new BindingNode(), symbolMap);
				node.addCollection("CELLSPCACE", csBN);
			}	
		}
		List<FeaturePropertyType> cellSpaceBoundaryMember = target.getCellSpaceBoundaryMember();
		if(checkListValidation(cellSpaceBoundaryMember)){
			for(FeaturePropertyType fp : cellSpaceBoundaryMember){
				JAXBElement<CellSpaceBoundaryType> csb = (JAXBElement<CellSpaceBoundaryType>) fp.getAbstractFeature();	
				BindingNode csBN = convertCellSpaceBoundaryType(csb.getValue(), new BindingNode(), symbolMap);
				node.addCollection("CELLSPCACEBOUNDARY", csBN);
			}
		}
		
		return node;
	}

	private static BindingNode convertCellSpaceType(CellSpaceType target, BindingNode node, XLinkSymbolMap symbolMap) throws Exception {
		/* =========== */
	    /* Super Class */
	    /* =========== */
		node = JGMLTraversalUtil.convertAbstractFeatureType(target, node, symbolMap);
		/* ========== */
	    /* Navigation */
	    /* ========== */
		if(target instanceof NavigableSpaceType){
			if(target instanceof GeneralSpaceType){
				node.addAttribute("NAVIGABLETYPE", "GeneralSpaceType");	
			}
			else if(target instanceof TransferSpaceType){
				if(target instanceof AnchorSpaceType){
					node.addAttribute("NAVIGABLETYPE", "AnchorSpaceType");
				}
				else if(target instanceof ConnectionSpaceType){
					node.addAttribute("NAVIGABLETYPE", "ConnectionSpaceType");
				}
				else if(target instanceof TransitionSpaceType){
					node.addAttribute("NAVIGABLETYPE", "TransitionSpaceType");
				}
				else{
					node.addAttribute("NAVIGABLETYPE", "TransferSpaceType");
				}
			}
			else{
				node.addAttribute("NAVIGABLETYPE", "NavigableSpaceType");
			}
			NavigableSpaceType ntarget = (NavigableSpaceType) target;
			
			BindingNode clazzNode = JGMLTraversalUtil.convertCodeType(ntarget.getClazz(), new BindingNode(), symbolMap);
			node.addAttribute("CLAZZ", clazzNode);
			BindingNode functionNode = JGMLTraversalUtil.convertCodeType(ntarget.getFunction(), new BindingNode(), symbolMap);
			node.addAttribute("FUNCTION", functionNode);
			BindingNode usageNode = JGMLTraversalUtil.convertCodeType(ntarget.getUsage(), new BindingNode(), symbolMap);
			node.addAttribute("USAGE", usageNode);
			
		}
		else{
			node.addAttribute("NAVIGABLETYPE", "CellSpaceType");
		}
		/* ========== */
	    /* Geometries */
	    /* ========== */
		SurfacePropertyType geometry2D = target.getGeometry2D();
		if(geometry2D != null){
			BindingNode g2BN = JGMLTraversalUtil.convertSurfacePropertyType(geometry2D, new BindingNode(), symbolMap);
			node.addAssociation("GEOMETRY2D", g2BN);
		}
		SolidPropertyType geometry3D = target.getGeometry3D();
		if(geometry3D != null){
			BindingNode g3BN = JGMLTraversalUtil.convertSolidPropertyType(geometry3D, new BindingNode(), symbolMap);
			node.addAssociation("GEOMETRY3D", g3BN);
		}
		/* ============ */
	    /* Associations */
	    /* ============ */
		StatePropertyType duality = target.getDuality();
		if(duality != null){
			node.addAttribute("DUALITY", duality.getHref());
		}
		List<ExternalReferenceType> externalReference = target.getExternalReference();
		if(checkListValidation(externalReference)){
			for(ExternalReferenceType ert : externalReference){
				BindingNode erBN = convertExternalReference(ert, new BindingNode(), symbolMap);
				node.addCollection("EXTERNALREFERENCE", erBN);
			}
		}
		List<CellSpaceBoundaryPropertyType> partialboundedBy = target.getPartialboundedBy();
		if(checkListValidation(partialboundedBy)){
			ArrayList<String> pb = new ArrayList<String>();
			for(CellSpaceBoundaryPropertyType csb : partialboundedBy){
				pb.add(csb.getHref());
			}
			node.addAttribute("PARTIALBOUNDEDBY", pb);
		}
		return node;
	}

	private static BindingNode convertCellSpaceBoundaryType(CellSpaceBoundaryType target, BindingNode node, XLinkSymbolMap symbolMap) {
		/* =========== */
	    /* Super Class */
	    /* =========== */
		node = JGMLTraversalUtil.convertAbstractFeatureType(target, node, symbolMap);
		/* ========== */
	    /* Navigation */
	    /* ========== */
		if(target instanceof NavigableBoundaryType){
			if(target instanceof TransferBoundaryType){
				if(target instanceof ConnectionBoundaryType){
					node.addAttribute("NAVIGABLETYPE", "ConnectionBoundaryType");
				}
				else if(target instanceof AnchorBoundaryType){
					node.addAttribute("NAVIGABLETYPE", "AnchorBoundaryType");
				}
				else{
					node.addAttribute("NAVIGABLETYPE", "TransferBoundaryType");
				}
			}
			else{
				node.addAttribute("NAVIGABLETYPE", "NavigableBoundaryType");
			}
		}
		else{
			node.addAttribute("NAVIGABLETYPE", "CellSpaceBoundaryType");
		}
		/* ========== */
	    /* Geometries */
	    /* ========== */
		CurvePropertyType geometry2D = target.getGeometry2D();
		if(geometry2D != null){
			BindingNode g2BN = JGMLTraversalUtil.convertCurvePropertyType(geometry2D, new BindingNode(), symbolMap);
			node.addAssociation("GEOMETRY2D", g2BN);
		}
		SurfacePropertyType geometry3D = target.getGeometry3D();
		if(geometry3D != null){
			BindingNode g3BN = JGMLTraversalUtil.convertSurfacePropertyType(geometry3D, new BindingNode(), symbolMap);
			node.addAssociation("GEOMETRY3D", g3BN);
		}
		/* ============ */
	    /* Associations */
	    /* ============ */
		TransitionPropertyType duality = target.getDuality();
		if(duality != null){
			node.addAttribute("DUALITY", duality.getHref());
		}
		List<ExternalReferenceType> externalReference = target.getExternalReference();
		if(checkListValidation(externalReference)){
			for(ExternalReferenceType ert : externalReference){
				BindingNode erBN = convertExternalReference(ert, new BindingNode(), symbolMap);
				node.addCollection("EXTERNALREFERENCE", erBN);
			}
		}
		
		return node;
	}
	
	private static BindingNode convertExternalReference(ExternalReferenceType target, BindingNode node, XLinkSymbolMap symbolMap) {
		/* ============ */
	    /* Associations */
	    /* ============ */
		if(target.getInformationSystem() != null){
			node.addAttribute("INFORMATIONSYSTEM", target.getInformationSystem());
		}
		if(target.getExternalObject() != null){
			ExternalObjectReferenceType externalObjectReferenceType = target.getExternalObject();
			if(externalObjectReferenceType.getName() != null){
				node.addAttribute("NAME", externalObjectReferenceType.getName());
			}
			if(externalObjectReferenceType.getUri() != null){
				node.addAttribute("URI", externalObjectReferenceType.getUri());
			}
		}
		return node;
	}

	private static BindingNode convertMultiLayeredGraphType(MultiLayeredGraphType target, BindingNode node, XLinkSymbolMap symbolMap) {
		/* =========== */
	    /* Super Class */
	    /* =========== */
		node = JGMLTraversalUtil.convertAbstractFeatureType(target, node, symbolMap);
		/* ============ */
	    /* Associations */
	    /* ============ */
		List<InterEdgesType> interEdges = target.getInterEdges();
		if(checkListValidation(interEdges)){
			for(InterEdgesType ie : interEdges){
				BindingNode ieBN = convertInterEdge(ie, new BindingNode(), symbolMap);
				node.addCollection("INTEREDGES", ieBN);
			}
		}
		List<SpaceLayersType> spaceLayers = target.getSpaceLayers();
		if(checkListValidation(spaceLayers)){
			for(SpaceLayersType sls : spaceLayers){
				BindingNode slsBN = convertSpaceLayers(sls, new BindingNode(), symbolMap);
				node.addCollection("SPACELAYERS", slsBN);
			}
		}
		
		return node;
	}

	private static BindingNode convertInterEdge(InterEdgesType target, BindingNode node, XLinkSymbolMap symbolMap) {
		/* =========== */
	    /* Super Class */
	    /* =========== */
		node = JGMLTraversalUtil.convertAbstractFeatureType(target, node, symbolMap);
		/* ============ */
	    /* Associations */
	    /* ============ */
		List<InterLayerConnectionMemberType> interLayerConnection = target.getInterLayerConnectionMember();
		if(checkListValidation(interLayerConnection)){
			for(InterLayerConnectionMemberType ilcm : interLayerConnection){
				BindingNode ilcBN = convertInterLayerConnection(ilcm.getInterLayerConnection(), new BindingNode(), symbolMap);
				node.addCollection("INTERLAYERCONNECTION", ilcBN);
			}
		}
		
		return node;
	}

	private static BindingNode convertSpaceLayers(SpaceLayersType target, BindingNode node,
			XLinkSymbolMap symbolMap) {
		/* =========== */
	    /* Super Class */
	    /* =========== */
		node = JGMLTraversalUtil.convertAbstractFeatureType(target, node, symbolMap);
		/* ============ */
	    /* Associations */
	    /* ============ */
		List<SpaceLayerMemberType> spaceLayer = target.getSpaceLayerMember();
		if(checkListValidation(spaceLayer)){
			for(SpaceLayerMemberType sl : spaceLayer){
				BindingNode slBN = convertSpaceLayerType(sl.getSpaceLayer(), new BindingNode(), symbolMap);
				node.addCollection("SPACELAYER", slBN);
			}
		}
		
		return node;
	}
	
	private static BindingNode convertInterLayerConnection(InterLayerConnectionType target, BindingNode node, XLinkSymbolMap symbolMap) {
		/* =========== */
	    /* Super Class */
	    /* =========== */
		node = JGMLTraversalUtil.convertAbstractFeatureType(target, node, symbolMap);
		/* ========== */
	    /* Attributes */
	    /* ========== */
		node.addAttribute("TYPEOFTOPOEXPRESSION", target.getTypeOfTopoExpression());
		node.addAttribute("COMMENT", target.getComment());
		/* ============ */
	    /* Associations */
	    /* ============ */
		List<SpaceLayerPropertyType> spaceLayers = target.getConnectedLayers();
		if(checkListValidation(spaceLayers)){
			ArrayList<String> cl = new ArrayList<String>();
			for(SpaceLayerPropertyType sl : spaceLayers){
				cl.add(sl.getHref());
			}
			node.addAttribute("CONNECTEDLAYERS", cl);
		}
		List<StatePropertyType> interConnects = target.getInterConnects();
		if(checkListValidation(interConnects)){
			ArrayList<String> ic = new ArrayList<String>();
			for(StatePropertyType sp : interConnects){
				ic.add(sp.getHref());
			}
			node.addAttribute("INTERCONNECTS", ic);
		}
		
		return node;
	}
	
	private static BindingNode convertSpaceLayerType(SpaceLayerType target, BindingNode node, XLinkSymbolMap symbolMap) {
		/* =========== */
	    /* Super Class */
	    /* =========== */
		node = JGMLTraversalUtil.convertAbstractFeatureType(target, node, symbolMap);
		/* ========== */
	    /* Attributes */
	    /* ========== */
		SpaceLayerClassTypeType clazz = target.getClazz();
		if(clazz != null) {
			node.addAttribute("CLAZZ/VALUE", clazz.value());
		}
		List<CodeType> function = target.getFunction();
		if(checkListValidation(function)) {
			BindingNode functionNode = JGMLTraversalUtil.convertCodeType(function.get(0), new BindingNode(), symbolMap);
			node.addAttribute("FUNCTION", functionNode);
		}
		List<CodeType> usage = target.getUsage();
		if(checkListValidation(usage)) {
			BindingNode usageNode = JGMLTraversalUtil.convertCodeType(usage.get(0), new BindingNode(), symbolMap);
			node.addAttribute("USAGE", usageNode);
		}
		XMLGregorianCalendar terminateDate = target.getTerminationDate();
		if(terminateDate != null){
			Date tDate = terminateDate.toGregorianCalendar().getTime();
			node.addAttribute("TERMINATEDATE", tDate);
		}
		XMLGregorianCalendar creationDate = target.getCreationDate();
		if(creationDate != null){
			Date cDate = creationDate.toGregorianCalendar().getTime();
			node.addAttribute("CREATIONDATE", cDate);
		}
		/* ============ */
	    /* Associations */
	    /* ============ */
		List<NodesType> nodes = target.getNodes();
		if(checkListValidation(nodes)){
			for(NodesType n : nodes){
				BindingNode nBN = convertNodesType(n, new BindingNode(), symbolMap);
				node.addCollection("NODES", nBN);
			}
		}
		List<EdgesType> edges = target.getEdges();
		if(checkListValidation(edges)){
			for(EdgesType e : edges){
				BindingNode eBN = convertEdgesType(e, new BindingNode(), symbolMap);
				node.addCollection("EDGES", eBN);
			}
		}
		return node;
	}
	
	private static BindingNode convertNodesType(NodesType target, BindingNode node, XLinkSymbolMap symbolMap) {
		/* =========== */
	    /* Super Class */
	    /* =========== */
		node = JGMLTraversalUtil.convertAbstractFeatureType(target, node, symbolMap);
		/* ============ */
	    /* Associations */
	    /* ============ */
		List<StateMemberType> stateMember = target.getStateMember();
		if(checkListValidation(stateMember)){
			for(StateMemberType sm : stateMember){
				BindingNode sBN = convertStateType(sm.getState(), new BindingNode(), symbolMap);
				node.addCollection("STATEMEMBER", sBN);
			}
		}
		return node;
	}

	public static BindingNode convertStateType(StateType target, BindingNode node, XLinkSymbolMap symbolMap) {
		/* =========== */
	    /* Super Class */
	    /* =========== */
		node = JGMLTraversalUtil.convertAbstractFeatureType(target, node, symbolMap);
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
		/* ========== */
	    /* Geometries */
	    /* ========== */
		PointPropertyType geometry = target.getGeometry();
		if(geometry != null){
			BindingNode gBN = JGMLTraversalUtil.convertPointPropertyType(geometry, new BindingNode(), symbolMap);
			node.addAssociation("GEOMETRY", gBN);
		}
		/* ============ */
	    /* Associations */
	    /* ============ */
		CellSpacePropertyType duality = target.getDuality();
		if(duality != null){
			node.addAttribute("DUALITY", duality.getHref());
		}
		List<TransitionPropertyType> connects = target.getConnects();
		if(checkListValidation(connects)){
			ArrayList<String> c = new ArrayList<String>();
			for(TransitionPropertyType tp : connects){
				c.add(tp.getHref());
			}
			node.addAttribute("CONNECTS", c);
		}
		
		return node;
	}

	private static BindingNode convertEdgesType(EdgesType target, BindingNode node, XLinkSymbolMap symbolMap) {
		/* =========== */
	    /* Super Class */
	    /* =========== */
		node = JGMLTraversalUtil.convertAbstractFeatureType(target, node, symbolMap);
		/* ============ */
	    /* Associations */
	    /* ============ */
		List<TransitionMemberType> transitionMember = target.getTransitionMember();
		if(checkListValidation(transitionMember)){
			for(TransitionMemberType tm : transitionMember){
				BindingNode tBN = convertTransitionType(tm.getTransition(), new BindingNode(), symbolMap);
				node.addCollection("TRANSITIONMEMBER", tBN);
			}
		}
		return node;
	}

	public static BindingNode convertTransitionType(TransitionType target, BindingNode node, XLinkSymbolMap symbolMap) {
		/* =========== */
	    /* Super Class */
	    /* =========== */
		node = JGMLTraversalUtil.convertAbstractFeatureType(target, node, symbolMap);
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
		node.addAttribute("WEIGHT", target.getWeight());
		/* ========== */
	    /* Geometries */
	    /* ========== */
		CurvePropertyType geometry = target.getGeometry();
		if(geometry != null){
			BindingNode gBN = JGMLTraversalUtil.convertCurvePropertyType(geometry, new BindingNode(), symbolMap);
			node.addAssociation("GEOMETRY", gBN);
		}
		/* ============ */
	    /* Associations */
	    /* ============ */
		CellSpaceBoundaryPropertyType duality = target.getDuality();
		if(duality != null){
			node.addAttribute("DUALITY", duality.getHref());
		}
		List<StatePropertyType> connects = target.getConnects();
		if(checkListValidation(connects)){
			ArrayList<String> c = new ArrayList<String>();
			for(StatePropertyType tp : connects){
				c.add(tp.getHref());
			}
			node.addAttribute("CONNECTS", c);
		}
		
		return node;
	}

	public static boolean checkListValidation(List<?> list) {
		return list != null && list.size()>0;
	}
}
