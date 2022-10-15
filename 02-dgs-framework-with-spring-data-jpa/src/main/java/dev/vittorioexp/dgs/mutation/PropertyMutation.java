package dev.vittorioexp.dgs.mutation;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import dev.vittorioexp.dgs.model.Property;
import dev.vittorioexp.dgs.model.PropertyInput;
import dev.vittorioexp.dgs.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@DgsComponent
public class PropertyMutation {

    @Autowired
    private PropertyService propertyService;

    @DgsMutation
    public List<Property> createProperties(@InputArgument List<PropertyInput> properties) {
        return propertyService.addProperties(properties);
    }

    @DgsMutation
    public List<Property> updateProperties(@InputArgument List<PropertyInput> properties) {
        return propertyService.updateProperties(properties);
    }

    @DgsMutation
    public List<Integer> deleteProperties(@InputArgument List<Integer> ids) {
        return propertyService.deleteProperties(ids);
    }

}
