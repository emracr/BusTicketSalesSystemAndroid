package com.ansi.busticketsalesautomation;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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

    EditText tbFirstName, tbLastName, tbEmail, tbPhone;
    RadioGroup rgGender;
    RadioButton rbMale, rbFemale;
    Button btnUpdate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        databaseHelper = new DatabaseHelper(getContext());

        tbFirstName = view.findViewById(R.id.tbFirstNameFragmentProfile);
        tbLastName = view.findViewById(R.id.tbLastNameFragmentProfile);
        tbEmail = view.findViewById(R.id.tbEmailFragmentProfile);
        tbPhone = view.findViewById(R.id.tbPhoneFragmentProfile);
        rgGender = view.findViewById(R.id.rgGenderFragmentProfile);
        rbMale = view.findViewById(R.id.rbMaleFragmentProfile);
        rbFemale= view.findViewById(R.id.rbFemaleFragmentProfile);
        btnUpdate = view.findViewById(R.id.btnUpdateFragmentProfile);

        tbFirstName.setText(Credentials.USER.getFirstName());
        tbLastName.setText(Credentials.USER.getLastName());
        tbEmail.setText(Credentials.USER.getEmail());
        tbPhone.setText(Credentials.USER.getPhoneNumber());

        if (Credentials.USER.getGender().equals("Erkek")){
            rbMale.setChecked(true);
        }
        else{
            rbFemale.setChecked(true);
        }

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String firstName, lastName, email, phoneNumber, gender;
                gender = null;

                firstName = tbFirstName.getText().toString();
                lastName = tbLastName.getText().toString();
                email = tbEmail.getText().toString();
                phoneNumber = tbPhone.getText().toString();

                if(rbMale.isChecked()){
                    gender = rbMale.getText().toString();
                }
                else{
                    gender = rbFemale.getText().toString();
                }

                if(firstName.isEmpty() || firstName.equals(null)){
                    tbFirstName.setError("Ad boş bırakılamaz");
                }
                else if(lastName.isEmpty() || lastName.equals(null)){
                    tbLastName.setError("Soyad boş bırakılamaz");
                }
                else if(email.isEmpty() || email.equals(null)){
                    tbEmail.setError("Email boş bırakılamaz");
                }
                else if(!isEmailValid(email)){
                    tbEmail.setError("Hatalı email formatı");
                }
                else if(phoneNumber.isEmpty() || phoneNumber.equals(null)){
                    tbPhone.setError("Telefon numarası boş bırakılamaz");
                }
                else if(phoneNumber.length() < 11){
                    tbPhone.setError("Telefon numarası 11 hane uzunluğunda olmalı");
                }
                else if(checkIfEmailAlreadyExists(Credentials.USER.getId(), email)){
                    DialogBox.showDialog(getParentFragmentManager(),"Uyarı", "Email zaten sistemde kayıtlı");
                }
                else if(checkIfPhoneNumberAlreadyExists(Credentials.USER.getId(), phoneNumber)){
                    DialogBox.showDialog(getParentFragmentManager(),"Uyarı", "Telefon numarası zaten sistemde kayıtlı");
                }
                else {

                    User user = new User(Credentials.USER.getId(),firstName, lastName, email, phoneNumber, gender, Credentials.USER.getPassword());
                    boolean result = updateUser(user);
                    if (!result){
                        DialogBox.showDialog(getParentFragmentManager(),"Hata","Beklenmedik bir hata oluştu");
                    }
                    else{
                        Credentials.USER = user;
                        Toast.makeText(getContext(), "Profil bilgileriniz başarılı bir şekilde güncellenmiştir.", Toast.LENGTH_SHORT).show();
                        ReplaceFragment.replaceFragment(new MyAccountFragment(), getActivity());
                    }
                }

            }
        });

        return view;
    }

    private boolean updateUser(User user){
        return databaseHelper.updateUser(user);
    }

    private boolean checkIfEmailAlreadyExists(int userId, String email){
        List<User> users = databaseHelper.getUsers();
        for (User usr : users) {
            if (usr.getEmail().equals(email) && usr.getId() != userId){
                return true;
            }
        }
        return false;
    }

    private boolean checkIfPhoneNumberAlreadyExists(int userId, String phoneNumber){
        List<User> users = databaseHelper.getUsers();
        for (User usr : users) {
            if (usr.getPhoneNumber().equals(phoneNumber) && usr.getId() != userId){
                return true;
            }
        }
        return false;
    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}