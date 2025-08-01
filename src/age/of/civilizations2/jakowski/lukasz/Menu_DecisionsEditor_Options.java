//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.GdxRuntimeException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Menu_DecisionsEditor_Options extends SliderMenu {
    private List<String> lTags;
    private List<String> lCivsTags;
    private List<Image> lFlags;
    private List<Integer> lLoadedFlags_TagsIDs;

    protected Menu_DecisionsEditor_Options() {
        super();
        this.lTags = null;
        this.lCivsTags = null;
        this.lFlags = new ArrayList<Image>();
        this.lLoadedFlags_TagsIDs = new ArrayList<Integer>();
        this.lCivsTags = new ArrayList<String>();
        this.lTags = new ArrayList<String>();
        final List<MenuElement> menuElements = new ArrayList<MenuElement>();

        menuElements.add(new Button_Menu_LR_Line((String) null, -1, 0, CFG.PADDING, CFG.GAME_WIDTH, CFG.BUTTON_HEIGHT, true));

        try {
            String[] tagsSPLITED = null;
            if (!CFG.isDesktop()) {
                FileHandle tempFileT = Gdx.files.internal("game/decisions/Age_of_Civilizations");
                String tempT = tempFileT.readString();
                tagsSPLITED = tempT.split(";");
            } else {
                List<String> tempFiles = CFG.getFileNames("game/decisions/");
                int i = 0;

                for (int iSize = tempFiles.size(); i < iSize; ++i) {
                    if (((String) tempFiles.get(i)).equals("Age_of_Civilizations")) {
                        tempFiles.remove(i);
                        break;
                    }
                }

                tagsSPLITED = new String[tempFiles.size()];
                i = 0;

                for (int iSize = tempFiles.size(); i < iSize; ++i) {
                    tagsSPLITED[i] = (String) tempFiles.get(i);
                }
            }

            final List<String> lTempNames = new ArrayList<String>();
            final List<String> lTempTags = new ArrayList<String>();
            final List<String> lTempCivsTags = new ArrayList<String>();
            if (CFG.sSearch != null && !CFG.sSearch.isEmpty()) {
                for (String s : tagsSPLITED) {
                    try {
                        try {
                            final FileHandle file = Gdx.files.local("game/decisions/" + s);
                            CFG.civDecision_GameData = (Civ_Decision_GameData) CFG.deserialize(file.readBytes());
                        } catch (final GdxRuntimeException ex) {
                            final FileHandle file = Gdx.files.internal("game/decisions/" + s);
                            CFG.civDecision_GameData = (Civ_Decision_GameData) CFG.deserialize(file.readBytes());
                        }
                    } catch (final ClassNotFoundException | IOException ex2) {
                    }
                    if (CFG.civDecision_GameData.getName().toLowerCase().contains(CFG.sSearch.toLowerCase())) {
                        lTempNames.add(CFG.civDecision_GameData.getName());
                        lTempTags.add(s);
                        lTempCivsTags.add(CFG.civDecision_GameData.getCiv(0));
                    }
                }
                int nPosY = 0;
                while (!lTempNames.isEmpty()) {
                    int toAddID = 0;
                    for (int k = 1; k < lTempNames.size(); ++k) {
                        if (CFG.compareAlphabetic_TwoString(lTempNames.get(toAddID), lTempNames.get(k))) {
                            toAddID = k;
                        }
                    }
                    menuElements.add(new Button_Menu(lTempNames.get(toAddID), (int) (50.0f * CFG.GUI_SCALE), 0, CFG.BUTTON_HEIGHT * (nPosY + 1) + CFG.PADDING * (nPosY + 2), CFG.GAME_WIDTH, CFG.BUTTON_HEIGHT, true));
                    this.lCivsTags.add(lTempCivsTags.get(toAddID));
                    this.lTags.add(lTempTags.get(toAddID));
                    lTempNames.remove(toAddID);
                    lTempTags.remove(toAddID);
                    lTempCivsTags.remove(toAddID);
                    ++nPosY;
                }
            } else if (CFG.chosen_AlphabetCharachter == null) {
                for (String s : tagsSPLITED) {
                    try {
                        try {
                            final FileHandle file = Gdx.files.local("game/decisions/" + s);
                            CFG.civDecision_GameData = (Civ_Decision_GameData) CFG.deserialize(file.readBytes());
                        } catch (final GdxRuntimeException ex) {
                            final FileHandle file = Gdx.files.internal("game/decisions/" + s);
                            CFG.civDecision_GameData = (Civ_Decision_GameData) CFG.deserialize(file.readBytes());
                        }
                    } catch (final ClassNotFoundException | IOException e) {
                        CFG.exceptionStack(e);
                    }
                    lTempNames.add(CFG.civDecision_GameData.getName());
                    lTempTags.add(s);
                    lTempCivsTags.add(CFG.civDecision_GameData.getCiv(0));
                }
                int nPosY = 0;
                while (!lTempNames.isEmpty()) {
                    int toAddID = 0;
                    for (int k = 1; k < lTempNames.size(); ++k) {
                        if (CFG.compareAlphabetic_TwoString(lTempNames.get(toAddID), lTempNames.get(k))) {
                            toAddID = k;
                        }
                    }
                    menuElements.add(new Button_Menu(lTempNames.get(toAddID), (int) (50.0f * CFG.GUI_SCALE), 0, CFG.BUTTON_HEIGHT * (nPosY + 1) + CFG.PADDING * (nPosY + 2), CFG.GAME_WIDTH, CFG.BUTTON_HEIGHT, true));
                    this.lCivsTags.add(lTempCivsTags.get(toAddID));
                    this.lTags.add(lTempTags.get(toAddID));
                    lTempNames.remove(toAddID);
                    lTempTags.remove(toAddID);
                    lTempCivsTags.remove(toAddID);
                    ++nPosY;
                }
            } else {
                for (int j = 0, iSize2 = tagsSPLITED.length; j < iSize2; ++j) {
                    try {
                        try {
                            final FileHandle file = Gdx.files.local("game/decisions/" + tagsSPLITED[j]);
                            CFG.civDecision_GameData = (Civ_Decision_GameData) CFG.deserialize(file.readBytes());
                        } catch (final GdxRuntimeException ex) {
                            final FileHandle file = Gdx.files.internal("game/decisions/" + tagsSPLITED[j]);
                            CFG.civDecision_GameData = (Civ_Decision_GameData) CFG.deserialize(file.readBytes());
                        }
                    } catch (final ClassNotFoundException | IOException ex4) {
                    }
                    if (CFG.civDecision_GameData.getName().charAt(0) == CFG.chosen_AlphabetCharachter.charAt(0)) {
                        lTempNames.add(CFG.civDecision_GameData.getName());
                        lTempTags.add(tagsSPLITED[j]);
                        lTempCivsTags.add(CFG.civDecision_GameData.getCiv(0));
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
                    menuElements.add(new Button_Menu(lTempNames.get(toAddID), (int) (50.0f * CFG.GUI_SCALE), 0, CFG.BUTTON_HEIGHT * (nPosY + 1) + CFG.PADDING * (nPosY + 2), CFG.GAME_WIDTH, CFG.BUTTON_HEIGHT, true));
                    this.lCivsTags.add(lTempCivsTags.get(toAddID));
                    this.lTags.add(lTempTags.get(toAddID));
                    lTempNames.remove(toAddID);
                    lTempTags.remove(toAddID);
                    lTempCivsTags.remove(toAddID);
                    ++nPosY;
                }
            }
        } catch (GdxRuntimeException var19) {
        }

        this.initMenu((SliderMenuTitle) null, 0, CFG.BUTTON_HEIGHT * 3 / 4 + CFG.BUTTON_HEIGHT + CFG.PADDING, CFG.GAME_WIDTH, CFG.GAME_HEIGHT - CFG.BUTTON_HEIGHT * 3 / 4 - CFG.BUTTON_HEIGHT - CFG.PADDING - (CFG.BUTTON_HEIGHT + CFG.PADDING), menuElements);
        this.updateLanguage();
    }

    protected void updateLanguage() {
        this.getMenuElement(0).setText(CFG.langManager.get("AddNewDecision"));
    }

    @Override
    protected void updateMenuElements_IsInView() {
        super.updateMenuElements_IsInView();
        for (int i = 1; i < this.getMenuElementsSize(); i += 2) {
            final int tempTagID = this.getIsLoaded(this.lCivsTags.get((i - 1) / 2));
            if (this.getMenuElement(i).getIsInView()) {
                if (tempTagID < 0) {
                    this.loadFlag((i - 1) / 2);
                }
            } else if (tempTagID >= 0) {
                this.lFlags.get(tempTagID).getTexture().dispose();
                this.lFlags.set(tempTagID, null);
                this.lFlags.remove(tempTagID);
                this.lLoadedFlags_TagsIDs.remove(tempTagID);
            }
        }
    }

    private final int getIsLoaded(String nCivTag) {
        for (int i = 0; i < this.lLoadedFlags_TagsIDs.size(); ++i) {
            if (((String) this.lCivsTags.get((Integer) this.lLoadedFlags_TagsIDs.get(i))).equals(nCivTag)) {
                return i;
            }
        }

        return -1;
    }

    private final int getFlagID(int nCivTagID) {
        for (int i = 0; i < this.lLoadedFlags_TagsIDs.size(); ++i) {
            if ((Integer) this.lLoadedFlags_TagsIDs.get(i) == nCivTagID) {
                return i;
            }
        }

        return 0;
    }

    private final void loadFlag(int nCivTagID) {
        try {
            try {
                this.lFlags.add(new Image(new Texture(Gdx.files.internal("game/flags/" + (String) this.lCivsTags.get(nCivTagID) + ".png")), TextureFilter.Nearest));
            } catch (GdxRuntimeException var3) {
                this.lFlags.add(new Image(new Texture(Gdx.files.internal("game/flags/" + CFG.ideologiesManager.getRealTag((String) this.lCivsTags.get(nCivTagID)) + ".png")), TextureFilter.Nearest));
            }
        } catch (GdxRuntimeException var4) {
            this.lFlags.add(new Image(new Texture(Gdx.files.internal("game/flags/ran.png")), TextureFilter.Nearest));
        }

        this.lLoadedFlags_TagsIDs.add(nCivTagID);
    }

    protected void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean sliderMenuIsActive) {
        super.beginClip(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
        super.drawMenu(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
        int tempRandomButton = 1;

        try {
            for (int i = tempRandomButton; i < this.getMenuElementsSize(); i += 2) {
                if (this.getMenuElement(i).getIsInView()) {
                    ((Image) this.lFlags.get(this.getFlagID((i - tempRandomButton) / 2))).draw(oSB, this.getMenuElement(i).getPosX() + this.getMenuElement(i).getTextPos() / 2 - CFG.CIV_FLAG_WIDTH / 2 + iTranslateX, this.getMenuElement(i).getPosY() + this.getMenuPosY() - ((Image) this.lFlags.get(this.getFlagID((i - tempRandomButton) / 2))).getHeight() + this.getMenuElement(i).getHeight() / 2 - CFG.CIV_FLAG_HEIGHT / 2 + iTranslateY, CFG.CIV_FLAG_WIDTH, CFG.CIV_FLAG_HEIGHT);
                    ImageManager.getImage(Images.flag_rect).draw(oSB, this.getMenuElement(i).getPosX() + this.getMenuElement(i).getTextPos() / 2 - CFG.CIV_FLAG_WIDTH / 2 + iTranslateX, this.getMenuElement(i).getPosY() + this.getMenuPosY() + this.getMenuElement(i).getHeight() / 2 - CFG.CIV_FLAG_HEIGHT / 2 + iTranslateY);
                }
            }
        } catch (IndexOutOfBoundsException | NullPointerException var7) {
        }

        super.endClip(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
    }

    protected final void actionElement(int iID) {
        switch (iID) {
            case 0:
                CFG.game.setActiveProvinceID(-1);
                CFG.game.getSelectedProvinces().clearSelectedProvinces();
                CFG.selectMode = true;
                CFG.brushTool = false;
                CFG.VIEW_SHOW_VALUES = false;
                CFG.civDecision_GameData = new Civ_Decision_GameData();
                CFG.civDecision_GameData.setTag(System.currentTimeMillis() + CFG.extraRandomTag());
                Game_Calendar.currentDay = CFG.civDecision_GameData.getDay();
                Game_Calendar.currentMonth = CFG.civDecision_GameData.getMonth();
                Game_Calendar.currentYear = CFG.civDecision_GameData.getYear();
                CFG.CREATE_SCENARIO_AGE = CFG.gameAges.getAgeOfYear(Game_Calendar.currentYear);
                CFG.menuManager.setViewID(Menu.eGAME_DECISIONS_EDITOR_EDIT);
                return;
            default:
                CFG.game.setActiveProvinceID(-1);
                CFG.game.getSelectedProvinces().clearSelectedProvinces();
                CFG.selectMode = true;
                CFG.brushTool = false;
                CFG.VIEW_SHOW_VALUES = false;

                try {
                    try {
                        FileHandle file = Gdx.files.local("game/decisions/" + (String) this.lTags.get((iID - 1) / 2));
                        CFG.civDecision_GameData = (Civ_Decision_GameData) CFG.deserialize(file.readBytes());
                    } catch (GdxRuntimeException var8) {
                        FileHandle file = Gdx.files.internal("game/decisions/" + (String) this.lTags.get((iID - 1) / 2));
                        CFG.civDecision_GameData = (Civ_Decision_GameData) CFG.deserialize(file.readBytes());
                    }
                } catch (ClassNotFoundException | IOException var9) {
                }

                Game_Calendar.currentDay = CFG.civDecision_GameData.getDay();
                Game_Calendar.currentMonth = CFG.civDecision_GameData.getMonth();
                Game_Calendar.currentYear = CFG.civDecision_GameData.getYear();
                CFG.CREATE_SCENARIO_AGE = CFG.gameAges.getAgeOfYear(Game_Calendar.currentYear);
                CFG.menuManager.setViewID(Menu.eGAME_DECISIONS_EDITOR_EDIT);
        }
    }

    protected void onBackPressed() {
        CFG.menuManager.setViewID(Menu.eEDITOR);
        this.disposeData();
    }

    protected void setVisible(boolean visible) {
        super.setVisible(visible);
        if (!visible) {
            this.disposeData();
        }

    }

    protected void disposeData() {
        for (int i = 0; i < this.lFlags.size(); ++i) {
            ((Image) this.lFlags.get(i)).getTexture().dispose();
        }

        this.lFlags.clear();
        this.lLoadedFlags_TagsIDs.clear();
        this.lCivsTags.clear();
    }
}
