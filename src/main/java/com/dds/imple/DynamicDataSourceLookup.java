package com.dds.imple;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.lookup.DataSourceLookup;
import org.springframework.jdbc.datasource.lookup.DataSourceLookupFailureException;

public class DynamicDataSourceLookup implements DataSourceLookup {
	private JdbcTemplate jdbcTemplate = null;
	private IDataSourceFactory dataSourceFactory = null;
	@Override
	public DataSource getDataSource(String dataSourceName) throws DataSourceLookupFailureException {
		IDataSourceFactory dsf = getDataSourceFactory();
		try {
			DataSource ds = dsf.createDataSource(dataSourceName);
			boolean exist = false;
			try{
				ds.getConnection();
				exist = true;
			}catch(Exception e){
				exist =false;
			}
			if(!exist){
				dsf.createDataDB(jdbcTemplate.getDataSource(), dataSourceName);
				dsf.createDBTables(ds); 
			}
			return ds;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public IDataSourceFactory getDataSourceFactory() {
		return dataSourceFactory;
	}
	public void setDataSourceFactory(IDataSourceFactory dataSourceFactory) {
		this.dataSourceFactory = dataSourceFactory;
	}

}
