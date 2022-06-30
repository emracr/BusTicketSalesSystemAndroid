package com.ansi.busticketsalesautomation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

public class AdminReservationExpeditionsActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;

    LinearLayout expeditionsLayout;
    TextView lblCityFromCityTo, lblTravelDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_reservation_expeditions);

        databaseHelper = new DatabaseHelper(this);

        expeditionsLayout = findViewById(R.id.ticketReservationExpeditionsLayoutActivityAdminReservation);
        lblCityFromCityTo = findViewById(R.id.lblCityFromCityToActivityAdminTicketReservationExpeditions);
        lblTravelDate = findViewById(R.id.lblTravelDateActivityAdminTicketReservationExpeditions);

        lblCityFromCityTo.setText(ReservationInformation.CITY_FROM + "   >  " + ReservationInformation.CITY_TO);
        lblTravelDate.setText(convertDateToLocaleDate(ReservationInformation.DEPARTURE_DATE));

        List<ExpeditionDto> expeditions = databaseHelper.getExpeditionsByCityFromAndCityToForTicketReservation(ReservationInformation.CITY_FROM, ReservationInformation.CITY_TO);

        if (expeditions.size() > 0){

            for (ExpeditionDto expedition : expeditions) {

                LinearLayout linearLayout = new LinearLayout(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(40,25,40,10);
                linearLayout.setLayoutParams(layoutParams);
                linearLayout.setBackgroundResource(R.drawable.expedition_box_background);
                linearLayout.setOrientation(LinearLayout.VERTICAL);

                TextView lblCompanyName = new TextView(this);
                LinearLayout.LayoutParams lblCompanyNameParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lblCompanyNameParams.setMargins(30,30,20,0);
                lblCompanyName.setLayoutParams(lblCompanyNameParams);
                lblCompanyName.setText(expedition.getCompany().getCompanyName());
                lblCompanyName.setTextSize(15);
                lblCompanyName.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                lblCompanyName.setTypeface(null, Typeface.BOLD);
                lblCompanyName.setTextColor(getResources().getColor(R.color.gray_dark2));
                linearLayout.addView(lblCompanyName);

                /* Draw line br */
                LinearLayout linearLayoutLine = new LinearLayout(this);
                LinearLayout.LayoutParams layoutLineParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 3);
                layoutLineParams.setMargins(40,15,40,5);
                linearLayoutLine.setLayoutParams(layoutLineParams);
                linearLayoutLine.setBackgroundResource(R.color.gray2);
                linearLayoutLine.setOrientation(LinearLayout.VERTICAL);
                linearLayout.addView(linearLayoutLine);

                LinearLayout linearLayoutTravelTime = new LinearLayout(this);
                LinearLayout.LayoutParams layoutTravelTimeParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutTravelTimeParams.setMargins(0,25,0,0);
                linearLayoutTravelTime.setLayoutParams(layoutTravelTimeParams);
                linearLayoutTravelTime.setGravity(Gravity.CENTER);
                linearLayoutTravelTime.setOrientation(LinearLayout.HORIZONTAL);
                linearLayout.addView(linearLayoutTravelTime);

                ImageView pbClock = new ImageView(this);
                LinearLayout.LayoutParams pbClockParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                pbClock.setLayoutParams(pbClockParams);
                pbClock.setImageResource(R.drawable.time);
                pbClock.setPadding(0,0,0,0);
                linearLayoutTravelTime.addView(pbClock);

                TextView lblTravelTime = new TextView(this);
                LinearLayout.LayoutParams lblTravelTimeParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lblTravelTime.setLayoutParams(lblTravelTimeParams);
                lblTravelTime.setTextSize(15);
                lblTravelTime.setTypeface(null, Typeface.BOLD);
                lblTravelTimeParams.setMargins(15,2,0,0);
                lblTravelTime.setText(expedition.getExpedition().getDepartureTime());
                lblTravelTime.setTextColor(getResources().getColor(R.color.gray_dark2));
                linearLayoutTravelTime.addView(lblTravelTime);

                TextView lblPrice = new TextView(this);
                LinearLayout.LayoutParams lblPriceParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lblPriceParams.setMargins(30,25,20,20);
                lblPrice.setLayoutParams(lblPriceParams);
                lblPrice.setText(expedition.getExpedition().getPrice() + ",00 ₺");
                lblPrice.setTextSize(17);
                lblPrice.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                lblPrice.setTypeface(null, Typeface.BOLD);
                lblPrice.setTextColor(getResources().getColor(R.color.gray_dark2));
                linearLayout.addView(lblPrice);

                Button btnRedirectToSeat = new Button(this);
                LinearLayout.LayoutParams btnRedirectToSeatParams = new LinearLayout.LayoutParams(300, 65);
                btnRedirectToSeat.setLayoutParams(btnRedirectToSeatParams);
                btnRedirectToSeat.setId(expedition.getExpedition().getId());
                btnRedirectToSeatParams.gravity= Gravity.CENTER;
                btnRedirectToSeatParams.setMargins(40,10,40,30);
                btnRedirectToSeat.setPadding(0, 0,0,0);
                btnRedirectToSeat.setBackgroundResource(R.drawable.redirect_to_seat_button_design);
                btnRedirectToSeat.setTextColor(getResources().getColor(R.color.white));
                btnRedirectToSeat.setTextSize(11);
                btnRedirectToSeat.setText("Koltuk seç");

                btnRedirectToSeat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ReservationInformation.EXPEDITION_ID = view.getId();
                        ReservationInformation.DEPARTURE_HOUR = expedition.getExpedition().getDepartureTime();
                        ReservationInformation.PRICE = expedition.getExpedition().getPrice();
                        Intent intent = new Intent(getApplicationContext(), AdminReservationSeatSelectionActivity.class);
                        startActivity(intent);
                    }
                });

                linearLayout.addView(btnRedirectToSeat);

                expeditionsLayout.setPadding(0,20,0,50);
                expeditionsLayout.addView(linearLayout);
            }

        }
        else{

            ImageView pbWarning = new ImageView(this);
            LinearLayout.LayoutParams pbWarningParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            pbWarning.setLayoutParams(pbWarningParams);
            pbWarningParams.gravity= Gravity.CENTER;
            pbWarning.setImageResource(R.drawable.warning);
            pbWarning.setPadding(0,70,0,0);
            expeditionsLayout.addView(pbWarning);

            TextView lblExpeditionNotFound = new TextView(this);
            LinearLayout.LayoutParams lblExpeditionNotFoundParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lblExpeditionNotFound.setLayoutParams(lblExpeditionNotFoundParams);
            lblExpeditionNotFound.setTextSize(15);
            lblExpeditionNotFound.setTypeface(null, Typeface.BOLD);
            lblExpeditionNotFound.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            lblExpeditionNotFoundParams.setMargins(30,30,30,10);
            lblExpeditionNotFound.setText(ReservationInformation.DEPARTURE_DATE + " tarihinde, " + ReservationInformation.CITY_FROM + " - " + ReservationInformation.CITY_TO + " güzergahları arasında sefer bulunmamaktadır!");
            lblExpeditionNotFound.setTextColor(getResources().getColor(R.color.gray_dark2));
            expeditionsLayout.addView(lblExpeditionNotFound);
        }

    }

    private String convertDateToLocaleDate(String date){

        String newDate = "";

        String[] splitDate = date.split("/");

        newDate += splitDate[0] + " ";

        int month = Integer.parseInt(splitDate[1]);
        String[] months = new String[]{"Ocak","Şubat","Mart","Nisan","Mayıs","Haziran","Temmuz","Ağustos","Eylül","Ekim","Kasım","Aralık"};
        newDate += months[month - 1] + " ";


        Calendar calendar = Calendar.getInstance();
        calendar.set(Integer.parseInt(splitDate[2]),Integer.parseInt(splitDate[1]) - 1, Integer.parseInt(splitDate[0]));
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        String[] days = new String[]{"Pazar","Pazartesi","Salı","Çarşamba","Perşembe","Cuma","Cumartesi"};
        newDate += days[day - 1];

        ReservationInformation.DEPARTURE_DATE_LONG = newDate + " " + splitDate[2];

        return newDate;
    }
}