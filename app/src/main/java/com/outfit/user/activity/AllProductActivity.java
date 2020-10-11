package com.outfit.user.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.outfit.user.R;
import com.outfit.user.adapter.ProductAdapter;
import com.outfit.user.model.ItemRecyclerModel;

import java.util.ArrayList;
import java.util.List;

public class AllProductActivity extends AppCompatActivity {

    RecyclerView rcyProduct;
    ProductAdapter productAdapter;

    private String TAG = "ALlproductActivity";

    private List<ItemRecyclerModel> itemList;
    private String seller_uid, gender;

    //Firebase var's
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_product);

        init();

        //reaching exact user personal details
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                Log.d(TAG, "onDataChange: " + dataSnapshot.toString());
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Log.d(TAG, "onDataChange: " + snapshot.toString());
                    ItemRecyclerModel itemRecyclerModel = new ItemRecyclerModel();
                    itemRecyclerModel.setSeller_uid(seller_uid);
                    itemRecyclerModel.setItem_uid(snapshot.getKey());
                    Log.d(TAG, "onDataChange: TEST " + snapshot.getKey());
                    itemRecyclerModel.setItemImageUrl(snapshot.child("item_image_url").getValue().toString());
                    itemRecyclerModel.setItemName(snapshot.child("itemname").getValue().toString());
                    itemRecyclerModel.setPrice(snapshot.child("price").getValue().toString());
                    itemRecyclerModel.setGender(gender);
                    itemRecyclerModel.setQty("1");


                    itemList.add(itemRecyclerModel);

                }
                productAdapter = new ProductAdapter(AllProductActivity.this, itemList);
                rcyProduct.setAdapter(productAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void init() {
        seller_uid = getIntent().getStringExtra("seller_uid");
        gender = getIntent().getStringExtra("gender");
        rcyProduct = findViewById(R.id.rcyProduct);

        itemList = new ArrayList<>();

        //initialization of firebase var's
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        myRef = FirebaseDatabase.getInstance().getReference().child("Items").child(seller_uid).child(gender);
    }
}
