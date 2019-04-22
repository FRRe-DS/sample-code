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
package ar.edu.utn.frre.dacs.app;

import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import ar.edu.utn.frre.dacs.configuration.JPAConfiguration;
import ar.edu.utn.frre.dacs.configuration.JdbcConfiguration;
import ar.edu.utn.frre.dacs.dao.PacienteDao;
import ar.edu.utn.frre.dacs.dao.request.Page;
import ar.edu.utn.frre.dacs.dao.request.SearchCriteria;
import ar.edu.utn.frre.dacs.dao.request.SearchRange;
import ar.edu.utn.frre.dacs.dao.request.criteria.AndSearchCriteria;
import ar.edu.utn.frre.dacs.dao.request.criteria.EqualPropertySeachCriteria;
import ar.edu.utn.frre.dacs.dao.request.criteria.NotSearchCriteria;
import ar.edu.utn.frre.dacs.dao.request.order.SearchOrder;
import ar.edu.utn.frre.dacs.model.Paciente;


/**
 * Data Access Object Spring Sample Application
 * 
 * @author Dr. Jorge Villaverde
 * @version 1.0
 */
@SpringBootApplication
//@Import({ JPAConfiguration.class })
@Import({ JdbcConfiguration.class })
public class SampleDaoApplication implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(SampleDaoApplication.class);
	
	@Autowired
	private PacienteDao pacienteDao;

	private SimpleDateFormat dateFrmt = new SimpleDateFormat("dd/mm/yyyy");
		
	public static void main(String[] args) {		
		SpringApplication.run(SampleDaoApplication.class, args);		
	}

	@Override
	public void run(String... args) throws Exception {
		
		
		SearchCriteria<Paciente> criteria = new AndSearchCriteria<Paciente>(
				new EqualPropertySeachCriteria<Paciente>("nombre", "Jorge"), 
				new NotSearchCriteria<Paciente>(
					new EqualPropertySeachCriteria<Paciente>("dni", Long.valueOf(28443521))));
		SearchOrder<Paciente> order = null;
		SearchRange<Paciente> range = null;

//		EqualPropertySeachCriteria.Builder<Paciente> builder 
//			= EqualPropertySeachCriteria.newBuilder();
//		
//		SearchCriteria<Paciente> criteria = 
//				builder.equal("nombre", "Jorge").build();
				
		logger.info("Buscando Paciente con el criterio: " + criteria.toString());
		
		Page<Paciente> page = pacienteDao.findPage(criteria, order, range);
		
		logPacienteHeader();
		for(Paciente paciente : page.getContent()) {
			logPacienteRow(paciente);
		}
		logPacienteFootter();
	}

	private void logPacienteHeader() {
		logger.info("Pacientes");
		logger.info("┌--------┬----------------------┬----------------------┬------------┐");
		logger.info("| Id     | Nombre               | Apellido             | Fech Nac   |");
		logger.info("├--------┼----------------------┼----------------------┼------------┤");
		
	}

	private void logPacienteRow(Paciente paciente) {		
		logger.info(String.format("| %6d | %-20s | %-20s | %10s |", 
				paciente.getId(),
				paciente.getNombre(),
				paciente.getApellido(),
				dateFrmt .format(paciente.getFechaNacimiento())));
	}

	private void logPacienteFootter() {
		logger.info("└--------┴----------------------┴----------------------┴------------┘");		
	}
}