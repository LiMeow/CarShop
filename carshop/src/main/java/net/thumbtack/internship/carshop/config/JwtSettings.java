package net.thumbtack.internship.carshop.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class JwtSettings {
    private final int aTokenDuration;
    private final int aLongTokenDuration;


    public JwtSettings(@Value("${jwt.aTokenDuration}") int aTokenDuration,
                       @Value("${jwt.aLongTokenDuration}") int aLongTokenDuration) {
        this.aTokenDuration = aTokenDuration;
        this.aLongTokenDuration = aLongTokenDuration;
    }

    public int getaTokenDuration() {
        return aTokenDuration;
    }

    public int getaLongTokenDuration() {
        return aLongTokenDuration;
    }

    public Duration getTokenExpiredIn() {
        return Duration.ofMinutes(aTokenDuration);
    }

    public Duration getLongTokenExpiredIn() {
        return Duration.ofMinutes(aLongTokenDuration);
    }
}
