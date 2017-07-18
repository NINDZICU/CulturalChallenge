package com.kpfu.itis.culturalchallenge.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kpfu.itis.culturalchallenge.R;
import com.kpfu.itis.culturalchallenge.providers.SharedPreferencesProvider;
import com.kpfu.itis.culturalchallenge.service.ApiService;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Anatoly on 18.07.2017.
 */

public class NewTaskFragment extends Fragment {
    private ApiService apiService;
    @BindView(R.id.name_new_task)
    TextView tvNameTask;
    @BindView(R.id.text_new_task)
    TextView tvTextTask;
    @BindView(R.id.address_new_task)
    TextView tvAddressTask;
    @BindView(R.id.city_new_task)
    TextView tvCityTask;

    public static NewTaskFragment getInstance(){
        return new NewTaskFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.challenge,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        apiService = new ApiService(getContext().getApplicationContext());
        if(!tvNameTask.getText().equals("")&&!tvTextTask.getText().equals("")&&!tvAddressTask.getText().equals("")&&
                !tvCityTask.getText().equals("")){
            apiService.addTask(tvAddressTask.getText().toString(), SharedPreferencesProvider.getInstance(getContext()).getVkId(),
                    "dateFinish",tvNameTask.getText().toString(), tvTextTask.getText().toString(), "difficulty",
                    tvCityTask.getText().toString());
        }



    }
}
