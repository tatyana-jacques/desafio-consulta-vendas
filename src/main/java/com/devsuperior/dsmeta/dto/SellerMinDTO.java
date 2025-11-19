package com.devsuperior.dsmeta.dto;

public class SellerMinDTO {

    private String sellerName;
    private Double totalAmount;

    public SellerMinDTO(String sellerName, Double totalAmount) {
        this.sellerName = sellerName;
        this.totalAmount = totalAmount;
    }

    public String getSellerName() {
        return sellerName;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }
}
