package com.solanki.sahil.kisan.data.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = Record.class, version = 1)
public abstract class MyDatabase extends RoomDatabase {

    public abstract Dao dao();
}
