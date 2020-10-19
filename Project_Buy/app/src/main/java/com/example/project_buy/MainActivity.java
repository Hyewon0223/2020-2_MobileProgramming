package com.example.project_buy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public String[] TrimData(){
        int check = 0;

        final CheckBox FILA_checkbox = (CheckBox)findViewById(R.id.checkBox_FILA);
        final CheckBox NIKE_checkbox = (CheckBox)findViewById(R.id.checkBox_NIKE);
        final CheckBox CON_checkbox = (CheckBox)findViewById(R.id.checkBox_CON);
        CheckBox[] checkboxProduct = {FILA_checkbox, NIKE_checkbox, CON_checkbox};
        String[] productName = {"[FILA]펑키테니스 1998","[나이키]나이키 클래식 코르테즈", "[CONVERSE]척테일러 올스타 클래식"};
        String[] productPrice = {"59000", "99000", "55000"};
        String product = "";

        for (int i=0;i<checkboxProduct.length;i++) {
            if (checkboxProduct[i].isChecked()) {
                check++;
                product += productName[i] + "/" + productPrice[i] + "&";
            }
        }
        if (check==0) return null;
        String[] checkProduct = product.split("&");
        return checkProduct;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button cartpage = (Button)findViewById(R.id.button_cart);

        cartpage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String[] DATA = TrimData();
                if (DATA == null) Toast.makeText(getApplicationContext(), "선택사항이 없습니다.", Toast.LENGTH_LONG).show();
                else {
                    Intent myintent = new Intent(MainActivity.this, CartActivity.class);
                    Toast.makeText(getApplicationContext(), "장바구니 페이지", Toast.LENGTH_LONG).show();
                    startActivity(myintent);

                    Intent infointent = new Intent(MainActivity.this, CartActivity.class);
                    infointent.putExtra("data", DATA);
                    startActivity(infointent);
                    finish();
                }
            }
        });

        Button buypage = (Button)findViewById(R.id.button_buy);
        buypage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String[] DATA = TrimData();
                if (DATA == null) Toast.makeText(getApplicationContext(), "선택사항이 없습니다.", Toast.LENGTH_LONG).show();
                else {
                    Toast.makeText(getApplicationContext(), "구매 페이지", Toast.LENGTH_LONG).show();

                    //System.out.println("buypage");

                    Intent infointent = new Intent(MainActivity.this, BuyActivity.class);
                    infointent.putExtra("data", DATA);
                    startActivity(infointent);
                    finish();
                }
            }
        });
    }


}