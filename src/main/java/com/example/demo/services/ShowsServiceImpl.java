package com.example.demo.services;

import com.example.demo.generated.types.Show;
import com.example.demo.types.InternalShow;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShowsServiceImpl implements ShowsService {
    @Override
    public List<InternalShow> shows() {
        return List.of(
                new InternalShow(1, Show.newBuilder().title("Stranger Things").releaseYear(2016).build()),
                new InternalShow(2, Show.newBuilder().title("Ozark").releaseYear(2017).build()),
                new InternalShow(3, Show.newBuilder().title("The Crown").releaseYear(2016).build()),
                new InternalShow(4, Show.newBuilder().title("Dead to Me").releaseYear(2019).build()),
                new InternalShow(5, Show.newBuilder().title("Orange is the New Black").releaseYear(2013).build())
        );
    }
}
