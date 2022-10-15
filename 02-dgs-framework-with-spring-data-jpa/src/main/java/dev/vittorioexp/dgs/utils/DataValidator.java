package dev.vittorioexp.dgs.utils;

import com.netflix.graphql.types.errors.ErrorType;
import dev.vittorioexp.dgs.error.GraphQLRuntimeException;
import dev.vittorioexp.dgs.model.PropertyInput;

import java.time.LocalDate;

public class DataValidator {

    public static boolean isValidLocalDate(Object date) {
        try {
            LocalDate.parse(date.toString());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static void validateNullableFields(PropertyInput propertyInput) {
        if (propertyInput.getPurchaseDate() != null && !DataValidator.isValidLocalDate(propertyInput.getPurchaseDate()))
            throw new GraphQLRuntimeException("Property must have a valid 'purchaseDate' date field (YYYY-MM-DD)", ErrorType.BAD_REQUEST);
    }
}
