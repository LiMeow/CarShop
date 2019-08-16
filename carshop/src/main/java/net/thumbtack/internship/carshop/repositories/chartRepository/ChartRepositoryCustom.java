package net.thumbtack.internship.carshop.repositories.chartRepository;

import net.thumbtack.internship.carshop.models.StatusName;
import net.thumbtack.internship.carshop.responses.ChartItem;

import java.util.List;

public interface ChartRepositoryCustom {

    List<ChartItem> findChartItems(int managerId, StatusName statusName);
}
