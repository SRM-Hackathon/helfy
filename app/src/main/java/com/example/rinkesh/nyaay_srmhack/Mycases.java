package com.example.rinkesh.nyaay_srmhack;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
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

public class Mycases extends AppCompatActivity {

    ListView listView;

    ArrayList<String> id = new ArrayList<String>();
    ArrayList<String> date = new ArrayList<String>();
    ArrayList<String> judge = new ArrayList<String>();
    ArrayList<String> oppo = new ArrayList<String>();
    ArrayList<String> priority = new ArrayList<String>();
    ArrayList<String> court = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycases);

        listView= (ListView) findViewById(R.id.mycaselistview);

        request();

    }

    public void request() {

        RequestQueue queue = Volley.newRequestQueue(this);
        final String url = "http://www.sastimasti.me/nyaay/getdata.php";
        StringRequest getRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    for (int i=0; i<jsonArray.length(); i++){

                        JSONObject post = jsonArray.getJSONObject(i);

                        id.add(post.getString("id"));
                        priority.add(post.getString("priority"));
                        judge.add(post.getString("judge"));
                        date.add(post.getString("date"));
                        court.add(post.getString("court"));
                        oppo.add(post.getString("opposition"));

                    }

                    List<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();

                    for (int i=0; i<jsonArray.length(); i++){

                        HashMap<String,String> hm = new HashMap<String,String>();
                        hm.put("id","Application Id Number: "+id.get(i));
                        hm.put("priority","Priority: "+priority.get(i));
                        hm.put("judge","Judge: "+judge.get(i));
                        hm.put("date","Date: "+date.get(i));
                        hm.put("court","Court: "+court.get(i));
                        hm.put("opposition","Opposition: "+oppo.get(i));
                        list.add(hm);

                    }

                    String[] from ={"id","priority","judge","date","court","opposition"};
                    int[] to = {R.id.id,R.id.priority,R.id.judge,R.id.date,R.id.court,R.id.oppo};

                    SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext(),list,R.layout.mycases_element_layout,from,to);
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

        );

        queue.add(getRequest);

    }

}
