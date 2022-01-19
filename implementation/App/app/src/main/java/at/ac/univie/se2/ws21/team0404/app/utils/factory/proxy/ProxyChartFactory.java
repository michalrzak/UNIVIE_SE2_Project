package at.ac.univie.se2.ws21.team0404.app.utils.factory.proxy;

import android.util.Log;

import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.core.Chart;

import java.util.ArrayList;
import java.util.List;

import at.ac.univie.se2.ws21.team0404.app.database.ERepositoryReturnStatus;
import at.ac.univie.se2.ws21.team0404.app.database.Repository;
import at.ac.univie.se2.ws21.team0404.app.model.account.AppAccount;
import at.ac.univie.se2.ws21.team0404.app.model.common.ETransactionType;
import at.ac.univie.se2.ws21.team0404.app.model.transaction.Transaction;
import at.ac.univie.se2.ws21.team0404.app.utils.ChangingData;
import at.ac.univie.se2.ws21.team0404.app.utils.IChangingData;
import at.ac.univie.se2.ws21.team0404.app.utils.factory.AChartFactory;
import at.ac.univie.se2.ws21.team0404.app.utils.factory.ETimeSpan;
import at.ac.univie.se2.ws21.team0404.app.utils.factory.ARealChartFactory;
import at.ac.univie.se2.ws21.team0404.app.utils.iterator.AccountCollection;
import at.ac.univie.se2.ws21.team0404.app.utils.iterator.IIterator;

public class ProxyChartFactory extends AChartFactory {
    private final ARealChartFactory chartFactory;

    /**
     * Constructs a new ProxyChartFactory object to handle chart requests.
     *
     * @param chartFactory the factory used to produce the charts
     */
    public ProxyChartFactory(ARealChartFactory chartFactory) {
        this.chartFactory = chartFactory;
    }

    /**
     * For Proxy Pattern. used to generate a proxy of a chart.
     *
     * @return a proxy chart, which displays loading.
     */
    public Chart createProxyChart() {
        List<DataEntry> dataEntryList = new ArrayList<>();
        dataEntryList.add(new ValueDataEntry("Loading", 100));

        return  instantiateChart(dataEntryList);
    }

    /**
     * Fetches data from the given {@param repository} and updates the provided chart with newest data.
     * Mutates {@param ret} with the new data.
     *
     * @param ret the chart to fetch and update data for
     * @param timeSpan time span of the chart
     * @param transactionType type of transactions to consider
     * @param repository the repository to fetch from
     */
    private void getData(IChangingData<Chart> ret, ETimeSpan timeSpan, ETransactionType transactionType, Repository repository) {
        IChangingData<ERepositoryReturnStatus> status = repository.reloadAccountsWithStatus();

        status.observe((newStatus) -> {
            switch (newStatus) {
                case SUCCESS:
                    List<AppAccount> accounts = repository.getAccountList().getData();
                    AccountCollection collection = new AccountCollection(accounts);
                    IIterator<AppAccount> iterator = collection.createIterator();
                    List<Transaction> transactions = new ArrayList<>();

                    while (iterator.hasNext()) {
                        AppAccount account = iterator.next();
                        transactions.addAll(repository.getTransactionList(account).getData());
                    }

                    try {
                        ret.setData(chartFactory.create(transactions, timeSpan, transactionType));
                    } catch (RuntimeException e) {
                        Log.d("ProxyChartFactory",
                                "Caught a runtime error while generating chart, returning proxy.");
                        return;
                    }
                    Log.d("ProxyChartFactory", "Successfully created chart!");
                    break;
                case ERROR:
                    Log.e("ProxyChartFactory", "Error while executing!");
                    break;
                case UPDATING:
                    Log.d("ProxyChartFactory", "Deploying proxy");
            }
        });
    }

    /**
     * Instantiates a chart using the internal {@link ARealChartFactory}
     *
     * @param data data entries to be used to construct the chart
     * @return a chart to be displayed
     */
    @Override
    protected Chart instantiateChart(List<DataEntry> data) {
        return chartFactory.instantiateChart(data);
    }

    /**
     * Creates a real chart using the internal {@link ARealChartFactory}.
     *
     * @param transactions transactions to display in the chart
     * @param timeSpan time span of the chart
     * @param transactionType type of transactions to consider
     * @return a chart to be displayed
     */
    @Override
    public Chart create(List<Transaction> transactions, ETimeSpan timeSpan, ETransactionType transactionType) {
        return chartFactory.create(transactions, timeSpan, transactionType);
    }

    /**
     * Generates a chart or a proxy chart for a given time span and transaction type.
     * The data comes from the ProxyChartFactory's internal repository.
     *
     * @param timeSpan the time span to consider
     * @param transactionType the type of transactions to consider
     *
     * @return a chart; either the proxy or a generated chart.
     */
    public IChangingData<Chart> generateChart(Repository repository, ETimeSpan timeSpan, ETransactionType transactionType) {
        Chart proxy = this.createProxyChart();
        IChangingData<Chart> ret = new ChangingData<>(proxy);

        getData(ret, timeSpan, transactionType, repository);

        return ret;
    }
}
