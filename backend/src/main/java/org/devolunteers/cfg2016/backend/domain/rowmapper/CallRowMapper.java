package org.devolunteers.cfg2016.backend.domain.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.devolunteers.cfg2016.backend.domain.Call;
import org.joda.time.DateTime;
import org.springframework.jdbc.core.RowMapper;

public class CallRowMapper implements RowMapper<Call> {

	public Call mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Call call = new Call();
		call.setFromNumber(rs.getString("from_number"));
		call.setToNumber(rs.getString("to_number"));
		call.setDatetime(new DateTime(rs.getString("datetime")));
		
		return call;
	}
}
