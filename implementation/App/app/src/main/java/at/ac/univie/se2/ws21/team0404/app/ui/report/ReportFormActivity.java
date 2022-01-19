package at.ac.univie.se2.ws21.team0404.app.ui.report;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;

import at.ac.univie.se2.ws21.team0404.app.R;
import at.ac.univie.se2.ws21.team0404.app.model.common.ETransactionType;
import at.ac.univie.se2.ws21.team0404.app.utils.EIntents;
import at.ac.univie.se2.ws21.team0404.app.utils.android.LifecycleHandler;
import at.ac.univie.se2.ws21.team0404.app.utils.factory.EChartType;

public class ReportFormActivity extends AppCompatActivity implements IReportFormActivityContract.IView{

    private Spinner chartTypeSpinner;
    private Spinner transactionTypeSpinner;
    private EditText datePickerEditText;
    private Calendar calendar;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    private final ReportFormPresenter presenter = new ReportFormPresenter();
    private LifecycleHandler<ReportFormActivity> lifecycleHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Report Form");
        setContentView(R.layout.activity_report_form);

        ActionBar actionBar = getSupportActionBar();
        assert (actionBar != null);

        actionBar.setDisplayHomeAsUpEnabled(true);

        lifecycleHandler = new LifecycleHandler<>(presenter, this);

        chartTypeSpinner = findViewById(R.id.chart_type_spinner);
        ArrayAdapter<EChartType> chartTypeArrayAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, EChartType.values());
        chartTypeSpinner.setAdapter(chartTypeArrayAdapter);

        transactionTypeSpinner = findViewById(R.id.report_transaction_type_spinner);
        ArrayAdapter<ETransactionType> transactionTypeArrayAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, ETransactionType.values());
        transactionTypeSpinner.setAdapter(transactionTypeArrayAdapter);

        datePickerEditText = findViewById(R.id.report_date_picker);

        Button button = findViewById(R.id.submit_report_button);
        button.setOnClickListener(view -> presenter.buttonPressed());
    }

    @Override
    public void changeActivity() {
        Intent intent = new Intent(this, ChartActivity.class);
        EChartType chartType = EChartType.valueOf(chartTypeSpinner.getSelectedItem().toString().toUpperCase());
        intent.putExtra(EIntents.CHART_TYPE.toString(), chartType);

        ETransactionType transactionType = ETransactionType.valueOf(transactionTypeSpinner.getSelectedItem().toString().toUpperCase());
        intent.putExtra(EIntents.TRANSACTION_TYPE.toString(), transactionType);

        getDateFromDatePicker();
        long timeSpan = ChronoUnit.MINUTES.between(calendar.toInstant(), Calendar.getInstance().toInstant());
        intent.putExtra(EIntents.TIME_SPAN.toString(), timeSpan);

        startActivity(intent);
    }

    private void getDateFromDatePicker() {
        try {
            dateFormat.parse(datePickerEditText.getText().toString());
            calendar = dateFormat.getCalendar();
        } catch (ParseException e) {
            calendar = Calendar.getInstance();
        }
    }

    public void datePickerEditTextOnClick(View v) {
        Calendar current = Calendar.getInstance();
        DatePickerDialog datePicker = new DatePickerDialog(this,
                (view, year, month, day) -> {
                    datePickerEditText.setText(String.format("%d/%d/%d", day, month + 1, year));
                },
                current.get(Calendar.YEAR),
                current.get(Calendar.MONTH),
                current.get(Calendar.DAY_OF_MONTH)
        );
        datePicker.show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            default:
                finish();
                return true;
        }
    }
}