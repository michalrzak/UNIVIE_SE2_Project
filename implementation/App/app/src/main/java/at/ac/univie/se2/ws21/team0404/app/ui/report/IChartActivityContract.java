package at.ac.univie.se2.ws21.team0404.app.ui.report;

import com.anychart.core.Chart;

import at.ac.univie.se2.ws21.team0404.app.ui.IBaseContract;
import at.ac.univie.se2.ws21.team0404.app.utils.factory.EChartType;

public interface IChartActivityContract {
    interface IView extends IBaseContract.IView {

    }

    interface IPresenter extends IBaseContract.IPresenter<IView>{
        Chart getChart();
        void setFactory(EChartType chartType);
    }
}
