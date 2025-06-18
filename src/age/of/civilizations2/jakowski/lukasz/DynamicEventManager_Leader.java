package age.of.civilizations2.jakowski.lukasz;

public class DynamicEventManager_Leader {
    private static final long serialVersionUID = 0L;
    private int civpl = 0;
    private int currentTurn = -1;
    protected Event_GameData event_lastturn;

    DynamicEventManager_Leader() {

    }

    protected void invokeLeaderEvents() {
        //civpl = CFG.getActiveCivInfo();
        //this.invokeRandomLeaderEvent();
    }

    protected static boolean isValidLeader() {
        return (
            (CFG.leader_GameData.getLeaderOfCiv() != null) && //non-null leader
            CFG.leader_GameData.getLeaderOfCiv().getYear() < Game_Calendar.currentYear && //leader in office before time
            CFG.leader_GameData.getLeaderOfCiv().getYear() > Game_Calendar.currentYear - 70 //leader in office not too long ago
        );
    }

    protected static void buildRandomLeader(int iCivID) {
        //CFG.game.getCiv(iCivID).civGameData.setLeader(new LeaderOfCiv_GameData());
        //CFG.game.getCiv(iCivID).civGameData.leaderData.setName("Steve Jobs");
        //CFG.game.getCiv(iCivID).civGameData.leaderData.setImage("bat.png");
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
