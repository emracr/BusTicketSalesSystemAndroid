package com.ansi.busticketsalesautomation;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CompanyExpeditionsActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;

    Spinner spnTravelFrom, spnTravelTo, spnTravelHour, spnTravelMinute;
    Button btnFindExpeditions;
    LinearLayout expeditionsLayout;

    String selectedCityFrom = null;
    String selectedCityTo = null;
    String selectedTravelHour = null;
    String selectedTravelMinute = null;

    int selectedCityFromId = 0;
    int selectedCityToId = 0;
    int selectedTravelHourId = 0;
    int selectedTravelMinuteId = 0;

    Dialog expeditionRemoveDialog, expeditionEditDialog;
    Button btnYes, btnNo, btnUpdate, btnCancel;

    EditText tbTravelPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_expeditions);

        databaseHelper = new DatabaseHelper(this);

        spnTravelFrom = findViewById(R.id.spnTravelFromCompanyExpeditionsActivity);
        spnTravelTo = findViewById(R.id.spnTravelToCompanyExpeditionsActivity);
        btnFindExpeditions = findViewById(R.id.btnFindExpeditionsActivityExpeditionsCompany);
        expeditionsLayout = findViewById(R.id.expeditionsLayoutCompanyExpeditionsActivity);

        /* Dialog For Expedition Delete */
        expeditionRemoveDialog = new Dialog(this);
        expeditionRemoveDialog.setContentView(R.layout.expedition_remove_dialog);
        expeditionRemoveDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.ticket_cancel_dialog_background));
        expeditionRemoveDialog.getWindow().setLayout(600, ViewGroup.LayoutParams.WRAP_CONTENT);
        expeditionRemoveDialog.setCancelable(false);
        expeditionRemoveDialog.getWindow().getAttributes().windowAnimations = R.style.ExpeditionRemoveDialogAnimation;

        /* Dialog For Expedition Edit */
        expeditionEditDialog = new Dialog(this);
        expeditionEditDialog.setContentView(R.layout.expedition_edit_dialog);
        expeditionEditDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.ticket_cancel_dialog_background));
        expeditionEditDialog.getWindow().setLayout(600, ViewGroup.LayoutParams.WRAP_CONTENT);
        expeditionEditDialog.setCancelable(false);
        expeditionEditDialog.getWindow().getAttributes().windowAnimations = R.style.ExpeditionEditDialogAnimation;


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

        List<ExpeditionDto> allExpeditions = databaseHelper.getExpeditionsDtoByCompanyId(CompanyCredentials.COMPANY.getId());
        listExpeditions(allExpeditions);

        btnFindExpeditions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(selectedCityFromId <= 0 && selectedCityToId <= 0){
                    List<ExpeditionDto> allExpeditions = databaseHelper.getExpeditionsDtoByCompanyId(CompanyCredentials.COMPANY.getId());
                    listExpeditions(allExpeditions);
                }
                else if (selectedCityFromId <= 0 && selectedCityToId > 0){
                    DialogBox.showDialog(getSupportFragmentManager(),"Uyarı","Lütfen kalkış şehri seçin");
                }
                else if(selectedCityToId <= 0 && selectedCityFromId > 0){
                    DialogBox.showDialog(getSupportFragmentManager(),"Uyarı","Lütfen varış şehri seçin");
                }
                else{
                    List<ExpeditionDto> expeditions = databaseHelper.getExpeditionsByCompanyIdAndCityFromAndCityTo(CompanyCredentials.COMPANY.getId(), selectedCityFrom, selectedCityTo);
                    listExpeditions(expeditions);
                }
            }
        });

    }

    private void listExpeditions(List<ExpeditionDto> expeditions){
        expeditionsLayout.removeAllViews();

        if (expeditions.size() > 0){

            for (ExpeditionDto expedition : expeditions) {

                LinearLayout linearLayout = new LinearLayout(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(40,25,40,10);
                linearLayout.setLayoutParams(layoutParams);
                linearLayout.setBackgroundResource(R.drawable.expedition_box_background);
                linearLayout.setOrientation(LinearLayout.VERTICAL);

                TextView lblCityFromToCityTo = new TextView(this);
                LinearLayout.LayoutParams lblCityFromToCityToParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lblCityFromToCityToParams.setMargins(10,15,10,0);
                lblCityFromToCityTo.setLayoutParams(lblCityFromToCityToParams);
                lblCityFromToCityTo.setText(expedition.getExpedition().getCityFrom() + " - " + expedition.getExpedition().getCityTo());
                lblCityFromToCityTo.setTextSize(16);
                lblCityFromToCityTo.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                lblCityFromToCityTo.setTypeface(null, Typeface.BOLD);
                lblCityFromToCityTo.setTextColor(getResources().getColor(R.color.gray_dark2));
                linearLayout.addView(lblCityFromToCityTo);

                LinearLayout linearLayoutTravelTime = new LinearLayout(this);
                LinearLayout.LayoutParams layoutTravelTimeParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutTravelTimeParams.setMargins(0,20,0,0);
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
                lblPrice.setTextSize(16);
                lblPrice.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                lblPrice.setTypeface(null, Typeface.BOLD);
                lblPrice.setTextColor(getResources().getColor(R.color.gray_dark2));
                linearLayout.addView(lblPrice);

                LinearLayout linearLayoutExpeditionRemoveAndEdit = new LinearLayout(this);
                LinearLayout.LayoutParams linearLayoutExpeditionRemoveAndEditParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                linearLayoutExpeditionRemoveAndEditParams.setMargins(0,5,0,0);
                linearLayoutExpeditionRemoveAndEdit.setLayoutParams(linearLayoutExpeditionRemoveAndEditParams);
                linearLayoutExpeditionRemoveAndEdit.setGravity(Gravity.CENTER);
                linearLayoutExpeditionRemoveAndEdit.setOrientation(LinearLayout.HORIZONTAL);
                linearLayout.addView(linearLayoutExpeditionRemoveAndEdit);

                Button btnRemoveExpedition = new Button(this);
                LinearLayout.LayoutParams btnRemoveExpeditionParams = new LinearLayout.LayoutParams(200, 65);
                btnRemoveExpedition.setLayoutParams(btnRemoveExpeditionParams);
                btnRemoveExpedition.setId(expedition.getExpedition().getId());
                btnRemoveExpeditionParams.gravity= Gravity.CENTER;
                btnRemoveExpeditionParams.setMargins(40,10,0,20);
                btnRemoveExpedition.setBackgroundResource(R.drawable.remove_expedition_button_design);
                btnRemoveExpedition.setTextColor(getResources().getColor(R.color.white));
                btnRemoveExpedition.setTextSize(11);
                btnRemoveExpedition.setAllCaps(false);
                btnRemoveExpedition.setText("Seferi Sil");
                btnRemoveExpedition.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        btnYes = expeditionRemoveDialog.findViewById(R.id.btnExpeditionRemoveYes);
                        btnNo = expeditionRemoveDialog.findViewById(R.id.btnExpeditionRemoveNo);

                        btnYes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                databaseHelper.deleteExpedition(expedition.getExpedition().getId());
                                deleteAllTicketsByExpeditionId(expedition.getExpedition().getId());
                                Toast.makeText(getApplicationContext(),"Sefer başarılı bir şekilde silindi", Toast.LENGTH_SHORT).show();
                                expeditionRemoveDialog.dismiss();
                                refreshActivity();
                            }
                        });
                        btnNo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                expeditionRemoveDialog.dismiss();
                            }
                        });
                        expeditionRemoveDialog.show();
                    }
                });
                linearLayoutExpeditionRemoveAndEdit.addView(btnRemoveExpedition);

                Button btnEditExpedition = new Button(this);
                LinearLayout.LayoutParams btnEditExpeditionParams = new LinearLayout.LayoutParams(200, 65);
                btnEditExpedition.setLayoutParams(btnEditExpeditionParams);
                btnEditExpedition.setId(expedition.getExpedition().getId());
                btnEditExpeditionParams.gravity= Gravity.CENTER;
                btnEditExpeditionParams.setMargins(30,10,40,20);
                btnEditExpedition.setBackgroundResource(R.drawable.edit_expedition_button_design);
                btnEditExpedition.setTextColor(getResources().getColor(R.color.gray_dark2));
                btnEditExpedition.setTextSize(11);
                btnEditExpedition.setAllCaps(false);
                btnEditExpedition.setText("Seferi Güncelle");
                btnEditExpedition.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        btnUpdate = expeditionEditDialog.findViewById(R.id.btnUpdateDialogEditExpedition);
                        btnCancel = expeditionEditDialog.findViewById(R.id.btnCancelDialogEditExpedition);
                        spnTravelHour = expeditionEditDialog.findViewById(R.id.spnTravelHourEditExpeditionDialog);
                        spnTravelMinute = expeditionEditDialog.findViewById(R.id.spnTravelMinuteEditExpeditionDialog);
                        tbTravelPrice = expeditionEditDialog.findViewById(R.id.tbTravelPriceDialogEditExpedition);

                        loadDepartureHourAndMinuteToSpinner();

                        String[] departureTimeSplit = expedition.getExpedition().getDepartureTime().split(":");

                        spnTravelHour.setSelection(Integer.parseInt(departureTimeSplit[0]) + 1);
                        spnTravelMinute.setSelection(Integer.parseInt(departureTimeSplit[1]) + 1);
                        tbTravelPrice.setText(String.valueOf(expedition.getExpedition().getPrice()));

                        btnUpdate.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                if (selectedTravelHourId <= 0){
                                    DialogBox.showDialog(getSupportFragmentManager(),"Uyarı","Sefer saati boş bırakılamaz");
                                }
                                else if(selectedTravelMinuteId <= 0){
                                    DialogBox.showDialog(getSupportFragmentManager(),"Uyarı","Sefer dakikası boş bırakılamaz");
                                }
                                else if(tbTravelPrice.getText().toString().isEmpty() || tbTravelPrice.getText().toString().length() <= 0){
                                    DialogBox.showDialog(getSupportFragmentManager(),"Uyarı","Sefer fiyatı boş bırakılamaz");
                                }
                                else{
                                    String departureTime = selectedTravelHour + ":" + selectedTravelMinute;
                                    int price = Integer.parseInt(tbTravelPrice.getText().toString());

                                    Expedition expeditionUpdate = expedition.getExpedition();
                                    expeditionUpdate.setDepartureTime(departureTime);
                                    expeditionUpdate.setPrice(price);
                                    boolean result = updateExpedition(expeditionUpdate);
                                    if (result){
                                        Toast.makeText(getApplicationContext(),"Sefer başarılı bir şekilde güncellendi",Toast.LENGTH_SHORT).show();
                                        expeditionEditDialog.dismiss();
                                        refreshActivity();
                                    }
                                    else{
                                        DialogBox.showDialog(getSupportFragmentManager(), "Hata", "Beklenmedik bir hata oluştu!");
                                    }
                                }
                            }
                        });

                        btnCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                expeditionEditDialog.dismiss();
                            }
                        });

                        expeditionEditDialog.show();

                    }
                });
                linearLayoutExpeditionRemoveAndEdit.addView(btnEditExpedition);

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
            lblExpeditionNotFound.setText(selectedCityFrom + " - " + selectedCityTo + " güzergahları arasında sefer bulunmamaktadır!");
            lblExpeditionNotFound.setTextColor(getResources().getColor(R.color.gray_dark2));
            expeditionsLayout.addView(lblExpeditionNotFound);
        }
    }

    private void loadDepartureHourAndMinuteToSpinner(){

        String[] hours = new String[] {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"};
        String[] minutes = new String[] {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59"};

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

    }

    private void refreshActivity(){
        expeditionsLayout.removeAllViews();
        btnFindExpeditions.performClick();
    }

    private void deleteAllTicketsByExpeditionId(int expeditionId){
        databaseHelper.deleteTicketsByExpeditionId(expeditionId);
    }

    private boolean updateExpedition(Expedition expedition){
        return databaseHelper.updateExpedition(expedition);
    }
}