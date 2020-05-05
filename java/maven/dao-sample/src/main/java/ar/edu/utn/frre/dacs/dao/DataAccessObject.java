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
package ar.edu.utn.frre.dacs.dao;

import java.io.Serializable;

import ar.edu.utn.frre.dacs.dao.request.Page;
import ar.edu.utn.frre.dacs.dao.request.SearchCriteria;
import ar.edu.utn.frre.dacs.dao.request.SearchRange;
import ar.edu.utn.frre.dacs.dao.request.order.SearchOrder;

/**
 * 
 * 
 * @author Dr. Jorge Villaverde
 * @version 1.0
 */
public interface DataAccessObject<T, PK extends Serializable> {
	/**
	 * @param criteria
	 * @param order
	 * @param range
	 * @return
	 */
	Page<T> findPage(SearchCriteria<T> criteria, SearchOrder<T> order, SearchRange<T> range);
	
	/**
	 * @param id
	 * @return
	 */
	T findById(PK id);
}