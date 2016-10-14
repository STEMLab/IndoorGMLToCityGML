package edu.pnu.importexport;

import java.io.File;
import java.util.Locale;
import java.util.Properties;

import org.apache.ibatis.io.Resources;
import org.junit.Before;
import org.junit.Test;

import edu.pnu.test.TestSupport;

public class MockUpTest {
	@Before
	public void setUp() throws Exception {
		Locale.setDefault(Locale.ENGLISH);
	}
	
	@Test
	public void CoreMockUpsTest() throws Exception {
		IndoorGMLKairosImporter importer = new IndoorGMLKairosImporter();
		//IndoorGMLKairosExporter exporter = new IndoorGMLKairosExporter();
		Properties props = TestSupport.getDefaultProperties();
		//IndoorGMLKairosManager manager = new IndoorGMLKairosManager();
		//manager.deleteSchema(props);
		//manager.createSchema(props);
		
		String coreResource = "example/SAMPLE_DATA_AVENUEL1F2F_3D.gml";
		//String coreResource = "test/indoorgml_core_mockup.gml";
		
		File coreFile = Resources.getResourceAsFile(coreResource);
		importer.importIndoorGML(props, "Core", coreFile.getAbsolutePath());
		//exporter.exportIndoorGMLCore(props, "Core", "result_core.gml");
	}
	
	//@Test
	public void NaviMockUpsTest() throws Exception {
		IndoorGMLKairosImporter importer = new IndoorGMLKairosImporter();
		
		//IndoorGMLKairosExporter exporter = new IndoorGMLKairosExporter();
		Properties props = TestSupport.getDefaultProperties();
		//IndoorGMLKairosManager manager = new IndoorGMLKairosManager();
		//manager.deleteSchema(props);
		//manager.createSchema(props);
		
		String naviResource = "test/indoorgml_navi_mockup.gml";
		
		File naviFile = Resources.getResourceAsFile(naviResource);
		importer.importIndoorGML(props, "Navi", naviFile.getAbsolutePath());
		//exporter.exportIndoorGMLNavi(props, "Navi", "result_navi.gml");
	}
}
