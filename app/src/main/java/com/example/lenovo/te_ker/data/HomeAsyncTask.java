package com.example.lenovo.te_ker.data;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.lenovo.te_ker.HomeActivity;
import com.example.lenovo.te_ker.LoginActivity;
import com.example.lenovo.te_ker.MySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by john paul on 10/7/2018.
 */

public class HomeAsyncTask extends AsyncTask {

    private Context context;
    String user_id;
    String url;

    public HomeAsyncTask(Context context, String user_id) {
        this.context = context;
        this.user_id = user_id;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        url = "https://te-ker.000webhostapp.com/api/v1/get-sections";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, response, Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("user_id", user_id);
                return params;
            }
        };
        MySingleton.getInstance(context).addToRequestQueue(stringRequest);
        return null;
    }
}
