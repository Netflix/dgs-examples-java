package dev.vittorioexp.dgs.utils;

import dev.vittorioexp.dgs.model.Agency;
import dev.vittorioexp.dgs.model.Property;
import dev.vittorioexp.dgs.model.PropertyInput;

import java.time.LocalDate;

public class EntityMapper {

    public static Property buildProperty(
            PropertyInput propertyInput,
            Property property,
            Agency agency
    ) {
        if (propertyInput.getName() != null)
            property.setName(propertyInput.getName());

        if (propertyInput.getType() != null)
            property.setType(propertyInput.getType());

        if (propertyInput.getLatitude() != null)
            property.setLatitude(propertyInput.getLatitude());

        if (propertyInput.getLongitude() != null)
            property.setLongitude(propertyInput.getLongitude());

        if (propertyInput.getPurchaseDate() != null)
            property.setPurchaseDate(LocalDate.parse(propertyInput.getPurchaseDate()));

        if (propertyInput.getAgencyId() != null)
            property.setAgency(agency);

        return property;
    }
}
