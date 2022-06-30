package com.ansi.busticketsalesautomation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CompanyCustomerInfoForReservationActivity extends AppCompatActivity {

    EditText tbFirstName, tbLastName, tbEmail, tbPhone;
    Button btnGoToTravelInfoCompany;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_customer_info_for_reservation);

        tbFirstName = findViewById(R.id.tbFirstNameCompanyCustomerInfo);
        tbLastName = findViewById(R.id.tbLastNameCompanyCustomerInfo);
        tbEmail = findViewById(R.id.tbEmailCompanyCustomerInfo);
        tbPhone = findViewById(R.id.tbPhoneCompanyCustomerInfo);
        btnGoToTravelInfoCompany = findViewById(R.id.btnGoToTravelInfoCompany);

        btnGoToTravelInfoCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String firstName, lastName, email, phoneNumber;

                firstName = tbFirstName.getText().toString();
                lastName = tbLastName.getText().toString();
                email = tbEmail.getText().toString();
                phoneNumber = tbPhone.getText().toString();

                if(firstName.isEmpty() || firstName.equals(null)){
                    tbFirstName.setError("Ad boş bırakılamaz");
                }
                else if(lastName.isEmpty() || lastName.equals(null)){
                    tbLastName.setError("Soyad boş bırakılamaz");
                }
                else if(email.isEmpty() || email.equals(null)){
                    tbEmail.setError("Email boş bırakılamaz");
                }
                else if(phoneNumber.isEmpty() || phoneNumber.equals(null)){
                    tbPhone.setError("Telefon numarası boş bırakılamaz");
                }
                else if(phoneNumber.length() < 11){
                    tbPhone.setError("Telefon numarası 11 hane uzunluğunda olmalı");
                }
                else {
                    ReservationInformation.FIRST_NAME = firstName;
                    ReservationInformation.LAST_NAME = lastName;
                    ReservationInformation.PHONE_NUMBER = phoneNumber;
                    ReservationInformation.EMAIL = email;
                    Intent intent = new Intent(getApplicationContext(), CompanyTicketReservationActivity.class);
                    startActivity(intent);
                }
            }
        });

    }
}