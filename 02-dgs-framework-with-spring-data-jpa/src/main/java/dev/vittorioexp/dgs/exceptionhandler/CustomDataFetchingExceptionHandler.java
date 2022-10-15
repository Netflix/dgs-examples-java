package dev.vittorioexp.dgs.exceptionhandler;

import com.netflix.graphql.types.errors.TypedGraphQLError;
import dev.vittorioexp.dgs.error.GraphQLRuntimeException;
import graphql.GraphQLError;
import graphql.execution.DataFetcherExceptionHandler;
import graphql.execution.DataFetcherExceptionHandlerParameters;
import graphql.execution.DataFetcherExceptionHandlerResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class CustomDataFetchingExceptionHandler implements DataFetcherExceptionHandler {

    @Override
    public CompletableFuture<DataFetcherExceptionHandlerResult> handleException(
            DataFetcherExceptionHandlerParameters handlerParameters
    ) {
        if (handlerParameters.getException() instanceof GraphQLRuntimeException graphQLRuntimeException) {

            GraphQLError graphqlError = TypedGraphQLError.newInternalErrorBuilder()
                    .message(graphQLRuntimeException.getMessage())
                    .location(handlerParameters.getSourceLocation())
                    .path(handlerParameters.getPath())
                    .errorType(graphQLRuntimeException.getErrorType())
                    .build();

            DataFetcherExceptionHandlerResult result = DataFetcherExceptionHandlerResult.newResult()
                    .error(graphqlError)
                    .build();

            return CompletableFuture.completedFuture(result);
        } else {
            return DataFetcherExceptionHandler.super.handleException(handlerParameters);
        }
    }
}


