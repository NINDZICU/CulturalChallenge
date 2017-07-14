package com.kpfu.itis.culturalchallenge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kpfu.itis.culturalchallenge.adapters.TasksRecyclerAdapter;
import com.kpfu.itis.culturalchallenge.entities.Task;
import com.kpfu.itis.culturalchallenge.fragments.AuthentificationActivity;
import com.kpfu.itis.culturalchallenge.fragments.AuthentificationFragment;
import com.kpfu.itis.culturalchallenge.providers.SharedPreferencesProvider;
import com.kpfu.itis.culturalchallenge.service.ApiService;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKList;

import java.util.Arrays;
import java.util.List;

import com.kpfu.itis.culturalchallenge.custom.TabViewWrapper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements TabViewWrapper.TabListener{

    @BindView(R.id.text_of_fact)
    TextView textOfFact;
    @BindView(R.id.task_for_today)
    TextView taskForToday;
    @BindView(R.id.tasks_recycler_view)
    RecyclerView tasksRecyclerView;
    @BindView(R.id.tabs)
    LinearLayout mTabLayout;
    private ApiService apiService;
    private VKAccessToken access_token;
    private TasksRecyclerAdapter taskAdapter;
    private TabViewWrapper mTabViewWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        ButterKnife.bind(this);
        mTabViewWrapper=new TabViewWrapper(mTabLayout);
        mTabViewWrapper.setSelectedTab(0);
        mTabViewWrapper.setSelectColor(R.color.button_pressed);
        mTabViewWrapper.setTabListenerClick(this);

        access_token = VKAccessToken.tokenFromSharedPreferences(this, VKAccessToken.ACCESS_TOKEN);
        if (!VKSdk.isLoggedIn()) {
            Intent intent = new Intent(this, AuthentificationActivity.class);
            startActivity(intent);

        } else {

            System.out.println(access_token.accessToken);
             VKRequest request = VKApi.friends().getAppUsers(VKParameters.from());
//            VKRequest request = new VKRequest("friends.getAppUsers", null);
            request.executeWithListener(new VKRequest.VKRequestListener() {
                @Override
                public void onError(VKError error) {
                    super.onError(error);
                    System.out.println("ERRROORORORR "+error.errorMessage);
                }
                @Override
                public void onComplete(VKResponse response) {
                    super.onComplete(response);
                    VKList list = (VKList) response.parsedModel;
                    System.out.println("FRIENDS IN APP "+Arrays.asList(list));
                }
            });

            apiService = new ApiService(MainActivity.this);

            textOfFact = (TextView) findViewById(R.id.text_of_fact);
            taskForToday = (TextView) findViewById(R.id.task_for_today);
            tasksRecyclerView = (RecyclerView) findViewById(R.id.tasks_recycler_view);

            taskAdapter = new TasksRecyclerAdapter(MainActivity.this, this);
            List<Task> mTasks = apiService.getMyTasks(SharedPreferencesProvider.getInstance(getApplicationContext()).getVkId(), taskAdapter);
            System.out.println(mTasks.size());

            tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            tasksRecyclerView.setAdapter(taskAdapter);
        }

    }

    @Override
    public void onTabClick(int position, View tab) {


    }
}
