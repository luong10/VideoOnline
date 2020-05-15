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
public class FragmentTinTuc extends Fragment {
    String ID_LIST = "PL3ZQ5CpNulQkArNxSQBUU8ZLhh40onFFJ";
    View v;
    APIKEY apikey = new APIKEY();
    RecyclerView lvTinTuc;
    ArrayList<VideoYouTube> arrayListTinTuc = new ArrayList<>();
    VideoYouTubeAdapterTren adapterTinTuc;

    public FragmentTinTuc() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_tin_tuc, container, false);
        lvTinTuc = (RecyclerView) v.findViewById(R.id.lvTinTuc);
        adapterTinTuc = new VideoYouTubeAdapterTren(getContext(),arrayListTinTuc);
        lvTinTuc.setLayoutManager(new LinearLayoutManager(getActivity()));
        lvTinTuc.setAdapter(adapterTinTuc);
        return v;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String urlTinTuc = "https://www.googleapis.com/youtube/v3/playlistItems?part=" +
                "snippet&maxResults=50&playlistId="+ ID_LIST +"&key="+ apikey.API_KEY;
        getJson(urlTinTuc);
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
                        JSONObject jsonresourceId = jsonSnippet.getJSONObject("resourceId");
                        title = jsonSnippet.getString("title");
                        date = jsonSnippet.getString("publishedAt");
                        jsonId = jsonresourceId.getString("videoId");
                        channelTitl = jsonSnippet.getString("channelTitle");
                        JSONObject jsonthumbnails = jsonSnippet.getJSONObject("thumbnails");
                        JSONObject jsonMedium = jsonthumbnails.getJSONObject("medium");
                        url = jsonMedium.getString("url");
                        arrayListTinTuc.add(new VideoYouTube(title,url,jsonId, date, channelTitl));
                    }
                    adapterTinTuc.notifyDataSetChanged();
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
