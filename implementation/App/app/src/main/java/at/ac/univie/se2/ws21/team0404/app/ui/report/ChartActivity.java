package at.ac.univie.se2.ws21.team0404.app.ui.report;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.anychart.AnyChartView;
import com.anychart.core.Chart;

import at.ac.univie.se2.ws21.team0404.app.R;
import at.ac.univie.se2.ws21.team0404.app.database.Repository;
import at.ac.univie.se2.ws21.team0404.app.model.common.ETransactionType;
import at.ac.univie.se2.ws21.team0404.app.utils.EIntents;
import at.ac.univie.se2.ws21.team0404.app.utils.android.LifecycleHandler;
import at.ac.univie.se2.ws21.team0404.app.utils.factory.EChartType;
import at.ac.univie.se2.ws21.team0404.app.utils.factory.ETimeSpan;

public class ChartActivity extends AppCompatActivity implements IChartActivityContract.IView{

    private final ChartActivityPresenter presenter = new ChartActivityPresenter(Repository.getInstance());

    private LifecycleHandler<ChartActivity> lifecycleHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        lifecycleHandler = new LifecycleHandler<>(presenter, this);

        ActionBar actionBar = getSupportActionBar();
        assert (actionBar != null);
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        
        EChartType chartType = (EChartType) intent.getSerializableExtra(EIntents.CHART_TYPE.toString());
        assert(chartType != null);

        ETimeSpan timeSpan = (ETimeSpan) intent.getSerializableExtra(EIntents.TIME_SPAN.toString());
        assert(timeSpan != null);

        ETransactionType transactionType = (ETransactionType) intent.getSerializableExtra(EIntents.TRANSACTION_TYPE.toString());
        assert(transactionType != null);

        presenter.setFactory(chartType);

        // temporary to see that stuff are working
        setTitle(chartType + " Chart, " + timeSpan + ", " + timeSpan.getValue() +  ", " + transactionType.toString());

        presenter.generateChart(timeSpan, transactionType);
    }

    @Override
    public void closeActivity() {
        Toast.makeText(getApplicationContext(),
                "No Transaction matching the parameters were found. Try again!",
                Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void setChart(Chart chart) {
        AnyChartView anyChartView = findViewById(R.id.chart);
        anyChartView.setChart(chart);
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