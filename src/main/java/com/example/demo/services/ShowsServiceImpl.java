package com.example.demo.services;

import com.example.demo.generated.types.Show;
import com.example.demo.scalars.PageInfoScalar;
import graphql.relay.DefaultConnectionCursor;
import graphql.relay.DefaultPageInfo;
import graphql.relay.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ShowsServiceImpl implements ShowsService {
    final
    PageInfoScalar pageInfoScalar;

    public ShowsServiceImpl(PageInfoScalar pageInfoScalar) {
        this.pageInfoScalar = pageInfoScalar;
    }

    @Override
    public List<Show> shows() {
        PageInfo pageinfo = pageInfoScalar.serialize(new DefaultPageInfo(new DefaultConnectionCursor("test"), new DefaultConnectionCursor("test"), true, true));
        return Arrays.asList(
                Show.newBuilder().id(1).title("Stranger Things").releaseYear(2016).pageinfo(pageinfo).build(),
                Show.newBuilder().id(2).title("Ozark").releaseYear(2017).pageinfo(pageinfo).build(),
                Show.newBuilder().id(3).title("The Crown").releaseYear(2016).pageinfo(pageinfo).build(),
                // Test PageInfo Scalar serialization
                Show.newBuilder().id(4).title("Dead to Me").releaseYear(2019).pageinfo(pageinfo).build(),
                Show.newBuilder().id(5).title("Orange is the New Black").pageinfo(pageinfo).releaseYear(2013).build()
        );
    }
}
