Desarrollo de Aplicaciones Cliente Servidor - 2017
==================================================

JMS Example Application
------------------------

El siguiente ejemplo muestra como implementar una aplicación JMS.


Configuración de WildFly
-

* Agregar la extensión JMS al standalone.xml: 
<pre><code>
&lt;extension module="org.wildfly.extension.messaging-activemq"/&gt;
</code></pre>

* Agregar el RAR de ActiveMQ al standalone.xml:
<pre><code>
&lt;mdb&gt;
 &lt;resource-adapter-ref resource-adapter-name="${ejb.resource-adapter-name:activemq-ra.rar}"/&gt;
 &lt;bean-instance-pool-ref pool-name="mdb-strict-max-pool"/&gt;
&lt;/mdb&gt;
</code></pre>

* Agregar la configuración del subsistema jms al standalone.xml:
<pre><code>
&lt;subsystem xmlns="urn:jboss:domain:messaging-activemq:1.0"&gt;
 	&lt;server name="default"&gt;
		&lt;security-setting name="#"&gt;
		&lt;role name="guest" send="true" consume="true" create-non-durable-queue="true" delete-non-durable-queue="true"/&gt;
		&lt;/security-setting&gt;
		&lt;address-setting name="#" dead-letter-address="jms.queue.DLQ" expiry-address="jms.queue.ExpiryQueue" max-size-bytes="10485760" page-size-bytes="2097152" message-counter-history-day-limit="10"/&gt;
		&lt;http-connector name="http-connector" socket-binding="http" endpoint="http-acceptor"/&gt;
		&lt;http-connector name="http-connector-throughput" socket-binding="http" endpoint="http-acceptor-throughput"&gt;
			&lt;param name="batch-delay" value="50"/&gt;
		&lt;/http-connector&gt;
		&lt;in-vm-connector name="in-vm" server-id="0"/&gt;
		&lt;http-acceptor name="http-acceptor" http-listener="default"/&gt;
		&lt;http-acceptor name="http-acceptor-throughput" http-listener="default"&gt;
			&lt;param name="batch-delay" value="50"/&gt;
			&lt;param name="direct-deliver" value="false"/&gt;
 		&lt;/http-acceptor&gt;
		&lt;in-vm-acceptor name="in-vm" server-id="0"/&gt;
		&lt;jms-queue name="ExpiryQueue" entries="java:/jms/queue/ExpiryQueue"/&gt;
		&lt;jms-queue name="DLQ" entries="java:/jms/queue/DLQ"/&gt;
		&lt;connection-factory name="InVmConnectionFactory" entries="java:/ConnectionFactory" connectors="in-vm"/&gt;
		&lt;connection-factory name="RemoteConnectionFactory" entries="java:jboss/exported/jms/RemoteConnectionFactory" connectors="http-connector"/&gt;
		&lt;pooled-connection-factory name="activemq-ra" entries="java:/JmsXA java:jboss/DefaultJMSConnectionFactory" connectors="in-vm" transaction="xa"/&gt;
	&lt;/server&gt;
&lt;/subsystem&gt;
</code></pre>

* Agregar el Queue para el ejemplo:
<pre><code>
&lt;jms-queue name="DACS" entries="java:/jms/queue/DACSQueue"/&gt;
</code></pre>