package net.opengis.indoorgml.v_1_0.vo.core;

import java.util.ArrayList;

public class PrimalSpaceFeatures extends IndoorObject {
	public ArrayList<IndoorObject> indoorObject = new ArrayList<IndoorObject>();
	public IndoorFeatures parents;
	
	private ArrayList<CellSpace> cellSpace;
	private ArrayList<CellSpaceBoundary> cellSpaceBoundary;
	
	public ArrayList<CellSpace> getCellSpace() {
		return cellSpace;
	}
	public void setCellSpace(ArrayList<CellSpace> cellSpace) {
		this.cellSpace = cellSpace;
	}
	public ArrayList<CellSpaceBoundary> getCellSpaceBoundary() {
		return cellSpaceBoundary;
	}
	public void setCellSpaceBoundary(ArrayList<CellSpaceBoundary> cellSpaceBoundary) {
		this.cellSpaceBoundary = cellSpaceBoundary;
	}
	
}
