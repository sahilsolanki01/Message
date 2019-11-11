package com.solanki.sahil.kisan.data.db;

import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@androidx.room.Dao
public interface Dao {

    @Insert
    void addRecord(Record record);

    @Query("select * from Record")
    List<Record> getRecords();


}
