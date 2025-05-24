package com.codigo.patron_strategy.controller;

import com.codigo.patron_strategy.service.PaymentService;
import com.codigo.patron_strategy.strategy.impl.CreditCardPayment;
import com.codigo.patron_strategy.strategy.impl.CryptoPayment;
import com.codigo.patron_strategy.strategy.impl.DebitCardPayment;
import com.codigo.patron_strategy.strategy.impl.PlinPayment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/strategy")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/credit-card")
    public ResponseEntity<String> payWithCreditCard(@RequestParam double amount,
                                                    @RequestParam String cardNumber,
                                                    @RequestParam String cardHolderName,
                                                    @RequestParam String numShare){
        paymentService.setPaymentStrategy(new CreditCardPayment(cardNumber,cardHolderName,numShare));
        paymentService.processPayment(amount);
        return ResponseEntity.ok("Pago Procesado con Tarjeta de Credito");
    }
    @PostMapping("/debit-card")
    public ResponseEntity<String> payWithDebitCard(@RequestParam double amount,
                                                    @RequestParam String cardNumber,
                                                    @RequestParam String cardHolderName){
        paymentService.setPaymentStrategy(new DebitCardPayment(cardNumber, cardHolderName));
        paymentService.processPayment(amount);
        return ResponseEntity.ok("Pago Procesado con Tarjeta de Debito");
    }
    @PostMapping("/crypto")
    public ResponseEntity<String> payWithCrypto(@RequestParam double amount,
                                                   @RequestParam String wallet){
        paymentService.setPaymentStrategy(new CryptoPayment(wallet));
        paymentService.processPayment(amount);
        return ResponseEntity.ok("Pago Procesado con Tarjeta de CryptoMonedas");
    }
    @PostMapping("/plin")
    public ResponseEntity<String> payWithPlin(@RequestParam double amount,
                                                @RequestParam String numberCelphone,
                                              @RequestParam String name){
        paymentService.setPaymentStrategy(new PlinPayment(numberCelphone,name));
        paymentService.processPayment(amount);
        return ResponseEntity.ok("Pago Procesado con PLIN IBK");
    }
}
