package com.solanki.sahil.kisan.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.solanki.sahil.kisan.data.model.Contact;
import com.solanki.sahil.kisan.data.model.ModelMessage;
import com.solanki.sahil.kisan.data.repository.MessageRepository;
import com.solanki.sahil.kisan.data.repository.UserRepository;

import java.util.List;

public class PageViewModel extends ViewModel {
    private MutableLiveData<List<Contact>> mList;
    private UserRepository userRepository;
    private MutableLiveData<List<ModelMessage>> mRecord;
    private MessageRepository messageRepository;

    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();


    public MutableLiveData<Integer> getmIndex() {
        return mIndex;
    }


    public void setIndex(int index) {
        mIndex.setValue(index);
    }


    public void init() {
        if(mList != null)
            return;

        userRepository = new UserRepository().getInstance();
        mList = userRepository.getData();
    }


    public void init2() {
        if(mRecord != null)
            return;

        messageRepository = new MessageRepository().getInstance();
        mRecord = messageRepository.getData();


    }

    public  LiveData<List<Contact>> getData() {
        return mList;
    }

    public  LiveData<List<ModelMessage>> getData2() {
        return mRecord;
    }
}