package at.ac.univie.se2.ws21.team0404.app.ui.report;

import com.anychart.core.Chart;

import java.util.Calendar;

import at.ac.univie.se2.ws21.team0404.app.model.common.ETransactionType;
import at.ac.univie.se2.ws21.team0404.app.ui.IBaseContract;
import at.ac.univie.se2.ws21.team0404.app.utils.factory.EChartType;

public interface IChartActivityContract {
    interface IView extends IBaseContract.IView {
        void closeActivity();
        void setChart(Chart chart);
    }

    interface IPresenter extends IBaseContract.IPresenter<IView>{
        void generateChart(Calendar start, Calendar end, ETransactionType transactionType);
        void setFactory(EChartType chartType);
    }
}
