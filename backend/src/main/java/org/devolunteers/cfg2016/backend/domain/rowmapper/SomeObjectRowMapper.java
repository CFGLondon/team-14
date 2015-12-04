package org.devolunteers.cfg2016.backend.domain.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.devolunteers.cfg2016.backend.domain.SomeObject;
import org.springframework.jdbc.core.RowMapper;

public class SomeObjectRowMapper implements RowMapper<SomeObject> {

	public SomeObject mapRow(ResultSet rs, int rowNum) throws SQLException {
		SomeObject someObject = new SomeObject();
		someObject.someProperty = rs.getString("someProperty");
		
		return someObject;
	}


}
