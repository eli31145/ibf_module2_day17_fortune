package Day17Practice.fortunecookie.controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Day17Practice.fortunecookie.services.FortuneCookie;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
//imported to use MediaType 
import org.springframework.http.MediaType;

import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(path="/cookies", produces=MediaType.APPLICATION_JSON_VALUE)
public class FortuneController {
        private int count = 1;
        private static final int maxCount = 10;
        Logger logger = Logger.getLogger(FortuneController.class.getName());

        @Autowired
        private FortuneCookie fortuneCookie;

        @GetMapping
        //to be safe, use <String> because string will accept everything
        //do the error handling yourself instead of Spring
        public ResponseEntity<String> getCookies(
            @RequestParam(defaultValue = "1") Integer count){
                /* while (count < maxCount){
                    this.count = count; 
                }*/
            long startTime = System.currentTimeMillis();
                //METHOD 1:
                if ((count < 1) || (count >10))
                    return
                        ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Json.createObjectBuilder()
                                .add("error", "count must be between 1 & 10")
                                .build()
                                .toString());   
                
                                /* 
                                if...
                                JsonObject errorObj = Json.createObjectBuilder()
                                    .add("error", "count problem");
                                     .build();
                                        return /*
         //METHOD 2:
            ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("count must be between 1 & 10 inclusive");
        //METHOD 3:
            ResponseEntity.badRequest().body("count must be between 1 & 10 inclusive");
            return re; */

                List<String> cookies = this.fortuneCookie.getCookies(count);
                JsonArrayBuilder jsonArr = Json.createArrayBuilder();
                
                /* List...
                        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
                        for (String c: cookies)
                        arrBuilder.add(c);
                    JsonObjectBuilder jsonObj = jsonCreateObjectBuilder(); */
                cookies.parallelStream()
                    .forEach(v->jsonArr.add(v));

                JsonObjectBuilder jsonObj = Json.createObjectBuilder()
                    .add("cookies", jsonArr);
                //.add("timestamp", System.currentTimeMillis());
                
                long endTime = System.currentTimeMillis();
                logger.log(Level.INFO,String.format("Elapsed time -> %s", (endTime - startTime)));
    
                return ResponseEntity.ok(jsonObj.build().toString()); 

            }
        
        

}


//accumulator/builder what values are needed to be added together 

//key-value in ARC: count, number