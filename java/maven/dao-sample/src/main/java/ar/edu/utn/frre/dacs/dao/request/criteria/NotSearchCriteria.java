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
package ar.edu.utn.frre.dacs.dao.request.criteria;

import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

import ar.edu.utn.frre.dacs.dao.request.SearchCriteria;
import ar.edu.utn.frre.dacs.dao.request.order.SearchOrderVisitor;

/**
 * 
 * 
 * @author Dr. Jorge Villaverde
 * @version 1.0
 */
public class NotSearchCriteria<T> implements SearchCriteria<T> {

	private final SearchCriteria<T> criteria;

	/**
	 * 
	 */
	public NotSearchCriteria(SearchCriteria<T> criteria) {
		super();
		this.criteria = criteria;
	}

	public SearchCriteria<T> getCriteria() {
		return criteria;
	}

	@Override
	public String toString() {
		return new StringBuilder().append("Not (").append(getCriteria().toString()).append(")").toString();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object acceptJPACriteria(Map<String, Object> context, SearchCriteriaVisitor<T> visitor) {
		CriteriaBuilder cb = SearchCriteriaVisitor.getCriteriaBuilder(context);

		Expression<Boolean> delegate = (Expression<Boolean>) this.criteria.acceptJPACriteria(context, visitor);

		Predicate notPredicate = cb.not(delegate);

		return notPredicate;
	}

	@Override
	public Object acceptJPAOrder(Map<String, Object> context, SearchOrderVisitor<T> visitor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object acceptJdbcCriteria(Map<String, Object> context, SearchCriteriaVisitor<T> visitor) {
		return new StringBuilder()
				.append("NOT ")
				.append(criteria.acceptJdbcCriteria(context, visitor))
				.toString();
	}

}
