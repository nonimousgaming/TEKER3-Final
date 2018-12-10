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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    EditText editTextPassword, editTextCPassword, editTextFirstName, editTextLastName, editTextEmail, editTextUsername;
    Button btnRegisterSignup;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initViews();
        userRegister();
    }

    private void initViews() {
        editTextCPassword = findViewById(R.id.editTextCPassword);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextUsername = findViewById(R.id.editTextUsername);
        btnRegisterSignup = findViewById(R.id.btnRegisterSignup);
    }

    private void userRegister() {
        url = "https://te-ker.000webhostapp.com/api/v1/register";

        btnRegisterSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String fname = editTextFirstName.getText().toString();
                final String lname = editTextLastName.getText().toString();
                final String password = editTextPassword.getText().toString();
                final String email = editTextEmail.getText().toString();
                final String username = editTextUsername.getText().toString();
                final String cpassword = editTextCPassword.getText().toString();
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            if(cpassword.equals(password)) {
                                JSONArray jsonArray = new JSONArray(response);
                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                String message = jsonObject.getString("message");
                                String code = jsonObject.getString("code");
                                Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
                                if(code.equals("200")){
                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else if(code.equals("400")) {
                                    editTextCPassword.setText("");
                                } else if(code.equals("500")) {
                                    editTextCPassword.setText("");
                                    editTextEmail.setText("");
                                    editTextFirstName.setText("");
                                    editTextLastName.setText("");
                                    editTextPassword.setText("");
                                }
                            } else {
                                Toast.makeText(RegisterActivity.this, "Password don't match!", Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String, String>  params = new HashMap<String, String>();
                        params.put("name", fname + " " + lname);
                        params.put("username", username);
                        params.put("email", email);
                        params.put("password", password);

                        return params;
                    }
                };
                MySingleton.getInstance(RegisterActivity.this).addToRequestQueue(stringRequest);
            }
        });
    }
}
