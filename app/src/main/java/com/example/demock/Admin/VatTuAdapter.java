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

public class VatTuAdapter extends RecyclerView.Adapter<VatTuAdapter.VatTuViewHolder> {

    private List<VatTu> mListVatTu;
    private IClickListener mIClickListener;

    public VatTuAdapter(List<VatTu> mListVatTu, IClickListener mIClickListener) {
        this.mListVatTu = mListVatTu;
        this.mIClickListener = mIClickListener;
    }

    public interface IClickListener {
        void onClickUpdateItem(VatTu vatTu);
        void onClickDeleteItem(VatTu vatTu);

    }

    @NonNull
    @Override
    public VatTuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vattu, parent, false);
        return new VatTuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VatTuViewHolder holder, int position) {
        VatTu vatTu = mListVatTu.get(position);
        if (vatTu == null) {
            return;
        }

        holder.tvId.setText("ID: " + vatTu.getId());
        holder.tvTen.setText("Tên: " + vatTu.getTen());
        holder.tvSoLuong.setText("Số lượng: " + vatTu.getSoLuong());
        holder.tvHanSuDung.setText("Hạn sử dụng: " + vatTu.getHanSuDung());


        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIClickListener.onClickUpdateItem(vatTu);
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIClickListener.onClickDeleteItem(vatTu);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mListVatTu != null) {
            return mListVatTu.size();
        }
        return 0;
    }

    public class VatTuViewHolder extends RecyclerView.ViewHolder {

        private TextView tvId, tvTen, tvSoLuong, tvHanSuDung;
        private Button btnUpdate, btnDelete;

        public VatTuViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tvId);
            tvTen = itemView.findViewById(R.id.tvTen);
            tvSoLuong = itemView.findViewById(R.id.tvSoLuong);
            tvHanSuDung = itemView.findViewById(R.id.tvHanSuDung);
            btnUpdate = itemView.findViewById(R.id.btnUpdate);
            btnDelete = itemView.findViewById(R.id.btnDelete);

        }
    }
}
