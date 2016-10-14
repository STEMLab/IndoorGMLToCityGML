package edu.pnu.importexport;

import java.io.File;
import java.util.Locale;
import java.util.Properties;

import org.apache.ibatis.io.Resources;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import edu.pnu.test.TestSupport;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RealDataTest {
	
	@Before
	public void setUp() throws Exception {
		Locale.setDefault(Locale.ENGLISH);
	}
	
	@Test
	public void insertLotteTest() throws Exception {
		/*
		IndoorGMLKairosImporter importer = new IndoorGMLKairosImporter();
		Properties props = TestSupport.getDefaultProperties();
		IndoorGMLKairosManager manager = new IndoorGMLKairosManager();
		manager.deleteSchema(props);
		manager.createSchema(props);
		
		//String resource = "example/navi_navigableSpace.gml";
		String resource = "example/navi.gml";
		//String resource = "example/lotteIndoorGML.gml";
		File file = Resources.getResourceAsFile(resource);
		importer.importIndoorGML(props, "1", file.getAbsolutePath());
		*/
	}
	
	@Test
	public void selectNaviTest() throws Exception {
		/*
		IndoorGMLKairosExporter exporter = new IndoorGMLKairosExporter();
		Properties props = TestSupport.getDefaultProperties();
		exporter.exportIndoorGMLNavi(props, "1", "result_navi.gml");
		*/
	}
	
	@Test
	public void selectLotteTest() throws Exception {
		/*
		IndoorGMLKairosExporter exporter = new IndoorGMLKairosExporter();
		Properties props = TestSupport.getDefaultProperties();
		exporter.exportIndoorGMLCore(props, "1", "result.gml");
		*/
	}
}
