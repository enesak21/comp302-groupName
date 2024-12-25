package domain.UI;

import domain.entity.Entity;
import java.awt.*;
import java.awt.image.BufferedImage;

/*
    * Abstract class representing the view of an entity
 */
public abstract class EntityView {
    protected Entity entity; // // Model representing the entity
    private BufferedImage upImage, downImage, leftImage, rightImage; // Images for different directions

    public EntityView(Entity entity) {
        this.entity = entity; // Set the model
        loadEntityImages(); // Load images for the entity
    }

    // Abstract method to load entity images, to be implemented by subclasses
    public abstract void loadEntityImages();

    // Abstract method to draw the entity, to be implemented by subclasses
    public abstract void draw(Graphics2D g2);
    }

