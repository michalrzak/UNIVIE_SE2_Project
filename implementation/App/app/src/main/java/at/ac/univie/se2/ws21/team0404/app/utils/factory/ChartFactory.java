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

import at.ac.univie.se2.ws21.team0404.app.model.categories.Category;
import at.ac.univie.se2.ws21.team0404.app.model.common.ETransactionType;
import at.ac.univie.se2.ws21.team0404.app.model.transaction.Transaction;

public abstract class ChartFactory {

    private final static String noCategory = "No Category";

    public Chart create(List<Transaction> transactions, long timeSpan, ETransactionType transactionType){

        List<DataEntry> data = processData(transactions, timeSpan, transactionType);

        return instantiateChart(data);
    }

    private List<DataEntry> processData(List<Transaction> transactions, long timeSpan, ETransactionType transactionType){
        Calendar targetCalender = Calendar.getInstance();
        targetCalender.setTime(new Date());
        if (timeSpan == 0)
            timeSpan--;
        targetCalender.add(Calendar.MINUTE, (int) -(timeSpan));
        targetCalender.set(Calendar.SECOND, 0);
        targetCalender.add(Calendar.SECOND, -1);

        Log.d("report", "target: " + targetCalender.getTime().toString());

        Map<String, Integer> filteredResult = new HashMap<>();
        Calendar transactionCalender = Calendar.getInstance();
        for (Transaction transaction : transactions){
            Optional<Category> optionalCategory = transaction.getCategory();
            int amount = transaction.getAmount();

            if (transaction.getType() != transactionType)
                continue;

            transactionCalender.setTime(transaction.getDate());
            Log.d("report", transactionCalender.getTime().toString());
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

    protected abstract Chart instantiateChart(List<DataEntry> data);
}
