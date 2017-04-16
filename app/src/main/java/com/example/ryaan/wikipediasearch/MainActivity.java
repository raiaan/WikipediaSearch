package com.example.ryaan.wikipediasearch;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        GetResult getResult=new GetResult();
        getResult.execute("egypt");
    }

   public class GetResult extends AsyncTask<String,Void,Void>{

       /**
        * Override this method to perform a computation on a background thread. The
        * specified parameters are the parameters passed to {@link #execute}
        * by the caller of this task.
        * <p>
        *
        * This method can call {@link #publishProgress} to publish updates
        * on the UI thread.
        *
        * @param params The parameters of the task.
        * @return A result, defined by the subclass of this task.
        * @see #onPreExecute()
        * @see #onPostExecute
        * @see #publishProgress
        */
       @Override
       protected Void doInBackground(String... params) {
           // These two need to be declared outside the try/catch
           // so that they can be closed in the finally block.
           HttpURLConnection urlConnection = null;
           BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
           String forecastJsonStr = null;

           try {
               // Construct the URL for the OpenWeatherMap query
               // Possible parameters are available at OWM's forecast API page, at
               // http://openweathermap.org/API#forecast
               URL url = new URL("https://en.wikipedia.org/w/api.php?action=opensearch&search="+params[0]+"&limit=10&namespace=0&format=json");

               // Create the request to OpenWeatherMap, and open the connection
               urlConnection = (HttpURLConnection) url.openConnection();
               urlConnection.setRequestMethod("GET");
               urlConnection.connect();

               // Read the input stream into a String
               InputStream inputStream = urlConnection.getInputStream();
               StringBuffer buffer = new StringBuffer();
               if (inputStream == null) {
                   // Nothing to do.
                   forecastJsonStr = null;
               }
               reader = new BufferedReader(new InputStreamReader(inputStream));

               String line;
               while ((line = reader.readLine()) != null) {
                   // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                   // But it does make debugging a *lot* easier if you print out the completed
                   // buffer for debugging.
                   buffer.append(line + "\n");
               }

               if (buffer.length() == 0) {
                   // Stream was empty.  No point in parsing.
                   forecastJsonStr = null;
               }
               forecastJsonStr = buffer.toString();
               Log.v("msg",forecastJsonStr);
           } catch (IOException e) {
               Log.e("PlaceholderFragment", "Error ", e);
               // If the code didn't successfully get the weather data, there's no point in attempting
               // to parse it.
               forecastJsonStr = null;
           } finally{
               if (urlConnection != null) {
                   urlConnection.disconnect();
               }
               if (reader != null) {
                   try {
                       reader.close();
                   } catch (final IOException e) {
                       Log.e("PlaceholderFragment", "Error closing stream", e);
                   }
               }
           }
           return null;
       }
   }
}
