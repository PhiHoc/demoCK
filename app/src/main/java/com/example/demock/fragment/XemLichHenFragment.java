package com.example.demock.fragment;

import static com.example.demock.Common.Utilities.formatDateTime;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.demock.Admin.LichHen;
import com.example.demock.Admin.LichHenActivity;
import com.example.demock.Admin.LichHenAdapter;
import com.example.demock.Common.Common;
import com.example.demock.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class XemLichHenFragment extends Fragment {
    private RecyclerView rvLichHen;
    private LichHenAdapter mLichHenAdapter;
    private List<LichHen> mListLichHen;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_xemlichhen, container, false);

        rvLichHen = view.findViewById(R.id.rvLichHen);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rvLichHen.setLayoutManager(linearLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
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
        getListLichHenFromDb();
        return view;
    }

    private void getListLichHenFromDb() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("ds_lichhen");

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                LichHen lichHen = snapshot.getValue(LichHen.class);
                if (lichHen != null && lichHen.getSdt().equals(Common.currentUserPhone)) {
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
        final Dialog dialog = new Dialog(getActivity());
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
                        Toast.makeText(getActivity(), "Cập nhật lịch hẹn thành công", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
            }
        });

        dialog.show();
    }

    private void onClickDeleteData(LichHen lichHen) {
        new AlertDialog.Builder(getActivity())
                .setTitle("Thông báo")
                .setMessage("Bạn có chắc chắn xoá lịch hẹn này không?")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("ds_lichhen");

                        myRef.child(String.valueOf(lichHen.getSdt())).removeValue(new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                Toast.makeText(getActivity(), "Xoá lịch hẹn thành công", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .setNegativeButton("Cancel", null).show();
    }
}