package com.example.lenovo.te_ker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class register extends AppCompatActivity {

        private EditText editTextfirstname;
        private EditText editTextlastname;
        private EditText editTextEmail;
        private EditText editTextPassword;
        private EditText editTextCPassword;
        private Button buttonconfirm;
        private TextView toogle;
        private TextView tview;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_register);
            editTextfirstname = findViewById(R.id.editTextfirstname);
            editTextlastname = findViewById(R.id.editTextlastname);
            editTextEmail = findViewById(R.id.editTextEmail);
            editTextPassword = findViewById(R.id.editTextPassword);
            editTextCPassword = findViewById(R.id.editTextCPassword);
            buttonconfirm= findViewById(R.id.signup);
            toogle = findViewById(R.id.Toogle);
            tview = findViewById(R.id.Tview);

            //text password

            toogle.setVisibility(View.GONE);
            editTextPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

            editTextPassword.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (editTextPassword.getText().length() > 0) {
                        toogle.setVisibility(View.VISIBLE);
                    } else {
                        toogle.setVisibility(View.GONE);
                    }

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });


            toogle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (toogle.getText() == "SHOW") {
                        toogle.setText("HIDE");
                        editTextPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        editTextPassword.setSelection(editTextPassword.length());
                    } else {
                        toogle.setText("SHOW");
                        editTextPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    }
                }
            });

            //textcpassword
            tview.setVisibility(View.GONE);
            editTextCPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

            editTextCPassword.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (editTextCPassword.getText().length() > 0) {
                        tview.setVisibility(View.VISIBLE);
                    } else {
                        tview.setVisibility(View.GONE);
                    }

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            tview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (tview.getText() == "SHOW") {
                        tview.setText("HIDE");
                        editTextCPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        editTextCPassword.setSelection(editTextPassword.length());
                    } else {
                        tview.setText("SHOW");
                        editTextCPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    }
                }
            });



            editTextfirstname.addTextChangedListener(registerTextWatcher);
            editTextlastname.addTextChangedListener(registerTextWatcher);
            editTextEmail.addTextChangedListener(registerTextWatcher);
            editTextPassword.addTextChangedListener(registerTextWatcher);
            editTextCPassword.addTextChangedListener(registerTextWatcher);
        }
        private TextWatcher registerTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String firstnameInput = editTextfirstname.getText().toString().trim();
                String lastnameInput = editTextlastname.getText().toString().trim();
                String emailInput = editTextEmail.getText().toString().trim();
                String PasswordInput = editTextPassword.getText().toString().trim();
                String CPasswordInput = editTextCPassword.getText().toString().trim();

                buttonconfirm.setEnabled(!firstnameInput.isEmpty()&& !lastnameInput.isEmpty());
                buttonconfirm.setEnabled(!emailInput.isEmpty()&& !PasswordInput.isEmpty());
                buttonconfirm.setEnabled(!CPasswordInput.isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
    }
