package edu.pnu.importexport.store;

import java.util.ArrayList;
import java.util.List;

import edu.pnu.common.XLinkSymbolMap;
import edu.pnu.importexport.BindingNode;
import net.opengis.gml.v_3_2_1.CurvePropertyType;
import net.opengis.gml.v_3_2_1.PointPropertyType;
import net.opengis.indoorgml.core.v_1_0.StatePropertyType;
import net.opengis.indoorgml.core.v_1_0.TransitionPropertyType;
import net.opengis.indoorgml.navigation.v_1_0.PathType;
import net.opengis.indoorgml.navigation.v_1_0.RouteNodeMemberType;
import net.opengis.indoorgml.navigation.v_1_0.RouteNodePropertyType;
import net.opengis.indoorgml.navigation.v_1_0.RouteNodeType;
import net.opengis.indoorgml.navigation.v_1_0.RouteNodesType;
import net.opengis.indoorgml.navigation.v_1_0.RouteSegmentMemberType;
import net.opengis.indoorgml.navigation.v_1_0.RouteSegmentType;
import net.opengis.indoorgml.navigation.v_1_0.RouteType;

public class JIndoorGMLNaviTraversalUtil {

	public static BindingNode convertRouteType(RouteType target, BindingNode node, XLinkSymbolMap symbolMap) {
		/* =========== */
	    /* Super Class */
	    /* =========== */
		node = JGMLTraversalUtil.convertAbstractFeatureType(target, node, symbolMap);
		
		/* ============ */
	    /* Associations */
	    /* ============ */
		RouteNodePropertyType startRouteNode = target.getStartRouteNode();
		if(startRouteNode != null){
			BindingNode srnBN = convertRouteNode(startRouteNode.getRouteNode(), new BindingNode(), symbolMap);
			node.addAssociation("STARTROUTENODE", srnBN);
		}
		RouteNodePropertyType endRouteNode = target.getEndRouteNode();
		if(endRouteNode != null){
			BindingNode ernBN = convertRouteNode(endRouteNode.getRouteNode(), new BindingNode(), symbolMap);
			node.addAssociation("ENDROUTENODE", ernBN);
		}
		RouteNodesType routeNodes = target.getRouteNodes();
		if(routeNodes != null){
			BindingNode rnBN = convertRouteNodes(routeNodes, new BindingNode(), symbolMap);
			node.addAssociation("ROUTENODES", rnBN);
		}
		PathType path = target.getPath();
		if(path != null){
			BindingNode pBN = converPath(path, new BindingNode(), symbolMap);
			node.addAssociation("PATH", pBN);
		}
		
		return node;
	}

	private static BindingNode convertRouteNode(RouteNodeType target, BindingNode node, XLinkSymbolMap symbolMap) {
		/* =========== */
	    /* Super Class */
	    /* =========== */
		node = JGMLTraversalUtil.convertAbstractFeatureType(target, node, symbolMap);
		/* ============ */
	    /* Associations */
	    /* ============ */
		StatePropertyType referencedState = target.getReferencedState();
		if(referencedState != null){
			if(referencedState.getHref() != null){
				node.addAttribute("REFERENCEDSTATE", referencedState.getHref());
			}
			else{
				BindingNode rsBN = JIndoorGMLCoreTraversalUtil.convertStateType(referencedState.getState(), new BindingNode(), symbolMap);
				node.addAssociation("REFERENCEDSTATE", rsBN);
			}
		}
		/* ========== */
	    /* Geometries */
	    /* ========== */
		PointPropertyType geometry = target.getGeometry();
		if(geometry != null){
			node.addAttribute("GEOMETRY", geometry.getHref());
		}
		return node;
	}

	private static BindingNode convertRouteNodes(RouteNodesType target, BindingNode node, XLinkSymbolMap symbolMap) {
		/* =========== */
	    /* Super Class */
	    /* =========== */
		node = JGMLTraversalUtil.convertAbstractFeatureType(target, node, symbolMap);
		/* ============ */
	    /* Associations */
	    /* ============ */
		List<RouteNodeMemberType> nodeMember = target.getNodeMember();
		if(checkListValidation(nodeMember)){
			for(RouteNodeMemberType rnm : nodeMember){
				BindingNode rnBN = convertRouteNode(rnm.getRouteNode(), new BindingNode(), symbolMap);
				node.addCollection("ROUTENODEMEMBER", rnBN);
			}
		}
		
		return node;
	}

	private static BindingNode converPath(PathType target, BindingNode node, XLinkSymbolMap symbolMap) {
		/* =========== */
	    /* Super Class */
	    /* =========== */
		node = JGMLTraversalUtil.convertAbstractFeatureType(target, node, symbolMap);
		/* ============ */
	    /* Associations */
	    /* ============ */
		List<RouteSegmentMemberType> routeMember = target.getRouteMember();
		if(checkListValidation(routeMember)){
			for(RouteSegmentMemberType rsm : routeMember){
				BindingNode rsBN = convertRouteSegmentType(rsm.getRouteSegment(), new BindingNode(), symbolMap);
				node.addCollection("ROUTESEGMENTMEMBER", rsBN);
			}
		}
		
		return node;
	}
	
	private static BindingNode convertRouteSegmentType(RouteSegmentType target, BindingNode node, XLinkSymbolMap symbolMap) {
		/* =========== */
	    /* Super Class */
	    /* =========== */
		node = JGMLTraversalUtil.convertAbstractFeatureType(target, node, symbolMap);
		/* ========== */
	    /* Attributes */
	    /* ========== */
		node.addAttribute("WEIGHT", target.getWeight());
		/* ============ */
	    /* Associations */
	    /* ============ */
		List<RouteNodePropertyType> connects = target.getConnects();
		if(checkListValidation(connects)){
			ArrayList<String> connectsHref = new ArrayList<String>();
			for(RouteNodePropertyType tp : connects){
				connectsHref.add(tp.getHref());
			}
			node.addAttribute("CONNECTS", connectsHref);
		}
		TransitionPropertyType referencedTransition = target.getReferencedTransition();
		if(referencedTransition != null){
			BindingNode rtBN = JIndoorGMLCoreTraversalUtil.convertTransitionType(referencedTransition.getTransition(), new BindingNode(), symbolMap);
			node.addAssociation("REFERENCEDTRANSITION", rtBN);
		}
		/* ========== */
	    /* Geometries */
	    /* ========== */
		CurvePropertyType geometry = target.getGeometry();
		if(geometry != null){
			node.addAttribute("GEOMETRY", geometry.getHref());
		}
		
		return node;
	}

	public static boolean checkListValidation(List<?> list) {
		return list != null && list.size()>0;
	}
}
