package com.kpfu.itis.culturalchallenge.fragments;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.kpfu.itis.culturalchallenge.R;
import com.kpfu.itis.culturalchallenge.providers.SharedPreferencesProvider;
import com.kpfu.itis.culturalchallenge.service.ApiService;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKList;

import org.json.JSONException;

public class AuthentificationActivity extends AppCompatActivity {
    private Button btnEnter;
    private String[] scope = new String[]{VKScope.FRIENDS};
    private VKAccessToken access_token;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        btnEnter = (Button) findViewById(R.id.btn_enter);

        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!VKSdk.isLoggedIn()) {
                    VKSdk.login(AuthentificationActivity.this, scope);
                }
            }
        });

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
// Пользователь успешно авторизовался
                access_token = res;
                access_token.saveTokenToSharedPreferences(getApplicationContext(), VKAccessToken.ACCESS_TOKEN);


                final VKRequest request = VKApi.users().get(VKParameters.from(VKApiConst.ACCESS_TOKEN, access_token.accessToken));
                request.executeWithListener(new VKRequest.VKRequestListener() {
                    @Override
                    public void onError(VKError error) {

                    }
                    @Override
                    public void onComplete(VKResponse response) {
                        super.onComplete(response);
                        VKList list = (VKList) response.parsedModel;
                        for (int i = 0; i < list.size(); i++) {
                            try {
                                System.out.println("ID " + list.get(i).fields.get("id"));
                                String vkID = list.get(i).fields.get("id").toString();
                                SharedPreferencesProvider.getInstance(AuthentificationActivity.this).saveVkId(vkID);
                                SharedPreferencesProvider.getInstance(AuthentificationActivity.this)
                                        .saveVkName(list.get(i).fields.get("first_name").toString()+list.get(i).fields.get("last_name"));
                                apiService = new ApiService(getApplicationContext());
                                apiService.saveUser(vkID, list.get(i).fields.get("first_name").toString()+list.get(i).fields.get("last_name"), "kazan" );
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        finish();
                    }

                });
            }

            @Override
            public void onError(VKError error) {
// Произошла ошибка авторизации (например, пользователь запретил авторизацию)
                Toast.makeText(getApplicationContext(), "Error authentification", Toast.LENGTH_SHORT).show();
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
