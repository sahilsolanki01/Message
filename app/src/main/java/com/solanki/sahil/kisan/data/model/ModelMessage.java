package com.solanki.sahil.kisan.data.model;

public class ModelMessage implements Comparable<ModelMessage> {
    private String body, number, date;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int compareTo(ModelMessage u) {
        if (getDate() == null || u.getDate() == null) {
            return 0;
        }
        return getDate().compareTo(u.getDate());
    }
}
