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

public class BenhNhanAdapter extends RecyclerView.Adapter<BenhNhanAdapter.BenhNhanViewHolder> {

    private List<BenhNhan> mListBenhNhan;
    private IClickListener mIClickListener;

    public interface IClickListener {
        void onClickUpdateItem(BenhNhan benhNhan);
        void onClickDeleteItem(BenhNhan benhNhan);

    }

    public BenhNhanAdapter(List<BenhNhan> mListBenhNhan, IClickListener listener) {
        this.mListBenhNhan = mListBenhNhan;
        this.mIClickListener = listener;
    }


    @NonNull
    @Override
    public BenhNhanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_benhnhan, parent, false);
        return new BenhNhanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BenhNhanViewHolder holder, int position) {
        BenhNhan benhNhan = mListBenhNhan.get(position);
        if (benhNhan == null) {
            return;
        }

        holder.tvId.setText("ID: " + benhNhan.getId());
        holder.tvTen.setText("Tên: " + benhNhan.getTen());
        holder.tvSdt.setText("Sđt: " + benhNhan.getSdt());

        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIClickListener.onClickUpdateItem(benhNhan);
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIClickListener.onClickDeleteItem(benhNhan);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (mListBenhNhan != null) {
            return mListBenhNhan.size();
        }
        return 0;
    }

    public class BenhNhanViewHolder extends RecyclerView.ViewHolder {

        private TextView tvId, tvTen, tvSdt;
        private Button btnUpdate, btnDelete;

        public BenhNhanViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tvId);
            tvTen = itemView.findViewById(R.id.tvTen);
            tvSdt = itemView.findViewById(R.id.tvSdt);
            btnUpdate = itemView.findViewById(R.id.btnUpdate);
            btnDelete = itemView.findViewById(R.id.btnDelete);

        }
    }
}
