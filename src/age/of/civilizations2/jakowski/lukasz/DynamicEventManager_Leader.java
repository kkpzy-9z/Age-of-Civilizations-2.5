package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxRuntimeException;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DynamicEventManager_Leader implements Serializable {
    private static final long serialVersionUID = 0L;
    private int civpl = 0;
    private int currentTurn = -1;
    protected Event_GameData event_lastturn;
    final static int MAXIMUM_TIME_OF_RULE = 50;

    DynamicEventManager_Leader() {

    }

    protected void invokeLeaderEvents() {
        civpl = CFG.getActiveCivInfo();
        //this.invokeRandomLeaderEvent();
    }

    protected static boolean isValidLeader() {
        return (
            CFG.leader_GameData.getLeaderOfCiv() != null && //non-null leader
            CFG.leader_GameData.getLeaderOfCiv().getYear() <= Game_Calendar.currentYear && //leader in office before time
            CFG.leader_GameData.getLeaderOfCiv().getYear() >= Game_Calendar.currentYear - MAXIMUM_TIME_OF_RULE //leader in office not too long ago
        );
    }

    protected static boolean isValidLeaderRandom() {
        return (
            CFG.leader_Random_GameData.getLeaderOfCiv() != null && //non-null leader
            CFG.leader_Random_GameData.getLeaderOfCiv().getYear() <= Game_Calendar.currentYear && //leader in office before time
            CFG.leader_Random_GameData.getLeaderOfCiv().getMonth() <= Game_Calendar.currentMonth && //leader in office before time
            CFG.leader_Random_GameData.getLeaderOfCiv().getDay() <= Game_Calendar.currentDay && //leader in office before time
            CFG.gameAges.getAgeOfYear(CFG.leader_Random_GameData.getLeaderOfCiv().getYear()) == CFG.gameAges.getAgeOfYear(Game_Calendar.currentYear) //leader in office in the same age
            //CFG.leader_Random_GameData.getLeaderOfCiv().getYear() >= Game_Calendar.currentYear - MAXIMUM_TIME_OF_RULE //leader in office not too long ago
        );
    }

    protected static void buildRandomLeader(int iCivID) {
        buildRandomLeader(iCivID, false);
    }

    protected static void buildRandomLeader(int iCivID, boolean atStartGame) {
        if (CFG.game.getCiv(iCivID).getNumOfProvinces() < 1) return;

        String sContName;
        if (CFG.game.getCiv(iCivID).getCapitalProvinceID() >= 0) {
            sContName = CFG.map.getMapContinents().getName(CFG.game.getProvince(CFG.game.getCiv(iCivID).getCapitalProvinceID()).getContinent());
        } else {
            sContName = CFG.map.getMapContinents().getName(CFG.game.getProvince(CFG.game.getCiv(iCivID).getProvinceID(0)).getContinent());
        }

        final ArrayList<Integer> candidates = new ArrayList<>();
        String[] tagsSPLITED = null;
        try {
            if (CFG.isDesktop()) {
                final List<String> tempFiles = CFG.getFileNames("game/leadersRandom/");
                for (int i = 0, iSize = tempFiles.size(); i < iSize; ++i) {
                    if (tempFiles.get(i).equals("Age_of_Civilizations")) {
                        tempFiles.remove(i);
                        break;
                    }
                }
                tagsSPLITED = new String[tempFiles.size()];
                for (int i = 0, iSize = tempFiles.size(); i < iSize; ++i) {
                    tagsSPLITED[i] = tempFiles.get(i);
                }
            } else {
                final FileHandle tempFileT = Gdx.files.internal("game/leadersRandom/Age_of_Civilizations");
                final String tempT = tempFileT.readString();
                tagsSPLITED = tempT.split(";");
            }
            for (int j = 0, iSize2 = tagsSPLITED.length; j < iSize2; ++j) {
                try {
                    try {
                        final FileHandle file = Gdx.files.local("game/leadersRandom/" + tagsSPLITED[j]);
                        CFG.leader_Random_GameData = (Leader_Random_GameData) CFG.deserialize(file.readBytes());
                    } catch (final GdxRuntimeException ex) {
                        final FileHandle file = Gdx.files.internal("game/leadersRandom/" + tagsSPLITED[j]);
                        CFG.leader_Random_GameData = (Leader_Random_GameData) CFG.deserialize(file.readBytes());
                    }
                } catch (final ClassNotFoundException | IOException ex4) {
                }
                if (CFG.leader_Random_GameData.containsCiv(sContName) || CFG.leader_Random_GameData.containsCiv("All")) {
                    if (CFG.leader_Random_GameData.containsAIType(CFG.ideologiesManager.getIdeology(CFG.game.getCiv(iCivID).getIdeologyID()).AI_TYPE) || CFG.leader_Random_GameData.containsAIType("All")) {
                        if (DynamicEventManager_Leader.isValidLeaderRandom()) {
                            if (!atStartGame || (CFG.leader_Random_GameData.getLeaderOfCiv().canAppearNaturally())) {
                                candidates.add(j);
                            }
                        }
                    }
                }
            }
        } catch (GdxRuntimeException e) {
        }

        if (!candidates.isEmpty()) {
            int randomIndex = candidates.get(CFG.oR.nextInt(candidates.size()));

            try {
                try {
                    try {
                        final FileHandle file = Gdx.files.local("game/leadersRandom/" + tagsSPLITED[randomIndex]);
                        CFG.leader_Random_GameData = (Leader_Random_GameData) CFG.deserialize(file.readBytes());
                    } catch (final GdxRuntimeException ex) {
                        final FileHandle file = Gdx.files.internal("game/leadersRandom/" + tagsSPLITED[randomIndex]);
                        CFG.leader_Random_GameData = (Leader_Random_GameData) CFG.deserialize(file.readBytes());
                    }
                } catch (final ClassNotFoundException | IOException ex4) {
                }
            } catch (GdxRuntimeException e) {
            }

            CFG.game.getCiv(iCivID).civGameData.setLeader(CFG.leader_Random_GameData.getLeaderOfCiv());
            if (!atStartGame) {
                CFG.game.getCiv(iCivID).civGameData.leaderData.setYear(Game_Calendar.currentYear);
                CFG.game.getCiv(iCivID).civGameData.leaderData.setMonth(Game_Calendar.currentMonth);
                CFG.game.getCiv(iCivID).civGameData.leaderData.setDay(Game_Calendar.currentDay);
            } else {
                CFG.game.getCiv(iCivID).civGameData.leaderData.setYear(Game_Calendar.currentYear - (int)(Math.random() * MAXIMUM_TIME_OF_RULE));
                CFG.game.getCiv(iCivID).civGameData.leaderData.setMonth(Math.abs(Game_Calendar.currentMonth - (int)(Math.random() * 12)));
                CFG.game.getCiv(iCivID).civGameData.leaderData.setDay(Math.abs(Game_Calendar.currentDay - (int)(Math.random() * 28)));
            }
        }
    }

    protected void invokeCivilWarLeaderChange(final int iCivID, final int newCivID) {
        Event_GameData eventGameData = new Event_GameData();
        Event_Outcome_ChangeLeader eventOutcomeChangeLeader = new Event_Outcome_ChangeLeader();
        Event_Outcome_ChangeLeader eventOutcomeChangeLeader2 = new Event_Outcome_ChangeLeader();
        Event_Outcome_UpdatePopulation eventOutcomeUpdatePopulation = new Event_Outcome_UpdatePopulation();
        Event_Outcome_UpdateHappinessOfCiv eventOutcomeUpdateHappinessOfCiv = new Event_Outcome_UpdateHappinessOfCiv();
        Event_Conditions_CivExist eventConditionsCivExist = new Event_Conditions_CivExist();

        eventOutcomeChangeLeader.iCivID = newCivID;
        eventOutcomeChangeLeader.setLeader(CFG.game.getCiv(iCivID).civGameData.leaderData);

        buildRandomLeader(iCivID);
        if (CFG.game.getCiv(iCivID).civGameData.leaderData == null || CFG.game.getCiv(iCivID).civGameData.leaderData.getName().equals(eventOutcomeChangeLeader.iValue.getName())) {
            //no valid leader found or same leader
            eventOutcomeChangeLeader = null;
            eventOutcomeUpdatePopulation = null;
            eventOutcomeUpdateHappinessOfCiv = null;
            eventConditionsCivExist = null;
            eventGameData = null;
            return;
        } else {
            eventOutcomeChangeLeader.iValue.setYear(Game_Calendar.currentYear);
            eventOutcomeChangeLeader.iValue.setMonth(Game_Calendar.currentMonth);
            eventOutcomeChangeLeader.iValue.setDay(Game_Calendar.currentDay);
        }

        eventOutcomeChangeLeader2.iCivID = iCivID;
        eventOutcomeChangeLeader2.setLeader(CFG.game.getCiv(iCivID).civGameData.leaderData);

        eventOutcomeUpdatePopulation.setCivID(iCivID);
        eventOutcomeUpdatePopulation.lProvinces.add(CFG.game.getCiv(iCivID).getCapitalProvinceID());
        eventOutcomeUpdatePopulation.setValue(-1);

        eventOutcomeUpdateHappinessOfCiv.iCivID = iCivID;
        eventOutcomeUpdateHappinessOfCiv.iValue = -5;

        ArrayList<Event_Outcome> outcomes = new ArrayList<Event_Outcome>();
        outcomes.add(eventOutcomeChangeLeader);
        outcomes.add(eventOutcomeChangeLeader2);
        outcomes.add(eventOutcomeUpdatePopulation);
        outcomes.add(eventOutcomeUpdateHappinessOfCiv);

        eventConditionsCivExist.iCivID = newCivID;

        eventGameData.setup_Event(
                CFG.langManager.get(String.format("EventCivilWarNameLeader_" + (int)(Math.ceil(Math.random() * (3)))), eventOutcomeChangeLeader.iValue.getName()),
                "EventCivilWarLeader_" + (int)(Math.ceil(Math.random() * (3))),
                "eventCivilWarLeader",
                CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()
        );
        eventGameData.setup_EventPopUp(
                true,
                CFG.langManager.get(String.format("EventCivilWarLeader_" + (int)(Math.ceil(Math.random() * (3)))), CFG.game.getCiv(iCivID).civGameData.leaderData.getName())
        );
        eventGameData.setup_EventDecisions_List(
                CFG.langManager.get(String.format("EventCivilWarLeaderDecision_" + (int)(Math.ceil(Math.random() * (3))))),
                outcomes
        );
        eventGameData.setup_EventTriggers(
                eventConditionsCivExist
        );

        CFG.dynamicEventManager.addEventIndex(eventGameData);
        event_lastturn = eventGameData;
        event_lastturn.setFired(currentTurn);
    }

    protected void invokeRandomLeaderEvent() {
        String civRef = CFG.game.getCiv(civpl).getCivName();
        Event_GameData eventGameData = new Event_GameData();
        Event_Outcome_ChangeLeader eventOutcomeChangeLeader = new Event_Outcome_ChangeLeader();
        Event_Conditions_CivExist eventConditionsCivExist = new Event_Conditions_CivExist();

        eventOutcomeChangeLeader.iCivID = civpl;
        eventOutcomeChangeLeader.iValue.setName("Steve Jobs");
        eventOutcomeChangeLeader.iValue.setImage("bat.png");
        eventConditionsCivExist.iCivID = civpl;

        if (CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID() == civpl) {
            //SELF
            eventGameData.setup_Event(
                    CFG.langManager.get(String.format("EventMiracleNamePlayer_" + (int)(Math.ceil(Math.random() * (2))))),
                    "EventMiracle_" + (int)(Math.ceil(Math.random() * (5))),
                    "eventMiracle",
                    CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()
            );
            eventGameData.setup_EventPopUp(
                    true,
                    CFG.langManager.get(String.format("EventMiracleDescPlayer_" + (int)(Math.ceil(Math.random() * (2)))))
            );
            eventGameData.setup_EventDecisions(
                    CFG.langManager.get(String.format("EventMiracleDecisionPlayer_" + (int)(Math.ceil(Math.random() * (2))))),
                    eventOutcomeChangeLeader
            );
            eventGameData.setup_EventTriggers(
                    eventConditionsCivExist
            );
        } else if (!CFG.getMetCiv(civpl)) {
            //UNDISCOVERED
            eventGameData.setup_Event(
                    CFG.langManager.get(String.format("EventMiracleNameUndiscovered_" + (int)(Math.ceil(Math.random() * (3))))),
                    "EventMiracle_" + (int)(Math.ceil(Math.random() * (5))),
                    "eventMiracle",
                    CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()
            );
            eventGameData.setup_EventPopUp(
                    true,
                    CFG.langManager.get(String.format("EventMiracleDescUndiscovered_" + (int)(Math.ceil(Math.random() * (3)))))
            );
            eventGameData.setup_EventDecisions(
                    CFG.langManager.get(String.format("EventMiracleDecisionUndiscovered_" + (int)(Math.ceil(Math.random() * (3))))),
                    eventOutcomeChangeLeader
            );
            eventGameData.setup_EventTriggers(
                    eventConditionsCivExist
            );
        } else {
            //OTHER
            eventGameData.setup_Event(
                    CFG.langManager.get(String.format("EventMiracleName_" + (int)(Math.ceil(Math.random() * (3)))), civRef),
                    "EventMiracle_" + (int)(Math.ceil(Math.random() * (5))),
                    "eventMiracle",
                    CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()
            );
            eventGameData.setup_EventPopUp(
                    true,
                    CFG.langManager.get(String.format("EventMiracleDesc_" + (int)(Math.ceil(Math.random() * (3)))), civRef)
            );
            eventGameData.setup_EventDecisions(
                    CFG.langManager.get(String.format("EventMiracleDecision_" + (int)(Math.ceil(Math.random() * (3))))),
                    eventOutcomeChangeLeader
            );
            eventGameData.setup_EventTriggers(
                    eventConditionsCivExist
            );
        }

        CFG.dynamicEventManager.addEventIndex(eventGameData);
        event_lastturn = eventGameData;
        event_lastturn.setFired(currentTurn);
    }
}
