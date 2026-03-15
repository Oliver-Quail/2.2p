package com.example.a22p;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        int source = 0;
        int target = 0;
        double value = 1;

        String[] inputTypes = {"USD", "AUD", "EUR", "JPY", "GBP", "Miles Per Gallon", "Kilometers Per Liter", "Gallon", "Liters", "Nautical Mile", "Kilometer", "Celsius", "Fahrenheit", "Kelvin"};

        String[] lengthTypes = {"USD", "AUD", "EUR", "JPY", "GBP"};

        String[] weightTypes = {"Miles Per Gallon", "Kilometers Per Liter", "Gallon", "Liters", "Nautical Mile", "Kilometer"};

        String[] temperatureTypes = {"Celsius", "Fahrenheit", "Kelvin"};

        ArrayAdapter<String> adapter = new ArrayAdapter(this, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, inputTypes);

        Spinner inputSpinner = (Spinner) findViewById(R.id.inputSpinner);
        Spinner outputSpinner = (Spinner) findViewById(R.id.outputSpinner);
        EditText inputValue = (EditText) findViewById(R.id.inputValue);

        inputSpinner.setAdapter(adapter);

        Context ctx = this;

        inputSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //AutoCompleteTextView outputType = (AutoCompleteTextView)findViewById(R.id.autoCompleteTextViewOutputType);
                if(position <= 4) {
                    ArrayAdapter<String> adapterOutput = new ArrayAdapter(ctx, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, lengthTypes);
                    outputSpinner.setAdapter(adapterOutput);
                }
                else if(position == 5) {
                    String[] temp = {"Kilometers Per Liter"};
                    ArrayAdapter<String> adapterOutput = new ArrayAdapter(ctx, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, temp);
                    outputSpinner.setAdapter(adapterOutput);
                }
                else if(position == 6) {
                    String[] temp = {"Miles Per Gallon"};
                    ArrayAdapter<String> adapterOutput = new ArrayAdapter(ctx, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, temp);
                    outputSpinner.setAdapter(adapterOutput);
                }
                else if(position == 7) {
                    String[] temp = {"Liters"};
                    ArrayAdapter<String> adapterOutput = new ArrayAdapter(ctx, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, temp);
                    outputSpinner.setAdapter(adapterOutput);
                }
                else if(position == 8) {
                    String[] temp = {"Gallons"};
                    ArrayAdapter<String> adapterOutput = new ArrayAdapter(ctx, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, temp);
                    outputSpinner.setAdapter(adapterOutput);
                }
                else if(position == 9) {
                    String[] temp = {"Kilometers"};
                    ArrayAdapter<String> adapterOutput = new ArrayAdapter(ctx, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, temp);
                    outputSpinner.setAdapter(adapterOutput);
                }
                else if(position == 10) {
                    String[] temp = {"Nautical Miles"};
                    ArrayAdapter<String> adapterOutput = new ArrayAdapter(ctx, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, temp);
                    outputSpinner.setAdapter(adapterOutput);
                }
                else if(position <= 11) {
                    ArrayAdapter<String> adapterOutput = new ArrayAdapter(ctx, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, weightTypes);
                    outputSpinner.setAdapter(adapterOutput);
                }
                else {
                    ArrayAdapter<String> adapterOutput = new ArrayAdapter(ctx, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, temperatureTypes);
                    outputSpinner.setAdapter(adapterOutput);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.d("MainActivity", "Nothing selected");

            }
        });


        findViewById(R.id.convertButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView resultText = findViewById(R.id.resultValueText);
                if(inputSpinner.getSelectedItemPosition() != outputSpinner.getSelectedItemPosition()) {
                    if(Double.parseDouble(inputValue.getText().toString()) > 0) {
                        resultText.setText(Double.toString(convertUnit(inputSpinner.getSelectedItemPosition(), outputSpinner.getSelectedItemPosition(), Double.parseDouble(inputValue.getText().toString()))));
                    }
                    else {
                        Toast.makeText(ctx, "Enter a positive number", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(ctx, "Invalid Conversion", Toast.LENGTH_LONG).show();
                }

            }
        });

    }


    public double convertUnit(int source, int target, double input) {
        double result = 0.0;

        double[] lengths = {1.55,0.92,148.50,0.78};

        // Convert to CM
        if(source <= 4) {
            double usdAmount = 0.0;
            switch (source){
                case 0:
                    usdAmount = input;
                    break;
                case 1:
                    usdAmount = input / lengths[0];
                    break;
                case 2:
                    usdAmount = input / lengths[1];
                    break;
                case 3:
                    usdAmount = input / lengths[2];
                    break;
                case 4:
                    usdAmount = input / lengths[3];
                    break;
            }
            switch (target) {
                case 0:
                    return usdAmount;
                case 1:
                    return usdAmount * lengths[0];
                case 2:
                    return input * lengths[1];
                case 3:
                    return input * lengths[2];
                case 4:
                    return input * lengths[3];
            }
        }
        else if(source <= 10) {
            switch (source) {
                case 5:
                    return input * 0.425;
                case 6:
                    return input / 0.425;
                case 7:
                    return input * 3.785;
                case 8:
                    return input / 3.785;
                case 9:
                    return input * 1.852;
                case 10:
                    return input / 1.852;

            }

        }

        else if(source == 11) {
            if(target == 12) {
                return input*1.8 + 32;
            }
            else {
                return input + 273.15;
            }
        }
        else if(source == 12) {
            if(target == 11) {
                return (input-32)/1.8;
            }
            else {
                return (input-32)/1.8 + 273.15;
            }
        }
        else if(source == 13) {
            result = input - 273.15;
            if(target == 11) {
                return result;
            }
            else {
                return result*1.8 + 32;
            }
        }



        return result;
    }


}