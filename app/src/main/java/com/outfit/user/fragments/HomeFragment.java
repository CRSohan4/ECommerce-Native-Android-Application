package com.outfit.user.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.outfit.user.R;
import com.outfit.user.activity.AllProductActivity;
import com.outfit.user.adapter.HomeSliderAdapter;
import com.outfit.user.adapter.ProductAdapter;
import com.outfit.user.adapter.SellerListAdapter;
import com.outfit.user.model.ItemRecyclerModel;
import com.outfit.user.model.SellerRecyclerModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private static final String TAG = "Home";
    ViewPager viewPager;
    HomeSliderAdapter homeSliderAdapter;
    RecyclerView rcySeller;
    SellerListAdapter sellerListAdapter;

    private List<SellerRecyclerModel> sellerList;
    private String seller_uid;

    //Firebase var's
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private FirebaseUser mUser;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home, container, false);

        init(view);

        //reaching exact user personal details
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                Log.d(TAG, "onDataChange: " + dataSnapshot.toString());
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    SellerRecyclerModel sellerRecyclerModel = new SellerRecyclerModel();
                    sellerRecyclerModel.setSeller_uid(snapshot.getKey());
                    sellerRecyclerModel.setAddress(snapshot.child("address").getValue().toString());
                    sellerRecyclerModel.setShowroom(snapshot.child("showroom").getValue().toString());
                    sellerRecyclerModel.setStatus(snapshot.child("status").getValue().toString());

                    sellerList.add(sellerRecyclerModel);

                }
                sellerListAdapter = new SellerListAdapter(view.getContext(), sellerList);
                rcySeller.setAdapter(sellerListAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }

    private void init(View view) {

        sellerList = new ArrayList<>();

        //initialization of firebase var's
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        myRef = FirebaseDatabase.getInstance().getReference().child("Users").child("Sellers");

//        viewPager = view.findViewById(R.id.viewPager);
//        homeSliderAdapter = new HomeSliderAdapter(getContext());
//        viewPager.setAdapter(homeSliderAdapter);

        rcySeller = view.findViewById(R.id.rcySellerList);
    }
}
