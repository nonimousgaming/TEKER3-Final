package com.example.lenovo.te_ker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class forgotpass extends AppCompatActivity {
    private EditText editemail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpass);

        editemail = findViewById(R.id.editemail);

        editemail.addTextChangedListener(forgotpassTextWatcher);

    }

    private TextWatcher forgotpassTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String firstnameInput = editemail.getText().toString().trim();

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}



