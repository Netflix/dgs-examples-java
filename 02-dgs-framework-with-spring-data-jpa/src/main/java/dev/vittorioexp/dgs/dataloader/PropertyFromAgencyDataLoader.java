package dev.vittorioexp.dgs.dataloader;

import com.netflix.graphql.dgs.DgsDataLoader;
import dev.vittorioexp.dgs.model.Property;
import dev.vittorioexp.dgs.service.PropertyService;
import org.dataloader.MappedBatchLoader;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;


@DgsDataLoader
public class PropertyFromAgencyDataLoader implements MappedBatchLoader<Integer, List<Property>> {

    @Autowired
    PropertyService propertyService;

    @Override
    public CompletionStage<Map<Integer, List<Property>>> load(Set<Integer> keys) {
        return CompletableFuture.supplyAsync(
                () -> propertyService.getPropertiesByAgencyIds(keys)
        );
    }
}
