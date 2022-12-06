package com.dyas.bookingevent;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.dyas.bookingevent.utility.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ProfileFragment extends Fragment {

    public ProfileFragment() {
        // Required empty public constructor
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_profile, container, false);


        TextView tvNama = rootView.findViewById(R.id.tv_nama);
        TextView tvPhone = rootView.findViewById(R.id.tv_phone);
        TextView tvEmail = rootView.findViewById(R.id.tv_email);

        String strJsonMember = SessionManager.getUserObject(getActivity(),"jsonmember");



        try {
            JSONObject joMember = new JSONObject(strJsonMember);

            JSONArray jaMember = joMember.getJSONArray("result");

            JSONObject joMember2  = jaMember.getJSONObject(0);


            String strNama = joMember2.getString("nama");
            String strPhone = joMember2.getString("phone");
            String strEmail = joMember2.getString("email");
            Log.e("strNama==", ""+strNama);
            Log.e("strPhone==", ""+strPhone);
            Log.e("strEmail==", ""+strEmail);

            tvNama.setText(": "+strNama);
            tvEmail.setText(": "+strEmail);
            tvPhone.setText(": "+strPhone);










        } catch (JSONException e) {
            e.printStackTrace();
        }


        Button btnLogout =rootView.findViewById(R.id.btn_logout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SessionManager.setUserObject(getActivity(),null,"jsonmember");
                Intent myIntent = new Intent(getActivity(), LoginActivity.class);
                getActivity().startActivity(myIntent);
            }
        });


        return  rootView;
    }
}
