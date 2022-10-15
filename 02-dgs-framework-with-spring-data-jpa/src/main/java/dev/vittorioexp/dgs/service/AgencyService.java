package dev.vittorioexp.dgs.service;

import dev.vittorioexp.dgs.model.Agency;
import dev.vittorioexp.dgs.repository.AgencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgencyService {

    @Autowired
    private AgencyRepository agencyRepository;

    public List<Agency> getAgencies(List<Integer> ids) {
        return agencyRepository.findAllByIds(ids);
    }

}
