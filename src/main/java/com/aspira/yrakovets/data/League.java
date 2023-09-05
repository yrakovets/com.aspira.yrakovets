package com.aspira.yrakovets.data;

public class League {
    Long id;
    String name;
    Boolean top;

    public League() {
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

    public Boolean getTop() {
        return top;
    }

    public void setTop(Boolean top) {
        this.top = top;
    }
}
