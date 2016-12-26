package com.dds.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.dds.vo.User;

@Repository
@Configurable(autowire=Autowire.BY_NAME)
public class MainDBDAO {
	@Autowired
	private JdbcTemplate mainJDBCTemplate = null;
	
	public User findUser(String userName){
		String sql = "select id, username, dbname from users where userName=?";
		final List<User> list = new ArrayList<User>();
		mainJDBCTemplate.query(sql,new String[]{userName}, new RowCallbackHandler(){
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setUserName(rs.getString("username"));
				user.setDbName(rs.getString("dbname"));
				list.add(user);
			}
		});
		return list.size() > 0 ? list.get(0) : null;
	}
	public int insertUser(final User user){
		final String sql = "insert into users (username, dbname) values(?,?)";
		KeyHolder kh = new GeneratedKeyHolder();
		mainJDBCTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
				ps.setString(1, user.getUserName());
				ps.setString(2, user.getDbName());
				return ps;
			}
		}, kh);
		int key = kh.getKey().intValue();
		user.setId(key);
		return key;
	}

}
