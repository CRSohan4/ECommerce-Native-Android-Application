package com.outfit.user.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.outfit.user.R;
import com.outfit.user.activity.AddAddressActivity;
import com.outfit.user.activity.CartActivity;
import com.outfit.user.activity.HomeActivity;
import com.outfit.user.activity.RegisterActivity;
import com.outfit.user.model.CustomerRegisterModel;
import com.outfit.user.model.ItemRecyclerModel;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private Context context;
    private List<ItemRecyclerModel> requestList;

    //Firebase var's
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;

    public ProductAdapter(Context context, List<ItemRecyclerModel> requestList) {
        this.context = context;
        this.requestList = requestList;


//        firebase variables declaration
        mAuth = FirebaseAuth.getInstance();
//        updating new user information to the database
        myRef = FirebaseDatabase.getInstance().getReference().child("AddToCart");
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_product_list_item, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {

        final ItemRecyclerModel item = requestList.get(position);
        holder.txtPrice.setText(item.getPrice());
        holder.txtProductName.setText(item.getItemName());

        holder.txtAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final DatabaseReference newFeedRef = myRef.child(mAuth.getUid()).push();
                newFeedRef.setValue(item).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent intent = new Intent(context, CartActivity.class);
                        intent.putExtra("item_uid", item.getItem_uid());
                    context.startActivity(intent);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "ERROR: " + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView txtAddToCart, txtProductName, txtPrice;
        ImageView iv_category;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            txtAddToCart = itemView.findViewById(R.id.txtAddToCart);
            txtProductName = itemView.findViewById(R.id.txtProductName);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            iv_category = itemView.findViewById(R.id.iv_category);


        }
    }
}
