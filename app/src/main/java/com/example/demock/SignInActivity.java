package com.example.demock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

//import com.example.demock.Admin.AdminActivity;
import com.example.demock.Common.Common;
import com.example.demock.Model.User;
import com.example.demock.fragment.TrangChuFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignInActivity extends AppCompatActivity {

    private Button btnDangKy,btnDangNhap;
    private EditText edPhone, edPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dangnhap);
        //Khởi tạo các giá trị
        btnDangKy = findViewById(R.id.btn_dk);
        btnDangNhap = findViewById(R.id.btn_dn);
        edPhone = findViewById(R.id.ed_sdt);
        edPassword = findViewById(R.id.ed_pw);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        //Xử lí sự kiện ấn nút đăng ký
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        //Xử lí sự kiện ấn nút đăng nhập
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String phone = edPhone.getText().toString().trim();
                String password = edPassword.getText().toString().trim();

                if(phone.equals("")){
                    Toast.makeText(SignInActivity.this, "Vui lòng nhập số điện thoại!", Toast.LENGTH_SHORT).show();

                }
                else if(password.equals("")){
                    Toast.makeText(SignInActivity.this, "Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();
                }
                else{
                    ProgressDialog mDialog = new ProgressDialog(SignInActivity.this);
                    mDialog.setMessage("Vui lòng chờ..");
                    mDialog.show();
                    table_user.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //Check if user exist
                            if (snapshot.child(phone).exists()) {
                                //Get user info
                                mDialog.dismiss();
                                User user = snapshot.child(phone).getValue(User.class);

                                if (user.getPassword().equals(password)) {
                                    Toast.makeText(SignInActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                                    Common.currentUser = user;
                                    Common.currentUserPhone = phone;
//                                    if(user.getIsAdmin().equals("true")){
//                                        Intent admin = new Intent(SignInActivity.this, AdminActivity.class);
//                                        startActivity(admin);
//                                    }
//                                    else{
                                        Intent home = new Intent(SignInActivity.this, MainActivity.class);
                                        startActivity(home);
//                                    }
                                    finish();
                                } else {
                                    Toast.makeText(SignInActivity.this, "Sai mật khẩu! Hãy thử lại", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                mDialog.dismiss();
                                Toast.makeText(SignInActivity.this, "Tài khoản không tồn tại", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(SignInActivity.this, "Đã có lỗi xảy ra hãy thử lại sau", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });


    }
}