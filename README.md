# KitchenProyect
aplicación que permita registrar las facturas de las ventas realizadas en el restaurante “LA MEJOR COCINA”. 

Se crea un proyecto con JEE + VAADIN + POSTGRESQL + WILDFLY
1. en el standalone.xml ingresar el manejo de la base de datos
<datasource jndi-name="java:jboss/datasources/kitchenDB" pool-name="kitchenDB" enabled="true" use-java-context="true">
                    <connection-url>jdbc:postgresql://localhost:5432/kitchen</connection-url>
                    <driver>postgresql</driver>
                    <transaction-isolation>TRANSACTION_READ_COMMITTED</transaction-isolation>
                    <pool>
                        <min-pool-size>10</min-pool-size>
                        <initial-pool-size>10</initial-pool-size>
                        <max-pool-size>60</max-pool-size>
                        <prefill>true</prefill>
                        <allow-multiple-users>true</allow-multiple-users>
                    </pool>
                    <security>
                        <user-name>postgres</user-name>
                        <password>postgres</password>
                    </security>
                    <validation>
                        <valid-connection-checker class-name="org.jboss.jca.adapters.jdbc.extensions.postgres.PostgreSQLValidConnectionChecker"/>
                        <background-validation>true</background-validation>
                        <exception-sorter class-name="org.jboss.jca.adapters.jdbc.extensions.postgres.PostgreSQLExceptionSorter"/>
                    </validation>
                    <timeout>
                        <idle-timeout-minutes>5</idle-timeout-minutes>
                    </timeout>
                    <statement>
                        <prepared-statement-cache-size>32</prepared-statement-cache-size>
                        <share-prepared-statements>true</share-prepared-statements>
                    </statement>
                </datasource>
2. Asegurar que el wildfly pueda ingresar por puerto seguro https y 8443 o sino deshabilitar las cookies a la hora de realizar la prueba
3. crear en postgresql la database kitchen y esquema public
4. ejecutar los scripts de la carpeta sql
5. compilar el proyecto "mvn clean install" y el .war colocarlo en la carpeta deployments
6. ejecutar wildfly y entrar a http://localhost:8080/kitchen-0.1/ o https://localhost:8443/kitchen-0.1/
7. en la pantalla vemos un tab con tres opciones
  1. opcion uno ingresa la factura con cliente, camarero, mesa y producto
  2. busca las ventas hechas por camarerop
  3. busca los clientes con mas de 100.000 en compra
