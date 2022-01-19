package at.ac.univie.se2.ws21.team0404.app.utils.factory.proxy;

import static org.junit.Assert.assertNotNull;

import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;

import com.anychart.core.Chart;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import at.ac.univie.se2.ws21.team0404.app.model.categories.Category;
import at.ac.univie.se2.ws21.team0404.app.model.common.ETransactionType;
import at.ac.univie.se2.ws21.team0404.app.model.transaction.Transaction;
import at.ac.univie.se2.ws21.team0404.app.ui.report.ChartActivity;
import at.ac.univie.se2.ws21.team0404.app.utils.EIntents;
import at.ac.univie.se2.ws21.team0404.app.utils.factory.BarChartFactory;
import at.ac.univie.se2.ws21.team0404.app.utils.factory.EChartType;
import at.ac.univie.se2.ws21.team0404.app.utils.factory.ETimeSpan;
import at.ac.univie.se2.ws21.team0404.app.utils.factory.PieChartFactory;

@RunWith(MockitoJUnitRunner.class)
public class ProxyAChartFactoryTest {

    private static class Fixtures {
        public static ETimeSpan testTimeSpan = ETimeSpan.DAYS_365;
        public static List<Transaction> mockTransactionList = Arrays.asList(
                new Transaction(UUID.randomUUID(), null, ETransactionType.INCOME, 10,
                        "test income tx1", new Date()),
                new Transaction(UUID.randomUUID(), null, ETransactionType.INCOME, 23,
                        "test income tx2", new Date()),
                new Transaction(UUID.randomUUID(), null, ETransactionType.EXPENSE, 9,
                        "test expense tx1", new Date()),
                new Transaction(UUID.randomUUID(), null, ETransactionType.EXPENSE, 31,
                        "test expense tx2", new Date()),
                new Transaction(UUID.randomUUID(), new Category(UUID.randomUUID(),
                        ETransactionType.EXPENSE, "Expense test category"),
                        ETransactionType.EXPENSE, 11, "test expense with category", new Date()),
                new Transaction(UUID.randomUUID(), new Category(UUID.randomUUID(),
                        ETransactionType.INCOME, "Income test category"),
                        ETransactionType.INCOME, 14, "test income with category", new Date())
        );
    }

    // Ideally, this test would compare a chart generated by PieChartFactory and by ProxyChartFactory
    // However, the Chart class doesn't provide an equals method, nor any way to get the contained data
    // making such a test impossible.
    @Test
    public void pieChartFactory_createProxyChart_generatesProxyChart() {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), ChartActivity.class);
        intent.putExtra(EIntents.TIME_SPAN.toString(), ETimeSpan.DAYS_1);

        intent.putExtra(EIntents.TRANSACTION_TYPE.toString(), ETransactionType.INCOME);
        intent.putExtra(EIntents.CHART_TYPE.toString(), EChartType.PIE);
        ActivityScenario.launch(intent);

        ProxyChartFactory factory = new ProxyChartFactory(new PieChartFactory());

        Chart chart = factory.createProxyChart();
        assertNotNull(chart);
    }

    // Ideally, this test would compare a chart generated by BarChartFactory and by ProxyChartFactory
    // However, the Chart class doesn't provide an equals method, nor any way to get the contained data
    // making such a test impossible.
    @Test
    public void barChartFactory_createProxyChart_generatesProxyChart() {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), ChartActivity.class);
        intent.putExtra(EIntents.TIME_SPAN.toString(), ETimeSpan.DAYS_1);

        intent.putExtra(EIntents.TRANSACTION_TYPE.toString(), ETransactionType.INCOME);
        intent.putExtra(EIntents.CHART_TYPE.toString(), EChartType.BAR);
        ActivityScenario.launch(intent);

        ProxyChartFactory factory = new ProxyChartFactory(new BarChartFactory());

        Chart chart = factory.createProxyChart();
        assertNotNull(chart);
    }

    @Test
    public void pieChartFactory_createProxyChart_generatesRealChart() {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), ChartActivity.class);
        intent.putExtra(EIntents.TIME_SPAN.toString(), ETimeSpan.DAYS_1);

        intent.putExtra(EIntents.TRANSACTION_TYPE.toString(), ETransactionType.INCOME);
        intent.putExtra(EIntents.CHART_TYPE.toString(), EChartType.PIE);
        ActivityScenario.launch(intent);

        ProxyChartFactory factory = new ProxyChartFactory(new PieChartFactory());

        Chart chart = factory.createProxyChart();
        assertNotNull(chart);
    }

    @Test
    public void barChartFactory_createProxyChart_generatesRealChart() {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), ChartActivity.class);
        intent.putExtra(EIntents.TIME_SPAN.toString(), ETimeSpan.DAYS_1);

        intent.putExtra(EIntents.TRANSACTION_TYPE.toString(), ETransactionType.INCOME);
        intent.putExtra(EIntents.CHART_TYPE.toString(), EChartType.BAR);
        ActivityScenario.launch(intent);

        ProxyChartFactory factory = new ProxyChartFactory(new BarChartFactory());

        Chart chart = factory.create(Fixtures.mockTransactionList,
                Fixtures.testTimeSpan, ETransactionType.INCOME);
        assertNotNull(chart);
    }
}