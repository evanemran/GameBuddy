package com.evanemran.gamebuddy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.evanemran.gamebuddy.Adapters.GameListAdapter;
import com.evanemran.gamebuddy.Database.RoomDB;
import com.evanemran.gamebuddy.Listeners.GameClickListener;
import com.evanemran.gamebuddy.Models.GameListObject;

import java.util.ArrayList;
import java.util.List;

public class StarredActivity extends AppCompatActivity implements GameClickListener, PopupMenu.OnMenuItemClickListener {
    RecyclerView recycler_saved;
    TextView textView_no_saved;
    RoomDB database;
    List<GameListObject> games = new ArrayList<>();
    GameListAdapter adapter;
    GameListObject selectedGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starred);

        recycler_saved = findViewById(R.id.recycler_saved);
        textView_no_saved = findViewById(R.id.textView_no_saved);

        database = RoomDB.getInstance(this);
        games = database.mainDao().getAll();

        recycler_saved.setHasFixedSize(true);
        recycler_saved.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        adapter = new GameListAdapter(this, games, this, true);
        recycler_saved.setAdapter(adapter);

    }

    @Override
    public void onGameClicked(GameListObject game) {
        startActivity(new Intent(StarredActivity.this, GameDetailsActivity.class)
        .putExtra("game", game));
    }

    @Override
    public void onGameLongClicked(GameListObject game, CardView cardView) {
        selectedGame = new GameListObject();
        selectedGame = game;
        showMenu(cardView);
    }

    private void showMenu(CardView cardView) {
        PopupMenu popupMenu = new PopupMenu(this, cardView);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.delete:
                database.mainDao().delete(selectedGame);
                games.remove(selectedGame);
                adapter.notifyDataSetChanged();
                Toast.makeText(StarredActivity.this, "Removed!", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;
        }
    }
}