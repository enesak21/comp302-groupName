package domain.UI;

import domain.entity.Entity;
import domain.entity.monsters.ArcherMonster;
import domain.entity.monsters.BaseMonster;
import domain.entity.monsters.FighterMonster;
import domain.entity.monsters.WizardMonster;
import domain.entity.playerObjects.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MonsterView extends EntityView{

    private BufferedImage archerMonster, fighterMonster, wizardMonster;

    public MonsterView(Entity entity) {
        super(entity);
        loadEntityImages();
    }

    @Override
    public void loadEntityImages() {

        try {
            archerMonster = ImageIO.read(getClass().getResourceAsStream("/resources/monsters/archer.png"));
            fighterMonster = ImageIO.read(getClass().getResourceAsStream("/resources/monsters/fighter.png"));
            wizardMonster = ImageIO.read(getClass().getResourceAsStream("/resources/monsters/wizard.png"));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load monster images.", e);
        }

    }

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
        }



        g2.drawImage(
                monsterImage,
                baseMonster.getPixelX(),
                baseMonster.getPixelY(),
                baseMonster.getTileSize(),
                baseMonster.getTileSize(),
                null
        );

    }
}
