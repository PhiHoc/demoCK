package com.example.demock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    private EditText edtPhone,edtPassword,edtUserName,edtPWConfirm;
    private Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dangky);
        initUI();
    }

    private void initUI(){
        edtPhone = findViewById(R.id.ed_email);
        edtPassword = findViewById(R.id.ed_pw);
        edtUserName = findViewById(R.id.ed_name);
        edtPWConfirm = findViewById(R.id.ed_xnpw);
        btnSignUp = findViewById(R.id.btnDangky);
    }

    private void  inintListener(){
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSignUp();
            }
        });
    }

    private void onClickSignUp() {
        String email = edtPhone.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        if(email.equals("")){
            Toast.makeText(this,"Vui lòng nhập email!",Toast.LENGTH_SHORT).show();
        }
        else if(password.length()<8){
            Toast.makeText(this,"Mật khẩu tối thiểu 8 kí tự!",Toast.LENGTH_SHORT).show();
        }
        else
        {
            FirebaseAuth auth = FirebaseAuth.getInstance();

        }
    }
}