package at.ac.univie.se2.ws21.team0404.app.ui.report;

import com.anychart.core.Chart;

import java.util.ArrayList;
import java.util.List;

import at.ac.univie.se2.ws21.team0404.app.database.Repository;
import at.ac.univie.se2.ws21.team0404.app.model.account.AppAccount;
import at.ac.univie.se2.ws21.team0404.app.model.common.ETransactionType;
import at.ac.univie.se2.ws21.team0404.app.model.transaction.Transaction;
import at.ac.univie.se2.ws21.team0404.app.ui.ABasePresenter;
import at.ac.univie.se2.ws21.team0404.app.utils.factory.BarChartFactory;
import at.ac.univie.se2.ws21.team0404.app.utils.factory.ChartFactory;
import at.ac.univie.se2.ws21.team0404.app.utils.factory.EChartType;
import at.ac.univie.se2.ws21.team0404.app.utils.factory.PieChartFactory;
import at.ac.univie.se2.ws21.team0404.app.utils.iterator.AccountCollection;
import at.ac.univie.se2.ws21.team0404.app.utils.iterator.IIterator;

public class ChartActivityPresenter
        extends ABasePresenter<IChartActivityContract.IView>
        implements IChartActivityContract.IPresenter {

    private ChartFactory chartFactory;
    private final Repository repository;


    public ChartActivityPresenter(Repository repository){
        this.repository = repository;
    }

    @Override
    public void generateChart(long timeSpan, ETransactionType transactionType) {
        List<AppAccount> accounts = repository.getAccountList().getData();
        AccountCollection collection = new AccountCollection(accounts);
        IIterator<AppAccount> iterator = collection.createIterator();
        List<Transaction> transactions = new ArrayList<>();

        while (iterator.hasNext()){
            AppAccount account = iterator.next();
            transactions.addAll(repository.getTransactionList(account).getData());
        }

        Chart chart;
        try{
            chart = chartFactory.create(transactions, timeSpan, transactionType);
        } catch (RuntimeException e) {
            view.closeActivity();
            return;
        }
        assert (chart != null);

        view.setChart(chart);
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
