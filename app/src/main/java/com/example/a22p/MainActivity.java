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

        String[] inputTypes = {"Inch", "Feet", "Yard", "Mile", "Centre Meters", "Kilometers", "Pound", "Ounce", "Ton", "Gram", "Kilogram", "Celsius", "Fahrenheit", "Kelvin"};

        String[] lengthTypes = {"Inch", "Feet", "Yard", "Mile", "Centre Meters", "Kilometers"};

        String[] weightTypes = {"Pound", "Ounce", "Ton", "Gram", "Kilogram"};

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
                if(position <= 5) {
                    ArrayAdapter<String> adapterOutput = new ArrayAdapter(ctx, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, lengthTypes);
                    outputSpinner.setAdapter(adapterOutput);
                }
                else if(position <= 10) {
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

        double[] lengths = {2.54, 30.48, 91.44, 1.60934};
        double[] massConversions = {0.453592, 28.3495, 907.285};

        // Convert to CM
        if(source < 3) {
            result = input * lengths[source];
            if(target == 4) {
                return result;
            }
            else if (target < 3) {
                return result / lengths[target];
            }
            else {
                return  result /100000;
            }
        }
        else if(source == 3) {
            result = input * 1.60934 * 1000 * 100;
            if(target < 3) {
                return  result / lengths[target];
            }
            else if(target == 3) {
                return result;
            }
            else {
                return result / 100000;
            }
        }
        else if(source == 4) {
            if(target < 3) {
                return input * lengths[target];
            }
            else if(target == 3) {
                return  input/(100000 * (lengths[target]));
            }
            else {
                return input / 100000;
            }
        }
        else if(source == 5) {
            //convert to cm
            result = input / 100000;
            if(target == 4) {
                return result;
            }
            else if (target < 3) {
                return result * lengths[target];
            }
            else {
                return  result /(100000 * lengths[target]);
            }

        }

        else if(source < 11) {
            result = input;
            // Convert everything to be in grams
            if(source == 6 || source == 8) {
                result = input * massConversions[source -6] * 1000;
            }
            if(source == 7) {
                result = input * massConversions[source -6];
            }
            else if(source == 10) {
                result = input * 1000;
            }

            switch (target) {
                case 0:
                    return result / (massConversions[target]*1000);
                case 1:
                    return result / massConversions[target];
                case 2:
                    return result / (massConversions[target]*1000);
                case 3:
                    return result;
                case 4:
                    return result /1000;
            }

        }


        if(source == 11) {
            if(target == 12) {
                return input*1.8 + 32;
            }
            else {
                return input + 273.15;
            }
        }
        if(source == 12) {
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