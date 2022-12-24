package com.example.demock.fragment;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.demock.Common.Common;
import com.example.demock.R;

public class TrangChuFragment extends Fragment implements View.OnClickListener{

    CardView cardDichVu;
    CardView cardLienHe;
    CardView cardLichSu;
    CardView cardThanhToan;
    private TextView tvName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trangchu, container, false);
        //Khởi tạo các giá trị
        cardDichVu = (CardView) view.findViewById(R.id.cardDichVu);
        cardLienHe = (CardView) view.findViewById(R.id.cardLienHe);
        cardLichSu = (CardView) view.findViewById(R.id.cardLichSu);
        cardThanhToan = (CardView) view.findViewById(R.id.cardThanhToan);
        tvName = view.findViewById(R.id.tvName);
        tvName.setText(Common.currentUser.getName());

        cardDichVu.setOnClickListener(this);
        cardLienHe.setOnClickListener(this);
        cardLichSu.setOnClickListener(this);
        cardThanhToan.setOnClickListener(this);

//        cardDichVu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showToast("dv");
//                Log.d(TAG, "onClick: ");
//                Toast.makeText(getActivity(),"dv", Toast.LENGTH_SHORT).show();
//            }
//        });
//        cardLienHe.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showToast("lh");
//                Toast.makeText(getActivity(),"lh", Toast.LENGTH_SHORT).show();
//
//            }
//        });
//        cardLichSu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showToast("ls");
//            }
//        });
//        cardThanhToan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showToast("TT");
//            }
//        });


        return view;
    }

//    private void showToast(String msg) {
//        Toast.makeText(getActivity(), "msg", Toast.LENGTH_SHORT).show();
//    }

    @Override
    public void onClick(View v) {
        Fragment fragment = null;
        switch (v.getId()) {
            case R.id.cardDichVu:
                fragment = new DichVuFragment();
                replaceFragment(fragment);
                break;

            case R.id.cardLienHe:
                fragment = new LienHeFragment();
                replaceFragment(fragment);
                break;

            case R.id.cardLichSu:
                fragment = new LichSuFragment();
                replaceFragment(fragment);
                break;

            case R.id.cardThanhToan:
                fragment = new ThanhToanFragment();
                replaceFragment(fragment);
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
