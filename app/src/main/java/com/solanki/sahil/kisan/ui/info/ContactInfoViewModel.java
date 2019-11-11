package com.solanki.sahil.kisan.ui.info;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.lifecycle.ViewModel;

import com.solanki.sahil.kisan.ui.message.Message;


public class ContactInfoViewModel extends ViewModel {
    private String name, number;
    private Context context;

    public void onButtonClicked(View view) {
        Intent intent = new Intent(context, Message.class);
        Bundle extras = new Bundle();
        extras.putString("name", name);
        extras.putString("number", number);
        intent.putExtras(extras);
        context.startActivity(intent);
    }

    public void init() {
        name = getName();
        number = getNumber();
        context = getContext();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
