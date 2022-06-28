package com.company.goodreads.dao.entity;

import com.company.goodreads.model.enums.BookStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "books")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BooksEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "genre")
    private String genre;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "left_in_stock")
    private Integer leftInStock;

    @Column(name = "author")
    private String author;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "page_count")
    private Integer pageCount;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private BookStatus status;

    @Column(name = "language")
    private String language;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "image")
    private String image;

    @OneToMany()  // book ile book reviews entity arasinda unidirectional(join column) elaqe qurdum. bidirectional(mappedby) da mapped bydan istifade olunur
    @JoinColumn(name = "book_id")
    private List<BookReviewsEntity> reviews = new ArrayList<>(); // THYMELEAF da istifade eden zaman null xetasi ala bilerik ona gore  bos array yaratdim.


}
