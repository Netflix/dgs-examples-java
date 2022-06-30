package com.example.demo;

import com.netflix.graphql.dgs.DgsQueryExecutor;
import com.netflix.graphql.dgs.exceptions.QueryException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class SecurityExampleFetchersTest {

    @Autowired
    DgsQueryExecutor dgsQueryExecutor;

    @Test
    void secureNone() {
        String result = dgsQueryExecutor.executeAndExtractJsonPathAsObject(
                " { secureNone }",
                "data.secureNone",
                String.class);

        assertThat(result).isEqualTo("Hello to everyone");
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    void secureUserWithUser() {
        String result = dgsQueryExecutor.executeAndExtractJsonPathAsObject(
                " { secureUser }",
                "data.secureUser",
                String.class
        );

        assertThat(result).isEqualTo("Hello to users or admins");
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = {"ADMIN"})
    void secureUserWithAdmin() {
        String result = dgsQueryExecutor.executeAndExtractJsonPathAsObject(
                " { secureUser }",
                "data.secureUser",
                String.class
        );

        assertThat(result).isEqualTo("Hello to users or admins");
    }

    @Test
    void secureUserWithNone() {
        assertThrows(QueryException.class, () -> {
            dgsQueryExecutor.executeAndExtractJsonPathAsObject(
                    " { secureUser }",
                    "data.secureUser",
                    String.class
            );
        });
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = {"ADMIN"})
    void secureAdminWithAdmin() {
        String result = dgsQueryExecutor.executeAndExtractJsonPathAsObject(
                " { secureAdmin }",
                "data.secureAdmin",
                String.class
        );

        assertThat(result).isEqualTo("Hello to admins only");
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    void secureAdminWithUser() {
        assertThrows(QueryException.class, () -> {
            dgsQueryExecutor.executeAndExtractJsonPathAsObject(
                    " { secureAdmin }",
                    "data.secureAdmin",
                    String.class
            );
        });
    }

    @Test
    void secureAdminWithNone() {
        assertThrows(QueryException.class, () -> {
            dgsQueryExecutor.executeAndExtractJsonPathAsObject(
                    " { secureAdmin }",
                    "data.secureAdmin",
                    String.class
            );
        });
    }
}
