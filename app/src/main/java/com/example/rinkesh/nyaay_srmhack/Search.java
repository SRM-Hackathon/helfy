package com.example.rinkesh.nyaay_srmhack;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.widget.Toast.LENGTH_SHORT;

public class Search extends AppCompatActivity {

    ArrayList<String> title = new ArrayList<String>();
    ArrayList<String> link = new ArrayList<String>();
    String input,type,pgnum="4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Intent intent = getIntent();
        input = intent.getExtras().getString("input");
        type = intent.getExtras().getString("type");

        if (type=="B"){

            input = input.replace("Search","");

        }

        request();
    }

    public void request() {

        RequestQueue queue = Volley.newRequestQueue(this);
        final String url = "https://api.indiankanoon.org/search/?formInput="+input+"&pagenum="+pgnum+"&maxpage=4";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("docs");

                    for (int i=0; i <jsonArray.length(); i++){

                        JSONObject post = jsonArray.getJSONObject(i);

                        title.add(post.getString("title"));
                        link.add(post.getString("url"));


                    }

                    List<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();

                    for (int i=0; i<jsonObject.length(); i++){

                        HashMap<String,String> hm = new HashMap<String,String>();
                        hm.put("title",title.get(i));
                        list.add(hm);

                    }

                    String[] from ={"title"};
                    int[] to = {R.id.title};

                    SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext(),list,R.layout.search_element_layout,from,to);
                    ListView listView = (ListView) findViewById(R.id.listview);
                    listView.setAdapter(simpleAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("error","error"+error.toString());
                    }
                }

        ) {

            public Map<String,String> getHeaders() throws AuthFailureError {

                Map<String,String> params = new HashMap<String, String>();
                params.put("Authorization","Token 3ee55dbc751245319bd3d4b58c7509fb5d99613d");

                return params;

            }

        };

        queue.add(postRequest);

    }


    public void test () {

        Toast.makeText(this, title.get(0),Toast.LENGTH_SHORT).show();

    }

}
