package com.product.food.service;

import com.product.food.dao.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RankService {
    @Autowired
    private RankChartRepository rankChartRepository;
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private FoodRepository foodRepository;


    private JSONArray jsonArray;
    private JSONObject jsonObject;

    public JSONArray getRankByScore(Integer start,Integer end) {
        ArrayList<RankChart> rankCharts = rankChartRepository.findAllByRankBetween(start,end);
        if (rankCharts.isEmpty()){
            return null;
        }

        jsonArray = new JSONArray();

        for (RankChart rankChart : rankCharts) {
            jsonObject = new JSONObject();
            try {
                jsonObject.put("id", rankChart.getId());
                jsonObject.put("foodName", rankChart.getFoodName());
                jsonObject.put("rank", rankChart.getRank());
                jsonObject.put("foodImg", rankChart.getUrl());
                jsonObject.put("score", rankChart.getScore());
                jsonObject.put("foodSite", rankChart.getSite());
                jsonObject.put("foodShop",rankChart.getShop());
                jsonObject.put("fid",rankChart.getFid());
                jsonArray.put(jsonObject);
            } catch (JSONException e) {
                return null;
            }
        }
        return jsonArray;

     //   return getJsonArray(rankCharts);
    }

    public JSONArray getRankByTime(){
        JSONArray jsonArray = getRankByScore(1,3);
        Sort sort = new Sort(Sort.Direction.DESC,"publishTime");
        Pageable pageable = new PageRequest(0,5,sort);
        List<Comment> comments = commentRepository.findAll(pageable).getContent();
       int i = 4;

        for(Comment comment:comments){
            JSONObject jsonObject2 = new JSONObject();
            Food food = foodRepository.findById(comment.getFid());
            try {
                jsonObject2.put("id",comment.getId());
                jsonObject2.put("foodName",food.getFoodName());
                jsonObject2.put("date",comment.getPublishTime());
                jsonObject2.put("rank", i);
                jsonObject2.put("foodImg", comment.getImgUrls());
                jsonObject2.put("score", comment.getScore());
                jsonObject2.put("foodSite", food.getSite());
                jsonObject2.put("foodShop",food.getShop());
                jsonObject2.put("fid",food.getId());
                i++;
                jsonArray.put(jsonObject2);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return jsonArray;
    }




}
