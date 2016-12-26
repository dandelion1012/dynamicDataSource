package com.dds.imple;

import javax.sql.DataSource;

public interface IDataSourceFactory {
	public DataSource createDataSource(String dbName) throws Exception;
	public void createDataDB(DataSource ds , String dbName) throws Exception;
	public void createDBTables(DataSource ds) throws Exception;
}
