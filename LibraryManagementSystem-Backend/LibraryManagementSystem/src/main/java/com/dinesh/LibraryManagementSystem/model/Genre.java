package com.dinesh.LibraryManagementSystem.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Genre {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank(message = "Genra code is mandetory")
	private String code;

	@NotBlank(message = "Genra name is mandetory")
	private String name;

	@Size(max = 500, message = "Description must not exceed 500 character")
	private String description;

	@Min(value = 0, message = "Display order cannot be negative")
	private Integer displayOrder = 0;

	@Column(nullable = false)
	private Boolean active = true;

	@ManyToOne
	private Genre parentGenre;

	@OneToMany
	private List<Genre> subGenres = new ArrayList<Genre>();

//	@OneToMany(mappedBy = "genre" , cascade = CascadeType.PERSIST)
//	private List<Book> books = new ArrayList<Book>();

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


	public Genre getParentGenre() {
		return parentGenre;
	}

	public void setParentGenre(Genre parentGenre) {
		this.parentGenre = parentGenre;
	}

	public List<Genre> getSubGenres() {
		return subGenres;
	}

	public void setSubGenres(List<Genre> subGenres) {
		this.subGenres = subGenres;
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

	public Genre() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Genre(Long id, @NotBlank(message = "Genra code is mandetory") String code,
			@NotBlank(message = "Genra name is mandetory") String name,
			@Size(max = 500, message = "Description must not exceed 500 character") String description,
			@Min(value = 0, message = "Display order cannot be negative") Integer displayOrder, Boolean active,
			Genre parentGenre, List<Genre> subGenres, LocalDateTime createdAt, LocalDateTime updatedAt) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
		this.description = description;
		this.displayOrder = displayOrder;
		this.active = active;
		this.parentGenre = parentGenre;
		this.subGenres = subGenres;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}


	
	

}
