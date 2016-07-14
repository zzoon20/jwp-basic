package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import core.jdbc.ConnectionManager;
import next.model.User;

public abstract class UpdateJdbcTemplate {
	public void update(User user) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ConnectionManager.getConnection();
			String sql = createQueryForUpdate();
			pstmt = con.prepareStatement(sql);
			setValuesForUpdate(user, pstmt);

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
	
	public abstract void setValuesForUpdate(User user, PreparedStatement pstmt) throws SQLException;

	public abstract String createQueryForUpdate();
}
