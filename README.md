## JMS WildFly
Для включения jms нужно добавить модуль 
`<extension module="org.wildfly.extension.messaging-activemq"/>`

настроить ресурс адаптер
[Настройка ресурс-адаптера](http://www.mastertheboss.com/jboss-server/jboss-jms/integrate-activemq-with-wildfly)

```xml
<subsystem xmlns="urn:jboss:domain:resource-adapters:5.0">
    <resource-adapters>
        <resource-adapter id="activemq-ra">
            <archive>
                activemq-rar-5.15.8.rar
            </archive>
            <transaction-support>XATransaction</transaction-support>
            <config-property name="ServerUrl">for active mq: tcp://localhost:61616</config-property>
            <config-property name="UserName">...</config-property>
            <config-property name="Password">...</config-property>
            <connection-definitions>
                <connection-definition class-name="org.apache.activemq.ra.ActiveMQManagedConnectionFactory" jndi-name="java:/ConnectionFactoryName" enabled="true" pool-name="ConnectionFactoryName">
                    
                </connection-definition>
            </connection-definitions>
            <admin-objects>
                
            </admin-objects>
        </resource-adapter>
    </resource-adapters>
</subsystem>
```

добавить конфиг модулей
 
```xml
<!-- см subsystem'ы -->
<profile>
    <subsystem xmlns="urn:jboss:domain:messaging-activemq:8.0"/>
    <subsystem xmlns="urn:jboss:domain:ejb3:6.0">
      <mdb>
          <resource-adapter-ref resource-adapter-name="activemq-ra"/>
          <bean-instance-pool-ref pool-name="mdb-strict-max-pool"/>
      </mdb>
    </subsystem>
</profile>
```

