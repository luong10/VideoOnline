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
public class FragmentLive extends Fragment {
    String ID_LIST = "PLU12uITxBEPGILPLxvkCc4L_iL7aHf4J2";
    View v;
    APIKEY apikey = new APIKEY();
    RecyclerView lvLive;
    ArrayList<VideoYouTube> arrayListLive = new ArrayList<>();
    VideoYouTubeAdapterTren adapterLive;
    public FragmentLive() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_live, container, false);
        lvLive = (RecyclerView) v.findViewById(R.id.lvLive);
        adapterLive = new VideoYouTubeAdapterTren(getContext(),arrayListLive);
        lvLive.setLayoutManager(new LinearLayoutManager(getActivity()));
        lvLive.setAdapter(adapterLive);

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
                        arrayListLive.add(new VideoYouTube(title,url,jsonId, date, channelTitl));
                    }
                    adapterLive.notifyDataSetChanged();
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
