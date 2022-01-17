package at.ac.univie.se2.ws21.team0404.app.ui.report;

import at.ac.univie.se2.ws21.team0404.app.ui.ABasePresenter;

public class ReportFormPresenter extends ABasePresenter<IReportFormActivityContract.IView>
        implements IReportFormActivityContract.IPresenter {
    @Override
    public void buttonPressed() {
        view.changeActivity();
    }
}
