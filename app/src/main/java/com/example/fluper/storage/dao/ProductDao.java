package com.example.fluper.storage.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.fluper.storage.entity.ProductEntity;

import java.util.List;

@Dao
public interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Long insertProduct(ProductEntity productEntity);

    @Query("SELECT * FROM ProductEntity")
    LiveData<List<ProductEntity>> getAllProducts();

    @Query("DELETE FROM ProductEntity WHERE product_id=:id")
    void deleteProduct(int id);

    @Query("DELETE FROM ProductEntity")
    void deleteAllProduct();
}
