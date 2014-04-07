package com.example.util.dao;


public class HsqlDataSource extends DriverManagerDataSource {

	private static final String JDBC_URL = "jdbc:hsqldb:mem;shutdown=true";
	private static final String USERNAME = "SA";
	private static final String PASSWORD = "";

	public HsqlDataSource() {
		super(JDBC_URL, USERNAME, PASSWORD);
	}

}
