package com.dyas.bookingevent;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dyas.bookingevent.utility.Constant;
import com.dyas.bookingevent.utility.SessionManager;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);




        EditText etNama = findViewById(R.id.et_nama);
        EditText etPhone = findViewById(R.id.et_phone);
        EditText etEmail = findViewById(R.id.et_email);
        EditText etPass = findViewById(R.id.et_password);
        EditText etRePass = findViewById(R.id.et_repassword);
        Button btnRegister = findViewById(R.id.btn_reg);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String strNama = etNama.getText().toString();
                String strPhone = etPhone.getText().toString();
                String strEmail = etEmail.getText().toString();
                String strPass = etPass.getText().toString();
                String strRePass = etRePass.getText().toString();




                if(!strPass.equalsIgnoreCase(strRePass)) {

                    new AlertDialog.Builder(RegisterActivity.this)
                            .setTitle("Warning")
                            .setMessage("Password tidak sama")
                            .setCancelable(false)
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                 dialog.dismiss();
                                }
                            }).show();
                }else{
                    sendPost(strNama, strPhone, strEmail, strPass);
                }


            }
        });
    }



    public void sendPost(String nama, String phone, String email, String pass) {

        try {

            String urlAdress = Constant.Code.REGISTER;
            URL url = new URL(urlAdress);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            JSONObject jsonParam = new JSONObject();
            jsonParam.put("nama", nama);
            jsonParam.put("phone", phone);
            jsonParam.put("email", email);
            jsonParam.put("password", pass);


            Log.i("JSON", jsonParam.toString());
            DataOutputStream os = new DataOutputStream(conn.getOutputStream());
            //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
            os.writeBytes(jsonParam.toString());

            os.flush();
            os.close();

            Log.i("STATUS", String.valueOf(conn.getResponseCode()));


            if (conn.getResponseCode() == 200) {


                //   success = true;
                InputStream response = conn.getInputStream();
                String jsonReply = convertStreamToString(response);
                SessionManager.setUserObject(getApplicationContext(),jsonReply,"jsonmember");

                new AlertDialog.Builder(RegisterActivity.this)
                        .setTitle("SUKSES")
                        .setMessage(jsonReply)
                        .setCancelable(false)
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Intent myIntent = new Intent(RegisterActivity.this, MainActivity.class);

                                RegisterActivity.this.startActivity(myIntent);
                            }
                        }).show();
            }

            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}