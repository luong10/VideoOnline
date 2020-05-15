package com.example.mywatch.danhMuc;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.example.mywatch.adapter.VideoYouTubeAdapterTren;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTrending extends Fragment {
    View v;
    APIKEY apikey = new APIKEY();
    RecyclerView lvTrending;
    ArrayList<VideoYouTube> arrayListTren;
    VideoYouTubeAdapterTren adapterTren;

    public FragmentTrending() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_trending, container,false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lvTrending = (RecyclerView) v.findViewById(R.id.lvTrending);
        arrayListTren = new ArrayList<>();
        adapterTren = new VideoYouTubeAdapterTren(getContext(),arrayListTren);
        String url1 = "https://www.googleapis.com/youtube/v3/" +
                "videos?part=snippet&chart=mostPopular&regionCode=VN&key="+ apikey.API_KEY +"&maxResults=50";
        getJson(url1);
        lvTrending.setLayoutManager(new GridLayoutManager(getActivity(),1));
        lvTrending.setAdapter(adapterTren);

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
                        arrayListTren.add(new VideoYouTube(title,url,jsonId, date, channelTitl));
                    }
                    adapterTren.notifyDataSetChanged();
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
