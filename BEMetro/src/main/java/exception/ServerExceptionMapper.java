package exception;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;

public class ServerExceptionMapper implements ExceptionMapper<CustomException> {

	private final ObjectMapper objectMapper = new ObjectMapper();

	private String convertObjectToJson(Object object) {
		try {
			return objectMapper.writeValueAsString(object);
		} catch (Exception e) {
			e.printStackTrace();
			return "{}";
		}
	}

	@Override
	public Response toResponse(CustomException exception) {
		String message;
		Status status = exception.getStatus();

		switch (status) {
			case CONFLICT :
				message = "Conflict detected. Please check your request. "
						+ exception.getMessage();
				break;
			case NOT_ACCEPTABLE :
				message = "The request cannot be accepted due to invalid or incomplete data. "
						+ exception.getMessage();
				break;
			case BAD_REQUEST :
				message = "The request contains invalid parameters. "
						+ exception.getMessage();
				break;
			case NOT_FOUND :
				message = "Invalid request parameter. "
						+ exception.getMessage();
				if (exception.getCause() != null
						&& exception.getCause().getCause() != null)
					message += exception.getCause().getCause().getMessage();
				break;
			case FORBIDDEN :
				message = "Forbidden: User is not authorized to borrow books due to excessive late returns. "
						+ exception.getMessage();
				break;
			case INTERNAL_SERVER_ERROR :
				message = "An internal server error occurred while processing your request. Please try again later or contact the system administrator for further assistance. "
						+ exception.getMessage();
				break;
			default :
				message = "[unhandled response code] " + exception;
		}
		ErrorResponse er = new ErrorResponse();
		er.setMessage(message);

		return Response.status(status).entity(convertObjectToJson(er))
				.type(MediaType.APPLICATION_JSON).build();
	}

}
