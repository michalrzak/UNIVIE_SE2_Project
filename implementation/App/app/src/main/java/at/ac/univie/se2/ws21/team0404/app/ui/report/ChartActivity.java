package at.ac.univie.se2.ws21.team0404.app.ui.report;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.anychart.AnyChartView;
import com.anychart.core.Chart;

import at.ac.univie.se2.ws21.team0404.app.R;
import at.ac.univie.se2.ws21.team0404.app.database.Repository;
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

        Intent intent = getIntent();
        
        EChartType chartType = (EChartType) intent.getSerializableExtra(EIntents.CHART_TYPE.toString());
        assert(chartType != null);

        ETimeSpan timeSpan = (ETimeSpan) intent.getSerializableExtra(EIntents.TIME_SPAN.toString());
        assert(timeSpan != null);

        presenter.setFactory(chartType);

        // temporary to see that stuff are working
        setTitle(chartType + " Chart, " + timeSpan + ", " + timeSpan.getValue());

        presenter.generateChart();
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
        AnyChartView anyChartView = (AnyChartView) findViewById(R.id.chart);
        anyChartView.setChart(chart);
    }
}