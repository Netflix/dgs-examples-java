package dev.vittorioexp.dgs.service;

import dev.vittorioexp.dgs.model.Agent;
import dev.vittorioexp.dgs.repository.AgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AgentService {

    @Autowired
    private AgentRepository agentRepository;

    public List<Agent> getAgents(List<String> ids) {
        return agentRepository.findAllByIds(ids);
    }

    public Map<Integer, List<Agent>> getAgentsByAgencyIds(Set<Integer> ids) {
        return agentRepository
                .findAllByAgencyIds(ids)
                .stream()
                .collect(
                        Collectors.groupingBy(
                                AgentRepository.AgentWithAgencyId::getAgencyId,
                                Collectors.mapping(
                                        AgentRepository.AgentWithAgencyId::getAgent,
                                        Collectors.toList()
                                )
                        )
                );
    }

}
