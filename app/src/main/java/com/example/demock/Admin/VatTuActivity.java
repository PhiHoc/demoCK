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

public class VatTuActivity extends AppCompatActivity {

    private EditText etId, etTen, etSoLuong, etHanSuDung;
    private Button btnThemVatTu, btnTroLai;

    private RecyclerView rvVatTu;
    private VatTuAdapter mVatTuAdapter;
    private List<VatTu> mListVatTu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vattu);

        initUi();

        btnThemVatTu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = Integer.parseInt(etId.getText().toString().trim());
                String ten = etTen.getText().toString().trim();
                int soLuong = Integer.parseInt(etSoLuong.getText().toString().trim());
                String hanSuDung = etHanSuDung.getText().toString().trim();

                VatTu vatTu = new VatTu(id, ten, soLuong, hanSuDung);
                onClickPushData(vatTu);
            }
        });

        btnTroLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getListVatTuFromDb();

    }

    private void initUi() {
        etId = findViewById(R.id.etId);
        etTen = findViewById(R.id.etTen);
        etSoLuong = findViewById(R.id.etSoLuong);
        etHanSuDung = findViewById(R.id.etHanSuDung);
        btnThemVatTu = findViewById(R.id.btnThemVatTu);
        btnTroLai = findViewById(R.id.btnTroLai);


        rvVatTu = findViewById(R.id.rvVatTu);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvVatTu.setLayoutManager(linearLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rvVatTu.addItemDecoration(dividerItemDecoration);

        mListVatTu = new ArrayList<>();
        mVatTuAdapter = new VatTuAdapter(mListVatTu, new VatTuAdapter.IClickListener() {
            @Override
            public void onClickUpdateItem(VatTu vatTu) {
                openDialogUpdateItem(vatTu);
            }

            @Override
            public void onClickDeleteItem(VatTu vatTu) {
                onClickDeleteData(vatTu);
            }
        });

        rvVatTu.setAdapter(mVatTuAdapter);
    }

    private void onClickPushData(VatTu vatTu) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("ds_vattu");

        String pathObject = String.valueOf(vatTu.getId());
        myRef.child(pathObject).setValue(vatTu, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(VatTuActivity.this, "Thêm Vật tư thành công", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getListVatTuFromDb() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("ds_vattu");

//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (mListVatTu != null) {
//                    mListVatTu.clear();
//                }
//
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    VatTu vatTu = dataSnapshot.getValue(VatTu.class);
//                    mListVatTu.add(vatTu);
//                }
//
//                mVatTuAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(ThemVatTuActivity.this, "Get list vật tư faild", Toast.LENGTH_SHORT).show();
//            }
//        });

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                VatTu vatTu = snapshot.getValue(VatTu.class);
                if (vatTu != null) {
                    mListVatTu.add(vatTu);
                    mVatTuAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                VatTu vatTu = snapshot.getValue(VatTu.class);
                if (vatTu == null || mListVatTu == null || mListVatTu.isEmpty()) {
                    return;
                }

                for (int i = 0; i < mListVatTu.size(); i++) {
                    if (vatTu.getId() == mListVatTu.get(i).getId()) {
                        mListVatTu.set(i, vatTu);
                        break;
                    }
                }

                mVatTuAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                VatTu vatTu = snapshot.getValue(VatTu.class);
                if (vatTu == null || mListVatTu == null || mListVatTu.isEmpty()) {
                    return;
                }

                for (int i = 0; i < mListVatTu.size(); i++) {
                    if (vatTu.getId() == mListVatTu.get(i).getId()) {
                        mListVatTu.remove(mListVatTu.get(i));
                        break;
                    }
                }

                mVatTuAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void openDialogUpdateItem(VatTu vatTu) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_update_vattu);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        EditText etTenMoi = dialog.findViewById(R.id.etTenMoi);
        EditText etSoLuongMoi = dialog.findViewById(R.id.etSoLuongMoi);
        EditText etHanSuDungMoi = dialog.findViewById(R.id.etHanSuDungMoi);
        Button btnCapNhatVatTu = dialog.findViewById(R.id.btnCapNhatVatTu);
        Button btnHuy = dialog.findViewById(R.id.btnHuy);

        etTenMoi.setText(vatTu.getTen());
        etSoLuongMoi.setText(Integer.toString(vatTu.getSoLuong()));
        etHanSuDungMoi.setText(vatTu.getHanSuDung());

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnCapNhatVatTu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("ds_vattu");

                String tenMoi = etTenMoi.getText().toString().trim();
                vatTu.setTen(tenMoi);

                int soLuongMoi = Integer.parseInt(etSoLuongMoi.getText().toString().trim());
                vatTu.setSoLuong(soLuongMoi);

                String hanSuDungMoi = etHanSuDungMoi.getText().toString().trim();
                vatTu.setHanSuDung(hanSuDungMoi);

                myRef.child(String.valueOf(vatTu.getId())).updateChildren(vatTu.toMap(), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        Toast.makeText(VatTuActivity.this, "Cập nhật vật tư thành công", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
            }
        });

        dialog.show();
    }

    private void onClickDeleteData(VatTu vatTu) {
        new AlertDialog.Builder(this)
                .setTitle("Thông báo")
                .setMessage("Bạn có chắc chắn xoá vật tư này không?")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("ds_vattu");

                        myRef.child(String.valueOf(vatTu.getId())).removeValue(new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                Toast.makeText(VatTuActivity.this, "Xoá vật tư thành công", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .setNegativeButton("Cancel", null).show();
    }
}