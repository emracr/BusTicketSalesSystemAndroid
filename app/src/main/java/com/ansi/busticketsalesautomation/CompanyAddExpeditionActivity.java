package com.ansi.busticketsalesautomation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CompanyAddExpeditionActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;

    Spinner spnTravelFrom, spnTravelTo, spnTravelHour, spnTravelMinute;
    EditText tbTravelPrice;
    Button btnAddExpedition;

    String selectedCityFrom = null;
    String selectedCityTo = null;
    String selectedTravelHour = null;
    String selectedTravelMinute = null;

    int selectedCityFromId = 0;
    int selectedCityToId = 0;
    int selectedTravelHourId = 0;
    int selectedTravelMinuteId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_add_expedition);

        databaseHelper = new DatabaseHelper(this);

        spnTravelFrom = findViewById(R.id.spnTravelFromAddExpeditionActivity);
        spnTravelTo = findViewById(R.id.spnTravelToAddExpeditionActivity);
        spnTravelHour = findViewById(R.id.spnTravelHourAddExpeditionActivity);
        spnTravelMinute = findViewById(R.id.spnTravelMinuteAddExpeditionActivity);
        tbTravelPrice = findViewById(R.id.tbTravelPriceAddExpeditionActivity);
        btnAddExpedition = findViewById(R.id.btnAddExpedition);

        /* Start All Cities Array Define */
        String[] cities = new String[] {"Adana", "Adıyaman", "Afyon", "Ağrı", "Amasya", "Ankara", "Antalya", "Artvin", "Aydın", "Balıkesir", "Bilecik", "Bingöl", "Bitlis", "Bolu", "Burdur", "Bursa", "Çanakkale", "Çankırı", "Çorum", "Denizli", "Diyarbakır", "Edirne", "Elazığ", "Erzincan", "Erzurum", "Eskişehir", "Gaziantep", "Giresun", "Gümüşhane", "Hakkari", "Hatay", "Isparta", "İçel (Mersin)", "İstanbul", "İzmir", "Kars", "Kastamonu", "Kayseri", "Kırklareli", "Kırşehir", "Kocaeli", "Konya", "Kütahya", "Malatya", "Manisa", "Kahramanmaraş", "Mardin", "Muğla", "Muş", "Nevşehir", "Niğde", "Ordu", "Rize", "Sakarya", "Samsun", "Siirt", "Sinop", "Sivas", "Tekirdağ", "Tokat", "Trabzon", "Tunceli", "Şanlıurfa", "Uşak", "Van", "Yozgat", "Zonguldak", "Aksaray", "Bayburt", "Karaman", "Kırıkkale", "Batman", "Şırnak", "Bartın", "Ardahan", "Iğdır", "Yalova", "Karabük", "Kilis", "Osmaniye", "Düzce"};
        /* End All Cities Array Define */

        String[] hours = new String[] {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"};
        String[] minutes = new String[] {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59"};


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

        /* Start Hours List Spinner */
        ArrayList<String> hoursList = new ArrayList<>();
        hoursList.add("Saat");

        for (String hour : hours) {
            hoursList.add(hour);
        }
        ArrayAdapter<String> spnTravelHourAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, hoursList);
        spnTravelHourAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spnTravelHour.setAdapter(spnTravelHourAdapter);

        spnTravelHour.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedTravelHour = spnTravelHour.getSelectedItem().toString();
                selectedTravelHourId = (int)spnTravelHour.getSelectedItemId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        /* End Hours List Spinner */

        /* Start Minutes List Spinner */
        ArrayList<String> minutesList = new ArrayList<>();
        minutesList.add("Dakika");

        for (String minute : minutes) {
            minutesList.add(minute);
        }
        ArrayAdapter<String> spnTravelMinuteAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, minutesList);
        spnTravelMinuteAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spnTravelMinute.setAdapter(spnTravelMinuteAdapter);

        spnTravelMinute.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedTravelMinute = spnTravelMinute.getSelectedItem().toString();
                selectedTravelMinuteId = (int)spnTravelMinute.getSelectedItemId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        /* End Minutes List Spinner */

        btnAddExpedition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String expeditionHour = null;
                String price = tbTravelPrice.getText().toString();

                if (selectedCityFromId <= 0){
                    DialogBox.showDialog(getSupportFragmentManager(),"Uyarı","Lütfen kalkış şehri seçin");
                }
                else if(selectedCityToId <= 0){
                    DialogBox.showDialog(getSupportFragmentManager(),"Uyarı","Lütfen varış şehri seçin");
                }
                else if(selectedTravelHourId <= 0){
                    DialogBox.showDialog(getSupportFragmentManager(),"Uyarı","Sefer saati boş bırakılamaz");
                }
                else if(selectedTravelMinuteId <= 0){
                    DialogBox.showDialog(getSupportFragmentManager(),"Uyarı","Sefer dakikası boş bırakılamaz");
                }
                else if(price.isEmpty() || price.equals(null)){
                    DialogBox.showDialog(getSupportFragmentManager(),"Uyarı","Sefer fiyatı boş bırakılamaz");
                }
                else{
                    expeditionHour = selectedTravelHour + ":" + selectedTravelMinute;
                    Expedition expedition = new Expedition(CompanyCredentials.COMPANY.getId(), selectedCityFrom, selectedCityTo, expeditionHour, Integer.parseInt(price));
                    boolean result = insertExpedition(expedition);
                    if (result){
                        Toast.makeText(getApplicationContext(), "Sefer başarılı bir şekilde eklendi", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), CompanyHomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        DialogBox.showDialog(getSupportFragmentManager(),"Hata","Beklenmedik bir hata oluştu!");
                    }
                }
            }
        });
    }

    private boolean insertExpedition(Expedition expedition){
        return databaseHelper.insertExpedition(expedition);
    }

}