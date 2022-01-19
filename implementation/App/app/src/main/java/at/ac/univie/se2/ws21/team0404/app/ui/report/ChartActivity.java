package at.ac.univie.se2.ws21.team0404.app.ui.report;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.anychart.AnyChartView;
import com.anychart.core.Chart;

import java.util.Calendar;

import at.ac.univie.se2.ws21.team0404.app.R;
import at.ac.univie.se2.ws21.team0404.app.database.Repository;
import at.ac.univie.se2.ws21.team0404.app.model.common.ETransactionType;
import at.ac.univie.se2.ws21.team0404.app.utils.EIntents;
import at.ac.univie.se2.ws21.team0404.app.utils.android.LifecycleHandler;
import at.ac.univie.se2.ws21.team0404.app.utils.factory.EChartType;

public class ChartActivity extends AppCompatActivity implements IChartActivityContract.IView{

    private final ChartActivityPresenter presenter = new ChartActivityPresenter(Repository.getInstance());
    private AnyChartView anyChartView;

    private Chart chart;

    private LifecycleHandler<ChartActivity> lifecycleHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        anyChartView = findViewById(R.id.chart);

        lifecycleHandler = new LifecycleHandler<>(presenter, this);

        ActionBar actionBar = getSupportActionBar();
        assert (actionBar != null);
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        
        EChartType chartType = (EChartType) intent.getSerializableExtra(EIntents.CHART_TYPE.toString());
        assert(chartType != null);

        ETransactionType transactionType = (ETransactionType) intent.getSerializableExtra(EIntents.TRANSACTION_TYPE.toString());
        assert(transactionType != null);

        Calendar start = (Calendar) intent.getSerializableExtra(EIntents.START_DATE.toString());
        Calendar end = (Calendar) intent.getSerializableExtra(EIntents.END_DATE.toString());

        presenter.setFactory(chartType);
        // temporary to see that stuff are working
        setTitle(chartType + " Chart, "  + transactionType.toString());

        presenter.generateChart(start, end, transactionType);
    }

    @Override
    public void closeActivity() {
        Toast.makeText(getApplicationContext(),
                "No Transaction matching the parameters were found. Try again!",
                Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void setChart(Chart chart) {
        AnyChartView anyChartView = findViewById(R.id.chart);
        if (this.chart != null) {
            this.chart.dispose();
            anyChartView.clear();
            anyChartView.invalidate();
            anyChartView.setChart(chart);
            findViewById(R.id.chart_constraint_layout).invalidate();
            return;
        }

        this.chart = chart;

        anyChartView.setChart(this.chart);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return true;
    }
}