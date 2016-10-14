package edu.pnu.test;

import java.util.Properties;

public class TestSupport {
	
	public static Properties getDefaultProperties() {
		Properties props = new Properties();
		props.put("driver", "kr.co.realtimetech.kairos.jdbc.kairosDriver");
		props.put("url", "jdbc:kairos://localhost:5000/test");
		props.put("username", "root");
		props.put("password", "root");
		return props;
	}
}
