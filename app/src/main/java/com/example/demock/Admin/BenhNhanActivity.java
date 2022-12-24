package com.example.demock.Admin;

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

import java.util.ArrayList;
import java.util.List;

public class BenhNhanActivity extends AppCompatActivity {

    private EditText etId, etTen, etSdt;
    private Button btnThemBenhNhan, btnTroLai;
    private RecyclerView rvBenhNhan;
    private BenhNhanAdapter mBenhNhanAdapter;
    private List<BenhNhan> mListBenhNhan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_benhnhan);

        initUi();

        btnThemBenhNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = Integer.parseInt(etId.getText().toString().trim());
                String ten = etTen.getText().toString().trim();
                String sdt = etSdt.getText().toString().trim();

                BenhNhan benhNhan = new BenhNhan(id, ten, sdt);
                onClickPushData(benhNhan);
            }
        });

        btnTroLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getListBenhNhanFromDb();
    }

    private void initUi() {
        etId = findViewById(R.id.etId);
        etTen = findViewById(R.id.etTen);
        etSdt = findViewById(R.id.etSdt);
        btnThemBenhNhan = findViewById(R.id.btnThemBenhNhan);
        btnTroLai = findViewById(R.id.btnTroLai);

        rvBenhNhan = findViewById(R.id.rvBenhNhan);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvBenhNhan.setLayoutManager(linearLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rvBenhNhan.addItemDecoration(dividerItemDecoration);

        mListBenhNhan = new ArrayList<>();
        mBenhNhanAdapter = new BenhNhanAdapter(mListBenhNhan, new BenhNhanAdapter.IClickListener() {
            @Override
            public void onClickUpdateItem(BenhNhan benhNhan) {
                openDialogUpdateItem(benhNhan);
            }

            @Override
            public void onClickDeleteItem(BenhNhan benhNhan) {
                onClickDeleteData(benhNhan);
            }
        });

        rvBenhNhan.setAdapter(mBenhNhanAdapter);
    }

    private void onClickPushData(BenhNhan benhNhan) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("ds_benhnhan");

        String pathObject = String.valueOf(benhNhan.getId());
        myRef.child(pathObject).setValue(benhNhan, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(BenhNhanActivity.this, "Thêm Bệnh nhân thành công", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getListBenhNhanFromDb() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("ds_benhnhan");

//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (mListBenhNhan != null) {
//                    mListBenhNhan.clear();
//                }
//
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    BenhNhan benhNhan = dataSnapshot.getValue(BenhNhan.class);
//                    mListBenhNhan.add(benhNhan);
//                }
//
//                mBenhNhanAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(ThemBenhNhanActivity.this, "Get list Bệnh Nhân faild", Toast.LENGTH_SHORT).show();
//            }
//        });

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                BenhNhan benhNhan = snapshot.getValue(BenhNhan.class);
                if (benhNhan != null) {
                    mListBenhNhan.add(benhNhan);
                    mBenhNhanAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                BenhNhan benhNhan = snapshot.getValue(BenhNhan.class);
                if (benhNhan == null || mListBenhNhan == null || mListBenhNhan.isEmpty()) {
                    return;
                }

                for (int i = 0; i < mListBenhNhan.size(); i++) {
                    if (benhNhan.getId() == mListBenhNhan.get(i).getId()) {
                        mListBenhNhan.set(i, benhNhan);
                        break;
                    }
                }

                mBenhNhanAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                BenhNhan benhNhan = snapshot.getValue(BenhNhan.class);
                if (benhNhan == null || mListBenhNhan == null || mListBenhNhan.isEmpty()) {
                    return;
                }

                for (int i = 0; i < mListBenhNhan.size(); i++) {
                    if (benhNhan.getId() == mListBenhNhan.get(i).getId()) {
                        mListBenhNhan.remove(mListBenhNhan.get(i));
                        break;
                    }
                }

                mBenhNhanAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void openDialogUpdateItem(BenhNhan benhNhan) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_update_benhnhan);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        EditText etTenMoi = dialog.findViewById(R.id.etTenMoi);
        EditText etSdtMoi = dialog.findViewById(R.id.etSdtMoi);
        Button btnCapNhat = dialog.findViewById(R.id.btnCapNhat);
        Button btnHuy = dialog.findViewById(R.id.btnHuy);

        etTenMoi.setText(benhNhan.getTen());
        etSdtMoi.setText(benhNhan.getSdt());

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
                DatabaseReference myRef = database.getReference("ds_benhnhan");

                String tenMoi = etTenMoi.getText().toString().trim();
                benhNhan.setTen(tenMoi);

                String sdtMoi = etSdtMoi.getText().toString().trim();
                benhNhan.setSdt(sdtMoi);

                myRef.child(String.valueOf(benhNhan.getId())).updateChildren(benhNhan.toMap(), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        Toast.makeText(BenhNhanActivity.this, "Cập nhật bệnh nhân thành công", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
            }
        });

        dialog.show();
    }

    private void onClickDeleteData(BenhNhan benhNhan) {
        new AlertDialog.Builder(this)
                .setTitle("Thông báo")
                .setMessage("Bạn có chắc chắn xoá bệnh nhân này không?")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("ds_benhnhan");

                        myRef.child(String.valueOf(benhNhan.getId())).removeValue(new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                Toast.makeText(BenhNhanActivity.this, "Xoá bệnh nhân thành công", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .setNegativeButton("Cancel", null).show();
    }
}