/*
 * Copyright (C) 2015-2018 UTN-FRRe
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
package ar.edu.utn.frre.dacs.ws.server.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.jws.WebService;

import ar.edu.utn.frre.dacs.model.Provincia;
import ar.edu.utn.frre.dacs.ws.server.ProvinciaService;

/**
 * 
 * @author Dr. Jorge Villaverde
 * @version 1.0
 */
@Stateless
@Local(ProvinciaService.class)
@WebService(
		portName = "ProvinciaServicePort",
        serviceName = "ProvinciaServiceService",
        targetNamespace = "http://www.frre.utn.edu.ar/wsdl",		
		endpointInterface = "ar.edu.utn.frre.dacs.ws.server.ProvinciaService")
public class SimpleProvinciaServiceImpl implements ProvinciaService {

	@Override
	public List<Provincia> listProvincias() {
		List<Provincia> l = new ArrayList<Provincia>();
		
		l.add(createChaco());
		l.add(createCorrientes());
		
		return l;
	}
	
	private Provincia createCorrientes() {
		Provincia provincia = new Provincia();
		provincia.setId(2L);
		provincia.setNombre("Corrientes");
		return provincia;
	}

	private static final Provincia createChaco() {
		Provincia provincia = new Provincia();
		provincia.setId(1L);
		provincia.setNombre("Chaco");
		return provincia;
	}

}
