package com.example.demock;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.demock.Common.Common;
import com.example.demock.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUpActivity extends AppCompatActivity {

    private EditText edtPhone,edtPassword,edtUserName,edtPWConfirm,edtAddress,edtBDay;
    private Button btnSignUp, btnBack;
    private RadioGroup radioGroup;
    private RadioButton selectedRadioButton;

    //Fire base
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference table_user = database.getReference("User");


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dangky);

        //Khởi tạo các giá trị
        edtPassword = findViewById(R.id.ed_pw);
        edtUserName = findViewById(R.id.ed_name);
        edtPWConfirm = findViewById(R.id.ed_xnpw);
        btnSignUp = findViewById(R.id.btnDangky);
        edtAddress = findViewById(R.id.ed_address);
        edtPhone = findViewById(R.id.ed_sdt);
        radioGroup = findViewById(R.id.genderRadioGroup);
        btnBack = findViewById(R.id.back);
        edtBDay = findViewById(R.id.ed_bday);

        //Xử lí sự kiện ấn nút quay lại
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //Xử lí sự kiện ấn nút đăng kí
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSignUp();
            }
        });

    }
    private void onClickSignUp() {
        String name = edtUserName.getText().toString().trim();
        String phone = edtPhone.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String addr = edtAddress.getText().toString().trim();
        String confirmPassword = edtPWConfirm.getText().toString().trim();
        String bday = edtBDay.getText().toString().trim();

        if(name.equals("") || phone.equals("") || addr.equals("") || bday.equals("")){
            //
            Toast.makeText(this,"Vui lòng nhập đầy đủ thông tin!",Toast.LENGTH_SHORT).show();
        }
        else if(radioGroup.getCheckedRadioButtonId() == -1){
            Toast.makeText(this,"Vui lòng chọn giới tính!",Toast.LENGTH_SHORT).show();
        }
        else if(password.length()<8){
            Toast.makeText(this,"Mật khẩu tối thiểu 8 kí tự!",Toast.LENGTH_SHORT).show();
        }
        else if(!password.equals(confirmPassword)){
            Toast.makeText(this,"Xác nhận mật khẩu không đúng!",Toast.LENGTH_SHORT).show();
        }
        else
        {
            //Nhập đầy đủ thông tin
            final ProgressDialog mDialog = new ProgressDialog(SignUpActivity.this);
            mDialog.setMessage("Vui lòng chờ..");
            mDialog.show();

            table_user.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    //If user exist
                    if(snapshot.child(phone).exists()){
                        mDialog.dismiss();
                        Toast.makeText(SignUpActivity.this,"Số điện thoại đã tồn tại!",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        mDialog.dismiss();
                        selectedRadioButton  = (RadioButton)findViewById(radioGroup.getCheckedRadioButtonId());
                        //get gender text
                        String gender = selectedRadioButton.getText().toString();
                        User user = new User(name,password,gender,addr,"false",bday);
                        table_user.child(phone).setValue(user, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                Toast.makeText(SignUpActivity.this,"Đăng kí thành công!",Toast.LENGTH_SHORT).show();
                                Common.currentUser = user;
                                Common.currentUserPhone = phone;
                                Intent home = new Intent(SignUpActivity.this,MainActivity.class);
                                startActivity(home);
                                finish();
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    }
}