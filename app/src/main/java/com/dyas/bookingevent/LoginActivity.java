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

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        EditText etEmail = findViewById(R.id.et_email_login);
        EditText etPassword = findViewById(R.id.et_pass_login);

        Button btnLogin = findViewById(R.id.btn_login);
        Button btnRegister = findViewById(R.id.btn_register);



        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String strEmail = etEmail.getText().toString();
                String strPassword = etPassword.getText().toString();

                sendPost(strEmail,strPassword);

            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(myIntent);
            }
        });

    }

    public void sendPost( String email, String pass) {

        try {
         String urlAdress = Constant.Code.LOGIN;

         //   String urlAdress ="http://192.168.100.5/bookingevent-rest/index.php/member/loginMember/format/json";
            Log.e("URL=======", ""+urlAdress);
            URL url = new URL(urlAdress);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            JSONObject jsonParam = new JSONObject();
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
                Log.e("JSONREPLAY=====", ""+jsonReply);
                SessionManager.setUserObject(getApplicationContext(),jsonReply,"jsonmember");

                Intent myIntent = new Intent(LoginActivity.this, MainActivity.class);

             LoginActivity.this.startActivity(myIntent);

            }
            else if(conn.getResponseCode()==201){
                new AlertDialog.Builder(LoginActivity.this)
                        .setTitle("Gagal")
                        .setMessage("Email/Password Salah")
                        .setCancelable(false)
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
               dialog.dismiss();
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