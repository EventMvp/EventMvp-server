package com.eventhive.eventHive.Voucher.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class VoucherDto {
    private int discountPercentage;
    private String name;
    private LocalDate expiryDate;
}
