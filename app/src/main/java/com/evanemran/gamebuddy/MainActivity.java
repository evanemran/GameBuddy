package com.evanemran.gamebuddy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.evanemran.gamebuddy.Adapters.CategoryAdapter;
import com.evanemran.gamebuddy.Adapters.GameListAdapter;
import com.evanemran.gamebuddy.Listeners.CategoryClickListener;
import com.evanemran.gamebuddy.Listeners.GameClickListener;
import com.evanemran.gamebuddy.Listeners.GameListListener;
import com.evanemran.gamebuddy.Manager.RequestManager;
import com.evanemran.gamebuddy.Models.GameListObject;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Wave;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GameClickListener, CategoryClickListener {
    RecyclerView recycler_game_list;
    RequestManager manager;
    String platform = "all";
    String category = "shooter";
    String sort = "release-date";
    ProgressBar progressBar;
    GameListAdapter gameListAdapter;
    List<String> categoryList;
    CategoryAdapter categoryAdapter;
    FloatingActionButton imageButton_category;
    ImageButton imageButton_star;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recycler_game_list = findViewById(R.id.recycler_game_list);
        imageButton_category = findViewById(R.id.imageButton_category);
        imageButton_star = findViewById(R.id.imageButton_star);

        String[] categoryArray = getResources().getStringArray(R.array.categories);
        categoryList = new ArrayList<String>(Arrays.asList(categoryArray));

        progressBar = (ProgressBar)findViewById(R.id.loader);
        Sprite doubleBounce = new Wave();
        progressBar.setIndeterminateDrawable(doubleBounce);

        manager = new RequestManager(this);
        manager.getGameListRandom(gameListListener, sort);

        imageButton_star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, StarredActivity.class));
            }
        });

        imageButton_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CategoryPicker categoryPicker =new CategoryPicker(categoryList, MainActivity.this);
                categoryPicker.show(getSupportFragmentManager(), "Category");
            }
        });
    }

    private final GameListListener gameListListener = new GameListListener() {
        @Override
        public void didFetchGameList(List<GameListObject> response, String message) {
            recycler_game_list.setVisibility(View.VISIBLE);
            imageButton_category.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            recycler_game_list.setHasFixedSize(true);
            recycler_game_list.setLayoutManager(new GridLayoutManager(MainActivity.this, 1));
            gameListAdapter = new GameListAdapter(MainActivity.this, response, MainActivity.this, false);
            recycler_game_list.setAdapter(gameListAdapter);
        }

        @Override
        public void didError(String message) {
            recycler_game_list.setVisibility(View.VISIBLE);
            imageButton_category.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onGameClicked(GameListObject game) {
        startActivity(new Intent(MainActivity.this, GameDetailsActivity.class)
        .putExtra("game", game));
    }

    @Override
    public void onGameLongClicked(GameListObject game, CardView cardView) {

    }

    @Override
    public void onCategoryClicked(String category) {
        manager.getGameList(gameListListener, category, sort);
        recycler_game_list.setVisibility(View.GONE);
        imageButton_category.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }
}