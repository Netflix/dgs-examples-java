package dev.vittorioexp.dgs.utils;

import com.netflix.graphql.types.errors.ErrorType;
import dev.vittorioexp.dgs.error.GraphQLRuntimeException;
import dev.vittorioexp.dgs.repository.AgencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EntityExistenceChecker {

    @Autowired
    private AgencyRepository agencyRepository;

    public void assertAgencyExistsById(Integer agencyId) {
        agencyRepository
                .findById(agencyId)
                .orElseThrow(
                        () -> new GraphQLRuntimeException(
                                "Agency with ID " + agencyId + " could not be fetched", ErrorType.NOT_FOUND)
                );
    }

    public void assertPropertyExistsById(Integer propertyId) {
        agencyRepository
                .findById(propertyId)
                .orElseThrow(
                        () -> new GraphQLRuntimeException(
                                "Property with ID " + propertyId + " could not be fetched", ErrorType.NOT_FOUND)
                );
    }
}
