package net.thumbtack.shop;


import net.thumbtack.bank.BankApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class ShopApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ShopApplication.class, BankApplication.class).run(args);
    }

}
