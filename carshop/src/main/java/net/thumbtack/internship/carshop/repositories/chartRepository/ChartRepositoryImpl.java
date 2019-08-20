package net.thumbtack.internship.carshop.repositories.chartRepository;

import net.thumbtack.internship.carshop.models.StatusName;
import net.thumbtack.internship.carshop.responses.ChartItem;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class ChartRepositoryImpl implements ChartRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<ChartItem> findChartItems(int managerId, StatusName statusName) {
        String sql = "SELECT count(transaction.customer_id) AS Y, date_part('month',transaction_status.date) as label " +
                "FROM transaction_status JOIN transaction " +
                "ON transaction_status.transaction_id = transaction.id " +
                "WHERE transaction_status.status_name = :statusName  " +
                "AND transaction.manager_id = :managerId " +
                "GROUP BY date_part('month',transaction_status.date ) " +
                "ORDER BY label";

        Query query = entityManager
                .createNativeQuery(sql)
                .setParameter("statusName", statusName.name())
                .setParameter("managerId", managerId)
                .unwrap(NativeQuery.class)
                .setResultTransformer(Transformers.aliasToBean(ChartItem.class));

        return query.getResultList();
    }
}
