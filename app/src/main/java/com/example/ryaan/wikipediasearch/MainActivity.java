package com.example.ryaan.wikipediasearch;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    //@BindView(R.id.search_text)
    EditText searchText;
    DatabaseHandler databaseHandler;
    @BindView(R.id.result)
    RecyclerView result;
    RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        searchText=(EditText) findViewById(R.id.search_text);
        result.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        result.setLayoutManager(layoutManager);
        databaseHandler=new DatabaseHandler(this);
    }

   public class GetResult extends AsyncTask<Object, Object, String> {

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
       protected String doInBackground(Object... params) {
           // These two need to be declared outside the try/catch
           // so that they can be closed in the finally block.
           HttpURLConnection urlConnection = null;
           BufferedReader reader = null;
           // Will contain the raw JSON response as a string.
           String jsonSearchResult = null;
           try {
               URL url = new URL("https://en.wikipedia.org/w/api.php?action=opensearch&search=" + params[0] + "&limit=10&namespace=0&format=json");
               urlConnection = (HttpURLConnection) url.openConnection();
               urlConnection.setRequestMethod("GET");
               urlConnection.connect();
               // Read the input stream into a String
               InputStream inputStream = urlConnection.getInputStream();
               StringBuffer buffer = new StringBuffer();
               if (inputStream == null) {
                   jsonSearchResult = null;
               }
               reader = new BufferedReader(new InputStreamReader(inputStream));
               String line;
               while ((line = reader.readLine()) != null) {
                   buffer.append(line + "\n");
               }

               if (buffer.length() == 0) {
                   jsonSearchResult = null;
               }

               jsonSearchResult = buffer.toString();
           } catch (IOException e) {
               Log.e("PlaceholderFragment", "Error ", e);
               jsonSearchResult = null;
           }  finally {
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
           return jsonSearchResult;
       }
   }
   public void search(View v){

       GetResult getResult = new GetResult();
       getResult.execute(searchText.getText().toString());
       try {
           result.setAdapter(new ResultAdapter(getResult.get()));
           databaseHandler.addData(searchText.getText().toString(),getResult.get());

           Log.v("t2",databaseHandler.getData(searchText.getText().toString()));

       } catch (InterruptedException e) {
           e.printStackTrace();
       } catch (ExecutionException e) {
           e.printStackTrace();
       }

   }
}
