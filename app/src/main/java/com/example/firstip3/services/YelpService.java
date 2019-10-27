package com.example.firstip3.services;

import com.example.firstip3.Constants;
import com.example.firstip3.models.News;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
public class YelpService {

    public static void findNews(String location, Callback callback){
        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.YELP_BASE_URL).newBuilder();
        urlBuilder.addQueryParameter(Constants.YELP_LOCATION_QUERY_PARAMETER, location);
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", Constants.YELP_API_KEY)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public ArrayList<News> processResults(Response response){
        ArrayList<News> news = new ArrayList<>();
        try{
            String jsonData = response.body().string();
            JSONObject yelpJSON = new JSONObject(jsonData);
            JSONArray businessesJSON = yelpJSON.getJSONArray("businesses");
            if (response.isSuccessful()){
                for (int i = 0; i < businessesJSON.length(); i++){
                    JSONObject newsJSON = businessesJSON.getJSONObject(i);
                    String name = newsJSON.getString("name");
                    String phone = newsJSON.optString("display_phone", "Phone not available");
                    String website = newsJSON.getString("url");
                    double rating = newsJSON.getDouble("rating");
                    String imageUrl = newsJSON.getString("image_url");
                    double latitude = newsJSON.getJSONObject("coordinates").getDouble("latitude");
                    double longitude = newsJSON.getJSONObject("coordinates").getDouble("longitude");
                    ArrayList<String> address = new ArrayList<>();
                    JSONArray addressJSON = newsJSON.getJSONObject("location").getJSONArray("display_address");
                    for (int y = 0; y < addressJSON.length(); y++){
                        address.add(addressJSON.get(y).toString());
                    }
                    ArrayList<String> categories = new ArrayList<>();
                    JSONArray categoriesJSON = newsJSON.getJSONArray("categories");
                    for (int y = 0; y < categoriesJSON.length(); y++){
                        categories.add(categoriesJSON.getJSONObject(y).getString("title"));
                    }
                    News restaurant = new News(name, phone, website, rating,
                            imageUrl, address, latitude, longitude, categories);
                    news.add(restaurant);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return news;
    }
}

