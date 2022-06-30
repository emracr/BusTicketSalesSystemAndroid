package com.ansi.busticketsalesautomation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AdminTicketSummaryActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;

    TextView lblFromCity, lblToCity, lblTravelDate, lblTravelHour, lblSeat, lblGender, lblPrice;
    Button btnCompleteReservation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_ticket_summary);

        databaseHelper = new DatabaseHelper(this);

        lblFromCity = findViewById(R.id.lblFromCityAdminTicketSummary);
        lblToCity = findViewById(R.id.lblToCityAdminTicketSummary);
        lblTravelDate = findViewById(R.id.lblTravelDateAdminTicketSummary);
        lblTravelHour = findViewById(R.id.lblTravelHourAdminTicketSummary);
        lblSeat = findViewById(R.id.lblSeatAdminTicketSummary);
        lblGender = findViewById(R.id.lblGenderAdminTicketSummary);
        lblPrice = findViewById(R.id.lblPriceAdminTicketSummary);
        btnCompleteReservation = findViewById(R.id.btnCompleteReservationAdmin);

        lblFromCity.setText("Nereden: " + ReservationInformation.CITY_FROM);
        lblToCity.setText("Nereye: " + ReservationInformation.CITY_TO);
        lblTravelDate.setText("Tarih: " + ReservationInformation.DEPARTURE_DATE_LONG);
        lblTravelHour.setText("Saat: " + ReservationInformation.DEPARTURE_HOUR);
        lblSeat.setText("Koltuk No: " + ReservationInformation.SEAT_NUMBER);
        lblGender.setText("Cinsiyet: " + ReservationInformation.GENDER);
        lblPrice.setText("Fiyat: " + ReservationInformation.PRICE + ",00 ₺");

        btnCompleteReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* int expeditionId, String firstName, String lastName, String phoneNumber, String email, String departureDate, String gender, int seatNumber, int price */
                TicketReservation reservation = new TicketReservation(ReservationInformation.EXPEDITION_ID, ReservationInformation.FIRST_NAME, ReservationInformation.LAST_NAME, ReservationInformation.PHONE_NUMBER, ReservationInformation.EMAIL, ReservationInformation.DEPARTURE_DATE, ReservationInformation.GENDER, ReservationInformation.SEAT_NUMBER, ReservationInformation.PRICE);
                boolean result = insertReservation(reservation);
                if (result){
                    Toast.makeText(getApplicationContext(), "Rezervasyon başarılı bir şekilde alındı",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), AdminHomeActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    DialogBox.showDialog(getSupportFragmentManager(), "Hata", "Beklenmedik bir hata oluştu");
                }
            }
        });

    }

    private boolean insertReservation(TicketReservation reservation){

        return databaseHelper.insertTicketReservation(reservation);

    }
}