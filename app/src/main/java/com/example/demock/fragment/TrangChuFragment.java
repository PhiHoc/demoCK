package com.example.demock.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.demock.Common.Common;
import com.example.demock.R;
import com.example.demock.SignInActivity;

public class TrangChuFragment extends Fragment implements View.OnClickListener{

    CardView cardDichVu;
    CardView cardLichHen;
    CardView cardThoat;
    private TextView tvName;

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trangchu, container, false);
        //Khởi tạo các giá trị
        cardDichVu = (CardView) view.findViewById(R.id.cardDichVu);
        cardLichHen = (CardView) view.findViewById(R.id.cardLichHen);
        cardThoat = (CardView) view.findViewById(R.id.cardThoat);
        tvName = view.findViewById(R.id.tvName);
        tvName.setText(Common.currentUser.getName());

        cardDichVu.setOnClickListener(this);
        cardLichHen.setOnClickListener(this);
        cardThoat.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View v) {
        Fragment fragment = null;
        Intent intent;

        switch (v.getId()) {
            case R.id.cardDichVu:
                fragment = new DichVuFragment();
                replaceFragment(fragment);
                break;

            case R.id.cardLichHen:
                fragment = new XemLichHenFragment();
                replaceFragment(fragment);
                break;

            case R.id.cardThoat:
                Intent home = new Intent(getActivity(), SignInActivity.class);
                startActivity(home);
                Common.clear();
                ((Activity) getActivity()).overridePendingTransition(0, 0);
                break;

        }
    }

    private void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container_trangchu, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
