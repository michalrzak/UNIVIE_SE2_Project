package at.ac.univie.se2.ws21.team0404.app.ui.report;

import android.util.Log;

import com.anychart.core.Chart;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import at.ac.univie.se2.ws21.team0404.app.database.Repository;
import at.ac.univie.se2.ws21.team0404.app.model.account.AppAccount;
import at.ac.univie.se2.ws21.team0404.app.model.categories.Category;
import at.ac.univie.se2.ws21.team0404.app.model.transaction.Transaction;
import at.ac.univie.se2.ws21.team0404.app.ui.ABasePresenter;
import at.ac.univie.se2.ws21.team0404.app.utils.exceptions.DataDoesNotExistException;
import at.ac.univie.se2.ws21.team0404.app.utils.factory.BarChartFactory;
import at.ac.univie.se2.ws21.team0404.app.utils.factory.ChartFactory;
import at.ac.univie.se2.ws21.team0404.app.utils.factory.EChartType;
import at.ac.univie.se2.ws21.team0404.app.utils.factory.ETimeSpan;
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
    public void generateChart(ETimeSpan timeSpan) {
        List<AppAccount> accounts = repository.getAccountList().getData();
        AccountCollection collection = new AccountCollection(accounts);
        IIterator<AppAccount> iterator = collection.createIterator();
        List<Transaction> transactions = new ArrayList<>();

        while (iterator.hasNext()){
            AppAccount account = iterator.next();
            transactions.addAll(repository.getTransactionList(account).getData());
        }

        // there is definitely a better way to solve this (java streams, etc.)
        // will also probably move some parts into the factory

        String nameless = "No Category";

        Calendar targetCalender = Calendar.getInstance();
        targetCalender.setTime(new Date());
        targetCalender.add(Calendar.DAY_OF_MONTH, -timeSpan.getValue());

        Map<String, Integer> filteredResult = new HashMap<>();
        Calendar transactionCalender = Calendar.getInstance();
        for (Transaction transaction : transactions){
            Optional<Category> optionalCategory = transaction.getCategory();
            int amount = transaction.getAmount();

            transactionCalender.setTime(transaction.getDate());
            if (!transactionCalender.after(targetCalender))
                continue;

            if (optionalCategory.isPresent()){
                Category category = optionalCategory.get();
                String categoryName = category.getName();
                if (filteredResult.containsKey(categoryName)){
                    int value = filteredResult.get(categoryName);
                    filteredResult.put(categoryName, value + amount);
                } else {
                    filteredResult.put(categoryName, amount);
                }
            } else {
                if (filteredResult.containsKey(nameless)){
                    int value = filteredResult.get(nameless);
                    filteredResult.put(nameless, value + amount);
                } else {
                    filteredResult.put(nameless, amount);
                }
            }
        }

        if (filteredResult.isEmpty())
            view.closeActivity();

        Chart chart = chartFactory.create(filteredResult);

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
