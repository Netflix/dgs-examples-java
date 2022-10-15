package dev.vittorioexp.dgs.datafetcher;

import com.netflix.graphql.dgs.*;
import dev.vittorioexp.dgs.dataloader.PropertyFromAgencyDataLoader;
import dev.vittorioexp.dgs.model.Agency;
import dev.vittorioexp.dgs.model.Property;
import dev.vittorioexp.dgs.service.PropertyService;
import org.dataloader.DataLoader;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@DgsComponent
public class PropertyDataFetcher {

    @Autowired
    private PropertyService propertyService;

    @DgsQuery
    public List<Property> properties(@InputArgument List<Integer> ids) {
        return propertyService.getProperties(ids);
    }

    @DgsData(parentType = "Agency", field = "properties")
    public CompletableFuture<Property> propertiesFromAgency(DgsDataFetchingEnvironment dfe) {
        Agency agency = dfe.getSource();
        DataLoader<Integer, Property> dataLoader = dfe.getDataLoader(PropertyFromAgencyDataLoader.class);
        return dataLoader.load(agency.getId());
    }

}
