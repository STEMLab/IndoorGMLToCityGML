package edu.pnu.importexport;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.JAXBIntrospector;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.helpers.DefaultValidationEventHandler;

import net.opengis.gml.v_3_2_1.AbstractGMLType;
import net.opengis.indoorgml.core.v_1_0.IndoorFeaturesType;
import net.opengis.indoorgml.navigation.v_1_0.RouteType;
import net.opengis.indoorgml.v_1_0.vo.core.IndoorFeatures;
import net.opengis.indoorgml.v_1_0.vo.navigation.Route;
import edu.pnu.analysis.IndoorGMLAnalyzer;
import edu.pnu.common.SymbolListener;
import edu.pnu.common.XLinkSymbolMap;
import edu.pnu.importexport.store.IndoorGMLCoreVOConvertUtil;
import edu.pnu.importexport.store.IndoorGMLNaviVOConvertUtil;
import edu.pnu.importexport.store.JIndoorGMLCoreTraversalUtil;
import edu.pnu.importexport.store.JIndoorGMLNaviTraversalUtil;

public class IndoorGMLKairosImporter {

	private XLinkSymbolMap mXLinkSymbolMap;
	
	public void importIndoorGML(Properties props, String id, String filePath) throws Exception {
		
		if(id == null || id == "") {
			throw new IllegalArgumentException(id);
		}
		
		Object unmarshalResult = unmarshalIndoorGML(filePath);
		IndoorFeaturesType indoorFeatureType = null;
		RouteType routeType = null;
		if(unmarshalResult instanceof IndoorFeaturesType)
			indoorFeatureType = (IndoorFeaturesType) unmarshalResult;
		else if(unmarshalResult instanceof RouteType)
			routeType = (RouteType) unmarshalResult;
		
		XLinkSymbolMap symbolMap = mXLinkSymbolMap;
		BindingNode node = new BindingNode();
		if(indoorFeatureType != null){
			node = JIndoorGMLCoreTraversalUtil.convertIndoorFeatureType(indoorFeatureType, node, symbolMap);
			IndoorFeatures inputIndoorFeature = IndoorGMLCoreVOConvertUtil.createIndoorFeature(node, new HashMap<String, Object>());
			System.out.println("Parsing end");
			
			IndoorGMLAnalyzer analyzer = new IndoorGMLAnalyzer(inputIndoorFeature);
			analyzer.classifyCellByFloor();
			analyzer.analyzeStep1();
			System.out.println("Step1 end");
		}
		else if(routeType != null){
			node = JIndoorGMLNaviTraversalUtil.convertRouteType(routeType, node, symbolMap);
			Route route = IndoorGMLNaviVOConvertUtil.createRoute(node, new HashMap<String, Object>());
			route.stringID = id;
			
		}
	}
	
	public Object unmarshalIndoorGML(String path) throws JAXBException, IOException {
		
		JAXBContext context;
		Unmarshaller unmarshaller;
		SymbolListener listener;
		
		context = JAXBContext.newInstance("net.opengis.indoorgml.core.v_1_0:net.opengis.indoorgml.navigation.v_1_0:net.opengis.gml.v_3_2_1");
		unmarshaller = context.createUnmarshaller();
		listener = new SymbolListener(AbstractGMLType.class);
		
		unmarshaller.setListener(listener);
		
		unmarshaller.setEventHandler( new DefaultValidationEventHandler() );
		
		File input = new File(path);
		Object unmarshalResult = JAXBIntrospector.getValue(unmarshaller.unmarshal(input));
		
		this.mXLinkSymbolMap = listener.getSymbolMap();
		
		return unmarshalResult;
	}
}
