package at.ac.univie.se2.ws21.team0404.app.ui.report;

import com.anychart.core.Chart;

import at.ac.univie.se2.ws21.team0404.app.ui.IBaseContract;
import at.ac.univie.se2.ws21.team0404.app.utils.factory.EChartType;
import at.ac.univie.se2.ws21.team0404.app.utils.factory.ETimeSpan;

public interface IChartActivityContract {
    interface IView extends IBaseContract.IView {
        void closeActivity();
        void setChart(Chart chart);
    }

    interface IPresenter extends IBaseContract.IPresenter<IView>{
        void generateChart(ETimeSpan timeSpan);
        void setFactory(EChartType chartType);
    }
}
