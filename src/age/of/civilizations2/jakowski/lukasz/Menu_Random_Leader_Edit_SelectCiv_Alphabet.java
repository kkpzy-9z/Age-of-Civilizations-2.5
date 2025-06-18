package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.util.ArrayList;
import java.util.List;

class Menu_Random_Leader_Edit_SelectCiv_Alphabet extends SliderMenu {
   private List<Character> lCharacters;
   private String nSearch;

   protected Menu_Random_Leader_Edit_SelectCiv_Alphabet() {
      super();
      final List<MenuElement> menuElements = new ArrayList<MenuElement>();
      this.nSearch = CFG.langManager.get("Search");

      menuElements.add(new Button_Menu_Classic_Search("", CFG.PADDING * 2, 0, CFG.PADDING, CFG.BUTTON_WIDTH * 2, CFG.BUTTON_HEIGHT, true) {
         protected void buildElementHover() {
            final List<MenuElement_Hover_v2_Element2> nElements = new ArrayList<MenuElement_Hover_v2_Element2>();
            final List<MenuElement_Hover_v2_Element_Type> nData = new ArrayList<MenuElement_Hover_v2_Element_Type>();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Search"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }

         protected String getTextToDraw() {
            return Menu_Random_Leader_Edit_SelectCiv_Alphabet.this.nSearch + ": " + super.getTextToDraw();
         }
      });
      if (CFG.chosen_AlphabetCharachter == null) {
         menuElements.add(new Button_Menu_Active((String)null, -1, CFG.BUTTON_WIDTH * 2, CFG.PADDING, CFG.BUTTON_HEIGHT, CFG.BUTTON_HEIGHT, true) {
            protected void buildElementHover() {
               final List<MenuElement_Hover_v2_Element2> nElements = new ArrayList<MenuElement_Hover_v2_Element2>();
               final List<MenuElement_Hover_v2_Element_Type> nData = new ArrayList<MenuElement_Hover_v2_Element_Type>();
               nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("AllCivilizations"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
               nElements.add(new MenuElement_Hover_v2_Element2(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover_v2(nElements);
            }
         });
      } else {
         menuElements.add(new Button_Menu_Classic((String)null, -1, CFG.BUTTON_WIDTH * 2, CFG.PADDING, CFG.BUTTON_HEIGHT, CFG.BUTTON_HEIGHT, true) {
            protected void buildElementHover() {
               final List<MenuElement_Hover_v2_Element2> nElements = new ArrayList<MenuElement_Hover_v2_Element2>();
               final List<MenuElement_Hover_v2_Element_Type> nData = new ArrayList<MenuElement_Hover_v2_Element_Type>();
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
      String[] tagsSPLITED_ED = new String[0];

      try {
         FileHandle tempFileT_ED = null;
         if (CFG.isAndroid()) {
            tempFileT_ED = Gdx.files.local("game/civilizations_editor/Age_of_Civilizations");
         } else {
            tempFileT_ED = Gdx.files.internal("game/civilizations_editor/Age_of_Civilizations");
         }

         String tempT_ED = tempFileT_ED.readString();
         tagsSPLITED_ED = tempT_ED.split(";");
      } catch (GdxRuntimeException var10) {
      }

      this.lCharacters = new ArrayList<Character>();
       for (String s : tagsSPLITED) {
           if (!CFG.isInLeaderCivs(s)) {
               boolean addChar = true;
               for (Character lCharacter : this.lCharacters) {
                   if (lCharacter == CFG.langManager.getCiv(s).charAt(0)) {
                       addChar = false;
                       break;
                   }
               }
               if (addChar) {
                   this.lCharacters.add(CFG.langManager.getCiv(s).charAt(0));
               }
           }
       }
       for (String s : tagsSPLITED_ED) {
           if (!CFG.isInLeaderCivs(s)) {
               boolean addChar = true;
               for (Character lCharacter : this.lCharacters) {
                   if (lCharacter == CFG.langManager.getCiv(s).charAt(0)) {
                       addChar = false;
                       break;
                   }
               }
               if (addChar) {
                   this.lCharacters.add(CFG.langManager.getCiv(s).charAt(0));
               }
           }
       }
      for (int i = 0; i < this.lCharacters.size() - 1; ++i) {
         for (int j = i + 1; j < this.lCharacters.size(); ++j) {
            if (this.lCharacters.get(i) > this.lCharacters.get(j)) {
               final char temp = this.lCharacters.get(i);
               this.lCharacters.set(i, this.lCharacters.get(j));
               this.lCharacters.set(j, temp);
            }
         }
      }
      for (int i = 0; i < this.lCharacters.size(); ++i) {
         if (CFG.chosen_AlphabetCharachter != null && this.lCharacters.get(i) == CFG.chosen_AlphabetCharachter.charAt(0)) {
            menuElements.add(new Button_Menu_Active("[" + this.lCharacters.get(i) + "]", -1, CFG.BUTTON_HEIGHT * (i + 1) + CFG.BUTTON_WIDTH * 2, CFG.PADDING, CFG.BUTTON_HEIGHT, CFG.BUTTON_HEIGHT, true));
         }
         else {
            menuElements.add(new Button_Menu_Classic("[" + this.lCharacters.get(i) + "]", -1, CFG.BUTTON_HEIGHT * (i + 1) + CFG.BUTTON_WIDTH * 2, CFG.PADDING, CFG.BUTTON_HEIGHT, CFG.BUTTON_HEIGHT, true));
         }
      }
      if (menuElements.get(menuElements.size() - 1).getPosX() + menuElements.get(menuElements.size() - 1).getWidth() < CFG.GAME_WIDTH) {
         final int tempElementWidth = (CFG.GAME_WIDTH - CFG.BUTTON_WIDTH * 2) / (menuElements.size() - 1);
         int tempPosX = 0;
         for (int k = 0; k < menuElements.size() - 1; ++k) {
            if (k == 0) {
               menuElements.get(k).setPosX(tempPosX);
               menuElements.get(k).setWidth(CFG.BUTTON_WIDTH * 2);
               tempPosX += menuElements.get(k).getWidth();
            }
            else {
               menuElements.get(k).setPosX(tempPosX);
               menuElements.get(k).setWidth(tempElementWidth);
               tempPosX += menuElements.get(k).getWidth();
            }
         }
         menuElements.get(menuElements.size() - 1).setPosX(tempPosX);
         menuElements.get(menuElements.size() - 1).setWidth(CFG.GAME_WIDTH - tempPosX);
      }
      this.initMenu(null, 0, CFG.BUTTON_HEIGHT * 3 / 4, CFG.GAME_WIDTH, CFG.BUTTON_HEIGHT + CFG.PADDING * 2, menuElements, true, false);
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
               CFG.menuManager.setViewID(Menu.eGAME_RANDOM_LEADERS_EDIT_SELECT_CIVS);
            }

            return;
         default:
            if (CFG.chosen_AlphabetCharachter == null || CFG.sSearch != null || CFG.chosen_AlphabetCharachter.charAt(0) != (Character)this.lCharacters.get(iID - 2)) {
               CFG.chosen_AlphabetCharachter = "" + this.lCharacters.get(iID - 2);
               CFG.sSearch = null;
               CFG.menuManager.setViewID(Menu.eGAME_RANDOM_LEADERS_EDIT_SELECT_CIVS);
            }

      }
   }
}
