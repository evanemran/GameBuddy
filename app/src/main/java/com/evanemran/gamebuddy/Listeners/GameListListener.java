package com.evanemran.gamebuddy.Listeners;

import com.evanemran.gamebuddy.Models.GameListObject;

import java.util.List;

public interface GameListListener {
    void didFetchGameList(List<GameListObject> response, String message);
    void didError(String message);
}
