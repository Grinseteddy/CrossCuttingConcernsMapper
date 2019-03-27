package com.annegret.servicepatterns.mapper;

import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.NotNull;

@RestController
public class MapperController {

    @GetMapping(value = "mapping")
    public String mapped(@NotNull @RequestParam("inputString") String inputString) {
        return map(inputString);
    }

    private String map(String inputString) {
        return inputString.replace('A', 'B').replace('a', 'b');
    }

}
