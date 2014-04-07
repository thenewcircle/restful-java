package com.example.util.dao;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.sql.DataSource;

public class DriverManagerDataSource implements DataSource {

	private PrintWriter logger = new PrintWriter(System.err);

	private String jdbcUrl;
	private String username;
	private String password;

	public DriverManagerDataSource() {
	}

	public DriverManagerDataSource(String jdbcUrl, String username,
			String password) {
		this.jdbcUrl = jdbcUrl;
		this.username = username;
		this.password = password;
	}

	@Override
	public PrintWriter getLogWriter() throws SQLException {
		return logger;
	}

	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {
		this.logger = out;
	}

	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
	}

	@Override
	public int getLoginTimeout() throws SQLException {
		return 0;
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		return null;
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return false;
	}

	@Override
	public Connection getConnection() throws SQLException {
		return getConnection(username, password);
	}

	@Override
	public Connection getConnection(String username, String password)
			throws SQLException {
		Connection c = DriverManager.getConnection(jdbcUrl, username, password);
		return c;
	}

	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		throw new SQLFeatureNotSupportedException();
	}

	public final void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
	}

	public final void setUsername(String username) {
		this.username = username;
	}

	public final void setPassword(String password) {
		this.password = password;
	}

}
