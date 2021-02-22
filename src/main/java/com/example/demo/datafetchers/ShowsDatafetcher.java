package com.example.demo.datafetchers;

import com.example.demo.data.JpaShow;
import com.example.demo.data.ShowsRepository;
import com.example.demo.generated.DgsConstants;
import com.example.demo.generated.types.Show;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@DgsComponent
public class ShowsDatafetcher {
//    private final ShowsService showsService;

    private final ShowsRepository showsService;

    public ShowsDatafetcher(ShowsRepository showsRepository) {
        this.showsService = showsRepository;
    }

    /**
     * This datafetcher resolves the shows field on Query.
     * It uses an @InputArgument to get the titleFilter from the Query if one is defined.
     */
    @DgsData(parentType = DgsConstants.QUERY_TYPE, field = DgsConstants.QUERY.Shows)
    public List<JpaShow> shows(@InputArgument("titleFilter") String titleFilter) {
        Iterable<JpaShow> all = showsService.findAll();
        if (titleFilter == null) {
            return StreamSupport.stream(all.spliterator(), false).collect(Collectors.toList());
        }

        return StreamSupport.stream(all.spliterator(), false)
                .filter(s -> s.getTitle().contains(titleFilter)).collect(Collectors.toList());
    }
}
