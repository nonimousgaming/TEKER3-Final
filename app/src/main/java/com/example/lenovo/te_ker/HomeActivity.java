package com.example.lenovo.te_ker;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.lenovo.te_ker.data.AppPreference;
import com.example.lenovo.te_ker.data.HomeAsyncTask;
import com.example.lenovo.te_ker.data.Section;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    String user_id, url;
    FloatingActionButton fab;
    LinearLayout linearLayout;
    TextView textViewNoSections;

    private RecyclerView mList;

    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private List<Section> movieList;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        linearLayout = findViewById(R.id.main_layout);
        mList = findViewById(R.id.main_list);
        mList.setVisibility(View.VISIBLE);
        movieList = new ArrayList<>();
        adapter = new SectionAdapter(getApplicationContext(),movieList);
        mList.setAdapter(adapter);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(mList.getContext(), linearLayoutManager.getOrientation());

        mList.setHasFixedSize(true);
        mList.setLayoutManager(linearLayoutManager);
        mList.addItemDecoration(dividerItemDecoration);

        initPreferences();
        getData();
        changeNavHeader();
        initViews();
        initEvents();
    }

    private void changeNavHeader() {
        String name = AppPreference.getName(this);
        String email = AppPreference.getEmail(this);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView navName = headerView.findViewById(R.id.nav_name);
        TextView navEmail = headerView.findViewById(R.id.nav_email);
        navName.setText(name);
        navEmail.setText(email);
    }

    private void getData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        url = "https://te-ker.000webhostapp.com/api/v1/get-sections";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    if(response.contentEquals("[]")) {
                        textViewNoSections.setVisibility(View.VISIBLE);
                        Toast.makeText(HomeActivity.this, "No sections uploaded yet", Toast.LENGTH_LONG).show();
                    } else {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Section section = new Section();
                            section.setID(jsonObject.getString("id"));
                            section.setName(jsonObject.getString("name"));
                            section.setSubject(jsonObject.getString("subject"));
                            section.setStart_time(jsonObject.getString("start_time"));
                            section.setEnd_time(jsonObject.getString("end_time"));
                            section.setRoom(jsonObject.getString("room"));
                            section.setStatus(jsonObject.getString("status"));
                            movieList.add(section);
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
                Toast.makeText(HomeActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }){
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("user_id", user_id);
                return params;
            }
        };
        MySingleton.getInstance(HomeActivity.this).addToRequestQueue(stringRequest);
    }

    private void initViews() {
        textViewNoSections = findViewById(R.id.textViewNoSections);
        fab = findViewById(R.id.fab);
    }

    private void initEvents() {
        initNavigationDrawer();
        fabOnClick();
    }

    private void fabOnClick() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, AddSectionActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initNavigationDrawer() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mDrawerLayout.addDrawerListener(
                new DrawerLayout.DrawerListener() {
                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {
                        // Respond when the drawer's position changes
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                        // Respond when the drawer is opened
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        // Respond when the drawer is closed
                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {
                        // Respond when the drawer motion state changes
                    }
                }
        );
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        Fragment fragment = null;
                        int id = menuItem.getItemId();
                        menuItem.setChecked(true);
                        if(id == R.id.nav_logout) {
                            AppPreference.setLogin(HomeActivity.this, false);
                            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }else if(id == R.id.nav_about_developers) {
                            fragment = new DevelopersFragment();
                            mList.setVisibility(View.GONE);
                            linearLayout.setBackgroundResource(0);
                            fab.hide();
                        } else if (id == R.id.nav_class_management) {
                            startActivity(getIntent());
                            finish();
                        } else{
                            fab.show();
                            linearLayout.setBackgroundResource(0);
                        }
                        if(fragment != null) {
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.content_frame, fragment);
                            fragmentTransaction.commit();
                        }
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initPreferences() {
        boolean checker = AppPreference.getLogin(this);
        if (checker == false) {
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            user_id = AppPreference.getUserId(this);
        }
    }

    @Override
    protected void onResume() {
        initPreferences();
        super.onResume();
    }

    @Override
    protected void onRestart() {
        initPreferences();
        super.onRestart();
    }
}
