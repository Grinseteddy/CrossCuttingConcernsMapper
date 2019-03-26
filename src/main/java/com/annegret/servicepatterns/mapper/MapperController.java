package com.annegret.servicepatterns.mapper;

import org.springframework.web.bind.annotation.*;

@RestController

public class MapperController {
    @GetMapping(value = "mapping")
    public String mapped(@RequestParam("inputString") String inputString) {
        String returnString = mapp (inputString);
        return returnString;
    }

    private String mapp(String inputString) {

        if (inputString.length()>0) {

            String mappStringA = inputString.replace('A', 'B');
            String mappStringB=  mappStringA.replace('a', 'b');

            return mappStringB;
        }
        return "";
    }
}
