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

import javax.persistence.EntityNotFoundException;

import ar.edu.utn.frre.dacs.model.BaseEntity;

/**
 * 
 * @author Dr. Jorge Villaverde
 * @version 1.0
 */
public interface DataAccessObject<T extends BaseEntity, PK extends Serializable> {

	/**
	 * Busca a una Entidad por su Id.
	 * @param id Identificar de la Entidad a buscar
	 * @return Entidad del repository
	 * @throws EntityNotFoundException Lanzada si no se encuentra la entidad 
	 * con el id. 
	 */
	T findById(PK id) throws EntityNotFoundException;
}
