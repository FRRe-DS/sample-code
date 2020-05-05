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
import javax.persistence.criteria.Root;

import ar.edu.utn.frre.dacs.dao.request.SearchCriteria;
import ar.edu.utn.frre.dacs.dao.request.order.SearchOrderVisitor;

/**
 * 
 * 
 * @author Dr. Jorge Villaverde
 * @version 1.0
 */
public abstract class PropertySearchCriteria<T> implements SearchCriteria<T> {

	private final String property;
	
	private final Object value;

	public PropertySearchCriteria(String property, Object value) {
		super();
		this.property = property;
		this.value = value;
	}

	public String getProperty() {
		return property;
	}

	public Object getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		return new StringBuilder()
				.append(getProperty())
				.append(" " + operatorName() + " ")
				.append(getValue().toString())
				.toString();
	}

	protected abstract String operatorName();

	@Override
	public Object acceptJPACriteria(Map<String, Object> context, SearchCriteriaVisitor<T> visitor) {
		CriteriaBuilder cb = SearchCriteriaVisitor.getCriteriaBuilder(context);
		Root<T> root = SearchCriteriaVisitor.getRoot(context);
				
		Expression<?> attributeExpression = root.get(getProperty());
		return buildPropertyValueExpression(cb, attributeExpression);
	}

	@Override
	public Object acceptJPAOrder(Map<String, Object> context, SearchOrderVisitor<T> visitor) {
		// TODO Auto-generated method stub
		return null;
	}


	protected abstract Predicate buildPropertyValueExpression(CriteriaBuilder cb, Expression<?> attributeExpression);

	@Override
	public Object acceptJdbcCriteria(Map<String, Object> context, SearchCriteriaVisitor<T> visitor) {
		StringBuilder builder = new StringBuilder();
		builder.append(getProperty());
		builder.append(" ");
		builder.append(operatorName());
		builder.append(" '");
		builder.append(getValue().toString());
		builder.append("'");
		return builder.toString();
	}
}
