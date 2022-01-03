package com.example.wallpapers;

import android.content.Context;
import android.widget.Toast;

import com.example.Listeners.CuratedResponseListener;
import com.example.Listeners.SearchResponseListener;
import com.example.Models.CuratedApiResponse;
import com.example.Models.SearchApiResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public class RequestManger {
    Context context;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.pexels.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public RequestManger(Context context) {
        this.context = context;
    }

    public void getCuratedWallpapers(CuratedResponseListener listener,String page){
        CallWallpaperList callWallpaperList = retrofit.create(CallWallpaperList.class);
        Call<CuratedApiResponse> call= callWallpaperList.getWallpapers(page,"25");

        call.enqueue(new Callback<CuratedApiResponse>() {
            @Override
            public void onResponse(Call<CuratedApiResponse> call, Response<CuratedApiResponse> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(context,"An Error Occurred!",Toast.LENGTH_SHORT).show();
                    return;
                }
                listener.onFetch(response.body(),response.message());
            }

            @Override
            public void onFailure(Call<CuratedApiResponse> call, Throwable t) {
                listener.onError(t.getMessage());

            }
        });
    }

    public void searchCuratedWallpapers(SearchResponseListener listener, String page, String query){
      CallWallpaperListSearch  callWallpaperListSearch = retrofit.create(CallWallpaperListSearch.class);
        Call<SearchApiResponse> call= callWallpaperListSearch.searchWallpapers(query,page,"25");

        call.enqueue(new Callback<SearchApiResponse>() {
            @Override
            public void onResponse(Call<SearchApiResponse> call, Response<SearchApiResponse> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(context,"An Error Occurred!",Toast.LENGTH_SHORT).show();
                    return;
                }
                listener.onFetch(response.body(),response.message());
            }

            @Override
            public void onFailure(Call<SearchApiResponse> call, Throwable t) {
                listener.onError(t.getMessage());

            }
        });
    }


    private interface CallWallpaperList{
        @Headers( {
                "Accept: application/json",
                "Authorization: 563492ad6f917000010000019364eb2ffc97427ba28a37413714826d"
        })
        @GET("curated/")
        Call<CuratedApiResponse> getWallpapers(
                @Query("page") String page,
                @Query("per_page") String per_page
        );
    }

    private interface CallWallpaperListSearch{
        @Headers( {
                "Accept: application/json",
                "Authorization: 563492ad6f917000010000019364eb2ffc97427ba28a37413714826d"
        })
        @GET("search")
        Call<SearchApiResponse> searchWallpapers(
                @Query("query")String query,
                @Query("page") String page,
                @Query("per_page") String per_page
        );
    }

}
