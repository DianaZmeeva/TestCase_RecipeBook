package com.example.test;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("recipes.json")
    Call<RecipeList> getMyJSON();
}
