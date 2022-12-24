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

import com.example.demock.Admin.LichHen;
import com.example.demock.Admin.LichHenActivity;
import com.example.demock.Common.Common;
import com.example.demock.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Date;

public class DatLichFragment extends Fragment {

    private CalendarView calendar;
    private Button btn_datlich;
    private ChipGroup cgGio;
    private Boolean isChooseDay = false;
    String strdate;
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
                strdate = dayOfMonth + "/" + (month+1) + "/" + year;
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                ParsePosition pos = new ParsePosition(0);
                Date date = format.parse(strdate,pos);

                //Kiểm tra ngày có hợp lệ
                if (System.currentTimeMillis() > date.getTime()) {
                    setChipEnable(false);
                    Toast.makeText(getActivity(), "Ngày đã quá hạn", Toast.LENGTH_SHORT).show();
                    isChooseDay=false;
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
                if(!isDateValid(strdate)){
                    Toast.makeText(getActivity(),"Vui lòng chọn ngày phù hợp" , Toast.LENGTH_SHORT).show();

                }
                else if(cgGio.getCheckedChipId()==-1){
                    Toast.makeText(getActivity(),"Vui lòng chọn giờ" , Toast.LENGTH_SHORT).show();
                }
                else{
                    String gio = "";
                    String sdt = Common.currentUserPhone;
                    String ten = Common.currentUser.getName();
                    for (int i=0; i<cgGio.getChildCount();i++){
                        Chip chip = (Chip)cgGio.getChildAt(i);
                        if (chip.isChecked()){
                            gio = chip.getText().toString().trim();
                            break;
                        }
                    }
                    String lich = strdate + " " + gio;
                    LichHen lichHen = new LichHen(sdt, ten, lich);
                    onClickPushData(lichHen);

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

    public static boolean isDateValid(String date)
    {
        try {
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void onClickPushData(LichHen lichHen) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("ds_lichhen");

        String pathObject = String.valueOf(lichHen.getSdt());
        myRef.child(pathObject).setValue(lichHen, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(getActivity(), "Thêm lịch hẹn thành công", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
