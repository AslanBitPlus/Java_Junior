package org.example.less_4.entity;

import jakarta.persistence.*;

import java.util.List;

// create table book (
//   id bigint,
//   name varchar,
//   author_id foreign key references (author) id
// )
//

@Entity
@Table(name = "book")
public class Book {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

//  @ManyToMany
//  @JoinTable(
//    name = "author_book",
//    joinColumns = @JoinColumn(name = "book_id"),
//    inverseJoinColumns = @JoinColumn(name = "author_id")
//  )
//  private List<Author> authors;

    public Book() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//  public List<Author> getAuthors() {
//    return authors;
//  }
//
//  public void setAuthors(List<Author> authors) {
//    this.authors = authors;
//  }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
//      ", author=" + author +
                '}';
    }
}

