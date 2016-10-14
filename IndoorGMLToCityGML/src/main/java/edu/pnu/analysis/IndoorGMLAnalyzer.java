package edu.pnu.analysis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.opengis.indoorgml.v_1_0.vo.core.CellSpace;
import net.opengis.indoorgml.v_1_0.vo.core.CellSpaceBoundary;
import net.opengis.indoorgml.v_1_0.vo.core.IndoorFeatures;
import net.opengis.indoorgml.v_1_0.vo.core.PrimalSpaceFeatures;
import net.opengis.indoorgml.v_1_0.vo.core.State;
import net.opengis.indoorgml.v_1_0.vo.core.Transition;

import org.geotools.factory.GeoTools;
import org.geotools.factory.Hints;
import org.geotools.geometry.GeometryBuilder;
import org.geotools.geometry.iso.primitive.PrimitiveFactoryImpl;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.opengis.geometry.Envelope;
import org.opengis.geometry.primitive.OrientableSurface;
import org.opengis.geometry.primitive.Primitive;
import org.opengis.geometry.primitive.Shell;
import org.opengis.geometry.primitive.Solid;
import org.opengis.geometry.primitive.SolidBoundary;
import org.opengis.geometry.primitive.Surface;
import org.opengis.geometry.primitive.SurfaceBoundary;


public class IndoorGMLAnalyzer {
	private IndoorFeatures indoorFeatures;
	
	private List<Double> floorZOrdinates;
	private Map<Double, List<CellSpace>> floorCellSpaceMap;

	private Hints hints = null;
	private GeometryBuilder builder = null;
	private PrimitiveFactoryImpl pf = null;

	public IndoorGMLAnalyzer(IndoorFeatures indoorFeatures) {
		this.indoorFeatures = indoorFeatures;
		
		hints = GeoTools.getDefaultHints();
        hints.put(Hints.CRS, DefaultGeographicCRS.WGS84_3D);
        hints.put(Hints.GEOMETRY_VALIDATE, false);
        builder = new GeometryBuilder(hints);
        pf = (PrimitiveFactoryImpl) builder.getPrimitiveFactory();
	}
	
	public void classifyCellByFloor() {
		PrimalSpaceFeatures psf = indoorFeatures.getPrimalSpaceFeatures();
		ArrayList<CellSpace> cellSpaceMember = psf.getCellSpace();
		
		floorZOrdinates = new ArrayList<Double>();
		floorCellSpaceMap = new HashMap<Double, List<CellSpace>>();
		for (CellSpace cellSpace : cellSpaceMember) {
			Solid solid = cellSpace.getGeometry3D().getGeometry();
			Envelope envelope = solid.getEnvelope();
			double minZ = envelope.getMinimum(2);
			
			Double floorZ = isContains(floorZOrdinates, minZ);
			if (floorZ == null) {
				Double doubleZ = Double.valueOf(minZ);
				floorZOrdinates.add(doubleZ);
				floorCellSpaceMap.put(doubleZ, new ArrayList<CellSpace>());
				
				floorZ = doubleZ;
			}
			
			List<CellSpace> floorCellSpace = floorCellSpaceMap.get(floorZ);
			floorCellSpace.add(cellSpace);
		}
		Collections.sort(floorZOrdinates);
	}
	
	public Double isContains(List<Double> values, double z) {		
		for (Double value : values) {
			if (value.doubleValue() == z) {
				return value;
			}
		}
		
		return null;
	}
	
	public void analyzeStep1() {
		AnalyzeStep1 step1 = new AnalyzeStep1(indoorFeatures, floorZOrdinates, floorCellSpaceMap, pf);
		step1.analyzeStep1();
	}
}
