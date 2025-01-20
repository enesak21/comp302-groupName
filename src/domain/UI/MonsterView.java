package domain.UI;

import domain.entity.Entity;
import domain.entity.monsters.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
// MonsterView class is responsible for drawing the monsters on the screen
public class MonsterView extends EntityView{

    private BufferedImage archerMonster, fighterMonster, wizardMonster, timerMonster; // Images for the monsters
    /**
     * Constructor for MonsterView
     * @param entity The entity to be drawn
     */
    public MonsterView(Entity entity) {
        super(entity);
        loadEntityImages();
    }
    /**
     * Loads the images for the monsters
     */
    @Override
    public void loadEntityImages() {

        try {
            archerMonster = ImageIO.read(getClass().getResourceAsStream("/resources/monsters/archer.png"));
            fighterMonster = ImageIO.read(getClass().getResourceAsStream("/resources/monsters/fighter.png"));
            wizardMonster = ImageIO.read(getClass().getResourceAsStream("/resources/monsters/wizard.png"));
            timerMonster = ImageIO.read(getClass().getResourceAsStream("/resources/monsters/timeStealer.png"));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load monster images.", e);
        }

    }
    /**
     * Draws the monsters on the screen
     * @param g2 The graphics object
     */
    @Override
    public void draw(Graphics2D g2) {

        BaseMonster baseMonster = (BaseMonster) entity;

        BufferedImage monsterImage = null;
        if (entity instanceof ArcherMonster) {
            monsterImage = archerMonster;
        } else if (entity instanceof FighterMonster) {
            monsterImage = fighterMonster;
        } else if (entity instanceof WizardMonster) {
            monsterImage = wizardMonster;
        } else if (entity instanceof TimerMonster) {
            monsterImage = timerMonster;
        }

        g2.drawImage( // Draw the monster image
                monsterImage,
                baseMonster.getPixelX(),
                baseMonster.getPixelY(),
                baseMonster.getTileSize(),
                baseMonster.getTileSize(),
                null
        );
    }
}
