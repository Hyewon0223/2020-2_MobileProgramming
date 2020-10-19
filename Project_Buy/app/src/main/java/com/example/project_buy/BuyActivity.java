package com.example.project_buy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class BuyActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;// ...

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        final String[] productName = {"[FILA]펑키테니스 1998","[나이키]나이키 클래식 코르테즈", "[CONVERSE]척테일러 올스타 클래식"};
        final String[] productPrice = {"59000", "99000", "55000"};

//        final ImageView img1 = (ImageView) findViewById(R.id.imageView_buy1);
//        final ImageView img2 = (ImageView) findViewById(R.id.imageView_buy2);
//        final ImageView img3 = (ImageView) findViewById(R.id.imageView_buy3);

        final TextView txt1 = (TextView) findViewById(R.id.textView_buy1);
        final TextView txt2 = (TextView) findViewById(R.id.textView_buy2);
        final TextView txt3 = (TextView) findViewById(R.id.textView_buy3);
        final TextView txt_sum = (TextView) findViewById((R.id.textView_sum));

        final EditText editPhone = (EditText) findViewById(R.id.editTextPhone);
        final EditText editAddress = (EditText) findViewById(R.id.editTextTextPostalAddress);

//        final ImageView[] img = {img1, img2, img3};
        final TextView[] txt = {txt1, txt2, txt3};

        Intent dataintent = getIntent();
        String[] data = dataintent.getStringArrayExtra("data");
        int sum = 0;


        for (int i=0;i<data.length;i++) {
            String[] select = data[i].split("/");
            String[] price = select[1].split("원");
            txt[i].setText(select[0]+"/"+price[0]+"원");
            sum += Integer.parseInt(price[0]);
        }
        txt_sum.setText("구매 합계 : "+sum+"원");

        Button completepage = (Button)findViewById(R.id.button_complete);
        completepage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String editTextPhone = editPhone.getText().toString();
                String editTextAddress = editAddress.getText().toString();
                String userId = editTextPhone;

                if (editTextPhone.length() == 0){
                    Toast.makeText(getApplicationContext(),"PHONE 정보를 입력하세요.", Toast.LENGTH_LONG).show();
                }
                else if (editTextAddress.length() == 0){
                    Toast.makeText(getApplicationContext(),"ADDRESS 정보를 입력하세요.", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getApplicationContext(),"구매 완료", Toast.LENGTH_LONG).show();
                    Intent myintent = new Intent(BuyActivity.this, MainActivity.class);
                    HashMap result = new HashMap<>();
                    result.put("phone", editTextPhone);
                    result.put("address", editTextAddress);

                    writeNewUser(userId, editTextPhone, editTextAddress);

                    startActivity(myintent);
                    finish();
                }
            }
            private void writeNewUser(String userId, String phonenumber, String address) {
                User user = new User(phonenumber, address);

                mDatabase.child("users").child(userId).setValue(user)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Write was successful!
                                Toast.makeText(BuyActivity.this, "저장을 완료했습니다.", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Write failed
                                Toast.makeText(BuyActivity.this, "저장을 실패했습니다.", Toast.LENGTH_SHORT).show();
                            }
                        });
            }

            private void readUser(){
                String userId = editPhone.getText().toString();
                mDatabase.child("users").child(userId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        // Get Post object and use the values to update the UI
                        if(dataSnapshot.getValue(User.class) != null){
                            User post = dataSnapshot.getValue(User.class);
                            Log.w("FireBaseData", "getData" + post.toString());
                        } else {
                            Toast.makeText(BuyActivity.this, "데이터 없음...", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Getting Post failed, log a message
                        Log.w("FireBaseData", "loadPost:onCancelled", databaseError.toException());
                    }
                });
            }
        });
    }
}