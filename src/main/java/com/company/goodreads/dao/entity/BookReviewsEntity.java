package com.company.goodreads.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.*;

@Entity
@Table(name = "book_reviews")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookReviewsEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "comment")
    private String comment;

    @Column(name = "rating")
    private Integer rating;

    @Column(name = "book_id")
    private Long bookId;

    @ManyToOne
    private UsersEntity user;
}
