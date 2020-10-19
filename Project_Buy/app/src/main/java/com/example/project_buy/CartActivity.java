package com.example.project_buy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        final String[] productName = {"[FILA]펑키테니스 1998","[나이키]나이키 클래식 코르테즈", "[CONVERSE]척테일러 올스타 클래식"};
        final String[] productPrice = {"59000", "99000", "55000"};

        final TextView tx1 = (TextView) findViewById(R.id.textView_cart1);
        final TextView tx2 = (TextView) findViewById(R.id.textView_cart2);
        final TextView tx3 = (TextView) findViewById(R.id.textView_cart3);

        final ImageView image1 = (ImageView) findViewById(R.id.imageView1);
        final ImageView image2 = (ImageView) findViewById(R.id.imageView2);
        final ImageView image3 = (ImageView) findViewById(R.id.imageView3);

        final CheckBox ch1 = (CheckBox) findViewById(R.id.checkBox_cart1);
        final CheckBox ch2 = (CheckBox) findViewById(R.id.checkBox_cart2);
        final CheckBox ch3 = (CheckBox) findViewById(R.id.checkBox_cart3);

        final TextView[] txt_name = {tx1, tx2, tx3};
        final ImageView[] img = {image1, image2, image3};
        final CheckBox[] check = {ch1, ch2, ch3};

        Intent dataintent = getIntent();
        String[] data = dataintent.getStringArrayExtra("data");

        for (int i=0;i<data.length;i++) {
            String[] select = data[i].split("/");
            if (select[0].equals(productName[0])) img[i].setImageResource(R.drawable.fila);
            else if (select[0].equals(productName[1])) img[i].setImageResource(R.drawable.nike);
            else if (select[0].equals(productName[2])) img[i].setImageResource(R.drawable.converse);
            check[i].setVisibility(View.VISIBLE);
            check[i].setChecked(true);
            txt_name[i].setText(select[0]+"/"+select[1]+"원");
        }

        Button homepage = (Button) findViewById(R.id.button_home);
        homepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(getApplicationContext(), "홈 페이지", Toast.LENGTH_LONG).show();
                Intent myintent = new Intent(CartActivity.this, MainActivity.class);
                startActivity(myintent);
                finish();
            }
        });

        Button buypage = (Button) findViewById(R.id.button_buy);
        buypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String product = "";
                for (int i = 0; i < check.length; i++) {
                    if (check[i].isChecked()) {
                        String text = txt_name[i].getText().toString();
                        product += text + "&";
                    }
                }
                String[] checkProduct = product.split("&");
                if (checkProduct == null) Toast.makeText(getApplicationContext(), "선택사항이 없습니다.", Toast.LENGTH_LONG).show();
                else {
                    Toast.makeText(getApplicationContext(), "구매 페이지", Toast.LENGTH_LONG).show();

                    Intent infointent = new Intent(CartActivity.this, BuyActivity.class);
                    infointent.putExtra("data", checkProduct);
                    startActivity(infointent);
                }
            }
        });
    }
}