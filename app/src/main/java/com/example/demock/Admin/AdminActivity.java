package com.example.demock.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.demock.R;

public class AdminActivity extends AppCompatActivity implements View.OnClickListener{

    public CardView cardBenhNhan, cardVatTu, cardLichHen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        cardBenhNhan = (CardView) findViewById(R.id.cardBenhNhan);
        cardVatTu = (CardView) findViewById(R.id.cardVatTu);
        cardLichHen = (CardView) findViewById(R.id.cardLichHen);

        cardBenhNhan.setOnClickListener(this);
        cardVatTu.setOnClickListener(this);
        cardLichHen.setOnClickListener(this);

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
                intent = new Intent(this, VatTuActivity.class);
                startActivity(intent);
                break;

            case R.id.cardLichHen:
                intent = new Intent(this,LichHenActivity.class);
                startActivity(intent);
                break;
        }
    }
}