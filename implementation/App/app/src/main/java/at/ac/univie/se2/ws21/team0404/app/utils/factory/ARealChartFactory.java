package at.ac.univie.se2.ws21.team0404.app.utils.factory;

import android.util.Log;

import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
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
import at.ac.univie.se2.ws21.team0404.app.model.common.ETransactionType;
import at.ac.univie.se2.ws21.team0404.app.model.transaction.Transaction;
import at.ac.univie.se2.ws21.team0404.app.utils.ChangingData;
import at.ac.univie.se2.ws21.team0404.app.utils.IChangingData;
import at.ac.univie.se2.ws21.team0404.app.utils.iterator.AccountCollection;
import at.ac.univie.se2.ws21.team0404.app.utils.iterator.IIterator;

/**
 * An Abstract class to be used to make initialization of {@link Chart} objects easier.
 * To add a new chart type, subclass it and implement all the needed methods
 */
public abstract class ARealChartFactory extends AChartFactory {

    private final static String noCategory = "No Category";

    /**
     * This is the method that outside classes can call to receive the {@link Chart} object
     *
     * @param transactions a list of {@link Transaction} that are going to be included in the chart
     * @param start beginning of the desired time frame
     * @param end end of the the desired time frame
     * @param transactionType type of transactions to be filtered out
     */
    public Chart create(List<Transaction> transactions, Calendar start, Calendar end, ETransactionType transactionType){

        List<DataEntry> data = processData(transactions, start, end, transactionType);

        return instantiateChart(data);
    }

    /**
     * Can be overridden in the subclasses if the target chart type requires differently processed data
     *
     * @param transactions Transactions that are going to be included in the chart
     * @param start beginning of the desired time frame
     * @param end end of the the desired time frame
     * @param transactionType type of transactions to be filtered out
     * @return data that is processed and can be used in {@link #instantiateChart(List)}
     */
    protected List<DataEntry> processData(List<Transaction> transactions, Calendar start, Calendar end, ETransactionType transactionType){
        Map<String, Integer> filteredResult = new HashMap<>();
        Calendar transactionCalender = Calendar.getInstance();
        for (Transaction transaction : transactions){
            Optional<Category> optionalCategory = transaction.getCategory();
            int amount = transaction.getAmount();

            if (transaction.getType() != transactionType)
                continue;

            transactionCalender.setTime(transaction.getDate());
            Log.d("report", transactionCalender.getTime().toString());

            if (!(transactionCalender.after(start) && transactionCalender.before(end)))
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
                if (filteredResult.containsKey(noCategory)){
                    int value = filteredResult.get(noCategory);
                    filteredResult.put(noCategory, value + amount);
                } else {
                    filteredResult.put(noCategory, amount);
                }
            }
        }

        if (filteredResult.isEmpty())
            throw new RuntimeException("ChartFactory: No Matching Data");

        List<DataEntry> formattedData = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : filteredResult.entrySet()) {
            formattedData.add(new ValueDataEntry(entry.getKey(), entry.getValue()));
        }

        return formattedData;
    }

    /**
     * When this is implemented it will return the specific {@link Chart} object of the subclass
     * @param data prepared data that can be used directly with the {@link Chart} object
     * @return specific implementation of {@link Chart}
     */
    public abstract Chart instantiateChart(List<DataEntry> data);

    @Override
    public IChangingData<Chart> generateChart(Repository repository, Calendar start, Calendar end, ETransactionType transactionType) {
        List<AppAccount> accounts = repository.getAccountList().getData();
        AccountCollection collection = new AccountCollection(accounts);
        IIterator<AppAccount> iterator = collection.createIterator();
        List<Transaction> transactions = new ArrayList<>();

        while (iterator.hasNext()) {
            AppAccount account = iterator.next();
            transactions.addAll(repository.getTransactionList(account).getData());
        }

        return new ChangingData<>(create(transactions, start, end, transactionType));
    }
}
