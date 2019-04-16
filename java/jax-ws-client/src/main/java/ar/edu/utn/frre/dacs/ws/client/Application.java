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
package ar.edu.utn.frre.dacs.ws.client;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

/**
 * Cliente JAX-WS
 * 
 * @author Dr. Jorge Eduardo Villaverde
 *
 */
public class Application {

	private static final String SERVICE_URL = "http://localhost:8080/jax-ws-server/ProvinciaServiceService/SimpleProvinciaServiceImpl?wsdl";

	private URL url;

	private QName qname;

	private Service service;

	private ProvinciaService provinciaService;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Application app = new Application();
		app.callService();
	}

	public void callService() {

		try {
			this.url = new URL(SERVICE_URL);

			this.qname = new QName("http://www.frre.utn.edu.ar/wsdl", "ProvinciaServiceService");

			this.service = Service.create(url, qname);

			this.provinciaService = service.getPort(ProvinciaService.class);

			List<Provincia> provincias = provinciaService.listProvincias();

			for (Provincia provincia : provincias) {
				System.out.println("Provincia: id = " + provincia.getId() + ", nombre = " + provincia.getNombre());
			}
		} catch (MalformedURLException e) {
			System.out.println(e.getMessage());
		}

	}

}
