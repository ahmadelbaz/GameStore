package com.ahmadelbaz.gamestore;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddProductActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;

    TextInputEditText productName;
    TextInputEditText productPrice;
    TextInputEditText productQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);


        mDatabase = FirebaseDatabase.getInstance().getReference();


        productName = (TextInputEditText) findViewById(R.id.productName);
        productPrice = (TextInputEditText) findViewById(R.id.productPrice);
        productQuantity = (TextInputEditText) findViewById(R.id.productQuantity);


    }

    public void add(View view) {

        String enteredName, enteredPrice, enteredQuantity;

        enteredName = productName.getText().toString();
        enteredPrice = productPrice.getText().toString();
        enteredQuantity = productQuantity.getText().toString();

        if(enteredName.isEmpty() || enteredName.equals(" ")){

            productName.setError("Fill here please");
            return;
        }

        if(enteredPrice.isEmpty() || enteredPrice.equals(" ")){

            productPrice.setError("Fill here please");
            return;
        }

        if(enteredQuantity.isEmpty() || enteredQuantity.equals(" ")){

            productQuantity.setError("Fill here please");
            return;
        }


        Game game = new Game(enteredName, enteredPrice, enteredQuantity);

        String key = mDatabase.child("game").push().getKey();

        game.setId(key);

        mDatabase.child("game").child(key).setValue(game);

        productName.setText("");
        productPrice.setText("");
        productQuantity.setText("");

        productName.requestFocus();

        Intent intent = new Intent(AddProductActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();

    }
}
