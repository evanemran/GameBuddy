package com.evanemran.gamebuddy.Listeners;

import com.evanemran.gamebuddy.Models.GameDetailResponse;

public interface GameDetailsResponseListener {
    void didFetchDetails(GameDetailResponse response, String message);
    void didError(String message);
}
