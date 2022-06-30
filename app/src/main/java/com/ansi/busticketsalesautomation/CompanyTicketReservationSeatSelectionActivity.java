package com.ansi.busticketsalesautomation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class CompanyTicketReservationSeatSelectionActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;

    int selectedSeatNumber = 0;

    Button btnSeat1, btnSeat2, btnSeat3, btnSeat4, btnSeat5, btnSeat6, btnSeat7, btnSeat8, btnSeat9, btnSeat10,
            btnSeat11, btnSeat12,btnSeat13,btnSeat14,btnSeat15,btnSeat16,btnSeat17,btnSeat18,btnSeat19,
            btnSeat20,btnSeat21,btnSeat22,btnSeat23,btnSeat24;

    Button btnConfirmSeat;
    List<View> seats;
    List<View> soldSeats;

    Dialog genderSelectionDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_ticket_reservation_seat_selection);

        databaseHelper = new DatabaseHelper(this);

        genderSelectionDialog = new Dialog(this);
        genderSelectionDialog.setContentView(R.layout.gender_selection_dialog);
        genderSelectionDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.gender_selection_dialog_background));
        genderSelectionDialog.getWindow().setLayout(500, ViewGroup.LayoutParams.WRAP_CONTENT);
        genderSelectionDialog.setCancelable(false);
        genderSelectionDialog.getWindow().getAttributes().windowAnimations = R.style.GenderSelectionDialogAnimation;

        Button btnMan = genderSelectionDialog.findViewById(R.id.btnManGenderSelectionDialog);
        Button btnWoman = genderSelectionDialog.findViewById(R.id.btnWomanGenderSelectionDialog);

        btnMan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReservationInformation.GENDER = "Erkek";
                btnConfirmSeat.setVisibility(View.VISIBLE);
                genderSelectionDialog.dismiss();
            }
        });

        btnWoman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReservationInformation.GENDER = "Kadın";
                btnConfirmSeat.setVisibility(View.VISIBLE);
                genderSelectionDialog.dismiss();
            }
        });

        btnSeat1 = findViewById(R.id.companyBtnSeat1);
        btnSeat2 = findViewById(R.id.companyBtnSeat2);
        btnSeat3 = findViewById(R.id.companyBtnSeat3);
        btnSeat4 = findViewById(R.id.companyBtnSeat4);
        btnSeat5 = findViewById(R.id.companyBtnSeat5);
        btnSeat6 = findViewById(R.id.companyBtnSeat6);
        btnSeat7 = findViewById(R.id.companyBtnSeat7);
        btnSeat8 = findViewById(R.id.companyBtnSeat8);
        btnSeat9 =  findViewById(R.id.companyBtnSeat9);
        btnSeat10 = findViewById(R.id.companyBtnSeat10);
        btnSeat11 = findViewById(R.id.companyBtnSeat11);
        btnSeat12 = findViewById(R.id.companyBtnSeat12);
        btnSeat13 = findViewById(R.id.companyBtnSeat13);
        btnSeat14 = findViewById(R.id.companyBtnSeat14);
        btnSeat15 = findViewById(R.id.companyBtnSeat15);
        btnSeat16 = findViewById(R.id.companyBtnSeat16);
        btnSeat17 = findViewById(R.id.companyBtnSeat17);
        btnSeat18 = findViewById(R.id.companyBtnSeat18);
        btnSeat19 = findViewById(R.id.companyBtnSeat19);
        btnSeat20 = findViewById(R.id.companyBtnSeat20);
        btnSeat21 = findViewById(R.id.companyBtnSeat21);
        btnSeat22 = findViewById(R.id.companyBtnSeat22);
        btnSeat23 = findViewById(R.id.companyBtnSeat23);
        btnSeat24 = findViewById(R.id.companyBtnSeat24);
        btnConfirmSeat = findViewById(R.id.btnConfirmSeatCompany);

        seats = new ArrayList<>();
        seats.add(btnSeat1);
        seats.add(btnSeat2);
        seats.add(btnSeat3);
        seats.add(btnSeat4);
        seats.add(btnSeat5);
        seats.add(btnSeat6);
        seats.add(btnSeat7);
        seats.add(btnSeat8);
        seats.add(btnSeat9);
        seats.add(btnSeat10);
        seats.add(btnSeat11);
        seats.add(btnSeat12);
        seats.add(btnSeat13);
        seats.add(btnSeat14);
        seats.add(btnSeat15);
        seats.add(btnSeat16);
        seats.add(btnSeat17);
        seats.add(btnSeat18);
        seats.add(btnSeat19);
        seats.add(btnSeat20);
        seats.add(btnSeat21);
        seats.add(btnSeat22);
        seats.add(btnSeat23);
        seats.add(btnSeat24);

        List<TicketDto> tickets = databaseHelper.getTicketsByExpeditionIdAndDepartureDate(ReservationInformation.EXPEDITION_ID, ReservationInformation.DEPARTURE_DATE);
        List<TicketReservation> reservations = databaseHelper.getTicketReservationsByExpeditionIdAndDepartureDate(ReservationInformation.EXPEDITION_ID, ReservationInformation.DEPARTURE_DATE);

        soldSeats = new ArrayList<>();
        for (TicketDto ticket : tickets) {
            for (View seat : seats) {
                int seatNumber = Integer.parseInt(((Button)seat).getText().toString());
                if (seatNumber == ticket.getTicket().getSeatNumber()){
                    soldTicketAction(seat, ticket.getTicket().getGender());
                    soldSeats.add(seat);
                }
            }
        }

        for (TicketReservation reservation : reservations) {
            for (View seat : seats) {
                int seatNumber = Integer.parseInt(((Button)seat).getText().toString());
                if (seatNumber == reservation.getSeatNumber()){
                    soldTicketAction(seat, reservation.getGender());
                    soldSeats.add(seat);
                }
            }
        }

        for (View seat : soldSeats) {
            seats.remove(seat);
        }

        for (View seat : seats) {
            selectedSeatAction(seat);
        }

        btnConfirmSeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedSeatNumber > 0){
                    ReservationInformation.SEAT_NUMBER = selectedSeatNumber;
                    Intent intent = new Intent(getApplicationContext(), CompanyTicketSummaryActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

    private void seatBackgroundReset(List<View> seats){
        for (View seat : seats) {
            seat.setBackground(getResources().getDrawable(R.drawable.armchair));
        }
    }

    private void selectedSeatAction(View view){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seatBackgroundReset(seats);
                view.setBackground(getResources().getDrawable(R.drawable.armchair_selected));
                selectedSeatNumber = Integer.parseInt(((Button)view).getText().toString());
                genderSelectionDialog.show();
            }
        });
    }

    private void soldTicketAction(View button, String gender){

        if (gender.equals("Erkek")){
            button.setBackground(getResources().getDrawable(R.drawable.armchair_man));
        }
        else{
            button.setBackground(getResources().getDrawable(R.drawable.armchair_woman));
        }
        button.setEnabled(false);

    }
}