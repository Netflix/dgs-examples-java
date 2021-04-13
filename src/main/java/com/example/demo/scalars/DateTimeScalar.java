package com.example.demo.scalars;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsRuntimeWiring;
import graphql.scalars.ExtendedScalars;
import graphql.schema.idl.RuntimeWiring;

/**
 * graphql-java provides optional scalars in the graphql-java-extended-scalars library.
 * We can wire a scalar from this library by adding the scalar to the RuntimeWiring.
 * Please consider {@link <a href="https://github.com/Netflix/dgs-framework/pull/242">
 * https://github.com/Netflix/dgs-framework/pull/242</a>} where the scalars are automatically
 * registered and you might not need to do this unless you have a custom scalar.
 */
@DgsComponent
public class DateTimeScalar {

    @DgsRuntimeWiring
    public RuntimeWiring.Builder addScalar(RuntimeWiring.Builder builder) {
        return builder.scalar(ExtendedScalars.DateTime);
    }
}
