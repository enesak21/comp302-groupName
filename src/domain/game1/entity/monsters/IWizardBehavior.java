package domain.game1.entity.monsters;

import domain.game1.game.Game;

/**
 * Interface for implementing the Strategy Pattern for the WizardMonster class.
 */
public interface IWizardBehavior {
    void execute(WizardMonster wizardMonster, Game game);
}
