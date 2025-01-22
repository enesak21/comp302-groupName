package domain.game1.entity.monsters;

import domain.game1.game.Game;

/**
 * The wizard will change the location of the player to a random empty location
 * once and disappear.
 */
public class CloseToLosingBehavior implements IWizardBehavior {

    @Override
    public void execute(WizardMonster wizardMonster, Game game) {
        game.teleportPlayer();
        wizardMonster.disappear();
    }
}
