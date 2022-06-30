package com.ansi.busticketsalesautomation;

import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExpeditionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExpeditionFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ExpeditionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExpeditionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExpeditionFragment newInstance(String param1, String param2) {
        ExpeditionFragment fragment = new ExpeditionFragment();
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

    LinearLayout expeditionsLayout;
    TextView lblCityFromCityTo, lblTravelDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_expedition, container, false);

        databaseHelper = new DatabaseHelper(getContext());

        expeditionsLayout = view.findViewById(R.id.expeditionsLayoutFragmentExpedition);
        lblCityFromCityTo = view.findViewById(R.id.lblCityFromCityToFragmentExpedition);
        lblTravelDate = view.findViewById(R.id.lblTravelDateFragmentExpedition);

        lblCityFromCityTo.setText(TravelInformation.CITY_FROM + "   >  " + TravelInformation.CITY_TO);
        lblTravelDate.setText(convertDateToLocaleDate(TravelInformation.DEPARTURE_DATE));

        List<ExpeditionDto> expeditions = databaseHelper.getExpeditionsByCityFromAndCityTo(TravelInformation.CITY_FROM, TravelInformation.CITY_TO);

        if (expeditions.size() > 0){

            for (ExpeditionDto expedition : expeditions) {

                LinearLayout linearLayout = new LinearLayout(view.getContext());
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(40,25,40,10);
                linearLayout.setLayoutParams(layoutParams);
                linearLayout.setBackgroundResource(R.drawable.expedition_box_background);
                linearLayout.setOrientation(LinearLayout.VERTICAL);

                TextView lblCompanyName = new TextView(view.getContext());
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
                LinearLayout linearLayoutLine = new LinearLayout(view.getContext());
                LinearLayout.LayoutParams layoutLineParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 3);
                layoutLineParams.setMargins(40,15,40,5);
                linearLayoutLine.setLayoutParams(layoutLineParams);
                linearLayoutLine.setBackgroundResource(R.color.gray2);
                linearLayoutLine.setOrientation(LinearLayout.VERTICAL);
                linearLayout.addView(linearLayoutLine);

                LinearLayout linearLayoutTravelTime = new LinearLayout(view.getContext());
                LinearLayout.LayoutParams layoutTravelTimeParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutTravelTimeParams.setMargins(0,25,0,0);
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

                Button btnRedirectToSeat = new Button(view.getContext());
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
                        TravelInformation.EXPEDITION_ID = view.getId();
                        TravelInformation.DEPARTURE_HOUR = expedition.getExpedition().getDepartureTime();
                        TravelInformation.PRICE = expedition.getExpedition().getPrice();
                        ReplaceFragment.replaceFragment(new SeatSelectionFragment(), getActivity());
                    }
                });

                linearLayout.addView(btnRedirectToSeat);


                expeditionsLayout.setPadding(0,20,0,120);
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
            lblExpeditionNotFound.setText(TravelInformation.DEPARTURE_DATE + " tarihinde, " + TravelInformation.CITY_FROM + " - " + TravelInformation.CITY_TO + " güzergahları arasında sefer bulunmamaktadır!");
            lblExpeditionNotFound.setTextColor(getResources().getColor(R.color.gray_dark2));
            expeditionsLayout.addView(lblExpeditionNotFound);
        }

        return view;
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

        TravelInformation.DEPARTURE_DATE_LONG = newDate + " " + splitDate[2];

        return newDate;
    }
}