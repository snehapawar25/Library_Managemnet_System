package edu.library_management_system.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookRequestDto {
	@NotBlank(message ="Tittle cannot be blank")
	 private String title;
	
	@NotBlank(message = "Title cannot be blank")
	  private String author;
	
     @Min(value =1,message="price must be greater than 0")
	  private Double price;
	
	
	  private Integer year;
}
