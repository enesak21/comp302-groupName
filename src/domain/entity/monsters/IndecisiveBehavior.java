package domain.entity.monsters;

import domain.game.Game;

/**
 * The wizard will stay in the place in which it appears, then disappear after 2
 * seconds without doing anything.
 */
public class IndecisiveBehavior implements IWizardBehavior {

    @Override
    public void execute(WizardMonster wizardMonster, Game game) {
        long currentTime = System.currentTimeMillis();
        if ((currentTime - wizardMonster.getLastTeleportTime() > 2000)) {
            wizardMonster.disappear();
        }
    }
}