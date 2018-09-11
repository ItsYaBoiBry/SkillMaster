package com.example.damianlzy.skillmaster;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
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

import java.util.concurrent.ExecutionException;

public class SignIn extends AppCompatActivity {
    EditText etUsername, etPassword;
    TextView tvHaveAccount, tvForgotPassword;
    Button signin;
    Toolbar toolbar;
    String usernameKey = "email";
    String passwordKey = "password";
    RelativeLayout loginPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        loginPage = findViewById(R.id.login_page);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        signin = findViewById(R.id.signin);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyBoard();
                String loginresult = Login();
                if(loginresult==null){
                    Log.e("RESULT","NULL");
                }else{
                    Sessions sessions = new Sessions(SignIn.this);
                    try {
                        JSONObject result = new JSONObject(loginresult);
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

                            Intent intent = new Intent(SignIn.this, ActivityHome.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }else if(result.has("error")){
                            Log.e("LOGIN RESULT", "Login Failed");
                            String error = result.getString("error");
                            if(error.equalsIgnoreCase("unauthorised")){
                                Snackbar snackbar = Snackbar
                                        .make(loginPage, "Invalid login details", Snackbar.LENGTH_LONG);
                                snackbar.show();
                            }
                        }else{
                            Log.e("LOGIN RESULT", result.toString());
                            Toast.makeText(SignIn.this, "An unexpected error has occurred, please contact our administrator", Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    private void hideSoftKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        if(imm.isAcceptingText()) { // verify if the soft keyboard is open
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
    public String Login(){
        PostRequest post = new PostRequest();
        try {
            String password = etPassword.getText().toString().trim();
            String username = etUsername.getText().toString().trim();

            JSONObject details = new JSONObject();
            details.put(usernameKey,username);
            details.put(passwordKey,password);

            Links links = new Links();
            String result = post.execute(links.loginLink(),details.toString()).get();
            Log.e("LOGIN RESULTS", result);
            return result;

        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

}
