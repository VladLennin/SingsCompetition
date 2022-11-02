package com.sings.competition.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "competitors")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
public class Competitor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @OneToOne
    private User leader;
    @ElementCollection
    private List<String> members = new ArrayList<String>();

    @Enumerated(EnumType.STRING)
    private TypesCompetitors type;


    public Competitor(String name, User leader, List<String> members, TypesCompetitors type) {
        this.name = name;
        this.leader = leader;
        this.members = members;
        this.type = type;
    }
}
