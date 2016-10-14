package net.opengis.indoorgml.v_1_0.vo.spatial;

import org.opengis.geometry.coordinate.Polygon;
import org.opengis.geometry.primitive.OrientableSurface;
import org.opengis.geometry.primitive.Surface;

public class VOSurface extends VOGeometry {
	private OrientableSurface geometry;
	private Polygon polygonGeometry;
	
	private boolean isReverse;
	private boolean isComposite;
	private boolean isTriangulated;
	
	/**
	 * @return the isReverse
	 */
	public boolean isReverse() {
		return isReverse;
	}
	/**
	 * @param isReverse the isReverse to set
	 */
	public void setReverse(Boolean isReverse) {
		this.isReverse = isReverse;
	}
	/**
	 * @return the isComposite
	 */
	public boolean isComposite() {
		return isComposite;
	}
	/**
	 * @param isComposite the isComposite to set
	 */
	public void setComposite(Boolean isComposite) {
		this.isComposite = isComposite;
	}
	/**
	 * @return the isTriangulated
	 */
	public boolean isTriangulated() {
		return isTriangulated;
	}
	/**
	 * @param isTriangulated the isTriangulated to set
	 */
	public void setTriangulated(Boolean isTriangulated) {
		this.isTriangulated = isTriangulated;
	}
	/**
	 * @return the geometry
	 */
	public OrientableSurface getGeometry() {
		return geometry;
	}
	/**
	 * @param geometry the geometry to set
	 */
	public void setGeometry(OrientableSurface geometry) {
		this.geometry = geometry;
	}
	/**
	 * @return the polygonGeometry
	 */
	public Polygon getPolygonGeometry() {
		return polygonGeometry;
	}
	/**
	 * @param polygonGeometry the polygonGeometry to set
	 */
	public void setPolygonGeometry(Polygon polygonGeometry) {
		this.polygonGeometry = polygonGeometry;
	}
	
}
