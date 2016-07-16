package core.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class JdbcTemplate {
	public void update(String sql, PreparedStatementSetter pstmtSetter) throws DataAccessException {
		try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmtSetter.setValues(pstmt);
			pstmt.execute();
		} catch (SQLException e) {
			throw new DataAccessException();
		}
	}
	
	public void update(String sql, Object...parameters){
		update(sql, createPreparedStatementSetter(parameters));
	}

	public <T> List<T> query(String sql, RowMapper<T> rowMapper, PreparedStatementSetter pstmtSetter)
			throws DataAccessException {
		ResultSet rs = null;

		try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmtSetter.setValues(pstmt);
			rs = pstmt.executeQuery();

			List<T> list = new ArrayList<T>();
			while (rs.next()) {
				list.add(rowMapper.mapRow(rs));
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

	public <T> T queryForObject(String sql, RowMapper<T> rm, Object... parameters) {
		return queryForObject(sql, rm, createPreparedStatementSetter(parameters));
	}
	
	public <T> T queryForObject(String sql, RowMapper<T> rowMapper, PreparedStatementSetter pstmtSetter)
			throws DataAccessException {
		List<T> list = query(sql, rowMapper, pstmtSetter);
		if(list.isEmpty()){
			return null;
		}
		return list.get(0);
	}

	public <T> List<T> query(String sql, RowMapper<T> rowMapper, Object... parameters) {
		return query(sql, rowMapper, createPreparedStatementSetter(parameters));
	}

	private PreparedStatementSetter createPreparedStatementSetter(Object... parameters) {
		return new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement pstmt) throws SQLException {
				for (int i = 0; i < parameters.length; i++) {
					pstmt.setObject(i + 1, parameters[i]);
				}
			}
		};
	}
}
