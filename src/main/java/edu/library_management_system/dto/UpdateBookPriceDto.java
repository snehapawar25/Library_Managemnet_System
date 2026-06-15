package edu.library_management_system.dto;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateBookPriceDto {
@Min(value =1,message = "price must be grater than 0")
   private Double price;
}
