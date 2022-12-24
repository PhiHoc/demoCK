package com.example.demock.Admin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demock.R;

import java.util.List;

public class LichHenAdapter extends RecyclerView.Adapter<LichHenAdapter.LichHenViewHolder> {

    private List<LichHen> mListLichHen;
    private IClickListener mIClickListener;

    public interface IClickListener {
        void onClickUpdateItem(LichHen lichHen);
        void onClickDeleteItem(LichHen lichHen);

    }

    public LichHenAdapter(List<LichHen> mListLichHen, IClickListener listener) {
        this.mListLichHen = mListLichHen;
        this.mIClickListener = listener;
    }


    @NonNull
    @Override
    public LichHenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lichhen, parent, false);
        return new LichHenViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LichHenViewHolder holder, int position) {
        LichHen lichHen = mListLichHen.get(position);
        if (lichHen == null) {
            return;
        }

        holder.tvSdt.setText("Sđt: " + lichHen.getSdt());
        holder.tvTen.setText("Tên: " + lichHen.getTen());
        holder.tvLichHen.setText("Thời gian: " + lichHen.getLichHen());

        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIClickListener.onClickUpdateItem(lichHen);
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIClickListener.onClickDeleteItem(lichHen);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (mListLichHen != null) {
            return mListLichHen.size();
        }
        return 0;
    }

    public class LichHenViewHolder extends RecyclerView.ViewHolder {

        private TextView tvSdt, tvTen, tvLichHen;
        private Button btnUpdate, btnDelete;

        public LichHenViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSdt = itemView.findViewById(R.id.tvSdt);
            tvTen = itemView.findViewById(R.id.tvTen);
            tvLichHen = itemView.findViewById(R.id.tvLichHen);
            btnUpdate = itemView.findViewById(R.id.btnUpdate);
            btnDelete = itemView.findViewById(R.id.btnDelete);

        }
    }
}
