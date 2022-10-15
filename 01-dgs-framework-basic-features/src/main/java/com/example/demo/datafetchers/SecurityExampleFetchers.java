package com.example.demo.datafetchers;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import org.springframework.security.access.annotation.Secured;

@DgsComponent
public class SecurityExampleFetchers {

    @DgsQuery
    public String secureNone() {
        return "Hello to everyone";
    }

    @DgsQuery
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public String secureUser() {
        return "Hello to users or admins";
    }

    @DgsQuery
    @Secured({"ROLE_ADMIN"})
    public String secureAdmin() {
        return "Hello to admins only";
    }
}
