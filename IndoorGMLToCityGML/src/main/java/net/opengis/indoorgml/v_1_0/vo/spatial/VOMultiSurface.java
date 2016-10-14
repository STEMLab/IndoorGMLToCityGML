package net.opengis.indoorgml.v_1_0.vo.spatial;

import org.opengis.geometry.aggregate.MultiSurface;

public class VOMultiSurface extends VOGeometry {
	private MultiSurface geometry;
	
	/**
	 * @return the geometry
	 */
	public MultiSurface getGeometry() {
		return geometry;
	}
	/**
	 * @param geometry the geometry to set
	 */
	public void setGeometry(MultiSurface geometry) {
		this.geometry = geometry;
	}
	
}
