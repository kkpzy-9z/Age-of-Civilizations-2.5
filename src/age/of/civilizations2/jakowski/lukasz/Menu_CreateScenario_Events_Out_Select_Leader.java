package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Menu_CreateScenario_Events_Out_Select_Leader extends SliderMenu {
   private List<Character> lCharacters;
   private String nSearch;

   protected Menu_CreateScenario_Events_Out_Select_Leader() {
      super();
      this.nSearch = null;
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
            return Menu_CreateScenario_Events_Out_Select_Leader.this.nSearch + ": " + super.getTextToDraw();
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
         int tempPosX;
         int i;
         if (!CFG.isDesktop()) {
            FileHandle tempFileT = Gdx.files.internal("game/leaders/Age_of_Civilizations");
            String tempT = tempFileT.readString();
            tagsSPLITED = tempT.split(";");
         } else {
            List tempFiles = CFG.getFileNames("game/leaders/");
            tempPosX = 0;

            for(i = tempFiles.size(); tempPosX < i; ++tempPosX) {
               if (((String)tempFiles.get(tempPosX)).equals("Age_of_Civilizations")) {
                  tempFiles.remove(tempPosX);
                  break;
               }
            }

            tagsSPLITED = new String[tempFiles.size()];
            tempPosX = 0;

            for(i = tempFiles.size(); tempPosX < i; ++tempPosX) {
               tagsSPLITED[tempPosX] = (String)tempFiles.get(tempPosX);
            }
         }

         this.lCharacters = new ArrayList();
         int tempElementWidth = 0;

         for(tempPosX = tagsSPLITED.length; tempElementWidth < tempPosX; ++tempElementWidth) {
            boolean addChar = true;

            try {
               FileHandle file;
               try {
                  file = Gdx.files.local("game/leaders/" + tagsSPLITED[tempElementWidth]);
                  CFG.leader_GameData = (Leader_GameData)CFG.deserialize(file.readBytes());
               } catch (GdxRuntimeException var8) {
                  file = Gdx.files.internal("game/leaders/" + tagsSPLITED[tempElementWidth]);
                  CFG.leader_GameData = (Leader_GameData)CFG.deserialize(file.readBytes());
               }
            } catch (ClassNotFoundException | IOException var9) {
            }

             if (!CFG.leader_GameData.getLeaderOfCiv().getName().isEmpty()) {
                 for (Character lCharacter : this.lCharacters) {
                     if ((Character) lCharacter == CFG.leader_GameData.getLeaderOfCiv().getName().charAt(0)) {
                         addChar = false;
                         break;
                     }
                 }

               if (addChar) {
                  this.lCharacters.add(CFG.leader_GameData.getLeaderOfCiv().getName().charAt(0));
               }
            }
         }

         for(tempElementWidth = 0; tempElementWidth < this.lCharacters.size() - 1; ++tempElementWidth) {
            for(tempPosX = tempElementWidth + 1; tempPosX < this.lCharacters.size(); ++tempPosX) {
               if ((Character)this.lCharacters.get(tempElementWidth) > (Character)this.lCharacters.get(tempPosX)) {
                  char temp = (Character)this.lCharacters.get(tempElementWidth);
                  this.lCharacters.set(tempElementWidth, (Character)this.lCharacters.get(tempPosX));
                  this.lCharacters.set(tempPosX, temp);
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
      } catch (GdxRuntimeException var11) {
      }

      this.initMenu((SliderMenuTitle)null, 0, CFG.PADDING, CFG.GAME_WIDTH, CFG.BUTTON_HEIGHT + CFG.PADDING * 2, menuElements, true, false);
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
               CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_OUT_SELECT_LEADER);
            }

            return;
         default:
            if (CFG.chosen_AlphabetCharachter == null || CFG.sSearch != null || CFG.chosen_AlphabetCharachter.charAt(0) != (Character)this.lCharacters.get(iID - 2)) {
               CFG.chosen_AlphabetCharachter = "" + this.lCharacters.get(iID - 2);
               CFG.sSearch = null;
               CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_EVENTS_OUT_SELECT_LEADER);
            }

      }
   }
}
