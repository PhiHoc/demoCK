package com.example.demock.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.demock.Common.Common;
import com.example.demock.R;
import com.example.demock.SignUpActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DoiMatKhauFragment extends Fragment {

    private Button btnLuuMK,btnHuy;
    private EditText etMatKhauHienTai,etMatKhauMoi,etMatKhauXacNhan;
    private DatabaseReference mDatabase;
    private Fragment fragment;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doimatkhau, container, false);
        //Khởi tạo các giá trị
        btnHuy = view.findViewById(R.id.btnHuyMK);
        btnLuuMK = view.findViewById(R.id.btnLuuMK);
        etMatKhauHienTai = view.findViewById(R.id.etMatKhauHienTai);
        etMatKhauMoi = view.findViewById(R.id.etMatKhauMoi);
        etMatKhauXacNhan = view.findViewById(R.id.etMatKhauXacNhan);

        //Xử lí sự kiện nhấn nút hủy
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new HoSoFragment();
                replaceFragment(fragment);
            }
        });

        //Xử lí sự kiện nhấn nút lưu
        btnLuuMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnClickSave();
            }
        });

        return view;
    }

    private void OnClickSave() {
        String mkhientai = etMatKhauHienTai.getText().toString().trim();
        String mkmoi = etMatKhauMoi.getText().toString().trim();
        String xacnhanmk = etMatKhauXacNhan.getText().toString().trim();
        if(mkhientai.equals("")){
            Toast.makeText(getActivity(),"Vui lòng nhập mật khẩu!",Toast.LENGTH_SHORT).show();
        }
        else if(mkmoi.length()<8){
            Toast.makeText(getActivity(),"Mật khẩu mới phải chứa ít nhất 8 kí tự!",Toast.LENGTH_SHORT).show();
        }
        else if(!mkhientai.equals(Common.currentUser.getPassword())){
            Toast.makeText(getActivity(),"Mật khẩu hiện tại không khớp! Hãy thử lại",Toast.LENGTH_SHORT).show();
        }
        else if(!mkmoi.equals(xacnhanmk))
        {
            Toast.makeText(getActivity(),"Xác nhận mật khẩu không khớp! Hãy thử lại",Toast.LENGTH_SHORT).show();
        }
        else {
            mDatabase = FirebaseDatabase.getInstance().getReference("User");
            mDatabase.child(Common.currentUserPhone).child("password").setValue(mkmoi);
            Toast.makeText(getActivity(),"Đổi mật khẩu thành công",Toast.LENGTH_SHORT).show();

            //Quay lại trang hồ sơ
            fragment = new HoSoFragment();
            replaceFragment(fragment);
        }
    }

    private void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container_doimatkhau, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}