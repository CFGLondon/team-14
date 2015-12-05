package org.devolunteers.cfg2016.backend.domain.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.devolunteers.cfg2016.backend.domain.Report;
import org.springframework.jdbc.core.RowMapper;

public class ReportRowMapper implements RowMapper<Report> {

	public Report mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Report rep = new Report();
		rep.setLatitude(rs.getString("latitude"));
		rep.setLongitude(rs.getString("longitude"));
		rep.setDisability(rs.getString("country"));
		rep.setActionRecquired(rs.getString("disability"));
		rep.setCountry(rs.getString("action_required"));
		rep.setComments(rs.getString("comments"));
		
		return rep;
	}
}
