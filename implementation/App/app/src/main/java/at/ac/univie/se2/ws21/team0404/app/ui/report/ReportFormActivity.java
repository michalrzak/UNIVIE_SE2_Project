package at.ac.univie.se2.ws21.team0404.app.ui.report;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import at.ac.univie.se2.ws21.team0404.app.R;
import at.ac.univie.se2.ws21.team0404.app.utils.EIntents;
import at.ac.univie.se2.ws21.team0404.app.utils.factory.EChartType;

public class ReportFormActivity extends AppCompatActivity {

    private Spinner chartTypeSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Report Form");
        setContentView(R.layout.activity_report_form);

        chartTypeSpinner = findViewById(R.id.chart_type_spinner);
        ArrayAdapter<EChartType> chartTypeArrayAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, EChartType.values());
        chartTypeSpinner.setAdapter(chartTypeArrayAdapter);

        Button button = findViewById(R.id.submit_report_button);
        button.setOnClickListener(view -> onButtonClick());
    }

    public void onButtonClick(){
        Intent intent = new Intent(this, ChartActivity.class);
        EChartType chartType = EChartType.valueOf(chartTypeSpinner.getSelectedItem().toString().toUpperCase());
        intent.putExtra(EIntents.CHART_TYPE.toString(), chartType);
        startActivity(intent);
    }
}