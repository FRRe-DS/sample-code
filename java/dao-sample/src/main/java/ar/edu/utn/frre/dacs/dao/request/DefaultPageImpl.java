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
 * 
 * 
 * @author Dr. Jorge Villaverde
 * @version 1.0
 */
public class DefaultPageImpl<T> implements Page<T> {
	
	private final int pageNumber;
	
	private final int offset;
	
	private final List<T> content;

	public DefaultPageImpl(int pageNumber, int offset, List<T> content) {
		super();
		this.pageNumber = pageNumber;
		this.offset = offset;
		this.content = content;
	}

	public DefaultPageImpl(SearchRange<T> range, List<T> content) {
		super();
		if (range != null) {
			this.pageNumber = range.getFirstResult();
			this.offset = range.getMaxResults();
		} else {
			this.pageNumber = 0;			
			this.offset = 0;
		}
		this.content = content;
	}
	
	/* (non-Javadoc)
	 * @see ar.edu.utn.frre.dacs.dao.request.Page#getPageNumber()
	 */
	@Override
	public int getPageNumber() {
		return pageNumber;
	}

	/* (non-Javadoc)
	 * @see ar.edu.utn.frre.dacs.dao.request.Page#getOffset()
	 */
	@Override
	public int getOffset() {
		return offset;
	}

	/* (non-Javadoc)
	 * @see ar.edu.utn.frre.dacs.dao.request.Page#getContent()
	 */
	@Override
	public List<T> getContent() {
		return content;
	}	
}
