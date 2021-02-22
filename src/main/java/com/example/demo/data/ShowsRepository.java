package com.example.demo.data;


import com.example.demo.generated.types.Show;
import org.springframework.data.repository.CrudRepository;

public interface ShowsRepository extends CrudRepository<JpaShow, Integer> {
}
