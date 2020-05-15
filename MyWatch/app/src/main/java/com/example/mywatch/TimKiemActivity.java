package com.example.mywatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mywatch.adapter.VideoYouTubeAdapterTren;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class TimKiemActivity extends AppCompatActivity {
       RecyclerView lvTimKiem;
       String chuoitk = "",t = "";
        APIKEY apikey = new APIKEY();
        ArrayList<VideoYouTube> arrayListTimKiem = new ArrayList<>();
        VideoYouTubeAdapterTren adapterTimKiem;
        TextView q;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tim_kiem);
        q = (TextView) findViewById(R.id.hienthi);

        Intent intent = getIntent();
        t = intent.getStringExtra("corona");
        q.setText(t);
        chuoitk = covertToString(t);
        while (chuoitk.indexOf(" ") != -1) chuoitk = chuoitk.replaceAll(" ","%20");

        lvTimKiem = (RecyclerView) findViewById(R.id.lvTimkiem);
        adapterTimKiem = new VideoYouTubeAdapterTren(this,arrayListTimKiem);
        String urlS = "https://www.googleapis.com/youtube/v3/search?part=snippet&q="+ chuoitk +"&type=video&key=" + apikey.API_KEY + "&regionCode=VN&maxResults=50";
        getJson(urlS);
        lvTimKiem.setLayoutManager(new LinearLayoutManager(this));
        lvTimKiem.setAdapter(adapterTimKiem);


    }
    public static String covertToString(String value) {
        try {
            String temp = Normalizer.normalize(value, Normalizer.Form.NFD);
            Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
            return pattern.matcher(temp).replaceAll("");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


    public void getJson(String url){

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonItems = response.getJSONArray("items");
                    String title ="";
                    String url ="";
                    String resourceId ="";
                    String date ="";
                    String channelTitl = "";
                    for (int i=0;i<jsonItems.length();i++){
                        JSONObject jsonItem = jsonItems.getJSONObject(i);
                        JSONObject jsonSnippet = jsonItem.getJSONObject("snippet");
                        JSONObject jsonObId = jsonItem.getJSONObject("id");

                        title = jsonSnippet.getString("title");
                        date = jsonSnippet.getString("publishedAt");
                        channelTitl = jsonSnippet.getString("channelTitle");

                        JSONObject jsonthumbnails = jsonSnippet.getJSONObject("thumbnails");
                        JSONObject jsonMedium = jsonthumbnails.getJSONObject("medium");
                        url = jsonMedium.getString("url");

                        resourceId = jsonObId.getString("videoId");
                        arrayListTimKiem.add(new VideoYouTube(title,url,resourceId,date,channelTitl));
                    }
                    adapterTimKiem.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(TimKiemActivity.this, "Loi~~", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

}
