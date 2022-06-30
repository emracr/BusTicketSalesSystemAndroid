package com.ansi.busticketsalesautomation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class AdminTicketsActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;

    LinearLayout ticketsLayout;
    EditText tbPhoneNumber;
    Button btnFindTicketByPhoneNumber;

    Dialog ticketCancelDialog;
    Button btnYes, btnNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_tickets);

        databaseHelper = new DatabaseHelper(this);

        ticketCancelDialog = new Dialog(this);
        ticketCancelDialog.setContentView(R.layout.ticket_cancel_dialog);
        ticketCancelDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.ticket_cancel_dialog_background));
        ticketCancelDialog.getWindow().setLayout(600, ViewGroup.LayoutParams.WRAP_CONTENT);
        ticketCancelDialog.setCancelable(false);
        ticketCancelDialog.getWindow().getAttributes().windowAnimations = R.style.TicketCancelDialogAnimation;

        ticketsLayout = findViewById(R.id.ticketsLayoutActivityTicketsAdmin);
        tbPhoneNumber = findViewById(R.id.tbPhoneNumberSearchTicketsAdmin);
        btnFindTicketByPhoneNumber = findViewById(R.id.btnFindTicketByPhoneNumberAdmin);

        loadTickets();

        btnFindTicketByPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = tbPhoneNumber.getText().toString();
                if (phoneNumber.isEmpty() || phoneNumber.length() <= 0){
                    loadTickets();
                }
                else if(phoneNumber.length() != 11){
                    tbPhoneNumber.setError("Telefon Numarası 11 karakter uzunluğunda olmalı!");
                }
                else{
                    List<TicketDtoAndUserDto> userTickets = databaseHelper.getTicketsDtoByPhoneNumber(phoneNumber);
                    List<TicketReservationDto> reservationTickets = databaseHelper.getTicketReservationsDtoByPhoneNumber(phoneNumber);
                    ticketsLayout.removeAllViews();
                    getTickets(userTickets);
                    getReservations(reservationTickets);
                    checkIfTicket();
                }
            }
        });


    }

    private void checkIfTicket(){

        List<TicketDtoAndUserDto> tickets = databaseHelper.getTicketsDto();
        List<TicketReservationDto> reservationTickets = databaseHelper.getTicketReservationsDto();
        if (tickets.size() <= 0 && reservationTickets.size() <= 0){
            ImageView pbWarning = new ImageView(this);
            LinearLayout.LayoutParams pbWarningParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            pbWarning.setLayoutParams(pbWarningParams);
            pbWarningParams.gravity= Gravity.CENTER;
            pbWarning.setImageResource(R.drawable.warning);
            pbWarning.setPadding(0,80,0,0);
            ticketsLayout.addView(pbWarning);

            TextView lblTicketNotFound = new TextView(this);
            LinearLayout.LayoutParams lblTicketNotFoundParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lblTicketNotFound.setLayoutParams(lblTicketNotFoundParams);
            lblTicketNotFound.setTextSize(15);
            lblTicketNotFound.setTypeface(null, Typeface.BOLD);
            lblTicketNotFound.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            lblTicketNotFoundParams.setMargins(30,30,30,10);
            lblTicketNotFound.setText("Sistemde kayıtlı bilet bulunmamaktadır !");
            lblTicketNotFound.setTextColor(getResources().getColor(R.color.gray_dark2));
            ticketsLayout.addView(lblTicketNotFound);
        }
    }

    private void loadTickets(){
        ticketsLayout.removeAllViews();
        getTickets();
        getReservations();
        checkIfTicket();
    }

    private void getTickets(){
        List<TicketDtoAndUserDto> tickets = databaseHelper.getTicketsDto();

        if (tickets.size() > 0){
            for (TicketDtoAndUserDto ticket : tickets) {

                LinearLayout linearLayout = new LinearLayout(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(20,25,20,5);
                linearLayout.setLayoutParams(layoutParams);
                linearLayout.setBackgroundResource(R.drawable.my_ticket_box_background);
                linearLayout.setOrientation(LinearLayout.VERTICAL);

                /* Start Ticket Type Layout */
                LinearLayout layoutTicketType = new LinearLayout(this);
                LinearLayout.LayoutParams layoutTicketTypeParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutTicketType.setLayoutParams(layoutTicketTypeParams);
                layoutTicketTypeParams.setMargins(30,30,0,0);
                layoutTicketType.setOrientation(LinearLayout.HORIZONTAL);

                ImageView pbTicketType = new ImageView(this);
                LinearLayout.LayoutParams pbTicketTypeParams = new LinearLayout.LayoutParams(35, 35);
                pbTicketType.setLayoutParams(pbTicketTypeParams);
                pbTicketType.setPadding(0,3,0,0);
                pbTicketType.setImageResource(R.drawable.tickets_dark);
                layoutTicketType.addView(pbTicketType);

                TextView lblTicketType = new TextView(this);
                LinearLayout.LayoutParams lblTicketTypeParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lblTicketType.setLayoutParams(lblTicketTypeParams);
                lblTicketType.setTextSize(14);
                lblTicketType.setTypeface(null, Typeface.BOLD);
                lblTicketTypeParams.setMargins(15,0,0,0);
                lblTicketType.setText("Bilet Tipi: Normal Bilet");
                lblTicketType.setTextColor(getResources().getColor(R.color.gray_dark2));
                layoutTicketType.addView(lblTicketType);

                linearLayout.addView(layoutTicketType);

                /* Start Firstname And Lastname Layout */
                LinearLayout layoutUserFirstNameAndLastName = new LinearLayout(this);
                LinearLayout.LayoutParams layoutUserFirstNameAndLastNameParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutUserFirstNameAndLastName.setLayoutParams(layoutUserFirstNameAndLastNameParams);
                layoutUserFirstNameAndLastNameParams.setMargins(30,30,0,0);
                layoutUserFirstNameAndLastName.setOrientation(LinearLayout.HORIZONTAL);

                ImageView pbUser = new ImageView(this);
                LinearLayout.LayoutParams pbUserParams = new LinearLayout.LayoutParams(35, 35);
                pbUser.setLayoutParams(pbUserParams);
                pbUser.setPadding(0,3,0,0);
                pbUser.setImageResource(R.drawable.person_2);
                layoutUserFirstNameAndLastName.addView(pbUser);

                TextView lblUserFirstNameAndLastName = new TextView(this);
                LinearLayout.LayoutParams lblUserFirstNameAndLastNameParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lblUserFirstNameAndLastName.setLayoutParams(lblUserFirstNameAndLastNameParams);
                lblUserFirstNameAndLastName.setTextSize(14);
                lblUserFirstNameAndLastName.setTypeface(null, Typeface.BOLD);
                lblUserFirstNameAndLastNameParams.setMargins(15,0,0,0);
                lblUserFirstNameAndLastName.setText("Ad Soyad: " + ticket.getUser().getFirstName() + " " + ticket.getUser().getLastName());
                lblUserFirstNameAndLastName.setTextColor(getResources().getColor(R.color.gray_dark2));
                layoutUserFirstNameAndLastName.addView(lblUserFirstNameAndLastName);

                linearLayout.addView(layoutUserFirstNameAndLastName);

                /* Start Use Phone Number Layout */
                LinearLayout layoutUserPhoneNumber = new LinearLayout(this);
                LinearLayout.LayoutParams layoutUserPhoneNumberParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutUserPhoneNumber.setLayoutParams(layoutUserPhoneNumberParams);
                layoutUserPhoneNumberParams.setMargins(30,30,0,0);
                layoutUserPhoneNumber.setOrientation(LinearLayout.HORIZONTAL);

                ImageView pbPhone = new ImageView(this);
                LinearLayout.LayoutParams pbPhoneParams = new LinearLayout.LayoutParams(35, 35);
                pbPhone.setLayoutParams(pbPhoneParams);
                pbPhone.setPadding(0,3,0,0);
                pbPhone.setImageResource(R.drawable.telephone);
                layoutUserPhoneNumber.addView(pbPhone);

                TextView lblUserPhoneNumber = new TextView(this);
                LinearLayout.LayoutParams lblUserPhoneNumberParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lblUserPhoneNumber.setLayoutParams(lblUserPhoneNumberParams);
                lblUserPhoneNumber.setTextSize(14);
                lblUserPhoneNumber.setTypeface(null, Typeface.BOLD);
                lblUserPhoneNumberParams.setMargins(15,0,0,0);
                lblUserPhoneNumber.setText("Telefon Numarası: " + ticket.getUser().getPhoneNumber());
                lblUserPhoneNumber.setTextColor(getResources().getColor(R.color.gray_dark2));
                layoutUserPhoneNumber.addView(lblUserPhoneNumber);

                linearLayout.addView(layoutUserPhoneNumber);

                /* Start From City Layout */
                LinearLayout layoutCompany = new LinearLayout(this);
                LinearLayout.LayoutParams layoutCompanyParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutCompany.setLayoutParams(layoutCompanyParams);
                layoutCompanyParams.setMargins(30,30,0,0);
                layoutCompany.setOrientation(LinearLayout.HORIZONTAL);

                ImageView pbBus = new ImageView(this);
                LinearLayout.LayoutParams pbBusParams = new LinearLayout.LayoutParams(35, 35);
                pbBus.setLayoutParams(pbBusParams);
                pbBus.setPadding(0,3,0,0);
                pbBus.setImageResource(R.drawable.bus);
                layoutCompany.addView(pbBus);

                TextView lblCompany = new TextView(this);
                LinearLayout.LayoutParams lblCompanyParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lblCompany.setLayoutParams(lblCompanyParams);
                lblCompany.setTextSize(14);
                lblCompany.setTypeface(null, Typeface.BOLD);
                lblCompanyParams.setMargins(15,0,0,0);
                lblCompany.setText("Firma: " + ticket.getTicketDto().getExpeditionDto().getCompany().getCompanyName());
                lblCompany.setTextColor(getResources().getColor(R.color.gray_dark2));
                layoutCompany.addView(lblCompany);

                linearLayout.addView(layoutCompany);

                /* Start From City Layout */
                LinearLayout layoutFromCity = new LinearLayout(this);
                LinearLayout.LayoutParams layoutFromCityParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutFromCity.setLayoutParams(layoutFromCityParams);
                layoutFromCityParams.setMargins(30,30,0,0);
                layoutFromCity.setOrientation(LinearLayout.HORIZONTAL);

                ImageView pbFromLocation = new ImageView(this);
                LinearLayout.LayoutParams pbFromLocationParams = new LinearLayout.LayoutParams(35, 35);
                pbFromLocation.setLayoutParams(pbFromLocationParams);
                pbFromLocation.setPadding(0,0,0,0);
                pbFromLocation.setImageResource(R.drawable.location_from);
                layoutFromCity.addView(pbFromLocation);

                TextView lblFromCity = new TextView(this);
                LinearLayout.LayoutParams lblFromCityParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lblFromCity.setLayoutParams(lblFromCityParams);
                lblFromCity.setTextSize(14);
                lblFromCity.setTypeface(null, Typeface.BOLD);
                lblFromCityParams.setMargins(15,0,0,0);
                lblFromCity.setText("Nereden: " + ticket.getTicketDto().getExpeditionDto().getExpedition().getCityFrom());
                lblFromCity.setTextColor(getResources().getColor(R.color.gray_dark2));
                layoutFromCity.addView(lblFromCity);

                linearLayout.addView(layoutFromCity);
                /* End From City Layout */

                /* Start To City Layout */
                LinearLayout layoutFromTo = new LinearLayout(this);
                LinearLayout.LayoutParams layoutToCityParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutFromTo.setLayoutParams(layoutToCityParams);
                layoutToCityParams.setMargins(30,30,0,0);
                layoutFromTo.setOrientation(LinearLayout.HORIZONTAL);

                ImageView pbToLocation = new ImageView(this);
                LinearLayout.LayoutParams pbToLocationParams = new LinearLayout.LayoutParams(35, 35);
                pbToLocation.setLayoutParams(pbToLocationParams);
                pbToLocation.setPadding(0,0,0,0);
                pbToLocation.setImageResource(R.drawable.location_to);
                layoutFromTo.addView(pbToLocation);

                TextView lblToCity = new TextView(this);
                LinearLayout.LayoutParams lblToCityParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lblToCity.setLayoutParams(lblToCityParams);
                lblToCity.setTextSize(14);
                lblToCity.setTypeface(null, Typeface.BOLD);
                lblToCityParams.setMargins(15,0,0,0);
                lblToCity.setText("Nereye: " + ticket.getTicketDto().getExpeditionDto().getExpedition().getCityTo());
                lblToCity.setTextColor(getResources().getColor(R.color.gray_dark2));
                layoutFromTo.addView(lblToCity);

                linearLayout.addView(layoutFromTo);
                /* End To City Layout */

                /* Start Travel Date Layout */
                LinearLayout layoutTravelDate = new LinearLayout(this);
                LinearLayout.LayoutParams layoutTravelDateParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutTravelDate.setLayoutParams(layoutTravelDateParams);
                layoutTravelDateParams.setMargins(30,30,0,0);
                layoutTravelDate.setOrientation(LinearLayout.HORIZONTAL);

                ImageView pbDate = new ImageView(this);
                LinearLayout.LayoutParams pbDateParams = new LinearLayout.LayoutParams(35, 35);
                pbDate.setLayoutParams(pbDateParams);
                pbDate.setPadding(0,2,0,0);
                pbDate.setImageResource(R.drawable.calendar);
                layoutTravelDate.addView(pbDate);

                TextView lblTravelDate = new TextView(this);
                LinearLayout.LayoutParams lblTravelDateParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lblTravelDate.setLayoutParams(lblTravelDateParams);
                lblTravelDate.setTextSize(14);
                lblTravelDate.setTypeface(null, Typeface.BOLD);
                lblTravelDateParams.setMargins(15,0,0,0);
                lblTravelDate.setText("Tarih: " + ticket.getTicketDto().getTicket().getDepartureDate());
                lblTravelDate.setTextColor(getResources().getColor(R.color.gray_dark2));
                layoutTravelDate.addView(lblTravelDate);

                linearLayout.addView(layoutTravelDate);
                /* End Travel Date Layout */

                /* Start Travel Hour Layout */
                LinearLayout layoutTravelHour = new LinearLayout(this);
                LinearLayout.LayoutParams layoutTravelHourParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutTravelHour.setLayoutParams(layoutTravelHourParams);
                layoutTravelHourParams.setMargins(30,30,0,0);
                layoutTravelHour.setOrientation(LinearLayout.HORIZONTAL);

                ImageView pbHour = new ImageView(this);
                LinearLayout.LayoutParams pbHourParams = new LinearLayout.LayoutParams(35, 35);
                pbHour.setLayoutParams(pbHourParams);
                pbHour.setPadding(0,0,0,0);
                pbHour.setImageResource(R.drawable.clock_3);
                layoutTravelHour.addView(pbHour);

                TextView lblTravelHour = new TextView(this);
                LinearLayout.LayoutParams lblTravelHourParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lblTravelHour.setLayoutParams(lblTravelHourParams);
                lblTravelHour.setTextSize(14);
                lblTravelHour.setTypeface(null, Typeface.BOLD);
                lblTravelHourParams.setMargins(15,-2,0,0);
                lblTravelHour.setText("Saat: " + ticket.getTicketDto().getExpeditionDto().getExpedition().getDepartureTime());
                lblTravelHour.setTextColor(getResources().getColor(R.color.gray_dark2));
                layoutTravelHour.addView(lblTravelHour);

                linearLayout.addView(layoutTravelHour);
                /* End Travel Hour Layout */

                /* Start Travel Hour Layout */
                LinearLayout layoutGender = new LinearLayout(this);
                LinearLayout.LayoutParams layoutGenderParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutGender.setLayoutParams(layoutGenderParams);
                layoutGenderParams.setMargins(30,30,0,0);
                layoutGender.setOrientation(LinearLayout.HORIZONTAL);

                ImageView pbGender = new ImageView(this);
                LinearLayout.LayoutParams pbGenderParams = new LinearLayout.LayoutParams(35, 35);
                pbGender.setLayoutParams(pbGenderParams);
                pbGender.setPadding(0,0,0,0);
                pbGender.setImageResource(R.drawable.gender_2);
                layoutGender.addView(pbGender);

                TextView lblGender = new TextView(this);
                LinearLayout.LayoutParams lblGenderParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lblGender.setLayoutParams(lblGenderParams);
                lblGender.setTextSize(14);
                lblGender.setTypeface(null, Typeface.BOLD);
                lblGenderParams.setMargins(15,0,0,0);
                lblGender.setText("Cinsiyet: " + ticket.getTicketDto().getTicket().getGender());
                lblGender.setTextColor(getResources().getColor(R.color.gray_dark2));
                layoutGender.addView(lblGender);

                linearLayout.addView(layoutGender);
                /* End Travel Hour Layout */

                /* Start Seat Layout */
                LinearLayout layoutSeatNumber = new LinearLayout(this);
                LinearLayout.LayoutParams layoutSeatNumberParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutSeatNumber.setLayoutParams(layoutSeatNumberParams);
                layoutSeatNumberParams.setMargins(30,30,0,30);
                layoutSeatNumber.setOrientation(LinearLayout.HORIZONTAL);

                ImageView pbSeat = new ImageView(this);
                LinearLayout.LayoutParams pbSeatParams = new LinearLayout.LayoutParams(35, 35);
                pbSeat.setLayoutParams(pbSeatParams);
                pbSeatParams.setMargins(0,0,0,0);
                pbSeat.setImageResource(R.drawable.seat);
                layoutSeatNumber.addView(pbSeat);

                TextView lblSeatNumber = new TextView(this);
                LinearLayout.LayoutParams lblSeatNumberParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lblSeatNumber.setLayoutParams(lblSeatNumberParams);
                lblSeatNumber.setTextSize(14);
                lblSeatNumber.setTypeface(null, Typeface.BOLD);
                lblSeatNumberParams.setMargins(15,2,0,0);
                lblSeatNumber.setText("Kotuk: " + ticket.getTicketDto().getTicket().getSeatNumber());
                lblSeatNumber.setTextColor(getResources().getColor(R.color.gray_dark2));
                layoutSeatNumber.addView(lblSeatNumber);
                linearLayout.addView(layoutSeatNumber);

                Button btnTicketCancel = new Button(this);
                LinearLayout.LayoutParams btnTicketCancelParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 65);
                btnTicketCancel.setLayoutParams(btnTicketCancelParams);
                btnTicketCancel.setText("İptal et / Sil");
                btnTicketCancel.setTextSize(13);
                btnTicketCancel.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                btnTicketCancel.setGravity(Gravity.END);
                btnTicketCancelParams.setMargins(30,0,30,30);
                btnTicketCancel.setPadding(0,15,0,0);
                btnTicketCancel.setTextColor(getResources().getColor(R.color.white));
                btnTicketCancel.setBackgroundResource(R.drawable.ticket_cancel_button_design);
                btnTicketCancel.setAllCaps(false);
                btnTicketCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        btnYes = ticketCancelDialog.findViewById(R.id.btnCancelYes);
                        btnNo = ticketCancelDialog.findViewById(R.id.btnCancelNo);

                        btnYes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                databaseHelper.deleteTicket(ticket.getTicketDto().getTicket().getId());
                                Toast.makeText(getApplicationContext(),"Bilet başarılı bir şekilde iptal edildi", Toast.LENGTH_SHORT).show();
                                ticketCancelDialog.dismiss();
                                refreshActivity();
                            }
                        });
                        btnNo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ticketCancelDialog.dismiss();
                            }
                        });
                        ticketCancelDialog.show();
                    }
                });
                linearLayout.addView(btnTicketCancel);

                ticketsLayout.addView(linearLayout);

            }
        }
    }

    private void getTickets(List<TicketDtoAndUserDto> tickets){
        ticketsLayout.removeAllViews();
        if (tickets != null){
            for (TicketDtoAndUserDto ticket : tickets) {

                LinearLayout linearLayout = new LinearLayout(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(20,25,20,5);
                linearLayout.setLayoutParams(layoutParams);
                linearLayout.setBackgroundResource(R.drawable.my_ticket_box_background);
                linearLayout.setOrientation(LinearLayout.VERTICAL);

                /* Start Ticket Type Layout */
                LinearLayout layoutTicketType = new LinearLayout(this);
                LinearLayout.LayoutParams layoutTicketTypeParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutTicketType.setLayoutParams(layoutTicketTypeParams);
                layoutTicketTypeParams.setMargins(30,30,0,0);
                layoutTicketType.setOrientation(LinearLayout.HORIZONTAL);

                ImageView pbTicketType = new ImageView(this);
                LinearLayout.LayoutParams pbTicketTypeParams = new LinearLayout.LayoutParams(35, 35);
                pbTicketType.setLayoutParams(pbTicketTypeParams);
                pbTicketType.setPadding(0,3,0,0);
                pbTicketType.setImageResource(R.drawable.tickets_dark);
                layoutTicketType.addView(pbTicketType);

                TextView lblTicketType = new TextView(this);
                LinearLayout.LayoutParams lblTicketTypeParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lblTicketType.setLayoutParams(lblTicketTypeParams);
                lblTicketType.setTextSize(14);
                lblTicketType.setTypeface(null, Typeface.BOLD);
                lblTicketTypeParams.setMargins(15,0,0,0);
                lblTicketType.setText("Bilet Tipi: Normal Bilet");
                lblTicketType.setTextColor(getResources().getColor(R.color.gray_dark2));
                layoutTicketType.addView(lblTicketType);

                linearLayout.addView(layoutTicketType);

                /* Start Firstname And Lastname Layout */
                LinearLayout layoutUserFirstNameAndLastName = new LinearLayout(this);
                LinearLayout.LayoutParams layoutUserFirstNameAndLastNameParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutUserFirstNameAndLastName.setLayoutParams(layoutUserFirstNameAndLastNameParams);
                layoutUserFirstNameAndLastNameParams.setMargins(30,30,0,0);
                layoutUserFirstNameAndLastName.setOrientation(LinearLayout.HORIZONTAL);

                ImageView pbUser = new ImageView(this);
                LinearLayout.LayoutParams pbUserParams = new LinearLayout.LayoutParams(35, 35);
                pbUser.setLayoutParams(pbUserParams);
                pbUser.setPadding(0,3,0,0);
                pbUser.setImageResource(R.drawable.person_2);
                layoutUserFirstNameAndLastName.addView(pbUser);

                TextView lblUserFirstNameAndLastName = new TextView(this);
                LinearLayout.LayoutParams lblUserFirstNameAndLastNameParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lblUserFirstNameAndLastName.setLayoutParams(lblUserFirstNameAndLastNameParams);
                lblUserFirstNameAndLastName.setTextSize(14);
                lblUserFirstNameAndLastName.setTypeface(null, Typeface.BOLD);
                lblUserFirstNameAndLastNameParams.setMargins(15,0,0,0);
                lblUserFirstNameAndLastName.setText("Ad Soyad: " + ticket.getUser().getFirstName() + " " + ticket.getUser().getLastName());
                lblUserFirstNameAndLastName.setTextColor(getResources().getColor(R.color.gray_dark2));
                layoutUserFirstNameAndLastName.addView(lblUserFirstNameAndLastName);

                linearLayout.addView(layoutUserFirstNameAndLastName);

                /* Start Use Phone Number Layout */
                LinearLayout layoutUserPhoneNumber = new LinearLayout(this);
                LinearLayout.LayoutParams layoutUserPhoneNumberParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutUserPhoneNumber.setLayoutParams(layoutUserPhoneNumberParams);
                layoutUserPhoneNumberParams.setMargins(30,30,0,0);
                layoutUserPhoneNumber.setOrientation(LinearLayout.HORIZONTAL);

                ImageView pbPhone = new ImageView(this);
                LinearLayout.LayoutParams pbPhoneParams = new LinearLayout.LayoutParams(35, 35);
                pbPhone.setLayoutParams(pbPhoneParams);
                pbPhone.setPadding(0,3,0,0);
                pbPhone.setImageResource(R.drawable.telephone);
                layoutUserPhoneNumber.addView(pbPhone);

                TextView lblUserPhoneNumber = new TextView(this);
                LinearLayout.LayoutParams lblUserPhoneNumberParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lblUserPhoneNumber.setLayoutParams(lblUserPhoneNumberParams);
                lblUserPhoneNumber.setTextSize(14);
                lblUserPhoneNumber.setTypeface(null, Typeface.BOLD);
                lblUserPhoneNumberParams.setMargins(15,0,0,0);
                lblUserPhoneNumber.setText("Telefon Numarası: " + ticket.getUser().getPhoneNumber());
                lblUserPhoneNumber.setTextColor(getResources().getColor(R.color.gray_dark2));
                layoutUserPhoneNumber.addView(lblUserPhoneNumber);

                linearLayout.addView(layoutUserPhoneNumber);

                /* Start From City Layout */
                LinearLayout layoutCompany = new LinearLayout(this);
                LinearLayout.LayoutParams layoutCompanyParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutCompany.setLayoutParams(layoutCompanyParams);
                layoutCompanyParams.setMargins(30,30,0,0);
                layoutCompany.setOrientation(LinearLayout.HORIZONTAL);

                ImageView pbBus = new ImageView(this);
                LinearLayout.LayoutParams pbBusParams = new LinearLayout.LayoutParams(35, 35);
                pbBus.setLayoutParams(pbBusParams);
                pbBus.setPadding(0,3,0,0);
                pbBus.setImageResource(R.drawable.bus);
                layoutCompany.addView(pbBus);

                TextView lblCompany = new TextView(this);
                LinearLayout.LayoutParams lblCompanyParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lblCompany.setLayoutParams(lblCompanyParams);
                lblCompany.setTextSize(14);
                lblCompany.setTypeface(null, Typeface.BOLD);
                lblCompanyParams.setMargins(15,0,0,0);
                lblCompany.setText("Firma: " + ticket.getTicketDto().getExpeditionDto().getCompany().getCompanyName());
                lblCompany.setTextColor(getResources().getColor(R.color.gray_dark2));
                layoutCompany.addView(lblCompany);

                linearLayout.addView(layoutCompany);

                /* Start From City Layout */
                LinearLayout layoutFromCity = new LinearLayout(this);
                LinearLayout.LayoutParams layoutFromCityParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutFromCity.setLayoutParams(layoutFromCityParams);
                layoutFromCityParams.setMargins(30,30,0,0);
                layoutFromCity.setOrientation(LinearLayout.HORIZONTAL);

                ImageView pbFromLocation = new ImageView(this);
                LinearLayout.LayoutParams pbFromLocationParams = new LinearLayout.LayoutParams(35, 35);
                pbFromLocation.setLayoutParams(pbFromLocationParams);
                pbFromLocation.setPadding(0,0,0,0);
                pbFromLocation.setImageResource(R.drawable.location_from);
                layoutFromCity.addView(pbFromLocation);

                TextView lblFromCity = new TextView(this);
                LinearLayout.LayoutParams lblFromCityParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lblFromCity.setLayoutParams(lblFromCityParams);
                lblFromCity.setTextSize(14);
                lblFromCity.setTypeface(null, Typeface.BOLD);
                lblFromCityParams.setMargins(15,0,0,0);
                lblFromCity.setText("Nereden: " + ticket.getTicketDto().getExpeditionDto().getExpedition().getCityFrom());
                lblFromCity.setTextColor(getResources().getColor(R.color.gray_dark2));
                layoutFromCity.addView(lblFromCity);

                linearLayout.addView(layoutFromCity);
                /* End From City Layout */

                /* Start To City Layout */
                LinearLayout layoutFromTo = new LinearLayout(this);
                LinearLayout.LayoutParams layoutToCityParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutFromTo.setLayoutParams(layoutToCityParams);
                layoutToCityParams.setMargins(30,30,0,0);
                layoutFromTo.setOrientation(LinearLayout.HORIZONTAL);

                ImageView pbToLocation = new ImageView(this);
                LinearLayout.LayoutParams pbToLocationParams = new LinearLayout.LayoutParams(35, 35);
                pbToLocation.setLayoutParams(pbToLocationParams);
                pbToLocation.setPadding(0,0,0,0);
                pbToLocation.setImageResource(R.drawable.location_to);
                layoutFromTo.addView(pbToLocation);

                TextView lblToCity = new TextView(this);
                LinearLayout.LayoutParams lblToCityParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lblToCity.setLayoutParams(lblToCityParams);
                lblToCity.setTextSize(14);
                lblToCity.setTypeface(null, Typeface.BOLD);
                lblToCityParams.setMargins(15,0,0,0);
                lblToCity.setText("Nereye: " + ticket.getTicketDto().getExpeditionDto().getExpedition().getCityTo());
                lblToCity.setTextColor(getResources().getColor(R.color.gray_dark2));
                layoutFromTo.addView(lblToCity);

                linearLayout.addView(layoutFromTo);
                /* End To City Layout */

                /* Start Travel Date Layout */
                LinearLayout layoutTravelDate = new LinearLayout(this);
                LinearLayout.LayoutParams layoutTravelDateParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutTravelDate.setLayoutParams(layoutTravelDateParams);
                layoutTravelDateParams.setMargins(30,30,0,0);
                layoutTravelDate.setOrientation(LinearLayout.HORIZONTAL);

                ImageView pbDate = new ImageView(this);
                LinearLayout.LayoutParams pbDateParams = new LinearLayout.LayoutParams(35, 35);
                pbDate.setLayoutParams(pbDateParams);
                pbDate.setPadding(0,2,0,0);
                pbDate.setImageResource(R.drawable.calendar);
                layoutTravelDate.addView(pbDate);

                TextView lblTravelDate = new TextView(this);
                LinearLayout.LayoutParams lblTravelDateParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lblTravelDate.setLayoutParams(lblTravelDateParams);
                lblTravelDate.setTextSize(14);
                lblTravelDate.setTypeface(null, Typeface.BOLD);
                lblTravelDateParams.setMargins(15,0,0,0);
                lblTravelDate.setText("Tarih: " + ticket.getTicketDto().getTicket().getDepartureDate());
                lblTravelDate.setTextColor(getResources().getColor(R.color.gray_dark2));
                layoutTravelDate.addView(lblTravelDate);

                linearLayout.addView(layoutTravelDate);
                /* End Travel Date Layout */

                /* Start Travel Hour Layout */
                LinearLayout layoutTravelHour = new LinearLayout(this);
                LinearLayout.LayoutParams layoutTravelHourParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutTravelHour.setLayoutParams(layoutTravelHourParams);
                layoutTravelHourParams.setMargins(30,30,0,0);
                layoutTravelHour.setOrientation(LinearLayout.HORIZONTAL);

                ImageView pbHour = new ImageView(this);
                LinearLayout.LayoutParams pbHourParams = new LinearLayout.LayoutParams(35, 35);
                pbHour.setLayoutParams(pbHourParams);
                pbHour.setPadding(0,0,0,0);
                pbHour.setImageResource(R.drawable.clock_3);
                layoutTravelHour.addView(pbHour);

                TextView lblTravelHour = new TextView(this);
                LinearLayout.LayoutParams lblTravelHourParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lblTravelHour.setLayoutParams(lblTravelHourParams);
                lblTravelHour.setTextSize(14);
                lblTravelHour.setTypeface(null, Typeface.BOLD);
                lblTravelHourParams.setMargins(15,-2,0,0);
                lblTravelHour.setText("Saat: " + ticket.getTicketDto().getExpeditionDto().getExpedition().getDepartureTime());
                lblTravelHour.setTextColor(getResources().getColor(R.color.gray_dark2));
                layoutTravelHour.addView(lblTravelHour);

                linearLayout.addView(layoutTravelHour);
                /* End Travel Hour Layout */

                /* Start Travel Hour Layout */
                LinearLayout layoutGender = new LinearLayout(this);
                LinearLayout.LayoutParams layoutGenderParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutGender.setLayoutParams(layoutGenderParams);
                layoutGenderParams.setMargins(30,30,0,0);
                layoutGender.setOrientation(LinearLayout.HORIZONTAL);

                ImageView pbGender = new ImageView(this);
                LinearLayout.LayoutParams pbGenderParams = new LinearLayout.LayoutParams(35, 35);
                pbGender.setLayoutParams(pbGenderParams);
                pbGender.setPadding(0,0,0,0);
                pbGender.setImageResource(R.drawable.gender_2);
                layoutGender.addView(pbGender);

                TextView lblGender = new TextView(this);
                LinearLayout.LayoutParams lblGenderParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lblGender.setLayoutParams(lblGenderParams);
                lblGender.setTextSize(14);
                lblGender.setTypeface(null, Typeface.BOLD);
                lblGenderParams.setMargins(15,0,0,0);
                lblGender.setText("Cinsiyet: " + ticket.getTicketDto().getTicket().getGender());
                lblGender.setTextColor(getResources().getColor(R.color.gray_dark2));
                layoutGender.addView(lblGender);

                linearLayout.addView(layoutGender);
                /* End Travel Hour Layout */

                /* Start Seat Layout */
                LinearLayout layoutSeatNumber = new LinearLayout(this);
                LinearLayout.LayoutParams layoutSeatNumberParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutSeatNumber.setLayoutParams(layoutSeatNumberParams);
                layoutSeatNumberParams.setMargins(30,30,0,30);
                layoutSeatNumber.setOrientation(LinearLayout.HORIZONTAL);

                ImageView pbSeat = new ImageView(this);
                LinearLayout.LayoutParams pbSeatParams = new LinearLayout.LayoutParams(35, 35);
                pbSeat.setLayoutParams(pbSeatParams);
                pbSeatParams.setMargins(0,0,0,0);
                pbSeat.setImageResource(R.drawable.seat);
                layoutSeatNumber.addView(pbSeat);

                TextView lblSeatNumber = new TextView(this);
                LinearLayout.LayoutParams lblSeatNumberParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lblSeatNumber.setLayoutParams(lblSeatNumberParams);
                lblSeatNumber.setTextSize(14);
                lblSeatNumber.setTypeface(null, Typeface.BOLD);
                lblSeatNumberParams.setMargins(15,2,0,0);
                lblSeatNumber.setText("Kotuk: " + ticket.getTicketDto().getTicket().getSeatNumber());
                lblSeatNumber.setTextColor(getResources().getColor(R.color.gray_dark2));
                layoutSeatNumber.addView(lblSeatNumber);
                linearLayout.addView(layoutSeatNumber);

                Button btnTicketCancel = new Button(this);
                LinearLayout.LayoutParams btnTicketCancelParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 65);
                btnTicketCancel.setLayoutParams(btnTicketCancelParams);
                btnTicketCancel.setText("İptal et / Sil");
                btnTicketCancel.setTextSize(13);
                btnTicketCancel.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                btnTicketCancel.setGravity(Gravity.END);
                btnTicketCancelParams.setMargins(30,0,30,30);
                btnTicketCancel.setPadding(0,15,0,0);
                btnTicketCancel.setTextColor(getResources().getColor(R.color.white));
                btnTicketCancel.setBackgroundResource(R.drawable.ticket_cancel_button_design);
                btnTicketCancel.setAllCaps(false);
                btnTicketCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        btnYes = ticketCancelDialog.findViewById(R.id.btnCancelYes);
                        btnNo = ticketCancelDialog.findViewById(R.id.btnCancelNo);

                        btnYes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                databaseHelper.deleteTicket(ticket.getTicketDto().getTicket().getId());
                                Toast.makeText(getApplicationContext(),"Bilet başarılı bir şekilde iptal edildi", Toast.LENGTH_SHORT).show();
                                ticketCancelDialog.dismiss();
                                refreshActivity();
                            }
                        });
                        btnNo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ticketCancelDialog.dismiss();
                            }
                        });
                        ticketCancelDialog.show();
                    }
                });
                linearLayout.addView(btnTicketCancel);

                ticketsLayout.addView(linearLayout);

            }
        }
    }

    private void getReservations(){
        List<TicketReservationDto> tickets = databaseHelper.getTicketReservationsDto();

        if (tickets.size() > 0){
            for (TicketReservationDto ticket : tickets) {

                LinearLayout linearLayout = new LinearLayout(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(20,25,20,5);
                linearLayout.setLayoutParams(layoutParams);
                linearLayout.setBackgroundResource(R.drawable.my_ticket_box_background);
                linearLayout.setOrientation(LinearLayout.VERTICAL);

                /* Start Ticket Type Layout */
                LinearLayout layoutTicketType = new LinearLayout(this);
                LinearLayout.LayoutParams layoutTicketTypeParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutTicketType.setLayoutParams(layoutTicketTypeParams);
                layoutTicketTypeParams.setMargins(30,30,0,0);
                layoutTicketType.setOrientation(LinearLayout.HORIZONTAL);

                ImageView pbTicketType = new ImageView(this);
                LinearLayout.LayoutParams pbTicketTypeParams = new LinearLayout.LayoutParams(35, 35);
                pbTicketType.setLayoutParams(pbTicketTypeParams);
                pbTicketType.setPadding(0,3,0,0);
                pbTicketType.setImageResource(R.drawable.tickets_dark);
                layoutTicketType.addView(pbTicketType);

                TextView lblTicketType = new TextView(this);
                LinearLayout.LayoutParams lblTicketTypeParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lblTicketType.setLayoutParams(lblTicketTypeParams);
                lblTicketType.setTextSize(14);
                lblTicketType.setTypeface(null, Typeface.BOLD);
                lblTicketTypeParams.setMargins(15,0,0,0);
                lblTicketType.setText("Bilet Tipi: Rezervasyon Bilet");
                lblTicketType.setTextColor(getResources().getColor(R.color.gray_dark2));
                layoutTicketType.addView(lblTicketType);

                linearLayout.addView(layoutTicketType);

                /* Start Firstname And Lastname Layout */
                LinearLayout layoutUserFirstNameAndLastName = new LinearLayout(this);
                LinearLayout.LayoutParams layoutUserFirstNameAndLastNameParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutUserFirstNameAndLastName.setLayoutParams(layoutUserFirstNameAndLastNameParams);
                layoutUserFirstNameAndLastNameParams.setMargins(30,30,0,0);
                layoutUserFirstNameAndLastName.setOrientation(LinearLayout.HORIZONTAL);

                ImageView pbUser = new ImageView(this);
                LinearLayout.LayoutParams pbUserParams = new LinearLayout.LayoutParams(35, 35);
                pbUser.setLayoutParams(pbUserParams);
                pbUser.setPadding(0,3,0,0);
                pbUser.setImageResource(R.drawable.person_2);
                layoutUserFirstNameAndLastName.addView(pbUser);

                TextView lblUserFirstNameAndLastName = new TextView(this);
                LinearLayout.LayoutParams lblUserFirstNameAndLastNameParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lblUserFirstNameAndLastName.setLayoutParams(lblUserFirstNameAndLastNameParams);
                lblUserFirstNameAndLastName.setTextSize(14);
                lblUserFirstNameAndLastName.setTypeface(null, Typeface.BOLD);
                lblUserFirstNameAndLastNameParams.setMargins(15,0,0,0);
                lblUserFirstNameAndLastName.setText("Ad Soyad: " + ticket.getTicketReservation().getFirstName() + " " + ticket.getTicketReservation().getLastName());
                lblUserFirstNameAndLastName.setTextColor(getResources().getColor(R.color.gray_dark2));
                layoutUserFirstNameAndLastName.addView(lblUserFirstNameAndLastName);

                linearLayout.addView(layoutUserFirstNameAndLastName);

                /* Start Use Phone Number Layout */
                LinearLayout layoutUserPhoneNumber = new LinearLayout(this);
                LinearLayout.LayoutParams layoutUserPhoneNumberParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutUserPhoneNumber.setLayoutParams(layoutUserPhoneNumberParams);
                layoutUserPhoneNumberParams.setMargins(30,30,0,0);
                layoutUserPhoneNumber.setOrientation(LinearLayout.HORIZONTAL);

                ImageView pbPhone = new ImageView(this);
                LinearLayout.LayoutParams pbPhoneParams = new LinearLayout.LayoutParams(35, 35);
                pbPhone.setLayoutParams(pbPhoneParams);
                pbPhone.setPadding(0,3,0,0);
                pbPhone.setImageResource(R.drawable.telephone);
                layoutUserPhoneNumber.addView(pbPhone);

                TextView lblUserPhoneNumber = new TextView(this);
                LinearLayout.LayoutParams lblUserPhoneNumberParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lblUserPhoneNumber.setLayoutParams(lblUserPhoneNumberParams);
                lblUserPhoneNumber.setTextSize(14);
                lblUserPhoneNumber.setTypeface(null, Typeface.BOLD);
                lblUserPhoneNumberParams.setMargins(15,0,0,0);
                lblUserPhoneNumber.setText("Telefon Numarası: " + ticket.getTicketReservation().getPhoneNumber());
                lblUserPhoneNumber.setTextColor(getResources().getColor(R.color.gray_dark2));
                layoutUserPhoneNumber.addView(lblUserPhoneNumber);

                linearLayout.addView(layoutUserPhoneNumber);

                /* Start From City Layout */
                LinearLayout layoutCompany = new LinearLayout(this);
                LinearLayout.LayoutParams layoutCompanyParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutCompany.setLayoutParams(layoutCompanyParams);
                layoutCompanyParams.setMargins(30,30,0,0);
                layoutCompany.setOrientation(LinearLayout.HORIZONTAL);

                ImageView pbBus = new ImageView(this);
                LinearLayout.LayoutParams pbBusParams = new LinearLayout.LayoutParams(35, 35);
                pbBus.setLayoutParams(pbBusParams);
                pbBus.setPadding(0,3,0,0);
                pbBus.setImageResource(R.drawable.bus);
                layoutCompany.addView(pbBus);

                TextView lblCompany = new TextView(this);
                LinearLayout.LayoutParams lblCompanyParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lblCompany.setLayoutParams(lblCompanyParams);
                lblCompany.setTextSize(14);
                lblCompany.setTypeface(null, Typeface.BOLD);
                lblCompanyParams.setMargins(15,0,0,0);
                lblCompany.setText("Firma: " + ticket.getExpeditionDto().getCompany().getCompanyName());
                lblCompany.setTextColor(getResources().getColor(R.color.gray_dark2));
                layoutCompany.addView(lblCompany);

                linearLayout.addView(layoutCompany);

                /* Start From City Layout */
                LinearLayout layoutFromCity = new LinearLayout(this);
                LinearLayout.LayoutParams layoutFromCityParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutFromCity.setLayoutParams(layoutFromCityParams);
                layoutFromCityParams.setMargins(30,30,0,0);
                layoutFromCity.setOrientation(LinearLayout.HORIZONTAL);

                ImageView pbFromLocation = new ImageView(this);
                LinearLayout.LayoutParams pbFromLocationParams = new LinearLayout.LayoutParams(35, 35);
                pbFromLocation.setLayoutParams(pbFromLocationParams);
                pbFromLocation.setPadding(0,0,0,0);
                pbFromLocation.setImageResource(R.drawable.location_from);
                layoutFromCity.addView(pbFromLocation);

                TextView lblFromCity = new TextView(this);
                LinearLayout.LayoutParams lblFromCityParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lblFromCity.setLayoutParams(lblFromCityParams);
                lblFromCity.setTextSize(14);
                lblFromCity.setTypeface(null, Typeface.BOLD);
                lblFromCityParams.setMargins(15,0,0,0);
                lblFromCity.setText("Nereden: " + ticket.getExpeditionDto().getExpedition().getCityFrom());
                lblFromCity.setTextColor(getResources().getColor(R.color.gray_dark2));
                layoutFromCity.addView(lblFromCity);

                linearLayout.addView(layoutFromCity);
                /* End From City Layout */

                /* Start To City Layout */
                LinearLayout layoutFromTo = new LinearLayout(this);
                LinearLayout.LayoutParams layoutToCityParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutFromTo.setLayoutParams(layoutToCityParams);
                layoutToCityParams.setMargins(30,30,0,0);
                layoutFromTo.setOrientation(LinearLayout.HORIZONTAL);

                ImageView pbToLocation = new ImageView(this);
                LinearLayout.LayoutParams pbToLocationParams = new LinearLayout.LayoutParams(35, 35);
                pbToLocation.setLayoutParams(pbToLocationParams);
                pbToLocation.setPadding(0,0,0,0);
                pbToLocation.setImageResource(R.drawable.location_to);
                layoutFromTo.addView(pbToLocation);

                TextView lblToCity = new TextView(this);
                LinearLayout.LayoutParams lblToCityParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lblToCity.setLayoutParams(lblToCityParams);
                lblToCity.setTextSize(14);
                lblToCity.setTypeface(null, Typeface.BOLD);
                lblToCityParams.setMargins(15,0,0,0);
                lblToCity.setText("Nereye: " + ticket.getExpeditionDto().getExpedition().getCityTo());
                lblToCity.setTextColor(getResources().getColor(R.color.gray_dark2));
                layoutFromTo.addView(lblToCity);

                linearLayout.addView(layoutFromTo);
                /* End To City Layout */

                /* Start Travel Date Layout */
                LinearLayout layoutTravelDate = new LinearLayout(this);
                LinearLayout.LayoutParams layoutTravelDateParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutTravelDate.setLayoutParams(layoutTravelDateParams);
                layoutTravelDateParams.setMargins(30,30,0,0);
                layoutTravelDate.setOrientation(LinearLayout.HORIZONTAL);

                ImageView pbDate = new ImageView(this);
                LinearLayout.LayoutParams pbDateParams = new LinearLayout.LayoutParams(35, 35);
                pbDate.setLayoutParams(pbDateParams);
                pbDate.setPadding(0,2,0,0);
                pbDate.setImageResource(R.drawable.calendar);
                layoutTravelDate.addView(pbDate);

                TextView lblTravelDate = new TextView(this);
                LinearLayout.LayoutParams lblTravelDateParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lblTravelDate.setLayoutParams(lblTravelDateParams);
                lblTravelDate.setTextSize(14);
                lblTravelDate.setTypeface(null, Typeface.BOLD);
                lblTravelDateParams.setMargins(15,0,0,0);
                lblTravelDate.setText("Tarih: " + ticket.getTicketReservation().getDepartureDate());
                lblTravelDate.setTextColor(getResources().getColor(R.color.gray_dark2));
                layoutTravelDate.addView(lblTravelDate);

                linearLayout.addView(layoutTravelDate);
                /* End Travel Date Layout */

                /* Start Travel Hour Layout */
                LinearLayout layoutTravelHour = new LinearLayout(this);
                LinearLayout.LayoutParams layoutTravelHourParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutTravelHour.setLayoutParams(layoutTravelHourParams);
                layoutTravelHourParams.setMargins(30,30,0,0);
                layoutTravelHour.setOrientation(LinearLayout.HORIZONTAL);

                ImageView pbHour = new ImageView(this);
                LinearLayout.LayoutParams pbHourParams = new LinearLayout.LayoutParams(35, 35);
                pbHour.setLayoutParams(pbHourParams);
                pbHour.setPadding(0,0,0,0);
                pbHour.setImageResource(R.drawable.clock_3);
                layoutTravelHour.addView(pbHour);

                TextView lblTravelHour = new TextView(this);
                LinearLayout.LayoutParams lblTravelHourParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lblTravelHour.setLayoutParams(lblTravelHourParams);
                lblTravelHour.setTextSize(14);
                lblTravelHour.setTypeface(null, Typeface.BOLD);
                lblTravelHourParams.setMargins(15,-2,0,0);
                lblTravelHour.setText("Saat: " + ticket.getExpeditionDto().getExpedition().getDepartureTime());
                lblTravelHour.setTextColor(getResources().getColor(R.color.gray_dark2));
                layoutTravelHour.addView(lblTravelHour);

                linearLayout.addView(layoutTravelHour);
                /* End Travel Hour Layout */

                /* Start Travel Hour Layout */
                LinearLayout layoutGender = new LinearLayout(this);
                LinearLayout.LayoutParams layoutGenderParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutGender.setLayoutParams(layoutGenderParams);
                layoutGenderParams.setMargins(30,30,0,0);
                layoutGender.setOrientation(LinearLayout.HORIZONTAL);

                ImageView pbGender = new ImageView(this);
                LinearLayout.LayoutParams pbGenderParams = new LinearLayout.LayoutParams(35, 35);
                pbGender.setLayoutParams(pbGenderParams);
                pbGender.setPadding(0,0,0,0);
                pbGender.setImageResource(R.drawable.gender_2);
                layoutGender.addView(pbGender);

                TextView lblGender = new TextView(this);
                LinearLayout.LayoutParams lblGenderParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lblGender.setLayoutParams(lblGenderParams);
                lblGender.setTextSize(14);
                lblGender.setTypeface(null, Typeface.BOLD);
                lblGenderParams.setMargins(15,0,0,0);
                lblGender.setText("Cinsiyet: " + ticket.getTicketReservation().getGender());
                lblGender.setTextColor(getResources().getColor(R.color.gray_dark2));
                layoutGender.addView(lblGender);

                linearLayout.addView(layoutGender);
                /* End Travel Hour Layout */

                /* Start Seat Layout */
                LinearLayout layoutSeatNumber = new LinearLayout(this);
                LinearLayout.LayoutParams layoutSeatNumberParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutSeatNumber.setLayoutParams(layoutSeatNumberParams);
                layoutSeatNumberParams.setMargins(30,30,0,30);
                layoutSeatNumber.setOrientation(LinearLayout.HORIZONTAL);

                ImageView pbSeat = new ImageView(this);
                LinearLayout.LayoutParams pbSeatParams = new LinearLayout.LayoutParams(35, 35);
                pbSeat.setLayoutParams(pbSeatParams);
                pbSeatParams.setMargins(0,0,0,0);
                pbSeat.setImageResource(R.drawable.seat);
                layoutSeatNumber.addView(pbSeat);

                TextView lblSeatNumber = new TextView(this);
                LinearLayout.LayoutParams lblSeatNumberParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lblSeatNumber.setLayoutParams(lblSeatNumberParams);
                lblSeatNumber.setTextSize(14);
                lblSeatNumber.setTypeface(null, Typeface.BOLD);
                lblSeatNumberParams.setMargins(15,2,0,0);
                lblSeatNumber.setText("Kotuk: " + ticket.getTicketReservation().getSeatNumber());
                lblSeatNumber.setTextColor(getResources().getColor(R.color.gray_dark2));
                layoutSeatNumber.addView(lblSeatNumber);
                linearLayout.addView(layoutSeatNumber);

                Button btnTicketCancel = new Button(this);
                LinearLayout.LayoutParams btnTicketCancelParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 65);
                btnTicketCancel.setLayoutParams(btnTicketCancelParams);
                btnTicketCancel.setText("İptal et / Sil");
                btnTicketCancel.setTextSize(13);
                btnTicketCancel.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                btnTicketCancel.setGravity(Gravity.END);
                btnTicketCancelParams.setMargins(30,0,30,30);
                btnTicketCancel.setPadding(0,15,0,0);
                btnTicketCancel.setTextColor(getResources().getColor(R.color.white));
                btnTicketCancel.setBackgroundResource(R.drawable.ticket_cancel_button_design);
                btnTicketCancel.setAllCaps(false);
                btnTicketCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        btnYes = ticketCancelDialog.findViewById(R.id.btnCancelYes);
                        btnNo = ticketCancelDialog.findViewById(R.id.btnCancelNo);

                        btnYes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                databaseHelper.deleteTicketReservation(ticket.getTicketReservation().getId());
                                Toast.makeText(getApplicationContext(),"Bilet rezervasyonu başarılı bir şekilde iptal edildi", Toast.LENGTH_SHORT).show();
                                ticketCancelDialog.dismiss();
                                refreshActivity();
                            }
                        });
                        btnNo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ticketCancelDialog.dismiss();
                            }
                        });
                        ticketCancelDialog.show();
                    }
                });
                linearLayout.addView(btnTicketCancel);

                ticketsLayout.addView(linearLayout);

            }
        }
    }

    private void getReservations(List<TicketReservationDto> tickets){

        if (tickets.size() > 0){
            for (TicketReservationDto ticket : tickets) {

                LinearLayout linearLayout = new LinearLayout(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(20,25,20,5);
                linearLayout.setLayoutParams(layoutParams);
                linearLayout.setBackgroundResource(R.drawable.my_ticket_box_background);
                linearLayout.setOrientation(LinearLayout.VERTICAL);

                /* Start Ticket Type Layout */
                LinearLayout layoutTicketType = new LinearLayout(this);
                LinearLayout.LayoutParams layoutTicketTypeParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutTicketType.setLayoutParams(layoutTicketTypeParams);
                layoutTicketTypeParams.setMargins(30,30,0,0);
                layoutTicketType.setOrientation(LinearLayout.HORIZONTAL);

                ImageView pbTicketType = new ImageView(this);
                LinearLayout.LayoutParams pbTicketTypeParams = new LinearLayout.LayoutParams(35, 35);
                pbTicketType.setLayoutParams(pbTicketTypeParams);
                pbTicketType.setPadding(0,3,0,0);
                pbTicketType.setImageResource(R.drawable.tickets_dark);
                layoutTicketType.addView(pbTicketType);

                TextView lblTicketType = new TextView(this);
                LinearLayout.LayoutParams lblTicketTypeParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lblTicketType.setLayoutParams(lblTicketTypeParams);
                lblTicketType.setTextSize(14);
                lblTicketType.setTypeface(null, Typeface.BOLD);
                lblTicketTypeParams.setMargins(15,0,0,0);
                lblTicketType.setText("Bilet Tipi: Rezervasyon Bilet");
                lblTicketType.setTextColor(getResources().getColor(R.color.gray_dark2));
                layoutTicketType.addView(lblTicketType);

                linearLayout.addView(layoutTicketType);

                /* Start Firstname And Lastname Layout */
                LinearLayout layoutUserFirstNameAndLastName = new LinearLayout(this);
                LinearLayout.LayoutParams layoutUserFirstNameAndLastNameParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutUserFirstNameAndLastName.setLayoutParams(layoutUserFirstNameAndLastNameParams);
                layoutUserFirstNameAndLastNameParams.setMargins(30,30,0,0);
                layoutUserFirstNameAndLastName.setOrientation(LinearLayout.HORIZONTAL);

                ImageView pbUser = new ImageView(this);
                LinearLayout.LayoutParams pbUserParams = new LinearLayout.LayoutParams(35, 35);
                pbUser.setLayoutParams(pbUserParams);
                pbUser.setPadding(0,3,0,0);
                pbUser.setImageResource(R.drawable.person_2);
                layoutUserFirstNameAndLastName.addView(pbUser);

                TextView lblUserFirstNameAndLastName = new TextView(this);
                LinearLayout.LayoutParams lblUserFirstNameAndLastNameParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lblUserFirstNameAndLastName.setLayoutParams(lblUserFirstNameAndLastNameParams);
                lblUserFirstNameAndLastName.setTextSize(14);
                lblUserFirstNameAndLastName.setTypeface(null, Typeface.BOLD);
                lblUserFirstNameAndLastNameParams.setMargins(15,0,0,0);
                lblUserFirstNameAndLastName.setText("Ad Soyad: " + ticket.getTicketReservation().getFirstName() + " " + ticket.getTicketReservation().getLastName());
                lblUserFirstNameAndLastName.setTextColor(getResources().getColor(R.color.gray_dark2));
                layoutUserFirstNameAndLastName.addView(lblUserFirstNameAndLastName);

                linearLayout.addView(layoutUserFirstNameAndLastName);

                /* Start Use Phone Number Layout */
                LinearLayout layoutUserPhoneNumber = new LinearLayout(this);
                LinearLayout.LayoutParams layoutUserPhoneNumberParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutUserPhoneNumber.setLayoutParams(layoutUserPhoneNumberParams);
                layoutUserPhoneNumberParams.setMargins(30,30,0,0);
                layoutUserPhoneNumber.setOrientation(LinearLayout.HORIZONTAL);

                ImageView pbPhone = new ImageView(this);
                LinearLayout.LayoutParams pbPhoneParams = new LinearLayout.LayoutParams(35, 35);
                pbPhone.setLayoutParams(pbPhoneParams);
                pbPhone.setPadding(0,3,0,0);
                pbPhone.setImageResource(R.drawable.telephone);
                layoutUserPhoneNumber.addView(pbPhone);

                TextView lblUserPhoneNumber = new TextView(this);
                LinearLayout.LayoutParams lblUserPhoneNumberParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lblUserPhoneNumber.setLayoutParams(lblUserPhoneNumberParams);
                lblUserPhoneNumber.setTextSize(14);
                lblUserPhoneNumber.setTypeface(null, Typeface.BOLD);
                lblUserPhoneNumberParams.setMargins(15,0,0,0);
                lblUserPhoneNumber.setText("Telefon Numarası: " + ticket.getTicketReservation().getPhoneNumber());
                lblUserPhoneNumber.setTextColor(getResources().getColor(R.color.gray_dark2));
                layoutUserPhoneNumber.addView(lblUserPhoneNumber);

                linearLayout.addView(layoutUserPhoneNumber);

                /* Start From City Layout */
                LinearLayout layoutCompany = new LinearLayout(this);
                LinearLayout.LayoutParams layoutCompanyParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutCompany.setLayoutParams(layoutCompanyParams);
                layoutCompanyParams.setMargins(30,30,0,0);
                layoutCompany.setOrientation(LinearLayout.HORIZONTAL);

                ImageView pbBus = new ImageView(this);
                LinearLayout.LayoutParams pbBusParams = new LinearLayout.LayoutParams(35, 35);
                pbBus.setLayoutParams(pbBusParams);
                pbBus.setPadding(0,3,0,0);
                pbBus.setImageResource(R.drawable.bus);
                layoutCompany.addView(pbBus);

                TextView lblCompany = new TextView(this);
                LinearLayout.LayoutParams lblCompanyParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lblCompany.setLayoutParams(lblCompanyParams);
                lblCompany.setTextSize(14);
                lblCompany.setTypeface(null, Typeface.BOLD);
                lblCompanyParams.setMargins(15,0,0,0);
                lblCompany.setText("Firma: " + ticket.getExpeditionDto().getCompany().getCompanyName());
                lblCompany.setTextColor(getResources().getColor(R.color.gray_dark2));
                layoutCompany.addView(lblCompany);

                linearLayout.addView(layoutCompany);

                /* Start From City Layout */
                LinearLayout layoutFromCity = new LinearLayout(this);
                LinearLayout.LayoutParams layoutFromCityParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutFromCity.setLayoutParams(layoutFromCityParams);
                layoutFromCityParams.setMargins(30,30,0,0);
                layoutFromCity.setOrientation(LinearLayout.HORIZONTAL);

                ImageView pbFromLocation = new ImageView(this);
                LinearLayout.LayoutParams pbFromLocationParams = new LinearLayout.LayoutParams(35, 35);
                pbFromLocation.setLayoutParams(pbFromLocationParams);
                pbFromLocation.setPadding(0,0,0,0);
                pbFromLocation.setImageResource(R.drawable.location_from);
                layoutFromCity.addView(pbFromLocation);

                TextView lblFromCity = new TextView(this);
                LinearLayout.LayoutParams lblFromCityParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lblFromCity.setLayoutParams(lblFromCityParams);
                lblFromCity.setTextSize(14);
                lblFromCity.setTypeface(null, Typeface.BOLD);
                lblFromCityParams.setMargins(15,0,0,0);
                lblFromCity.setText("Nereden: " + ticket.getExpeditionDto().getExpedition().getCityFrom());
                lblFromCity.setTextColor(getResources().getColor(R.color.gray_dark2));
                layoutFromCity.addView(lblFromCity);

                linearLayout.addView(layoutFromCity);
                /* End From City Layout */

                /* Start To City Layout */
                LinearLayout layoutFromTo = new LinearLayout(this);
                LinearLayout.LayoutParams layoutToCityParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutFromTo.setLayoutParams(layoutToCityParams);
                layoutToCityParams.setMargins(30,30,0,0);
                layoutFromTo.setOrientation(LinearLayout.HORIZONTAL);

                ImageView pbToLocation = new ImageView(this);
                LinearLayout.LayoutParams pbToLocationParams = new LinearLayout.LayoutParams(35, 35);
                pbToLocation.setLayoutParams(pbToLocationParams);
                pbToLocation.setPadding(0,0,0,0);
                pbToLocation.setImageResource(R.drawable.location_to);
                layoutFromTo.addView(pbToLocation);

                TextView lblToCity = new TextView(this);
                LinearLayout.LayoutParams lblToCityParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lblToCity.setLayoutParams(lblToCityParams);
                lblToCity.setTextSize(14);
                lblToCity.setTypeface(null, Typeface.BOLD);
                lblToCityParams.setMargins(15,0,0,0);
                lblToCity.setText("Nereye: " + ticket.getExpeditionDto().getExpedition().getCityTo());
                lblToCity.setTextColor(getResources().getColor(R.color.gray_dark2));
                layoutFromTo.addView(lblToCity);

                linearLayout.addView(layoutFromTo);
                /* End To City Layout */

                /* Start Travel Date Layout */
                LinearLayout layoutTravelDate = new LinearLayout(this);
                LinearLayout.LayoutParams layoutTravelDateParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutTravelDate.setLayoutParams(layoutTravelDateParams);
                layoutTravelDateParams.setMargins(30,30,0,0);
                layoutTravelDate.setOrientation(LinearLayout.HORIZONTAL);

                ImageView pbDate = new ImageView(this);
                LinearLayout.LayoutParams pbDateParams = new LinearLayout.LayoutParams(35, 35);
                pbDate.setLayoutParams(pbDateParams);
                pbDate.setPadding(0,2,0,0);
                pbDate.setImageResource(R.drawable.calendar);
                layoutTravelDate.addView(pbDate);

                TextView lblTravelDate = new TextView(this);
                LinearLayout.LayoutParams lblTravelDateParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lblTravelDate.setLayoutParams(lblTravelDateParams);
                lblTravelDate.setTextSize(14);
                lblTravelDate.setTypeface(null, Typeface.BOLD);
                lblTravelDateParams.setMargins(15,0,0,0);
                lblTravelDate.setText("Tarih: " + ticket.getTicketReservation().getDepartureDate());
                lblTravelDate.setTextColor(getResources().getColor(R.color.gray_dark2));
                layoutTravelDate.addView(lblTravelDate);

                linearLayout.addView(layoutTravelDate);
                /* End Travel Date Layout */

                /* Start Travel Hour Layout */
                LinearLayout layoutTravelHour = new LinearLayout(this);
                LinearLayout.LayoutParams layoutTravelHourParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutTravelHour.setLayoutParams(layoutTravelHourParams);
                layoutTravelHourParams.setMargins(30,30,0,0);
                layoutTravelHour.setOrientation(LinearLayout.HORIZONTAL);

                ImageView pbHour = new ImageView(this);
                LinearLayout.LayoutParams pbHourParams = new LinearLayout.LayoutParams(35, 35);
                pbHour.setLayoutParams(pbHourParams);
                pbHour.setPadding(0,0,0,0);
                pbHour.setImageResource(R.drawable.clock_3);
                layoutTravelHour.addView(pbHour);

                TextView lblTravelHour = new TextView(this);
                LinearLayout.LayoutParams lblTravelHourParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lblTravelHour.setLayoutParams(lblTravelHourParams);
                lblTravelHour.setTextSize(14);
                lblTravelHour.setTypeface(null, Typeface.BOLD);
                lblTravelHourParams.setMargins(15,-2,0,0);
                lblTravelHour.setText("Saat: " + ticket.getExpeditionDto().getExpedition().getDepartureTime());
                lblTravelHour.setTextColor(getResources().getColor(R.color.gray_dark2));
                layoutTravelHour.addView(lblTravelHour);

                linearLayout.addView(layoutTravelHour);
                /* End Travel Hour Layout */

                /* Start Travel Hour Layout */
                LinearLayout layoutGender = new LinearLayout(this);
                LinearLayout.LayoutParams layoutGenderParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutGender.setLayoutParams(layoutGenderParams);
                layoutGenderParams.setMargins(30,30,0,0);
                layoutGender.setOrientation(LinearLayout.HORIZONTAL);

                ImageView pbGender = new ImageView(this);
                LinearLayout.LayoutParams pbGenderParams = new LinearLayout.LayoutParams(35, 35);
                pbGender.setLayoutParams(pbGenderParams);
                pbGender.setPadding(0,0,0,0);
                pbGender.setImageResource(R.drawable.gender_2);
                layoutGender.addView(pbGender);

                TextView lblGender = new TextView(this);
                LinearLayout.LayoutParams lblGenderParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lblGender.setLayoutParams(lblGenderParams);
                lblGender.setTextSize(14);
                lblGender.setTypeface(null, Typeface.BOLD);
                lblGenderParams.setMargins(15,0,0,0);
                lblGender.setText("Cinsiyet: " + ticket.getTicketReservation().getGender());
                lblGender.setTextColor(getResources().getColor(R.color.gray_dark2));
                layoutGender.addView(lblGender);

                linearLayout.addView(layoutGender);
                /* End Travel Hour Layout */

                /* Start Seat Layout */
                LinearLayout layoutSeatNumber = new LinearLayout(this);
                LinearLayout.LayoutParams layoutSeatNumberParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutSeatNumber.setLayoutParams(layoutSeatNumberParams);
                layoutSeatNumberParams.setMargins(30,30,0,30);
                layoutSeatNumber.setOrientation(LinearLayout.HORIZONTAL);

                ImageView pbSeat = new ImageView(this);
                LinearLayout.LayoutParams pbSeatParams = new LinearLayout.LayoutParams(35, 35);
                pbSeat.setLayoutParams(pbSeatParams);
                pbSeatParams.setMargins(0,0,0,0);
                pbSeat.setImageResource(R.drawable.seat);
                layoutSeatNumber.addView(pbSeat);

                TextView lblSeatNumber = new TextView(this);
                LinearLayout.LayoutParams lblSeatNumberParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lblSeatNumber.setLayoutParams(lblSeatNumberParams);
                lblSeatNumber.setTextSize(14);
                lblSeatNumber.setTypeface(null, Typeface.BOLD);
                lblSeatNumberParams.setMargins(15,2,0,0);
                lblSeatNumber.setText("Kotuk: " + ticket.getTicketReservation().getSeatNumber());
                lblSeatNumber.setTextColor(getResources().getColor(R.color.gray_dark2));
                layoutSeatNumber.addView(lblSeatNumber);
                linearLayout.addView(layoutSeatNumber);

                Button btnTicketCancel = new Button(this);
                LinearLayout.LayoutParams btnTicketCancelParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 65);
                btnTicketCancel.setLayoutParams(btnTicketCancelParams);
                btnTicketCancel.setText("İptal et / Sil");
                btnTicketCancel.setTextSize(13);
                btnTicketCancel.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                btnTicketCancel.setGravity(Gravity.END);
                btnTicketCancelParams.setMargins(30,0,30,30);
                btnTicketCancel.setPadding(0,15,0,0);
                btnTicketCancel.setTextColor(getResources().getColor(R.color.white));
                btnTicketCancel.setBackgroundResource(R.drawable.ticket_cancel_button_design);
                btnTicketCancel.setAllCaps(false);
                btnTicketCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        btnYes = ticketCancelDialog.findViewById(R.id.btnCancelYes);
                        btnNo = ticketCancelDialog.findViewById(R.id.btnCancelNo);

                        btnYes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                databaseHelper.deleteTicketReservation(ticket.getTicketReservation().getId());
                                Toast.makeText(getApplicationContext(),"Bilet rezervasyonu başarılı bir şekilde iptal edildi", Toast.LENGTH_SHORT).show();
                                ticketCancelDialog.dismiss();
                                refreshActivity();
                            }
                        });
                        btnNo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ticketCancelDialog.dismiss();
                            }
                        });
                        ticketCancelDialog.show();
                    }
                });
                linearLayout.addView(btnTicketCancel);

                ticketsLayout.addView(linearLayout);

            }
        }
    }

    private void refreshActivity(){
        tbPhoneNumber.setText("");
        loadTickets();
    }
}