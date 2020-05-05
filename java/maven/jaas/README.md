Desarrollo de Aplicaciones Cliente Servidor - 2017
==================================================

JAAS Example Application
------------------------

El siguiente ejemplo muestra como implementar Java Authentication and 
Authorization Service en una aplicación Web.


Configuración de WildFly
-

<code><pre>
./subsystem=elytron/properties-realm=dacs-realm:add(users-properties={path=dacs-users.properties, relative-to=jboss.server.config.dir, plain-text=true, digest-realm-name="DACS Application Security"}, groups-properties={path=dacs-roles.properties, relative-to=jboss.server.config.dir}, groups-attribute=Roles)
</code></pre>

<code><pre>
./subsystem=elytron/security-domain=dacs-security:add(realms=[{realm=dacs-realm}], default-realm=dacs-realm, permission-mapper=default-permission-mapper)
</code></pre>

<code><pre>
./subsystem=elytron/http-authentication-factory=dacs-security-http:add(http-server-mechanism-factory=global, security-domain=dacs-security, mechanism-configurations=[{mechanism-name=FORM}])
</code></pre>

<code><pre>
./subsystem=undertow/application-security-domain=dacs-security:add(http-authentication-factory=dacs-security-http)
</code></pre>
