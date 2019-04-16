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
package ar.edu.utn.frre.dacs.ms.compservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class GreettingServiceImpl implements GreetingService {
	
	protected Logger logger = LoggerFactory.getLogger(GreettingServiceImpl.class.getName());

	@Autowired
	private ServiceAClient serviceAClient;
	
	@Autowired
	private ServiceBClient serviceBClient;
	
	@Override
	@HystrixCommand(fallbackMethod = "defaultHi")
	public String sayHi() {
		logger.info("Getting greetings.");
		
		StringBuilder builder = new StringBuilder();
		
		builder.append("Greenting from A: ");
		builder.append(serviceAClient.serviceAGreeting());
		builder.append("\n");
		builder.append("Greenting from B: ");
		builder.append(serviceBClient.serviceBGreeting());
		
		return builder.toString();
	}

	
	public String defaultHi() {
		return "Hello World!";
	}
}
