package com.fosanzdev.listacompra;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;

import com.fosanzdev.listacompra.controllers.CategoryManager;
import com.fosanzdev.listacompra.controllers.ItemManager;
import com.fosanzdev.listacompra.models.Category;
import com.fosanzdev.listacompra.models.Item;
import com.fosanzdev.listacompra.models.ItemViewFittable;
import com.fosanzdev.listacompra.ui.GridItemAdapter;
import com.fosanzdev.listacompra.ui.fragments.ItemSelectionFragment;

public class SelectItemActivity extends AppCompatActivity implements GridItemAdapter.ItemAdapter.IOnItemClickedListener {

    FragmentContainerView fragmentContainer;
    CategoryManager categoryManager;
    ItemManager itemManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_item_activity);

        fragmentContainer = findViewById(R.id.fcvSelectionContainer);

        categoryManager = getIntent().getParcelableExtra("categoryManager");
        itemManager = getIntent().getParcelableExtra("itemManager");
    }

    @Override
    public void onItemClicked(ItemViewFittable item) {
        if (item instanceof Category) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fcvSelectionContainer, new ItemSelectionFragment(itemManager.getItemsByCategory((Category) item), this))
                    .addToBackStack(null)
                    .commit();
        } else if (item instanceof Item) {
            Intent intent = new Intent();
            intent.putExtra("item", String.valueOf(item.getId()));
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}