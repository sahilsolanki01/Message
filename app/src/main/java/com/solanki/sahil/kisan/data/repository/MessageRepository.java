package com.solanki.sahil.kisan.data.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.solanki.sahil.kisan.data.db.MyDatabase;
import com.solanki.sahil.kisan.data.db.Record;
import com.solanki.sahil.kisan.data.model.ModelMessage;
import com.solanki.sahil.kisan.data.provider.App;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MessageRepository {

    private MessageRepository instance;
    private List<Record> mRecord = new ArrayList<>();
    private List<ModelMessage> mList = new ArrayList<>();
    public static MyDatabase myDatabase;


    public MessageRepository getInstance() {
        if (instance == null)
            instance = new MessageRepository();

        return instance;
    }

    public MutableLiveData<List<ModelMessage>> getData() {

        setData();
        MutableLiveData<List<ModelMessage>> result = new MutableLiveData<>();
        result.setValue(mList);

        return result;
    }

    private void setData() {

        myDatabase = Room.databaseBuilder(App.getContext(), MyDatabase.class, "recorddb").allowMainThreadQueries().build();
        mRecord = myDatabase.dao().getRecords();
        Log.e("mess_repo", "setData: " + mRecord.size());


        for (Record record : mRecord) {

            String otp = record.getOtp();
            String number = record.getNumber();
            String date = record.getDate();

            ModelMessage modelMessage = new ModelMessage();
            modelMessage.setBody(otp);
            modelMessage.setDate(date);
            modelMessage.setNumber(number);

            mList.add(modelMessage);
            Collections.sort(mList);
            Collections.reverse(mList);

            Log.e("lkvdhaliv((", otp + " " + number + " " + date + "\n");


        }
    }


}


