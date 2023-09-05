package com.aspira.yrakovets.data;

import java.util.List;

public class Event {
    Long id;
    String name;
    Long kickoff;

    List<Market> markets;

    public Event() {
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

    public Long getKickoff() {
        return kickoff;
    }

    public void setKickoff(Long kickoff) {
        this.kickoff = kickoff;
    }

    public List<Market> getMarkets() {
        return markets;
    }

    public void setMarkets(List<Market> markets) {
        this.markets = markets;
    }
}
