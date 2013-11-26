package com.ns.sudoku;

import android.graphics.Color;

public class Border {
	private int orientation;
    private int width = 4;
    private int color = Color.BLACK;
    private int style;
    
    
    public int getWidth() {
        return width;
    }
    
    public void setWidth(int width) {
        this.width = width;
    }
    
    public int getColor() {
        return color;
    }
    
    public void setColor(int color) {
        this.color = color;
    }
    
    public int getStyle() {
        return style;
    }
    
    public void setStyle(int style) {
        this.style = style;
    }
    
    public int getOrientation() {
        return orientation;
    }
    
    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }
    
    public Border(int Style) {
        this.style = Style;
    }
    
    
}
