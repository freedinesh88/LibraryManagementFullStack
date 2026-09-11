package com.dinesh.LibraryManagementSystem.payload.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class GenreDTO {
	private Long id;

	@NotBlank(message = "Genra code is mandetory")
	private String code;

	@NotBlank(message = "Genra name is mandetory")
	private String name;

	@Size(max = 500, message = "Description must not exceed 500 character")
	private String description;

	@Min(value = 0, message = "Display order cannot be negative")
	private Integer displayOrder = 0;

	private Boolean active;

	private Long parentGenreId;

	private String parentGenreName;

	private List<GenreDTO> subGenre;

	private Long bookCount;

	@CreationTimestamp
	private LocalDateTime createdAt;

	@UpdateTimestamp
	private LocalDateTime updatedAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Long getParentGenreId() {
		return parentGenreId;
	}

	public void setParentGenreId(Long parentGenreId) {
		this.parentGenreId = parentGenreId;
	}

	public String getParentGenreName() {
		return parentGenreName;
	}

	public void setParentGenreName(String parentGenreName) {
		this.parentGenreName = parentGenreName;
	}

	public List<GenreDTO> getSubGenre() {
		return subGenre;
	}

	public void setSubGenre(List<GenreDTO> subGenre) {
		this.subGenre = subGenre;
	}

	public Long getBookCount() {
		return bookCount;
	}

	public void setBookCount(Long bookCount) {
		this.bookCount = bookCount;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public GenreDTO(Long id, @NotBlank(message = "Genra code is mandetory") String code,
			@NotBlank(message = "Genra name is mandetory") String name,
			@Size(max = 500, message = "Description must not exceed 500 character") String description,
			@Min(value = 0, message = "Display order cannot be negative") Integer displayOrder, Boolean active,
			Long parentGenreId, String parentGenreName, List<GenreDTO> subGenre, Long bookCount,
			LocalDateTime createdAt, LocalDateTime updatedAt) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
		this.description = description;
		this.displayOrder = displayOrder;
		this.active = active;
		this.parentGenreId = parentGenreId;
		this.parentGenreName = parentGenreName;
		this.subGenre = subGenre;
		this.bookCount = bookCount;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public GenreDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

}
