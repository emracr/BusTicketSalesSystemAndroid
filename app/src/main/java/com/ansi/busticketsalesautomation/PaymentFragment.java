package com.ansi.busticketsalesautomation;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PaymentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PaymentFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PaymentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PaymentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PaymentFragment newInstance(String param1, String param2) {
        PaymentFragment fragment = new PaymentFragment();
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

    Spinner spnMonthNumber, spnYear;
    EditText tbCardNumber, tbCardHolder, tbCCV;
    Button btnComplete;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment, container, false);

        databaseHelper = new DatabaseHelper(getContext());

        spnMonthNumber = view.findViewById(R.id.spnMonthNumberPaymentFragment);
        spnYear = view.findViewById(R.id.spnYearPaymentFragment);
        tbCardNumber = view.findViewById(R.id.tbCardNumber);
        tbCardHolder = view.findViewById(R.id.tbCardHolder);
        tbCCV = view.findViewById(R.id.tbCCVPaymentFragment);
        btnComplete = view.findViewById(R.id.btnCompletePaymentFragment);

        /* Start Month Number Spinner */
        ArrayList<String> monthNumbers = new ArrayList<>();
        for (int i = 1; i <= 12 ; i++) {
            monthNumbers.add(String.valueOf(i));
        }
        ArrayAdapter<String> spnMonthNumberAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, monthNumbers);
        spnMonthNumberAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spnMonthNumber.setAdapter(spnMonthNumberAdapter);
        spnMonthNumber.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        /* End Month Number Spinner */

        /* Start Year Spinner */
        ArrayList<String> years = new ArrayList<>();
        for (int i = getCurrentYear(); i <= getCurrentYear() + 15 ; i++) {
            years.add(String.valueOf(i));
        }
        ArrayAdapter<String> spnYearAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, years);
        spnYearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spnYear.setAdapter(spnYearAdapter);
        spnYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        /* End Year Spinner */

        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String cardNumber = tbCardNumber.getText().toString();
                String cardHolder = tbCardHolder.getText().toString();
                String ccv = tbCCV.getText().toString();

                if (cardNumber.isEmpty() || cardNumber.equals(null)){
                    tbCardNumber.setError("Kart numarası boş bırakılamaz");
                }
                else if(cardNumber.length() < 16){
                    tbCardNumber.setError("Kart numarası 16 rakamdan oluşması gerek");
                }
                else if(cardHolder.isEmpty() || cardHolder.equals(null)){
                    tbCardHolder.setError("Kart sahibi boş bırakılamaz");
                }
                else if(ccv.isEmpty() || ccv.equals(null)){
                    tbCCV.setError("CCV boş bırakılamaz");
                }
                else{
                    Ticket ticket = new Ticket(Credentials.USER.getId(), TravelInformation.EXPEDITION_ID, TravelInformation.DEPARTURE_DATE, TravelInformation.GENDER, TravelInformation.SEAT_NUMBER, TravelInformation.PRICE);
                    databaseHelper.insertTicket(ticket);
                    ReplaceFragment.replaceFragment(new TicketPurchasedSuccessfullyFragment(), getActivity());
                }

            }
        });

        return view;
    }


    public int getCurrentYear(){
        Date date = new Date();
        int year = date.getYear();
        return year + 1900;
    }
}