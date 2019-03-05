package com.ahmadelbaz.gamestore;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class GameAdapter extends ArrayAdapter<Game> {

    private DatabaseReference mDatabase;

    private Context context;



    public GameAdapter( Context context, ArrayList<Game> games) {
        super(context, 0, games);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final Game game = getItem(position);

        convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_game, parent, false);

        TextView gameName;
        TextView gamePrice;
        TextView gameQuantity;

        ImageView deleteImage;

        RelativeLayout layout;

        mDatabase = FirebaseDatabase.getInstance().getReference();

        gameName = (TextView) convertView.findViewById(R.id.gameName_textView);
        gamePrice = (TextView) convertView.findViewById(R.id.gamePrice_textView);
        gameQuantity = (TextView) convertView.findViewById(R.id.gameQuantity_textView);
        layout = (RelativeLayout) convertView.findViewById(R.id.layout);

        deleteImage = (ImageView) convertView.findViewById(R.id.deleteImage);

        deleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                new AlertDialog.Builder(context)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are You Sure?")
                        .setMessage("Do You Want to Delete this item?")
                        .setPositiveButton("Yes, Sure", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                mDatabase.child("game").child(game.getId()).removeValue();

                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });


        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), EditGameActivity.class);

                intent.putExtra("name", game.getNameProduct());
                intent.putExtra("id", game.getId());
                intent.putExtra("price", game.getPriceProduct());
                intent.putExtra("quantity", game.getQuantityProduct());

                getContext().startActivity(intent);
            }
        });


        gameName.setText(game.getNameProduct());
        gamePrice.setText("Price : " + game.getPriceProduct() + " $");
        gameQuantity.setText("Quantity : " + game.getQuantityProduct());

        return convertView;

    }
}
