package com.kaif.springboot.windsorbookshop.controller;

import com.kaif.springboot.windsorbookshop.entitis.Books;
import com.kaif.springboot.windsorbookshop.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/v1/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/{id}")
    public ResponseEntity<Books> getBookById(@PathVariable int id) {
        Books book = bookService.getBookById(id);
        return ResponseEntity.ok(book);
    }

    @PostMapping("/add")
    public ResponseEntity<Books> addBook(
            @ModelAttribute Books book,
            @RequestParam(value = "image", required = false) MultipartFile imageFile,
            @RequestParam("authorId") int authorId,
            @RequestParam("categoryId") int categoryId
    ) throws IOException {
        Books savedBook = bookService.saveBookWithImage(book, imageFile, authorId, categoryId);
        return ResponseEntity.ok(savedBook);
    }

    @PutMapping("/{id}/image")
    public ResponseEntity<Books> updateBookImage(
            @PathVariable int id,
            @RequestParam("image") MultipartFile imageFile
    ) throws IOException {
        Books updatedBook = bookService.updateBookImage(id, imageFile);
        return ResponseEntity.ok(updatedBook);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getBookCount() {
        long count = bookService.getBookCount();
        return ResponseEntity.ok(count);
    }

    // Endpoint to get image directly
    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getBookImage(@PathVariable int id) {
        Books book = bookService.getBookById(id);

        if (book.getImageData() == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(determineImageType(book.getImageName())))
                .body(book.getImageData());
    }

    // Helper method to determine image MIME type
    private String determineImageType(String filename) {
        if (filename == null) return "application/octet-stream";

        filename = filename.toLowerCase();
        if (filename.endsWith(".png")) return "image/png";
        if (filename.endsWith(".jpg") || filename.endsWith(".jpeg")) return "image/jpeg";
        if (filename.endsWith(".gif")) return "image/gif";

        return "application/octet-stream";
    }
    @GetMapping
    public ResponseEntity<List<Books>> getAllBooks() {
        List<Books> books = bookService.getAllBooks();
        if (books.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(books);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable int id) {
        try {
            bookService.deleteBook(id);  // Call service method to delete the book
            return ResponseEntity.noContent().build();  // Respond with 204 No Content status if successful
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();  // Handle errors
        }
    }


    @PutMapping("/update")
    public ResponseEntity<Books> updateBook(
            @ModelAttribute Books book,
            @RequestParam(value = "image", required = false) MultipartFile imageFile,
            @RequestParam("authorId") int authorId,
            @RequestParam("categoryId") int categoryId) {
        try {
            Books updatedBook = bookService.updateBook(book, imageFile, authorId, categoryId);
            return ResponseEntity.ok(updatedBook);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    @GetMapping("/category/{categoryId}")
    public List<Books> getBooksByCategory(@PathVariable int categoryId) {
        return bookService.getBooksByCategoryId(categoryId);
    }
}
