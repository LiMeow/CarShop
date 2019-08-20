package net.thumbtack.internship.carshop.responses;

import javax.persistence.Embeddable;
import java.math.BigInteger;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;

@Embeddable
public class ChartItem {
    private String label;
    private int Y;

    public ChartItem() {
    }

    public ChartItem(int Y, String label) {
        this.Y = Y;
        this.label = label;
    }

    public int getY() {
        return Y;
    }

    public void setY(BigInteger y) {
        this.Y = y.intValue();
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(Double label) {
        this.label = Month.of(label.intValue()).getDisplayName(TextStyle.FULL_STANDALONE, Locale.ENGLISH);
    }

    @Override
    public String toString() {
        return "ChartItem{" +
                "label='" + label + '\'' +
                ", Y=" + Y +
                '}';
    }
}
