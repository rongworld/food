package com.product.food.service;

import com.product.food.dao.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
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

    public JSONArray getRankByScore() {
        ArrayList<RankChart> rankCharts = rankChartRepository.findTop8ByOrderByScoreDesc();
        if (rankCharts.isEmpty()){
            return null;
        }

        jsonArray = new JSONArray();
        DecimalFormat df = new DecimalFormat("0.0");
        int rank = 1;
        for (RankChart rankChart : rankCharts) {
            jsonObject = new JSONObject();
            Integer fid = rankChart.getFid();
            Food food = foodRepository.findById(fid);
            try {
                jsonObject.put("id", rankChart.getId());
                jsonObject.put("foodName", food.getFoodName());
                jsonObject.put("rank", rank);
                rank += 1;
                jsonObject.put("foodImg", food.getImgUrl());
                jsonObject.put("score", df.format(rankChart.getScore()));
                jsonObject.put("foodSite", food.getSite());
                jsonObject.put("foodShop",food.getShop());
                jsonObject.put("fid",rankChart.getFid());
                jsonArray.put(jsonObject);
            } catch (JSONException e) {
                return null;
            }
        }
        return jsonArray;
    }

    public JSONArray getRankByTime() throws JSONException {
        DecimalFormat df = new DecimalFormat("0.0");
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(0,getRankByScore().get(0));
        jsonArray.put(1,getRankByScore().get(1));
        jsonArray.put(2,getRankByScore().get(2));
        List<Comment> comments = commentRepository.findTop10ByOrderByPublishTimeDesc();
       int rank = 4;

        for(Comment comment:comments){
            JSONObject jsonObject2 = new JSONObject();
            Food food = foodRepository.findById(comment.getFid());
            try {

                jsonObject2.put("id",comment.getId());
                jsonObject2.put("foodName",food.getFoodName());
                jsonObject2.put("date",comment.getPublishTime());
                jsonObject2.put("rank", rank);
                rank += 1;
                jsonObject2.put("foodImg", comment.getImgUrls());
                jsonObject2.put("score", df.format(comment.getScore()));
                jsonObject2.put("foodSite", food.getSite());
                jsonObject2.put("foodShop",food.getShop());
                jsonObject2.put("fid",food.getId());
                rank++;
                jsonArray.put(jsonObject2);
                if (rank == 8){
                    break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonArray;
    }
}
