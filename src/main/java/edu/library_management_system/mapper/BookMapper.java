package edu.library_management_system.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.library_management_system.dto.request.BookRequestDto;
import edu.library_management_system.dto.response.BookResponseDto;
import edu.library_management_system.entity.Book;
@Component
//To acces bbok to dto and dto to book so that implement this mapper class
public class BookMapper {
	@Autowired
  private ModelMapper mapper;
	
	public BookResponseDto toDto(Book book) {
		return mapper.map(book, BookResponseDto.class);
	}
	
	public Book toEntity(BookRequestDto dto) {
		return mapper.map(dto, Book.class);
	}
  
}
