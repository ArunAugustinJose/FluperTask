package com.example.fluper.storage;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.fluper.storage.dao.ProductDao;
import com.example.fluper.storage.entity.ProductEntity;

@Database(entities = {ProductEntity.class}, version = 1, exportSchema = false)

public abstract class DbHandler extends RoomDatabase {
    public abstract ProductDao productDao();
}
