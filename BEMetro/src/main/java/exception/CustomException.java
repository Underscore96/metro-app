package exception;

import jakarta.ws.rs.core.Response;

public class CustomException extends RuntimeException {
    private final Response.Status status;
    private final String message;
    
	private static final long serialVersionUID = 9111667021544834541L;

	public CustomException(String message, Response.Status status) {
        super(message);
        this.status = status;
        this.message = message;
	}
	
	  public Response.Status getStatus() {
	        return status;
	    }

	    public String getMessageError() {
	        return message;
	    }
}