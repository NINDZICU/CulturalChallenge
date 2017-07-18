package com.kpfu.itis.culturalchallenge.fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.kpfu.itis.culturalchallenge.R;
import com.kpfu.itis.culturalchallenge.providers.SharedPreferencesProvider;
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

/**
 * Created by Anatoly on 11.07.2017.
 */

public class AuthentificationFragment extends Fragment {
    private Button btnEnter;
    private String[] scope = new String[]{VKScope.FRIENDS};
    private VKAccessToken access_token;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnEnter = (Button) view.findViewById(R.id.btn_enter);

        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!VKSdk.isLoggedIn()) {
                    VKSdk.login(getActivity(), scope);
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
                access_token.saveTokenToSharedPreferences(getActivity().getApplicationContext(), VKAccessToken.ACCESS_TOKEN);


                final VKRequest request = VKApi.users().get(VKParameters.from(VKApiConst.ACCESS_TOKEN, access_token.accessToken));
                request.executeWithListener(new VKRequest.VKRequestListener() {
                    @Override
                    public void onError(VKError error) {
                        System.out.println("ERRRRRRRRRRRRRRRRRRRRRRRRRRRRRROOOOOOOOORRRRRRRRRR K");
                    }

                    @Override
                    public void onComplete(VKResponse response) {
                        super.onComplete(response);
                        VKList list = (VKList) response.parsedModel;
                        for (int i = 0; i < list.size(); i++) {
                            try {
                                System.out.println("ID " + list.get(i).fields.get("id"));
                                String vkID = list.get(i).fields.get("id").toString();
                                SharedPreferencesProvider.getInstance(getContext()).saveVkId(vkID);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                });
                getActivity().getSupportFragmentManager().popBackStack();
            }

            @Override
            public void onError(VKError error) {
// Произошла ошибка авторизации (например, пользователь запретил авторизацию)
                Toast.makeText(getActivity().getApplicationContext(), "Error authentification", Toast.LENGTH_SHORT).show();
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
