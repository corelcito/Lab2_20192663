package com.juegoantena;

public class Letter {
    boolean selected;
    String letter;

    public Letter(boolean selected, String letter) {
        this.selected = selected;
        this.letter = letter;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }
}
