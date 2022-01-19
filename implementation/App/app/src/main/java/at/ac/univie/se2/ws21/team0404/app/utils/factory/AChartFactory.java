package at.ac.univie.se2.ws21.team0404.app.utils.factory;

import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.core.Chart;

import java.util.Calendar;
import java.util.List;

import at.ac.univie.se2.ws21.team0404.app.database.Repository;
import at.ac.univie.se2.ws21.team0404.app.model.common.ETransactionType;
import at.ac.univie.se2.ws21.team0404.app.model.transaction.Transaction;
import at.ac.univie.se2.ws21.team0404.app.utils.IChangingData;

public abstract class AChartFactory {
    protected final static String noCategory = "No Category";

    protected abstract Chart instantiateChart(List<DataEntry> data);
    public abstract Chart create(List<Transaction> transactions, Calendar start, Calendar end, ETransactionType transactionType);
    public abstract IChangingData<Chart> generateChart(Repository repository, Calendar start, Calendar end, ETransactionType transactionType);
}
