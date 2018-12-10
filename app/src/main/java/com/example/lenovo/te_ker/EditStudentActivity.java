package com.example.lenovo.te_ker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
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

public class EditStudentActivity extends AppCompatActivity {

    EditText editTextEditStudentName, editTextEditStudentEmail, editTextEditParentName, editTextEditParentNumber;
    Button btnEditStudent;
    RadioButton radioButtonActive, radioButtonInactive;
    String student_id, name, email, parent_name, parent_number, status, url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student);
        initExtras();
        initViews();
        initEvents();
    }

    private void initEvents() {
        editTextEditStudentName.setText(name);
        editTextEditStudentEmail.setText(email);
        editTextEditParentName.setText(parent_name);
        editTextEditParentNumber.setText(parent_number);
        if(status == "0") {
            radioButtonInactive.setChecked(true);
        } else {
            radioButtonActive.setChecked(true);
        }
        url = "https://te-ker.000webhostapp.com/api/v1/edit-student";
        btnEditStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name1 = editTextEditStudentName.getText().toString();
                final String email1 = editTextEditStudentEmail.getText().toString();
                final String parent_name1 = editTextEditParentName.getText().toString();
                final String parent_number1 = editTextEditParentNumber.getText().toString();
                final String status1;
                if(radioButtonActive.isChecked()) {
                    status1 = "1";
                } else {
                    status1 = "0";
                }
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            String message = jsonObject.getString("message");
                            String code = jsonObject.getString("code");
                            Toast.makeText(EditStudentActivity.this, message, Toast.LENGTH_LONG).show();
                            if(code.equals("200")) {
                                Intent intent = new Intent(EditStudentActivity.this, StudentsActivity.class);
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
                        params.put("student_id", student_id);
                        params.put("name", name1);
                        params.put("email", email1);
                        params.put("parent_name", parent_name1);
                        params.put("parent_number", parent_number1);
                        params.put("status", status1);
                        return params;
                    }
                };
                MySingleton.getInstance(EditStudentActivity.this).addToRequestQueue(stringRequest);
            }
        });
    }

    private void initViews() {
        editTextEditStudentName = findViewById(R.id.editTextEditStudentName);
        editTextEditStudentEmail = findViewById(R.id.editTextEditStudentEmail);
        editTextEditParentName = findViewById(R.id.editTextEditParentName);
        editTextEditParentNumber = findViewById(R.id.editTextEditParentNumber);
        btnEditStudent = findViewById(R.id.btnEditStudent);
        radioButtonActive = findViewById(R.id.radioButtonActive);
        radioButtonInactive = findViewById(R.id.radioButtonInactive);
    }

    private void initExtras() {
        Intent intent = getIntent();
        student_id = intent.getStringExtra("student_id");
        name = intent.getStringExtra("name");
        email = intent.getStringExtra("email");
        parent_name = intent.getStringExtra("parent_name");
        parent_number = intent.getStringExtra("parent_number");
        status = intent.getStringExtra("status");
    }
}
