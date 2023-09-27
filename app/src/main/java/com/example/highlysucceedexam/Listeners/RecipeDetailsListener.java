package com.example.highlysucceedexam.Listeners;

import com.example.highlysucceedexam.Models.RecipeDetailsResponse;

public interface RecipeDetailsListener {
    void didFetch(RecipeDetailsResponse response, String message);
    void didError(String message);
}
