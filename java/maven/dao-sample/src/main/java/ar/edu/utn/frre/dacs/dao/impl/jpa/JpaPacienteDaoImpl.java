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
package ar.edu.utn.frre.dacs.dao.impl.jpa;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ar.edu.utn.frre.dacs.dao.PacienteDao;
import ar.edu.utn.frre.dacs.dao.request.DefaultPageImpl;
import ar.edu.utn.frre.dacs.dao.request.Page;
import ar.edu.utn.frre.dacs.dao.request.SearchCriteria;
import ar.edu.utn.frre.dacs.dao.request.SearchRange;
import ar.edu.utn.frre.dacs.dao.request.criteria.SearchCriteriaVisitor;
import ar.edu.utn.frre.dacs.dao.request.order.SearchOrder;
import ar.edu.utn.frre.dacs.dao.request.order.SearchOrderVisitor;
import ar.edu.utn.frre.dacs.model.Paciente;

/**
 * 
 * 
 * @author Dr. Jorge Villaverde
 * @version 1.0
 */
@Transactional
public class JpaPacienteDaoImpl implements PacienteDao {
	
	private static final Logger logger = LoggerFactory.getLogger(JpaPacienteDaoImpl.class);
	
	@PersistenceUnit(name = "dacs-pu")
	private EntityManagerFactory emf;
	
	private EntityManager em;

	private SearchCriteriaVisitor<Paciente> searchCriteriaVisitor = 
			new JpaSearchCriteriaVisitor<Paciente>();
	
	private SearchOrderVisitor<Paciente> searchOrderVisitor =
			new JpaSearchOrderVisitor<Paciente>();
	
	/* (non-Javadoc)
	 * @see ar.edu.utn.frre.dacs.dao.DataAccessObject#findPage(ar.edu.utn.frre.dacs.dao.request.criteria.SearchCriteria, ar.edu.utn.frre.dacs.dao.request.SearchOrder, ar.edu.utn.frre.dacs.dao.request.SearchRange)
	 */
	@Override
	public Page<Paciente> findPage(SearchCriteria<Paciente> criteria, SearchOrder<Paciente> order,
			SearchRange<Paciente> range) {

		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		
		CriteriaQuery<Paciente> query = cb.createQuery(Paciente.class);
		Root<Paciente> root = query.from(Paciente.class);
		
		Map<String, Object> context = new HashMap<String, Object>();
		context.put(SearchCriteriaVisitor.ENTITY_MANAGER, getEntityManager());
		context.put(SearchCriteriaVisitor.CRITERIA_BUILDER, cb);
		context.put(SearchCriteriaVisitor.ROOT, root);
		
		query.select(root);
		
		@SuppressWarnings("unchecked")
		Expression<Boolean> where = (Expression<Boolean>)searchCriteriaVisitor.visit(context, criteria);
		
		if (where != null) {			
			logger.info("JPA Search Criteria: " + where.toString());			
			query.where(where);
		}
		
		Order criteriaOrder = (Order)searchOrderVisitor.visit(context, order);
		
		if (criteriaOrder != null) {			
			logger.info("JPA Order: " + criteriaOrder.toString());			
			query.orderBy(criteriaOrder);
		}
		
		TypedQuery<Paciente> typedQuery = getEntityManager().createQuery(query);
		
		if (range != null) {
			typedQuery.setFirstResult(range.getFirstResult());
			typedQuery.setMaxResults(range.getMaxResults());			
		}
		
		List<Paciente> results = typedQuery.getResultList();
		
		return pageFromResults(results, range);
	}

	private static Page<Paciente> pageFromResults(List<Paciente> results, SearchRange<Paciente> range) {
		int pageNumber = 0;
		int offset = 0;
		if (range != null) {
			pageNumber = range.getFirstResult();
			offset = range.getMaxResults();
		}
		return new DefaultPageImpl<Paciente>(pageNumber, offset, results);
	}

	/* (non-Javadoc)
	 * @see ar.edu.utn.frre.dacs.dao.DataAccessObject#findById(java.io.Serializable)
	 */
	@Override
	public Paciente findById(Long id) {
		return getEntityManager().find(Paciente.class, id);
	}

	private EntityManager getEntityManager() {
		if (em == null) {
			em = emf.createEntityManager();
		}
		return em;
	}
	
}
