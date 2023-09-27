package com.example.highlysucceedexam.Listeners;

import com.example.highlysucceedexam.Models.RandomRecipeApiResponse;

public interface RandomRecipeResponseListener {
    void  didFetch(RandomRecipeApiResponse response, String message);
    void didError (String message);
}
