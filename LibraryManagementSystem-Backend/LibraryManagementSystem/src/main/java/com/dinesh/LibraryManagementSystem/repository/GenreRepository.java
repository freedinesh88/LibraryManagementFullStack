package com.dinesh.LibraryManagementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dinesh.LibraryManagementSystem.model.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {

}
