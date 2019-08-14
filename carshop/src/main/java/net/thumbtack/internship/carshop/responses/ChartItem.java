package net.thumbtack.internship.carshop.responses;

import javax.persistence.Embeddable;
import java.math.BigInteger;

@Embeddable
public class ChartItem {
    private int count;
    private int month;

    public ChartItem() {
    }

    public ChartItem(int count, int month) {
        this.count = count;
        this.month = month;
    }

    public int getCount() {
        return count;
    }

    public void setCount(BigInteger count) {
        this.count = count.intValue();
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(Double month) {
        this.month = month.intValue();
    }

    @Override
    public String toString() {
        return "ChartItem{" +
                "count=" + count +
                ", month=" + month +
                '}';
    }
}
