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
package ar.edu.utn.frre.dacs.samplerest.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import ar.edu.utn.frre.dacs.samplerest.model.Hospital;

/**
 * 
 * 
 * @author Dr. Jorge Villaverde
 * @version 1.0
 */
@Repository
public class InMemoryHospitalDao implements HospitalDao {

	private static List<Hospital> hospitales;
	
	private static Map<Long, Hospital> hospitalesMap;
	
	static {
		hospitales = new ArrayList<>();
		hospitalesMap = new HashMap<>();
		Hospital hospitalPerrando = new Hospital();
		hospitalPerrando.setId(1L);
		hospitalPerrando.setNombre("Perrando");
		hospitalPerrando.setDireccion("9 de Julio 1000");
		hospitalPerrando.setNroCamas(500);
		hospitales.add(hospitalPerrando);
		hospitalesMap.put(hospitalPerrando.getId(), hospitalPerrando);
	}
	
	
	@Override
	public List<Hospital> findAll() {
		return hospitales;
	}

	@Override
	public Hospital findById(Long id) {
		return hospitalesMap.get(id);
	}

	@Override
	public Hospital create(Hospital entity) {
		hospitales.add(entity);
		entity.setId((long)hospitales.size());
		hospitalesMap.put(entity.getId(), entity);
		return entity;
	}

	@Override
	public boolean delete(Long id) {
		if(hospitalesMap.containsKey(id)) {
			hospitalesMap.remove(id);
			return true;
		}
		return false;
	}

}
