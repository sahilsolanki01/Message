package com.solanki.sahil.kisan.ui.main;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.single.PermissionListener;
import com.solanki.sahil.kisan.R;
import com.solanki.sahil.kisan.data.adapter.MyRecyclerViewAdapter;
import com.solanki.sahil.kisan.data.adapter.MyRecyclerViewAdapter_Message;
import com.solanki.sahil.kisan.data.adapter.RecyclerViewClickListener;
import com.solanki.sahil.kisan.data.model.Contact;
import com.solanki.sahil.kisan.data.model.ModelMessage;
import com.solanki.sahil.kisan.ui.info.ContactInfo;

import java.util.List;


/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String TAG = "TAG ------";
    private static final int REQUEST_SETTINGS = 101;

    private PageViewModel pageViewModel;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter, mAdapter_messages;



    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);

        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);


        }
        pageViewModel.setIndex(index);

    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        startLocationButtonClick();


        return root;
    }


    private void startLocationUpdates() {

        if(pageViewModel.getmIndex().getValue() ==1) {
            pageViewModel.init();
            pageViewModel.getData().observe(this, new Observer<List<Contact>>() {
                @Override
                public void onChanged(List<Contact> contacts) {
                    if(mAdapter != null)
                    mAdapter.notifyDataSetChanged();

                }
            });


            RecyclerViewClickListener listener = new RecyclerViewClickListener() {
                @Override
                public void onClick(View view, int position) {

                    Log.e("PlaceholderFragment", "onItemClick: ");
                    Intent intent = new Intent(getActivity(), ContactInfo.class);
                    Bundle extras = new Bundle();
                    extras.putString("name", pageViewModel.getData().getValue().get(position).getName());
                    extras.putString("number", pageViewModel.getData().getValue().get(position).getNumber());
                    intent.putExtras(extras);
                    startActivity(intent);
                }
            };

            mAdapter = new MyRecyclerViewAdapter(pageViewModel.getData().getValue(), listener);
            recyclerView.setAdapter(mAdapter);

        }else if(pageViewModel.getmIndex().getValue()==2){

            Log.e("mindex", "onCreateView: ");
            pageViewModel.init2();

            pageViewModel.getData2().observe(this, new Observer<List<ModelMessage>>() {
                @Override
                public void onChanged(List<ModelMessage> messages) {
                    mAdapter_messages.notifyDataSetChanged();

                }
            });


            mAdapter_messages = new MyRecyclerViewAdapter_Message(pageViewModel.getData2().getValue());
            recyclerView.setAdapter(mAdapter_messages);

        }
    }


    public void startLocationButtonClick() {

        Dexter.withActivity(getActivity())
                .withPermission(Manifest.permission.READ_CONTACTS)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {

                        startLocationUpdates();
                        Log.e(TAG, "onPermissionGranted:  Dexter is here  " + response.getPermissionName());

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        if (response.isPermanentlyDenied()) {
                            Log.e(TAG, "onPermissionDenied: ");
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        Log.e(TAG, "onPermissionRationaleShouldBeShown: ");
                        token.continuePermissionRequest();

                    }

                }).withErrorListener(new PermissionRequestErrorListener() {
            @Override
            public void onError(DexterError error) {
                Log.e("Dexter", "There was an error: " + error.toString());
            }
        }).check();
    }



    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                showSnackbar_Result();
            }
        });
        builder.show();

    }


    private void showSnackbar(final int mainTextStringId, final int actionStringId,
                              View.OnClickListener listener) {
        Snackbar.make(
                getActivity().findViewById(android.R.id.content),
                getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(actionStringId), listener).show();
    }

    private void showSnackbar_Result() {

        showSnackbar(R.string.restart_detail,
                R.string.alert, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getActivity().finish();
                        System.exit(0);
                    }
                });
    }


    private void openSettings() {

        Log.e(TAG, "openSettings: ");
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData((Uri.parse("package:" + getActivity().getPackageName())));
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivityForResult(intent, REQUEST_SETTINGS);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SETTINGS) {

            startLocationButtonClick();
        }
    }


}

