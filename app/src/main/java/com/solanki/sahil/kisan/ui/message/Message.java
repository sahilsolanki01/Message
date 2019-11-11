package com.solanki.sahil.kisan.ui.message;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.solanki.sahil.kisan.R;
import com.solanki.sahil.kisan.data.model.ModelMessage;
import com.solanki.sahil.kisan.data.network.Api;
import com.solanki.sahil.kisan.data.network.Network_Interceptor;
import com.solanki.sahil.kisan.data.network.RetrofitInstance;
import com.solanki.sahil.kisan.data.repository.SendMessageRepository;
import com.solanki.sahil.kisan.databinding.ActivityMessageBinding;

import java.util.List;

import static com.solanki.sahil.kisan.data.provider.App.getContext;



public class Message extends AppCompatActivity implements MessageListener {

    private MessageViewModel messageViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Network_Interceptor network_interceptor = new Network_Interceptor(getContext());
        Api api = RetrofitInstance.getApi(network_interceptor);
        SendMessageRepository sendMessageRepository = new SendMessageRepository(api);
        MessageViewModelFactory factory = new MessageViewModelFactory(sendMessageRepository);
        messageViewModel = ViewModelProviders.of(this, factory).get(MessageViewModel.class);

        ActivityMessageBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_message);
        binding.setModel(messageViewModel);

        Bundle bundle = getIntent().getExtras();
        binding.getModel().init(bundle.getString("number"), this);

        binding.getModel().messageListener = this;

    }

    @Override
    public void onSuccess(LiveData<List<ModelMessage>> response) {
        response.observe(this, new Observer<List<ModelMessage>>() {
            @Override
            public void onChanged(List<ModelMessage> s) {

                Log.e("message**&  ", "onChanged: " + s);

            }
        });
    }

}
