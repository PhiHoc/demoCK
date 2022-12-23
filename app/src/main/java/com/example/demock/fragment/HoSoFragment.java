package com.example.demock.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.demock.R;

public class HoSoFragment extends Fragment implements View.OnClickListener {

    Button btnChinhSuaThongTin;
    Button btnDoiMatKhau;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hoso, container, false);

        btnChinhSuaThongTin = view.findViewById(R.id.btnChinhSuaThongTin);
        btnDoiMatKhau = view.findViewById(R.id.btnDoiMatKhau);
        btnChinhSuaThongTin.setOnClickListener(this);
        btnDoiMatKhau.setOnClickListener(this);

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
