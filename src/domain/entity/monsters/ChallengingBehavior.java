package domain.entity.monsters;

import domain.entity.playerObjects.Player;

/*
 * Behavior for the WizardMonster when it remaining time is > 70%.
 */
public class ChallengingBehavior implements IWizardBehavior{
    @Override
    public void execute(WizardMonster wizardMonster, Player player) {
        wizardMonster.setTeleportFrequecy(3000);
        wizardMonster.attack(player);
    }
}
