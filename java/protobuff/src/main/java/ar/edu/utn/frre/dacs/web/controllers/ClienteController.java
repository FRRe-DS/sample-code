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
package ar.edu.utn.frre.dacs.web.controllers;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.protobuf.Timestamp;

import ar.edu.utn.frre.dacs.dao.ClienteRepository;
import ar.edu.utn.frre.dacs.proto.dto.DACSProtos.Cliente;
import ar.edu.utn.frre.dacs.proto.dto.DACSProtos.Cliente.Sexo;

/**
 * 
 * 
 * @author Dr. Jorge Villaverde
 * @version 1.0
 */
@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

	@Autowired
	private ClienteRepository repository;
	
    @RequestMapping("/{id}")
    public Cliente customer(@PathVariable Long id) {
    	Optional<ar.edu.utn.frre.dacs.model.Cliente> optional = repository.findById(id);
    	
    	if (!optional.isPresent())
    		return null;
    	
    	ar.edu.utn.frre.dacs.model.Cliente cliente = optional.get();
    	
    	return Cliente.newBuilder()
    			.setId(cliente.getId())
    			.setNumeroDocumento(cliente.getNumeroDocumento())
    			.setCuitCuil(cliente.getCuitCuil())
    			.setPrimerNombre(cliente.getPrimerNombre())
    			.setApellido(cliente.getApellido())
    			.setSexo(Sexo.forNumber(cliente.getSexo().ordinal()))
    			.setFechaNacimiento(Timestamp.newBuilder().setSeconds(new Date().getTime()))
    			.build();
    }
	
}
