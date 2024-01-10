package com.fosanzdev.listacompra;

import android.database.sqlite.SQLiteDatabase;

import com.fosanzdev.listacompra.db.dao.CategoryDAO;

import java.util.ArrayList;

public class CategoryManager extends ArrayList<Category> {

    private SQLiteDatabase db;

        public CategoryManager(SQLiteDatabase db) {
            super();
            this.db = db;
            init();
        }

        /**
        * Reads from the database all the categories and adds them to the manager
        */
        private void init(){
            CategoryDAO dao = new CategoryDAO(db);
            for (Category category : dao.findAll()) {
                add(category);
            }
        }

        @Override
        public boolean remove(Object o) {
            boolean result = super.remove(o);
            if (result) {
                new CategoryDAO(db).delete((Category) o);
            }
            return result;
        }
}
