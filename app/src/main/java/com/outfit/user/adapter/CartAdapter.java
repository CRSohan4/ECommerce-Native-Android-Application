package com.outfit.user.adapter;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.outfit.user.Global.TotalPrice;
import com.outfit.user.R;
import com.outfit.user.activity.CartActivity;
import com.outfit.user.model.ItemRecyclerModel;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private Context context;
    private List<ItemRecyclerModel> requestList;

    //Firebase var's
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;

    private static int totalPrice;

    public CartAdapter(Context context, List<ItemRecyclerModel> requestList, TextView txtTotalPrice) {
        this.context = context;
        this.requestList = requestList;

        //        firebase variables declaration
        mAuth = FirebaseAuth.getInstance();
//        updating new user information to the database
        myRef = FirebaseDatabase.getInstance().getReference().child("AddToCart").child(mAuth.getUid());

        int totalPrice = 0;
        for(ItemRecyclerModel list: requestList){
            int itemPrice = Integer.parseInt(list.getPrice());
            int itemQty = Integer.parseInt(list.getQty());

            totalPrice = totalPrice + itemPrice * itemQty;
        }
        txtTotalPrice.setText(String.valueOf(totalPrice));
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_cart_item, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {

        final ItemRecyclerModel item = requestList.get(position);
        holder.name.setText(item.getItemName());
        holder.quantity.setText(item.getQty());
        holder.price.setText(item.getPrice());
        
        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int qty = Integer.parseInt(item.getQty()) - 1;
                if(qty <= 0){
                    Toast.makeText(context, "Quantity should not be zero.", Toast.LENGTH_SHORT).show();
                }else{
                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                                Log.d("TAG", "onDataChange: pass 1 " + item.getItem_uid() + "   " + snapshot.child("item_uid").getValue().toString());
                                if(snapshot.child("item_uid").getValue().toString().equals(item.getItem_uid())){
                                    Log.d("TAG", "onDataChange: pass 2");
                                    int quantity = Integer.parseInt(snapshot.child("qty").getValue().toString());
                                    quantity = quantity - 1;
                                    myRef.child(snapshot.getKey()).child("qty").setValue(String.valueOf(quantity)).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d("TAG", "onSuccess: pass 3");
                                            Toast.makeText(context, "Done", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView img, minus, plus;
        TextView name, quantity, price, remove;


        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imgProduct);
            minus = itemView.findViewById(R.id.minus);
            plus = itemView.findViewById(R.id.plus);
            name = itemView.findViewById(R.id.txtProductName);
            quantity = itemView.findViewById(R.id.txtQuantity);
            price = itemView.findViewById(R.id.txtPrice);
            remove = itemView.findViewById(R.id.txRemove);

        }
    }
}
