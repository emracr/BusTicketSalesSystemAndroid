package com.ansi.busticketsalesautomation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class PassengersActivity extends AppCompatActivity {


    DatabaseHelper databaseHelper;

    final Calendar myCalendar= Calendar.getInstance();

    Spinner spnTravelFrom, spnTravelTo;
    EditText tbTravelDate;
    TextView lblPassengersCount;
    Button btnFindExpeditions;
    LinearLayout expeditionsLayout, passengersLayout;

    String selectedCityFrom = null;
    String selectedCityTo = null;
    String selectedTravelDate = null;

    int selectedCityFromId = 0;
    int selectedCityToId = 0;

    Dialog passengersListDialog;
    Button btnOkay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passengers);

        databaseHelper = new DatabaseHelper(this);

        spnTravelFrom = findViewById(R.id.spnTravelFromCompanyPassengersActivity);
        spnTravelTo = findViewById(R.id.spnTravelToCompanyPassengersActivity);
        btnFindExpeditions = findViewById(R.id.btnListExpeditionsActivityPassengersCompany);
        expeditionsLayout = findViewById(R.id.expeditionsLayoutCompanyPassengersActivity);
        tbTravelDate = findViewById(R.id.tbTravelDateActivityCompanyPassengers);

        /* Start All Cities Array Define */
        String[] cities = new String[] {"Adana", "Adıyaman", "Afyon", "Ağrı", "Amasya", "Ankara", "Antalya", "Artvin", "Aydın", "Balıkesir", "Bilecik", "Bingöl", "Bitlis", "Bolu", "Burdur", "Bursa", "Çanakkale", "Çankırı", "Çorum", "Denizli", "Diyarbakır", "Edirne", "Elazığ", "Erzincan", "Erzurum", "Eskişehir", "Gaziantep", "Giresun", "Gümüşhane", "Hakkari", "Hatay", "Isparta", "İçel (Mersin)", "İstanbul", "İzmir", "Kars", "Kastamonu", "Kayseri", "Kırklareli", "Kırşehir", "Kocaeli", "Konya", "Kütahya", "Malatya", "Manisa", "Kahramanmaraş", "Mardin", "Muğla", "Muş", "Nevşehir", "Niğde", "Ordu", "Rize", "Sakarya", "Samsun", "Siirt", "Sinop", "Sivas", "Tekirdağ", "Tokat", "Trabzon", "Tunceli", "Şanlıurfa", "Uşak", "Van", "Yozgat", "Zonguldak", "Aksaray", "Bayburt", "Karaman", "Kırıkkale", "Batman", "Şırnak", "Bartın", "Ardahan", "Iğdır", "Yalova", "Karabük", "Kilis", "Osmaniye", "Düzce"};
        /* End All Cities Array Define */

        /* Start Travel From Cities Spinner */
        ArrayList<String> citiesFrom = new ArrayList<>();
        citiesFrom.add("Nereden");

        for (String city : cities) {
            citiesFrom.add(city);
        }
        ArrayAdapter<String> spnTravelCityFromAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, citiesFrom);
        spnTravelCityFromAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spnTravelFrom.setAdapter(spnTravelCityFromAdapter);

        spnTravelFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedCityFrom = spnTravelFrom.getSelectedItem().toString();
                selectedCityFromId = (int)spnTravelFrom.getSelectedItemId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        /* End Travel From Cities Spinner */

        /* Start Travel To Cities Spinner */
        ArrayList<String> citiesTo = new ArrayList<>();
        citiesTo.add("Nereye");

        for (String city : cities) {
            citiesTo.add(city);
        }
        ArrayAdapter<String> spnTravelCityToAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, citiesTo);
        spnTravelCityToAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spnTravelTo.setAdapter(spnTravelCityToAdapter);

        spnTravelTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedCityTo = spnTravelTo.getSelectedItem().toString();
                selectedCityToId = (int)spnTravelTo.getSelectedItemId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        /* End Travel To Cities Spinner */

        /* Start Date Picker Dialog */
        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                month += 1;
                selectedTravelDate = day + "/" + month + "/" + year;
                writeDateOfBirth();
            }
        };

        tbTravelDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(PassengersActivity.this, date, myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(myCalendar.getTimeInMillis());
                datePickerDialog.show();
            }
        });
        /* End Date Picker Dialog */

        btnFindExpeditions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (selectedCityFromId <= 0){
                    DialogBox.showDialog(getSupportFragmentManager(),"Uyarı","Lütfen kalkış şehri seçin");
                }
                else if(selectedCityToId <= 0){
                    DialogBox.showDialog(getSupportFragmentManager(),"Uyarı","Lütfen varış şehri seçin");
                }
                else if(tbTravelDate.getText().toString().isEmpty()){
                    DialogBox.showDialog(getSupportFragmentManager(),"Uyarı","Lütfen seyahat tarihi seçin");
                }
                else{

                    List<ExpeditionDto> expeditions = databaseHelper.getExpeditionsByCompanyIdAndCityFromAndCityTo(CompanyCredentials.COMPANY.getId(), selectedCityFrom, selectedCityTo);

                    expeditionsLayout.removeAllViews();

                    if (expeditions.size() > 0){

                        for (ExpeditionDto expedition : expeditions) {

                            LinearLayout linearLayout = new LinearLayout(view.getContext());
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            layoutParams.setMargins(40,25,40,10);
                            linearLayout.setLayoutParams(layoutParams);
                            linearLayout.setBackgroundResource(R.drawable.expedition_box_background);
                            linearLayout.setOrientation(LinearLayout.VERTICAL);

                            LinearLayout linearLayoutTravelTime = new LinearLayout(view.getContext());
                            LinearLayout.LayoutParams layoutTravelTimeParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            layoutTravelTimeParams.setMargins(0,40,0,0);
                            linearLayoutTravelTime.setLayoutParams(layoutTravelTimeParams);
                            linearLayoutTravelTime.setGravity(Gravity.CENTER);
                            linearLayoutTravelTime.setOrientation(LinearLayout.HORIZONTAL);
                            linearLayout.addView(linearLayoutTravelTime);

                            ImageView pbClock = new ImageView(view.getContext());
                            LinearLayout.LayoutParams pbClockParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            pbClock.setLayoutParams(pbClockParams);
                            pbClock.setImageResource(R.drawable.time);
                            pbClock.setPadding(0,0,0,0);
                            linearLayoutTravelTime.addView(pbClock);

                            TextView lblTravelTime = new TextView(view.getContext());
                            LinearLayout.LayoutParams lblTravelTimeParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            lblTravelTime.setLayoutParams(lblTravelTimeParams);
                            lblTravelTime.setTextSize(15);
                            lblTravelTime.setTypeface(null, Typeface.BOLD);
                            lblTravelTimeParams.setMargins(15,2,0,0);
                            lblTravelTime.setText(expedition.getExpedition().getDepartureTime());
                            lblTravelTime.setTextColor(getResources().getColor(R.color.gray_dark2));
                            linearLayoutTravelTime.addView(lblTravelTime);

                            TextView lblPrice = new TextView(view.getContext());
                            LinearLayout.LayoutParams lblPriceParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            lblPriceParams.setMargins(30,25,20,20);
                            lblPrice.setLayoutParams(lblPriceParams);
                            lblPrice.setText(expedition.getExpedition().getPrice() + ",00 ₺");
                            lblPrice.setTextSize(17);
                            lblPrice.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            lblPrice.setTypeface(null, Typeface.BOLD);
                            lblPrice.setTextColor(getResources().getColor(R.color.gray_dark2));
                            linearLayout.addView(lblPrice);

                            Button btnGetPassengersList = new Button(view.getContext());
                            LinearLayout.LayoutParams btnGetPassengersListParams = new LinearLayout.LayoutParams(400, 65);
                            btnGetPassengersList.setLayoutParams(btnGetPassengersListParams);
                            btnGetPassengersList.setId(expedition.getExpedition().getId());
                            btnGetPassengersListParams.gravity= Gravity.CENTER;
                            btnGetPassengersListParams.setMargins(20,5,20,20);
                            btnGetPassengersList.setBackgroundResource(R.drawable.button_design);
                            btnGetPassengersList.setTextColor(getResources().getColor(R.color.white));
                            btnGetPassengersList.setTextSize(12);
                            btnGetPassengersList.setAllCaps(false);
                            btnGetPassengersList.setText("Yolcu Listesi");
                            btnGetPassengersList.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    String expeditionDate = tbTravelDate.getText().toString();
                                    List<TicketAndUserDto> passengers = databaseHelper.getPassengersByExpeditionIdAndDate(expedition.getExpedition().getId(), expeditionDate);
                                    List<TicketReservation> reservations = databaseHelper.getTicketReservationsByExpeditionIdAndDepartureDate(expedition.getExpedition().getId(), expeditionDate);

                                    passengersLayout = passengersListDialog.findViewById(R.id.passengersLayoutActivityPassengersDialog);
                                    btnOkay = passengersListDialog.findViewById(R.id.btnOkayPassengersListDialog);
                                    lblPassengersCount = passengersListDialog.findViewById(R.id.lblPassengersCount);
                                    lblPassengersCount.setText("");
                                    passengersLayout.removeAllViews();

                                    if (passengers.size() > 0 || reservations.size() > 0){
                                        lblPassengersCount.setText("Toplam " + String.valueOf(passengers.size() + reservations.size()) + " yolcu listelenmektedir.");

                                        if (passengers.size() > 0){
                                            TextView lblNormalTicket = new TextView(view.getContext());
                                            LinearLayout.LayoutParams lblNormalTicketParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                            lblNormalTicket.setLayoutParams(lblNormalTicketParams);
                                            lblNormalTicket.setTextSize(14);
                                            lblNormalTicket.setTypeface(null, Typeface.BOLD);
                                            lblNormalTicketParams.setMargins(20,15,20,0);
                                            lblNormalTicket.setText("Normal Biletler ");
                                            lblNormalTicketParams.gravity = Gravity.CENTER_HORIZONTAL;
                                            lblNormalTicket.setTextColor(getResources().getColor(R.color.gray_dark2));
                                            passengersLayout.addView(lblNormalTicket);

                                            /* Draw line br */
                                            LinearLayout linearLayoutLine = new LinearLayout(view.getContext());
                                            LinearLayout.LayoutParams layoutLineParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 3);
                                            layoutLineParams.setMargins(40,15,40,5);
                                            linearLayoutLine.setLayoutParams(layoutLineParams);
                                            linearLayoutLine.setBackgroundResource(R.color.gray2);
                                            linearLayoutLine.setOrientation(LinearLayout.VERTICAL);
                                            passengersLayout.addView(linearLayoutLine);

                                            for (TicketAndUserDto passenger : passengers) {
                                                LinearLayout linearLayoutPassenger = new LinearLayout(view.getContext());
                                                LinearLayout.LayoutParams linearLayoutPassengerParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                                linearLayoutPassengerParams.setMargins(40,25,40,10);
                                                linearLayoutPassenger.setLayoutParams(linearLayoutPassengerParams);
                                                linearLayoutPassenger.setBackgroundResource(R.drawable.expedition_box_background);
                                                linearLayoutPassenger.setOrientation(LinearLayout.VERTICAL);

                                                TextView lblPassengerFullName = new TextView(view.getContext());
                                                LinearLayout.LayoutParams lblPassengerFullNameParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                                lblPassengerFullName.setLayoutParams(lblPassengerFullNameParams);
                                                lblPassengerFullName.setTextSize(14);
                                                lblPassengerFullName.setTypeface(null, Typeface.BOLD);
                                                lblPassengerFullNameParams.setMargins(20,15,0,0);
                                                lblPassengerFullName.setText("Ad - Soyad: " + passenger.getUser().getFirstName().toUpperCase(Locale.ROOT) + " " + passenger.getUser().getLastName().toUpperCase(Locale.ROOT));
                                                lblPassengerFullName.setTextColor(getResources().getColor(R.color.gray_dark2));
                                                linearLayoutPassenger.addView(lblPassengerFullName);

                                                TextView lblPassengerPhoneNumber = new TextView(view.getContext());
                                                LinearLayout.LayoutParams lblPassengerPhoneNumberParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                                lblPassengerPhoneNumber.setLayoutParams(lblPassengerPhoneNumberParams);
                                                lblPassengerPhoneNumber.setTextSize(14);
                                                lblPassengerPhoneNumber.setTypeface(null, Typeface.BOLD);
                                                lblPassengerPhoneNumberParams.setMargins(20,3,0,0);
                                                lblPassengerPhoneNumber.setText("Telefon Numarası: " + passenger.getUser().getPhoneNumber());
                                                lblPassengerPhoneNumber.setTextColor(getResources().getColor(R.color.gray_dark2));
                                                linearLayoutPassenger.addView(lblPassengerPhoneNumber);

                                                TextView lblPassengerSeatNumber = new TextView(view.getContext());
                                                LinearLayout.LayoutParams lblPassengerSeatNumberParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                                lblPassengerSeatNumber.setLayoutParams(lblPassengerSeatNumberParams);
                                                lblPassengerSeatNumber.setTextSize(14);
                                                lblPassengerSeatNumber.setTypeface(null, Typeface.BOLD);
                                                lblPassengerSeatNumberParams.setMargins(20,3,0,15);
                                                lblPassengerSeatNumber.setText("Koltuk No: " + passenger.getTicket().getSeatNumber());
                                                lblPassengerSeatNumber.setTextColor(getResources().getColor(R.color.gray_dark2));
                                                linearLayoutPassenger.addView(lblPassengerSeatNumber);

                                                passengersLayout.addView(linearLayoutPassenger);

                                            }
                                        }
                                        if (reservations.size() > 0){
                                            TextView lblReservationTicket = new TextView(view.getContext());
                                            LinearLayout.LayoutParams lblReservationTicketParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                            lblReservationTicket.setLayoutParams(lblReservationTicketParams);
                                            lblReservationTicket.setTextSize(14);
                                            lblReservationTicket.setTypeface(null, Typeface.BOLD);
                                            lblReservationTicketParams.setMargins(20,50,20,0);
                                            lblReservationTicket.setText("Rezervasyon Biletleri ");
                                            lblReservationTicketParams.gravity = Gravity.CENTER_HORIZONTAL;
                                            lblReservationTicket.setTextColor(getResources().getColor(R.color.gray_dark2));
                                            passengersLayout.addView(lblReservationTicket);

                                            /* Draw line br */
                                            LinearLayout linearLayoutLine2 = new LinearLayout(view.getContext());
                                            LinearLayout.LayoutParams layoutLineParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 3);
                                            layoutLineParams2.setMargins(40,15,40,5);
                                            linearLayoutLine2.setLayoutParams(layoutLineParams2);
                                            linearLayoutLine2.setBackgroundResource(R.color.gray2);
                                            linearLayoutLine2.setOrientation(LinearLayout.VERTICAL);
                                            passengersLayout.addView(linearLayoutLine2);

                                            for (TicketReservation reservation : reservations) {
                                                LinearLayout linearLayoutPassenger = new LinearLayout(view.getContext());
                                                LinearLayout.LayoutParams linearLayoutPassengerParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                                linearLayoutPassengerParams.setMargins(40,25,40,10);
                                                linearLayoutPassenger.setLayoutParams(linearLayoutPassengerParams);
                                                linearLayoutPassenger.setBackgroundResource(R.drawable.expedition_box_background);
                                                linearLayoutPassenger.setOrientation(LinearLayout.VERTICAL);

                                                TextView lblPassengerFullName = new TextView(view.getContext());
                                                LinearLayout.LayoutParams lblPassengerFullNameParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                                lblPassengerFullName.setLayoutParams(lblPassengerFullNameParams);
                                                lblPassengerFullName.setTextSize(14);
                                                lblPassengerFullName.setTypeface(null, Typeface.BOLD);
                                                lblPassengerFullNameParams.setMargins(20,15,0,0);
                                                lblPassengerFullName.setText("Ad - Soyad: " + reservation.getFirstName().toUpperCase(Locale.ROOT) + " " + reservation.getLastName().toUpperCase(Locale.ROOT));
                                                lblPassengerFullName.setTextColor(getResources().getColor(R.color.gray_dark2));
                                                linearLayoutPassenger.addView(lblPassengerFullName);

                                                TextView lblPassengerPhoneNumber = new TextView(view.getContext());
                                                LinearLayout.LayoutParams lblPassengerPhoneNumberParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                                lblPassengerPhoneNumber.setLayoutParams(lblPassengerPhoneNumberParams);
                                                lblPassengerPhoneNumber.setTextSize(14);
                                                lblPassengerPhoneNumber.setTypeface(null, Typeface.BOLD);
                                                lblPassengerPhoneNumberParams.setMargins(20,3,0,0);
                                                lblPassengerPhoneNumber.setText("Telefon Numarası: " + reservation.getPhoneNumber());
                                                lblPassengerPhoneNumber.setTextColor(getResources().getColor(R.color.gray_dark2));
                                                linearLayoutPassenger.addView(lblPassengerPhoneNumber);

                                                TextView lblPassengerSeatNumber = new TextView(view.getContext());
                                                LinearLayout.LayoutParams lblPassengerSeatNumberParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                                lblPassengerSeatNumber.setLayoutParams(lblPassengerSeatNumberParams);
                                                lblPassengerSeatNumber.setTextSize(14);
                                                lblPassengerSeatNumber.setTypeface(null, Typeface.BOLD);
                                                lblPassengerSeatNumberParams.setMargins(20,3,0,15);
                                                lblPassengerSeatNumber.setText("Koltuk No: " + reservation.getSeatNumber());
                                                lblPassengerSeatNumber.setTextColor(getResources().getColor(R.color.gray_dark2));
                                                linearLayoutPassenger.addView(lblPassengerSeatNumber);

                                                passengersLayout.addView(linearLayoutPassenger);

                                            }
                                        }
                                    }

                                    else{

                                        ImageView pbWarning = new ImageView(view.getContext());
                                        LinearLayout.LayoutParams pbWarningParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                        pbWarning.setLayoutParams(pbWarningParams);
                                        pbWarningParams.gravity= Gravity.CENTER;
                                        pbWarning.setImageResource(R.drawable.warning);
                                        pbWarning.setPadding(0,70,0,0);
                                        passengersLayout.addView(pbWarning);

                                        TextView lblPassengersNotFound = new TextView(view.getContext());
                                        LinearLayout.LayoutParams lblPassengersNotFoundParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                        lblPassengersNotFound.setLayoutParams(lblPassengersNotFoundParams);
                                        lblPassengersNotFound.setTextSize(15);
                                        lblPassengersNotFound.setTypeface(null, Typeface.BOLD);
                                        lblPassengersNotFound.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                        lblPassengersNotFoundParams.setMargins(30,30,30,10);
                                        lblPassengersNotFound.setText("Henüz bilet alan yolcu yok");
                                        lblPassengersNotFound.setTextColor(getResources().getColor(R.color.gray_dark2));
                                        passengersLayout.addView(lblPassengersNotFound);

                                    }

                                    btnOkay.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            passengersListDialog.dismiss();
                                        }
                                    });
                                    passengersListDialog.show();
                                }
                            });
                            linearLayout.addView(btnGetPassengersList);
                            expeditionsLayout.addView(linearLayout);
                        }
                    }
                    else{
                        ImageView pbWarning = new ImageView(view.getContext());
                        LinearLayout.LayoutParams pbWarningParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        pbWarning.setLayoutParams(pbWarningParams);
                        pbWarningParams.gravity= Gravity.CENTER;
                        pbWarning.setImageResource(R.drawable.warning);
                        pbWarning.setPadding(0,70,0,0);
                        expeditionsLayout.addView(pbWarning);

                        TextView lblExpeditionNotFound = new TextView(view.getContext());
                        LinearLayout.LayoutParams lblExpeditionNotFoundParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        lblExpeditionNotFound.setLayoutParams(lblExpeditionNotFoundParams);
                        lblExpeditionNotFound.setTextSize(15);
                        lblExpeditionNotFound.setTypeface(null, Typeface.BOLD);
                        lblExpeditionNotFound.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        lblExpeditionNotFoundParams.setMargins(30,30,30,10);
                        lblExpeditionNotFound.setText(selectedCityFrom + " - " + selectedCityTo + " güzergahları arasında sefer bulunmamaktadır!");
                        lblExpeditionNotFound.setTextColor(getResources().getColor(R.color.gray_dark2));
                        expeditionsLayout.addView(lblExpeditionNotFound);
                    }
                }

            }
        });

        /* Dialog For Expedition Delete */
        passengersListDialog = new Dialog(this);
        passengersListDialog.setContentView(R.layout.passengers_list_dialog);
        passengersListDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.ticket_cancel_dialog_background));
        passengersListDialog.getWindow().setLayout(600, ViewGroup.LayoutParams.WRAP_CONTENT);
        passengersListDialog.setCancelable(false);
        passengersListDialog.getWindow().getAttributes().windowAnimations = R.style.PassengersListDialogAnimation;

    }

    private void writeDateOfBirth(){
        String myFormat="dd/MM/yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        tbTravelDate.setText(dateFormat.format(myCalendar.getTime()));
    }
}