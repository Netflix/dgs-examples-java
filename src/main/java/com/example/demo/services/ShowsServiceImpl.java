package com.example.demo.services;

import com.example.demo.generated.types.Show;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ShowsServiceImpl implements ShowsService {
    @Override
    public List<Show> shows() {
        return Arrays.asList(
                Show.newBuilder().id(1).title("Stranger Things").releaseYear(2016).build(),
                Show.newBuilder().id(2).title("Ozark").releaseYear(2017).build(),
                Show.newBuilder().id(3).title("The Crown").releaseYear(2016).build(),
                Show.newBuilder().id(4).title("Dead to Me").releaseYear(2019).build(),
                Show.newBuilder().id(5).title("Orange is the New Black").releaseYear(2013).build()
        );
    }
}
