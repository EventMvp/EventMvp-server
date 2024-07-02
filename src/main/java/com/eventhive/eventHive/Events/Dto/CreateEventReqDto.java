package com.eventhive.eventHive.Events.Dto;

import com.eventhive.eventHive.EventTicket.Dto.EventTicketDto;
import com.eventhive.eventHive.Voucher.dto.VoucherDto;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class CreateEventReqDto {
    private String title;
    private String description;
    private LocalDate date;
    private LocalTime time;
    private String location;
    private Long categoryId;
    private BigDecimal price;
    private List<EventTicketDto> ticketTypes;
    private List<VoucherDto> vouchers;
    private MultipartFile picture;
}