package com.uukeshov.findu;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

class RestClient {

    RestClient() {
    }

    static final String LOG_TAG = "RestClientLog";
    BackgroundTask t;

    public void StartReq() {
        t = new BackgroundTask();
        t.execute();

    }

    class BackgroundTask extends AsyncTask<Void, Void, String> {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String resultJson = "";

        @Override
        protected String doInBackground(Void... params) {

            // получаем данные с внешнего ресурса
            try {
                URL url = new URL("http://www.room.megaline.kg/test/index.php?expression=SELECT `id`, `NAME`, `MobileNumber`, `Status`, `create_date`, `userkey` FROM `USERS` WHERE `id`=1");

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                resultJson = buffer.toString();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return resultJson;
        }

        @Override
        protected void onPostExecute(String strJson) {
            super.onPostExecute(strJson);

            JSONObject dataJsonObj = null;

            try {
                dataJsonObj = new JSONObject(strJson);
                JSONArray get = dataJsonObj.getJSONArray("GET");

                // 2. перебираем и выводим контакты каждого друга
                for (int i = 0; i < get.length(); i++) {
                    JSONObject secondFriend = get.getJSONObject(0);
                    int id = secondFriend.getInt("id");
                    String name = secondFriend.getString("NAME");
                    String mobileNumber = secondFriend.getString("MobileNumber");
                    int status = secondFriend.getInt("Status");
                    String createDate = secondFriend.getString("create_date");
                    String userkey = secondFriend.getString("userkey");

                    Log.d(LOG_TAG, "phone: " + id);
                    Log.d(LOG_TAG, "email: " + name);
                    Log.d(LOG_TAG, "skype: " + mobileNumber);
                    Log.d(LOG_TAG, "skype: " + status);
                    Log.d(LOG_TAG, "skype: " + createDate);
                    Log.d(LOG_TAG, "skype: " + userkey);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}