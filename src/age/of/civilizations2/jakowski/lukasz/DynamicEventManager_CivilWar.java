package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.Gdx;

import java.io.Serializable;
import java.util.*;

class DynamicEventManager_CivilWar implements Serializable {
    private static final long serialVersionUID = 0L;
    protected ArrayList<Integer> lChangingCivs;
    protected ArrayList<Integer> lChangingCivsOldGov;
    protected ArrayList<Integer> lCivilWarCivs;
    protected ArrayList<Integer> lCivilWarCivsOldGov;
    protected ArrayList<Integer> lCivilWarAggressors;
    protected ArrayList<Integer> lCivilWarDefenders;
    protected static float MAX_ARMY_DISSENTERS = 0.75F;
    protected static float MIN_ARMY_DISSENTERS = 0.35F;
    protected static float MAX_ARMY_DISSENTERS_BUFFER = 0.35F;
    protected static float MIN_ARMY_DISSENTERS_BUFFER = 0.15F;
    protected static float MAX_TREASURY_TRANSFER = 0.75F;
    protected static float MIN_TREASURY_TRANSFER = 0.35F;
    protected static float MAX_PERC_PROVINCES_TO_TAKE = 0.50F;
    protected static int MIN_PROVINCES_WAR = 5;
    protected DynamicEventManager_CivilWar() {
        lChangingCivs = new ArrayList<>();
        lChangingCivsOldGov = new ArrayList<>();
        lCivilWarCivs = new ArrayList<>();
        lCivilWarCivsOldGov = new ArrayList<>();
        lCivilWarAggressors = new ArrayList<>();
        lCivilWarDefenders = new ArrayList<>();
    }

    protected boolean inCivilWar(int nCivID) {
        return (lChangingCivs.contains(nCivID) || lCivilWarCivs.contains(nCivID) || lCivilWarAggressors.contains(nCivID) || lCivilWarDefenders.contains(nCivID));
    }

    protected void changesGovernment(int nCivID) {
        //check for pop. support algorithm (or add that to invokecivWarChoice if putting event for differential government)
        lChangingCivs.add(nCivID);
        lChangingCivsOldGov.add(CFG.game.getCiv(nCivID).getIdeologyID());
    }

    protected void invokeCivilWarEvents() {
        //sort through civs actively in civil war
        ArrayList<Integer> ltoRemove = new ArrayList<>();
        for (int i = 0; i < lCivilWarDefenders.size(); i++) {
            //if not at war add to queried list to remove
            if (CFG.game.getCivTruce(lCivilWarDefenders.get(i), lCivilWarAggressors.get(i)) > 0) {
                //invoke event
                invokePostCivilWar(lCivilWarDefenders.get(i), lCivilWarAggressors.get(i));
                ltoRemove.add(i);
            };
        }
        //remove truced civs safely from queried list
        for (int i: ltoRemove) {
            lCivilWarDefenders.remove(i);
            lCivilWarAggressors.remove(i);
        }
        ltoRemove.clear();

        //sort through gov-changed civs after throwing event, check if they switched govtype back
        for (int i = 0; i < lCivilWarCivs.size(); i++) {
            //safechecks, if gov switched back or if state has less than min provinces don't do anything
            if (CFG.game.getCiv(lCivilWarCivs.get(i)).getIdeologyID() == lCivilWarCivsOldGov.get(i) || CFG.game.getCiv(lCivilWarCivs.get(i)).getNumOfProvinces() < MIN_PROVINCES_WAR) continue;
            invokeCivilWar(lCivilWarCivs.get(i), lCivilWarCivsOldGov.get(i));
        }
        lCivilWarCivs.clear();
        lCivilWarCivsOldGov.clear();

        //sort through gov-changed civs
        for (int i = 0; i < lChangingCivs.size(); i++) {
            //if city state skip
            if (Objects.equals(CFG.ideologiesManager.getIdeology(lChangingCivsOldGov.get(i)).AI_TYPE, "CITYSTATE")) continue;

            //if changing btwn two of same ai types acknowledge change, else do precivilwar
            if (Objects.equals(CFG.ideologiesManager.getIdeology(CFG.game.getCiv(lChangingCivs.get(i)).getIdeologyID()).AI_TYPE, CFG.ideologiesManager.getIdeology(lChangingCivsOldGov.get(i)).AI_TYPE)) {
                invokeAklgIdeologyChange(lChangingCivs.get(i), lChangingCivsOldGov.get(i));
            } else {
                invokeCivilWarChoice(lChangingCivs.get(i), lChangingCivsOldGov.get(i));
            }
        }
        lChangingCivs.clear();
        lChangingCivsOldGov.clear();
    }

    protected void invokeAklgIdeologyChange(int nCivID, int oldGovType) {
        String ideRef = CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).getName();
        //if AI_TYPE is default, rename to "Despotic", if not then just lowercase it, then format
        String ideRefOld = (Objects.equals(CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).AI_TYPE, "DEFAULT")) ? "DESPOTISM" : CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).AI_TYPE;
        ideRefOld = ideRefOld.toLowerCase();

        Event_GameData eventGameData = new Event_GameData();

        Event_Outcome_ChangeIdeology eventOutcomeChangeIdeology = new Event_Outcome_ChangeIdeology();
        eventOutcomeChangeIdeology.iCivID = nCivID;
        eventOutcomeChangeIdeology.iValue = CFG.game.getCiv(nCivID).getIdeologyID();

        Event_Conditions_CivExist eventConditionsCivExist = new Event_Conditions_CivExist();
        eventConditionsCivExist.iCivID = nCivID;

        eventGameData.setup_Event(
                CFG.langManager.get(String.format("EventAklgIdeologyName_" + (int)(Math.ceil(Math.random() * (3)))), ideRef),
                "EventAklgIdeology_" + (int)(Math.ceil(Math.random() * (3))),
                "eventAklgIdeology",
                nCivID
        );
        eventGameData.setup_EventPopUp(
                true,
                CFG.langManager.get(String.format("EventAklgIdeologyDesc_" + (int)(Math.ceil(Math.random() * (3)))), ideRefOld)
        );
        eventGameData.setup_EventDecisions(
                String.format("EventAklgIdeologyDecision_" + (int)(Math.ceil(Math.random() * (3)))),
                eventOutcomeChangeIdeology
        );
        eventGameData.setup_EventTriggers(
                eventConditionsCivExist
        );

        CFG.dynamicEventManager.addEventIndex(eventGameData);
    }

    protected void invokeCivilWarChoice(int nCivID, int oldGovType) {
        String ideRef = CFG.ideologiesManager.getIdeology(CFG.game.getCiv(nCivID).getIdeologyID()).getName();
        Event_GameData eventGameData = new Event_GameData();

        Event_Outcome_ChangeIdeology eventOutcomeChangeIdeology = new Event_Outcome_ChangeIdeology();
        eventOutcomeChangeIdeology.iCivID = nCivID;
        eventOutcomeChangeIdeology.iValue = oldGovType;

        Event_Outcome_UpdateHappinessOfCiv eventOutcomeUpdateHappinessOfCiv = new Event_Outcome_UpdateHappinessOfCiv();
        eventOutcomeUpdateHappinessOfCiv.iCivID = nCivID;
        eventOutcomeUpdateHappinessOfCiv.iValue = -50;

        //build tooltip for pre-civilwar
        List<MenuElement_Hover_v2_Element_Type> tData = new ArrayList();
        tData.add(new MenuElement_Hover_v2_Element_Type_Flag(nCivID));
        tData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("PotentialConsequences"), CFG.COLOR_TEXT_MODIFIER_NEGATIVE_HOVER));
        eventOutcomeUpdateHappinessOfCiv.setSecondaryHoverText(tData);

        Event_Conditions_CivExist eventConditionsCivExist = new Event_Conditions_CivExist();
        eventConditionsCivExist.iCivID = nCivID;

        eventGameData.setup_Event(
                CFG.langManager.get(String.format("EventPreCivilWarName_" + (int)(Math.ceil(Math.random() * (3)))), ideRef),
                "EventPreCivilWar_" + (int)(Math.ceil(Math.random() * (5))),
                "eventPreCivilWar",
                nCivID
        );
        eventGameData.setup_EventPopUp(
                true,
                CFG.langManager.get(String.format("EventPreCivilWarDesc_" + (int)(Math.ceil(Math.random() * (3)))), ideRef)
        );
        eventGameData.setup_EventDecisions(
                (String.format("EventPreCivilWarDecisionA_" + (int)(Math.ceil(Math.random() * (3))))),
                eventOutcomeChangeIdeology
        );
        eventGameData.setup_EventDecisions(
                String.format("EventPreCivilWarDecisionB_" + (int)(Math.ceil(Math.random() * (3)))),
                eventOutcomeUpdateHappinessOfCiv
        );
        eventGameData.setup_EventTriggers(
                eventConditionsCivExist
        );

        lCivilWarCivs.add(nCivID);
        lCivilWarCivsOldGov.add(oldGovType);
        CFG.dynamicEventManager.addEventIndex(eventGameData);
    }
    
    protected void invokeCivilWar(int nCivID, int oldGovType) {
        try {
            //sort through every province of civ splitting
            int maxProvinces = (int) (CFG.game.getCiv(nCivID).getNumOfProvinces() * MAX_PERC_PROVINCES_TO_TAKE);
            ArrayList<Integer> tempProvinces = new ArrayList<>(CFG.game.getCiv(nCivID).lProvincesWithLowStability);
            for (int i = 0; i < CFG.game.getCiv(nCivID).getNumOfProvinces(); i++) {
                //if capital or in list already, skip
                if (CFG.game.getCiv(nCivID).getProvinceID(i) == CFG.game.getCiv(nCivID).getCapitalProvinceID() || tempProvinces.contains(CFG.game.getCiv(nCivID).getProvinceID(i))) continue;

                //randomly assign to new civ
                if ((int)Math.round(Math.random()) == 1) {
                    tempProvinces.add(CFG.game.getCiv(nCivID).getProvinceID(i));
                }

                if (tempProvinces.size() > maxProvinces) break;
            }

            //sort through every army of civilization getting split
            int iSwitchingArmy = 0;
            for (int i = 0; i < CFG.game.getCiv(nCivID).lArmiesPosition.size(); i++) {
                //get random num between 75% of army on province and 0 to remove and add to opposite civil war side
                int nToRemove = (int)CFG.dynamicEventManager.randomF((CFG.game.getProvince(CFG.game.getCiv(nCivID).lArmiesPosition.get(i)).getArmyCivID(nCivID) * .75f), 0.0f, true);
                CFG.game.getProvince(CFG.game.getCiv(nCivID).lArmiesPosition.get(i)).updateArmy(nCivID, CFG.game.getProvince(CFG.game.getCiv(nCivID).lArmiesPosition.get(i)).getArmyCivID(nCivID) - nToRemove);

                iSwitchingArmy += nToRemove;
            }
            for (int i = 0; i < CFG.game.getCiv(nCivID).getArmyInAnotherProvinceSize(); i++) {
                int nProvinceID = CFG.game.getCiv(nCivID).getArmyInAnotherProvince(i);
                //remove if already removed from
                if (CFG.game.getCiv(nCivID).lArmiesPosition.contains(nProvinceID)) continue;

                //get random num between 75% and 25% of army on province and 0 to remove and add to opposite civil war side
                int nToRemove = (int)CFG.dynamicEventManager.randomF((CFG.game.getProvince(nProvinceID).getArmyCivID(nCivID) * MAX_ARMY_DISSENTERS), (CFG.game.getProvince(nProvinceID).getArmyCivID(nCivID) * MIN_ARMY_DISSENTERS), true);

                CFG.game.getProvince(nProvinceID).updateArmy(nCivID, CFG.game.getProvince(nProvinceID).getArmyCivID(nCivID) - nToRemove);
                iSwitchingArmy += nToRemove;
            }
            CFG.game.getCiv(nCivID).setNumOfUnits(CFG.game.getCiv(nCivID).getNumOfUnits() - iSwitchingArmy);

            int iNewCivID = CFG.game.releaseAVasssal(CFG.ideologiesManager.getRealTag(CFG.game.getCiv(nCivID).getCivTag()) + CFG.ideologiesManager.getIdeology(oldGovType).getExtraTag(), tempProvinces, tempProvinces.get(0), nCivID, false);
            CFG.game.moveCapitalToTheLargestCity(iNewCivID);
            if (iNewCivID >= 0) {
                CFG.menuManager.rebuildMenu_InGame_VassalReleased(iNewCivID);
            }
            CFG.game.getCiv(iNewCivID).setPuppetOfCivID(iNewCivID);

            //add
            iSwitchingArmy += CFG.dynamicEventManager.randomF(CFG.game.getCiv(nCivID).getNumOfUnits() * MAX_ARMY_DISSENTERS_BUFFER, CFG.game.getCiv(nCivID).getNumOfUnits() * MIN_ARMY_DISSENTERS_BUFFER, true);
            //sort through dissenting provinces, setup
            for (int i = 0; i < tempProvinces.size(); i++) {
                //remove player and allies army
                for (int j = 0; j < CFG.game.getProvince(tempProvinces.get(i)).getCivsSize(); ++j) {
                    int civsLostAccess = (CFG.game.getProvince(tempProvinces.get(i)).getCivID(j));
                    //if (CFG.game.getCiv(civsLostAccess).getPuppetOfCivID() != iNewCivID && CFG.game.getCiv(iNewCivID).getPuppetOfCivID() != civsLostAccess && (CFG.game.getCiv(civsLostAccess).getAllianceID() <= 0 || CFG.game.getCiv(civsLostAccess).getAllianceID() != CFG.game.getCiv(iNewCivID).getAllianceID()) && CFG.game.getMilitaryAccess(civsLostAccess, iNewCivID) <= 0) {
                    if (iNewCivID != civsLostAccess) {
                        CFG.gameAction.accessLost_MoveArmyToClosetsProvince(civsLostAccess, tempProvinces.get(i));
                    }
                }

                //add switched army equally across dissenting civ's provinces, 1/2 chance to have army
                if ((int)Math.round(Math.random()) == 1) {
                    int nToAdd = (int)Math.floor(CFG.dynamicEventManager.randomF((float)(iSwitchingArmy / (tempProvinces.size() - i)), 0.0f, false));
                    CFG.game.getProvince(tempProvinces.get(i)).updateArmy(iNewCivID, CFG.game.getProvince(tempProvinces.get(i)).getArmyCivID(iNewCivID) + nToAdd);
                    iSwitchingArmy -= nToAdd;
                };

                //establish cores, stability
                CFG.game.getProvince(tempProvinces.get(i)).setHappiness(0.9F);
                CFG.game.getProvince(tempProvinces.get(i)).setRevolutionaryRisk(0.0F);
                CFG.game.getProvince(tempProvinces.get(i)).getCore().addNewCore(iNewCivID, CFG.PLAYER_TURNID);
                CFG.game.getProvince(tempProvinces.get(i)).getCore().increaseOwnership(iNewCivID, tempProvinces.get(i));

                for (int j2 = CFG.game.getProvince(tempProvinces.get(i)).getPopulationData().getNationalitiesSize() - 1; j2 >= 0; j2--) {
                    //if original government, turn into dissenters population
                    if (CFG.game.getProvince(tempProvinces.get(i)).getPopulationData().getCivID(j2) == nCivID) {
                        CFG.game.getProvince(tempProvinces.get(i)).getPopulationData().setPopulationOfCivID(iNewCivID, CFG.game.getProvince(tempProvinces.get(i)).getPopulationData().getPopulationOfCivID(tempProvinces.get(i)) + CFG.game.getProvince(tempProvinces.get(i)).getPopulationData().getPopulationID(j2));
                        CFG.game.getProvince(tempProvinces.get(i)).getPopulationData().setPopulationOfCivID(CFG.game.getProvince(tempProvinces.get(i)).getPopulationData().getCivID(j2), 0);
                        CFG.game.getProvince(tempProvinces.get(i)).getPopulationData().updatePopulationOfProvince();

                        CFG.menuManager.updateInGame_ProvinceInfoGraph(tempProvinces.get(i));
                    }
                }
            }
            if (iSwitchingArmy > 0) {
                CFG.game.getProvince(CFG.game.getCiv(iNewCivID).getCapitalProvinceID()).updateArmy(iNewCivID, CFG.game.getProvince(CFG.game.getCiv(iNewCivID).getCapitalProvinceID()).getArmyCivID(iNewCivID) + iSwitchingArmy);
                iSwitchingArmy = 0;
            }

            //give dissenting up to half of civ's economy
            long iSwitchingTreasury = Math.min((long)CFG.dynamicEventManager.randomF((float)CFG.game.getCiv(nCivID).getMoney() * MAX_TREASURY_TRANSFER, (float)CFG.game.getCiv(nCivID).getMoney() * MIN_TREASURY_TRANSFER, true), CFG.game.getCiv(nCivID).getMoney());
            CFG.game.getCiv(nCivID).setMoney(CFG.game.getCiv(nCivID).getMoney() - iSwitchingTreasury);
            CFG.game.getCiv(iNewCivID).setMoney(CFG.game.getCiv(iNewCivID).getMoney() + iSwitchingTreasury);

            CFG.game.getCiv(iNewCivID).setWarWeariness(0.0F);
            CFG.game.getCiv(iNewCivID).setStability(1.0F);
            CFG.game.getCiv(iNewCivID).setHappiness(85);

            //set at war
            CFG.game.declareWar(iNewCivID, nCivID, true);

            CFG.game.war_CheckDiplomacy(iNewCivID, nCivID);
            CFG.game.war_CheckDiplomacy(nCivID, iNewCivID);
            CFG.game.setCivRelation_OfCivB(nCivID, iNewCivID, -100.0F);
            CFG.game.setCivRelation_OfCivB(iNewCivID, nCivID, -100.0F);
            CFG.game.addWarData(iNewCivID, nCivID);

            CFG.game.getWar(CFG.game.getWarID(iNewCivID, nCivID)).canEnd = false;
            //fire event
            invokeTrueCivilWarEvent(nCivID, iNewCivID);
            if ((int)(Math.ceil(Math.random() * (2))) == 1) {
                CFG.dynamicEventManager.eventManagerLeader.invokeCivilWarLeaderChange(nCivID, iNewCivID);
            } else {
                //try 5 times to randomize leader and find one that is not original
                DynamicEventManager_Leader.buildUniqueLeader(iNewCivID, nCivID);
            }

            //reload for ui
            CFG.menuManager.rebuildMenu_InGame_War(nCivID, CFG.game.getCiv(iNewCivID).getCivID());
            CFG.menuManager.setVisible_Menu_InGame_CurrentWars(true);
            if (CFG.viewsManager.getActiveViewID() == ViewsManager.VIEW_DIPLOMACY_MODE) {
                CFG.viewsManager.disableAllViews();
            }
            if (CFG.menuManager.getVisibleInGame_WarDetails()) {
                CFG.menuManager.rebuildInGame_WarDetails();
            }
            if (CFG.menuManager.getVisibleInGame_WarPreparations()) {
                CFG.menuManager.setVisibleInGame_WarPreparations(false);
            }

        } catch (IndexOutOfBoundsException ex) {
            CFG.exceptionStack(ex);
        }
    }

    protected void invokeTrueCivilWarEvent(int nCivID, int iNewCivID) {
        Event_GameData eventGameData = new Event_GameData();

        Event_Outcome_DeclareWar eventOutcomeDeclareWar = new Event_Outcome_DeclareWar();
        eventOutcomeDeclareWar.iCivID = nCivID;
        eventOutcomeDeclareWar.iCivID2 = iNewCivID;

        //build tooltip for button
        List<MenuElement_Hover_v2_Element_Type> tData = new ArrayList();
        tData.add(new MenuElement_Hover_v2_Element_Type_Flag(nCivID));
        tData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("WeMustFight"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
        eventOutcomeDeclareWar.setSecondaryHoverText(tData);

        Event_Conditions_CivExist eventConditionsCivExist = new Event_Conditions_CivExist();
        eventConditionsCivExist.iCivID = nCivID;

        eventGameData.setup_Event(
                CFG.langManager.get(String.format("EventCivilWarName_" + (int)(Math.ceil(Math.random() * (3)))), CFG.ideologiesManager.getIdeology(CFG.game.getCiv(iNewCivID).getIdeologyID()).getName()),
                "EventCivilWar_" + (int)(Math.ceil(Math.random() * (5))),
                "eventCivilWar",
                nCivID
        );
        eventGameData.setup_EventPopUp(
                true,
                String.format("EventCivilWarDesc_" + (int)(Math.ceil(Math.random() * (3))))
        );
        eventGameData.setup_EventDecisions(
                String.format("EventCivilWarDecision_" + (int)(Math.ceil(Math.random() * (3)))),
                eventOutcomeDeclareWar
        );
        eventGameData.setup_EventTriggers(
                eventConditionsCivExist
        );
        eventGameData.setEventSound("eventCivilWar.mp3");
        eventGameData.superEvent = true;

        lCivilWarDefenders.add(nCivID);
        lCivilWarAggressors.add(iNewCivID);
        CFG.dynamicEventManager.addEventIndex(eventGameData);
    }

    protected void invokePostCivilWar(int defID, int aggID) {
        //auto-assign victor to aggressive, but if def has more score than assign victor to def
        int victorID = aggID;
        int loserID = defID;

        //build scores, check
        CFG.gameAction.buildRank_Score(defID);
        CFG.gameAction.buildRank_Score(aggID);
        if (CFG.game.getCiv(defID).getRankScore()  > CFG.game.getCiv(aggID).getRankScore()) {
            victorID = defID;
            loserID = aggID;
        }

        Event_GameData eventGameData = new Event_GameData();

        //outcomes in list
        ArrayList<Event_Outcome> outcomesList = new ArrayList<>();

        Event_Outcome_UpdateHappinessOfCiv eventOutcomeUpdateHappinessOfCiv = new Event_Outcome_UpdateHappinessOfCiv();
        eventOutcomeUpdateHappinessOfCiv.iCivID = victorID;
        eventOutcomeUpdateHappinessOfCiv.iValue = 15;
        outcomesList.add(eventOutcomeUpdateHappinessOfCiv);

        Event_Outcome_ChangeOwner eventOutcomeChangeOwner = new Event_Outcome_ChangeOwner();
        eventOutcomeChangeOwner.iCivID = victorID;
        eventOutcomeChangeOwner.iCivID_ControlledBy = loserID;
        //add loser provinces to be annexed upon outcome
        for (int i = 0; i < CFG.game.getCiv(loserID).getNumOfProvinces(); i++) {
            //if capital remove icon
            if (CFG.game.getCiv(loserID).getCapitalProvinceID() == CFG.game.getCiv(loserID).getProvinceID(i)) {
                CFG.game.getProvince(CFG.game.getCiv(loserID).getProvinceID(i)).removeCapitalCityIcon();
            }

            eventOutcomeChangeOwner.lProvinces.add(CFG.game.getCiv(loserID).getProvinceID(i));
        }
        outcomesList.add(eventOutcomeChangeOwner);

        //build tooltip for victory
        List<MenuElement_Hover_v2_Element_Type> tData = new ArrayList();
        tData.add(new MenuElement_Hover_v2_Element_Type_Flag(victorID));
        tData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Victory") + "!", CFG.COLOR_TEXT_MODIFIER_POSITIVE));
        eventOutcomeChangeOwner.setSecondaryHoverText(tData);

        Event_Conditions_CivExist eventConditionsCivExist = new Event_Conditions_CivExist();
        eventConditionsCivExist.iCivID = victorID;

        eventGameData.setup_Event(
                CFG.langManager.get(String.format("EventPostCivilWarName_" + (int)(Math.ceil(Math.random() * (3)))), CFG.ideologiesManager.getIdeology(CFG.game.getCiv(victorID).getIdeologyID()).getName()),
                "EventPostCivilWar_" + (int)(Math.ceil(Math.random() * (5))),
                "eventPostCivilWar",
                victorID
        );
        eventGameData.setup_EventPopUp(
                true,
                CFG.langManager.get(String.format("EventPostCivilWarDesc_" + (int)(Math.ceil(Math.random() * (3)))), CFG.ideologiesManager.getIdeology(CFG.game.getCiv(loserID).getIdeologyID()).getName())
        );
        eventGameData.setup_EventDecisions_List(
                (String.format("EventPostCivilWarDecision_" + (int)(Math.ceil(Math.random() * (3))))),
                outcomesList
        );
        eventGameData.setup_EventTriggers(
                eventConditionsCivExist
        );

        CFG.dynamicEventManager.addEventIndex(eventGameData);
    }
}
