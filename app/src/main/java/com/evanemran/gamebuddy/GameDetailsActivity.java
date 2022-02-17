package com.evanemran.gamebuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.evanemran.gamebuddy.Adapters.SliderAdapter;
import com.evanemran.gamebuddy.Database.RoomDB;
import com.evanemran.gamebuddy.Listeners.GameDetailsResponseListener;
import com.evanemran.gamebuddy.Manager.RequestManager;
import com.evanemran.gamebuddy.Models.GameDetailResponse;
import com.evanemran.gamebuddy.Models.GameListObject;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Wave;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.squareup.picasso.Picasso;

public class GameDetailsActivity extends AppCompatActivity {
    RequestManager manager;
    ProgressBar progressBar;
    ImageView imageView_details;
    TextView textView_name, textView_status, textView_short_description, textView_genre, textView_platform;
    TextView textView_publisher, textView_developer, textView_release_date, textView_os, textView_processor;
    TextView textView_memory, textView_graphics, textView_storage, textView_description;
    Button button_game, button_save, button_share;
    GameDetailResponse detailResponse;
    SliderView sliderView;
    SliderAdapter sliderAdapter;
    ScrollView scrollView_holder;
    RoomDB database;
    GameListObject selectedGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_details);

        findViews();

        progressBar = (ProgressBar)findViewById(R.id.loader);
        Sprite doubleBounce = new Wave();
        progressBar.setIndeterminateDrawable(doubleBounce);

        selectedGame = (GameListObject) getIntent().getSerializableExtra("game");

        manager = new RequestManager(this);
        manager.getGameDetails(detailsResponseListener, String.valueOf(selectedGame.getId()));

        button_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (detailResponse!=null){
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(detailResponse.getGame_url()));
                    startActivity(browserIntent);
                }
            }
        });

        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database = RoomDB.getInstance(GameDetailsActivity.this);
                database.mainDao().insert(selectedGame);
                Toast.makeText(GameDetailsActivity.this, "Added to Favorites", Toast.LENGTH_SHORT).show();
            }
        });

        button_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, detailResponse.getTitle());
                shareIntent.putExtra(Intent.EXTRA_TEXT, detailResponse.getGame_url());
                startActivity(Intent.createChooser(shareIntent, "Share Game"));
            }
        });

    }

    private void findViews() {
        imageView_details = findViewById(R.id.imageView_details);
        scrollView_holder = findViewById(R.id.scrollView_holder);
        textView_name = findViewById(R.id.textView_name);
        textView_status = findViewById(R.id.textView_status);
        textView_short_description = findViewById(R.id.textView_short_description);
        textView_genre = findViewById(R.id.textView_genre);
        textView_platform = findViewById(R.id.textView_platform);
        textView_publisher = findViewById(R.id.textView_publisher);
        textView_developer = findViewById(R.id.textView_developer);
        textView_release_date = findViewById(R.id.textView_release_date);
        textView_os = findViewById(R.id.textView_os);
        textView_processor = findViewById(R.id.textView_processor);
        textView_memory = findViewById(R.id.textView_memory);
        textView_graphics = findViewById(R.id.textView_graphics);
        textView_storage = findViewById(R.id.textView_storage);
        textView_description = findViewById(R.id.textView_description);
        button_game = findViewById(R.id.button_game);
        sliderView = findViewById(R.id.image_slider);
        button_save = findViewById(R.id.button_save);
        button_share = findViewById(R.id.button_share);
    }

    private final GameDetailsResponseListener detailsResponseListener = new GameDetailsResponseListener() {
        @Override
        public void didFetchDetails(GameDetailResponse response, String message) {
            scrollView_holder.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);

            detailResponse = response;
            Picasso.get().load(response.getThumbnail()).into(imageView_details);
            sliderAdapter = new SliderAdapter(GameDetailsActivity.this, response.screenshots);
            sliderView.setSliderAdapter(sliderAdapter);
            sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
            sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
            sliderView.startAutoCycle();
            textView_name.setText(response.getTitle());
            textView_status.setText(response.getStatus());
            textView_short_description.setText(response.getShort_description());
            textView_genre.setText("Genre: " + response.getGenre());
            textView_platform.setText("Platform: " + response.getPlatform());
            textView_publisher.setText("Publisher: " + response.getPublisher());
            textView_developer.setText("Developer: " + response.getDeveloper());
            textView_release_date.setText("Release Date: " + response.getRelease_date());
            textView_os.setText(response.getMinimum_system_requirements().os);
            textView_processor.setText(response.getMinimum_system_requirements().processor);
            textView_memory.setText(response.getMinimum_system_requirements().memory);
            textView_graphics.setText(response.getMinimum_system_requirements().graphics);
            textView_storage.setText(response.getMinimum_system_requirements().storage);
            textView_description.setText(response.getDescription());
        }

        @Override
        public void didError(String message) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(GameDetailsActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };
}