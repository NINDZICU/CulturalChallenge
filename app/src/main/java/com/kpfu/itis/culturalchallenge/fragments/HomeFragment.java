package com.kpfu.itis.culturalchallenge.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.kpfu.itis.culturalchallenge.MainActivity;
import com.kpfu.itis.culturalchallenge.R;
import com.kpfu.itis.culturalchallenge.adapters.TasksRecyclerAdapter;
import com.kpfu.itis.culturalchallenge.entities.Task;
import com.kpfu.itis.culturalchallenge.providers.SharedPreferencesProvider;
import com.kpfu.itis.culturalchallenge.service.ApiService;
import com.kpfu.itis.culturalchallenge.util.ConfirmTask;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKSdk;
import com.vk.sdk.VKUIHelper;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKList;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Rage on 14.07.2017.
 */

public class HomeFragment extends Fragment {

    @BindView(R.id.text_of_fact)
    TextView textOfFact;
//    @BindView(R.id.task_for_today)
//    TextView taskForToday;
    @BindView(R.id.tasks_recycler_view)
    RecyclerView tasksRecyclerView;

    private ProgressDialog mProgressDialog;
    private ApiService apiService;
    private ConfirmTask mConfirmTask;
    private VKAccessToken access_token;
    private TasksRecyclerAdapter taskAdapter;

    public static HomeFragment getInstance() {
        return new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        mConfirmTask=new ConfirmTask(getContext());
        access_token = VKAccessToken.tokenFromSharedPreferences(getContext(), VKAccessToken.ACCESS_TOKEN);
        if (!VKSdk.isLoggedIn()) {
            Intent intent = new Intent(getActivity(), AuthentificationActivity.class);
            startActivity(intent);
//            AuthentificationFragment fragment = new AuthentificationFragment();
//            getChildFragmentManager().beginTransaction()
//                    .replace(R.id.task_detail_frame, fragment, AuthentificationFragment.class.getName()).commit();
        } else {
            final VKRequest request = VKApi.friends().getAppUsers(VKParameters.from(VKApiConst.ACCESS_TOKEN, access_token.accessToken));
            request.executeWithListener(new VKRequest.VKRequestListener() {
                @Override
                public void onError(VKError error) {
                    super.onError(error);
                }

                @Override
                public void onComplete(VKResponse response) {
                    super.onComplete(response);
                    VKList list = (VKList) response.parsedModel;
                    System.out.println(Arrays.asList(list));
                }
            });

            apiService = new ApiService(getContext().getApplicationContext());
            taskAdapter = new TasksRecyclerAdapter();
            taskAdapter.setTaskListener(task -> {
                TaskDetailFragment fragment = new TaskDetailFragment().newInstance(task);
                fragment.setTaskListener(task1 -> {
                    try {
                        mProgressDialog=ProgressDialog.show(getContext(),getContext().getString(R.string.confirm_address)
                                ,getContext().getString(R.string.loading),true,false);
                        mConfirmTask.confirm(task1.getAddress()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                                .subscribe(confirm->{
                                    if(confirm){
                                        Log.d("GEO","GPS SUCCESS" );
                                        apiService.successTask(SharedPreferencesProvider.getInstance(getContext()).getVkId(),
                                               task1.getId() );
                                    }else{
                                        Toast.makeText(getContext(), "Вы не находитесь в нужном месте", Toast.LENGTH_LONG).show();
                                    }
                                },throwable -> {},()->mProgressDialog.dismiss());
                    } catch (Exception e) {
                        e.printStackTrace();
                        if(mProgressDialog!=null)mProgressDialog.dismiss();
                        Toast.makeText(getContext(),getContext().getString(R.string.enable_gps),Toast.LENGTH_SHORT).show();
                    }
                });
                fragment.setTaskListenerDone(task1 -> {
                    getActivity().getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                });
                getChildFragmentManager().beginTransaction()
                        .add(R.id.task_detail_frame, fragment, TaskDetailFragment.class.getName()).commit();
                fragment.setTextConfirm("Завершить?");
            });

            List<Task> mTasks = apiService.getMyTasks(SharedPreferencesProvider.getInstance(getContext().getApplicationContext()).getVkId(), taskAdapter);
            tasksRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            tasksRecyclerView.setAdapter(taskAdapter);
        }
    }

    public void notifyDataSetChanged() {
        apiService.getMyTasks(SharedPreferencesProvider.getInstance(getContext()).getVkId(), taskAdapter);
    }


}
