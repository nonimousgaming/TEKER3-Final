package com.example.lenovo.te_ker;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.lenovo.te_ker.data.AppPreference;
import com.example.lenovo.te_ker.data.Student;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentsActivity extends AppCompatActivity {

    private RecyclerView mList;

    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private List<Student> movieList;
    private RecyclerView.Adapter adapter;
    private Button btnViewAttendance, btnCheck;
    String section_id, section_name;
    FloatingActionButton fab1;
    TextView textViewNoStudent;


    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);
        initViews();
        initEvents();
        section_id = AppPreference.getPrefSectionId(this);
        section_name = AppPreference.getPrefSectionName(this);
        textViewNoStudent = findViewById(R.id.textViewNoStudent);

        mList = findViewById(R.id.student_list);
        mList.setVisibility(View.VISIBLE);
        movieList = new ArrayList<>();
        adapter = new StudentAdapter(getApplicationContext(),movieList);
        mList.setAdapter(adapter);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(mList.getContext(), linearLayoutManager.getOrientation());

        mList.setHasFixedSize(true);
        mList.setLayoutManager(linearLayoutManager);
        mList.addItemDecoration(dividerItemDecoration);


        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        String url = "https://te-ker.000webhostapp.com/api/v1/get-students";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    if(response.contentEquals("[]")) {
                        textViewNoStudent.setVisibility(View.VISIBLE);
                        Toast.makeText(StudentsActivity.this, "No students uploaded yet", Toast.LENGTH_LONG).show();
                    } else {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Student student = new Student();
                            student.setID(jsonObject.getString("id"));
                            student.setPerson_id(jsonObject.getString("person_id"));
                            student.setName(jsonObject.getString("name"));
                            student.setEmail(jsonObject.getString("email"));
                            student.setParent_name(jsonObject.getString("parent_name"));
                            student.setParent_number(jsonObject.getString("parent_number"));
                            student.setStatus(jsonObject.getString("status"));
                            movieList.add(student);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(StudentsActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }){
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("section_id", section_id);
                params.put("section_name", section_name);
                return params;
            }
        };
        MySingleton.getInstance(StudentsActivity.this).addToRequestQueue(stringRequest);
    }

    private void initEvents() {
        btnViewAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentsActivity.this, AttendanceActivity.class);
                intent.putExtra("section_id", section_id);
                startActivity(intent);
            }
        });

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentsActivity.this, AddStudentActivity.class);
                intent.putExtra("section_id", section_id);
                startActivity(intent);
                finish();
            }
        });

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA},
                                MY_CAMERA_PERMISSION_CODE);
                    } else {
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, CAMERA_REQUEST);
                    }
                } else {
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    }
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CAMERA_REQUEST || requestCode ==  REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data != null) {
            Bundle extras = data.getExtras();
            final Bitmap imageBitmap = (Bitmap) extras.get("data");
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            String url = "https://te-ker.000webhostapp.com/api/v1/check-attendance";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        Toast.makeText(StudentsActivity.this, response, Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    progressDialog.hide();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(StudentsActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            }){
                protected Map<String, String> getParams()
                {
                    Map<String, String>  params = new HashMap<String, String>();
                    params.put("image", imageToString(imageBitmap));
                    params.put("section_id", section_id);
                    params.put("section_name", section_name);
                    return params;
                }
            };
            MySingleton.getInstance(StudentsActivity.this).addToRequestQueue(stringRequest);
        }
    }

    private String imageToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    private void initViews() {
        btnViewAttendance = findViewById(R.id.btnViewAttendance);
        fab1 = findViewById(R.id.fab1);
        btnCheck = findViewById(R.id.btnCheck);
    }

}
