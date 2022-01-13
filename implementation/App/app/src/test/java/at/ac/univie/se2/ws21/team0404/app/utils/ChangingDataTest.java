package at.ac.univie.se2.ws21.team0404.app.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ChangingDataTest {

  private static final Integer TEST_DATA = 4711;
  private static final Integer OTHER_TEST_DATA = 815;

  @Mock
  private IChangingData.Observer<Integer> mockObserver;

  private IChangingData<Integer> testInstance;

  @Before
  public void setUp() {
    testInstance = new ChangingData<>(TEST_DATA);
  }

  @Test
  public void createDefaultChangingData_getDataReturnsNull() {
    testInstance = new ChangingData<>();
    assertNull(testInstance.getData());
  }

  @Test
  public void createChangingDataWithValue_getDataReturnsValue() {
    assertEquals(TEST_DATA, testInstance.getData());
  }

  @Test
  public void noObserver_installObserver_observerGetsCalledOnce() {
    testInstance.observe(mockObserver);
    verify(mockObserver, times(1)).changed(TEST_DATA);
    verifyNoMoreInteractions(mockObserver);
  }

  @Test
  public void installedObserver_setData_observerGetsCalledOnce() {
    testInstance.observe(mockObserver);
    verify(mockObserver, times(1)).changed(TEST_DATA);
    testInstance.setData(OTHER_TEST_DATA);
    verify(mockObserver, times(1)).changed(OTHER_TEST_DATA);
    verifyNoMoreInteractions(mockObserver);
  }

  @Test
  public void installedObserver_unobserve_setData_observerDoesNotGetCalled() {
    testInstance.observe(mockObserver);
    verify(mockObserver, times(1)).changed(TEST_DATA);
    testInstance.unobserve(mockObserver);
    testInstance.setData(OTHER_TEST_DATA);
    verifyNoMoreInteractions(mockObserver);
  }
}
