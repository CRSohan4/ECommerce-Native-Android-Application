package com.outfit.user.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.outfit.user.R;

public class ChooseShoppingGenderActivity extends AppCompatActivity {

    LinearLayout lnrMale, lnrFemale;
    String seller_uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_shopping_gender);
        lnrMale = findViewById(R.id.lnrMale);
        lnrFemale = findViewById(R.id.lnrFemale);

        seller_uid = getIntent().getStringExtra("seller_uid");

        lnrMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseShoppingGenderActivity.this, AllProductActivity.class);
                intent.putExtra("seller_uid", seller_uid);
                intent.putExtra("gender", "Men");
                startActivity(intent);
            }
        });

        lnrFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseShoppingGenderActivity.this, AllProductActivity.class);
                intent.putExtra("seller_uid", seller_uid);
                intent.putExtra("gender", "Women");
                startActivity(intent);
            }
        });
    }
}
