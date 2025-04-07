package com.kaif.springboot.windsorbookshop.controller;

import com.kaif.springboot.windsorbookshop.dto.AuthorDTO;
import com.kaif.springboot.windsorbookshop.entitis.Authors;
import com.kaif.springboot.windsorbookshop.service.AuthorService;
import com.kaif.springboot.windsorbookshop.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/author")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @PostMapping("save")
    public ResponseUtil saveAuthor(@RequestBody AuthorDTO authorDTO){
        authorService.saveAuthor(authorDTO);
        return new ResponseUtil(
                200,
                "Author Saved",
                null
        );
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getTotalAuthors() {
        long count = authorService.getAuthorCount();
        return ResponseEntity.ok(count);
    }


    @GetMapping("getAll")
    public ResponseUtil getAllAuthors(){
        return new ResponseUtil(
                200,
                "Author List",
                authorService.getAllAuthors()
        );
    }

    @PutMapping("update")
    public ResponseUtil updateAuthor(@RequestBody AuthorDTO authorDTO){
        authorService.updateAuthor(authorDTO);
        return new ResponseUtil(
                200,
                "Author Updated",
                null
        );
    }

    @DeleteMapping("delete/{id}")
    public ResponseUtil deleteAuthor(@PathVariable("id") int id){
        authorService.deleteAuthor(id);
        return new ResponseUtil(
                200,
                "Author Deleted",
                null
        );
    }

    @GetMapping("get/{id}")
    public ResponseUtil getAuthorById(@PathVariable("id") int id) {

        List<Authors> authorsList = authorService.getAuthorById(id);

        if (!authorsList.isEmpty()) {
            return new ResponseUtil(
                    200,
                    "Author Found",
                    authorsList.get(0)
            );
        } else {
            return new ResponseUtil(
                    404,
                    "Author Not Found",
                    null
            );
        }
    }



}
