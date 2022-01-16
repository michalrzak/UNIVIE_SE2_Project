package at.ac.univie.se2.ws21.team0404.app.ui.report;

import com.anychart.core.Chart;

import java.util.HashMap;
import java.util.Map;

import at.ac.univie.se2.ws21.team0404.app.database.Repository;
import at.ac.univie.se2.ws21.team0404.app.ui.ABasePresenter;
import at.ac.univie.se2.ws21.team0404.app.utils.factory.BarChartFactory;
import at.ac.univie.se2.ws21.team0404.app.utils.factory.ChartFactory;
import at.ac.univie.se2.ws21.team0404.app.utils.factory.EChartType;
import at.ac.univie.se2.ws21.team0404.app.utils.factory.PieChartFactory;

public class ChartActivityPresenter
        extends ABasePresenter<IChartActivityContract.IView>
        implements IChartActivityContract.IPresenter {

    private ChartFactory chartFactory;
    private Repository repository;

    @Override
    public Chart getChart() {
        // this data is here for testing purposes
        // will later be changed so we use the repository to fetch it
        Map<String, Integer> map = new HashMap<>();
        map.put("House", 6000);
        map.put("Sports", 2000);
        map.put("Job", 10000);
        map.put("Gaming", 1000);

        return chartFactory.create(map);
    }

    @Override
    public void setFactory(EChartType chartType) {
        switch (chartType)  {
            case PIE:
                chartFactory = new PieChartFactory();
                break;
            case BAR:
                chartFactory = new BarChartFactory();
                break;
        }
    }
}
