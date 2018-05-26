package com.sumzupp.backendapp.beans;

/**
 * Created by akash.mercer on 01-Jan-18.
 */

public class MerchantHashBean extends StatusBean {

    private String merchantHash;

    public MerchantHashBean() {

    }

    public String getMerchantHash() {
        return merchantHash;
    }

    public void setMerchantHash(String merchantHash) {
        this.merchantHash = merchantHash;
    }
}
