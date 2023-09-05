package com.aspira.yrakovets.data;

import java.util.List;

public class Region {
    Long id;
    List<League> leagues;

    public Region() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<League> getLeagues() {
        return leagues;
    }

    public void setLeagues(List<League> leagues) {
        this.leagues = leagues;
    }
}
