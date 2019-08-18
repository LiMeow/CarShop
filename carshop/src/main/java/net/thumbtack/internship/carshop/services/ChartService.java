package net.thumbtack.internship.carshop.services;

import net.thumbtack.internship.carshop.exceptions.CarShopException;
import net.thumbtack.internship.carshop.exceptions.ErrorCode;
import net.thumbtack.internship.carshop.models.Manager;
import net.thumbtack.internship.carshop.models.StatusName;
import net.thumbtack.internship.carshop.repositories.ManagerRepository;
import net.thumbtack.internship.carshop.repositories.chartRepository.ChartRepositoryCustom;
import net.thumbtack.internship.carshop.responses.ChartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

@Service
public class ChartService {
    private final ChartRepositoryCustom chartRepository;
    private final ManagerRepository managerRepository;

    @Autowired
    private EntityManager entityManager;

    public ChartService(ChartRepositoryCustom chartRepository, ManagerRepository managerRepository) {
        this.chartRepository = chartRepository;
        this.managerRepository = managerRepository;
    }

    public List<ChartItem> getChartData(String username, StatusName statusName) {
        Manager manager = findManagerByUsername(username);

        return chartRepository.findChartItems(manager.getId(), statusName);
    }

    private Manager findManagerByUsername(String username) {
        Manager manager = managerRepository.findByUsername(username);

        if (manager == null)
            throw new CarShopException(ErrorCode.USER_NOT_EXISTS, username);

        return manager;
    }

}
