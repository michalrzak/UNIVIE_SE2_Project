package at.ac.univie.se2.ws21.team0404.app.utils.factory;

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

public abstract class ARealChartFactory extends AChartFactory {
    public Chart create(List<Transaction> transactions, ETimeSpan timeSpan, ETransactionType transactionType){

        List<DataEntry> data = processData(transactions, timeSpan, transactionType);

        return instantiateChart(data);
    }

    private List<DataEntry> processData(List<Transaction> transactions, ETimeSpan timeSpan, ETransactionType transactionType){
        Calendar targetCalender = Calendar.getInstance();
        targetCalender.setTime(new Date());
        targetCalender.add(Calendar.DAY_OF_MONTH, -timeSpan.getValue());

        Map<String, Integer> filteredResult = new HashMap<>();
        Calendar transactionCalender = Calendar.getInstance();
        for (Transaction transaction : transactions){
            Optional<Category> optionalCategory = transaction.getCategory();
            int amount = transaction.getAmount();

            if (transaction.getType() != transactionType)
                continue;

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
                if (filteredResult.containsKey(noCategory)){
                    int value = filteredResult.get(noCategory);
                    filteredResult.put(noCategory, value + amount);
                } else {
                    filteredResult.put(noCategory, amount);
                }
            }
        }

        if (filteredResult.isEmpty())
            throw new RuntimeException("No Matching Data");

        List<DataEntry> formattedData = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : filteredResult.entrySet()) {
            formattedData.add(new ValueDataEntry(entry.getKey(), entry.getValue()));
        }

        return formattedData;
    }

    public abstract Chart instantiateChart(List<DataEntry> data);

    @Override
    public IChangingData<Chart> generateChart(Repository repository, ETimeSpan timeSpan, ETransactionType transactionType) {
        List<AppAccount> accounts = repository.getAccountList().getData();
        AccountCollection collection = new AccountCollection(accounts);
        IIterator<AppAccount> iterator = collection.createIterator();
        List<Transaction> transactions = new ArrayList<>();

        while (iterator.hasNext()) {
            AppAccount account = iterator.next();
            transactions.addAll(repository.getTransactionList(account).getData());
        }

        return new ChangingData<>(create(transactions, timeSpan, transactionType));
    }
}
