package com.ansi.busticketsalesautomation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdminMyProfileActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;

    EditText tbFirstName, tbLastName, tbUsername;
    Button btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_my_profile);

        databaseHelper = new DatabaseHelper(this);

        tbFirstName = findViewById(R.id.tbFirstNameActivityMyProfileAdmin);
        tbLastName = findViewById(R.id.tbLastNameActivityMyProfileAdmin);
        tbUsername = findViewById(R.id.tbUserNameActivityMyProfileAdmin);
        btnUpdate = findViewById(R.id.btnUpdateMyProfileAdmin);

        tbFirstName.setText(AdminCredentials.ADMIN.getFirstName());
        tbLastName.setText(AdminCredentials.ADMIN.getLastName());
        tbUsername.setText(AdminCredentials.ADMIN.getUserName());

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String firstName, lastName, userName;

                firstName = tbFirstName.getText().toString();
                lastName = tbLastName.getText().toString();
                userName = tbUsername.getText().toString();

                if(firstName.isEmpty() || firstName.equals(null)){
                    tbFirstName.setError("Ad boş bırakılamaz");
                }
                else if(lastName.isEmpty() || lastName.equals(null)){
                    tbLastName.setError("Soyad boş bırakılamaz");
                }
                else if(userName.isEmpty() || userName.equals(null)){
                    tbUsername.setError("Kullancı adı boş bırakılamaz");
                }
                else{

                    Admin admin = new Admin(AdminCredentials.ADMIN.getId(), firstName, lastName, userName, AdminCredentials.ADMIN.getPassword());
                    boolean result = updateAdmin(admin);
                    if (!result){
                        DialogBox.showDialog(getSupportFragmentManager(),"Hata","Beklenmedik bir hata oluştu");
                    }
                    else{
                        AdminCredentials.ADMIN = admin;
                        Toast.makeText(getApplicationContext(), "Profil bilgileriniz başarılı bir şekilde güncellenmiştir.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), AdminHomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });
    }

    private boolean updateAdmin(Admin admin){
        return databaseHelper.updateAdmin(admin);
    }
}