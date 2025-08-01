package age.of.civilizations2.jakowski.lukasz;

import java.util.*;
import com.badlogic.gdx.graphics.g2d.*;

class Menu_Editor extends SliderMenu
{
    protected Menu_Editor() {
        super();
        final List<MenuElement> menuElements = new ArrayList<MenuElement>();
        menuElements.add(new Button_Menu_LR_Line(null, -1, 0, 0, CFG.GAME_WIDTH, CFG.BUTTON_HEIGHT, true));
        menuElements.add(new Button_Menu(null, (int)(50.0f * CFG.GUI_SCALE), 0 + AoCGame.LEFT, CFG.PADDING, CFG.GAME_WIDTH - AoCGame.LEFT, CFG.BUTTON_HEIGHT, true));
        menuElements.add(new Button_Menu(null, (int)(50.0f * CFG.GUI_SCALE), 0 + AoCGame.LEFT, CFG.BUTTON_HEIGHT + CFG.PADDING * 2, CFG.GAME_WIDTH - AoCGame.LEFT, CFG.BUTTON_HEIGHT, true));
        menuElements.add(new Button_Menu(null, (int)(50.0f * CFG.GUI_SCALE), 0 + AoCGame.LEFT, CFG.BUTTON_HEIGHT * 2 + CFG.PADDING * 3, CFG.GAME_WIDTH - AoCGame.LEFT, CFG.BUTTON_HEIGHT, true));
        menuElements.add(new Button_Menu(null, (int)(50.0f * CFG.GUI_SCALE), 0 + AoCGame.LEFT, CFG.BUTTON_HEIGHT * 3 + CFG.PADDING * 4, CFG.GAME_WIDTH - AoCGame.LEFT, CFG.BUTTON_HEIGHT, true));
        menuElements.add(new Button_Menu(null, (int)(50.0f * CFG.GUI_SCALE), 0 + AoCGame.LEFT, CFG.BUTTON_HEIGHT * 4 + CFG.PADDING * 5, CFG.GAME_WIDTH - AoCGame.LEFT, CFG.BUTTON_HEIGHT, true));
        menuElements.add(new Button_Menu(null, (int)(50.0f * CFG.GUI_SCALE), 0 + AoCGame.LEFT, CFG.BUTTON_HEIGHT * 5 + CFG.PADDING * 6, CFG.GAME_WIDTH - AoCGame.LEFT, CFG.BUTTON_HEIGHT, true));
        menuElements.add(new Button_Menu(null, (int)(50.0f * CFG.GUI_SCALE), 0 + AoCGame.LEFT, CFG.BUTTON_HEIGHT * 6 + CFG.PADDING * 7, CFG.GAME_WIDTH - AoCGame.LEFT, CFG.BUTTON_HEIGHT, true));
        menuElements.add(new Button_Menu(null, (int)(50.0f * CFG.GUI_SCALE), 0 + AoCGame.LEFT, CFG.BUTTON_HEIGHT * 7 + CFG.PADDING * 8, CFG.GAME_WIDTH - AoCGame.LEFT, CFG.BUTTON_HEIGHT, true));
        menuElements.add(new Button_Menu(null, (int)(50.0f * CFG.GUI_SCALE), 0 + AoCGame.LEFT, CFG.BUTTON_HEIGHT * 8 + CFG.PADDING * 9, CFG.GAME_WIDTH - AoCGame.LEFT, CFG.BUTTON_HEIGHT, true));
        this.initMenuWithBackButton(new SliderMenuTitle(null, CFG.BUTTON_HEIGHT * 3 / 4, false, false), 0, CFG.BUTTON_HEIGHT * 3 / 4, CFG.GAME_WIDTH, CFG.GAME_HEIGHT - CFG.BUTTON_HEIGHT * 3 / 4, menuElements);
        this.updateLanguage();
    }
    
    @Override
    protected void updateLanguage() {
        this.getMenuElement(0).setText(CFG.langManager.get("Back"));
        this.getMenuElement(1).setText(CFG.langManager.get("CreateaScenario"));
        this.getMenuElement(2).setText(CFG.langManager.get("CreateaCivilization"));
        this.getMenuElement(3).setText(CFG.langManager.get("Leaders"));
        this.getMenuElement(4).setText(CFG.langManager.get("RandomLeaders"));
        this.getMenuElement(5).setText(CFG.langManager.get("Decisions"));
        this.getMenuElement(6).setText(CFG.langManager.get("MapEditor"));
        this.getMenuElement(7).setText(CFG.langManager.get("CreateaCity"));
        this.getMenuElement(8).setText(CFG.langManager.get("GameEditor"));
        this.getMenuElement(9).setText(CFG.langManager.get("www.AgeofCivilizationsGame.com"));
        this.getTitle().setText(CFG.langManager.get("GameEditor"));
    }
    
    @Override
    protected void draw(final SpriteBatch oSB, final int iTranslateX, final int iTranslateY, final boolean sliderMenuIsActive) {
        super.beginClip(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
        super.drawMenu(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
        CFG.map.getIcon(0).draw(oSB, this.getMenuElement(1).getPosX() + this.getMenuElement(1).getTextPos() / 2 - CFG.CIV_FLAG_WIDTH / 2 + iTranslateX, this.getMenuElement(1).getPosY() + this.getMenuElement(1).getHeight() / 2 - CFG.CIV_FLAG_HEIGHT / 2 + this.getMenuPosY() - CFG.map.getIcon(0).getHeight() + iTranslateY, CFG.CIV_FLAG_WIDTH, CFG.CIV_FLAG_HEIGHT);
        ImageManager.getImage(Images.editor_civ).draw(oSB, this.getMenuElement(2).getPosX() + this.getMenuElement(2).getTextPos() / 2 - ImageManager.getImage(Images.editor_civ).getWidth() / 2 + iTranslateX, this.getMenuElement(2).getPosY() + this.getMenuElement(2).getHeight() / 2 + this.getMenuPosY() - ImageManager.getImage(Images.editor_civ).getHeight() / 2 + iTranslateY);
        ImageManager.getImage(Images.editor_leaders).draw(oSB, this.getMenuElement(3).getPosX() + this.getMenuElement(3).getTextPos() / 2 - ImageManager.getImage(Images.editor_leaders).getWidth() / 2 + iTranslateX, this.getMenuElement(3).getPosY() + this.getMenuElement(3).getHeight() / 2 + this.getMenuPosY() - ImageManager.getImage(Images.editor_leaders).getHeight() / 2 + iTranslateY);
        ImageManager.getImage(Images.editor_leaders).draw(oSB, this.getMenuElement(4).getPosX() + this.getMenuElement(4).getTextPos() / 2 - ImageManager.getImage(Images.editor_leaders).getWidth() / 2 + iTranslateX, this.getMenuElement(4).getPosY() + this.getMenuElement(4).getHeight() / 2 + this.getMenuPosY() - ImageManager.getImage(Images.editor_leaders).getHeight() / 2 + iTranslateY);
        ImageManager.getImage(Images.diplo_message).draw(oSB, this.getMenuElement(5).getPosX() + this.getMenuElement(5).getTextPos() / 2 - ImageManager.getImage(Images.diplo_message).getWidth() / 2 + iTranslateX, this.getMenuElement(5).getPosY() + this.getMenuElement(5).getHeight() / 2 + this.getMenuPosY() - ImageManager.getImage(Images.diplo_message).getHeight() / 2 + iTranslateY);
        ImageManager.getImage(Images.editor_map).draw(oSB, this.getMenuElement(6).getPosX() + this.getMenuElement(6).getTextPos() / 2 - ImageManager.getImage(Images.editor_map).getWidth() / 2 + iTranslateX, this.getMenuElement(6).getPosY() + this.getMenuElement(6).getHeight() / 2 + this.getMenuPosY() - ImageManager.getImage(Images.editor_map).getHeight() / 2 + iTranslateY);
        ImageManager.getImage(Images.editor_city).draw(oSB, this.getMenuElement(7).getPosX() + this.getMenuElement(7).getTextPos() / 2 - ImageManager.getImage(Images.editor_city).getWidth() / 2 + iTranslateX, this.getMenuElement(7).getPosY() + this.getMenuElement(7).getHeight() / 2 + this.getMenuPosY() - ImageManager.getImage(Images.editor_city).getHeight() / 2 + iTranslateY);
        ImageManager.getImage(Images.editor_game).draw(oSB, this.getMenuElement(8).getPosX() + this.getMenuElement(8).getTextPos() / 2 - ImageManager.getImage(Images.editor_game).getWidth() / 2 + iTranslateX, this.getMenuElement(8).getPosY() + this.getMenuElement(8).getHeight() / 2 + this.getMenuPosY() - ImageManager.getImage(Images.editor_game).getHeight() / 2 + iTranslateY);
        ImageManager.getImage(Images.editor_game).draw(oSB, this.getMenuElement(9).getPosX() + this.getMenuElement(9).getTextPos() / 2 - ImageManager.getImage(Images.editor_game).getWidth() / 2 + iTranslateX, this.getMenuElement(9).getPosY() + this.getMenuElement(9).getHeight() / 2 + this.getMenuPosY() - ImageManager.getImage(Images.editor_game).getHeight() / 2 + iTranslateY);
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
                CFG.PLAYER_TURNID = 0;
                CFG.menuManager.setViewID(Menu.eEDITOR_SCENARIOS);
                break;
            }
            case 2: {
                CFG.menuManager.setViewID(Menu.eEDITOR_CIVILIZATIONS);
                break;
            }
            case 3: {
                CFG.RELOAD_SCENARIO = true;
                CFG.menuManager.setViewID(Menu.eGAME_LEADERS);
                break;
            }
            case 4: {
                CFG.RELOAD_SCENARIO = true;
                CFG.menuManager.setViewID(Menu.eGAME_RANDOM_LEADERS);
                break;
            }
            case 5: {
                CFG.menuManager.setViewID(Menu.eGAME_DECISIONS_EDITOR);
                break;
            }
            case 6: {
                CFG.menuManager.setViewID(Menu.eMAP_EDITOR);
                break;
            }
            case 7: {
                CFG.menuManager.setViewID(Menu.eEDITOR_CITIES);
                break;
            }
            case 8: {
                CFG.menuManager.setViewID(Menu.eGAME_EDITOR);
                break;
            }
            case 9: {
                CFG.GO_TO_LINK = "http://www.AgeofCivilizationsGame.com";
                CFG.setDialogType(Dialog.GO_TO_LINK);
                break;
            }
        }
    }
    
    @Override
    protected final void onBackPressed() {
        if (CFG.lCreateScenario_UndoAssignProvincesCivID != null) {
            CFG.lCreateScenario_UndoAssignProvincesCivID.clear();
        }
        if (CFG.lCreateScenario_UndoWastelandProvinces != null) {
            CFG.lCreateScenario_UndoWastelandProvinces.clear();
        }
        if (CFG.RELOAD_SCENARIO) {
            reloadScenario();
        }
        CFG.editor_Continent_GameData = null;
        CFG.editor_Package_ContinentsData = null;
        CFG.editorAlliancesNames_GameData = null;
        CFG.editorPalletOfCivsColors_Data = null;
        CFG.editorCivilization_GameData = null;
        CFG.editorTerrain_Data2 = null;
        CFG.editorServiceRibbon_GameData = null;
        CFG.editorCity = null;
        CFG.editorServiceRibbon_Colors = null;
        CFG.editorLine_GameData = null;
        CFG.eventsManager.lCreateScenario_Event = null;
        CFG.brushTool = false;
        CFG.menuManager.setViewID(Menu.eMAINMENU);
        CFG.menuManager.setBackAnimation(true);
    }
    
    protected static final void reloadScenario() {
        CFG.PLAYER_TURNID = 0;
        CFG.FOG_OF_WAR = 2;
        CFG.CAPITULATION = 1;
        Game_Render_Province.updateDrawProvinces();
        CFG.game.loadScenario(false);
        CFG.game.initPlayers();
        CFG.RELOAD_SCENARIO = false;
    }
}
