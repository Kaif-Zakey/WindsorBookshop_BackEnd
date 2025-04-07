package com.kaif.springboot.windsorbookshop.service;

import com.kaif.springboot.windsorbookshop.dto.AuthorDTO;
import com.kaif.springboot.windsorbookshop.entitis.Authors;
import com.kaif.springboot.windsorbookshop.repo.AuthorRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepo authorRepository;

    @Autowired
    private ModelMapper modelMapper;

    public void saveAuthor(AuthorDTO authorDTO) {
       if (authorRepository.existsById(authorDTO.getId())) {
           throw new RuntimeException("Author already exists");
         }
        Authors authors= modelMapper.map(authorDTO, Authors.class);
       authorRepository.save(authors);
    }


    public List<AuthorDTO> getAllAuthors() {
        return modelMapper.map(authorRepository.findAll(),
                new org.modelmapper.TypeToken<List<AuthorDTO>>(){}.getType());
    }



    public void updateAuthor(AuthorDTO authorDTO) {
        if (authorRepository.existsById(authorDTO.getId())) {
            authorRepository.save(modelMapper.map(authorDTO, Authors.class));
        } else {
            throw new RuntimeException("Author does not exist");
        }

    }

    public void deleteAuthor(int id) {
        authorRepository.deleteById(id);
    }


    public List<Authors> getAuthorById(int id) {
    List<Authors> authorsList = authorRepository.findById(id)
            .map(Collections::singletonList)
            .orElse(Collections.emptyList());

    return modelMapper.map(authorsList, new org.modelmapper.TypeToken<List<Authors>>(){}.getType());


    }


    public long getAuthorCount() {
        return authorRepository.count();
    }
}
