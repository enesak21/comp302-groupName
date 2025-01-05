package domain.entity.monsters;

import domain.game.Game;

/**
 * Interface for implementing the Strategy Pattern for the WizardMonster class.
 */
public interface IWizardBehavior {
    void execute(WizardMonster wizardMonster, Game game);
}
