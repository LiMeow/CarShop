package net.thumbtack.shop;

import net.thumbtack.shop.exceptions.CarShopException;
import net.thumbtack.shop.exceptions.ErrorCode;
import net.thumbtack.shop.models.Manager;
import net.thumbtack.shop.models.StatusName;
import net.thumbtack.shop.repositories.ManagerRepository;
import net.thumbtack.shop.repositories.chartRepository.ChartRepositoryCustom;
import net.thumbtack.shop.responses.ChartItem;
import net.thumbtack.shop.services.ChartService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ChartServiceTests {

    @Mock
    private ChartRepositoryCustom chartRepository;
    @Mock
    private ManagerRepository managerRepository;
    @InjectMocks
    private ChartService chartService;

    @Test
    public void testGetChartData() {
        Manager manager = new Manager(1, "manager", "password");

        ChartItem chartItem1 = new ChartItem("April", 1);
        ChartItem chartItem2 = new ChartItem("May", 2);
        ChartItem chartItem3 = new ChartItem("June", 3);
        List<ChartItem> chartItems = Arrays.asList(chartItem1, chartItem2, chartItem3);

        when(managerRepository.findByUsername(manager.getUsername())).thenReturn(manager);
        when(chartRepository.findChartItems(manager.getId(), StatusName.CONFIRMED)).thenReturn(chartItems);

        assertEquals(chartItems, chartService.getChartData(manager.getUsername(), StatusName.CONFIRMED));

        verify(managerRepository).findByUsername(manager.getUsername());
        verify(chartRepository).findChartItems(manager.getId(), StatusName.CONFIRMED);
    }

    @Test
    public void testGetChartDataByNonExistingManager() {
        when(managerRepository.findByUsername("manager1")).thenReturn(null);
        try {
            chartService.getChartData("manager1", StatusName.CONFIRMED);
            fail();
        } catch (CarShopException ex) {
            assertEquals(ErrorCode.USER_NOT_EXISTS, ex.getErrorCode());
        }
        verify(managerRepository).findByUsername("manager1");
    }
}
