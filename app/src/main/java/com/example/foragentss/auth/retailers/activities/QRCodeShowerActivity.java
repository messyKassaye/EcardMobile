package com.example.foragentss.auth.retailers.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.foragentss.R;
import com.example.foragentss.constants.Constants;
import com.example.foragentss.rooms.entity.Card;
import com.example.foragentss.rooms.view_model.CardsRoomViewModel;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class QRCodeShowerActivity extends AppCompatActivity implements View.OnClickListener {

    private String card_number,aftaCardText;
    private ImageView qrImage;
    private TextView card_in_text,cardTypeName;
    private int card_id;
    private int card_type_id;
    private Button notSoldBTN,soldBTN;
    private CardsRoomViewModel cardsRoomViewModel;
    private LinearLayout cardsMainLayout,updatingLayout;

    private  Card card;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_shower);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Selling cards");

        card = new Card();

        cardsRoomViewModel = ViewModelProviders.of(this).get(CardsRoomViewModel.class);

        card_number = getIntent().getStringExtra("card_number");
        card_id = Integer.valueOf(getIntent().getStringExtra("card_id"));
        card_type_id = Integer.parseInt(getIntent().getStringExtra("card_type_id"));

        cardsRoomViewModel.update(card_id)
                .observe(this,cards -> {
                    card = cards.get(0);
                });
        aftaCardText = "Afta*805*"+card_number+"#"+Constants.getCardTypeValue(card_type_id)+".";

        cardTypeName = findViewById(R.id.card_type_name);
        cardTypeName.setText(Constants.getCardTypeValue(card_type_id)+" Birr card");
        qrImage = findViewById(R.id.QRcode);
        card_in_text = findViewById(R.id.card_in_text);
        card_in_text.setText(addSpaces(card_number));

        notSoldBTN = findViewById(R.id.notSoldBTN);
        notSoldBTN.setOnClickListener(this::onClick);
        soldBTN = findViewById(R.id.soldBTN);
        soldBTN.setOnClickListener(this::onClick);

        cardsMainLayout = findViewById(R.id.QRCodeMainLayout);
        updatingLayout = findViewById(R.id.QRUpdatingCardLayout);

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(aftaCardText, BarcodeFormat.QR_CODE,200,200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            qrImage.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    public void updateCard(int card_id,String status){
        card.setStatus(status);
        cardsRoomViewModel.store(card);
    }

    public static String addSpaces(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(str.charAt(0));
        for (int i = 1; i < str.length(); i++) {
            if(i%4==0){
                sb.append("   ");
            }
            sb.append(str.charAt(i));
        }
        return sb.toString();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View view) {
        cardsMainLayout.setVisibility(View.GONE);
        updatingLayout.setVisibility(View.VISIBLE);
        switch (view.getId()){
            case R.id.notSoldBTN:
                updateCard(card_id,"not_soled");
            case R.id.soldBTN:
                updateCard(card_id,"Sold");
        }
        finish();
    }
}
