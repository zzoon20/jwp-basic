package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import core.jdbc.ConnectionManager;
import next.model.User;

public class UpdateJdbcTemplate {
	public void update(User user, UserDao userDao) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ConnectionManager.getConnection();
			String sql = userDao.createQueryForUpdate();
			pstmt = con.prepareStatement(sql);
			userDao.setValuesForUpdate(user, pstmt);

			pstmt.execute();
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}

			if (con != null) {
				con.close();
			}
		}
	}
}
