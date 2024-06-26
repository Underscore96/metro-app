package presentation.provider;

import java.io.IOException;

import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.ext.Provider;

@Provider
@Priority(Priorities.HEADER_DECORATOR)
public class ProviderFilter implements ContainerResponseFilter {
	@Override
	public void filter(ContainerRequestContext requestContext,
			ContainerResponseContext responseContext) throws IOException {
		final MultivaluedMap<String, Object> headers = responseContext
				.getHeaders();
		headers.add("Access-Control-Allow-Methods",
				"GET, POST, PUT, OPTIONS, DELETE");
		headers.add("Access-Control-Allow-Origin", "*");
		headers.add("Access-Control-Allow-Headers",
				"Origin, Content-Type, Accept, Authorization");

		if (requestContext.getMethod().equalsIgnoreCase("OPTIONS")) {
			headers.add("Access-Control-Allow-Headers", requestContext
					.getHeaderString("Access-Control-Request-Headers"));
		}
	}
}