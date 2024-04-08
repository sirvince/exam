package com.example.myapplication.view;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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

    String myFormat = "dd/MM/yyyy";
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

        initGenderOptions();
        calendar = Calendar.getInstance();
        etDateOfBirth.setOnClickListener(v -> showDatePickerDialog());
        btnSubmit.setOnClickListener(v->validateInfo());
    }

    private boolean isValidate = true;
    private void validateInfo() {
        isValidate = true;
        isFieldEmpty(tilFullName);
        isFieldEmpty(tilEmailAddress);
        isFieldEmpty(tilMobileNumber);
        isFieldEmpty(tilDateOfBirth);
        isFieldEmpty(tilAge);
        isFieldEmpty(tilGender);
        isFieldValidate(tilEmailAddress,RegexHelper.REGEX_EMAIL_ADDRESS,"Invalid email address: Must be validated in right format");
        isFieldValidate(tilMobileNumber,RegexHelper.REGEX_MOBILE_NUMBER,"Invalid mobile number: Must be validated in the right format for PH mobile number eg. 09171234567");
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
        String myFormat = "dd/MM/yyyy"; // Define your format
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
            tilAge.setError(age < 18? "Age must be greater than or equal 18" : null);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }


    private boolean isFieldEmpty(TextInputLayout textInputLayout) {
        String inputText = textInputLayout.getEditText().getText().toString().trim();
        if (inputText.isEmpty()) {
            textInputLayout.setError("This field is required!");
            isValidate = false;
            return true;
        } else {
            textInputLayout.setError(null);
            return false;
        }
    }

    public static int calculateAge(Date birthdate) {
        Date currentDate = new Date();
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        int birthYear = Integer.parseInt(yearFormat.format(birthdate));
        int currentYear = Integer.parseInt(yearFormat.format(currentDate));

        return currentYear - birthYear;
    }


    private boolean isFieldValidate(TextInputLayout textInputLayout,String regex,String errorMessage) {
        String inputText = textInputLayout.getEditText().getText().toString().trim();

        if(!RegexHelper.isRegexValidate(inputText,regex)){
            textInputLayout.setError(errorMessage);
            isValidate = false;
            return true;
        }
        return false;
    }
}