/**
 * 
 */
package net.opengis.indoorgml.v_1_0.vo.spatial;

import org.opengis.geometry.aggregate.MultiCurve;

/**
 * @author hgryoo
 *
 */
public class VOMultiCurve extends VOGeometry {
	private MultiCurve geometry;
	
	/**
	 * @return the geometry
	 */
	public MultiCurve getGeometry() {
		return geometry;
	}
	
	/**
	 * @param geometry the geometry to set
	 */
	public void setGeometry(MultiCurve geometry) {
		this.geometry = geometry;
	}	
	
}
