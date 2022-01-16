package at.ac.univie.se2.ws21.team0404.app.utils.factory;

import com.anychart.AnyChart;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.charts.Pie;
import com.anychart.core.Chart;

import java.util.List;

public class PieChartFactory extends ChartFactory{
    @Override
    protected Chart instantiateChart(List<DataEntry> data) {
        Pie pie = AnyChart.pie();
        pie.data(data);
        pie.title("Pie Chart");
        pie.legend().title().enabled(true);
        pie.legend().title()
                .text("Categories")
                .padding(0d, 0d, 5d, 0d);
        return pie;
    }
}
