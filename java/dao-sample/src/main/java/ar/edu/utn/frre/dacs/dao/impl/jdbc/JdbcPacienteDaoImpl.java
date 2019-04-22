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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import ar.edu.utn.frre.dacs.dao.PacienteDao;
import ar.edu.utn.frre.dacs.dao.request.DefaultPageImpl;
import ar.edu.utn.frre.dacs.dao.request.Page;
import ar.edu.utn.frre.dacs.dao.request.SearchCriteria;
import ar.edu.utn.frre.dacs.dao.request.SearchRange;
import ar.edu.utn.frre.dacs.dao.request.criteria.SearchCriteriaVisitor;
import ar.edu.utn.frre.dacs.dao.request.order.SearchOrder;
import ar.edu.utn.frre.dacs.model.Paciente;

/**
 * 
 * 
 * @author Dr. Jorge Villaverde
 * @version 1.0
 */
public class JdbcPacienteDaoImpl implements PacienteDao {

	private static final Logger logger = LoggerFactory.getLogger(JdbcPacienteDaoImpl.class);

	private static final String PACIENTE_SELECT_CLAUSE = "SELECT * FROM public.paciente";

	@Autowired
    private JdbcTemplate jdbcTemplate;
    
	private SearchCriteriaVisitor<Paciente> searchCriteriaVisitor = 
			new JdbcSearchCriteriaVisitor<Paciente>();
	/* (non-Javadoc)
	 * @see ar.edu.utn.frre.dacs.dao.DataAccessObject#findPage(ar.edu.utn.frre.dacs.dao.request.criteria.SearchCriteria, ar.edu.utn.frre.dacs.dao.request.SearchOrder, ar.edu.utn.frre.dacs.dao.request.SearchRange)
	 */
	@Override
	public Page<Paciente> findPage(SearchCriteria<Paciente> criteria, SearchOrder<Paciente> order,
			SearchRange<Paciente> range) {
		
		
		Map<String, Object> context = new HashMap<String, Object>();
		
		String query = PACIENTE_SELECT_CLAUSE;
		String where = (String) searchCriteriaVisitor.visit(context , criteria);
		
		if (where != null) {
			query += where;
		}
		
		logger.info("Query: " + query);
		
		List<Paciente> pacientes = jdbcTemplate.query(query, new PacienteRowMapper());
		
		return new DefaultPageImpl<Paciente>(range, pacientes);
	}

	/* (non-Javadoc)
	 * @see ar.edu.utn.frre.dacs.dao.DataAccessObject#findById(java.io.Serializable)
	 */
	@Override
	public Paciente findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
