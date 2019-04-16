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
package ar.com.edu.nbch.frre.dacs.dao.impl;

import java.util.HashMap;
import java.util.Map;

import ar.com.edu.nbch.frre.dacs.dao.UsuarioDao;
import ar.com.edu.nbch.frre.dacs.model.Usuario;

/**
 * @author Dr. Jorge E. Villaverde
 *
 */
public class UsuarioDaoImpl implements UsuarioDao {

	private static Map<String, Usuario> map;
	
	static {
		Usuario usuario = new Usuario();
		usuario.setUsername("usuario");
		usuario.setNombre("Juan");
		usuario.setApellido("Perez");
		
		Usuario admin = new Usuario();
		admin.setUsername("admin");
		admin.setNombre("Homero");
		admin.setApellido("Simpson");
		
		map = new HashMap<String, Usuario>();
		map.put("user", usuario);
		map.put("admin", admin);
	}
	
	@Override
	public Usuario findByUsername(String username) {
		return map.get(username);
	}

}
