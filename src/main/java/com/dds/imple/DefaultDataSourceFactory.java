package com.dds.imple;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp.BasicDataSourceFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.dds.utils.AppUtils;
import com.dds.utils.ParameterizationString;

public class DefaultDataSourceFactory implements IDataSourceFactory {
	private Properties dbProps = null;
	private String sqlFileDir = null;


	public void setDbProps(Properties dbProps) {
		this.dbProps = dbProps;
	}
	public void setSqlFileDir(String sqlFileDir) {
		this.sqlFileDir = sqlFileDir;
	}
	@Override
	public DataSource createDataSource(String dbName) throws Exception {
		Properties theProps = new Properties(dbProps);
		String url = theProps.getProperty("url");
		Map<String, String> map = new HashMap<String, String>();
		map.put("dbName", dbName);
		url = new ParameterizationString(url).getRealString(map); 
		theProps.put("url", url);
		return BasicDataSourceFactory.createDataSource(theProps);
	}

	@Override
	public void createDataDB(DataSource ds, String dbName) throws Exception {
		try{
		JdbcTemplate template = new JdbcTemplate(ds);
		String sql = "create database "+dbName;
		template.execute(sql);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void createDBTables(DataSource ds) throws Exception {
		File rootFile = AppUtils.getInstances().getRootFile();
		File sqlDir = new File(rootFile, sqlFileDir);
		File[] files = sqlDir.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return pathname.getName().toLowerCase().endsWith(".sql");
			}
		});
		int count = files == null? 0 : files.length;
		JdbcTemplate template = new JdbcTemplate(ds);
		for (int i = 0; i < count; i++) {
			executSqlFile(template, files[i]);
		}
	}
	private void executSqlFile(JdbcTemplate template, File file) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				line = line.trim();
				sb.append(line);
				if(line.endsWith(";")){
					String sql = sb.toString();
					sb.setLength(0);
					template.execute(sql);
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(reader != null){
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}



	
}
