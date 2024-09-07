package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.Gdx;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

class DynamicEventManager_Economy implements Serializable {
    private static final long serialVersionUID = 0L;
    protected int CRASH_CIV = -1;
    protected int MIRACLE_CIV = -1;
    protected int CRASH_SCANAT = -1;
    protected int MIRACLE_SCANAT = -1;
    protected int PLAYER_SCANAT = -1;
    private final int TURNS_LOWER_BOUND = 10;
    private final int TURNS_UPPER_BOUND = 30;
    private final int SCAN_TOPECONOMIES = 10;
    private final float CRASH_INVESTMENT_BUFFER = 0.25f;
    private final float MIRACLE_INVESTMENT_BUFFER = 0.25f;
    private final float CRASH_RIPPLE_INVESTMENT_BUFFER = 0.035f; //increase this (rn doesn't give any crash-bordering nations a chance)
    private final float MIRACLE_RIPPLE_INVESTMENT_BUFFER = 0.035f; //increase this (rn gives any miracle-bordering nations too much chance)
    private final float CRASH_INVESTMENT_THRESHOLD = 0.25f; //decrease this (too many crash events)
    private final float MIRACLE_INVESTMENT_THRESHOLD = 0.1f; //increase this (too many miracle events)
    private final int CRASH_ECONOMY_DECREASE = 15;
    private final int CRASH_ECONOMY_RIPPLE_DECREASE = 15;
    private final int CRASH_ECONOMY_RIPPLE_MIN = 5;
    private final int MIRACLE_ECONOMY_INCREASE = 15;
    private final int MIRACLE_ECONOMY_RIPPLE_INCREASE = 15;
    private final int MIRACLE_ECONOMY_RIPPLE_MIN = 5;
    private int currentTurn = -1;
    protected Event_GameData event_lastturn;
    protected ArrayList<Integer> lRippleEffectCivs;

    protected void invokeEconomicEvents() {
        currentTurn = CFG.game.getPlayer(CFG.PLAYER_TURNID).statistics_Civ_GameData.getTurns();
        //onstart set first scan dates
        if (CRASH_SCANAT == -1 || MIRACLE_SCANAT == -1 || PLAYER_SCANAT == -1) {
            CRASH_SCANAT = currentTurn + (int)CFG.dynamicEventManager.randomF(TURNS_UPPER_BOUND, 3, true);
            MIRACLE_SCANAT = currentTurn + (int)CFG.dynamicEventManager.randomF(TURNS_UPPER_BOUND, 3, true);
            PLAYER_SCANAT = currentTurn + (int)CFG.dynamicEventManager.randomF(TURNS_UPPER_BOUND, 3, true);
            Gdx.app.log("AoC2.5", "Scans C M P: " + (CRASH_SCANAT - currentTurn) + " " + (MIRACLE_SCANAT - currentTurn) + " " + (PLAYER_SCANAT - currentTurn));
        }

        //detect if event fired last turn, if so randomly create "ripple" effect
        if (event_lastturn != null && (event_lastturn.getFired() - currentTurn == -1)) {
            lRippleEffectCivs = new ArrayList<Integer>();
            if (Objects.equals(event_lastturn.getEventTag(), "eventCrash")) {
                //sort through crashing civ's bordering civilizations
                for (AI_BordersWith bordersWith: CFG.game.getCiv(CRASH_CIV).lBorderWithCivs) {
                   //remove if minor nation's embassy is closed with crashing or at war (enemies) bc realistically wouldn't trade, or if player (independantly scanned)
                   if (bordersWith.iWithCivID == CRASH_CIV || CFG.game.getCiv(bordersWith.iWithCivID).getCivilization_Diplomacy_GameData().isEmassyClosed(CRASH_CIV) || CFG.game.getCivsAtWar(bordersWith.iWithCivID, CRASH_CIV) || bordersWith.iWithCivID == CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()) continue;

                   float randBuffer = CFG.dynamicEventManager.randomF(CRASH_RIPPLE_INVESTMENT_BUFFER, 0, false);
                   Gdx.app.log("AoC2.5", CFG.game.getCiv(bordersWith.iWithCivID).getCivName() + " Ripple " + String.valueOf((CFG.game.getCiv(bordersWith.iWithCivID).getSpendings_Investments() - CFG.ideologiesManager.getIdeology(CFG.game.getCiv(bordersWith.iWithCivID).getIdeologyID()).MIN_INVESTMENTS) + randBuffer));

                   if ((CFG.game.getCiv(bordersWith.iWithCivID).getSpendings_Investments() - CFG.ideologiesManager.getIdeology(CFG.game.getCiv(bordersWith.iWithCivID).getIdeologyID()).MIN_INVESTMENTS) + randBuffer < CRASH_INVESTMENT_THRESHOLD) {
                       lRippleEffectCivs.add(bordersWith.iWithCivID);
                   }
                }
                //sort through player civ's bordering civilizations
                for (AI_BordersWith bordersWith: CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).lBorderWithCivs) {
                    //remove if minor nation's embassy is closed with crashing or at war (enemies) bc realistically wouldn't trade, or if player (independantly scanned) or if already in list from previous detection
                    if (bordersWith.iWithCivID == CRASH_CIV || CFG.game.getCiv(bordersWith.iWithCivID).getCivilization_Diplomacy_GameData().isEmassyClosed(CRASH_CIV) || CFG.game.getCivsAtWar(bordersWith.iWithCivID, CRASH_CIV) || bordersWith.iWithCivID == CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID() || lRippleEffectCivs.contains(bordersWith.iWithCivID)) continue;

                    float randBuffer = CFG.dynamicEventManager.randomF(CRASH_RIPPLE_INVESTMENT_BUFFER, 0, false);
                    Gdx.app.log("AoC2.5", CFG.game.getCiv(bordersWith.iWithCivID).getCivName() + " PlyNearRipple " + String.valueOf((CFG.game.getCiv(bordersWith.iWithCivID).getSpendings_Investments() - CFG.ideologiesManager.getIdeology(CFG.game.getCiv(bordersWith.iWithCivID).getIdeologyID()).MIN_INVESTMENTS) + randBuffer));

                    if ((CFG.game.getCiv(bordersWith.iWithCivID).getSpendings_Investments() - CFG.ideologiesManager.getIdeology(CFG.game.getCiv(bordersWith.iWithCivID).getIdeologyID()).MIN_INVESTMENTS) + randBuffer < CRASH_INVESTMENT_THRESHOLD) {
                        lRippleEffectCivs.add(bordersWith.iWithCivID);
                    }
                }
                //calculator player risk if not crashing civ
                if (CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID() != CRASH_CIV) {
                    float randBuffer = CFG.dynamicEventManager.randomF(CRASH_RIPPLE_INVESTMENT_BUFFER, 0, false);
                    Gdx.app.log("AoC2.5", CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getCivName() + " PlySelfRipple " + String.valueOf((CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getSpendings_Investments() - CFG.ideologiesManager.getIdeology(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getIdeologyID()).MIN_INVESTMENTS) + randBuffer));
                    if ((CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getSpendings_Investments() - CFG.ideologiesManager.getIdeology(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getIdeologyID()).MIN_INVESTMENTS) + randBuffer < CRASH_INVESTMENT_THRESHOLD) {
                        lRippleEffectCivs.add(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
                    }

                    //only if not player do event2
                    if (lRippleEffectCivs.size() > 1) {
                        invokeCrashRipple();
                    }
                }

                //if (lRippleEffectCivs.size() > 1) {
                //    invokeCrashRipple();
                //}
            } else if (Objects.equals(event_lastturn.getEventTag(), "eventMiracle")) {
                //sort through miracle civ's bordering civilizations
                for (AI_BordersWith bordersWith: CFG.game.getCiv(MIRACLE_CIV).lBorderWithCivs) {
                    //remove if minor nation's embassy is closed with miracle civ or at war (enemies) bc realistically wouldn't trade, or if player (independantly scanned)
                    if (bordersWith.iWithCivID == MIRACLE_CIV || CFG.game.getCiv(bordersWith.iWithCivID).getCivilization_Diplomacy_GameData().isEmassyClosed(MIRACLE_CIV) || CFG.game.getCivsAtWar(bordersWith.iWithCivID, MIRACLE_CIV) || bordersWith.iWithCivID == CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()) continue;

                    float randBuffer = CFG.dynamicEventManager.randomF(MIRACLE_RIPPLE_INVESTMENT_BUFFER, 0, false);
                    Gdx.app.log("AoC2.5", CFG.game.getCiv(bordersWith.iWithCivID).getCivName() + " Ripple " + String.valueOf((CFG.game.getCiv(bordersWith.iWithCivID).getSpendings_Investments() - CFG.ideologiesManager.getIdeology(CFG.game.getCiv(bordersWith.iWithCivID).getIdeologyID()).MIN_INVESTMENTS) + randBuffer));

                    if ((CFG.game.getCiv(bordersWith.iWithCivID).getSpendings_Investments() - CFG.ideologiesManager.getIdeology(CFG.game.getCiv(bordersWith.iWithCivID).getIdeologyID()).MIN_INVESTMENTS) + randBuffer > MIRACLE_INVESTMENT_THRESHOLD) {
                        lRippleEffectCivs.add(bordersWith.iWithCivID);
                    }
                }
                //sort through player civ's bordering civilizations
                for (AI_BordersWith bordersWith: CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).lBorderWithCivs) {
                    //remove if minor nation's embassy is closed with miracle civ or at war (enemies) bc realistically wouldn't trade, or if player (independantly scanned) or if already in list from previous detection
                    if (bordersWith.iWithCivID == MIRACLE_CIV || CFG.game.getCiv(bordersWith.iWithCivID).getCivilization_Diplomacy_GameData().isEmassyClosed(MIRACLE_CIV) || CFG.game.getCivsAtWar(bordersWith.iWithCivID, MIRACLE_CIV) || bordersWith.iWithCivID == CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID() || lRippleEffectCivs.contains(bordersWith.iWithCivID)) continue;

                    float randBuffer = CFG.dynamicEventManager.randomF(MIRACLE_RIPPLE_INVESTMENT_BUFFER, 0, false);
                    Gdx.app.log("AoC2.5", CFG.game.getCiv(bordersWith.iWithCivID).getCivName() + " PlyNearRipple " + String.valueOf((CFG.game.getCiv(bordersWith.iWithCivID).getSpendings_Investments() - CFG.ideologiesManager.getIdeology(CFG.game.getCiv(bordersWith.iWithCivID).getIdeologyID()).MIN_INVESTMENTS) + randBuffer));

                    if ((CFG.game.getCiv(bordersWith.iWithCivID).getSpendings_Investments() - CFG.ideologiesManager.getIdeology(CFG.game.getCiv(bordersWith.iWithCivID).getIdeologyID()).MIN_INVESTMENTS) + randBuffer > MIRACLE_INVESTMENT_THRESHOLD) {
                        lRippleEffectCivs.add(bordersWith.iWithCivID);
                    }
                }
                //calculator player risk if not miracle civ
                if (CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID() != MIRACLE_CIV) {
                    float randBuffer = CFG.dynamicEventManager.randomF(MIRACLE_RIPPLE_INVESTMENT_BUFFER, 0, false);
                    Gdx.app.log("AoC2.5", CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getCivName() + " PlySelfRipple " + String.valueOf((CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getSpendings_Investments() - CFG.ideologiesManager.getIdeology(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getIdeologyID()).MIN_INVESTMENTS) + randBuffer));
                    if ((CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getSpendings_Investments() - CFG.ideologiesManager.getIdeology(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getIdeologyID()).MIN_INVESTMENTS) + randBuffer > MIRACLE_INVESTMENT_THRESHOLD) {
                        lRippleEffectCivs.add(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
                    }

                    //only if not player do event
                    if (lRippleEffectCivs.size() > 1) {
                        invokeMiracleRipple();
                    }
                }

                //if (lRippleEffectCivs.size() > 1) {
                //    invokeMiracleRipple();
                //}
            }

            lRippleEffectCivs.clear();
        }

        //SAFECHECKS
        boolean crs = CRASH_SCANAT == currentTurn;
        boolean mir = MIRACLE_SCANAT == currentTurn;
        boolean ply = PLAYER_SCANAT == currentTurn;
        //if ripple effect this turn
        if (event_lastturn != null && event_lastturn.getFired() == currentTurn) {
            if (crs) {
                CRASH_SCANAT = currentTurn + (int)CFG.dynamicEventManager.randomF(TURNS_UPPER_BOUND, TURNS_LOWER_BOUND, true);
                Gdx.app.log("AoC2.5", "Random Turn Conflict Delayed Crash");
                crs = false;
            }
            if (mir) {
                MIRACLE_SCANAT = currentTurn + (int)CFG.dynamicEventManager.randomF(TURNS_UPPER_BOUND, TURNS_LOWER_BOUND, true);
                Gdx.app.log("AoC2.5", "Random Turn Conflict Delayed Miracle");
                mir = false;
            }
            if (ply) {
                PLAYER_SCANAT = currentTurn + (int)CFG.dynamicEventManager.randomF(TURNS_UPPER_BOUND, TURNS_LOWER_BOUND, true);
                Gdx.app.log("AoC2.5", "Random Turn Conflict Delayed Player");
                ply = false;
            }
        //if player and some other, delay player
        } else if ((crs && ply) || (mir && ply)) {
            PLAYER_SCANAT = currentTurn + (int)CFG.dynamicEventManager.randomF(TURNS_UPPER_BOUND, TURNS_LOWER_BOUND, true);
            Gdx.app.log("AoC2.5", "Random Turn Conflict Delayed Player");
            ply = false;
        }
        //if crash + miracle at same time (this turn), pick one to delay
        if (crs && mir) {
            if ((int)Math.round(Math.random()) == 1) {
                //delay crash
                CRASH_SCANAT = currentTurn + (int)CFG.dynamicEventManager.randomF(TURNS_UPPER_BOUND, TURNS_LOWER_BOUND, true);
                Gdx.app.log("AoC2.5", "Random Turn Conflict Delayed Crash");
            } else {
                //delay miracle
                MIRACLE_SCANAT = currentTurn + (int)CFG.dynamicEventManager.randomF(TURNS_UPPER_BOUND, TURNS_LOWER_BOUND, true);
                Gdx.app.log("AoC2.5", "Random Turn Conflict Delayed Miracle");
            }
        }

        if (currentTurn == CRASH_SCANAT) {
            Gdx.app.log("AoC2.5", "Scanning Crash");

            //get list of top 10 ecos to scan through, then randomize it
            ArrayList<Integer> topEcoRandomCivs = new ArrayList<Integer>();
            ArrayList<Integer> topEconomiesList = topEconomies();
            for (int i = 0; i < SCAN_TOPECONOMIES; ++i) {
                //if current scan of top 15 ecos is bigger than size of valid economical civs, break
                if (i == topEconomiesList.size()) {
                    break;
                }
                topEcoRandomCivs.add(topEconomiesList.get(i));
            }
            Collections.shuffle(topEcoRandomCivs);

            for (int tCivID: topEcoRandomCivs) {
                float randBuffer = CFG.dynamicEventManager.randomF(CRASH_INVESTMENT_BUFFER, 0, false);
                Gdx.app.log("AoC2.5", CFG.game.getCiv(tCivID).getCivName() + " " + String.valueOf((CFG.game.getCiv(tCivID).getSpendings_Investments() - CFG.ideologiesManager.getIdeology(CFG.game.getCiv(tCivID).getIdeologyID()).MIN_INVESTMENTS) + randBuffer));
                if ((CFG.game.getCiv(tCivID).getSpendings_Investments() - CFG.ideologiesManager.getIdeology(CFG.game.getCiv(tCivID).getIdeologyID()).MIN_INVESTMENTS) + randBuffer < CRASH_INVESTMENT_THRESHOLD) {
                    CRASH_CIV = tCivID;
                    invokeCrashEvent();

                    CRASH_SCANAT = currentTurn + (int)CFG.dynamicEventManager.randomF(TURNS_UPPER_BOUND, TURNS_LOWER_BOUND, true);
                    break;
                }
            }

            topEconomiesList.clear();
            topEcoRandomCivs.clear();
        } else if (currentTurn == MIRACLE_SCANAT) {
            Gdx.app.log("AoC2.5", "Scanning Miracle");

            //get list of top 10 ecos to scan through, then randomize it old table method
            ArrayList<Integer> topEconomiesList = topEconomies();
            ArrayList<Integer> topEcoRandomCivs = new ArrayList<Integer>();
            for (int i = 0; i < SCAN_TOPECONOMIES; ++i) {
                //if current scan of top 15 ecos is bigger than size of valid economical civs, break
                if (i == topEconomiesList.size()) {
                    break;
                }
                topEcoRandomCivs.add(topEconomiesList.get(i));
            }
            Collections.shuffle(topEcoRandomCivs);

            for (int tCivID: topEcoRandomCivs) {
                float randBuffer = CFG.dynamicEventManager.randomF(MIRACLE_INVESTMENT_BUFFER, 0, false);
                Gdx.app.log("AoC2.5", CFG.game.getCiv(tCivID).getCivName() + " " + String.valueOf((CFG.game.getCiv(tCivID).getSpendings_Investments() - CFG.ideologiesManager.getIdeology(CFG.game.getCiv(tCivID).getIdeologyID()).MIN_INVESTMENTS) + randBuffer));
                if ((CFG.game.getCiv(tCivID).getSpendings_Investments() - CFG.ideologiesManager.getIdeology(CFG.game.getCiv(tCivID).getIdeologyID()).MIN_INVESTMENTS) + randBuffer > MIRACLE_INVESTMENT_THRESHOLD) {
                    MIRACLE_CIV = tCivID;
                    invokeMiracleEvent();

                    MIRACLE_SCANAT = currentTurn + (int)CFG.dynamicEventManager.randomF(TURNS_UPPER_BOUND, TURNS_LOWER_BOUND, true);
                    break;
                }
            }
            topEconomiesList.clear();
            topEcoRandomCivs.clear();
        }

        //only scan player if no other event
        if (currentTurn == PLAYER_SCANAT) {
            Gdx.app.log("AoC2.5", "Scanning Player");
            int plyCivID = CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID();
            float randBufferM = CFG.dynamicEventManager.randomF(MIRACLE_INVESTMENT_BUFFER, 0, false);
            float randBufferC = CFG.dynamicEventManager.randomF(CRASH_INVESTMENT_BUFFER, 0, false);

            //randomly choose to scan miracle probability first or crash probability first during player scan
            if ((int)Math.round(Math.random()) == 1) {
                //miracle prob first
                Gdx.app.log("AoC2.5", "Player Random Top Miracle");
                if ((CFG.game.getCiv(plyCivID).getSpendings_Investments() - CFG.ideologiesManager.getIdeology(CFG.game.getCiv(plyCivID).getIdeologyID()).MIN_INVESTMENTS) + randBufferM > MIRACLE_INVESTMENT_THRESHOLD) {
                    Gdx.app.log("AoC2.5", "PlayerCiv " + String.valueOf((CFG.game.getCiv(plyCivID).getSpendings_Investments() - CFG.ideologiesManager.getIdeology(CFG.game.getCiv(plyCivID).getIdeologyID()).MIN_INVESTMENTS) + randBufferM));

                    MIRACLE_CIV = plyCivID;
                    invokeMiracleEvent();
                } else if ((CFG.game.getCiv(plyCivID).getSpendings_Investments() - CFG.ideologiesManager.getIdeology(CFG.game.getCiv(plyCivID).getIdeologyID()).MIN_INVESTMENTS) + randBufferC < CRASH_INVESTMENT_THRESHOLD) {
                    Gdx.app.log("AoC2.5", "PlayerCiv " + String.valueOf((CFG.game.getCiv(plyCivID).getSpendings_Investments() - CFG.ideologiesManager.getIdeology(CFG.game.getCiv(plyCivID).getIdeologyID()).MIN_INVESTMENTS) + randBufferC));

                    CRASH_CIV = plyCivID;
                    invokeCrashEvent();
                }
            } else {
                //crash prob first
                Gdx.app.log("AoC2.5", "Player Random Top Crash");
                if ((CFG.game.getCiv(plyCivID).getSpendings_Investments() - CFG.ideologiesManager.getIdeology(CFG.game.getCiv(plyCivID).getIdeologyID()).MIN_INVESTMENTS) + randBufferC < CRASH_INVESTMENT_THRESHOLD) {
                    Gdx.app.log("AoC2.5", "PlayerCiv " + String.valueOf((CFG.game.getCiv(plyCivID).getSpendings_Investments() - CFG.ideologiesManager.getIdeology(CFG.game.getCiv(plyCivID).getIdeologyID()).MIN_INVESTMENTS) + randBufferC));

                    CRASH_CIV = plyCivID;
                    invokeCrashEvent();
                } else if ((CFG.game.getCiv(plyCivID).getSpendings_Investments() - CFG.ideologiesManager.getIdeology(CFG.game.getCiv(plyCivID).getIdeologyID()).MIN_INVESTMENTS) + randBufferM > MIRACLE_INVESTMENT_THRESHOLD) {
                    Gdx.app.log("AoC2.5", "PlayerCiv " + String.valueOf((CFG.game.getCiv(plyCivID).getSpendings_Investments() - CFG.ideologiesManager.getIdeology(CFG.game.getCiv(plyCivID).getIdeologyID()).MIN_INVESTMENTS) + randBufferM));

                    MIRACLE_CIV = plyCivID;
                    invokeMiracleEvent();
                }
            }
            PLAYER_SCANAT = currentTurn + (int)CFG.dynamicEventManager.randomF(TURNS_UPPER_BOUND, TURNS_LOWER_BOUND, true);
        }
    }

    protected ArrayList<Integer> topEconomies() {
        ArrayList<Integer> tSorted = new ArrayList<Integer>();
        ArrayList<Integer> tempIDS = new ArrayList<Integer>();
        ArrayList<Integer> tempScore = new ArrayList<>();
        for (int i = 1; i < CFG.game.getCivsSize(); ++i) {
            //Create new list of IDs sorted by economical score
            //Skip player (bc in independent search) + Remove countries with no provinces
            if (CFG.game.getSortedCivsAZ(i - 1) == CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID() || CFG.game.getCiv(CFG.game.getSortedCivsAZ(i - 1)).getNumOfProvinces() < 1) {
                continue;
            };

            tempIDS.add(CFG.game.getSortedCivsAZ(i - 1));
            tempScore.add(CFG.gameAction.buildRank_Score_Economy(CFG.game.getSortedCivsAZ(i - 1)));
        }
        int tAddID = 0;
        while (tempIDS.size() > 0) {
            tAddID = 0;
            for (int i = 1; i < tempIDS.size(); ++i) {
                if ((Integer)tempScore.get(tAddID) >= (Integer)tempScore.get(i)) continue;
                tAddID = i;
            }
            tSorted.add((Integer)tempIDS.get(tAddID));
            tempIDS.remove(tAddID);
            tempScore.remove(tAddID);
        }
        return tSorted;
    }

    protected void invokeCrashEvent() {
        String civRef = CFG.game.getCiv(CRASH_CIV).getCivName();
        Event_GameData eventGameData = new Event_GameData();
        Event_Outcome_UpdateEconomyOfCiv eventOutcomeUpdateEconomyOfCiv = new Event_Outcome_UpdateEconomyOfCiv();
        Event_Conditions_CivExist eventConditionsCivExist = new Event_Conditions_CivExist();

        eventOutcomeUpdateEconomyOfCiv.iCivID = CRASH_CIV;
        eventOutcomeUpdateEconomyOfCiv.iValue = -CRASH_ECONOMY_DECREASE;
        eventConditionsCivExist.iCivID = CRASH_CIV;

        if (CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID() == CRASH_CIV) {
            //SELF
            eventGameData.setup_Event(
                    CFG.langManager.get(String.format("EventCrashNamePlayer_" + (int)(Math.ceil(Math.random() * (2))))),
                    "EventCrash_" + (int)(Math.ceil(Math.random() * (5))),
                    "eventCrash",
                    CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()
            );
            eventGameData.setup_EventPopUp(
                    true,
                    CFG.langManager.get(String.format("EventCrashDescPlayer_" + (int)(Math.ceil(Math.random() * (2)))))
            );
            eventGameData.setup_EventDecisions(
                    CFG.langManager.get(String.format("EventCrashDecisionPlayer_" + (int)(Math.ceil(Math.random() * (2))))),
                    eventOutcomeUpdateEconomyOfCiv
            );
            eventGameData.setup_EventTriggers(
                    eventConditionsCivExist
            );
        } else if (!CFG.getMetCiv(CRASH_CIV)) {
            //UNDISCOVERED
            eventGameData.setup_Event(
                    CFG.langManager.get(String.format("EventCrashNameUndiscovered_" + (int)(Math.ceil(Math.random() * (2))))),
                    "EventCrash_" + (int)(Math.ceil(Math.random() * (5))),
                    "eventCrash",
                    CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()
            );
            eventGameData.setup_EventPopUp(
                    true,
                    CFG.langManager.get(String.format("EventCrashDescUndiscovered_" + (int)(Math.ceil(Math.random() * (2)))))
            );
            eventGameData.setup_EventDecisions(
                    CFG.langManager.get(String.format("EventCrashDecisionUndiscovered_" + (int)(Math.ceil(Math.random() * (2))))),
                    eventOutcomeUpdateEconomyOfCiv
            );
            eventGameData.setup_EventTriggers(
                    eventConditionsCivExist
            );
        } else {
            //OTHER
            eventGameData.setup_Event(
                    CFG.langManager.get(String.format("EventCrashName_" + (int)(Math.ceil(Math.random() * (5)))), civRef),
                    "EventCrash_" + (int)(Math.ceil(Math.random() * (5))),
                    "eventCrash",
                    CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()
            );
            eventGameData.setup_EventPopUp(
                    true,
                    CFG.langManager.get(String.format("EventCrashDesc_" + (int)(Math.ceil(Math.random() * (5)))), civRef)
            );
            eventGameData.setup_EventDecisions(
                    CFG.langManager.get(String.format("EventCrashDecision_" + (int)(Math.ceil(Math.random() * (5))))),
                    eventOutcomeUpdateEconomyOfCiv
            );
            eventGameData.setup_EventTriggers(
                    eventConditionsCivExist
            );
        }

        CFG.dynamicEventManager.addEventIndex(eventGameData);
        event_lastturn = eventGameData;
        event_lastturn.setFired(currentTurn);
    }

    protected void invokeCrashRipple() {
        String civRef = CFG.game.getCiv(CRASH_CIV).getCivName();
        Event_GameData eventGameData = new Event_GameData();

        Event_Conditions_CivExist eventConditionsCivExist = new Event_Conditions_CivExist();
        eventConditionsCivExist.iCivID = CRASH_CIV;
        eventGameData.setup_EventTriggers(eventConditionsCivExist);

        //create list of outcomes for each ripple effect civ
        ArrayList<Event_Outcome> eventOutcomes = new ArrayList<Event_Outcome>();
        for (int iCivID: lRippleEffectCivs) {
            Event_Outcome_UpdateEconomyOfCiv eventOutcomeUpdateEconomyOfCiv = new Event_Outcome_UpdateEconomyOfCiv();
            float iDistance = CFG.game_NextTurnUpdate.getDistanceFromCapital(CFG.game.getCiv(CRASH_CIV).getCapitalProvinceID(), CFG.game.getCiv(iCivID).getCapitalProvinceID());
            eventOutcomeUpdateEconomyOfCiv.iValue = -(int)Math.max((int)CRASH_ECONOMY_RIPPLE_DECREASE - ((iDistance * (CRASH_ECONOMY_RIPPLE_DECREASE/100.0f)) / 7), CRASH_ECONOMY_RIPPLE_MIN);
            eventOutcomeUpdateEconomyOfCiv.iCivID = iCivID;
            eventOutcomes.add(eventOutcomeUpdateEconomyOfCiv);
        }

        if (CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID() == CRASH_CIV) {
            //PLAYER CIV CRASHING
            //player crashed civ => other civs crashing
            eventGameData.setup_Event(
                    CFG.langManager.get(String.format("EventPlayerCrashRippleName_" + (int)(Math.ceil(Math.random() * (3)))), civRef),
                    "EventCrashRipple_" + (int)(Math.ceil(Math.random() * (3))),
                    "eventCrashRipple",
                    CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()
            );
            eventGameData.setup_EventPopUp(
                    true,
                    CFG.langManager.get(String.format("EventPlayerCrashRippleDesc_" + (int)(Math.ceil(Math.random() * (3)))), civRef)
            );
            //setup all outcomes into one cohesive event
            eventGameData.setup_EventDecisions_List(
                    CFG.langManager.get(String.format("EventPlayerCrashRippleDecision_" + (int)(Math.ceil(Math.random() * (3))))),
                    eventOutcomes
            );
        } else if (!CFG.getMetCiv(CRASH_CIV)) {
            //UNDISCOVERED CIV CRASHING
            //change desc based on if player effected by ripple
            if (lRippleEffectCivs.contains(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID())) {
                //unknown civ => player effected
                eventGameData.setup_Event(
                        CFG.langManager.get(String.format("EventCrashRipplePlayerNameUndiscovered_" + (int)(Math.ceil(Math.random() * (3)))), civRef),
                        "EventCrashRipple_" + (int)(Math.ceil(Math.random() * (3))),
                        "eventCrashRipple",
                        CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()
                );
                eventGameData.setup_EventPopUp(
                        true,
                        CFG.langManager.get(String.format("EventCrashRipplePlayerDescUndiscovered_" + (int)(Math.ceil(Math.random() * (3)))), civRef)
                );
                //setup all outcomes into one cohesive event
                eventGameData.setup_EventDecisions_List(
                        CFG.langManager.get(String.format("EventCrashRipplePlayerDecisionUndiscovered_" + (int)(Math.ceil(Math.random() * (3))))),
                        eventOutcomes
                );
            } else {
                //unknown civ => player not effected
                eventGameData.setup_Event(
                        CFG.langManager.get(String.format("EventCrashRippleNameUndiscovered_" + (int)(Math.ceil(Math.random() * (3)))), civRef),
                        "EventCrashRipple_" + (int)(Math.ceil(Math.random() * (3))),
                        "eventCrashRipple",
                        CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()
                );
                eventGameData.setup_EventPopUp(
                        true,
                        CFG.langManager.get(String.format("EventCrashRippleDescUndiscovered_" + (int)(Math.ceil(Math.random() * (3)))), civRef)
                );
                //setup all outcomes into one cohesive event
                eventGameData.setup_EventDecisions_List(
                        CFG.langManager.get(String.format("EventCrashRippleDecisionUndiscovered_" + (int)(Math.ceil(Math.random() * (3))))),
                        eventOutcomes
                );
            }
        } else {
            //OTHER CIV CRASHING
            if (lRippleEffectCivs.contains(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID())) {
                //known civ => player effected
                eventGameData.setup_Event(
                        CFG.langManager.get(String.format("EventCrashRipplePlayerName_" + (int)(Math.ceil(Math.random() * (3)))), civRef),
                        "EventCrashRipple_" + (int)(Math.ceil(Math.random() * (3))),
                        "eventCrashRipple",
                        CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()
                );
                eventGameData.setup_EventPopUp(
                        true,
                        CFG.langManager.get(String.format("EventCrashRipplePlayerDesc_" + (int)(Math.ceil(Math.random() * (3)))), civRef)
                );
                //setup all outcomes into one cohesive event
                eventGameData.setup_EventDecisions_List(
                        CFG.langManager.get(String.format("EventCrashRipplePlayerDecision_" + (int)(Math.ceil(Math.random() * (3))))),
                        eventOutcomes
                );
            } else {
                //known civ => player not effected
                eventGameData.setup_Event(
                        CFG.langManager.get(String.format("EventCrashRippleName_" + (int)(Math.ceil(Math.random() * (3)))), civRef),
                        "EventCrashRipple_" + (int)(Math.ceil(Math.random() * (3))),
                        "eventCrashRipple",
                        CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()
                );
                eventGameData.setup_EventPopUp(
                        true,
                        CFG.langManager.get(String.format("EventCrashRippleDesc_" + (int)(Math.ceil(Math.random() * (3)))), civRef)
                );
                //setup all outcomes into one cohesive event
                eventGameData.setup_EventDecisions_List(
                        CFG.langManager.get(String.format("EventCrashRippleDecision_" + (int)(Math.ceil(Math.random() * (3))))),
                        eventOutcomes
                );
            }
        }

        CFG.dynamicEventManager.addEventIndex(eventGameData);
        event_lastturn = eventGameData;
        event_lastturn.setFired(currentTurn);
    }

    protected void invokeMiracleEvent() {
        String civRef = CFG.game.getCiv(MIRACLE_CIV).getCivName();
        Event_GameData eventGameData = new Event_GameData();
        Event_Outcome_UpdateEconomyOfCiv eventOutcomeUpdateEconomyOfCiv = new Event_Outcome_UpdateEconomyOfCiv();
        Event_Conditions_CivExist eventConditionsCivExist = new Event_Conditions_CivExist();

        eventOutcomeUpdateEconomyOfCiv.iCivID = MIRACLE_CIV;
        eventOutcomeUpdateEconomyOfCiv.iValue = MIRACLE_ECONOMY_INCREASE;
        eventConditionsCivExist.iCivID = MIRACLE_CIV;

        if (CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID() == MIRACLE_CIV) {
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
                    eventOutcomeUpdateEconomyOfCiv
            );
            eventGameData.setup_EventTriggers(
                    eventConditionsCivExist
            );
        } else if (!CFG.getMetCiv(MIRACLE_CIV)) {
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
                    eventOutcomeUpdateEconomyOfCiv
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
                    eventOutcomeUpdateEconomyOfCiv
            );
            eventGameData.setup_EventTriggers(
                    eventConditionsCivExist
            );
        }

        CFG.dynamicEventManager.addEventIndex(eventGameData);
        event_lastturn = eventGameData;
        event_lastturn.setFired(currentTurn);
    }

    protected void invokeMiracleRipple() {
        String civRef = CFG.game.getCiv(MIRACLE_CIV).getCivName();
        Event_GameData eventGameData = new Event_GameData();

        Event_Conditions_CivExist eventConditionsCivExist = new Event_Conditions_CivExist();
        eventConditionsCivExist.iCivID = MIRACLE_CIV;
        eventGameData.setup_EventTriggers(eventConditionsCivExist);

        //create list of outcomes for each ripple effect civ
        ArrayList<Event_Outcome> eventOutcomes = new ArrayList<Event_Outcome>();
        for (int iCivID: lRippleEffectCivs) {
            Event_Outcome_UpdateEconomyOfCiv eventOutcomeUpdateEconomyOfCiv = new Event_Outcome_UpdateEconomyOfCiv();
            float iDistance = CFG.game_NextTurnUpdate.getDistanceFromCapital(CFG.game.getCiv(MIRACLE_CIV).getCapitalProvinceID(), CFG.game.getCiv(iCivID).getCapitalProvinceID());
            eventOutcomeUpdateEconomyOfCiv.iValue = (int)Math.max((int)MIRACLE_ECONOMY_RIPPLE_INCREASE - ((iDistance * (MIRACLE_ECONOMY_RIPPLE_INCREASE/100.0f)) / 7), MIRACLE_ECONOMY_RIPPLE_MIN);
            eventOutcomeUpdateEconomyOfCiv.iCivID = iCivID;
            eventOutcomes.add(eventOutcomeUpdateEconomyOfCiv);
        }

        if (CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID() == MIRACLE_CIV) {
            //PLAYER CIV MIRACLE
            //player is miracle civ => other civs miracle
            eventGameData.setup_Event(
                    CFG.langManager.get(String.format("EventPlayerMiracleRippleName_" + (int)(Math.ceil(Math.random() * (3)))), civRef),
                    "EventMiracleRipple_" + (int)(Math.ceil(Math.random() * (3))),
                    "eventMiracleRipple",
                    CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()
            );
            eventGameData.setup_EventPopUp(
                    true,
                    CFG.langManager.get(String.format("EventPlayerMiracleRippleDesc_" + (int)(Math.ceil(Math.random() * (3)))), civRef)
            );
            //setup all outcomes into one cohesive event
            eventGameData.setup_EventDecisions_List(
                    CFG.langManager.get(String.format("EventPlayerMiracleRippleDecision_" + (int)(Math.ceil(Math.random() * (3))))),
                    eventOutcomes
            );
        } else if (!CFG.getMetCiv(MIRACLE_CIV)) {
            //UNDISCOVERED CIV MIRACLEING
            //change desc based on if player effected by ripple
            if (lRippleEffectCivs.contains(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID())) {
                //unknown civ => player effected
                eventGameData.setup_Event(
                        CFG.langManager.get(String.format("EventMiracleRipplePlayerNameUndiscovered_" + (int)(Math.ceil(Math.random() * (3)))), civRef),
                        "EventMiracleRipple_" + (int)(Math.ceil(Math.random() * (3))),
                        "eventMiracleRipple",
                        CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()
                );
                eventGameData.setup_EventPopUp(
                        true,
                        CFG.langManager.get(String.format("EventMiracleRipplePlayerDescUndiscovered_" + (int)(Math.ceil(Math.random() * (3)))), civRef)
                );
                //setup all outcomes into one cohesive event
                eventGameData.setup_EventDecisions_List(
                        CFG.langManager.get(String.format("EventMiracleRipplePlayerDecisionUndiscovered_" + (int)(Math.ceil(Math.random() * (3))))),
                        eventOutcomes
                );
            } else {
                //unknown civ => player not effected
                eventGameData.setup_Event(
                        CFG.langManager.get(String.format("EventMiracleRippleNameUndiscovered_" + (int)(Math.ceil(Math.random() * (3)))), civRef),
                        "EventMiracleRipple_" + (int)(Math.ceil(Math.random() * (3))),
                        "eventMiracleRipple",
                        CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()
                );
                eventGameData.setup_EventPopUp(
                        true,
                        CFG.langManager.get(String.format("EventMiracleRippleDescUndiscovered_" + (int)(Math.ceil(Math.random() * (3)))), civRef)
                );
                //setup all outcomes into one cohesive event
                eventGameData.setup_EventDecisions_List(
                        CFG.langManager.get(String.format("EventMiracleRippleDecisionUndiscovered_" + (int)(Math.ceil(Math.random() * (3))))),
                        eventOutcomes
                );
            }
        } else {
            //OTHER CIV MIRACLE
            if (lRippleEffectCivs.contains(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID())) {
                //known civ => player effected
                eventGameData.setup_Event(
                        CFG.langManager.get(String.format("EventMiracleRipplePlayerName_" + (int)(Math.ceil(Math.random() * (3)))), civRef),
                        "EventMiracleRipple_" + (int)(Math.ceil(Math.random() * (3))),
                        "eventMiracleRipple",
                        CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()
                );
                eventGameData.setup_EventPopUp(
                        true,
                        CFG.langManager.get(String.format("EventMiracleRipplePlayerDesc_" + (int)(Math.ceil(Math.random() * (3)))), civRef)
                );
                //setup all outcomes into one cohesive event
                eventGameData.setup_EventDecisions_List(
                        CFG.langManager.get(String.format("EventMiracleRipplePlayerDecision_" + (int)(Math.ceil(Math.random() * (3))))),
                        eventOutcomes
                );
            } else {
                //known civ => player not effected
                eventGameData.setup_Event(
                        CFG.langManager.get(String.format("EventMiracleRippleName_" + (int)(Math.ceil(Math.random() * (3)))), civRef),
                        "EventMiracleRipple_" + (int)(Math.ceil(Math.random() * (3))),
                        "eventMiracleRipple",
                        CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()
                );
                eventGameData.setup_EventPopUp(
                        true,
                        CFG.langManager.get(String.format("EventMiracleRippleDesc_" + (int)(Math.ceil(Math.random() * (3)))), civRef)
                );
                //setup all outcomes into one cohesive event
                eventGameData.setup_EventDecisions_List(
                        CFG.langManager.get(String.format("EventMiracleRippleDecision_" + (int)(Math.ceil(Math.random() * (3))))),
                        eventOutcomes
                );
            }
        }

        CFG.dynamicEventManager.addEventIndex(eventGameData);
        event_lastturn = eventGameData;
        event_lastturn.setFired(currentTurn);
    }
}

