package com.example.demo;

import com.example.demo.generated.types.Image;
import com.jayway.jsonpath.TypeRef;
import com.netflix.graphql.dgs.DgsQueryExecutor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class ArtworkUploadDataFetcherTest {

    @Autowired
    DgsQueryExecutor dgsQueryExecutor;

    @Test
    void addArtwork() {
        int showId = new Random().nextInt();

        Map<String, Object> map = new HashMap<String, Object>() {{
            put("showId", showId);
            put("upload", new MockMultipartFile("test", "test.file", "text/plain", "test".getBytes()));
        }};

        String mutation = "mutation addArtwork($showId:Int!, $upload:Upload!) { addArtwork(showId:$showId, upload:$upload) {url} }";

        List<Image> result = dgsQueryExecutor.executeAndExtractJsonPathAsObject(
                mutation,
                "data.addArtwork",
                map,
                new TypeRef<List<Image>>() {
                }
        );

        assertThat(result.size()).isNotZero();
        assertThat(result.get(0).getUrl()).contains(String.valueOf(showId));
    }
}