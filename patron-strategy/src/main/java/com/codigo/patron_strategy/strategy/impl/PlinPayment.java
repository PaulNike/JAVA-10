package com.codigo.patron_strategy.strategy.impl;

import com.codigo.patron_strategy.constant.Constants;
import com.codigo.patron_strategy.strategy.PaymentStrategy;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@AllArgsConstructor
public class PlinPayment implements PaymentStrategy {

    private  String numberCelphone;
    private String name;
    @Override
    public void pay(double amount) {
        log.info(Constants.PAGANDO + amount
                + " CON PLIN IBK PARA EL CEL: " + numberCelphone
                + " PERTENECIENTE A: "+ name);

    }
}
