package com.ansi.busticketsalesautomation;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SeatSelectionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SeatSelectionFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SeatSelectionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SeatSelectionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SeatSelectionFragment newInstance(String param1, String param2) {
        SeatSelectionFragment fragment = new SeatSelectionFragment();
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

    int selectedSeatNumber = 0;

    Button btnSeat1, btnSeat2, btnSeat3, btnSeat4, btnSeat5, btnSeat6, btnSeat7, btnSeat8, btnSeat9, btnSeat10,
            btnSeat11, btnSeat12,btnSeat13,btnSeat14,btnSeat15,btnSeat16,btnSeat17,btnSeat18,btnSeat19,
            btnSeat20,btnSeat21,btnSeat22,btnSeat23,btnSeat24;

    Button btnConfirmSeat;
    List<View> seats;
    List<View> soldSeats;

    Dialog genderSelectionDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_seat_selection, container, false);

        databaseHelper = new DatabaseHelper(getContext());

        genderSelectionDialog = new Dialog(getContext());
        genderSelectionDialog.setContentView(R.layout.gender_selection_dialog);
        genderSelectionDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.gender_selection_dialog_background));
        genderSelectionDialog.getWindow().setLayout(500, ViewGroup.LayoutParams.WRAP_CONTENT);
        genderSelectionDialog.setCancelable(false);
        genderSelectionDialog.getWindow().getAttributes().windowAnimations = R.style.GenderSelectionDialogAnimation;

        Button btnMan = genderSelectionDialog.findViewById(R.id.btnManGenderSelectionDialog);
        Button btnWoman = genderSelectionDialog.findViewById(R.id.btnWomanGenderSelectionDialog);

        btnMan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TravelInformation.GENDER = "Erkek";
                btnConfirmSeat.setVisibility(View.VISIBLE);
                genderSelectionDialog.dismiss();
            }
        });

        btnWoman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TravelInformation.GENDER = "Kadın";
                btnConfirmSeat.setVisibility(View.VISIBLE);
                genderSelectionDialog.dismiss();
            }
        });


        btnSeat1 = view.findViewById(R.id.btnSeat1);
        btnSeat2 = view.findViewById(R.id.btnSeat2);
        btnSeat3 = view.findViewById(R.id.btnSeat3);
        btnSeat4 = view.findViewById(R.id.btnSeat4);
        btnSeat5 = view.findViewById(R.id.btnSeat5);
        btnSeat6 = view.findViewById(R.id.btnSeat6);
        btnSeat7 = view.findViewById(R.id.btnSeat7);
        btnSeat8 = view.findViewById(R.id.btnSeat8);
        btnSeat9 = view.findViewById(R.id.btnSeat9);
        btnSeat10 = view.findViewById(R.id.btnSeat10);
        btnSeat11 = view.findViewById(R.id.btnSeat11);
        btnSeat12 = view.findViewById(R.id.btnSeat12);
        btnSeat13 = view.findViewById(R.id.btnSeat13);
        btnSeat14 = view.findViewById(R.id.btnSeat14);
        btnSeat15 = view.findViewById(R.id.btnSeat15);
        btnSeat16 = view.findViewById(R.id.btnSeat16);
        btnSeat17 = view.findViewById(R.id.btnSeat17);
        btnSeat18 = view.findViewById(R.id.btnSeat18);
        btnSeat19 = view.findViewById(R.id.btnSeat19);
        btnSeat20 = view.findViewById(R.id.btnSeat20);
        btnSeat21 = view.findViewById(R.id.btnSeat21);
        btnSeat22 = view.findViewById(R.id.btnSeat22);
        btnSeat23 = view.findViewById(R.id.btnSeat23);
        btnSeat24 = view.findViewById(R.id.btnSeat24);
        btnConfirmSeat = view.findViewById(R.id.btnConfirmSeat);

        seats = new ArrayList<>();
        seats.add(btnSeat1);
        seats.add(btnSeat2);
        seats.add(btnSeat3);
        seats.add(btnSeat4);
        seats.add(btnSeat5);
        seats.add(btnSeat6);
        seats.add(btnSeat7);
        seats.add(btnSeat8);
        seats.add(btnSeat9);
        seats.add(btnSeat10);
        seats.add(btnSeat11);
        seats.add(btnSeat12);
        seats.add(btnSeat13);
        seats.add(btnSeat14);
        seats.add(btnSeat15);
        seats.add(btnSeat16);
        seats.add(btnSeat17);
        seats.add(btnSeat18);
        seats.add(btnSeat19);
        seats.add(btnSeat20);
        seats.add(btnSeat21);
        seats.add(btnSeat22);
        seats.add(btnSeat23);
        seats.add(btnSeat24);

        List<TicketDto> tickets = databaseHelper.getTicketsByExpeditionIdAndDepartureDate(TravelInformation.EXPEDITION_ID, TravelInformation.DEPARTURE_DATE);
        List<TicketReservation> reservations = databaseHelper.getTicketReservationsByExpeditionIdAndDepartureDate(TravelInformation.EXPEDITION_ID, TravelInformation.DEPARTURE_DATE);

        soldSeats = new ArrayList<>();
        for (TicketDto ticket : tickets) {
            for (View seat : seats) {
                int seatNumber = Integer.parseInt(((Button)seat).getText().toString());
                if (seatNumber == ticket.getTicket().getSeatNumber()){
                    soldTicketAction(seat, ticket.getTicket().getGender());
                    soldSeats.add(seat);
                }
            }
        }

        for (TicketReservation reservation : reservations) {
            for (View seat : seats) {
                int seatNumber = Integer.parseInt(((Button)seat).getText().toString());
                if (seatNumber == reservation.getSeatNumber()){
                    soldTicketAction(seat, reservation.getGender());
                    soldSeats.add(seat);
                }
            }
        }

        for (View seat : soldSeats) {
            seats.remove(seat);
        }

        for (View seat : seats) {
            selectedSeatAction(seat);
        }

        btnConfirmSeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedSeatNumber > 0){
                    TravelInformation.SEAT_NUMBER = selectedSeatNumber;
                    ReplaceFragment.replaceFragment(new TicketSummaryFragment(), getActivity());
                }
            }
        });

        return view;
    }

    private void seatBackgroundReset(List<View> seats){
        for (View seat : seats) {
            seat.setBackground(getResources().getDrawable(R.drawable.armchair));
        }
    }

    private void selectedSeatAction(View view){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seatBackgroundReset(seats);
                view.setBackground(getResources().getDrawable(R.drawable.armchair_selected));
                selectedSeatNumber = Integer.parseInt(((Button)view).getText().toString());
                genderSelectionDialog.show();
            }
        });
    }

    private void soldTicketAction(View button, String gender){

        if (gender.equals("Erkek")){
            button.setBackground(getResources().getDrawable(R.drawable.armchair_man));
        }
        else{
            button.setBackground(getResources().getDrawable(R.drawable.armchair_woman));
        }
        button.setEnabled(false);

    }
}