package com.ansi.busticketsalesautomation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;

    EditText tbFirstName, tbLastName, tbEmail, tbPhone, tbPassword, tbPasswordConfirm;
    Button btnCompleteRegister;
    RadioGroup rgGender;
    RadioButton rbGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        databaseHelper = new DatabaseHelper(this);

        tbFirstName = findViewById(R.id.tbFirstNameRegister);
        tbLastName = findViewById(R.id.tbLastNameRegister);
        tbEmail = findViewById(R.id.tbEmailRegister);
        tbPhone = findViewById(R.id.tbPhoneRegister);
        tbPassword = findViewById(R.id.tbPasswordRegister);
        tbPasswordConfirm = findViewById(R.id.tbPasswordConfirmRegister);
        btnCompleteRegister = findViewById(R.id.btnCompleteRegister);
        rgGender = findViewById(R.id.rgGender);

        btnCompleteRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int genderId = rgGender.getCheckedRadioButtonId();
                rbGender = findViewById(genderId);

                String firstName, lastName, email, phoneNumber, password, passwordConfirm, gender;

                firstName = tbFirstName.getText().toString();
                lastName = tbLastName.getText().toString();
                email = tbEmail.getText().toString();
                phoneNumber = tbPhone.getText().toString();
                password = tbPassword.getText().toString();
                passwordConfirm = tbPasswordConfirm.getText().toString();

                if(firstName.isEmpty() || firstName.equals(null)){
                    tbFirstName.setError("Ad bo?? b??rak??lamaz");
                }
                else if(lastName.isEmpty() || lastName.equals(null)){
                    tbLastName.setError("Soyad bo?? b??rak??lamaz");
                }
                else if(email.isEmpty() || email.equals(null)){
                    tbEmail.setError("Email bo?? b??rak??lamaz");
                }
                else if(!isEmailValid(email)){
                    tbEmail.setError("Hatal?? email format??");
                }
                else if(phoneNumber.isEmpty() || phoneNumber.equals(null)){
                    tbPhone.setError("Telefon numaras?? bo?? b??rak??lamaz");
                }
                else if(phoneNumber.length() < 11){
                    tbPhone.setError("Telefon numaras?? 11 hane uzunlu??unda olmal??");
                }
                else if(password.isEmpty() || password.equals(null)){
                    tbPassword.setError("Parola bo?? b??rak??lamaz");
                }
                else if(passwordConfirm.isEmpty() || passwordConfirm.equals(null)){
                    tbPasswordConfirm.setError("Parola tekrar?? bo?? b??rak??lamaz");
                }
                else if(!password.equals(passwordConfirm)){
                    tbPasswordConfirm.setError("Parolalar uyu??muyor");
                }
                else if(Integer.valueOf(genderId) < 0){
                    DialogBox.showDialog(getSupportFragmentManager(),"Uyar??", "Cinsiyet bo?? b??rak??lamaz");
                }
                else if(checkIfEmailAlreadyExists(email)){
                    DialogBox.showDialog(getSupportFragmentManager(),"Uyar??", "Email zaten sistemde kay??tl??");
                }
                else if(checkIfPhoneNumberAlreadyExists(phoneNumber)){
                    DialogBox.showDialog(getSupportFragmentManager(),"Uyar??", "Telefon numaras?? zaten sistemde kay??tl??");
                }
                else {
                    gender = rbGender.getText().toString();
                    boolean result = addUser(new User(firstName, lastName, email, phoneNumber, gender, password));
                    if (!result){
                        DialogBox.showDialog(getSupportFragmentManager(),"Hata","Beklenmedik bir hata olu??tu");
                    }
                    else{
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        Toast.makeText(getApplicationContext(), "Ba??ar??l?? bir ??ekilde kay??t oldunuz", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    }
                }
            }
        });
    }

    private boolean addUser(User user){
        return databaseHelper.insertUser(user);
    }

    private boolean checkIfEmailAlreadyExists(String email){
        List<User> users = databaseHelper.getUsers();
        for (User usr : users) {
            if (usr.getEmail().equals(email)){
                return true;
            }
        }
        return false;
    }

    private boolean checkIfPhoneNumberAlreadyExists(String phoneNumber){
        List<User> users = databaseHelper.getUsers();
        for (User usr : users) {
            if (usr.getPhoneNumber().equals(phoneNumber)){
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