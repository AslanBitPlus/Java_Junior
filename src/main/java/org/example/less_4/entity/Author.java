package org.example.less_4.entity;

import jakarta.persistence.*;

import java.util.List;

// create table author(
//   id bigint primary key,
//   name varchar(256)
// )

@Entity
@Table(name = "author")
public class Author { // final нельзя

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "first_name")
    @JoinColumn
    private String f_name;

    @Column(name = "last_name")
    @JoinColumn
    private String l_name;

    public Author() {

    }

    public Author(String f_name, String l_name) {
        this.f_name = f_name;
        this.l_name = l_name;
    }

    public String getF_name() {
        return f_name;
    }

    public String getL_name() {
        return l_name;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    public void setL_name(String l_name) {
        this.l_name = l_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", f_name='" + f_name + '\'' +
                ", l_name='" + l_name + '\'' +
                '}';
    }

    //  @OneToMany(mappedBy = "author")
//  @ManyToMany(fetch = FetchType.LAZY)
//  @JoinTable(
//    name = "author_book",
//    joinColumns = @JoinColumn(name = "author_id"),
//    inverseJoinColumns = @JoinColumn(name = "book_id")
//  )
//  private List<Book> books;


}
