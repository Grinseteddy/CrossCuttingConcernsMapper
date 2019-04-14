package com.annegret.servicepatterns.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@RestController
@EnableAutoConfiguration
@Configuration
public class MapperController {

    static Logger logger=LoggerFactory.getLogger(MapperController.class);

    @Value("${index}")
    private int index;

    @GetMapping(value = "mapping")
    public String mapped(@RequestParam("inputString") String inputString) throws JSONException {

        try {
            String returnString = mapp(inputString)+String.valueOf(index);
            logger.info(inputString+" --> "+returnString);
            return returnString;
        } catch (JSONException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Master data for input string "+inputString+" not found");
        }
    }




    private JSONObject Configuration(int configuration) throws JSONException {


        try {
            JSONObject mappingCharacter=getCharacterByIndex("http://localhost:8083/Character/", String.valueOf(configuration));
            return mappingCharacter;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please select correct configuration");
        }

    }

    private JSONObject getCharacterByIndex(String urlAsString, String parameter) throws IOException, JSONException {
        try {
            String urlParameter = urlAsString+parameter;
            URL url=new URL(urlParameter);
            HttpURLConnection conn= (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            if (conn.getResponseCode() != 200) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "According masterdata not found");
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;

            output = br.readLine();
            //TODO think about timeouts
            conn.disconnect();

            JSONObject jsonReturn=new JSONObject(output);

            return jsonReturn;

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Master data not available");
        }
    }

    private String mapp(String inputString) throws JSONException {

        try {
            if (inputString.length() > 0) {


                JSONObject toBeMapped = Configuration(index);
                String toBeMappedCapital = (String)toBeMapped.get("capital");
                String toBeMappedSmallLetter = (String) toBeMapped.get("smallLetter");

                JSONObject newCharacter= Configuration(index+1);
                String newCharacterCapital = (String)newCharacter.get("capital");
                String newCharacterSmallLetter =(String) newCharacter.get("smallLetter");

                String mappStringA = inputString.replace(toBeMappedCapital.charAt(0), newCharacterCapital.charAt(0));
                String mappStringB = mappStringA.replace(toBeMappedSmallLetter.charAt(0), newCharacterSmallLetter.charAt(0));

                return mappStringB;
            }
            return "";
        } catch (JSONException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Master data answer not well formed");
        }
    }
}
