package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.Gdx;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

class DynamicEventManager_Class implements Serializable {
    private static final long serialVersionUID = 0L;
    protected static int TURNS_UPPER_BOUND = 40;
    protected static int TURNS_LOWER_BOUND = 25;
    protected static float CHANGE_NEEDED_FOR_EVENT = 0.10F;
    protected static float REVOLT_MAX_APPROVAL = 0.10F;
    protected static float UNREST_MAX_APPROVAL = 0.30F;
    protected static float OBEY_MIN_APPROVAL = 0.70F;
    protected static float LOYAL_MIN_APPROVAL = 0.90F;

    protected int PLAYER_SCANAT = -1;
    protected float[] classPerceptionsScan = null;
    protected Event_GameData event_lastturn;

    protected DynamicEventManager_Class() {

    }

    protected void invokeClassEvents() {
        //onstart set first scan dates, archive class perceptions
        if (this.PLAYER_SCANAT == -1) {
            this.PLAYER_SCANAT = Game_Calendar.TURN_ID + (int)CFG.dynamicEventManager.randomF(TURNS_UPPER_BOUND, TURNS_LOWER_BOUND, true);

            this.classPerceptionsScan = new float[3];
            for (int i = 0; i < 3; i++) {
                this.classPerceptionsScan[i] = CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.leaderData.getClassViews(i);
            }
        }
        event_lastturn = null;

        if (Game_Calendar.TURN_ID >= PLAYER_SCANAT) {
            try {
                int classEvent = -1;
                //pick class that has changed the most since archived (regardless of direction)
                for (int i = 0; i < 3; i++) {
                    if (classEvent == -1 || Math.abs(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.leaderData.getClassViews(i) - this.classPerceptionsScan[i]) > Math.abs(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.leaderData.getClassViews(classEvent) - this.classPerceptionsScan[classEvent])) {
                        classEvent = i;
                    }
                }

                boolean posChange = true;
                //if difference is minimal (<= 5%) skip
                if (Math.abs(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.leaderData.getClassViews(classEvent) - this.classPerceptionsScan[classEvent]) <= CHANGE_NEEDED_FOR_EVENT) {
                    this.PLAYER_SCANAT = Game_Calendar.TURN_ID + (int)CFG.dynamicEventManager.randomF(TURNS_UPPER_BOUND, TURNS_LOWER_BOUND, true);
                    return;
                } else if (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.leaderData.getClassViews(classEvent) - this.classPerceptionsScan[classEvent] < 0) {
                    posChange = false;
                }

                switch (classEvent) {
                    case 0: //upper class
                        if (!posChange) {
                            if (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.leaderData.getClassViews(0) <= REVOLT_MAX_APPROVAL) {
                                this.invokeUpperRevoltEvent();
                            } else if (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.leaderData.getClassViews(0) <= UNREST_MAX_APPROVAL) {
                                this.invokeUpperDiscontentEvent();
                            }
                        } else {
                            if (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.leaderData.getClassViews(0) >= LOYAL_MIN_APPROVAL) {
                                this.invokeUpperObeyEvent();
                            } else if (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.leaderData.getClassViews(0) >= OBEY_MIN_APPROVAL) {
                                this.invokeUpperLoyalEvent();
                            }
                        }
                        break;
                    case 1: //middle class
                        if (!posChange) {
                            if (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.leaderData.getClassViews(1) <= REVOLT_MAX_APPROVAL) {
                                this.invokeMiddleRevoltEvent();
                            } else if (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.leaderData.getClassViews(1) <= UNREST_MAX_APPROVAL) {
                                this.invokeMiddleDiscontentEvent();
                            }
                        } else {
                            if (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.leaderData.getClassViews(1) >= LOYAL_MIN_APPROVAL) {
                                this.invokeMiddleObeyEvent();
                            } else if (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.leaderData.getClassViews(1) >= OBEY_MIN_APPROVAL) {
                                this.invokeMiddleLoyalEvent();
                            }
                        }
                        break;
                    case 2: //lower class
                        if (!posChange) {
                            if (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.leaderData.getClassViews(2) <= REVOLT_MAX_APPROVAL) {
                                this.invokeLowerRevoltEvent();
                            } else if (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.leaderData.getClassViews(2) <= UNREST_MAX_APPROVAL) {
                                this.invokeLowerDiscontentEvent();
                            }
                        } else {
                            if (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.leaderData.getClassViews(2) >= LOYAL_MIN_APPROVAL) {
                                this.invokeLowerObeyEvent();
                            } else if (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.leaderData.getClassViews(2) >= OBEY_MIN_APPROVAL) {
                                this.invokeLowerLoyalEvent();
                            }
                        }
                        break;
                }

                //re-archive class perceptions if event fired
                if (this.event_lastturn != null) {
                    for (int i = 0; i < 3; i++) {
                        this.classPerceptionsScan[i] = CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.leaderData.getClassViews(i);
                    }
                }
            } catch (NullPointerException | IndexOutOfBoundsException ex) {
                DynamicEventManager_Leader.safeReplaceLeader(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
                CFG.gameAction.updateClassPerceptions();
            }

            this.PLAYER_SCANAT = Game_Calendar.TURN_ID + (int)CFG.dynamicEventManager.randomF(TURNS_UPPER_BOUND, TURNS_LOWER_BOUND, true);
        }
    }

    protected void invokeUpperDiscontentEvent() {
        Event_GameData eventGameData = new Event_GameData();

        Event_Outcome_UpdateHappinessOfCiv eventOutcomeUpdateHappinessOfCiv = new Event_Outcome_UpdateHappinessOfCiv();
        eventOutcomeUpdateHappinessOfCiv.setCivID(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
        eventOutcomeUpdateHappinessOfCiv.setValue(-15);

        Event_Conditions_CivExist eventConditionsCivExist = new Event_Conditions_CivExist();
        eventConditionsCivExist.iCivID = CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID();

        ArrayList<Event_Outcome> outcomes = new ArrayList<Event_Outcome>();
        outcomes.add(eventOutcomeUpdateHappinessOfCiv);

        List<MenuElement_Hover_v2_Element_Type> tData = new ArrayList<>();
        tData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()));
        tData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("EventUnrestUpperTip"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
        eventOutcomeUpdateHappinessOfCiv.setSecondaryHoverText(tData);

        eventGameData.setup_Event(
                CFG.langManager.get(String.format("EventUnrestUpperName_" + (int)(Math.ceil(Math.random() * (3))))),
                "EventUnrestUpper_" + (int)(Math.ceil(Math.random() * (3))),
                "eventUnrestUpper",
                CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()
        );
        eventGameData.setup_EventPopUp(
                true,
                CFG.langManager.get(String.format("EventUnrestUpperDesc_" + (int)(Math.ceil(Math.random() * (3)))))
        );
        eventGameData.setup_EventDecisions_List(
                CFG.langManager.get(String.format("EventUnrestUpperDecision_" + (int)(Math.ceil(Math.random() * (3))))),
                outcomes
        );
        eventGameData.setup_EventTriggers(
                eventConditionsCivExist
        );

        CFG.dynamicEventManager.addEventIndex(eventGameData);
        event_lastturn = eventGameData;
        event_lastturn.setFired(Game_Calendar.TURN_ID);
    }

    protected void invokeMiddleDiscontentEvent() {
        Event_GameData eventGameData = new Event_GameData();

        Event_Outcome_UpdateHappinessOfCiv eventOutcomeUpdateHappinessOfCiv = new Event_Outcome_UpdateHappinessOfCiv();
        eventOutcomeUpdateHappinessOfCiv.setCivID(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
        eventOutcomeUpdateHappinessOfCiv.setValue(-15);

        Event_Conditions_CivExist eventConditionsCivExist = new Event_Conditions_CivExist();
        eventConditionsCivExist.iCivID = CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID();

        ArrayList<Event_Outcome> outcomes = new ArrayList<Event_Outcome>();
        outcomes.add(eventOutcomeUpdateHappinessOfCiv);

        List<MenuElement_Hover_v2_Element_Type> tData = new ArrayList<>();
        tData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()));
        tData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("EventUnrestMiddleTip"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
        eventOutcomeUpdateHappinessOfCiv.setSecondaryHoverText(tData);

        eventGameData.setup_Event(
                CFG.langManager.get(String.format("EventUnrestMiddleName_" + (int)(Math.ceil(Math.random() * (3))))),
                "EventUnrestMiddle_" + (int)(Math.ceil(Math.random() * (3))),
                "eventUnrestMiddle",
                CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()
        );
        eventGameData.setup_EventPopUp(
                true,
                CFG.langManager.get(String.format("EventUnrestMiddleDesc_" + (int)(Math.ceil(Math.random() * (3)))))
        );
        eventGameData.setup_EventDecisions_List(
                CFG.langManager.get(String.format("EventUnrestMiddleDecision_" + (int)(Math.ceil(Math.random() * (3))))),
                outcomes
        );
        eventGameData.setup_EventTriggers(
                eventConditionsCivExist
        );

        CFG.dynamicEventManager.addEventIndex(eventGameData);
        event_lastturn = eventGameData;
        event_lastturn.setFired(Game_Calendar.TURN_ID);
    }

    protected void invokeLowerDiscontentEvent() {
        Event_GameData eventGameData = new Event_GameData();

        Event_Outcome_UpdateHappinessOfCiv eventOutcomeUpdateHappinessOfCiv = new Event_Outcome_UpdateHappinessOfCiv();
        eventOutcomeUpdateHappinessOfCiv.setCivID(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
        eventOutcomeUpdateHappinessOfCiv.setValue(-15);

        Event_Conditions_CivExist eventConditionsCivExist = new Event_Conditions_CivExist();
        eventConditionsCivExist.iCivID = CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID();

        ArrayList<Event_Outcome> outcomes = new ArrayList<Event_Outcome>();
        outcomes.add(eventOutcomeUpdateHappinessOfCiv);

        List<MenuElement_Hover_v2_Element_Type> tData = new ArrayList<>();
        tData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()));
        tData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("EventUnrestLowerTip"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
        eventOutcomeUpdateHappinessOfCiv.setSecondaryHoverText(tData);

        eventGameData.setup_Event(
                CFG.langManager.get(String.format("EventUnrestLowerName_" + (int)(Math.ceil(Math.random() * (3))))),
                "EventUnrestLower_" + (int)(Math.ceil(Math.random() * (3))),
                "eventUnrestLower",
                CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()
        );
        eventGameData.setup_EventPopUp(
                true,
                CFG.langManager.get(String.format("EventUnrestLowerDesc_" + (int)(Math.ceil(Math.random() * (3)))))
        );
        eventGameData.setup_EventDecisions_List(
                CFG.langManager.get(String.format("EventUnrestLowerDecision_" + (int)(Math.ceil(Math.random() * (3))))),
                outcomes
        );
        eventGameData.setup_EventTriggers(
                eventConditionsCivExist
        );

        CFG.dynamicEventManager.addEventIndex(eventGameData);
        event_lastturn = eventGameData;
        event_lastturn.setFired(Game_Calendar.TURN_ID);
    }

    protected void invokeUpperRevoltEvent() {
        Event_GameData eventGameData = new Event_GameData();

        Event_Outcome_UpdateHappinessOfCiv eventOutcomeUpdateHappinessOfCiv = new Event_Outcome_UpdateHappinessOfCiv();
        eventOutcomeUpdateHappinessOfCiv.setCivID(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
        eventOutcomeUpdateHappinessOfCiv.setValue(-20);

        Event_Outcome_UpdateEconomyOfCiv eventOutcomeUpdateEconomyOfCiv = new Event_Outcome_UpdateEconomyOfCiv();
        eventOutcomeUpdateEconomyOfCiv.setCivID(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
        eventOutcomeUpdateEconomyOfCiv.setValue(-25);

        Event_Outcome_UpdateClass eventOutcomeUpdateClass = new Event_Outcome_UpdateClass();
        eventOutcomeUpdateClass.setCivID(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
        eventOutcomeUpdateClass.setClassIndex(0);
        eventOutcomeUpdateClass.setChangePerc(0.60F);

        Event_Conditions_CivExist eventConditionsCivExist = new Event_Conditions_CivExist();
        eventConditionsCivExist.iCivID = CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID();

        ArrayList<Event_Outcome> outcomes = new ArrayList<Event_Outcome>();
        outcomes.add(eventOutcomeUpdateHappinessOfCiv);
        outcomes.add(eventOutcomeUpdateEconomyOfCiv);
        outcomes.add(eventOutcomeUpdateClass);

        List<MenuElement_Hover_v2_Element_Type> tData = new ArrayList<>();
        tData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()));
        tData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("EventRevoltUpperTip"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
        eventOutcomeUpdateClass.setSecondaryHoverText(tData);

        eventGameData.setup_Event(
                CFG.langManager.get(String.format("EventRevoltUpperName_" + (int)(Math.ceil(Math.random() * (3))))),
                "EventRevoltUpper_" + (int)(Math.ceil(Math.random() * (3))),
                "eventRevoltUpper",
                CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()
        );
        eventGameData.setup_EventPopUp(
                true,
                CFG.langManager.get(String.format("EventRevoltUpperDesc_" + (int)(Math.ceil(Math.random() * (3)))))
        );
        eventGameData.setup_EventDecisions_List(
                CFG.langManager.get(String.format("EventRevoltUpperDecision_" + (int)(Math.ceil(Math.random() * (3))))),
                outcomes
        );
        eventGameData.setup_EventTriggers(
                eventConditionsCivExist
        );

        CFG.dynamicEventManager.addEventIndex(eventGameData);
        event_lastturn = eventGameData;
        event_lastturn.setFired(Game_Calendar.TURN_ID);

        try {
            ArrayList<Integer> defIdeologies = new ArrayList<>();
            List<Boolean> canChange = CFG.ideologiesManager.canChangeToIdeology(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());

            //add ideologies with "default" (despotism) AI_TYPE
            for (int i = 0; i < CFG.ideologiesManager.getIdeologiesSize(); i++) {
                if (canChange.get(i)
                        && "default".equalsIgnoreCase(CFG.ideologiesManager.getIdeology(i).AI_TYPE)
                        && CFG.ideologiesManager.canBeAdded(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), i)) {
                    defIdeologies.add(i);
                }
            }

            //add any valid ideologies if no preference AI_TYPE
            if (defIdeologies.isEmpty()) {
                for (int i = 0; i < CFG.ideologiesManager.getIdeologiesSize(); i++) {
                    if (canChange.get(i)
                            && CFG.ideologiesManager.canBeAdded(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), i)) {
                        defIdeologies.add(i);
                    }
                }
            }

            CFG.dynamicEventManager.eventManagerCivilWar.invokeCivilWar(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), defIdeologies.get((int) CFG.dynamicEventManager.randomF(0, defIdeologies.size() - 1, true)));
        } catch (NullPointerException ex) {
            return;
        }
    }

    protected void invokeMiddleRevoltEvent() {
        Event_GameData eventGameData = new Event_GameData();

        Event_Outcome_UpdateHappinessOfCiv eventOutcomeUpdateHappinessOfCiv = new Event_Outcome_UpdateHappinessOfCiv();
        eventOutcomeUpdateHappinessOfCiv.setCivID(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
        eventOutcomeUpdateHappinessOfCiv.setValue(-25);

        Event_Outcome_UpdateEconomyOfCiv eventOutcomeUpdateEconomyOfCiv = new Event_Outcome_UpdateEconomyOfCiv();
        eventOutcomeUpdateEconomyOfCiv.setCivID(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
        eventOutcomeUpdateEconomyOfCiv.setValue(-60);

        Event_Outcome_UpdateClass eventOutcomeUpdateClass = new Event_Outcome_UpdateClass();
        eventOutcomeUpdateClass.setCivID(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
        eventOutcomeUpdateClass.setClassIndex(1);
        eventOutcomeUpdateClass.setChangePerc(0.60F);

        Event_Conditions_CivExist eventConditionsCivExist = new Event_Conditions_CivExist();
        eventConditionsCivExist.iCivID = CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID();

        ArrayList<Event_Outcome> outcomes = new ArrayList<Event_Outcome>();
        outcomes.add(eventOutcomeUpdateHappinessOfCiv);
        outcomes.add(eventOutcomeUpdateEconomyOfCiv);
        outcomes.add(eventOutcomeUpdateClass);

        List<MenuElement_Hover_v2_Element_Type> tData = new ArrayList<>();
        tData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()));
        tData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("EventRevoltMiddleTip"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
        eventOutcomeUpdateClass.setSecondaryHoverText(tData);

        eventGameData.setup_Event(
                CFG.langManager.get(String.format("EventRevoltMiddleName_" + (int)(Math.ceil(Math.random() * (3))))),
                "EventRevoltMiddle_" + (int)(Math.ceil(Math.random() * (3))),
                "eventRevoltMiddle",
                CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()
        );
        eventGameData.setup_EventPopUp(
                true,
                CFG.langManager.get(String.format("EventRevoltMiddleDesc_" + (int)(Math.ceil(Math.random() * (3)))))
        );
        eventGameData.setup_EventDecisions_List(
                CFG.langManager.get(String.format("EventRevoltMiddleDecision_" + (int)(Math.ceil(Math.random() * (3))))),
                outcomes
        );
        eventGameData.setup_EventTriggers(
                eventConditionsCivExist
        );

        CFG.dynamicEventManager.addEventIndex(eventGameData);
        event_lastturn = eventGameData;
        event_lastturn.setFired(Game_Calendar.TURN_ID);
    }

    protected void invokeLowerRevoltEvent() {
        Event_GameData eventGameData = new Event_GameData();

        Event_Outcome_UpdateHappinessOfCiv eventOutcomeUpdateHappinessOfCiv = new Event_Outcome_UpdateHappinessOfCiv();
        eventOutcomeUpdateHappinessOfCiv.setCivID(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
        eventOutcomeUpdateHappinessOfCiv.setValue(-20);

        Event_Outcome_UpdateEconomyOfCiv eventOutcomeUpdateEconomyOfCiv = new Event_Outcome_UpdateEconomyOfCiv();
        eventOutcomeUpdateEconomyOfCiv.setCivID(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
        eventOutcomeUpdateEconomyOfCiv.setValue(-40);

        Event_Outcome_UpdateClass eventOutcomeUpdateClass = new Event_Outcome_UpdateClass();
        eventOutcomeUpdateClass.setCivID(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
        eventOutcomeUpdateClass.setClassIndex(2);
        eventOutcomeUpdateClass.setChangePerc(0.60F);

        Event_Conditions_CivExist eventConditionsCivExist = new Event_Conditions_CivExist();
        eventConditionsCivExist.iCivID = CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID();

        ArrayList<Event_Outcome> outcomes = new ArrayList<Event_Outcome>();
        outcomes.add(eventOutcomeUpdateHappinessOfCiv);
        outcomes.add(eventOutcomeUpdateEconomyOfCiv);
        outcomes.add(eventOutcomeUpdateClass);

        List<MenuElement_Hover_v2_Element_Type> tData = new ArrayList<>();
        tData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()));
        tData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("EventRevoltLowerTip"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
        eventOutcomeUpdateClass.setSecondaryHoverText(tData);

        eventGameData.setup_Event(
                CFG.langManager.get(String.format("EventRevoltLowerName_" + (int)(Math.ceil(Math.random() * (3))))),
                "EventRevoltLower_" + (int)(Math.ceil(Math.random() * (3))),
                "eventRevoltLower",
                CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()
        );
        eventGameData.setup_EventPopUp(
                true,
                CFG.langManager.get(String.format("EventRevoltLowerDesc_" + (int)(Math.ceil(Math.random() * (3)))))
        );
        eventGameData.setup_EventDecisions_List(
                CFG.langManager.get(String.format("EventRevoltLowerDecision_" + (int)(Math.ceil(Math.random() * (3))))),
                outcomes
        );
        eventGameData.setup_EventTriggers(
                eventConditionsCivExist
        );

        CFG.dynamicEventManager.addEventIndex(eventGameData);
        event_lastturn = eventGameData;
        event_lastturn.setFired(Game_Calendar.TURN_ID);

        try {
            ArrayList<Integer> defIdeologies = new ArrayList<>();
            List<Boolean> canChange = CFG.ideologiesManager.canChangeToIdeology(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());

            //add ideologies with communism, democracy AI_TYPE
            for (int i = 0; i < CFG.ideologiesManager.getIdeologiesSize(); i++) {
                if (canChange.get(i)
                        && ("communism".equalsIgnoreCase(CFG.ideologiesManager.getIdeology(i).AI_TYPE) || "democracy".equalsIgnoreCase(CFG.ideologiesManager.getIdeology(i).AI_TYPE))
                        && CFG.ideologiesManager.canBeAdded(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), i)) {
                    defIdeologies.add(i);
                }
            }

            //add any valid ideologies if no preference AI_TYPE
            if (defIdeologies.isEmpty()) {
                for (int i = 0; i < CFG.ideologiesManager.getIdeologiesSize(); i++) {
                    if (canChange.get(i)
                            && CFG.ideologiesManager.canBeAdded(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), i)) {
                        defIdeologies.add(i);
                    }
                }
            }

            CFG.dynamicEventManager.eventManagerCivilWar.invokeCivilWar(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), defIdeologies.get((int) CFG.dynamicEventManager.randomF(0, defIdeologies.size() - 1, true)));
        } catch (NullPointerException ex) {
            return;
        }
    }

    protected void invokeUpperObeyEvent() {
        Event_GameData eventGameData = new Event_GameData();

        Event_Outcome_UpdateHappinessOfCiv eventOutcomeUpdateHappinessOfCiv = new Event_Outcome_UpdateHappinessOfCiv();
        eventOutcomeUpdateHappinessOfCiv.setCivID(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
        eventOutcomeUpdateHappinessOfCiv.setValue(20);

        Event_Outcome_UpdateEconomyOfCiv eventOutcomeUpdateEconomyOfCiv = new Event_Outcome_UpdateEconomyOfCiv();
        eventOutcomeUpdateEconomyOfCiv.setCivID(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
        eventOutcomeUpdateEconomyOfCiv.setValue(15);

        Event_Outcome_UpdateClass eventOutcomeUpdateClass = new Event_Outcome_UpdateClass();
        eventOutcomeUpdateClass.setCivID(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
        eventOutcomeUpdateClass.setClassIndex(0);
        eventOutcomeUpdateClass.setChangePerc(0.05F);

        Event_Conditions_CivExist eventConditionsCivExist = new Event_Conditions_CivExist();
        eventConditionsCivExist.iCivID = CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID();

        ArrayList<Event_Outcome> outcomes = new ArrayList<Event_Outcome>();
        outcomes.add(eventOutcomeUpdateHappinessOfCiv);
        outcomes.add(eventOutcomeUpdateEconomyOfCiv);
        outcomes.add(eventOutcomeUpdateClass);

        List<MenuElement_Hover_v2_Element_Type> tData = new ArrayList<>();
        tData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()));
        tData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("EventObeyUpperTip"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
        eventOutcomeUpdateClass.setSecondaryHoverText(tData);

        eventGameData.setup_Event(
                CFG.langManager.get(String.format("EventObeyUpperName_" + (int)(Math.ceil(Math.random() * (3))))),
                "EventObeyUpper_" + (int)(Math.ceil(Math.random() * (3))),
                "eventObeyUpper",
                CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()
        );
        eventGameData.setup_EventPopUp(
                true,
                CFG.langManager.get(String.format("EventObeyUpperDesc_" + (int)(Math.ceil(Math.random() * (3)))))
        );
        eventGameData.setup_EventDecisions_List(
                CFG.langManager.get(String.format("EventObeyUpperDecision_" + (int)(Math.ceil(Math.random() * (3))))),
                outcomes
        );
        eventGameData.setup_EventTriggers(
                eventConditionsCivExist
        );

        CFG.dynamicEventManager.addEventIndex(eventGameData);
        event_lastturn = eventGameData;
        event_lastturn.setFired(Game_Calendar.TURN_ID);
    }

    protected void invokeMiddleObeyEvent() {
        Event_GameData eventGameData = new Event_GameData();

        Event_Outcome_UpdateHappinessOfCiv eventOutcomeUpdateHappinessOfCiv = new Event_Outcome_UpdateHappinessOfCiv();
        eventOutcomeUpdateHappinessOfCiv.setCivID(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
        eventOutcomeUpdateHappinessOfCiv.setValue(20);

        Event_Outcome_UpdateEconomyOfCiv eventOutcomeUpdateEconomyOfCiv = new Event_Outcome_UpdateEconomyOfCiv();
        eventOutcomeUpdateEconomyOfCiv.setCivID(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
        eventOutcomeUpdateEconomyOfCiv.setValue(15);

        Event_Outcome_UpdateClass eventOutcomeUpdateClass = new Event_Outcome_UpdateClass();
        eventOutcomeUpdateClass.setCivID(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
        eventOutcomeUpdateClass.setClassIndex(1);
        eventOutcomeUpdateClass.setChangePerc(0.05F);

        Event_Conditions_CivExist eventConditionsCivExist = new Event_Conditions_CivExist();
        eventConditionsCivExist.iCivID = CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID();

        ArrayList<Event_Outcome> outcomes = new ArrayList<Event_Outcome>();
        outcomes.add(eventOutcomeUpdateHappinessOfCiv);
        outcomes.add(eventOutcomeUpdateEconomyOfCiv);
        outcomes.add(eventOutcomeUpdateClass);

        List<MenuElement_Hover_v2_Element_Type> tData = new ArrayList<>();
        tData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()));
        tData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("EventObeyMiddleTip"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
        eventOutcomeUpdateClass.setSecondaryHoverText(tData);

        eventGameData.setup_Event(
                CFG.langManager.get(String.format("EventObeyMiddleName_" + (int)(Math.ceil(Math.random() * (3))))),
                "EventObeyMiddle_" + (int)(Math.ceil(Math.random() * (3))),
                "eventObeyMiddle",
                CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()
        );
        eventGameData.setup_EventPopUp(
                true,
                CFG.langManager.get(String.format("EventObeyMiddleDesc_" + (int)(Math.ceil(Math.random() * (3)))))
        );
        eventGameData.setup_EventDecisions_List(
                CFG.langManager.get(String.format("EventObeyMiddleDecision_" + (int)(Math.ceil(Math.random() * (3))))),
                outcomes
        );
        eventGameData.setup_EventTriggers(
                eventConditionsCivExist
        );

        CFG.dynamicEventManager.addEventIndex(eventGameData);
        event_lastturn = eventGameData;
        event_lastturn.setFired(Game_Calendar.TURN_ID);
    }

    protected void invokeLowerObeyEvent() {
        Event_GameData eventGameData = new Event_GameData();

        Event_Outcome_UpdateHappinessOfCiv eventOutcomeUpdateHappinessOfCiv = new Event_Outcome_UpdateHappinessOfCiv();
        eventOutcomeUpdateHappinessOfCiv.setCivID(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
        eventOutcomeUpdateHappinessOfCiv.setValue(20);

        Event_Outcome_UpdateEconomyOfCiv eventOutcomeUpdateEconomyOfCiv = new Event_Outcome_UpdateEconomyOfCiv();
        eventOutcomeUpdateEconomyOfCiv.setCivID(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
        eventOutcomeUpdateEconomyOfCiv.setValue(15);

        Event_Outcome_UpdateClass eventOutcomeUpdateClass = new Event_Outcome_UpdateClass();
        eventOutcomeUpdateClass.setCivID(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
        eventOutcomeUpdateClass.setClassIndex(2);
        eventOutcomeUpdateClass.setChangePerc(0.05F);

        Event_Conditions_CivExist eventConditionsCivExist = new Event_Conditions_CivExist();
        eventConditionsCivExist.iCivID = CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID();

        ArrayList<Event_Outcome> outcomes = new ArrayList<Event_Outcome>();
        outcomes.add(eventOutcomeUpdateHappinessOfCiv);
        outcomes.add(eventOutcomeUpdateEconomyOfCiv);
        outcomes.add(eventOutcomeUpdateClass);

        List<MenuElement_Hover_v2_Element_Type> tData = new ArrayList<>();
        tData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()));
        tData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("EventObeyLowerTip"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
        eventOutcomeUpdateClass.setSecondaryHoverText(tData);

        eventGameData.setup_Event(
                CFG.langManager.get(String.format("EventObeyLowerName_" + (int)(Math.ceil(Math.random() * (3))))),
                "EventObeyLower_" + (int)(Math.ceil(Math.random() * (3))),
                "eventObeyLower",
                CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()
        );
        eventGameData.setup_EventPopUp(
                true,
                CFG.langManager.get(String.format("EventObeyLowerDesc_" + (int)(Math.ceil(Math.random() * (3)))))
        );
        eventGameData.setup_EventDecisions_List(
                CFG.langManager.get(String.format("EventObeyLowerDecision_" + (int)(Math.ceil(Math.random() * (3))))),
                outcomes
        );
        eventGameData.setup_EventTriggers(
                eventConditionsCivExist
        );

        CFG.dynamicEventManager.addEventIndex(eventGameData);
        event_lastturn = eventGameData;
        event_lastturn.setFired(Game_Calendar.TURN_ID);
    }


    protected void invokeUpperLoyalEvent() {
        Event_GameData eventGameData = new Event_GameData();

        Event_Outcome_UpdateHappinessOfCiv eventOutcomeUpdateHappinessOfCiv = new Event_Outcome_UpdateHappinessOfCiv();
        eventOutcomeUpdateHappinessOfCiv.setCivID(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
        eventOutcomeUpdateHappinessOfCiv.setValue(25);

        Event_Outcome_UpdateEconomyOfCiv eventOutcomeUpdateEconomyOfCiv = new Event_Outcome_UpdateEconomyOfCiv();
        eventOutcomeUpdateEconomyOfCiv.setCivID(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
        eventOutcomeUpdateEconomyOfCiv.setValue(30);

        Event_Outcome_UpdateClass eventOutcomeUpdateClass = new Event_Outcome_UpdateClass();
        eventOutcomeUpdateClass.setCivID(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
        eventOutcomeUpdateClass.setClassIndex(0);
        eventOutcomeUpdateClass.setChangePerc(0.05F);

        Event_Conditions_CivExist eventConditionsCivExist = new Event_Conditions_CivExist();
        eventConditionsCivExist.iCivID = CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID();

        ArrayList<Event_Outcome> outcomes = new ArrayList<Event_Outcome>();
        outcomes.add(eventOutcomeUpdateHappinessOfCiv);
        outcomes.add(eventOutcomeUpdateEconomyOfCiv);
        outcomes.add(eventOutcomeUpdateClass);

        List<MenuElement_Hover_v2_Element_Type> tData = new ArrayList<>();
        tData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()));
        tData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("EventLoyalUpperTip"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
        eventOutcomeUpdateClass.setSecondaryHoverText(tData);

        eventGameData.setup_Event(
                CFG.langManager.get(String.format("EventLoyalUpperName_" + (int)(Math.ceil(Math.random() * (3))))),
                "EventLoyalUpper_" + (int)(Math.ceil(Math.random() * (3))),
                "eventLoyalUpper",
                CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()
        );
        eventGameData.setup_EventPopUp(
                true,
                CFG.langManager.get(String.format("EventLoyalUpperDesc_" + (int)(Math.ceil(Math.random() * (3)))))
        );
        eventGameData.setup_EventDecisions_List(
                CFG.langManager.get(String.format("EventLoyalUpperDecision_" + (int)(Math.ceil(Math.random() * (3))))),
                outcomes
        );
        eventGameData.setup_EventTriggers(
                eventConditionsCivExist
        );

        CFG.dynamicEventManager.addEventIndex(eventGameData);
        event_lastturn = eventGameData;
        event_lastturn.setFired(Game_Calendar.TURN_ID);
    }

    protected void invokeMiddleLoyalEvent() {
        Event_GameData eventGameData = new Event_GameData();

        Event_Outcome_UpdateHappinessOfCiv eventOutcomeUpdateHappinessOfCiv = new Event_Outcome_UpdateHappinessOfCiv();
        eventOutcomeUpdateHappinessOfCiv.setCivID(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
        eventOutcomeUpdateHappinessOfCiv.setValue(25);

        Event_Outcome_UpdateEconomyOfCiv eventOutcomeUpdateEconomyOfCiv = new Event_Outcome_UpdateEconomyOfCiv();
        eventOutcomeUpdateEconomyOfCiv.setCivID(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
        eventOutcomeUpdateEconomyOfCiv.setValue(30);

        Event_Outcome_UpdateClass eventOutcomeUpdateClass = new Event_Outcome_UpdateClass();
        eventOutcomeUpdateClass.setCivID(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
        eventOutcomeUpdateClass.setClassIndex(1);
        eventOutcomeUpdateClass.setChangePerc(0.05F);

        Event_Conditions_CivExist eventConditionsCivExist = new Event_Conditions_CivExist();
        eventConditionsCivExist.iCivID = CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID();

        ArrayList<Event_Outcome> outcomes = new ArrayList<Event_Outcome>();
        outcomes.add(eventOutcomeUpdateHappinessOfCiv);
        outcomes.add(eventOutcomeUpdateEconomyOfCiv);
        outcomes.add(eventOutcomeUpdateClass);

        List<MenuElement_Hover_v2_Element_Type> tData = new ArrayList<>();
        tData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()));
        tData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("EventLoyalMiddleTip"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
        eventOutcomeUpdateClass.setSecondaryHoverText(tData);

        eventGameData.setup_Event(
                CFG.langManager.get(String.format("EventLoyalMiddleName_" + (int)(Math.ceil(Math.random() * (3))))),
                "EventLoyalMiddle_" + (int)(Math.ceil(Math.random() * (3))),
                "eventLoyalMiddle",
                CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()
        );
        eventGameData.setup_EventPopUp(
                true,
                CFG.langManager.get(String.format("EventLoyalMiddleDesc_" + (int)(Math.ceil(Math.random() * (3)))))
        );
        eventGameData.setup_EventDecisions_List(
                CFG.langManager.get(String.format("EventLoyalMiddleDecision_" + (int)(Math.ceil(Math.random() * (3))))),
                outcomes
        );
        eventGameData.setup_EventTriggers(
                eventConditionsCivExist
        );

        CFG.dynamicEventManager.addEventIndex(eventGameData);
        event_lastturn = eventGameData;
        event_lastturn.setFired(Game_Calendar.TURN_ID);
    }

    protected void invokeLowerLoyalEvent() {
        Event_GameData eventGameData = new Event_GameData();

        Event_Outcome_UpdateHappinessOfCiv eventOutcomeUpdateHappinessOfCiv = new Event_Outcome_UpdateHappinessOfCiv();
        eventOutcomeUpdateHappinessOfCiv.setCivID(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
        eventOutcomeUpdateHappinessOfCiv.setValue(25);

        Event_Outcome_UpdateEconomyOfCiv eventOutcomeUpdateEconomyOfCiv = new Event_Outcome_UpdateEconomyOfCiv();
        eventOutcomeUpdateEconomyOfCiv.setCivID(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
        eventOutcomeUpdateEconomyOfCiv.setValue(30);

        Event_Outcome_UpdateClass eventOutcomeUpdateClass = new Event_Outcome_UpdateClass();
        eventOutcomeUpdateClass.setCivID(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
        eventOutcomeUpdateClass.setClassIndex(2);
        eventOutcomeUpdateClass.setChangePerc(0.05F);

        Event_Conditions_CivExist eventConditionsCivExist = new Event_Conditions_CivExist();
        eventConditionsCivExist.iCivID = CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID();

        ArrayList<Event_Outcome> outcomes = new ArrayList<Event_Outcome>();
        outcomes.add(eventOutcomeUpdateHappinessOfCiv);
        outcomes.add(eventOutcomeUpdateEconomyOfCiv);
        outcomes.add(eventOutcomeUpdateClass);

        List<MenuElement_Hover_v2_Element_Type> tData = new ArrayList<>();
        tData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()));
        tData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("EventLoyalLowerTip"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
        eventOutcomeUpdateClass.setSecondaryHoverText(tData);

        eventGameData.setup_Event(
                CFG.langManager.get(String.format("EventLoyalLowerName_" + (int)(Math.ceil(Math.random() * (3))))),
                "EventLoyalLower_" + (int)(Math.ceil(Math.random() * (3))),
                "eventLoyalLower",
                CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()
        );
        eventGameData.setup_EventPopUp(
                true,
                CFG.langManager.get(String.format("EventLoyalLowerDesc_" + (int)(Math.ceil(Math.random() * (3)))))
        );
        eventGameData.setup_EventDecisions_List(
                CFG.langManager.get(String.format("EventLoyalLowerDecision_" + (int)(Math.ceil(Math.random() * (3))))),
                outcomes
        );
        eventGameData.setup_EventTriggers(
                eventConditionsCivExist
        );

        CFG.dynamicEventManager.addEventIndex(eventGameData);
        event_lastturn = eventGameData;
        event_lastturn.setFired(Game_Calendar.TURN_ID);
    }
}
