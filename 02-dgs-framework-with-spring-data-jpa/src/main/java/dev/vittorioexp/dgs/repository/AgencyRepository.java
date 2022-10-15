package dev.vittorioexp.dgs.repository;

import dev.vittorioexp.dgs.model.Agency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgencyRepository extends JpaRepository<Agency, Integer> {

    @Query("SELECT a FROM Agency a " +
            "WHERE ((:ids) IS NULL OR a.id IN (:ids)) " +
            "ORDER BY a.id ASC")
    List<Agency> findAllByIds(List<Integer> ids);
}
