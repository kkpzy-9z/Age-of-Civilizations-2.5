/*
 * Decompiled with CFR 0.152.
 */
package age.of.civilizations2.jakowski.lukasz;

import age.of.civilizations2.jakowski.lukasz.AI;
import age.of.civilizations2.jakowski.lukasz.Button_Diplomacy;
import age.of.civilizations2.jakowski.lukasz.CFG;
import age.of.civilizations2.jakowski.lukasz.Game_Action;
import age.of.civilizations2.jakowski.lukasz.Game_Ages;
import age.of.civilizations2.jakowski.lukasz.Game_NewGame;
import age.of.civilizations2.jakowski.lukasz.Game_NextTurnUpdate;
import age.of.civilizations2.jakowski.lukasz.Game_Render_Province;
import age.of.civilizations2.jakowski.lukasz.Graph_CircleDraw;
import age.of.civilizations2.jakowski.lukasz.HistoryManager;
import age.of.civilizations2.jakowski.lukasz.HolyRomanEmpire_Manager;
import age.of.civilizations2.jakowski.lukasz.Ideologies_Manager;
import age.of.civilizations2.jakowski.lukasz.ImageManager;
import age.of.civilizations2.jakowski.lukasz.Images;
import age.of.civilizations2.jakowski.lukasz.LinesManager;
import age.of.civilizations2.jakowski.lukasz.Map_Scale;
import age.of.civilizations2.jakowski.lukasz.Menu;
import age.of.civilizations2.jakowski.lukasz.MenuElement;
import age.of.civilizations2.jakowski.lukasz.Pallet_Manager;
import age.of.civilizations2.jakowski.lukasz.PlagueManager;
import age.of.civilizations2.jakowski.lukasz.Report_Data;
import age.of.civilizations2.jakowski.lukasz.ServiceRibbon_Manager;
import age.of.civilizations2.jakowski.lukasz.SliderMenu;
import age.of.civilizations2.jakowski.lukasz.TerrainTypesManager;
import age.of.civilizations2.jakowski.lukasz.UnionsManager;
import age.of.civilizations2.jakowski.lukasz.ViewsManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.util.ArrayList;
import java.util.HashMap;

class Menu_InitGame
extends SliderMenu {
    private int iStepID = 0;
    private int iNumOfSteps = 33;
    protected int numToLoad = 60;
    protected int numToLoad2 = 120;

    protected Menu_InitGame() {
        ArrayList<MenuElement> menuElements = new ArrayList<MenuElement>();
        if (!CFG.isDesktop()) {
            this.numToLoad = 30;
            this.numToLoad2 = 75;
        }
        this.initMenu(null, 0, 0, CFG.GAME_WIDTH, CFG.GAME_HEIGHT, menuElements);
    }

    @Override
    protected void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean sliderMenuIsActive) {
        oSB.setColor(new Color(0.0f, 0.0f, 0.0f, 0.6f));
        ImageManager.getImage(Images.gradient).draw(oSB, iTranslateX, -ImageManager.getImage(Images.gradient).getHeight() + iTranslateY, CFG.GAME_WIDTH, CFG.PADDING * 3);
        ImageManager.getImage(Images.gradient).draw(oSB, iTranslateX, CFG.GAME_HEIGHT - ImageManager.getImage(Images.gradient).getHeight() - CFG.PADDING * 3 + iTranslateY, CFG.GAME_WIDTH, CFG.PADDING * 3, false, true);
        oSB.setColor(Color.WHITE);
        super.draw(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
        CFG.drawLoading(oSB, (int)((float)CFG.GAME_WIDTH * CFG.getLoadingPadding()) + iTranslateX, CFG.GAME_HEIGHT - (int)((float)CFG.BUTTON_HEIGHT * 0.6f) * 2 - CFG.PADDING + iTranslateY, (int)((float)CFG.GAME_WIDTH * (1.0f - CFG.getLoadingPadding() * 2.0f)), (int)((float)CFG.BUTTON_HEIGHT * 0.6f), (float)this.iStepID / (float)(this.iNumOfSteps + CFG.map.getMapNumOfProvinces(CFG.map.getActiveMapID()) * 2));
        CFG.drawJakowskiGames_RIGHT_BOT(oSB, iTranslateX, (float)this.iStepID / (float)(this.iNumOfSteps + CFG.map.getMapNumOfProvinces(CFG.map.getActiveMapID()) * 2));
        new Thread(new Runnable(){

            @Override
            public void run() {
                Gdx.app.postRunnable(new Runnable(){

                    @Override
                    public void run() {
                        Menu_InitGame.this.loadAssets();
                    }
                });
            }
        }).start();
        CFG.drawVersion_LEFT_BOT(oSB, iTranslateX);
        CFG.setRender_3(true);
    }

    private final void loadAssets() {
        block50: {
            try {
                if (this.iStepID == 0) {
                    CFG.map.getMapBG().loadGameMap();
                    CFG.map.getMapScroll().stopScrollingTheMap();
                    CFG.map.getMapScale().setCurrentScale(Map_Scale.MINSCALE);
                    CFG.map.getMapCoordinates().setNewPosX(-((int)((float)(CFG.map.getMapBG().getWidth() / 2) - (float)CFG.GAME_WIDTH / Map_Scale.MINSCALE / 2.0f)));
                    CFG.map.getMapCoordinates().setNewPosY(-((int)((float)(CFG.map.getMapBG().getHeight() / 2) - (float)CFG.GAME_HEIGHT / Map_Scale.MINSCALE / 2.0f)));
                    CFG.map.getMapCoordinates().updateMapPos();
                    CFG.glyphLayout.setText(CFG.fontMain, CFG.sLoading);
                    CFG.iLoadingWidth = (int)CFG.glyphLayout.width;
                    CFG.sLoading = CFG.langManager.get("LoadingMap");
                    CFG.palletManager = new Pallet_Manager();
                    ++this.iStepID;
                    break block50;
                }
                if (this.iStepID == 1) {
                    ++this.iStepID;
                    break block50;
                }
                if (this.iStepID == 2) {
                    CFG.map.initMapContinents();
                    CFG.map.initMapRegions();
                    CFG.loadFontArmy();
                    CFG.oAI = new AI();
                    CFG.RANDOM_CIVILIZATION = CFG.langManager.get("RandomCivilization");
                    CFG.sVERSION = CFG.langManager.get("Version");
                    CFG.sAUTHOR = CFG.langManager.get("Author");
                    CFG.glyphLayout.setText(CFG.fontMain, CFG.sLoading);
                    CFG.iLoadingWidth = (int)CFG.glyphLayout.width;
                    CFG.sLoading = CFG.langManager.get("LoadingGraphics");
                    ++this.iStepID;
                    break block50;
                }
                if (this.iStepID == 3) {
                    Images.btnh_close = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "buttons/" + "close_h.png");
                    Images.btnh_menu_h = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "buttons/" + "menu_h.png");
                    Images.btnh_clear = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "buttons/" + "clear_h.png");
                    Menu_InitGame.loadArmyBGImages();
                    CFG.game_NextTurnUpdate = new Game_NextTurnUpdate();
                    CFG.gameAges = new Game_Ages();
                    //new autonomy load
                    CFG.gameAutonomy = new Game_Autonomy();
                    //new decisions load
                    CFG.gameDecisions = new Game_Decisions();
                    CFG.plagueManager = new PlagueManager();
                    ++this.iStepID;
                    break block50;
                }
                if (this.iStepID == 4) {
                    ++this.iStepID;
                    break block50;
                }
                if (this.iStepID == 5) {
                    CFG.terrainTypesManager = new TerrainTypesManager();
                    CFG.ideologiesManager = new Ideologies_Manager();
                    CFG.ideologiesManager.loadIdeologies();
                    CFG.unionsManager = new UnionsManager();
                    CFG.dynamicEventManager = new DynamicEventManager(); //sandboxcut init eventmanager change//
                    CFG.DYNAMIC_EVENTS = true; //2.5 init eventmanager change//
                    CFG.PLAYER_PEACE = false; //2.5 init playerpeace change//
                    CFG.AI_VASSALS = false; //2.5 init AIVassals change//
                    CFG.AI_DIPLOMACY = true;
                    CFG.sLoading = CFG.langManager.get("LoadingGameData");
                    ++this.iStepID;
                    break block50;
                }
                if (this.iStepID == 6) {
                    Images.title_edge = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "title/" + "title_edge.png");
                    Images.dialog_title = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "dialog/" + "title.png");
                    Images.dialog_desc = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "dialog/" + "desc.png");
                    Images.dialog_line = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "dialog/" + "line_2xdesc.png");
                    Images.main_menu_edge = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "main_menu/" + "main_menu_edge.png");
                    Images.main_menu_edge2 = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "main_menu/" + "main_menu_edge2.png");
                    Images.logo_steam = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "main_menu/" + "pc.png");
                    Images.logo_android = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "main_menu/" + "android.png");
                    Images.logo_app = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "main_menu/" + "app.png");
                    Images.logo_fb = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "main_menu/" + "fb.png");
                    Images.logo_twit = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "main_menu/" + "twit.png");
                    Images.logo_yt = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "main_menu/" + "yt.png");
                    CFG.sLoading = CFG.langManager.get("LoadingGraphics");
                    ++this.iStepID;
                    break block50;
                }
                if (this.iStepID == 7) {
                    Images.slider_rect_edge = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "loading/" + "slider_edge.png", Pixmap.Format.RGBA8888, Texture.TextureFilter.Nearest, Texture.TextureWrap.ClampToEdge);
                    Images.randomCivilizationFlag = ImageManager.addImage("game/flags/ran.png");
                    CFG.sLoading = CFG.langManager.get("LoadingMap");
                    ++this.iStepID;
                    break block50;
                }
                if (this.iStepID >= 8 && this.iStepID < 8 + CFG.map.getMapNumOfProvinces(CFG.map.getActiveMapID())) {
                    CFG.sLoading = CFG.langManager.get("LoadingMap") + " [#" + (this.iStepID - 8) + "/" + CFG.map.getMapNumOfProvinces(CFG.map.getActiveMapID()) + "] - ";
                    for (int i = 0; i < this.numToLoad2 && this.iStepID < 8 + CFG.map.getMapNumOfProvinces(CFG.map.getActiveMapID()); ++i) {
                        CFG.game.loadProvince(this.iStepID++ - 8);
                    }
                    break block50;
                }
                if (this.iStepID == 8 + CFG.map.getMapNumOfProvinces(CFG.map.getActiveMapID())) {
                    CFG.sLoading = CFG.langManager.get("LoadingProvinces");
                    CFG.saveSettings_LoadingStatus();
                    ++this.iStepID;
                    break block50;
                }
                if (this.iStepID >= 9 + CFG.map.getMapNumOfProvinces(CFG.map.getActiveMapID()) && this.iStepID < 9 + CFG.map.getMapNumOfProvinces(CFG.map.getActiveMapID()) * 2) {
                    CFG.sLoading = CFG.langManager.get("LoadingProvinces") + " [#" + (this.iStepID - (9 + CFG.map.getMapNumOfProvinces(CFG.map.getActiveMapID()))) + "/" + CFG.map.getMapNumOfProvinces(CFG.map.getActiveMapID()) + "] - ";
                    for (int i = 0; i < this.numToLoad && this.iStepID < 9 + CFG.map.getMapNumOfProvinces(CFG.map.getActiveMapID()) * 2; ++i) {
                        CFG.game.loadProvinceTexture(this.iStepID++ - 9 - CFG.map.getMapNumOfProvinces(CFG.map.getActiveMapID()));
                    }
                    break block50;
                }
                if (this.iStepID == 9 + CFG.map.getMapNumOfProvinces(CFG.map.getActiveMapID()) * 2) {
                    CFG.holyRomanEmpire_Manager = new HolyRomanEmpire_Manager();
                    CFG.game.initGameScenarios();
                    Images.btn_clear_checkbox_true = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "buttons/" + "clear_checkbox_true.png");
                    Images.btn_clear_checkbox_false = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "buttons/" + "clear_checkbox_false.png");
                    Images.btn_v = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "buttons/" + "v.png");
                    Images.btn_v_active = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "buttons/" + "v_active.png");
                    Images.btn_x = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "buttons/" + "x.png");
                    Images.btn_x_active = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "buttons/" + "x_active.png");
                    Images.btn_menu_1_h = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "buttons/" + "menu_1.png");
                    Images.btnh_menu_1_h = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "buttons/" + "menu_1_h.png");
                    Images.arrow = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "buttons/" + "arrow.png");
                    Images.arrow_active = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "buttons/" + "arrow_active.png");
                    Images.btn_remove = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "buttons/" + "remove.png");
                    Images.btn_up = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "buttons/" + "up.png");
                    Images.btn_localization = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "buttons/" + "localization.png");
                    Images.btn_show = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "buttons/" + "show.png");
                    Images.btn_add = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "buttons/" + "add.png");
                    Images.bg_game_menu = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "bg_game_menu.png");
                    Images.bg_game_menu_r = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "bg_game_menu_r.png");
                    Images.bg_game_action = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "bg_game_action.png");
                    Images.bg_stats = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "bg_stats.png");
                    CFG.sLoading = CFG.langManager.get("LoadingGraphics");
                    ++this.iStepID;
                    break block50;
                }
                if (this.iStepID == 10 + CFG.map.getMapNumOfProvinces(CFG.map.getActiveMapID()) * 2) {
                    Images.new_game_top = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "new_game/" + "top.png");
                    Images.new_game_top_edge = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "new_game/" + "top_edge.png");
                    Images.new_game_top_edge_title = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "new_game/" + "top_edge_title.png");
                    Images.new_game_top_edge_line = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "new_game/" + "top_edge_line.png");
                    Images.new_game_top_edge_line_horizontal = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "new_game/" + "top_edge_line_horizontal.png");
                    Images.new_game_box = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "new_game/" + "box.png");
                    Images.new_game_box_hover = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "new_game/" + "box_hover.png");
                    Images.new_game_box_line = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "new_game/" + "box_line_end.png");
                    Images.new_game_box_line_hover = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "new_game/" + "box_line_end_hover.png");
                    Images.editor_top = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "editor/" + "editor_top.png");
                    Images.editor_top_line = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "editor/" + "editor_top_line.png");
                    Images.editor_line = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "editor/" + "editor_line.png");
                    Images.bot_end_left = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "bottom/" + "end_left.png");
                    Images.bot_prov_name = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "bottom/" + "prov_name.png");
                    Images.bot_prov_name_left = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "bottom/" + "prov_name_left.png");
                    Images.bot_icons_bg = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "bottom/" + "icons_bg.png");
                    ++this.iStepID;
                    break block50;
                }
                if (this.iStepID == 11 + CFG.map.getMapNumOfProvinces(CFG.map.getActiveMapID()) * 2) {
                    Images.city = ImageManager.addImage("UI/icons/city.png");
                    Images.city2 = ImageManager.addImage("UI/icons/city2.png");
                    Images.city3 = ImageManager.addImage("UI/icons/city3.png");
                    Images.city4 = ImageManager.addImage("UI/icons/city4.png");
                    Images.city5 = ImageManager.addImage("UI/icons/city5.png");
                    Images.mount = ImageManager.addImage("UI/icons/mount.png");
                    Images.patt = ImageManager.addImage("UI/patt.png", Pixmap.Format.RGBA8888, Texture.TextureFilter.Nearest, Texture.TextureWrap.Repeat);
                    Images.patt2 = ImageManager.addImage("UI/patt2.png", Pixmap.Format.RGBA8888, Texture.TextureFilter.Nearest, Texture.TextureWrap.Repeat);
                    Images.patt3 = ImageManager.addImage("UI/patt3.png", Pixmap.Format.RGBA8888, Texture.TextureFilter.Nearest, Texture.TextureWrap.Repeat);
                    Images.patt_square = ImageManager.addImage("UI/pattsquare.png", Pixmap.Format.RGBA8888, Texture.TextureFilter.Nearest, Texture.TextureWrap.Repeat);
                    //old, replaced w random because breaking
                    //Images.aoh3Logo = ImageManager.addImage("aoh3.png", Pixmap.Format.RGBA8888, Texture.TextureFilter.Linear, Texture.TextureWrap.Repeat);
                    Images.aoh3Logo = ImageManager.addImage("UI/pattsquare.png", Pixmap.Format.RGBA8888, Texture.TextureFilter.Linear, Texture.TextureWrap.Repeat);
                    Images.port_ico = ImageManager.addImage("UI/icons/port.png");
                    Images.tower_ico = ImageManager.addImage("UI/icons/tower.png");
                    Images.fort_ico = ImageManager.addImage("UI/icons/fort.png");
                    Images.towerfort_ico = ImageManager.addImage("UI/icons/towerfort.png");
                    Images.armoury_ico = ImageManager.addImage("UI/icons/armoury.png");
                    Images.line_11 = ImageManager.addImage("UI/lines/line_11.png", Pixmap.Format.RGBA8888, Texture.TextureFilter.Linear, Texture.TextureWrap.Repeat);
                    Images.line_22 = ImageManager.addImage("UI/lines/line_22.png", Pixmap.Format.RGBA8888, Texture.TextureFilter.Linear, Texture.TextureWrap.Repeat);
                    Images.line_32 = ImageManager.addImage("UI/lines/line_32.png", Pixmap.Format.RGBA8888, Texture.TextureFilter.Linear, Texture.TextureWrap.Repeat);
                    Images.line_33 = ImageManager.addImage("UI/lines/line_33.png", Pixmap.Format.RGBA8888, Texture.TextureFilter.Linear, Texture.TextureWrap.Repeat);
                    Images.line_44 = ImageManager.addImage("UI/lines/line_44.png", Pixmap.Format.RGBA8888, Texture.TextureFilter.Linear, Texture.TextureWrap.Repeat);
                    Images.line_26 = ImageManager.addImage("UI/lines/line_26.png", Pixmap.Format.RGBA8888, Texture.TextureFilter.Linear, Texture.TextureWrap.Repeat);
                    Images.line_62 = ImageManager.addImage("UI/lines/line_62.png", Pixmap.Format.RGBA8888, Texture.TextureFilter.Linear, Texture.TextureWrap.Repeat);
                    Images.line_32_vertical = ImageManager.addImage("UI/lines/line_32_vertical.png", Pixmap.Format.RGBA8888, Texture.TextureFilter.Linear, Texture.TextureWrap.Repeat);
                    ++this.iStepID;
                    break block50;
                }
                if (this.iStepID == 12 + CFG.map.getMapNumOfProvinces(CFG.map.getActiveMapID()) * 2) {
                    Images.army_capital = ImageManager.addImage("UI/icons/army/army_capital.png", Pixmap.Format.RGBA8888, Texture.TextureFilter.Nearest, Texture.TextureWrap.ClampToEdge);
                    Images.army_capital_frame = ImageManager.addImage("UI/icons/army/army_capital_frame.png", Pixmap.Format.RGBA8888, Texture.TextureFilter.Nearest, Texture.TextureWrap.ClampToEdge);
                    Images.civ_name_bg = ImageManager.addImage("UI/icons/army/civ_name_bg.png");
                    Images.civ_flag_bg = ImageManager.addImage("UI/icons/army/civ_flag_bg.png");
                    Images.circle_55 = ImageManager.addImage("UI/icons/circle_55.png");
                    Images.population = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "population.png");
                    Images.population_growth = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "population_growth.png");
                    Images.economy = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "economy.png");
                    Images.disease = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "disease.png");
                    Images.victoryPoints = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "victory_points.png");
                    Images.rank = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "rank.png");
                    Images.time = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "time.png");
                    Images.icon_move_attack = ImageManager.addImage("UI/icons/move_0.png");
                    Images.icon_move_ally = ImageManager.addImage("UI/icons/move_1.png");
                    Images.icon_move_sea = ImageManager.addImage("UI/icons/move_2.png");
                    Images.skull = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "skull.png");
                    Images.flag_rect = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "flags/" + "rect.png");
                    CFG.CIV_FLAG_WIDTH = ImageManager.getImage(Images.flag_rect).getWidth();
                    CFG.CIV_FLAG_HEIGHT = ImageManager.getImage(Images.flag_rect).getHeight();
                    Images.flag_circle = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "flags/" + "circle.png");
                    Images.flag_circle_shader = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "flags/" + "circle_sh.png");
                    ++this.iStepID;
                    break block50;
                }
                if (this.iStepID == 13 + CFG.map.getMapNumOfProvinces(CFG.map.getActiveMapID()) * 2) {
                    Images.scroll_posiotion = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "scroll_posiotion.png");
                    Images.scroll_posiotion_active = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "scroll_posiotion_active.png");
                    Images.slide_bg = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "slide/" + "slide_bg.png");
                    Images.slider_gradient = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "slider_gradient.png", Pixmap.Format.RGBA8888, Texture.TextureFilter.Linear, Texture.TextureWrap.ClampToEdge);
                    Images.pickerSV = ImageManager.addImage("UI/picker/sv.png", Pixmap.Format.RGBA8888, Texture.TextureFilter.Linear, Texture.TextureWrap.ClampToEdge);
                    Images.pickerHUE = ImageManager.addImage("UI/picker/hue.png", Pixmap.Format.RGBA8888, Texture.TextureFilter.Linear, Texture.TextureWrap.ClampToEdge);
                    Images.pickerSVPos = ImageManager.addImage("UI/picker/pos.png");
                    Images.pickerEdge = ImageManager.addImage("UI/picker/edge.png");
                    CFG.sLoading = CFG.langManager.get("LoadingMap");
                    ++this.iStepID;
                    break block50;
                }
                if (this.iStepID == 14 + CFG.map.getMapNumOfProvinces(CFG.map.getActiveMapID()) * 2) {
                    CFG.game.updateProvincesSize();
                    Game_Render_Province.updateDrawProvinces();
                    CFG.game.checkLandBySeaProvincesBorders();
                    ++this.iStepID;
                    break block50;
                }
                if (this.iStepID == 15 + CFG.map.getMapNumOfProvinces(CFG.map.getActiveMapID()) * 2) {
                    CFG.game.checkSeaBySeaProvincesBorders();
                    CFG.game.buildProvinceBorder();
                    CFG.sLoading = CFG.langManager.get("LoadingGameData");
                    ++this.iStepID;
                    break block50;
                }
                if (this.iStepID == 16 + CFG.map.getMapNumOfProvinces(CFG.map.getActiveMapID()) * 2) {
                    CFG.loadFontBorder();
                    ++this.iStepID;
                    break block50;
                }
                if (this.iStepID == 17 + CFG.map.getMapNumOfProvinces(CFG.map.getActiveMapID()) * 2) {
                    CFG.gameAction = new Game_Action();
                    CFG.game.initGameCities();
                    CFG.game.loadCities();
                    ++this.iStepID;
                    break block50;
                }
                if (this.iStepID == 18 + CFG.map.getMapNumOfProvinces(CFG.map.getActiveMapID()) * 2) {
                    CFG.game.loadScenario(false);
                    ++this.iStepID;
                    break block50;
                }
                if (this.iStepID == 19 + CFG.map.getMapNumOfProvinces(CFG.map.getActiveMapID()) * 2) {
                    CFG.game.initPlayers();
                    ++this.iStepID;
                    break block50;
                }
                if (this.iStepID == 20 + CFG.map.getMapNumOfProvinces(CFG.map.getActiveMapID()) * 2) {
                    CFG.game.buildDrawArmy();
                    CFG.game.buildBasinsOfSeaProvinces();
                    ++this.iStepID;
                    break block50;
                }
                if (this.iStepID == 21 + CFG.map.getMapNumOfProvinces(CFG.map.getActiveMapID()) * 2) {
                    CFG.game.loadProvinceNames_ALL();
                    CFG.game.loadRegions();
                    ++this.iStepID;
                    break block50;
                }
                if (this.iStepID == 22 + CFG.map.getMapNumOfProvinces(CFG.map.getActiveMapID()) * 2) {
                    CFG.game.initGameMountains();
                    CFG.game.loadMountains();
                    Images.bot_left = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "bot/" + "left.png");
                    Images.bot_left_red = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "bot/" + "left_red.png");
                    Images.top_left = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "top/" + "left.png");
                    Images.top_left2 = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "top/" + "left2.png");
                    Images.top_left3 = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "top/" + "left3.png");
                    Images.top_left2_sha = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "top/" + "left2_sha.png");
                    Images.top_left_extra = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "top/" + "left_extra.png");
                    Images.messages = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "top/" + "message.png");
                    Images.messages_g = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "top/" + "message_g.png");
                    Images.messages_r = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "top/" + "message_r.png");
                    Images.top_circle = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "top/" + "circle.png");
                    Images.top_flag_frame = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "top/" + "flag_frame.png");
                    Images.top_flag_frame_h = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "top/" + "flag_frame_h.png");
                    Images.top_civ_color = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "top/" + "civ_color.png");
                    Images.top_civ_color_shader = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "top/" + "civ_color_sh.png");
                    Images.top_view_right = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "top/" + "view_right.png");
                    Images.top_view_right_h = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "top/" + "view_right_h.png");
                    Images.top_view_right_last = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "top/" + "view_right_last.png");
                    CFG.sLoading = CFG.langManager.get("LoadingGraphics");
                    ++this.iStepID;
                    break block50;
                }
                if (this.iStepID == 23 + CFG.map.getMapNumOfProvinces(CFG.map.getActiveMapID()) * 2) {
                    Images.difficulty_box = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "difficulty/" + "difficulty_box.png");
                    Images.difficulty_heaven = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "difficulty/" + "difficulty_heaven.png");
                    Images.difficulty_hell = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "difficulty/" + "difficulty_hell.png");
                    Images.top_gold = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "gold.png");
                    Images.top_gold2 = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "gold_medium.png");
                    Images.top_movement_points = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "movement_points.png");
                    Images.top_diplomacy_points = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "diplomacy_points.png");
                    Images.ar_up = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "ar_up.png");
                    Images.ar_down = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "ar_down.png");
                    Images.hre_icon = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "hre_icon.png");
                    Images.hre_flag = ImageManager.addImage("UI/icons/hre_flag.png");
                    Images.hre_crown = ImageManager.addImage("UI/icons/crowns/hre.png");
                    Images.hre_crown_x = ImageManager.addImage("UI/icons/crowns/hre_x.png");
                    Images.hre_crown_scaled = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "crowns/" + "hre.png");
                    Images.stats = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "stats.png");
                    Images.dice = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "dice.png");
                    ++this.iStepID;
                    break block50;
                }
                if (this.iStepID == 24 + CFG.map.getMapNumOfProvinces(CFG.map.getActiveMapID()) * 2) {
                    Images.technology = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "technology.png");
                    Images.provinces = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "provinces.png");
                    Images.research = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "research.png");
                    Images.development = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "development.png");
                    Images.development_down = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "development_down.png");
                    Images.happiness = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "happiness.png");
                    Images.happiness1 = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "happiness1.png");
                    Images.happiness2 = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "happiness2.png");
                    Images.b_fort = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "b_fort.png");
                    Images.b_tower = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "b_tower.png");
                    Images.b_port = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "b_port.png");
                    Images.b_farm = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "b_farm.png");
                    Images.b_library = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "b_library.png");
                    Images.b_armoury = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "b_armoury.png");
                    Images.b_workshop = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "b_workshop.png");
                    Images.b_supply = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "b_supply.png");
                    ++this.iStepID;
                    break block50;
                }
                if (this.iStepID == 25 + CFG.map.getMapNumOfProvinces(CFG.map.getActiveMapID()) * 2) {
                    Images.diplo_war = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "war.png");
                    Images.diplo_weariness = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "weariness.png");
                    Images.diplo_war_preparations = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "war_preparations.png");
                    Images.diplo_truce = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "truce.png");
                    Images.diplo_alliance = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "alliance.png");
                    Images.diplo_relations = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "relations.png");
                    Images.diplo_relations_inc = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "relations_inc.png");
                    Images.diplo_relations_dec = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "relations_dec.png");
                    Images.diplo_trade = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "trade.png");
                    Images.diplo_defensive_pact = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "defensive.png");
                    Images.diplo_non_aggression = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "nonagg.png");
                    Images.diplo_guarantee_has = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "indepe.png");
                    Images.diplo_guarantee_gives = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "indepe2.png");
                    Images.diplo_access_has = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "military.png");
                    Images.diplo_access_gives = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "military2.png");
                    Images.diplo_gift = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "gift.png");
                    Images.diplo_revolution = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "revolution.png");
                    Images.diplo_popstability = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "popstability.png");
                    Images.diplo_union = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "union.png");
                    Images.diplo_vassal = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "vassal.png");
                    Images.diplo_lord = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "lord.png");
                    Images.diplo_loan = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "loan.png");
                    Images.diplo_loan2 = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "loan2.png");
                    Images.diplo_plunder = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "plunder.png");
                    Images.diplo_army = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "army.png");
                    Images.diplo_goldenage = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "goldenage.png");
                    Images.diplo_goldenage_m = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "goldenage_m.png");
                    Images.diplo_goldenage_s = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "goldenage_s.png");
                    Images.defensive_position = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "defensive_position.png");
                    Images.diplo_festival = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "festival.png");
                    Images.transfer_control = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "transfer_control.png");
                    Images.diplo_heart = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "heart.png");
                    Images.diplo_rivals = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "rivals.png");
                    Images.diplo_AZ = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "az.png");
                    Button_Diplomacy.setMaxDiploWidth(ImageManager.getImage(Images.top_movement_points).getWidth());
                    Button_Diplomacy.setMaxDiploWidth(ImageManager.getImage(Images.diplo_war).getWidth());
                    Button_Diplomacy.setMaxDiploWidth(ImageManager.getImage(Images.diplo_truce).getWidth());
                    Button_Diplomacy.setMaxDiploWidth(ImageManager.getImage(Images.diplo_alliance).getWidth());
                    Button_Diplomacy.setMaxDiploWidth(ImageManager.getImage(Images.diplo_relations).getWidth());
                    Button_Diplomacy.setMaxDiploWidth(ImageManager.getImage(Images.diplo_relations_inc).getWidth());
                    Button_Diplomacy.setMaxDiploWidth(ImageManager.getImage(Images.diplo_relations_dec).getWidth());
                    Button_Diplomacy.setMaxDiploWidth(ImageManager.getImage(Images.diplo_trade).getWidth());
                    Button_Diplomacy.setMaxDiploWidth(ImageManager.getImage(Images.diplo_defensive_pact).getWidth());
                    Button_Diplomacy.setMaxDiploWidth(ImageManager.getImage(Images.diplo_non_aggression).getWidth());
                    Button_Diplomacy.setMaxDiploWidth(ImageManager.getImage(Images.diplo_access_has).getWidth());
                    Button_Diplomacy.setMaxDiploWidth(ImageManager.getImage(Images.diplo_guarantee_gives).getWidth());
                    Button_Diplomacy.setMaxDiploWidth(ImageManager.getImage(Images.diplo_access_has).getWidth());
                    Button_Diplomacy.setMaxDiploWidth(ImageManager.getImage(Images.diplo_access_gives).getWidth());
                    Button_Diplomacy.setMaxDiploWidth(ImageManager.getImage(Images.diplo_gift).getWidth());
                    Button_Diplomacy.setMaxDiploWidth(ImageManager.getImage(Images.diplo_revolution).getWidth());
                    Button_Diplomacy.setMaxDiploWidth(ImageManager.getImage(Images.diplo_union).getWidth());
                    Button_Diplomacy.setMaxDiploWidth(ImageManager.getImage(Images.diplo_vassal).getWidth());
                    Button_Diplomacy.setMaxDiploWidth(ImageManager.getImage(Images.diplo_lord).getWidth());
                    Button_Diplomacy.setMaxDiploWidth(ImageManager.getImage(Images.diplo_loan).getWidth());
                    Button_Diplomacy.setMaxDiploWidth(ImageManager.getImage(Images.diplo_loan2).getWidth());
                    Button_Diplomacy.setMaxDiploWidth(ImageManager.getImage(Images.diplo_plunder).getWidth());
                    ++this.iStepID;
                    break block50;
                }
                if (this.iStepID == 26 + CFG.map.getMapNumOfProvinces(CFG.map.getActiveMapID()) * 2) {
                    Images.act_plunder = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "act_plunder.png");
                    Images.act_recruit = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "act_recruit.png");
                    Images.act_move = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "act_move.png");
                    Images.act_moveto = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "act_moveto.png");
                    Images.act_more = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "act_more.png");
                    Images.act_migrate = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "act_migrate.png");
                    ++this.iStepID;
                    break block50;
                }
                if (this.iStepID == 27 + CFG.map.getMapNumOfProvinces(CFG.map.getActiveMapID()) * 2) {
                    Images.editor_game = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "editor_game.png");
                    Images.editor_map = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "editor_map.png");
                    Images.editor_city = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "editor_city.png");
                    Images.editor_civ = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "editor_civ.png");
                    Images.icon_save = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "save.png");
                    Images.editor_leaders = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "editor_leaders.png");
                    Images.icon_check_true = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "check_true.png");
                    Images.icon_check_false = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "check_false.png");
                    CFG.graphCircleDraw = new Graph_CircleDraw("bg.png", "over.png", "frame.png");
                    Images.wikipedia = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "wiki.png");
                    Images.pickeIcon = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "picker_icon.png");
                    Images.diplo_message = ImageManager.addImage("UI/" + CFG.getRescouresPath() + "icons/" + "message.png");
                    ++this.iStepID;
                    break block50;
                }
                if (this.iStepID == 28 + CFG.map.getMapNumOfProvinces(CFG.map.getActiveMapID()) * 2) {
                    Images.map_border = ImageManager.addImage("UI/lines/map_border.png", Pixmap.Format.RGBA8888, Texture.TextureFilter.Nearest, Texture.TextureWrap.Repeat);
                    try {
                        FileHandle file = Gdx.files.internal("game/diplomacy_colors/Age_of_Civilizations_Active");
                        CFG.sACTIVE_DIPLOMACY_COLORS_TAG = file.readString();
                    }
                    catch (GdxRuntimeException ex) {
                        CFG.sACTIVE_DIPLOMACY_COLORS_TAG = "DEFAULT";
                    }
                    CFG.loadDiplomacyColors_GameData(CFG.sACTIVE_DIPLOMACY_COLORS_TAG);
                    CFG.loadRandomAlliancesNames();
                    CFG.menuManager.getColorPicker().buildColors();
                    CFG.menuManager.getColorPicker().setHueWidth((int)((float)CFG.BUTTON_WIDTH * 0.35f * CFG.GUI_SCALE));
                    CFG.menuManager.getColorPicker().setSVHeight((int)((float)(ImageManager.getImage(Images.pickerSV).getHeight() * 2) * CFG.GUI_SCALE));
                    CFG.menuManager.getColorPicker().setResizeHeight((int)(30.0f * CFG.GUI_SCALE));
                    CFG.PROVINCE_BORDER_ANIMATION_TIME = new HashMap();
                    CFG.sLoading = CFG.langManager.get("Loading");
                    ++this.iStepID;
                    break block50;
                }
                if (this.iStepID == 29 + CFG.map.getMapNumOfProvinces(CFG.map.getActiveMapID()) * 2) {
                    CFG.game.updateDrawMoveUnitsArmy();
                    CFG.gameNewGame = new Game_NewGame();
                    ++this.iStepID;
                } else if (this.iStepID == 30 + CFG.map.getMapNumOfProvinces(CFG.map.getActiveMapID()) * 2) {
                    CFG.map.getMapBG().loadMinimap();
                    CFG.serviceRibbon_Manager = new ServiceRibbon_Manager();
                    ++this.iStepID;
                } else if (this.iStepID == 31 + CFG.map.getMapNumOfProvinces(CFG.map.getActiveMapID()) * 2) {
                    CFG.viewsManager = new ViewsManager();
                    CFG.linesManager = new LinesManager();
                    ++this.iStepID;
                } else if (this.iStepID == 32 + CFG.map.getMapNumOfProvinces(CFG.map.getActiveMapID()) * 2) {
                    CFG.historyManager = new HistoryManager();
                    CFG.reportData = new Report_Data();
                    CFG.reportData.lAttackers_IDs.add(0);
                    CFG.reportData.lAttackers_Armies.add(1);
                    CFG.reportData.lAttackers_Armies_Lost.add(0);
                    CFG.reportData.lDefenders_IDs.add(0);
                    CFG.reportData.lDefenders_Armies.add(1);
                    CFG.reportData.lDefenders_ArmiesLost.add(0);
                    CFG.map.load_DeleteStatusFile();
                    CFG.FOG_OF_WAR = 2;
                    if (CFG.settingsManager.LANGUAGE_TAG == null) {
                        CFG.backToMenu = Menu.eMAINMENU;
                        CFG.menuManager.setViewID(Menu.eSELECT_LANGUAGE);
                        CFG.map.getMapBG().updateWorldMap_Shaders();
                        CFG.VIEW_SHOW_VALUES = true;
                    } else {
                        CFG.menuManager.setViewID(Menu.eMAINMENU);
                    }
                    ++this.iStepID;
                } else {
                    ++this.iStepID;
                    if (this.iStepID >= 100) {
                        if (CFG.settingsManager.LANGUAGE_TAG == null) {
                            CFG.backToMenu = Menu.eMAINMENU;
                            CFG.menuManager.setViewID(Menu.eSELECT_LANGUAGE);
                            CFG.map.getMapBG().updateWorldMap_Shaders();
                            CFG.VIEW_SHOW_VALUES = true;
                        } else {
                            CFG.menuManager.setViewID(Menu.eMAINMENU);
                        }
                    }
                }
            }
            catch (OutOfMemoryError ex) {
                CFG.toast.setInView("Out of RAM", CFG.COLOR_TEXT_MODIFIER_NEGATIVE2);
                CFG.toast.setTimeInView(6000);
            }
        }
    }

    protected static final void loadArmyBGImages() {
        if (Images.army_bg > 0) {
            try {
                ImageManager.getImage(Images.army_bg).dispose();
                ImageManager.getImage(Images.army_bg).init(new Texture(Gdx.files.internal("UI/icons/army/" + CFG.settingsManager.FONT_ARMY_SIZE + "/army.png"), Pixmap.Format.RGBA8888, true), Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest, Texture.TextureWrap.ClampToEdge, Texture.TextureWrap.ClampToEdge);
            }
            catch (GdxRuntimeException ex) {
                ImageManager.getImage(Images.army_bg).dispose();
                ImageManager.getImage(Images.army_bg).init(new Texture(Gdx.files.internal("UI/icons/army/16/army.png"), Pixmap.Format.RGBA8888, true), Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest, Texture.TextureWrap.ClampToEdge, Texture.TextureWrap.ClampToEdge);
            }
            try {
                ImageManager.getImage(Images.army_sea).dispose();
                ImageManager.getImage(Images.army_sea).init(new Texture(Gdx.files.internal("UI/icons/army/" + CFG.settingsManager.FONT_ARMY_SIZE + "/army_sea.png"), Pixmap.Format.RGBA8888, true), Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest, Texture.TextureWrap.ClampToEdge, Texture.TextureWrap.ClampToEdge);
            }
            catch (GdxRuntimeException ex) {
                ImageManager.getImage(Images.army_sea).dispose();
                ImageManager.getImage(Images.army_sea).init(new Texture(Gdx.files.internal("UI/icons/army/16/army_sea.png"), Pixmap.Format.RGBA8888, true), Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest, Texture.TextureWrap.ClampToEdge, Texture.TextureWrap.ClampToEdge);
            }
            try {
                ImageManager.getImage(Images.army_16_seabg).dispose();
                ImageManager.getImage(Images.army_16_seabg).init(new Texture(Gdx.files.internal("UI/icons/army/" + CFG.settingsManager.FONT_ARMY_SIZE + "/army_seabg.png"), Pixmap.Format.RGBA8888, true), Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest, Texture.TextureWrap.ClampToEdge, Texture.TextureWrap.ClampToEdge);
            }
            catch (GdxRuntimeException ex) {
                ImageManager.getImage(Images.army_16_seabg).dispose();
                ImageManager.getImage(Images.army_16_seabg).init(new Texture(Gdx.files.internal("UI/icons/army/16/army_seabg.png"), Pixmap.Format.RGBA8888, true), Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest, Texture.TextureWrap.ClampToEdge, Texture.TextureWrap.ClampToEdge);
            }
        } else {
            try {
                Images.army_bg = ImageManager.addImage("UI/icons/army/" + CFG.settingsManager.FONT_ARMY_SIZE + "/army.png", Pixmap.Format.RGBA8888, Texture.TextureFilter.Nearest, Texture.TextureWrap.ClampToEdge);
            }
            catch (GdxRuntimeException ex) {
                Images.army_bg = ImageManager.addImage("UI/icons/army/16/army.png", Pixmap.Format.RGBA8888, Texture.TextureFilter.Nearest, Texture.TextureWrap.ClampToEdge);
            }
            try {
                Images.army_sea = ImageManager.addImage("UI/icons/army/" + CFG.settingsManager.FONT_ARMY_SIZE + "/army_sea.png", Pixmap.Format.RGBA8888, Texture.TextureFilter.Nearest, Texture.TextureWrap.ClampToEdge);
            }
            catch (GdxRuntimeException ex) {
                Images.army_sea = ImageManager.addImage("UI/icons/army/16/army_sea.png", Pixmap.Format.RGBA8888, Texture.TextureFilter.Nearest, Texture.TextureWrap.ClampToEdge);
            }
            try {
                Images.army_16_seabg = ImageManager.addImage("UI/icons/army/" + CFG.settingsManager.FONT_ARMY_SIZE + "/army_seabg.png", Pixmap.Format.RGBA8888, Texture.TextureFilter.Nearest, Texture.TextureWrap.ClampToEdge);
            }
            catch (GdxRuntimeException ex) {
                Images.army_16_seabg = ImageManager.addImage("UI/icons/army/16/army_seabg.png", Pixmap.Format.RGBA8888, Texture.TextureFilter.Nearest, Texture.TextureWrap.ClampToEdge);
            }
        }
    }
}

