package age.of.civilizations2.jakowski.lukasz;

import java.util.ArrayList;
import java.util.List;

class Menu_Decisions_Edit_SelectCiv extends SliderMenu {
    protected Menu_Decisions_Edit_SelectCiv() {
        super();
        List<MenuElement> menuElements = new ArrayList<>();
        menuElements.add(new Button_Menu_LR_Line(null, -1, 0, 0, CFG.GAME_WIDTH, CFG.BUTTON_HEIGHT, true));
        this.initMenuWithBackButton(new SliderMenuTitle(null, CFG.BUTTON_HEIGHT * 3 / 4, false, false), 0, CFG.BUTTON_HEIGHT * 3 / 4, CFG.GAME_WIDTH, CFG.GAME_HEIGHT - CFG.BUTTON_HEIGHT * 3 / 4, menuElements, true, true);
        this.updateLanguage();
    }

    protected void updateLanguage() {
        this.getMenuElement(0).setText(CFG.langManager.get("Back"));
        this.getTitle().setText(CFG.langManager.get("SelectDecision"));
    }

    protected final void actionElement(int iID) {
        this.onBackPressed();
    }

    protected void onBackPressed() {
        CFG.menuManager.setViewID(Menu.eGAME_DECISIONS_EDITOR_EDIT);
        CFG.menuManager.clearDecisions__SelectCiv();
    }

    protected void actionClose() {
        this.onBackPressed();
    }
}
