package dev.vittorioexp.dgs.repository;

import dev.vittorioexp.dgs.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Integer> {

    @Query("SELECT p FROM Property p " +
            "WHERE ((:ids) IS NULL OR p.id IN (:ids)) " +
            "ORDER BY p.id ASC")
    List<Property> findAllByIds(List<Integer> ids);


    @Query("SELECT agency.id as agencyId, p as property " +
            "FROM Property p JOIN p.agency agency " +
            "WHERE agency.id IN (:ids)")
    List<PropertyWithAgencyId> findAllByAgencyIds(Set<Integer> ids);

    interface PropertyWithAgencyId {
        Property getProperty();

        Integer getAgencyId();
    }
}
