package dev.vittorioexp.dgs.error;


import com.netflix.graphql.types.errors.ErrorType;

public class GraphQLRuntimeException extends RuntimeException {

    private final ErrorType errorType;

    public GraphQLRuntimeException(String errorMessage, ErrorType errorType) {
        super(errorMessage);
        this.errorType = errorType;
    }

    public ErrorType getErrorType() {
        return errorType;
    }

}