# Dropwizard

Showcase of a simple dropwizard project, trying out a few functionalities

**Build & Run** *./gradlew run* will start the application

**First Service: Hello World** *http://localhost:8080/hello-world?name=mattia* is an example of a basic service. See HelloWorldResource for the configuration

**Connection Pool** I've registered a connection pool as a managed resource; see DatabaseConnectionPool. Dropwizard will take care of calling start and stop on the managed resource. When you start and stop the app you can see in the logs that the connection pool is being initialised.
Uses BoneCP as connection pool implementation

**Custom Health Checks** I've created a custom health check to monitor the state of the connection pool; see DatabaseHealthCheck. It gets a connection from the pool and checks that it's valid. Visit *http://localhost:8081/healthcheck* to see the check in action

**Authentication & Authorization** For authentication to work you need an AuthenticationProvider and an Authenticator. The AuthenticationProvider is responsible for extracting the user credentials from the request. The Authenticator receives the credentials and creates a User object.
HelloAuthenticationResource exposes services to demonstrate two different strategies for authenticating users:
* *dropwizard's default authentication*: /hello-authentication/noRole injects the User through the @Auth annotation; that works because we've registered the dropwizard's BasicAuthProvider, which gets the user credentials from the http basic auth header
* *custom authorization roles*: /hello-authentication/role1 and /hello-authentication/role2 demonstrate how to restrict access based on user roles. We register a custom ServerRestrictedToProvider that reads the configuration from the RestrictedTo annotation. 
When the service receives a request the provider uses the authenticator to authenticate the user and then checks that it's allowed to access the service.

**Views & Static files** I've registered some AssetsBundles to serve html pages (http://localhost:8080/views/index.html), js (http://localhost:8080/js/main.js) and css (http://localhost:8080/css/main.css)

### Resources
* [Dropwizard official docs](http://dropwizard.readthedocs.org/en/latest/getting-started.html)
* [Dropwizard and Gradle](https://github.com/gini/dropwizard-gradle)
