package com.david.superlist.Login;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.david.superlist.R;
import com.david.superlist.pojos.UsuariosRegistrados;
import com.google.android.material.textfield.TextInputEditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    ImageButton goBack;
    TextInputEditText registerEmailEditText, firstPasswordEditText, secondPasswordEditText;
    Button buttonRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerEmailEditText = findViewById(R.id.RegisterInputEmail);
        firstPasswordEditText = findViewById(R.id.RegisterInputFirstPassword);
        secondPasswordEditText = findViewById(R.id.RegisterInputSecondPassword);
        goBack = findViewById(R.id.imgButtonGoBackRegister);
        buttonRegister = findViewById(R.id.buttonRegister);

        buttonRegister.setOnClickListener(v -> {
            String email = registerEmailEditText.getText().toString();
            String firstPasswordInput = firstPasswordEditText.getText().toString();
            String secondPasswordInput = secondPasswordEditText.getText().toString();

            if (!checkRegisterErrors(email, firstPasswordInput, secondPasswordInput)) {
                UsuariosRegistrados.addNormalUser(email, firstPasswordInput);
                finish();
            }
        });

        goBack.setOnClickListener(view -> {
            finish();
        });
    }

    private boolean checkRegisterErrors(String email, String firstPasswordInput, String secondPasswordInput) {
        boolean thereIsAnError = false;

        if (TextUtils.isEmpty(email)) {
            setEmptyFieldError(registerEmailEditText);
            thereIsAnError = true;
        } else if (!checkEmail(email)) {
            setWrongEmailFormatError(registerEmailEditText);
            thereIsAnError = true;
        }

        if (UsuariosRegistrados.existUser(email)) {
            setUserAlreadyExistsError(registerEmailEditText);
            thereIsAnError = true;
        }

        if (TextUtils.isEmpty(firstPasswordInput)) {
            setEmptyFieldError(firstPasswordEditText);
            thereIsAnError = true;
        }
        if (TextUtils.isEmpty(secondPasswordInput)) {
            setEmptyFieldError(secondPasswordEditText);
            thereIsAnError = true;
        }

        if (!firstPasswordInput.equals(secondPasswordInput)) {
            setNotMatchingPasswordsError(secondPasswordEditText);
            thereIsAnError = true;
        }

        return thereIsAnError;
    }

    private void setEmptyFieldError(EditText et) {
        String EmptyFieldErrorMessage = getResources().getString(R.string.textoCampoObligatorio);
        et.setError(EmptyFieldErrorMessage);
    }

    private boolean checkEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void setWrongEmailFormatError(EditText et) {
        String wrongEmailFormatMessage = getResources().getString(R.string.errorDeFormatoDeEmail);
        et.setError(wrongEmailFormatMessage);
    }

    private void setNotMatchingPasswordsError(EditText et) {
        String notMatchingPasswordsErrorMessage = getResources().getString(R.string.mensajeErrorContraseñasNoCoinciden);
        et.setError(notMatchingPasswordsErrorMessage);
    }

    private void setUserAlreadyExistsError(EditText et) {
        String userAlreadyExistsError = getResources().getString(R.string.textoErrorUsuarioExistente);
        et.setError(userAlreadyExistsError);
    }
}