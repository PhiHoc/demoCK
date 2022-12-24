package com.example.demock.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.demock.Model.LichHen;
import com.example.demock.R;
import com.google.android.material.chip.ChipGroup;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DatLichFragment extends Fragment {

    private CalendarView calendar;
    private Button btn_datlich;
    private ChipGroup cgGio;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_datlich, container, false);
        //Khởi tạo các giá trị
        calendar = view.findViewById(R.id.calendar);
        cgGio = view.findViewById(R.id.cgGio);
        btn_datlich = view.findViewById(R.id.btn_datlich);

        //Xử lí sự kiện chọn lịch hẹn
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                String strdate = dayOfMonth + "/" + (month+1) + "/" + year;
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                ParsePosition pos = new ParsePosition(0);
                Date date = format.parse(strdate,pos);

                //Kiểm tra ngày có hợp lệ
                if (System.currentTimeMillis() > date.getTime()) {
                    setChipEnable(false);
                    Toast.makeText(getActivity(), "Ngày đã quá hạn", Toast.LENGTH_SHORT).show();
                }
                else {
                    setChipEnable(true);
                }
            }
        });

        //Xử lí sự kiện ấn nút đặt lịch
        btn_datlich.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                if(cgGio.getCheckedChipId()==-1){
                    Toast.makeText(getActivity(),"Vui lòng chọn giờ" , Toast.LENGTH_SHORT).show();
                }
                else{

                }
            }
        });

        return view;
    }
    public void setChipEnable(boolean status) {
        for (int i = 0; i < cgGio.getChildCount(); i++) {
            cgGio.getChildAt(i).setEnabled(status);
        }
    }
}
