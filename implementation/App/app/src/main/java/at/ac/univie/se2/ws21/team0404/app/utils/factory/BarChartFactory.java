package at.ac.univie.se2.ws21.team0404.app.utils.factory;

import com.anychart.AnyChart;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.charts.Cartesian3d;
import com.anychart.core.Chart;

import java.util.List;

public class BarChartFactory extends ChartFactory{
    @Override
    protected Chart instantiateChart(List<DataEntry> data) {
        Cartesian bar = AnyChart.column();
        bar.title("Bar Chart");
        bar.column(data);
        return bar;
    }
}
