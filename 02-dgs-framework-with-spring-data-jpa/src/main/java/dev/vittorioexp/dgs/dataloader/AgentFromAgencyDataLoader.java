package dev.vittorioexp.dgs.dataloader;

import com.netflix.graphql.dgs.DgsDataLoader;
import dev.vittorioexp.dgs.model.Agent;
import dev.vittorioexp.dgs.service.AgentService;
import org.dataloader.MappedBatchLoader;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;


@DgsDataLoader
public class AgentFromAgencyDataLoader implements MappedBatchLoader<Integer, List<Agent>> {

    @Autowired
    AgentService agentService;

    @Override
    public CompletionStage<Map<Integer, List<Agent>>> load(Set<Integer> keys) {
        return CompletableFuture.supplyAsync(
                () -> agentService.getAgentsByAgencyIds(keys)
        );
    }
}
