package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.util.ArrayList;
import java.util.List;

class Menu_InGame_CreateAVassal_SelectCiv_Alphabet extends SliderMenu {
   private List lCharacters;
   private String nSearch = null;

   protected Menu_InGame_CreateAVassal_SelectCiv_Alphabet() {
      List menuElements = new ArrayList();
      this.nSearch = CFG.langManager.get("Search");
      menuElements.add(new Button_Menu_Classic_Search("", CFG.PADDING * 2, 0, CFG.PADDING, CFG.BUTTON_WIDTH * 2, CFG.BUTTON_HEIGHT, true) {
         protected void buildElementHover() {
            List nElements = new ArrayList();
            List nData = new ArrayList();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Search"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }

         protected String getTextToDraw() {
            return Menu_InGame_CreateAVassal_SelectCiv_Alphabet.this.nSearch + ": " + super.getTextToDraw();
         }
      });
      if (CFG.chosen_AlphabetCharachter == null) {
         menuElements.add(new Button_Menu_Active((String)null, -1, CFG.BUTTON_WIDTH * 2, CFG.PADDING, CFG.BUTTON_HEIGHT, CFG.BUTTON_HEIGHT, true) {
            protected void buildElementHover() {
               List nElements = new ArrayList();
               List nData = new ArrayList();
               nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("AllCivilizations"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
               nElements.add(new MenuElement_Hover_v2_Element2(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover_v2(nElements);
            }
         });
      } else {
         menuElements.add(new Button_Menu_Classic((String)null, -1, CFG.BUTTON_WIDTH * 2, CFG.PADDING, CFG.BUTTON_HEIGHT, CFG.BUTTON_HEIGHT, true) {
            protected void buildElementHover() {
               List nElements = new ArrayList();
               List nData = new ArrayList();
               nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("AllCivilizations"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
               nElements.add(new MenuElement_Hover_v2_Element2(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover_v2(nElements);
            }
         });
      }

      FileHandle tempFileT = Gdx.files.internal("game/civilizations/Age_of_Civilizations");
      String tempT = tempFileT.readString();
      String[] tagsSPLITED = tempT.split(";");
      ArrayList<String> tagsSPLITED_ED = new ArrayList<String>();

      try {
         FileHandle tempFileT_ED = null;
         tempFileT_ED = Gdx.files.internal("game/civilizations_editor/Age_of_Civilizations");
         String tempT_ED = tempFileT_ED.readString();
         java.util.Collections.addAll(tagsSPLITED_ED, tempT_ED.split(";"));

         if (CFG.isAndroid()) {
            tempFileT_ED = Gdx.files.local("game/civilizations_editor/Age_of_Civilizations");
            tempT_ED = tempFileT_ED.readString();
            java.util.Collections.addAll(tagsSPLITED_ED, tempT_ED.split(";"));
         }
      } catch (GdxRuntimeException var10) {
      }

      this.lCharacters = new ArrayList();
      int tempElementWidth = 0;

      boolean addChar;
      int a;
      int tempPosX;
      for(tempPosX = tagsSPLITED.length; tempElementWidth < tempPosX; ++tempElementWidth) {
         //bugfix for if two semicolons/no civ in AoC civs file change//
         if (tagsSPLITED[tempElementWidth].length() < 1) continue;

         if (!CFG.isInTheGame_Or_IsFormableCiv(tagsSPLITED[tempElementWidth])) {
            addChar = true;

            for(a = 0; a < this.lCharacters.size(); ++a) {
               if ((Character)this.lCharacters.get(a) == CFG.langManager.getCiv(tagsSPLITED[tempElementWidth]).charAt(0)) {
                  addChar = false;
                  break;
               }
            }

            if (addChar) {
               this.lCharacters.add(CFG.langManager.getCiv(tagsSPLITED[tempElementWidth]).charAt(0));
            }
         }
      }

      tempElementWidth = 0;

      for(tempPosX = tagsSPLITED_ED.size(); tempElementWidth < tempPosX; ++tempElementWidth) {
         //bugfix for if two semicolons/no civ in AoC civs file change//
         if (tagsSPLITED_ED.get(tempElementWidth).length() < 1) continue;

         if (!CFG.isInTheGame_Or_IsFormableCiv(tagsSPLITED_ED.get(tempElementWidth))) {
            addChar = true;

            for(a = 0; a < this.lCharacters.size(); ++a) {
               if ((Character)this.lCharacters.get(a) == CFG.langManager.getCiv(tagsSPLITED_ED.get(tempElementWidth)).charAt(0)) {
                  addChar = false;
                  break;
               }
            }

            if (addChar) {
               this.lCharacters.add(CFG.langManager.getCiv(tagsSPLITED_ED.get(tempElementWidth)).charAt(0));
            }
         }
      }

      int i;
      for(tempElementWidth = 0; tempElementWidth < this.lCharacters.size() - 1; ++tempElementWidth) {
         for(tempPosX = tempElementWidth + 1; tempPosX < this.lCharacters.size(); ++tempPosX) {
            if ((Character)this.lCharacters.get(tempElementWidth) > (Character)this.lCharacters.get(tempPosX)) {
               i = (Character)this.lCharacters.get(tempElementWidth);
               this.lCharacters.set(tempElementWidth, (Character)this.lCharacters.get(tempPosX));
               this.lCharacters.set(tempPosX, Character.valueOf((char)i));
            }
         }
      }

      for(tempElementWidth = 0; tempElementWidth < this.lCharacters.size(); ++tempElementWidth) {
         if (CFG.chosen_AlphabetCharachter != null && (Character)this.lCharacters.get(tempElementWidth) == CFG.chosen_AlphabetCharachter.charAt(0)) {
            menuElements.add(new Button_Menu_Active("[" + this.lCharacters.get(tempElementWidth) + "]", -1, CFG.BUTTON_HEIGHT * (tempElementWidth + 1) + CFG.BUTTON_WIDTH * 2, CFG.PADDING, CFG.BUTTON_HEIGHT, CFG.BUTTON_HEIGHT, true));
         } else {
            menuElements.add(new Button_Menu_Classic("[" + this.lCharacters.get(tempElementWidth) + "]", -1, CFG.BUTTON_HEIGHT * (tempElementWidth + 1) + CFG.BUTTON_WIDTH * 2, CFG.PADDING, CFG.BUTTON_HEIGHT, CFG.BUTTON_HEIGHT, true));
         }
      }

      if (((MenuElement)menuElements.get(menuElements.size() - 1)).getPosX() + ((MenuElement)menuElements.get(menuElements.size() - 1)).getWidth() < CFG.GAME_WIDTH) {
         tempElementWidth = (CFG.GAME_WIDTH - CFG.BUTTON_WIDTH * 2) / (menuElements.size() - 1);
         tempPosX = 0;

         for(i = 0; i < menuElements.size() - 1; ++i) {
            if (i == 0) {
               ((MenuElement)menuElements.get(i)).setPosX(tempPosX);
               ((MenuElement)menuElements.get(i)).setWidth(CFG.BUTTON_WIDTH * 2);
               tempPosX += ((MenuElement)menuElements.get(i)).getWidth();
            } else {
               ((MenuElement)menuElements.get(i)).setPosX(tempPosX);
               ((MenuElement)menuElements.get(i)).setWidth(tempElementWidth);
               tempPosX += ((MenuElement)menuElements.get(i)).getWidth();
            }
         }

         ((MenuElement)menuElements.get(menuElements.size() - 1)).setPosX(tempPosX);
         ((MenuElement)menuElements.get(menuElements.size() - 1)).setWidth(CFG.GAME_WIDTH - tempPosX);
      }

      this.initMenu((SliderMenuTitle)null, 0, CFG.BUTTON_HEIGHT * 3 / 4, CFG.GAME_WIDTH, CFG.BUTTON_HEIGHT + CFG.PADDING * 2, menuElements, true, false);
      this.updateLanguage();
   }

   protected void updateLanguage() {
      if (CFG.sSearch != null) {
         this.getMenuElement(0).setText(CFG.sSearch);
      }

      this.getMenuElement(1).setText("[" + CFG.langManager.get("ALL") + "]");
   }

   protected final void actionElement(int iID) {
      switch (iID) {
         case 0:
            CFG.showKeyboard();
            return;
         case 1:
            if (CFG.chosen_AlphabetCharachter != null || CFG.sSearch != null) {
               CFG.chosen_AlphabetCharachter = null;
               CFG.sSearch = null;
               CFG.menuManager.setViewID(Menu.eINGAME_CREATE_VASSAL_SELECT_CIV);
            }

            return;
         default:
            if (CFG.chosen_AlphabetCharachter == null || CFG.sSearch != null || CFG.chosen_AlphabetCharachter.charAt(0) != (Character)this.lCharacters.get(iID - 2)) {
               CFG.chosen_AlphabetCharachter = "" + this.lCharacters.get(iID - 2);
               CFG.sSearch = null;
               CFG.menuManager.setViewID(Menu.eINGAME_CREATE_VASSAL_SELECT_CIV);
            }

      }
   }
}
