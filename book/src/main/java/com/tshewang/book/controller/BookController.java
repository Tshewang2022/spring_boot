package com.tshewang.book.controller;

// this is the end of the first java project
// this is the complete rest api project
import com.tshewang.book.entities.Book;
import com.tshewang.book.exception.BookNotFound;
import com.tshewang.book.request.BookRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

// will complete this project by end of this day
// data binding, from json to java pojo
@Tag(name="Books Rest API Endpoints", description = "Operations related to book")
@RestController
@RequestMapping("/api/books")
public class BookController {

    private final List<Book> books = new ArrayList<>();

    public BookController(){
        initializeBook();
    }

    private void initializeBook(){
        books.addAll(List.of(
                new Book(1, "Computer science bro", "tshewang", "computer science", 5),
                new Book(2, "Mastery", "Robert Green", "philosophy", 5),
                new Book(3, "Think slow and fast", "Denial", "math", 4),
                new Book(4, "Rich dad, Poor dad", "Robert .k", "Finance", 3),
                new Book(6, "Think like a scientist", "Alux", "Human science", 4)

        ));
    }

    // look at every corner of code flow and how it works
    // then be master of it
    // restful api endpoint
    @Operation(summary = "Get all books", description = "Retrieve a list of all available books")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<Book> getBooks(@Parameter(description = "Optional query parameter") @RequestParam(required = false) String category){
        if(category==null){
            return books;
        }
        List<Book> filteredBooks = new ArrayList<>();
        for(Book book:books){
            if(book.getCategory().equalsIgnoreCase(category)){
                filteredBooks.add(book);
            }
        }
        return filteredBooks;
    }

    // GET methods in spring-boot
    @Operation(summary = "Get by Id", description = "Retrieve specific book by id")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public Book getBookById(@Parameter(description = "Get book by id")@PathVariable @Min(value=1) long id) {

        return books.stream().filter(book->book.getId() == id)
                .findFirst()
                .orElseThrow(
                        ()->new BookNotFound("Book not found "+ id)
                );
    }

    @Operation(summary = "Create a new book", description = "Add a new book to the list ")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createBook(@Valid @RequestBody BookRequest bookRequest){
      long id = books.isEmpty()? 1:books.get(books.size()-1).getId()+1;
       Book book =convertToBook(id, bookRequest);
       books.add(book);
    }

    @Operation(summary = "Update a book", description = "Update existing book ")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public Book updateBook(@Parameter(description = "Update book by Id")@PathVariable  long id,@Valid @RequestBody BookRequest bookRequest){
        for(int i = 0; i<books.size(); i++){
            if (books.get(i).getId()==id){
                Book updatedBook = convertToBook(id, bookRequest);
                books.set(i, updatedBook);
                return updatedBook;
            }
        }
        throw new BookNotFound("Book not found" + id);
    }

    @Operation(summary = "Delete a book", description = "Delete a book from the list ")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteBook(@Parameter(description = "Delete book by id")@PathVariable @Min(value = 1) long id){
        books.stream().filter(book->book.getId()==id).findFirst().orElseThrow(()-> new BookNotFound("Book not found " + id));
        books.removeIf(book->book.getId()==id);
    }

    private Book convertToBook(long id, BookRequest bookRequest){
        return new Book(
                id,
                bookRequest.getTitle(),
                bookRequest.getAuthor(),
                bookRequest.getCategory(),
                bookRequest.getRating()
        );
    }
}
