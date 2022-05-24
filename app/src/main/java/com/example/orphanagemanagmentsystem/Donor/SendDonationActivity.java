package com.example.orphanagemanagmentsystem.Donor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.orphanagemanagmentsystem.Model.Model_Donation;
import com.example.orphanagemanagmentsystem.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SendDonationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    String[] CardType = { "Credit Card", "Debit Card"};
EditText name,email,phone,cardNumber,amount,date;
Spinner spinner;
String CType,orphankey;
Button btnSubmit;
DatabaseReference databaseReference;
FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_donation);
        name=findViewById(R.id.donationName);
        email=findViewById(R.id.donationEmail);
        spinner=findViewById(R.id.cardSpinner);
        phone=findViewById(R.id.donationPhone);
        cardNumber=findViewById(R.id.donationCardNum);
        amount=findViewById(R.id.donationAmount);
        date=findViewById(R.id.donationDate);
        btnSubmit=findViewById(R.id.btnSubmitdonation);
        spinner.setOnItemSelectedListener(this);
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Donation");
        firebaseAuth=FirebaseAuth.getInstance();
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,CardType);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        Intent intent=getIntent();
        if (intent !=null){
            orphankey=intent.getStringExtra("KeyOrphan");

            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SendDonation();
                }
            });

        }

    }

    private void SendDonation() {

String Name = name.getText().toString().trim();
String Email = email.getText().toString().trim();
String Phone = phone.getText().toString().trim();
String CardT = CType;
String CardNumber = cardNumber.getText().toString().trim();
String Amount = amount.getText().toString().trim();
String Date = date.getText().toString().trim();
if (TextUtils.isEmpty(Name)){
    name.setError("Required");
} else if (TextUtils.isEmpty(Email)){
    email.setError("Required");
} else if (TextUtils.isEmpty(Phone)){
    phone.setError("Required");
} else if (TextUtils.isEmpty(CardT)){
    Toast.makeText(this, "Please Select Card Type", Toast.LENGTH_SHORT).show();
} else if (TextUtils.isEmpty(CardNumber)){
    cardNumber.setError("Required");
} else if (TextUtils.isEmpty(Amount)){
    amount.setError("Required");
} else  if (TextUtils.isEmpty(Date)){
    date.setError("Required");
} else {
    String UID=firebaseAuth.getUid();
    String key= databaseReference.push().getKey();
    assert key != null;
    Model_Donation model_donation= new Model_Donation(key,UID,orphankey,Name,Email,Phone,CardT,CardNumber,Amount,Date);
    databaseReference.child(key).setValue(model_donation).addOnSuccessListener(new OnSuccessListener<Void>() {
        @Override
        public void onSuccess(Void unused) {
            Toast.makeText(SendDonationActivity.this, "Donation Sended", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(SendDonationActivity.this,ViewOrphanActivity.class));
        }
    })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(SendDonationActivity.this, "OnDonationException\t"+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
}


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
CType = CardType[i];
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    }



