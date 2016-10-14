package net.opengis.indoorgml.v_1_0.vo.navigation;

import java.util.ArrayList;

import net.opengis.indoorgml.v_1_0.vo.core.IndoorObject;
import net.opengis.indoorgml.v_1_0.vo.core.Transition;
import net.opengis.indoorgml.v_1_0.vo.spatial.VOCurve;

public class RouteSegment extends IndoorObject {
	public ArrayList<IndoorObject> indoorObject = new ArrayList<IndoorObject>();
	public Path parents;
	public Integer referencedTransitionID;
	public Integer connectsAID;
	public Integer connectsBID;
	public Integer geometryID;
	
	private Double weight;
	private ArrayList<String> connects;
	private Transition referencedTransition;
	private VOCurve geometry;
	
	private String geometryHref;
	private String connectsA;
	private String connectsB;
	
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public ArrayList<String> getConnects() {
		if(connectsA != null && connectsB != null){
			connects = new ArrayList<String>();
			connects.add(connectsA);
			connects.add(connectsB);
		}
		return connects;
	}
	public void setConnects(ArrayList<String> connects) {
		if(connects.size() == 2)
			this.connects = connects;	
	}
	public Transition getReferencedTransition() {
		return referencedTransition;
	}
	public void setReferencedTransition(Transition referencedTransition) {
		this.referencedTransition = referencedTransition;
	}
	public VOCurve getGeometry() {
		return geometry;
	}
	public void setGeometry(VOCurve geometry) {
		this.geometry = geometry;
	}
	public String getGeometryHref() {
		return geometryHref;
	}
	public void setGeometryHref(String geometry) {
		this.geometryHref = geometry;
	}
	public String getConnectsA() {
		return connectsA;
	}
	public void setConnectsA(String connectsA) {
		this.connectsA = connectsA;
	}
	public String getConnectsB() {
		return connectsB;
	}
	public void setConnectsB(String connectsB) {
		this.connectsB = connectsB;
	}
}
