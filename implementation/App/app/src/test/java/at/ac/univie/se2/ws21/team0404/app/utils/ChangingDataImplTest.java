package at.ac.univie.se2.ws21.team0404.app.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ChangingDataImplTest {

    @Mock
    ChangingData.Observer<Integer> mockObserver;

    @Test
    public void createDefaultChangingData_getDataReturnsNull() {
        ChangingData<Integer> uut = new ChangingDataImpl<>();
        assertNull(uut.getData());
    }

    @Test
    public void createChangingDataWithValue_getDataReturnsValue() {
        ChangingData<Integer> uut = new ChangingDataImpl<>(4711);
        assertEquals(new Integer(4711), uut.getData());
    }

    @Test
    public void noObserver_installObserver_observerGetsCalledOnce() {
        ChangingData<Integer> uut = new ChangingDataImpl<>(4711);
        uut.observe(mockObserver);
        verify(mockObserver, times(1)).changed(4711);
        verifyNoMoreInteractions(mockObserver);
    }

    @Test
    public void installedObserver_setData_observerGetsCalledOnce() {
        ChangingData<Integer> uut = new ChangingDataImpl<>(4711);
        uut.observe(mockObserver);
        verify(mockObserver, times(1)).changed(4711);
        uut.setData(815);
        verify(mockObserver, times(1)).changed(815);
        verifyNoMoreInteractions(mockObserver);
    }

    @Test
    public void installedObserver_unobserve_setData_observerGetsNotCalled() {
        ChangingData<Integer> uut = new ChangingDataImpl<>(4711);
        uut.observe(mockObserver);
        verify(mockObserver, times(1)).changed(4711);
        uut.unobserve(mockObserver);
        uut.setData(815);
        verifyNoMoreInteractions(mockObserver);
    }
}
