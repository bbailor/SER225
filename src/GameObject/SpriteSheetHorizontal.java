package GameObject;

import java.awt.image.BufferedImage;

public class SpriteSheetHorizontal extends SpriteSheet {
    public SpriteSheetHorizontal(BufferedImage image, int spriteWidth, int spriteHeight) {
        super(image, spriteWidth, spriteHeight);
        this.rowLength = 1; // single row
        this.columnLength = image.getWidth() / spriteWidth;
    }

    @Override
    public BufferedImage getSprite(int row, int col) {
        return image.getSubimage(col * spriteWidth, row * spriteHeight, spriteWidth, spriteHeight);
    }
}
