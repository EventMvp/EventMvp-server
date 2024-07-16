package com.eventhive.eventHive.Voucher.dto;

import com.eventhive.eventHive.Voucher.Entity.Voucher;
import com.eventhive.eventHive.utils.LocalDateSerializer;
import com.eventhive.eventHive.utils.LocalDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.time.LocalDate;

@Data
public class VoucherDto {
    private Long id;
    private int discountPercentage;
    private String name;
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate expiryDate;
    private Boolean isReferralPromotion;

    public static VoucherDto convertToDto (Voucher voucher){
        VoucherDto dto = new VoucherDto();
        dto.setId(voucher.getId());
        dto.setName(voucher.getName());
        dto.setExpiryDate(voucher.getExpiryDate());
        dto.setDiscountPercentage(voucher.getDiscountPercentage());
        dto.setIsReferralPromotion(voucher.isReferralBased());
        return dto;
    }
}
