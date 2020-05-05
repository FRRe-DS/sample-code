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
package ar.edu.utn.frre.dacs.ws.rs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ar.edu.utn.frre.dacs.model.Provincia;

/**
 *
 * @author Dr. Jorge Eduardo Villaverde
 */
@Path("provincia")
public class ProvinciaResource {

    @GET
    @Produces(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response list() {
    	Provincia[] l = new Provincia[1];
		
		l[0]=createChaco();
		
		return Response.ok().entity(l).build();
    }

	private static final Provincia createChaco() {
		Provincia provincia = new Provincia();
		provincia.setId(1L);
		provincia.setNombre("Corrientes");
		return provincia;
	}
}
