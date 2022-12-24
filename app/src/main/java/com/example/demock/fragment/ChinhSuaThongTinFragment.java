package com.example.demock.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.FractionRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.demock.Common.Common;
import com.example.demock.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChinhSuaThongTinFragment extends Fragment {
    private Button btnLuu,btnHuy;
    private EditText etHoTen,etNgaySinh,etDiaChi;
    private RadioGroup genderRadioGroup;
    private RadioButton maleRadioButton,femaleRadioButton,selected;
    private Fragment fragment;
    private DatabaseReference userdatabase;

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chinhsuathongtin, container, false);
        //Khởi tạo các giá trị
        btnLuu = view.findViewById(R.id.btnLuu_chinhsuathongtin);
        btnHuy = view.findViewById(R.id.btnHuy_chinhsuathongtin);
        etHoTen = view.findViewById(R.id.etHoTen);
        etNgaySinh = view.findViewById(R.id.etNgaySinh);
        etDiaChi = view.findViewById(R.id.etDiaChi);
        femaleRadioButton = view.findViewById(R.id.femaleRadioButton);
        maleRadioButton = view.findViewById(R.id.maleRadioButton);
        genderRadioGroup = view.findViewById(R.id.genderRadioGroup);

        //Gán các giá trị mặc định
        etHoTen.setText(Common.currentUser.getName());
        etNgaySinh.setText(Common.currentUser.getBirthday());
        etDiaChi.setText(Common.currentUser.getAddress());
        if(Common.currentUser.getGender().equals("Nam"))
        {
            maleRadioButton.setChecked(true);
        }
        else{
            femaleRadioButton.setChecked(true);
        }

        //Xử lí sự kiện nhấn nút hủy
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new HoSoFragment();
                replaceFragment(fragment);
            }
        });

        //Xử lí sự kiện ấn nút lưu
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnClickSave();
            }
        });

        return view;
    }

    private void OnClickSave() {
        String hoTen = etHoTen.getText().toString().trim();
        String diachi = etDiaChi.getText().toString().trim();
        String ngaysinh = etNgaySinh.getText().toString().trim();
        if(hoTen.equals("") || diachi.equals("") || ngaysinh.equals("")){
            Toast.makeText(getActivity(),"Vui lòng nhập đầy đủ thông tin!",Toast.LENGTH_SHORT).show();
        }
        else if(genderRadioGroup.getCheckedRadioButtonId() == -1){
            Toast.makeText(getActivity(),"Vui lòng chọn giới tính!",Toast.LENGTH_SHORT).show();
        }
        else
        {
            String gender;
            if(maleRadioButton.isChecked()){
                gender = maleRadioButton.getText().toString();
            }
            else {
                gender = femaleRadioButton.getText().toString();
            }
            //get gender text
            userdatabase = FirebaseDatabase.getInstance().getReference("User").child(Common.currentUserPhone);
            userdatabase.child("name").setValue(hoTen);
            userdatabase.child("address").setValue(diachi);
            userdatabase.child("birthday").setValue(ngaysinh);
            userdatabase.child("gender").setValue(gender);
            Toast.makeText(getActivity(),"Cập nhật thông tin thành công!",Toast.LENGTH_SHORT).show();

            //Quay lại trang hồ sơ
            fragment = new HoSoFragment();
            replaceFragment(fragment);

        }
    }

    private void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container_chinhsuathongtin, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
