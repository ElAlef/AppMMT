package com.example.mmt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class RegistroExitoso extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_exitoso);
    }

    public void launchLoginActivity(View view) {
        Intent intent = new Intent(RegistroExitoso.this, LoginActivity.class);
        startActivity(intent);
    }
}