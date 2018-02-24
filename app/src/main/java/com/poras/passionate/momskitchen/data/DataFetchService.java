package com.poras.passionate.momskitchen.data;

import android.support.annotation.Nullable;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@SuppressWarnings("ConstantConditions")
public class DataFetchService {
    public static final String RECIPE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    @Nullable
    public static String getRecipeData(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request newRequest = new Request.Builder()
                .url(url)
                .build();
        Response response = null;
        response = client.newCall(newRequest).execute();
        return response == null ? null : response.body().string();
    }
}
