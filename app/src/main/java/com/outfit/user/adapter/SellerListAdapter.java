package com.outfit.user.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.outfit.user.R;
import com.outfit.user.activity.CartActivity;
import com.outfit.user.activity.ChooseShoppingGenderActivity;
import com.outfit.user.model.ItemRecyclerModel;
import com.outfit.user.model.SellerRecyclerModel;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class SellerListAdapter extends RecyclerView.Adapter<SellerListAdapter.SellerListViewHolder> {

    private Context context;
    private List<SellerRecyclerModel> requestList;

    public SellerListAdapter(Context context, List<SellerRecyclerModel> requestList) {
        this.context = context;
        this.requestList = requestList;
    }

    @NonNull
    @Override
    public SellerListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_seller_list_item, parent, false);
        return new SellerListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SellerListViewHolder holder, int position) {

        final SellerRecyclerModel item = requestList.get(position);
        holder.showroomTv.setText(item.getShowroom());
        holder.addressTv.setText(item.getAddress());
        holder.statusTv.setText(item.getStatus());
        
        holder.cardSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChooseShoppingGenderActivity.class);
                intent.putExtra("seller_uid", item.getSeller_uid());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }

    public class SellerListViewHolder extends RecyclerView.ViewHolder {

        TextView showroomTv, addressTv, statusTv;
        LinearLayout cardSeller;

        public SellerListViewHolder(@NonNull View itemView) {
            super(itemView);
            showroomTv = itemView.findViewById(R.id.showroomTv);
            addressTv = itemView.findViewById(R.id.addressTv);
            statusTv = itemView.findViewById(R.id.statusTv);
            cardSeller = itemView.findViewById(R.id.cardSeller);
        }
    }
}
