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

public class AddStudentActivity extends AppCompatActivity {

    EditText editTextStudentName, editTextStudentEmail, editTextParentName, editTextParentNumber;
    Button btnAddStudent, btnImport;
    String url, section_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        initViews();
        initEvents();
        initExtras();
    }

    private void initExtras() {
        Intent intent = getIntent();
        section_id = intent.getStringExtra("section_id");
    }

    private void initEvents() {
        url = "https://te-ker.000webhostapp.com/api/v1/create-student";
        btnAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = editTextStudentName.getText().toString();
                final String email = editTextStudentEmail.getText().toString();
                final String parent_name = editTextParentName.getText().toString();
                final String parent_number = editTextParentNumber.getText().toString();
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            String message = jsonObject.getString("message");
                            String code = jsonObject.getString("code");
                            Toast.makeText(AddStudentActivity.this, message, Toast.LENGTH_LONG).show();
                            if(code.equals("200")) {
                                Intent intent = new Intent(AddStudentActivity.this, StudentsActivity.class);
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
                        params.put("section_id", section_id);
                        params.put("name", name);
                        params.put("email", email);
                        params.put("parent_name", parent_name);
                        params.put("parent_number", parent_number);
                        return params;
                    }
                };
                MySingleton.getInstance(AddStudentActivity.this).addToRequestQueue(stringRequest);
            }
        });

        btnImport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void initViews() {
        editTextStudentName = findViewById(R.id.editTextStudentName);
        editTextStudentEmail = findViewById(R.id.editTextStudentEmail);
        editTextParentName = findViewById(R.id.editTextParentName);
        editTextParentNumber = findViewById(R.id.editTextParentNumber);
        btnAddStudent = findViewById(R.id.btnAddStudent);
        btnImport = findViewById(R.id.btnImport);
    }
}
