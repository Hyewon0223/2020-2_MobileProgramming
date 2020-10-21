# [모바일 프로그래밍] Project_Buy
### 소프트웨어학부 20191686 최혜원
### https://github.com/Hyewon0223/2020-2_MobileProgramming
---
### 실행 환경
- Android Studio 4.0
- AVD : Pixel 2 API 30
---
### 파일 설명 
#### > JAVA
- MainActivity : 선택된 상품 정보 및 가격을 장바구니 또는 구매 화면에 전달
- CartActivity : 선택 화면의 정보 받기 / 장바구니에서 선택된 상품 구매 화면으로 전달
- BuyActivity : 선택 화면 또는 장바구니로부터 상품 정보 받기 / firebase로 정보 전달
- User : firebase에 담을 정보 정리  
  
#### > Layout
- activity_main.xml : [Relative Layout]상품 이름, 가격 및 정보 표시 / 장바구니 또는 바로 구매 버튼 선택 가능
- activity_cart.xml : [Linear Layout]상품 선택 화면에서 check된 상품 표시 / HOME(상품 선택 화면) 또는 구매 버튼 선택 가능
- activity_buy.xml : [Table Layout]상품 선택 화면 또는 장바구니에서 check된 상품 구매 / 전화번호, 주소 입력 / 구매 완료시 Select 화면으로 이동
---
### 실행 화면
#### > 기본 화면
1. 상품 선택 화면
- 기본 화면   
<img src="/img/선택 화면.png" width="40%" title="실행화면_선택화면" alt="Select Product"></img>
<img src="/img/선택 화면2.png" width="40%" title="실행화면_선택화면2" alt="Select Product2"></img>   
   
- 어떤 것도 체크 되어 있지 않을 경우   
<img src="/img/NoCheck_Select.png" width="40%" title="실행화면_선택화면_NoCheck" alt="Select Product_NoCheck"></img>   
   
2. 장바구니 화면
- 기본 화면   
<img src="/img/장바구니 화면.png" width="40%" title="실행화면_장바구니화면" alt="Cart"></img>
   
- 어떤 것도 체크 되어 있지 않을 경우   
<img src="/img/NoCheck_Cart.png" width="40%" title="실행화면_장바구니_NoCheck" alt="Cart_NoCheck"></img>   
   
3. 구매 화면
- 기본 화면   
<img src="/img/구매 화면.png" width="40%" title="실행화면_구매화면" alt="Purchase"></img>   
   
- PHONE 또는 ADDRESS 정보를 입력하지 않을 때   
<img src="/img/NoData.png" width="40%" title="실행화면_구매화면_NoData" alt="Purechase_Nodata"></img>   
   
- firebase 정보전달   
<img src="/img/firebase1.png" width="70%" title="Firebase1" alt="firebase1"></img>
<img src="/img/firebase2.png" width="70%" title="Firebase1" alt="firebase2"></img>
---
### 주요 코드 및 구현 내용 설명
#### > 상품 선택 화면
1) 다른 화면으로 보낼 정보 String으로 저장해 배열로 리턴
```
public String[] TrimData(){
  int check = 0; 
  String product = "";
  for (int i=0;i<checkboxProduct.length;i++) { 
    if (checkboxProduct[i].isChecked()) { // 상품에 check되면
      check++; 
      product += productName[i] + "/" + productPrice[i] + "&"; // 상품명과 가격 사이에는 '/'로 구분, 상품과 상품 사이는 '&'로 구분
    }
  }
  if (check==0) return null; // 받아온 정보가 없다면 정보를 보내지 않음
  String[] checkProduct = product.split("&"); // product를 &를 기준으로 분리해 배열에 저장
  return checkProduct;
}
```

2) TrimData()메소드를 이용해 다른 페이지로 정보 전달
```
String[] DATA = TrimData();
if (DATA == null) Toast.makeText(getApplicationContext(), "선택사항이 없습니다.", Toast.LENGTH_LONG).show(); //데이터가 없다면 보내지 않음
else { // 그렇지 않으면 DATA 전달
  Toast.makeText(getApplicationContext(), "장바구니 페이지", Toast.LENGTH_LONG).show();
  Intent infointent = new Intent(MainActivity.this, CartActivity.class);
  infointent.putExtra("data", DATA);
  startActivity(infointent);
  finish();
}
```

#### > 장바구니 화면
1) 상품 선택 화면으로부터 정보를 받아와 화면에 이미지와 상품명, 가격 출력
```
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
```

2) 구매 화면으로 정보 전달 - 장바구니 화면의 텍스트를 
```
String product = "";
for (int i = 0; i < check.length; i++) {
    if (check[i].isChecked()) {
        String text = txt_name[i].getText().toString();
        product += text + "&";
    }
}
String[] checkProduct = product.split("&");
if (product.equals("")) Toast.makeText(getApplicationContext(), "선택사항이 없습니다.", Toast.LENGTH_LONG).show();
else {
    Toast.makeText(getApplicationContext(), "구매 페이지", Toast.LENGTH_LONG).show();

    Intent infointent = new Intent(CartActivity.this, BuyActivity.class);
    infointent.putExtra("data", checkProduct);
    startActivity(infointent);
}
```

#### > 정보 입력 및 구매 화면
1) 상품의 합계를 계산하고 화면에 표시
```
Intent dataintent = getIntent();
String[] data = dataintent.getStringArrayExtra("data");
int sum = 0;

for (int i=0;i<data.length;i++) {
    String[] select = data[i].split("/");
    String[] price = select[1].split("원");
    data_product += select[0] + "/";
    txt[i].setText(select[0]+"/"+price[0]+"원");
    sum += Integer.parseInt(price[0]);
}
txt_sum.setText("구매 합계 : "+sum+"원");
```

2) PHONE과 ADDRESS 정보를 입력하지 않을 시 Toast메시지를 이용해 화면에 표시
```
String editTextPhone = editPhone.getText().toString();
String editTextAddress = editAddress.getText().toString();
String userId = editTextPhone;

if (editTextPhone.length() == 0){
    Toast.makeText(getApplicationContext(),"PHONE 정보를 입력하세요.", Toast.LENGTH_LONG).show();
}
else if (editTextAddress.length() == 0){
    Toast.makeText(getApplicationContext(),"ADDRESS 정보를 입력하세요.", Toast.LENGTH_LONG).show();
}
```

3) PHONE과 ADDRESS에 정보를 입력하면 구매 완료
```
else {
    Toast.makeText(getApplicationContext(),"구매 완료", Toast.LENGTH_LONG).show();
    Intent myintent = new Intent(BuyActivity.this, MainActivity.class);
    HashMap result = new HashMap<>();
    result.put("phone", editTextPhone);
    result.put("address", editTextAddress);
    result.put("product", data_product);

    writeNewUser(userId, editTextPhone, editTextAddress, data_product);

    startActivity(myintent);
    finish();
}
```

4) firebase 
```
private void writeNewUser(String userId, String phonenumber, String address, String product) {
    User user = new User(phonenumber, address, product);

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
```
 
