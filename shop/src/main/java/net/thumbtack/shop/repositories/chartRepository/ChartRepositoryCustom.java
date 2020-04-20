package net.thumbtack.shop.repositories.chartRepository;

import net.thumbtack.shop.models.StatusName;
import net.thumbtack.shop.responses.ChartItem;

import java.util.List;

public interface ChartRepositoryCustom {

    List<ChartItem> findChartItems(int managerId, StatusName statusName);
}
