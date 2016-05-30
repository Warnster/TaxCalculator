package com.LWA.lewis.taxcalculator;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import java.text.DecimalFormat;


public class MainMenu extends ActionBarActivity {

    DecimalFormat pounds = new DecimalFormat("£###,##0.00");
    static boolean hasSLoan;
    double niLowRate = 0.12;
    double niHighRate = 0.02;
    //Updated from 10600
    static int personalAllowance = 11000;
    int basicTaxThreshold = 43000;
    int higherTaxThreshold = 150000;
    static double studentLoan = 0;
    //initialising tax rate variables
    static double basicTax = 0;
    static double higherTax = 0;
    static double additionalTax = 0;
    static double ni = 0;
    double basicTaxRate = 0.2;
    double higherTaxRate = 0.4;
    double additionalTaxRate = 0.45;
    static double totalTax = 0;
    //setting object variables
    static double totalIncome;
    double taxableIncome = 0;
    EditText txtFieldSalary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        txtFieldSalary = (EditText) findViewById(R.id.edtFieldSalary);
    }

    public void btnCalculateClick(View v) {
        boolean error;
        if (txtFieldSalary.getText().toString().isEmpty()) {
            txtFieldSalary.setText("");
            error = true;
        }
        try {
            Double.parseDouble(txtFieldSalary.getText().toString());
            error = false;
        } catch (NumberFormatException nfe) {
            error = true;
            txtFieldSalary.setText("Enter Salaryyyy");
        }
        if (!error) {
            //edit from 10600 17/05/2016
            personalAllowance = 11000;
            Button btnCalculate = (Button) findViewById(R.id.btnCalculate);
            CheckBox cbLoan = (CheckBox) findViewById(R.id.cbStudentLoan);
            totalIncome = Double.parseDouble(txtFieldSalary.getText().toString());
            if (totalIncome > personalAllowance)
                taxableIncome = totalIncome - personalAllowance;
            //calculates student loan
            studentLoan = 0;
            hasSLoan = false;
            if (cbLoan.isChecked()) {
                studentLoan = StudentLoanRepayment(totalIncome);
                hasSLoan = true;
            }
            //calculating tax for < basicTaxThreshold
            if (totalIncome <= basicTaxThreshold) {
                BasicTierIncome(totalIncome);
                //calculates higherTaxThreshold
            } else if (totalIncome > basicTaxThreshold & totalIncome < 150001) {
                HigherTierIncome(totalIncome);
            } else if (totalIncome > higherTaxThreshold) {
                AdditionalTierIncome(totalIncome);
            }
            startActivity(new Intent(getApplicationContext(), ResultActivity.class));
        }
    }

    public void onLblSalaryClick(View v) {
        boolean containsLetter;
        try {
            Double.parseDouble(txtFieldSalary.getText().toString());
            containsLetter = false;
        } catch(NumberFormatException nfe) {
            containsLetter = true;
        }
        if (containsLetter)
            txtFieldSalary.setText("");
    }

    private void BasicTierIncome(double totalIncome) {
        ni = NIRepayment(totalIncome);
        basicTax = taxableIncome * basicTaxRate;
        totalTax = basicTax + studentLoan + ni;
        higherTax = 0;
    }

    private void HigherTierIncome(double totalIncome) {
        //calculates personal allowance changes on income over 99999
        if (totalIncome > 100000) {
            int lostPA = 0;
            int count = 0;
            double over = 0;
            if (totalIncome < 122001) {
                over = totalIncome - 100000;
            } else {
                over = 22000;
            }
            while (count != over) {
                count++;
            }
            lostPA = count/2;
            personalAllowance = personalAllowance - lostPA;
        }
        //calculating tax
        basicTax = (basicTaxThreshold - personalAllowance) * basicTaxRate;
        higherTax = (totalIncome - basicTaxThreshold) * higherTaxRate;
        ni = NIRepayment(totalIncome);
        totalTax = basicTax + higherTax + ni + studentLoan;
    }

    public void AdditionalTierIncome(double totalIncome) {
        personalAllowance = 0;
        basicTax = basicTaxThreshold * basicTaxRate;
        higherTax = (higherTaxThreshold - basicTaxThreshold) * higherTaxRate;
        ni = NIRepayment(totalIncome);
        additionalTax = (totalIncome - higherTaxThreshold) * additionalTaxRate;
        totalTax = basicTax + higherTax + ni + additionalTax + studentLoan;
    }

    private double StudentLoanRepayment(double totalIncome) {
        int loanThreshold = 21000;
        double loanPaymentPercent = 0.09;
        if (totalIncome > loanThreshold)
            return (totalIncome - loanThreshold) * loanPaymentPercent;
        return 0;
    }

    private double NIRepayment(double totalIncome) {
        int niHighThreshold = 42380;
        int niLowThreshold = 8060;
        if (totalIncome >= niLowThreshold & totalIncome <= niHighThreshold) {
            totalIncome = totalIncome - niLowThreshold;
            return totalIncome * niLowRate;
        } else if (totalIncome > niHighThreshold) {
            double niLowAmount = (niHighThreshold - niLowThreshold) * niLowRate;
            double niHighAmount = (totalIncome - niHighThreshold) * niHighRate;
            return niLowAmount + niHighAmount;
        }
        return 0;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
