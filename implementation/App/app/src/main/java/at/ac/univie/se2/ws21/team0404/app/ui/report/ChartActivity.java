package at.ac.univie.se2.ws21.team0404.app.ui.report;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.anychart.AnyChartView;
import com.anychart.core.Chart;

import at.ac.univie.se2.ws21.team0404.app.R;
import at.ac.univie.se2.ws21.team0404.app.utils.EIntents;
import at.ac.univie.se2.ws21.team0404.app.utils.factory.EChartType;

public class ChartActivity extends AppCompatActivity implements IChartActivityContract.IView{

    private final ChartActivityPresenter presenter = new ChartActivityPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        Intent intent = getIntent();
        EChartType chartType = (EChartType) intent.getSerializableExtra(EIntents.CHART_TYPE.toString());
        presenter.setFactory(chartType);

        setTitle(chartType + " Chart");

        Chart chart = presenter.getChart();
        AnyChartView anyChartView = (AnyChartView) findViewById(R.id.chart);
        anyChartView.setChart(chart);
    }

}