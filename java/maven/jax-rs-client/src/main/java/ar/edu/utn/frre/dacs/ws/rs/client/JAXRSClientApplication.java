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
package ar.edu.utn.frre.dacs.ws.rs.client;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import ar.edu.utn.frre.dacs.model.Provincia;

/**
 * Cliente JAX-RS
 * @author Dr. Jorge Eduardo Villaverde
 *
 */
public class JAXRSClientApplication {

	private static final String RESOURCES_PROVINCIA = "resources/provincia";
	
	private static final String WS_ENDPOINT = "http://localhost:8080/jax-rs-server";

	public static void main(String[] args) {
		// Create cliete from Builder
		Client client = ClientBuilder.newClient();
		
		// Creates web targets
		
		WebTarget webTarget = client.target(WS_ENDPOINT);

		WebTarget provinciaWebTarget = webTarget.path(RESOURCES_PROVINCIA);
		
		// Build a HTTP Request
		
		Invocation.Builder invocationBuilder = 
				provinciaWebTarget.request(MediaType.APPLICATION_JSON);
		
		// Invoking HTTP Requests
		List<Provincia> provincias =
				invocationBuilder.get(new GenericType<List<Provincia>>() {});
		
		// Use results
		
		for(Provincia provincia : provincias) {
			System.out.println("Provincia: "
			+ "id = " + provincia.getId()
			+ ", nombre = " + provincia.getNombre());
		}		
	}

}
