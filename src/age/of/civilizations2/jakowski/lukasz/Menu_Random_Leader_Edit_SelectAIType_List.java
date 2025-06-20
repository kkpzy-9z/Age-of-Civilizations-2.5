package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.util.ArrayList;
import java.util.List;

class Menu_Random_Leader_Edit_SelectAIType_List extends SliderMenu {
   protected List<String> lAITags;
   protected Menu_Random_Leader_Edit_SelectAIType_List() {
      super();
      final List<MenuElement> menuElements = new ArrayList<MenuElement>();

      this.lAITags = new ArrayList<String>();
      final List<String> lTempNames = new ArrayList<String>();

      for(int i = 0; i < CFG.ideologiesManager.getIdeologiesSize(); ++i) {
         if (!lTempNames.contains(CFG.ideologiesManager.getIdeology(i).AI_TYPE)) {
            lTempNames.add(CFG.ideologiesManager.getIdeology(i).AI_TYPE);
         }
      }

      int nPosY = 0;
      //Add the "All" button and wiki button first
      if (!CFG.leader_Random_GameData.containsAIType("All")) {
         menuElements.add(new Button_Menu(CFG.langManager.get("All"), (int) (50.0f * CFG.GUI_SCALE), 0, CFG.PADDING, CFG.GAME_WIDTH - (CFG.BUTTON_WIDTH + CFG.BUTTON_WIDTH / 2), CFG.BUTTON_HEIGHT, true));
         menuElements.add(new Button_Menu_Classic_Wiki(CFG.GAME_WIDTH - (CFG.BUTTON_WIDTH + CFG.BUTTON_WIDTH / 2), CFG.PADDING, CFG.BUTTON_WIDTH + CFG.BUTTON_WIDTH / 2, CFG.BUTTON_HEIGHT, true));
         this.lAITags.add("All");
         nPosY++;
      }

      while (!lTempNames.isEmpty()) {
         int toAddID = 0;
         for (int j = 1; j < lTempNames.size(); ++j) {
            if (CFG.compareAlphabetic_TwoString(lTempNames.get(toAddID), lTempNames.get(j))) {
               toAddID = j;
            }
         }

         if (CFG.leader_Random_GameData.containsAIType(lTempNames.get(toAddID))) {
             lTempNames.remove(toAddID);
             continue;
         }

         menuElements.add(new Button_Menu(CFG.langManager.get(lTempNames.get(toAddID)), (int) (50.0f * CFG.GUI_SCALE), 0, CFG.BUTTON_HEIGHT * nPosY + CFG.PADDING * (nPosY + 1), CFG.GAME_WIDTH - (CFG.BUTTON_WIDTH + CFG.BUTTON_WIDTH / 2), CFG.BUTTON_HEIGHT, true));
         menuElements.add(new Button_Menu_Classic_Wiki(CFG.GAME_WIDTH - (CFG.BUTTON_WIDTH + CFG.BUTTON_WIDTH / 2), CFG.BUTTON_HEIGHT * nPosY + CFG.PADDING * (nPosY + 1), CFG.BUTTON_WIDTH + CFG.BUTTON_WIDTH / 2, CFG.BUTTON_HEIGHT, true));
         this.lAITags.add(lTempNames.get(toAddID));
         lTempNames.remove(toAddID);
         ++nPosY;
      }

      this.initMenu(null, 0, CFG.BUTTON_HEIGHT * 3 / 4 + CFG.PADDING, CFG.GAME_WIDTH, CFG.GAME_HEIGHT - CFG.BUTTON_HEIGHT * 3 / 4 - CFG.BUTTON_HEIGHT - CFG.PADDING * 2, menuElements, true, false);
      this.updateLanguage();
   }

   protected void updateLanguage() {
   }

   protected final void actionElement(int iID) {
      if (iID % 2 == 0) {
         CFG.leader_Random_GameData.addAIType((String)this.lAITags.get(iID / 2));
         this.onBack();
         CFG.chosen_AlphabetCharachter = null;
         CFG.sSearch = null;
         this.onBackPressed();
      } else {
         Gdx.net.openURI(CFG.WWW_WIKI + (String)this.lAITags.get(iID / 2));
      }

   }

   private final void onBack() {
      CFG.menuManager.setViewID(Menu.eGAME_RANDOM_LEADERS_EDIT);
   }

   protected void onBackPressed() {
      this.lAITags.clear();
   }
}
