package age.of.civilizations2.jakowski.lukasz;

import java.io.Serializable;

class Civilization_Diplomacy_ImproveRelations_GameData implements Serializable {
    private static final long serialVersionUID = 0L;
    protected int iWithCivID = 0;
    protected int iNumOfTurns = 0;

    protected Civilization_Diplomacy_ImproveRelations_GameData(int iWithCivID, int iNumOfTurns, int byCivID) {
        this.iWithCivID = iWithCivID;
        this.iNumOfTurns = iNumOfTurns;
        CFG.game.getCiv(iWithCivID).getCivilization_Diplomacy_GameData().messageBox.addMessage(new Message_Relations_Increase(byCivID));
    }

    protected final boolean action(int iCivA) {
        boolean out = !DiplomacyManager.improveRelation(iCivA, this.iWithCivID) || --this.iNumOfTurns <= 0;
        //no improve relation debuff to diplo points (Game_Action for visual)
        /*if (CFG.game.getCiv(iCivA).getDiplomacyPoints() >= 5) {
            CFG.game.getCiv(iCivA).setDiplomacyPoints(CFG.game.getCiv(iCivA).getDiplomacyPoints() - 5);
        }*/

        return out;
    }
}
