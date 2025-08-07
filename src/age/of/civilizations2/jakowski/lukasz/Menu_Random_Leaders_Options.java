package age.of.civilizations2.jakowski.lukasz;

import java.util.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.utils.*;
import java.io.*;
import com.badlogic.gdx.files.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;

class Menu_Random_Leaders_Options extends SliderMenu
{
    private List<String> lTags;
    private List<String> lCivsTags;
    
    protected Menu_Random_Leaders_Options() {
        super();
        this.lTags = null;
        this.lCivsTags = null;
        this.lCivsTags = new ArrayList<String>();
        this.lTags = new ArrayList<String>();
        final List<MenuElement> menuElements = new ArrayList<MenuElement>();
        menuElements.add(new Button_Menu_LR_Line(null, -1, 0, CFG.PADDING, CFG.GAME_WIDTH, CFG.BUTTON_HEIGHT, true));
        try {
            String[] tagsSPLITED = null;
            if (CFG.isDesktop()) {
                final List<String> tempFiles = CFG.getFileNames("game/leadersRandom/");
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
                FileHandle tempFileT = Gdx.files.internal("game/leadersRandom/Age_of_Civilizations");
                if (CFG.readLocalFiles()) {
                    tempFileT = Gdx.files.local("game/leadersRandom/Age_of_Civilizations");
                }
                final String tempT = tempFileT.readString();
                tagsSPLITED = tempT.split(";");
            }
            final List<String> lTempNames = new ArrayList<String>();
            final List<String> lTempTags = new ArrayList<String>();
            final List<String> lTempCivsTags = new ArrayList<String>();
            if (CFG.sSearch != null && CFG.sSearch.length() > 0) {
                for (int j = 0, iSize2 = tagsSPLITED.length; j < iSize2; ++j) {
                    try {
                        try {
                            final FileHandle file = Gdx.files.local("game/leadersRandom/" + tagsSPLITED[j]);
                            CFG.leader_Random_GameData = (Leader_Random_GameData)CFG.deserialize(file.readBytes());
                        }
                        catch (final GdxRuntimeException ex) {
                            final FileHandle file = Gdx.files.internal("game/leadersRandom/" + tagsSPLITED[j]);
                            CFG.leader_Random_GameData = (Leader_Random_GameData)CFG.deserialize(file.readBytes());
                        }
                    }
                    catch (final ClassNotFoundException | IOException ex2) {}
                    if (CFG.leader_Random_GameData.getLeaderOfCiv().getName().toLowerCase().contains(CFG.sSearch.toLowerCase())) {
                        lTempNames.add(CFG.leader_Random_GameData.getLeaderOfCiv().getName());
                        lTempTags.add(tagsSPLITED[j]);
                        lTempCivsTags.add(CFG.leader_Random_GameData.getCiv(0));
                    }
                }
                int nPosY = 0;
                while (lTempNames.size() > 0) {
                    int toAddID = 0;
                    for (int k = 1; k < lTempNames.size(); ++k) {
                        if (CFG.compareAlphabetic_TwoString(lTempNames.get(toAddID), lTempNames.get(k))) {
                            toAddID = k;
                        }
                    }
                    menuElements.add(new Button_Menu(lTempNames.get(toAddID), (int)(50.0f * CFG.GUI_SCALE), 0, CFG.BUTTON_HEIGHT * (nPosY + 1) + CFG.PADDING * (nPosY + 2), CFG.GAME_WIDTH, CFG.BUTTON_HEIGHT, true));
                    this.lCivsTags.add(lTempCivsTags.get(toAddID));
                    this.lTags.add(lTempTags.get(toAddID));
                    lTempNames.remove(toAddID);
                    lTempTags.remove(toAddID);
                    lTempCivsTags.remove(toAddID);
                    ++nPosY;
                }
            }
            else if (CFG.chosen_AlphabetCharachter == null) {
                for (int j = 0, iSize2 = tagsSPLITED.length; j < iSize2; ++j) {
                    try {
                        try {
                            final FileHandle file = Gdx.files.local("game/leadersRandom/" + tagsSPLITED[j]);
                            CFG.leader_Random_GameData = (Leader_Random_GameData)CFG.deserialize(file.readBytes());
                        }
                        catch (final GdxRuntimeException ex) {
                            final FileHandle file = Gdx.files.internal("game/leadersRandom/" + tagsSPLITED[j]);
                            CFG.leader_Random_GameData = (Leader_Random_GameData)CFG.deserialize(file.readBytes());
                        }
                    }
                    catch (final ClassNotFoundException | IOException e) {
                        CFG.exceptionStack(e);
                    }
                    lTempNames.add(CFG.leader_Random_GameData.getLeaderOfCiv().getName());
                    lTempTags.add(tagsSPLITED[j]);
                    lTempCivsTags.add(CFG.leader_Random_GameData.getCiv(0));
                }
                int nPosY = 0;
                while (lTempNames.size() > 0) {
                    int toAddID = 0;
                    for (int k = 1; k < lTempNames.size(); ++k) {
                        if (CFG.compareAlphabetic_TwoString(lTempNames.get(toAddID), lTempNames.get(k))) {
                            toAddID = k;
                        }
                    }
                    menuElements.add(new Button_Menu(lTempNames.get(toAddID), (int)(50.0f * CFG.GUI_SCALE), 0, CFG.BUTTON_HEIGHT * (nPosY + 1) + CFG.PADDING * (nPosY + 2), CFG.GAME_WIDTH, CFG.BUTTON_HEIGHT, true));
                    this.lCivsTags.add(lTempCivsTags.get(toAddID));
                    this.lTags.add(lTempTags.get(toAddID));
                    lTempNames.remove(toAddID);
                    lTempTags.remove(toAddID);
                    lTempCivsTags.remove(toAddID);
                    ++nPosY;
                }
            }
            else {
                for (int j = 0, iSize2 = tagsSPLITED.length; j < iSize2; ++j) {
                    try {
                        try {
                            final FileHandle file = Gdx.files.local("game/leadersRandom/" + tagsSPLITED[j]);
                            CFG.leader_Random_GameData = (Leader_Random_GameData)CFG.deserialize(file.readBytes());
                        }
                        catch (final GdxRuntimeException ex) {
                            final FileHandle file = Gdx.files.internal("game/leadersRandom/" + tagsSPLITED[j]);
                            CFG.leader_Random_GameData = (Leader_Random_GameData)CFG.deserialize(file.readBytes());
                        }
                    }
                    catch (final ClassNotFoundException | IOException ex4) {}
                    if (CFG.leader_Random_GameData.getLeaderOfCiv().getName().charAt(0) == CFG.chosen_AlphabetCharachter.charAt(0)) {
                        lTempNames.add(CFG.leader_Random_GameData.getLeaderOfCiv().getName());
                        lTempTags.add(tagsSPLITED[j]);
                        lTempCivsTags.add(CFG.leader_Random_GameData.getCiv(0));
                    }
                }
                int nPosY = 0;
                while (lTempNames.size() > 0) {
                    int toAddID = 0;
                    for (int k = 1; k < lTempNames.size(); ++k) {
                        if (CFG.compareAlphabetic_TwoString(lTempNames.get(toAddID), lTempNames.get(k))) {
                            toAddID = k;
                        }
                    }
                    menuElements.add(new Button_Menu(lTempNames.get(toAddID), (int)(50.0f * CFG.GUI_SCALE), 0, CFG.BUTTON_HEIGHT * (nPosY + 1) + CFG.PADDING * (nPosY + 2), CFG.GAME_WIDTH, CFG.BUTTON_HEIGHT, true));
                    this.lCivsTags.add(lTempCivsTags.get(toAddID));
                    this.lTags.add(lTempTags.get(toAddID));
                    lTempNames.remove(toAddID);
                    lTempTags.remove(toAddID);
                    lTempCivsTags.remove(toAddID);
                    ++nPosY;
                }
            }
        }
        catch (final GdxRuntimeException ex6) {}
        this.initMenu(null, 0, CFG.BUTTON_HEIGHT * 3 / 4 + CFG.BUTTON_HEIGHT + CFG.PADDING, CFG.GAME_WIDTH, CFG.GAME_HEIGHT - CFG.BUTTON_HEIGHT * 3 / 4 - CFG.BUTTON_HEIGHT - CFG.PADDING - (CFG.BUTTON_HEIGHT + CFG.PADDING), menuElements);
        this.updateLanguage();
    }
    
    @Override
    protected void updateLanguage() {
        this.getMenuElement(0).setText(CFG.langManager.get("AddNewRandomLeader"));
    }
    
    @Override
    protected void draw(final SpriteBatch oSB, final int iTranslateX, final int iTranslateY, final boolean sliderMenuIsActive) {
        super.beginClip(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
        super.drawMenu(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);

        super.endClip(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
    }
    
    @Override
    protected final void actionElement(final int iID) {
        switch (iID) {
            case 0: {
                CFG.game.setActiveProvinceID(-1);
                CFG.game.getSelectedProvinces().clearSelectedProvinces();
                CFG.selectMode = true;
                CFG.brushTool = false;
                CFG.VIEW_SHOW_VALUES = false;
                CFG.leader_Random_GameData = new Leader_Random_GameData();
                CFG.leader_Random_GameData.getLeaderOfCiv().setTag(System.currentTimeMillis() + CFG.extraRandomTag());
                Game_Calendar.currentDay = CFG.leader_Random_GameData.getLeaderOfCiv().getDay();
                Game_Calendar.currentMonth = CFG.leader_Random_GameData.getLeaderOfCiv().getMonth();
                Game_Calendar.currentYear = CFG.leader_Random_GameData.getLeaderOfCiv().getYear();
                CFG.CREATE_SCENARIO_AGE = CFG.gameAges.getAgeOfYear(Game_Calendar.currentYear);
                CFG.menuManager.setViewID(Menu.eGAME_RANDOM_LEADERS_EDIT);
                return;
            }
            default: {
                CFG.game.setActiveProvinceID(-1);
                CFG.game.getSelectedProvinces().clearSelectedProvinces();
                CFG.selectMode = true;
                CFG.brushTool = false;
                CFG.VIEW_SHOW_VALUES = false;
                try {
                    try {
                        final FileHandle file = Gdx.files.local("game/leadersRandom/" + this.lTags.get(iID - 1));
                        CFG.leader_Random_GameData = (Leader_Random_GameData)CFG.deserialize(file.readBytes());
                    }
                    catch (final GdxRuntimeException ex) {
                        final FileHandle file = Gdx.files.internal("game/leadersRandom/" + this.lTags.get(iID - 1));
                        CFG.leader_Random_GameData = (Leader_Random_GameData)CFG.deserialize(file.readBytes());
                    }
                }
                catch (final ClassNotFoundException | IOException ex2) {}
                Game_Calendar.currentDay = CFG.leader_Random_GameData.getLeaderOfCiv().getDay();
                Game_Calendar.currentMonth = CFG.leader_Random_GameData.getLeaderOfCiv().getMonth();
                Game_Calendar.currentYear = CFG.leader_Random_GameData.getLeaderOfCiv().getYear();
                CFG.CREATE_SCENARIO_AGE = CFG.gameAges.getAgeOfYear(Game_Calendar.currentYear);
                CFG.menuManager.setViewID(Menu.eGAME_RANDOM_LEADERS_EDIT);
            }
        }
    }
    
    @Override
    protected void onBackPressed() {
        CFG.menuManager.setViewID(Menu.eEDITOR);
        this.disposeData();
    }
    
    @Override
    protected void setVisible(final boolean visible) {
        super.setVisible(visible);
        if (!visible) {
            this.disposeData();
        }
    }

    protected void disposeData() {
        this.lCivsTags.clear();
    }
}
