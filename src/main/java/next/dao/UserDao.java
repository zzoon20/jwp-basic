package next.dao;

import java.util.List;

import next.model.User;

public class UserDao {
	public void insert(User user) {
		JdbcTemplate template = new JdbcTemplate() {
		};
		template.update("INSERT INTO USERS VALUES (?, ?, ?, ?)", user.getUserId(), user.getPassword(), user.getName(),
				user.getEmail());
	}

	public void update(User user) {
		JdbcTemplate template = new JdbcTemplate() {
		};
		template.update("UPDATE USERS SET password = ?, name = ?, email = ? WHERE userId = ? ", user.getPassword(),
				user.getName(), user.getEmail(), user.getUserId());
	}

	public List<User> findAll() {
		JdbcTemplate template = new JdbcTemplate() {
		};

		return template.query("SELECT userId, password, name, email FROM USERS", rs -> new User(rs.getString("userId"),
				rs.getString("password"), rs.getString("name"), rs.getString("email")));
	}

	public User findByUserId(String userId) {
		JdbcTemplate template = new JdbcTemplate() {
		};

		return template.queryForObject("SELECT userId, password, name, email FROM USERS WHERE userid=?",
				rs -> new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
						rs.getString("email")),
				userId);
	}
}
