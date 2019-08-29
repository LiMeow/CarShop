package net.thumbtack.shop.responses;

import javax.persistence.Embeddable;
import java.math.BigInteger;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Objects;

@Embeddable
public class ChartItem {
    private String label;
    private int Y;

    public ChartItem() {
    }

   /* public ChartItem(String label, int y) {
        this.label = label;
        Y = y;
    }*/

    public ChartItem(Integer label, int y) {
        this.label = Month.of(label).getDisplayName(TextStyle.FULL_STANDALONE, Locale.ENGLISH);
        Y = y;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(Double label) {
        this.label = Month.of(label.intValue()).getDisplayName(TextStyle.FULL_STANDALONE, Locale.ENGLISH);
    }

    public int getY() {
        return Y;
    }

    public void setY(BigInteger y) {
        this.Y = y.intValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChartItem)) return false;
        ChartItem chartItem = (ChartItem) o;
        return getY() == chartItem.getY() &&
                getLabel().equals(chartItem.getLabel());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLabel(), getY());
    }

    @Override
    public String toString() {
        return "ChartItem{" +
                "label='" + label + '\'' +
                ", Y=" + Y +
                '}';
    }
}
