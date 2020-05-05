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
package ar.edu.utn.frre.dacs.dao.request;

import java.util.List;

/**
 * A Page is a sublist of objects.
 * 
 * @author Dr. Jorge Villaverde
 * @version 1.0
 */
public interface Page<T> {

	/**
	 * @return Number of this page.
	 */
	int getPageNumber();

	/**
	 * @return Offset of the Page.
	 */
	int getOffset();

	/**
	 * @return Content of the Page.
	 */
	List<T> getContent();
}