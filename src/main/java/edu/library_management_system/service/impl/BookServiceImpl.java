package edu.library_management_system.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import edu.library_management_system.dto.UpdateBookPriceDto;
import edu.library_management_system.dto.request.BookRequestDto;
import edu.library_management_system.dto.response.BookResponseDto;
import edu.library_management_system.entity.Book;
import edu.library_management_system.exception.ResourceNotFoundException;
import edu.library_management_system.mapper.BookMapper;
import edu.library_management_system.repository.BookRepository;
import edu.library_management_system.service.BookService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService{
   //Dependency Injection
	 private final BookRepository bookRepository;
	 private final BookMapper mapper;
	 
	@Override
	public BookResponseDto getBookById(int id) {
		Book book=bookRepository.findById(id).orElseThrow(
				()->new ResourceNotFoundException("Book not found with given id:" + id)
				);
		return mapper.toDto(book);
	}

	@Override
	public List<BookResponseDto> getAllBooks() {
		List<Book> books =bookRepository.findAll();
		return books.stream()
				.map(mapper::toDto)
				.toList();
	}

	@Override
	public BookResponseDto getBookByTitleAndAuthor(String title, String author) {
		Book book=bookRepository.findByTitleAndAuthor(title, author);
		return mapper.toDto(book);
	}

	@Override
	public List<BookResponseDto> getBooksByPrice(double price) {
		List<Book> books=bookRepository.getBooksByPrice(price);
		
		return books.stream().map(mapper::toDto).toList();
	}

	@Override
	public Page<BookResponseDto> getBooks(Pageable p) {
		Page<Book> books=bookRepository.findAll(p);
		return books.map(mapper::toDto);
	}

	@Override
	public BookResponseDto addBook(BookRequestDto dto) {
		Book book =mapper.toEntity(dto);
		Book newBook = bookRepository.save(book);
		
		return mapper.toDto(newBook);
	}

	@Override
	public BookResponseDto UpdateBook(int id, BookRequestDto dto) {
		 Book book=bookRepository.findById(id).orElseThrow();
		 
		book.setTitle(dto.getTitle());
		book.setAuthor(dto.getAuthor());
		book.setPrice(dto.getPrice());
		book.setYear(dto.getYear());
		
		//After updation we need to save
		Book updated= bookRepository.save(book);
		
		return mapper.toDto(updated);
	}

	@Override
	public BookResponseDto updateBookPrice(int id, UpdateBookPriceDto dto) {
		Book book=bookRepository.findById(id).orElseThrow();
		book.setPrice(dto.getPrice());
		
		Book b=bookRepository.save(book);
		
		return mapper.toDto(b);
		
	}

	@Override
	public void deleteBook(int id) {
		//bookRepository.deleteById(id);
		
		//OR
		
		Book book=bookRepository.findById(id).orElseThrow();
		bookRepository.delete(book);
	}

	
	

}
