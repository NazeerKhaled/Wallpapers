package com.example.Listeners;

import com.example.Models.SearchApiResponse;

public interface SearchResponseListener {
    void onFetch(SearchApiResponse response , String message);
    void onError(String message);
}
