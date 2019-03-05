package com.ahmadelbaz.gamestore;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditGameActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;

    String name, id, price, quantity;


    TextInputEditText productName;
    TextInputEditText productPrice;
    TextInputEditText productQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_game);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        name = getIntent().getExtras().getString("name");
        price = getIntent().getExtras().getString("price");
        quantity = getIntent().getExtras().getString("quantity");
        id = getIntent().getExtras().getString("id");


        productName = (TextInputEditText) findViewById(R.id.productName);
        productPrice = (TextInputEditText) findViewById(R.id.productPrice);
        productQuantity = (TextInputEditText) findViewById(R.id.productQuantity);

        productName.setText(name);
        productPrice.setText(price);
        productQuantity.setText(quantity);

        productName.requestFocus();

    }

    public void update(View view) {

        String nameText, priceText, quantityText;

        nameText = productName.getText().toString();
        priceText = productPrice.getText().toString();
        quantityText = productQuantity.getText().toString();

        Game game = new Game(nameText, priceText, quantityText);

        game.setId(id);

        mDatabase.child("game").child(id).setValue(game);

        onBackPressed();
        finish();
    }

}
