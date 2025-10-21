package Screens.components;

import java.awt.Color;

import org.jetbrains.annotations.Nullable;

import Engine.GraphicsHandler;
import Utils.Drawable;
import Utils.TailwindColorScheme;

public class Slot implements Drawable {

    protected int width;
    protected int height;
    protected Color borderColor = TailwindColorScheme.slate700;

    public Slot(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(GraphicsHandler handler, int x, int y) {
        handler.drawFilledRectangleWithBorder(x, y, this.width, this.height, TailwindColorScheme.slate600, this.borderColor, 5);
        // handler.drawRectangle(x, y, this.width, this.height, this.borderColor, 10);
    }

    public void setBorderColor(@Nullable Color borderColor) {
        if (borderColor == null) {
            this.borderColor = TailwindColorScheme.slate700;
            return;
        }
        this.borderColor = borderColor;
    }
    
}
