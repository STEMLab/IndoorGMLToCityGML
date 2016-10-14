package net.opengis.indoorgml.v_1_0.vo.spatial;

import org.opengis.geometry.primitive.Point;

public class VOPoint extends VOGeometry {
	private Point geometry;

	/**
	 * @return the geometry
	 */
	public Point getGeometry() {
		return geometry;
	}

	/**
	 * @param geometry the geometry to set
	 */
	public void setGeometry(Point point) {
		this.geometry = point;
	}
}
