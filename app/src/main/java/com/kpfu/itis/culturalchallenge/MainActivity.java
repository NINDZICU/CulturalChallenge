package com.kpfu.itis.culturalchallenge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.kpfu.itis.culturalchallenge.adapters.TasksRecyclerAdapter;
import com.kpfu.itis.culturalchallenge.fragments.AuthentificationFragment;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.methods.VKApiFriends;
import com.vk.sdk.api.model.VKList;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private TextView textOfFact;
    private TextView taskForToday;
    private RecyclerView tasksRecyclerView;
    private TasksRecyclerAdapter taskAdapter;
    private VKAccessToken access_token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        access_token = VKAccessToken.tokenFromSharedPreferences(this, VKAccessToken.ACCESS_TOKEN);
        if (!VKSdk.isLoggedIn()) {
            AuthentificationFragment fragment = new AuthentificationFragment();
            getFragmentManager().beginTransaction()
                    .replace(R.id.task_detail_frame, fragment, AuthentificationFragment.class.getName()).commit();
        } else {

            VKRequest request = VKApi.friends().getAppUsers(VKParameters.from(VKApiConst.FIELDS, "first_name, last_name"));
            request.executeWithListener(new VKRequest.VKRequestListener() {
                @Override
                public void onComplete(VKResponse response) {
                    super.onComplete(response);
                    VKList list = (VKList) response.parsedModel;
                    System.out.println(Arrays.asList(list));
                }
            });

            textOfFact = (TextView) findViewById(R.id.text_of_fact);
            taskForToday = (TextView) findViewById(R.id.task_for_today);
            tasksRecyclerView = (RecyclerView) findViewById(R.id.tasks_recycler_view);

            taskAdapter = new TasksRecyclerAdapter(MainActivity.this, this);

            tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            tasksRecyclerView.setAdapter(taskAdapter);
        }


    }
}
