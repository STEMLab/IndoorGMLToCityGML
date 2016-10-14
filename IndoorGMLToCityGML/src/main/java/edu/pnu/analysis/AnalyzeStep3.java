package edu.pnu.analysis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.opengis.indoorgml.v_1_0.vo.core.CellSpace;
import net.opengis.indoorgml.v_1_0.vo.core.IndoorFeatures;
import net.opengis.indoorgml.v_1_0.vo.core.State;
import net.opengis.indoorgml.v_1_0.vo.core.Transition;
import net.opengis.indoorgml.v_1_0.vo.spatial.VOCurve;

import org.geotools.geometry.iso.primitive.PrimitiveFactoryImpl;
import org.opengis.geometry.DirectPosition;
import org.opengis.geometry.primitive.Curve;

public class AnalyzeStep3 {
	private IndoorFeatures indoorFeatures;

	private List<Double> floorZOrdinates;
	private Map<Double, List<CellSpace>> floorCellSpaceMap;

	private PrimitiveFactoryImpl pf = null;

	public AnalyzeStep3(IndoorFeatures indoorFeatures, List<Double> floorZOrdinates, Map<Double, List<CellSpace>> floorCellSpaceMap,
			PrimitiveFactoryImpl pf) {
		this.indoorFeatures = indoorFeatures;
		this.floorZOrdinates = floorZOrdinates;
		this.floorCellSpaceMap = floorCellSpaceMap;
		this.pf = pf;
	}

	public void analyzeStep3() {		
		for (Double z : floorZOrdinates) {
			List<CellSpace> floorCellSpace = floorCellSpaceMap.get(z);

			for (CellSpace cellSpace : floorCellSpace) {
				findCellSpaceSemantic(cellSpace);
			}
		}
	}
	
	public boolean findCellSpaceSemantic(CellSpace cellSpace) {
		State stateDuality = cellSpace.getDuality();
		if (stateDuality == null) return false;
		
		ArrayList<Transition> transitions = stateDuality.getConnects();
		if (transitions == null || transitions.size() == 0) {
			cellSpace.setEstimatedType("WALL");
		} else {
			boolean isStair = false;
			for (Transition transition : transitions) {
				VOCurve voCurve = transition.getGeometry();
				Curve curve = voCurve.getGeometry();
				DirectPosition startPoint = curve.getStartPoint();
				DirectPosition endPoint = curve.getEndPoint();
				
				if (startPoint.getOrdinate(2) != endPoint.getOrdinate(2)) {
					isStair = true;
					break;
				}
			}
			
			if (isStair) {
				cellSpace.setEstimatedType("STAIR");
			} else {
				
			}
		}
		
		return true;
	}
}
