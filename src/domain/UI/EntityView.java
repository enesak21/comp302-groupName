package domain.UI;

import domain.entity.Entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public abstract class EntityView {
    protected Entity entity; // Model
    private BufferedImage upImage, downImage, leftImage, rightImage;

    public EntityView(Entity entity) {
        this.entity = entity;
        loadEntityImages();
    }

    public abstract void loadEntityImages();

    public abstract void draw(Graphics2D g2);
    }

