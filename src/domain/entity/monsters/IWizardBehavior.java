package domain.entity.monsters;

import domain.entity.playerObjects.Player;

/**
 * Interface for implementing the Strategy Pattern for the WizardMonster class.
 */
public interface IWizardBehavior {
    void execute(WizardMonster wizardMonster, Player player);
}
