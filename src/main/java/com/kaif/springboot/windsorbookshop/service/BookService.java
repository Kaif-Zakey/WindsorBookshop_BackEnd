package com.kaif.springboot.windsorbookshop.service;

import com.kaif.springboot.windsorbookshop.dto.BookDTO;
import com.kaif.springboot.windsorbookshop.entitis.Authors;
import com.kaif.springboot.windsorbookshop.entitis.Books;
import com.kaif.springboot.windsorbookshop.entitis.Category;
import com.kaif.springboot.windsorbookshop.repo.AuthorRepo;
import com.kaif.springboot.windsorbookshop.repo.BookRepo;
import com.kaif.springboot.windsorbookshop.repo.CategoryRepo;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRepo bookRepository;

    @Autowired
    private AuthorRepo AuthorRepo;

    @Autowired
    private CategoryRepo CategoryRepo;

    @Autowired
    ModelMapper modelMapper;

    public Books saveBookWithImage(Books book, MultipartFile imageFile,Integer authorId,int categoryId) throws IOException {
        // Convert MultipartFile to byte array
        if (imageFile != null && !imageFile.isEmpty()) {
            book.setImageData(imageFile.getBytes());
            book.setImageName(imageFile.getOriginalFilename());
        }
        Authors authors= AuthorRepo.findById(authorId).orElseThrow(()->new RuntimeException("Author not found"));
        Category category= CategoryRepo.findById(categoryId).orElseThrow(()->new RuntimeException("Category not found"));

        book.setAuthors(authors);
        book.setCategory(category);

        return bookRepository.save(book);
    }

    public Books updateBookImage(int bookId, MultipartFile imageFile) throws IOException {
        Books existingBook = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        existingBook.setImageData(imageFile.getBytes());
        existingBook.setImageName(imageFile.getOriginalFilename());

        return bookRepository.save(existingBook);
    }

    // Method to retrieve book with image
    public Books getBookById(int id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }

    public List<Books> getAllBooks() {
        return bookRepository.findAll();  // Assuming you're using JPA and a method like this exists in the repository
    }

    public void deleteBook(int id) {
        Optional<Books> bookOptional = bookRepository.findById(id);
        if (bookOptional.isPresent()) {
            bookRepository.delete(bookOptional.get());
        } else {
            throw new RuntimeException("Book not found");  // Or handle it based on your needs
        }
    }
    public long getBookCount() {
        return bookRepository.count(); // Assuming bookRepository is a JPA repository
    }

    @Transactional
    public Books updateBook(Books book, MultipartFile imageFile, Integer authorId, Integer categoryId) throws IOException {
        if (bookRepository.existsById(book.getId())) {
            System.out.println("Book ID: " + book.getId());
            System.out.println("Author ID: " + authorId);
            System.out.println("Category ID: " + categoryId);

            book.setTitle(book.getTitle());
            book.setPrice(book.getPrice());
            book.setQuantity(book.getQuantity());

            Authors authors = AuthorRepo.findById(authorId)
                    .orElseThrow(() -> new RuntimeException("Author not found"));
            Category category = CategoryRepo.findById(categoryId)
                    .orElseThrow(() -> new RuntimeException("Category not found"));

            book.setAuthors(authors);
            book.setCategory(category);

            if (imageFile != null && !imageFile.isEmpty()) {
                book.setImageData(imageFile.getBytes());
                book.setImageName(imageFile.getOriginalFilename());
            }

            // Log the book object before saving
            System.out.println("Updated Book: " + book);

            return bookRepository.save(book);
        } else {
            return null;
        }
    }


    public List<Books> getBooksByCategoryId(int categoryId) {
        List<Books> books = bookRepository.findByCategoryId(categoryId);
        return books;
        // Fetch books based on category ID
    }
}