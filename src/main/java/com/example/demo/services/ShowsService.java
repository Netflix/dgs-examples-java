package com.example.demo.services;


import com.example.demo.generated.types.Show;
import com.example.demo.types.InternalShow;

import java.util.List;

public interface ShowsService {
    List<InternalShow> shows();
}
