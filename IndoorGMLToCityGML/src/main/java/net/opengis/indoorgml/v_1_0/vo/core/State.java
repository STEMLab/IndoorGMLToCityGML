package net.opengis.indoorgml.v_1_0.vo.core;

import java.util.ArrayList;

import net.opengis.indoorgml.v_1_0.vo.spatial.VOPoint;

public class State extends IndoorObject {
	public ArrayList<IndoorObject> indoorObject = new ArrayList<IndoorObject>();
	public Object parents;
	public Integer dualityID;
	
	private String hrefDuality;
	private ArrayList<String> hrefConnects;
	private VOPoint geometry;
	
	private CellSpace duality;
	private ArrayList<Transition> connects;
	
	public String getHrefDuality() {
		return hrefDuality;
	}
	public void setHrefDuality(String hrefDuality) {
		this.hrefDuality = hrefDuality;
	}
	public ArrayList<String> getHrefConnects() {
		return hrefConnects;
	}
	public void setHrefConnects(ArrayList<String> hrefConnects) {
		this.hrefConnects = hrefConnects;
	}
	public VOPoint getGeometry() {
		return geometry;
	}
	public void setGeometry(VOPoint geometry) {
		this.geometry = geometry;
	}
	/**
	 * @return the duality
	 */
	public CellSpace getDuality() {
		return duality;
	}
	/**
	 * @param duality the duality to set
	 */
	public void setDuality(CellSpace duality) {
		this.duality = duality;
	}
	/**
	 * @return the connects
	 */
	public ArrayList<Transition> getConnects() {
		return connects;
	}
	/**
	 * @param connects the connects to set
	 */
	public void setConnects(ArrayList<Transition> connects) {
		this.connects = connects;
	}
	
}
