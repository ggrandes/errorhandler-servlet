# ErrorHandler Servlet

Custom Error Handler in a Servlet container like Tomcat. Open Source Java project under Apache License v2.0

### Current Stable Version is [1.0.0](https://search.maven.org/#search|ga|1|g%3Aorg.javastack%20a%3Aerrorhandler-servlet)

---

## DOC

#### Usage Example

```xml
<!-- ErrorHandler Servlet -->
<!-- tomcat/conf/web.xml or WEB-INF/web.xml -->
<servlet>
    <servlet-name>ErrorHandlerServlet</servlet-name>
    <servlet-class>org.javastack.servlet.ErrorHandlerServlet</servlet-class>
    <init-param>
      <param-name>400:BAD_REQUEST</param-name>
      <param-value>Your browser (or proxy) sent a request that this server could not understand.</param-value>
    </init-param>
    <init-param>
      <param-name>401:UNAUTHORIZED</param-name>
      <param-value>This server could not verify that you are authorized to access</param-value>
    </init-param>
    <init-param>
      <param-name>403:FORBIDDEN</param-name>
      <param-value>You don't have permission to access the requested object.</param-value>
    </init-param>
    <init-param>
      <param-name>404:NOT_FOUND</param-name>
      <param-value>The requested URL was not found on this server.</param-value>
    </init-param>
    <init-param>
      <param-name>405:METHOD_NOT_ALLOWED</param-name>
      <param-value>The method is not allowed for the requested URL.</param-value>
    </init-param>
    <init-param>
      <param-name>408:REQUEST_TIME_OUT</param-name>
      <param-value>The server closed the network connection because the browser didn't finish the request within the specified time.</param-value>
    </init-param>
    <init-param>
      <param-name>410:GONE</param-name>
      <param-value>The requested URL is no longer available on this server and there is no forwarding address.</param-value>
    </init-param>
    <init-param>
      <param-name>411:LENGTH_REQUIRED</param-name>
      <param-value>The request method requires a valid Content-Length header.</param-value>
    </init-param>
    <init-param>
      <param-name>412:PRECONDITION_FAILED</param-name>
      <param-value>The precondition on the request for the URL failed positive evaluation.</param-value>
    </init-param>
    <init-param>
      <param-name>413:REQUEST_ENTITY_TOO_LARGE</param-name>
      <param-value>The method does not allow the data transmitted, or the data volume exceeds the capacity limit.</param-value>
    </init-param>
    <init-param>
      <param-name>414:REQUEST_URI_TOO_LARGE</param-name>
      <param-value>The length of the requested URL exceeds the capacity limit for</param-value>
    </init-param>
    <init-param>
      <param-name>415:UNSUPPORTED_MEDIA_TYPE</param-name>
      <param-value>The server does not support the media type transmitted in the request.</param-value>
    </init-param>
    <init-param>
      <param-name>500:INTERNAL_SERVER_ERROR</param-name>
      <param-value>The server encountered an internal error and was unable to complete your request. Either the server is overloaded or there was an error in a CGI script.</param-value>
    </init-param>
    <init-param>
      <param-name>501:NOT_IMPLEMENTED</param-name>
      <param-value>The server does not support the action requested by the browser.</param-value>
    </init-param>
    <init-param>
      <param-name>502:BAD_GATEWAY</param-name>
      <param-value>The proxy server received an invalid response from an upstream server.</param-value>
    </init-param>
    <init-param>
      <param-name>503:SERVICE_UNAVAILABLE</param-name>
      <param-value>The server is temporarily unable to service your request due to maintenance downtime or capacity problems. Please try again later.</param-value>
    </init-param>
    <init-param>
      <param-name>504:GATEWAY_TIMEOUT</param-name>
      <param-value>The server was acting as a gateway or proxy and did not receive a timely response from the upstream server.</param-value>
    </init-param>
    <init-param>
      <param-name>506:VARIANT_ALSO_VARIES</param-name>
      <param-value>A variant for the requested entity is itself a negotiable resource. Access not possible.</param-value>
    </init-param>
    <init-param>
      <param-name>508:LOOP_DETECTED</param-name>
      <param-value>The server detected an infinite loop while processing the request.</param-value>
    </init-param>
    <load-on-startup>1</load-on-startup>
</servlet>
<servlet-mapping>
    <servlet-name>ErrorHandlerServlet</servlet-name>
    <url-pattern>/ErrorHandlerServlet</url-pattern>
</servlet-mapping>

<error-page>
    <location>/ErrorHandlerServlet</location>
</error-page>
```

---

## MAVEN

Add the dependency to your pom.xml:

    <dependency>
        <groupId>org.javastack</groupId>
        <artifactId>errorhandler-servlet</artifactId>
        <version>1.0.0</version>
    </dependency>

---
Inspired in [ErrorDocument](http://httpd.apache.org/docs/2.4/mod/core.html#errordocument), this code is Java-minimalistic version.
