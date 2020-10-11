package com.outfit.user.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.outfit.user.Global.TotalPrice;
import com.outfit.user.R;
import com.outfit.user.adapter.CartAdapter;
import com.outfit.user.adapter.ProductAdapter;
import com.outfit.user.model.ItemRecyclerModel;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    CartAdapter cartAdapter;
    RecyclerView recyclerView;
    TextView txtTotalPrice;
    LinearLayout lnrCart;
    Button btnCheckout;
    ImageView back;

    private List<ItemRecyclerModel> itemList;

    //Firebase var's
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private FirebaseUser mUser;

    private String TAG = "Cart";

    private String item_uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        init();

        //reaching exact user personal details
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    ItemRecyclerModel itemRecyclerModel = new ItemRecyclerModel();
                    itemRecyclerModel.setQty(snapshot.child("qty").getValue().toString());
                    itemRecyclerModel.setGender(snapshot.child("gender").getValue().toString());
                    itemRecyclerModel.setSeller_uid(snapshot.child("seller_uid").getValue().toString());
                    itemRecyclerModel.setPrice(snapshot.child("price").getValue().toString());
                    itemRecyclerModel.setItemName(snapshot.child("itemName").getValue().toString());
                    itemRecyclerModel.setItemImageUrl(snapshot.child("itemImageUrl").getValue().toString());
                    itemRecyclerModel.setItem_uid(item_uid);


                    itemList.add(itemRecyclerModel);

                }
                cartAdapter = new CartAdapter(CartActivity.this, itemList, txtTotalPrice);
                recyclerView.setAdapter(cartAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


//        cartAdapter = new CartAdapter(this);
//        recyclerView.setAdapter(cartAdapter);

        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this, AddAddressActivity.class);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void init() {

        recyclerView = findViewById(R.id.rcyCart);
        txtTotalPrice = findViewById(R.id.txtTotalPrice);
        lnrCart = findViewById(R.id.lnrCart);
        btnCheckout = findViewById(R.id.btnCheckout);
        back = findViewById(R.id.back);

        item_uid = getIntent().getStringExtra("item_uid");

        itemList = new ArrayList<>();

        //initialization of firebase var's
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        myRef = FirebaseDatabase.getInstance().getReference().child("AddToCart").child(mUser.getUid());
    }
}
