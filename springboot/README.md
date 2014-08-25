### LEANING POINTS

* replacing auto-configuration
If you need to find out what auto-configuration is currently being applied, and why, starting your application with the --debug switch. This will log an auto-configuration report to the console.
Also when actuators endpoints are active you can go to /autoconfig for a full report

* Disabling specific auto-configuration
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
Also, from 20.5: "It is also possible to take complete control of the ApplicationContext type that will be used by calling setApplicationContextClass(...)."

* Customizing SpringApplication
```
// eg. to disable the banner; other configurations follow the same approach 
SpringApplication app = new SpringApplication(MySpringConfiguration.class);
app.setShowBanner(false);
app.run(args);
```
Note: It is also possible to configure the SpringApplication using an application.properties

* Application exit
use DisposableBean or @PreDestroy to register lifecycle callbacks

* Application property files
SpringApplication will load properties from application.properties. 
If you don’t like application.properties as the configuration file name you can switch to another by specifying a spring.config.name environment property
```
$ java -jar myproject.jar --spring.config.name=myproject
$ java -jar myproject.jar --spring.config.location=classpath:/default.properties,classpath:/override.properties
```
In addition to application.properties files, profile specific properties can also be defined using the naming convention application-{profile}.properties.
To use yaml create a file called application.yml and stick it in the root of your classpath, and also add snakeyaml to your dependencies
See "Appendix A. Common application properties" for a list of common properties

* Logging
Default uses logback; prints to console and file; file rotates on 10 MB size
To customize the output folder set the logging.path property. It is also possible to change the filename using a logging.file property
Custom logback configuration: create a logback.xml in the classpath
See "61. Logging" for more

* Rest Service
use @RestController and @RequestMapping(value="/users")

* Static Content
By default Spring Boot will serve static content from a folder called /static (or /public or /resources or /META-INF/resources) in the classpath

* Connection Pool
prefers the Tomcat pooling Datasource; add spring-boot-starter-jdbc or spring-boot-starter-data-jpa dependency; use spring.datasource.* properties to configure it
To override the default settings just define a @Bean of your own of type DataSource
```
@Bean
@ConfigurationProperties(prefix="datasource.mine")
public DataSource dataSource() {
    return ...;
}
```

* Actuators
enable production features; publish useful endpoints;
to disable an endpoint: endpoints.beans.enabled=false
to enable only admins to access endpoints: management.security.role=SUPERUSER (will restrict access to all sensitive endpoints)
to create a prefix path for all management endpoints: management.context-path=/manage; to configure them on a different port: management.port=8081
If you don’t want to expose endpoints over HTTP you can set: management.port=-1

* Jmx stats 
By default Spring Boot will create an MBeanServer with bean id “mbeanServer” and expose any of your beans that are annotated with Spring JMX annotations (@ManagedResource, @ManagedAttribute, @ManagedOperation). See the JmxAutoConfiguration class for more details.
set the endpoints.jmx.uniqueNames property to true so that MBean names are always unique.
Jolokia is a JMX-HTTP bridge giving an alternative method of accessing JMX beans. simply include a dependency to org.jolokia:jolokia-core. Jolokia can then be accessed using /jolokia

* Metrics
/metrics
To record your own metrics inject a CounterService and/or GaugeService into your bean

* Tracing
/trace shows all the events that happened. By default an InMemoryTraceRepository will be used that stores the last 100 events. You can also create your own alternative TraceRepository implementation if needed.

* Using SSL
Use an EmbeddedServletContainerCustomizer (see 59.6 Terminate SSL in Tomcat)?

* Using Jetty instead of Tomcat
```
configurations {
    compile.exclude module: "spring-boot-starter-tomcat"
}

dependencies {
    compile("org.springframework.boot:spring-boot-starter-web:1.1.5.RELEASE")
    compile("org.springframework.boot:spring-boot-starter-jetty:1.1.5.RELEASE")
    // ...
}
```
Use JettyEmbeddedServletContainerFactory to customise it

* Security
@EnableWebSecurity
If you provide a @Bean of type AuthenticationManager the default one will not be created, so you have the full feature set of Spring Security available
See "66. Security" for more

### QUESTIONS
* can I use @Inject rather than @Autowired?
* can I debug when I start the application as an external process (aka from the jar)?
-Drun.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005"
or
gradle run --debug-jvm ("68.7 Remote debug a Spring Boot application started with Gradle")

* can I use JRebel for code hot-swapping?
See 67.6.1 Configuring Spring Loaded for use with Gradle and IntelliJ

* can I use profiles for acceptance-tests vs production? maybe SpringApplication.setAdditionalProfiles(...) ?
* how does Thymeleaf work as a template engine with Spring Boot?
* how do I configure Spring Security?
* can I use a multi-module structure? see 54.9 Understanding how the Gradle plugin works
