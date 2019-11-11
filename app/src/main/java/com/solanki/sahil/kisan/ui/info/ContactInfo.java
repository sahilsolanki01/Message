package com.solanki.sahil.kisan.ui.info;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import com.solanki.sahil.kisan.R;
import com.solanki.sahil.kisan.databinding.ActivityContactInfoBinding;

public class ContactInfo extends AppCompatActivity {

    private ContactInfoViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(ContactInfoViewModel.class);

        ActivityContactInfoBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_contact_info);
        binding.setViewmodel(viewModel);

        Bundle extras = getIntent().getExtras();
        String name = extras.getString("name");
        String number = extras.getString("number");

        binding.getViewmodel().setName(name);
        binding.getViewmodel().setNumber(number);
        binding.getViewmodel().setContext(this);
        binding.getViewmodel().init();


    }


}
