package com.example.camilih.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends Activity {
    TextView textView;
    Button butsave;
    CheckBox High_Blood_pressure,Low_Blood_pressure,Heart_failure,Heart_attack,Varicose_veins,CVA,Heart_disease;
    String high_Blood_pressure;
    String low_Blood_pressure;
    String heart_failure;
    String heart_attack;
    String varicose_veins;
    String cVA;
    String heart_disease;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        butsave=(Button)findViewById(R.id.butSave);
        High_Blood_pressure=(CheckBox)findViewById(R.id.cb1);
        Low_Blood_pressure=(CheckBox)findViewById(R.id.cb2);
        Heart_failure=(CheckBox)findViewById(R.id.cb3);
        Heart_attack=(CheckBox)findViewById(R.id.cb4);
        Varicose_veins=(CheckBox)findViewById(R.id.cb5);
        CVA=(CheckBox)findViewById(R.id.cb6);
        Heart_disease=(CheckBox)findViewById(R.id.cb7);
        textView=(TextView)findViewById(R.id.tvNet);
        ConnectivityManager connectivityManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected())
        {
            textView.setVisibility(View.INVISIBLE);
        }
        else
        {
            butsave.setEnabled(false);
        }
    }
    public void saveInfo(View view)

    {
        high_Blood_pressure=High_Blood_pressure.getText().toString();
        low_Blood_pressure=Low_Blood_pressure.getText().toString();
        heart_failure=Heart_failure.getText().toString();
        heart_attack=Heart_attack.getText().toString();
        varicose_veins=Varicose_veins.getText().toString();
        cVA=CVA.getText().toString();
        heart_disease=Heart_disease.getText().toString();
        BackgroundTask backgroundTask=new BackgroundTask();
        backgroundTask.execute(high_Blood_pressure,low_Blood_pressure,heart_failure,heart_attack,varicose_veins,cVA,heart_disease);

    }



    class BackgroundTask extends AsyncTask<String,Void,String> {
        String save_url;

        @Override
        protected void onPreExecute() {

            save_url = "http://project2017.comli.com/seconedform.php";
        }


        @Override
        protected String doInBackground(String... args) {

            String high_Blood_pressure, low_Blood_pressure, heart_failure, heart_attack, varicose_veins, cVA, heart_disease;
            high_Blood_pressure = args[0];
            low_Blood_pressure = args[1];
            heart_failure = args[2];
            heart_attack = args[3];
            varicose_veins = args[4];
            cVA = args[5];
            heart_disease = args[6];

            try {
                URL url = new URL(save_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data_string = URLEncoder.encode("high_blood_pressure", "UTF-8") + "=" + URLEncoder.encode(high_Blood_pressure, "UTF-8") + "&" +
                        URLEncoder.encode("low_blood_pressure", "UTF-8") + "=" + URLEncoder.encode(low_Blood_pressure, "UTF-8") + "&" +
                        URLEncoder.encode("heart_failure", "UTF-8") + "=" + URLEncoder.encode(heart_failure, "UTF-8") + "&" +
                        URLEncoder.encode("heart_attack", "UTF-8") + "=" + URLEncoder.encode(heart_attack, "UTF-8") + "&" +
                        URLEncoder.encode("varicose_veins", "UTF-8") + "=" + URLEncoder.encode(varicose_veins, "UTF-8") + "&" +
                        URLEncoder.encode(" CVA", "UTF-8") + "=" + URLEncoder.encode(cVA, "UTF-8") + "&" +
                        URLEncoder.encode("heart_disease", "UTF-8") + "=" + URLEncoder.encode(heart_disease, "UTF-8");
                bufferedWriter.write(data_string);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                inputStream.close();
                //httpURLConnection.connect();
                httpURLConnection.disconnect();
                return "inserted ...";
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {

            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
        }


    }

}