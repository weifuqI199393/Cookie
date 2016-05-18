package com.cookie.ui.login.view.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import com.cookie.ui.R;
import com.cookie.ui.login.Presenter.imp.pimp;
import com.cookie.ui.login.Presenter.pinf;
import com.cookie.ui.login.model.bean.User;
import com.cookie.ui.login.view.vinf;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements vinf {


    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private pinf _pinf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);


        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_NULL) {

                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                _pinf.performOnClick();
                //_pinf.Login();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        _pinf=new pimp(this);
    }






    @Override
    public void setData(final User user) {
             runOnUiThread(new Runnable() {     //ugly
                          public void run() {
                              mEmailView.setText(user.getTel());
                              mPasswordView.setText(user.getPwd());
                          }
                      }
        );

    }

    @Override
    public void toMainActivity(final User user) {
        runOnUiThread(new Runnable() {     //ugly
                          public void run() {
                              Toast.makeText(LoginActivity.this, "登入："+ user.getTel() + "," + user.getPwd(), Toast.LENGTH_SHORT).show();
                          }
                      }
        );
    }

    @Override
    public String getTel() {
        return mEmailView.getText().toString();
    }

    @Override
    public String getPwd() {
        return mPasswordView.getText().toString();
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }


}

