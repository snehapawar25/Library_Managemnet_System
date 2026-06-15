package edu.library_management_system.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.library_management_system.dto.UpdateBookPriceDto;
import edu.library_management_system.dto.request.BookRequestDto;
import edu.library_management_system.dto.response.ApiResponseDto;
import edu.library_management_system.dto.response.BookResponseDto;
import edu.library_management_system.service.BookService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@RestController
@RequestMapping("/book")  //give common url/endpoint pattern
public class BookController {
    //To add connection use DI 
	private BookService bookService;
	
	public BookController(BookService bookService) {
		this.bookService=bookService;
	}
	
	
	//Get Rest API
	
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponseDto<BookResponseDto>> getBook(@PathVariable int id) {
		BookResponseDto book=bookService.getBookById(id);
		
		return ResponseEntity.ok(ApiResponseDto.succes(book, "Book fetched succesfully"));
				
	}
	
	//=============================================================================================================
	//by BookresponseDto
//	@GetMapping
//	public List<BookResponseDto> getAllBook(){
//		return bookService.getAllBooks();
//	}
	
	@GetMapping
	public ResponseEntity<ApiResponseDto<List<BookResponseDto>>> getAllBooks(){
		List<BookResponseDto> books=bookService.getAllBooks();
		
		return ResponseEntity.ok(ApiResponseDto.succes(books, "Books fetch succesfully"));
	}
	
	//==============================================================================================
	@GetMapping("/search")
	public ResponseEntity<ApiResponseDto<BookResponseDto>> 
	getBookByTitleAndAuthor(@RequestParam @NotBlank(message="title is require")
	String title,
	@RequestParam @NotBlank(message="author is required")
	String author){
		BookResponseDto book
		=bookService.getBookByTitleAndAuthor(title, author);
		
		return ResponseEntity.ok(ApiResponseDto.succes(book, "Books fetch succesfully"));
	}
	
	//==============================================================================================
	
	@GetMapping("/price")
	public ResponseEntity<ApiResponseDto <List<BookResponseDto>>> getBooksPrice(
			@RequestParam @Positive(message="price must be greater than 0")double price)
	//@positive is used to make the user input must be greater than 0
	{
		List<BookResponseDto>books= bookService.getBooksByPrice(price);
		return ResponseEntity.ok(ApiResponseDto.succes(books, "Books with given price fetched successfully"));
	}
	//==============================================================================================
   
	@GetMapping("/filter")
	public ResponseEntity<ApiResponseDto<List<BookResponseDto>>> getBooks(
			@RequestParam(defaultValue ="0") int page,
			@RequestParam(defaultValue="2") int size,
			@RequestParam(defaultValue="tittle") String sortBy,
			@RequestParam(defaultValue="asc") String sortDirection
			){
		//Sorting
		Sort sort=(sortDirection.equalsIgnoreCase("desc"))? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
		
		Pageable p=PageRequest.of(page, size, sort);
		
		Page<BookResponseDto>books= bookService.getBooks(p);
		
		return ResponseEntity.ok(ApiResponseDto.succes(books.toList(), "Books with given price fetched successfully"));
	}
	
	//========================================================================
	
	//by bookresponsedto
//	
//	@PostMapping
//	public BookResponseDto addBook(@Valid @RequestBody BookRequestDto bookDto) {
//		return bookService.addBook(bookDto);
//	}
	
	@PostMapping
	public ResponseEntity<ApiResponseDto<BookResponseDto>> addBook(@Valid @RequestBody BookRequestDto bookDto) {
		
		BookResponseDto book=bookService.addBook(bookDto);
		
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(ApiResponseDto.created(book, "book added succesfully"));
	}
//=======================================================================================================
	
	
//	@PutMapping("{id}")
//	public BookResponseDto updateBook(@PathVariable int id, @Valid @RequestBody BookRequestDto dto) {
//		return bookService.UpdateBook(id,dto);
//	}
//	
	
	@PutMapping("{id}")
	public ResponseEntity<ApiResponseDto<BookResponseDto>> updateBook(@PathVariable int id, @Valid @RequestBody BookRequestDto dto) {
		
		BookResponseDto book=bookService.UpdateBook(id, dto);
		
		return ResponseEntity.ok(ApiResponseDto.succes(book, "Book update succesfully"));
	}
	
//=======================================================================================================
	
//	@PatchMapping("/{id}")
//public void UpdateBookPrice(@PathVariable int id,@Valid @RequestBody UpdateBookPriceDto dto) {
//		bookService.updateBookPrice(id, dto);
//	}
	
	
	@PatchMapping("/{id}")
	public ResponseEntity<ApiResponseDto<BookResponseDto>> UpdateBookPrice(@PathVariable int id,
			@Valid @RequestBody UpdateBookPriceDto dto, @RequestHeader(value="prefer",required=false)String prefer) {
			BookResponseDto book=bookService.updateBookPrice(id, dto);
			
			if("return=reperesentation".equals(prefer)) {
				return ResponseEntity.ok(ApiResponseDto.succes(book, "Price updated sucessfully"));
			}
			return ResponseEntity.noContent().build();   //When don't want to return any message then use nocontent.build
		}

//=======================================================================================================
	
//	@DeleteMapping("/{id}")
//	public void deleteBook(@PathVariable int id) {
//		bookService.deleteBook(id);
//	}
//	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteBook(@PathVariable int id) {
		bookService.deleteBook(id);
		return ResponseEntity.noContent().build();
	}
	
	
	
}
