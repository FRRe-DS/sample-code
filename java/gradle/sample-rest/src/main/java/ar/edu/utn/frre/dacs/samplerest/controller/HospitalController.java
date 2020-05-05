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
package ar.edu.utn.frre.dacs.samplerest.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.utn.frre.dacs.samplerest.dao.HospitalDao;
import ar.edu.utn.frre.dacs.samplerest.model.Hospital;
import ar.edu.utn.frre.dacs.samplerest.service.HospitalService;

/**
 * 
 * 
 * @author Dr. Jorge Villaverde
 * @version 1.0
 */
@RestController
@RequestMapping("/hospital")
@Validated
public class HospitalController {
	
	@Autowired
	private HospitalDao repository;
	
	@GetMapping("")
	public ResponseEntity<List<Hospital>> listarTodos() {
		return ResponseEntity.ok(repository.findAll());
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Hospital> findById(@PathVariable("id") @Min(1) Long id) {
		Hospital result = repository.findById(id);
		if(result != null) {			
			return ResponseEntity.ok(result);
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping("")
	public ResponseEntity<Hospital> create(@RequestBody @Valid Hospital nuevoHospital) {
		Hospital entitiy = repository.create(nuevoHospital);
		return ResponseEntity.created(URI.create("/hospital/"+entitiy.getId())).build();
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> borrar(@PathVariable("id") Long id) {
		boolean deleted = repository.delete(id);
		if(deleted) {			
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}
	
}
