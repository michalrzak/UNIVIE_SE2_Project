package at.ac.univie.se2.ws21.team0404.app.ui.report;

import android.util.Log;

import com.anychart.core.Chart;

import at.ac.univie.se2.ws21.team0404.app.database.Repository;
import at.ac.univie.se2.ws21.team0404.app.model.common.ETransactionType;
import at.ac.univie.se2.ws21.team0404.app.utils.factory.AChartFactory;
import at.ac.univie.se2.ws21.team0404.app.utils.factory.proxy.ProxyChartFactory;
import at.ac.univie.se2.ws21.team0404.app.ui.ABasePresenter;
import at.ac.univie.se2.ws21.team0404.app.utils.IChangingData;
import at.ac.univie.se2.ws21.team0404.app.utils.factory.BarChartFactory;
import at.ac.univie.se2.ws21.team0404.app.utils.factory.EChartType;
import at.ac.univie.se2.ws21.team0404.app.utils.factory.ETimeSpan;
import at.ac.univie.se2.ws21.team0404.app.utils.factory.PieChartFactory;

public class ChartActivityPresenter
        extends ABasePresenter<IChartActivityContract.IView>
        implements IChartActivityContract.IPresenter {

    private AChartFactory AChartFactory;
    private final Repository repository;
    IChangingData<Chart> chartChangingData;


    public ChartActivityPresenter(Repository repository){
        this.repository = repository;
        this.AChartFactory = new ProxyChartFactory(new PieChartFactory());
    }

    @Override
    public void generateChart(ETimeSpan timeSpan, ETransactionType transactionType) {
        chartChangingData = AChartFactory.generateChart(this.repository, timeSpan, transactionType);

        chartChangingData.observe((newChart) -> {
            Log.d("Chart observer", "Changing chart!");
            view.setChart(newChart);
        });
    }

    @Override
    public void setFactory(EChartType chartType) {
        switch (chartType)  {
            case PIE:
                AChartFactory = new ProxyChartFactory(new PieChartFactory());
                break;
            case BAR:
                AChartFactory = new ProxyChartFactory(new BarChartFactory());
                break;
        }
    }
}
