package com.example.lenovo.te_ker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.lenovo.te_ker.data.AppPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NewPasswordActivity extends AppCompatActivity {
    EditText editTextNewPassword, editTextCode;
    Button btnNewPassword;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);

        initViews();
        initEvents();
    }

    private void initViews() {
        editTextNewPassword = findViewById(R.id.editTextNewPassword);
        editTextCode = findViewById(R.id.editTextCode);
        btnNewPassword = findViewById(R.id.btnNewPassword);
    }

    private void initEvents() {
        url = "https://te-ker.000webhostapp.com/api/v1/reset-password";
        btnNewPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String password = editTextNewPassword.getText().toString();
                final String code = editTextCode.getText().toString();
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            String message = jsonObject.getString("message");
                            String code = jsonObject.getString("code");
                            Toast.makeText(NewPasswordActivity.this, message, Toast.LENGTH_LONG).show();
                            if(code.equals("200")) {
                                Intent intent = new Intent(NewPasswordActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    protected Map<String, String> getParams()
                    {
                        Map<String, String>  params = new HashMap<String, String>();
                        params.put("password", password);
                        params.put("code", code);
                        return params;
                    }
                };
                MySingleton.getInstance(NewPasswordActivity.this).addToRequestQueue(stringRequest);
            }
        });
    }
}
