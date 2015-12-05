package org.devolunteers.cfg2016.backend.services;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.devolunteers.cfg2016.backend.domain.Call;
import org.joda.time.DateTime;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class DBService {
	
	private NamedParameterJdbcTemplate jdbcTemplateObject;

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplateObject = new NamedParameterJdbcTemplate(dataSource);
	}

	public void logCall(Call call) {
		String query = "INSERT INTO Calls (from_number, to_number, datetime, from_city, from_state, from_country) "
					 + "VALUES (:from_number, :to_number, :datetime, :from_city, :from_state, :from_country)";
		
		Map<String, String> namedParameters = new HashMap<String, String>();
		namedParameters.put("from_number", call.getFromNumber());
		namedParameters.put("to_number", call.getToNumber());
		namedParameters.put("from_city", call.getFromCity());
		namedParameters.put("from_state", call.getFromState());
		namedParameters.put("from_country", call.getFromCountry());
		namedParameters.put("datetime", DateTime.now().toString());

		jdbcTemplateObject.update(query, namedParameters);
	}

	public void storeReport(String latitude, String longitude, String disability,
			String country, String actionRequired, String comments) {
		
		String query = String.format("INSERT INTO Reports (latitude, longitude, disability, country, action_required, comments) "
				+ "VALUES ('%s', '%s', '%s', '%s', '%s', '%s')",
				 latitude,  longitude,  disability,
				 country,  actionRequired,  comments);
		jdbcTemplateObject. update(query, new HashMap<String, String>());
	}
}
