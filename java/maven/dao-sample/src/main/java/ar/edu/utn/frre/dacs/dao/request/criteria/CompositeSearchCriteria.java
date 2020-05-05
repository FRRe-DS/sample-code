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
public abstract class CompositeSearchCriteria<T> implements SearchCriteria<T> {

	private final SearchCriteria<T> lhs;
	
	private final SearchCriteria<T> rhs;
	
	public CompositeSearchCriteria(SearchCriteria<T> lhs, SearchCriteria<T> rhs) {
		super();
		this.lhs = lhs;
		this.rhs = rhs;
	}

	public SearchCriteria<T> getLhs() {
		return lhs;
	}

	public SearchCriteria<T> getRhs() {
		return rhs;
	}	

	@Override
	public String toString() {
		return new StringBuilder()
				.append(getOperatorSign())
				.append(" (")
				.append(lhs.toString())
				.append(", ")
				.append(rhs.toString())
				.append(")")
				.toString();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object acceptJPACriteria(Map<String, Object> context, SearchCriteriaVisitor<T> visitor) {
		CriteriaBuilder cb = SearchCriteriaVisitor.getCriteriaBuilder(context);
				
		Expression<Boolean> lhsExpression = (Expression<Boolean>)lhs.acceptJPACriteria(context, visitor);
		Expression<Boolean> rhsExpression = (Expression<Boolean>)rhs.acceptJPACriteria(context, visitor);
		
		return buildCompositeOperator(cb, lhsExpression, rhsExpression);
	}

	@Override
	public Object acceptJdbcCriteria(Map<String, Object> context, SearchCriteriaVisitor<T> visitor) {
		StringBuilder builder = new StringBuilder();
		
		builder.append(lhs.acceptJdbcCriteria(context, visitor));
		builder.append(" ");
		builder.append(getOperatorSign());
		builder.append(" ");
		builder.append(rhs.acceptJdbcCriteria(context, visitor));
		
		return builder.toString();
	}

	protected abstract String getOperatorSign();
	
	protected abstract Predicate buildCompositeOperator(CriteriaBuilder cb, 
			Expression<Boolean> lhsExpression, Expression<Boolean> rhsExpression);

	@Override
	public Object acceptJPAOrder(Map<String, Object> context, SearchOrderVisitor<T> visitor) {
		// TODO Auto-generated method stub
		return null;
	}

}
