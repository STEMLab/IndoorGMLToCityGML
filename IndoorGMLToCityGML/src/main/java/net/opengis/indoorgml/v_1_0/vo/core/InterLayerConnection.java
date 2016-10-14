package net.opengis.indoorgml.v_1_0.vo.core;

import java.util.ArrayList;

public class InterLayerConnection extends IndoorObject {
	public ArrayList<IndoorObject> indoorObject = new ArrayList<IndoorObject>();
	public InterEdges parents;
	public Integer interConnectsAID;
	public Integer interConnectsBID;
	public Integer connectedLayersAID;
	public Integer connectedLayersBID;
	
	private String typeOfTopoExpression;
	private String comment;
	private String hrefInterConnectsA;
	private String hrefInterConnectsB;
	private String hrefConnectedLayersA;
	private String hrefCconnectedLayersB;
	private ArrayList<String> hrefInterConnects;
	private ArrayList<String> hrefConnectedLayers;
	
	private ArrayList<State> interConnects;
	private ArrayList<SpaceLayer> connectedLayers;

	public String getTypeOfTopoExpression() {
		return typeOfTopoExpression;
	}
	public void setTypeOfTopoExpression(String typeOfTopoExpression) {
		this.typeOfTopoExpression = typeOfTopoExpression;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public ArrayList<String> getHrefInterConnects() {
		if(hrefInterConnectsA != null && hrefInterConnectsB != null){
			hrefInterConnects = new ArrayList<String>();
			hrefInterConnects.add(hrefInterConnectsA);
			hrefInterConnects.add(hrefInterConnectsB);
		}
		return hrefInterConnects;
	}
	public void setHrefInterConnects(ArrayList<String> hrefInterConnects) {
		if(interConnects.size() == 2){
			this.hrefInterConnects = hrefInterConnects;
			hrefInterConnectsA = hrefInterConnects.get(0);
			hrefInterConnectsB = hrefInterConnects.get(1);
		}
	}
	public ArrayList<String> getHrefConnectedLayers() {
		if(hrefConnectedLayersA != null && hrefCconnectedLayersB != null){
			hrefConnectedLayers = new ArrayList<String>();
			hrefConnectedLayers.add(hrefConnectedLayersA);
			hrefConnectedLayers.add(hrefCconnectedLayersB);
		}
		return hrefConnectedLayers;
	}
	public void setHrefConnectedLayers(ArrayList<String> hrefConnectedLayers) {
		if(hrefConnectedLayers.size() == 2){
			this.hrefConnectedLayers = hrefConnectedLayers;
			hrefConnectedLayersA = hrefConnectedLayers.get(0);
			hrefCconnectedLayersB = hrefConnectedLayers.get(1);
		}
	}
	public ArrayList<State> getInterConnects() {
		return interConnects;
	}
	public void setInterConnects(ArrayList<State> interConnects) {
		this.interConnects = interConnects;
	}
	public ArrayList<SpaceLayer> getConnectedLayers() {
		return connectedLayers;
	}
	public void setConnectedLayers(ArrayList<SpaceLayer> connectedLayers) {
		this.connectedLayers = connectedLayers;
	}
}
