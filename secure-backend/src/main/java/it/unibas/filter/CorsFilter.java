package it.unibas.filter;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;

@Provider
public class CorsFilter implements ContainerResponseFilter {
    @Override
    public void filter(ContainerRequestContext request, ContainerResponseContext response) {
        response.getHeaders().add("Access-Control-Allow-Origin", "*");
        response.getHeaders().add("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE");
        response.getHeaders().add("Access-Control-Allow-Headers", "Content-Type, x-ijt, Authorization");
        response.getHeaders().add("Access-Control-Expose-Headers", "Custom-Header");
        response.getHeaders().add("Access-Control-Max-Age", "86400"); // Cache per 24h
    }
}