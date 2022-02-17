package com.evanemran.gamebuddy.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.evanemran.gamebuddy.Listeners.GameClickListener;
import com.evanemran.gamebuddy.Models.GameListObject;
import com.evanemran.gamebuddy.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GameListAdapter extends RecyclerView.Adapter<GameListViewHolder>{
    Context context;
    List<GameListObject> list;
    GameClickListener listener;
    boolean isFav;

    public GameListAdapter(Context context, List<GameListObject> list, GameClickListener listener, boolean isFav) {
        this.context = context;
        this.list = list;
        this.listener = listener;
        this.isFav = isFav;
    }

    @NonNull
    @Override
    public GameListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (isFav){
            return new GameListViewHolder(LayoutInflater.from(context).inflate(R.layout.list_favs, parent, false));
        }
        else {
            return new GameListViewHolder(LayoutInflater.from(context).inflate(R.layout.list_games, parent, false));
        }

    }

    @Override
    public void onBindViewHolder(@NonNull GameListViewHolder holder, int position) {
        holder.textView_game_name.setText(list.get(position).getTitle());
        holder.textView_game_name.setSelected(true);

        holder.textView_game_category.setText("Category: " + list.get(position).getGenre());
        holder.textView_game_desc.setText("About: " + list.get(position).getShort_description());

        Picasso.get().load(list.get(position).getThumbnail()).into(holder.imageView_game);

        holder.game_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onGameClicked(list.get(holder.getAdapterPosition()));
            }
        });

        holder.game_container.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                listener.onGameLongClicked(list.get(holder.getAdapterPosition()), holder.game_container);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
class GameListViewHolder extends RecyclerView.ViewHolder {

    CardView game_container;
    ImageView imageView_game;
    TextView textView_game_name, textView_game_category, textView_game_desc;

    public GameListViewHolder(@NonNull View itemView) {
        super(itemView);
        game_container = itemView.findViewById(R.id.game_container);
        imageView_game = itemView.findViewById(R.id.imageView_game);
        textView_game_name = itemView.findViewById(R.id.textView_game_name);
        textView_game_category = itemView.findViewById(R.id.textView_game_category);
        textView_game_desc = itemView.findViewById(R.id.textView_game_desc);
    }
}
