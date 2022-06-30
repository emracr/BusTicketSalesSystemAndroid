package com.ansi.busticketsalesautomation;

import android.app.Dialog;
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
import android.widget.Toast;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyTicketsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyTicketsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MyTicketsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyTicketsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyTicketsFragment newInstance(String param1, String param2) {
        MyTicketsFragment fragment = new MyTicketsFragment();
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

    LinearLayout myTicketsLayout;

    Dialog ticketCancelDialog;
    Button btnYes, btnNo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_tickets, container, false);

        databaseHelper = new DatabaseHelper(getContext());

        myTicketsLayout = view.findViewById(R.id.myTicketsLayoutFragmentMyTicket);

        ticketCancelDialog = new Dialog(getContext());
        ticketCancelDialog.setContentView(R.layout.ticket_cancel_dialog);
        ticketCancelDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.ticket_cancel_dialog_background));
        ticketCancelDialog.getWindow().setLayout(600, ViewGroup.LayoutParams.WRAP_CONTENT);
        ticketCancelDialog.setCancelable(false);
        ticketCancelDialog.getWindow().getAttributes().windowAnimations = R.style.TicketCancelDialogAnimation;


        List<TicketDto> tickets = databaseHelper.getFutureTicketByUserId(Credentials.USER.getId());
        List<TicketDto> oldTickets = databaseHelper.getOldTicketByUserId(Credentials.USER.getId());

        if (tickets.size() > 0 || oldTickets.size() > 0){

            if (tickets.size() > 0){
                for (TicketDto ticket : tickets) {
                    LinearLayout linearLayout = new LinearLayout(view.getContext());
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(40,20,40,5);
                    linearLayout.setLayoutParams(layoutParams);
                    linearLayout.setBackgroundResource(R.drawable.my_ticket_box_background);
                    linearLayout.setOrientation(LinearLayout.VERTICAL);

                    /* Start From City Layout */
                    LinearLayout layoutCompany = new LinearLayout(view.getContext());
                    LinearLayout.LayoutParams layoutCompanyParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutCompany.setLayoutParams(layoutCompanyParams);
                    layoutCompanyParams.setMargins(30,20,0,0);
                    layoutCompany.setOrientation(LinearLayout.HORIZONTAL);

                    ImageView pbBus = new ImageView(view.getContext());
                    LinearLayout.LayoutParams pbBusParams = new LinearLayout.LayoutParams(35, 35);
                    pbBus.setLayoutParams(pbBusParams);
                    pbBus.setPadding(0,3,0,0);
                    pbBus.setImageResource(R.drawable.bus);
                    layoutCompany.addView(pbBus);

                    TextView lblCompany = new TextView(view.getContext());
                    LinearLayout.LayoutParams lblCompanyParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    lblCompany.setLayoutParams(lblCompanyParams);
                    lblCompany.setTextSize(14);
                    lblCompany.setTypeface(null, Typeface.BOLD);
                    lblCompanyParams.setMargins(15,0,0,0);
                    lblCompany.setText("Firma: " + ticket.getExpeditionDto().getCompany().getCompanyName());
                    lblCompany.setTextColor(getResources().getColor(R.color.gray_dark2));
                    layoutCompany.addView(lblCompany);

                    linearLayout.addView(layoutCompany);

                    /* Start From City Layout */
                    LinearLayout layoutFromCity = new LinearLayout(view.getContext());
                    LinearLayout.LayoutParams layoutFromCityParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutFromCity.setLayoutParams(layoutFromCityParams);
                    layoutFromCityParams.setMargins(30,30,0,0);
                    layoutFromCity.setOrientation(LinearLayout.HORIZONTAL);

                    ImageView pbFromLocation = new ImageView(view.getContext());
                    LinearLayout.LayoutParams pbFromLocationParams = new LinearLayout.LayoutParams(35, 35);
                    pbFromLocation.setLayoutParams(pbFromLocationParams);
                    pbFromLocation.setPadding(0,0,0,0);
                    pbFromLocation.setImageResource(R.drawable.location_from);
                    layoutFromCity.addView(pbFromLocation);

                    TextView lblFromCity = new TextView(view.getContext());
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
                    LinearLayout layoutFromTo = new LinearLayout(view.getContext());
                    LinearLayout.LayoutParams layoutToCityParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutFromTo.setLayoutParams(layoutToCityParams);
                    layoutToCityParams.setMargins(30,30,0,0);
                    layoutFromTo.setOrientation(LinearLayout.HORIZONTAL);

                    ImageView pbToLocation = new ImageView(view.getContext());
                    LinearLayout.LayoutParams pbToLocationParams = new LinearLayout.LayoutParams(35, 35);
                    pbToLocation.setLayoutParams(pbToLocationParams);
                    pbToLocation.setPadding(0,0,0,0);
                    pbToLocation.setImageResource(R.drawable.location_to);
                    layoutFromTo.addView(pbToLocation);

                    TextView lblToCity = new TextView(view.getContext());
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
                    LinearLayout layoutTravelDate = new LinearLayout(view.getContext());
                    LinearLayout.LayoutParams layoutTravelDateParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutTravelDate.setLayoutParams(layoutTravelDateParams);
                    layoutTravelDateParams.setMargins(30,30,0,0);
                    layoutTravelDate.setOrientation(LinearLayout.HORIZONTAL);

                    ImageView pbDate = new ImageView(view.getContext());
                    LinearLayout.LayoutParams pbDateParams = new LinearLayout.LayoutParams(35, 35);
                    pbDate.setLayoutParams(pbDateParams);
                    pbDate.setPadding(0,2,0,0);
                    pbDate.setImageResource(R.drawable.calendar);
                    layoutTravelDate.addView(pbDate);

                    TextView lblTravelDate = new TextView(view.getContext());
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
                    LinearLayout layoutTravelHour = new LinearLayout(view.getContext());
                    LinearLayout.LayoutParams layoutTravelHourParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutTravelHour.setLayoutParams(layoutTravelHourParams);
                    layoutTravelHourParams.setMargins(30,30,0,0);
                    layoutTravelHour.setOrientation(LinearLayout.HORIZONTAL);

                    ImageView pbHour = new ImageView(view.getContext());
                    LinearLayout.LayoutParams pbHourParams = new LinearLayout.LayoutParams(35, 35);
                    pbHour.setLayoutParams(pbHourParams);
                    pbHour.setPadding(0,0,0,0);
                    pbHour.setImageResource(R.drawable.clock_3);
                    layoutTravelHour.addView(pbHour);

                    TextView lblTravelHour = new TextView(view.getContext());
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
                    LinearLayout layoutGender = new LinearLayout(view.getContext());
                    LinearLayout.LayoutParams layoutGenderParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutGender.setLayoutParams(layoutGenderParams);
                    layoutGenderParams.setMargins(30,30,0,0);
                    layoutGender.setOrientation(LinearLayout.HORIZONTAL);

                    ImageView pbGender = new ImageView(view.getContext());
                    LinearLayout.LayoutParams pbGenderParams = new LinearLayout.LayoutParams(35, 35);
                    pbGender.setLayoutParams(pbGenderParams);
                    pbGender.setPadding(0,0,0,0);
                    pbGender.setImageResource(R.drawable.gender_2);
                    layoutGender.addView(pbGender);

                    TextView lblGender = new TextView(view.getContext());
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
                    LinearLayout layoutSeatNumber = new LinearLayout(view.getContext());
                    LinearLayout.LayoutParams layoutSeatNumberParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutSeatNumber.setLayoutParams(layoutSeatNumberParams);
                    layoutSeatNumberParams.setMargins(30,30,0,30);
                    layoutSeatNumber.setOrientation(LinearLayout.HORIZONTAL);

                    ImageView pbSeat = new ImageView(view.getContext());
                    LinearLayout.LayoutParams pbSeatParams = new LinearLayout.LayoutParams(35, 35);
                    pbSeat.setLayoutParams(pbSeatParams);
                    pbSeatParams.setMargins(0,0,0,0);
                    pbSeat.setImageResource(R.drawable.seat);
                    layoutSeatNumber.addView(pbSeat);

                    TextView lblSeatNumber = new TextView(view.getContext());
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

                    Button btnTicketCancel = new Button(view.getContext());
                    LinearLayout.LayoutParams btnTicketCancelParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 65);
                    btnTicketCancel.setLayoutParams(btnTicketCancelParams);
                    btnTicketCancel.setText("İptal et");
                    btnTicketCancel.setTextSize(13);
                    btnTicketCancel.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    btnTicketCancel.setGravity(Gravity.END);
                    btnTicketCancelParams.setMargins(30,0,30,30);
                    btnTicketCancel.setPadding(0,15,0,0);
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
                                    Toast.makeText(getContext(),"Bilet başarılı bir şekilde iptal edildi", Toast.LENGTH_SHORT).show();
                                    ticketCancelDialog.dismiss();
                                    refreshFragment();
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

                    myTicketsLayout.addView(linearLayout);
                }
            }
            if (oldTickets.size() > 0){
                for (TicketDto ticket : oldTickets) {
                    LinearLayout linearLayout = new LinearLayout(view.getContext());
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(40,20,40,5);
                    linearLayout.setLayoutParams(layoutParams);
                    linearLayout.setBackgroundResource(R.drawable.my_ticket_box_background);
                    linearLayout.setOrientation(LinearLayout.VERTICAL);

                    /* Start From City Layout */
                    LinearLayout layoutCompany = new LinearLayout(view.getContext());
                    LinearLayout.LayoutParams layoutCompanyParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutCompany.setLayoutParams(layoutCompanyParams);
                    layoutCompanyParams.setMargins(30,20,0,0);
                    layoutCompany.setOrientation(LinearLayout.HORIZONTAL);

                    ImageView pbBus = new ImageView(view.getContext());
                    LinearLayout.LayoutParams pbBusParams = new LinearLayout.LayoutParams(35, 35);
                    pbBus.setLayoutParams(pbBusParams);
                    pbBus.setPadding(0,3,0,0);
                    pbBus.setImageResource(R.drawable.bus);
                    layoutCompany.addView(pbBus);

                    TextView lblCompany = new TextView(view.getContext());
                    LinearLayout.LayoutParams lblCompanyParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    lblCompany.setLayoutParams(lblCompanyParams);
                    lblCompany.setTextSize(14);
                    lblCompany.setTypeface(null, Typeface.BOLD);
                    lblCompanyParams.setMargins(15,0,0,0);
                    lblCompany.setText("Firma: " + ticket.getExpeditionDto().getCompany().getCompanyName());
                    lblCompany.setTextColor(getResources().getColor(R.color.gray_dark2));
                    layoutCompany.addView(lblCompany);

                    linearLayout.addView(layoutCompany);

                    /* Start From City Layout */
                    LinearLayout layoutFromCity = new LinearLayout(view.getContext());
                    LinearLayout.LayoutParams layoutFromCityParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutFromCity.setLayoutParams(layoutFromCityParams);
                    layoutFromCityParams.setMargins(30,30,0,0);
                    layoutFromCity.setOrientation(LinearLayout.HORIZONTAL);

                    ImageView pbFromLocation = new ImageView(view.getContext());
                    LinearLayout.LayoutParams pbFromLocationParams = new LinearLayout.LayoutParams(35, 35);
                    pbFromLocation.setLayoutParams(pbFromLocationParams);
                    pbFromLocation.setPadding(0,0,0,0);
                    pbFromLocation.setImageResource(R.drawable.location_from);
                    layoutFromCity.addView(pbFromLocation);

                    TextView lblFromCity = new TextView(view.getContext());
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
                    LinearLayout layoutFromTo = new LinearLayout(view.getContext());
                    LinearLayout.LayoutParams layoutToCityParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutFromTo.setLayoutParams(layoutToCityParams);
                    layoutToCityParams.setMargins(30,30,0,0);
                    layoutFromTo.setOrientation(LinearLayout.HORIZONTAL);

                    ImageView pbToLocation = new ImageView(view.getContext());
                    LinearLayout.LayoutParams pbToLocationParams = new LinearLayout.LayoutParams(35, 35);
                    pbToLocation.setLayoutParams(pbToLocationParams);
                    pbToLocation.setPadding(0,0,0,0);
                    pbToLocation.setImageResource(R.drawable.location_to);
                    layoutFromTo.addView(pbToLocation);

                    TextView lblToCity = new TextView(view.getContext());
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
                    LinearLayout layoutTravelDate = new LinearLayout(view.getContext());
                    LinearLayout.LayoutParams layoutTravelDateParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutTravelDate.setLayoutParams(layoutTravelDateParams);
                    layoutTravelDateParams.setMargins(30,30,0,0);
                    layoutTravelDate.setOrientation(LinearLayout.HORIZONTAL);

                    ImageView pbDate = new ImageView(view.getContext());
                    LinearLayout.LayoutParams pbDateParams = new LinearLayout.LayoutParams(35, 35);
                    pbDate.setLayoutParams(pbDateParams);
                    pbDate.setPadding(0,2,0,0);
                    pbDate.setImageResource(R.drawable.calendar);
                    layoutTravelDate.addView(pbDate);

                    TextView lblTravelDate = new TextView(view.getContext());
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
                    LinearLayout layoutTravelHour = new LinearLayout(view.getContext());
                    LinearLayout.LayoutParams layoutTravelHourParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutTravelHour.setLayoutParams(layoutTravelHourParams);
                    layoutTravelHourParams.setMargins(30,30,0,0);
                    layoutTravelHour.setOrientation(LinearLayout.HORIZONTAL);

                    ImageView pbHour = new ImageView(view.getContext());
                    LinearLayout.LayoutParams pbHourParams = new LinearLayout.LayoutParams(35, 35);
                    pbHour.setLayoutParams(pbHourParams);
                    pbHour.setPadding(0,0,0,0);
                    pbHour.setImageResource(R.drawable.clock_3);
                    layoutTravelHour.addView(pbHour);

                    TextView lblTravelHour = new TextView(view.getContext());
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
                    LinearLayout layoutGender = new LinearLayout(view.getContext());
                    LinearLayout.LayoutParams layoutGenderParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutGender.setLayoutParams(layoutGenderParams);
                    layoutGenderParams.setMargins(30,30,0,0);
                    layoutGender.setOrientation(LinearLayout.HORIZONTAL);

                    ImageView pbGender = new ImageView(view.getContext());
                    LinearLayout.LayoutParams pbGenderParams = new LinearLayout.LayoutParams(35, 35);
                    pbGender.setLayoutParams(pbGenderParams);
                    pbGender.setPadding(0,0,0,0);
                    pbGender.setImageResource(R.drawable.gender_2);
                    layoutGender.addView(pbGender);

                    TextView lblGender = new TextView(view.getContext());
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
                    LinearLayout layoutSeatNumber = new LinearLayout(view.getContext());
                    LinearLayout.LayoutParams layoutSeatNumberParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutSeatNumber.setLayoutParams(layoutSeatNumberParams);
                    layoutSeatNumberParams.setMargins(30,30,0,20);
                    layoutSeatNumber.setOrientation(LinearLayout.HORIZONTAL);

                    ImageView pbSeat = new ImageView(view.getContext());
                    LinearLayout.LayoutParams pbSeatParams = new LinearLayout.LayoutParams(35, 35);
                    pbSeat.setLayoutParams(pbSeatParams);
                    pbSeatParams.setMargins(0,0,0,0);
                    pbSeat.setImageResource(R.drawable.seat);
                    layoutSeatNumber.addView(pbSeat);

                    TextView lblSeatNumber = new TextView(view.getContext());
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

                    TextView lblTravelTookPlace = new TextView(view.getContext());
                    LinearLayout.LayoutParams lblTravelTookPlaceParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    lblTravelTookPlace.setLayoutParams(lblTravelTookPlaceParams);
                    lblTravelTookPlace.setTextSize(13);
                    lblTravelTookPlace.setTypeface(null, Typeface.BOLD);
                    lblTravelTookPlaceParams.setMargins(15,0,0,30);
                    lblTravelTookPlace.setText("Sefer gerçekleşti");
                    lblTravelTookPlace.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    lblTravelTookPlace.setTextColor(getResources().getColor(R.color.red_main));
                    linearLayout.addView(lblTravelTookPlace);

                    myTicketsLayout.addView(linearLayout);
                }
            }
        }
        else{
            ImageView pbWarning = new ImageView(view.getContext());
            LinearLayout.LayoutParams pbWarningParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            pbWarning.setLayoutParams(pbWarningParams);
            pbWarningParams.gravity= Gravity.CENTER;
            pbWarning.setImageResource(R.drawable.warning);
            pbWarning.setPadding(0,80,0,0);
            myTicketsLayout.addView(pbWarning);

            TextView lblTicketNotFound = new TextView(view.getContext());
            LinearLayout.LayoutParams lblTicketNotFoundParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lblTicketNotFound.setLayoutParams(lblTicketNotFoundParams);
            lblTicketNotFound.setTextSize(15);
            lblTicketNotFound.setTypeface(null, Typeface.BOLD);
            lblTicketNotFound.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            lblTicketNotFoundParams.setMargins(30,30,30,10);
            lblTicketNotFound.setText("Sistemde kayıtlı biletiniz bulunmamaktadır !");
            lblTicketNotFound.setTextColor(getResources().getColor(R.color.gray_dark2));
            myTicketsLayout.addView(lblTicketNotFound);
        }

        return view;
    }

    private void refreshFragment(){
        ReplaceFragment.replaceFragment(new MyTicketsFragment(), getActivity());
    }
}