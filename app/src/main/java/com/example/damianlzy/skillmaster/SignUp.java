package com.example.damianlzy.skillmaster;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.damianlzy.skillmaster.content.ActivityHome;
import com.example.damianlzy.skillmaster.functions.PostRequest;
import com.example.damianlzy.skillmaster.functions.Sessions;
import com.example.damianlzy.skillmaster.model.Links;
import com.example.damianlzy.skillmaster.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.concurrent.ExecutionException;

public class SignUp extends AppCompatActivity {

    TextView tvPP, tvTNC;
    Toolbar toolbar;
    EditText etFullName, etMobile, etEmail, etPassword, etCfmPassword, etAddress, etNRIC, etNationality, etEducationLevel, etRace, etOccupation, etSalary;
    TextView etGender, etDob;
    CheckBox cbtnc, cbcomment;
    Button btnSignup;
    RelativeLayout signupPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etFullName = findViewById(R.id.et_full_name);
        etMobile = findViewById(R.id.et_mobile);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        etCfmPassword = findViewById(R.id.et_cfm_password);
        etAddress = findViewById(R.id.et_address);
        etNRIC = findViewById(R.id.et_nric);
        etNationality = findViewById(R.id.et_nationality);
        etEducationLevel = findViewById(R.id.et_education_level);
        etRace = findViewById(R.id.et_race);
        etOccupation = findViewById(R.id.et_occupation);
        etSalary = findViewById(R.id.et_salary);
        etGender = findViewById(R.id.et_gender);
        etDob = findViewById(R.id.et_dob);
        cbtnc = findViewById(R.id.checkboxTNC);
        cbcomment = findViewById(R.id.checkboxComments);
        btnSignup = findViewById(R.id.btn_signup);

        signupPage = findViewById(R.id.signup_page);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvPP = findViewById(R.id.tvPP);
        tvTNC = findViewById(R.id.tvTNC);

        tvPP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SignUp.this);
                LayoutInflater li = LayoutInflater.from(SignUp.this);
                final View gtnc = li.inflate(R.layout.pp_dialog, null);
                builder.setCancelable(true);
                builder.setView(gtnc);
                Button btnDone = gtnc.findViewById(R.id.btnDone);
                final AlertDialog dialog = builder.create();
                btnDone.setOnClickListener(new View.OnClickListener() {
                    @Override

                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        tvTNC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SignUp.this);
                LayoutInflater li = LayoutInflater.from(SignUp.this);
                final View gtnc = li.inflate(R.layout.pp_dialog, null);
                builder.setCancelable(true);
                builder.setView(gtnc);
                Button btnDone = gtnc.findViewById(R.id.btnDone);
                final AlertDialog dialog = builder.create();
                btnDone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        etDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectDate();
            }
        });
        etGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectGender();
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyBoard();
                Log.e("Click", "Signup Clicked");

                if (validate()) {
                    if (cbtnc.isChecked() && cbcomment.isChecked()) {
                        //TODO Do register
                        PostRequest request = new PostRequest();
                        Links links = new Links();
                        try {
                            String register = request.execute(links.registerLink(),GetDetails().toString()).get();
                            if(register==null){
                                Log.e("RESULT","NULL");
                            }else{
                                Sessions sessions = new Sessions(SignUp.this);
                                try {
                                    JSONObject result = new JSONObject(register);
                                    if(result.has(getString(R.string.success))){
                                        JSONObject details = result.getJSONObject("success");
                                        sessions.StoreToken(details.getString("token"));
                                        JSONObject user = details.getJSONObject("user-info");
                                        User userKey = new User();

                                        sessions.StoreUser(user.getInt(userKey.user_id_key)
                                                , user.getString(userKey.user_name_key)
                                                , user.getString(userKey.user_email_key)
                                                , user.getString(userKey.user_created_at_key)
                                                , user.getString(userKey.user_updated_at_key)
                                                , user.getString(userKey.user_mobile_key)
                                                , user.getString(userKey.user_address_key)
                                                , user.getString(userKey.user_nric_key)
                                                , user.getString(userKey.user_dob_key)
                                                , user.getString(userKey.user_gender_key)
                                                , user.getString(userKey.user_nationality_key)
                                                , user.getString(userKey.user_education_level_key)
                                                , user.getString(userKey.user_race_key)
                                                , user.getString(userKey.user_occupation_key)
                                                , user.getString(userKey.user_salary_key));

                                        Intent intent = new Intent(SignUp.this, ActivityHome.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    }else if(result.has("error")){
                                        Log.e("REGISTER RESULT", "Registration Failed");
                                        Toast.makeText(SignUp.this, "An unexpected error has occurred, please contact our administrator", Toast.LENGTH_LONG).show();
                                    }else{
                                        Log.e("REGISTER RESULT", result.toString());
                                        Toast.makeText(SignUp.this, "An unexpected error has occurred, please contact our administrator", Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Snackbar snackbar = Snackbar
                                .make(signupPage, "Please read and agree to out terms and conditions and privacy policy", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                } else {
                    Snackbar snackbar = Snackbar
                            .make(signupPage, "Please fll in ALL details", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        });
    }

    private JSONObject GetDetails() {
        JSONObject signupDetails = new JSONObject();
        User user = new User();
        try {
            signupDetails.put(user.user_name_key, etFullName.getText().toString());
            signupDetails.put(user.user_email_key, etEmail.getText().toString());
            signupDetails.put(user.user_password_key, etPassword.getText().toString());
            signupDetails.put(user.user_cmf_password_key, etCfmPassword.getText().toString());
            signupDetails.put(user.user_mobile_key, etMobile.getText().toString());
            signupDetails.put(user.user_address_key, etAddress.getText().toString());
            signupDetails.put(user.user_nric_key, etNRIC.getText().toString());
            signupDetails.put(user.user_dob_key, etDob.getText().toString());
            signupDetails.put(user.user_gender_key, etGender.getText().toString());
            signupDetails.put(user.user_nationality_key, etNationality.getText().toString());
            signupDetails.put(user.user_education_level_key, etEducationLevel.getText().toString());
            signupDetails.put(user.user_race_key, etRace.getText().toString());
            signupDetails.put(user.user_occupation_key, etOccupation.getText().toString());
            signupDetails.put(user.user_salary_key, etSalary.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return signupDetails;
    }

    public boolean validate() {
        boolean check = true;
        if (!hasText(etFullName)) check = false;
        if (!hasText(etMobile)) check = false;
        if (!hasText(etEmail)) check = false;
        if (!hasText(etPassword)) check = false;
        if (!hasText(etCfmPassword)) check = false;
        if (!hasText(etAddress)) check = false;
        if (!hasText(etNRIC)) check = false;
        if (!hasText(etNationality)) check = false;
        if (!hasText(etEducationLevel)) check = false;
        if (!hasText(etRace)) check = false;
        if (!hasText(etOccupation)) check = false;
        if (!hasText(etSalary)) check = false;
        if (!CheckDOB(etDob)) check = false;
        if (!CheckGender(etGender)) check = false;
        return check;
    }

    public static boolean hasText(EditText editText) {
        if (editText.getText().toString().isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean CheckDOB(TextView textView) {
        if (textView.getText().toString().equalsIgnoreCase("Date of birth")) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean CheckGender(TextView textView) {
        if (textView.getText().toString().equalsIgnoreCase("Gender")) {
            return false;
        } else {
            return true;
        }
    }

    private void SelectGender() {
        final CharSequence[] items = {"Male", "Female", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(SignUp.this);
        builder.setTitle("Select Time Slot");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (items[which].equals("Male")) {
                    etGender.setText("Male");
                    dialog.dismiss();
                } else if (items[which].equals("Female")) {
                    etGender.setText("Female");
                    dialog.dismiss();
                } else {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void SelectDate() {
        DatePickerDialog datePickerDialog;
        int year;
        int month;
        int dayOfMonth;
        Calendar calendar;
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(SignUp.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        if(String.valueOf(month).length()==1){
                            etDob.setText(String.format("%d-0%d-%d", year, month + 1, day));
                        }else{
                            etDob.setText(String.format("%d-%d-%d", year, month + 1, day));
                        }

                    }
                }, year, month, dayOfMonth);
        datePickerDialog.updateDate(year, month, dayOfMonth);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    private void hideSoftKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        if(imm.isAcceptingText()) { // verify if the soft keyboard is open
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

}
