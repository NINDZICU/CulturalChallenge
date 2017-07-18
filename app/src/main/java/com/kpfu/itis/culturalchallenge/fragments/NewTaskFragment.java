package com.kpfu.itis.culturalchallenge.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.kpfu.itis.culturalchallenge.R;
import com.kpfu.itis.culturalchallenge.providers.SharedPreferencesProvider;
import com.kpfu.itis.culturalchallenge.service.ApiService;
import com.kpfu.itis.culturalchallenge.util.ConfirmTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Anatoly on 18.07.2017.
 */

public class NewTaskFragment extends Fragment {
    private ApiService apiService;
    private ConfirmTask mConfirmTask;
    private ProgressDialog mProgressDialog;
    @BindView(R.id.name_new_task)
    EditText tvNameTask;
    @BindView(R.id.text_new_task)
    EditText tvTextTask;
    @BindView(R.id.address_new_task)
    EditText tvAddressTask;
    @BindView(R.id.city_new_task)
    EditText tvCityTask;
    @BindView(R.id.btn_send_task)
    Button btnSendTask;
    @BindView(R.id.btn_date_picker)
    Button btnShowDatePicker;
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;

    private ConfirmTask confirmTask;
    private String date="";

    public static NewTaskFragment newInstance() {
        return new NewTaskFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.challenge, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        mConfirmTask = new ConfirmTask(getContext());
        btnSendTask.setOnClickListener(v -> {
            apiService = new ApiService(getContext().getApplicationContext());
            final String difficulty;
            switch (radioGroup.getCheckedRadioButtonId()) {
                case R.id.radio_easy:
                    difficulty = "EASY";
                    break;
                case R.id.radio_middle:
                    difficulty = "MIDDLE";
                    break;
                case R.id.radio_hard:
                    difficulty = "HARD";
                    break;
                default:
                    difficulty = "";
                    break;
            }

            if (!tvNameTask.getText().toString().equals("") && !tvTextTask.getText().toString().equals("") && !tvAddressTask.getText().toString().equals("") &&
                    !tvCityTask.getText().toString().equals("") && !date.equals("")) {
                mProgressDialog = ProgressDialog.show(getContext(),
                        getContext().getString(R.string.check_address), getContext().getString(R.string.loading), true, false);
                mConfirmTask.exists(tvAddressTask.getText().toString()+" "+tvCityTask.getText().toString()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(exist -> {
                            if (exist) {
                                apiService.addTask(tvAddressTask.getText().toString(), SharedPreferencesProvider.getInstance(getContext()).getVkId(),
                                        date, tvNameTask.getText().toString(), tvTextTask.getText().toString(), difficulty,
                                        tvCityTask.getText().toString());
                                getActivity().getSupportFragmentManager().beginTransaction().remove(NewTaskFragment.this).commit();
                            }else{
                                Toast.makeText(getContext(),getContext().getString(R.string.address_not_exist),Toast.LENGTH_SHORT).show();
                            }
                        }, throwable -> throwable.printStackTrace(), () -> {
                            if (mProgressDialog != null) {
                                mProgressDialog.dismiss();
                            }
                        });

            }
        });

        btnShowDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker dateDialog = new DatePicker();
                dateDialog.setNewTaskFragment(NewTaskFragment.this);
                dateDialog.show(getActivity().getSupportFragmentManager(), "datePicker");
            }
        });


    }

    public void setDate(String date) {
        this.date = date;
    }
}
