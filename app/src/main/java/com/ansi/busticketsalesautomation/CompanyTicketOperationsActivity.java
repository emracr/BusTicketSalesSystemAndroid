package com.ansi.busticketsalesautomation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
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

public class CompanyTicketOperationsActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;

    final Calendar myCalendar = Calendar.getInstance();

    Spinner spnTravelFrom, spnTravelTo;
    EditText tbPhoneNumber, tbTravelDate, tbTravelHour;
    Button btnGetTickets;
    TextView lblShowFilters;

    LinearLayout customerTicketsLayout, layout_ticket_filters;

    String selectedCityFrom = null;
    String selectedCityTo = null;

    int selectedCityFromId = 0;
    int selectedCityToId = 0;

    Dialog ticketCancelDialog;
    Button btnYes, btnNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_ticket_operations);

        databaseHelper = new DatabaseHelper(this);

        tbPhoneNumber = findViewById(R.id.tbPhoneNumberCompanyTicketOperations);
        tbTravelDate = findViewById(R.id.tbTravelDateCompanyTicketOperationsActivity);
        tbTravelHour = findViewById(R.id.tbTravelHourTicketOperationsActivity);
        spnTravelFrom = findViewById(R.id.spnTravelFromCompanyTicketOperations);
        spnTravelTo = findViewById(R.id.spnTravelToCompanyTicketOperations);
        btnGetTickets = findViewById(R.id.btnFindTicketsCompanyTicketOperations);
        customerTicketsLayout = findViewById(R.id.customerTicketsLayoutActivityCompanyTicketOperations);
        layout_ticket_filters = findViewById(R.id.layout_ticket_filters_activity_ticket_operations);
        lblShowFilters = findViewById(R.id.lblShowFiltersActivityTicketOperations);

        ticketCancelDialog = new Dialog(this);
        ticketCancelDialog.setContentView(R.layout.ticket_cancel_dialog);
        ticketCancelDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.ticket_cancel_dialog_background));
        ticketCancelDialog.getWindow().setLayout(600, ViewGroup.LayoutParams.WRAP_CONTENT);
        ticketCancelDialog.setCancelable(false);
        ticketCancelDialog.getWindow().getAttributes().windowAnimations = R.style.TicketCancelDialogAnimation;

        /* Start Date Picker Dialog */
        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                month += 1;
                writeDateOfBirth();
            }
        };

        tbTravelDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(CompanyTicketOperationsActivity.this, date, myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(myCalendar.getTimeInMillis());
                datePickerDialog.show();
            }
        });
        /* End Date Picker Dialog */

        /* Start All Cities Array Define */
        String[] cities = new String[] {"Adana", "Adıyaman", "Afyon", "Ağrı", "Amasya", "Ankara", "Antalya", "Artvin", "Aydın", "Balıkesir", "Bilecik", "Bingöl", "Bitlis", "Bolu", "Burdur", "Bursa", "Çanakkale", "Çankırı", "Çorum", "Denizli", "Diyarbakır", "Edirne", "Elazığ", "Erzincan", "Erzurum", "Eskişehir", "Gaziantep", "Giresun", "Gümüşhane", "Hakkari", "Hatay", "Isparta", "İçel (Mersin)", "İstanbul", "İzmir", "Kars", "Kastamonu", "Kayseri", "Kırklareli", "Kırşehir", "Kocaeli", "Konya", "Kütahya", "Malatya", "Manisa", "Kahramanmaraş", "Mardin", "Muğla", "Muş", "Nevşehir", "Niğde", "Ordu", "Rize", "Sakarya", "Samsun", "Siirt", "Sinop", "Sivas", "Tekirdağ", "Tokat", "Trabzon", "Tunceli", "Şanlıurfa", "Uşak", "Van", "Yozgat", "Zonguldak", "Aksaray", "Bayburt", "Karaman", "Kırıkkale", "Batman", "Şırnak", "Bartın", "Ardahan", "Iğdır", "Yalova", "Karabük", "Kilis", "Osmaniye", "Düzce"};
        /* End All Cities Array Define */

        /* Start Travel From Cities Spinner */
        ArrayList<String> citiesFrom = new ArrayList<>();
        citiesFrom.add("Nereden");

        for (String city : cities) {
            citiesFrom.add(city);
        }
        ArrayAdapter<String> spnTravelCityFromAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, citiesFrom);
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
        ArrayAdapter<String> spnTravelCityToAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, citiesTo);
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

        lblShowFilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_ticket_filters.setVisibility(View.VISIBLE);
                lblShowFilters.setVisibility(View.GONE);
            }
        });

        btnGetTickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String phoneNumber = tbPhoneNumber.getText().toString();

                if ((!phoneNumber.isEmpty() || phoneNumber.length() > 0) && (selectedCityFromId <= 0 && selectedCityToId <= 0)){
                    List<TicketDtoAndUserDto> tickets = databaseHelper.getTicketsDtoByPhoneNumber(phoneNumber);
                    List<TicketReservationDto> ticketReservations = databaseHelper.getTicketReservationsDtoByPhoneNumber(phoneNumber);
                    customerTicketsLayout.removeAllViews();
                    layout_ticket_filters.setVisibility(View.GONE);
                    lblShowFilters.setVisibility(View.VISIBLE);
                    int ticketsSize = 0;
                    if (tickets != null || ticketReservations != null){
                        if (tickets != null && tickets.size() > 0){
                            listTickets(tickets);
                            ticketsSize += tickets.size();
                        }
                        if (ticketReservations != null && ticketReservations.size() > 0){
                            listTicketReservations(ticketReservations);
                            ticketsSize += ticketReservations.size();
                        }
                    }
                    if(ticketsSize <= 0){
                        ImageView pbWarning = new ImageView(getApplicationContext());
                        LinearLayout.LayoutParams pbWarningParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        pbWarning.setLayoutParams(pbWarningParams);
                        pbWarningParams.gravity= Gravity.CENTER;
                        pbWarning.setImageResource(R.drawable.warning);
                        pbWarning.setPadding(0,70,0,0);
                        customerTicketsLayout.addView(pbWarning);

                        TextView lblTicketNotFound = new TextView(getApplicationContext());
                        LinearLayout.LayoutParams lblTicketNotFoundParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        lblTicketNotFound.setLayoutParams(lblTicketNotFoundParams);
                        lblTicketNotFound.setTextSize(15);
                        lblTicketNotFound.setTypeface(null, Typeface.BOLD);
                        lblTicketNotFound.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        lblTicketNotFoundParams.setMargins(30,30,30,10);
                        lblTicketNotFound.setText("Girilen telefon numarasına ait herhangi bir bilet bulunamadı");
                        lblTicketNotFound.setTextColor(getResources().getColor(R.color.gray_dark2));
                        customerTicketsLayout.addView(lblTicketNotFound);
                    }

                }
                else if (selectedCityFromId <= 0 && selectedCityToId > 0){
                    DialogBox.showDialog(getSupportFragmentManager(),"Uyarı","Lütfen kalkış şehri seçin");
                }
                else if(selectedCityToId <= 0 && selectedCityFromId > 0){
                    DialogBox.showDialog(getSupportFragmentManager(),"Uyarı","Lütfen varış şehri seçin");
                }
                else if(tbTravelDate.getText().toString().isEmpty()){
                    DialogBox.showDialog(getSupportFragmentManager(),"Uyarı","Lütfen seyahat tarihi seçin");
                }
                else if(tbTravelHour.getText().toString().isEmpty()){
                    DialogBox.showDialog(getSupportFragmentManager(),"Uyarı","Lütfen seyahat saati seçin");
                }
                else{
                    String date = tbTravelDate.getText().toString();
                    TicketDtoExtend tickets = databaseHelper.getTicketsByPhoneNumberAndCompanyIdAndCityFromAndCityToAndDepartureDate(CompanyCredentials.COMPANY.getId(), phoneNumber, selectedCityFrom, selectedCityTo, date);
                    //List<TicketReservationDto> ticketReservations = databaseHelper.getTicketReservationsByPhoneNumberAndCompanyIdAndCityFromAndCityToAndDepartureDate(CompanyCredentials.COMPANY.getId(), phoneNumber, selectedCityFrom, selectedCityTo, date);
                    List<TicketDtoAndUserDto> ticketsByDepartureHour = databaseHelper.getTicketsByCompanyIdAndCityFromAndCityToAndDepartureDateAndDepartureHour(CompanyCredentials.COMPANY.getId(), selectedCityFrom, selectedCityTo, date, tbTravelHour.getText().toString());
                    List<TicketReservationDto> ticketReservationsByDepartureHour = databaseHelper.getTicketReservationsByCompanyIdAndCityFromAndCityToAndDepartureDateAndDepartureHour(CompanyCredentials.COMPANY.getId(), selectedCityFrom, selectedCityTo, date, tbTravelHour.getText().toString());
                    customerTicketsLayout.removeAllViews();
                    if (tickets.getTicketDto().size() > 0 || ticketsByDepartureHour.size() > 0 || ticketReservationsByDepartureHour.size() > 0){
                        layout_ticket_filters.setVisibility(View.GONE);
                        lblShowFilters.setVisibility(View.VISIBLE);
                        if (!phoneNumber.isEmpty() || phoneNumber.length() > 0){
                            if (tickets.getTicketDto().size() > 0){
                                listTickets(tickets);
                            }
                        }
                        else if(!tbTravelHour.getText().toString().isEmpty() || tbTravelHour.getText().toString().length() > 0){
                            if (ticketsByDepartureHour.size() > 0){
                                listTickets(ticketsByDepartureHour);
                            }
                            if (ticketReservationsByDepartureHour.size() > 0){
                                listTicketReservations(ticketReservationsByDepartureHour);
                            }
                        }
                    }
                    else{
                        ImageView pbWarning = new ImageView(getApplicationContext());
                        LinearLayout.LayoutParams pbWarningParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        pbWarning.setLayoutParams(pbWarningParams);
                        pbWarningParams.gravity= Gravity.CENTER;
                        pbWarning.setImageResource(R.drawable.warning);
                        pbWarning.setPadding(0,70,0,0);
                        customerTicketsLayout.addView(pbWarning);

                        TextView lblTicketNotFound = new TextView(getApplicationContext());
                        LinearLayout.LayoutParams lblTicketNotFoundParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        lblTicketNotFound.setLayoutParams(lblTicketNotFoundParams);
                        lblTicketNotFound.setTextSize(15);
                        lblTicketNotFound.setTypeface(null, Typeface.BOLD);
                        lblTicketNotFound.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        lblTicketNotFoundParams.setMargins(30,30,30,10);
                        lblTicketNotFound.setText("Bu bilgiler doğrultusunda herhangi bir bilet bulunamadı");
                        lblTicketNotFound.setTextColor(getResources().getColor(R.color.gray_dark2));
                        customerTicketsLayout.addView(lblTicketNotFound);
                    }
                }
            }
        });
    }

    private void listTickets(TicketDtoExtend tickets){
        for (TicketDto ticket : tickets.getTicketDto()) {

            LinearLayout linearLayout = new LinearLayout(getApplicationContext());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0,20,0,0);
            linearLayout.setLayoutParams(layoutParams);
            linearLayout.setBackgroundResource(R.drawable.expedition_box_background);
            linearLayout.setOrientation(LinearLayout.VERTICAL);

            /* Start From City Layout */
            LinearLayout layoutCompany = new LinearLayout(this);
            LinearLayout.LayoutParams layoutCompanyParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutCompany.setLayoutParams(layoutCompanyParams);
            layoutCompanyParams.setMargins(30,20,0,0);
            layoutCompany.setOrientation(LinearLayout.HORIZONTAL);

            ImageView pbBus = new ImageView(this);
            LinearLayout.LayoutParams pbBusParams = new LinearLayout.LayoutParams(35, 35);
            pbBus.setLayoutParams(pbBusParams);
            pbBus.setPadding(0,3,0,0);
            pbBus.setImageResource(R.drawable.person_2);
            layoutCompany.addView(pbBus);

            TextView lblCustomerName = new TextView(this);
            LinearLayout.LayoutParams lblCustomerNameParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lblCustomerName.setLayoutParams(lblCustomerNameParams);
            lblCustomerName.setTextSize(14);
            lblCustomerName.setTypeface(null, Typeface.BOLD);
            lblCustomerNameParams.setMargins(15,0,0,0);
            lblCustomerName.setText("Ad - Soyad: " + tickets.getUser().getFirstName() + " " + tickets.getUser().getLastName());
            lblCustomerName.setTextColor(getResources().getColor(R.color.gray_dark2));
            layoutCompany.addView(lblCustomerName);

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
            lblTravelDate.setText("Tarih: " + ticket.getTicket().getDepartureDate());
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
            lblGender.setText("Cinsiyet: " + ticket.getTicket().getGender());
            lblGender.setTextColor(getResources().getColor(R.color.gray_dark2));
            layoutGender.addView(lblGender);

            linearLayout.addView(layoutGender);
            /* End Travel Hour Layout */

            /* Start Seat Layout */
            LinearLayout layoutSeatNumber = new LinearLayout(this);
            LinearLayout.LayoutParams layoutSeatNumberParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutSeatNumber.setLayoutParams(layoutSeatNumberParams);
            layoutSeatNumberParams.setMargins(30,30,0,0);
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
            lblSeatNumber.setText("Kotuk: " + ticket.getTicket().getSeatNumber());
            lblSeatNumber.setTextColor(getResources().getColor(R.color.gray_dark2));
            layoutSeatNumber.addView(lblSeatNumber);
            linearLayout.addView(layoutSeatNumber);
            /* End Seat Layout */

            LinearLayout layoutTicketTypeLayout = new LinearLayout(this);
            LinearLayout.LayoutParams layoutTicketTypeLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutTicketTypeLayout.setLayoutParams(layoutTicketTypeLayoutParams);
            layoutTicketTypeLayoutParams.setMargins(30,30,0,30);
            layoutTicketTypeLayout.setOrientation(LinearLayout.HORIZONTAL);

            ImageView pbTicket = new ImageView(this);
            LinearLayout.LayoutParams pbTicketParams = new LinearLayout.LayoutParams(35, 35);
            pbTicket.setLayoutParams(pbTicketParams);
            pbTicketParams.setMargins(0,0,0,0);
            pbTicket.setImageResource(R.drawable.tickets_dark);
            layoutTicketTypeLayout.addView(pbTicket);

            TextView lblTicketType = new TextView(this);
            LinearLayout.LayoutParams lblTicketTypeParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lblTicketType.setLayoutParams(lblTicketTypeParams);
            lblTicketType.setTextSize(14);
            lblTicketType.setTypeface(null, Typeface.BOLD);
            lblTicketTypeParams.setMargins(15,2,0,0);
            lblTicketType.setText("Bilet Tipi: Normal Bilet");
            lblTicketType.setTextColor(getResources().getColor(R.color.gray_dark2));
            layoutTicketTypeLayout.addView(lblTicketType);
            linearLayout.addView(layoutTicketTypeLayout);

            Button btnTicketCancel = new Button(this);
            LinearLayout.LayoutParams btnTicketCancelParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 60);
            btnTicketCancel.setLayoutParams(btnTicketCancelParams);
            btnTicketCancel.setText("İptal et");
            btnTicketCancel.setTextSize(13);
            btnTicketCancel.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            btnTicketCancel.setGravity(Gravity.END);
            btnTicketCancelParams.setMargins(30,0,30,30);
            btnTicketCancel.setPadding(0,10,0,0);
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
                            databaseHelper.deleteTicket(ticket.getTicket().getId());
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
            customerTicketsLayout.addView(linearLayout);
        }
    }

    private void listTickets(List<TicketDtoAndUserDto> ticketsByDepartureHour){
        for (TicketDtoAndUserDto ticket : ticketsByDepartureHour) {

            LinearLayout linearLayout = new LinearLayout(getApplicationContext());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0,20,0,0);
            linearLayout.setLayoutParams(layoutParams);
            linearLayout.setBackgroundResource(R.drawable.expedition_box_background);
            linearLayout.setOrientation(LinearLayout.VERTICAL);

            /* Start From City Layout */
            LinearLayout layoutCompany = new LinearLayout(this);
            LinearLayout.LayoutParams layoutCompanyParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutCompany.setLayoutParams(layoutCompanyParams);
            layoutCompanyParams.setMargins(30,20,0,0);
            layoutCompany.setOrientation(LinearLayout.HORIZONTAL);

            ImageView pbBus = new ImageView(this);
            LinearLayout.LayoutParams pbBusParams = new LinearLayout.LayoutParams(35, 35);
            pbBus.setLayoutParams(pbBusParams);
            pbBus.setPadding(0,3,0,0);
            pbBus.setImageResource(R.drawable.person_2);
            layoutCompany.addView(pbBus);

            TextView lblCustomerName = new TextView(this);
            LinearLayout.LayoutParams lblCustomerNameParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lblCustomerName.setLayoutParams(lblCustomerNameParams);
            lblCustomerName.setTextSize(14);
            lblCustomerName.setTypeface(null, Typeface.BOLD);
            lblCustomerNameParams.setMargins(15,0,0,0);
            lblCustomerName.setText("Ad - Soyad: " + ticket.getUser().getFirstName() + " " + ticket.getUser().getLastName());
            lblCustomerName.setTextColor(getResources().getColor(R.color.gray_dark2));
            layoutCompany.addView(lblCustomerName);

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
            layoutSeatNumberParams.setMargins(30,30,0,0);
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
            /* End Seat Layout */

            LinearLayout layoutTicketTypeLayout = new LinearLayout(this);
            LinearLayout.LayoutParams layoutTicketTypeLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutTicketTypeLayout.setLayoutParams(layoutTicketTypeLayoutParams);
            layoutTicketTypeLayoutParams.setMargins(30,30,0,30);
            layoutTicketTypeLayout.setOrientation(LinearLayout.HORIZONTAL);

            ImageView pbTicket = new ImageView(this);
            LinearLayout.LayoutParams pbTicketParams = new LinearLayout.LayoutParams(35, 35);
            pbTicket.setLayoutParams(pbTicketParams);
            pbTicketParams.setMargins(0,0,0,0);
            pbTicket.setImageResource(R.drawable.tickets_dark);
            layoutTicketTypeLayout.addView(pbTicket);

            TextView lblTicketType = new TextView(this);
            LinearLayout.LayoutParams lblTicketTypeParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lblTicketType.setLayoutParams(lblTicketTypeParams);
            lblTicketType.setTextSize(14);
            lblTicketType.setTypeface(null, Typeface.BOLD);
            lblTicketTypeParams.setMargins(15,2,0,0);
            lblTicketType.setText("Bilet Tipi: Normal Bilet");
            lblTicketType.setTextColor(getResources().getColor(R.color.gray_dark2));
            layoutTicketTypeLayout.addView(lblTicketType);
            linearLayout.addView(layoutTicketTypeLayout);

            Button btnTicketCancel = new Button(this);
            LinearLayout.LayoutParams btnTicketCancelParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 60);
            btnTicketCancel.setLayoutParams(btnTicketCancelParams);
            btnTicketCancel.setText("İptal et");
            btnTicketCancel.setTextSize(13);
            btnTicketCancel.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            btnTicketCancel.setGravity(Gravity.END);
            btnTicketCancelParams.setMargins(30,0,30,30);
            btnTicketCancel.setPadding(0,10,0,0);
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
            customerTicketsLayout.addView(linearLayout);
        }
    }

    private void listTicketReservations(List<TicketReservationDto> ticketReservations){
        for (TicketReservationDto ticket : ticketReservations) {

            LinearLayout linearLayout = new LinearLayout(getApplicationContext());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0,20,0,0);
            linearLayout.setLayoutParams(layoutParams);
            linearLayout.setBackgroundResource(R.drawable.expedition_box_background);
            linearLayout.setOrientation(LinearLayout.VERTICAL);

            /* Start From City Layout */
            LinearLayout layoutCompany = new LinearLayout(this);
            LinearLayout.LayoutParams layoutCompanyParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutCompany.setLayoutParams(layoutCompanyParams);
            layoutCompanyParams.setMargins(30,20,0,0);
            layoutCompany.setOrientation(LinearLayout.HORIZONTAL);

            ImageView pbBus = new ImageView(this);
            LinearLayout.LayoutParams pbBusParams = new LinearLayout.LayoutParams(35, 35);
            pbBus.setLayoutParams(pbBusParams);
            pbBus.setPadding(0,3,0,0);
            pbBus.setImageResource(R.drawable.person_2);
            layoutCompany.addView(pbBus);

            TextView lblCustomerName = new TextView(this);
            LinearLayout.LayoutParams lblCustomerNameParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lblCustomerName.setLayoutParams(lblCustomerNameParams);
            lblCustomerName.setTextSize(14);
            lblCustomerName.setTypeface(null, Typeface.BOLD);
            lblCustomerNameParams.setMargins(15,0,0,0);
            lblCustomerName.setText("Ad - Soyad: " + ticket.getTicketReservation().getFirstName() + " " + ticket.getTicketReservation().getLastName());
            lblCustomerName.setTextColor(getResources().getColor(R.color.gray_dark2));
            layoutCompany.addView(lblCustomerName);

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
            layoutSeatNumberParams.setMargins(30,30,0,0);
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
            /* End Seat Layout */

            LinearLayout layoutTicketTypeLayout = new LinearLayout(this);
            LinearLayout.LayoutParams layoutTicketTypeLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutTicketTypeLayout.setLayoutParams(layoutTicketTypeLayoutParams);
            layoutTicketTypeLayoutParams.setMargins(30,30,0,30);
            layoutTicketTypeLayout.setOrientation(LinearLayout.HORIZONTAL);

            ImageView pbTicket = new ImageView(this);
            LinearLayout.LayoutParams pbTicketParams = new LinearLayout.LayoutParams(35, 35);
            pbTicket.setLayoutParams(pbTicketParams);
            pbTicketParams.setMargins(0,0,0,0);
            pbTicket.setImageResource(R.drawable.tickets_dark);
            layoutTicketTypeLayout.addView(pbTicket);

            TextView lblTicketType = new TextView(this);
            LinearLayout.LayoutParams lblTicketTypeParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lblTicketType.setLayoutParams(lblTicketTypeParams);
            lblTicketType.setTextSize(14);
            lblTicketType.setTypeface(null, Typeface.BOLD);
            lblTicketTypeParams.setMargins(15,2,0,0);
            lblTicketType.setText("Bilet Tipi: Rezervasyon Bilet");
            lblTicketType.setTextColor(getResources().getColor(R.color.gray_dark2));
            layoutTicketTypeLayout.addView(lblTicketType);
            linearLayout.addView(layoutTicketTypeLayout);

            Button btnTicketCancel = new Button(this);
            LinearLayout.LayoutParams btnTicketCancelParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 60);
            btnTicketCancel.setLayoutParams(btnTicketCancelParams);
            btnTicketCancel.setText("İptal et");
            btnTicketCancel.setTextSize(13);
            btnTicketCancel.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            btnTicketCancel.setGravity(Gravity.END);
            btnTicketCancelParams.setMargins(30,0,30,30);
            btnTicketCancel.setPadding(0,10,0,0);
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
            customerTicketsLayout.addView(linearLayout);
        }
    }

    private void refreshActivity(){
        customerTicketsLayout.removeAllViews();
        btnGetTickets.performClick();
    }

    private void writeDateOfBirth(){
        String myFormat="dd/MM/yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        tbTravelDate.setText(dateFormat.format(myCalendar.getTime()));
    }
}