/*
 * Copyright (C) 2017 UTN-FRRe
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
package ar.edu.utn.frre.dacs.jms;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * @author Dr. Jorge Eduardo Villaverde
 *
 */
@MessageDriven(
	mappedName="java:/jms/queue/DACSQueue", activationConfig =  {
		@ActivationConfigProperty(propertyName = "acknowledgeMode",
		                          propertyValue = "Auto-acknowledge"),
		@ActivationConfigProperty(propertyName = "destinationType",
								  propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName="destination", 
								  propertyValue="java:/jms/queue/DACSQueue")
	})
public class JMSProvider implements MessageListener {
	
	private static final Logger logger = 
			Logger.getLogger(JMSProvider.class.getSimpleName());

	 @Resource
	 private MessageDrivenContext mdc;
	
	@Override
	public void onMessage(Message message) {
		
		logger.log(Level.FINE, "Llego un mensaje");
		
		try {
            if (message instanceof TextMessage) {
                logger.log(Level.INFO,
                        "MESSAGE BEAN: Message received: {0}",
                        message.getBody(String.class));
            } else {
                logger.log(Level.WARNING,
                        "Message of wrong type: {0}",
                        message.getClass().getName());
            }
        } catch (JMSException e) {
            logger.log(Level.SEVERE,
                    "onMessage: JMSException: {0}",
                    e.toString());
            mdc.setRollbackOnly();
        }		
	}
}
