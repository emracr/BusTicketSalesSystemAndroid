package com.ansi.busticketsalesautomation;

import android.app.Dialog;
import android.content.ClipData;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

    TextView lblWelcomeUsername;
    ImageView pbExit;
    LinearLayout layout_find_ticket, layout_my_tickets, layout_campaigns, layout_exit_button;

    Dialog exitDialog;
    Button btnExitYes, btnExitNo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        layout_find_ticket = view.findViewById(R.id.layout_find_ticket_fragment_home);
        layout_my_tickets = view.findViewById(R.id.layout_my_tickets_fragment_home);
        layout_campaigns = view.findViewById(R.id.layout_campaigns_fragment_home);
        layout_exit_button = view.findViewById(R.id.layout_exit_button_fragment_home);

        lblWelcomeUsername = view.findViewById(R.id.lblWelcomeUsernameHome);
        pbExit = view.findViewById(R.id.pbExitHome);

        lblWelcomeUsername.setText(Credentials.USER.getFirstName().toUpperCase(Locale.ROOT) + " " + Credentials.USER.getLastName().toUpperCase(Locale.ROOT));

        layout_find_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReplaceFragment.replaceFragment(new FindTicketFragment(), getActivity());
            }
        });

        layout_my_tickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReplaceFragment.replaceFragment(new MyTicketsFragment(), getActivity());
            }
        });

        layout_campaigns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReplaceFragment.replaceFragment(new CampaignsFragment(), getActivity());
            }
        });

        layout_exit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitDialog.show();
            }
        });

        exitDialog = new Dialog(getContext());
        exitDialog.setContentView(R.layout.exit_dialog);
        exitDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.exit_dialog_background));
        exitDialog.getWindow().setLayout(600, ViewGroup.LayoutParams.WRAP_CONTENT);
        exitDialog.setCancelable(false);
        exitDialog.getWindow().getAttributes().windowAnimations = R.style.ExitDialogAnimation;

        btnExitYes = exitDialog.findViewById(R.id.btnExitYes);
        btnExitNo = exitDialog.findViewById(R.id.btnExitNo);

        btnExitYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitDialog.dismiss();
                getActivity().finishAffinity();
            }
        });

        btnExitNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitDialog.dismiss();
            }
        });

        return view;
    }
}