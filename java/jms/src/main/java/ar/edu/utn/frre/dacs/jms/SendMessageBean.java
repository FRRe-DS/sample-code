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

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * @author Dr. Jorge Eduardo Villaverde
 *
 */
@ViewScoped
@ManagedBean
public class SendMessageBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static Logger logger = 
			Logger.getLogger(SendMessageBean.class.getSimpleName());
	
	// Dependencies -----------------------------------------------------------
	
	@Resource(name = "java:/ConnectionFactory")
	private ConnectionFactory connectionFactory;

	@Resource(name = "java:/jms/queue/DACSQueue")
	private Queue queue;
	
	// Properties -------------------------------------------------------------
	
	//private String message;
	
	// Methods ----------------------------------------------------------------
	
	public void sendMessage(String message) {
		Connection connection = null;
		Session session = null;
		MessageProducer producer = null;
		try {
			// 1 - Create the Connection
            connection = connectionFactory.createConnection();
            
            // 2 - Create the Session
			session = connection.createSession(
                    false,
                    Session.AUTO_ACKNOWLEDGE);

			// 3 - Create the producer
            producer = session.createProducer(queue);

            // 4 - Create the message
            
            TextMessage textMessage = session.createTextMessage();

            // 5 - Set the payload
            textMessage.setText(message);
            
            // 6 - Send the message
            producer.send(textMessage);			
		} catch (JMSException e) {
			logger.log(Level.SEVERE, e.getMessage());
		} finally {
			// 7 - Clean up
			if(producer != null) {
				try { producer.close();
				} catch (JMSException e) {}
			}
			if(session != null) {
				try { session.close();
				} catch (JMSException e) {}
			}			
            if(connection != null) {
                try { connection.close(); 
                } catch (JMSException e) {}
            }
        }		
	}

	// Getters/Setters --------------------------------------------------------
	
//	public String getMessage() {
//		return message;
//	}
//
//	public void setMessage(String message) {
//		this.message = message;
//	}

}
