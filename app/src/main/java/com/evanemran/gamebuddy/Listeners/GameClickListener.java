package com.evanemran.gamebuddy.Listeners;

import androidx.cardview.widget.CardView;

import com.evanemran.gamebuddy.Models.GameListObject;

public interface GameClickListener {
    void onGameClicked(GameListObject game);
    void onGameLongClicked(GameListObject game, CardView cardView);
}
