package age.of.civilizations2.jakowski.lukasz;

import java.util.*;
import com.badlogic.gdx.graphics.g2d.*;

class Menu_CreateScenario_Events_Out_CreateAVassal extends SliderMenu
{
    protected Menu_CreateScenario_Events_Out_CreateAVassal() {
        super();
        final List<MenuElement> menuElements = new ArrayList<MenuElement>();
        int tY = CFG.PADDING;
        menuElements.add(new Button_Menu_LR_Line(null, -1, 0, tY, CFG.GAME_WIDTH, CFG.BUTTON_HEIGHT, true));
        menuElements.add(new Button_Menu(null, (int)(50.0f * CFG.GUI_SCALE), 0, tY, CFG.GAME_WIDTH, CFG.BUTTON_HEIGHT, true));
        tY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
        menuElements.add(new Button_Menu(null, (int)(50.0f * CFG.GUI_SCALE), 0, tY, CFG.GAME_WIDTH, CFG.BUTTON_HEIGHT, true));
        tY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
        menuElements.add(new Button_Menu(null, (int)(50.0f * CFG.GUI_SCALE), 0, tY, CFG.GAME_WIDTH, CFG.BUTTON_HEIGHT, true));
        tY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
        menuElements.add(new Button_Menu(null, (int)(50.0f * CFG.GUI_SCALE), 0, tY, CFG.GAME_WIDTH, CFG.BUTTON_HEIGHT, true));
        tY += menuElements.get(menuElements.size() - 1).getHeight() + CFG.PADDING;
        this.initMenuWithBackButton(new SliderMenuTitle(null, CFG.BUTTON_HEIGHT * 3 / 4, false, false), 0, CFG.BUTTON_HEIGHT * 3 / 4, CFG.GAME_WIDTH, CFG.GAME_HEIGHT - CFG.BUTTON_HEIGHT * 3 / 4, menuElements);
        this.updateLanguage();
    }
    
    @Override
    protected void updateLanguage() {
        this.getMenuElement(0).setText(CFG.langManager.get("Save"));
        try {
            this.getMenuElement(1).setText((CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.get(CFG.eventsManager.iCreateEvent_EditConditionID).getCivID() >= 0) ? (CFG.langManager.get("Lord") + ": " + CFG.game.getCiv(CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.get(CFG.eventsManager.iCreateEvent_EditConditionID).getCivID()).getCivName()) : CFG.langManager.get("Lord"));
        }
        catch (final IndexOutOfBoundsException ex) {
            this.getMenuElement(1).setText(CFG.langManager.get("Lord"));
        }
        try {
            this.getMenuElement(2).setText((CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.get(CFG.eventsManager.iCreateEvent_EditConditionID).getCivID2() >= 0) ? (CFG.langManager.get("Vassal") + ": " + CFG.game.getCiv(CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.get(CFG.eventsManager.iCreateEvent_EditConditionID).getCivID2()).getCivName()) : CFG.langManager.get("Vassal"));
        }
        catch (final IndexOutOfBoundsException ex) {
            this.getMenuElement(2).setText(CFG.langManager.get("Vassal"));
        }

        this.getMenuElement(3).setText(CFG.langManager.get("SelectProvinces") + ": " + CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.get(CFG.eventsManager.iCreateEvent_EditConditionID).getProvinces().size());

        try {
            this.getMenuElement(4).setText(((Event_Outcome_CreateVassal) CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.get(CFG.eventsManager.iCreateEvent_EditConditionID)).getAutonomyStatus() >= 0 ? (CFG.langManager.get("Autonomy") + ": " + CFG.gameAutonomy.getAutonomy(((Event_Outcome_CreateVassal) CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.get(CFG.eventsManager.iCreateEvent_EditConditionID)).getAutonomyStatus()).getName()) : CFG.langManager.get("Autonomy"));
        }
        catch (final IndexOutOfBoundsException | NullPointerException ex) {
            this.getMenuElement(4).setText(CFG.langManager.get("Autonomy"));
        }

        this.getTitle().setText(CFG.langManager.get("CreateAVassal"));
    }
    
    @Override
    protected void draw(final SpriteBatch oSB, final int iTranslateX, final int iTranslateY, final boolean sliderMenuIsActive) {
        super.beginClip(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
        super.drawMenu(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
        int tempButtonID = 1;
        try {
            CFG.game.getCiv(CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.get(CFG.eventsManager.iCreateEvent_EditConditionID).getCivID()).getFlag().draw(oSB, this.getMenuElement(tempButtonID).getPosX() + this.getMenuElement(tempButtonID).getTextPos() / 2 - CFG.CIV_FLAG_WIDTH / 2 + iTranslateX, -CFG.game.getCiv(CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.get(CFG.eventsManager.iCreateEvent_EditConditionID).getCivID()).getFlag().getHeight() + this.getMenuElement(tempButtonID).getPosY() + this.getMenuPosY() + this.getMenuElement(tempButtonID).getHeight() / 2 - CFG.CIV_FLAG_HEIGHT / 2 + iTranslateY, CFG.CIV_FLAG_WIDTH, CFG.CIV_FLAG_HEIGHT);
        }
        catch (final IndexOutOfBoundsException ex) {
            ImageManager.getImage(Images.randomCivilizationFlag).draw(oSB, this.getMenuElement(tempButtonID).getPosX() + this.getMenuElement(tempButtonID).getTextPos() / 2 - CFG.CIV_FLAG_WIDTH / 2 + iTranslateX, this.getMenuElement(tempButtonID).getPosY() + this.getMenuPosY() - ImageManager.getImage(Images.randomCivilizationFlag).getHeight() + this.getMenuElement(tempButtonID).getHeight() / 2 - CFG.CIV_FLAG_HEIGHT / 2 + iTranslateY, CFG.CIV_FLAG_WIDTH, CFG.CIV_FLAG_HEIGHT);
        }
        ImageManager.getImage(Images.flag_rect).draw(oSB, this.getMenuElement(tempButtonID).getPosX() + this.getMenuElement(tempButtonID).getTextPos() / 2 - CFG.CIV_FLAG_WIDTH / 2 + iTranslateX, this.getMenuElement(tempButtonID).getPosY() + this.getMenuPosY() + this.getMenuElement(tempButtonID).getHeight() / 2 - CFG.CIV_FLAG_HEIGHT / 2 + iTranslateY);
        tempButtonID = 2;
        try {
            CFG.game.getCiv(CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.get(CFG.eventsManager.iCreateEvent_EditConditionID).getCivID2()).getFlag().draw(oSB, this.getMenuElement(tempButtonID).getPosX() + this.getMenuElement(tempButtonID).getTextPos() / 2 - CFG.CIV_FLAG_WIDTH / 2 + iTranslateX, -CFG.game.getCiv(CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID).lOutcomes.get(CFG.eventsManager.iCreateEvent_EditConditionID).getCivID2()).getFlag().getHeight() + this.getMenuElement(tempButtonID).getPosY() + this.getMenuPosY() + this.getMenuElement(tempButtonID).getHeight() / 2 - CFG.CIV_FLAG_HEIGHT / 2 + iTranslateY, CFG.CIV_FLAG_WIDTH, CFG.CIV_FLAG_HEIGHT);
        }
        catch (final IndexOutOfBoundsException ex) {
            ImageManager.getImage(Images.randomCivilizationFlag).draw(oSB, this.getMenuElement(tempButtonID).getPosX() + this.getMenuElement(tempButtonID).getTextPos() / 2 - CFG.CIV_FLAG_WIDTH / 2 + iTranslateX, this.getMenuElement(tempButtonID).getPosY() + this.getMenuPosY() - ImageManager.getImage(Images.randomCivilizationFlag).getHeight() + this.getMenuElement(tempButtonID).getHeight() / 2 - CFG.CIV_FLAG_HEIGHT / 2 + iTranslateY, CFG.CIV_FLAG_WIDTH, CFG.CIV_FLAG_HEIGHT);
        }
        ImageManager.getImage(Images.flag_rect).draw(oSB, this.getMenuElement(tempButtonID).getPosX() + this.getMenuElement(tempButtonID).getTextPos() / 2 - CFG.CIV_FLAG_WIDTH / 2 + iTranslateX, this.getMenuElement(tempButtonID).getPosY() + this.getMenuPosY() + this.getMenuElement(tempButtonID).getHeight() / 2 - CFG.CIV_FLAG_HEIGHT / 2 + iTranslateY);
        super.endClip(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
    }
    
    @Override
    protected final void actionElement(final int iID) {
        switch (iID) {
            case 0: {
                this.onBackPressed();
                break;
            }
            case 1: {
                CFG.eventsManager.eSelectCivAction = Event_SelectCivAction.OUT_SELECTCIV_CREATEVASSAL_A;
                CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_SELECT_CIV);
                break;
            }
            case 2: {
                CFG.eventsManager.eSelectCivAction = Event_SelectCivAction.OUT_SELECTCIV_CREATEVASSAL_B;
                CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_SELECT_CIV);
                break;
            }
            case 3: {
                CFG.game.getSelectedProvinces().clearSelectedProvinces();

                for(int i = 0; i < ((Event_Outcome)((Event_Decision)CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID)).lOutcomes.get(CFG.eventsManager.iCreateEvent_EditConditionID)).getProvinces().size(); ++i) {
                    CFG.game.getSelectedProvinces().addProvince((Integer)((Event_Outcome)((Event_Decision)CFG.eventsManager.lCreateScenario_Event.lDecisions.get(CFG.eventsManager.iCreateEvent_EditTriggerID)).lOutcomes.get(CFG.eventsManager.iCreateEvent_EditConditionID)).getProvinces().get(i));
                }

                CFG.eventsManager.eSelectCivAction = Event_SelectCivAction.OUT_SELECTPROVINCES_CREATEVASSAL;
                CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_SELECT_PROVINCES);
                break;
            }

            case 4: {
                CFG.eventsManager.eSelectCivAction = Event_SelectCivAction.OUT_SELECTAUTONOMY_CREATEVASSAL;
                CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_SELECT_AUTONOMY);
                break;
            }
        }
    }
    
    @Override
    protected final void onBackPressed() {
        CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_DECISION);
        CFG.menuManager.setBackAnimation(true);
    }
}
