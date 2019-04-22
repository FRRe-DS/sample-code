/*
 * Copyright (C) 2015-2019 UTN-FRRe
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ar.edu.utn.frre.dacs.dao.impl.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ar.edu.utn.frre.dacs.model.Paciente;

/**
 * 
 * 
 * @author Dr. Jorge Villaverde
 * @version 1.0
 */
public class PacienteRowMapper implements RowMapper<Paciente> {

	@Override
	public Paciente mapRow(ResultSet rs, int rowNum) throws SQLException {
		final Paciente paciente = new Paciente();

		paciente.setId(rs.getLong("id"));
		paciente.setNombre(rs.getString("nombre"));
		paciente.setApellido(rs.getString("apellido"));
		paciente.setDni(rs.getLong("dni"));
		paciente.setFechaNacimiento(rs.getDate("fecha_nacimiento"));

		return paciente;
	}

}
