package com.solanki.sahil.kisan.ui.message;

import androidx.lifecycle.LiveData;

import com.solanki.sahil.kisan.data.model.ModelMessage;

import java.util.List;

public interface MessageListener {

    void onSuccess(LiveData<List<ModelMessage>> response);
}
