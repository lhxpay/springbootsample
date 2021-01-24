package com.lhxpay.examples.demo;

public class PayRequest {
    private double amount;
    private String appKey;
    private String callbackUrl;
    private int channelId;
    private String mrn;
    private String returnUrl;
    private String sign;

    public double getAmount() {
        return amount;
    }

    public PayRequest setAmount(double amount) {
        this.amount = amount;
        return this;
    }

    public String getAppKey() {
        return appKey;
    }

    public PayRequest setAppKey(String appKey) {
        this.appKey = appKey;
        return this;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public PayRequest setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
        return this;
    }

    public int getChannelId() {
        return channelId;
    }

    public PayRequest setChannelId(int channelId) {
        this.channelId = channelId;
        return this;
    }

    public String getMrn() {
        return mrn;
    }

    public PayRequest setMrn(String mrn) {
        this.mrn = mrn;
        return this;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public PayRequest setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
        return this;
    }

    public String getSign() {
        return sign;
    }

    public PayRequest setSign(String sign) {
        this.sign = sign;
        return this;
    }

    public String toSignContent(String key) {
        return String.format(
                "amount=%.2f&appKey=%s&callbackUrl=%s&channelId=%d&mrn=%s&returnUrl=%s&key=%s",
                this.amount,
                this.appKey,
                this.callbackUrl,
                this.channelId,
                this.mrn,
                this.returnUrl,
                key);
    }
}
