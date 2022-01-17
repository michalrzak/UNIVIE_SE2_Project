package at.ac.univie.se2.ws21.team0404.app.ui.report;

import at.ac.univie.se2.ws21.team0404.app.ui.IBaseContract;

// not sure if we need it since there is only UI interaction
public interface IReportFormActivityContract {
    interface IView extends IBaseContract.IView{
        void changeActivity();
    }

    interface IPresenter extends IBaseContract.IPresenter<IView>{
        void buttonPressed();
    }
}
