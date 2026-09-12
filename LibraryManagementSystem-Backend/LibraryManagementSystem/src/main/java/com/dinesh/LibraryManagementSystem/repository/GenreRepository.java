package com.dinesh.LibraryManagementSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dinesh.LibraryManagementSystem.model.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {

	List<Genre> findByActiveTrueOrderByDisplayOrderAsc();

	List<Genre> findByParentGenreIsNullAndActiveTrueOrderByDisplayOrderAsc();

	List<Genre> findByParentGenreIdAndActiveTrueOrderByDisplayOrderAsc(Long parentGenreId);

	Long countByActiveTrue();

//	@Query("select count(b) from book b where b.genre.id=:genreId")
//	Long countBookByGenre(@Param("genreId") Long genreId);

}
