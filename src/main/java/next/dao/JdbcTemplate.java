package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.jdbc.ConnectionManager;

public abstract class JdbcTemplate {
	public void update(String sql, PreparedStatementSetter pstmtSetter) throws DataAccessException {
		try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmtSetter.setValues(pstmt);
			pstmt.execute();
		} catch (SQLException e) {
			throw new DataAccessException();
		}
	}

	public List<?> query(String sql, RowMapper rowMapper) throws DataAccessException {
		ResultSet rs = null;

		try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
			rs = pstmt.executeQuery();

			ArrayList<Object> list = new ArrayList<>();
			while (rs.next()) {
				Object obj = rowMapper.mapRow(rs);
				list.add(obj);
			}
			return list;

		} catch (SQLException e) {
			throw new DataAccessException();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					throw new DataAccessException();
				}
			}
		}
	}

	public Object queryForObject(String sql, PreparedStatementSetter pstmtSetter, RowMapper rowMapper)
			throws DataAccessException {
		ResultSet rs = null;

		try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmtSetter.setValues(pstmt);
			rs = pstmt.executeQuery();

			Object obj = null;
			if (rs.next()) {
				obj = rowMapper.mapRow(rs);
			}

			return obj;
		} catch (SQLException e) {
			throw new DataAccessException();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					throw new DataAccessException();
				}
			}
		}
	}
}
