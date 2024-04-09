package com.example.myapplication.view;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.repository.model.ResponseModel;
import com.example.myapplication.repository.remote.ApiClient;
import com.example.myapplication.repository.remote.ApiService;
import com.example.myapplication.viewmodel.PersonRegistrationViewModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonalRegistrationActivity extends AppCompatActivity {

    private TextInputLayout tilFullName;
    private TextInputLayout tilEmailAddress;
    private TextInputLayout tilMobileNumber;
    private TextInputLayout tilDateOfBirth;
    private TextInputEditText etDateOfBirth;
    private TextInputLayout tilAge;
    private Calendar calendar;

    private TextInputLayout tilGender;
    private AutoCompleteTextView actvGender;


    private Button btnSubmit;
    private boolean hasError = false;
    String myFormat = "dd/MM/yyyy";
    private PersonRegistrationViewModel personRegistrationViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_personal_registration);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();
    }


    private void  init(){

        tilFullName = findViewById(R.id.tilFullName);
        tilEmailAddress = findViewById(R.id.tilEmailAddress);
        tilMobileNumber = findViewById(R.id.tilMobileNumber);
        tilDateOfBirth = findViewById(R.id.tilDateOfBirth);
        etDateOfBirth = findViewById(R.id.etDateOfBirth);
        tilAge = findViewById(R.id.tilAge);
        tilGender = findViewById(R.id.tilGender);
        actvGender = findViewById(R.id.actvGender);
        btnSubmit = findViewById(R.id.btnSubmit);
        personRegistrationViewModel = new ViewModelProvider(this).get(PersonRegistrationViewModel.class);
        initGenderOptions();
        calendar = Calendar.getInstance();
        personRegistrationViewModel.getApiResponseLiveData().observe(this, responseModel -> {
            if(responseModel != null)
                new MaterialAlertDialogBuilder(PersonalRegistrationActivity.this)
                        .setTitle(responseModel.getCode() == 200 ? "Success" : "Failed")
                        .setMessage(responseModel.getMessage())
                        .show();
        });

        etDateOfBirth.setOnClickListener(v -> showDatePickerDialog());
        btnSubmit.setOnClickListener(v->validateInfo());
    }


    private void validateInfo() {
        hasError = false;
        hasError = ValidationHelper.isFieldEmpty(tilDateOfBirth);
        hasError = ValidationHelper.isFieldEmpty(tilAge);
        hasError = ValidationHelper.isFieldEmpty(tilGender);
        hasError = ValidationHelper.isFieldValidate(tilFullName,RegexHelper.REGEX_NAME,"Invalid name: Text only and characters like comma and period");
        hasError = ValidationHelper.isFieldValidate(tilEmailAddress,RegexHelper.REGEX_EMAIL_ADDRESS,"Invalid email address: Must be validated in right format");
        hasError = ValidationHelper.isFieldValidate(tilMobileNumber,RegexHelper.REGEX_MOBILE_NUMBER,"Invalid mobile number: Must be validated in the right format for PH mobile number eg. 09171234567");

        if(!hasError){

            Person person = new Person(
                    tilFullName.getEditText().getText().toString().trim(),
                    tilEmailAddress.getEditText().getText().toString().trim(),
                    tilMobileNumber.getEditText().getText().toString().trim(),
                    tilDateOfBirth.getEditText().getText().toString().trim(),
                    Integer.parseInt(tilAge.getEditText().getText().toString().trim()),
                    tilGender.getEditText().getText().toString().trim()
            );

            Log.v(PersonalRegistrationActivity.class.getSimpleName(),new Gson().toJson(person));
            personRegistrationViewModel.registerDetails(person);
        }
    }

    private void initGenderOptions() {
        String[] genderOptions = getResources().getStringArray(R.array.gender_options);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, genderOptions);
        actvGender.setAdapter(adapter);
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, monthOfYear, dayOfMonth) -> {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, monthOfYear);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateDateOfBirth();
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        Calendar maxDateCalendar = Calendar.getInstance();
        datePickerDialog.getDatePicker().setMaxDate(maxDateCalendar.getTimeInMillis());
        datePickerDialog.show();
    }

    private void updateDateOfBirth() {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
        etDateOfBirth.setText(sdf.format(calendar.getTime()));
        computeAge(sdf.format(calendar.getTime()));

    }

    private void computeAge(String dateOfBirth)  {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
            Date birthdate = sdf.parse(dateOfBirth);
            int age = calculateAge(birthdate);
            tilAge.getEditText().setText(String.valueOf(age));
            tilAge.setError(ValidationHelper.isAgeValidate(age) ? "Age must be greater than or equal 18" : null);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static int calculateAge(Date birthdate) {
        Date currentDate = new Date();
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        int birthYear = Integer.parseInt(yearFormat.format(birthdate));
        int currentYear = Integer.parseInt(yearFormat.format(currentDate));

        return currentYear - birthYear;
    }

}