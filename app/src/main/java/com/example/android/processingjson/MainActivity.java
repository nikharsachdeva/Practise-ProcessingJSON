package com.example.android.processingjson;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    String main1,desc1,loc;
    TextView main,desc,cel;
    Button button;
    EditText location;
    DownloadTask task;
    int tempp;


    public void submitFun(View view)
    {
        loc = location.getText().toString();
        //Log.i("Check123",loc);

        task = new DownloadTask();
        task.execute("http://api.openweathermap.org/data/2.5/weather?q="+loc+"&APPID=6257a594149c0f8653f4e4cf68d6c303");
    }

    public class DownloadTask extends AsyncTask<String,Void,String>{


        @Override
        protected String doInBackground(String... urls) {
            String result ="";
            URL url;
            HttpURLConnection urlConnection = null;

            try{
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                while (data!=-1)
                {
                    char current = (char) data;
                    result+=current;

                    data=reader.read();
                } return result;

            }catch (Exception e)
            {
                e.printStackTrace();
                return null;
            }


        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try{
                JSONObject jsonObject = new JSONObject(s);

                String weatherInfo = jsonObject.getString("weather");
                JSONArray jsonArray = new JSONArray(weatherInfo);
                for(int i=0;i<jsonArray.length();i++)
                {
                    JSONObject JSONPart = jsonArray.getJSONObject(i);

                    main1 = JSONPart.getString("main");
                    desc1 =JSONPart.getString("description");

//                    Log.i("Main = ",main1);
//                    Log.i("Description =",desc1);
                    main.setText(main1);
                    desc.setText(desc1);

                }

                JSONObject JSONPart2 = jsonObject.getJSONObject("main");
                tempp = JSONPart2.getInt("temp");
                cel.setText(Integer.toString(tempp-273));


            }catch (Exception e)
            {
                e.printStackTrace();
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        main = (TextView)findViewById(R.id.main);
        desc = (TextView)findViewById(R.id.desc);
        button = (Button) findViewById(R.id.button);
        location = (EditText) findViewById(R.id.location);
        cel=(TextView) findViewById(R.id.textView4);

    }
}
