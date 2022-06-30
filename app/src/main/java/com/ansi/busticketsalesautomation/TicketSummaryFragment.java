package com.ansi.busticketsalesautomation;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TicketSummaryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TicketSummaryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TicketSummaryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TicketSummaryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TicketSummaryFragment newInstance(String param1, String param2) {
        TicketSummaryFragment fragment = new TicketSummaryFragment();
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

    TextView lblFromCity, lblToCity, lblTravelDate, lblTravelHour, lblSeat, lblGender, lblPrice;
    Button btnGoToPayment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_ticket_summary, container, false);

        lblFromCity = view.findViewById(R.id.lblFromCityFragmentTicketSummary);
        lblToCity = view.findViewById(R.id.lblToCityFragmentTicketSummary);
        lblTravelDate = view.findViewById(R.id.lblTravelDateFragmentTicketSummary);
        lblTravelHour = view.findViewById(R.id.lblTravelHourFragmentTicketSummary);
        lblSeat = view.findViewById(R.id.lblSeatFragmentTicketSummary);
        lblGender = view.findViewById(R.id.lblGenderFragmentTicketSummary);
        lblPrice = view.findViewById(R.id.lblPriceFragmentTicketSummary);
        btnGoToPayment = view.findViewById(R.id.btnGoToPayment);

        lblFromCity.setText("Nereden: " + TravelInformation.CITY_FROM);
        lblToCity.setText("Nereye: " + TravelInformation.CITY_TO);
        lblTravelDate.setText("Tarih: " + TravelInformation.DEPARTURE_DATE_LONG);
        lblTravelHour.setText("Saat: " + TravelInformation.DEPARTURE_HOUR);
        lblSeat.setText("Koltuk No: " + TravelInformation.SEAT_NUMBER);
        lblGender.setText("Cinsiyet: " + TravelInformation.GENDER);
        lblPrice.setText("Fiyat: " + TravelInformation.PRICE + ",00 ₺");

        btnGoToPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReplaceFragment.replaceFragment(new PaymentFragment(), getActivity());
            }
        });

        return view;
    }
}