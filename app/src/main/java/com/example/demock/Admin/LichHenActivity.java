package com.example.demock.Admin;

import static com.example.demock.Common.Utilities.formatDateTime;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demock.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LichHenActivity extends AppCompatActivity {

    private EditText etSdt, etTen, etLichHen;
    private Button btnThemLichHen, btnTroLai;
    private RecyclerView rvLichHen;
    private LichHenAdapter mLichHenAdapter;
    private List<LichHen> mListLichHen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lichhen);

        initUi();

        btnThemLichHen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sdt = etSdt.getText().toString().trim();
                String ten = etTen.getText().toString().trim();
                String lich = etLichHen.getText().toString().trim();

                if(sdt.equals("") || ten.equals("")||lich.equals(""))
                {
                    Toast.makeText(LichHenActivity.this, "H??y ??i???n ?????y ????? th??ng tin", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    LichHen lichHen = new LichHen(sdt, ten, lich);
                    onClickPushData(lichHen);
                }
            }
        });

        btnTroLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getListLichHenFromDb();
    }

    private void initUi() {
        etSdt = findViewById(R.id.etSdt);
        etTen = findViewById(R.id.etTen);
        etLichHen = findViewById(R.id.etLichHen);
        btnThemLichHen = findViewById(R.id.btnThemLichHen);
        btnTroLai = findViewById(R.id.btnTroLai);

        rvLichHen = findViewById(R.id.rvLichHen);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvLichHen.setLayoutManager(linearLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rvLichHen.addItemDecoration(dividerItemDecoration);

        mListLichHen = new ArrayList<>();
        mLichHenAdapter = new LichHenAdapter(mListLichHen, new LichHenAdapter.IClickListener() {
            @Override
            public void onClickUpdateItem(LichHen lichHen) {
                openDialogUpdateItem(lichHen);
            }

            @Override
            public void onClickDeleteItem(LichHen lichHen) {
                onClickDeleteData(lichHen);
            }
        });

        rvLichHen.setAdapter(mLichHenAdapter);
    }


    private void onClickPushData(LichHen lichHen) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("ds_lichhen");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String pathObject = formatDateTime(lichHen.getLichHen());
                //Ki???m tra l???ch h???n t???n t???i ch??a
                if(snapshot.child(pathObject).exists()){
                    Toast.makeText(LichHenActivity.this,"L???ch h???n ???? b??? tr??ng h??y thay ?????i th???i gian!",Toast.LENGTH_SHORT).show();
                }
                else{
                    myRef.child(pathObject).setValue(lichHen, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                            Toast.makeText(LichHenActivity.this, "Th??m l???ch h???n th??nh c??ng", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    private void getListLichHenFromDb() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("ds_lichhen");

//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (mListLichHen != null) {
//                    mListLichHen.clear();
//                }
//
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    LichHen lichHen = dataSnapshot.getValue(LichHen.class);
//                    mListLichHen.add(lichHen);
//                }
//
//                mLichHenAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(ThemLichHenActivity.this, "Get list B???nh Nh??n faild", Toast.LENGTH_SHORT).show();
//            }
//        });

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                LichHen lichHen = snapshot.getValue(LichHen.class);
                if (lichHen != null) {
                    mListLichHen.add(lichHen);
                    mLichHenAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                LichHen lichHen = snapshot.getValue(LichHen.class);
                if (lichHen == null || mListLichHen == null || mListLichHen.isEmpty()) {
                    return;
                }

                for (int i = 0; i < mListLichHen.size(); i++) {
                    if (lichHen.getSdt() == mListLichHen.get(i).getSdt()) {
                        mListLichHen.set(i, lichHen);
                        break;
                    }
                }

                mLichHenAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                LichHen lichHen = snapshot.getValue(LichHen.class);
                if (lichHen == null || mListLichHen == null || mListLichHen.isEmpty()) {
                    return;
                }

                for (int i = 0; i < mListLichHen.size(); i++) {
                    if (lichHen.getSdt() == mListLichHen.get(i).getSdt()) {
                        mListLichHen.remove(mListLichHen.get(i));
                        break;
                    }
                }

                mLichHenAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void openDialogUpdateItem(LichHen lichHen) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_update_lichhen);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        EditText etTenMoi = dialog.findViewById(R.id.etTenMoi);
        EditText etLichHenMoi = dialog.findViewById(R.id.etLichHenMoi);
        Button btnCapNhat = dialog.findViewById(R.id.btnCapNhatLichHen);
        Button btnHuy = dialog.findViewById(R.id.btnHuy);

        etTenMoi.setText(lichHen.getTen());
        etLichHenMoi.setText(lichHen.getLichHen());

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("ds_lichhen");

                String tenMoi = etTenMoi.getText().toString().trim();
                lichHen.setTen(tenMoi);

                String lichHenMoi = etLichHenMoi.getText().toString().trim();
                lichHen.setLichHen(formatDateTime(lichHenMoi));

                myRef.child(String.valueOf(lichHen.getSdt())).updateChildren(lichHen.toMap(), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        Toast.makeText(LichHenActivity.this, "C???p nh???t l???ch h???n th??nh c??ng", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
            }
        });

        dialog.show();
    }

    private void onClickDeleteData(LichHen lichHen) {
        new AlertDialog.Builder(this)
                .setTitle("Th??ng b??o")
                .setMessage("B???n c?? ch???c ch???n xo?? l???ch h???n n??y kh??ng?")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("ds_lichhen");

                        myRef.child(String.valueOf(lichHen.getSdt())).removeValue(new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                Toast.makeText(LichHenActivity.this, "Xo?? l???ch h???n th??nh c??ng", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .setNegativeButton("Cancel", null).show();
    }
}