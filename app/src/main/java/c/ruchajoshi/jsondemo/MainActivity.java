package c.ruchajoshi.jsondemo;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Downloadtask downloadtask= new Downloadtask();
        downloadtask.execute("https://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=b6907d289e10d714a6e88b30761fae22");

    }


    public class Downloadtask extends AsyncTask<String,Void,String>{


        @Override
        protected String doInBackground(String... strings) {

            String result= "";
            URL url;
            HttpURLConnection httpURLConnection=null;


            try {
                url=new URL(strings[0]);

                httpURLConnection= (HttpURLConnection) url.openConnection();

                InputStream in= httpURLConnection.getInputStream();
                InputStreamReader reader= new InputStreamReader(in);

                int data =reader.read();

                while(data !=-1){

                    char current= (char) data;
                result +=current;
                data=reader.read();

                }
                return  result;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {
                JSONObject jsonObject= new JSONObject(result);

                String weatherInfo= jsonObject.getString("weather");
                Log.i("weather Content", weatherInfo);


                JSONArray array= new JSONArray(weatherInfo);

                for(int i=0; i< array.length();i++)
                {

                    JSONObject jsonObj= array.getJSONObject(i);
                    Log.i("Main", jsonObj.getString("main"));
                    Log.i("Description", jsonObj.getString("description"));

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }




        }



    }


}
