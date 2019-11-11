package com.solanki.sahil.kisan.ui.message;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.solanki.sahil.kisan.data.repository.SendMessageRepository;

public class MessageViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private SendMessageRepository sendMessageRepository;


    public MessageViewModelFactory(SendMessageRepository sendMessageRepository) {
        this.sendMessageRepository = sendMessageRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MessageViewModel(sendMessageRepository);
    }
}
