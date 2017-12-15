package com.product.food.controller;

import com.product.food.model.JSON;
import com.product.food.service.RankService;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class RankController {
    @Autowired
    private RankService rankService;
    private JSONArray jsonArray;
    @GetMapping(value = "/api/rank")
    public String getData(@RequestParam(name = "p",required = false,defaultValue = "score")String p){
        if (!p.equals("time")) {
            return getRankByScore();
        }else{
            return getRankByTime();
        }
    }

    public String getRankByScore()  {
        jsonArray = rankService.getRankByScore(1,8);
        if(jsonArray != null){
            JSON json = new JSON("0","successful");
            json.setKeyAndValue("data",jsonArray);
            return json.getJSON();
        }else{
            return new JSON("5","NOT FOUND").getJSON();
        }
    }
    public String getRankByTime(){
        jsonArray = rankService.getRankByTime();
        if (jsonArray == null){
            return new JSON("5","NOT FOUND").getJSON();
        }
        JSON json = new JSON("0","successful");
        json.setKeyAndValue("data",jsonArray);
        return json.getJSON();
    }
}
