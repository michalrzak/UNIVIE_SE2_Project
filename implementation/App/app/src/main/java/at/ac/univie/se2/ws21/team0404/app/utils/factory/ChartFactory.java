package at.ac.univie.se2.ws21.team0404.app.utils.factory;

import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.core.Chart;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class ChartFactory {
    public Chart create(Map<String, Integer> data){

        List<DataEntry> processedData = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            processedData.add(new ValueDataEntry(entry.getKey(), entry.getValue()));
        }

        return instantiateChart(processedData);
    }

    protected abstract Chart instantiateChart(List<DataEntry> data);
}
