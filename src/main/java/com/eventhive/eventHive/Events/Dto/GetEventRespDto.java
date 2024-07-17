package com.eventhive.eventHive.Events.Dto;

import com.eventhive.eventHive.Category.Dto.CategoryRespDto;
import com.eventhive.eventHive.EventTicket.Dto.EventTicketDto;
import com.eventhive.eventHive.EventTicket.Entity.EventTicket;
import com.eventhive.eventHive.Events.Entity.Events;
import lombok.Data;
import lombok.extern.java.Log;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Data
@Log
public class GetEventRespDto {
    private Long id;
    private String title;
    private String description;
    private String organizer;
    private String date;
    private String time;
    private String location;
    private CategoryRespDto category;
    private String picture;
    private String priceRange;
    public static GetEventRespDto convertToDto (Events events){
        GetEventRespDto dto = new GetEventRespDto();
        dto.setId(events.getId());
        dto.setTitle(events.getTitle());
        dto.setDescription(events.getDescription());
        dto.setOrganizer(events.getOrganizer().getUsername());
        dto.setDate(events.getDate().toString());
        dto.setTime(events.getTime().toString());
        dto.setLocation(events.getLocation());
        dto.setPicture(events.getPicture());
        dto.setCategory(CategoryRespDto.convertDto(events.getCategory()));

        // Get Price Range
        List<BigDecimal> prices = events.getEventTickets().stream()
                .map(EventTicket::getPrice)
                .toList();
        if (prices != null && !prices.isEmpty()) {
            BigDecimal minPrice = prices.stream().min(BigDecimal::compareTo).orElse(BigDecimal.ZERO);
            BigDecimal maxPrice = prices.stream().max(BigDecimal::compareTo).orElse(BigDecimal.ZERO);

            if (minPrice.compareTo(BigDecimal.ZERO) == 0 && maxPrice.compareTo(BigDecimal.ZERO) == 0) {
                dto.setPriceRange("FREE");
            } else {
                var idrLocale = Locale.of("id", "ID");
                var idrFormat = NumberFormat.getCurrencyInstance(idrLocale);
                idrFormat.setMaximumFractionDigits(0);

                dto.setPriceRange(minPrice.equals(maxPrice)
                        ? idrFormat.format(minPrice)
                        : idrFormat.format(minPrice) + " - " + idrFormat.format(maxPrice));
            }
        } else {
            dto.setPriceRange("FREE");
        }
        return dto;
    }
}
