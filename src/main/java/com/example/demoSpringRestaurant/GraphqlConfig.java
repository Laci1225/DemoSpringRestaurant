package com.example.demoSpringRestaurant;

import com.example.demoSpringRestaurant.exception.DocumentNotFoundException;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;


@Component
public class GraphqlConfig extends DataFetcherExceptionResolverAdapter {


    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
        Throwable t = NestedExceptionUtils.getMostSpecificCause(ex);

        if (t instanceof DocumentNotFoundException customException) {
            return GraphqlErrorBuilder.newError(env)
                    .errorType(ErrorType.NOT_FOUND)
                    .message(customException.getMessage())
                    .build();
        }

        if( t instanceof ConstraintViolationException constraintViolationException){
            return validationError(constraintViolationException, env);
        }

        // other exceptions not yet caught
        return GraphqlErrorBuilder.newError(env)
                .message("Error occurred: Ensure request is valid ")
                .errorType(ErrorType.BAD_REQUEST)
                .build();
    }

    private GraphQLError validationError(ConstraintViolationException exception, DataFetchingEnvironment env){
        String invalidFields = exception.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining("\n"));

        return GraphqlErrorBuilder.newError(env)
                .errorType(ErrorType.BAD_REQUEST)
                .message(invalidFields)
                .build();
    }
}
