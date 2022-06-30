package com.ansi.busticketsalesautomation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class AdminManagementActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    LinearLayout adminsLayout;

    TextView lblAddNewAdmin;
    EditText tbFirstNameAddAdmin, tbLastNameAddAdmin, tbUsernameAddAdmin, tbPasswordAddAdmin, tbPasswordConfirmAddAdmin;

    Dialog addAdminDialog, deleteAdminDialog;
    Button btnAdd, btnCancel, btnDeleteYes, btnDeleteNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_management);

        databaseHelper = new DatabaseHelper(this);

        adminsLayout = findViewById(R.id.adminsLayoutActivityAdmin);
        lblAddNewAdmin = findViewById(R.id.lblAddNewAdmin);

        addAdminDialog = new Dialog(this);
        addAdminDialog.setContentView(R.layout.add_new_admin_dialog);
        addAdminDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.dialog_background));
        addAdminDialog.getWindow().setLayout(600, ViewGroup.LayoutParams.WRAP_CONTENT);
        addAdminDialog.setCancelable(false);
        addAdminDialog.getWindow().getAttributes().windowAnimations = R.style.AddAdminDialogAnimation;

        btnAdd = addAdminDialog.findViewById(R.id.btnAddDialogAddAdmin);
        btnCancel = addAdminDialog.findViewById(R.id.btnCancelDialogAddAdmin);

        deleteAdminDialog = new Dialog(this);
        deleteAdminDialog.setContentView(R.layout.admin_delete_dialog);
        deleteAdminDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.dialog_background));
        deleteAdminDialog.getWindow().setLayout(600, ViewGroup.LayoutParams.WRAP_CONTENT);
        deleteAdminDialog.setCancelable(false);
        deleteAdminDialog.getWindow().getAttributes().windowAnimations = R.style.DeleteAdminDialogAnimation;

        lblAddNewAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAdminDialog.show();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tbFirstNameAddAdmin = addAdminDialog.findViewById(R.id.tbFirstNameAddAdmin);
                tbLastNameAddAdmin = addAdminDialog.findViewById(R.id.tbLastNameAddAdmin);
                tbUsernameAddAdmin = addAdminDialog.findViewById(R.id.tbUsernameAddAdmin);
                tbPasswordAddAdmin = addAdminDialog.findViewById(R.id.tbPasswordAddAdmin);
                tbPasswordConfirmAddAdmin = addAdminDialog.findViewById(R.id.tbPasswordConfirmAddAdmin);

                String firstName = tbFirstNameAddAdmin.getText().toString();
                String lastName = tbLastNameAddAdmin.getText().toString();
                String userName = tbUsernameAddAdmin.getText().toString();
                String password = tbPasswordAddAdmin.getText().toString();
                String passwordConfirm = tbPasswordConfirmAddAdmin.getText().toString();

                if (firstName.isEmpty() || firstName.equals(null)){
                    tbFirstNameAddAdmin.setError("Ad boş bırakılamaz");
                }
                else if (lastName.isEmpty() || lastName.equals(null)){
                    tbLastNameAddAdmin.setError("Soyad boş bırakılamaz");
                }
                else if (userName.isEmpty() || userName.equals(null)){
                    tbUsernameAddAdmin.setError("Kullanıcı adı boş bırakılamaz");
                }
                else if (password.isEmpty() || password.equals(null)){
                    tbPasswordAddAdmin.setError("Parola boş bırakılamaz");
                }
                else if (passwordConfirm.isEmpty() || passwordConfirm.equals(null)){
                    tbPasswordConfirmAddAdmin.setError("Parola tekrarı boş bırakılamaz");
                }
                else if (!passwordConfirm.equals(password)){
                    tbPasswordConfirmAddAdmin.setError("Parolalar uyuşmuyor");
                }
                else{

                    Admin admin = new Admin(firstName, lastName, userName, password);
                    databaseHelper.insertAdmin(admin);
                    Toast.makeText(getApplicationContext(), "Admin başarılı bir şekilde eklendi", Toast.LENGTH_SHORT).show();
                    refreshActivity();
                    clearTextBoxAddNewAdmin();
                    addAdminDialog.dismiss();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAdminDialog.dismiss();
            }
        });

        listAdmins();
    }

    private void listAdmins(){
        List<Admin> admins = databaseHelper.getAdmins();
        adminsLayout.removeAllViews();
        if (admins.size() > 0){

            LinearLayout layoutTitle = new LinearLayout(this);
            LinearLayout.LayoutParams layoutTitleParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutTitle.setLayoutParams(layoutTitleParams);
            layoutTitleParams.setMargins(0,20,0,0);
            layoutTitle.setOrientation(LinearLayout.HORIZONTAL);

            TextView lblAdminFirstNameAndLastNameTitle = new TextView(this);
            LinearLayout.LayoutParams lblAdminFirstNameAndLastNameTitleParams = new LinearLayout.LayoutParams(200, LinearLayout.LayoutParams.WRAP_CONTENT);
            lblAdminFirstNameAndLastNameTitle.setLayoutParams(lblAdminFirstNameAndLastNameTitleParams);
            lblAdminFirstNameAndLastNameTitle.setTextSize(12);
            lblAdminFirstNameAndLastNameTitle.setTypeface(null, Typeface.BOLD);
            lblAdminFirstNameAndLastNameTitle.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            lblAdminFirstNameAndLastNameTitleParams.setMargins(5,0,0,0);
            lblAdminFirstNameAndLastNameTitle.setText("Ad Soyad");
            lblAdminFirstNameAndLastNameTitle.setPaintFlags(lblAdminFirstNameAndLastNameTitle.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
            lblAdminFirstNameAndLastNameTitle.setTextColor(getResources().getColor(R.color.gray_dark2));
            layoutTitle.addView(lblAdminFirstNameAndLastNameTitle);

            TextView lblAdminUsernameTitle = new TextView(this);
            LinearLayout.LayoutParams lblAdminUsernameTitleParams = new LinearLayout.LayoutParams(200, LinearLayout.LayoutParams.WRAP_CONTENT);
            lblAdminUsernameTitle.setLayoutParams(lblAdminUsernameTitleParams);
            lblAdminUsernameTitle.setTextSize(12);
            lblAdminUsernameTitle.setTypeface(null, Typeface.BOLD);
            lblAdminUsernameTitle.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            lblAdminUsernameTitleParams.setMargins(40,0,0,0);
            lblAdminUsernameTitle.setText("Kullanıcı Adı");
            lblAdminUsernameTitle.setPaintFlags(lblAdminUsernameTitle.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
            lblAdminUsernameTitle.setTextColor(getResources().getColor(R.color.gray_dark2));
            layoutTitle.addView(lblAdminUsernameTitle);

            TextView lblDeleteUpdateTitle = new TextView(this);
            LinearLayout.LayoutParams lblDeleteUpdateTitleParams = new LinearLayout.LayoutParams(200, LinearLayout.LayoutParams.WRAP_CONTENT);
            lblDeleteUpdateTitle.setLayoutParams(lblDeleteUpdateTitleParams);
            lblDeleteUpdateTitle.setTextSize(12);
            lblDeleteUpdateTitle.setTypeface(null, Typeface.BOLD);
            lblDeleteUpdateTitle.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            lblDeleteUpdateTitleParams.setMargins(15,0,15,0);
            lblDeleteUpdateTitle.setText("Sil");
            lblDeleteUpdateTitle.setPaintFlags(lblDeleteUpdateTitle.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
            lblDeleteUpdateTitle.setTextColor(getResources().getColor(R.color.gray_dark2));
            layoutTitle.addView(lblDeleteUpdateTitle);
            adminsLayout.addView(layoutTitle);

            for (Admin admin : admins) {

                LinearLayout layoutUser = new LinearLayout(this);
                LinearLayout.LayoutParams layoutUserParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutUser.setLayoutParams(layoutUserParams);
                layoutUserParams.setMargins(10,20,10,0);
                layoutUser.setOrientation(LinearLayout.HORIZONTAL);

                TextView lblAdminFirstNameAndLastName = new TextView(this);
                LinearLayout.LayoutParams lblAdminFirstNameAndLastNameParams = new LinearLayout.LayoutParams(250, LinearLayout.LayoutParams.WRAP_CONTENT);
                lblAdminFirstNameAndLastName.setLayoutParams(lblAdminFirstNameAndLastNameParams);
                lblAdminFirstNameAndLastName.setTextSize(12);
                lblAdminFirstNameAndLastName.setTypeface(null, Typeface.BOLD);
                lblAdminFirstNameAndLastNameParams.setMargins(15,0,0,0);
                lblAdminFirstNameAndLastName.setText(admin.getFirstName() + "  " + admin.getLastName());
                lblAdminFirstNameAndLastName.setTextColor(getResources().getColor(R.color.gray_dark2));
                layoutUser.addView(lblAdminFirstNameAndLastName);

                TextView lblAdminUsername = new TextView(this);
                LinearLayout.LayoutParams lblAdminUsernameParams = new LinearLayout.LayoutParams(200, LinearLayout.LayoutParams.WRAP_CONTENT);
                lblAdminUsername.setLayoutParams(lblAdminUsernameParams);
                lblAdminUsername.setTextSize(12);
                lblAdminUsername.setTypeface(null, Typeface.BOLD);
                lblAdminUsernameParams.setMargins(20,0,45,0);
                lblAdminUsername.setText(admin.getUserName());
                lblAdminUsername.setTextColor(getResources().getColor(R.color.gray_dark2));
                layoutUser.addView(lblAdminUsername);

                ImageView pbDelete = new ImageView(this);
                LinearLayout.LayoutParams pbDeleteParams = new LinearLayout.LayoutParams(35, 35);
                pbDelete.setLayoutParams(pbDeleteParams);
                pbDelete.setImageResource(R.drawable.trash);
                pbDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        btnDeleteYes = deleteAdminDialog.findViewById(R.id.btnDeleteAdminYes);
                        btnDeleteNo = deleteAdminDialog.findViewById(R.id.btnDeleteAdminNo);

                        btnDeleteYes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                databaseHelper.deleteAdmin(admin.getId());
                                Toast.makeText(getApplicationContext(), "Admin başarılı bir şekilde silindi",Toast.LENGTH_SHORT).show();
                                refreshActivity();
                                deleteAdminDialog.dismiss();
                            }
                        });

                        btnDeleteNo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                deleteAdminDialog.dismiss();
                            }
                        });

                        deleteAdminDialog.show();
                    }
                });
                layoutUser.addView(pbDelete);

                adminsLayout.addView(layoutUser);
            }
        }
        else{

            ImageView pbWarning = new ImageView(this);
            LinearLayout.LayoutParams pbWarningParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            pbWarning.setLayoutParams(pbWarningParams);
            pbWarningParams.gravity= Gravity.CENTER;
            pbWarning.setImageResource(R.drawable.warning);
            pbWarning.setPadding(0,80,0,0);
            adminsLayout.addView(pbWarning);

            TextView lblAdminsNotFound = new TextView(this);
            LinearLayout.LayoutParams lblAdminsNotFoundParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lblAdminsNotFound.setLayoutParams(lblAdminsNotFoundParams);
            lblAdminsNotFound.setTextSize(15);
            lblAdminsNotFound.setTypeface(null, Typeface.BOLD);
            lblAdminsNotFound.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            lblAdminsNotFoundParams.setMargins(30,30,30,10);
            lblAdminsNotFound.setText("Sistemde kayıtlı admin bulunmamaktadır !");
            lblAdminsNotFound.setTextColor(getResources().getColor(R.color.gray_dark2));
            adminsLayout.addView(lblAdminsNotFound);
        }
    }

    private void refreshActivity(){
        adminsLayout.removeAllViews();
        listAdmins();
    }

    private void clearTextBoxAddNewAdmin(){
        tbFirstNameAddAdmin.setText("");
        tbLastNameAddAdmin.setText("");
        tbUsernameAddAdmin.setText("");
        tbPasswordAddAdmin.setText("");
        tbPasswordConfirmAddAdmin.setText("");
    }
}