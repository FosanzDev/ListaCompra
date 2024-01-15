package com.fosanzdev.listacompra.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fosanzdev.listacompra.R;
import com.fosanzdev.listacompra.Utils;
import com.fosanzdev.listacompra.models.Item;
import com.fosanzdev.listacompra.models.ItemViewFittable;
import com.fosanzdev.listacompra.models.ShoppingList;

import java.util.ArrayList;
import java.util.List;

public class GridItemAdapter extends RecyclerView.Adapter<GridItemAdapter.ItemAdapter>{

    //Manager extends ArrayList
    private ShoppingList shoppingList;
    private List<? extends ItemViewFittable> items;
    private Context context;

    public GridItemAdapter(ShoppingList shoppingList, Context context) {
        this.shoppingList = shoppingList;
        items = shoppingList.getItems();
        this.context = context;
    }

    @NonNull
    @Override
    public GridItemAdapter.ItemAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.grid_recycler_item_view, parent, false);
        return new ItemAdapter(v, context);
    }

    @Override
    public void onBindViewHolder(@NonNull GridItemAdapter.ItemAdapter holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ItemAdapter extends RecyclerView.ViewHolder implements View.OnClickListener{
        public interface IOnItemClickedListener {
            void onItemClicked(ItemViewFittable item);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClicked(item);
        }

        ItemViewFittable item;
        ImageView ivItem;
        TextView tvItemName;
        IOnItemClickedListener listener;

        public ItemAdapter(@NonNull View view, Context context) {
            super(view);
            ivItem = view.findViewById(R.id.ivItemImage);
            tvItemName = view.findViewById(R.id.textView2);
            this.listener = (IOnItemClickedListener) context;
            view.setOnClickListener(this);
        }

        public void bind(ItemViewFittable item){
            //Transform Base64 to Bitmap and set it to ImageVie
            ivItem.setImageBitmap(item.getImage());
            tvItemName.setText(item.getName());
            this.item = item;
        }
    }
}
