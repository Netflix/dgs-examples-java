package dev.vittorioexp.dgs.datafetcher;

import com.netflix.graphql.dgs.*;
import dev.vittorioexp.dgs.dataloader.AgentFromAgencyDataLoader;
import dev.vittorioexp.dgs.model.Agency;
import dev.vittorioexp.dgs.model.Agent;
import dev.vittorioexp.dgs.service.AgentService;
import org.dataloader.DataLoader;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@DgsComponent
public class AgentDataFetcher {

    @Autowired
    private AgentService agentService;

    @DgsQuery
    public List<Agent> agents(@InputArgument List<String> ids) {
        return agentService.getAgents(ids);
    }

    @DgsData(parentType = "Agency", field = "agents")
    public CompletableFuture<Agent> agentsFromAgency(DgsDataFetchingEnvironment dfe) {
        Agency agency = dfe.getSource();
        DataLoader<Integer, Agent> dataLoader = dfe.getDataLoader(AgentFromAgencyDataLoader.class);
        return dataLoader.load(agency.getId());
    }

}
