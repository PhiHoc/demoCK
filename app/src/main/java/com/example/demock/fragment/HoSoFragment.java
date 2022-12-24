package com.example.demock.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.demock.Common.Common;
import com.example.demock.R;
import com.example.demock.SignInActivity;

public class HoSoFragment extends Fragment implements View.OnClickListener {

    Button btnChinhSuaThongTin;
    Button btnDoiMatKhau,btnDangXuat;
    TextView tvHoTen,tvNgaySinh,tvGioiTinh,tvDiaChi;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hoso, container, false);

        //Khởi tạo các giá trị
        btnChinhSuaThongTin = view.findViewById(R.id.btnChinhSuaThongTin);
        btnDoiMatKhau = view.findViewById(R.id.btnDoiMatKhau);
        btnDangXuat = view.findViewById(R.id.btnDangXuat);
        btnDangXuat.setOnClickListener(this);
        btnChinhSuaThongTin.setOnClickListener(this);
        btnDoiMatKhau.setOnClickListener(this);
        tvHoTen = view.findViewById(R.id.tvHoTen);
        tvNgaySinh = view.findViewById(R.id.tvNgaySinh);
        tvGioiTinh = view.findViewById(R.id.tvGioiTinh);
        tvDiaChi = view.findViewById(R.id.tvDiaChi);

        //Gán các giá trị mặc định
        tvHoTen.setText(Common.currentUser.getName());
        tvNgaySinh.setText(Common.currentUser.getBirthday());
        tvGioiTinh.setText(Common.currentUser.getGender());
        tvDiaChi.setText(Common.currentUser.getAddress());

        return view;
    }

    @Override
    public void onClick(View v) {
        Fragment fragment = null;
        switch (v.getId()) {
            case R.id.btnChinhSuaThongTin:
                fragment = new ChinhSuaThongTinFragment();
                replaceFragment(fragment);
                break;

            case R.id.btnDoiMatKhau:
                fragment = new DoiMatKhauFragment();
                replaceFragment(fragment);
                break;
            case R.id.btnDangXuat:
                Intent home = new Intent(getActivity(), SignInActivity.class);
                startActivity(home);
                ((Activity) getActivity()).overridePendingTransition(0, 0);
                break;
            default:
                break;

        }
    }

    private void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container_hoso, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
