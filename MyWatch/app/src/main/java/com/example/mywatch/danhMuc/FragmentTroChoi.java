package com.example.mywatch.danhMuc;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mywatch.APIKEY;
import com.example.mywatch.PlayVideoActivity;
import com.example.mywatch.R;
import com.example.mywatch.VideoYouTube;
import com.example.mywatch.adapter.VideoYouTubeAdapter;
import com.example.mywatch.adapter.VideoYouTubeAdapterTren;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTroChoi extends Fragment {
    View v;
    APIKEY apikey = new APIKEY();
    RecyclerView lvTroChoi;
    ArrayList<VideoYouTube> arrayListTroChoi = new ArrayList<>();
    VideoYouTubeAdapterTren adapterTroChoi;

    public FragmentTroChoi() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_tro_choi, container, false);
        lvTroChoi = (RecyclerView) v.findViewById(R.id.lvTroChoi);
        adapterTroChoi = new VideoYouTubeAdapterTren(getContext(),arrayListTroChoi);
        lvTroChoi.setLayoutManager(new LinearLayoutManager(getActivity()));
        lvTroChoi.setAdapter(adapterTroChoi);

        return v;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String urlTroChoi = "https://www.googleapis.com/youtube/v3/videos?part=snippet&chart=" +
                "mostPopular&maxResults=50&regionCode=VN&videoCategoryId=20&key="+ apikey.API_KEY;
        getJson(urlTroChoi);
    }
    public void getJson(String url){

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonItems = response.getJSONArray("items");
                    String title ="";
                    String url ="";
                    String jsonId ="";
                    String date ="";
                    String channelTitl = "";
                    for (int i=0;i<jsonItems.length();i++){
                        JSONObject jsonItem = jsonItems.getJSONObject(i);
                        JSONObject jsonSnippet = jsonItem.getJSONObject("snippet");
                        title = jsonSnippet.getString("title");
                        date = jsonSnippet.getString("publishedAt");

                        channelTitl = jsonSnippet.getString("channelTitle");
                        JSONObject jsonthumbnails = jsonSnippet.getJSONObject("thumbnails");
                        JSONObject jsonMedium = jsonthumbnails.getJSONObject("medium");
                        url = jsonMedium.getString("url");
                        jsonId = jsonItem.getString("id");
                        arrayListTroChoi.add(new VideoYouTube(title,url,jsonId, date, channelTitl));
                    }
                    adapterTroChoi.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Loi~~", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

}
