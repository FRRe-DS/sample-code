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

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

import ar.edu.utn.frre.dacs.dao.request.SearchCriteria;

/**
 * 
 * 
 * @author Dr. Jorge Villaverde
 * @version 1.0
 */
public class AndSearchCriteria<T> extends CompositeSearchCriteria<T> {

	/**
	 * @param lhs
	 * @param rhs
	 */
	public AndSearchCriteria(SearchCriteria<T> lhs, SearchCriteria<T> rhs) {
		super(lhs, rhs);
	}

	@Override
	protected String getOperatorSign() {
		return "AND";
	}

	@Override
	protected Predicate buildCompositeOperator(CriteriaBuilder cb, Expression<Boolean> lhsExpression,
			Expression<Boolean> rhsExpression) {
		return cb.and(lhsExpression, rhsExpression);
	}
}