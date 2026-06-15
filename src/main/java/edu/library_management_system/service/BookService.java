package edu.library_management_system.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import edu.library_management_system.dto.UpdateBookPriceDto;
import edu.library_management_system.dto.request.BookRequestDto;
import edu.library_management_system.dto.response.BookResponseDto;

public interface BookService {
     public BookResponseDto getBookById(int id);
     
     public List<BookResponseDto> getAllBooks();
     
     public BookResponseDto getBookByTitleAndAuthor(String tittle,String author);
     
     public List<BookResponseDto> getBooksByPrice(double price);
     
     //Pagination
     public Page<BookResponseDto> getBooks(Pageable p);//pageable data domain
     
     public BookResponseDto addBook(BookRequestDto dto);
     
     public BookResponseDto UpdateBook(int id,BookRequestDto dto);
     
     public BookResponseDto updateBookPrice(int id,UpdateBookPriceDto dto);
     
     public void deleteBook(int id);
     
     
}
