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

public class AdminUserActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;

    Button btnFindUser;
    EditText tbPhoneNumber, tbEmail, tbFirstNameUpdateDialog, tbLastNameUpdateDialog, tbPhoneNumberUpdateDialog, tbEmailUpdateDialog;

    LinearLayout usersLayout;

    Dialog userDeleteDialog, userUpdateDialog;
    Button btnYes, btnNo, btnUpdateYes, btnUpdateCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user);

        databaseHelper = new DatabaseHelper(this);

        usersLayout = findViewById(R.id.usersLayoutActivityUsersAdmin);
        btnFindUser = findViewById(R.id.btnFindUserAdmin);
        tbPhoneNumber= findViewById(R.id.tbPhoneNumberSearchUserAdmin);
        tbEmail= findViewById(R.id.tbEmailSearchUserAdmin);

        btnFindUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String phoneNumber = tbPhoneNumber.getText().toString();
                String email = tbEmail.getText().toString();

                if (!phoneNumber.isEmpty() || phoneNumber.length() > 0){
                    User user = databaseHelper.getUserByPhoneNumber(phoneNumber);
                    listUser(user);
                }
                else if (!email.isEmpty() || email.length() > 0){
                    User user = databaseHelper.getUserByEmail(email);
                    listUser(user);
                }
                else{
                    listUsers();
                }
            }
        });
        userDeleteDialog = new Dialog(this);
        userDeleteDialog.setContentView(R.layout.user_delete_dialog);
        userDeleteDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.ticket_cancel_dialog_background));
        userDeleteDialog.getWindow().setLayout(600, ViewGroup.LayoutParams.WRAP_CONTENT);
        userDeleteDialog.setCancelable(false);
        userDeleteDialog.getWindow().getAttributes().windowAnimations = R.style.DeleteUserDialogAnimation;

        userUpdateDialog = new Dialog(this);
        userUpdateDialog.setContentView(R.layout.admin_user_update_dialog);
        userUpdateDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.ticket_cancel_dialog_background));
        userUpdateDialog.getWindow().setLayout(600, ViewGroup.LayoutParams.WRAP_CONTENT);
        userUpdateDialog.setCancelable(false);
        userUpdateDialog.getWindow().getAttributes().windowAnimations = R.style.UpdateUserDialogAnimation;

        listUsers();

    }

    private void listUsers(){
        List<User> users = databaseHelper.getUsers();
        usersLayout.removeAllViews();
        if (users.size() > 0){
            LinearLayout layoutTitle = new LinearLayout(this);
            LinearLayout.LayoutParams layoutTitleParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutTitle.setLayoutParams(layoutTitleParams);
            layoutTitleParams.setMargins(0,20,0,0);
            layoutTitle.setOrientation(LinearLayout.HORIZONTAL);

            TextView lblUserFirstNameAndLastNameTitle = new TextView(this);
            LinearLayout.LayoutParams lblUserFirstNameAndLastNameTitleParams = new LinearLayout.LayoutParams(200, LinearLayout.LayoutParams.WRAP_CONTENT);
            lblUserFirstNameAndLastNameTitle.setLayoutParams(lblUserFirstNameAndLastNameTitleParams);
            lblUserFirstNameAndLastNameTitle.setTextSize(12);
            lblUserFirstNameAndLastNameTitle.setTypeface(null, Typeface.BOLD);
            lblUserFirstNameAndLastNameTitle.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            lblUserFirstNameAndLastNameTitleParams.setMargins(5,0,0,0);
            lblUserFirstNameAndLastNameTitle.setText("Ad Soyad");
            lblUserFirstNameAndLastNameTitle.setPaintFlags(lblUserFirstNameAndLastNameTitle.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
            lblUserFirstNameAndLastNameTitle.setTextColor(getResources().getColor(R.color.gray_dark2));
            layoutTitle.addView(lblUserFirstNameAndLastNameTitle);

            TextView lblPhoneNumberTitle = new TextView(this);
            LinearLayout.LayoutParams lblPhoneNumberTitleParams = new LinearLayout.LayoutParams(200, LinearLayout.LayoutParams.WRAP_CONTENT);
            lblPhoneNumberTitle.setLayoutParams(lblPhoneNumberTitleParams);
            lblPhoneNumberTitle.setTextSize(12);
            lblPhoneNumberTitle.setTypeface(null, Typeface.BOLD);
            lblPhoneNumberTitle.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            lblPhoneNumberTitleParams.setMargins(40,0,0,0);
            lblPhoneNumberTitle.setText("Telefon Numarası");
            lblPhoneNumberTitle.setPaintFlags(lblPhoneNumberTitle.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
            lblPhoneNumberTitle.setTextColor(getResources().getColor(R.color.gray_dark2));
            layoutTitle.addView(lblPhoneNumberTitle);

            TextView lblDeleteUpdateTitle = new TextView(this);
            LinearLayout.LayoutParams lblDeleteUpdateTitleParams = new LinearLayout.LayoutParams(200, LinearLayout.LayoutParams.WRAP_CONTENT);
            lblDeleteUpdateTitle.setLayoutParams(lblDeleteUpdateTitleParams);
            lblDeleteUpdateTitle.setTextSize(12);
            lblDeleteUpdateTitle.setTypeface(null, Typeface.BOLD);
            lblDeleteUpdateTitle.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            lblDeleteUpdateTitleParams.setMargins(15,0,15,0);
            lblDeleteUpdateTitle.setText("Sil / Güncelle");
            lblDeleteUpdateTitle.setPaintFlags(lblDeleteUpdateTitle.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
            lblDeleteUpdateTitle.setTextColor(getResources().getColor(R.color.gray_dark2));
            layoutTitle.addView(lblDeleteUpdateTitle);
            usersLayout.addView(layoutTitle);

            for (User user : users) {
                LinearLayout layoutUser = new LinearLayout(this);
                LinearLayout.LayoutParams layoutUserParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutUser.setLayoutParams(layoutUserParams);
                layoutUserParams.setMargins(10,20,10,0);
                layoutUser.setOrientation(LinearLayout.HORIZONTAL);

                TextView lblUserFirstNameAndLastName = new TextView(this);
                LinearLayout.LayoutParams lblUserFirstNameAndLastNameParams = new LinearLayout.LayoutParams(250, LinearLayout.LayoutParams.WRAP_CONTENT);
                lblUserFirstNameAndLastName.setLayoutParams(lblUserFirstNameAndLastNameParams);
                lblUserFirstNameAndLastName.setTextSize(12);
                lblUserFirstNameAndLastName.setTypeface(null, Typeface.BOLD);
                lblUserFirstNameAndLastNameParams.setMargins(15,0,0,0);
                lblUserFirstNameAndLastName.setText(user.getFirstName() + "  " + user.getLastName());
                lblUserFirstNameAndLastName.setTextColor(getResources().getColor(R.color.gray_dark2));
                layoutUser.addView(lblUserFirstNameAndLastName);

                TextView lblPhoneNumber = new TextView(this);
                LinearLayout.LayoutParams lblPhoneNumberParams = new LinearLayout.LayoutParams(200, LinearLayout.LayoutParams.WRAP_CONTENT);
                lblPhoneNumber.setLayoutParams(lblPhoneNumberParams);
                lblPhoneNumber.setTextSize(12);
                lblPhoneNumber.setTypeface(null, Typeface.BOLD);
                lblPhoneNumberParams.setMargins(0,0,40,0);
                lblPhoneNumber.setText(user.getPhoneNumber());
                lblPhoneNumber.setTextColor(getResources().getColor(R.color.gray_dark2));
                layoutUser.addView(lblPhoneNumber);

                ImageView pbDelete = new ImageView(this);
                LinearLayout.LayoutParams pbDeleteParams = new LinearLayout.LayoutParams(35, 35);
                pbDelete.setLayoutParams(pbDeleteParams);
                pbDelete.setImageResource(R.drawable.trash);
                pbDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        btnYes = userDeleteDialog.findViewById(R.id.btnDeleteYes);
                        btnNo = userDeleteDialog.findViewById(R.id.btnDeleteNo);

                        btnYes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                databaseHelper.deleteUser(user.getId());
                                Toast.makeText(getApplicationContext(),"Kullanıcı başarılı bir şekilde silindi", Toast.LENGTH_SHORT).show();
                                listUsers();
                                userDeleteDialog.dismiss();
                            }
                        });
                        btnNo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                userDeleteDialog.dismiss();
                            }
                        });

                        userDeleteDialog.show();
                    }
                });
                layoutUser.addView(pbDelete);

                ImageView pbUpdate = new ImageView(this);
                LinearLayout.LayoutParams pbUpdateParams = new LinearLayout.LayoutParams(35, 35);
                pbUpdate.setLayoutParams(pbUpdateParams);
                pbUpdateParams.setMargins(35,0,0,0);
                pbUpdate.setImageResource(R.drawable.editing);
                pbUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        btnUpdateYes = userUpdateDialog.findViewById(R.id.btnUpdateDialogUpdateUserAdmin);
                        btnUpdateCancel = userUpdateDialog.findViewById(R.id.btnCancelDialogUpdateUserAdmin);

                        tbFirstNameUpdateDialog = userUpdateDialog.findViewById(R.id.tbFirstNameUserUpdateAdmin);
                        tbLastNameUpdateDialog = userUpdateDialog.findViewById(R.id.tbLastNameUserUpdateAdmin);
                        tbPhoneNumberUpdateDialog = userUpdateDialog.findViewById(R.id.tbPhoneNumberUserUpdateAdmin);
                        tbEmailUpdateDialog = userUpdateDialog.findViewById(R.id.tbEmailUserUpdateAdmin);

                        tbFirstNameUpdateDialog.setText(user.getFirstName());
                        tbLastNameUpdateDialog.setText(user.getLastName());
                        tbPhoneNumberUpdateDialog.setText(user.getPhoneNumber());
                        tbEmailUpdateDialog.setText(user.getEmail());

                        btnUpdateYes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String firstName = tbFirstNameUpdateDialog.getText().toString();
                                String lastName = tbLastNameUpdateDialog.getText().toString();
                                String phoneNumber = tbPhoneNumberUpdateDialog.getText().toString();
                                String email = tbEmailUpdateDialog.getText().toString();

                                if (firstName.isEmpty() || firstName.length() <= 0){
                                    tbFirstNameUpdateDialog.setError("Ad boş bırakılamaz");
                                }
                                else if (lastName.isEmpty() || lastName.length() <= 0){
                                    tbFirstNameUpdateDialog.setError("Soyad boş bırakılamaz");
                                }
                                else if (phoneNumber.isEmpty() || phoneNumber.length() <= 0){
                                    tbFirstNameUpdateDialog.setError("Telefon numarası boş bırakılamaz");
                                }
                                else if (phoneNumber.length() != 11){
                                    tbFirstNameUpdateDialog.setError("Telefon numarası 11 karakter uzunluğunda olmalı");
                                }
                                else if (email.isEmpty() || email.length() <= 0){
                                    tbFirstNameUpdateDialog.setError("Email boş bırakılamaz");
                                }
                                else{
                                    User updateUser = user;
                                    user.setFirstName(firstName);
                                    user.setLastName(lastName);
                                    user.setPhoneNumber(phoneNumber);
                                    user.setEmail(email);
                                    databaseHelper.updateUser(user);
                                    Toast.makeText(getApplicationContext(), "Kullanıcı başarılı bir şekilde güncellendi", Toast.LENGTH_SHORT).show();
                                    userUpdateDialog.dismiss();
                                    listUsers();
                                }
                            }
                        });

                        btnUpdateCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                userUpdateDialog.dismiss();
                            }
                        });

                        userUpdateDialog.show();
                    }
                });
                layoutUser.addView(pbUpdate);
                usersLayout.addView(layoutUser);
            }
        }
        else{

            ImageView pbWarning = new ImageView(this);
            LinearLayout.LayoutParams pbWarningParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            pbWarning.setLayoutParams(pbWarningParams);
            pbWarningParams.gravity= Gravity.CENTER;
            pbWarning.setImageResource(R.drawable.warning);
            pbWarning.setPadding(0,80,0,0);
            usersLayout.addView(pbWarning);

            TextView lblUsersNotFound = new TextView(this);
            LinearLayout.LayoutParams lblUsersNotFoundParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lblUsersNotFound.setLayoutParams(lblUsersNotFoundParams);
            lblUsersNotFound.setTextSize(15);
            lblUsersNotFound.setTypeface(null, Typeface.BOLD);
            lblUsersNotFound.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            lblUsersNotFoundParams.setMargins(30,30,30,10);
            lblUsersNotFound.setText("Sistemde kayıtlı kullanıcı bulunmamaktadır !");
            lblUsersNotFound.setTextColor(getResources().getColor(R.color.gray_dark2));
            usersLayout.addView(lblUsersNotFound);
        }
    }

    private void listUser(User user){
        usersLayout.removeAllViews();
        if (user != null){
            LinearLayout layoutTitle = new LinearLayout(this);
            LinearLayout.LayoutParams layoutTitleParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutTitle.setLayoutParams(layoutTitleParams);
            layoutTitleParams.setMargins(0,20,0,0);
            layoutTitle.setOrientation(LinearLayout.HORIZONTAL);

            TextView lblUserFirstNameAndLastNameTitle = new TextView(this);
            LinearLayout.LayoutParams lblUserFirstNameAndLastNameTitleParams = new LinearLayout.LayoutParams(200, LinearLayout.LayoutParams.WRAP_CONTENT);
            lblUserFirstNameAndLastNameTitle.setLayoutParams(lblUserFirstNameAndLastNameTitleParams);
            lblUserFirstNameAndLastNameTitle.setTextSize(12);
            lblUserFirstNameAndLastNameTitle.setTypeface(null, Typeface.BOLD);
            lblUserFirstNameAndLastNameTitle.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            lblUserFirstNameAndLastNameTitleParams.setMargins(5,0,0,0);
            lblUserFirstNameAndLastNameTitle.setText("Ad Soyad");
            lblUserFirstNameAndLastNameTitle.setPaintFlags(lblUserFirstNameAndLastNameTitle.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
            lblUserFirstNameAndLastNameTitle.setTextColor(getResources().getColor(R.color.gray_dark2));
            layoutTitle.addView(lblUserFirstNameAndLastNameTitle);

            TextView lblPhoneNumberTitle = new TextView(this);
            LinearLayout.LayoutParams lblPhoneNumberTitleParams = new LinearLayout.LayoutParams(200, LinearLayout.LayoutParams.WRAP_CONTENT);
            lblPhoneNumberTitle.setLayoutParams(lblPhoneNumberTitleParams);
            lblPhoneNumberTitle.setTextSize(12);
            lblPhoneNumberTitle.setTypeface(null, Typeface.BOLD);
            lblPhoneNumberTitle.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            lblPhoneNumberTitleParams.setMargins(40,0,0,0);
            lblPhoneNumberTitle.setText("Telefon Numarası");
            lblPhoneNumberTitle.setPaintFlags(lblPhoneNumberTitle.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
            lblPhoneNumberTitle.setTextColor(getResources().getColor(R.color.gray_dark2));
            layoutTitle.addView(lblPhoneNumberTitle);

            TextView lblDeleteUpdateTitle = new TextView(this);
            LinearLayout.LayoutParams lblDeleteUpdateTitleParams = new LinearLayout.LayoutParams(200, LinearLayout.LayoutParams.WRAP_CONTENT);
            lblDeleteUpdateTitle.setLayoutParams(lblDeleteUpdateTitleParams);
            lblDeleteUpdateTitle.setTextSize(12);
            lblDeleteUpdateTitle.setTypeface(null, Typeface.BOLD);
            lblDeleteUpdateTitle.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            lblDeleteUpdateTitleParams.setMargins(15,0,15,0);
            lblDeleteUpdateTitle.setText("Sil / Güncelle");
            lblDeleteUpdateTitle.setPaintFlags(lblDeleteUpdateTitle.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
            lblDeleteUpdateTitle.setTextColor(getResources().getColor(R.color.gray_dark2));
            layoutTitle.addView(lblDeleteUpdateTitle);
            usersLayout.addView(layoutTitle);
            LinearLayout layoutUser = new LinearLayout(this);
            LinearLayout.LayoutParams layoutUserParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutUser.setLayoutParams(layoutUserParams);
            layoutUserParams.setMargins(10,20,10,0);
            layoutUser.setOrientation(LinearLayout.HORIZONTAL);

            TextView lblUserFirstNameAndLastName = new TextView(this);
            LinearLayout.LayoutParams lblUserFirstNameAndLastNameParams = new LinearLayout.LayoutParams(250, LinearLayout.LayoutParams.WRAP_CONTENT);
            lblUserFirstNameAndLastName.setLayoutParams(lblUserFirstNameAndLastNameParams);
            lblUserFirstNameAndLastName.setTextSize(12);
            lblUserFirstNameAndLastName.setTypeface(null, Typeface.BOLD);
            lblUserFirstNameAndLastNameParams.setMargins(15,0,0,0);
            lblUserFirstNameAndLastName.setText(user.getFirstName() + "  " + user.getLastName());
            lblUserFirstNameAndLastName.setTextColor(getResources().getColor(R.color.gray_dark2));
            layoutUser.addView(lblUserFirstNameAndLastName);

            TextView lblPhoneNumber = new TextView(this);
            LinearLayout.LayoutParams lblPhoneNumberParams = new LinearLayout.LayoutParams(200, LinearLayout.LayoutParams.WRAP_CONTENT);
            lblPhoneNumber.setLayoutParams(lblPhoneNumberParams);
            lblPhoneNumber.setTextSize(12);
            lblPhoneNumber.setTypeface(null, Typeface.BOLD);
            lblPhoneNumberParams.setMargins(0,0,40,0);
            lblPhoneNumber.setText(user.getPhoneNumber());
            lblPhoneNumber.setTextColor(getResources().getColor(R.color.gray_dark2));
            layoutUser.addView(lblPhoneNumber);

            ImageView pbDelete = new ImageView(this);
            LinearLayout.LayoutParams pbDeleteParams = new LinearLayout.LayoutParams(35, 35);
            pbDelete.setLayoutParams(pbDeleteParams);
            pbDelete.setImageResource(R.drawable.trash);
            pbDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    btnYes = userDeleteDialog.findViewById(R.id.btnDeleteYes);
                    btnNo = userDeleteDialog.findViewById(R.id.btnDeleteNo);

                    btnYes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            databaseHelper.deleteUser(user.getId());
                            Toast.makeText(getApplicationContext(),"Kullanıcı başarılı bir şekilde silindi", Toast.LENGTH_SHORT).show();
                            listUsers();
                            userDeleteDialog.dismiss();
                        }
                    });
                    btnNo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            userDeleteDialog.dismiss();
                        }
                    });

                    userDeleteDialog.show();
                }
            });
            layoutUser.addView(pbDelete);

            ImageView pbUpdate = new ImageView(this);
            LinearLayout.LayoutParams pbUpdateParams = new LinearLayout.LayoutParams(35, 35);
            pbUpdate.setLayoutParams(pbUpdateParams);
            pbUpdateParams.setMargins(35,0,0,0);
            pbUpdate.setImageResource(R.drawable.editing);
            pbUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    btnUpdateYes = userUpdateDialog.findViewById(R.id.btnUpdateDialogUpdateUserAdmin);
                    btnUpdateCancel = userUpdateDialog.findViewById(R.id.btnCancelDialogUpdateUserAdmin);

                    tbFirstNameUpdateDialog = userUpdateDialog.findViewById(R.id.tbFirstNameUserUpdateAdmin);
                    tbLastNameUpdateDialog = userUpdateDialog.findViewById(R.id.tbLastNameUserUpdateAdmin);
                    tbPhoneNumberUpdateDialog = userUpdateDialog.findViewById(R.id.tbPhoneNumberUserUpdateAdmin);
                    tbEmailUpdateDialog = userUpdateDialog.findViewById(R.id.tbEmailUserUpdateAdmin);

                    tbFirstNameUpdateDialog.setText(user.getFirstName());
                    tbLastNameUpdateDialog.setText(user.getLastName());
                    tbPhoneNumberUpdateDialog.setText(user.getPhoneNumber());
                    tbEmailUpdateDialog.setText(user.getEmail());

                    btnUpdateYes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String firstName = tbFirstNameUpdateDialog.getText().toString();
                            String lastName = tbLastNameUpdateDialog.getText().toString();
                            String phoneNumber = tbPhoneNumberUpdateDialog.getText().toString();
                            String email = tbEmailUpdateDialog.getText().toString();

                            if (firstName.isEmpty() || firstName.length() <= 0){
                                tbFirstNameUpdateDialog.setError("Ad boş bırakılamaz");
                            }
                            else if (lastName.isEmpty() || lastName.length() <= 0){
                                tbFirstNameUpdateDialog.setError("Soyad boş bırakılamaz");
                            }
                            else if (phoneNumber.isEmpty() || phoneNumber.length() <= 0){
                                tbFirstNameUpdateDialog.setError("Telefon numarası boş bırakılamaz");
                            }
                            else if (phoneNumber.length() != 11){
                                tbFirstNameUpdateDialog.setError("Telefon numarası 11 karakter uzunluğunda olmalı");
                            }
                            else if (email.isEmpty() || email.length() <= 0){
                                tbFirstNameUpdateDialog.setError("Email boş bırakılamaz");
                            }
                            else{
                                User updateUser = user;
                                user.setFirstName(firstName);
                                user.setLastName(lastName);
                                user.setPhoneNumber(phoneNumber);
                                user.setEmail(email);
                                databaseHelper.updateUser(user);
                                Toast.makeText(getApplicationContext(), "Kullanıcı başarılı bir şekilde güncellendi", Toast.LENGTH_SHORT).show();
                                userUpdateDialog.dismiss();
                                listUser(user);
                            }
                        }
                    });
                    btnUpdateCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            userUpdateDialog.dismiss();
                        }
                    });
                    userUpdateDialog.show();
                }
            });
            layoutUser.addView(pbUpdate);

            usersLayout.addView(layoutUser);
        }
        else{
            ImageView pbWarning = new ImageView(this);
            LinearLayout.LayoutParams pbWarningParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            pbWarning.setLayoutParams(pbWarningParams);
            pbWarningParams.gravity= Gravity.CENTER;
            pbWarning.setImageResource(R.drawable.warning);
            pbWarning.setPadding(0,80,0,0);
            usersLayout.addView(pbWarning);

            TextView lblUsersNotFound = new TextView(this);
            LinearLayout.LayoutParams lblUsersNotFoundParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lblUsersNotFound.setLayoutParams(lblUsersNotFoundParams);
            lblUsersNotFound.setTextSize(15);
            lblUsersNotFound.setTypeface(null, Typeface.BOLD);
            lblUsersNotFound.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            lblUsersNotFoundParams.setMargins(30,30,30,10);
            lblUsersNotFound.setText("Kullanıcı bulunamadı !");
            lblUsersNotFound.setTextColor(getResources().getColor(R.color.gray_dark2));
            usersLayout.addView(lblUsersNotFound);
        }
    }
}