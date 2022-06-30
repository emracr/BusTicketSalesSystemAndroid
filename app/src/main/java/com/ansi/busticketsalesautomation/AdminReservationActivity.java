package com.ansi.busticketsalesautomation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AdminReservationActivity extends AppCompatActivity {

    EditText tbFirstName, tbLastName, tbEmail, tbPhoneNumber;
    Button btnGoToTravelInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_reservation);

        tbFirstName = findViewById(R.id.tbFirstNameReservationAdminCustomerInfo);
        tbLastName = findViewById(R.id.tbLastNameReservationAdminCustomerInfo);
        tbEmail = findViewById(R.id.tbEmailReservationAdminCustomerInfo);
        tbPhoneNumber = findViewById(R.id.tbPhoneNumberReservationAdminCustomerInfo);
        btnGoToTravelInfo = findViewById(R.id.btnGoToTravelInfoAdminReservation);

        btnGoToTravelInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String firstName, lastName, email, phoneNumber;

                firstName = tbFirstName.getText().toString();
                lastName = tbLastName.getText().toString();
                email = tbEmail.getText().toString();
                phoneNumber = tbPhoneNumber.getText().toString();

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
                    tbPhoneNumber.setError("Telefon numarası boş bırakılamaz");
                }
                else if(phoneNumber.length() < 11){
                    tbPhoneNumber.setError("Telefon numarası 11 hane uzunluğunda olmalı");
                }
                else {
                    ReservationInformation.FIRST_NAME = firstName;
                    ReservationInformation.LAST_NAME = lastName;
                    ReservationInformation.PHONE_NUMBER = phoneNumber;
                    ReservationInformation.EMAIL = email;
                    Intent intent = new Intent(getApplicationContext(), AdminReservationTravelInfoActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}