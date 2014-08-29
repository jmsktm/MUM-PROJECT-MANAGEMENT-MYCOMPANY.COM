/*
 * Copyright (c)2014
 */
package com.mycompany.services.paymentgateway;

import com.mycompany.models.CreditCard;
import com.mycompany.models.Users;
import com.mycompany.services.MandrillService;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

/**
 *
 * @author Va Y.
 */
@Stateless
@LocalBean
public class PaymentGatewayListener {
    
    @EJB
    private PaymentGatewayService client;
    
    @EJB
    MandrillService smtp;

    @AroundInvoke
    public Object modifyGreeting(InvocationContext ctx) throws Exception {
        Object[] parameters = ctx.getParameters();
        Users param = (Users) parameters[0];
        parameters[0] = param;
        ctx.setParameters(parameters);
        
        CreditCard card = new CreditCard();
        StringBuilder sb = new StringBuilder();
        Random rand = new Random();
        for (int i=0; i<4; i++) {
            sb.append(String.format("%04d", rand.nextInt(9999)));
        }
        
        System.out.println("Card Number=" + sb.toString());
        
        card.setCardNumber(sb.toString());
        card.setCardholderName(param.getFirstName() + " " + param.getLastName());
        Calendar cal = Calendar.getInstance();
        //cal.add(Calendar.YEAR, 2);
//        Date expiredDate = new Date();
        
        card.setExpiryDate("0716");
        String pin = String.format("%03d", rand.nextInt(1000));
        card.setSecurityCode(pin);
        
        client.create_JSON(card);
        
        smtp.sendNewCreditCardInformation(param, card, "Credit Card registration information");
        
        try {
            return ctx.proceed();
        } catch (Exception e) {
            return null;
        }
    }
}
