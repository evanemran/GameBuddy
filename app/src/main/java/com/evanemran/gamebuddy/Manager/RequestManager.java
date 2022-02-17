package com.evanemran.gamebuddy.Manager;

import android.content.Context;

import com.evanemran.gamebuddy.Listeners.GameDetailsResponseListener;
import com.evanemran.gamebuddy.Listeners.GameListListener;
import com.evanemran.gamebuddy.Models.GameDetailResponse;
import com.evanemran.gamebuddy.Models.GameListObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class RequestManager {
    Context context;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://www.freetogame.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public RequestManager(Context context) {
        this.context = context;
    }

    public void getGameList(GameListListener listener, String category, String sort){
        GetGameList getGameList = retrofit.create(GetGameList.class);
        Call<List<GameListObject>> call = getGameList.callGameList(category, sort);
        call.enqueue(new Callback<List<GameListObject>>() {
            @Override
            public void onResponse(Call<List<GameListObject>> call, Response<List<GameListObject>> response) {
                if (!response.isSuccessful()){
                    listener.didError(response.message());
                    return;
                }
                listener.didFetchGameList(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<List<GameListObject>> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }

    public void getGameListRandom(GameListListener listener, String sort){
        GetGameListRandom getGameList = retrofit.create(GetGameListRandom.class);
        Call<List<GameListObject>> call = getGameList.callGameList(sort);
        call.enqueue(new Callback<List<GameListObject>>() {
            @Override
            public void onResponse(Call<List<GameListObject>> call, Response<List<GameListObject>> response) {
                if (!response.isSuccessful()){
                    listener.didError(response.message());
                    return;
                }
                listener.didFetchGameList(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<List<GameListObject>> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }

    public void getGameDetails(GameDetailsResponseListener listener, String id){
        GetGameDetails getGameDetails = retrofit.create(GetGameDetails.class);
        Call<GameDetailResponse> call = getGameDetails.callGameDetails(id);
        call.enqueue(new Callback<GameDetailResponse>() {
            @Override
            public void onResponse(Call<GameDetailResponse> call, Response<GameDetailResponse> response) {
                if (!response.isSuccessful()){
                    listener.didError(response.message());
                    return;
                }
                listener.didFetchDetails(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<GameDetailResponse> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }

    private interface GetGameList{
        @GET("api/games")
        Call<List<GameListObject>> callGameList(
                @Query("category") String category,
                @Query("sort-by") String sort
        );
    }

    private interface GetGameListRandom{
        @GET("api/games")
        Call<List<GameListObject>> callGameList(
                @Query("sort-by") String sort
        );
    }

    private interface GetGameDetails{
        @GET("api/game")
        Call<GameDetailResponse> callGameDetails(
                @Query("id") String id
        );
    }
}
