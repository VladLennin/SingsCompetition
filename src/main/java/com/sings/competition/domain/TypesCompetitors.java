package com.sings.competition.domain;

public enum TypesCompetitors {
    CHORAL("Хор"), POP("Естрадний"), SOLO("Соло"), OPERATIC("Оперний");

    private final String name;

    public String getName() {
        return name;
    }

    TypesCompetitors(String name) {
        this.name = name;
    }
}
