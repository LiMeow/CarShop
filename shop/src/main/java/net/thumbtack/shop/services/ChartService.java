package net.thumbtack.shop.services;

import net.thumbtack.shop.exceptions.CarShopException;
import net.thumbtack.shop.exceptions.ErrorCode;
import net.thumbtack.shop.models.StatusName;
import net.thumbtack.shop.models.User;
import net.thumbtack.shop.repositories.UserRepository;
import net.thumbtack.shop.repositories.chartRepository.ChartRepositoryCustom;
import net.thumbtack.shop.responses.ChartItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChartService {
    private final ChartRepositoryCustom chartRepository;
    private final UserRepository userRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(ChartService.class);

    @Autowired
    private EntityManager entityManager;

    public ChartService(ChartRepositoryCustom chartRepository, UserRepository userRepository) {
        this.chartRepository = chartRepository;
        this.userRepository = userRepository;
    }

    public List<ChartItem> getChartData(String username, StatusName statusName) {
        LOGGER.debug("ChartService get transaction data with status '{}' by manager with username '{}'", statusName, username);
        User manager = findManagerByUsername(username);
        return updateChartData(chartRepository.findChartItems(manager.getId(), statusName));
    }

    private User findManagerByUsername(String username) {
        User manager = userRepository.findManagerByUsername(username);

        if (manager == null) {
            LOGGER.error("Unable to find manager with username '{}'", username);
            throw new CarShopException(ErrorCode.USER_NOT_EXISTS, username);
        }

        return manager;
    }

    private List<ChartItem> updateChartData(List<ChartItem> chartItems) {
        LOGGER.debug("ChartService update chart data '{}'", chartItems);
        List<ChartItem> chartItemList = new ArrayList<>();
        int start = 1, end = 0;

        if (!chartItems.isEmpty()) {
            start = Month.valueOf(chartItems.get(0).getLabel()).getValue();
            end = Month.valueOf(chartItems.get(chartItems.size() - 1).getLabel()).getValue();
        }

        for (Integer i = 1; i < start; i++) {
            chartItemList.add(new ChartItem(i, 0));
        }

        chartItemList.addAll(chartItems);

        for (Integer i = end + 1; i < 13; i++) {
            chartItemList.add(new ChartItem(i, 0));
        }

        return chartItemList;
    }

}
