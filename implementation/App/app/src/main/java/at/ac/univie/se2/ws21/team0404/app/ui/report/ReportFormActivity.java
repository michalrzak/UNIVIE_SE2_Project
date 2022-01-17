package at.ac.univie.se2.ws21.team0404.app.ui.report;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import at.ac.univie.se2.ws21.team0404.app.R;
import at.ac.univie.se2.ws21.team0404.app.model.common.ETransactionType;
import at.ac.univie.se2.ws21.team0404.app.utils.EIntents;
import at.ac.univie.se2.ws21.team0404.app.utils.android.LifecycleHandler;
import at.ac.univie.se2.ws21.team0404.app.utils.factory.EChartType;
import at.ac.univie.se2.ws21.team0404.app.utils.factory.ETimeSpan;

public class ReportFormActivity extends AppCompatActivity implements IReportFormActivityContract.IView{

    private Spinner chartTypeSpinner;
    private Spinner timeSpanSpinner;
    private Spinner transactionTypeSpinner;

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

        timeSpanSpinner = findViewById(R.id.time_span_spinner);
        ArrayAdapter<ETimeSpan> timeSpanArrayAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, ETimeSpan.values());
        timeSpanSpinner.setAdapter(timeSpanArrayAdapter);

        transactionTypeSpinner = findViewById(R.id.report_transaction_type_spinner);
        ArrayAdapter<ETransactionType> transactionTypeArrayAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, ETransactionType.values());
        transactionTypeSpinner.setAdapter(transactionTypeArrayAdapter);

        Button button = findViewById(R.id.submit_report_button);
        button.setOnClickListener(view -> presenter.buttonPressed());
    }

    @Override
    public void changeActivity() {
        Intent intent = new Intent(this, ChartActivity.class);
        EChartType chartType = EChartType.valueOf(chartTypeSpinner.getSelectedItem().toString().toUpperCase());
        intent.putExtra(EIntents.CHART_TYPE.toString(), chartType);

        // not the most elegant solution (could possibly be solved by creating a custom ArrayAdapter to hold the enum itself
        ETimeSpan timeSpan = null;
        String value = timeSpanSpinner.getSelectedItem().toString();
        for (ETimeSpan eachTimeSpan: ETimeSpan.values()){
            if (value.equals(eachTimeSpan.toString())){
                timeSpan = eachTimeSpan;
            }
        }
        intent.putExtra(EIntents.TIME_SPAN.toString(), timeSpan);

        ETransactionType transactionType = ETransactionType.valueOf(transactionTypeSpinner.getSelectedItem().toString().toUpperCase());
        intent.putExtra(EIntents.TRANSACTION_TYPE.toString(), transactionType);

        startActivity(intent);
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