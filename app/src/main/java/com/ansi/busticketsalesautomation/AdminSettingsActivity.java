package com.ansi.busticketsalesautomation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class AdminSettingsActivity extends AppCompatActivity {

    Button btnAdminManagement, btnChangeAdminPassword, btnMyProfile, btnExit;

    TextView lblUsername;

    Dialog exitDialog;
    Button btnExitYes, btnExitNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_settings);

        btnAdminManagement = findViewById(R.id.btnAdminManagementAdminSettings);
        btnChangeAdminPassword = findViewById(R.id.btnChangeAdminPasswordAdminSettings);
        btnMyProfile = findViewById(R.id.btnMyProfileAdminSettings);
        btnExit = findViewById(R.id.btnExitAdminSettings);
        lblUsername = findViewById(R.id.lblUsernameActivityAdminSettings);

        if (AdminCredentials.ADMIN != null){
            lblUsername.setText(AdminCredentials.ADMIN.getFirstName() + " " + AdminCredentials.ADMIN.getLastName());
        }

        btnAdminManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AdminManagementActivity.class);
                startActivity(intent);
            }
        });

        btnMyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AdminMyProfileActivity.class);
                startActivity(intent);
            }
        });

        btnChangeAdminPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AdminChangePasswordActivity.class);
                startActivity(intent);
            }
        });

        exitDialog = new Dialog(this);
        exitDialog.setContentView(R.layout.exit_dialog);
        exitDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.exit_dialog_background));
        exitDialog.getWindow().setLayout(600, ViewGroup.LayoutParams.WRAP_CONTENT);
        exitDialog.setCancelable(false);
        exitDialog.getWindow().getAttributes().windowAnimations = R.style.ExitDialogAnimation;

        btnExitYes = exitDialog.findViewById(R.id.btnExitYes);
        btnExitNo = exitDialog.findViewById(R.id.btnExitNo);

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitDialog.show();
            }
        });

        btnExitYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitDialog.dismiss();
                finishAffinity();
            }
        });
        btnExitNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitDialog.dismiss();
            }
        });
    }
}