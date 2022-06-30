package com.ansi.busticketsalesautomation;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FindTicketFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FindTicketFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FindTicketFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FindTicketFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FindTicketFragment newInstance(String param1, String param2) {
        FindTicketFragment fragment = new FindTicketFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    DatabaseHelper databaseHelper;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find_ticket, container, false);

        databaseHelper = new DatabaseHelper(getContext());

        tbTravelDate = view.findViewById(R.id.tbTravelDateFragmentFindTicket);
        spnTravelFrom = view.findViewById(R.id.spnTravelFromFindTicketFragment);
        spnTravelTo = view.findViewById(R.id.spnTravelToFindTicketFragment);
        pbSwap = view.findViewById(R.id.pbSwapFragmentFindTicket);
        btnGetTravels = view.findViewById(R.id.btnGetTravels);

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
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), date, myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH));
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
        ArrayAdapter<String> spnTravelCityFromAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, citiesFrom);
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
        ArrayAdapter<String> spnTravelCityToAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, citiesTo);
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
                    DialogBox.showDialog(getChildFragmentManager(),"Uyarı","Lütfen kalkış şehri seçin");
                }
                else if(selectedCityToId <= 0){
                    DialogBox.showDialog(getChildFragmentManager(),"Uyarı","Lütfen varış şehri seçin");
                }
                else if(tbTravelDate.getText().toString().isEmpty()){
                    DialogBox.showDialog(getChildFragmentManager(),"Uyarı","Lütfen seyahat tarihi seçin");
                }
                else{
                    TravelInformation.CITY_FROM = selectedCityFrom;
                    TravelInformation.CITY_TO = selectedCityTo;
                    TravelInformation.DEPARTURE_DATE = tbTravelDate.getText().toString();
                    ReplaceFragment.replaceFragment(new ExpeditionFragment(), getActivity());
                    //List<ExpeditionDto> expeditions = databaseHelper.getExpeditionsByCityFromAndCityTo(selectedCityFrom, selectedCityTo);
                }
            }
        });
        /* End Get Travels */

        return view;
    }

    private void writeDateOfBirth(){
        String myFormat="dd/MM/yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        tbTravelDate.setText(dateFormat.format(myCalendar.getTime()));
    }
}