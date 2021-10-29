package com.example.userlisttest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AccountAddActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY =
            "com.example.android.userlisttest.REPLY";

    private EditText editTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_add);

        editTextView = findViewById(R.id.edit_account_name);

        final Button saveButton = findViewById(R.id.button_save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent replyIntent = new Intent();
                if(TextUtils.isEmpty(editTextView.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                }
                else {
                    String word = editTextView.getText().toString();
                    replyIntent.putExtra(EXTRA_REPLY, word);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });

    }
}