package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.jdbc.ConnectionManager;
import next.model.User;

public class UserDao {
	public void insert(User user) throws SQLException {
		JdbcTemplate template = new JdbcTemplate() {

			@Override
			public void setValues(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, user.getUserId());
				pstmt.setString(2, user.getPassword());
				pstmt.setString(3, user.getName());
				pstmt.setString(4, user.getEmail());
			}
		};
		template.update("INSERT INTO USERS VALUES (?, ?, ?, ?)");
	}

	public void update(User user) throws SQLException {
		JdbcTemplate template = new JdbcTemplate() {

			@Override
			public void setValues(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, user.getPassword());
				pstmt.setString(2, user.getName());
				pstmt.setString(3, user.getEmail());
				pstmt.setString(4, user.getUserId());
			}
		};
		template.update("UPDATE USERS SET password = ?, name = ?, email = ? WHERE userId = ? ");
	}

	public List<User> findAll() throws SQLException {
		SelectJdbcTemplate template = new SelectJdbcTemplate() {
			
			@Override
			public void setValues(PreparedStatement pstmt) throws SQLException {
			}
			
			@Override
			public Object mapRow(ResultSet rs) throws SQLException {
				return new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
						rs.getString("email"));
			}
		};
		
		return (List<User>) template.query("SELECT userId, password, name, email FROM USERS");
	}

	public User findByUserId(String userId) throws SQLException {
		SelectJdbcTemplate template = new SelectJdbcTemplate() {
			
			@Override
			public void setValues(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, userId);
			}
			
			@Override
			public Object mapRow(ResultSet rs) throws SQLException {
				return new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
						rs.getString("email"));
			}
		};
		
		return (User) template.queryForObject("SELECT userId, password, name, email FROM USERS WHERE userid=?");
	}}
