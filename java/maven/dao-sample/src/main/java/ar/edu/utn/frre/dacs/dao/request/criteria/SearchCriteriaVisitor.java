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
import javax.persistence.criteria.Root;

import ar.edu.utn.frre.dacs.dao.request.SearchCriteria;

/**
 * 
 * 
 * @author Dr. Jorge Villaverde
 * @version 1.0
 */
public interface SearchCriteriaVisitor<T> {

	/** Criteria Builder constant */
	public static final String CRITERIA_BUILDER = "CRITERIA_BUILDER";
	
	/** Root constant */
	public static final String ROOT = "ROOT";

	/** Entity Manager constant */
	public static final String ENTITY_MANAGER = "ENTITY_MANAGER";
	
	Object visit(Map<String, Object> context, SearchCriteria<T> criteria);

	public static CriteriaBuilder getCriteriaBuilder(Map<String, Object> context) {
		CriteriaBuilder cb = (CriteriaBuilder)context.get(CRITERIA_BUILDER);
		return cb;
	}

	public static <T> Root<T> getRoot(Map<String, Object> context) {
		@SuppressWarnings("unchecked")
		Root<T> root = (Root<T>)context.get(ROOT);
		return root;
	}

}
