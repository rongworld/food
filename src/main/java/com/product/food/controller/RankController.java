package com.product.food.controller;

import com.product.food.model.JSON;
import com.product.food.service.RankService;
import org.json.JSONArray;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class RankController {
    private Logger logger = LoggerFactory.getLogger(RankController.class);
    @Autowired
    private RankService rankService;
    private JSONArray jsonArray;
    @GetMapping(value = "/api/rank")
    public String getData(@RequestParam(name = "p",required = false,defaultValue = "score")String p) throws JSONException {
        logger.info("ok");
        if (!p.equals("time")) {
            return getRankByScore();
        }else{
            return getRankByTime();
        }
    }

    public String getRankByScore()  {
        jsonArray = rankService.getRankByScore();
        if(jsonArray != null){
            JSON json = new JSON(0,"successful");
            json.setKeyAndValue("data",jsonArray);
            return json.getJSON();
        }else{
            return new JSON(5,"NOT FOUND").getJSON();
        }
    }
    public String getRankByTime() throws JSONException {
        jsonArray = rankService.getRankByTime();
        if (jsonArray == null){
            return new JSON(5,"NOT FOUND").getJSON();
        }
        JSON json = new JSON(0,"successful");
        json.setKeyAndValue("data",jsonArray);
        return json.getJSON();
    }
}
