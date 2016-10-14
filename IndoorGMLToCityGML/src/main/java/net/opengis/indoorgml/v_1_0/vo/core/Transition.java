package net.opengis.indoorgml.v_1_0.vo.core;

import java.util.ArrayList;

import net.opengis.indoorgml.v_1_0.vo.spatial.VOCurve;

public class Transition extends IndoorObject {
	public ArrayList<IndoorObject> indoorObject = new ArrayList<IndoorObject>();
	public Object parents;
	public Integer dualityID;
	public Integer connectsAID;
	public Integer connectsBID;
	
	private Double weight;
	private ArrayList<String> hrefConnects;
	private String hrefDuality;
	private VOCurve geometry;
	
	private String hrefConnectsA;
	private String hrefConnectsB;
	
	private CellSpaceBoundary duality;
	private ArrayList<State> connects;
	private State connectsA;
	private State connectsB;
	
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	public ArrayList<String> getHrefConnects() {
		if(hrefConnectsA != null && hrefConnectsB != null){
			hrefConnects = new ArrayList<String>();
			hrefConnects.add(hrefConnectsA);
			hrefConnects.add(hrefConnectsB);
		}
		return hrefConnects;
	}
	public void setHrefConnects(ArrayList<String> hrefConnects) {
		if(hrefConnects.size() == 2)
			this.hrefConnects = hrefConnects;	
	}
	public String getHrefDuality() {
		return hrefDuality;
	}
	public void setHrefDuality(String hrefDuality) {
		this.hrefDuality = hrefDuality;
	}
	public VOCurve getGeometry() {
		return geometry;
	}
	public void setGeometry(VOCurve geometry) {
		this.geometry = geometry;
	}
	/**
	 * @return the connects
	 */
	public ArrayList<State> getConnects() {
		return connects;
	}
	/**
	 * @param connects the connects to set
	 */
	public void setConnects(ArrayList<State> connects) {
		this.connects = connects;
	}
	/**
	 * @return the duality
	 */
	public CellSpaceBoundary getDuality() {
		return duality;
	}
	/**
	 * @param duality the duality to set
	 */
	public void setDuality(CellSpaceBoundary duality) {
		this.duality = duality;
	}
	
}
