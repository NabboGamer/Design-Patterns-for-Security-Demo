package it.unibas.filter;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;

@Provider
public class LoggingResponseFilter implements ContainerResponseFilter {

    private static final Logger logger = LoggerFactory.getLogger(LoggingResponseFilter.class);

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        String method = requestContext.getMethod();
        String requestUri = requestContext.getUriInfo().getPath();
        int status = responseContext.getStatus();
        if (status == Response.Status.OK.getStatusCode()) {
            logger.info("Request {} '{}' - Response status: {}", method, requestUri, status);
        } else if (status == Response.Status.BAD_REQUEST.getStatusCode() || status == Response.Status.UNAUTHORIZED.getStatusCode()) {
            logger.warn("Request {} '{}' - Response status: {}", method, requestUri, status);
        } else if (status == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()){
            logger.error("Request {} '{}' - Response status: {}", method, requestUri, status);
        }
    }
}

