package net.thumbtack.shop;

import net.thumbtack.shop.exceptions.CarShopException;
import net.thumbtack.shop.exceptions.ErrorCode;
import net.thumbtack.shop.models.StatusName;
import net.thumbtack.shop.models.User;
import net.thumbtack.shop.models.UserRole;
import net.thumbtack.shop.repositories.UserRepository;
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
    private UserRepository userRepository;
    @InjectMocks
    private ChartService chartService;

    @Test
    public void testGetChartData() {
        User manager = new User(1, "manager", "password", UserRole.ROLE_MANAGER);

        ChartItem chartItem1 = new ChartItem("January", 1);
        ChartItem chartItem2 = new ChartItem("February", 2);
        ChartItem chartItem3 = new ChartItem("March", 3);
        ChartItem chartItem4 = new ChartItem("April", 0);
        ChartItem chartItem5 = new ChartItem("May", 0);
        ChartItem chartItem6 = new ChartItem("June", 0);
        ChartItem chartItem7 = new ChartItem("July", 0);
        ChartItem chartItem8 = new ChartItem("August", 0);
        ChartItem chartItem9 = new ChartItem("September", 0);
        ChartItem chartItem10 = new ChartItem("October", 0);
        ChartItem chartItem11 = new ChartItem("November", 0);
        ChartItem chartItem12 = new ChartItem("December", 0);
        List<ChartItem> chartItems = Arrays.asList(chartItem1, chartItem2, chartItem3);
        List<ChartItem> updatedChartItems = Arrays.asList(chartItem1, chartItem2, chartItem3, chartItem4, chartItem5, chartItem6, chartItem7, chartItem8, chartItem9, chartItem10, chartItem11, chartItem12);

        when(userRepository.findManagerByUsername(manager.getUsername())).thenReturn(manager);
        when(chartRepository.findChartItems(manager.getId(), StatusName.APPLICATION_CONFIRMATION)).thenReturn(chartItems);

        assertEquals(updatedChartItems, chartService.getChartData(manager.getUsername(), StatusName.APPLICATION_CONFIRMATION));

        verify(userRepository).findManagerByUsername(manager.getUsername());
        verify(chartRepository).findChartItems(manager.getId(), StatusName.APPLICATION_CONFIRMATION);
    }

    @Test
    public void testGetChartDataByNonExistingManager() {
        when(userRepository.findManagerByUsername("manager1")).thenReturn(null);
        try {
            chartService.getChartData("manager1", StatusName.CONFIRMED);
            fail();
        } catch (CarShopException ex) {
            assertEquals(ErrorCode.USER_NOT_EXISTS, ex.getErrorCode());
        }
        verify(userRepository).findManagerByUsername("manager1");
    }
}
