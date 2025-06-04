package age.of.civilizations2.jakowski.lukasz;

import java.io.Serializable;

class SettingsManager implements Serializable {
   private static final long serialVersionUID = 0L;
   protected String LANGUAGE_TAG = null;
   protected int FONT_MAIN_SIZE = -1;
   protected int FONT_BORDER_SIZE = 36;
   protected int FONT_ARMY_SIZE = -1;
   protected float VOLUME_MUSIC = 0.4F;
   protected float VOLUME_SOUNDS = 0.55F;
   protected float VOLUME_MASTER = 1.0F;
   protected int FONT_BORDER_WIDTH_OF_BORDER = 1;
   protected int PROVINCE_ALPHA = 100;
   protected int OCCUPIED_PROVINCE_ALPHA = 100;
   protected float OCCUPIED_STRIPES_SIZE = 2.0F;
   protected boolean ENABLE_INNER_BORDERS = true;
   protected boolean DRAW_MOVE_UNITS_ARMY_IN_EVERYSINGLE_PROVINCE = true;
   protected boolean CONFIRM_END_TURN = false;
   protected boolean CONFIRM_NO_ORDERS = false;
   protected boolean CONFIRM_NEXT_PLAYER_TURN = true;
   protected boolean DRAW_CIVILIZATIONS_NAMES_OVER_PRPOVINCES_IN_GAME = true;
   protected int PERCETANGE_OF_CITIES_ON_MAP = 24;
   protected int TURNS_BETWEEN_AUTOSAVE = 50;
   protected boolean CONTINUOUS_RENDERING = false;
   protected float CITIES_FONT_SCALE = 0.35F;
   protected final int CITIES_DEFAULT_FONT_SIZE = 10;
   protected Color_GameData civNamesFontColor = new Color_GameData(0.0F, 0.0F, 0.0F);
   protected float civNamesFontColor_ALPHA = 0.85F;
   protected Color_GameData civNamesFontColorBorder = new Color_GameData(0.58F, 0.58F, 0.58F);
   protected float civNamesFontColorBorder_ALPHA = 0.45F;
   protected float CIV_NAMES_MIN_SCALE_OF_FONT = 0.5F;
   protected int CIVILIZATIONS_NAMES_INTERVAL = 1000;
   protected Color_GameData COLOR_PROVINCE_BG_WASTELAND = new Color_GameData(0.7882353F, 0.64705884F, 0.5137255F);
   protected float PROVINCE_ALPHA_WASTELAND = 0.2F;
   protected Color_GameData COLOR_PROVINCE_DISCOVERY = new Color_GameData(0.039215688F, 0.039215688F, 0.11764706F);
   protected float COLOR_PROVINCE_DISCOVERY_ALPHA = 0.11764706F;
   protected String sMoveLine = "default";
   protected String sHighlightLine = "62";
   protected int GRAPH_DATA_LIMIT_PROVINCES = 100;
   protected int GRAPH_DATA_LIMIT_POPULATION = 100;
   protected int GRAPH_DATA_LIMIT_RANK = 75;
   protected int GRAPH_DATA_LIMIT_TECH_LEVEL = 50;
   protected int GRAPH_DATA_LIMIT_PLAYER_DATA = 100;
   protected float STOP_SCALING_ARMY = 2.0F;
   protected boolean showNextPlayerView = false;
   protected boolean showOrderOfMovesView = false;
   protected boolean loadCursor = false;
   protected boolean gameRated = false;
   protected boolean randomLeaders = false;
   //width text setting
   protected int CIV_INFO_MENU_WIDTH = 240;
   //border width setting
   protected float PROVINCE_BORDER_SIZE = 0.17F;
   protected Color_GameData PROVINCE_BORDER_COLOR = new Color_GameData(0.0f, 0.0f, 0.0f);


   protected final void updateCitiesFontScale() {
      this.CITIES_FONT_SCALE = 10.0F / (float)this.FONT_MAIN_SIZE;
   }
}
