package com.example.lenovo.te_ker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class login extends AppCompatActivity {
        private EditText editTextUsername;
        private EditText editTextPassword;
        private Button buttonconfirm;
        private Button buttonregister;
        private TextView toogleView;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);
            editTextUsername = findViewById(R.id.editUser);
            editTextPassword = findViewById(R.id.editPass);
            buttonconfirm = findViewById(R.id.login);
            buttonregister = findViewById(R.id.signup);
            toogleView = findViewById(R.id.toogleview);
            final TextView registerlink = (TextView) findViewById(R.id.forgot);

            registerlink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent registerIntent = new Intent(login.this,forgotpass.class);
                    login.this.startActivity(registerIntent);
                }
            });

            toogleView.setVisibility(View.GONE);
            editTextPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

            editTextPassword.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (editTextPassword.getText().length() > 0) {
                        toogleView.setVisibility(View.VISIBLE);
                    } else {
                        toogleView.setVisibility(View.GONE);

                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            toogleView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (toogleView.getText() == "SHOW") {
                        toogleView.setText("HIDE");
                        editTextPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        editTextPassword.setSelection(editTextPassword.length());
                    } else {
                        toogleView.setText("SHOW");
                        editTextPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    }
                }

            });

            buttonregister.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent i = new Intent(login.this, register.class);
                    startActivity(i);

                }
            });


            editTextUsername.addTextChangedListener(loginTextwatcher);
            editTextPassword.addTextChangedListener(loginTextwatcher);

            buttonconfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(editTextUsername.getText().equals("") && editTextPassword.getText().equals("")) {
                        Toast.makeText(login.this, "Please fill up the required fields", Toast.LENGTH_LONG).show();
                    } else {
                        Intent intent = new Intent(login.this, HomeActivity.class);
                        startActivity(intent);
                    }
                }
            });
        }



        private TextWatcher loginTextwatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String usernameInput = editTextUsername.getText().toString().trim();
                String passwordInput = editTextPassword.getText().toString().trim();

                buttonconfirm.setEnabled(!usernameInput.isEmpty() && !passwordInput.isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

    }


