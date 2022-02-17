package com.evanemran.gamebuddy.Database;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.evanemran.gamebuddy.Models.GameListObject;

import java.util.List;

@Dao
public interface MainDao {
    @Insert(onConflict = REPLACE)
    void insert (GameListObject object);

    @Delete
    void delete (GameListObject object);

    @Query("SELECT * FROM games ORDER BY id DESC")
    List<GameListObject> getAll();
}
