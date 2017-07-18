package com.kpfu.itis.culturalchallenge.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.kpfu.itis.culturalchallenge.R;
import com.kpfu.itis.culturalchallenge.api.art.ArtApi;
import com.kpfu.itis.culturalchallenge.entities.Authority;
import com.kpfu.itis.culturalchallenge.entities.User;
import com.kpfu.itis.culturalchallenge.providers.SharedPreferencesProvider;
import com.kpfu.itis.culturalchallenge.service.ApiService;

import java.util.Iterator;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Rage on 14.07.2017.
 */

public class Fragment4 extends Fragment {

    @BindView(R.id.profile_name)
    TextView tvProfileName;
    @BindView(R.id.profile_level)
    TextView tvLevel;
    @BindView(R.id.profile_coins)
    TextView tvCoins;
    @BindView(R.id.btn_add_task)
    Button btnAdd;

    private ApiService apiService;
    private ArtApi artApi = new ArtApi();


    public static Fragment4 getInstance(){
        return new Fragment4();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        artApi.getUser(SharedPreferencesProvider.getInstance(getContext()).getVkId()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> {
                    tvProfileName.setText(SharedPreferencesProvider.getInstance(getContext()).getVkName());
                    tvLevel.setText(String.valueOf(user.getLevel()));
                    tvCoins.setText("0");
                    Iterator<Authority> iter= user.getAuthorities().iterator();
                    while(iter.hasNext()){
                        if(iter.next().getAuthority().equals("ADMIN")){
                            btnAdd.setVisibility(View.VISIBLE);
                        }
                    }

                }, throwable ->{
                        Toast.makeText(getContext(), "Throw " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
        System.out.println("getUSer "+throwable.getMessage());
                        });

        apiService = new ApiService(getContext().getApplicationContext());

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewTaskFragment fragment = new NewTaskFragment().newInstance();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .add(R.id.add_task_frame, fragment, TaskDetailFragment.class.getName()).commit();
            }
        });
    }
}
