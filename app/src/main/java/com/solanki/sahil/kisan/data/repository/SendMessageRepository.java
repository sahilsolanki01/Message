package com.solanki.sahil.kisan.data.repository;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.solanki.sahil.kisan.data.db.MyDatabase;
import com.solanki.sahil.kisan.data.db.Record;
import com.solanki.sahil.kisan.data.model.ModelMessage;
import com.solanki.sahil.kisan.data.network.Api;
import com.solanki.sahil.kisan.data.provider.App;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SendMessageRepository {
    private SendMessageRepository instance;
    private Api api;
    private static final String ACCOUNT_SID = " ";/*Your twilio credentials */
    private static final String AUTH_TOKEN = "";/*Your twilio credentials */
    private String mbody, to;
    private ArrayList<ModelMessage> mList = new ArrayList<>();
    private static MyDatabase myDatabase;
    MutableLiveData<List<ModelMessage>> result = new MutableLiveData<>();
    Activity activity;


    public SendMessageRepository getInstance() {
        if (instance == null)
            instance = new SendMessageRepository(api);


        return instance;
    }

    public MyDatabase getMyDatabase() {
        if (myDatabase == null) {
            myDatabase = Room.databaseBuilder(App.getContext(), MyDatabase.class, "recorddb").allowMainThreadQueries().build();
        }

        return myDatabase;

    }

    public SendMessageRepository(Api api) {
        this.api = api;
    }


    public LiveData<List<ModelMessage>> result(String number, String body, Activity activity) {

        mbody = body;
        String from = "+12053904247";
        to = number;
        this.activity = activity;

        String base64EncodedCredentials = "Basic " + Base64.encodeToString(
                (ACCOUNT_SID + ":" + AUTH_TOKEN).getBytes(), Base64.NO_WRAP
        );

        Map<String, String> data = new HashMap<>();
        data.put("From", from);
        data.put("To", to);
        data.put("Body", body);


        if (isNetworkAvailable()) {
            setData(to, mbody);
        }
        Call<ResponseBody> call = api.sendMessage(ACCOUNT_SID, base64EncodedCredentials, data);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {

                    result.setValue(mList);
                }

            }


            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("TAG", "onFailure");

            }
        });


        return result;
    }

    private void setData(String number, String body) {

        mList = new ArrayList<>();
        ModelMessage modelMessage = new ModelMessage();

        String otp = body.substring(body.indexOf(":") + 1);

        Calendar c = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
        String currentDate = df.format(c.getTime());

        modelMessage.setNumber(number);
        modelMessage.setBody(otp);
        modelMessage.setDate(currentDate);
        mList.add(modelMessage);

        Record record = new Record();
        record.setDate(currentDate);
        record.setNumber(number);
        record.setOtp(otp);


        getMyDatabase().dao().addRecord(record);
        Log.e("send_repo add_record^&", record.toString());

    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


}
