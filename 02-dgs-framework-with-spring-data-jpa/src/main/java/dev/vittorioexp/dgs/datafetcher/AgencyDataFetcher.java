package dev.vittorioexp.dgs.datafetcher;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import dev.vittorioexp.dgs.model.Agency;
import dev.vittorioexp.dgs.service.AgencyService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@DgsComponent
public class AgencyDataFetcher {

    @Autowired
    private AgencyService agencyService;

    @DgsQuery
    public List<Agency> agencies(@InputArgument List<Integer> ids) {
        return agencyService.getAgencies(ids);
    }

}
