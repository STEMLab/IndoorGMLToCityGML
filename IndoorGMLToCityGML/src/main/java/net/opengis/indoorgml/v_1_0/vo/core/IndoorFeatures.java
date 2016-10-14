package net.opengis.indoorgml.v_1_0.vo.core;

import java.util.ArrayList;

public class IndoorFeatures extends IndoorObject {
	public ArrayList<IndoorObject> indoorObject = new ArrayList<IndoorObject>();
	public String stringID;
	
	private PrimalSpaceFeatures primalSpaceFeatures;
	private MultiLayeredGraph multiLayeredGraph;
	
	
	public PrimalSpaceFeatures getPrimalSpaceFeatures() {
		return primalSpaceFeatures;
	}
	public void setPrimalSpaceFeatures(PrimalSpaceFeatures primalSpaceFeatures) {
		this.primalSpaceFeatures = primalSpaceFeatures;
	}
	public MultiLayeredGraph getMultiLayeredGraph() {
		return multiLayeredGraph;
	}
	public void setMultiLayeredGraph(MultiLayeredGraph multiLayeredGraph) {
		this.multiLayeredGraph = multiLayeredGraph;
	}
}
