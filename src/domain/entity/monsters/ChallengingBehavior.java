package domain.entity.monsters;

import domain.game.Game;

/**
 * The wizard will make the situation challenging by changing the location of
 * the rune every 3 seconds.
 */
public class ChallengingBehavior implements IWizardBehavior {
    @Override
    public void execute(WizardMonster wizardMonster, Game game) {
        wizardMonster.setTeleportFrequecy(3000);
        wizardMonster.attack(game.getPlayer());
    }
}
