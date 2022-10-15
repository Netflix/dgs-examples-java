package dev.vittorioexp.dgs.service;

import com.netflix.graphql.types.errors.ErrorType;
import dev.vittorioexp.dgs.error.GraphQLRuntimeException;
import dev.vittorioexp.dgs.model.Property;
import dev.vittorioexp.dgs.model.PropertyInput;
import dev.vittorioexp.dgs.repository.AgencyRepository;
import dev.vittorioexp.dgs.repository.PropertyRepository;
import dev.vittorioexp.dgs.utils.DataValidator;
import dev.vittorioexp.dgs.utils.EntityExistenceChecker;
import dev.vittorioexp.dgs.utils.EntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private AgencyRepository agencyRepository;

    @Autowired
    private EntityExistenceChecker entityExistenceChecker;

    private void checkPropertyInputBeforeCreate(PropertyInput property) {

        // check mandatory fields
        if (property.getName() == null)
            throw new GraphQLRuntimeException("Property must have non null 'name' field", ErrorType.BAD_REQUEST);

        if (property.getAgencyId() == null)
            throw new GraphQLRuntimeException("Property must have non null 'agencyId' field", ErrorType.BAD_REQUEST);

        // if nullable fields are presents, check if they are valid
        DataValidator.validateNullableFields(property);

        // Throw an exception if the Agency does not exist
        entityExistenceChecker.assertAgencyExistsById(property.getAgencyId());
    }

    private void checkPropertyInputBeforeUpdate(PropertyInput property) {

        if (property.getId() == null)
            throw new GraphQLRuntimeException("Property must have non null 'id' field", ErrorType.BAD_REQUEST);

        // if nullable fields are presents, check if they are valid
        DataValidator.validateNullableFields(property);

        // Throw an exception if the Property does not exist
        entityExistenceChecker.assertPropertyExistsById(property.getId());

        // Throw an exception if the Agency does not exist
        if (property.getAgencyId() != null)
            entityExistenceChecker.assertAgencyExistsById(property.getAgencyId());

    }

    private void checkPropertyBeforeDelete(Integer id) {
        if (id == null)
            throw new GraphQLRuntimeException("'id' field cannot be null", ErrorType.BAD_REQUEST);

        // Throw an exception if the Property does not exist
        entityExistenceChecker.assertPropertyExistsById(id);
    }

    private void checkPropertyBeforeSave(Property property) {

        if (property == null)
            throw new GraphQLRuntimeException("Property is null and cannot be saved", ErrorType.INTERNAL);

        if (property.getName() == null)
            throw new GraphQLRuntimeException("Property 'name' field is null", ErrorType.INTERNAL);

        if (property.getAgency() == null)
            throw new GraphQLRuntimeException("Property 'agency' field is null", ErrorType.INTERNAL);
    }


    public List<Property> getProperties(List<Integer> ids) {
        return propertyRepository.findAllByIds(ids);
    }

    public Map<Integer, List<Property>> getPropertiesByAgencyIds(Set<Integer> ids) {
        return propertyRepository
                .findAllByAgencyIds(ids)
                .stream()
                .collect(
                        Collectors.groupingBy(
                                PropertyRepository.PropertyWithAgencyId::getAgencyId,
                                Collectors.mapping(
                                        PropertyRepository.PropertyWithAgencyId::getProperty,
                                        Collectors.toList()
                                )
                        )
                );
    }

    public List<Property> addProperties(List<PropertyInput> properties) {
        return properties
                .stream()
                .peek(this::checkPropertyInputBeforeCreate)
                .map(propertyInput ->
                        EntityMapper.buildProperty(
                                propertyInput,
                                Property.builder().build(),
                                propertyInput.getAgencyId() != null ? agencyRepository.findById(propertyInput.getAgencyId()).orElse(null) : null
                        ))
                .peek(this::checkPropertyBeforeSave)
                .map(propertyRepository::save)
                .collect(Collectors.toList());
    }

    public List<Property> updateProperties(List<PropertyInput> properties) {
        return properties
                .stream()
                .peek(this::checkPropertyInputBeforeUpdate)
                .map(propertyInput ->
                        EntityMapper.buildProperty(
                                propertyInput,
                                propertyRepository.findById(propertyInput.getId()).orElse(null),
                                propertyInput.getAgencyId() != null ? agencyRepository.findById(propertyInput.getAgencyId()).orElse(null) : null
                        ))
                .peek(this::checkPropertyBeforeSave)
                .map(propertyRepository::save)
                .collect(Collectors.toList());
    }

    public List<Integer> deleteProperties(List<Integer> ids) {
        return ids
                .stream()
                .peek(this::checkPropertyBeforeDelete)
                .peek(propertyRepository::deleteById)
                .collect(Collectors.toList());
    }
}
