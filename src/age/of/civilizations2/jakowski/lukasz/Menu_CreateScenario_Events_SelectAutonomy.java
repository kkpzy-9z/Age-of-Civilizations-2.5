package age.of.civilizations2.jakowski.lukasz;

import java.util.*;

class Menu_CreateScenario_Events_SelectAutonomy extends SliderMenu
{
    protected Menu_CreateScenario_Events_SelectAutonomy() {
        super();

        final List<MenuElement> menuElements = new ArrayList<MenuElement>();
        int nPosY = 0;
        menuElements.add(new Button_Menu_LR_Line(null, -1, 0, nPosY, CFG.GAME_WIDTH, CFG.BUTTON_HEIGHT, true));

        for (AutonomyStatus autonomyStatus : CFG.gameAutonomy.lAutonomy) {
            try {
                menuElements.add(new Button_Menu(autonomyStatus.getName(), (int)(50.0f * CFG.GUI_SCALE), 0, CFG.BUTTON_HEIGHT * (nPosY) + CFG.PADDING * (nPosY + 1), CFG.GAME_WIDTH, CFG.BUTTON_HEIGHT, true));
            } catch (NullPointerException | IndexOutOfBoundsException ex) {
                menuElements.add(new Button_Menu("ERROR", (int)(50.0f * CFG.GUI_SCALE), 0, 0, 0, 0, false));
                menuElements.get(menuElements.size() - 1).setVisible(false);
                continue;
            }
            nPosY++;
        }

        this.initMenuWithBackButton(new SliderMenuTitle(null, CFG.BUTTON_HEIGHT * 3 / 4, false, false), 0, CFG.BUTTON_HEIGHT * 3 / 4, CFG.GAME_WIDTH, CFG.GAME_HEIGHT - (CFG.PADDING * 2) - (CFG.BUTTON_HEIGHT * 3 / 4), menuElements);
        this.updateLanguage();
    }

    @Override
    protected void updateLanguage() {
        this.getTitle().setText(CFG.langManager.get("Autonomy"));
        this.getMenuElement(0).setText("Back");
    }

    @Override
    protected final void actionElement(final int iID) {
        if (iID > 0) {
            ((Event_Outcome_CreateVassal) ((Event_Decision) CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID)).lOutcomes.get(CFG.eventsManager.iCreateEvent_EditConditionID)).setAutonomyStatus(iID - 1);
        }
        this.onBackPressed();
    }

    @Override
    protected void onBackPressed() {
        CFG.eventsManager.selectCivBack();
    }
}
