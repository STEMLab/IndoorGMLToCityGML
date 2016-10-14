package net.opengis.indoorgml.v_1_0.vo.navigation;

import java.util.ArrayList;

import net.opengis.indoorgml.v_1_0.vo.core.IndoorObject;

public class Route extends IndoorObject {
	public ArrayList<IndoorObject> indoorObject = new ArrayList<IndoorObject>();
	public Integer startRouteNodeID;
	public Integer endRouteNodeID;
	public String stringID;
	
	private RouteNode startRouteNode;
	private RouteNode endRouteNode;
	private RouteNodes routeNodes;
	private Path path;
	
	public RouteNode getStartRouteNode() {
		return startRouteNode;
	}
	public void setStartRouteNode(RouteNode startRouteNode) {
		this.startRouteNode = startRouteNode;
	}
	public RouteNode getEndRouteNode() {
		return endRouteNode;
	}
	public void setEndRouteNode(RouteNode endRouteNode) {
		this.endRouteNode = endRouteNode;
	}
	public RouteNodes getRouteNodes() {
		return routeNodes;
	}
	public void setRouteNodes(RouteNodes routeNodes) {
		this.routeNodes = routeNodes;
	}
	public Path getPath() {
		return path;
	}
	public void setPath(Path path) {
		this.path = path;
	}
	
}
