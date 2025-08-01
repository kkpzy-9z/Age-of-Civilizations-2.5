package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Menu_DecisionsEditor_Alphabet extends SliderMenu {
   private List<Character> lCharacters;
   private String nSearch;

   protected Menu_DecisionsEditor_Alphabet() {
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
            return Menu_DecisionsEditor_Alphabet.this.nSearch + ": " + super.getTextToDraw();
         }
      });
      if (CFG.chosen_AlphabetCharachter == null) {
         menuElements.add(new Button_Menu_Active((String)null, -1, CFG.BUTTON_WIDTH * 2, CFG.PADDING, CFG.BUTTON_HEIGHT, CFG.BUTTON_HEIGHT, true) {
            protected void buildElementHover() {
               final List<MenuElement_Hover_v2_Element2> nElements = new ArrayList<MenuElement_Hover_v2_Element2>();
               final List<MenuElement_Hover_v2_Element_Type> nData = new ArrayList<MenuElement_Hover_v2_Element_Type>();
               nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("All"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
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
               nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("All"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
               nElements.add(new MenuElement_Hover_v2_Element2(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover_v2(nElements);
            }
         });
      }

      try {
         String[] tagsSPLITED = null;
         if (CFG.isDesktop()) {
            final List<String> tempFiles = CFG.getFileNames("game/decisions/");
            for (int i = 0, iSize = tempFiles.size(); i < iSize; ++i) {
               if (tempFiles.get(i).equals("Age_of_Civilizations")) {
                  tempFiles.remove(i);
                  break;
               }
            }
            tagsSPLITED = new String[tempFiles.size()];
            for (int i = 0, iSize = tempFiles.size(); i < iSize; ++i) {
               tagsSPLITED[i] = tempFiles.get(i);
            }
         }
         else {
            final FileHandle tempFileT = Gdx.files.internal("game/decisions/Age_of_Civilizations");
            final String tempT = tempFileT.readString();
            tagsSPLITED = tempT.split(";");
         }

         this.lCharacters = new ArrayList<Character>();
         for (int j = 0, iSize2 = tagsSPLITED.length; j < iSize2; ++j) {
            boolean addChar = true;
            try {
               try {
                  final FileHandle file = Gdx.files.local("game/decisions/" + tagsSPLITED[j]);
                  CFG.civDecision_GameData = (Civ_Decision_GameData)CFG.deserialize(file.readBytes());
               }
               catch (final GdxRuntimeException ex) {
                  final FileHandle file = Gdx.files.internal("game/decisions/" + tagsSPLITED[j]);
                  CFG.civDecision_GameData = (Civ_Decision_GameData)CFG.deserialize(file.readBytes());
               }
            }
            catch (final ClassNotFoundException | IOException ex2) {}
             if (!CFG.civDecision_GameData.getName().isEmpty()) {
                 for (Character lCharacter : this.lCharacters) {
                     if (lCharacter == CFG.civDecision_GameData.getName().charAt(0)) {
                         addChar = false;
                         break;
                     }
                 }
               if (addChar) {
                  this.lCharacters.add(CFG.civDecision_GameData.getName().charAt(0));
               }
            }
         }
         for (int j = 0; j < this.lCharacters.size() - 1; ++j) {
            for (int k = j + 1; k < this.lCharacters.size(); ++k) {
               if (this.lCharacters.get(j) > this.lCharacters.get(k)) {
                  final char temp = this.lCharacters.get(j);
                  this.lCharacters.set(j, this.lCharacters.get(k));
                  this.lCharacters.set(k, temp);
               }
            }
         }
         for (int j = 0; j < this.lCharacters.size(); ++j) {
            if (CFG.chosen_AlphabetCharachter != null && this.lCharacters.get(j) == CFG.chosen_AlphabetCharachter.charAt(0)) {
               menuElements.add(new Button_Menu_Active("[" + this.lCharacters.get(j) + "]", -1, CFG.BUTTON_HEIGHT * (j + 1) + CFG.BUTTON_WIDTH * 2, CFG.PADDING, CFG.BUTTON_HEIGHT, CFG.BUTTON_HEIGHT, true));
            }
            else {
               menuElements.add(new Button_Menu_Classic("[" + this.lCharacters.get(j) + "]", -1, CFG.BUTTON_HEIGHT * (j + 1) + CFG.BUTTON_WIDTH * 2, CFG.PADDING, CFG.BUTTON_HEIGHT, CFG.BUTTON_HEIGHT, true));
            }
         }
         if (menuElements.get(menuElements.size() - 1).getPosX() + menuElements.get(menuElements.size() - 1).getWidth() < CFG.GAME_WIDTH) {
            final int tempElementWidth = (CFG.GAME_WIDTH - CFG.BUTTON_WIDTH * 2) / (menuElements.size() - 1);
            int tempPosX = 0;
            for (int l = 0; l < menuElements.size() - 1; ++l) {
               if (l == 0) {
                  menuElements.get(l).setPosX(tempPosX);
                  menuElements.get(l).setWidth(CFG.BUTTON_WIDTH * 2);
                  tempPosX += menuElements.get(l).getWidth();
               }
               else {
                  menuElements.get(l).setPosX(tempPosX);
                  menuElements.get(l).setWidth(tempElementWidth);
                  tempPosX += menuElements.get(l).getWidth();
               }
            }
            menuElements.get(menuElements.size() - 1).setPosX(tempPosX);
            menuElements.get(menuElements.size() - 1).setWidth(CFG.GAME_WIDTH - tempPosX);
         }
      }
      catch (final GdxRuntimeException ex4) {}
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
               CFG.menuManager.setViewID(Menu.eGAME_DECISIONS_EDITOR);
            }

            return;
         default:
            if (CFG.chosen_AlphabetCharachter == null || CFG.sSearch != null || CFG.chosen_AlphabetCharachter.charAt(0) != (Character)this.lCharacters.get(iID - 2)) {
               CFG.chosen_AlphabetCharachter = "" + this.lCharacters.get(iID - 2);
               CFG.sSearch = null;
               CFG.menuManager.setViewID(Menu.eGAME_DECISIONS_EDITOR);
            }

      }
   }
}
