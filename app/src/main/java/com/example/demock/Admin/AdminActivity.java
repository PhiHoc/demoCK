package com.example.demock.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.demock.Common.Common;
import com.example.demock.R;
import com.example.demock.SignInActivity;

public class AdminActivity extends AppCompatActivity implements View.OnClickListener{

    public CardView cardBenhNhan, cardVatTu, cardLichHen,cardThoat;
    private TextView tvName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        //Khởi tạo các giá trị
        cardBenhNhan = (CardView) findViewById(R.id.cardBenhNhan);
        cardVatTu = (CardView) findViewById(R.id.cardVatTu);
        cardLichHen = (CardView) findViewById(R.id.cardLichHen);
        cardThoat = (CardView) findViewById(R.id.cardThoat);
        tvName = findViewById(R.id.tvName);

        //Gán giá trị mặc định
        if(!Common.currentUser.getName().equals(""))
            tvName.setText(Common.currentUser.getName());

        //Xử lí sự kiện nhấn các nút
        cardBenhNhan.setOnClickListener(this);
        cardVatTu.setOnClickListener(this);
        cardLichHen.setOnClickListener(this);
        cardThoat.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.cardBenhNhan:
                intent = new Intent(this,BenhNhanActivity.class);
                startActivity(intent);
                break;

            case R.id.cardVatTu:
                intent = new Intent(this,VatTuActivity.class);
                startActivity(intent);
                break;

            case R.id.cardLichHen:
                intent = new Intent(this,LichHenActivity.class);
                startActivity(intent);
                break;
            case R.id.cardThoat:
                intent = new Intent(this, SignInActivity.class);
                Common.clear();
                startActivity(intent);
                finish();
                break;
        }
    }
}