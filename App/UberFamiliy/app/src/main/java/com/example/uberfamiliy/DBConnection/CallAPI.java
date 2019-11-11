package com.example.uberfamiliy.DBConnection;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static android.content.ContentValues.TAG;

public class CallAPI extends AsyncTask<String, Void, String> {
    private String method;
    private String body;
    public CallAPIResponse delegate = null;

    // This is a constructor that allows you to pass in the JSON body
    public CallAPI(String body, String method, CallAPIResponse delegate) {

        this.delegate = delegate;

        if (body != null) {
            this.body = body;
        }
        if (method != null) {
            this.method = method;
        }
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            URL url = new URL(strings[0]);

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestMethod(this.method);

            if (body != null) {
                byte[] postData = body.getBytes(StandardCharsets.UTF_8);
                urlConnection.setRequestProperty("Content-Length", Integer.toString(postData.length));
                try (DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream())) {
                    wr.write(postData);
                }
            }
            int responseCode = urlConnection.getResponseCode();
            if (responseCode == 200) {
                InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                return convertInputStreamToString(inputStream);
            }

            urlConnection.disconnect();
        } catch (Exception e) {
            Log.d(TAG, e.getLocalizedMessage());
        }
        return "";
    }

    private String convertInputStreamToString(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    @Override
    protected void onPostExecute(String result) {
        delegate.processFinish(result);
    }


}
