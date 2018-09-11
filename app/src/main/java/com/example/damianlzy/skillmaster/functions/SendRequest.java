package com.example.damianlzy.skillmaster.functions;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SendRequest extends AsyncTask<String, Void, String>{
    @Override
    protected String doInBackground(String... strings) {
        try {
            return post(strings[0],strings[1],strings[2]);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();

    String post(String url, String json, String token) throws IOException {
        Log.e("SENDING POST REQUEST",url);
        Log.e("POST DETAILS",json);
        Log.e("HEADER","Bearer "+token);
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization"," Bearer "+token)
                .post(body)
                .build();
    Response response = client.newCall(request).execute();
        return response.body().string();
}

}
