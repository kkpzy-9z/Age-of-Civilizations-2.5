package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.Gdx;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class DynamicEventManager_War implements Serializable {
    private static final long serialVersionUID = 0L;
    protected ArrayList<String> lPlayerWars;
    protected ArrayList<String> lLargeWars;
    protected ArrayList<Integer> lCTAs;
    protected ArrayList<Integer> lCTAsFrom;
    protected ArrayList<Integer> lCaps;
    protected ArrayList<Integer> lCappedCivs;
    private final int SCAN_TOPCIVILIZATIONS = 12;
    protected DynamicEventManager_War() {
        lPlayerWars = new ArrayList<String>();
        lLargeWars = new ArrayList<String>();
        lCTAs = new ArrayList<Integer>();
        lCTAsFrom = new ArrayList<Integer>();
        lCaps = new ArrayList<Integer>();
        lCappedCivs = new ArrayList<Integer>();
    }

    protected void ctaInvokeEvents(int iCivID, int iFromCivID) {
        //If already event from this same civ scenario skip
        if (lCTAsFrom.contains(iFromCivID) || iFromCivID == CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()) return;

        //if one of the top civs in prestige, not player, and has been met
        if (topPrestige().indexOf(iCivID) < SCAN_TOPCIVILIZATIONS && iCivID != CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID() && CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetCivilization(iCivID)) {
            //add to CTAs turn index
            lCTAs.add(iCivID);
            lCTAsFrom.add(iFromCivID);
        }
    }

    protected void capInvokeEvents(int iCivID, int iCapCivID) {
        //If country surrendering being processed, capitulated civ is player, or capitulated civ in civil war continue
        if (lCappedCivs.contains(iCapCivID) || iCapCivID == CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID() || CFG.dynamicEventManager.eventManagerCivilWar.inCivilWar(iCapCivID)) return;

        //if one of the top civs in prestige, and has been met (or is player)
        if (topPrestige().indexOf(iCapCivID) < SCAN_TOPCIVILIZATIONS && (CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID() == iCivID || CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetCivilization(iCivID))) {
            //add to CTAs turn index
            lCaps.add(iCivID);
            lCappedCivs.add(iCapCivID);
        }
    }

    protected void invokeWarEvents() {
        //get list of top civs based on int
        ArrayList<Integer> topPrestigeList = topPrestige();
        ArrayList<Integer> topPrsRandomCivs = new ArrayList<Integer>();
        for (int i = 0; i < SCAN_TOPCIVILIZATIONS; ++i) {
            //if current scan of top 15 ecos is bigger than size of valid prestige civs, break
            if (i == topPrestigeList.size()) {
                break;
            }
            topPrsRandomCivs.add(topPrestigeList.get(i));
        }

        //for each civ player is at war with
        for (int i = 0; i < CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).isAtWarWithCivs.size(); i++) {
            int iWarWith = CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).isAtWarWithCivs.get(i);
            int iWarID = CFG.game.getWarID(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), iWarWith);
            //if warID valid, player is defender, and not in playerwars list
            if (iWarID > -1 && CFG.game.getWar(iWarID).getIsDefender(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()) && !lPlayerWars.contains(CFG.game.getWar(iWarID).WAR_TAG)) {
                Gdx.app.log("Aoc2.5", "Player-Declared War");
                //fire event
                invokePlayerWarEvent(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), iWarWith);
                lPlayerWars.add(CFG.game.getWar(iWarID).WAR_TAG);
            }
        }

        //sort through every war
        for(int i = 0; i < CFG.game.getWarsSize(); ++i) {
            //if already superevent'd war (logged by tag), skip
            if (lLargeWars.contains(CFG.game.getWar(i).WAR_TAG)) continue;
            Gdx.app.log("AoC2.5", "Scanning Wars " + i);

            int aggTop = -1;
            //check if aggressors are top civs
            for (int j = 0; j < CFG.game.getWar(i).getAggressorsSize(); ++j) {
                int nCivID = CFG.game.getWar(i).getAggressorID(j).getCivID();

                //if civ in/going through civil war or player has not met skip
                if (CFG.dynamicEventManager.eventManagerCivilWar.inCivilWar(nCivID) || !CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetCivilization(nCivID)) continue;

                //if civ is top, and there is no currently top civ, then assign civ to them
                if (topPrsRandomCivs.contains(nCivID)) {
                    //if there already is a top civ then check if this one is better, assign respectively
                    if (aggTop < 0 || topPrsRandomCivs.indexOf(nCivID) < topPrsRandomCivs.indexOf(aggTop)) {
                        aggTop = nCivID;
                    }
                }
            }

            //optimize, check if aggressror still null prerequisite to avoid unnecessary computation
            if (aggTop == -1) continue;

            int defTop = -1;
            //check if defenders are top civs
            for (int j = 0; j < CFG.game.getWar(i).getDefendersSize(); ++j) {
                int nCivID = CFG.game.getWar(i).getDefenderID(j).getCivID();

                //if civ in/going through civil war or player has not met skip
                if (CFG.dynamicEventManager.eventManagerCivilWar.inCivilWar(nCivID) || !CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetCivilization(nCivID)) continue;

                //if civ is top, and there is no currently top civ, then assign civ to them
                if (topPrsRandomCivs.contains(nCivID)) {
                    //if there already is a top civ then check if this one is better, assign respectively
                    if (defTop < 0 || topPrsRandomCivs.indexOf(nCivID) < topPrsRandomCivs.indexOf(defTop)) {
                        defTop = nCivID;
                    }
                }
            }

            //if defenders not null (given aggressors not null from above safecheck) fire event
            if (defTop != -1) {
                //initiate superevent, add to event'd war tag to list
                Gdx.app.log("Aoc2.5", "Firing War");
                invokeWarEvent(aggTop, defTop);
                lLargeWars.add(CFG.game.getWar(i).WAR_TAG);
                //break, no more than one superevent per turn
                break;
            }
        }
        topPrestigeList.clear();
        topPrsRandomCivs.clear();

        for (int i = 0; i < lCaps.size(); ++i) {
            invokeCapEvent(lCaps.get(i), lCappedCivs.get(i));
        }
        lCaps.clear();
        lCappedCivs.clear();

        for (int i = 0; i < lCTAs.size(); ++i) {
            invokeCTAEvent(lCTAs.get(i), lCTAsFrom.get(i));
        }
        lCTAs.clear();
        lCTAsFrom.clear();
    }

    protected void invokeWarEvent(int aggTop, int defTop) {
        String civRef = CFG.game.getCiv(aggTop).getCivName() + " - " + CFG.game.getCiv(defTop).getCivName();
        Event_GameData eventGameData = new Event_GameData();

        Event_Outcome_DeclareWar eventOutcomeDeclareWar = new Event_Outcome_DeclareWar();
        eventOutcomeDeclareWar.iCivID = aggTop;
        eventOutcomeDeclareWar.iCivID2 = defTop;

        //List<MenuElement_Hover_v2_Element_Type> tData = new ArrayList();
        //tData.add(new MenuElement_Hover_v2_Element_Type_Flag(nCivID));
        //tData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("WeMustFight"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
        //eventOutcomeDeclareWar.setSecondaryHoverText(tData);

        Event_Conditions_CivExist eventConditionsCivExist = new Event_Conditions_CivExist();
        eventConditionsCivExist.iCivID = defTop;

        eventGameData.setup_Event(
                CFG.langManager.get(String.format("EventWarName_" + (int)(Math.ceil(Math.random() * (3)))), civRef),
                "EventWar_" + (int)(Math.ceil(Math.random() * (5))),
                "eventWar",
                CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()
        );
        eventGameData.setup_EventPopUp(
                true,
                String.format("EventWarDesc_" + (int)(Math.ceil(Math.random() * (3))))
        );
        eventGameData.setup_EventDecisions(
                String.format("EventWarDecision_" + (int)(Math.ceil(Math.random() * (3)))),
                eventOutcomeDeclareWar
        );
        eventGameData.setup_EventTriggers(
                eventConditionsCivExist
        );
        eventGameData.superEvent = true;
        eventGameData.setEventSound("eventWar.mp3");

        CFG.dynamicEventManager.addEventIndex(eventGameData);
    }

    protected void invokePlayerWarEvent(int plyID, int aggID) {
        String civRef = CFG.game.getCiv(aggID).getCivName() + " - " + CFG.game.getCiv(plyID).getCivName();
        Event_GameData eventGameData = new Event_GameData();

        Event_Outcome_DeclareWar eventOutcomeDeclareWar = new Event_Outcome_DeclareWar();
        eventOutcomeDeclareWar.iCivID = plyID;
        eventOutcomeDeclareWar.iCivID2 = aggID;

        //build tooltip for button
        List<MenuElement_Hover_v2_Element_Type> tData = new ArrayList();
        tData.add(new MenuElement_Hover_v2_Element_Type_Flag(plyID));
        tData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("ForOurPeople"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
        eventOutcomeDeclareWar.setSecondaryHoverText(tData);

        Event_Conditions_CivExist eventConditionsCivExist = new Event_Conditions_CivExist();
        eventConditionsCivExist.iCivID = plyID;

        eventGameData.setup_Event(
                //use normal wars name
                //use CTA icons
                CFG.langManager.get(String.format("EventWarName_" + (int)(Math.ceil(Math.random() * (3)))), civRef),
                "EventCTA_" + (int)(Math.ceil(Math.random() * (3))),
                "eventPlayerWar",
                CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()
        );
        eventGameData.setup_EventPopUp(
                true,
                CFG.langManager.get(String.format("EventPlayerWarDesc_" + (int)(Math.ceil(Math.random() * (3)))), CFG.game.getCiv(aggID).getCivName())
        );
        eventGameData.setup_EventDecisions(
                String.format("EventPlayerWarDecision_" + (int)(Math.ceil(Math.random() * (3)))),
                eventOutcomeDeclareWar
        );
        eventGameData.setup_EventTriggers(
                eventConditionsCivExist
        );

        CFG.dynamicEventManager.addEventIndex(eventGameData);
    }

    protected void invokeCTAEvent(int iCivID, int iFromCivID) {
        String civRef = CFG.game.getCiv(iCivID).getCivName();
        String fromCivRef = CFG.game.getCiv(iFromCivID).getCivName();

        Event_GameData eventGameData = new Event_GameData();
        Event_Outcome_UpdateHappinessOfCiv eventOutcomeUpdateHappinessOfCiv = new Event_Outcome_UpdateHappinessOfCiv();
        eventOutcomeUpdateHappinessOfCiv.iCivID = iFromCivID;
        eventOutcomeUpdateHappinessOfCiv.iValue = 5;

        Event_Conditions_CivExist eventConditionsCivExist = new Event_Conditions_CivExist();
        eventConditionsCivExist.iCivID = iCivID;

        eventGameData.setup_Event(
                CFG.langManager.get(String.format("EventCTAName_" + (int)(Math.ceil(Math.random() * (3)))), fromCivRef, civRef),
                "EventCTA_" + (int)(Math.ceil(Math.random() * (3))),
                "eventCTA",
                CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()
        );
        eventGameData.setup_EventPopUp(
                true,
                CFG.langManager.get(String.format("EventCTADesc_" + (int)(Math.ceil(Math.random() * (3)))), fromCivRef, civRef)
        );
        eventGameData.setup_EventDecisions(
                CFG.langManager.get(String.format("EventCTADecision_" + (int)(Math.ceil(Math.random() * (3))))),
                eventOutcomeUpdateHappinessOfCiv
        );
        eventGameData.setup_EventTriggers(
                eventConditionsCivExist
        );

        CFG.dynamicEventManager.addEventIndex(eventGameData);
    }

    protected void invokeCapEvent(int victorCiv, int occupyCiv) {
        String civRef = CFG.game.getCiv(occupyCiv).getCivName();
        Event_GameData eventGameData = new Event_GameData();
        Event_Outcome_UpdateHappinessOfCiv eventOutcomeUpdateHappinessOfCiv = new Event_Outcome_UpdateHappinessOfCiv();
        eventOutcomeUpdateHappinessOfCiv.iCivID = victorCiv;
        eventOutcomeUpdateHappinessOfCiv.iValue = 15;

        Event_Conditions_CivExist eventConditionsCivExist = new Event_Conditions_CivExist();
        eventConditionsCivExist.iCivID = victorCiv;

        if (CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID() == victorCiv) {
            //SELF
            eventGameData.setup_Event(
                    CFG.langManager.get(String.format("EventCapNamePlayer_" + (int)(Math.ceil(Math.random() * (3)))), civRef),
                    "EventCap_" + (int)(Math.ceil(Math.random() * (3))),
                    "eventCap",
                    CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()
            );
            eventGameData.setup_EventPopUp(
                    true,
                    CFG.langManager.get(String.format("EventCapDescPlayer_" + (int)(Math.ceil(Math.random() * (3)))), civRef)
            );
            eventGameData.setup_EventDecisions(
                    String.format("EventCapDecisionPlayer_" + (int)(Math.ceil(Math.random() * (3)))),
                    eventOutcomeUpdateHappinessOfCiv
            );
            eventGameData.setup_EventTriggers(
                    eventConditionsCivExist
            );
        } else {
            //OTHER
            eventGameData.setup_Event(
                    CFG.langManager.get(String.format("EventCapName_" + (int)(Math.ceil(Math.random() * (3)))), civRef),
                    "EventCap_" + (int)(Math.ceil(Math.random() * (3))),
                    "eventCap",
                    CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()
            );
            eventGameData.setup_EventPopUp(
                    true,
                    CFG.langManager.get(String.format("EventCapDesc_" + (int)(Math.ceil(Math.random() * (3)))), civRef)
            );
            eventGameData.setup_EventDecisions(
                    String.format("EventCapDecision_" + (int)(Math.ceil(Math.random() * (3)))),
                    eventOutcomeUpdateHappinessOfCiv
            );
            eventGameData.setup_EventTriggers(
                    eventConditionsCivExist
            );
        }

        CFG.dynamicEventManager.addEventIndex(eventGameData);
    }

    protected ArrayList<Integer> topPrestige() {
        ArrayList<Integer> tSorted = new ArrayList<Integer>();
        ArrayList<Integer> tempIDS = new ArrayList<Integer>();
        ArrayList<Integer> tempScore = new ArrayList<Integer>();
        for (int j = 1; j < CFG.game.getCivsSize(); ++j) {
            tempIDS.add(CFG.game.getSortedCivsAZ(j - 1));
            tempScore.add(CFG.gameAction.buildRank_Score_Prestige(CFG.game.getSortedCivsAZ(j - 1)));
        }
        int tAddID = 0;
        while (tempIDS.size() > 0) {
            tAddID = 0;
            for (int k = 1; k < tempIDS.size(); ++k) {
                if (tempScore.get(tAddID) < tempScore.get(k)) {
                    tAddID = k;
                }
            }
            tSorted.add(tempIDS.get(tAddID));
            tempIDS.remove(tAddID);
            tempScore.remove(tAddID);
        }
        return tSorted;
    }
}
