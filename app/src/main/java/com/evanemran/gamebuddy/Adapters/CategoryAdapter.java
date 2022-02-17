package com.evanemran.gamebuddy.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.evanemran.gamebuddy.Listeners.CategoryClickListener;
import com.evanemran.gamebuddy.Listeners.CategorySelectListener;
import com.evanemran.gamebuddy.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryViewHolder>{
    List<String> list;
    CategorySelectListener listener;
    private int selectedPos = 0;

    public CategoryAdapter(List<String> list, CategorySelectListener listener) {
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_categories, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.textView_cat.setText(list.get(position));
        holder.card_container.setCardBackgroundColor(selectedPos == holder.getAdapterPosition() ? holder.itemView.getResources().getColor(R.color.blue, null) : holder.itemView.getResources().getColor(R.color.grey, null));
        holder.card_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.didSelect(list.get(holder.getAdapterPosition()));
//                holder.textView_cat.setBackgroundColor(Color.parseColor("#4694E3"));
                notifyItemChanged(selectedPos);
                selectedPos = holder.getAdapterPosition();
                notifyItemChanged(selectedPos);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
class CategoryViewHolder extends RecyclerView.ViewHolder {
    TextView textView_cat;
    CardView card_container;

    public CategoryViewHolder(@NonNull View itemView) {
        super(itemView);
        textView_cat = itemView.findViewById(R.id.textView_cat);
        card_container = itemView.findViewById(R.id.card_container);
    }
}
