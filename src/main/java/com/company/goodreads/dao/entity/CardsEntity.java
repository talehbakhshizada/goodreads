package com.company.goodreads.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cards")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "number")
    private String number;

    @Column(name = "cvv")
    private String cvv;

    @ManyToMany
    @JoinTable(name = "users_cards",
            joinColumns = @JoinColumn(name = "card_id"), // cards users-i tanimaq uchun istifade olunur
            inverseJoinColumns = @JoinColumn(name = "user_id")) // users cards-i tanimaq uchun istifade olunur
    private List<UsersEntity> users = new ArrayList<>(); // bazadan userin butun kartlarini goturmek uchun bu elaqe istifade olunacaq
}
