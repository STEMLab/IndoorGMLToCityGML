package net.opengis.indoorgml.v_1_0.vo.spatial;

import org.opengis.geometry.primitive.Curve;

public class VOCurve extends VOGeometry {
	private Curve geometry;
	
	/**
	 * @return the geometry
	 */
	public Curve getGeometry() {
		return geometry;
	}

	/**
	 * @param geometry the geometry to set
	 */
	public void setGeometry(Curve geometry) {
		this.geometry = geometry;
	}	
	
}
