package exception;

public class ErrorMessages {

	ErrorMessages() {
	}

	public static final String UNIQUE_CONSTRAINT = "Unique constraint violated for %s";
	public static final String PROPERTY_VALUE_EXCEPTION = "The field %s cannot be null";
	public static final String HIBERNATE_EXCEPTION = "An error occurred while accessing the database.\n Please contact the system administrator for assistance.";
	public static final String NULL_POINTER_EXCEPTION = "Null value encountered in %s. %s or one not nullable attribute is null.\n Please check for null references.";
	public static final String ILLEGAL_ARGUMENT_EXCEPTION = "Invalid argument provided: %s or one attributo is not valid.";
	public static final String INDEX_OUT_OF_BOUNDS_EXCEPTION = "An error occurred due to an index being out of bounds. Please check your input and try again.";
	public static final String FERMATA_FE_NULL_POINTER_EXCEPTION = "The field %s or %s cannot be null. Please check your input and try again.";
}