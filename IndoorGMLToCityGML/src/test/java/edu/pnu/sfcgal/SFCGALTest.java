package edu.pnu.sfcgal;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.geotools.factory.GeoTools;
import org.geotools.factory.Hints;
import org.geotools.geometry.GeometryBuilder;
import org.geotools.geometry.iso.primitive.PrimitiveFactoryImpl;
import org.geotools.geometry.iso.sfcgal.util.Geometry3DOperationTest;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.junit.Before;
import org.junit.Test;
import org.opengis.geometry.DirectPosition;
import org.opengis.geometry.TransfiniteSet;
import org.opengis.geometry.primitive.Solid;
import org.opengis.geometry.primitive.Surface;

public class SFCGALTest {
	private Hints hints = null;
	private GeometryBuilder builder = null;
	private PrimitiveFactoryImpl pf = null;
	
	@Before
	public void setUp() throws Exception {
		Locale.setDefault(Locale.ENGLISH);
		
		hints = GeoTools.getDefaultHints();
	    hints.put(Hints.CRS, DefaultGeographicCRS.WGS84_3D);
	    hints.put(Hints.GEOMETRY_VALIDATE, false);
	    builder = new GeometryBuilder(hints);
	    pf = (PrimitiveFactoryImpl) builder.getPrimitiveFactory();
	}

	@Test
	public void intersectTest() {
		List<DirectPosition> positions1 = new ArrayList<DirectPosition>();
		
		DirectPosition p11 = builder.createDirectPosition(new double[]{0, 0, 0});
		DirectPosition p12 = builder.createDirectPosition(new double[]{5, 0, 0});
		DirectPosition p13 = builder.createDirectPosition(new double[]{5, -5, 0});
		DirectPosition p14 = builder.createDirectPosition(new double[]{0, -5, 0});
		
		positions1.add(p11);
		positions1.add(p12);
		positions1.add(p13);
		positions1.add(p14);
		positions1.add(p11);
		
		Surface surface1 = pf.createSurfaceByDirectPositions(positions1);
		

		List<DirectPosition> positions2 = new ArrayList<DirectPosition>();
		
		DirectPosition p21 = builder.createDirectPosition(new double[]{0, 0, 0});
		DirectPosition p22 = builder.createDirectPosition(new double[]{0, 5, 0});
		DirectPosition p23 = builder.createDirectPosition(new double[]{5, 5, 0});
		DirectPosition p24 = builder.createDirectPosition(new double[]{5, 0, 0});
		
		positions2.add(p21);
		positions2.add(p22);
		positions2.add(p23);
		positions2.add(p24);
		positions2.add(p21);
		
		Surface surface2 = pf.createSurfaceByDirectPositions(positions2);
		
		TransfiniteSet intersection = surface1.intersection(surface2);
		boolean isIntersects = surface1.intersects(surface2);
		
		System.out.println(intersection.toString());
		System.out.println("isIntersects : "  + isIntersects);
		
		Solid solid1 = Geometry3DOperationTest.getSolids(builder).get(0);
		Solid solid2 = Geometry3DOperationTest.getSolids(builder).get(1);
		System.out.println(solid1.distance(solid2));
		System.out.println(solid1.toString());
		System.out.println(solid2.toString());
		
		TransfiniteSet intersection2 = solid1.intersection(surface1);
		System.out.println(intersection2.toString());
		System.out.println(surface1.toString());
	}

}
