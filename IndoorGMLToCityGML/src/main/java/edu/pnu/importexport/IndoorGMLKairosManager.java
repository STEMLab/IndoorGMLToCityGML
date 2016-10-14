package edu.pnu.importexport;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import edu.pnu.mybatis.ConnectionFactory;

public class IndoorGMLKairosManager {
	
	public void executeSQL(Properties props, String sqlPath) throws Exception {
		InputStream stream = Resources.getResourceAsStream(sqlPath);
		java.util.Scanner scanner = new java.util.Scanner(stream, "UTF-8");
		scanner.useDelimiter(";");

		SqlSessionFactory ssf = ConnectionFactory.getInstance(props);
		SqlSession session = ssf.openSession();
		
		Connection conn = session.getConnection();
		conn.setAutoCommit(true);
		while(scanner.hasNext()) {
			Statement s = conn.createStatement();
			String sql = scanner.next();
			try {
				s.executeUpdate(sql + ";");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
			s.close();
		}
		scanner.close();
		conn.close();
		session.close();
	}
	
	public void deleteSchema(Properties props) throws Exception {
		executeSQL(props, "sql/drop_table.sql");
	}
	
	public void createSchema(Properties props) throws Exception {
		executeSQL(props, "sql/create_schema.sql");
	}
}
