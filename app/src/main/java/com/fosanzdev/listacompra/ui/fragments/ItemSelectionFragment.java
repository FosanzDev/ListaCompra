package com.fosanzdev.listacompra.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fosanzdev.listacompra.R;
import com.fosanzdev.listacompra.models.ItemViewFittable;
import com.fosanzdev.listacompra.ui.GridItemAdapter;

import java.util.List;

public class ItemSelectionFragment extends Fragment{

    List<? extends ItemViewFittable> items;
    Context context;
    GridItemAdapter.ItemAdapter.IOnItemClickedListener listener;

    public ItemSelectionFragment(List<? extends ItemViewFittable> items, Context context) {
        this.items = items;
        this.context = context;
        this.listener = (GridItemAdapter.ItemAdapter.IOnItemClickedListener) context;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.select_item_fragment, container, false);
        RecyclerView recyclerView = v.findViewById(R.id.rvItemCollection);
        GridItemAdapter adapter = new GridItemAdapter(items, listener);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 3));

        return v;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
