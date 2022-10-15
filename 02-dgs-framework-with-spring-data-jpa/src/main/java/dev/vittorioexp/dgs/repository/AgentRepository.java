package dev.vittorioexp.dgs.repository;

import dev.vittorioexp.dgs.model.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface AgentRepository extends JpaRepository<Agent, Integer> {

    @Query("SELECT a FROM Agent a " +
            "WHERE ((:ids) IS NULL OR a.id IN (:ids)) " +
            "ORDER BY a.id ASC")
    List<Agent> findAllByIds(List<String> ids);


    @Query("SELECT agency.id as agencyId, a as agent " +
            "FROM Agent a JOIN a.agency agency " +
            "WHERE agency.id IN (:ids)")
    List<AgentWithAgencyId> findAllByAgencyIds(Set<Integer> ids);

    interface AgentWithAgencyId {
        Agent getAgent();

        Integer getAgencyId();
    }
}
