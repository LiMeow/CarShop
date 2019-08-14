package net.thumbtack.internship.carshop.services;

import net.thumbtack.internship.carshop.responses.ChartItem;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChartService {

    @Autowired
    private EntityManager entityManager;

    public List<List<Map<Object, Object>>> getChartData() {
        Map<Object, Object> map = null;
        List<List<Map<Object, Object>>> list = new ArrayList<>();
        List<Map<Object, Object>> dataPoints = new ArrayList<>();
        List<ChartItem> chartItems = getChartItems();

        for (ChartItem item : chartItems) {
            map = new HashMap<>();
            map.put("x", item.getMonth());
            map.put("y", item.getCount());
            dataPoints.add(map);
        }
        list.add(dataPoints);
        return list;
    }

    private List<ChartItem> getChartItems() {
        String sql = "SELECT count(transaction.customer_id), date_part('month',transaction_status.date) as month " +
                "FROM transaction_status JOIN transaction " +
                "ON transaction_status.transaction_id = transaction.id " +
                "GROUP BY date_part('month',transaction_status.date ) " +
                "ORDER BY month";

        List chartItems = entityManager
                .createNativeQuery(sql)
                .unwrap(NativeQuery.class)
                .setResultTransformer(Transformers.aliasToBean(ChartItem.class))
                .getResultList();

        return chartItems;
    }

}
