package com.lhxpay.examples.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Controller
public class PayController {

    private static final String APP_KEY = "woKL_S7BS0aorZiIsrZD9Q";
    private static final String APP_SECRET = "4c4f4dee11eb2a6b8c9d97c67942ed64bc6f25d42a1ef8b80f74a530035b4542";

    @Autowired
    private PaymentStore paymentStore;

    @RequestMapping(value = "/pay", method = RequestMethod.GET)
    public String showPayPage(ModelMap modelMap) {
        return "prepare";
    }

    @RequestMapping(value = "/sendRequest", method = RequestMethod.POST)
    public String createPayment(ModelMap modelMap, @RequestParam double amount, @RequestParam int channel) {
        Payment payment = new Payment()
                .setAmount(amount)
                .setId(UUID.randomUUID().toString());
        this.paymentStore.insert(payment);

        PayRequest payRequest =  new PayRequest()
                .setAmount(amount)
                .setChannelId(channel)
                .setAppKey(APP_KEY)
                .setCallbackUrl("https://your-call-back-url")
                .setReturnUrl("https://member-return-url")
                .setMrn(payment.getId());
        payRequest.setSign(DigestUtils.md5DigestAsHex(
                payRequest.toSignContent(APP_SECRET)
                        .getBytes(StandardCharsets.UTF_8)));
        modelMap.put("payRequest", payRequest);
        return "sendRequest";
    }

    @RequestMapping(value = "/notify", method = RequestMethod.POST, produces = {"text/plain"})
    @ResponseBody
    public String processCallback(
            ModelMap modelMap,
            @RequestParam(value = "amount")double amount,
            @RequestParam(value = "appKey")String appKey,
            @RequestParam(value = "channel")int channel,
            @RequestParam(value = "mrn")String mrn,
            @RequestParam(value = "originAmount")int originAmount,
            @RequestParam(value = "sign")String sign
            ) {
        String localSign = DigestUtils.md5DigestAsHex(
                String.format(
                        "amount=%.2f&appKey=%s&channel=%d&mrn=%s&originAmount=%d&key=%s",
                        amount,
                        appKey,
                        channel,
                        mrn,
                        originAmount,
                        APP_SECRET)
                .getBytes(StandardCharsets.UTF_8));
        if (!localSign.equals(sign)) {
            // log error and return
            return "bad signature";
        } else {
            Payment payment = this.paymentStore.getPayment(mrn);
            if (payment == null) {
                return "payment not found";
            } else {
                // set payment as completed and callback merchant
                return "success";
            }
        }
    }
}
