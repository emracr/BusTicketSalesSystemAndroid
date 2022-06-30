package com.ansi.busticketsalesautomation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AdminReservationTravelInfoActivity extends AppCompatActivity {

    final Calendar myCalendar= Calendar.getInstance();

    Spinner spnTravelFrom, spnTravelTo;
    EditText tbTravelDate;
    ImageView pbSwap;
    Button btnGetTravels;

    String selectedCityFrom = null;
    String selectedCityTo = null;

    String selectedTravelDate = null;

    int selectedCityFromId = 0;
    int selectedCityToId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_reservation_travel_info);

        tbTravelDate = findViewById(R.id.tbTravelDateTicketReservationActivityAdmin);
        spnTravelFrom = findViewById(R.id.spnTravelFromTicketReservationActivityAdmin);
        spnTravelTo = findViewById(R.id.spnTravelToTicketReservationActivityAdmin);
        pbSwap = findViewById(R.id.pbSwapTicketReservationActivityAdmin);
        btnGetTravels = findViewById(R.id.btnGetExpeditionsTicketReservationActivityAdmin);

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
                DatePickerDialog datePickerDialog = new DatePickerDialog(AdminReservationTravelInfoActivity.this, date, myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH));
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

        /* Start Swap */
        pbSwap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (spnTravelFrom.getSelectedItemId() > 0 && spnTravelTo.getSelectedItemId() > 0){
                    int travelFromCityIndex = (int)spnTravelFrom.getSelectedItemId();
                    int travelToCityIndex = (int)spnTravelTo.getSelectedItemId();
                    spnTravelFrom.setSelection(travelToCityIndex);
                    spnTravelTo.setSelection(travelFromCityIndex);
                }
            }
        });
        /* End Swap */

        /* Start Get Travels */
        btnGetTravels.setOnClickListener(new View.OnClickListener() {
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
                    ReservationInformation.CITY_FROM = selectedCityFrom;
                    ReservationInformation.CITY_TO = selectedCityTo;
                    ReservationInformation.DEPARTURE_DATE = tbTravelDate.getText().toString();
                    Intent intent = new Intent(getApplicationContext(), AdminReservationExpeditionsActivity.class);
                    startActivity(intent);
                }
            }
        });
        /* End Get Travels */

    }

    private void writeDateOfBirth(){
        String myFormat="dd/MM/yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        tbTravelDate.setText(dateFormat.format(myCalendar.getTime()));
    }
}