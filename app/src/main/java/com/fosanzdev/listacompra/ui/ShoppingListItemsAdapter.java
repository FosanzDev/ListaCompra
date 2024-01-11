package com.fosanzdev.listacompra.ui;

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
import com.fosanzdev.listacompra.models.ItemViewFittable;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListItemsAdapter extends RecyclerView.Adapter<ShoppingListItemsAdapter.ItemAdapter>{

    private List<? extends ItemViewFittable> items;

    public ShoppingListItemsAdapter(List<? extends ItemViewFittable> items) {
        this.items = items;
    }


    @NonNull
    @Override
    public ShoppingListItemsAdapter.ItemAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.grid_recycler_item_view, parent, false);
        return new ItemAdapter(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingListItemsAdapter.ItemAdapter holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ItemAdapter extends RecyclerView.ViewHolder {

        ImageView ivItem;
        TextView tvItemName;

        public ItemAdapter(@NonNull View view) {
            super(view);
            ivItem = view.findViewById(R.id.ivItemImage);
            tvItemName = view.findViewById(R.id.textView2);
        }

        public void bind(ItemViewFittable item){
            //Transform Base64 to Bitmap and set it to ImageVie

            Bitmap image = Utils.byteArrayToBitmap(item.getImage());
            System.out.println("Image: " + image);
            ivItem.setImageBitmap(image);
            tvItemName.setText(item.getName());
        }
    }
}
