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
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChangePasswordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChangePasswordFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChangePasswordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChangePasswordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChangePasswordFragment newInstance(String param1, String param2) {
        ChangePasswordFragment fragment = new ChangePasswordFragment();
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

    EditText tbOldPassword, tbNewPassword, tbNewPasswordConfirm;
    Button btnUpdate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);

        databaseHelper = new DatabaseHelper(getContext());

        tbOldPassword = view.findViewById(R.id.tbOldPasswordFragmentChangePassword);
        tbNewPassword = view.findViewById(R.id.tbNewPasswordFragmentChangePassword);
        tbNewPasswordConfirm = view.findViewById(R.id.tbNewPasswordConfirmFragmentChangePassword);
        btnUpdate = view.findViewById(R.id.btnUpdateFragmentChangePassword);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String oldPassword, newPassword, newPasswordConfirm;

                oldPassword = tbOldPassword.getText().toString();
                newPassword = tbNewPassword.getText().toString();
                newPasswordConfirm = tbNewPasswordConfirm.getText().toString();

                if(oldPassword.isEmpty() || oldPassword.equals(null)){
                    tbOldPassword.setError("Mevcut parola boş bırakılamaz");
                }
                else if(newPassword.isEmpty() || newPassword.equals(null)){
                    tbNewPassword.setError("Yeni parola boş bırakılamaz");
                }
                else if(newPasswordConfirm.isEmpty() || newPasswordConfirm.equals(null)){
                    tbNewPasswordConfirm.setError("Yeni parola tekrarı boş bırakılamaz");
                }
                else if(!oldPassword.equals(Credentials.USER.getPassword())){
                    tbOldPassword.setError("Mecut parola hatalı");
                }
                else if(!newPassword.equals(newPasswordConfirm)){
                    tbNewPasswordConfirm.setError("Parolalar uyuşmuyor");
                }
                else{
                    User user = Credentials.USER;
                    user.setPassword(newPassword);
                    boolean result = updateUserPassword(user);
                    if (!result){
                        DialogBox.showDialog(getParentFragmentManager(),"Hata","Beklenmedik bir hata oluştu");
                    }
                    else{
                        Credentials.USER = user;
                        Toast.makeText(getContext(), "Parolanız başarılı bir şekilde güncellenmiştir.", Toast.LENGTH_SHORT).show();
                        ReplaceFragment.replaceFragment(new MyAccountFragment(), getActivity());
                    }
                }
            }
        });

        return view;
    }

    private boolean updateUserPassword(User user){
        return databaseHelper.updateUser(user);
    }
}