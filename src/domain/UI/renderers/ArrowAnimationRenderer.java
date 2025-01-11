package domain.UI.renderers;

import domain.UI.ArrowAnimationView;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ArrowAnimationRenderer {

    private final List<ArrowAnimationView> arrowAnimations;

    public ArrowAnimationRenderer() {
        this.arrowAnimations = new ArrayList<>();
    }

    public void addArrowAnimation(ArrowAnimationView animation) {
        arrowAnimations.add(animation);
    }

    public void update() {
        Iterator<ArrowAnimationView> iterator = arrowAnimations.iterator();
        while (iterator.hasNext()) {
            ArrowAnimationView animation = iterator.next();
            animation.update();
            if (animation.isFinished()) {
                iterator.remove();
            }
        }
    }

    public void draw(Graphics2D g2) {
        for (ArrowAnimationView animation : arrowAnimations) {
            animation.draw(g2);
        }
    }
}
