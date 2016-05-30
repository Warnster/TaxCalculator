package com.LWA.lewis.taxcalculator;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.text.NumberFormat;
import java.util.Locale;

public class ResultActivity extends ActionBarActivity {

    TextView txtVTotalIncome, txtVLoanClick, txtVLblBasic, txtHigherTax, txtVPA, txtVATax;
    TextSwitcher salarySwitcher, totalTaxSwitcher, nISwitcher, basicTSwitcher, studentSwitcher, higherTSwitcher,
            paSwitcher, atSwitcher;
    double totalIncome = MainMenu.totalIncome;
    double totalTax = MainMenu.totalTax;
    double postTaxIncome = totalIncome - totalTax;
    double basicTax = MainMenu.basicTax;
    double studentLoan = MainMenu.studentLoan;
    double higherTax = MainMenu.higherTax;
    double personalAllowance = MainMenu.personalAllowance;
    double additionalTax = MainMenu.additionalTax;
    NumberFormat pounds = NumberFormat.getCurrencyInstance(Locale.UK);
    int currentSalaryIndex = 0;
    int currentTTIndex = 0;
    int currentNIIndex = 0;
    int currentBTIndex = 0;
    int currentSIndex = 0;
    int currentHTIndex = 0;
    int currentATIndex = 0;
    double ni = MainMenu.ni;
    boolean salaryClicked = false;
    boolean ttClicked = false;
    boolean niClicked = false;
    boolean btClicked = false;
    boolean sClicked = false;
    boolean htClicked = false;
    boolean paClicked = false;
    boolean atClicked = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        String str = pounds.format(postTaxIncome);
        txtVTotalIncome = (TextView) findViewById(R.id.textView9);
        txtVTotalIncome.setText(str);
        txtHigherTax = (TextView) findViewById(R.id.textView5);
        txtVLoanClick = (TextView) findViewById(R.id.textView4);
        txtVLblBasic = (TextView) findViewById(R.id.textView17);
        salarySwitcher = (TextSwitcher) findViewById(R.id.textSwitcher);
        totalTaxSwitcher = (TextSwitcher) findViewById(R.id.textSwitcher2);
        nISwitcher = (TextSwitcher) findViewById(R.id.textSwitcher3);
        basicTSwitcher = (TextSwitcher) findViewById(R.id.textSwitcher4);
        studentSwitcher = (TextSwitcher) findViewById(R.id.textSwitcher5);
        higherTSwitcher = (TextSwitcher) findViewById(R.id.textSwitcher6);
        paSwitcher = (TextSwitcher) findViewById(R.id.textSwitcher7);
        txtVPA = (TextView) findViewById(R.id.textView6);
        txtVATax = (TextView) findViewById(R.id.textView10);
        atSwitcher = (TextSwitcher) findViewById(R.id.textSwitcher8);

        // Declare the in and out animations and initialize them
        Animation in = AnimationUtils.loadAnimation(this,
                android.R.anim.fade_in);
        Animation out = AnimationUtils.loadAnimation(this,
                android.R.anim.fade_out);

        // set the animation type of textSwitcher
        salarySwitcher.setInAnimation(in);
        salarySwitcher.setOutAnimation(out);
        totalTaxSwitcher.setInAnimation(in);
        totalTaxSwitcher.setOutAnimation(out);
        nISwitcher.setInAnimation(in);
        nISwitcher.setOutAnimation(out);
        basicTSwitcher.setInAnimation(in);
        basicTSwitcher.setOutAnimation(out);
        studentSwitcher.setInAnimation(in);
        studentSwitcher.setOutAnimation(out);
        higherTSwitcher.setInAnimation(in);
        higherTSwitcher.setOutAnimation(out);
        paSwitcher.setInAnimation(in);
        paSwitcher.setOutAnimation(out);

        if (personalAllowance == 11000) {
            paSwitcher.setVisibility(View.INVISIBLE);
            txtVPA.setVisibility(View.GONE);
        } else {
            txtVLoanClick.setBackgroundColor(0xfffbfbfb);
        }
        if (totalIncome <= 150000) {
            txtVATax.setVisibility(View.GONE);
        }
        if (higherTax == 0) {
            higherTSwitcher.setVisibility(View.INVISIBLE);
            txtHigherTax.setVisibility(View.GONE);
        }
        if (!MainMenu.hasSLoan) {
            txtVLoanClick.setVisibility(View.GONE);
            studentSwitcher.setVisibility(View.INVISIBLE);
        }
    }

    //Factory Switcher method

    private ViewSwitcher.ViewFactory setFactorySwitcher (final int colour) {
    return new ViewSwitcher.ViewFactory() {
        @Override
        public View makeView() {
            TextView myText = new TextView(ResultActivity.this);
            myText.setTextSize(15);
            myText.setBackgroundColor(colour);
            myText.setPadding(0,0,0,17);
            myText.setGravity(Gravity.CENTER);
            return myText;
        }
    };
    }

    //Total Salary Deduction methods

    public void onLblTaxDeductionClick(View v) {
        if (!ttClicked) {
            ttClicked = true;
            totalTaxSwitcher.setFactory(setFactorySwitcher(0xfffdfdfd));
            String[] S = {"Annual\n" + pounds.format(totalTax), "Monthly\n" + pounds.format(totalTax/12),
                    "Weekly\n" + pounds.format(totalTax/52)};
            totalTaxSwitcher.setText(S[currentTTIndex]);
        } else {
            ttClicked = false;
            totalTaxSwitcher.removeAllViews();
        }
    }

    public void onTTClick(View v){
        currentTTIndex++;
        if (currentTTIndex > 2)
            currentTTIndex = 0;
        String[] S = {"Annual\n" + pounds.format(totalTax), "Monthly\n" + pounds.format(totalTax/12),
                "Weekly\n" + pounds.format(totalTax/52)};
        totalTaxSwitcher.setText(S[currentTTIndex]);

    }

    //Salary Breakdown Methods

    public void onLblSalaryBreakClick(View v) {
        if (!salaryClicked) {
            salaryClicked = true;
            salarySwitcher.setFactory(setFactorySwitcher(0xfff5f5f5));
            String[] S = {"Annual\n" + pounds.format(postTaxIncome), "Monthly\n" + pounds.format(postTaxIncome / 12),
                    "Weekly\n" + pounds.format(postTaxIncome/52)};
            salarySwitcher.setText(S[currentSalaryIndex]);
        } else {
            salaryClicked = false;
            salarySwitcher.removeAllViews();
        }
    }

    public void onSSListener(View v) {
        currentSalaryIndex++;
        if (currentSalaryIndex > 2)
            currentSalaryIndex = 0;
        String[] S = {"Annual\n" + pounds.format(postTaxIncome), "Monthly\n" + pounds.format(postTaxIncome / 12),
                "Weekly\n" + pounds.format(postTaxIncome/52)};
        salarySwitcher.setText(S[currentSalaryIndex]);
    }

    //Student Loan methods

    public void onStudentLoanClick(View v) {
        if (!sClicked) {
            sClicked = true;
            if (personalAllowance != 11000) {
                studentSwitcher.setFactory(setFactorySwitcher(0xfffbfbfb));
                txtVLoanClick.setBackgroundColor(0xfffbfbfb);
            } else {
                studentSwitcher.setFactory(setFactorySwitcher(0xfff5f5f5));
                txtVLoanClick.setBackgroundColor(0xfff5f5f5);
            }
            String[] s = {"Annual\n" + pounds.format(studentLoan), "Monthly\n" + pounds.format(studentLoan/12),
                    "Weekly\n" + pounds.format(studentLoan/52)};
            studentSwitcher.setText(s[currentSIndex]);
        } else {
            sClicked = false;
            studentSwitcher.removeAllViews();
        }
    }

    public void onSSwitcherClick(View V) {
        currentSIndex++;
        if (currentSIndex > 2)
            currentSIndex = 0;
        String[] s = {"Annual\n" + pounds.format(studentLoan), "Monthly\n" + pounds.format(studentLoan/12),
                "Weekly\n" + pounds.format(studentLoan/52)};
        studentSwitcher.setText(s[currentSIndex]);
    }

    //National Insurance methods

    public void onNIClick(View v) {
        if (!niClicked) {
            niClicked = true;
            nISwitcher.setFactory(setFactorySwitcher(0xfff3f3f3));
            String[] S = {"Annual\n" + pounds.format(ni), "Monthly\n" + pounds.format(ni/12), "Weekly\n" + pounds.format(ni/52)};
            nISwitcher.setText(S[currentNIIndex]);
        } else {
            nISwitcher.removeAllViews();
            niClicked = false;
        }
    }

    public void onNISwitcherClick(View v) {
        currentNIIndex++;
        if (currentNIIndex > 2)
            currentNIIndex = 0;
        String[] S = {"Annual\n" + pounds.format(ni), "Monthly\n" + pounds.format(ni/12), "Weekly\n" + pounds.format(ni/52)};

        nISwitcher.setText(S[currentNIIndex]);
    }

    //Basic Tax methods

    public void onBasicTaxClick(View v){
        if (!btClicked) {
            btClicked = true;
            basicTSwitcher.setFactory(setFactorySwitcher(0xfffbfbfb));
            String[] S = {"Annual\n" + pounds.format(basicTax), "Monthly\n" + pounds.format(basicTax / 12),
                    "Weekly\n" + pounds.format(basicTax / 52)};
            basicTSwitcher.setText(S[currentBTIndex]);
        } else {
            btClicked = false;
            basicTSwitcher.removeAllViews();
        }
    }

    public void onBTClick(View v) {
        currentBTIndex++;
        if (currentBTIndex > 2)
            currentBTIndex = 0;
        String[] S = {"Annual\n" + pounds.format(basicTax), "Monthly\n" + pounds.format(basicTax / 12),
                "Weekly\n" + pounds.format(basicTax / 52)};
        basicTSwitcher.setText(S[currentBTIndex]);
    }

    //Higher Tax Methods

    public void onLblHigherTaxClick(View v) {
        if (!htClicked) {
            htClicked = true;
            higherTSwitcher.setFactory(setFactorySwitcher(0xfffdfdfd));
            String[] S = {"Annual\n" + pounds.format(higherTax), "Monthly\n" + pounds.format(higherTax/12), "Weekly\n" + pounds.format(higherTax/52)};
            higherTSwitcher.setText(S[currentHTIndex]);
        } else {
            htClicked = false;
            higherTSwitcher.removeAllViews();
        }
    }

    public void onHTSwitcherClick(View v) {
        currentHTIndex++;
        if (currentHTIndex > 2)
            currentHTIndex = 0;
        String[] S = {"Annual\n" + pounds.format(higherTax), "Monthly\n" + pounds.format(higherTax/12), "Weekly\n" + pounds.format(higherTax/52)};
        higherTSwitcher.setText(S[currentHTIndex]);
    }

    //Personal Allowance

    public void onLblPAClick(View v) {
        if (!paClicked) {
            paClicked = true;
            String S = "Total\n" + pounds.format(personalAllowance);
            paSwitcher.setFactory(setFactorySwitcher(0xfff5f5f5));
            paSwitcher.setText(S);
        } else {
            paClicked = false;
            paSwitcher.removeAllViews();
        }

    }

    //Additional Tax

    public void onLblATClick(View v) {
        if (!atClicked) {
            atClicked = true;
            atSwitcher.setFactory(setFactorySwitcher(0xfff3f3f3));
            String[] s = {"Annual\n" + pounds.format(additionalTax), "Monthly\n" + pounds.format(additionalTax/12),
            "Weekly\n" + pounds.format(additionalTax/52)};
            atSwitcher.setText(s[currentATIndex]);
        } else {
            atClicked = false;
            atSwitcher.removeAllViews();
        }
    }

    public void onATSwitcherClick(View v) {
        currentATIndex++;
        if (currentATIndex > 2)
            currentATIndex = 0;
        String[] s = {"Annual\n" + pounds.format(additionalTax), "Monthly\n" + pounds.format(additionalTax/12),
                "Weekly\n" + pounds.format(additionalTax/52)};
        atSwitcher.setText(s[currentATIndex]);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_result, menu);
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
