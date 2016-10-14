package edu.pnu.importexport.store;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.pnu.importexport.BindingNode;
import net.opengis.indoorgml.v_1_0.vo.core.IndoorObject;
import net.opengis.indoorgml.v_1_0.vo.core.State;
import net.opengis.indoorgml.v_1_0.vo.core.Transition;
import net.opengis.indoorgml.v_1_0.vo.navigation.Path;
import net.opengis.indoorgml.v_1_0.vo.navigation.Route;
import net.opengis.indoorgml.v_1_0.vo.navigation.RouteNode;
import net.opengis.indoorgml.v_1_0.vo.navigation.RouteNodes;
import net.opengis.indoorgml.v_1_0.vo.navigation.RouteSegment;

public class IndoorGMLNaviVOConvertUtil {

	public static Route createRoute(BindingNode node, Map<String, Object> symbolMap){
		/* SuperClass Init */
		Route route = new Route();
		IndoorGMLCoreVOConvertUtil.setIndoorObject(route, node, "ROUTE", symbolMap);
		route.indoorObject.add(route);
		/* associations */
		BindingNode srnbN = node.getAssociation("STARTROUTENODE");
		if(srnbN != null){
			RouteNode startRouteNode = createRouteNode(srnbN, symbolMap);
			startRouteNode.parents = route;
			
			route.setStartRouteNode(startRouteNode);
			for(IndoorObject io : startRouteNode.indoorObject){
				route.indoorObject.add(io);
			}
		}
		BindingNode ernBN = node.getAssociation("ENDROUTENODE");
		if(ernBN != null){
			RouteNode endRouteNode = createRouteNode(ernBN, symbolMap);
			endRouteNode.parents = route;
			
			route.setEndRouteNode(endRouteNode);
			for(IndoorObject io : endRouteNode.indoorObject){
				route.indoorObject.add(io);
			}
		}
		BindingNode rnBN = node.getAssociation("ROUTENODES");
		if(rnBN != null){
			RouteNodes routeNodes = createRouteNodes(rnBN, symbolMap);
			routeNodes.parents = route;
			
			route.setRouteNodes(routeNodes);
			for(IndoorObject io : routeNodes.indoorObject){
				route.indoorObject.add(io);
			}
		}
		BindingNode pBN = node.getAssociation("PATH");
		if(pBN != null){
			Path path = createPath(pBN, symbolMap);
			path.parents = route;
			
			route.setPath(path);
			for(IndoorObject io : path.indoorObject){
				route.indoorObject.add(io);
			}
		}
		
		return route;
	}

	private static RouteNode createRouteNode(BindingNode node, Map<String, Object> symbolMap) {
		/* SuperClass Init */
		RouteNode routeNode = new RouteNode();
		IndoorGMLCoreVOConvertUtil.setIndoorObject(routeNode, node, "ROUTENODE", symbolMap);
		routeNode.indoorObject.add(routeNode);
		/* associations */
		BindingNode rsBN = node.getAssociation("REFERENCEDSTATE");
		if(rsBN != null){
			State referencedState = IndoorGMLCoreVOConvertUtil.createState(rsBN, symbolMap);
			referencedState.parents = routeNode;
			
			routeNode.setReferencedState(referencedState);
			for(IndoorObject io : referencedState.indoorObject){
				routeNode.indoorObject.add(io);
			}
		}
		/* attributes */
		if(node.getAttribute("REFERENCEDSTATE") != null){
			routeNode.setReferencedStateHref((String) node.getAttribute("REFERENCEDSTATE"));
		}
		if(node.getAttribute("GEOMETRY") != null){
			routeNode.setGeometryHref((String) node.getAttribute("GEOMETRY"));
		}
		
		return routeNode;
	}

	private static RouteNodes createRouteNodes(BindingNode node, Map<String, Object> symbolMap) {
		/* SuperClass Init */
		RouteNodes routeNodes = new RouteNodes();
		IndoorGMLCoreVOConvertUtil.setIndoorObject(routeNodes, node, "ROUTENODES", symbolMap);
		routeNodes.indoorObject.add(routeNodes);
		/* associations */
		ArrayList<RouteNode> nodeMember = new ArrayList<RouteNode>();
		List<BindingNode> rnBNList = node.getCollection("ROUTENODEMEMBER");
		for(BindingNode rnBN : rnBNList){
			RouteNode routeNode = createRouteNode(rnBN, symbolMap);
			routeNode.parents = routeNodes;
			nodeMember.add(routeNode);
			
			for(IndoorObject io : routeNode.indoorObject){
				routeNodes.indoorObject.add(io);
			}
		}
		routeNodes.setNodeMember(nodeMember);
		
		return routeNodes;
	}

	private static Path createPath(BindingNode node, Map<String, Object> symbolMap) {
		/* SuperClass Init */
		Path path = new Path();
		IndoorGMLCoreVOConvertUtil.setIndoorObject(path, node, "PATH", symbolMap);
		path.indoorObject.add(path);
		/* associations */
		ArrayList<RouteSegment> routeMember = new ArrayList<RouteSegment>();
		List<BindingNode> rsBNList = node.getCollection("ROUTESEGMENTMEMBER");
		for(BindingNode rsBN : rsBNList){
			RouteSegment routeSegment = createRouteSegment(rsBN, symbolMap);
			routeSegment.parents = path;
			routeMember.add(routeSegment);
			
			for(IndoorObject io : routeSegment.indoorObject){
				path.indoorObject.add(io);
			}
		}
		path.setRouteMember(routeMember);
		
		return path;
	}
	
	@SuppressWarnings("unchecked")
	private static RouteSegment createRouteSegment(BindingNode node, Map<String, Object> symbolMap) {
		/* SuperClass Init */
		RouteSegment routeSegment = new RouteSegment();
		IndoorGMLCoreVOConvertUtil.setIndoorObject(routeSegment, node, "ROUTESEGMENT", symbolMap);
		routeSegment.indoorObject.add(routeSegment);
		/* attributes */
		if(node.getAttribute("WEIGHT") != null){
			routeSegment.setWeight(((Double) node.getAttribute("WEIGHT")).doubleValue());
		}
		if(node.getAttribute("CONNECTS") != null){
			routeSegment.setConnects((ArrayList<String>) node.getAttribute("CONNECTS"));
		}
		if(node.getAttribute("GEOMETRY") != null){
			routeSegment.setGeometryHref((String) node.getAttribute("GEOMETRY"));
		}
		/* associations */
		if(node.getAssociation("REFERENCEDTRANSITION") != null){
			Transition referencedTransition = IndoorGMLCoreVOConvertUtil.createTransition(node.getAssociation("REFERENCEDTRANSITION"), symbolMap);
			referencedTransition.parents = routeSegment;
			
			routeSegment.setReferencedTransition(referencedTransition);
			for(IndoorObject io : referencedTransition.indoorObject){
				routeSegment.indoorObject.add(io);
			}
		}
		
		return routeSegment;
	}
}
