package com.ansi.busticketsalesautomation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CompanyHomeActivity extends AppCompatActivity {

    TextView lblWelcomeUsername;
    LinearLayout layout_exit_button;

    LinearLayout layout_add_new_expedition, layout_expeditions, layout_passengers, layout_ticket_reservation, layout_ticket_operations, layout_settings;

    Dialog exitDialog;
    Button btnExitYes, btnExitNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_home);

        lblWelcomeUsername = findViewById(R.id.lblWelcomeUsernameActivityCompanyHome);
        layout_exit_button = findViewById(R.id.layout_exit_button_activity_company_home);
        layout_add_new_expedition = findViewById(R.id.layout_add_new_expedition_activity_company_home);
        layout_expeditions = findViewById(R.id.layout_expeditions_activity_company_home);
        layout_passengers = findViewById(R.id.layout_passengers_activity_company_home);
        layout_ticket_reservation = findViewById(R.id.layout_ticket_reservation_activity_company_home);
        layout_ticket_operations = findViewById(R.id.layout_ticket_operations_activity_company_home);
        layout_settings = findViewById(R.id.layout_settings_company_home);

        lblWelcomeUsername.setText(CompanyCredentials.COMPANY.getCompanyName() + " Turizm");

        layout_exit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitDialog.show();
            }
        });

        layout_add_new_expedition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CompanyAddExpeditionActivity.class);
                startActivity(intent);
            }
        });

        layout_expeditions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CompanyExpeditionsActivity.class);
                startActivity(intent);
            }
        });

        layout_passengers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PassengersActivity.class);
                startActivity(intent);
            }
        });

        layout_ticket_reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CompanyCustomerInfoForReservationActivity.class);
                startActivity(intent);
            }
        });

        layout_ticket_operations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CompanyTicketOperationsActivity.class);
                startActivity(intent);
            }
        });

        layout_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CompanySettingsActivity.class);
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