package com.ansi.busticketsalesautomation;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyAccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyAccountFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MyAccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyAccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyAccountFragment newInstance(String param1, String param2) {
        MyAccountFragment fragment = new MyAccountFragment();
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

    public static final String SHARED_PREFERENCES = "sharedPreferences";

    TextView lblUsername;
    Button btnMyProfile, btnChangePassword, btnExit;

    Dialog exitDialog;
    Button btnExitYes, btnExitNo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_my_account, container, false);

        lblUsername = view.findViewById(R.id.lblUsernameFragmentMyAccount);
        btnMyProfile = view.findViewById(R.id.btnMyProfileFragmentMyAccount);
        btnChangePassword = view.findViewById(R.id.btnChangePasswordFragmentMyAccount);
        btnExit = view.findViewById(R.id.btnExitFragmentMyAccount);

        lblUsername.setText(Credentials.USER.getFirstName().toUpperCase(Locale.ROOT) + " " + Credentials.USER.getLastName().toUpperCase(Locale.ROOT));

        btnMyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReplaceFragment.replaceFragment(new ProfileFragment(), getActivity());
            }
        });

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReplaceFragment.replaceFragment(new ChangePasswordFragment(), getActivity());
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitDialog.show();
            }
        });

        exitDialog = new Dialog(getContext());
        exitDialog.setContentView(R.layout.exit_session_dialog);
        exitDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.exit_dialog_background));
        exitDialog.getWindow().setLayout(600, ViewGroup.LayoutParams.WRAP_CONTENT);
        exitDialog.setCancelable(false);
        exitDialog.getWindow().getAttributes().windowAnimations = R.style.ExitSessionDialogAnimation;

        btnExitYes = exitDialog.findViewById(R.id.btnExitYesExitSession);
        btnExitNo = exitDialog.findViewById(R.id.btnExitNoExitSession);

        btnExitYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearSharedPreferences();
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

    public void clearSharedPreferences(){
        android.content.SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARED_PREFERENCES, getContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}