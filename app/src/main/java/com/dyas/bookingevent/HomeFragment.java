package com.dyas.bookingevent;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.dyas.bookingevent.utility.Constant;
import com.dyas.bookingevent.utility.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class HomeFragment extends Fragment {
    public RequestWorkerDetails adapterlist;
    public HomeFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        GridView gridView = rootView.findViewById(R.id.gv);

        adapterlist = new RequestWorkerDetails(getActivity());
        gridView.setAdapter(adapterlist);
        getKategory();


        return  rootView;
    }


    public void getKategory() {

        URL url = null;
        try {
            url = new URL(Constant.Code.GET_KATEGORI);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection con = null;
        try {
            con = (HttpURLConnection) url.openConnection();

        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(
                con.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            System.out.println(inputLine);

            try {
                JSONObject newJO = new JSONObject(inputLine);
                JSONArray jsonArrayKategori =newJO.getJSONArray("result");
                for(int i=0;i<jsonArrayKategori.length();i++){
                    JSONObject jO = jsonArrayKategori.getJSONObject(i);
                    String strNama = jO.getString("namakategori");
                    String strImage = jO.getString("imagekategori");
                    Log.e("strNama====index"+i,""+strNama);
                    adapterlist.add(new RequestWorkerDetail(strNama, strImage));


                }



            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }




    private class RequestWorkerDetail {
        public String _nama;
        public String _image;



        public RequestWorkerDetail(String nama, String image) {

            this._image = image;
            this._nama = nama;



        }
    }

    public class RequestWorkerDetails extends ArrayAdapter<RequestWorkerDetail> {

        public RequestWorkerDetails(Context context) {
            super(context, 0);
        }

        public class ViewHolder {

            TextView tvNama;
            ImageView imgKategori;


        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            final RequestWorkerDetails.ViewHolder holder;
            holder = new RequestWorkerDetails.ViewHolder();
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.items_kategori, null);

            }


            holder.imgKategori=convertView.findViewById(R.id.img_kategori);
            holder.tvNama=convertView.findViewById(R.id.tv_nama_kategory);

            holder.tvNama.setText(getItem(position)._nama);
            new DownloadImageTask(holder.imgKategori)
                    .execute(getItem(position)._image);
            convertView.setTag(holder);


            return convertView;
        }

    }
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}