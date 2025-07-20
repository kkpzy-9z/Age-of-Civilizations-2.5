package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxRuntimeException;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

public class DynamicEventManager_Leader implements Serializable {
    private static final long serialVersionUID = 0L;
    private static int currentAge = -1;
    private static int currentTurn = -1;
    private static int currentScenario = -1;
    protected Event_GameData event_lastturn;
    final static int MAXIMUM_TIME_OF_RULE = 75;
    final static int MINIMUM_TIME_OF_RULE = 10;

    DynamicEventManager_Leader() {
        //cacheLeaders();
    }

    protected void invokeLeaderEvents() {
        currentTurn = CFG.game.getPlayer(CFG.PLAYER_TURNID).statistics_Civ_GameData.getTurns();

        if (currentTurn == 1 && CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.leaderData != null
            && CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.leaderData.isRandom()) {
            this.invokeInitLeaderEvent();
        }

    }

    private static LinkedHashMap<String, Leader_Random_GameData> leaderCache = new LinkedHashMap<>();
    private static void cacheLeaders() {
        currentAge = CFG.gameAges.getAgeOfYear(Game_Calendar.currentYear);
        currentScenario = CFG.game.getScenarioID();

        //if (!leaderCache.isEmpty()) return;
        leaderCache.clear();

        //randomize leader order
        List<String> files = CFG.getFileNames("game/leadersRandom/");
        Collections.shuffle(files);

        LinkedHashMap<String, Leader_Random_GameData> toAppendtoLeaders = new LinkedHashMap<>();
        for (String fileName : files) {
            if (fileName.equals("Age_of_Civilizations")) continue;
            try {
                FileHandle file = Gdx.files.local("game/leadersRandom/" + fileName);
                CFG.leader_Random_GameData = (Leader_Random_GameData) CFG.deserialize(file.readBytes());

                CFG.leader_Random_GameData.getLeaderOfCiv().setRandom(true); //ensure that the leader is marked as random

                if (isValidLeaderRandom()) {
                    if (CFG.leader_Random_GameData.containsCiv("All") || CFG.leader_Random_GameData.containsAIType("All")) {
                        toAppendtoLeaders.put(fileName, CFG.leader_Random_GameData);
                    } else {
                        leaderCache.put(fileName, CFG.leader_Random_GameData);
                    }
                }
                CFG.leader_Random_GameData = null;
            } catch (Exception e) {
                try {
                    FileHandle file = Gdx.files.internal("game/leadersRandom/" + fileName);
                    CFG.leader_Random_GameData = (Leader_Random_GameData) CFG.deserialize(file.readBytes());

                    CFG.leader_Random_GameData.getLeaderOfCiv().setRandom(true); //ensure that the leader is marked as random

                    if (isValidLeaderRandom()) {
                        if (CFG.leader_Random_GameData.containsCiv("All") || CFG.leader_Random_GameData.containsAIType("All")) {
                            toAppendtoLeaders.put(fileName, CFG.leader_Random_GameData);
                        } else {
                            leaderCache.put(fileName, CFG.leader_Random_GameData);
                        }
                    }
                    CFG.leader_Random_GameData = null;
                } catch (Exception e2) {

                }
            }
        }
        //merge generics with leader list at END
        //ensures generics are last resort
        leaderCache.putAll(toAppendtoLeaders);
    }

    protected static boolean isValidLeader() {
        return (
            CFG.leader_GameData.getLeaderOfCiv() != null && //non-null leader
            CFG.leader_GameData.getLeaderOfCiv().getYear() <= Game_Calendar.currentYear && //leader in office before time
            CFG.leader_GameData.getLeaderOfCiv().getYear() >= Game_Calendar.currentYear - MAXIMUM_TIME_OF_RULE && //leader in office not too long ago
            (CFG.leader_GameData.getLeaderOfCiv().isIncumbentYear() || CFG.leader_GameData.getLeaderOfCiv().getYear() <= Game_Calendar.currentYear - MINIMUM_TIME_OF_RULE) //leader not too young (only if birthdate set)
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

    protected static void safeReplaceLeader(final int iCivID) {
        try {
            final LeaderOfCiv_GameData newLeader = new LeaderOfCiv_GameData();

            newLeader.setTag(CFG.game.getCiv(iCivID).civGameData.leaderData.getTag());
            newLeader.setName(CFG.game.getCiv(iCivID).civGameData.leaderData.getName());
            newLeader.setImage(CFG.game.getCiv(iCivID).civGameData.leaderData.getImage());
            newLeader.setWiki(CFG.game.getCiv(iCivID).civGameData.leaderData.getWiki());
            newLeader.setYear(CFG.game.getCiv(iCivID).civGameData.leaderData.getYear());
            newLeader.setMonth(CFG.game.getCiv(iCivID).civGameData.leaderData.getMonth());
            newLeader.setDay(CFG.game.getCiv(iCivID).civGameData.leaderData.getDay());
            newLeader.setIncumbentYear(CFG.game.getCiv(iCivID).civGameData.leaderData.isIncumbentYear());
            newLeader.setRandom(CFG.game.getCiv(iCivID).civGameData.leaderData.isRandom());

            newLeader.fModifier_PopGrowth = CFG.game.getCiv(iCivID).civGameData.leaderData.fModifier_PopGrowth;
            newLeader.fModifier_EconomyGrowth = CFG.game.getCiv(iCivID).civGameData.leaderData.fModifier_EconomyGrowth;
            newLeader.fModifier_IncomeTaxation = CFG.game.getCiv(iCivID).civGameData.leaderData.fModifier_IncomeTaxation;
            newLeader.fModifier_IncomeProduction = CFG.game.getCiv(iCivID).civGameData.leaderData.fModifier_IncomeProduction;
            newLeader.fModifier_Administration = CFG.game.getCiv(iCivID).civGameData.leaderData.fModifier_Administration;
            newLeader.fModifier_Research = CFG.game.getCiv(iCivID).civGameData.leaderData.fModifier_Research;
            newLeader.fModifier_MilitaryUpkeep = CFG.game.getCiv(iCivID).civGameData.leaderData.fModifier_MilitaryUpkeep;
            newLeader.fModifier_AttackBonus = CFG.game.getCiv(iCivID).civGameData.leaderData.fModifier_AttackBonus;
            newLeader.fModifier_DefenseBonus = CFG.game.getCiv(iCivID).civGameData.leaderData.fModifier_DefenseBonus;
            newLeader.fModifier_MovementPoints = CFG.game.getCiv(iCivID).civGameData.leaderData.fModifier_MovementPoints;

            CFG.game.getCiv(iCivID).civGameData.leaderData = newLeader;
        } catch (NullPointerException ex) {
            buildPlaceholderLeader(iCivID);
        }
    }

    protected static void buildPlaceholderLeader(int iCivID) {
         CFG.game.getCiv(iCivID).civGameData.leaderData = new LeaderOfCiv_GameData();
         CFG.game.getCiv(iCivID).civGameData.leaderData.setName(CFG.langManager.get("Leader" + " " + CFG.langManager.get("Of") + " " + CFG.game.getCiv(iCivID).getCivName()));
         CFG.game.getCiv(iCivID).civGameData.leaderData.setYear(Game_Calendar.currentYear - (int) (Math.random() * DynamicEventManager_Leader.MAXIMUM_TIME_OF_RULE));
         CFG.game.getCiv(iCivID).civGameData.leaderData.setMonth(Math.abs(Game_Calendar.currentMonth - (int) (Math.random() * 12)));
         CFG.game.getCiv(iCivID).civGameData.leaderData.setDay(Math.abs(Game_Calendar.currentDay - (int) (Math.random() * 28)));
    }

    protected static boolean buildRandomLeader(int iCivID) {
        return buildRandomLeader(iCivID, false);
    }

    protected static boolean buildRandomLeader(int iCivID, boolean atStartGame) {
        if (CFG.game.getCiv(iCivID).getNumOfProvinces() < 1) return false;
        if (currentAge != CFG.gameAges.getAgeOfYear(Game_Calendar.currentYear) || currentScenario != CFG.game.getScenarioID()) {
            cacheLeaders();
        }

        String sContName;
        if (CFG.game.getCiv(iCivID).getCapitalProvinceID() >= 0) {
            sContName = CFG.map.getMapContinents().getName(CFG.game.getProvince(CFG.game.getCiv(iCivID).getCapitalProvinceID()).getContinent());
        } else {
            sContName = CFG.map.getMapContinents().getName(CFG.game.getProvince(CFG.game.getCiv(iCivID).getProvinceID(0)).getContinent());
        }

        try {
            String toRemove = null;

            //shuffle the keys to randomize leader selection
            //List<String> keys = new ArrayList<>(leaderCache.keySet());
            //Collections.shuffle(keys);
            //for (String leaderKey : keys) {
            for (String leaderKey : leaderCache.keySet()) {
                CFG.leader_Random_GameData = leaderCache.get(leaderKey);
                if ((CFG.leader_Random_GameData.containsCiv(sContName) || CFG.leader_Random_GameData.containsCiv("All")) &&
                   (CFG.leader_Random_GameData.containsAIType(CFG.ideologiesManager.getIdeology(CFG.game.getCiv(iCivID).getIdeologyID()).AI_TYPE) || CFG.leader_Random_GameData.containsAIType("All")) &&
                   (CFG.leader_Random_GameData.getLeaderOfCiv().canAppearNaturally() || !atStartGame)) {

                    CFG.game.getCiv(iCivID).civGameData.setLeader(CFG.leader_Random_GameData.getLeaderOfCiv());
                    if (!atStartGame) {
                        CFG.game.getCiv(iCivID).civGameData.leaderData.setYear(Game_Calendar.currentYear);
                        CFG.game.getCiv(iCivID).civGameData.leaderData.setMonth(Game_Calendar.currentMonth);
                        CFG.game.getCiv(iCivID).civGameData.leaderData.setDay(Game_Calendar.currentDay);
                    } else {
                        CFG.game.getCiv(iCivID).civGameData.leaderData.setYear(Game_Calendar.currentYear - (int) (Math.random() * MAXIMUM_TIME_OF_RULE));
                        CFG.game.getCiv(iCivID).civGameData.leaderData.setMonth(Math.abs(Game_Calendar.currentMonth - (int) (Math.random() * 12)));
                        CFG.game.getCiv(iCivID).civGameData.leaderData.setDay(Math.abs(Game_Calendar.currentDay - (int) (Math.random() * 28)));
                    }

                    toRemove = leaderKey;
                    if (leaderCache.isEmpty()) {
                        //if no more leaders are available, reset the cache
                        Gdx.app.log("AoC", "No more leaders available, resetting cache...");
                        cacheLeaders();
                    }
                    break;
                }
                CFG.leader_Random_GameData = null;
            }
            if (toRemove != null) {
                leaderCache.remove(toRemove);
                return true;
            }

        } catch (GdxRuntimeException e) {
            // Optionally log error
        }
        return false;
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

    protected void invokeInitLeaderEvent() {
        String civRef = CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getCivName();
        Event_GameData eventGameData = new Event_GameData();
        Event_Outcome_ChangeLeader eventOutcomeChangeLeader = new Event_Outcome_ChangeLeader();
        Event_Conditions_CivExist eventConditionsCivExist = new Event_Conditions_CivExist();

        eventOutcomeChangeLeader.iCivID = CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID();
        eventOutcomeChangeLeader.iValue.setName(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.leaderData.getName());
        eventOutcomeChangeLeader.bIsLeaderChange = false; //no leader change, just ouput
        eventConditionsCivExist.iCivID = CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID();

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

        CFG.dynamicEventManager.addEventIndex(eventGameData);
        event_lastturn = eventGameData;
        event_lastturn.setFired(currentTurn);
    }
}
