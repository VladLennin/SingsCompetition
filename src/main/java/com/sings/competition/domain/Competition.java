package com.sings.competition.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Table(name = "competitions")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
public class Competition {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDate date;
    private String name;

    private String description;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Competitor> competitors = new ArrayList<>();


    public boolean isActive() {
        if (LocalDate.now().isAfter(date)) {
            return false;
        } else {
            return true;
        }
    }

    public Competition(LocalDate date, String name, String description) {
        this.date = date;
        this.name = name;
        this.description = description;
    }


}
