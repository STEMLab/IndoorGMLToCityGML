package net.opengis.indoorgml.v_1_0.vo.spatial;

import org.opengis.geometry.primitive.Solid;

public class VOSolid extends VOGeometry {
	private Solid geometry;	
	
	/**
	 * @return the geometry
	 */
	public Solid getGeometry() {
		return geometry;
	}
	/**
	 * @param geometry the geometry to set
	 */
	public void setGeometry(Solid geometry) {
		this.geometry = geometry;
	}
}
