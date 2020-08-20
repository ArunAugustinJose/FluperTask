package com.example.fluper.storage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.example.fluper.model.ProductListModel;
import com.example.fluper.storage.entity.ProductEntity;

import java.util.List;

public class DbRepository {
    //creating db with name fluper
    private String DB_NAME = "fluper";

    private DbHandler dbHandler;

    public DbRepository(Context context) {
        dbHandler = Room.databaseBuilder(context, DbHandler.class, DB_NAME).build();
    }

    //    Product operations
    public void insertProduct(ProductListModel productListModel) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setProduct_id(productListModel.getId());
        productEntity.setName(productListModel.getName());
        productEntity.setDescription(productListModel.getDescription());
        productEntity.setRegular_price(productListModel.getRegular_price());
        productEntity.setSale_price(productListModel.getSale_price());
        productEntity.setProduct_photo(productListModel.getProduct_photo());
        productEntity.setColors(String.valueOf(productListModel.getColorList()));
        insertItem(productEntity);

    }

    //insert values into productEntity
    @SuppressLint("StaticFieldLeak")
    private void insertItem(final ProductEntity productEntity) {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                dbHandler.productDao().insertProduct(productEntity);
                return null;
            }
        }.execute();
    }

    //get values from productEntity
    public LiveData<List<ProductEntity>> getAllProducts() {
        return dbHandler.productDao().getAllProducts();
    }

    //delete all values from productEntity
    //passing product id
    @SuppressLint("StaticFieldLeak")
    public void deleteProduct(int id) {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                dbHandler.productDao().deleteProduct(id);
                return null;
            }
        }.execute();
    }


    //delete all values from productEntity
    @SuppressLint("StaticFieldLeak")
    public void deleteAllProducts() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                dbHandler.productDao().deleteAllProduct();
                return null;
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    public void closeDb() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                dbHandler.close();
                return null;
            }
        }.execute();
    }
}

