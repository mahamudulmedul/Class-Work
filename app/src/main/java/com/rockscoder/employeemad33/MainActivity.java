package com.rockscoder.employeemad33;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText editTextName, editTextAge, editTextPhone, editTextDOB;
    private List<String> skills = new ArrayList<>();
    private String gender = "Male";
    private String dob = "";
    private String city = "";
    private String empType = "Base Salaried Employee";
    private RadioGroup genderRG, employeeTypeRG;
    private Spinner spinner;
    private boolean isLoggedIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextName = findViewById(R.id.editTextName);
        editTextAge = findViewById(R.id.editTextAge);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextDOB = findViewById(R.id.editTextDOB);
        spinner = findViewById(R.id.spinner);

        genderRG = findViewById(R.id.genderRG);

        genderRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                gender = radioButton.getText().toString();
                //showToast(gender);
            }
        });

        employeeTypeRG = findViewById(R.id.employeeTypeRG);
        employeeTypeRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                empType = radioButton.getText().toString();
                //showToast(gender);
            }
        });

        String[] cities = getResources().getStringArray(R.array.city);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, cities);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                city = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void registerEmployee(View view) {
        String name = editTextName.getText().toString();
        String age = editTextAge.getText().toString();
        String phone = editTextPhone.getText().toString();


        Employee employee = new Employee(name, age, dob, gender, phone, empType, city, skills);

        List<Employee> employeeArrayList = new ArrayList<>();
        employeeArrayList.add(employee);

        Intent intent = new Intent(this, EmployeeDetailsActivity.class);
        //intent.putExtra("name", name);
        //intent.putExtra("age", age);
        //intent.putExtra("phone", phone);


        intent.putExtra("emp", (Serializable) employee);
        intent.putExtra("empList", (ArrayList<Employee>) employeeArrayList);

        startActivity(intent);

        //showToast(name);
        //showToast(age);
        //showToast(phone);
    }

    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    public void selectSkill(View view) {
        CheckBox checkBox = (CheckBox) view;
        boolean status = checkBox.isChecked();

        if (status) {
            skills.add(checkBox.getText().toString());
        } else {
            skills.remove(checkBox.getText().toString());
        }
    }

    public void showDOB(View view) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog =
                new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        editTextDOB.setText(day + "/" + month + "/" + year);
                        dob = day + "/" + month + "/" + year;
                    }
                }, year, month, day);

        datePickerDialog.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        showToast("onCreateOptionsMenu");
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        showToast("onPrepareOptionsMenu");
        MenuItem loginItem = menu.findItem(R.id.item_login);
        MenuItem logoutItem = menu.findItem(R.id.item_logout);
        if(isLoggedIn){
            loginItem.setVisible(false);
            logoutItem.setVisible(true);
        }else {
            loginItem.setVisible(true);
            logoutItem.setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.item_login:
                isLoggedIn = true;
                break;
            case R.id.item_logout:
                isLoggedIn = false;
                break;
            case R.id.item_settings:
                break;
            case R.id.item_red:
                if(item.isChecked()){
                    item.setChecked(false);
                }else {
                    item.setChecked(true);
                }
                break;
            case R.id.item_blue:
                if(item.isChecked()){
                    item.setChecked(false);
                }else {
                    item.setChecked(true);
                }
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
