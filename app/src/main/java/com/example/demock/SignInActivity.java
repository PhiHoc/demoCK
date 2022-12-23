package com.example.demock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.firebase.database.FirebaseDatabase;

public class SignInActivity extends AppCompatActivity {

    private Button btnDangKy,btnDangNhap;
    private EditText edPhone, edPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dangnhap);
        initUI();
        inintListener();
    }

    private  void initUI(){
        btnDangKy = findViewById(R.id.btn_dk);
        btnDangNhap = findViewById(R.id.btn_dn);
        edPhone = findViewById(R.id.ed_sdt);
        edPassword = findViewById(R.id.ed_pw);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
    }

    private void  inintListener(){
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


}