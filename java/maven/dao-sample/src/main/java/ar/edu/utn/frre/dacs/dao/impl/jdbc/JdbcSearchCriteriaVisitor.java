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

import java.util.Map;

import ar.edu.utn.frre.dacs.dao.request.SearchCriteria;
import ar.edu.utn.frre.dacs.dao.request.criteria.SearchCriteriaVisitor;
import ar.edu.utn.frre.dacs.model.Paciente;

/**
 * 
 * 
 * @author Dr. Jorge Villaverde
 * @version 1.0
 */
public class JdbcSearchCriteriaVisitor<T> implements SearchCriteriaVisitor<Paciente> {

	/* (non-Javadoc)
	 * @see ar.edu.utn.frre.dacs.dao.request.criteria.SearchCriteriaVisitor#visit(java.util.Map, ar.edu.utn.frre.dacs.dao.request.SearchCriteria)
	 */
	@Override
	public Object visit(Map<String, Object> context, SearchCriteria<Paciente> criteria) {
		return " WHERE " + criteria.acceptJdbcCriteria(context, this);
	}

}
