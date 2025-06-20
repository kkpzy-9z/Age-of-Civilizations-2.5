package age.of.civilizations2.jakowski.lukasz;

import javax.swing.MenuElement;
import java.util.ArrayList;
import java.util.List;

class Menu_Random_Leader_Edit_SelectAIType extends SliderMenu {
   protected Menu_Random_Leader_Edit_SelectAIType() {
      super();
      final List menuElements = new ArrayList<MenuElement>();
      menuElements.add(new Button_Menu_LR_Line(null, -1, 0, 0, CFG.GAME_WIDTH, CFG.BUTTON_HEIGHT, true));
      this.initMenuWithBackButton(new SliderMenuTitle(null, CFG.BUTTON_HEIGHT * 3 / 4, false, false), 0, CFG.BUTTON_HEIGHT * 3 / 4, CFG.GAME_WIDTH, CFG.GAME_HEIGHT - CFG.BUTTON_HEIGHT * 3 / 4, menuElements, true, true);
      this.updateLanguage();
   }

   protected void updateLanguage() {
      this.getMenuElement(0).setText(CFG.langManager.get("Back"));
      this.getTitle().setText(CFG.langManager.get("SelectAIType"));
   }

   protected final void actionElement(int iID) {
      this.onBackPressed();
   }

   protected void onBackPressed() {
      CFG.menuManager.setViewID(Menu.eGAME_RANDOM_LEADERS_EDIT);
      CFG.menuManager.clearRandom_Leaders__SelectAIType();
   }

   protected void actionClose() {
      this.onBackPressed();
   }
}
