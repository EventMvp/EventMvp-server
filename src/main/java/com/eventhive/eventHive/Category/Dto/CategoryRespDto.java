package com.eventhive.eventHive.Category.Dto;

import com.eventhive.eventHive.Category.Entity.Category;
import lombok.Data;

@Data
public class CategoryRespDto {
    private String name;

    public static CategoryRespDto convertDto (Category category){
        CategoryRespDto dto = new CategoryRespDto();
        dto.setName(category.getName());
        return dto;
    }
}
