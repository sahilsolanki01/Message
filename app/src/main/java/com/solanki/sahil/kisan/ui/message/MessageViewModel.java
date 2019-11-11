package com.solanki.sahil.kisan.ui.message;


import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.solanki.sahil.kisan.MainActivity;
import com.solanki.sahil.kisan.data.model.ModelMessage;
import com.solanki.sahil.kisan.data.repository.SendMessageRepository;

import java.util.List;
import java.util.Random;


public class MessageViewModel extends ViewModel {
    private String body, number;
    private SendMessageRepository sendMessageRepository;
    public MessageListener messageListener;
    private Activity activity;

    public MessageViewModel(SendMessageRepository sendMessageRepository) {
        this.sendMessageRepository = sendMessageRepository;
    }

    public void onButtonClicked(View view) {

        LiveData<List<ModelMessage>> response = sendMessageRepository.result(number, getBody(), activity);
        messageListener.onSuccess(response);
        Log.e("after sent_repo((***&", "MainViewModel: ");

        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);

    }

    public void init(String number, Activity activity) {

        setBody("Hi. Your OTP is :" +"  "+ rand());
        this.number = number;
        this.activity = activity;

    }

    public int rand(){
        int min = 100000;
        int max = 999999;
        int random = new Random().nextInt((max - min) + 1) + min;

        return random;
    }



    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
