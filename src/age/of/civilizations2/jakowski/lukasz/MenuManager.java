// Decompiled with: CFR 0.152
// Class Version: 8
package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.GdxRuntimeException;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class MenuManager {
    private List<List<SliderMenu>> menus = new ArrayList<List<SliderMenu>>();
    private List<List<Integer>> orderOfMenu = new ArrayList<List<Integer>>();
    private SliderMenu dialogMenu;
    private SliderMenu keyboard;
    private ColorPicker_AoC colorPicker;
    private Drag_Civilization dragCivilization;
    private MenuElement_Hover provinceHover_Informations;
    protected static int iHoveredProvinceID = -1;
    private int hoverActiveSliderMenuID = -1;
    private int hoverActiveMenuElementID = -1;
    protected static long hoverMobileTime = 0L;
    protected static int HOVER_MOBILE_TIME_VISIBLE = 2750;
    private boolean BE2_PLUGINS = false;
    private boolean NUCLEAR_MOD = false;
    private int MAINMENU = -1;
    private int INGAME = -1;
    protected int INGAME_MINIMAP_ACTION = -1;
    protected int INGAME_PROVINCE_ACTION = -1;
    protected int INGAME_PROVINCE_ACTION_COLONIZE = -1;
    protected int INGAME_PROVINCE_ACTION_UNCIVILIZE = -1;
    protected int INGAME_PROVINCE_ACTION_COLONIZE_TECH_REQUIRED = -1;
    protected int INGAME_DICES = -1;
    protected int INGAME_PROVINCE_MOVE_UNITS = -1;
    protected int INGAME_PROVINCE_RECRUIT_ARMY = -1;
    protected int INGAME_PROVINCE_RECRUIT_ARMY_INSTANTLY = -1;
    protected int INGAME_PROVINCE_DISBAND_ARMY = -1;
    protected int INGAME_PROVINCE_REGROUP_ARMY = -1;
    protected int INGAME_PROVINCE_BUILD = -1;
    protected int INGAME_PROVINCE_BUILD_CONFIRM = -1;
    protected int INGAME_VIEW_STATS = -1;
    protected int INGAME_MESSAGES = -1;
    protected int INGAME_PROVINCE_INFO = -1;
    protected int INGAME_END_TURN = -1;
    protected int INGAME_HRE = -1;
    protected int INGAME_HRE_VOTE = -1;
    protected int INGAME_MESSAGE = -1;
    protected int INGAME_WAR_PREPARATIONS = -1;
    protected int INGAME_ACTION_INFO_CHOOSE_PROVINCE = -1;
    protected int INGAME_FLAG_ACTION = -1;
    protected int INGAME_FLAG_ACTION_STATS = -1;
    protected int INGAME_FLAG_ACTION_BOT = -1;
    protected int INGAME_FLAG_ACTION_BOT_RIGHT_LEFT = -1;
    protected int INGAME_FLAG_ACTION_BOT_RIGHT_RIGHT = -1;
    protected int INGAME_FLAG_ACTION_BOT_LEFT = -1;
    protected int INGAME_FLAG_ACTION_GRAPH_MODES = -1;
    protected int INGAME_FLAG_ACTION_CONSOLE = -1;
    protected int INGAME_BUDGET = -1;
    //new vassals budget control
    protected int INGAME_BUDGET_VASSALS = -1;
    protected int INGAME_RTO = -1;
    protected int INGAME_RTO_BOT = -1;
    protected int INGAME_MAP_MODES = -1;
    protected int INGAME_MAP_CENSUS_OF_PROVINCE = -1;
    protected int INGAME_OUTLINER = -1;
    protected int INGAME_CURRENT_WARS = -1;
    protected int INGAME_CURRENT_WARS_INFO = -1;
    protected int INGAME_WAR = -1;
    protected int INGAME_SAVED_GAME = -1;
    protected int INGAME_ALLIANCE_INFO = -1;
    protected int INGAME_CITY_HAVE_BEEN_FOUNED = -1;
    protected int INGAME_CAPITAL_MOVED = -1;
    protected int INGAME_VASSAL_RELEASED = -1;
    protected int INGAME_WARS = -1;
    protected int INGAME_WAR_DETAILS = -1;
    protected int INGAME_WORLD_POPULATION = -1;
    protected int INGAME_RANK = -1;
    protected int INGAME_CONQURED_PROVINCES = -1;
    protected int INGAME_VICOTORY_CONDITIONS = -1;
    protected int INGAME_BUILDINGS_CONSTRUCTED = -1;
    protected int INGAME_RECRUITED_ARMY = -1;
    protected int INGAME_ARMY = -1;
    protected int INGAME_PLAYLIST = -1;
    protected int INGAME_TRIBUTE = -1;
    protected int INGAME_TECHNOLOGY = -1;
    protected int INGAME_WONDERS = -1;
    protected int INGAME_VIEW_DETAILS = -1;
    protected int INGAME_VIEW_GRAPH = -1;
    protected int INGAME_VIEW_PROVINCE_POPULATION = -1;
    protected int INGAME_OPTIONS = -1;
    protected int INGAME_ENDOFGAME = -1;
    protected int INGAME_CIV_INFO = -1;
    protected int INGAME_CIV_INFO2 = -1;
    protected int INGAME_CIV_INFO_DIPLOMACY = -1;
    protected int INGAME_CIV_INFO_DIPLO_ACTIONS = -1;
    protected int INGAME_CIV_MANAGE = -1;
    protected int INGAME_CIV_MANAGE2 = -1;
    protected int INGAME_CIV_MANAGE_ACTIONS = -1;
    protected int INGAME_CIV_INFO_ACTIONS = -1;
    protected int INGAME_CIV_INFO_OPINIONS = -1;
    protected int INGAME_CIV_INFO_DECISIONS = -1;
    protected int INGAME_HISTORY = -1;
    protected int INGAME_TIMELINE = -1;
    protected int INGAME_FORMABLE_CIV_PROVINCES = -1;
    protected int INGAME_PEACE_TREATY = -1;
    protected int INGAME_PEACE_TREATY_PROVINCES = -1;
    protected int INGAME_PEACE_TREATY_PROVINCES_SHOW = -1;
    protected int INGAME_PEACE_TREATY_SCORES = -1;
    protected int INGAME_PEACE_TREATY_RESPONSE = -1;
    protected int INGAME_PEACE_TREATY_RESPONSE_PROVINCES = -1;
    protected int INGAME_PEACE_TREATY_RESPONSE_PROVINCES_SHOW = -1;
    protected int INGAME_PEACE_TREATY_RESPONSE_CIVS = -1;
    protected int INGAME_CREATE_VASSAL = -1;
    protected int INGAME_CREATE_VASSAL_INFO = -1;
    protected int INGAME_CREATE_VASSAL_INFO_STATS = -1;
    protected int INGAME_CREATE_VASSAL_MAPMODES = -1;
    protected int INGAME_CREATE_VASSAL_CIVS = -1;
    protected int INGAME_CREATE_VASSAL_CHANGEAUTONOMY = -1;
    protected int INGAME_CREATE_VASSAL_CHANGEGOVERNMENT = -1;
    protected int INGAME_CREATE_VASSAL_SELECT_CIV = -1;
    protected int INGAME_CREATE_VASSAL_SELECT_CIV_ALPHABET = -1;
    protected int INGAME_CREATE_VASSAL_SELECT_CIV_LIST = -1;
    protected int INGAME_FORM_ANIMATION = -1;
    protected int INGAME_EVENT = -1;
    protected int INGAME_CIV_VIEW = -1;
    protected int INGAME_SELECT_PROVINCES = -1;
    protected int INGAME_TRADE_SELECT_CIV = -1;
    protected int INGAME_SHOW_PROVINCES = -1;
    protected int INGAME_SEND_MESSAGE = -1;
    protected int INGAME_SEND_MESSAGE_TRADE_LEFT = -1;
    protected int INGAME_SEND_MESSAGE_TRADE_RIGHT = -1;
    protected int INGAME_PLUNDER = -1;
    protected boolean INGAME_CIV_INFO_ACTIONS_VISIBLE = true;
    protected int INGAME_GRAPH_MOVEMENTS = -1;
    protected int INGAME_ALLIANCE = -1;
    protected int INGAME_MILITARY_ALLIANCES = -1;
    protected int INGAME_TURN_SUMMARY = -1;
    protected int INGAME_REPORT = -1;
    protected int START_THE_GAME = -1;
    protected int END_THE_GAME = -1;
    private int NEXT_PLAYER_TURN = -1;
    private int VICTORY = -1;
    private int GAMES = -1;
    private int SELECT_UI_SCALE = -1;
    private int SETTINGS_GRAPHICS = -1;
    private int SETTINGS_RESOLUTION = -1;
    private int EDITOR = -1;
    private int EDITOR_SCENARIOS = -1;
    private int EDITOR_GAME_CIVS = -1;
    private int EDITOR_GAME_CIVS_EDIT = -1;
    private int LOADGAME = -1;
    private int EDITOR_UNIONS = -1;
    private int EDITOR_UNIONS_EDIT = -1;
    private int EDITOR_UNIONS_ADDCIV = -1;
    private int EDITOR_UNIONS_ADDCIV_LIST = -1;
    private int EDITOR_UNIONS_ADDCIV_ALPHABET = -1;
    private int CREATE_SCENARIO_CIVILIZATIONS = -1;
    private int CREATE_SCENARIO_CIVILIZATIONS_SUGGEST = -1;
    private int CREATE_SCENARIO_CIVILIZATIONS_IDEOLOGIES = -1;
    private int CREATE_SCENARIO_CIVILIZATIONS_SELECT = -1;
    private int CREATE_SCENARIO_CIVILIZATIONS_SELECT_ALPHABET = -1;
    private int CREATE_SCENARIO_CIVILIZATIONS_SELECT_LIST = -1;
    private int CREATE_SCENARIO_ASSIGN = -1;
    private int CREATE_SCENARIO_ASSIGN_LIST = -1;
    private int CREATE_SCENARIO_ASSIGN_SELECT = -1;
    private int CREATE_SCENARIO_WASTELAND = -1;
    private int CREATE_SCENARIO_SETTINGS = -1;
    private int CREATE_SCENARIO_AVAILABLE_PROVINCES = -1;
    private int CREATE_SCENARIO_SET_UP_ARMY = -1;
    private int CREATE_SCENARIO_SET_UP_ARMY_SLIDERS = -1;
    private int CREATE_SCENARIO_SET_UP_ARMY_CIVS = -1;
    private int CREATE_SCENARIO_SET_UP_ARMY_NEUTRAL = -1;
    private int CREATE_SCENARIO_TECHNOLOGY_LEVELS = -1;
    private int CREATE_SCENARIO_HAPPINESS = -1;
    private int CREATE_SCENARIO_TECHNOLOGY_LEVELS_CONTINENTS = -1;
    private int CREATE_SCENARIO_STARTING_MONEY = -1;
    private int CREATE_SCENARIO_PREVIEW = -1;
    private int CREATE_SCENARIO_PALLET_OF_COLORS = -1;
    private int CREATE_SCENARIO_EVENTS = -1;
    private int CREATE_SCENARIO_EVENTS_LIST = -1;
    private int CREATE_SCENARIO_EVENTS_EDIT = -1;
    private int CREATE_SCENARIO_CORES = -1;
    private int CREATE_SCENARIO_CORES_SETUP = -1;
    private int CREATE_SCENARIO_COLONIZATION = -1;
    private int CREATE_SCENARIO_CORES_ADD_CORE = -1;
    private int CREATE_SCENARIO_CORES_ADD_CORE_LIST = -1;
    private int CREATE_SCENARIO_CORES_ADD_CORE_ALPHABET = -1;
    private int CREATE_SCENARIO_CORES_ADD_CIV = -1;
    private int CREATE_SCENARIO_CORES_ADD_CIV_LIST = -1;
    private int CREATE_SCENARIO_CORES_ADD_CIV_ALPHABET = -1;
    private int CREATE_SCENARIO_EVENTS_SELECT_CIV = -1;
    private int CREATE_SCENARIO_EVENTS_SELECT_CIV_LIST = -1;
    private int CREATE_SCENARIO_EVENTS_SELECT_CIV_ALPHABET = -1;
    private int CREATE_SCENARIO_EVENTS_TRIGGER = -1;
    private int CREATE_SCENARIO_EVENTS_ADD_NEW_CONDITION = -1;
    private int CREATE_SCENARIO_EVENTS_ADD_NEW_OUTCOME = -1;
    private int CREATE_SCENARIO_EVENTS_COND_CIVEXIST = -1;
    private int CREATE_SCENARIO_EVENTS_COND_CONTROLSPROVINCES = -1;
    private int CREATE_SCENARIO_EVENTS_COND_OCCUPIED_PROVINCES = -1;
    private int CREATE_SCENARIO_EVENTS_COND_HAVEARMY = -1;
    private int CREATE_SCENARIO_EVENTS_COND_HAVECORE = -1;
    private int CREATE_SCENARIO_EVENTS_COND_EVENTCHANCE = -1;
    private int CREATE_SCENARIO_EVENTS_COND_DECISIONTAKEN = -1;
    private int CREATE_SCENARIO_EVENTS_COND_ISCAPITAL = -1;
    private int CREATE_SCENARIO_EVENTS_COND_NUMOFPROVINCES = -1;
    private int CREATE_SCENARIO_EVENTS_COND_NUMOFPROVINCES_LOW = -1;
    private int CREATE_SCENARIO_EVENTS_COND_NUMOFUNITS = -1;
    private int CREATE_SCENARIO_EVENTS_COND_NUMOFUNITS_LOW = -1;
    private int CREATE_SCENARIO_EVENTS_COND_NUMOFVASSALS = -1;
    private int CREATE_SCENARIO_EVENTS_COND_NUMOFVASSALS_LOW = -1;
    private int CREATE_SCENARIO_EVENTS_COND_NUMOFWARS = -1;
    private int CREATE_SCENARIO_EVENTS_COND_NUMOFWARS_LOW = -1;
    private int CREATE_SCENARIO_EVENTS_COND_NUMOFALLIES = -1;
    private int CREATE_SCENARIO_EVENTS_COND_NUMOFALLIES_LOW = -1;
    private int CREATE_SCENARIO_EVENTS_COND_NUMOFNEIGHBORS = -1;
    private int CREATE_SCENARIO_EVENTS_COND_NUMOFNEIGHBORS_LOW = -1;
    private int CREATE_SCENARIO_EVENTS_COND_ECONOMY = -1;
    private int CREATE_SCENARIO_EVENTS_COND_ECONOMY_LOW = -1;
    private int CREATE_SCENARIO_EVENTS_COND_POPULATION = -1;
    private int CREATE_SCENARIO_EVENTS_COND_POPULATION_LOW = -1;
    private int CREATE_SCENARIO_EVENTS_COND_RELATION = -1;
    private int CREATE_SCENARIO_EVENTS_COND_RELATION_LOW = -1;
    private int CREATE_SCENARIO_EVENTS_COND_ISATWAR = -1;
    private int CREATE_SCENARIO_EVENTS_COND_ALLIES = -1;
    private int CREATE_SCENARIO_EVENTS_COND_ATWAR = -1;
    private int CREATE_SCENARIO_EVENTS_COND_NONAGGRESSION = -1;
    private int CREATE_SCENARIO_EVENTS_COND_DEFENSIVE = -1;
    private int CREATE_SCENARIO_EVENTS_COND_INDEPENDENCE = -1;
    private int CREATE_SCENARIO_EVENTS_COND_MILITARYACCESS = -1;
    private int CREATE_SCENARIO_EVENTS_COND_ISVASSAL = -1;
    private int CREATE_SCENARIO_EVENTS_COND_ISVASSAL_OFCIV = -1;
    private int CREATE_SCENARIO_EVENTS_COND_ISPARTOFHRE = -1;
    private int CREATE_SCENARIO_EVENTS_COND_IDEOLOGY = -1;
    private int CREATE_SCENARIO_EVENTS_COND_TECHNOLOGY = -1;
    private int CREATE_SCENARIO_EVENTS_COND_TECHNOLOGY_LOW = -1;
    private int CREATE_SCENARIO_EVENTS_COND_DEVELOPMENT = -1;
    private int CREATE_SCENARIO_EVENTS_COND_DEVELOPMENT_LOW = -1;
    private int CREATE_SCENARIO_EVENTS_COND_HAPPINESS = -1;
    private int CREATE_SCENARIO_EVENTS_COND_HAPPINESS_LOW = -1;
    private int CREATE_SCENARIO_EVENTS_COND_TREASURY = -1;
    private int CREATE_SCENARIO_EVENTS_COND_TREASURY_LOW = -1;
    private int CREATE_SCENARIO_EVENTS_COND_CONTROLLEDBYPLAYER = -1;
    private int CREATE_SCENARIO_EVENTS_COND_WASTELAND = -1;
    private int CREATE_SCENARIO_EVENTS_COND_NEUTRAL = -1;
    private int CREATE_SCENARIO_EVENTS_COND_WATCHTOWER = -1;
    private int CREATE_SCENARIO_EVENTS_COND_FORT = -1;
    private int CREATE_SCENARIO_EVENTS_COND_PORT = -1;
    private int CREATE_SCENARIO_EVENTS_COND_FARM = -1;
    private int CREATE_SCENARIO_EVENTS_SELECT_PROVINCES = -1;
    private int CREATE_SCENARIO_EVENTS_SELECT_AUTONOMY = -1;
    private int CREATE_SCENARIO_EVENTS_SELECTDECISION = -1;
    private int CREATE_SCENARIO_EVENTS_OUT_CREATEVASSAL = -1;
    private int CREATE_SCENARIO_EVENTS_OUT_JOINALLIANCE = -1;
    private int CREATE_SCENARIO_EVENTS_OUT_LEAVEALLIANCE = -1;
    private int CREATE_SCENARIO_EVENTS_OUT_JOINUNION = -1;
    private int CREATE_SCENARIO_EVENTS_OUT_NONAGGRESSION = -1;
    private int CREATE_SCENARIO_EVENTS_OUT_DEFENSIVE = -1;
    private int CREATE_SCENARIO_EVENTS_OUT_MILITARY = -1;
    private int CREATE_SCENARIO_EVENTS_OUT_INDEPENENCE = -1;
    private int CREATE_SCENARIO_EVENTS_OUT_MOVECAPITAL = -1;
    private int CREATE_SCENARIO_EVENTS_OUT_LIBERATEVASSAL = -1;
    private int CREATE_SCENARIO_EVENTS_OUT_CHANGE_OWNER = -1;
    private int CREATE_SCENARIO_EVENTS_OUT_OCCUPY = -1;
    private int CREATE_SCENARIO_EVENTS_OUT_ADDCORE = -1;
    private int CREATE_SCENARIO_EVENTS_OUT_REMOVECORE = -1;
    private int CREATE_SCENARIO_EVENTS_OUT_DECLAREWAR = -1;
    private int CREATE_SCENARIO_EVENTS_OUT_WHITEPEACE = -1;
    private int CREATE_SCENARIO_EVENTS_OUT_CHANGEIDEOLOGY = -1;
    private int CREATE_SCENARIO_EVENTS_OUT_CHANGELEADER = -1;
    private int CREATE_SCENARIO_EVENTS_OUT_INCRELATION = -1;
    private int CREATE_SCENARIO_EVENTS_OUT_DECRELATION = -1;
    private int CREATE_SCENARIO_EVENTS_OUT_ADDARMY = -1;
    private int CREATE_SCENARIO_EVENTS_OUT_UPDATEPOPULATION = -1;
    private int CREATE_SCENARIO_EVENTS_OUT_UPDATEPOPULATION_PERC = -1;
    private int CREATE_SCENARIO_EVENTS_OUT_UPDATEPOPULATION_OFCIV = -1;
    private int CREATE_SCENARIO_EVENTS_OUT_UPDATEECONOMY = -1;
    private int CREATE_SCENARIO_EVENTS_OUT_UPDATEECONOMY_PERC = -1;
    private int CREATE_SCENARIO_EVENTS_OUT_UPDATEECONOMY_OFCIV = -1;
    private int CREATE_SCENARIO_EVENTS_OUT_TECHLEVEL = -1;
    private int CREATE_SCENARIO_EVENTS_OUT_DEVELOPMENT = -1;
    private int CREATE_SCENARIO_EVENTS_OUT_HAPPINESS = -1;
    private int CREATE_SCENARIO_EVENTS_OUT_HAPPINESS_OF_CIV = -1;
    private int CREATE_SCENARIO_EVENTS_OUT_MONEY = -1;
    private int CREATE_SCENARIO_EVENTS_OUT_MOVEMENTPOINTS = -1;
    private int CREATE_SCENARIO_EVENTS_OUT_DIPLOMACYPOINTS = -1;
    private int CREATE_SCENARIO_EVENTS_OUT_WASTELAND = -1;
    private int CREATE_SCENARIO_EVENTS_OUT_TRIGGERANOTHEREVENT = -1;
    private int CREATE_SCENARIO_EVENTS_OUT_FORM_CIV = -1;
    private int CREATE_SCENARIO_EVENTS_ADD_CIV = -1;
    private int CREATE_SCENARIO_EVENTS_ADD_CIV_LIST = -1;
    private int CREATE_SCENARIO_EVENTS_ADD_CIV_ALPHABET = -1;
    private int CREATE_SCENARIO_EVENTS_SELECTEVENT = -1;
    private int CREATE_SCENARIO_EVENTS_OUT_SELECT_LEADER = -1;
    private int CREATE_SCENARIO_EVENTS_SELECTIDEOLOGY = -1;
    private int CREATE_SCENARIO_EVENTS_DECISION = -1;
    private int CREATE_SCENARIO_EVENTS_DATE = -1;
    private int CREATE_SCENARIO_EVENTS_DATE_CALENDAR = -1;
    private int CREATE_SCENARIO_HOLY_ROMAN_EMPIRE = -1;
    private int CREATE_SCENARIO_HOLY_ROMAN_EMPIRE_PRINCES = -1;
    private int EDITOR_CIVILIZATIONS = -1;
    private int CREATE_CIVILIZATION = -1;
    private int CREATE_CIVILIZATION_TOP = -1;
    private int CREATE_CIVILIZATION_DATA = -1;
    private int CREATE_CIVILIZATION_FLAG = -1;
    private int CREATE_CIVILIZATION_OVERLAY = -1;
    private int MAP_EDITOR = -1;
    private int MAP_EDITOR_PACKAGES_CONTINENTS = -1;
    private int MAP_EDITOR_CREATE_CONTINENTS_PACKAGE = -1;
    private int MAP_EDITOR_CREATE_NEW_CONTINENT = -1;
    private int MAP_EDITOR_CONTINENTS_ADDNEWCONTINENT_TOPACKAGE = -1;
    private int MAP_EDITOR_PACKAGES_REGIONS = -1;
    private int MAP_EDITOR_CREATE_REGIONS_PACKAGE = -1;
    private int MAP_EDITOR_CREATE_NEW_REGION = -1;
    private int MAP_EDITOR_REGIONS_ADDNEWREGION_TOPACKAGE = -1;
    private int MAP_EDITOR_EDIT = -1;
    private int MAP_EDITOR_CONNECTIONS = -1;
    private int MAP_EDITOR_UPDATE_PROVINCE_DATA = -1;
    private int MAP_EDITOR_PROVINCE_BACKGROUND = -1;
    private int MAP_EDITOR_SEA_PROVINCES = -1;
    private int MAP_EDITOR_TERRAIN = -1;
    private int MAP_EDITOR_CONTINENTS = -1;
    private int MAP_EDITOR_REGIONS = -1;
    private int MAP_EDITOR_GROWTH_RATE = -1;
    private int MAP_EDITOR_TRADE_ZONES = -1;
    private int MAP_EDITOR_TRADE_ZONES_EDIT = -1;
    private int MAP_EDITOR_ARMY_POSITION = -1;
    private int MAP_EDITOR_PORT_POSITION = -1;
    private int MAP_EDITOR_ARMY_POSITION_CONVERT = -1;
    private int MAP_EDITOR_PORT_POSITION_CONVERT = -1;
    private int MAP_EDITOR_ARMY_SEA_BOXES = -1;
    private int MAP_EDITOR_ARMY_SEA_BOXES_EDIT = -1;
    private int MAP_EDITOR_ARMY_SEA_BOXES_ADD = -1;
    private int MAP_EDITOR_WASTELAND_MAPS = -1;
    private int MAP_EDITOR_WASTELAND_MAPS_EDIT = -1;
    private int GAME_LEADERS = -1;
    private int GAME_RANDOM_LEADERS = -1;
    private int GAME_LEADERS_EDIT = -1;
    private int GAME_RANDOM_LEADERS_EDIT = -1;
    private int GAME_LEADERS_EDIT_DATA = -1;
    private int GAME_LEADERS_EDIT_CIVS = -1;
    private int GAME_RANDOM_LEADERS_EDIT_DATA = -1;
    private int GAME_RANDOM_LEADERS_EDIT_CIVS = -1;
    private int GAME_RANDOM_LEADERS_EDIT_AITYPE = -1;
    private int GAME_LEADERS_EDIT_SELECT_CIVS = -1;
    private int GAME_LEADERS_EDIT_SELECT_CIVS_ALPHABET = -1;
    private int GAME_LEADERS_EDIT_SELECT_CIVS_LIST = -1;
    private int GAME_RANDOM_LEADERS_EDIT_SELECT_CIVS = -1;
    private int GAME_RANDOM_LEADERS_EDIT_SELECT_CIVS_LIST = -1;
    private int GAME_RANDOM_LEADERS_EDIT_SELECT_AITYPE = -1;
    private int GAME_RANDOM_LEADERS_EDIT_SELECT_AITYPE_LIST = -1;
    private int MAP_EDITOR_FORMABLE_CIVS = -1;
    private int MAP_EDITOR_FORMABLE_CIVS_EDIT = -1;
    private int MAP_EDITOR_FORMABLE_CIVS_EDIT_CLAIMANTS = -1;
    private int MAP_EDITOR_FORMABLE_CIVS_SELECT_FORMABLE = -1;
    private int MAP_EDITOR_FORMABLE_CIVS_SELECT_FORMABLE_ALPHABET = -1;
    private int MAP_EDITOR_FORMABLE_CIVS_SELECT_FORMABLE_LIST = -1;
    private int MAP_EDITOR_FORMABLE_CIVS_SELECT_CLAIMANT = -1;
    private int MAP_EDITOR_FORMABLE_CIVS_SELECT_CLAIMANT_ALPHABET = -1;
    private int MAP_EDITOR_FORMABLE_CIVS_SELECT_CLAIMANT_LIST = -1;
    private int EDITOR_CITIES = -1;
    private int CREATE_CITY = -1;
    private int EDITOR_TRANSLATIONS = -1;
    private int CREATE_TRANSLATION = -1;
    private int CREATE_NEW_GAME = -1;
    private int CREATE_NEW_GAME_OPTIONS = -1;
    private int CREATE_NEW_GAME_SCENARIOS = -1;
    private int CREATE_NEW_GAME_PALLETS = -1;
    private int CREATE_NEW_GAME_TOP = -1;
    private int CREATE_NEW_GAME_TOP_VIEWS = -1;
    private int CREATE_NEW_GAME_CIV_INFO = -1;
    private int CREATE_NEW_GAME_CIV_INFO_STATS = -1;
    private int CREATE_NEW_GAME_CIV_INFO_DIPLOMACY = -1;
    private int CREATE_NEW_GAME_CIV_INFO_PLAYERS = -1;
    private int CREATE_NEW_GAME_CIV_INFO_SHOW_MENU = -1;
    protected int CREATE_NEW_GAME_MAPMODES = -1;
    private int NEWGAME_PLAYERS = -1;
    private int CREATE_RANDOM_GAME = -1;
    private int CREATE_RANDOM_GAME_OPTIONS = -1;
    private int CREATE_RANDOM_GAME_WASTELANDMAPS = -1;
    private int CREATE_RANDOM_GAME_SETTINGS = -1;
    private int CREATE_RANDOM_GAME_PLAYERS = -1;
    private int CREATE_RANDOM_GAME_CIVILIZATIONS_SELECT = -1;
    private int CREATE_RANDOM_GAME_CIVILIZATIONS_SELECT_ALPHABET = -1;
    private int CREATE_RANDOM_GAME_CIVILIZATIONS_SELECT_LIST = -1;
    private int HELP = -1;
    private int SETTINGS = -1;
    private int SETTINGS_AUDIO = -1;
    private int SELECT_LANGUAGE = -1;
    private int SETTINGS_PROVINCE = -1;
    private int SELECT_CIVILIZATION = -1;
    private int SELECT_CIVILIZATION_CLASSIC = -1;
    private int CHOOSE_SCENARIO = -1;
    private int SELECT_AVAILABLE_CIVILIZATIONS = -1;
    protected int ABOUT = -1;
    private int FLAG_EDITOR = -1;
    private int INIT_GAME = this.addMenu(new Menu_InitGame());
    private int LOAD_MAP = -1;
    private int LOAD_SAVE = -1;
    private int MANAGE_DIPLOMACY = -1;
    private int MANAGE_DIPLOMACY_ALLIANCES = -1;
    private int MANAGE_DIPLOMACY_RELATIONS_INTERACTIVE = -1;
    private int MANAGE_DIPLOMACY_PACTS2 = -1;
    private int MANAGE_DIPLOMACY_TRUCES = -1;
    private int MANAGE_DIPLOMACY_TRUCES_LIST = -1;
    private int MANAGE_DIPLOMACY_PACTS_LIST = -1;
    private int MANAGE_DIPLOMACY_DEFENSIVE = -1;
    private int MANAGE_DIPLOMACY_DEFENSIVE_LIST = -1;
    private int MANAGE_DIPLOMACY_GUARANTEE = -1;
    private int MANAGE_DIPLOMACY_GUARANTEE_LIST = -1;
    private int MANAGE_DIPLOMACY_MILITARY_ACCESS = -1;
    private int MANAGE_DIPLOMACY_MILITARY_ACCESS_LIST = -1;
    private int MANAGE_DIPLOMACY_VASSALS = -1;
    private int MANAGE_DIPLOMACY_VASSALS_LIST = -1;
    //new vassals autonomy menu
    private int MANAGE_DIPLOMACY_VASSALS_AUTONOMY = -1;
    private int CUSTOMIZE_ALLIANCE = -1;
    private int CUSTOMIZE_ALLIANCE_ADD_CIVLIZATION = -1;
    private int SCENARIO_AGE = -1;
    private int SCENARIO_AGE_CALENDAR = -1;
    private int SELECT_MAP_TYPE = -1;
    private int SELECT_MAP_TYPE_SCALE = -1;
    private int VICTORY_CONDITIONS = -1;
    private int GAME_EDITOR = -1;
    private int GAME_EDITOR_ALLIANCE_NAMES_PACKAGE = -1;
    private int GAME_EDITOR_ALLIANCE_NAMES_PACKAGE_CREATE = -1;
    private int GAME_EDITOR_ALLIANCE_NAMES_PACKAGE_CREATE_BUNDLE = -1;
    private int GAME_EDITOR_DIPLOMACY_COLORS_PACKAGES = -1;
    private int GAME_EDITOR_DIPLOMACY_COLORS_PACKAGES_CREATE = -1;
    private int GAME_EDITOR_PALLETS_OF_CIVS_COLORS_PACKAGES = -1;
    private int GAME_EDITOR_PALLETS_OF_CIVS_COLORS_PACKAGES_EDIT = -1;
    private int LOAD_GENERATE_SUGGESTED_OWNERS = -1;
    private int LOAD_GENERATE_PRE_DEFINED_BORDERS = -1;
    private int EDITOR_RELIGION = -1;
    private int EDITOR_RELIGION_ADD = -1;
    private int GAME_EDITOR_SERVICE_RIBBON = -1;
    private int GAME_EDITOR_SERVICE_RIBBON_EDIT = -1;
    private int GAME_EDITOR_SERVICE_RIBBON_EDIT_OVERLAY = -1;
    private int GAME_EDITOR_LINES = -1;
    private int GAME_EDITOR_LINES_EDIT = -1;
    private int TERRAIN_TYPES_EDITOR = -1;
    private int TERRAIN_TYPE_ADD = -1;
    private int GAME_EDITOR_REGIONS = -1;
    private int GENERATE_PREVIEW = -1;
    private int GENERATE_FLAG = -1;
    private int PRINT_A_MAP = -1;
    protected int ACHIEVEMENTS = -1;
    protected int DOWNLOAD_PALLETS = -1;
    private int viewID = 0;
    private int activeSliderMenuID = -1;
    private int activeMenuElementID = -1;
    private boolean sliderMenuMode = false;
    private int iSliderMenuStartPosY = 0;
    private int iSliderMenuActionDownPosY = 0;
    private boolean updateSliderMenuPosY = false;
    private int iSliderMenuStartPosX = 0;
    private int iSliderMenuActionDownPosX = 0;
    private boolean updateSliderMenuPosX = false;
    private boolean sliderMenuTitleMode = false;
    private boolean sliderMenuResizeMode = false;
    private boolean sliderMenuResizeLEFT = false;
    private boolean sliderMenuCloseMode = false;
    private boolean sliderMode = false;
    private boolean slideMapMode = false;
    private boolean graphMode = false;
    private boolean graphButtonMode = false;
    private boolean graphButtonModeX = false;
    private boolean graphButtonMode2 = false;
    private boolean keyboardMode = false;
    private int keyboardActiveSliderMenuID = 0;
    private int keyboardActiveMenuElementID = 0;
    private boolean colorPickerMode = false;
    private int fromViewID = -1;
    private int toViewID = -1;
    private boolean backAnimation = false;
    private float animationChangeViewPosX = 0.0f;
    private int animationStepID = 0;
    private boolean flagEditorMode = false;
    private boolean textSliderMode = false;
    private float slidePercent;
    private SlideMap_ActionMove slideMap_ActionMove;
    protected static BuildProvinceHover_Informations buildProvinceHover_Informations;

    protected final int getHover_ExtraPosX() {
        return 25;
    }

    protected final int getHover_ExtraPosY() {
        return 30;
    }

    protected final int getViewID(Menu eMenu) {
        try {
            switch (eMenu) {
                case eMAINMENU: {
                    if (this.MAINMENU == -1) {
                        this.MAINMENU = this.addMenu(new Menu_Main());
                        this.menus.get(this.MAINMENU).get(0).setVisible(true);
                    } else {
                        this.menus.get(this.MAINMENU).set(0, new Menu_Main());
                        this.menus.get(this.MAINMENU).get(0).setVisible(true);
                    }
                    CFG.map.getMapScroll().stopScrollingTheMap();
                    CFG.map.getMapScale().setCurrentScale(Map_Scale.MINSCALE);
                    CFG.map.getMapCoordinates().setNewPosX(-((int)((float)(CFG.map.getMapBG().getWidth() / 2) - (float)CFG.GAME_WIDTH / Map_Scale.MINSCALE / 2.0f)));
                    CFG.map.getMapCoordinates().setNewPosY(-((int)((float)(CFG.map.getMapBG().getHeight() / 2) - (float)CFG.GAME_HEIGHT / Map_Scale.MINSCALE / 2.0f)));
                    if ((Map.GAME_CRASHED_LOADED_MIN_SCALE || CFG.map.getMapBG().getMapScale() <= 1) && CFG.map.getMapBG().getMapScale() == 1) {
                        ArrayList<String> nMess = new ArrayList<String>();
                        ArrayList<Color> nCol = new ArrayList<Color>();
                        nMess.add("Game crashed while loading");
                        nCol.add(CFG.COLOR_TEXT_MODIFIER_NEGATIVE2);
                        nMess.add("-- Loaded minimum scale of map --");
                        nCol.add(CFG.COLOR_TEXT_MODIFIER_NEGATIVE2);
                        nMess.add("Go to: Games -> Map: XX -> Earth: -> Scale X5");
                        nCol.add(Color.WHITE);
                        CFG.toast.setInView(nMess, nCol);
                        CFG.toast.setTimeInView(6000);
                    }
                    return this.MAINMENU;
                }
                case eGAMES: {
                    if (this.GAMES == -1) {
                        this.GAMES = this.addMenu(new Menu_Games_Title());
                        this.addNextMenuToView(this.GAMES, new Menu_Games());
                        this.setOrderOfMenu(1);
                    } else {
                        this.menus.get(this.GAMES).set(1, new Menu_Games());
                    }
                    if ((Map.GAME_CRASHED_LOADED_MIN_SCALE || CFG.map.getMapBG().getMapScale() <= 1) && CFG.map.getMapBG().getMapScale() == 1) {
                        ArrayList<String> nMess = new ArrayList<String>();
                        ArrayList<Color> nCol = new ArrayList<Color>();
                        nMess.add("Game crashed while loading");
                        nCol.add(CFG.COLOR_TEXT_MODIFIER_NEGATIVE2);
                        nMess.add("-- Loaded minimum scale of map --");
                        nCol.add(CFG.COLOR_TEXT_MODIFIER_NEGATIVE2);
                        nMess.add("Go to: Map: XX -> Earth: -> Scale X5");
                        nCol.add(Color.WHITE);
                        CFG.toast.setInView(nMess, nCol);
                        CFG.toast.setTimeInView(6000);
                    }
                    return this.GAMES;
                }
                case eEDITOR: {
                    if (this.EDITOR == -1) {
                        this.EDITOR = this.addMenu(new Menu_Editor());
                    }
                    return this.EDITOR;
                }
                case eCREATE_NEW_GAME: {
                    if (this.CREATE_NEW_GAME == -1) {
                        this.CREATE_NEW_GAME = this.addMenu(new Menu_CreateNewGame());
                        this.CREATE_NEW_GAME_OPTIONS = this.addNextMenuToView(this.CREATE_NEW_GAME, new Menu_CreateNewGame_Options_v2());
                        this.CREATE_NEW_GAME_SCENARIOS = this.addNextMenuToView(this.CREATE_NEW_GAME, new Menu_CreateNewGame_Scenarios());
                        this.CREATE_NEW_GAME_PALLETS = this.addNextMenuToView(this.CREATE_NEW_GAME, new Menu_CreateNewGame_Options_Pallets());
                        this.CREATE_NEW_GAME_TOP = this.addNextMenuToView(this.CREATE_NEW_GAME, new Menu_CreateNewGame_Top());
                        this.CREATE_NEW_GAME_CIV_INFO = this.addNextMenuToView(this.CREATE_NEW_GAME, new Menu_Civilization_Info());
                        this.CREATE_NEW_GAME_CIV_INFO_STATS = this.addNextMenuToView(this.CREATE_NEW_GAME, new Menu_Civilization_Info_Stats());
                        this.CREATE_NEW_GAME_CIV_INFO_DIPLOMACY = this.addNextMenuToView(this.CREATE_NEW_GAME, new Menu_Civilization_Info_Diplomacy());
                        this.CREATE_NEW_GAME_CIV_INFO_PLAYERS = this.addNextMenuToView(this.CREATE_NEW_GAME, new Menu_Civilizations_Info_Players());
                        this.CREATE_NEW_GAME_MAPMODES = this.addNextMenuToView(this.CREATE_NEW_GAME, new Menu_CreateNewGame_MapModes());
                        this.CREATE_NEW_GAME_CIV_INFO_SHOW_MENU = this.addNextMenuToView(this.CREATE_NEW_GAME, new Menu_CreateNewGame_ShowCivInfo());
                        this.CREATE_NEW_GAME_TOP_VIEWS = this.addNextMenuToView(this.CREATE_NEW_GAME, new Menu_CreateNewGame_Top_Views());
                        this.menus.get(this.CREATE_NEW_GAME).get(this.CREATE_NEW_GAME_CIV_INFO_DIPLOMACY).setPosY(2 + this.menus.get(this.CREATE_NEW_GAME).get(this.CREATE_NEW_GAME_CIV_INFO_STATS).getPosY() + this.menus.get(this.CREATE_NEW_GAME).get(this.CREATE_NEW_GAME_CIV_INFO_STATS).getHeight() + this.menus.get(this.CREATE_NEW_GAME).get(this.CREATE_NEW_GAME_CIV_INFO_DIPLOMACY).getTitle().getHeight());
                        this.menus.get(this.CREATE_NEW_GAME).get(this.CREATE_NEW_GAME_CIV_INFO_PLAYERS).setPosY(2 + this.menus.get(this.CREATE_NEW_GAME).get(this.CREATE_NEW_GAME_CIV_INFO_DIPLOMACY).getPosY() + this.menus.get(this.CREATE_NEW_GAME).get(this.CREATE_NEW_GAME_CIV_INFO_DIPLOMACY).getHeight() + this.menus.get(this.CREATE_NEW_GAME).get(this.CREATE_NEW_GAME_CIV_INFO_PLAYERS).getTitle().getHeight());
                        if ((float)(this.menus.get(this.CREATE_NEW_GAME).get(this.CREATE_NEW_GAME_CIV_INFO_DIPLOMACY).getPosY() + this.menus.get(this.CREATE_NEW_GAME).get(this.CREATE_NEW_GAME_CIV_INFO_PLAYERS).getHeight()) > (float)CFG.GAME_HEIGHT - (float)CFG.BUTTON_HEIGHT * 0.6f * 4.0f + (float)(CFG.PADDING * 5)) {
                            this.menus.get(this.CREATE_NEW_GAME).get(this.CREATE_NEW_GAME_CIV_INFO_STATS).setHeight(this.menus.get(this.CREATE_NEW_GAME).get(this.CREATE_NEW_GAME_CIV_INFO_STATS).getHeight() / 2);
                            this.menus.get(this.CREATE_NEW_GAME).get(this.CREATE_NEW_GAME_CIV_INFO_DIPLOMACY).setPosY(2 + this.menus.get(this.CREATE_NEW_GAME).get(this.CREATE_NEW_GAME_CIV_INFO_STATS).getPosY() + this.menus.get(this.CREATE_NEW_GAME).get(this.CREATE_NEW_GAME_CIV_INFO_STATS).getHeight() + this.menus.get(this.CREATE_NEW_GAME).get(this.CREATE_NEW_GAME_CIV_INFO_DIPLOMACY).getTitle().getHeight());
                            this.menus.get(this.CREATE_NEW_GAME).get(this.CREATE_NEW_GAME_CIV_INFO_PLAYERS).setPosY(2 + this.menus.get(this.CREATE_NEW_GAME).get(this.CREATE_NEW_GAME_CIV_INFO_DIPLOMACY).getPosY() + this.menus.get(this.CREATE_NEW_GAME).get(this.CREATE_NEW_GAME_CIV_INFO_DIPLOMACY).getHeight() + this.menus.get(this.CREATE_NEW_GAME).get(this.CREATE_NEW_GAME_CIV_INFO_PLAYERS).getTitle().getHeight());
                            if ((float)(this.menus.get(this.CREATE_NEW_GAME).get(this.CREATE_NEW_GAME_CIV_INFO_DIPLOMACY).getPosY() + this.menus.get(this.CREATE_NEW_GAME).get(this.CREATE_NEW_GAME_CIV_INFO_PLAYERS).getHeight()) > (float)CFG.GAME_HEIGHT - (float)CFG.BUTTON_HEIGHT * 0.6f * 4.0f + (float)(CFG.PADDING * 5)) {
                                this.menus.get(this.CREATE_NEW_GAME).get(this.CREATE_NEW_GAME_CIV_INFO_DIPLOMACY).setHeight(this.menus.get(this.CREATE_NEW_GAME).get(this.CREATE_NEW_GAME_CIV_INFO_DIPLOMACY).getHeight() * 2 / 5);
                                this.menus.get(this.CREATE_NEW_GAME).get(this.CREATE_NEW_GAME_CIV_INFO_DIPLOMACY).setPosY(2 + this.menus.get(this.CREATE_NEW_GAME).get(this.CREATE_NEW_GAME_CIV_INFO_STATS).getPosY() + this.menus.get(this.CREATE_NEW_GAME).get(this.CREATE_NEW_GAME_CIV_INFO_STATS).getHeight() + this.menus.get(this.CREATE_NEW_GAME).get(this.CREATE_NEW_GAME_CIV_INFO_DIPLOMACY).getTitle().getHeight());
                                this.menus.get(this.CREATE_NEW_GAME).get(this.CREATE_NEW_GAME_CIV_INFO_PLAYERS).setPosY(2 + this.menus.get(this.CREATE_NEW_GAME).get(this.CREATE_NEW_GAME_CIV_INFO_DIPLOMACY).getPosY() + this.menus.get(this.CREATE_NEW_GAME).get(this.CREATE_NEW_GAME_CIV_INFO_DIPLOMACY).getHeight() + this.menus.get(this.CREATE_NEW_GAME).get(this.CREATE_NEW_GAME_CIV_INFO_PLAYERS).getTitle().getHeight());
                            }
                            this.rebuildCivilizations_Info_Players();
                        }
                    } else {
                        this.menus.get(this.CREATE_NEW_GAME).set(this.CREATE_NEW_GAME_OPTIONS, new Menu_CreateNewGame_Options_v2());
                        this.menus.get(this.CREATE_NEW_GAME).set(this.CREATE_NEW_GAME_SCENARIOS, new Menu_CreateNewGame_Scenarios());
                        this.rebuildCivilizations_Info_Players();
                    }
                    if (CFG.map.getMapScale().getCurrentScale() < 1.0f) {
                        CFG.map.getMapScale().setCurrentScale(1.0f);
                    }
                    if (CFG.game.getPlayer(0).getCivID() > 0) {
                        CFG.map.getMapCoordinates().centerToProvinceID(CFG.game.getCiv(CFG.game.getPlayer(0).getCivID()).getCapitalProvinceID());
                        CFG.game.setActiveProvinceID(CFG.game.getCiv(CFG.game.getPlayer(0).getCivID()).getCapitalProvinceID());
                        CFG.setActiveCivInfo(CFG.game.getPlayer(0).getCivID());
                        CFG.updateActiveCivInfo_CreateNewGame();
                    }
                    this.getCreateNewGame_CivInfo_updateLanguage();
                    CFG.iSelectCivilizationPlayerID = 0;
                    CFG.game.enableDrawCivlizationsRegions_Players();
                    this.setOrderOfMenu_CreateNewGame_CivInfo();
                    return this.CREATE_NEW_GAME;
                }
                case eCREATE_RANDOM_GAME: {
                    if (this.CREATE_RANDOM_GAME == -1) {
                        this.CREATE_RANDOM_GAME = this.addMenu(new Menu_RandomGame());
                        this.CREATE_RANDOM_GAME_OPTIONS = this.addNextMenuToView(this.CREATE_RANDOM_GAME, new Menu_RandomGame_Options());
                        this.CREATE_RANDOM_GAME_WASTELANDMAPS = this.addNextMenuToView(this.CREATE_RANDOM_GAME, new Menu_RandomGame_WastelandMap());
                        this.CREATE_RANDOM_GAME_SETTINGS = this.addNextMenuToView(this.CREATE_RANDOM_GAME, new Menu_RandomGame_Settings());
                        this.CREATE_RANDOM_GAME_PLAYERS = this.addNextMenuToView(this.CREATE_RANDOM_GAME, new Menu_RandomGame_Players());
                    } else {
                        this.menus.get(this.CREATE_RANDOM_GAME).set(this.CREATE_RANDOM_GAME_OPTIONS, new Menu_RandomGame_Options());
                        this.menus.get(this.CREATE_RANDOM_GAME).set(this.CREATE_RANDOM_GAME_WASTELANDMAPS, new Menu_RandomGame_WastelandMap());
                        this.menus.get(this.CREATE_RANDOM_GAME).set(this.CREATE_RANDOM_GAME_PLAYERS, new Menu_RandomGame_Players());
                    }
                    return this.CREATE_RANDOM_GAME;
                }
                case eNEWGAME_PLAYERS: {
                    if (this.NEWGAME_PLAYERS == -1) {
                        this.NEWGAME_PLAYERS = this.addMenu(new Menu_NewGame_Players());
                    } else {
                        this.menus.get(this.NEWGAME_PLAYERS).set(0, new Menu_NewGame_Players());
                    }
                    return this.NEWGAME_PLAYERS;
                }
                case eINGAME: {
                    if (this.INGAME == -1) {
                        this.INGAME = this.addMenu(new Menu_InGame());
                        this.INGAME_MINIMAP_ACTION = this.addNextMenuToView(this.INGAME, new Menu_InGame_MinimapAction());
                        this.INGAME_PROVINCE_ACTION = this.addNextMenuToView(this.INGAME, new Menu_InGame_ProvinceAction());
                        this.INGAME_PROVINCE_ACTION_COLONIZE = this.addNextMenuToView(this.INGAME, new Menu_InGame_ProvinceAction_Colonize());
                        this.INGAME_PROVINCE_ACTION_COLONIZE_TECH_REQUIRED = this.addNextMenuToView(this.INGAME, new Menu_InGame_Uncolonized());
                        this.INGAME_PROVINCE_ACTION_UNCIVILIZE = this.addNextMenuToView(this.INGAME, new Menu_InGame_ProvinceAction_Uncivilized());
                        this.INGAME_PROVINCE_INFO = this.addNextMenuToView(this.INGAME, new Menu_InGame_ProvinceInfo());
                        this.INGAME_MESSAGES = this.addNextMenuToView(this.INGAME, new Menu_InGame_Messages());
                        this.INGAME_HRE = this.addNextMenuToView(this.INGAME, new Menu_InGame_HRE());
                        this.INGAME_HRE_VOTE = this.addNextMenuToView(this.INGAME, new Menu_InGame_HRE_VoteFor());
                        this.INGAME_MESSAGE = this.addNextMenuToView(this.INGAME, new Menu_InGame_Message_Alliance());
                        this.INGAME_DICES = this.addNextMenuToView(this.INGAME, new Menu_InGame_Dices());
                        this.INGAME_PROVINCE_MOVE_UNITS = this.addNextMenuToView(this.INGAME, new Menu_InGame_Slider());
                        this.INGAME_PROVINCE_RECRUIT_ARMY = this.addNextMenuToView(this.INGAME, new Menu_InGame_Slider_Recruit());
                        this.INGAME_PROVINCE_RECRUIT_ARMY_INSTANTLY = this.addNextMenuToView(this.INGAME, new Menu_InGame_Slider_RecruitInstantly());
                        this.INGAME_PROVINCE_DISBAND_ARMY = this.addNextMenuToView(this.INGAME, new Menu_InGame_Slider_Disband());
                        this.INGAME_PROVINCE_REGROUP_ARMY = this.addNextMenuToView(this.INGAME, new Menu_InGame_Slider_Regroup());
                        this.INGAME_PROVINCE_BUILD = this.addNextMenuToView(this.INGAME, new Menu_InGame_Build());
                        this.INGAME_PROVINCE_BUILD_CONFIRM = this.addNextMenuToView(this.INGAME, new Menu_InGame_BuildConfirm());
                        this.INGAME_VIEW_STATS = this.addNextMenuToView(this.INGAME, new Menu_InGame_View_Population(0));
                        this.INGAME_ACTION_INFO_CHOOSE_PROVINCE = this.addNextMenuToView(this.INGAME, new Menu_InGame_ActionInfo());
                        this.INGAME_FLAG_ACTION = this.addNextMenuToView(this.INGAME, new Menu_InGame_FlagAction());
                        this.INGAME_FLAG_ACTION_STATS = this.addNextMenuToView(this.INGAME, new Menu_InGame_FlagAction_Stats());
                        this.INGAME_FLAG_ACTION_BOT = this.addNextMenuToView(this.INGAME, new Menu_InGame_FlagAction_Bot());
                        this.INGAME_FLAG_ACTION_BOT_RIGHT_LEFT = this.addNextMenuToView(this.INGAME, new Menu_InGame_FlagAction_Bot_Right_Left());
                        this.INGAME_FLAG_ACTION_BOT_RIGHT_RIGHT = this.addNextMenuToView(this.INGAME, new Menu_InGame_FlagAction_Bot_Right_Right());
                        this.INGAME_FLAG_ACTION_BOT_LEFT = this.addNextMenuToView(this.INGAME, new Menu_InGame_FlagAction_Bot_Left());
                        this.INGAME_FLAG_ACTION_GRAPH_MODES = this.addNextMenuToView(this.INGAME, new Menu_InGame_FlagAction_GraphModes());
                        this.INGAME_BUDGET = this.addNextMenuToView(this.INGAME, new Menu_InGame_FlagAction_Budget());
                        this.INGAME_BUDGET_VASSALS = this.addNextMenuToView(this.INGAME, new Menu_InGame_FlagAction_Budget());
                        this.INGAME_FLAG_ACTION_CONSOLE = this.addNextMenuToView(this.INGAME, new Menu_InGame_FlagAction_Console());
                        this.INGAME_RTO = this.addNextMenuToView(this.INGAME, new Menu_InGame_RTO2());
                        this.INGAME_RTO_BOT = this.addNextMenuToView(this.INGAME, new Menu_InGame_RTO_Bot2());
                        this.INGAME_MAP_MODES = this.addNextMenuToView(this.INGAME, new Menu_InGame_MapModes());
                        this.INGAME_MAP_CENSUS_OF_PROVINCE = this.addNextMenuToView(this.INGAME, new Menu_InGame_CensusOfProvince(0));
                        this.INGAME_OUTLINER = this.addNextMenuToView(this.INGAME, new Menu_InGame_Outliner());
                        this.INGAME_CURRENT_WARS = this.addNextMenuToView(this.INGAME, new Menu_InGame_CurrentWars(0));
                        this.INGAME_CURRENT_WARS_INFO = this.addNextMenuToView(this.INGAME, new Menu_InGame_CurrentWars_Info(0));
                        this.INGAME_WAR = this.addNextMenuToView(this.INGAME, new Menu_InGame_War());
                        this.INGAME_CITY_HAVE_BEEN_FOUNED = this.addNextMenuToView(this.INGAME, new Menu_InGame_CityFounded());
                        this.INGAME_CAPITAL_MOVED = this.addNextMenuToView(this.INGAME, new Menu_InGame_CityFounded());
                        this.INGAME_VASSAL_RELEASED = this.addNextMenuToView(this.INGAME, new Menu_InGame_VassalReleased());
                        this.INGAME_SAVED_GAME = this.addNextMenuToView(this.INGAME, new Menu_InGame_GameSaved());
                        this.INGAME_ALLIANCE_INFO = this.addNextMenuToView(this.INGAME, new Menu_InGame_AllianceInfo());
                        this.INGAME_WORLD_POPULATION = this.addNextMenuToView(this.INGAME, new Menu_InGame_WorldPopulation(0));
                        this.INGAME_WARS = this.addNextMenuToView(this.INGAME, new Menu_InGame_Wars(0));
                        this.INGAME_WAR_DETAILS = this.addNextMenuToView(this.INGAME, new Menu_InGame_WarDetails(0));
                        this.INGAME_RANK = this.addNextMenuToView(this.INGAME, new Menu_InGame_Rank(0));
                        this.INGAME_CONQURED_PROVINCES = this.addNextMenuToView(this.INGAME, new Menu_InGame_ConqueredProvinces(0));
                        this.INGAME_VICOTORY_CONDITIONS = this.addNextMenuToView(this.INGAME, new Menu_InGame_VicotryConditions(0));
                        this.INGAME_BUILDINGS_CONSTRUCTED = this.addNextMenuToView(this.INGAME, new Menu_InGame_BuildingsConstructed(0));
                        this.INGAME_RECRUITED_ARMY = this.addNextMenuToView(this.INGAME, new Menu_InGame_RecruitedArmy(0));
                        this.INGAME_ARMY = this.addNextMenuToView(this.INGAME, new Menu_InGame_Army(0));
                        this.INGAME_TRIBUTE = this.addNextMenuToView(this.INGAME, new Menu_InGame_Tribute());
                        this.INGAME_TECHNOLOGY = this.addNextMenuToView(this.INGAME, new Menu_InGame_Technology());
                        this.INGAME_WONDERS = this.addNextMenuToView(this.INGAME, new Menu_InGameWonders(0));
                        this.INGAME_PLAYLIST = this.addNextMenuToView(this.INGAME, new Menu_InGame_Playlist());
                        this.INGAME_WAR_PREPARATIONS = this.addNextMenuToView(this.INGAME, new Menu_InGame_WarPreparations());
                        this.addNextMenuToView(this.INGAME, new Menu_InGame_TopViews());
                        this.INGAME_VIEW_DETAILS = this.addNextMenuToView(this.INGAME, new Menu_InGame_ViewDetails());
                        this.INGAME_VIEW_GRAPH = this.addNextMenuToView(this.INGAME, new Menu_InGame_Graph());
                        this.INGAME_OPTIONS = this.addNextMenuToView(this.INGAME, new Menu_InGame_Options());
                        this.INGAME_ENDOFGAME = this.addNextMenuToView(this.INGAME, new Menu_InGame_EndOfGame());
                        this.INGAME_CIV_INFO = this.addNextMenuToView(this.INGAME, new Menu_InGame_CivInfo());
                        this.INGAME_CIV_INFO2 = this.addNextMenuToView(this.INGAME, new Menu_InGame_CivInfo_Stats());
                        this.INGAME_CIV_INFO_DIPLOMACY = this.addNextMenuToView(this.INGAME, new Menu_InGame_CivInfo_Stats_Diplomacy());
                        this.INGAME_CIV_INFO_DIPLO_ACTIONS = this.addNextMenuToView(this.INGAME, new Menu_InGame_CivInfo_Stats_DiploActions());
                        this.INGAME_CIV_MANAGE = this.addNextMenuToView(this.INGAME, new Menu_InGame_Manage_Info());
                        this.INGAME_CIV_MANAGE2 = this.addNextMenuToView(this.INGAME, new Menu_InGame_Manage_Stats());
                        this.INGAME_CIV_MANAGE_ACTIONS = this.addNextMenuToView(this.INGAME, new Menu_InGame_CivInfo_Manage_DiploActions());
                        this.INGAME_ALLIANCE = this.addNextMenuToView(this.INGAME, new Menu_InGame_Alliance());
                        this.INGAME_MILITARY_ALLIANCES = this.addNextMenuToView(this.INGAME, new Menu_InGame_MilitaryAlliances());
                        this.INGAME_HISTORY = this.addNextMenuToView(this.INGAME, new Menu_InGame_History(0));
                        this.INGAME_TURN_SUMMARY = this.addNextMenuToView(this.INGAME, new Menu_InGame_TurnSummary());
                        this.INGAME_CIV_INFO_ACTIONS = this.addNextMenuToView(this.INGAME, new Menu_InGame_CivInfo_Stats_Actions());
                        this.INGAME_CIV_INFO_OPINIONS = this.addNextMenuToView(this.INGAME, new Menu_InGame_CivInfo_Stats_Opinions());
                        this.INGAME_EVENT = this.addNextMenuToView(this.INGAME, new Menu_InGame_Event(0));
                        this.INGAME_SEND_MESSAGE = this.addNextMenuToView(this.INGAME, new Menu_InGame_DeclareWar());
                        this.INGAME_PLUNDER = this.addNextMenuToView(this.INGAME, new Menu_InGame_DeclareWar());
                        this.INGAME_SEND_MESSAGE_TRADE_LEFT = this.addNextMenuToView(this.INGAME, new Menu_InGame_TradeRequest_Side());
                        this.INGAME_SEND_MESSAGE_TRADE_RIGHT = this.addNextMenuToView(this.INGAME, new Menu_InGame_TradeRequest_Side());
                        this.INGAME_CIV_INFO_DECISIONS = this.addNextMenuToView(this.INGAME, new Menu_InGame_CivInfo_Stats_Decisions());
                        this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_DIPLOMACY).setPosY(2 + this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO2).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO2).getHeight() + this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_DIPLOMACY).getTitle().getHeight());
                        this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_DIPLO_ACTIONS).setPosY(2 + this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_DIPLOMACY).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_DIPLOMACY).getHeight());
                        this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_ACTIONS).setPosY(2 + this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_DIPLO_ACTIONS).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_DIPLO_ACTIONS).getHeight());
                        this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_OPINIONS).setPosY(2 + this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_DIPLO_ACTIONS).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_DIPLO_ACTIONS).getHeight());
                        this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_DECISIONS).setPosY(2 + this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_DIPLOMACY).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_DIPLOMACY).getHeight() + this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_DECISIONS).getTitle().getHeight());
                        if (this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_DIPLO_ACTIONS).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_DIPLO_ACTIONS).getHeight() > CFG.GAME_HEIGHT - (CFG.TEXT_HEIGHT + CFG.PADDING * 4) * 4) {
                            this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO2).setHeight(this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO2).getHeight() * 3 / 5);
                            this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_DIPLOMACY).setPosY(2 + this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO2).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO2).getHeight() + this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_DIPLOMACY).getTitle().getHeight());
                            this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_DIPLO_ACTIONS).setPosY(2 + this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_DIPLOMACY).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_DIPLOMACY).getHeight());
                            this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_ACTIONS).setPosY(2 + this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_DIPLO_ACTIONS).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_DIPLO_ACTIONS).getHeight());
                            this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_OPINIONS).setPosY(2 + this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_DIPLO_ACTIONS).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_DIPLO_ACTIONS).getHeight());
                            this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_DECISIONS).setPosY(2 + this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_DIPLOMACY).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_DIPLOMACY).getHeight() + this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_DECISIONS).getTitle().getHeight());
                            if (this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_DIPLO_ACTIONS).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_DIPLO_ACTIONS).getHeight() > CFG.GAME_HEIGHT - (CFG.TEXT_HEIGHT + CFG.PADDING * 4) * 4) {
                                this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_DIPLOMACY).setHeight(this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_DIPLOMACY).getHeight() * 3 / 5);
                                this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_DIPLOMACY).setPosY(2 + this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO2).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO2).getHeight() + this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_DIPLOMACY).getTitle().getHeight());
                                this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_DIPLO_ACTIONS).setPosY(2 + this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_DIPLOMACY).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_DIPLOMACY).getHeight());
                                this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_ACTIONS).setPosY(2 + this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_DIPLO_ACTIONS).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_DIPLO_ACTIONS).getHeight());
                                this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_OPINIONS).setPosY(2 + this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_DIPLO_ACTIONS).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_DIPLO_ACTIONS).getHeight());
                                this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_DECISIONS).setPosY(2 + this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_DIPLOMACY).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_DIPLOMACY).getHeight() + this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_DECISIONS).getTitle().getHeight());
                            }
                        }
                        this.INGAME_REPORT = this.addNextMenuToView(this.INGAME, new Menu_InGame_Report());
                    }
                    return this.INGAME;
                }
                case eINGAME_FORMABLE_CIV_PROVINCES: {
                    //spectatormode formable change//
                    if (this.INGAME_FORMABLE_CIV_PROVINCES == -1) {
                        if (CFG.SPECTATOR_MODE) {
                            this.INGAME_FORMABLE_CIV_PROVINCES = this.addMenu(new Menu_InGame_FormableCiv_Sandbox());
                        } else {
                            this.INGAME_FORMABLE_CIV_PROVINCES = this.addMenu(new Menu_InGame_FormableCiv_Provinces());
                            this.addNextMenuToView(this.INGAME_FORMABLE_CIV_PROVINCES, new Menu_InGame_FormableCiv_Provinces_TodayPartOf());
                        }
                    } else {
                        if (CFG.SPECTATOR_MODE) {
                            this.menus.get(this.INGAME_FORMABLE_CIV_PROVINCES).set(0, new Menu_InGame_FormableCiv_Sandbox());
                        } else {
                            this.menus.get(this.INGAME_FORMABLE_CIV_PROVINCES).set(0, new Menu_InGame_FormableCiv_Provinces());
                            this.menus.get(this.INGAME_FORMABLE_CIV_PROVINCES).set(1, new Menu_InGame_FormableCiv_Provinces_TodayPartOf());
                        }
                    }
                    return this.INGAME_FORMABLE_CIV_PROVINCES;
                }
                case eINGAME_PEACE_TREATY: {
                    CFG.game.setActiveProvinceID(-1);
                    Menu_PeaceTreaty_Response.DRAW_TREATY_PROVINCES = true;
                    if (this.INGAME_PEACE_TREATY == -1) {
                        this.INGAME_PEACE_TREATY = this.addMenu(new Menu_PeaceTreaty());
                        this.INGAME_PEACE_TREATY_PROVINCES = this.addNextMenuToView(this.INGAME_PEACE_TREATY, new Menu_PeaceTreaty_Provinces());
                        this.INGAME_PEACE_TREATY_PROVINCES_SHOW = this.addNextMenuToView(this.INGAME_PEACE_TREATY, new Menu_PeaceTreaty_Provinces_Show());
                        this.INGAME_PEACE_TREATY_SCORES = this.addNextMenuToView(this.INGAME_PEACE_TREATY, new Menu_PeaceTreaty_Scores());
                    } else {
                        this.menus.get(this.INGAME_PEACE_TREATY).set(0, new Menu_PeaceTreaty());
                        this.menus.get(this.INGAME_PEACE_TREATY).set(this.INGAME_PEACE_TREATY_PROVINCES, new Menu_PeaceTreaty_Provinces());
                        this.menus.get(this.INGAME_PEACE_TREATY).set(this.INGAME_PEACE_TREATY_SCORES, new Menu_PeaceTreaty_Scores());
                        this.menus.get(this.INGAME_PEACE_TREATY).get(this.INGAME_PEACE_TREATY_PROVINCES_SHOW).setVisible(false);
                    }
                    CFG.peaceTreatyData.AI_UseVictoryPoints();
                    return this.INGAME_PEACE_TREATY;
                }
                case eINGAME_PEACE_TREATY_RESPONSE: {
                    CFG.game.setActiveProvinceID(-1);
                    Menu_PeaceTreaty_Response.DRAW_TREATY_PROVINCES = true;
                    if (this.INGAME_PEACE_TREATY_RESPONSE == -1) {
                        this.INGAME_PEACE_TREATY_RESPONSE = this.addMenu(new Menu_PeaceTreaty_Response());
                        this.INGAME_PEACE_TREATY_RESPONSE_PROVINCES = this.addNextMenuToView(this.INGAME_PEACE_TREATY_RESPONSE, new Menu_PeaceTreaty_Response_Demands());
                        this.INGAME_PEACE_TREATY_RESPONSE_PROVINCES_SHOW = this.addNextMenuToView(this.INGAME_PEACE_TREATY_RESPONSE, new Menu_PeaceTreaty_Response_Demands_Show());
                        this.INGAME_PEACE_TREATY_RESPONSE_CIVS = this.addNextMenuToView(this.INGAME_PEACE_TREATY_RESPONSE, new Menu_PeaceTreaty_Response_Civs());
                    } else {
                        this.menus.get(this.INGAME_PEACE_TREATY_RESPONSE).set(0, new Menu_PeaceTreaty_Response());
                        this.menus.get(this.INGAME_PEACE_TREATY_RESPONSE).set(this.INGAME_PEACE_TREATY_RESPONSE_PROVINCES, new Menu_PeaceTreaty_Response_Demands());
                        this.menus.get(this.INGAME_PEACE_TREATY_RESPONSE).set(this.INGAME_PEACE_TREATY_RESPONSE_PROVINCES_SHOW, new Menu_PeaceTreaty_Response_Demands_Show());
                        this.menus.get(this.INGAME_PEACE_TREATY_RESPONSE).set(this.INGAME_PEACE_TREATY_RESPONSE_CIVS, new Menu_PeaceTreaty_Response_Civs());
                    }
                    return this.INGAME_PEACE_TREATY_RESPONSE;
                }
                case eTIMELINE: {
                    CFG.timelapseManager.buildTimeline();
                    if (this.INGAME_TIMELINE == -1) {
                        this.INGAME_TIMELINE = this.addMenu(new Menu_Timeline());
                    } else {
                        this.menus.get(this.INGAME_TIMELINE).get(0).updateLanguage();
                    }
                    return this.INGAME_TIMELINE;
                }
                case eINGAME_CREATE_VASSAL: {
                    if (this.INGAME_CREATE_VASSAL == -1) {
                        this.INGAME_CREATE_VASSAL = this.addMenu(new Menu_InGame_CreateAVassal());
                        this.INGAME_CREATE_VASSAL_INFO = this.addNextMenuToView(this.INGAME_CREATE_VASSAL, new Menu_InGame_CreateAVassal_Info());
                        this.INGAME_CREATE_VASSAL_INFO_STATS = this.addNextMenuToView(this.INGAME_CREATE_VASSAL, new Menu_InGame_CreateAVassal_Info_Stats());
                        this.INGAME_CREATE_VASSAL_CHANGEAUTONOMY = this.addNextMenuToView(this.INGAME_CREATE_VASSAL, new Menu_InGame_CreateAVassal_ChangeAutonomy());
                        this.INGAME_CREATE_VASSAL_CHANGEGOVERNMENT = this.addNextMenuToView(this.INGAME_CREATE_VASSAL, new Menu_InGame_CreateAVassal_ChangeGovernment());
                        this.INGAME_CREATE_VASSAL_MAPMODES = this.addNextMenuToView(this.INGAME_CREATE_VASSAL, new Menu_InGame_CreateAVassal_MapModes());
                        this.INGAME_CREATE_VASSAL_CIVS = this.addNextMenuToView(this.INGAME_CREATE_VASSAL, new Menu_InGame_CreateAVassal_Civ());
                    } else {
                        this.menus.get(this.INGAME_CREATE_VASSAL).set(0, new Menu_InGame_CreateAVassal());
                        this.menus.get(this.INGAME_CREATE_VASSAL).set(this.INGAME_CREATE_VASSAL_INFO, new Menu_InGame_CreateAVassal_Info());
                        this.menus.get(this.INGAME_CREATE_VASSAL).set(this.INGAME_CREATE_VASSAL_INFO_STATS, new Menu_InGame_CreateAVassal_Info_Stats());
                        this.menus.get(this.INGAME_CREATE_VASSAL).set(this.INGAME_CREATE_VASSAL_CHANGEAUTONOMY, new Menu_InGame_CreateAVassal_ChangeAutonomy());
                        this.menus.get(this.INGAME_CREATE_VASSAL).set(this.INGAME_CREATE_VASSAL_CHANGEGOVERNMENT, new Menu_InGame_CreateAVassal_ChangeGovernment());
                        this.menus.get(this.INGAME_CREATE_VASSAL).set(this.INGAME_CREATE_VASSAL_CIVS, new Menu_InGame_CreateAVassal_Civ());
                    }
                    CFG.updateCreateAVassal_CivInfo();
                    return this.INGAME_CREATE_VASSAL;
                }
                case eINGAME_CREATE_VASSAL_SELECT_CIV: {
                    if (this.INGAME_CREATE_VASSAL_SELECT_CIV == -1) {
                        this.INGAME_CREATE_VASSAL_SELECT_CIV = this.addMenu(new Menu_InGame_CreateAVassal_SelectCiv());
                        this.INGAME_CREATE_VASSAL_SELECT_CIV_ALPHABET = this.addNextMenuToView(this.INGAME_CREATE_VASSAL_SELECT_CIV, new Menu_InGame_CreateAVassal_SelectCiv_Alphabet());
                        this.INGAME_CREATE_VASSAL_SELECT_CIV_LIST = this.addNextMenuToView(this.INGAME_CREATE_VASSAL_SELECT_CIV, new Menu_InGame_CreateAVassal_SelectCiv_List());
                    } else {
                        this.menus.get(this.INGAME_CREATE_VASSAL_SELECT_CIV).set(this.INGAME_CREATE_VASSAL_SELECT_CIV_ALPHABET, new Menu_InGame_CreateAVassal_SelectCiv_Alphabet());
                        this.menus.get(this.INGAME_CREATE_VASSAL_SELECT_CIV).get(this.INGAME_CREATE_VASSAL_SELECT_CIV_LIST).onBackPressed();
                        this.menus.get(this.INGAME_CREATE_VASSAL_SELECT_CIV).set(this.INGAME_CREATE_VASSAL_SELECT_CIV_LIST, new Menu_InGame_CreateAVassal_SelectCiv_List());
                    }
                    return this.INGAME_CREATE_VASSAL_SELECT_CIV;
                }
                case eINGAME_FORM_ANIMATION: {
                    if (this.INGAME_FORM_ANIMATION == -1) {
                        this.INGAME_FORM_ANIMATION = this.addMenu(new Menu_InGame_FormAnimation());
                    } else {
                        this.menus.get(this.INGAME_FORM_ANIMATION).set(0, new Menu_InGame_FormAnimation());
                    }
                    return this.INGAME_FORM_ANIMATION;
                }
                case eSELECT_CIVILIZATION: {
                    if (this.SELECT_CIVILIZATION == -1) {
                        this.SELECT_CIVILIZATION = this.addMenu(new Menu_SelectCivilization());
                    }
                    return this.SELECT_CIVILIZATION;
                }
                case eINGAME_SELECT_PROVINCES: {
                    if (this.INGAME_SELECT_PROVINCES == -1) {
                        this.INGAME_SELECT_PROVINCES = this.addMenu(new Menu_InGame_SelectProvinces());
                    } else {
                        this.menus.get(this.INGAME_SELECT_PROVINCES).set(0, new Menu_InGame_SelectProvinces());
                    }
                    return this.INGAME_SELECT_PROVINCES;
                }
                case eINGAME_SHOW_PROVINCES: {
                    if (this.INGAME_SHOW_PROVINCES == -1) {
                        this.INGAME_SHOW_PROVINCES = this.addMenu(new Menu_InGame_ShowProvinces());
                        this.addNextMenuToView(this.INGAME_SHOW_PROVINCES, new Menu_InGame_ShowProvinces_List());
                    } else {
                        this.menus.get(this.INGAME_SHOW_PROVINCES).set(1, new Menu_InGame_ShowProvinces_List());
                    }
                    return this.INGAME_SHOW_PROVINCES;
                }
                case eINGAME_TRADE_SELECT_CIV: {
                    if (this.INGAME_TRADE_SELECT_CIV == -1) {
                        this.INGAME_TRADE_SELECT_CIV = this.addMenu(new Menu_InGame_TradeRequest_SelectCiv());
                    } else {
                        this.menus.get(this.INGAME_TRADE_SELECT_CIV).set(0, new Menu_InGame_TradeRequest_SelectCiv());
                    }
                    return this.INGAME_TRADE_SELECT_CIV;
                }
                case eSELECT_CIVILIZATION_CLASSIC: {
                    if (this.SELECT_CIVILIZATION_CLASSIC == -1) {
                        this.SELECT_CIVILIZATION_CLASSIC = this.addMenu(new Menu_SelectCivilization_Classic());
                    }
                    return this.SELECT_CIVILIZATION_CLASSIC;
                }
                case eCHOOSE_SCENARIO: {
                    if (this.CHOOSE_SCENARIO == -1) {
                        this.CHOOSE_SCENARIO = this.addMenu(new Menu_ChooseScenario_Title());
                        this.addNextMenuToView(this.CHOOSE_SCENARIO, new Menu_ChooseScenario());
                        this.menus.get(this.CHOOSE_SCENARIO).get(0).setVisible(true);
                    } else {
                        this.menus.get(this.CHOOSE_SCENARIO).get(0).setVisible(true);
                        this.menus.get(this.CHOOSE_SCENARIO).set(1, new Menu_ChooseScenario());
                    }
                    return this.CHOOSE_SCENARIO;
                }
                case eSELECT_AVAILABLE_CIVILIZATIONS: {
                    if (this.SELECT_AVAILABLE_CIVILIZATIONS == -1) {
                        this.SELECT_AVAILABLE_CIVILIZATIONS = this.addMenu(new Menu_SelectAvailableCivilizations());
                    }
                    return this.SELECT_AVAILABLE_CIVILIZATIONS;
                }
                case eSETTINGS: {
                    if (this.SETTINGS == -1) {
                        this.SETTINGS = this.addMenu(new Menu_Settings());
                        this.addNextMenuToView(this.SETTINGS, new Menu_Settings_Options());
                        this.SETTINGS_AUDIO = this.addNextMenuToView(this.SETTINGS, new Menu_InGame_Playlist());
                    } else {
                        this.menus.get(this.SETTINGS).set(1, new Menu_Settings_Options());
                        this.menus.get(this.SETTINGS).set(2, new Menu_InGame_Playlist());
                    }
                    return this.SETTINGS;
                }
                case eSELECT_LANGUAGE: {
                    if (this.SELECT_LANGUAGE == -1) {
                        this.SELECT_LANGUAGE = this.addMenu(new Menu_SelectLanguage());
                    } else {
                        this.menus.get(this.SELECT_LANGUAGE).set(0, new Menu_SelectLanguage());
                    }
                    CFG.map.getMapScroll().stopScrollingTheMap();
                    CFG.map.getMapScale().setCurrentScale(Map_Scale.MINSCALE);
                    CFG.map.getMapCoordinates().setNewPosX(-((int)((float)(CFG.map.getMapBG().getWidth() / 2) - (float)CFG.GAME_WIDTH / Map_Scale.MINSCALE / 2.0f)));
                    CFG.map.getMapCoordinates().setNewPosY(-((int)((float)(CFG.map.getMapBG().getHeight() / 2) - (float)CFG.GAME_HEIGHT / Map_Scale.MINSCALE / 2.0f)));
                    return this.SELECT_LANGUAGE;
                }
                case eSETTINGS_PROVINCE: {
                    if (this.SETTINGS_PROVINCE == -1) {
                        this.SETTINGS_PROVINCE = this.addMenu(new Menu_Settings_Province());
                    } else {
                        this.menus.get(this.SETTINGS_PROVINCE).set(0, new Menu_Settings_Province());
                    }
                    return this.SETTINGS_PROVINCE;
                }
                case eHELP: {
                    if (this.HELP == -1) {
                        this.HELP = this.addMenu(new Menu_Help());
                    }
                    return this.HELP;
                }
                case eSTART_THE_GAME: {
                    if (this.START_THE_GAME == -1) {
                        this.START_THE_GAME = this.addMenu(new Menu_StartTheGame());
                    } else {
                        this.menus.get(this.START_THE_GAME).set(0, new Menu_StartTheGame());
                    }
                    return this.START_THE_GAME;
                }
                case eEND_THE_GAME: {
                    if (this.END_THE_GAME == -1) {
                        this.END_THE_GAME = this.addMenu(new Menu_StartTheGame_Reverse());
                    } else {
                        this.menus.get(this.END_THE_GAME).set(0, new Menu_StartTheGame_Reverse());
                    }
                    return this.END_THE_GAME;
                }
                case eNEXT_PLAYER_TURN: {
                    if (this.NEXT_PLAYER_TURN == -1) {
                        this.NEXT_PLAYER_TURN = this.addMenu(new Menu_NextPlayerTurn());
                    }
                    this.menus.get(this.NEXT_PLAYER_TURN).get(0).getMenuElement(0).setVisible(true);
                    if (Game_Calendar.TURN_ID % 100 == 0 || CFG.gameAction.getNumOfPlayersInGame() > 1) {
                        CFG.map.getMapScroll().stopScrollingTheMap();
                        Menu_NextPlayerTurn.SCALE_BEFORE_NEXT_PLAYER_MENU = CFG.map.getMapScale().getCurrentScale();
                        CFG.map.getMapCoordinates().centerToCivilizationBox(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), true, false);
                    }
                    CFG.toast.setInView(false);
                    Menu_NextPlayerTurn.updateData();
                    return this.NEXT_PLAYER_TURN;
                }
                case eVICTORY: {
                    CFG.timelapseManager.buildTimeline();
                    if (this.VICTORY == -1) {
                        this.VICTORY = this.addMenu(new Menu_Victory(true));
                    } else {
                        this.menus.get(this.VICTORY).set(0, new Menu_Victory(true));
                    }
                    CFG.toast.setInView(false);
                    return this.VICTORY;
                }
                case eDEFEAT: {
                    CFG.timelapseManager.buildTimeline();
                    if (this.VICTORY == -1) {
                        this.VICTORY = this.addMenu(new Menu_Victory(false));
                    } else {
                        this.menus.get(this.VICTORY).set(0, new Menu_Victory(false));
                    }
                    CFG.toast.setInView(false);
                    return this.VICTORY;
                }
                case eINGAME_CIV_VIEW: {
                    if (this.INGAME_CIV_VIEW == -1) {
                        this.INGAME_CIV_VIEW = this.addMenu(new Menu_InGame_CivilizationView());
                    }
                    this.menus.get(this.INGAME_CIV_VIEW).get(0).getMenuElement(0).setVisible(true);
                    CFG.map.getMapScroll().stopScrollingTheMap();
                    if (CFG.FOG_OF_WAR == 2) {
                        CFG.map.getMapCoordinates().centerToCivilizationBox_FogOfWar(Menu_InGame_CivilizationView.iCivID, true);
                    } else {
                        CFG.map.getMapCoordinates().centerToCivilizationBox(Menu_InGame_CivilizationView.iCivID, true);
                    }
                    CFG.toast.setInView(false);
                    return this.INGAME_CIV_VIEW;
                }
                case eFLAG_EDITOR: {
                    if (this.FLAG_EDITOR == -1) {
                        this.FLAG_EDITOR = this.addMenu(new Menu_FlagEditor());
                        this.addNextMenuToView(this.FLAG_EDITOR, new Menu_Flag());
                    }
                    return this.FLAG_EDITOR;
                }
                case eABOUT: {
                    if (this.ABOUT == -1) {
                        this.ABOUT = this.addMenu(new Menu_About());
                        this.addNextMenuToView(this.ABOUT, new Menu_AboutRate());
                    } else {
                        this.menus.get(this.ABOUT).set(0, new Menu_About());
                    }
                    return this.ABOUT;
                }
                case eSELECT_UI_SCALE: {
                    if (this.SELECT_UI_SCALE == -1) {
                        this.SELECT_UI_SCALE = this.addMenu(new Menu_SelectUIScale_Title());
                        this.addNextMenuToView(this.SELECT_UI_SCALE, new Menu_SelectUIScale());
                    } else {
                        this.menus.get(this.SELECT_UI_SCALE).set(1, new Menu_SelectUIScale());
                    }
                    return this.SELECT_UI_SCALE;
                }
                case eSETTINGS_GRAPHICS: {
                    if (this.SETTINGS_GRAPHICS == -1) {
                        this.SETTINGS_GRAPHICS = this.addMenu(new Menu_Graphics_Title());
                        this.addNextMenuToView(this.SETTINGS_GRAPHICS, new Menu_Graphics());
                    } else {
                        this.menus.get(this.SETTINGS_GRAPHICS).set(1, new Menu_Graphics());
                    }
                    return this.SETTINGS_GRAPHICS;
                }
                case eSETTINGS_RESOLUTION: {
                    if (this.SETTINGS_RESOLUTION == -1) {
                        this.SETTINGS_RESOLUTION = this.addMenu(new Menu_Resolution_Title());
                        this.addNextMenuToView(this.SETTINGS_RESOLUTION, new Menu_Resolution());
                    }
                    return this.SETTINGS_RESOLUTION;
                }
                case eSELECT_MAP_TYPE: {
                    if (this.SELECT_MAP_TYPE == -1) {
                        this.SELECT_MAP_TYPE = this.addMenu(new Menu_SelectMapType());
                    } else {
                        this.menus.get(this.SELECT_MAP_TYPE).set(0, new Menu_SelectMapType());
                    }
                    return this.SELECT_MAP_TYPE;
                }
                case eVICTORY_CONDITIONS: {
                    if (this.VICTORY_CONDITIONS == -1) {
                        this.VICTORY_CONDITIONS = this.addMenu(new Menu_VicotryConditions());
                    } else {
                        this.menus.get(this.VICTORY_CONDITIONS).set(0, new Menu_VicotryConditions());
                    }
                    return this.VICTORY_CONDITIONS;
                }
                case eSELECT_MAP_TYPE_SCALE: {
                    if (this.SELECT_MAP_TYPE_SCALE == -1) {
                        this.SELECT_MAP_TYPE_SCALE = this.addMenu(new Menu_SelectMapType_Scale());
                    } else {
                        this.menus.get(this.SELECT_MAP_TYPE_SCALE).set(0, new Menu_SelectMapType_Scale());
                    }
                    return this.SELECT_MAP_TYPE_SCALE;
                }
                case eEDITOR_SCENARIOS: {
                    if (this.EDITOR_SCENARIOS == -1) {
                        this.EDITOR_SCENARIOS = this.addMenu(new Menu_Editor_Scenarios_Title());
                        this.addNextMenuToView(this.EDITOR_SCENARIOS, new Menu_Editor_Scenarios());
                    } else {
                        this.menus.get(this.EDITOR_SCENARIOS).set(1, new Menu_Editor_Scenarios());
                    }
                    return this.EDITOR_SCENARIOS;
                }
                case eLOADGAME: {
                    if (this.LOADGAME == -1) {
                        this.LOADGAME = this.addMenu(new Menu_LoadGame_Title());
                        this.addNextMenuToView(this.LOADGAME, new Menu_LoadGame());
                    } else {
                        this.menus.get(this.LOADGAME).set(1, new Menu_LoadGame());
                    }
                    return this.LOADGAME;
                }
                case eEDITOR_UNIONS: {
                    if (this.EDITOR_UNIONS == -1) {
                        this.EDITOR_UNIONS = this.addMenu(new Menu_Editor_Unions());
                        this.addNextMenuToView(this.EDITOR_UNIONS, new Menu_Editor_Unions_List());
                    } else {
                        this.menus.get(this.EDITOR_UNIONS).set(1, new Menu_Editor_Unions_List());
                    }
                    return this.EDITOR_UNIONS;
                }
                case eEDITOR_UNIONS_EDIT: {
                    if (this.EDITOR_UNIONS_EDIT == -1) {
                        this.EDITOR_UNIONS_EDIT = this.addMenu(new Menu_Editor_Union_Edit());
                    } else {
                        this.menus.get(this.EDITOR_UNIONS_EDIT).set(0, new Menu_Editor_Union_Edit());
                    }
                    return this.EDITOR_UNIONS_EDIT;
                }
                case eCREATE_SCENARIO_COLONIZATION: {
                    if (this.CREATE_SCENARIO_COLONIZATION == -1) {
                        this.CREATE_SCENARIO_COLONIZATION = this.addMenu(new Menu_CreateScenario_ColonizationTitle());
                        this.addNextMenuToView(this.CREATE_SCENARIO_COLONIZATION, new Menu_CreateScenario_Colonization());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_COLONIZATION).set(1, new Menu_CreateScenario_Colonization());
                    }
                    return this.CREATE_SCENARIO_COLONIZATION;
                }
                case eCREATE_SCENARIO_EVENTS: {
                    if (this.CREATE_SCENARIO_EVENTS == -1) {
                        this.CREATE_SCENARIO_EVENTS = this.addMenu(new Menu_CreateScenario_Events());
                        this.CREATE_SCENARIO_EVENTS_LIST = this.addNextMenuToView(this.CREATE_SCENARIO_EVENTS, new Menu_CreateScenario_Events_List());
                        this.CREATE_SCENARIO_EVENTS_EDIT = this.addNextMenuToView(this.CREATE_SCENARIO_EVENTS, new Menu_CreateScenario_Events_Edit());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS).set(0, new Menu_CreateScenario_Events());
                        this.menus.get(this.CREATE_SCENARIO_EVENTS).set(this.CREATE_SCENARIO_EVENTS_LIST, new Menu_CreateScenario_Events_List());
                        this.menus.get(this.CREATE_SCENARIO_EVENTS).set(this.CREATE_SCENARIO_EVENTS_EDIT, new Menu_CreateScenario_Events_Edit());
                    }
                    return this.CREATE_SCENARIO_EVENTS;
                }
                case eCREATE_SCENARIO_CIVILIZATIONS: {
                    if (this.CREATE_SCENARIO_CIVILIZATIONS == -1) {
                        this.CREATE_SCENARIO_CIVILIZATIONS = this.addMenu(new Menu_CreateScenario_Civilizations());
                        this.CREATE_SCENARIO_CIVILIZATIONS_SUGGEST = this.addNextMenuToView(this.CREATE_SCENARIO_CIVILIZATIONS, new Menu_CreateScenario_Civilizations_Suggest());
                        this.CREATE_SCENARIO_CIVILIZATIONS_IDEOLOGIES = this.addNextMenuToView(this.CREATE_SCENARIO_CIVILIZATIONS, new Menu_CreateScenario_Civilizations_Ideologies());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_CIVILIZATIONS).get(this.CREATE_SCENARIO_CIVILIZATIONS_IDEOLOGIES).setVisible(false);
                    }
                    if (CFG.game.getActiveProvinceID() >= 0) {
                        CFG.game.enableDrawCivilizationRegions(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID(), 0);
                    }
                    return this.CREATE_SCENARIO_CIVILIZATIONS;
                }
                case eCREATE_SCENARIO_CIVILIZATIONS_SELECT: {
                    if (this.CREATE_SCENARIO_CIVILIZATIONS_SELECT == -1) {
                        this.CREATE_SCENARIO_CIVILIZATIONS_SELECT = this.addMenu(new Menu_CreateScenario_Civilizations_Select());
                        this.CREATE_SCENARIO_CIVILIZATIONS_SELECT_ALPHABET = this.addNextMenuToView(this.CREATE_SCENARIO_CIVILIZATIONS_SELECT, new Menu_CreateScenario_Civilizations_Select_Alphabet());
                        this.CREATE_SCENARIO_CIVILIZATIONS_SELECT_LIST = this.addNextMenuToView(this.CREATE_SCENARIO_CIVILIZATIONS_SELECT, new Menu_CreateScenario_Civilizations_Select_List());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_CIVILIZATIONS_SELECT).set(this.CREATE_SCENARIO_CIVILIZATIONS_SELECT_ALPHABET, new Menu_CreateScenario_Civilizations_Select_Alphabet());
                        this.menus.get(this.CREATE_SCENARIO_CIVILIZATIONS_SELECT).get(this.CREATE_SCENARIO_CIVILIZATIONS_SELECT_LIST).onBackPressed();
                        this.menus.get(this.CREATE_SCENARIO_CIVILIZATIONS_SELECT).set(this.CREATE_SCENARIO_CIVILIZATIONS_SELECT_LIST, new Menu_CreateScenario_Civilizations_Select_List());
                    }
                    return this.CREATE_SCENARIO_CIVILIZATIONS_SELECT;
                }
                case eEDITOR_UNIONS_ADDCIV: {
                    if (this.EDITOR_UNIONS_ADDCIV == -1) {
                        this.EDITOR_UNIONS_ADDCIV = this.addMenu(new Menu_Editor_Unions_AddCiv());
                        this.EDITOR_UNIONS_ADDCIV_ALPHABET = this.addNextMenuToView(this.EDITOR_UNIONS_ADDCIV, new Menu_Editor_Unions_AddCiv_Alphabet());
                        this.EDITOR_UNIONS_ADDCIV_LIST = this.addNextMenuToView(this.EDITOR_UNIONS_ADDCIV, new Menu_Editor_Unions_AddCiv_List());
                    } else {
                        this.menus.get(this.EDITOR_UNIONS_ADDCIV).set(this.EDITOR_UNIONS_ADDCIV_ALPHABET, new Menu_Editor_Unions_AddCiv_Alphabet());
                        this.menus.get(this.EDITOR_UNIONS_ADDCIV).get(this.EDITOR_UNIONS_ADDCIV_LIST).onBackPressed();
                        this.menus.get(this.EDITOR_UNIONS_ADDCIV).set(this.EDITOR_UNIONS_ADDCIV_LIST, new Menu_Editor_Unions_AddCiv_List());
                    }
                    return this.EDITOR_UNIONS_ADDCIV;
                }
                case eCREATE_SCENARIO_CORES_ADD_CORE: {
                    if (this.CREATE_SCENARIO_CORES_ADD_CORE == -1) {
                        this.CREATE_SCENARIO_CORES_ADD_CORE = this.addMenu(new Menu_CreateScenario_Cores_AddCore());
                        this.CREATE_SCENARIO_CORES_ADD_CORE_ALPHABET = this.addNextMenuToView(this.CREATE_SCENARIO_CORES_ADD_CORE, new Menu_CreateScenario_Cores_AddCore_Alphabet());
                        this.CREATE_SCENARIO_CORES_ADD_CORE_LIST = this.addNextMenuToView(this.CREATE_SCENARIO_CORES_ADD_CORE, new Menu_CreateScenario_Cores_AddCore_List());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_CORES_ADD_CORE).set(this.CREATE_SCENARIO_CORES_ADD_CORE_ALPHABET, new Menu_CreateScenario_Cores_AddCore_Alphabet());
                        this.menus.get(this.CREATE_SCENARIO_CORES_ADD_CORE).get(this.CREATE_SCENARIO_CORES_ADD_CORE_LIST).onBackPressed();
                        this.menus.get(this.CREATE_SCENARIO_CORES_ADD_CORE).set(this.CREATE_SCENARIO_CORES_ADD_CORE_LIST, new Menu_CreateScenario_Cores_AddCore_List());
                    }
                    return this.CREATE_SCENARIO_CORES_ADD_CORE;
                }
                case eCREATE_SCENARIO_CORES_ADD_CIV: {
                    if (this.CREATE_SCENARIO_CORES_ADD_CIV == -1) {
                        this.CREATE_SCENARIO_CORES_ADD_CIV = this.addMenu(new Menu_CreateScenario_Cores_AddCiv());
                        this.CREATE_SCENARIO_CORES_ADD_CIV_ALPHABET = this.addNextMenuToView(this.CREATE_SCENARIO_CORES_ADD_CIV, new Menu_CreateScenario_Cores_AddCiv_Alphabet());
                        this.CREATE_SCENARIO_CORES_ADD_CIV_LIST = this.addNextMenuToView(this.CREATE_SCENARIO_CORES_ADD_CIV, new Menu_CreateScenario_Cores_AddCiv_List());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_CORES_ADD_CIV).set(this.CREATE_SCENARIO_CORES_ADD_CIV_ALPHABET, new Menu_CreateScenario_Cores_AddCiv_Alphabet());
                        this.menus.get(this.CREATE_SCENARIO_CORES_ADD_CIV).get(this.CREATE_SCENARIO_CORES_ADD_CIV_LIST).onBackPressed();
                        this.menus.get(this.CREATE_SCENARIO_CORES_ADD_CIV).set(this.CREATE_SCENARIO_CORES_ADD_CIV_LIST, new Menu_CreateScenario_Cores_AddCiv_List());
                    }
                    return this.CREATE_SCENARIO_CORES_ADD_CIV;
                }
                case eCREATE_RANDOM_GAME_CIVILIZATIONS_SELECT: {
                    if (this.CREATE_RANDOM_GAME_CIVILIZATIONS_SELECT == -1) {
                        this.CREATE_RANDOM_GAME_CIVILIZATIONS_SELECT = this.addMenu(new Menu_RandomGame_Civilizations_Select());
                        this.CREATE_RANDOM_GAME_CIVILIZATIONS_SELECT_ALPHABET = this.addNextMenuToView(this.CREATE_RANDOM_GAME_CIVILIZATIONS_SELECT, new Menu_RandomGame_Civilizations_Select_Alphabet());
                        this.CREATE_RANDOM_GAME_CIVILIZATIONS_SELECT_LIST = this.addNextMenuToView(this.CREATE_RANDOM_GAME_CIVILIZATIONS_SELECT, new Menu_RandomGame_Civilizations_Select_List());
                    } else {
                        this.menus.get(this.CREATE_RANDOM_GAME_CIVILIZATIONS_SELECT).set(this.CREATE_RANDOM_GAME_CIVILIZATIONS_SELECT_ALPHABET, new Menu_RandomGame_Civilizations_Select_Alphabet());
                        this.menus.get(this.CREATE_RANDOM_GAME_CIVILIZATIONS_SELECT).get(this.CREATE_RANDOM_GAME_CIVILIZATIONS_SELECT_LIST).onBackPressed();
                        this.menus.get(this.CREATE_RANDOM_GAME_CIVILIZATIONS_SELECT).set(this.CREATE_RANDOM_GAME_CIVILIZATIONS_SELECT_LIST, new Menu_RandomGame_Civilizations_Select_List());
                    }
                    return this.CREATE_RANDOM_GAME_CIVILIZATIONS_SELECT;
                }
                case eCREATE_SCENARIO_EVENTS_SELECT_CIV: {
                    if (this.CREATE_SCENARIO_EVENTS_SELECT_CIV == -1) {
                        this.CREATE_SCENARIO_EVENTS_SELECT_CIV = this.addMenu(new Menu_CreateScenario_Events_SelectCiv());
                        this.CREATE_SCENARIO_EVENTS_SELECT_CIV_ALPHABET = this.addNextMenuToView(this.CREATE_SCENARIO_EVENTS_SELECT_CIV, new Menu_CreateScenario_Events_SelectCiv_Alphabet());
                        this.CREATE_SCENARIO_EVENTS_SELECT_CIV_LIST = this.addNextMenuToView(this.CREATE_SCENARIO_EVENTS_SELECT_CIV, new Menu_CreateScenario_Events_SelectCiv_List());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_SELECT_CIV).set(this.CREATE_SCENARIO_EVENTS_SELECT_CIV_ALPHABET, new Menu_CreateScenario_Events_SelectCiv_Alphabet());
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_SELECT_CIV).get(this.CREATE_SCENARIO_EVENTS_SELECT_CIV_LIST).onBackPressed();
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_SELECT_CIV).set(this.CREATE_SCENARIO_EVENTS_SELECT_CIV_LIST, new Menu_CreateScenario_Events_SelectCiv_List());
                    }
                    return this.CREATE_SCENARIO_EVENTS_SELECT_CIV;
                }
                case eCREATE_SCENARIO_EVENTS_ADD_CIV: {
                    if (this.CREATE_SCENARIO_EVENTS_ADD_CIV == -1) {
                        this.CREATE_SCENARIO_EVENTS_ADD_CIV = this.addMenu(new Menu_CreateScenario_Events_AddCiv());
                        this.CREATE_SCENARIO_EVENTS_ADD_CIV_ALPHABET = this.addNextMenuToView(this.CREATE_SCENARIO_EVENTS_ADD_CIV, new Menu_CreateScenario_Events_AddCiv_Alphabet());
                        this.CREATE_SCENARIO_EVENTS_ADD_CIV_LIST = this.addNextMenuToView(this.CREATE_SCENARIO_EVENTS_ADD_CIV, new Menu_CreateScenario_Events_AddCiv_List());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_ADD_CIV).set(this.CREATE_SCENARIO_EVENTS_ADD_CIV_ALPHABET, new Menu_CreateScenario_Events_AddCiv_Alphabet());
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_ADD_CIV).get(this.CREATE_SCENARIO_EVENTS_ADD_CIV_LIST).onBackPressed();
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_ADD_CIV).set(this.CREATE_SCENARIO_EVENTS_ADD_CIV_LIST, new Menu_CreateScenario_Events_AddCiv_List());
                    }
                    return this.CREATE_SCENARIO_EVENTS_ADD_CIV;
                }
                case eCREATE_SCENARIO_EVENTS_TRIGGER: {
                    if (this.CREATE_SCENARIO_EVENTS_TRIGGER == -1) {
                        this.CREATE_SCENARIO_EVENTS_TRIGGER = this.addMenu(new Menu_CreateScenario_Events_Trigger());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_TRIGGER).set(0, new Menu_CreateScenario_Events_Trigger());
                    }
                    return this.CREATE_SCENARIO_EVENTS_TRIGGER;
                }
                case eCREATE_SCENARIO_EVENTS_ADD_NEW_CONDITION: {
                    if (this.CREATE_SCENARIO_EVENTS_ADD_NEW_CONDITION == -1) {
                        this.CREATE_SCENARIO_EVENTS_ADD_NEW_CONDITION = this.addMenu(new Menu_CreateScenario_Events_AddNewCondition());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_ADD_NEW_CONDITION).set(0, new Menu_CreateScenario_Events_AddNewCondition());
                    }
                    return this.CREATE_SCENARIO_EVENTS_ADD_NEW_CONDITION;
                }
                case eCREATE_SCENARIO_EVENTS_ADD_NEW_OUTCOME: {
                    if (this.CREATE_SCENARIO_EVENTS_ADD_NEW_OUTCOME == -1) {
                        this.CREATE_SCENARIO_EVENTS_ADD_NEW_OUTCOME = this.addMenu(new Menu_CreateScenario_Events_AddNewOutcome());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_ADD_NEW_OUTCOME).set(0, new Menu_CreateScenario_Events_AddNewOutcome());
                    }
                    return this.CREATE_SCENARIO_EVENTS_ADD_NEW_OUTCOME;
                }
                case eCREATE_SCENARIO_EVENTS_COND_CIVEXIST: {
                    if (this.CREATE_SCENARIO_EVENTS_COND_CIVEXIST == -1) {
                        this.CREATE_SCENARIO_EVENTS_COND_CIVEXIST = this.addMenu(new Menu_CreateScenario_Events_Cond_CivExist());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_COND_CIVEXIST).set(0, new Menu_CreateScenario_Events_Cond_CivExist());
                    }
                    return this.CREATE_SCENARIO_EVENTS_COND_CIVEXIST;
                }
                case eCREATE_SCENARIO_EVENTS_COND_ISPARTOFHRE: {
                    if (this.CREATE_SCENARIO_EVENTS_COND_ISPARTOFHRE == -1) {
                        this.CREATE_SCENARIO_EVENTS_COND_ISPARTOFHRE = this.addMenu(new Menu_CreateScenario_Events_Cond_IsPartOfHRE());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_COND_ISPARTOFHRE).set(0, new Menu_CreateScenario_Events_Cond_IsPartOfHRE());
                    }
                    return this.CREATE_SCENARIO_EVENTS_COND_ISPARTOFHRE;
                }
                case eCREATE_SCENARIO_EVENTS_COND_IDEOLOGY: {
                    if (this.CREATE_SCENARIO_EVENTS_COND_IDEOLOGY == -1) {
                        this.CREATE_SCENARIO_EVENTS_COND_IDEOLOGY = this.addMenu(new Menu_CreateScenario_Events_Cond_Ideology());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_COND_IDEOLOGY).set(0, new Menu_CreateScenario_Events_Cond_Ideology());
                    }
                    return this.CREATE_SCENARIO_EVENTS_COND_IDEOLOGY;
                }
                case eCREATE_SCENARIO_EVENTS_COND_TECHNOLOGY: {
                    if (this.CREATE_SCENARIO_EVENTS_COND_TECHNOLOGY == -1) {
                        this.CREATE_SCENARIO_EVENTS_COND_TECHNOLOGY = this.addMenu(new Menu_CreateScenario_Events_Cond_Technology());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_COND_TECHNOLOGY).set(0, new Menu_CreateScenario_Events_Cond_Technology());
                    }
                    return this.CREATE_SCENARIO_EVENTS_COND_TECHNOLOGY;
                }
                case eCREATE_SCENARIO_EVENTS_COND_TECHNOLOGY_LOW: {
                    if (this.CREATE_SCENARIO_EVENTS_COND_TECHNOLOGY_LOW == -1) {
                        this.CREATE_SCENARIO_EVENTS_COND_TECHNOLOGY_LOW = this.addMenu(new Menu_CreateScenario_Events_Cond_Technology_Low());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_COND_TECHNOLOGY_LOW).set(0, new Menu_CreateScenario_Events_Cond_Technology_Low());
                    }
                    return this.CREATE_SCENARIO_EVENTS_COND_TECHNOLOGY_LOW;
                }
                case eCREATE_SCENARIO_EVENTS_COND_HAPPINESS: {
                    if (this.CREATE_SCENARIO_EVENTS_COND_HAPPINESS == -1) {
                        this.CREATE_SCENARIO_EVENTS_COND_HAPPINESS = this.addMenu(new Menu_CreateScenario_Events_Cond_Happiness());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_COND_HAPPINESS).set(0, new Menu_CreateScenario_Events_Cond_Happiness());
                    }
                    return this.CREATE_SCENARIO_EVENTS_COND_HAPPINESS;
                }
                case eCREATE_SCENARIO_EVENTS_COND_HAPPINESS_LOW: {
                    if (this.CREATE_SCENARIO_EVENTS_COND_HAPPINESS_LOW == -1) {
                        this.CREATE_SCENARIO_EVENTS_COND_HAPPINESS_LOW = this.addMenu(new Menu_CreateScenario_Events_Cond_Happiness_Low());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_COND_HAPPINESS_LOW).set(0, new Menu_CreateScenario_Events_Cond_Happiness_Low());
                    }
                    return this.CREATE_SCENARIO_EVENTS_COND_HAPPINESS_LOW;
                }
                case eCREATE_SCENARIO_EVENTS_COND_CONTROLLEDBYPLAYER: {
                    if (this.CREATE_SCENARIO_EVENTS_COND_CONTROLLEDBYPLAYER == -1) {
                        this.CREATE_SCENARIO_EVENTS_COND_CONTROLLEDBYPLAYER = this.addMenu(new Menu_CreateScenario_Events_Cond_ControlledByPlayer());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_COND_CONTROLLEDBYPLAYER).set(0, new Menu_CreateScenario_Events_Cond_ControlledByPlayer());
                    }
                    return this.CREATE_SCENARIO_EVENTS_COND_CONTROLLEDBYPLAYER;
                }
                case eCREATE_SCENARIO_EVENTS_COND_NEUTRAL: {
                    if (this.CREATE_SCENARIO_EVENTS_COND_NEUTRAL == -1) {
                        this.CREATE_SCENARIO_EVENTS_COND_NEUTRAL = this.addMenu(new Menu_CreateScenario_Events_Cond_Neutral());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_COND_NEUTRAL).set(0, new Menu_CreateScenario_Events_Cond_Neutral());
                    }
                    return this.CREATE_SCENARIO_EVENTS_COND_NEUTRAL;
                }
                case eCREATE_SCENARIO_EVENTS_COND_FARM: {
                    if (this.CREATE_SCENARIO_EVENTS_COND_FARM == -1) {
                        this.CREATE_SCENARIO_EVENTS_COND_FARM = this.addMenu(new Menu_CreateScenario_Events_Cond_Farm());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_COND_FARM).set(0, new Menu_CreateScenario_Events_Cond_Farm());
                    }
                    return this.CREATE_SCENARIO_EVENTS_COND_FARM;
                }
                case eCREATE_SCENARIO_EVENTS_COND_FORT: {
                    if (this.CREATE_SCENARIO_EVENTS_COND_FORT == -1) {
                        this.CREATE_SCENARIO_EVENTS_COND_FORT = this.addMenu(new Menu_CreateScenario_Events_Cond_Fort());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_COND_FORT).set(0, new Menu_CreateScenario_Events_Cond_Fort());
                    }
                    return this.CREATE_SCENARIO_EVENTS_COND_FORT;
                }
                case eCREATE_SCENARIO_EVENTS_COND_PORT: {
                    if (this.CREATE_SCENARIO_EVENTS_COND_PORT == -1) {
                        this.CREATE_SCENARIO_EVENTS_COND_PORT = this.addMenu(new Menu_CreateScenario_Events_Cond_Port());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_COND_PORT).set(0, new Menu_CreateScenario_Events_Cond_Port());
                    }
                    return this.CREATE_SCENARIO_EVENTS_COND_PORT;
                }
                case eCREATE_SCENARIO_EVENTS_COND_WATCHTOWER: {
                    if (this.CREATE_SCENARIO_EVENTS_COND_WATCHTOWER == -1) {
                        this.CREATE_SCENARIO_EVENTS_COND_WATCHTOWER = this.addMenu(new Menu_CreateScenario_Events_Cond_WatchTower());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_COND_WATCHTOWER).set(0, new Menu_CreateScenario_Events_Cond_WatchTower());
                    }
                    return this.CREATE_SCENARIO_EVENTS_COND_WATCHTOWER;
                }
                case eCREATE_SCENARIO_EVENTS_COND_WASTELAND: {
                    if (this.CREATE_SCENARIO_EVENTS_COND_WASTELAND == -1) {
                        this.CREATE_SCENARIO_EVENTS_COND_WASTELAND = this.addMenu(new Menu_CreateScenario_Events_Cond_Wasteland());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_COND_WASTELAND).set(0, new Menu_CreateScenario_Events_Cond_Wasteland());
                    }
                    return this.CREATE_SCENARIO_EVENTS_COND_WASTELAND;
                }
                case eCREATE_SCENARIO_EVENTS_COND_TREASURY: {
                    if (this.CREATE_SCENARIO_EVENTS_COND_TREASURY == -1) {
                        this.CREATE_SCENARIO_EVENTS_COND_TREASURY = this.addMenu(new Menu_CreateScenario_Events_Cond_Treasury());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_COND_TREASURY).set(0, new Menu_CreateScenario_Events_Cond_Treasury());
                    }
                    return this.CREATE_SCENARIO_EVENTS_COND_TREASURY;
                }
                case eCREATE_SCENARIO_EVENTS_COND_TREASURY_LOW: {
                    if (this.CREATE_SCENARIO_EVENTS_COND_TREASURY_LOW == -1) {
                        this.CREATE_SCENARIO_EVENTS_COND_TREASURY_LOW = this.addMenu(new Menu_CreateScenario_Events_Cond_Treasury_Low());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_COND_TREASURY_LOW).set(0, new Menu_CreateScenario_Events_Cond_Treasury_Low());
                    }
                    return this.CREATE_SCENARIO_EVENTS_COND_TREASURY_LOW;
                }
                case eCREATE_SCENARIO_EVENTS_COND_DEVELOPMENT: {
                    if (this.CREATE_SCENARIO_EVENTS_COND_DEVELOPMENT == -1) {
                        this.CREATE_SCENARIO_EVENTS_COND_DEVELOPMENT = this.addMenu(new Menu_CreateScenario_Events_Cond_Development());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_COND_DEVELOPMENT).set(0, new Menu_CreateScenario_Events_Cond_Development());
                    }
                    return this.CREATE_SCENARIO_EVENTS_COND_DEVELOPMENT;
                }
                case eCREATE_SCENARIO_EVENTS_COND_DEVELOPMENT_LOW: {
                    if (this.CREATE_SCENARIO_EVENTS_COND_DEVELOPMENT_LOW == -1) {
                        this.CREATE_SCENARIO_EVENTS_COND_DEVELOPMENT_LOW = this.addMenu(new Menu_CreateScenario_Events_Cond_Development_Low());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_COND_DEVELOPMENT_LOW).set(0, new Menu_CreateScenario_Events_Cond_Development_Low());
                    }
                    return this.CREATE_SCENARIO_EVENTS_COND_DEVELOPMENT_LOW;
                }
                case eCREATE_SCENARIO_EVENTS_COND_CONTROLS_PROVINCES: {
                    if (this.CREATE_SCENARIO_EVENTS_COND_CONTROLSPROVINCES == -1) {
                        this.CREATE_SCENARIO_EVENTS_COND_CONTROLSPROVINCES = this.addMenu(new Menu_CreateScenario_Events_Cond_ControlsProvinces());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_COND_CONTROLSPROVINCES).set(0, new Menu_CreateScenario_Events_Cond_ControlsProvinces());
                    }
                    return this.CREATE_SCENARIO_EVENTS_COND_CONTROLSPROVINCES;
                }
                case eCREATE_SCENARIO_EVENTS_COND_OCCUPIED_PROVINCES: {
                    if (this.CREATE_SCENARIO_EVENTS_COND_OCCUPIED_PROVINCES == -1) {
                        this.CREATE_SCENARIO_EVENTS_COND_OCCUPIED_PROVINCES = this.addMenu(new Menu_CreateScenario_Events_Cond_OccupiedProvinces());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_COND_OCCUPIED_PROVINCES).set(0, new Menu_CreateScenario_Events_Cond_OccupiedProvinces());
                    }
                    return this.CREATE_SCENARIO_EVENTS_COND_OCCUPIED_PROVINCES;
                }
                case eCREATE_SCENARIO_EVENTS_COND_HAVEARMY: {
                    if (this.CREATE_SCENARIO_EVENTS_COND_HAVEARMY == -1) {
                        this.CREATE_SCENARIO_EVENTS_COND_HAVEARMY = this.addMenu(new Menu_CreateScenario_Events_Cond_HaveArmy());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_COND_HAVEARMY).set(0, new Menu_CreateScenario_Events_Cond_HaveArmy());
                    }
                    return this.CREATE_SCENARIO_EVENTS_COND_HAVEARMY;
                }
                case eCREATE_SCENARIO_EVENTS_COND_HAVECORE: {
                    if (this.CREATE_SCENARIO_EVENTS_COND_HAVECORE == -1) {
                        this.CREATE_SCENARIO_EVENTS_COND_HAVECORE = this.addMenu(new Menu_CreateScenario_Events_Cond_HaveCore());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_COND_HAVECORE).set(0, new Menu_CreateScenario_Events_Cond_HaveCore());
                    }
                    return this.CREATE_SCENARIO_EVENTS_COND_HAVECORE;
                }
                case eCREATE_SCENARIO_EVENTS_COND_ISCAPITAL: {
                    if (this.CREATE_SCENARIO_EVENTS_COND_ISCAPITAL == -1) {
                        this.CREATE_SCENARIO_EVENTS_COND_ISCAPITAL = this.addMenu(new Menu_CreateScenario_Events_Cond_IsCapital());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_COND_ISCAPITAL).set(0, new Menu_CreateScenario_Events_Cond_IsCapital());
                    }
                    return this.CREATE_SCENARIO_EVENTS_COND_ISCAPITAL;
                }
                case eCREATE_SCENARIO_EVENTS_COND_NUMOFPROVINCES: {
                    if (this.CREATE_SCENARIO_EVENTS_COND_NUMOFPROVINCES == -1) {
                        this.CREATE_SCENARIO_EVENTS_COND_NUMOFPROVINCES = this.addMenu(new Menu_CreateScenario_Events_Cond_NumOfProvinces());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_COND_NUMOFPROVINCES).set(0, new Menu_CreateScenario_Events_Cond_NumOfProvinces());
                    }
                    return this.CREATE_SCENARIO_EVENTS_COND_NUMOFPROVINCES;
                }
                case eCREATE_SCENARIO_EVENTS_COND_NUMOFPROVINCES_LOW: {
                    if (this.CREATE_SCENARIO_EVENTS_COND_NUMOFPROVINCES_LOW == -1) {
                        this.CREATE_SCENARIO_EVENTS_COND_NUMOFPROVINCES_LOW = this.addMenu(new Menu_CreateScenario_Events_Cond_NumOfProvinces_Low());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_COND_NUMOFPROVINCES_LOW).set(0, new Menu_CreateScenario_Events_Cond_NumOfProvinces_Low());
                    }
                    return this.CREATE_SCENARIO_EVENTS_COND_NUMOFPROVINCES_LOW;
                }
                case eCREATE_SCENARIO_EVENTS_COND_NUMOFUNITS: {
                    if (this.CREATE_SCENARIO_EVENTS_COND_NUMOFUNITS == -1) {
                        this.CREATE_SCENARIO_EVENTS_COND_NUMOFUNITS = this.addMenu(new Menu_CreateScenario_Events_Cond_NumOfUnits());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_COND_NUMOFUNITS).set(0, new Menu_CreateScenario_Events_Cond_NumOfUnits());
                    }
                    return this.CREATE_SCENARIO_EVENTS_COND_NUMOFUNITS;
                }
                case eCREATE_SCENARIO_EVENTS_COND_NUMOFUNITS_LOW: {
                    if (this.CREATE_SCENARIO_EVENTS_COND_NUMOFUNITS_LOW == -1) {
                        this.CREATE_SCENARIO_EVENTS_COND_NUMOFUNITS_LOW = this.addMenu(new Menu_CreateScenario_Events_Cond_NumOfUnits_Low());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_COND_NUMOFUNITS_LOW).set(0, new Menu_CreateScenario_Events_Cond_NumOfUnits_Low());
                    }
                    return this.CREATE_SCENARIO_EVENTS_COND_NUMOFUNITS_LOW;
                }
                case eCREATE_SCENARIO_EVENTS_COND_NUMOFWARS: {
                    if (this.CREATE_SCENARIO_EVENTS_COND_NUMOFWARS == -1) {
                        this.CREATE_SCENARIO_EVENTS_COND_NUMOFWARS = this.addMenu(new Menu_CreateScenario_Events_Cond_NumOfWars());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_COND_NUMOFWARS).set(0, new Menu_CreateScenario_Events_Cond_NumOfWars());
                    }
                    return this.CREATE_SCENARIO_EVENTS_COND_NUMOFWARS;
                }
                case eCREATE_SCENARIO_EVENTS_COND_NUMOFWARS_LOW: {
                    if (this.CREATE_SCENARIO_EVENTS_COND_NUMOFWARS_LOW == -1) {
                        this.CREATE_SCENARIO_EVENTS_COND_NUMOFWARS_LOW = this.addMenu(new Menu_CreateScenario_Events_Cond_NumOfWars_Low());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_COND_NUMOFWARS_LOW).set(0, new Menu_CreateScenario_Events_Cond_NumOfWars_Low());
                    }
                    return this.CREATE_SCENARIO_EVENTS_COND_NUMOFWARS_LOW;
                }
                case eCREATE_SCENARIO_EVENTS_COND_NUMOFNEIGHBORS: {
                    if (this.CREATE_SCENARIO_EVENTS_COND_NUMOFNEIGHBORS == -1) {
                        this.CREATE_SCENARIO_EVENTS_COND_NUMOFNEIGHBORS = this.addMenu(new Menu_CreateScenario_Events_Cond_NumOfNeighbors());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_COND_NUMOFNEIGHBORS).set(0, new Menu_CreateScenario_Events_Cond_NumOfNeighbors());
                    }
                    return this.CREATE_SCENARIO_EVENTS_COND_NUMOFNEIGHBORS;
                }
                case eCREATE_SCENARIO_EVENTS_COND_NUMOFNEIGHBORS_LOW: {
                    if (this.CREATE_SCENARIO_EVENTS_COND_NUMOFNEIGHBORS_LOW == -1) {
                        this.CREATE_SCENARIO_EVENTS_COND_NUMOFNEIGHBORS_LOW = this.addMenu(new Menu_CreateScenario_Events_Cond_NumOfNeighbors_Low());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_COND_NUMOFNEIGHBORS_LOW).set(0, new Menu_CreateScenario_Events_Cond_NumOfNeighbors_Low());
                    }
                    return this.CREATE_SCENARIO_EVENTS_COND_NUMOFNEIGHBORS_LOW;
                }
                case eCREATE_SCENARIO_EVENTS_COND_POPULATION: {
                    if (this.CREATE_SCENARIO_EVENTS_COND_POPULATION == -1) {
                        this.CREATE_SCENARIO_EVENTS_COND_POPULATION = this.addMenu(new Menu_CreateScenario_Events_Cond_Population());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_COND_POPULATION).set(0, new Menu_CreateScenario_Events_Cond_Population());
                    }
                    return this.CREATE_SCENARIO_EVENTS_COND_POPULATION;
                }
                case eCREATE_SCENARIO_EVENTS_COND_POPULATION_LOW: {
                    if (this.CREATE_SCENARIO_EVENTS_COND_POPULATION_LOW == -1) {
                        this.CREATE_SCENARIO_EVENTS_COND_POPULATION_LOW = this.addMenu(new Menu_CreateScenario_Events_Cond_Population_Low());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_COND_POPULATION_LOW).set(0, new Menu_CreateScenario_Events_Cond_Population_Low());
                    }
                    return this.CREATE_SCENARIO_EVENTS_COND_POPULATION_LOW;
                }
                case eCREATE_SCENARIO_EVENTS_COND_ECONOMY: {
                    if (this.CREATE_SCENARIO_EVENTS_COND_ECONOMY == -1) {
                        this.CREATE_SCENARIO_EVENTS_COND_ECONOMY = this.addMenu(new Menu_CreateScenario_Events_Cond_Economy());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_COND_ECONOMY).set(0, new Menu_CreateScenario_Events_Cond_Economy());
                    }
                    return this.CREATE_SCENARIO_EVENTS_COND_ECONOMY;
                }
                case eCREATE_SCENARIO_EVENTS_COND_ECONOMY_LOW: {
                    if (this.CREATE_SCENARIO_EVENTS_COND_ECONOMY_LOW == -1) {
                        this.CREATE_SCENARIO_EVENTS_COND_ECONOMY_LOW = this.addMenu(new Menu_CreateScenario_Events_Cond_Economy_Low());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_COND_ECONOMY_LOW).set(0, new Menu_CreateScenario_Events_Cond_Economy_Low());
                    }
                    return this.CREATE_SCENARIO_EVENTS_COND_ECONOMY_LOW;
                }
                case eCREATE_SCENARIO_EVENTS_COND_ISATWAR: {
                    if (this.CREATE_SCENARIO_EVENTS_COND_ISATWAR == -1) {
                        this.CREATE_SCENARIO_EVENTS_COND_ISATWAR = this.addMenu(new Menu_CreateScenario_Events_Cond_IsAtWar());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_COND_ISATWAR).set(0, new Menu_CreateScenario_Events_Cond_IsAtWar());
                    }
                    return this.CREATE_SCENARIO_EVENTS_COND_ISATWAR;
                }
                case eCREATE_SCENARIO_EVENTS_COND_ALLIES: {
                    if (this.CREATE_SCENARIO_EVENTS_COND_ALLIES == -1) {
                        this.CREATE_SCENARIO_EVENTS_COND_ALLIES = this.addMenu(new Menu_CreateScenario_Events_Cond_Allies());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_COND_ALLIES).set(0, new Menu_CreateScenario_Events_Cond_Allies());
                    }
                    return this.CREATE_SCENARIO_EVENTS_COND_ALLIES;
                }
                case eCREATE_SCENARIO_EVENTS_COND_ATWAR: {
                    if (this.CREATE_SCENARIO_EVENTS_COND_ATWAR == -1) {
                        this.CREATE_SCENARIO_EVENTS_COND_ATWAR = this.addMenu(new Menu_CreateScenario_Events_Cond_AtWar());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_COND_ATWAR).set(0, new Menu_CreateScenario_Events_Cond_AtWar());
                    }
                    return this.CREATE_SCENARIO_EVENTS_COND_ATWAR;
                }
                case eCREATE_SCENARIO_EVENTS_COND_INDEPENDENCE: {
                    if (this.CREATE_SCENARIO_EVENTS_COND_INDEPENDENCE == -1) {
                        this.CREATE_SCENARIO_EVENTS_COND_INDEPENDENCE = this.addMenu(new Menu_CreateScenario_Events_Cond_Independence());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_COND_INDEPENDENCE).set(0, new Menu_CreateScenario_Events_Cond_Independence());
                    }
                    return this.CREATE_SCENARIO_EVENTS_COND_INDEPENDENCE;
                }
                case eCREATE_SCENARIO_EVENTS_COND_DEFENSIVE: {
                    if (this.CREATE_SCENARIO_EVENTS_COND_DEFENSIVE == -1) {
                        this.CREATE_SCENARIO_EVENTS_COND_DEFENSIVE = this.addMenu(new Menu_CreateScenario_Events_Cond_Defensive());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_COND_DEFENSIVE).set(0, new Menu_CreateScenario_Events_Cond_Defensive());
                    }
                    return this.CREATE_SCENARIO_EVENTS_COND_DEFENSIVE;
                }
                case eCREATE_SCENARIO_EVENTS_COND_MILITARYACCESS: {
                    if (this.CREATE_SCENARIO_EVENTS_COND_MILITARYACCESS == -1) {
                        this.CREATE_SCENARIO_EVENTS_COND_MILITARYACCESS = this.addMenu(new Menu_CreateScenario_Events_Cond_MilitaryAccess());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_COND_MILITARYACCESS).set(0, new Menu_CreateScenario_Events_Cond_MilitaryAccess());
                    }
                    return this.CREATE_SCENARIO_EVENTS_COND_MILITARYACCESS;
                }
                case eCREATE_SCENARIO_EVENTS_COND_NONAGGRESSION: {
                    if (this.CREATE_SCENARIO_EVENTS_COND_NONAGGRESSION == -1) {
                        this.CREATE_SCENARIO_EVENTS_COND_NONAGGRESSION = this.addMenu(new Menu_CreateScenario_Events_Cond_NonAggreession());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_COND_NONAGGRESSION).set(0, new Menu_CreateScenario_Events_Cond_NonAggreession());
                    }
                    return this.CREATE_SCENARIO_EVENTS_COND_NONAGGRESSION;
                }
                case eCREATE_SCENARIO_EVENTS_COND_ISVASSAL: {
                    if (this.CREATE_SCENARIO_EVENTS_COND_ISVASSAL == -1) {
                        this.CREATE_SCENARIO_EVENTS_COND_ISVASSAL = this.addMenu(new Menu_CreateScenario_Events_Cond_IsVassal());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_COND_ISVASSAL).set(0, new Menu_CreateScenario_Events_Cond_IsVassal());
                    }
                    return this.CREATE_SCENARIO_EVENTS_COND_ISVASSAL;
                }
                case eCREATE_SCENARIO_EVENTS_COND_ISVASSAL_OFCIV: {
                    if (this.CREATE_SCENARIO_EVENTS_COND_ISVASSAL_OFCIV == -1) {
                        this.CREATE_SCENARIO_EVENTS_COND_ISVASSAL_OFCIV = this.addMenu(new Menu_CreateScenario_Events_Cond_IsVassal_OfCiv());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_COND_ISVASSAL_OFCIV).set(0, new Menu_CreateScenario_Events_Cond_IsVassal_OfCiv());
                    }
                    return this.CREATE_SCENARIO_EVENTS_COND_ISVASSAL_OFCIV;
                }
                case eCREATE_SCENARIO_EVENTS_COND_RELATION: {
                    if (this.CREATE_SCENARIO_EVENTS_COND_RELATION == -1) {
                        this.CREATE_SCENARIO_EVENTS_COND_RELATION = this.addMenu(new Menu_CreateScenario_Events_Cond_Relation());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_COND_RELATION).set(0, new Menu_CreateScenario_Events_Cond_Relation());
                    }
                    return this.CREATE_SCENARIO_EVENTS_COND_RELATION;
                }
                case eCREATE_SCENARIO_EVENTS_COND_RELATION_LOW: {
                    if (this.CREATE_SCENARIO_EVENTS_COND_RELATION_LOW == -1) {
                        this.CREATE_SCENARIO_EVENTS_COND_RELATION_LOW = this.addMenu(new Menu_CreateScenario_Events_Cond_Relation_Low());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_COND_RELATION_LOW).set(0, new Menu_CreateScenario_Events_Cond_Relation_Low());
                    }
                    return this.CREATE_SCENARIO_EVENTS_COND_RELATION_LOW;
                }
                case eCREATE_SCENARIO_EVENTS_COND_NUMOFALLIES: {
                    if (this.CREATE_SCENARIO_EVENTS_COND_NUMOFALLIES == -1) {
                        this.CREATE_SCENARIO_EVENTS_COND_NUMOFALLIES = this.addMenu(new Menu_CreateScenario_Events_Cond_NumOfAllies());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_COND_NUMOFALLIES).set(0, new Menu_CreateScenario_Events_Cond_NumOfAllies());
                    }
                    return this.CREATE_SCENARIO_EVENTS_COND_NUMOFALLIES;
                }
                case eCREATE_SCENARIO_EVENTS_COND_NUMOFALLIES_LOW: {
                    if (this.CREATE_SCENARIO_EVENTS_COND_NUMOFALLIES_LOW == -1) {
                        this.CREATE_SCENARIO_EVENTS_COND_NUMOFALLIES_LOW = this.addMenu(new Menu_CreateScenario_Events_Cond_NumOfAllies_Low());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_COND_NUMOFALLIES_LOW).set(0, new Menu_CreateScenario_Events_Cond_NumOfAllies_Low());
                    }
                    return this.CREATE_SCENARIO_EVENTS_COND_NUMOFALLIES_LOW;
                }
                case eCREATE_SCENARIO_EVENTS_COND_NUMOFVASSALS: {
                    if (this.CREATE_SCENARIO_EVENTS_COND_NUMOFVASSALS == -1) {
                        this.CREATE_SCENARIO_EVENTS_COND_NUMOFVASSALS = this.addMenu(new Menu_CreateScenario_Events_Cond_NumOfVassals());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_COND_NUMOFVASSALS).set(0, new Menu_CreateScenario_Events_Cond_NumOfVassals());
                    }
                    return this.CREATE_SCENARIO_EVENTS_COND_NUMOFVASSALS;
                }
                case eCREATE_SCENARIO_EVENTS_COND_NUMOFVASSALS_LOW: {
                    if (this.CREATE_SCENARIO_EVENTS_COND_NUMOFVASSALS_LOW == -1) {
                        this.CREATE_SCENARIO_EVENTS_COND_NUMOFVASSALS_LOW = this.addMenu(new Menu_CreateScenario_Events_Cond_NumOfVassals_Low());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_COND_NUMOFVASSALS_LOW).set(0, new Menu_CreateScenario_Events_Cond_NumOfVassals_Low());
                    }
                    return this.CREATE_SCENARIO_EVENTS_COND_NUMOFVASSALS_LOW;
                }
                case eCREATE_SCENARIO_EVENTS_COND_EVENTCHANCE: {
                    if (this.CREATE_SCENARIO_EVENTS_COND_EVENTCHANCE == -1) {
                        this.CREATE_SCENARIO_EVENTS_COND_EVENTCHANCE = this.addMenu(new Menu_CreateScenario_Events_Cond_EventChance());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_COND_EVENTCHANCE).set(0, new Menu_CreateScenario_Events_Cond_EventChance());
                    }
                    return this.CREATE_SCENARIO_EVENTS_COND_EVENTCHANCE;
                }
                case eCREATE_SCENARIO_EVENTS_COND_DECISIONTAKEN: {
                    if (this.CREATE_SCENARIO_EVENTS_COND_DECISIONTAKEN == -1) {
                        this.CREATE_SCENARIO_EVENTS_COND_DECISIONTAKEN = this.addMenu(new Menu_CreateScenario_Events_Cond_DecisionTaken());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_COND_DECISIONTAKEN).set(0, new Menu_CreateScenario_Events_Cond_DecisionTaken());
                    }
                    return this.CREATE_SCENARIO_EVENTS_COND_DECISIONTAKEN;
                }
                case eCREATE_SCENARIO_EVENTS_OUT_CHANGE_OWNER: {
                    if (this.CREATE_SCENARIO_EVENTS_OUT_CHANGE_OWNER == -1) {
                        this.CREATE_SCENARIO_EVENTS_OUT_CHANGE_OWNER = this.addMenu(new Menu_CreateScenario_Events_Out_ChangeOwner());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_OUT_CHANGE_OWNER).set(0, new Menu_CreateScenario_Events_Out_ChangeOwner());
                    }
                    return this.CREATE_SCENARIO_EVENTS_OUT_CHANGE_OWNER;
                }
                case eCREATE_SCENARIO_EVENTS_OUT_OCCUPY: {
                    if (this.CREATE_SCENARIO_EVENTS_OUT_OCCUPY == -1) {
                        this.CREATE_SCENARIO_EVENTS_OUT_OCCUPY = this.addMenu(new Menu_CreateScenario_Events_Out_Occupy());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_OUT_OCCUPY).set(0, new Menu_CreateScenario_Events_Out_Occupy());
                    }
                    return this.CREATE_SCENARIO_EVENTS_OUT_OCCUPY;
                }
                case eCREATE_SCENARIO_EVENTS_SELECTDECISION: {
                    if (this.CREATE_SCENARIO_EVENTS_SELECTDECISION == -1) {
                        this.CREATE_SCENARIO_EVENTS_SELECTDECISION = this.addMenu(new Menu_CreateScenario_Events_SelectDecision());
                        this.addNextMenuToView(this.CREATE_SCENARIO_EVENTS_SELECTDECISION, new Menu_CreateScenario_Events_SelectDecision_List());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_SELECTDECISION).set(1, new Menu_CreateScenario_Events_SelectDecision_List());
                    }
                    return this.CREATE_SCENARIO_EVENTS_SELECTDECISION;
                }
                case eCREATE_SCENARIO_EVENTS_OUT_ADDCORE: {
                    if (this.CREATE_SCENARIO_EVENTS_OUT_ADDCORE == -1) {
                        this.CREATE_SCENARIO_EVENTS_OUT_ADDCORE = this.addMenu(new Menu_CreateScenario_Events_Out_AddCore());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_OUT_ADDCORE).set(0, new Menu_CreateScenario_Events_Out_AddCore());
                    }
                    return this.CREATE_SCENARIO_EVENTS_OUT_ADDCORE;
                }
                case eCREATE_SCENARIO_EVENTS_OUT_REMOVECORE: {
                    if (this.CREATE_SCENARIO_EVENTS_OUT_REMOVECORE == -1) {
                        this.CREATE_SCENARIO_EVENTS_OUT_REMOVECORE = this.addMenu(new Menu_CreateScenario_Events_Out_RemoveCore());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_OUT_REMOVECORE).set(0, new Menu_CreateScenario_Events_Out_AddCore());
                    }
                    return this.CREATE_SCENARIO_EVENTS_OUT_REMOVECORE;
                }
                case eCREATE_SCENARIO_EVENTS_OUT_DECLAREWAR: {
                    if (this.CREATE_SCENARIO_EVENTS_OUT_DECLAREWAR == -1) {
                        this.CREATE_SCENARIO_EVENTS_OUT_DECLAREWAR = this.addMenu(new Menu_CreateScenario_Events_Out_DeclareWar());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_OUT_DECLAREWAR).set(0, new Menu_CreateScenario_Events_Out_DeclareWar());
                    }
                    return this.CREATE_SCENARIO_EVENTS_OUT_DECLAREWAR;
                }
                case eCREATE_SCENARIO_EVENTS_OUT_WHITEPEACE: {
                    if (this.CREATE_SCENARIO_EVENTS_OUT_WHITEPEACE == -1) {
                        this.CREATE_SCENARIO_EVENTS_OUT_WHITEPEACE = this.addMenu(new Menu_CreateScenario_Events_Out_WhitePeace());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_OUT_WHITEPEACE).set(0, new Menu_CreateScenario_Events_Out_WhitePeace());
                    }
                    return this.CREATE_SCENARIO_EVENTS_OUT_WHITEPEACE;
                }
                case eCREATE_SCENARIO_EVENTS_OUT_CHANGEIDEOLOGY: {
                    if (this.CREATE_SCENARIO_EVENTS_OUT_CHANGEIDEOLOGY == -1) {
                        this.CREATE_SCENARIO_EVENTS_OUT_CHANGEIDEOLOGY = this.addMenu(new Menu_CreateScenario_Events_Out_ChangeIdeology());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_OUT_CHANGEIDEOLOGY).set(0, new Menu_CreateScenario_Events_Out_ChangeIdeology());
                    }
                    return this.CREATE_SCENARIO_EVENTS_OUT_CHANGEIDEOLOGY;
                }
                case eCREATE_SCENARIO_EVENTS_OUT_CHANGELEADER: {
                    if (this.CREATE_SCENARIO_EVENTS_OUT_CHANGELEADER == -1) {
                        this.CREATE_SCENARIO_EVENTS_OUT_CHANGELEADER = this.addMenu(new Menu_CreateScenario_Events_Out_ChangeLeader());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_OUT_CHANGELEADER).set(0, new Menu_CreateScenario_Events_Out_ChangeLeader());
                    }
                    return this.CREATE_SCENARIO_EVENTS_OUT_CHANGELEADER;
                }
                case eCREATE_SCENARIO_EVENTS_OUT_INCRELATION: {
                    if (this.CREATE_SCENARIO_EVENTS_OUT_INCRELATION == -1) {
                        this.CREATE_SCENARIO_EVENTS_OUT_INCRELATION = this.addMenu(new Menu_CreateScenario_Events_Out_IncRelation());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_OUT_INCRELATION).set(0, new Menu_CreateScenario_Events_Out_IncRelation());
                    }
                    return this.CREATE_SCENARIO_EVENTS_OUT_INCRELATION;
                }
                case eCREATE_SCENARIO_EVENTS_OUT_DECRELATION: {
                    if (this.CREATE_SCENARIO_EVENTS_OUT_DECRELATION == -1) {
                        this.CREATE_SCENARIO_EVENTS_OUT_DECRELATION = this.addMenu(new Menu_CreateScenario_Events_Out_DecRelation());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_OUT_DECRELATION).set(0, new Menu_CreateScenario_Events_Out_DecRelation());
                    }
                    return this.CREATE_SCENARIO_EVENTS_OUT_DECRELATION;
                }
                case eCREATE_SCENARIO_EVENTS_SELECTIDEOLOGY: {
                    if (this.CREATE_SCENARIO_EVENTS_SELECTIDEOLOGY == -1) {
                        this.CREATE_SCENARIO_EVENTS_SELECTIDEOLOGY = this.addMenu(new Menu_CreateScenario_Events_SelectIdeology());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_SELECTIDEOLOGY).set(0, new Menu_CreateScenario_Events_SelectIdeology());
                    }
                    return this.CREATE_SCENARIO_EVENTS_SELECTIDEOLOGY;
                }
                case eCREATE_SCENARIO_EVENTS_OUT_ADDARMY: {
                    if (this.CREATE_SCENARIO_EVENTS_OUT_ADDARMY == -1) {
                        this.CREATE_SCENARIO_EVENTS_OUT_ADDARMY = this.addMenu(new Menu_CreateScenario_Events_Out_AddArmy());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_OUT_ADDARMY).set(0, new Menu_CreateScenario_Events_Out_AddArmy());
                    }
                    return this.CREATE_SCENARIO_EVENTS_OUT_ADDARMY;
                }
                case eCREATE_SCENARIO_EVENTS_OUT_UPDATEPOPULATION: {
                    if (this.CREATE_SCENARIO_EVENTS_OUT_UPDATEPOPULATION == -1) {
                        this.CREATE_SCENARIO_EVENTS_OUT_UPDATEPOPULATION = this.addMenu(new Menu_CreateScenario_Events_Out_UpdatePopulation());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_OUT_UPDATEPOPULATION).set(0, new Menu_CreateScenario_Events_Out_UpdatePopulation());
                    }
                    return this.CREATE_SCENARIO_EVENTS_OUT_UPDATEPOPULATION;
                }
                case eCREATE_SCENARIO_EVENTS_OUT_UPDATEPOPULATION_OFCIV: {
                    if (this.CREATE_SCENARIO_EVENTS_OUT_UPDATEPOPULATION_OFCIV == -1) {
                        this.CREATE_SCENARIO_EVENTS_OUT_UPDATEPOPULATION_OFCIV = this.addMenu(new Menu_CreateScenario_Events_Out_UpdatePopulation_OfCiv());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_OUT_UPDATEPOPULATION_OFCIV).set(0, new Menu_CreateScenario_Events_Out_UpdatePopulation_OfCiv());
                    }
                    return this.CREATE_SCENARIO_EVENTS_OUT_UPDATEPOPULATION_OFCIV;
                }
                case eCREATE_SCENARIO_EVENTS_OUT_UPDATEECONOMY_OFCIV: {
                    if (this.CREATE_SCENARIO_EVENTS_OUT_UPDATEECONOMY_OFCIV == -1) {
                        this.CREATE_SCENARIO_EVENTS_OUT_UPDATEECONOMY_OFCIV = this.addMenu(new Menu_CreateScenario_Events_Out_UpdateEconomy_OfCiv());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_OUT_UPDATEECONOMY_OFCIV).set(0, new Menu_CreateScenario_Events_Out_UpdateEconomy_OfCiv());
                    }
                    return this.CREATE_SCENARIO_EVENTS_OUT_UPDATEECONOMY_OFCIV;
                }
                case eCREATE_SCENARIO_EVENTS_OUT_TECHLEVEL: {
                    if (this.CREATE_SCENARIO_EVENTS_OUT_TECHLEVEL == -1) {
                        this.CREATE_SCENARIO_EVENTS_OUT_TECHLEVEL = this.addMenu(new Menu_CreateScenario_Events_Out_TechLevel());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_OUT_TECHLEVEL).set(0, new Menu_CreateScenario_Events_Out_TechLevel());
                    }
                    return this.CREATE_SCENARIO_EVENTS_OUT_TECHLEVEL;
                }
                case eCREATE_SCENARIO_EVENTS_OUT_DEVELOPEMNT: {
                    if (this.CREATE_SCENARIO_EVENTS_OUT_DEVELOPMENT == -1) {
                        this.CREATE_SCENARIO_EVENTS_OUT_DEVELOPMENT = this.addMenu(new Menu_CreateScenario_Events_Out_Development());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_OUT_DEVELOPMENT).set(0, new Menu_CreateScenario_Events_Out_Development());
                    }
                    return this.CREATE_SCENARIO_EVENTS_OUT_DEVELOPMENT;
                }
                case eCREATE_SCENARIO_EVENTS_OUT_UPDATEECONOMY: {
                    if (this.CREATE_SCENARIO_EVENTS_OUT_UPDATEECONOMY == -1) {
                        this.CREATE_SCENARIO_EVENTS_OUT_UPDATEECONOMY = this.addMenu(new Menu_CreateScenario_Events_Out_UpdateEconomy());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_OUT_UPDATEECONOMY).set(0, new Menu_CreateScenario_Events_Out_UpdateEconomy());
                    }
                    return this.CREATE_SCENARIO_EVENTS_OUT_UPDATEECONOMY;
                }
                case eCREATE_SCENARIO_EVENTS_OUT_UPDATEECONOMYPERC: {
                    if (this.CREATE_SCENARIO_EVENTS_OUT_UPDATEECONOMY_PERC == -1) {
                        this.CREATE_SCENARIO_EVENTS_OUT_UPDATEECONOMY_PERC = this.addMenu(new Menu_CreateScenario_Events_Out_UpdateEconomyPerc());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_OUT_UPDATEECONOMY_PERC).set(0, new Menu_CreateScenario_Events_Out_UpdateEconomyPerc());
                    }
                    return this.CREATE_SCENARIO_EVENTS_OUT_UPDATEECONOMY_PERC;
                }
                case eCREATE_SCENARIO_EVENTS_OUT_HAPPINESS: {
                    if (this.CREATE_SCENARIO_EVENTS_OUT_HAPPINESS == -1) {
                        this.CREATE_SCENARIO_EVENTS_OUT_HAPPINESS = this.addMenu(new Menu_CreateScenario_Events_Out_Happiness());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_OUT_HAPPINESS).set(0, new Menu_CreateScenario_Events_Out_Happiness());
                    }
                    return this.CREATE_SCENARIO_EVENTS_OUT_HAPPINESS;
                }
                case eCREATE_SCENARIO_EVENTS_OUT_HAPPINESS_OF_CIV: {
                    if (this.CREATE_SCENARIO_EVENTS_OUT_HAPPINESS_OF_CIV == -1) {
                        this.CREATE_SCENARIO_EVENTS_OUT_HAPPINESS_OF_CIV = this.addMenu(new Menu_CreateScenario_Events_Out_Happiness_OfCiv());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_OUT_HAPPINESS_OF_CIV).set(0, new Menu_CreateScenario_Events_Out_Happiness_OfCiv());
                    }
                    return this.CREATE_SCENARIO_EVENTS_OUT_HAPPINESS_OF_CIV;
                }
                case eCREATE_SCENARIO_EVENTS_OUT_UPDATEPOPULATION_PERC: {
                    if (this.CREATE_SCENARIO_EVENTS_OUT_UPDATEPOPULATION_PERC == -1) {
                        this.CREATE_SCENARIO_EVENTS_OUT_UPDATEPOPULATION_PERC = this.addMenu(new Menu_CreateScenario_Events_Out_UpdatePopulation_Perc());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_OUT_UPDATEPOPULATION_PERC).set(0, new Menu_CreateScenario_Events_Out_UpdatePopulation_Perc());
                    }
                    return this.CREATE_SCENARIO_EVENTS_OUT_UPDATEPOPULATION_PERC;
                }
                case eCREATE_SCENARIO_EVENTS_OUT_MONEY: {
                    if (this.CREATE_SCENARIO_EVENTS_OUT_MONEY == -1) {
                        this.CREATE_SCENARIO_EVENTS_OUT_MONEY = this.addMenu(new Menu_CreateScenario_Events_Out_Money());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_OUT_MONEY).set(0, new Menu_CreateScenario_Events_Out_Money());
                    }
                    return this.CREATE_SCENARIO_EVENTS_OUT_MONEY;
                }
                case eCREATE_SCENARIO_EVENTS_OUT_MOVEMENTPOINTS: {
                    if (this.CREATE_SCENARIO_EVENTS_OUT_MOVEMENTPOINTS == -1) {
                        this.CREATE_SCENARIO_EVENTS_OUT_MOVEMENTPOINTS = this.addMenu(new Menu_CreateScenario_Events_Out_MovementPoints());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_OUT_MOVEMENTPOINTS).set(0, new Menu_CreateScenario_Events_Out_MovementPoints());
                    }
                    return this.CREATE_SCENARIO_EVENTS_OUT_MOVEMENTPOINTS;
                }
                case eCREATE_SCENARIO_EVENTS_OUT_DIPLOMACYPOINTS: {
                    if (this.CREATE_SCENARIO_EVENTS_OUT_DIPLOMACYPOINTS == -1) {
                        this.CREATE_SCENARIO_EVENTS_OUT_DIPLOMACYPOINTS = this.addMenu(new Menu_CreateScenario_Events_Out_DiplomacyPoints());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_OUT_DIPLOMACYPOINTS).set(0, new Menu_CreateScenario_Events_Out_DiplomacyPoints());
                    }
                    return this.CREATE_SCENARIO_EVENTS_OUT_DIPLOMACYPOINTS;
                }
                case eCREATE_SCENARIO_EVENTS_OUT_WASTELAND: {
                    if (this.CREATE_SCENARIO_EVENTS_OUT_WASTELAND == -1) {
                        this.CREATE_SCENARIO_EVENTS_OUT_WASTELAND = this.addMenu(new Menu_CreateScenario_Events_Out_Wasteland());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_OUT_WASTELAND).set(0, new Menu_CreateScenario_Events_Out_Wasteland());
                    }
                    return this.CREATE_SCENARIO_EVENTS_OUT_WASTELAND;
                }
                case eCREATE_SCENARIO_EVENTS_OUT_FORM_CIV: {
                    if (this.CREATE_SCENARIO_EVENTS_OUT_FORM_CIV == -1) {
                        this.CREATE_SCENARIO_EVENTS_OUT_FORM_CIV = this.addMenu(new Menu_CreateScenario_Events_Out_FormCiv());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_OUT_FORM_CIV).set(0, new Menu_CreateScenario_Events_Out_FormCiv());
                    }
                    return this.CREATE_SCENARIO_EVENTS_OUT_FORM_CIV;
                }
                case eCREATE_SCENARIO_EVENTS_OUT_TRIGGERANOTHEREVENT: {
                    if (this.CREATE_SCENARIO_EVENTS_OUT_TRIGGERANOTHEREVENT == -1) {
                        this.CREATE_SCENARIO_EVENTS_OUT_TRIGGERANOTHEREVENT = this.addMenu(new Menu_CreateScenario_Events_Out_TriggerAnotherEvent());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_OUT_TRIGGERANOTHEREVENT).set(0, new Menu_CreateScenario_Events_Out_TriggerAnotherEvent());
                    }
                    return this.CREATE_SCENARIO_EVENTS_OUT_TRIGGERANOTHEREVENT;
                }
                case eCREATE_SCENARIO_EVENTS_OUT_SELECT_EVENT: {
                    if (this.CREATE_SCENARIO_EVENTS_SELECTEVENT == -1) {
                        this.CREATE_SCENARIO_EVENTS_SELECTEVENT = this.addMenu(new Menu_CreateScenario_Events_SelectEvent());
                        this.addNextMenuToView(this.CREATE_SCENARIO_EVENTS_SELECTEVENT, new Menu_CreateScenario_Events_SelectEvent_List());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_SELECTEVENT).set(0, new Menu_CreateScenario_Events_SelectEvent());
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_SELECTEVENT).set(1, new Menu_CreateScenario_Events_SelectEvent_List());
                    }
                    return this.CREATE_SCENARIO_EVENTS_SELECTEVENT;
                }
                case eCREATE_SCENARIO_EVENTS_OUT_SELECT_LEADER: {
                    if (this.CREATE_SCENARIO_EVENTS_OUT_SELECT_LEADER == -1) {
                        this.CREATE_SCENARIO_EVENTS_OUT_SELECT_LEADER = this.addMenu(new Menu_CreateScenario_Events_Out_Select_Leader());
                        this.addNextMenuToView(this.CREATE_SCENARIO_EVENTS_OUT_SELECT_LEADER, new Menu_CreateScenario_Events_Out_Select_Leader_List());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_OUT_SELECT_LEADER).set(0, new Menu_CreateScenario_Events_Out_Select_Leader());
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_OUT_SELECT_LEADER).set(1, new Menu_CreateScenario_Events_Out_Select_Leader_List());
                    }
                    return this.CREATE_SCENARIO_EVENTS_OUT_SELECT_LEADER;
                }
                case eCREATE_SCENARIO_EVENTS_SELECT_PROVINCES: {
                    if (this.CREATE_SCENARIO_EVENTS_SELECT_PROVINCES == -1) {
                        this.CREATE_SCENARIO_EVENTS_SELECT_PROVINCES = this.addMenu(new Menu_CreateScenario_Events_SelectProvinces());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_SELECT_PROVINCES).set(0, new Menu_CreateScenario_Events_SelectProvinces());
                    }
                    return this.CREATE_SCENARIO_EVENTS_SELECT_PROVINCES;
                }
                case eCREATE_SCENARIO_EVENTS_SELECT_AUTONOMY: {
                    if (this.CREATE_SCENARIO_EVENTS_SELECT_AUTONOMY == -1) {
                        this.CREATE_SCENARIO_EVENTS_SELECT_AUTONOMY = this.addMenu(new Menu_CreateScenario_Events_SelectAutonomy());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_SELECT_AUTONOMY).set(0, new Menu_CreateScenario_Events_SelectAutonomy());
                    }
                    return this.CREATE_SCENARIO_EVENTS_SELECT_AUTONOMY;
                }
                case eCREATE_SCENARIO_EVENTS_OUT_CREATEVASSAL: {
                    if (this.CREATE_SCENARIO_EVENTS_OUT_CREATEVASSAL == -1) {
                        this.CREATE_SCENARIO_EVENTS_OUT_CREATEVASSAL = this.addMenu(new Menu_CreateScenario_Events_Out_CreateAVassal());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_OUT_CREATEVASSAL).set(0, new Menu_CreateScenario_Events_Out_CreateAVassal());
                    }
                    return this.CREATE_SCENARIO_EVENTS_OUT_CREATEVASSAL;
                }
                case eCREATE_SCENARIO_EVENTS_OUT_JOINALLIANCE: {
                    if (this.CREATE_SCENARIO_EVENTS_OUT_JOINALLIANCE == -1) {
                        this.CREATE_SCENARIO_EVENTS_OUT_JOINALLIANCE = this.addMenu(new Menu_CreateScenario_Events_Out_JoinAlliance());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_OUT_JOINALLIANCE).set(0, new Menu_CreateScenario_Events_Out_JoinAlliance());
                    }
                    return this.CREATE_SCENARIO_EVENTS_OUT_JOINALLIANCE;
                }
                case eCREATE_SCENARIO_EVENTS_OUT_LEAVEALLIANCE: {
                    if (this.CREATE_SCENARIO_EVENTS_OUT_LEAVEALLIANCE == -1) {
                        this.CREATE_SCENARIO_EVENTS_OUT_LEAVEALLIANCE = this.addMenu(new Menu_CreateScenario_Events_Out_LeaveAlliance());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_OUT_LEAVEALLIANCE).set(0, new Menu_CreateScenario_Events_Out_LeaveAlliance());
                    }
                    return this.CREATE_SCENARIO_EVENTS_OUT_LEAVEALLIANCE;
                }
                case eCREATE_SCENARIO_EVENTS_OUT_JOINUNION: {
                    if (this.CREATE_SCENARIO_EVENTS_OUT_JOINUNION == -1) {
                        this.CREATE_SCENARIO_EVENTS_OUT_JOINUNION = this.addMenu(new Menu_CreateScenario_Events_Out_JoinUnion());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_OUT_JOINUNION).set(0, new Menu_CreateScenario_Events_Out_JoinUnion());
                    }
                    return this.CREATE_SCENARIO_EVENTS_OUT_JOINUNION;
                }
                case eCREATE_SCENARIO_EVENTS_OUT_NONAGGRESSION: {
                    if (this.CREATE_SCENARIO_EVENTS_OUT_NONAGGRESSION == -1) {
                        this.CREATE_SCENARIO_EVENTS_OUT_NONAGGRESSION = this.addMenu(new Menu_CreateScenario_Events_Out_NonAggression());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_OUT_NONAGGRESSION).set(0, new Menu_CreateScenario_Events_Out_NonAggression());
                    }
                    return this.CREATE_SCENARIO_EVENTS_OUT_NONAGGRESSION;
                }
                case eCREATE_SCENARIO_EVENTS_OUT_DEFENSIVE: {
                    if (this.CREATE_SCENARIO_EVENTS_OUT_DEFENSIVE == -1) {
                        this.CREATE_SCENARIO_EVENTS_OUT_DEFENSIVE = this.addMenu(new Menu_CreateScenario_Events_Out_Defensive());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_OUT_DEFENSIVE).set(0, new Menu_CreateScenario_Events_Out_Defensive());
                    }
                    return this.CREATE_SCENARIO_EVENTS_OUT_DEFENSIVE;
                }
                case eCREATE_SCENARIO_EVENTS_OUT_INDEPENENCE: {
                    if (this.CREATE_SCENARIO_EVENTS_OUT_INDEPENENCE == -1) {
                        this.CREATE_SCENARIO_EVENTS_OUT_INDEPENENCE = this.addMenu(new Menu_CreateScenario_Events_Out_Independence());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_OUT_INDEPENENCE).set(0, new Menu_CreateScenario_Events_Out_Independence());
                    }
                    return this.CREATE_SCENARIO_EVENTS_OUT_INDEPENENCE;
                }
                case eCREATE_SCENARIO_EVENTS_OUT_MOVECAPITAL: {
                    if (this.CREATE_SCENARIO_EVENTS_OUT_MOVECAPITAL == -1) {
                        this.CREATE_SCENARIO_EVENTS_OUT_MOVECAPITAL = this.addMenu(new Menu_CreateScenario_Events_Out_MoveCapital());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_OUT_MOVECAPITAL).set(0, new Menu_CreateScenario_Events_Out_MoveCapital());
                    }
                    return this.CREATE_SCENARIO_EVENTS_OUT_MOVECAPITAL;
                }
                case eCREATE_SCENARIO_EVENTS_OUT_LIBERATEVASSAL: {
                    if (this.CREATE_SCENARIO_EVENTS_OUT_LIBERATEVASSAL == -1) {
                        this.CREATE_SCENARIO_EVENTS_OUT_LIBERATEVASSAL = this.addMenu(new Menu_CreateScenario_Events_Out_LiberateVassal());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_OUT_LIBERATEVASSAL).set(0, new Menu_CreateScenario_Events_Out_LiberateVassal());
                    }
                    return this.CREATE_SCENARIO_EVENTS_OUT_LIBERATEVASSAL;
                }
                case eCREATE_SCENARIO_EVENTS_OUT_MILITARY: {
                    if (this.CREATE_SCENARIO_EVENTS_OUT_MILITARY == -1) {
                        this.CREATE_SCENARIO_EVENTS_OUT_MILITARY = this.addMenu(new Menu_CreateScenario_Events_Out_MilitaryAccess());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_OUT_MILITARY).set(0, new Menu_CreateScenario_Events_Out_MilitaryAccess());
                    }
                    return this.CREATE_SCENARIO_EVENTS_OUT_MILITARY;
                }
                case eCREATE_SCENARIO_ASSIGN: {
                    if (this.CREATE_SCENARIO_ASSIGN == -1) {
                        this.CREATE_SCENARIO_ASSIGN = this.addMenu(new Menu_CreateScenario_Assign());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_ASSIGN).get(0).getMenuElement(6).setClickable(false);
                    }
                    return this.CREATE_SCENARIO_ASSIGN;
                }
                case eCREATE_SCENARIO_ASSIGN_SELECT: {
                    if (this.CREATE_SCENARIO_ASSIGN_SELECT == -1) {
                        this.CREATE_SCENARIO_ASSIGN_SELECT = this.addMenu(new Menu_CreateScenario_Assign_Select());
                        this.addNextMenuToView(this.CREATE_SCENARIO_ASSIGN_SELECT, new Menu_CreateScenario_Assign_Select_Alphabet());
                        this.addNextMenuToView(this.CREATE_SCENARIO_ASSIGN_SELECT, new Menu_CreateScenario_Assign_Select_List());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_ASSIGN_SELECT).set(1, new Menu_CreateScenario_Assign_Select_Alphabet());
                        this.menus.get(this.CREATE_SCENARIO_ASSIGN_SELECT).set(2, new Menu_CreateScenario_Assign_Select_List());
                    }
                    return this.CREATE_SCENARIO_ASSIGN_SELECT;
                }
                case eCREATE_SCENARIO_WASTELAND: {
                    if (this.CREATE_SCENARIO_WASTELAND == -1) {
                        this.CREATE_SCENARIO_WASTELAND = this.addMenu(new Menu_CreateScenario_WastelandMap());
                        this.addNextMenuToView(this.CREATE_SCENARIO_WASTELAND, new Menu_CreateScenario_WastelandMap_List());
                        this.addNextMenuToView(this.CREATE_SCENARIO_WASTELAND, new Menu_CreateScenario_WastelandMap_Continents());
                    }
                    CFG.map.getMapScale().setCurrentScale(Map_Scale.MINSCALE);
                    CFG.map.getMapCoordinates().setNewPosX(-((int)((float)(CFG.map.getMapBG().getWidth() / 2) - (float)CFG.GAME_WIDTH / Map_Scale.MINSCALE / 2.0f)));
                    CFG.map.getMapCoordinates().setNewPosY(-((int)((float)(CFG.map.getMapBG().getHeight() / 2) - (float)CFG.GAME_HEIGHT / Map_Scale.MINSCALE / 2.0f)));
                    return this.CREATE_SCENARIO_WASTELAND;
                }
                case eCREATE_SCENARIO_AVAILABLE_PROVINCES: {
                    if (this.CREATE_SCENARIO_AVAILABLE_PROVINCES == -1) {
                        this.CREATE_SCENARIO_AVAILABLE_PROVINCES = this.addMenu(new Menu_CreateScenario_AvailableProvinces());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_AVAILABLE_PROVINCES).set(0, new Menu_CreateScenario_AvailableProvinces());
                    }
                    return this.CREATE_SCENARIO_AVAILABLE_PROVINCES;
                }
                case eCREATE_SCENARIO_SETTINGS: {
                    if (this.CREATE_SCENARIO_SETTINGS == -1) {
                        this.CREATE_SCENARIO_SETTINGS = this.addMenu(new Menu_CreateScenario_Settings_Top());
                        this.addNextMenuToView(this.CREATE_SCENARIO_SETTINGS, new Menu_CreateScenario_Settings());
                    } else {
                        Menu_CreateScenario_Settings.disposePreview();
                        this.menus.get(this.CREATE_SCENARIO_SETTINGS).set(1, new Menu_CreateScenario_Settings());
                    }
                    return this.CREATE_SCENARIO_SETTINGS;
                }
                case eCREATE_SCENARIO_SET_UP_ARMY: {
                    if (this.CREATE_SCENARIO_SET_UP_ARMY == -1) {
                        this.CREATE_SCENARIO_SET_UP_ARMY = this.addMenu(new Menu_CreateScenario_SetUpArmy());
                        this.addNextMenuToView(this.CREATE_SCENARIO_SET_UP_ARMY, new Menu_CreateScenario_SetUpArmy_Options());
                        this.CREATE_SCENARIO_SET_UP_ARMY_SLIDERS = this.addNextMenuToView(this.CREATE_SCENARIO_SET_UP_ARMY, new Menu_CreateScenario_SetUpArmy_Sliders());
                        this.CREATE_SCENARIO_SET_UP_ARMY_CIVS = this.addNextMenuToView(this.CREATE_SCENARIO_SET_UP_ARMY, new Menu_CreateScenario_SetUpArmy_Civs());
                        this.CREATE_SCENARIO_SET_UP_ARMY_NEUTRAL = this.addNextMenuToView(this.CREATE_SCENARIO_SET_UP_ARMY, new Menu_CreateScenario_SetUpArmy_NeutralArmy());
                    }
                    return this.CREATE_SCENARIO_SET_UP_ARMY;
                }
                case eCREATE_SCENARIO_TECHNOLOGY_LEVELS: {
                    if (this.CREATE_SCENARIO_TECHNOLOGY_LEVELS == -1) {
                        this.CREATE_SCENARIO_TECHNOLOGY_LEVELS = this.addMenu(new Menu_CreateScenario_TechnologyLevels());
                        this.CREATE_SCENARIO_TECHNOLOGY_LEVELS_CONTINENTS = this.addNextMenuToView(this.CREATE_SCENARIO_TECHNOLOGY_LEVELS, new Menu_CreateScenario_TechnologyLevels_Continents());
                    }
                    return this.CREATE_SCENARIO_TECHNOLOGY_LEVELS;
                }
                case eCREATE_SCENARIO_HAPPINESS: {
                    if (this.CREATE_SCENARIO_HAPPINESS == -1) {
                        this.CREATE_SCENARIO_HAPPINESS = this.addMenu(new Menu_CreateScenario_Happiness());
                    }
                    return this.CREATE_SCENARIO_HAPPINESS;
                }
                case eCREATE_SCENARIO_STARTING_MONEY: {
                    if (this.CREATE_SCENARIO_STARTING_MONEY == -1) {
                        this.CREATE_SCENARIO_STARTING_MONEY = this.addMenu(new Menu_CreateScenario_SetUp_StartingMoney());
                    }
                    return this.CREATE_SCENARIO_STARTING_MONEY;
                }
                case eEDITOR_CIVILIZATIONS: {
                    if (this.EDITOR_CIVILIZATIONS == -1) {
                        this.EDITOR_CIVILIZATIONS = this.addMenu(new Menu_Editor_Civilizations());
                    } else {
                        this.menus.get(this.EDITOR_CIVILIZATIONS).set(0, new Menu_Editor_Civilizations());
                    }
                    return this.EDITOR_CIVILIZATIONS;
                }
                case eCREATE_CIVILIZATION: {
                    if (this.CREATE_CIVILIZATION == -1) {
                        this.CREATE_CIVILIZATION = this.addMenu(new Menu_CreateCiv_BG());
                        this.CREATE_CIVILIZATION_TOP = this.addNextMenuToView(this.CREATE_CIVILIZATION, new Menu_CreateCiv());
                        this.CREATE_CIVILIZATION_DATA = this.addNextMenuToView(this.CREATE_CIVILIZATION, new Menu_CreateCiv_Data());
                        this.CREATE_CIVILIZATION_FLAG = this.addNextMenuToView(this.CREATE_CIVILIZATION, new Menu_CreateCiv_Flag());
                        this.CREATE_CIVILIZATION_OVERLAY = this.addNextMenuToView(this.CREATE_CIVILIZATION, new Menu_CreateCiv_Overlay());
                    } else {
                        this.menus.get(this.CREATE_CIVILIZATION).set(this.CREATE_CIVILIZATION_TOP, new Menu_CreateCiv());
                        this.menus.get(this.CREATE_CIVILIZATION).set(this.CREATE_CIVILIZATION_DATA, new Menu_CreateCiv_Data());
                    }
                    return this.CREATE_CIVILIZATION;
                }
                case eEDITOR_GAME_CIVS: {
                    if (this.EDITOR_GAME_CIVS == -1) {
                        this.EDITOR_GAME_CIVS = this.addMenu(new Menu_Editor_GameCivs_Title());
                        this.addNextMenuToView(this.EDITOR_GAME_CIVS, new Menu_Editor_GameCivs());
                        this.addNextMenuToView(this.EDITOR_GAME_CIVS, new Menu_Editor_GameCivs_Alphabet());
                    } else {
                        this.menus.get(this.EDITOR_GAME_CIVS).get(0).updateLanguage();
                        this.menus.get(this.EDITOR_GAME_CIVS).get(1).setVisible(false);
                        this.menus.get(this.EDITOR_GAME_CIVS).set(1, new Menu_Editor_GameCivs());
                        this.menus.get(this.EDITOR_GAME_CIVS).set(2, new Menu_Editor_GameCivs_Alphabet());
                    }
                    return this.EDITOR_GAME_CIVS;
                }
                case eEDITOR_GAME_CIVS_EDIT: {
                    if (this.EDITOR_GAME_CIVS_EDIT == -1) {
                        this.EDITOR_GAME_CIVS_EDIT = this.addMenu(new Menu_Editor_GameCivs_Edit2());
                    } else {
                        this.menus.get(this.EDITOR_GAME_CIVS_EDIT).set(0, new Menu_Editor_GameCivs_Edit2());
                    }
                    return this.EDITOR_GAME_CIVS_EDIT;
                }
                case eSCENARIO_AGE: {
                    if (this.SCENARIO_AGE == -1) {
                        this.SCENARIO_AGE = this.addMenu(new Menu_Scenario_Age());
                        this.addNextMenuToView(this.SCENARIO_AGE, new Menu_Scenario_Age_List());
                        this.SCENARIO_AGE_CALENDAR = this.addNextMenuToView(this.SCENARIO_AGE, new Menu_Calendar());
                    }
                    return this.SCENARIO_AGE;
                }
                case eCREATE_SCENARIO_EVENTS_DATE: {
                    if (this.CREATE_SCENARIO_EVENTS_DATE == -1) {
                        this.CREATE_SCENARIO_EVENTS_DATE = this.addMenu(new Menu_CreateScenario_Events_Date());
                        this.addNextMenuToView(this.CREATE_SCENARIO_EVENTS_DATE, new Menu_CreateScenario_Events_Date_List());
                        this.CREATE_SCENARIO_EVENTS_DATE_CALENDAR = this.addNextMenuToView(this.CREATE_SCENARIO_EVENTS_DATE, new Menu_CreateScenario_Events_Date_Calendar());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_DATE).set(1, new Menu_CreateScenario_Events_Date_List());
                    }
                    return this.CREATE_SCENARIO_EVENTS_DATE;
                }
                case eLOAD_MAP: {
                    if (this.LOAD_MAP == -1) {
                        this.LOAD_MAP = this.addMenu(new Menu_LoadMap());
                    } else {
                        this.menus.get(this.LOAD_MAP).set(0, new Menu_LoadMap());
                    }
                    return this.LOAD_MAP;
                }
                case eLOAD_SAVE: {
                    if (this.LOAD_SAVE == -1) {
                        this.LOAD_SAVE = this.addMenu(new Menu_LoadSave());
                    } else {
                        this.menus.get(this.LOAD_SAVE).set(0, new Menu_LoadSave());
                    }
                    return this.LOAD_SAVE;
                }
                case eMANAGE_DIPLOMACY: {
                    if (this.MANAGE_DIPLOMACY == -1) {
                        this.MANAGE_DIPLOMACY = this.addMenu(new Menu_ManageDiplomacy());
                        this.addNextMenuToView(this.MANAGE_DIPLOMACY, new Menu_ManageDiplomacy_Options());
                        this.MANAGE_DIPLOMACY_ALLIANCES = this.addNextMenuToView(this.MANAGE_DIPLOMACY, new Menu_ManageDiplomacy_Alliances());
                        this.MANAGE_DIPLOMACY_RELATIONS_INTERACTIVE = this.addNextMenuToView(this.MANAGE_DIPLOMACY, new Menu_ManageDiplomacy_Relations_Interactive());
                        this.MANAGE_DIPLOMACY_PACTS2 = this.addNextMenuToView(this.MANAGE_DIPLOMACY, new Menu_ManageDiplomacy_Pacts3());
                        this.MANAGE_DIPLOMACY_PACTS_LIST = this.addNextMenuToView(this.MANAGE_DIPLOMACY, new Menu_ManageDiplomacy_Pacts_List());
                        this.MANAGE_DIPLOMACY_TRUCES = this.addNextMenuToView(this.MANAGE_DIPLOMACY, new Menu_ManageDiplomacy_Truces());
                        this.MANAGE_DIPLOMACY_TRUCES_LIST = this.addNextMenuToView(this.MANAGE_DIPLOMACY, new Menu_ManageDiplomacy_Truces_List());
                        this.MANAGE_DIPLOMACY_VASSALS = this.addNextMenuToView(this.MANAGE_DIPLOMACY, new Menu_ManageDiplomacy_Vassals());
                        this.MANAGE_DIPLOMACY_VASSALS_LIST = this.addNextMenuToView(this.MANAGE_DIPLOMACY, new Menu_ManageDiplomacy_Vassals_List());
                        this.MANAGE_DIPLOMACY_VASSALS_AUTONOMY = this.addNextMenuToView(this.MANAGE_DIPLOMACY, new Menu_InGame_ChangeAutonomy());
                        this.MANAGE_DIPLOMACY_GUARANTEE = this.addNextMenuToView(this.MANAGE_DIPLOMACY, new Menu_ManageDiplomacy_Guarantee());
                        this.MANAGE_DIPLOMACY_GUARANTEE_LIST = this.addNextMenuToView(this.MANAGE_DIPLOMACY, new Menu_ManageDiplomacy_Guarantee_List());
                        this.MANAGE_DIPLOMACY_DEFENSIVE = this.addNextMenuToView(this.MANAGE_DIPLOMACY, new Menu_ManageDiplomacy_DefensivePact());
                        this.MANAGE_DIPLOMACY_DEFENSIVE_LIST = this.addNextMenuToView(this.MANAGE_DIPLOMACY, new Menu_ManageDiplomacy_DefensivePact_List());
                        this.MANAGE_DIPLOMACY_MILITARY_ACCESS = this.addNextMenuToView(this.MANAGE_DIPLOMACY, new Menu_ManageDiplomacy_MilitaryAccess());
                        this.MANAGE_DIPLOMACY_MILITARY_ACCESS_LIST = this.addNextMenuToView(this.MANAGE_DIPLOMACY, new Menu_ManageDiplomacy_MilitaryAccess_List());
                    }
                    return this.MANAGE_DIPLOMACY;
                }
                case eCUSTOMIZE_ALLIANCE: {
                    if (this.CUSTOMIZE_ALLIANCE == -1) {
                        this.CUSTOMIZE_ALLIANCE = this.addMenu(new Menu_CustomizeAlliance());
                        this.addNextMenuToView(this.CUSTOMIZE_ALLIANCE, new Menu_CustomizeAlliance_Options());
                    } else {
                        this.menus.get(this.CUSTOMIZE_ALLIANCE).set(1, new Menu_CustomizeAlliance_Options());
                    }
                    return this.CUSTOMIZE_ALLIANCE;
                }
                case eCUSTOMIZE_ALLIANCE_ADD_CIVILIZATION: {
                    if (this.CUSTOMIZE_ALLIANCE_ADD_CIVLIZATION == -1) {
                        this.CUSTOMIZE_ALLIANCE_ADD_CIVLIZATION = this.addMenu(new Menu_CustomizeAlliance_AddCivilization());
                        this.addNextMenuToView(this.CUSTOMIZE_ALLIANCE_ADD_CIVLIZATION, new Menu_CustomizeAlliance_AddCivilization_List());
                        this.addNextMenuToView(this.CUSTOMIZE_ALLIANCE_ADD_CIVLIZATION, new Menu_CustomizeAlliance_AddCivilization_Alphabet());
                    } else {
                        this.menus.get(this.CUSTOMIZE_ALLIANCE_ADD_CIVLIZATION).set(1, new Menu_CustomizeAlliance_AddCivilization_List());
                        this.menus.get(this.CUSTOMIZE_ALLIANCE_ADD_CIVLIZATION).set(2, new Menu_CustomizeAlliance_AddCivilization_Alphabet());
                    }
                    return this.CUSTOMIZE_ALLIANCE_ADD_CIVLIZATION;
                }
                case eCREATE_SCENARIO_CORES: {
                    if (this.CREATE_SCENARIO_CORES == -1) {
                        this.CREATE_SCENARIO_CORES = this.addMenu(new Menu_CreateScenario_Cores());
                        this.CREATE_SCENARIO_CORES_SETUP = this.addNextMenuToView(this.CREATE_SCENARIO_CORES, new Menu_CreateScenario_Cores_SetUp());
                    } else {
                        this.rebuildCreateScenario_Cores_SetUp();
                    }
                    return this.CREATE_SCENARIO_CORES;
                }
                case eCREATE_SCENARIO_EVENTS_DECISION: {
                    if (this.CREATE_SCENARIO_EVENTS_DECISION == -1) {
                        this.CREATE_SCENARIO_EVENTS_DECISION = this.addMenu(new Menu_CreateScenario_Events_Decision());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_EVENTS_DECISION).set(0, new Menu_CreateScenario_Events_Decision());
                    }
                    return this.CREATE_SCENARIO_EVENTS_DECISION;
                }
                case eCREATE_SCENARIO_PALLET_OF_COLORS: {
                    if (this.CREATE_SCENARIO_PALLET_OF_COLORS == -1) {
                        this.CREATE_SCENARIO_PALLET_OF_COLORS = this.addMenu(new Menu_CreateScenario_PalletOfColors());
                        this.addNextMenuToView(this.CREATE_SCENARIO_PALLET_OF_COLORS, new Menu_CreateScenario_PalletOfColors_List());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_PALLET_OF_COLORS).set(1, new Menu_CreateScenario_PalletOfColors_List());
                    }
                    return this.CREATE_SCENARIO_PALLET_OF_COLORS;
                }
                case eEDITOR_CITIES: {
                    if (this.EDITOR_CITIES == -1) {
                        this.EDITOR_CITIES = this.addMenu(new Menu_Editor_Cities());
                    } else {
                        this.menus.get(this.EDITOR_CITIES).set(0, new Menu_Editor_Cities());
                    }
                    return this.EDITOR_CITIES;
                }
                case eCREATE_CITY: {
                    if (this.CREATE_CITY == -1) {
                        this.CREATE_CITY = this.addMenu(new Menu_CreateCity());
                    } else {
                        this.menus.get(this.CREATE_CITY).set(0, new Menu_CreateCity());
                    }
                    return this.CREATE_CITY;
                }
                case eEDITOR_TRANSLATION: {
                    if (this.EDITOR_TRANSLATIONS == -1) {
                        this.EDITOR_TRANSLATIONS = this.addMenu(new Menu_Editor_Translations());
                    }
                    return this.EDITOR_TRANSLATIONS;
                }
                case eCREATE_TRANSLATION: {
                    if (this.CREATE_TRANSLATION == -1) {
                        this.CREATE_TRANSLATION = this.addMenu(new Menu_CreateTranslation());
                    }
                    return this.CREATE_TRANSLATION;
                }
                case eDOWNLOAD_PALLETS: {
                    if (this.DOWNLOAD_PALLETS == -1) {
                        this.DOWNLOAD_PALLETS = this.addMenu(new Menu_Download_Pallets());
                    }
                    return this.DOWNLOAD_PALLETS;
                }
                case eMAP_EDITOR: {
                    if (this.MAP_EDITOR == -1) {
                        this.MAP_EDITOR = this.addMenu(new Menu_MapEditor());
                    } else {
                        this.menus.get(this.MAP_EDITOR).set(0, new Menu_MapEditor());
                    }
                    return this.MAP_EDITOR;
                }
                case eMAP_EDITOR_EDIT: {
                    if (this.MAP_EDITOR_EDIT == -1) {
                        this.MAP_EDITOR_EDIT = this.addMenu(new Menu_MapEditor_Edit());
                        this.addNextMenuToView(this.MAP_EDITOR_EDIT, new Menu_MapEditor_Edit_Options());
                    } else {
                        this.menus.get(this.MAP_EDITOR_EDIT).set(0, new Menu_MapEditor_Edit());
                        this.menus.get(this.MAP_EDITOR_EDIT).set(1, new Menu_MapEditor_Edit_Options());
                    }
                    return this.MAP_EDITOR_EDIT;
                }
                case eMAP_EDITOR_CONNECTIONS: {
                    if (this.MAP_EDITOR_CONNECTIONS == -1) {
                        this.MAP_EDITOR_CONNECTIONS = this.addMenu(new Menu_MapEditor_Connections());
                        this.addNextMenuToView(this.MAP_EDITOR_CONNECTIONS, new Menu_MapEditor_Connections_IDs(-1));
                    }
                    return this.MAP_EDITOR_CONNECTIONS;
                }
                case eMAP_EDITOR_UPDATE_PROVINCE_DATA: {
                    if (this.MAP_EDITOR_UPDATE_PROVINCE_DATA == -1) {
                        this.MAP_EDITOR_UPDATE_PROVINCE_DATA = this.addMenu(new Menu_MapEditor_UpdateProvinceData());
                    } else {
                        this.menus.get(this.MAP_EDITOR_UPDATE_PROVINCE_DATA).set(0, new Menu_MapEditor_UpdateProvinceData());
                    }
                    return this.MAP_EDITOR_UPDATE_PROVINCE_DATA;
                }
                case eMAP_EDITOR_PROVINCE_BACKGROUND: {
                    if (this.MAP_EDITOR_PROVINCE_BACKGROUND == -1) {
                        this.MAP_EDITOR_PROVINCE_BACKGROUND = this.addMenu(new Menu_MapEditor_ProvinceBG());
                    }
                    return this.MAP_EDITOR_PROVINCE_BACKGROUND;
                }
                case eGAME_LEADERS: {
                    if (this.GAME_LEADERS == -1) {
                        this.GAME_LEADERS = this.addMenu(new Menu_Leaders());
                        this.addNextMenuToView(this.GAME_LEADERS, new Menu_Leaders_Options());
                        this.addNextMenuToView(this.GAME_LEADERS, new Menu_Leaders_Alphabet());
                    } else {
                        this.menus.get(this.GAME_LEADERS).get(1).setVisible(false);
                        this.menus.get(this.GAME_LEADERS).set(1, new Menu_Leaders_Options());
                        this.menus.get(this.GAME_LEADERS).set(2, new Menu_Leaders_Alphabet());
                    }
                    return this.GAME_LEADERS;
                }
                case eGAME_RANDOM_LEADERS: {
                    if (this.GAME_RANDOM_LEADERS == -1) {
                        this.GAME_RANDOM_LEADERS = this.addMenu(new Menu_Leaders());
                        this.addNextMenuToView(this.GAME_RANDOM_LEADERS, new Menu_Random_Leaders_Options());
                        this.addNextMenuToView(this.GAME_RANDOM_LEADERS, new Menu_Random_Leaders_Alphabet());
                    } else {
                        this.menus.get(this.GAME_RANDOM_LEADERS).get(1).setVisible(false);
                        this.menus.get(this.GAME_RANDOM_LEADERS).set(1, new Menu_Random_Leaders_Options());
                        this.menus.get(this.GAME_RANDOM_LEADERS).set(2, new Menu_Random_Leaders_Alphabet());
                    }
                    return this.GAME_RANDOM_LEADERS;
                }
                case eMAP_EDITOR_FORMABLE_CIVS: {
                    if (this.MAP_EDITOR_FORMABLE_CIVS == -1) {
                        this.MAP_EDITOR_FORMABLE_CIVS = this.addMenu(new Menu_MapEditor_FormableCivs());
                        this.addNextMenuToView(this.MAP_EDITOR_FORMABLE_CIVS, new Menu_MapEditor_FormableCivs_Options());
                        this.addNextMenuToView(this.MAP_EDITOR_FORMABLE_CIVS, new Menu_MapEditor_FormableCivs_Alphabet());
                    } else {
                        this.menus.get(this.MAP_EDITOR_FORMABLE_CIVS).get(1).setVisible(false);
                        this.menus.get(this.MAP_EDITOR_FORMABLE_CIVS).set(1, new Menu_MapEditor_FormableCivs_Options());
                        this.menus.get(this.MAP_EDITOR_FORMABLE_CIVS).set(2, new Menu_MapEditor_FormableCivs_Alphabet());
                    }
                    return this.MAP_EDITOR_FORMABLE_CIVS;
                }
                case eGAME_LEADERS_EDIT: {
                    if (this.GAME_LEADERS_EDIT == -1) {
                        this.GAME_LEADERS_EDIT = this.addMenu(new Menu_Leader_Edit());
                        this.GAME_LEADERS_EDIT_CIVS = this.addNextMenuToView(this.GAME_LEADERS_EDIT, new Menu_Leader_Edit_Civs());
                        this.GAME_LEADERS_EDIT_DATA = this.addNextMenuToView(this.GAME_LEADERS_EDIT, new Menu_Leader_Edit_Data());
                    } else {
                        this.menus.get(this.GAME_LEADERS_EDIT).get(0).onBackPressed();
                        this.menus.get(this.GAME_LEADERS_EDIT).set(0, new Menu_Leader_Edit());
                        this.menus.get(this.GAME_LEADERS_EDIT).get(this.GAME_LEADERS_EDIT_CIVS).onBackPressed();
                        this.menus.get(this.GAME_LEADERS_EDIT).set(this.GAME_LEADERS_EDIT_CIVS, new Menu_Leader_Edit_Civs());
                        this.menus.get(this.GAME_LEADERS_EDIT).get(this.GAME_LEADERS_EDIT_DATA).onBackPressed();
                        this.menus.get(this.GAME_LEADERS_EDIT).set(this.GAME_LEADERS_EDIT_DATA, new Menu_Leader_Edit_Data());
                    }
                    return this.GAME_LEADERS_EDIT;
                }
                case eGAME_RANDOM_LEADERS_EDIT: {
                    if (this.GAME_RANDOM_LEADERS_EDIT == -1) {
                        this.GAME_RANDOM_LEADERS_EDIT = this.addMenu(new Menu_Random_Leader_Edit());
                        this.GAME_RANDOM_LEADERS_EDIT_AITYPE = this.addNextMenuToView(this.GAME_RANDOM_LEADERS_EDIT, new Menu_Random_Leader_Edit_AIType());
                        this.GAME_RANDOM_LEADERS_EDIT_CIVS = this.addNextMenuToView(this.GAME_RANDOM_LEADERS_EDIT, new Menu_Random_Leader_Edit_Civs());
                        this.GAME_RANDOM_LEADERS_EDIT_DATA = this.addNextMenuToView(this.GAME_RANDOM_LEADERS_EDIT, new Menu_Random_Leader_Edit_Data());
                    } else {
                        this.menus.get(this.GAME_RANDOM_LEADERS_EDIT).get(0).onBackPressed();
                        this.menus.get(this.GAME_RANDOM_LEADERS_EDIT).set(0, new Menu_Random_Leader_Edit());
                        this.menus.get(this.GAME_RANDOM_LEADERS_EDIT).get(this.GAME_RANDOM_LEADERS_EDIT_AITYPE).onBackPressed();
                        this.menus.get(this.GAME_RANDOM_LEADERS_EDIT).set(this.GAME_RANDOM_LEADERS_EDIT_AITYPE, new Menu_Random_Leader_Edit_AIType());
                        this.menus.get(this.GAME_RANDOM_LEADERS_EDIT).get(this.GAME_RANDOM_LEADERS_EDIT_CIVS).onBackPressed();
                        this.menus.get(this.GAME_RANDOM_LEADERS_EDIT).set(this.GAME_RANDOM_LEADERS_EDIT_CIVS, new Menu_Random_Leader_Edit_Civs());
                        this.menus.get(this.GAME_RANDOM_LEADERS_EDIT).get(this.GAME_RANDOM_LEADERS_EDIT_DATA).onBackPressed();
                        this.menus.get(this.GAME_RANDOM_LEADERS_EDIT).set(this.GAME_RANDOM_LEADERS_EDIT_DATA, new Menu_Random_Leader_Edit_Data());
                    }
                    return this.GAME_RANDOM_LEADERS_EDIT;
                }
                case eMAP_EDITOR_FORMABLE_CIVS_EDIT: {
                    if (this.MAP_EDITOR_FORMABLE_CIVS_EDIT == -1) {
                        this.MAP_EDITOR_FORMABLE_CIVS_EDIT = this.addMenu(new Menu_MapEditor_FormableCivs_Edit());
                        this.MAP_EDITOR_FORMABLE_CIVS_EDIT_CLAIMANTS = this.addNextMenuToView(this.MAP_EDITOR_FORMABLE_CIVS_EDIT, new Menu_MapEditor_FormableCivs_Claimants());
                    } else {
                        this.menus.get(this.MAP_EDITOR_FORMABLE_CIVS_EDIT).get(0).onBackPressed();
                        this.menus.get(this.MAP_EDITOR_FORMABLE_CIVS_EDIT).set(0, new Menu_MapEditor_FormableCivs_Edit());
                        this.menus.get(this.MAP_EDITOR_FORMABLE_CIVS_EDIT).get(this.MAP_EDITOR_FORMABLE_CIVS_EDIT_CLAIMANTS).onBackPressed();
                        this.menus.get(this.MAP_EDITOR_FORMABLE_CIVS_EDIT).set(this.MAP_EDITOR_FORMABLE_CIVS_EDIT_CLAIMANTS, new Menu_MapEditor_FormableCivs_Claimants());
                    }
                    return this.MAP_EDITOR_FORMABLE_CIVS_EDIT;
                }
                case eCREATE_SCENARIO_HOLY_ROMAN_EMPIRE: {
                    if (this.CREATE_SCENARIO_HOLY_ROMAN_EMPIRE == -1) {
                        this.CREATE_SCENARIO_HOLY_ROMAN_EMPIRE = this.addMenu(new Menu_CreateScenario_HRE());
                        this.CREATE_SCENARIO_HOLY_ROMAN_EMPIRE_PRINCES = this.addNextMenuToView(this.CREATE_SCENARIO_HOLY_ROMAN_EMPIRE, new Menu_CreateScenario_HRE_Princes());
                    } else {
                        this.menus.get(this.CREATE_SCENARIO_HOLY_ROMAN_EMPIRE).set(0, new Menu_CreateScenario_HRE());
                        this.menus.get(this.CREATE_SCENARIO_HOLY_ROMAN_EMPIRE).set(this.CREATE_SCENARIO_HOLY_ROMAN_EMPIRE_PRINCES, new Menu_CreateScenario_HRE_Princes());
                    }
                    return this.CREATE_SCENARIO_HOLY_ROMAN_EMPIRE;
                }
                case eMAP_EDITOR_FORMABLE_CIVS_SELECT_FORMABLE: {
                    if (this.MAP_EDITOR_FORMABLE_CIVS_SELECT_FORMABLE == -1) {
                        this.MAP_EDITOR_FORMABLE_CIVS_SELECT_FORMABLE = this.addMenu(new Menu_MapEditor_FormableCivs_SelectFormable());
                        this.MAP_EDITOR_FORMABLE_CIVS_SELECT_FORMABLE_ALPHABET = this.addNextMenuToView(this.MAP_EDITOR_FORMABLE_CIVS_SELECT_FORMABLE, new Menu_MapEditor_FormableCivs_SelectFormable_Alphabet());
                        this.MAP_EDITOR_FORMABLE_CIVS_SELECT_FORMABLE_LIST = this.addNextMenuToView(this.MAP_EDITOR_FORMABLE_CIVS_SELECT_FORMABLE, new Menu_MapEditor_FormableCivs_SelectFormable_List());
                    } else {
                        this.menus.get(this.MAP_EDITOR_FORMABLE_CIVS_SELECT_FORMABLE).set(this.MAP_EDITOR_FORMABLE_CIVS_SELECT_FORMABLE_ALPHABET, new Menu_MapEditor_FormableCivs_SelectFormable_Alphabet());
                        this.menus.get(this.MAP_EDITOR_FORMABLE_CIVS_SELECT_FORMABLE).get(this.MAP_EDITOR_FORMABLE_CIVS_SELECT_FORMABLE_LIST).onBackPressed();
                        this.menus.get(this.MAP_EDITOR_FORMABLE_CIVS_SELECT_FORMABLE).set(this.MAP_EDITOR_FORMABLE_CIVS_SELECT_FORMABLE_LIST, new Menu_MapEditor_FormableCivs_SelectFormable_List());
                    }
                    return this.MAP_EDITOR_FORMABLE_CIVS_SELECT_FORMABLE;
                }
                case eGAME_LEADERS_EDIT_SELECT_CIVS: {
                    if (this.GAME_LEADERS_EDIT_SELECT_CIVS == -1) {
                        this.GAME_LEADERS_EDIT_SELECT_CIVS = this.addMenu(new Menu_Leader_Edit_SelectCiv());
                        this.GAME_LEADERS_EDIT_SELECT_CIVS_ALPHABET = this.addNextMenuToView(this.GAME_LEADERS_EDIT_SELECT_CIVS, new Menu_Leader_Edit_SelectCiv_Alphabet());
                        this.GAME_LEADERS_EDIT_SELECT_CIVS_LIST = this.addNextMenuToView(this.GAME_LEADERS_EDIT_SELECT_CIVS, new Menu_Leader_Edit_SelectCiv_List());
                    } else {
                        this.menus.get(this.GAME_LEADERS_EDIT_SELECT_CIVS).set(this.GAME_LEADERS_EDIT_SELECT_CIVS_ALPHABET, new Menu_Leader_Edit_SelectCiv_Alphabet());
                        this.menus.get(this.GAME_LEADERS_EDIT_SELECT_CIVS).get(this.GAME_LEADERS_EDIT_SELECT_CIVS_LIST).onBackPressed();
                        this.menus.get(this.GAME_LEADERS_EDIT_SELECT_CIVS).set(this.GAME_LEADERS_EDIT_SELECT_CIVS_LIST, new Menu_Leader_Edit_SelectCiv_List());
                    }
                    return this.GAME_LEADERS_EDIT_SELECT_CIVS;
                }
                case eGAME_RANDOM_LEADERS_EDIT_SELECT_CIVS: {
                    if (this.GAME_RANDOM_LEADERS_EDIT_SELECT_CIVS == -1) {
                        this.GAME_RANDOM_LEADERS_EDIT_SELECT_CIVS = this.addMenu(new Menu_Random_Leader_Edit_SelectCiv());
                        this.GAME_RANDOM_LEADERS_EDIT_SELECT_CIVS_LIST = this.addNextMenuToView(this.GAME_RANDOM_LEADERS_EDIT_SELECT_CIVS, new Menu_Random_Leader_Edit_SelectCiv_List());
                    } else {
                        this.menus.get(this.GAME_RANDOM_LEADERS_EDIT_SELECT_CIVS).get(this.GAME_RANDOM_LEADERS_EDIT_SELECT_CIVS_LIST).onBackPressed();
                        this.menus.get(this.GAME_RANDOM_LEADERS_EDIT_SELECT_CIVS).set(this.GAME_RANDOM_LEADERS_EDIT_SELECT_CIVS_LIST, new Menu_Random_Leader_Edit_SelectCiv_List());
                    }
                    return this.GAME_RANDOM_LEADERS_EDIT_SELECT_CIVS;
                }
                case eGAME_RANDOM_LEADERS_EDIT_SELECT_AITYPE: {
                    if (this.GAME_RANDOM_LEADERS_EDIT_SELECT_AITYPE == -1) {
                        this.GAME_RANDOM_LEADERS_EDIT_SELECT_AITYPE = this.addMenu(new Menu_Random_Leader_Edit_SelectAIType());
                        this.GAME_RANDOM_LEADERS_EDIT_SELECT_AITYPE_LIST = this.addNextMenuToView(this.GAME_RANDOM_LEADERS_EDIT_SELECT_AITYPE, new Menu_Random_Leader_Edit_SelectAIType_List());
                    } else {
                        this.menus.get(this.GAME_RANDOM_LEADERS_EDIT_SELECT_AITYPE).get(this.GAME_RANDOM_LEADERS_EDIT_SELECT_AITYPE_LIST).onBackPressed();
                        this.menus.get(this.GAME_RANDOM_LEADERS_EDIT_SELECT_AITYPE).set(this.GAME_RANDOM_LEADERS_EDIT_SELECT_AITYPE_LIST, new Menu_Random_Leader_Edit_SelectAIType_List());
                    }
                    return this.GAME_RANDOM_LEADERS_EDIT_SELECT_AITYPE;
                }
                case eMAP_EDITOR_FORMABLE_CIVS_SELECT_CLAIMANT: {
                    if (this.MAP_EDITOR_FORMABLE_CIVS_SELECT_CLAIMANT == -1) {
                        this.MAP_EDITOR_FORMABLE_CIVS_SELECT_CLAIMANT = this.addMenu(new Menu_MapEditor_FormableCivs_SelectClaimant());
                        this.MAP_EDITOR_FORMABLE_CIVS_SELECT_CLAIMANT_ALPHABET = this.addNextMenuToView(this.MAP_EDITOR_FORMABLE_CIVS_SELECT_CLAIMANT, new Menu_MapEditor_FormableCivs_SelectClaimant_Alphabet());
                        this.MAP_EDITOR_FORMABLE_CIVS_SELECT_CLAIMANT_LIST = this.addNextMenuToView(this.MAP_EDITOR_FORMABLE_CIVS_SELECT_CLAIMANT, new Menu_MapEditor_FormableCivs_SelectClaimant_List());
                    } else {
                        this.menus.get(this.MAP_EDITOR_FORMABLE_CIVS_SELECT_CLAIMANT).set(this.MAP_EDITOR_FORMABLE_CIVS_SELECT_CLAIMANT_ALPHABET, new Menu_MapEditor_FormableCivs_SelectClaimant_Alphabet());
                        this.menus.get(this.MAP_EDITOR_FORMABLE_CIVS_SELECT_CLAIMANT).get(this.MAP_EDITOR_FORMABLE_CIVS_SELECT_CLAIMANT_LIST).onBackPressed();
                        this.menus.get(this.MAP_EDITOR_FORMABLE_CIVS_SELECT_CLAIMANT).set(this.MAP_EDITOR_FORMABLE_CIVS_SELECT_CLAIMANT_LIST, new Menu_MapEditor_FormableCivs_SelectClaimant_List());
                    }
                    return this.MAP_EDITOR_FORMABLE_CIVS_SELECT_CLAIMANT;
                }
                case eMAP_EDITOR_SEA_PROVINCES: {
                    if (this.MAP_EDITOR_SEA_PROVINCES == -1) {
                        this.MAP_EDITOR_SEA_PROVINCES = this.addMenu(new Menu_MapEditor_SeaProvinces());
                    }
                    return this.MAP_EDITOR_SEA_PROVINCES;
                }
                case eMAP_EDITOR_TERRAIN: {
                    if (this.MAP_EDITOR_TERRAIN == -1) {
                        this.MAP_EDITOR_TERRAIN = this.addMenu(new Menu_MapEditor_Terrain());
                        this.addNextMenuToView(this.MAP_EDITOR_TERRAIN, new Menu_MapEditor_Terrain_List());
                    } else {
                        this.menus.get(this.MAP_EDITOR_TERRAIN).set(1, new Menu_MapEditor_Terrain_List());
                    }
                    return this.MAP_EDITOR_TERRAIN;
                }
                case eMAP_EDITOR_CONTINENTS: {
                    if (this.MAP_EDITOR_CONTINENTS == -1) {
                        this.MAP_EDITOR_CONTINENTS = this.addMenu(new Menu_MapEditor_Continents());
                        this.addNextMenuToView(this.MAP_EDITOR_CONTINENTS, new Menu_MapEditor_Continents_List());
                    } else {
                        this.menus.get(this.MAP_EDITOR_CONTINENTS).set(1, new Menu_MapEditor_Continents_List());
                    }
                    return this.MAP_EDITOR_CONTINENTS;
                }
                case eMAP_EDITOR_REGIONS: {
                    if (this.MAP_EDITOR_REGIONS == -1) {
                        this.MAP_EDITOR_REGIONS = this.addMenu(new Menu_MapEditor_Regions());
                        this.addNextMenuToView(this.MAP_EDITOR_REGIONS, new Menu_MapEditor_Regions_List());
                    } else {
                        this.menus.get(this.MAP_EDITOR_REGIONS).set(1, new Menu_MapEditor_Regions_List());
                    }
                    return this.MAP_EDITOR_REGIONS;
                }
                case eMAP_EDITOR_GROWTH_RATE: {
                    if (this.MAP_EDITOR_GROWTH_RATE == -1) {
                        this.MAP_EDITOR_GROWTH_RATE = this.addMenu(new Menu_MapEditor_GrowthRate());
                    }
                    return this.MAP_EDITOR_GROWTH_RATE;
                }
                case eMAP_EDITOR_TRADE_ZONES: {
                    if (this.MAP_EDITOR_TRADE_ZONES == -1) {
                        this.MAP_EDITOR_TRADE_ZONES = this.addMenu(new Menu_MapEditor_TradeZones());
                    } else {
                        this.menus.get(this.MAP_EDITOR_TRADE_ZONES).get(0).getMenuElement(2).setCurrent(CFG.MANAGE_DIPLOMACY_CUSTOMIZE_RELATIONS_CIV_ID2);
                    }
                    return this.MAP_EDITOR_TRADE_ZONES;
                }
                case eMAP_EDITOR_TRADE_ZONES_EDIT: {
                    if (this.MAP_EDITOR_TRADE_ZONES_EDIT == -1) {
                        this.MAP_EDITOR_TRADE_ZONES_EDIT = this.addMenu(new Menu_MapEditor_TradeZones_Edit());
                    } else {
                        this.menus.get(this.MAP_EDITOR_TRADE_ZONES_EDIT).set(0, new Menu_MapEditor_TradeZones_Edit());
                    }
                    CFG.brushTool = false;
                    CFG.selectMode = true;
                    return this.MAP_EDITOR_TRADE_ZONES_EDIT;
                }
                case eMAP_EDITOR_ARMY_POSITION: {
                    if (this.MAP_EDITOR_ARMY_POSITION == -1) {
                        this.MAP_EDITOR_ARMY_POSITION = this.addMenu(new Menu_MapEditor_ArmyPosition());
                    }
                    return this.MAP_EDITOR_ARMY_POSITION;
                }
                case eMAP_EDITOR_PORT_POSITION: {
                    if (this.MAP_EDITOR_PORT_POSITION == -1) {
                        this.MAP_EDITOR_PORT_POSITION = this.addMenu(new Menu_MapEditor_PortPosition());
                    }
                    return this.MAP_EDITOR_PORT_POSITION;
                }
                case eMAP_EDITOR_ARMY_POSITION_CONVERT: {
                    if (this.MAP_EDITOR_ARMY_POSITION_CONVERT == -1) {
                        this.MAP_EDITOR_ARMY_POSITION_CONVERT = this.addMenu(new Menu_MapEditor_ArmyPosition_Convert());
                    } else {
                        this.menus.get(this.MAP_EDITOR_ARMY_POSITION_CONVERT).set(0, new Menu_MapEditor_ArmyPosition_Convert());
                    }
                    return this.MAP_EDITOR_ARMY_POSITION_CONVERT;
                }
                case eMAP_EDITOR_PORT_POSITION_CONVERT: {
                    if (this.MAP_EDITOR_PORT_POSITION_CONVERT == -1) {
                        this.MAP_EDITOR_PORT_POSITION_CONVERT = this.addMenu(new Menu_MapEditor_PortPosition_Convert());
                    } else {
                        this.menus.get(this.MAP_EDITOR_PORT_POSITION_CONVERT).set(0, new Menu_MapEditor_PortPosition_Convert());
                    }
                    return this.MAP_EDITOR_PORT_POSITION_CONVERT;
                }
                case eMAP_EDITOR_ARMY_SEA_BOXES: {
                    if (this.MAP_EDITOR_ARMY_SEA_BOXES == -1) {
                        this.MAP_EDITOR_ARMY_SEA_BOXES = this.addMenu(new Menu_MapEditor_ArmySeaBoxes());
                    }
                    return this.MAP_EDITOR_ARMY_SEA_BOXES;
                }
                case eMAP_EDITOR_ARMY_SEA_BOXES_EDIT: {
                    if (this.MAP_EDITOR_ARMY_SEA_BOXES_EDIT == -1) {
                        this.MAP_EDITOR_ARMY_SEA_BOXES_EDIT = this.addMenu(new Menu_MapEditor_ArmySeaBoxes_Edit());
                        this.addNextMenuToView(this.MAP_EDITOR_ARMY_SEA_BOXES_EDIT, new Menu_MapEditor_ArmySeaBoxes_Edit_Top());
                    } else {
                        this.menus.get(this.MAP_EDITOR_ARMY_SEA_BOXES_EDIT).set(0, new Menu_MapEditor_ArmySeaBoxes_Edit());
                        this.menus.get(this.MAP_EDITOR_ARMY_SEA_BOXES_EDIT).set(1, new Menu_MapEditor_ArmySeaBoxes_Edit_Top());
                    }
                    return this.MAP_EDITOR_ARMY_SEA_BOXES_EDIT;
                }
                case eMAP_EDITOR_ARMY_SEA_BOXES_ADD: {
                    if (this.MAP_EDITOR_ARMY_SEA_BOXES_ADD == -1) {
                        this.MAP_EDITOR_ARMY_SEA_BOXES_ADD = this.addMenu(new Menu_MapEditor_ArmySeaBoxes_Add());
                    } else {
                        this.menus.get(this.MAP_EDITOR_ARMY_SEA_BOXES_ADD).set(0, new Menu_MapEditor_ArmySeaBoxes_Add());
                    }
                    return this.MAP_EDITOR_ARMY_SEA_BOXES_ADD;
                }
                case eMAP_EDITOR_WASTELAND_MAPS: {
                    if (this.MAP_EDITOR_WASTELAND_MAPS == -1) {
                        this.MAP_EDITOR_WASTELAND_MAPS = this.addMenu(new Menu_MapEditor_WastelandMaps());
                    } else {
                        this.menus.get(this.MAP_EDITOR_WASTELAND_MAPS).set(0, new Menu_MapEditor_WastelandMaps());
                    }
                    return this.MAP_EDITOR_WASTELAND_MAPS;
                }
                case eMAP_EDITOR_WASTELAND_MAPS_EDIT: {
                    if (this.MAP_EDITOR_WASTELAND_MAPS_EDIT == -1) {
                        this.MAP_EDITOR_WASTELAND_MAPS_EDIT = this.addMenu(new Menu_MapEditor_WastelandMaps_Edit());
                    } else {
                        this.menus.get(this.MAP_EDITOR_WASTELAND_MAPS_EDIT).set(0, new Menu_MapEditor_WastelandMaps_Edit());
                    }
                    return this.MAP_EDITOR_WASTELAND_MAPS_EDIT;
                }
                case eMAP_EDITOR_PACKAGES_CONTINENTS: {
                    if (this.MAP_EDITOR_PACKAGES_CONTINENTS == -1) {
                        this.MAP_EDITOR_PACKAGES_CONTINENTS = this.addMenu(new Menu_Packages_WorldContinents());
                    } else {
                        this.menus.get(this.MAP_EDITOR_PACKAGES_CONTINENTS).set(0, new Menu_Packages_WorldContinents());
                    }
                    return this.MAP_EDITOR_PACKAGES_CONTINENTS;
                }
                case eMAP_EDITOR_PACKAGES_REGIONS: {
                    if (this.MAP_EDITOR_PACKAGES_REGIONS == -1) {
                        this.MAP_EDITOR_PACKAGES_REGIONS = this.addMenu(new Menu_Packages_Regions());
                    } else {
                        this.menus.get(this.MAP_EDITOR_PACKAGES_REGIONS).set(0, new Menu_Packages_Regions());
                    }
                    return this.MAP_EDITOR_PACKAGES_REGIONS;
                }
                case eMAP_EDITOR_CREATE_CONTINENTS_PACKAGE: {
                    if (this.MAP_EDITOR_CREATE_CONTINENTS_PACKAGE == -1) {
                        this.MAP_EDITOR_CREATE_CONTINENTS_PACKAGE = this.addMenu(new Menu_Continents_CreateNewPackage());
                    } else {
                        this.menus.get(this.MAP_EDITOR_CREATE_CONTINENTS_PACKAGE).set(0, new Menu_Continents_CreateNewPackage());
                    }
                    return this.MAP_EDITOR_CREATE_CONTINENTS_PACKAGE;
                }
                case eMAP_EDITOR_CREATE_REGIONS_PACKAGE: {
                    if (this.MAP_EDITOR_CREATE_REGIONS_PACKAGE == -1) {
                        this.MAP_EDITOR_CREATE_REGIONS_PACKAGE = this.addMenu(new Menu_Regions_CreateNewPackage());
                    } else {
                        this.menus.get(this.MAP_EDITOR_CREATE_REGIONS_PACKAGE).set(0, new Menu_Regions_CreateNewPackage());
                    }
                    return this.MAP_EDITOR_CREATE_REGIONS_PACKAGE;
                }
                case eMAP_EDITOR_CONTINENTS_ADDNEWCONTINENT_TOPACKAGE: {
                    if (this.MAP_EDITOR_CONTINENTS_ADDNEWCONTINENT_TOPACKAGE == -1) {
                        this.MAP_EDITOR_CONTINENTS_ADDNEWCONTINENT_TOPACKAGE = this.addMenu(new Menu_Continents_AddNewContinentToPackage());
                    } else {
                        this.menus.get(this.MAP_EDITOR_CONTINENTS_ADDNEWCONTINENT_TOPACKAGE).set(0, new Menu_Continents_AddNewContinentToPackage());
                    }
                    return this.MAP_EDITOR_CONTINENTS_ADDNEWCONTINENT_TOPACKAGE;
                }
                case eMAP_EDITOR_REGIONS_ADDNEWREGION_TOPACKAGE: {
                    if (this.MAP_EDITOR_REGIONS_ADDNEWREGION_TOPACKAGE == -1) {
                        this.MAP_EDITOR_REGIONS_ADDNEWREGION_TOPACKAGE = this.addMenu(new Menu_Regions_AddNewRegionToPackage());
                    } else {
                        this.menus.get(this.MAP_EDITOR_REGIONS_ADDNEWREGION_TOPACKAGE).set(0, new Menu_Regions_AddNewRegionToPackage());
                    }
                    return this.MAP_EDITOR_REGIONS_ADDNEWREGION_TOPACKAGE;
                }
                case eMAP_EDITOR_CREATE_NEW_CONTINENT: {
                    if (this.MAP_EDITOR_CREATE_NEW_CONTINENT == -1) {
                        this.MAP_EDITOR_CREATE_NEW_CONTINENT = this.addMenu(new Menu_Continents_CreateNewContinent());
                    } else {
                        this.menus.get(this.MAP_EDITOR_CREATE_NEW_CONTINENT).set(0, new Menu_Continents_CreateNewContinent());
                    }
                    return this.MAP_EDITOR_CREATE_NEW_CONTINENT;
                }
                case eMAP_EDITOR_CREATE_NEW_REGION: {
                    if (this.MAP_EDITOR_CREATE_NEW_REGION == -1) {
                        this.MAP_EDITOR_CREATE_NEW_REGION = this.addMenu(new Menu_Regions_CreateNewRegion());
                    } else {
                        this.menus.get(this.MAP_EDITOR_CREATE_NEW_REGION).set(0, new Menu_Regions_CreateNewRegion());
                    }
                    return this.MAP_EDITOR_CREATE_NEW_REGION;
                }
                case eCREATE_SCENARIO_PREVIEW: {
                    if (this.CREATE_SCENARIO_PREVIEW == -1) {
                        this.CREATE_SCENARIO_PREVIEW = this.addMenu(new Menu_CreateScenario_Preview());
                    }
                    return this.CREATE_SCENARIO_PREVIEW;
                }
                case eGAME_EDITOR: {
                    if (this.GAME_EDITOR == -1) {
                        this.GAME_EDITOR = this.addMenu(new Menu_GameEditor_Title());
                        this.addNextMenuToView(this.GAME_EDITOR, new Menu_GameEditor());
                    }
                    return this.GAME_EDITOR;
                }
                case eGAME_EDITOR_ALLIANCE_NAMES_PACKAGE: {
                    if (this.GAME_EDITOR_ALLIANCE_NAMES_PACKAGE == -1) {
                        this.GAME_EDITOR_ALLIANCE_NAMES_PACKAGE = this.addMenu(new Menu_Packages_RandomAllianceNames());
                    } else {
                        this.menus.get(this.GAME_EDITOR_ALLIANCE_NAMES_PACKAGE).set(0, new Menu_Packages_RandomAllianceNames());
                    }
                    return this.GAME_EDITOR_ALLIANCE_NAMES_PACKAGE;
                }
                case eGAME_EDITOR_ALLIANCE_NAMES_PACKAGE_CREATE: {
                    if (this.GAME_EDITOR_ALLIANCE_NAMES_PACKAGE_CREATE == -1) {
                        this.GAME_EDITOR_ALLIANCE_NAMES_PACKAGE_CREATE = this.addMenu(new Menu_AlliancesNames_Create());
                    } else {
                        this.menus.get(this.GAME_EDITOR_ALLIANCE_NAMES_PACKAGE_CREATE).set(0, new Menu_AlliancesNames_Create());
                    }
                    return this.GAME_EDITOR_ALLIANCE_NAMES_PACKAGE_CREATE;
                }
                case eGAME_EDITOR_ALLIANCE_NAMES_PACKAGE_CREATE_BUNDLE: {
                    if (this.GAME_EDITOR_ALLIANCE_NAMES_PACKAGE_CREATE_BUNDLE == -1) {
                        this.GAME_EDITOR_ALLIANCE_NAMES_PACKAGE_CREATE_BUNDLE = this.addMenu(new Menu_AlliancesNames_Create_Bundle());
                    } else {
                        this.menus.get(this.GAME_EDITOR_ALLIANCE_NAMES_PACKAGE_CREATE_BUNDLE).set(0, new Menu_AlliancesNames_Create_Bundle());
                    }
                    return this.GAME_EDITOR_ALLIANCE_NAMES_PACKAGE_CREATE_BUNDLE;
                }
                case eGAME_EDITOR_DIPLOMACY_COLORS_PACKAGES: {
                    if (this.GAME_EDITOR_DIPLOMACY_COLORS_PACKAGES == -1) {
                        this.GAME_EDITOR_DIPLOMACY_COLORS_PACKAGES = this.addMenu(new Menu_Packages_DiplomacyColors());
                    } else {
                        this.menus.get(this.GAME_EDITOR_DIPLOMACY_COLORS_PACKAGES).set(0, new Menu_Packages_DiplomacyColors());
                    }
                    return this.GAME_EDITOR_DIPLOMACY_COLORS_PACKAGES;
                }
                case eGAME_EDITOR_DIPLOMACY_COLORS_PACKAGES_CREATE: {
                    if (this.GAME_EDITOR_DIPLOMACY_COLORS_PACKAGES_CREATE == -1) {
                        this.GAME_EDITOR_DIPLOMACY_COLORS_PACKAGES_CREATE = this.addMenu(new Menu_DiplomacyColors_Create());
                        this.addNextMenuToView(this.GAME_EDITOR_DIPLOMACY_COLORS_PACKAGES_CREATE, new Menu_DiplomacyColors_Create_Relations());
                        this.addNextMenuToView(this.GAME_EDITOR_DIPLOMACY_COLORS_PACKAGES_CREATE, new Menu_DiplomacyColors_Create_Colors());
                    } else {
                        this.menus.get(this.GAME_EDITOR_DIPLOMACY_COLORS_PACKAGES_CREATE).set(0, new Menu_DiplomacyColors_Create());
                    }
                    return this.GAME_EDITOR_DIPLOMACY_COLORS_PACKAGES_CREATE;
                }
                case eGAME_EDITOR_PALLETS_OF_CIVS_COLORS_PACKAGES: {
                    if (this.GAME_EDITOR_PALLETS_OF_CIVS_COLORS_PACKAGES == -1) {
                        this.GAME_EDITOR_PALLETS_OF_CIVS_COLORS_PACKAGES = this.addMenu(new Menu_Packages_PalletOfCivsColors());
                    } else {
                        this.menus.get(this.GAME_EDITOR_PALLETS_OF_CIVS_COLORS_PACKAGES).set(0, new Menu_Packages_PalletOfCivsColors());
                    }
                    return this.GAME_EDITOR_PALLETS_OF_CIVS_COLORS_PACKAGES;
                }
                case eGAME_EDITOR_PALLETS_OF_CIVS_COLORS_PACKAGES_EDIT: {
                    if (this.GAME_EDITOR_PALLETS_OF_CIVS_COLORS_PACKAGES_EDIT == -1) {
                        this.GAME_EDITOR_PALLETS_OF_CIVS_COLORS_PACKAGES_EDIT = this.addMenu(new Menu_PalletOfCivilizationsColors());
                    } else {
                        this.menus.get(this.GAME_EDITOR_PALLETS_OF_CIVS_COLORS_PACKAGES_EDIT).set(0, new Menu_PalletOfCivilizationsColors());
                    }
                    return this.GAME_EDITOR_PALLETS_OF_CIVS_COLORS_PACKAGES_EDIT;
                }
                case eLOAD_GENERATE_SUGGESTED_OWNERS: {
                    if (this.LOAD_GENERATE_SUGGESTED_OWNERS == -1) {
                        this.LOAD_GENERATE_SUGGESTED_OWNERS = this.addMenu(new Menu_Load_GenerateSuggestedOwners());
                    } else {
                        this.menus.get(this.LOAD_GENERATE_SUGGESTED_OWNERS).set(0, new Menu_Load_GenerateSuggestedOwners());
                    }
                    CFG.map.getMapScroll().stopScrollingTheMap();
                    CFG.map.getMapScale().setCurrentScale(Map_Scale.MINSCALE);
                    CFG.map.getMapCoordinates().setNewPosX(-((int)((float)(CFG.map.getMapBG().getWidth() / 2) - (float)CFG.GAME_WIDTH / Map_Scale.MINSCALE / 2.0f)));
                    CFG.map.getMapCoordinates().setNewPosY(-((int)((float)(CFG.map.getMapBG().getHeight() / 2) - (float)CFG.GAME_HEIGHT / Map_Scale.MINSCALE / 2.0f)));
                    return this.LOAD_GENERATE_SUGGESTED_OWNERS;
                }
                case eLOAD_GENERATE_PRE_DEFINED_BORDERS: {
                    if (this.LOAD_GENERATE_PRE_DEFINED_BORDERS == -1) {
                        this.LOAD_GENERATE_PRE_DEFINED_BORDERS = this.addMenu(new Menu_Load_GeneratePreDefinedBorders());
                    } else {
                        this.menus.get(this.LOAD_GENERATE_PRE_DEFINED_BORDERS).set(0, new Menu_Load_GeneratePreDefinedBorders());
                    }
                    CFG.map.getMapScroll().stopScrollingTheMap();
                    CFG.map.getMapScale().setCurrentScale(Map_Scale.MINSCALE);
                    CFG.map.getMapCoordinates().setNewPosX(-((int)((float)(CFG.map.getMapBG().getWidth() / 2) - (float)CFG.GAME_WIDTH / Map_Scale.MINSCALE / 2.0f)));
                    CFG.map.getMapCoordinates().setNewPosY(-((int)((float)(CFG.map.getMapBG().getHeight() / 2) - (float)CFG.GAME_HEIGHT / Map_Scale.MINSCALE / 2.0f)));
                    return this.LOAD_GENERATE_PRE_DEFINED_BORDERS;
                }
                case eEDITOR_RELIGION: {
                    if (this.EDITOR_RELIGION == -1) {
                        this.EDITOR_RELIGION = this.addMenu(new Menu_ReligionsEditor_Title());
                        this.addNextMenuToView(this.EDITOR_RELIGION, new Menu_ReligionsEditor());
                    } else {
                        this.menus.get(this.EDITOR_RELIGION).set(1, new Menu_ReligionsEditor());
                    }
                    return this.EDITOR_RELIGION;
                }
                case eEDITOR_RELIGION_ADD: {
                    if (this.EDITOR_RELIGION_ADD == -1) {
                        this.EDITOR_RELIGION_ADD = this.addMenu(new Menu_ReligionEditor_Add());
                    } else {
                        this.menus.get(this.EDITOR_RELIGION_ADD).set(0, new Menu_ReligionEditor_Add());
                    }
                    return this.EDITOR_RELIGION_ADD;
                }
                case eGAME_EDITOR_SERVICE_RIBBON: {
                    if (this.GAME_EDITOR_SERVICE_RIBBON == -1) {
                        this.GAME_EDITOR_SERVICE_RIBBON = this.addMenu(new Menu_ServiceRibbon_Editor_Title());
                        this.addNextMenuToView(this.GAME_EDITOR_SERVICE_RIBBON, new Menu_ServiceRibbon_Editor());
                    } else {
                        this.menus.get(this.GAME_EDITOR_SERVICE_RIBBON).set(1, new Menu_ServiceRibbon_Editor());
                    }
                    return this.GAME_EDITOR_SERVICE_RIBBON;
                }
                case eGAME_EDITOR_SERVICE_RIBBON_EDIT: {
                    if (this.GAME_EDITOR_SERVICE_RIBBON_EDIT == -1) {
                        this.GAME_EDITOR_SERVICE_RIBBON_EDIT = this.addMenu(new Menu_ServiceRibbon_Editor_Edit());
                    } else {
                        this.menus.get(this.GAME_EDITOR_SERVICE_RIBBON_EDIT).set(0, new Menu_ServiceRibbon_Editor_Edit());
                    }
                    return this.GAME_EDITOR_SERVICE_RIBBON_EDIT;
                }
                case eGAME_EDITOR_SERVICE_RIBBON_EDIT_OVERLAY: {
                    if (this.GAME_EDITOR_SERVICE_RIBBON_EDIT_OVERLAY == -1) {
                        this.GAME_EDITOR_SERVICE_RIBBON_EDIT_OVERLAY = this.addMenu(new Menu_ServiceRibbon_Editor_Edit_Overlay());
                    } else {
                        this.menus.get(this.GAME_EDITOR_SERVICE_RIBBON_EDIT_OVERLAY).set(0, new Menu_ServiceRibbon_Editor_Edit_Overlay());
                    }
                    return this.GAME_EDITOR_SERVICE_RIBBON_EDIT_OVERLAY;
                }
                case eTERRAIN_TYPES_EDITOR: {
                    if (this.TERRAIN_TYPES_EDITOR == -1) {
                        this.TERRAIN_TYPES_EDITOR = this.addMenu(new Menu_TerrainTypes_Editor_Title());
                        this.addNextMenuToView(this.TERRAIN_TYPES_EDITOR, new Menu_TerrainTypes_Editor());
                    } else {
                        this.menus.get(this.TERRAIN_TYPES_EDITOR).set(1, new Menu_TerrainTypes_Editor());
                    }
                    return this.TERRAIN_TYPES_EDITOR;
                }
                case eTERRAIN_TYPE_ADD: {
                    if (this.TERRAIN_TYPE_ADD == -1) {
                        this.TERRAIN_TYPE_ADD = this.addMenu(new Menu_TerrainType_Add());
                    } else {
                        this.menus.get(this.TERRAIN_TYPE_ADD).set(0, new Menu_TerrainType_Add());
                    }
                    return this.TERRAIN_TYPE_ADD;
                }
                case eACHIEVEMENTS: {
                    if (this.ACHIEVEMENTS == -1) {
                        this.ACHIEVEMENTS = this.addMenu(new Menu_Achievements());
                        this.addNextMenuToView(this.ACHIEVEMENTS, new Menu_Achievements_Options());
                    } else {
                        this.menus.get(this.ACHIEVEMENTS).set(1, new Menu_Achievements_Options());
                    }
                    return this.ACHIEVEMENTS;
                }
                case eGAME_EDITOR_LINES: {
                    if (this.GAME_EDITOR_LINES == -1) {
                        this.GAME_EDITOR_LINES = this.addMenu(new Menu_GameEditor_Lines_Title());
                        this.addNextMenuToView(this.GAME_EDITOR_LINES, new Menu_GameEditor_Lines());
                    } else {
                        this.menus.get(this.GAME_EDITOR_LINES).set(1, new Menu_GameEditor_Lines());
                    }
                    return this.GAME_EDITOR_LINES;
                }
                case eGAME_EDITOR_LINES_EDIT: {
                    if (this.GAME_EDITOR_LINES_EDIT == -1) {
                        this.GAME_EDITOR_LINES_EDIT = this.addMenu(new Menu_GameEditor_Lines_Edit());
                    } else {
                        this.menus.get(this.GAME_EDITOR_LINES_EDIT).set(0, new Menu_GameEditor_Lines_Edit());
                    }
                    return this.GAME_EDITOR_LINES_EDIT;
                }
                case eGENERATE_FLAG: {
                    if (this.GENERATE_FLAG == -1) {
                        this.GENERATE_FLAG = this.addMenu(new Menu_GenerateFlag());
                    } else {
                        this.menus.get(this.GENERATE_FLAG).set(0, new Menu_GenerateFlag());
                    }
                    return this.GENERATE_FLAG;
                }
                case eGENERATE_PREVIEW: {
                    if (this.GENERATE_PREVIEW == -1) {
                        this.GENERATE_PREVIEW = this.addMenu(new Menu_GeneratePreview());
                    }
                    return this.GENERATE_PREVIEW;
                }
                case ePRINT_A_MAP: {
                    if (this.PRINT_A_MAP == -1) {
                        this.PRINT_A_MAP = this.addMenu(new Menu_Printamap());
                    } else {
                        this.menus.get(this.PRINT_A_MAP).set(0, new Menu_Printamap());
                    }
                    return this.PRINT_A_MAP;
                }
                case eGAME_EDITOR_REGIONS: {
                    if (this.GAME_EDITOR_REGIONS == -1) {
                        this.GAME_EDITOR_REGIONS = this.addMenu(new Menu_GameEditor_Regions());
                        this.addNextMenuToView(this.GAME_EDITOR_REGIONS, new Menu_MapEditor_OptimizationRegions());
                    }
                    return this.GAME_EDITOR_REGIONS;
                }
            }
            Gdx.app.log("AoC2.5", "Defaulting Menu Revert " + eMenu.name());
            Gdx.app.log("AoC2.5", "Stacktrace " + Arrays.toString(Thread.currentThread().getStackTrace()));
            return this.MAINMENU;
        }
        catch (IndexOutOfBoundsException | NullPointerException ex) {
            Gdx.app.log("AoC2.5", "Exception Menu Revert " + eMenu.name());
            CFG.exceptionStack(ex);
            Gdx.app.log("AoC2.5", "Stacktrace " + Arrays.toString(Thread.currentThread().getStackTrace()));
            return this.MAINMENU;
        }
    }

    protected final boolean getInGameView() {
        return this.viewID == this.INGAME;
    }

    protected final boolean getInGameView_Options() {
        try {
            return this.menus.get(this.INGAME).get(this.INGAME_OPTIONS).getVisible();
        }
        catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    protected final boolean getInGameView_EndOfGame() {
        try {
            return this.menus.get(this.INGAME).get(this.INGAME_ENDOFGAME).getVisible();
        }
        catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    protected final void setOrderOfMenu_CreateNewGame_CivInfo() {
        try {
            if (this.menus.get(this.CREATE_NEW_GAME).get(this.CREATE_NEW_GAME_CIV_INFO).getVisible()) {
                this.setOrderOfMenu(this.CREATE_NEW_GAME_CIV_INFO);
                this.setOrderOfMenu(this.CREATE_NEW_GAME_CIV_INFO_STATS);
                this.setOrderOfMenu(this.CREATE_NEW_GAME_CIV_INFO_DIPLOMACY);
                this.setOrderOfMenu(this.CREATE_NEW_GAME_CIV_INFO_PLAYERS);
            }
        }
        catch (IndexOutOfBoundsException indexOutOfBoundsException) {
        }
        catch (NullPointerException nullPointerException) {
            // empty catch block
        }
    }

    protected final void setOrderOfMenu_InGame_FlagAction() {
        if (this.menus.get(this.INGAME).get(this.INGAME_FLAG_ACTION).getVisible()) {
            this.setOrderOfMenu(this.INGAME_FLAG_ACTION);
            this.setOrderOfMenu(this.INGAME_FLAG_ACTION_BOT);
            this.setOrderOfMenu(this.INGAME_FLAG_ACTION_BOT_RIGHT_LEFT);
            this.setOrderOfMenu(this.INGAME_FLAG_ACTION_BOT_RIGHT_RIGHT);
            this.setOrderOfMenu(this.INGAME_FLAG_ACTION_BOT_LEFT);
            this.setOrderOfMenu(this.INGAME_FLAG_ACTION_GRAPH_MODES);
            this.setOrderOfMenu(this.INGAME_FLAG_ACTION_STATS);
            this.setOrderOfMenu(this.INGAME_FLAG_ACTION_CONSOLE);
        }
    }

    protected final void setOrderOfMenu_Games() {
        try {
            if (this.viewID == this.GAMES) {
                this.setOrderOfMenu(0);
                this.setOrderOfMenu(1);
            }
        }
        catch (IndexOutOfBoundsException indexOutOfBoundsException) {
        }
        catch (NullPointerException nullPointerException) {
            // empty catch block
        }
    }

    protected final void setOrderOfMenu_LoadGame() {
        try {
            if (this.viewID == this.LOADGAME) {
                this.setOrderOfMenu(0);
                this.setOrderOfMenu(1);
            }
        }
        catch (IndexOutOfBoundsException indexOutOfBoundsException) {
        }
        catch (NullPointerException nullPointerException) {
            // empty catch block
        }
    }

    protected final void setOrderOfMenu_InGame_Recruit() {
        this.setOrderOfMenu(0);
        this.setOrderOfMenu(this.INGAME_PROVINCE_INFO);
        this.setOrderOfMenu(this.INGAME_ACTION_INFO_CHOOSE_PROVINCE);
        this.setOrderOfMenu(this.INGAME_PROVINCE_RECRUIT_ARMY);
        this.setOrderOfMenu(this.INGAME_PROVINCE_RECRUIT_ARMY_INSTANTLY);
        this.setOrderOfMenu(this.INGAME_PROVINCE_DISBAND_ARMY);
        this.setOrderOfMenu(this.INGAME_PROVINCE_MOVE_UNITS);
        this.setOrderOfMenu(this.INGAME_PROVINCE_REGROUP_ARMY);
    }

    protected final void setOrderOfMenu_InGame_CivInfo() {
        if (this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO).getVisible()) {
            this.setOrderOfMenu(this.INGAME_CIV_INFO);
            this.setOrderOfMenu(this.INGAME_CIV_INFO2);
            this.setOrderOfMenu(this.INGAME_CIV_INFO_DIPLOMACY);
            this.setOrderOfMenu(this.INGAME_CIV_INFO_DIPLO_ACTIONS);
            this.setOrderOfMenu(this.INGAME_CIV_INFO_ACTIONS);
            this.setOrderOfMenu(this.INGAME_CIV_INFO_OPINIONS);
            this.setOrderOfMenu(this.INGAME_CIV_INFO_DECISIONS);
        }
    }

    protected final void setOrderOfMenu_InGame_CreateAVassal_Info() {
        if (this.menus.get(this.INGAME_CREATE_VASSAL).get(this.INGAME_CREATE_VASSAL_INFO).getVisible()) {
            this.setOrderOfMenu(this.INGAME_CREATE_VASSAL_INFO);
            this.setOrderOfMenu(this.INGAME_CREATE_VASSAL_INFO_STATS);
            this.setOrderOfMenu(this.INGAME_CREATE_VASSAL_CHANGEAUTONOMY);
            this.setOrderOfMenu(this.INGAME_CREATE_VASSAL_CHANGEGOVERNMENT);
        }
    }

    protected final boolean getVisible_Menu_InGame_Outliner() {
        return this.menus.get(this.INGAME).get(this.INGAME_OUTLINER).getVisible();
    }

    protected final SliderMenu getMenu_InGame_Outliner() {
        return this.menus.get(this.INGAME).get(this.INGAME_OUTLINER);
    }

    protected final SliderMenu getMenu_InGame_CurrentWars() {
        return this.menus.get(this.INGAME).get(this.INGAME_CURRENT_WARS);
    }

    protected final SliderMenu getMenu_InGame_CurrentWars_Info() {
        return this.menus.get(this.INGAME).get(this.INGAME_CURRENT_WARS_INFO);
    }

    protected final void setVisible_Menu_InGame_Outliner(boolean visible) {
        this.menus.get(this.INGAME).get(this.INGAME_OUTLINER).setVisible(visible);
        if (this.menus.get(this.INGAME).get(this.INGAME_OUTLINER).getVisible()) {
            this.setOrderOfMenu(this.INGAME_OUTLINER);
        }
    }

    protected final boolean getVisible_Menu_InGame_War() {
        return this.menus.get(this.INGAME).get(this.INGAME_WAR).getVisible();
    }

    protected final void setVisible_Menu_InGame_War(boolean visible) {
        this.menus.get(this.INGAME).get(this.INGAME_WAR).setVisible(visible);
        if (this.menus.get(this.INGAME).get(this.INGAME_WAR).getVisible()) {
            this.setOrderOfMenu(this.INGAME_WAR);
        }
    }

    protected final void rebuildMenu_InGame_War(int nCivA, int nCivB) {
        this.menus.get(this.INGAME).set(this.INGAME_WAR, new Menu_InGame_War(nCivA, nCivB));
        if (this.menus.get(this.INGAME).get(this.INGAME_WAR).getVisible()) {
            this.setOrderOfMenu(this.INGAME_WAR);
        }
    }

    protected final boolean getVisible_Menu_InGame_CityHaveBeenFounded() {
        return this.menus.get(this.INGAME).get(this.INGAME_CITY_HAVE_BEEN_FOUNED).getVisible();
    }

    protected final void setVisible_Menu_InGame_CityHaveBeenFounded(boolean visible) {
        this.menus.get(this.INGAME).get(this.INGAME_CITY_HAVE_BEEN_FOUNED).setVisible(visible);
        if (this.menus.get(this.INGAME).get(this.INGAME_CITY_HAVE_BEEN_FOUNED).getVisible()) {
            this.setOrderOfMenu(this.INGAME_CITY_HAVE_BEEN_FOUNED);
        }
    }

    protected final void rebuildMenu_InGame_CityHaveBeenFounded(int nProvinceID, int nCivID) {
        this.menus.get(this.INGAME).set(this.INGAME_CITY_HAVE_BEEN_FOUNED, new Menu_InGame_CityFounded(nProvinceID, nCivID));
        if (this.menus.get(this.INGAME).get(this.INGAME_CITY_HAVE_BEEN_FOUNED).getVisible()) {
            this.setOrderOfMenu(this.INGAME_CITY_HAVE_BEEN_FOUNED);
        }
    }

    protected final void setVisible_Menu_InGame_CapitalMoved(boolean visible) {
        this.menus.get(this.INGAME).get(this.INGAME_CAPITAL_MOVED).setVisible(visible);
        if (this.menus.get(this.INGAME).get(this.INGAME_CAPITAL_MOVED).getVisible()) {
            this.setOrderOfMenu(this.INGAME_CAPITAL_MOVED);
        }
    }

    protected final void rebuildMenu_InGame_CapitalMoved(int nProvinceID, int nCivID) {
        this.menus.get(this.INGAME).set(this.INGAME_CAPITAL_MOVED, new Menu_InGame_CapitalMoved(nProvinceID, nCivID));
        if (this.menus.get(this.INGAME).get(this.INGAME_CAPITAL_MOVED).getVisible()) {
            this.setOrderOfMenu(this.INGAME_CAPITAL_MOVED);
        }
    }

    protected final void setVisible_Menu_InGame_VassalReleased(boolean visible) {
        this.menus.get(this.INGAME).get(this.INGAME_VASSAL_RELEASED).setVisible(visible);
        if (this.menus.get(this.INGAME).get(this.INGAME_VASSAL_RELEASED).getVisible()) {
            this.setOrderOfMenu(this.INGAME_VASSAL_RELEASED);
        }
    }

    protected final void rebuildMenu_InGame_VassalReleased(int nCivID) {
        this.menus.get(this.INGAME).set(this.INGAME_VASSAL_RELEASED, new Menu_InGame_VassalReleased(nCivID));
        if (this.menus.get(this.INGAME).get(this.INGAME_VASSAL_RELEASED).getVisible()) {
            this.setOrderOfMenu(this.INGAME_VASSAL_RELEASED);
        }
    }

    protected final void rebuildMenu_InGame_SavedGame() {
        this.menus.get(this.INGAME).set(this.INGAME_SAVED_GAME, new Menu_InGame_GameSaved(0));
        if (this.menus.get(this.INGAME).get(this.INGAME_SAVED_GAME).getVisible()) {
            this.setOrderOfMenu(this.INGAME_SAVED_GAME);
        }
    }

    protected final boolean getVisible_Menu_InGame_Graph() {
        return this.menus.get(this.INGAME).get(this.INGAME_VIEW_GRAPH).getVisible();
    }

    protected final void setVisible_Menu_InGame_Graph(boolean visible) {
        this.menus.get(this.INGAME).get(this.INGAME_VIEW_GRAPH).setVisible(visible);
    }

    protected final void rebuildInGame_Graph() {
        this.menus.get(this.INGAME).get(this.INGAME_VIEW_GRAPH).setVisible(false);
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_VIEW_GRAPH).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_VIEW_GRAPH).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_VIEW_GRAPH).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_VIEW_GRAPH).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_VIEW_GRAPH, new Menu_InGame_Graph());
        if (tHeight + tPosY > CFG.GAME_HEIGHT - CFG.BUTTON_HEIGHT / 2) {
            tHeight = CFG.GAME_HEIGHT - tPosY - CFG.BUTTON_HEIGHT / 2;
        }
        this.menus.get(this.INGAME).get(this.INGAME_VIEW_GRAPH).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_VIEW_GRAPH).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_VIEW_GRAPH).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_VIEW_GRAPH).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_VIEW_GRAPH).setVisible(true);
        this.setOrderOfMenu(this.INGAME_VIEW_GRAPH);
        this.menus.get(this.INGAME).get(this.INGAME_VIEW_GRAPH).updateMenuElements_IsInView();
    }

    protected final boolean getVisible_Menu_InGame_AllianceInfo() {
        return this.menus.get(this.INGAME).get(this.INGAME_ALLIANCE_INFO).getVisible();
    }

    protected final void setVisible_Menu_InGame_AllianceInfo(boolean visible) {
        this.menus.get(this.INGAME).get(this.INGAME_ALLIANCE_INFO).setVisible(visible);
        if (this.menus.get(this.INGAME).get(this.INGAME_ALLIANCE_INFO).getVisible()) {
            this.setOrderOfMenu(this.INGAME_ALLIANCE_INFO);
        }
    }

    protected final void rebuildMenu_InGame_AllianceInfo(int nCivA, int nCivB) {
        this.menus.get(this.INGAME).set(this.INGAME_ALLIANCE_INFO, new Menu_InGame_AllianceInfo(nCivA, nCivB));
        if (this.menus.get(this.INGAME).get(this.INGAME_ALLIANCE_INFO).getVisible()) {
            this.setOrderOfMenu(this.INGAME_ALLIANCE_INFO);
        }
    }

    protected final void setVisible_InGame_HRE(boolean visible) {
        this.menus.get(this.INGAME).get(this.INGAME_HRE).setVisible(visible);
    }

    protected final void setVisible_InGame_HRE_VoteFor(boolean visible) {
        this.menus.get(this.INGAME).get(this.INGAME_HRE_VOTE).setVisible(visible);
    }

    protected final boolean getVisible_Menu_InGame_CurrentWars() {
        return this.menus.get(this.INGAME).get(this.INGAME_CURRENT_WARS).getVisible();
    }

    protected final boolean getVisible_Menu_InGame_CurrentWars_Info() {
        return this.menus.get(this.INGAME).get(this.INGAME_CURRENT_WARS_INFO).getVisible();
    }

    protected final void setVisible_Menu_InGame_CurrentWars(boolean visible) {
        block4: {
            try {
                if (visible) {
                    this.menus.get(this.INGAME).set(this.INGAME_CURRENT_WARS, new Menu_InGame_CurrentWars());
                }
                this.menus.get(this.INGAME).get(this.INGAME_CURRENT_WARS).setVisible(visible);
                if (this.menus.get(this.INGAME).get(this.INGAME_CURRENT_WARS).getVisible()) {
                    this.setOrderOfMenu(this.INGAME_CURRENT_WARS);
                }
                this.setVisible_Menu_InGame_CurrentWars_Info(visible);
            }
            catch (IndexOutOfBoundsException ex) {
                if (!CFG.LOGS) break block4;
                CFG.exceptionStack(ex);
            }
        }
    }

    protected final void setVisible_Menu_InGame_CurrentWars_Info(boolean visible) {
        block4: {
            try {
                if (visible) {
                    this.menus.get(this.INGAME).set(this.INGAME_CURRENT_WARS_INFO, new Menu_InGame_CurrentWars_Info());
                }
                this.menus.get(this.INGAME).get(this.INGAME_CURRENT_WARS_INFO).setVisible(visible);
                if (this.menus.get(this.INGAME).get(this.INGAME_CURRENT_WARS_INFO).getVisible()) {
                    this.setOrderOfMenu(this.INGAME_CURRENT_WARS_INFO);
                }
            }
            catch (IndexOutOfBoundsException ex) {
                if (!CFG.LOGS) break block4;
                CFG.exceptionStack(ex);
            }
        }
    }

    protected final void setVisible_Settings_Audio() {
        this.menus.get(this.SETTINGS).get(this.SETTINGS_AUDIO).setVisible(!this.menus.get(this.SETTINGS).get(this.SETTINGS_AUDIO).getVisible());
        if (this.menus.get(this.SETTINGS).get(this.SETTINGS_AUDIO).getVisible()) {
            this.setOrderOfMenu(this.SETTINGS_AUDIO);
        }
    }

    protected final void setOrderOfMenu_Menu_InGame_Outliner() {
        this.setOrderOfMenu(this.INGAME_OUTLINER);
    }

    protected final void setOrderOfMenu_Menu_InGame_CurrentWars() {
        this.setOrderOfMenu(this.INGAME_CURRENT_WARS);
        this.setOrderOfMenu(this.INGAME_CURRENT_WARS_INFO);
    }

    protected final void setVisible_InGame_CivInfo(boolean visible) {
        this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO).setVisible(visible);
        this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO2).setVisible(visible);
        this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_DIPLOMACY).setVisible(visible);
        this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_DIPLO_ACTIONS).setVisible(visible);
        if (visible) {
            int nCivID = CFG.getActiveCivInfo_BasedOnActiveProvinceID(CFG.game.getActiveProvinceID());
            if (nCivID != CFG.getActiveCivInfo()) {
                CFG.setActiveCivInfo(nCivID);
            }
            CFG.updateActiveCivInfo_InGame();
            this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO).updateLanguage();
            if (this.getInGame_ProvinceBuild_Visible()) {
                this.setVisible_InGame_ProvinceBuild(false, false);
            }
            if (this.getVisible_InGame_View_Stats()) {
                this.setVisible_InGame_View(false);
            }
        }
        this.setOrderOfMenu_InGame_CivInfo();
    }

    protected final void setVisible_InGame_CivManage(boolean visible) {
        this.menus.get(this.INGAME).get(this.INGAME_CIV_MANAGE).setVisible(visible);
        this.menus.get(this.INGAME).get(this.INGAME_CIV_MANAGE2).setVisible(visible);
        this.menus.get(this.INGAME).get(this.INGAME_CIV_MANAGE_ACTIONS).setVisible(visible);
        if (visible) {
            //int nCivID = CFG.getActiveCivInfo_BasedOnActiveProvinceID(CFG.game.getActiveProvinceID());
            //if (nCivID != CFG.getActiveCivInfo()) {
            //    CFG.setActiveCivInfo(nCivID);
            //}
            CFG.updateActiveCivManagement_InGame();
            if (CFG.menuManager.getVisible_Menu_InGame_Outliner()) {
                CFG.menuManager.setVisible_Menu_InGame_Outliner(false);
            }
        }
        this.setOrderOfMenu_InGame_ManageCiv_Info();
    }

    protected final boolean getVisible_InGame_CivManage() {
        return this.menus.get(this.INGAME).get(this.INGAME_CIV_MANAGE).getVisible();
    }

    protected final SliderMenu getInGame_Civ_Manage() {
        return this.menus.get(this.INGAME).get(this.INGAME_CIV_MANAGE);
    }

    protected final SliderMenu getInGame_Civ_Manage2() {
        return this.menus.get(this.INGAME).get(this.INGAME_CIV_MANAGE2);
    }

    protected final void rebuildInGame_ManageCiv2() {
        if (this.menus.get(this.INGAME).get(this.INGAME_CIV_MANAGE2).getVisible()) {
            this.menus.get(this.INGAME).set(this.INGAME_CIV_MANAGE2, new Menu_InGame_Manage_Stats());
            this.menus.get(this.INGAME).get(this.INGAME_CIV_MANAGE2).setVisible(true);
            this.setOrderOfMenu_InGame_ManageCiv_Info();
        } else {
            this.menus.get(this.INGAME).set(this.INGAME_CIV_MANAGE2, new Menu_InGame_Manage_Stats());
            this.menus.get(this.INGAME).get(this.INGAME_CIV_MANAGE2).setVisible(false);
        }
    }

    protected final void rebuildInGame_ManageCiv_Actions() {
        if (this.menus.get(this.INGAME).get(this.INGAME_CIV_MANAGE_ACTIONS).getVisible()) {
            this.menus.get(this.INGAME).set(this.INGAME_CIV_MANAGE_ACTIONS, new Menu_InGame_CivInfo_Manage_DiploActions());
            this.menus.get(this.INGAME).get(this.INGAME_CIV_MANAGE_ACTIONS).setVisible(true);
            this.setOrderOfMenu_InGame_ManageCiv_Info();
        } else {
            this.menus.get(this.INGAME).set(this.INGAME_CIV_MANAGE_ACTIONS, new Menu_InGame_CivInfo_Manage_DiploActions());
            this.menus.get(this.INGAME).get(this.INGAME_CIV_MANAGE_ACTIONS).setVisible(false);
        }
    }

    protected final void setOrderOfMenu_InGame_ManageCiv_Info() {
        if (this.menus.get(this.INGAME).get(this.INGAME_CIV_MANAGE).getVisible()) {
            this.setOrderOfMenu(this.INGAME_CIV_MANAGE);
            this.setOrderOfMenu(this.INGAME_CIV_MANAGE2);
            this.setOrderOfMenu(this.INGAME_CIV_MANAGE_ACTIONS);
            //this.setOrderOfMenu(this.INGAME_CIV_INFO_ACTIONS);
            //this.setOrderOfMenu(this.INGAME_CIV_INFO_OPINIONS);
            //this.setOrderOfMenu(this.INGAME_CIV_INFO_DECISIONS);
        }
    }

    protected final void setVisible_InGame_CivInfo_Decisions(boolean visible) {
        if (visible) {
            this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_DECISIONS).actionClose();
            this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_DECISIONS).setVisible(visible);
            this.rebuildInGame_Civ_Info_Decisions();
            this.INGAME_CIV_INFO_ACTIONS_VISIBLE = !this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_OPINIONS).getVisible();
            this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_DIPLO_ACTIONS).actionClose();
            this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_ACTIONS).actionClose();
            this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_OPINIONS).actionClose();
        } else {
            this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_DECISIONS).actionClose();
            this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_DIPLO_ACTIONS).setVisible(!visible);
            if (this.INGAME_CIV_INFO_ACTIONS_VISIBLE) {
                this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_ACTIONS).setVisible(!visible);
                this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_OPINIONS).actionClose();
            } else {
                this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_OPINIONS).setVisible(!visible);
                this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_ACTIONS).actionClose();
            }
        }
    }

    protected final boolean getVisible_InGame_CivInfo() {
        return this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO).getVisible();
    }

    protected final boolean getVisible_InGame_ManageInfo() {
        return this.menus.get(this.INGAME).get(this.INGAME_CIV_MANAGE).getVisible();
    }

    protected final SliderMenu getInGame_CivInfo() {
        return this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO);
    }

    protected final SliderMenu getInGame_Manage_Info() {
        return this.menus.get(this.INGAME).get(this.INGAME_CIV_MANAGE);
    }

    protected final SliderMenu getInGame_SendMessage() {
        return this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE);
    }

    protected final SliderMenu getInGame_Plunder() {
        return this.menus.get(this.INGAME).get(this.INGAME_PLUNDER);
    }

    protected final SliderMenu getInGame_SendMessage_TradeLEFT() {
        try {
            return this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE_TRADE_LEFT);
        }
        catch (NullPointerException nullPointerException) {
        }
        catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            // empty catch block
        }
        return null;
    }

    protected final SliderMenu getInGame_SendMessage_TradeRIGHT() {
        try {
            return this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE_TRADE_RIGHT);
        }
        catch (NullPointerException nullPointerException) {
        }
        catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            // empty catch block
        }
        return null;
    }

    protected final MenuElement getCreateCiv_Name() {
        return this.menus.get(this.CREATE_CIVILIZATION).get(this.CREATE_CIVILIZATION_TOP).getMenuElement(0);
    }

    protected final SliderMenu getInGame_CivInfo_Stats() {
        return this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO2);
    }

    protected final boolean getInMainMenu() {
        return this.viewID == this.MAINMENU;
    }

    protected final boolean getInAboutMenu() {
        return this.viewID == this.ABOUT;
    }

    protected final boolean getInInitMenu() {
        return this.viewID == this.INIT_GAME;
    }

    protected final boolean getInLoadMap() {
        return this.viewID == this.LOAD_MAP;
    }

    protected final boolean getInLoadSave() {
        return this.viewID == this.LOAD_SAVE;
    }

    protected final boolean getInSelectMapType() {
        return this.viewID == this.SELECT_MAP_TYPE;
    }

    protected final boolean getInSelectCiv() {
        return this.viewID == this.SELECT_CIVILIZATION;
    }

    protected final boolean getInGame_Formable_Civ_Provinces() {
        return this.viewID == this.INGAME_FORMABLE_CIV_PROVINCES;
    }

    protected final boolean getInGame_PeaceTreaty() {
        return this.viewID == this.INGAME_PEACE_TREATY;
    }

    protected final boolean getInGame_PeaceTreaty_Response() {
        return this.viewID == this.INGAME_PEACE_TREATY_RESPONSE;
    }

    protected final boolean getInGame_CreateAVassal() {
        return this.viewID == this.INGAME_CREATE_VASSAL;
    }

    protected final boolean getInGame_Timeline() {
        return this.viewID == this.INGAME_TIMELINE;
    }

    protected final void getInGame_Timeline_UpdateLanguage() {
        this.menus.get(this.INGAME_TIMELINE).get(0).updateLanguage();
    }

    protected final void getInGame_Victory_UpdateLanguage() {
        this.menus.get(this.VICTORY).get(0).updateLanguage();
    }

    protected final boolean getInGame_SelectProvinces() {
        return this.viewID == this.INGAME_SELECT_PROVINCES;
    }

    protected final boolean getInGame_ShowProvinces() {
        return this.viewID == this.INGAME_SHOW_PROVINCES;
    }

    protected final boolean getInGame_TradeSelectCiv() {
        return this.viewID == this.INGAME_TRADE_SELECT_CIV;
    }

    protected final boolean getInGame_FormAnimation() {
        return this.viewID == this.INGAME_FORM_ANIMATION;
    }

    protected final boolean getInNewGamePlayers() {
        return this.viewID == this.NEWGAME_PLAYERS;
    }

    protected final boolean getInCreateNewGame() {
        return this.viewID == this.CREATE_NEW_GAME;
    }

    protected final boolean getInCreateScenario_Civilizations() {
        return this.viewID == this.CREATE_SCENARIO_CIVILIZATIONS;
    }

    protected final boolean getInCreateScenario_Civilizations_Select() {
        return this.viewID == this.CREATE_SCENARIO_CIVILIZATIONS_SELECT;
    }

    protected final boolean getInUnions_AddCiv() {
        return this.viewID == this.EDITOR_UNIONS_ADDCIV;
    }

    protected final boolean getInCreateScenario_Cores_AddCore() {
        return this.viewID == this.CREATE_SCENARIO_CORES_ADD_CORE;
    }

    protected final boolean getInCreateScenario_Events_SelectProvinces() {
        return this.viewID == this.CREATE_SCENARIO_EVENTS_SELECT_PROVINCES;
    }

    protected final boolean getInCreateScenario_Events_SelectLeader() {
        return this.viewID == this.CREATE_SCENARIO_EVENTS_OUT_SELECT_LEADER;
    }

    protected final boolean getInCreateScenario_Cores_AddCiv() {
        return this.viewID == this.CREATE_SCENARIO_CORES_ADD_CIV;
    }

    protected final boolean getInCreateVassal_Select() {
        return this.viewID == this.INGAME_CREATE_VASSAL_SELECT_CIV;
    }

    protected final boolean getInRandomGame_Civilizations_Select() {
        return this.viewID == this.CREATE_RANDOM_GAME_CIVILIZATIONS_SELECT;
    }

    protected final boolean getInCreateScenario_Events_SelectCiv() {
        return this.viewID == this.CREATE_SCENARIO_EVENTS_SELECT_CIV;
    }

    protected final boolean getInCreateScenario_Events_AddCiv() {
        return this.viewID == this.CREATE_SCENARIO_EVENTS_ADD_CIV;
    }

    protected final boolean getInCreateScenario_Assign() {
        return this.viewID == this.CREATE_SCENARIO_ASSIGN;
    }

    protected final boolean getInCustomizeAlliance() {
        return this.viewID == this.CUSTOMIZE_ALLIANCE;
    }

    protected final boolean getInManageDiplomacy_Relations_Interactive() {
        return this.menus.get(this.MANAGE_DIPLOMACY).get(this.MANAGE_DIPLOMACY_RELATIONS_INTERACTIVE).getVisible();
    }

    protected final boolean getInManageDiplomacy_Vassals() {
        try {
            return this.menus.get(this.MANAGE_DIPLOMACY).get(this.MANAGE_DIPLOMACY_VASSALS).getVisible();
        }
        catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    protected final boolean getInManageDiplomacy_Pacts3() {
        try {
            return this.menus.get(this.MANAGE_DIPLOMACY).get(this.MANAGE_DIPLOMACY_PACTS2).getVisible();
        }
        catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    protected final boolean getInManageDiplomacy_Truces() {
        try {
            return this.menus.get(this.MANAGE_DIPLOMACY).get(this.MANAGE_DIPLOMACY_TRUCES).getVisible();
        }
        catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    protected final boolean getInManageDiplomacy_Guarantee() {
        try {
            return this.menus.get(this.MANAGE_DIPLOMACY).get(this.MANAGE_DIPLOMACY_GUARANTEE).getVisible();
        }
        catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    protected final boolean getInManageDiplomacy_DefensivePact() {
        try {
            return this.menus.get(this.MANAGE_DIPLOMACY).get(this.MANAGE_DIPLOMACY_DEFENSIVE).getVisible();
        }
        catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    protected final boolean getInManageDiplomacy_MilitaryAccess() {
        try {
            return this.menus.get(this.MANAGE_DIPLOMACY).get(this.MANAGE_DIPLOMACY_MILITARY_ACCESS).getVisible();
        }
        catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    protected final boolean getInManageDiplomacy_Alliances() {
        return this.menus.get(this.MANAGE_DIPLOMACY).get(this.MANAGE_DIPLOMACY_ALLIANCES).getVisible();
    }

    protected final boolean getInCreateScenario_SetUpArmy() {
        return this.viewID == this.CREATE_SCENARIO_SET_UP_ARMY;
    }

    protected final boolean getInCreateScenario_Preview() {
        return this.viewID == this.CREATE_SCENARIO_PREVIEW;
    }

    protected final boolean getInCreateScenario_TechnologyLevels() {
        return this.viewID == this.CREATE_SCENARIO_TECHNOLOGY_LEVELS;
    }

    protected final boolean getInCreateScenario_Happiness() {
        return this.viewID == this.CREATE_SCENARIO_HAPPINESS;
    }

    protected final boolean getInCreateScenario_StartingMoney() {
        return this.viewID == this.CREATE_SCENARIO_STARTING_MONEY;
    }

    protected final boolean getInCreateScenario_Cores() {
        return this.viewID == this.CREATE_SCENARIO_CORES;
    }

    protected final boolean getInCreateScenario_Assign_Select() {
        return this.viewID == this.CREATE_SCENARIO_ASSIGN_SELECT;
    }

    protected final boolean getInPrintAMap() {
        return this.viewID == this.PRINT_A_MAP;
    }

    protected final boolean getInCreateScenario_WastelandMap() {
        return this.viewID == this.CREATE_SCENARIO_WASTELAND;
    }

    protected final boolean getInCreateScenario_Events() {
        return this.viewID == this.CREATE_SCENARIO_EVENTS;
    }

    //visibility check for CFG-event searching connection
    protected final boolean getVisibleCreateScenario_Events_List() {
        try {
            return this.menus.get(this.CREATE_SCENARIO_EVENTS).get(this.CREATE_SCENARIO_EVENTS_LIST).getVisible() && !this.menus.get(this.CREATE_SCENARIO_EVENTS).get(this.CREATE_SCENARIO_EVENTS_EDIT).getVisible();
        }
        catch (IndexOutOfBoundsException | NullPointerException ex) {
            return false;
        }
    }

    //search method for CFG-event searching connection
    protected final void getInCreateScenario_Events_List() {
        if (CFG.sSearch == null) {
            this.menus.get(this.CREATE_SCENARIO_EVENTS).get(this.CREATE_SCENARIO_EVENTS_LIST).getMenuElement(0).setText("");
        } else {
            this.menus.get(this.CREATE_SCENARIO_EVENTS).get(this.CREATE_SCENARIO_EVENTS_LIST).getMenuElement(0).setText(CFG.sSearch);
        }
    }

    protected final boolean getInSelectLanguage() {
        return this.viewID == this.SELECT_LANGUAGE;
    }

    protected final void getInSelectLanguage_OnBackPressed() {
        this.menus.get(this.SELECT_LANGUAGE).get(0).onBackPressed();
    }

    protected final boolean getInSettingsProvince() {
        return this.viewID == this.SETTINGS_PROVINCE;
    }

    protected final boolean getInSettings() {
        return this.viewID == this.SETTINGS;
    }

    protected final boolean getInCreateScenario_Available_Provinces() {
        return this.viewID == this.CREATE_SCENARIO_AVAILABLE_PROVINCES;
    }

    protected final boolean getInStartGameMenu() {
        return this.viewID == this.START_THE_GAME;
    }

    protected final boolean getInEndGameMenu() {
        return this.viewID == this.END_THE_GAME;
    }

    protected final boolean getInManageDiplomacy() {
        return this.viewID == this.MANAGE_DIPLOMACY;
    }

    protected final boolean getInGameEditor_Regions() {
        return this.viewID == this.GAME_EDITOR_REGIONS;
    }

    protected final boolean getInSelectAvailableCivilizations() {
        return this.viewID == this.SELECT_AVAILABLE_CIVILIZATIONS;
    }

    protected final boolean getInChooseScenario() {
        return this.viewID == this.CHOOSE_SCENARIO;
    }

    protected final boolean getInNextPlayerTurn() {
        return this.viewID == this.NEXT_PLAYER_TURN;
    }

    protected final boolean getInVictory() {
        return this.viewID == this.VICTORY;
    }

    protected final boolean getInGame_CivlizationView() {
        return this.viewID == this.INGAME_CIV_VIEW;
    }

    protected final boolean getInCreateCivilization() {
        return this.viewID == this.CREATE_CIVILIZATION;
    }

    protected final boolean getInCreateCity() {
        return this.viewID == this.CREATE_CITY;
    }

    protected final boolean getInRandomGame() {
        return this.viewID == this.CREATE_RANDOM_GAME;
    }

    protected final boolean getInCreateScenario_PalletOfColors() {
        return this.viewID == this.CREATE_SCENARIO_PALLET_OF_COLORS;
    }

    protected final boolean getInMapEditor_Create_NewContinent() {
        return this.viewID == this.MAP_EDITOR_CREATE_NEW_CONTINENT;
    }

    protected final boolean getInMapEditor_Create_NewRegion() {
        return this.viewID == this.MAP_EDITOR_CREATE_NEW_REGION;
    }

    protected final boolean getInGameEditor_Create_DiplomacyPackage() {
        return this.viewID == this.GAME_EDITOR_DIPLOMACY_COLORS_PACKAGES_CREATE;
    }

    protected final boolean getInGameEditor_ReligionAdd() {
        return this.viewID == this.EDITOR_RELIGION_ADD;
    }

    protected final boolean getInGameEditor_TerrainAdd() {
        return this.viewID == this.TERRAIN_TYPE_ADD;
    }

    protected final boolean getInPalletOfCivsColorsEdit() {
        return this.viewID == this.GAME_EDITOR_PALLETS_OF_CIVS_COLORS_PACKAGES_EDIT;
    }

    protected final boolean getInMapEditor_Terrain() {
        return this.viewID == this.MAP_EDITOR_TERRAIN;
    }

    protected final boolean getInMapEditor_Connections() {
        return this.viewID == this.MAP_EDITOR_CONNECTIONS || this.viewID == this.MAP_EDITOR_UPDATE_PROVINCE_DATA;
    }

    protected final boolean getInMapEditor_FormableCivs_Edit() {
        return this.viewID == this.MAP_EDITOR_FORMABLE_CIVS_EDIT;
    }

    protected final boolean getInMapEditor_FormableCivs_SelectFormable() {
        return this.viewID == this.MAP_EDITOR_FORMABLE_CIVS_SELECT_FORMABLE;
    }

    protected final boolean getInCreateScenario_HolyRomanEmpire() {
        return this.viewID == this.CREATE_SCENARIO_HOLY_ROMAN_EMPIRE;
    }

    protected final boolean getInGameCivs() {
        return this.viewID == this.EDITOR_GAME_CIVS;
    }

    protected final boolean getInMapEditor_FormableCivs_SelectClaimant() {
        return this.viewID == this.MAP_EDITOR_FORMABLE_CIVS_SELECT_CLAIMANT;
    }

    protected final boolean getInLeader_Edit_SelectCivs() {
        return this.viewID == this.GAME_LEADERS_EDIT_SELECT_CIVS;
    }

    protected final boolean getInRandom_Leader_Edit_SelectCivs() {
        return this.viewID == this.GAME_RANDOM_LEADERS_EDIT_SELECT_CIVS;
    }

    protected final boolean getInMapEditor_LoadSuggestedOwners() {
        return this.viewID == this.LOAD_GENERATE_SUGGESTED_OWNERS;
    }

    protected final boolean getInMapEditor_LoadPreDefinedBorders() {
        return this.viewID == this.LOAD_GENERATE_PRE_DEFINED_BORDERS;
    }

    protected final boolean getCreateScenario_ScenarioAge() {
        return this.viewID == this.SCENARIO_AGE;
    }

    protected final boolean getInMapEditor_ProvinceBackground() {
        return this.viewID == this.MAP_EDITOR_PROVINCE_BACKGROUND;
    }

    protected final boolean getInMapEditor_SeaProvinces() {
        return this.viewID == this.MAP_EDITOR_SEA_PROVINCES;
    }

    protected final boolean getInMapEditor_Continents() {
        return this.viewID == this.MAP_EDITOR_CONTINENTS;
    }

    protected final boolean getInMapEditor_Regions() {
        return this.viewID == this.MAP_EDITOR_REGIONS;
    }

    protected final boolean getInMapEditor_GrowthRate() {
        return this.viewID == this.MAP_EDITOR_GROWTH_RATE;
    }

    protected final boolean getInMapEditor_TradeZones() {
        return this.viewID == this.MAP_EDITOR_TRADE_ZONES;
    }

    protected final boolean getInMapEditor_TradeZones_Edit() {
        return this.viewID == this.MAP_EDITOR_TRADE_ZONES_EDIT;
    }

    protected final boolean getInMapEditor_ArmyPosition() {
        return this.viewID == this.MAP_EDITOR_ARMY_POSITION;
    }

    protected final boolean getInMapEditor_PortPosition() {
        return this.viewID == this.MAP_EDITOR_PORT_POSITION;
    }

    protected final boolean getInMapEditor_ArmySeaBoxes() {
        return this.viewID == this.MAP_EDITOR_ARMY_SEA_BOXES;
    }

    protected final boolean getInMapEditor_ArmySeaBoxes_Edit() {
        return this.viewID == this.MAP_EDITOR_ARMY_SEA_BOXES_EDIT;
    }

    protected final boolean getInMapEditor_ArmySeaBoxes_Add() {
        return this.viewID == this.MAP_EDITOR_ARMY_SEA_BOXES_ADD;
    }

    protected final boolean getInMapEditor_FormableCivs() {
        return this.viewID == this.MAP_EDITOR_FORMABLE_CIVS;
    }

    protected final boolean getInLeaders() {
        return this.viewID == this.GAME_LEADERS;
    }

    protected final boolean getInRandom_Leaders() {
        return this.viewID == this.GAME_RANDOM_LEADERS;
    }

    protected final boolean getInDownloadPallets() {
        return this.viewID == this.DOWNLOAD_PALLETS;
    }

    protected final boolean getInEditor_GameCivs() {
        return this.viewID == this.EDITOR_GAME_CIVS_EDIT;
    }

    protected final boolean getInMapEditor_WastelandMaps_Edit() {
        return this.viewID == this.MAP_EDITOR_WASTELAND_MAPS_EDIT;
    }

    protected final void rebuildCreateCiv_Data() {
        this.menus.get(this.CREATE_CIVILIZATION).set(this.CREATE_CIVILIZATION_DATA, new Menu_CreateCiv_Data());
    }

    protected final boolean getVisibleCreateCiv_Data() {
        try {
            return this.menus.get(this.CREATE_CIVILIZATION).get(this.CREATE_CIVILIZATION_DATA).getVisible();
        }
        catch (IndexOutOfBoundsException ex) {
            return false;
        }
        catch (NullPointerException ex) {
            return false;
        }
    }

    protected final void rebuildInGame_Messages() {
        this.menus.get(this.INGAME).set(this.INGAME_MESSAGES, new Menu_InGame_Messages());
    }

    protected final SliderMenu getInGame_Messages() {
        return this.menus.get(this.INGAME).get(this.INGAME_MESSAGES);
    }

    protected final void setVisibleInGame_Messages(boolean visible) {
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGES).setVisible(visible);
    }

    protected final void setVisibleInGame_MessageView(boolean visible) {
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setVisible(visible);
    }

    protected final boolean getVisibleInGame_HRE() {
        return this.menus.get(this.INGAME).get(this.INGAME_HRE).getVisible();
    }

    protected final boolean getVisibleInGame_HRE_VoteFor() {
        return this.menus.get(this.INGAME).get(this.INGAME_HRE_VOTE).getVisible();
    }

    protected final void setVisibleInGame_HRE(boolean visible) {
        this.menus.get(this.INGAME).get(this.INGAME_HRE).setVisible(visible);
    }

    protected final void rebuildInGame_HRE() {
        this.menus.get(this.INGAME).get(this.INGAME_HRE).setVisible(false);
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_HRE).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_HRE).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_HRE).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_HRE).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_HRE, new Menu_InGame_HRE(0));
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_HRE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_HRE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_HRE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_HRE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_HRE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_HRE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_HRE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_HRE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < (this.menus.get(this.INGAME).get(this.INGAME_HRE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_HRE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_HRE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_HRE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) / 2) {
            tHeight = (this.menus.get(this.INGAME).get(this.INGAME_HRE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_HRE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_HRE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_HRE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) / 2;
        }
        this.menus.get(this.INGAME).get(this.INGAME_HRE).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_HRE).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_HRE).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_HRE).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_HRE).setVisible(true);
        this.setOrderOfMenu(this.INGAME_HRE);
        this.menus.get(this.INGAME).get(this.INGAME_HRE).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_HRE_VoteFor() {
        this.menus.get(this.INGAME).get(this.INGAME_HRE_VOTE).setVisible(false);
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_HRE_VOTE).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_HRE_VOTE).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_HRE_VOTE).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_HRE_VOTE).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_HRE_VOTE, new Menu_InGame_HRE_VoteFor(0));
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_HRE_VOTE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_HRE_VOTE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_HRE_VOTE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_HRE_VOTE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_HRE_VOTE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_HRE_VOTE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_HRE_VOTE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_HRE_VOTE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < (this.menus.get(this.INGAME).get(this.INGAME_HRE_VOTE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_HRE_VOTE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_HRE_VOTE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_HRE_VOTE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) / 2) {
            tHeight = (this.menus.get(this.INGAME).get(this.INGAME_HRE_VOTE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_HRE_VOTE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_HRE_VOTE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_HRE_VOTE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) / 2;
        }
        this.menus.get(this.INGAME).get(this.INGAME_HRE_VOTE).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_HRE_VOTE).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_HRE_VOTE).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_HRE_VOTE).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_HRE_VOTE).setVisible(true);
        this.setOrderOfMenu(this.INGAME_HRE_VOTE);
        this.menus.get(this.INGAME).get(this.INGAME_HRE_VOTE).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_Message_Alliance(int iOnCivID, int iMessageID, int iValue) {
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setVisible(false);
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_MESSAGE, new Menu_InGame_Message_Alliance(iOnCivID, iMessageID, iValue));
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setVisible(true);
        this.setOrderOfMenu(this.INGAME_MESSAGE);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_Message_NonAggression(int iOnCivID, int iMessageID, int iValue) {
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setVisible(false);
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_MESSAGE, new Menu_InGame_Message_NonAggressionPact(iOnCivID, iMessageID, iValue));
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setVisible(true);
        this.setOrderOfMenu(this.INGAME_MESSAGE);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_Message_OfferVasalization(int iOnCivID, int iMessageID, int iValue) {
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setVisible(false);
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_MESSAGE, new Menu_InGame_Message_OfferVasalization(iOnCivID, iMessageID, iValue));
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setVisible(true);
        this.setOrderOfMenu(this.INGAME_MESSAGE);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_Message_MilitaryAccess_Ask(int iOnCivID, int iMessageID, int iValue) {
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setVisible(false);
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_MESSAGE, new Menu_InGame_Message_MilitaryAccess_Ask(iOnCivID, iMessageID, iValue));
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setVisible(true);
        this.setOrderOfMenu(this.INGAME_MESSAGE);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_Message_MilitaryAccess_Give(int iOnCivID, int iMessageID, int iValue) {
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setVisible(false);
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_MESSAGE, new Menu_InGame_Message_MilitaryAccess_Give(iOnCivID, iMessageID, iValue));
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setVisible(true);
        this.setOrderOfMenu(this.INGAME_MESSAGE);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_Message_Independence_Ask(int iOnCivID, int iMessageID, int iValue) {
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setVisible(false);
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_MESSAGE, new Menu_InGame_Message_Independence_Ask(iOnCivID, iMessageID, iValue));
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setVisible(true);
        this.setOrderOfMenu(this.INGAME_MESSAGE);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_Message_DefensivePact(int iOnCivID, int iMessageID, int iValue) {
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setVisible(false);
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_MESSAGE, new Menu_InGame_Message_DefensivePact(iOnCivID, iMessageID, iValue));
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setVisible(true);
        this.setOrderOfMenu(this.INGAME_MESSAGE);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_Message_CallToArms(int iOnCivID, int iMessageID, int iValue) {
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setVisible(false);
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_MESSAGE, new Menu_InGame_Message_CallToArms(iOnCivID, iMessageID, iValue));
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setVisible(true);
        this.setOrderOfMenu(this.INGAME_MESSAGE);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_Message_TransferControl(int iOnCivID, int iMessageID, int iValue) {
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setVisible(false);
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_MESSAGE, new Menu_InGame_Message_TransferControl(iOnCivID, iMessageID, iValue));
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setVisible(true);
        this.setOrderOfMenu(this.INGAME_MESSAGE);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_Message_PrepareForWar(int iOnCivID, int iMessageID, int iValue, int iValue2) {
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setVisible(false);
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_MESSAGE, new Menu_InGame_Message_PrepareForWar(iOnCivID, iMessageID, iValue, iValue2));
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setVisible(true);
        this.setOrderOfMenu(this.INGAME_MESSAGE);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_PrepareForWar(int iOnCivID, int iValue, int iValue2) {
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setVisible(false);
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_MESSAGE, new Menu_InGame_WarPreparations(iOnCivID, iValue, iValue2));
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setVisible(true);
        this.setOrderOfMenu(this.INGAME_MESSAGE);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_Message_CallToArms_Denied(int iOnCivID, int iMessageID, int iValue) {
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setVisible(false);
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_MESSAGE, new Menu_InGame_Message_CallToArms_Denied(iOnCivID, iMessageID, iValue));
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setVisible(true);
        this.setOrderOfMenu(this.INGAME_MESSAGE);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_Message_Gift(int iOnCivID, int iMessageID, int iValue) {
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setVisible(false);
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_MESSAGE, new Menu_InGame_Message_Gift(iOnCivID, iMessageID, iValue));
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setVisible(true);
        this.setOrderOfMenu(this.INGAME_MESSAGE);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_Message_TradeRequest(int iOnCivID, int iMessageID, TradeRequest_GameData tradeRequest) {
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setVisible(false);
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_MESSAGE, new Menu_InGame_Message_TradeRequest(iOnCivID, iMessageID, tradeRequest));
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setVisible(true);
        this.setOrderOfMenu(this.INGAME_MESSAGE);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_Message_Ultimatum(int iOnCivID, int iMessageID, int iValue, Ultimatum_GameData nUltimatum) {
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setVisible(false);
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_MESSAGE, new Menu_InGame_Message_Ultimatum(iOnCivID, iMessageID, iValue, nUltimatum));
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setVisible(true);
        this.setOrderOfMenu(this.INGAME_MESSAGE);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_Message_UltimatumRefused(int iOnCivID, int iMessageID, int iValue) {
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setVisible(false);
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_MESSAGE, new Menu_InGame_Message_UltimatumRefused(iOnCivID, iMessageID, iValue));
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setVisible(true);
        this.setOrderOfMenu(this.INGAME_MESSAGE);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_Message_DeclarationOfIndependence_ByVassal(int iOnCivID, int iMessageID, int iValue) {
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setVisible(false);
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_MESSAGE, new Menu_InGame_Message_DeclarationOfIndependence_ByVassal(iOnCivID, iMessageID, iValue));
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setVisible(true);
        this.setOrderOfMenu(this.INGAME_MESSAGE);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_Message_Union(int iOnCivID, int iMessageID, int iValue) {
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setVisible(false);
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_MESSAGE, new Menu_InGame_Message_Union(iOnCivID, iMessageID, iValue));
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).setVisible(true);
        this.setOrderOfMenu(this.INGAME_MESSAGE);
        this.menus.get(this.INGAME).get(this.INGAME_MESSAGE).updateMenuElements_IsInView();
    }

    protected final void rebuildCreateScenario_HolyRomanEmpire_Princes() {
        this.menus.get(this.CREATE_SCENARIO_HOLY_ROMAN_EMPIRE).set(this.CREATE_SCENARIO_HOLY_ROMAN_EMPIRE_PRINCES, new Menu_CreateScenario_HRE_Princes());
    }

    protected final void rebuildCreateCiv_Flag() {
        this.menus.get(this.CREATE_CIVILIZATION).set(this.CREATE_CIVILIZATION_FLAG, new Menu_CreateCiv_Flag());
        this.menus.get(this.CREATE_CIVILIZATION).get(this.CREATE_CIVILIZATION_FLAG).setVisible(true);
    }

    protected final void rebuildCreateScenario_AssignCiv() {
    }

    protected final void rebuildCreateScenario_Civilizations_SelectList() {
        this.menus.get(this.CREATE_SCENARIO_CIVILIZATIONS_SELECT).get(this.CREATE_SCENARIO_CIVILIZATIONS_SELECT_LIST).onBackPressed();
        this.menus.get(this.CREATE_SCENARIO_CIVILIZATIONS_SELECT).set(this.CREATE_SCENARIO_CIVILIZATIONS_SELECT_LIST, new Menu_CreateScenario_Civilizations_Select_List());
    }

    protected final void rebuildUnions_AddCiv_List() {
        this.menus.get(this.EDITOR_UNIONS_ADDCIV).get(this.EDITOR_UNIONS_ADDCIV_LIST).onBackPressed();
        this.menus.get(this.EDITOR_UNIONS_ADDCIV).set(this.EDITOR_UNIONS_ADDCIV_LIST, new Menu_Editor_Unions_AddCiv_List());
    }

    protected final void rebuildCreateScenario_Cores_AddCore_List() {
        this.menus.get(this.CREATE_SCENARIO_CORES_ADD_CORE).get(this.CREATE_SCENARIO_CORES_ADD_CORE_LIST).onBackPressed();
        this.menus.get(this.CREATE_SCENARIO_CORES_ADD_CORE).set(this.CREATE_SCENARIO_CORES_ADD_CORE_LIST, new Menu_CreateScenario_Cores_AddCore_List());
    }

    protected final void rebuildCreateScenario_Cores_AddCiv_List() {
        this.menus.get(this.CREATE_SCENARIO_CORES_ADD_CIV).get(this.CREATE_SCENARIO_CORES_ADD_CIV_LIST).onBackPressed();
        this.menus.get(this.CREATE_SCENARIO_CORES_ADD_CIV).set(this.CREATE_SCENARIO_CORES_ADD_CIV_LIST, new Menu_CreateScenario_Cores_AddCiv_List());
    }

    protected final void rebuildCreateVassal_SelectList() {
        this.menus.get(this.INGAME_CREATE_VASSAL_SELECT_CIV).get(this.INGAME_CREATE_VASSAL_SELECT_CIV_LIST).onBackPressed();
        this.menus.get(this.INGAME_CREATE_VASSAL_SELECT_CIV).set(this.INGAME_CREATE_VASSAL_SELECT_CIV_LIST, new Menu_InGame_CreateAVassal_SelectCiv_List());
    }

    protected final void rebuildMapEditor_FormableCivs_SelectList() {
        this.menus.get(this.MAP_EDITOR_FORMABLE_CIVS_SELECT_FORMABLE).get(this.MAP_EDITOR_FORMABLE_CIVS_SELECT_FORMABLE_LIST).onBackPressed();
        this.menus.get(this.MAP_EDITOR_FORMABLE_CIVS_SELECT_FORMABLE).set(this.MAP_EDITOR_FORMABLE_CIVS_SELECT_FORMABLE_LIST, new Menu_MapEditor_FormableCivs_SelectFormable_List());
    }

    protected final void rebuildGameCivs_SelectList() {
        this.menus.get(this.EDITOR_GAME_CIVS).get(1).setVisible(false);
        this.menus.get(this.EDITOR_GAME_CIVS).set(1, new Menu_Editor_GameCivs());
    }

    protected final void rebuildMapEditor_FormableCivs() {
        this.menus.get(this.MAP_EDITOR_FORMABLE_CIVS).get(1).setVisible(false);
        this.menus.get(this.MAP_EDITOR_FORMABLE_CIVS).set(1, new Menu_MapEditor_FormableCivs_Options());
    }

    protected final void rebuildLeaders() {
        this.menus.get(this.GAME_LEADERS).get(1).setVisible(false);
        this.menus.get(this.GAME_LEADERS).set(1, new Menu_Leaders_Options());
    }

    protected final void rebuildRandom_Leaders() {
        this.menus.get(this.GAME_RANDOM_LEADERS).get(1).setVisible(false);
        this.menus.get(this.GAME_RANDOM_LEADERS).set(1, new Menu_Random_Leaders_Options());
    }

    protected final void rebuildSelect_Leaders() {
        this.menus.get(this.CREATE_SCENARIO_EVENTS_OUT_SELECT_LEADER).get(1).setVisible(false);
        this.menus.get(this.CREATE_SCENARIO_EVENTS_OUT_SELECT_LEADER).set(1, new Menu_CreateScenario_Events_Out_Select_Leader_List());
    }

    protected final void rebuildMapEditor_FormableCivs_SelectClaimantList() {
        this.menus.get(this.MAP_EDITOR_FORMABLE_CIVS_SELECT_CLAIMANT).get(this.MAP_EDITOR_FORMABLE_CIVS_SELECT_CLAIMANT_LIST).onBackPressed();
        this.menus.get(this.MAP_EDITOR_FORMABLE_CIVS_SELECT_CLAIMANT).set(this.MAP_EDITOR_FORMABLE_CIVS_SELECT_CLAIMANT_LIST, new Menu_MapEditor_FormableCivs_SelectClaimant_List());
    }

    protected final void rebuildLeader_Edit_SelectCivs_List() {
        this.menus.get(this.GAME_LEADERS_EDIT_SELECT_CIVS).get(this.GAME_LEADERS_EDIT_SELECT_CIVS_LIST).onBackPressed();
        this.menus.get(this.GAME_LEADERS_EDIT_SELECT_CIVS).set(this.GAME_LEADERS_EDIT_SELECT_CIVS_LIST, new Menu_Leader_Edit_SelectCiv_List());
    }

    protected final void rebuildRandom_Leader_Edit_SelectCivs_List() {
        this.menus.get(this.GAME_RANDOM_LEADERS_EDIT_SELECT_CIVS).get(this.GAME_RANDOM_LEADERS_EDIT_SELECT_CIVS_LIST).onBackPressed();
        this.menus.get(this.GAME_RANDOM_LEADERS_EDIT_SELECT_CIVS).set(this.GAME_RANDOM_LEADERS_EDIT_SELECT_CIVS_LIST, new Menu_Random_Leader_Edit_SelectCiv_List());
    }

    protected final void rebuildRandom_Leader_Edit_SelectAIType_List() {
        this.menus.get(this.GAME_RANDOM_LEADERS_EDIT_SELECT_AITYPE).get(this.GAME_RANDOM_LEADERS_EDIT_SELECT_AITYPE_LIST).onBackPressed();
        this.menus.get(this.GAME_RANDOM_LEADERS_EDIT_SELECT_AITYPE).set(this.GAME_RANDOM_LEADERS_EDIT_SELECT_AITYPE_LIST, new Menu_Random_Leader_Edit_SelectAIType_List());
    }

    protected final void rebuildMapEditor_FormableCivs_Claimants() {
        this.menus.get(this.MAP_EDITOR_FORMABLE_CIVS_EDIT).get(this.MAP_EDITOR_FORMABLE_CIVS_EDIT_CLAIMANTS).onBackPressed();
        this.menus.get(this.MAP_EDITOR_FORMABLE_CIVS_EDIT).set(this.MAP_EDITOR_FORMABLE_CIVS_EDIT_CLAIMANTS, new Menu_MapEditor_FormableCivs_Claimants());
    }

    protected final void rebuildLeaders_Edit_Civs() {
        this.menus.get(this.GAME_LEADERS_EDIT).get(this.GAME_LEADERS_EDIT_CIVS).onBackPressed();
        this.menus.get(this.GAME_LEADERS_EDIT).set(this.GAME_LEADERS_EDIT_CIVS, new Menu_Leader_Edit_Civs());
    }

    protected final void rebuildRandom_Leaders_Edit_Civs() {
        this.menus.get(this.GAME_RANDOM_LEADERS_EDIT).get(this.GAME_RANDOM_LEADERS_EDIT_CIVS).onBackPressed();
        this.menus.get(this.GAME_RANDOM_LEADERS_EDIT).set(this.GAME_RANDOM_LEADERS_EDIT_CIVS, new Menu_Random_Leader_Edit_Civs());
    }

    protected final void rebuildRandom_Leaders_Edit_AIType() {
        this.menus.get(this.GAME_RANDOM_LEADERS_EDIT).get(this.GAME_RANDOM_LEADERS_EDIT_AITYPE).onBackPressed();
        this.menus.get(this.GAME_RANDOM_LEADERS_EDIT).set(this.GAME_RANDOM_LEADERS_EDIT_AITYPE, new Menu_Random_Leader_Edit_AIType());
    }

    protected final void rebuildRandomGame_Civilizations_SelectList() {
        this.menus.get(this.CREATE_RANDOM_GAME_CIVILIZATIONS_SELECT).get(this.CREATE_RANDOM_GAME_CIVILIZATIONS_SELECT_LIST).onBackPressed();
        this.menus.get(this.CREATE_RANDOM_GAME_CIVILIZATIONS_SELECT).set(this.CREATE_RANDOM_GAME_CIVILIZATIONS_SELECT_LIST, new Menu_RandomGame_Civilizations_Select_List());
    }

    //new refresh for CFG/events search
    protected final void rebuildCreateScenario_Events_List() {
        this.setVisibleCreateScenario_Events_Edit(false);
        //this.menus.get(this.CREATE_SCENARIO_EVENTS).set(0, new Menu_CreateScenario_Events());
        this.menus.get(this.CREATE_SCENARIO_EVENTS).set(this.CREATE_SCENARIO_EVENTS_LIST, new Menu_CreateScenario_Events_List());
        //this.menus.get(this.CREATE_SCENARIO_EVENTS).set(this.CREATE_SCENARIO_EVENTS_EDIT, new Menu_CreateScenario_Events_Edit());
    }

    protected final void rebuildCreateScenario_Events_SelectCiv_List() {
        this.menus.get(this.CREATE_SCENARIO_EVENTS_SELECT_CIV).get(this.CREATE_SCENARIO_EVENTS_SELECT_CIV_LIST).onBackPressed();
        this.menus.get(this.CREATE_SCENARIO_EVENTS_SELECT_CIV).set(this.CREATE_SCENARIO_EVENTS_SELECT_CIV_LIST, new Menu_CreateScenario_Events_SelectCiv_List());
    }

    protected final void rebuildCreateScenario_Events_AddCiv_List() {
        this.menus.get(this.CREATE_SCENARIO_EVENTS_ADD_CIV).get(this.CREATE_SCENARIO_EVENTS_ADD_CIV_LIST).onBackPressed();
        this.menus.get(this.CREATE_SCENARIO_EVENTS_ADD_CIV).set(this.CREATE_SCENARIO_EVENTS_ADD_CIV_LIST, new Menu_CreateScenario_Events_AddCiv_List());
    }

    protected final void getCreateScenario_Civilizations_SelectAlphabet() {
        if (CFG.sSearch == null) {
            this.menus.get(this.CREATE_SCENARIO_CIVILIZATIONS_SELECT).get(this.CREATE_SCENARIO_CIVILIZATIONS_SELECT_ALPHABET).getMenuElement(0).setText("");
        } else {
            this.menus.get(this.CREATE_SCENARIO_CIVILIZATIONS_SELECT).get(this.CREATE_SCENARIO_CIVILIZATIONS_SELECT_ALPHABET).getMenuElement(0).setText(CFG.sSearch);
        }
    }

    protected final void getUnions_AddCiv_Alphabet() {
        if (CFG.sSearch == null) {
            this.menus.get(this.EDITOR_UNIONS_ADDCIV).get(this.EDITOR_UNIONS_ADDCIV_ALPHABET).getMenuElement(0).setText("");
        } else {
            this.menus.get(this.EDITOR_UNIONS_ADDCIV).get(this.EDITOR_UNIONS_ADDCIV_ALPHABET).getMenuElement(0).setText(CFG.sSearch);
        }
    }

    protected final void getCreateScenario_Cores_AddCore_Alphabet() {
        if (CFG.sSearch == null) {
            this.menus.get(this.CREATE_SCENARIO_CORES_ADD_CORE).get(this.CREATE_SCENARIO_CORES_ADD_CORE_ALPHABET).getMenuElement(0).setText("");
        } else {
            this.menus.get(this.CREATE_SCENARIO_CORES_ADD_CORE).get(this.CREATE_SCENARIO_CORES_ADD_CORE_ALPHABET).getMenuElement(0).setText(CFG.sSearch);
        }
    }

    protected final void getCreateScenario_Cores_AddCiv_Alphabet() {
        if (CFG.sSearch == null) {
            this.menus.get(this.CREATE_SCENARIO_CORES_ADD_CIV).get(this.CREATE_SCENARIO_CORES_ADD_CIV_ALPHABET).getMenuElement(0).setText("");
        } else {
            this.menus.get(this.CREATE_SCENARIO_CORES_ADD_CIV).get(this.CREATE_SCENARIO_CORES_ADD_CIV_ALPHABET).getMenuElement(0).setText(CFG.sSearch);
        }
    }

    protected final void getCreateVassal_SelectAlphabet() {
        if (CFG.sSearch == null) {
            this.menus.get(this.INGAME_CREATE_VASSAL_SELECT_CIV).get(this.INGAME_CREATE_VASSAL_SELECT_CIV_ALPHABET).getMenuElement(0).setText("");
        } else {
            this.menus.get(this.INGAME_CREATE_VASSAL_SELECT_CIV).get(this.INGAME_CREATE_VASSAL_SELECT_CIV_ALPHABET).getMenuElement(0).setText(CFG.sSearch);
        }
    }

    protected final void getMapEditor_FormableCivs_SelectAlphabet() {
        if (CFG.sSearch == null) {
            this.menus.get(this.MAP_EDITOR_FORMABLE_CIVS_SELECT_FORMABLE).get(this.MAP_EDITOR_FORMABLE_CIVS_SELECT_FORMABLE_ALPHABET).getMenuElement(0).setText("");
        } else {
            this.menus.get(this.MAP_EDITOR_FORMABLE_CIVS_SELECT_FORMABLE).get(this.MAP_EDITOR_FORMABLE_CIVS_SELECT_FORMABLE_ALPHABET).getMenuElement(0).setText(CFG.sSearch);
        }
    }

    protected final void getGameCivs_SelectAlphabet() {
        if (CFG.sSearch == null) {
            this.menus.get(this.EDITOR_GAME_CIVS).get(2).getMenuElement(0).setText("");
        } else {
            this.menus.get(this.EDITOR_GAME_CIVS).get(2).getMenuElement(0).setText(CFG.sSearch);
        }
    }

    protected final void getMapEditor_FormableCivs_Alphabet() {
        if (CFG.sSearch == null) {
            this.menus.get(this.MAP_EDITOR_FORMABLE_CIVS).get(2).getMenuElement(0).setText("");
        } else {
            this.menus.get(this.MAP_EDITOR_FORMABLE_CIVS).get(2).getMenuElement(0).setText(CFG.sSearch);
        }
    }

    protected final void getLeaders_Alphabet() {
        if (CFG.sSearch == null) {
            this.menus.get(this.GAME_LEADERS).get(2).getMenuElement(0).setText("");
        } else {
            this.menus.get(this.GAME_LEADERS).get(2).getMenuElement(0).setText(CFG.sSearch);
        }
    }

    protected final void getRandom_Leaders_Alphabet() {
        if (CFG.sSearch == null) {
            this.menus.get(this.GAME_RANDOM_LEADERS).get(2).getMenuElement(0).setText("");
        } else {
            this.menus.get(this.GAME_RANDOM_LEADERS).get(2).getMenuElement(0).setText(CFG.sSearch);
        }
    }

    protected final void getSelect_Leaders_Alphabet() {
        if (CFG.sSearch == null) {
            this.menus.get(this.CREATE_SCENARIO_EVENTS_OUT_SELECT_LEADER).get(2).getMenuElement(0).setText("");
        } else {
            this.menus.get(this.CREATE_SCENARIO_EVENTS_OUT_SELECT_LEADER).get(2).getMenuElement(0).setText(CFG.sSearch);
        }
    }

    protected final void getMapEditor_FormableCivs_SelectClaimantAlphabet() {
        if (CFG.sSearch == null) {
            this.menus.get(this.MAP_EDITOR_FORMABLE_CIVS_SELECT_CLAIMANT).get(this.MAP_EDITOR_FORMABLE_CIVS_SELECT_CLAIMANT_ALPHABET).getMenuElement(0).setText("");
        } else {
            this.menus.get(this.MAP_EDITOR_FORMABLE_CIVS_SELECT_CLAIMANT).get(this.MAP_EDITOR_FORMABLE_CIVS_SELECT_CLAIMANT_ALPHABET).getMenuElement(0).setText(CFG.sSearch);
        }
    }

    protected final void getLeaders_SelectCivs_Alphabet() {
        if (CFG.sSearch == null) {
            this.menus.get(this.GAME_LEADERS_EDIT_SELECT_CIVS).get(this.GAME_LEADERS_EDIT_SELECT_CIVS_ALPHABET).getMenuElement(0).setText("");
        } else {
            this.menus.get(this.GAME_LEADERS_EDIT_SELECT_CIVS).get(this.GAME_LEADERS_EDIT_SELECT_CIVS_ALPHABET).getMenuElement(0).setText(CFG.sSearch);
        }
    }

    protected final void getRandomGame_Civilizations_SelectAlphabet() {
        if (CFG.sSearch == null) {
            this.menus.get(this.CREATE_RANDOM_GAME_CIVILIZATIONS_SELECT).get(this.CREATE_RANDOM_GAME_CIVILIZATIONS_SELECT_ALPHABET).getMenuElement(0).setText("");
        } else {
            this.menus.get(this.CREATE_RANDOM_GAME_CIVILIZATIONS_SELECT).get(this.CREATE_RANDOM_GAME_CIVILIZATIONS_SELECT_ALPHABET).getMenuElement(0).setText(CFG.sSearch);
        }
    }

    protected final void getCreateScenario_Events_SelectCiv_Alphabet() {
        if (CFG.sSearch == null) {
            this.menus.get(this.CREATE_SCENARIO_EVENTS_SELECT_CIV).get(this.CREATE_SCENARIO_EVENTS_SELECT_CIV_ALPHABET).getMenuElement(0).setText("");
        } else {
            this.menus.get(this.CREATE_SCENARIO_EVENTS_SELECT_CIV).get(this.CREATE_SCENARIO_EVENTS_SELECT_CIV_ALPHABET).getMenuElement(0).setText(CFG.sSearch);
        }
    }

    protected final void getCreateScenario_Events_AddCiv_Alphabet() {
        if (CFG.sSearch == null) {
            this.menus.get(this.CREATE_SCENARIO_EVENTS_ADD_CIV).get(this.CREATE_SCENARIO_EVENTS_ADD_CIV_ALPHABET).getMenuElement(0).setText("");
        } else {
            this.menus.get(this.CREATE_SCENARIO_EVENTS_ADD_CIV).get(this.CREATE_SCENARIO_EVENTS_ADD_CIV_ALPHABET).getMenuElement(0).setText(CFG.sSearch);
        }
    }

    protected final void setVisibleCreateCiv_Data(boolean visible) {
        this.menus.get(this.CREATE_CIVILIZATION).get(this.CREATE_CIVILIZATION_DATA).setVisible(visible);
    }

    protected final void setVisibleCreateScenario_Events_Edit(boolean visible) {
        if (visible) {
            this.menus.get(this.CREATE_SCENARIO_EVENTS).set(this.CREATE_SCENARIO_EVENTS_EDIT, new Menu_CreateScenario_Events_Edit());
        } else {
            this.menus.get(this.CREATE_SCENARIO_EVENTS).set(this.CREATE_SCENARIO_EVENTS_LIST, new Menu_CreateScenario_Events_List());
        }
        this.menus.get(this.CREATE_SCENARIO_EVENTS).get(this.CREATE_SCENARIO_EVENTS_EDIT).setVisible(visible);
        this.menus.get(this.CREATE_SCENARIO_EVENTS).get(this.CREATE_SCENARIO_EVENTS_LIST).setVisible(!visible);
    }

    protected final boolean getVisibleCreateScenario_Events_Edit() {
        return this.menus.get(this.CREATE_SCENARIO_EVENTS).get(this.CREATE_SCENARIO_EVENTS_EDIT).getVisible();
    }

    protected final void setVisibleCreateCiv_Flag(boolean visible) {
        this.menus.get(this.CREATE_CIVILIZATION).get(this.CREATE_CIVILIZATION_FLAG).setVisible(visible);
    }

    protected final void setVisibleCreateCiv_Overlay(boolean visible) {
        this.menus.get(this.CREATE_CIVILIZATION).get(this.CREATE_CIVILIZATION_OVERLAY).setVisible(visible);
        if (visible) {
            this.menus.get(this.CREATE_CIVILIZATION).get(this.CREATE_CIVILIZATION_OVERLAY).updateLanguage();
        }
    }

    protected final void rebuildCreateScenario_Cores_SetUp() {
        this.menus.get(this.CREATE_SCENARIO_CORES).set(this.CREATE_SCENARIO_CORES_SETUP, new Menu_CreateScenario_Cores_SetUp());
        this.menus.get(this.CREATE_SCENARIO_CORES).get(this.CREATE_SCENARIO_CORES_SETUP).setVisible(CFG.game.getActiveProvinceID() >= 0 && !CFG.game.getProvince(CFG.game.getActiveProvinceID()).getSeaProvince() && CFG.game.getProvince(CFG.game.getActiveProvinceID()).getWasteland() < 0 || CFG.game.getSelectedProvinces().getProvincesSize() > 0);
    }

    protected final void setVisible_CreateScenario_Cores_SetUp(boolean visible) {
        this.menus.get(this.CREATE_SCENARIO_CORES).get(this.CREATE_SCENARIO_CORES_SETUP).setVisible(visible);
    }

    protected final void rebuildCreateScenario_SetUpArmies_Sliders() {
        this.menus.get(this.CREATE_SCENARIO_SET_UP_ARMY).set(this.CREATE_SCENARIO_SET_UP_ARMY_SLIDERS, new Menu_CreateScenario_SetUpArmy_Sliders());
    }

    protected final void rebuildCreateScenario_SetUpArmies_Civs() {
        this.menus.get(this.CREATE_SCENARIO_SET_UP_ARMY).set(this.CREATE_SCENARIO_SET_UP_ARMY_CIVS, new Menu_CreateScenario_SetUpArmy_Civs());
    }

    protected final void setVisible_CreateScenario_SetUpArmies_Sliders(boolean visible) {
        this.menus.get(this.CREATE_SCENARIO_SET_UP_ARMY).get(this.CREATE_SCENARIO_SET_UP_ARMY_SLIDERS).setVisible(visible);
    }

    protected final boolean getVisible_CreateScenario_SetUpArmies_Sliders() {
        return this.menus.get(this.CREATE_SCENARIO_SET_UP_ARMY).get(this.CREATE_SCENARIO_SET_UP_ARMY_SLIDERS).getVisible();
    }

    protected final void rebuildCreateScenario_SetUpArmies_Neutral() {
        this.menus.get(this.CREATE_SCENARIO_SET_UP_ARMY).set(this.CREATE_SCENARIO_SET_UP_ARMY_NEUTRAL, new Menu_CreateScenario_SetUpArmy_NeutralArmy());
        this.menus.get(this.CREATE_SCENARIO_SET_UP_ARMY).get(this.CREATE_SCENARIO_SET_UP_ARMY_NEUTRAL).setVisible(true);
    }

    protected final void setVisible_CreateScenario_SetUpArmies_Neutral(boolean visible) {
        this.menus.get(this.CREATE_SCENARIO_SET_UP_ARMY).get(this.CREATE_SCENARIO_SET_UP_ARMY_NEUTRAL).setVisible(visible);
    }

    protected final boolean getVisible_CreateScenario_SetUpArmies_Neutral() {
        return this.menus.get(this.CREATE_SCENARIO_SET_UP_ARMY).get(this.CREATE_SCENARIO_SET_UP_ARMY_NEUTRAL).getVisible();
    }

    protected final void setVisible_CreateScenario_SetUpArmies_Civs(boolean visible) {
        this.menus.get(this.CREATE_SCENARIO_SET_UP_ARMY).get(this.CREATE_SCENARIO_SET_UP_ARMY_CIVS).setVisible(visible);
    }

    protected final boolean getVisible_InGame_CreateVassal_Civs() {
        return this.menus.get(this.INGAME_CREATE_VASSAL).get(this.INGAME_CREATE_VASSAL_CIVS).getVisible();
    }

    protected final void setVisible_InGame_CreateVassal_Civs(boolean visible) {
        if (visible) {
            this.menus.get(this.INGAME_CREATE_VASSAL).set(this.INGAME_CREATE_VASSAL_CIVS, new Menu_InGame_CreateAVassal_Civ());
        }
        this.menus.get(this.INGAME_CREATE_VASSAL).get(this.INGAME_CREATE_VASSAL_CIVS).setVisible(visible);
    }

    protected final boolean getVisible_CreateScenario_SetUpArmies_Civs() {
        return this.menus.get(this.CREATE_SCENARIO_SET_UP_ARMY).get(this.CREATE_SCENARIO_SET_UP_ARMY_CIVS).getVisible();
    }

    protected final void rebuildMapEditor_Connections_IDs(int nProvinceID) {
        this.menus.get(this.MAP_EDITOR_CONNECTIONS).set(1, new Menu_MapEditor_Connections_IDs(nProvinceID));
    }

    protected final void clearCreateScenario_SelectCivilizations() {
        this.menus.get(this.CREATE_SCENARIO_CIVILIZATIONS_SELECT).get(2).onBackPressed();
    }

    protected final void clearUnions_AddCiv() {
        this.menus.get(this.EDITOR_UNIONS_ADDCIV).get(2).onBackPressed();
    }

    protected final void clearCreateScenario_Cores_AddCore() {
        this.menus.get(this.CREATE_SCENARIO_CORES_ADD_CORE).get(2).onBackPressed();
    }

    protected final void clearCreateScenario_Cores_AddCiv() {
        this.menus.get(this.CREATE_SCENARIO_CORES_ADD_CIV).get(2).onBackPressed();
    }

    protected final void clearCreateVassal_SelectCivilizations() {
        this.menus.get(this.INGAME_CREATE_VASSAL_SELECT_CIV).get(2).onBackPressed();
    }

    protected final void menuCreateScenario_SelectCivilizations_UpdateTitle(int nCivs) {
        this.menus.get(this.CREATE_SCENARIO_CIVILIZATIONS_SELECT).get(0).getTitle().setText(CFG.langManager.get("SelectCivilization") + " [" + nCivs + "]");
    }

    protected final void menuCreateScenario_Cores_AddCiv_UpdateTitle(int nCivs) {
        this.menus.get(this.CREATE_SCENARIO_CORES_ADD_CIV).get(0).getTitle().setText(CFG.langManager.get("SelectCivilization") + " [" + nCivs + "]");
    }

    protected final void menuCreateVassal_SelectCiv_UpdateTitle(int nCivs) {
        this.menus.get(this.INGAME_CREATE_VASSAL_SELECT_CIV).get(0).getTitle().setText(CFG.langManager.get("SelectCivilization") + " [" + nCivs + "]");
    }

    protected final void clearMapEditor_FormableCivs_SelectCivilizations() {
        this.menus.get(this.MAP_EDITOR_FORMABLE_CIVS_SELECT_FORMABLE).get(2).onBackPressed();
    }

    protected final void clearMapEditor_FormableCivs_SelectClaimant() {
        this.menus.get(this.MAP_EDITOR_FORMABLE_CIVS_SELECT_CLAIMANT).get(2).onBackPressed();
    }

    protected final void clearLeaders__SelectCiv() {
        this.menus.get(this.GAME_LEADERS_EDIT_SELECT_CIVS).get(2).onBackPressed();
    }

    protected final void clearRandom_Leaders__SelectCiv() {
        this.menus.get(this.GAME_RANDOM_LEADERS_EDIT_SELECT_CIVS).get(2).onBackPressed();
    }

    protected final void clearRandom_Leaders__SelectAIType() {
        this.menus.get(this.GAME_RANDOM_LEADERS_EDIT_SELECT_AITYPE).get(2).onBackPressed();
    }

    protected final void clearRandomGame_SelectCivilizations() {
        this.menus.get(this.CREATE_RANDOM_GAME_CIVILIZATIONS_SELECT).get(2).onBackPressed();
    }

    protected final void clearCreateScenario_Events_SelectCiv() {
        this.menus.get(this.CREATE_SCENARIO_EVENTS_SELECT_CIV).get(2).onBackPressed();
    }

    protected final void clearCreateScenario_Events_AddCiv() {
        this.menus.get(this.CREATE_SCENARIO_EVENTS_ADD_CIV).get(2).onBackPressed();
    }

    protected final void clearGameEditoLines() {
        this.menus.get(this.GAME_EDITOR_LINES).get(1).onBackPressed();
    }

    protected final void rebuildCreateScenario_Events_Calendar() {
        int tX = this.menus.get(this.CREATE_SCENARIO_EVENTS_DATE).get(this.CREATE_SCENARIO_EVENTS_DATE_CALENDAR).getPosX();
        int tY = this.menus.get(this.CREATE_SCENARIO_EVENTS_DATE).get(this.CREATE_SCENARIO_EVENTS_DATE_CALENDAR).getPosY();
        this.menus.get(this.CREATE_SCENARIO_EVENTS_DATE).set(this.CREATE_SCENARIO_EVENTS_DATE_CALENDAR, new Menu_CreateScenario_Events_Date_Calendar());
        this.menus.get(this.CREATE_SCENARIO_EVENTS_DATE).get(this.CREATE_SCENARIO_EVENTS_DATE_CALENDAR).setPosX(tX);
        this.menus.get(this.CREATE_SCENARIO_EVENTS_DATE).get(this.CREATE_SCENARIO_EVENTS_DATE_CALENDAR).setPosY(tY);
        this.setCreateScenario_Events_Calendar_Visible(true);
    }

    protected final void rebuildScenario_Age_Calendar() {
        int tX = this.menus.get(this.SCENARIO_AGE).get(this.SCENARIO_AGE_CALENDAR).getPosX();
        int tY = this.menus.get(this.SCENARIO_AGE).get(this.SCENARIO_AGE_CALENDAR).getPosY();
        this.menus.get(this.SCENARIO_AGE).set(this.SCENARIO_AGE_CALENDAR, new Menu_Calendar());
        this.menus.get(this.SCENARIO_AGE).get(this.SCENARIO_AGE_CALENDAR).setPosX(tX);
        this.menus.get(this.SCENARIO_AGE).get(this.SCENARIO_AGE_CALENDAR).setPosY(tY);
        this.setScenarioAge_Calendar_Visible(true);
    }

    protected final void set_CreateScenario_TechnologyLevels_Slider(int nCurrent) {
        this.menus.get(this.CREATE_SCENARIO_TECHNOLOGY_LEVELS).get(0).getMenuElement(4).setCurrent(nCurrent);
        this.menus.get(this.CREATE_SCENARIO_TECHNOLOGY_LEVELS).set(this.CREATE_SCENARIO_TECHNOLOGY_LEVELS_CONTINENTS, new Menu_CreateScenario_TechnologyLevels_Continents());
    }

    protected final void set_CreateScenario_TechnologyLevels_SliderCivs() {
        this.menus.get(this.CREATE_SCENARIO_TECHNOLOGY_LEVELS).set(this.CREATE_SCENARIO_TECHNOLOGY_LEVELS_CONTINENTS, new Menu_CreateScenario_TechnologyLevels_Civs());
    }

    protected final void set_CreateScenario_Happiness_Slider(int nCurrent) {
        this.menus.get(this.CREATE_SCENARIO_HAPPINESS).get(0).getMenuElement(4).setCurrent(nCurrent);
    }

    protected final void set_CreateScenario_StartingMoney_Slider(int nCurrent) {
        this.menus.get(this.CREATE_SCENARIO_STARTING_MONEY).get(0).getMenuElement(4).setCurrent(nCurrent);
    }

    protected final void updateSelecetScenarioAge_Slider() {
        if (this.SCENARIO_AGE >= 0) {
            this.menus.get(this.SCENARIO_AGE).get(0).getMenuElement(4).setMin(CFG.gameAges.getAge(CFG.CREATE_SCENARIO_AGE).getBeginningYear());
            this.menus.get(this.SCENARIO_AGE).get(0).getMenuElement(4).setMax(CFG.gameAges.getAge(CFG.CREATE_SCENARIO_AGE).getEndYear());
            this.menus.get(this.SCENARIO_AGE).get(0).getMenuElement(4).setCurrent(Game_Calendar.currentYear);
        }
    }

    protected final void updateCreateScanerio_Events_Slider() {
        if (this.CREATE_SCENARIO_EVENTS_DATE >= 0) {
            this.menus.get(this.CREATE_SCENARIO_EVENTS_DATE).get(0).getMenuElement(4).setMin(Game_Calendar.currentYear <= CFG.gameAges.getAge(CFG.eventsManager.iCreateEvent_Age).getBeginningYear() ? CFG.gameAges.getAge(CFG.eventsManager.iCreateEvent_Age).getBeginningYear() : Game_Calendar.currentYear);
            this.menus.get(this.CREATE_SCENARIO_EVENTS_DATE).get(0).getMenuElement(4).setMax(CFG.gameAges.getAge(CFG.eventsManager.iCreateEvent_Age).getEndYear());
            this.menus.get(this.CREATE_SCENARIO_EVENTS_DATE).get(0).getMenuElement(4).setCurrent(CFG.eventsManager.iCreateEvent_Year);
        }
    }

    protected final void rebuildInGame_Report() {
        int tempPosX = this.menus.get(this.INGAME).get(this.INGAME_REPORT).getPosX();
        int tempPosY = this.menus.get(this.INGAME).get(this.INGAME_REPORT).getPosY();
        int tempWidth = this.menus.get(this.INGAME).get(this.INGAME_REPORT).getWidth();
        this.menus.get(this.INGAME).set(this.INGAME_REPORT, new Menu_InGame_Report());
        this.menus.get(this.INGAME).get(this.INGAME_REPORT).setVisible(true);
        this.menus.get(this.INGAME).get(this.INGAME_REPORT).setWidth(tempWidth);
        if (tempPosX > 0 && tempPosX + this.menus.get(this.INGAME).get(this.INGAME_REPORT).getWidth() < CFG.GAME_WIDTH && tempPosY > 0 && tempPosY + this.menus.get(this.INGAME).get(this.INGAME_REPORT).getHeight() < CFG.GAME_HEIGHT - CFG.map.getMapBG().getMinimapOfCivilizationsHeight()) {
            this.menus.get(this.INGAME).get(this.INGAME_REPORT).setPosX(tempPosX);
            this.menus.get(this.INGAME).get(this.INGAME_REPORT).setPosY(tempPosY);
        }
    }

    protected final boolean getInGame_Report_Visible() {
        return this.menus.get(this.INGAME).get(this.INGAME_REPORT).getVisible();
    }

    protected final void setInGame_Report_Visible(boolean visible) {
        this.menus.get(this.INGAME).get(this.INGAME_REPORT).setVisible(visible);
    }

    protected final boolean getScenarioAge_Calendar_Visible() {
        return this.menus.get(this.SCENARIO_AGE).get(this.SCENARIO_AGE_CALENDAR).getVisible();
    }

    protected final boolean getCreateScenario_Events_Calendar_Visible() {
        return this.menus.get(this.CREATE_SCENARIO_EVENTS_DATE).get(this.CREATE_SCENARIO_EVENTS_DATE_CALENDAR).getVisible();
    }

    protected final void setScenarioAge_Calendar_Visible(boolean visible) {
        this.menus.get(this.SCENARIO_AGE).get(this.SCENARIO_AGE_CALENDAR).setVisible(visible);
        if (visible && this.orderOfMenu.get(this.viewID).get(0) != this.SCENARIO_AGE_CALENDAR) {
            this.setOrderOfMenu(this.SCENARIO_AGE_CALENDAR);
        }
    }

    protected final void setCreateScenario_Events_Calendar_Visible(boolean visible) {
        this.menus.get(this.CREATE_SCENARIO_EVENTS_DATE).get(this.CREATE_SCENARIO_EVENTS_DATE_CALENDAR).setVisible(visible);
        if (visible && this.orderOfMenu.get(this.viewID).get(0) != this.CREATE_SCENARIO_EVENTS_DATE_CALENDAR) {
            this.setOrderOfMenu(this.CREATE_SCENARIO_EVENTS_DATE_CALENDAR);
        }
    }

    protected final void updateInGameRTO(boolean visible) {
        if (visible) {
            this.menus.get(this.INGAME).set(this.INGAME_RTO, new Menu_InGame_RTO2());
            this.menus.get(this.INGAME).set(this.INGAME_RTO_BOT, new Menu_InGame_RTO_Bot2());
            int tHeight = CFG.GAME_HEIGHT - this.menus.get(this.INGAME).get(this.INGAME_RTO).getPosY() - this.menus.get(this.INGAME).get(this.INGAME_RTO_BOT).getHeight() - CFG.BUTTON_HEIGHT - CFG.PADDING * 2 - CFG.BUTTON_HEIGHT - CFG.PADDING * 2;
            if (this.menus.get(this.INGAME).get(this.INGAME_RTO).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_RTO).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_RTO).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_RTO).getMenuElementsSize() - 1).getHeight() < tHeight) {
                tHeight = this.menus.get(this.INGAME).get(this.INGAME_RTO).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_RTO).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_RTO).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_RTO).getMenuElementsSize() - 1).getHeight();
            }
            this.menus.get(this.INGAME).get(this.INGAME_RTO).setHeight(tHeight);
            this.menus.get(this.INGAME).get(this.INGAME_RTO).updateMenuElements_IsInView();
            this.menus.get(this.INGAME).get(this.INGAME_RTO_BOT).setPosX(this.menus.get(this.INGAME).get(this.INGAME_RTO).getPosX());
            this.menus.get(this.INGAME).get(this.INGAME_RTO_BOT).setPosY(this.menus.get(this.INGAME).get(this.INGAME_RTO).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_RTO).getHeight() + 1);
            this.menus.get(this.INGAME).get(this.INGAME_RTO_BOT).setWidth(this.menus.get(this.INGAME).get(this.INGAME_RTO).getWidth());
        }
        this.menus.get(this.INGAME).get(this.INGAME_RTO).setVisible(visible);
        this.menus.get(this.INGAME).get(this.INGAME_RTO_BOT).setVisible(visible && CFG.settingsManager.showOrderOfMovesView);
    }

    protected final void setVisibleInGame_RTOBot(boolean visible) {
        this.menus.get(this.INGAME).get(this.INGAME_RTO_BOT).setVisible(visible);
    }

    protected final void updateInGame_TOP_All(int nCivID) {
        this.updateInGame_TOP_Rank(nCivID);
        this.updateInGame_TOP_Money(nCivID);
        this.updateInGame_TOP_MovementPoints(nCivID);
        this.updateInGame_TOP_DiplomacyPoints(nCivID);
        this.updateInGame_TOP_Date();
        this.updateInGame_TOP_Turn();
        this.menus.get(this.INGAME).get(0).getMenuElement(10).setVisible(false);
    }

    protected final void updateInGame_TOP_All_NextTurnActions(int nCivID) {
        this.menus.get(this.INGAME).get(0).getMenuElement(1).setVisible(false);
        this.menus.get(this.INGAME).get(0).getMenuElement(2).setVisible(false);
        this.menus.get(this.INGAME).get(0).getMenuElement(3).setVisible(false);
        this.menus.get(this.INGAME).get(0).getMenuElement(6).setVisible(false);
        this.menus.get(this.INGAME).get(0).getMenuElement(10).setWidth(1);
        this.menus.get(this.INGAME).get(0).getMenuElement(10).setText("" + CFG.game.getCiv(nCivID).getCivName());
        this.menus.get(this.INGAME).get(0).getMenuElement(10).setVisible(true);
    }

    protected final void updateInGame_TOP_Rank(int nCivID) {
        this.menus.get(this.INGAME).get(0).getMenuElement(6).setText("" + CFG.game.getCiv(nCivID).getRankPosition());
        this.menus.get(this.INGAME).get(0).getMenuElement(6).setVisible(true);
    }

    protected final void updateInGame_TOP_Money(int nCivID) {
        this.menus.get(this.INGAME).get(0).getMenuElement(1).setWidth(1);
        this.menus.get(this.INGAME).get(0).getMenuElement(1).setText("" + CFG.getNumberWithSpaces("" + CFG.game.getCiv(nCivID).getMoney()));
        this.menus.get(this.INGAME).get(0).getMenuElement(1).setVisible(true);
        Menu_InGame.updateOverBudget();
    }

    protected final void updateInGame_TOP_MovementPoints(int nCivID) {
        this.menus.get(this.INGAME).get(0).getMenuElement(2).setWidth(1);
        this.menus.get(this.INGAME).get(0).getMenuElement(2).setText(("" + (float)CFG.game.getCiv(nCivID).getMovePoints() / 10.0f).replaceAll(",", "."));
        this.menus.get(this.INGAME).get(0).getMenuElement(2).setVisible(true);
    }

    protected final void updateInGame_TOP_DiplomacyPoints(int nCivID) {
        this.menus.get(this.INGAME).get(0).getMenuElement(3).setWidth(1);
        this.menus.get(this.INGAME).get(0).getMenuElement(3).setText(("" + (float)CFG.game.getCiv(nCivID).getDiplomacyPoints() / 10.0f).replaceAll(",", "."));
        this.menus.get(this.INGAME).get(0).getMenuElement(3).setVisible(true);
    }

    protected final void updateInGame_TOP_Date() {
        this.menus.get(this.INGAME).get(0).getMenuElement(4).setWidth(1);
        this.menus.get(this.INGAME).get(0).getMenuElement(4).setText(Game_Calendar.getCurrentDate());
    }

    protected final void updateInGame_TOP_Turn() {
        this.menus.get(this.INGAME).get(0).getMenuElement(5).setWidth(1);
        this.menus.get(this.INGAME).get(0).getMenuElement(5).setText(CFG.langManager.get("Turn") + ": " + Game_Calendar.TURN_ID);
    }

    protected final void updateScenario_Age_Date() {
        this.menus.get(this.SCENARIO_AGE).get(0).getMenuElement(1).setText("" + Game_Calendar.currentDay + " " + Game_Calendar.getMonthName(Game_Calendar.currentMonth));
    }

    protected final void updateCreateScenario_Events_Age_Date() {
        this.menus.get(this.CREATE_SCENARIO_EVENTS_DATE).get(0).getMenuElement(1).setText("" + CFG.eventsManager.iCreateEvent_Day + " " + Game_Calendar.getMonthName(CFG.eventsManager.iCreateEvent_Month));
    }

    protected final void saveCreateScenarioSettings_Data() {
        CFG.CREATE_SCENARIO_NAME = this.menus.get(this.CREATE_SCENARIO_SETTINGS).get(1).getMenuElement(0).getText();
        CFG.CREATE_SCENARIO_AUTHOR = this.menus.get(this.CREATE_SCENARIO_SETTINGS).get(1).getMenuElement(2).getText();
        CFG.CREATE_SCENARIO_WIKI = this.menus.get(this.CREATE_SCENARIO_SETTINGS).get(1).getMenuElement(26).getText();
    }

    protected final void saveLeader_Edit_Data() {
        CFG.leader_GameData.getLeaderOfCiv().setName(this.menus.get(this.GAME_LEADERS_EDIT).get(this.GAME_LEADERS_EDIT_DATA).getMenuElement(0).getText());
        CFG.leader_GameData.getLeaderOfCiv().setImage(this.menus.get(this.GAME_LEADERS_EDIT).get(this.GAME_LEADERS_EDIT_DATA).getMenuElement(1).getText());
        CFG.leader_GameData.getLeaderOfCiv().setWiki(this.menus.get(this.GAME_LEADERS_EDIT).get(this.GAME_LEADERS_EDIT_DATA).getMenuElement(3).getText());
        CFG.leader_GameData.getLeaderOfCiv().setDay(Game_Calendar.currentDay);
        CFG.leader_GameData.getLeaderOfCiv().setMonth(Game_Calendar.currentMonth);
        CFG.leader_GameData.getLeaderOfCiv().setYear(Game_Calendar.currentYear);
    }

    protected final void saveRandom_Leader_Edit_Data() {
        CFG.leader_Random_GameData.getLeaderOfCiv().setName(this.menus.get(this.GAME_RANDOM_LEADERS_EDIT).get(this.GAME_RANDOM_LEADERS_EDIT_DATA).getMenuElement(0).getText());
        CFG.leader_Random_GameData.getLeaderOfCiv().setImage(this.menus.get(this.GAME_RANDOM_LEADERS_EDIT).get(this.GAME_RANDOM_LEADERS_EDIT_DATA).getMenuElement(1).getText());
        CFG.leader_Random_GameData.getLeaderOfCiv().setRiseToPower(this.menus.get(this.GAME_RANDOM_LEADERS_EDIT).get(this.GAME_RANDOM_LEADERS_EDIT_DATA).getMenuElement(3).getText());
        CFG.leader_Random_GameData.getLeaderOfCiv().setDay(Game_Calendar.currentDay);
        CFG.leader_Random_GameData.getLeaderOfCiv().setMonth(Game_Calendar.currentMonth);
        CFG.leader_Random_GameData.getLeaderOfCiv().setYear(Game_Calendar.currentYear);
    }

    protected MenuManager() {
    }

    protected void initExtraMenus() {
        this.dialogMenu = new Menu_Dialog();
        this.keyboard = new Keyboard();
        this.colorPicker = new ColorPicker_AoC();
        this.dragCivilization = new Drag_Civilization();
        this.provinceHover_Informations = null;
    }

    private final int addMenu(SliderMenu menu) {
        ArrayList<SliderMenu> nMenus = new ArrayList<SliderMenu>();
        nMenus.add(menu);
        this.menus.add(nMenus);
        ArrayList<Integer> order = new ArrayList<Integer>();
        order.add(this.menus.get(this.menus.size() - 1).size() - 1);
        this.orderOfMenu.add(order);
        return this.menus.size() - 1;
    }

    protected final int addNextMenuToView(int toView, SliderMenu menu) {
        this.menus.get(toView).add(menu);
        this.addMenuToOrderAtTheTop(toView);
        return this.menus.get(toView).size() - 1;
    }

    protected final void addMenuToOrderAtTheTop(int toView) {
        try {
            this.orderOfMenu.get(toView).add(this.menus.get(toView).size() - 1);
        }
        catch (IndexOutOfBoundsException ex) {
            CFG.exceptionStack(ex);
        }
        catch (NullPointerException ex) {
            CFG.exceptionStack(ex);
        }
    }

    protected final void setOrderOfMenu(int menuID) {
        try {
            boolean found = false;
            for (int i = 0; i < this.orderOfMenu.get(this.viewID).size(); ++i) {
                if (this.orderOfMenu.get(this.viewID).get(i) != menuID) continue;
                menuID = this.orderOfMenu.get(this.viewID).get(i);
                if (i == 0) {
                    return;
                }
                found = true;
                break;
            }
            if (found) {
                ArrayList<Integer> lOrder = new ArrayList<Integer>();
                lOrder.add(menuID);
                for (int i = 0; i < this.orderOfMenu.get(this.viewID).size(); ++i) {
                    if (this.orderOfMenu.get(this.viewID).get(i) == menuID) continue;
                    lOrder.add(this.orderOfMenu.get(this.viewID).get(i));
                }
                this.orderOfMenu.set(this.viewID, lOrder);
            }
        }
        catch (IndexOutOfBoundsException ex) {
            CFG.exceptionStack(ex);
        }
        catch (NullPointerException ex) {
            CFG.exceptionStack(ex);
        }
    }

    protected final void updateSlideMap_ActionMove() {
        block6: {
            boolean menuWithSlide = false;
            try {
                block3: for (int i = 0; i < this.menus.get(this.viewID).size(); ++i) {
                    for (int j = 0; j < this.menus.get(this.viewID).get(i).getMenuElementsSize(); ++j) {
                        if (this.menus.get(this.viewID).get(i).getMenuElement(j).getTypeOfElement() != MenuElement.TypeOfElement.SLIDE) continue;
                        menuWithSlide = true;
                        i = this.menus.get(this.viewID).size();
                        continue block3;
                    }
                }
                this.slideMap_ActionMove = menuWithSlide ? new SlideMap_ActionMove(){

                    @Override
                    public void update_ActionMove_SlideMap() {
                        if (MenuManager.this.slideMapMode) {
                            MenuManager.this.actionMoveSlideMap(CFG.slidePosX, CFG.slidePosY);
                        }
                    }
                } : new SlideMap_ActionMove(){

                    @Override
                    public void update_ActionMove_SlideMap() {
                    }
                };
            }
            catch (NullPointerException ex) {
                if (CFG.LOGS) {
                    CFG.exceptionStack(ex);
                }
            }
            catch (IndexOutOfBoundsException ex) {
                if (!CFG.LOGS) break block6;
                CFG.exceptionStack(ex);
            }
        }
    }

    protected final void update() {
        try {
            if (this.fromViewID >= 0) {
                CFG.setRender_3(true);
                this.animationChangeViewPosX = CFG.changeAnimationPos(this.animationStepID++, this.animationChangeViewPosX, this.backAnimation, CFG.GAME_WIDTH);
                if (Math.abs(this.animationChangeViewPosX) >= (float)CFG.GAME_WIDTH || CFG.iNumOfFPS < 22) {
                    this.resetChangeViewMode();
                }
            }
            if (CFG.isAndroid() && this.get_MenuElementHover_IsInView() && hoverMobileTime < System.currentTimeMillis() - (long)HOVER_MOBILE_TIME_VISIBLE) {
                this.resetHoverActive();
            }
            for (int i = 0; i < this.getActiveMenu().size(); ++i) {
                if (!this.getActiveMenu().get(i).getScrollableY() && !this.getActiveMenu().get(i).getScrollableX()) continue;
                this.getActiveMenu().get(i).update();
            }
            this.slideMap_ActionMove.update_ActionMove_SlideMap();
        }
        catch (NullPointerException nullPointerException) {
        }
        catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            // empty catch block
        }
    }

    protected final void resetMobileHover() {
        try {
            if (CFG.isAndroid()) {
                this.resetHoverActive();
            }
        }
        catch (NullPointerException nullPointerException) {
        }
        catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            // empty catch block
        }
    }

    protected final void draw(SpriteBatch oSB) {
        block9: {
            try {
                if (this.fromViewID < 0) {
                    this.draw(oSB, this.viewID, 0);
                } else {
                    this.draw(oSB, this.fromViewID, (int)this.animationChangeViewPosX);
                    this.draw(oSB, this.toViewID, (int)this.animationChangeViewPosX + CFG.GAME_WIDTH * (this.backAnimation ? -1 : 1));
                }
                if (CFG.achievement_Data != null) {
                    CFG.achievement_Data.draw(oSB, 0, 0);
                    if (CFG.achievement_Data.canBeDisposed()) {
                        CFG.achievement_Data = null;
                    }
                }
                if (CFG.toast.getInView()) {
                    CFG.toast.draw(oSB);
                }
            }
            catch (IndexOutOfBoundsException ex) {
                if (CFG.LOGS) {
                    CFG.exceptionStack(ex);
                }
            }
            catch (NullPointerException ex) {
                if (!CFG.LOGS) break block9;
                CFG.exceptionStack(ex);
            }
        }
    }

    protected final void updateEleemntHover_Animation() {
        if (MenuElement_Hover_v2.ANIMATION_ALPHA < 1.0f) {
            MenuElement_Hover_v2.ANIMATION_PADDING = (float)(CFG.PADDING * 2) - (float)(CFG.PADDING * 2) * ((MenuElement_Hover_v2.ANIMATION_ALPHA += (float)(System.currentTimeMillis() - MenuElement_Hover_v2.ANIMATION_TIME) / (float)MenuElement_Hover_v2.ANIMATION_INTERVAL) * 1.65f);
            if (MenuElement_Hover_v2.ANIMATION_PADDING < 0.0f) {
                MenuElement_Hover_v2.ANIMATION_PADDING = 0.0f;
            }
            if (MenuElement_Hover_v2.ANIMATION_ALPHA > 1.0f) {
                MenuElement_Hover_v2.ANIMATION_ALPHA = 1.0f;
            }
            CFG.setRender_3(true);
        }
    }

    protected final void draw(SpriteBatch oSB, int menuID, int iTranslateX) {
        try {
            for (int i = this.menus.get(menuID).size() - 1; i >= 0; --i) {
                if (!this.menus.get(menuID).get(this.orderOfMenu.get(menuID).get(i)).getVisible()) continue;
                this.menus.get(menuID).get(this.orderOfMenu.get(menuID).get(i)).draw(oSB, iTranslateX, this.dialogMenu.getVisible() ? false : (this.keyboardMode ? false : this.orderOfMenu.get(menuID).get(i) == this.activeSliderMenuID));
            }
            CFG.tutorialManager.draw(oSB, iTranslateX, 0);
        }
        catch (IndexOutOfBoundsException ex) {
            CFG.setRender_3(true);
        }
        catch (NullPointerException ex) {
            CFG.setRender_3(true);
        }
        try {
            if (this.hoverActiveSliderMenuID >= 0 && this.hoverActiveMenuElementID >= 0) {
                this.updateEleemntHover_Animation();
                this.getActiveMenu().get(this.hoverActiveSliderMenuID).drawHover(oSB, iTranslateX, 0, this.hoverActiveMenuElementID);
            } else if (this.provinceHover_Informations != null) {
                this.updateEleemntHover_Animation();
                this.provinceHover_Informations.drawProvinceInfo(oSB, Touch.getMousePosX() + this.getHover_ExtraPosX(), Touch.getMousePosY() + this.getHover_ExtraPosY());
            }
            if (this.colorPicker.getVisible()) {
                this.colorPicker.draw(oSB, iTranslateX);
            }
            if (this.keyboard.getVisible()) {
                this.keyboard.draw(oSB, iTranslateX, this.keyboardMode);
            }
            if (this.dialogMenu.getVisible()) {
                this.dialogMenu.draw(oSB, iTranslateX, true);
            }
            this.dragCivilization.draw(oSB, iTranslateX);
        }
        catch (IndexOutOfBoundsException ex) {
            CFG.setRender_3(true);
        }
        catch (NullPointerException ex) {
            CFG.setRender_3(true);
        }
    }

    protected final boolean get_MenuElementHover_IsInView() {
        try {
            if (this.hoverActiveSliderMenuID >= 0 && this.hoverActiveMenuElementID >= 0 && !this.getActiveMenu().get(this.hoverActiveSliderMenuID).getMenuElement(this.hoverActiveMenuElementID).getMenuElement_Hover_IsNull()) {
                return true;
            }
            return this.provinceHover_Informations != null;
        }
        catch (IndexOutOfBoundsException ex) {
            return this.provinceHover_Informations != null;
        }
        catch (NullPointerException ex) {
            return this.provinceHover_Informations != null;
        }
    }

    protected final int getHoveredProvinceID() {
        return iHoveredProvinceID;
    }

    protected final void updateBuildProvinceHoverInformation() {
        buildProvinceHover_Informations = this.getInGameView() || this.getInGame_CreateAVassal() || this.getInGame_SelectProvinces() || this.getInGame_ShowProvinces() || this.getInGame_TradeSelectCiv() || this.getInGame_PeaceTreaty() || this.getInGame_PeaceTreaty_Response() ? (CFG.gameAction.getActiveTurnState() == Game_Action.TurnStates.LOAD_AI_RTO || CFG.gameAction.getActiveTurnState() == Game_Action.TurnStates.LOADING_NEXT_TURN ? new BuildProvinceHover_Informations(){

            @Override
            public void build() {
                MenuManager.this.provinceHover_Informations = null;
            }
        } : (CFG.viewsManager.getActiveViewID() >= 0 ? new BuildProvinceHover_Informations(){

            @Override
            public void build() {
                if (CFG.map.getMapScale().getCurrentScale() < Game_Render.DISABLE_INNER_BORDERS) {
                    MenuManager.this.provinceHover_Informations = null;
                    return;
                }
                MenuManager.this.provinceHover_Informations = CFG.viewsManager.getActiveView().getProvinceInformations();
            }
        } : new BuildProvinceHover_Informations(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void build() {
                try {
                    ArrayList<MenuElement_Hover_v2_Element_Type> nData;
                    ArrayList<MenuElement_Hover_v2_Element2> nElements;
                    block30: {
                        block32: {
                            block33: {
                                block31: {
                                    if (CFG.map.getMapScale().getCurrentScale() < Game_Render.DISABLE_INNER_BORDERS) {
                                        MenuManager.this.provinceHover_Informations = null;
                                        return;
                                    }
                                    nElements = new ArrayList<MenuElement_Hover_v2_Element2>();
                                    nData = new ArrayList<MenuElement_Hover_v2_Element_Type>();
                                    if (CFG.FOG_OF_WAR != 2 || CFG.getMetProvince(iHoveredProvinceID)) break block31;
                                    nData.add(new MenuElement_Hover_v2_Element_Type_Flag(-1));
                                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Undiscovered"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                                    nElements.add(new MenuElement_Hover_v2_Element2(nData));
                                    nData.clear();
                                    break block30;
                                }
                                if (CFG.game.getProvince(iHoveredProvinceID).getWasteland() >= 0) break block32;
                                if (!CFG.game.getProvince(iHoveredProvinceID).getSeaProvince()) break block33;
                                if (CFG.game.getProvince(iHoveredProvinceID).getName().length() > 0) {
                                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getProvince(iHoveredProvinceID).getName(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                                    nElements.add(new MenuElement_Hover_v2_Element2(nData));
                                    nData.clear();
                                    if (CFG.game.getProvince(iHoveredProvinceID).getCivsSize() > 1) {
                                        for (int i = 1; i < CFG.game.getProvince(iHoveredProvinceID).getCivsSize(); ++i) {
                                            nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getProvince(iHoveredProvinceID).getCivID(i)));
                                            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(CFG.game.getProvince(iHoveredProvinceID).getCivID(i)).getCivName()));
                                            nElements.add(new MenuElement_Hover_v2_Element2(nData));
                                            nData.clear();
                                        }
                                    }
                                    break block30;
                                } else {
                                    if (CFG.game.getProvince(iHoveredProvinceID).getCivsSize() <= 1) {
                                        MenuManager.this.provinceHover_Informations = null;
                                        return;
                                    }
                                    for (int i = 1; i < CFG.game.getProvince(iHoveredProvinceID).getCivsSize(); ++i) {
                                        nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getProvince(iHoveredProvinceID).getCivID(i)));
                                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(CFG.game.getProvince(iHoveredProvinceID).getCivID(i)).getCivName()));
                                        nElements.add(new MenuElement_Hover_v2_Element2(nData));
                                        nData.clear();
                                    }
                                }
                                break block30;
                            }
                            if (CFG.game.getProvince(iHoveredProvinceID).getCivID() > 0) {
                                if (CFG.game.getProvince(iHoveredProvinceID).getName().length() > 0) {
                                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getProvince(iHoveredProvinceID).getName(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                                    nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getProvince(iHoveredProvinceID).getCivID(), CFG.PADDING, 0));
                                    nData.add(new MenuElement_Hover_v2_Element_Type_Terrain(CFG.game.getProvince(iHoveredProvinceID).getTerrainTypeID(), CFG.PADDING, 0));
                                } else {
                                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(CFG.game.getProvince(iHoveredProvinceID).getCivID()).getCivName(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                                    nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getProvince(iHoveredProvinceID).getCivID(), CFG.PADDING, 0));
                                    nData.add(new MenuElement_Hover_v2_Element_Type_Terrain(CFG.game.getProvince(iHoveredProvinceID).getTerrainTypeID(), CFG.PADDING, 0));
                                }
                                nElements.add(new MenuElement_Hover_v2_Element2(nData));
                                nData.clear();
                            } else if (CFG.game.getProvince(iHoveredProvinceID).getName().length() > 0) {
                                nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getProvince(iHoveredProvinceID).getName(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                                nData.add(new MenuElement_Hover_v2_Element_Type_Terrain(CFG.game.getProvince(iHoveredProvinceID).getTerrainTypeID(), CFG.PADDING, 0));
                                nElements.add(new MenuElement_Hover_v2_Element2(nData));
                                nData.clear();
                            } else {
                                nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(CFG.game.getProvince(iHoveredProvinceID).getCivID()).getCivName(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                                nData.add(new MenuElement_Hover_v2_Element_Type_Terrain(CFG.game.getProvince(iHoveredProvinceID).getTerrainTypeID(), CFG.PADDING, 0));
                                nElements.add(new MenuElement_Hover_v2_Element2(nData));
                                nData.clear();
                            }
                            for (int o = 0; o < CFG.game.getProvince(iHoveredProvinceID).getWonderSize(); ++o) {
                                nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getProvince((int)MenuManager.iHoveredProvinceID).getWonder((int)o).sName, CFG.COLOR_TEXT_NUM_OF_PROVINCES));
                                nData.add(new MenuElement_Hover_v2_Element_Type_Wonder(iHoveredProvinceID, o, CFG.PADDING, 0));
                                nElements.add(new MenuElement_Hover_v2_Element2(nData));
                                nData.clear();
                            }
                            try {
                                if (!CFG.SPECTATOR_MODE && CFG.menuManager.getInGameView()) {
                                    int tempRegroupArmy = 0;
                                    for (int i = 0; i < CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getRegroupArmySize(); ++i) {
                                        if (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getRegroupArmy(i).getToProvinceID() != iHoveredProvinceID) continue;
                                        tempRegroupArmy += CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getRegroupArmy(i).getNumOfUnits();
                                    }
                                    if (tempRegroupArmy > 0) {
                                        nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()));
                                        nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + tempRegroupArmy, CFG.COLOR_TEXT_NUM_OF_PROVINCES));
                                        nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.act_move, CFG.PADDING, 0));
                                        nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.diplo_army, CFG.PADDING, 0));
                                        nElements.add(new MenuElement_Hover_v2_Element2(nData));
                                        nData.clear();
                                    }
                                }
                                break block30;
                            }
                            catch (IndexOutOfBoundsException indexOutOfBoundsException) {}
                            break block30;
                        }
                        nData.add(new MenuElement_Hover_v2_Element_Type_Flag(-1));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Wasteland"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                        nElements.add(new MenuElement_Hover_v2_Element2(nData));
                        nData.clear();
                        if (!Game_Calendar.ENABLE_COLONIZATION) {
                            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("ThisProvinceCanNotBeColonized") + "."));
                            nElements.add(new MenuElement_Hover_v2_Element2(nData));
                            nData.clear();
                        } else if (Game_Calendar.getColonizationOfWastelandIsEnabled()) {
                            if (CFG.game.getProvince(iHoveredProvinceID).getWasteland() > 0) {
                                nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("ThisProvinceCanNotBeColonized") + "."));
                                nElements.add(new MenuElement_Hover_v2_Element2(nData));
                                nData.clear();
                            } else {
                                nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Terrain") + ": "));
                                nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.terrainTypesManager.getName(CFG.game.getProvince(iHoveredProvinceID).getTerrainTypeID()), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                                nData.add(new MenuElement_Hover_v2_Element_Type_Terrain(CFG.game.getProvince(iHoveredProvinceID).getTerrainTypeID(), CFG.PADDING, 0));
                                nElements.add(new MenuElement_Hover_v2_Element2(nData));
                                nData.clear();
                            }
                        } else {
                            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("ThisProvinceCanNotBeColonized") + "."));
                            nElements.add(new MenuElement_Hover_v2_Element2(nData));
                            nData.clear();
                        }
                    }
                    if (CFG.DEBUG_MODE) {
                        nData.add(new MenuElement_Hover_v2_Element_Type_Space());
                        nElements.add(new MenuElement_Hover_v2_Element2(nData));
                        nData.clear();
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.sDEBUG + CFG.langManager.get("Province") + ": ", CFG.COLOR_TEXT_MODIFIER_NEUTRAL2));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + iHoveredProvinceID, CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
                        nElements.add(new MenuElement_Hover_v2_Element2(nData));
                        nData.clear();
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Taxation") + ": "));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + (int)CFG.game_NextTurnUpdate.getProvinceIncome_Taxation(iHoveredProvinceID), CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.top_gold, CFG.PADDING, 0));
                        nElements.add(new MenuElement_Hover_v2_Element2(nData));
                        nData.clear();
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Production") + ": "));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + (int)CFG.game_NextTurnUpdate.getProvinceIncome_Production(iHoveredProvinceID), CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.top_gold, CFG.PADDING, 0));
                        nElements.add(new MenuElement_Hover_v2_Element2(nData));
                        nData.clear();
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Administration") + ": "));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text("-" + (int)CFG.game_NextTurnUpdate.getProvinceAdministration(iHoveredProvinceID, CFG.game.getCiv(CFG.game.getProvince(iHoveredProvinceID).getCivID()).getCapitalProvinceID()), CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.top_gold, CFG.PADDING, 0));
                        nElements.add(new MenuElement_Hover_v2_Element2(nData));
                        nData.clear();
                        if (CFG.game.getProvince(iHoveredProvinceID).getCivID() > 0 && CFG.game.getProvinceArmy(iHoveredProvinceID) > 0) {
                            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("MilitaryUpkeep") + ": "));
                            nData.add(new MenuElement_Hover_v2_Element_Type_Text("-" + (int)CFG.game_NextTurnUpdate.getMilitaryUpkeep(iHoveredProvinceID, CFG.game.getProvince(iHoveredProvinceID).getCivID()), CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.top_gold, CFG.PADDING, 0));
                            nElements.add(new MenuElement_Hover_v2_Element2(nData));
                            nData.clear();
                        }
                        if (CFG.game.getProvince(iHoveredProvinceID).getCivID() > 0) {
                            nData.add(new MenuElement_Hover_v2_Element_Type_Space());
                            nElements.add(new MenuElement_Hover_v2_Element2(nData));
                            nData.clear();
                            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.sDEBUG + "CIV: ", CFG.COLOR_TEXT_MODIFIER_NEUTRAL2));
                            nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + CFG.game.getProvince(iHoveredProvinceID).getCivID() + " "));
                            nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + CFG.game.getCiv(CFG.game.getProvince(iHoveredProvinceID).getCivID()).getCivTag(), CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
                            nElements.add(new MenuElement_Hover_v2_Element2(nData));
                            nData.clear();
                            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Treasury") + ": "));
                            nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + CFG.game.getCiv(CFG.game.getProvince(iHoveredProvinceID).getCivID()).getMoney(), CFG.COLOR_INGAME_GOLD));
                            nElements.add(new MenuElement_Hover_v2_Element2(nData));
                            nData.clear();
                            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Balance") + ": "));
                            nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + CFG.game_NextTurnUpdate.getBalance(CFG.game.getProvince(iHoveredProvinceID).getCivID()), CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
                            nElements.add(new MenuElement_Hover_v2_Element2(nData));
                            nData.clear();
                            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Army") + ": "));
                            nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + CFG.game.getCiv(CFG.game.getProvince(iHoveredProvinceID).getCivID()).getNumOfUnits(), CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
                            nElements.add(new MenuElement_Hover_v2_Element2(nData));
                            nData.clear();
                        }
                    }
                    MenuManager.this.provinceHover_Informations = new MenuElement_Hover_v2(nElements);
                    return;
                }
                catch (IndexOutOfBoundsException ex) {
                    MenuManager.this.provinceHover_Informations = null;
                }
            }
        })) : (this.getInGame_Timeline() || this.getInVictory() ? (CFG.FOG_OF_WAR == 2 ? new BuildProvinceHover_Informations(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void build() {
                try {
                    ArrayList<MenuElement_Hover_v2_Element2> nElements;
                    block11: {
                        ArrayList<MenuElement_Hover_v2_Element_Type> nData;
                        block12: {
                            block13: {
                                if (CFG.map.getMapScale().getCurrentScale() < Game_Render.DISABLE_INNER_BORDERS) {
                                    MenuManager.this.provinceHover_Informations = null;
                                    return;
                                }
                                if (!TimelapseManager.PAUSE) {
                                    MenuManager.this.provinceHover_Informations = null;
                                    return;
                                }
                                nElements = new ArrayList<MenuElement_Hover_v2_Element2>();
                                nData = new ArrayList<MenuElement_Hover_v2_Element_Type>();
                                if (!CFG.game.getPlayer(CFG.PLAYER_TURNID).getMetProvince(iHoveredProvinceID)) break block12;
                                if (!CFG.game.getProvince(iHoveredProvinceID).getSeaProvince()) break block13;
                                if (CFG.game.getProvince(iHoveredProvinceID).getName().length() <= 0) {
                                    MenuManager.this.provinceHover_Informations = null;
                                    return;
                                }
                                nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getProvince(iHoveredProvinceID).getName(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                                nElements.add(new MenuElement_Hover_v2_Element2(nData));
                                nData.clear();
                                break block11;
                            }
                            if (CFG.timelapseManager.timelineOwners.get(iHoveredProvinceID) > 0) {
                                if (CFG.game.getProvince(iHoveredProvinceID).getName().length() > 0) {
                                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getProvince(iHoveredProvinceID).getName(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                                    nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.timelapseManager.timelineOwners.get(iHoveredProvinceID), CFG.PADDING, 0));
                                    nData.add(new MenuElement_Hover_v2_Element_Type_Terrain(CFG.game.getProvince(iHoveredProvinceID).getTerrainTypeID(), CFG.PADDING, 0));
                                } else {
                                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(CFG.timelapseManager.timelineOwners.get(iHoveredProvinceID)).getCivName(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                                    nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.timelapseManager.timelineOwners.get(iHoveredProvinceID), CFG.PADDING, 0));
                                    nData.add(new MenuElement_Hover_v2_Element_Type_Terrain(CFG.game.getProvince(iHoveredProvinceID).getTerrainTypeID(), CFG.PADDING, 0));
                                }
                                nElements.add(new MenuElement_Hover_v2_Element2(nData));
                                nData.clear();
                                break block11;
                            } else if (CFG.game.getProvince(iHoveredProvinceID).getName().length() > 0) {
                                nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getProvince(iHoveredProvinceID).getName(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                                nData.add(new MenuElement_Hover_v2_Element_Type_Terrain(CFG.game.getProvince(iHoveredProvinceID).getTerrainTypeID(), CFG.PADDING, 0));
                                nElements.add(new MenuElement_Hover_v2_Element2(nData));
                                nData.clear();
                                break block11;
                            } else {
                                nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(CFG.timelapseManager.timelineOwners.get(iHoveredProvinceID)).getCivName(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                                nData.add(new MenuElement_Hover_v2_Element_Type_Terrain(CFG.game.getProvince(iHoveredProvinceID).getTerrainTypeID(), CFG.PADDING, 0));
                                nElements.add(new MenuElement_Hover_v2_Element2(nData));
                                nData.clear();
                            }
                            break block11;
                        }
                        nData.add(new MenuElement_Hover_v2_Element_Type_Flag(-1));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Undiscovered"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                        nElements.add(new MenuElement_Hover_v2_Element2(nData));
                        nData.clear();
                    }
                    MenuManager.this.provinceHover_Informations = new MenuElement_Hover_v2(nElements);
                    return;
                }
                catch (IndexOutOfBoundsException ex) {
                    MenuManager.this.provinceHover_Informations = null;
                }
            }
        } : new BuildProvinceHover_Informations(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void build() {
                try {
                    if (CFG.map.getMapScale().getCurrentScale() < Game_Render.DISABLE_INNER_BORDERS) {
                        MenuManager.this.provinceHover_Informations = null;
                        return;
                    }
                    if (!TimelapseManager.PAUSE) {
                        MenuManager.this.provinceHover_Informations = null;
                        return;
                    }
                    ArrayList<MenuElement_Hover_v2_Element2> nElements = new ArrayList<MenuElement_Hover_v2_Element2>();
                    ArrayList<MenuElement_Hover_v2_Element_Type> nData = new ArrayList<MenuElement_Hover_v2_Element_Type>();
                    if (CFG.game.getProvince(iHoveredProvinceID).getSeaProvince()) {
                        if (CFG.game.getProvince(iHoveredProvinceID).getName().length() <= 0) {
                            MenuManager.this.provinceHover_Informations = null;
                            return;
                        }
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getProvince(iHoveredProvinceID).getName(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                        nElements.add(new MenuElement_Hover_v2_Element2(nData));
                        nData.clear();
                    } else if (CFG.timelapseManager.timelineOwners.get(iHoveredProvinceID) > 0) {
                        if (CFG.game.getProvince(iHoveredProvinceID).getName().length() > 0) {
                            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getProvince(iHoveredProvinceID).getName(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                            nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.timelapseManager.timelineOwners.get(iHoveredProvinceID), CFG.PADDING, 0));
                            nData.add(new MenuElement_Hover_v2_Element_Type_Terrain(CFG.game.getProvince(iHoveredProvinceID).getTerrainTypeID(), CFG.PADDING, 0));
                        } else {
                            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(CFG.timelapseManager.timelineOwners.get(iHoveredProvinceID)).getCivName(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                            nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.timelapseManager.timelineOwners.get(iHoveredProvinceID), CFG.PADDING, 0));
                            nData.add(new MenuElement_Hover_v2_Element_Type_Terrain(CFG.game.getProvince(iHoveredProvinceID).getTerrainTypeID(), CFG.PADDING, 0));
                        }
                        nElements.add(new MenuElement_Hover_v2_Element2(nData));
                        nData.clear();
                    } else if (CFG.game.getProvince(iHoveredProvinceID).getName().length() > 0) {
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getProvince(iHoveredProvinceID).getName(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Terrain(CFG.game.getProvince(iHoveredProvinceID).getTerrainTypeID(), CFG.PADDING, 0));
                        nElements.add(new MenuElement_Hover_v2_Element2(nData));
                        nData.clear();
                    } else {
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(CFG.timelapseManager.timelineOwners.get(iHoveredProvinceID)).getCivName(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Terrain(CFG.game.getProvince(iHoveredProvinceID).getTerrainTypeID(), CFG.PADDING, 0));
                        nElements.add(new MenuElement_Hover_v2_Element2(nData));
                        nData.clear();
                    }
                    MenuManager.this.provinceHover_Informations = new MenuElement_Hover_v2(nElements);
                    return;
                }
                catch (IndexOutOfBoundsException ex) {
                    MenuManager.this.provinceHover_Informations = null;
                }
            }
        }) : (this.getInGame_Formable_Civ_Provinces() ? new BuildProvinceHover_Informations(){

            @Override
            public void build() {
                try {
                    if (CFG.map.getMapScale().getCurrentScale() < Game_Render.DISABLE_INNER_BORDERS) {
                        MenuManager.this.provinceHover_Informations = null;
                        return;
                    }
                    if (CFG.game.getProvince(iHoveredProvinceID).getWasteland() < 0) {
                        if (CFG.getIsInFormableCiv(iHoveredProvinceID)) {
                            ArrayList<MenuElement_Hover_v2_Element2> nElements = new ArrayList<MenuElement_Hover_v2_Element2>();
                            ArrayList<MenuElement_Hover_v2_Element_Type> nData = new ArrayList<MenuElement_Hover_v2_Element_Type>();
                            if (CFG.FOG_OF_WAR == 2 && !CFG.getMetProvince(iHoveredProvinceID)) {
                                nData.add(new MenuElement_Hover_v2_Element_Type_Flag(-1));
                                nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Undiscovered"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                                nElements.add(new MenuElement_Hover_v2_Element2(nData));
                                nData.clear();
                            } else {
                                nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getProvince(iHoveredProvinceID).getCivID()));
                                nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(CFG.game.getProvince(iHoveredProvinceID).getCivID()).getCivName(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                                nElements.add(new MenuElement_Hover_v2_Element2(nData));
                                nData.clear();
                            }
                            MenuManager.this.provinceHover_Informations = new MenuElement_Hover_v2(nElements);
                        } else {
                            MenuManager.this.provinceHover_Informations = null;
                        }
                    } else {
                        MenuManager.this.provinceHover_Informations = null;
                    }
                }
                catch (IndexOutOfBoundsException ex) {
                    MenuManager.this.provinceHover_Informations = null;
                }
            }
        } : (this.getInCreateScenario_Events() ? new BuildProvinceHover_Informations(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void build() {
                try {
                    ArrayList<MenuElement_Hover_v2_Element2> nElements = new ArrayList<MenuElement_Hover_v2_Element2>();
                    ArrayList<MenuElement_Hover_v2_Element_Type> nData = new ArrayList<MenuElement_Hover_v2_Element_Type>();
                    if (CFG.game.getProvince(iHoveredProvinceID).getWasteland() < 0) {
                        if (CFG.game.getProvince(iHoveredProvinceID).getSeaProvince()) {
                            MenuManager.this.provinceHover_Informations = null;
                            return;
                        }
                        if (CFG.game.getProvince(iHoveredProvinceID).getCivID() <= 0) {
                            MenuManager.this.provinceHover_Informations = null;
                            return;
                        }
                        nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getProvince(iHoveredProvinceID).getCivID()));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(CFG.game.getProvince(iHoveredProvinceID).getCivID()).getCivName(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                        nElements.add(new MenuElement_Hover_v2_Element2(nData));
                        nData.clear();
                    } else {
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Wasteland"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                        nElements.add(new MenuElement_Hover_v2_Element2(nData));
                        nData.clear();
                    }
                    MenuManager.this.provinceHover_Informations = new MenuElement_Hover_v2(nElements);
                    return;
                }
                catch (IndexOutOfBoundsException ex) {
                    MenuManager.this.provinceHover_Informations = null;
                }
            }
        } : (this.getInCreateNewGame() ? (CFG.viewsManager.getActiveViewID() >= 0 ? new BuildProvinceHover_Informations(){

            @Override
            public void build() {
                if (CFG.map.getMapScale().getCurrentScale() < Game_Render.DISABLE_INNER_BORDERS) {
                    MenuManager.this.provinceHover_Informations = null;
                    return;
                }
                MenuManager.this.provinceHover_Informations = CFG.viewsManager.getActiveView().getProvinceInformations();
            }
        } : new BuildProvinceHover_Informations(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void build() {
                try {
                    if (CFG.map.getMapScale().getCurrentScale() < Game_Render.DISABLE_INNER_BORDERS) {
                        MenuManager.this.provinceHover_Informations = null;
                        return;
                    }
                    ArrayList<MenuElement_Hover_v2_Element2> nElements = new ArrayList<MenuElement_Hover_v2_Element2>();
                    ArrayList<MenuElement_Hover_v2_Element_Type> nData = new ArrayList<MenuElement_Hover_v2_Element_Type>();
                    if (CFG.game.getProvince(iHoveredProvinceID).getWasteland() < 0) {
                        if (CFG.game.getProvince(iHoveredProvinceID).getSeaProvince()) {
                            MenuManager.this.provinceHover_Informations = null;
                            return;
                        }
                        if (CFG.game.getProvince(iHoveredProvinceID).getCivID() <= 0) {
                            MenuManager.this.provinceHover_Informations = null;
                            return;
                        }
                        nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getProvince(iHoveredProvinceID).getCivID()));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(CFG.game.getProvince(iHoveredProvinceID).getCivID()).getCivName(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                        nElements.add(new MenuElement_Hover_v2_Element2(nData));
                        nData.clear();
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Provinces") + ": "));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + CFG.game.getCiv(CFG.game.getProvince(iHoveredProvinceID).getCivID()).getNumOfProvinces(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                        nElements.add(new MenuElement_Hover_v2_Element2(nData));
                        nData.clear();
                    } else {
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Wasteland"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                        nElements.add(new MenuElement_Hover_v2_Element2(nData));
                        nData.clear();
                        if (!Game_Calendar.ENABLE_COLONIZATION) {
                            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("ThisProvinceCanNotBeColonized") + "."));
                            nElements.add(new MenuElement_Hover_v2_Element2(nData));
                            nData.clear();
                        }
                    }
                    MenuManager.this.provinceHover_Informations = new MenuElement_Hover_v2(nElements);
                    return;
                }
                catch (IndexOutOfBoundsException ex) {
                    MenuManager.this.provinceHover_Informations = null;
                }
            }
        }) : (this.getInCreateScenario_Cores() ? new BuildProvinceHover_Informations(){

            @Override
            public void build() {
                try {
                    ArrayList<MenuElement_Hover_v2_Element2> nElements = new ArrayList<MenuElement_Hover_v2_Element2>();
                    ArrayList<MenuElement_Hover_v2_Element_Type> nData = new ArrayList<MenuElement_Hover_v2_Element_Type>();
                    if (CFG.game.getProvince(iHoveredProvinceID).getWasteland() < 0) {
                        if (CFG.game.getProvince(iHoveredProvinceID).getSeaProvince()) {
                            MenuManager.this.provinceHover_Informations = null;
                            return;
                        }
                        for (int i = 0; i < CFG.game.getProvince(iHoveredProvinceID).getCore().getCivsSize(); ++i) {
                            nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getProvince(iHoveredProvinceID).getCore().getCivID(i)));
                            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(CFG.game.getProvince(iHoveredProvinceID).getCore().getCivID(i)).getCivName(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                            nElements.add(new MenuElement_Hover_v2_Element2(nData));
                            nData.clear();
                        }
                        if (nElements.size() == 0) {
                            MenuManager.this.provinceHover_Informations = null;
                            return;
                        }
                    } else {
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Wasteland"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                        nElements.add(new MenuElement_Hover_v2_Element2(nData));
                        nData.clear();
                    }
                    MenuManager.this.provinceHover_Informations = new MenuElement_Hover_v2(nElements);
                }
                catch (IndexOutOfBoundsException ex) {
                    MenuManager.this.provinceHover_Informations = null;
                }
                catch (NullPointerException ex) {
                    MenuManager.this.provinceHover_Informations = null;
                }
            }
        } : (this.getInCreateScenario_Civilizations() ? new BuildProvinceHover_Informations(){

            @Override
            public void build() {
                try {
                    ArrayList<MenuElement_Hover_v2_Element2> nElements = new ArrayList<MenuElement_Hover_v2_Element2>();
                    ArrayList<MenuElement_Hover_v2_Element_Type> nData = new ArrayList<MenuElement_Hover_v2_Element_Type>();
                    if (CFG.game.getProvince(iHoveredProvinceID).getWasteland() < 0) {
                        if (CFG.game.getProvince(iHoveredProvinceID).getCivID() > 0) {
                            if (CFG.game.getProvince(iHoveredProvinceID).getName().length() > 0) {
                                nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getProvince(iHoveredProvinceID).getName(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                                if (!CFG.game.getProvince(iHoveredProvinceID).getSeaProvince()) {
                                    nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getProvince(iHoveredProvinceID).getCivID(), CFG.PADDING, CFG.PADDING));
                                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.ideologiesManager.getIdeology(CFG.game.getCiv(CFG.game.getProvince(iHoveredProvinceID).getCivID()).getIdeologyID()).getName(), CFG.ideologiesManager.getIdeology(CFG.game.getCiv(CFG.game.getProvince(iHoveredProvinceID).getCivID()).getIdeologyID()).getColor()));
                                }
                            } else {
                                nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(CFG.game.getProvince(iHoveredProvinceID).getCivID()).getCivName(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                                if (!CFG.game.getProvince(iHoveredProvinceID).getSeaProvince()) {
                                    nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getProvince(iHoveredProvinceID).getCivID(), CFG.PADDING, CFG.PADDING));
                                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.ideologiesManager.getIdeology(CFG.game.getCiv(CFG.game.getProvince(iHoveredProvinceID).getCivID()).getIdeologyID()).getName(), CFG.ideologiesManager.getIdeology(CFG.game.getCiv(CFG.game.getProvince(iHoveredProvinceID).getCivID()).getIdeologyID()).getColor()));
                                }
                            }
                            nElements.add(new MenuElement_Hover_v2_Element2(nData));
                            nData.clear();
                        } else if (CFG.game.getProvince(iHoveredProvinceID).getName().length() > 0) {
                            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getProvince(iHoveredProvinceID).getName(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                            if (!CFG.game.getProvince(iHoveredProvinceID).getSeaProvince()) {
                                nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getProvince(iHoveredProvinceID).getCivID(), CFG.PADDING, 0));
                            }
                            nElements.add(new MenuElement_Hover_v2_Element2(nData));
                            nData.clear();
                        } else {
                            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(CFG.game.getProvince(iHoveredProvinceID).getCivID()).getCivName(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                            if (!CFG.game.getProvince(iHoveredProvinceID).getSeaProvince()) {
                                nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getProvince(iHoveredProvinceID).getCivID(), CFG.PADDING, 0));
                            }
                            nElements.add(new MenuElement_Hover_v2_Element2(nData));
                            nData.clear();
                        }
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Terrain") + ": "));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.terrainTypesManager.getName(CFG.game.getProvince(iHoveredProvinceID).getTerrainTypeID()), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Terrain(CFG.game.getProvince(iHoveredProvinceID).getTerrainTypeID(), CFG.PADDING, 0));
                        nElements.add(new MenuElement_Hover_v2_Element2(nData));
                        nData.clear();
                        if (!CFG.game.getProvince(iHoveredProvinceID).getSeaProvince()) {
                            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("GrowthRate") + ": "));
                            nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + (int)(CFG.game.getProvince(iHoveredProvinceID).getGrowthRate_Population() * 100.0f) + "%", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                            nElements.add(new MenuElement_Hover_v2_Element2(nData));
                            nData.clear();
                        }
                    } else {
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Wasteland"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                        nElements.add(new MenuElement_Hover_v2_Element2(nData));
                        nData.clear();
                        if (CFG.game.getProvince(iHoveredProvinceID).getName().length() > 0) {
                            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getProvince(iHoveredProvinceID).getName()));
                            nElements.add(new MenuElement_Hover_v2_Element2(nData));
                            nData.clear();
                        }
                    }
                    MenuManager.this.provinceHover_Informations = new MenuElement_Hover_v2(nElements);
                }
                catch (IndexOutOfBoundsException ex) {
                    MenuManager.this.provinceHover_Informations = null;
                }
            }
        } : (this.getInCreateScenario_HolyRomanEmpire() ? new BuildProvinceHover_Informations(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void build() {
                try {
                    ArrayList<MenuElement_Hover_v2_Element2> nElements = new ArrayList<MenuElement_Hover_v2_Element2>();
                    ArrayList<MenuElement_Hover_v2_Element_Type> nData = new ArrayList<MenuElement_Hover_v2_Element_Type>();
                    if (CFG.game.getProvince(iHoveredProvinceID).getSeaProvince()) {
                        if (CFG.game.getProvince(iHoveredProvinceID).getName().length() <= 0) {
                            MenuManager.this.provinceHover_Informations = null;
                            return;
                        }
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getProvince(iHoveredProvinceID).getName(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                        nElements.add(new MenuElement_Hover_v2_Element2(nData));
                        nData.clear();
                    } else if (CFG.game.getProvince(iHoveredProvinceID).getWasteland() < 0) {
                        nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getProvince(iHoveredProvinceID).getCivID()));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(CFG.game.getProvince(iHoveredProvinceID).getCivID()).getCivName(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                        nElements.add(new MenuElement_Hover_v2_Element2(nData));
                        nData.clear();
                    } else {
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Wasteland")));
                        nElements.add(new MenuElement_Hover_v2_Element2(nData));
                        nData.clear();
                        if (CFG.game.getProvince(iHoveredProvinceID).getName().length() > 0) {
                            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getProvince(iHoveredProvinceID).getName(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                            nElements.add(new MenuElement_Hover_v2_Element2(nData));
                            nData.clear();
                        }
                    }
                    MenuManager.this.provinceHover_Informations = new MenuElement_Hover_v2(nElements);
                    return;
                }
                catch (IndexOutOfBoundsException ex) {
                    MenuManager.this.provinceHover_Informations = null;
                }
            }
        } : (this.getInCreateScenario_Assign() ? new BuildProvinceHover_Informations(){

            @Override
            public void build() {
                try {
                    ArrayList<MenuElement_Hover_v2_Element2> nElements = new ArrayList<MenuElement_Hover_v2_Element2>();
                    ArrayList<MenuElement_Hover_v2_Element_Type> nData = new ArrayList<MenuElement_Hover_v2_Element_Type>();
                    if (CFG.game.getProvince(iHoveredProvinceID).getWasteland() < 0) {
                        if (CFG.game.getProvince(iHoveredProvinceID).getCivID() > 0) {
                            if (CFG.game.getProvince(iHoveredProvinceID).getName().length() > 0) {
                                nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getProvince(iHoveredProvinceID).getName(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                                if (!CFG.game.getProvince(iHoveredProvinceID).getSeaProvince()) {
                                    nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getProvince(iHoveredProvinceID).getCivID(), CFG.PADDING, 0));
                                }
                            } else {
                                nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(CFG.game.getProvince(iHoveredProvinceID).getCivID()).getCivName(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                                if (!CFG.game.getProvince(iHoveredProvinceID).getSeaProvince()) {
                                    nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getProvince(iHoveredProvinceID).getCivID(), CFG.PADDING, 0));
                                }
                            }
                            nElements.add(new MenuElement_Hover_v2_Element2(nData));
                            nData.clear();
                        } else if (CFG.game.getProvince(iHoveredProvinceID).getName().length() > 0) {
                            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getProvince(iHoveredProvinceID).getName(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                            if (!CFG.game.getProvince(iHoveredProvinceID).getSeaProvince()) {
                                nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getProvince(iHoveredProvinceID).getCivID(), CFG.PADDING, 0));
                            }
                            nElements.add(new MenuElement_Hover_v2_Element2(nData));
                            nData.clear();
                        } else {
                            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(CFG.game.getProvince(iHoveredProvinceID).getCivID()).getCivName(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                            if (!CFG.game.getProvince(iHoveredProvinceID).getSeaProvince()) {
                                nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getProvince(iHoveredProvinceID).getCivID(), CFG.PADDING, 0));
                            }
                            nElements.add(new MenuElement_Hover_v2_Element2(nData));
                            nData.clear();
                        }
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Terrain") + ": "));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.terrainTypesManager.getName(CFG.game.getProvince(iHoveredProvinceID).getTerrainTypeID()), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Terrain(CFG.game.getProvince(iHoveredProvinceID).getTerrainTypeID(), CFG.PADDING, 0));
                        nElements.add(new MenuElement_Hover_v2_Element2(nData));
                        nData.clear();
                        if (!CFG.game.getProvince(iHoveredProvinceID).getSeaProvince()) {
                            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("GrowthRate") + ": "));
                            nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + (int)(CFG.game.getProvince(iHoveredProvinceID).getGrowthRate_Population() * 100.0f) + "%", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                            nElements.add(new MenuElement_Hover_v2_Element2(nData));
                            nData.clear();
                        }
                    } else {
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Wasteland"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                        nElements.add(new MenuElement_Hover_v2_Element2(nData));
                        nData.clear();
                        if (CFG.game.getProvince(iHoveredProvinceID).getName().length() > 0) {
                            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getProvince(iHoveredProvinceID).getName()));
                            nElements.add(new MenuElement_Hover_v2_Element2(nData));
                            nData.clear();
                        }
                    }
                    MenuManager.this.provinceHover_Informations = new MenuElement_Hover_v2(nElements);
                }
                catch (IndexOutOfBoundsException ex) {
                    MenuManager.this.provinceHover_Informations = null;
                }
            }
        } : (this.getInCreateScenario_Available_Provinces() ? new BuildProvinceHover_Informations(){

            @Override
            public void build() {
                try {
                    ArrayList<MenuElement_Hover_v2_Element2> nElements = new ArrayList<MenuElement_Hover_v2_Element2>();
                    ArrayList<MenuElement_Hover_v2_Element_Type> nData = new ArrayList<MenuElement_Hover_v2_Element_Type>();
                    if (CFG.game.getProvince(iHoveredProvinceID).getWasteland() < 0) {
                        if (CFG.game.getProvince(iHoveredProvinceID).getName().length() > 0) {
                            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getProvince(iHoveredProvinceID).getName(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                            nElements.add(new MenuElement_Hover_v2_Element2(nData));
                            nData.clear();
                        }
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Terrain") + ": "));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.terrainTypesManager.getName(CFG.game.getProvince(iHoveredProvinceID).getTerrainTypeID()), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Terrain(CFG.game.getProvince(iHoveredProvinceID).getTerrainTypeID(), CFG.PADDING, 0));
                        nElements.add(new MenuElement_Hover_v2_Element2(nData));
                        nData.clear();
                        if (!CFG.game.getProvince(iHoveredProvinceID).getSeaProvince()) {
                            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("GrowthRate") + ": "));
                            nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + (int)(CFG.game.getProvince(iHoveredProvinceID).getGrowthRate_Population() * 100.0f) + "%", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                            nElements.add(new MenuElement_Hover_v2_Element2(nData));
                            nData.clear();
                        }
                    } else {
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Wasteland"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                        nElements.add(new MenuElement_Hover_v2_Element2(nData));
                        nData.clear();
                        nData.add(new MenuElement_Hover_v2_Element_Type_Space());
                        nElements.add(new MenuElement_Hover_v2_Element2(nData));
                        nData.clear();
                        if (CFG.game.getProvince(iHoveredProvinceID).getName().length() > 0) {
                            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getProvince(iHoveredProvinceID).getName()));
                            nElements.add(new MenuElement_Hover_v2_Element2(nData));
                            nData.clear();
                        }
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Terrain") + ": "));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.terrainTypesManager.getName(CFG.game.getProvince(iHoveredProvinceID).getTerrainTypeID()), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Terrain(CFG.game.getProvince(iHoveredProvinceID).getTerrainTypeID(), CFG.PADDING, 0));
                        nElements.add(new MenuElement_Hover_v2_Element2(nData));
                        nData.clear();
                        if (!CFG.game.getProvince(iHoveredProvinceID).getSeaProvince()) {
                            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("GrowthRate") + ": "));
                            nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + (int)(CFG.game.getProvince(iHoveredProvinceID).getGrowthRate_Population() * 100.0f) + "%", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                            nElements.add(new MenuElement_Hover_v2_Element2(nData));
                            nData.clear();
                        }
                    }
                    MenuManager.this.provinceHover_Informations = new MenuElement_Hover_v2(nElements);
                }
                catch (IndexOutOfBoundsException ex) {
                    MenuManager.this.provinceHover_Informations = null;
                }
            }
        } : (this.getInCreateScenario_WastelandMap() ? new BuildProvinceHover_Informations(){

            @Override
            public void build() {
                try {
                    if (!CFG.game.getProvince(iHoveredProvinceID).getSeaProvince()) {
                        ArrayList<MenuElement_Hover_v2_Element2> nElements = new ArrayList<MenuElement_Hover_v2_Element2>();
                        ArrayList<MenuElement_Hover_v2_Element_Type> nData = new ArrayList<MenuElement_Hover_v2_Element_Type>();
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Continent") + ": "));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.map.getMapContinents().getName(CFG.game.getProvince(iHoveredProvinceID).getContinent()), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                        nElements.add(new MenuElement_Hover_v2_Element2(nData));
                        nData.clear();
                        if (CFG.game.getProvince(iHoveredProvinceID).getName().length() > 0) {
                            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getProvince(iHoveredProvinceID).getName(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                            nElements.add(new MenuElement_Hover_v2_Element2(nData));
                            nData.clear();
                        }
                        MenuManager.this.provinceHover_Informations = new MenuElement_Hover_v2(nElements);
                    } else {
                        MenuManager.this.provinceHover_Informations = null;
                    }
                }
                catch (IndexOutOfBoundsException ex) {
                    MenuManager.this.provinceHover_Informations = null;
                }
            }
        } : (this.getInCreateScenario_TechnologyLevels() ? new BuildProvinceHover_Informations(){

            @Override
            public void build() {
                try {
                    if (CFG.game.getProvince(iHoveredProvinceID).getWasteland() < 0 && !CFG.game.getProvince(iHoveredProvinceID).getSeaProvince() && CFG.game.getProvince(iHoveredProvinceID).getCivID() > 0) {
                        ArrayList<MenuElement_Hover_v2_Element2> nElements = new ArrayList<MenuElement_Hover_v2_Element2>();
                        ArrayList<MenuElement_Hover_v2_Element_Type> nData = new ArrayList<MenuElement_Hover_v2_Element_Type>();
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + CFG.map.getMapRegions().getName(CFG.game.getProvince(iHoveredProvinceID).getRegion()), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getProvince(iHoveredProvinceID).getCivID(), CFG.PADDING, 0));
                        nElements.add(new MenuElement_Hover_v2_Element2(nData));
                        nData.clear();
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("TechnologyLevel") + ": "));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + CFG.game.getCiv(CFG.game.getProvince(iHoveredProvinceID).getCivID()).getTechnologyLevel(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                        nElements.add(new MenuElement_Hover_v2_Element2(nData));
                        nData.clear();
                        MenuManager.this.provinceHover_Informations = new MenuElement_Hover_v2(nElements);
                    } else {
                        MenuManager.this.provinceHover_Informations = null;
                    }
                }
                catch (IndexOutOfBoundsException ex) {
                    MenuManager.this.provinceHover_Informations = null;
                }
            }
        } : (this.getInManageDiplomacy() ? new BuildProvinceHover_Informations(){

            @Override
            public void build() {
                try {
                    if (CFG.game.getProvince(iHoveredProvinceID).getWasteland() < 0 && !CFG.game.getProvince(iHoveredProvinceID).getSeaProvince() && CFG.game.getProvince(iHoveredProvinceID).getCivID() > 0) {
                        ArrayList<MenuElement_Hover_v2_Element2> nElements = new ArrayList<MenuElement_Hover_v2_Element2>();
                        ArrayList<MenuElement_Hover_v2_Element_Type> nData = new ArrayList<MenuElement_Hover_v2_Element_Type>();
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(CFG.game.getProvince(iHoveredProvinceID).getCivID()).getCivName(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getProvince(iHoveredProvinceID).getCivID(), CFG.PADDING, 0));
                        nElements.add(new MenuElement_Hover_v2_Element2(nData));
                        nData.clear();
                        MenuManager.this.provinceHover_Informations = new MenuElement_Hover_v2(nElements);
                    } else {
                        MenuManager.this.provinceHover_Informations = null;
                    }
                }
                catch (IndexOutOfBoundsException ex) {
                    MenuManager.this.provinceHover_Informations = null;
                }
            }
        } : (this.getInCreateScenario_Happiness() ? new BuildProvinceHover_Informations(){

            @Override
            public void build() {
                try {
                    if (CFG.game.getProvince(iHoveredProvinceID).getWasteland() < 0 && !CFG.game.getProvince(iHoveredProvinceID).getSeaProvince() && CFG.game.getProvince(iHoveredProvinceID).getCivID() > 0) {
                        ArrayList<MenuElement_Hover_v2_Element2> nElements = new ArrayList<MenuElement_Hover_v2_Element2>();
                        ArrayList<MenuElement_Hover_v2_Element_Type> nData = new ArrayList<MenuElement_Hover_v2_Element_Type>();
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(CFG.game.getProvince(iHoveredProvinceID).getCivID()).getCivName(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getProvince(iHoveredProvinceID).getCivID(), CFG.PADDING, 0));
                        nElements.add(new MenuElement_Hover_v2_Element2(nData));
                        nData.clear();
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Happiness") + ": "));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + CFG.game.getCiv(CFG.game.getProvince(iHoveredProvinceID).getCivID()).getHappiness() + "%", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                        nElements.add(new MenuElement_Hover_v2_Element2(nData));
                        nData.clear();
                        MenuManager.this.provinceHover_Informations = new MenuElement_Hover_v2(nElements);
                    } else {
                        MenuManager.this.provinceHover_Informations = null;
                    }
                }
                catch (IndexOutOfBoundsException ex) {
                    MenuManager.this.provinceHover_Informations = null;
                }
            }
        } : (this.getInCreateScenario_StartingMoney() ? new BuildProvinceHover_Informations(){

            @Override
            public void build() {
                try {
                    if (CFG.game.getProvince(iHoveredProvinceID).getWasteland() < 0 && !CFG.game.getProvince(iHoveredProvinceID).getSeaProvince() && CFG.game.getProvince(iHoveredProvinceID).getCivID() > 0) {
                        ArrayList<MenuElement_Hover_v2_Element2> nElements = new ArrayList<MenuElement_Hover_v2_Element2>();
                        ArrayList<MenuElement_Hover_v2_Element_Type> nData = new ArrayList<MenuElement_Hover_v2_Element_Type>();
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(CFG.game.getProvince(iHoveredProvinceID).getCivID()).getCivName(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getProvince(iHoveredProvinceID).getCivID(), CFG.PADDING, 0));
                        nElements.add(new MenuElement_Hover_v2_Element2(nData));
                        nData.clear();
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("StartingMoney") + ": "));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + (-999999L == CFG.game.getCiv(CFG.game.getProvince(iHoveredProvinceID).getCivID()).getMoney() ? (long)CFG.game.getGameScenarios().getScenario_StartingMoney() : CFG.game.getCiv(CFG.game.getProvince(iHoveredProvinceID).getCivID()).getMoney()), (-999999L == CFG.game.getCiv(CFG.game.getProvince(iHoveredProvinceID).getCivID()).getMoney() ? (long)CFG.game.getGameScenarios().getScenario_StartingMoney() : CFG.game.getCiv(CFG.game.getProvince(iHoveredProvinceID).getCivID()).getMoney()) >= 0L ? CFG.COLOR_TEXT_MODIFIER_POSITIVE : CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                        nElements.add(new MenuElement_Hover_v2_Element2(nData));
                        nData.clear();
                        MenuManager.this.provinceHover_Informations = new MenuElement_Hover_v2(nElements);
                    } else {
                        MenuManager.this.provinceHover_Informations = null;
                    }
                }
                catch (IndexOutOfBoundsException ex) {
                    MenuManager.this.provinceHover_Informations = null;
                }
            }
        } : (this.getInMapEditor_Terrain() ? new BuildProvinceHover_Informations(){

            @Override
            public void build() {
                try {
                    ArrayList<MenuElement_Hover_v2_Element2> nElements = new ArrayList<MenuElement_Hover_v2_Element2>();
                    ArrayList<MenuElement_Hover_v2_Element_Type> nData = new ArrayList<MenuElement_Hover_v2_Element_Type>();
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Terrain") + ": "));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.terrainTypesManager.getName(CFG.game.getProvince(iHoveredProvinceID).getTerrainTypeID()), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Terrain(CFG.game.getProvince(iHoveredProvinceID).getTerrainTypeID(), CFG.PADDING, 0));
                    nElements.add(new MenuElement_Hover_v2_Element2(nData));
                    nData.clear();
                    if (CFG.game.getProvince(iHoveredProvinceID).getName().length() > 0) {
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getProvince(iHoveredProvinceID).getName(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                        nElements.add(new MenuElement_Hover_v2_Element2(nData));
                        nData.clear();
                    }
                    MenuManager.this.provinceHover_Informations = new MenuElement_Hover_v2(nElements);
                }
                catch (IndexOutOfBoundsException ex) {
                    MenuManager.this.provinceHover_Informations = null;
                }
            }
        } : (this.getInMapEditor_SeaProvinces() ? new BuildProvinceHover_Informations(){

            @Override
            public void build() {
                try {
                    ArrayList<MenuElement_Hover_v2_Element2> nElements = new ArrayList<MenuElement_Hover_v2_Element2>();
                    ArrayList<MenuElement_Hover_v2_Element_Type> nData = new ArrayList<MenuElement_Hover_v2_Element_Type>();
                    if (CFG.game.getProvince(iHoveredProvinceID).getLevelOfPort() >= -1) {
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("LandProvince") + ": "));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + CFG.game.getProvince(iHoveredProvinceID).getLevelOfPort(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                        nElements.add(new MenuElement_Hover_v2_Element2(nData));
                        nData.clear();
                    } else if (CFG.game.getProvince(iHoveredProvinceID).getLevelOfPort() == -2) {
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("SeaProvince") + ": "));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + CFG.game.getProvince(iHoveredProvinceID).getLevelOfPort(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                        nElements.add(new MenuElement_Hover_v2_Element2(nData));
                        nData.clear();
                    } else if (CFG.game.getProvince(iHoveredProvinceID).getLevelOfPort() == -3) {
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("ClosedSeaLake") + ": "));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + CFG.game.getProvince(iHoveredProvinceID).getLevelOfPort(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                        nElements.add(new MenuElement_Hover_v2_Element2(nData));
                        nData.clear();
                    }
                    if (CFG.game.getProvince(iHoveredProvinceID).getName().length() > 0) {
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getProvince(iHoveredProvinceID).getName(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                        nElements.add(new MenuElement_Hover_v2_Element2(nData));
                        nData.clear();
                    }
                    MenuManager.this.provinceHover_Informations = new MenuElement_Hover_v2(nElements);
                }
                catch (IndexOutOfBoundsException ex) {
                    MenuManager.this.provinceHover_Informations = null;
                }
            }
        } : (this.getInMapEditor_Continents() ? new BuildProvinceHover_Informations(){

            @Override
            public void build() {
                try {
                    ArrayList<MenuElement_Hover_v2_Element2> nElements = new ArrayList<MenuElement_Hover_v2_Element2>();
                    ArrayList<MenuElement_Hover_v2_Element_Type> nData = new ArrayList<MenuElement_Hover_v2_Element_Type>();
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Continent") + ": "));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + CFG.map.getMapContinents().getName(CFG.game.getProvince(iHoveredProvinceID).getContinent()), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                    nElements.add(new MenuElement_Hover_v2_Element2(nData));
                    nData.clear();
                    if (CFG.game.getProvince(iHoveredProvinceID).getName().length() > 0) {
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getProvince(iHoveredProvinceID).getName(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                        nElements.add(new MenuElement_Hover_v2_Element2(nData));
                        nData.clear();
                    }
                    MenuManager.this.provinceHover_Informations = new MenuElement_Hover_v2(nElements);
                }
                catch (IndexOutOfBoundsException ex) {
                    MenuManager.this.provinceHover_Informations = null;
                }
            }
        } : (this.getInMapEditor_Regions() ? new BuildProvinceHover_Informations(){

            @Override
            public void build() {
                try {
                    if (CFG.game.getProvince(iHoveredProvinceID).getSeaProvince()) {
                        MenuManager.this.provinceHover_Informations = null;
                    } else {
                        ArrayList<MenuElement_Hover_v2_Element2> nElements = new ArrayList<MenuElement_Hover_v2_Element2>();
                        ArrayList<MenuElement_Hover_v2_Element_Type> nData = new ArrayList<MenuElement_Hover_v2_Element_Type>();
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Region") + ": "));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + CFG.map.getMapRegions().getName(CFG.game.getProvince(iHoveredProvinceID).getRegion()), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                        nElements.add(new MenuElement_Hover_v2_Element2(nData));
                        nData.clear();
                        if (CFG.game.getProvince(iHoveredProvinceID).getName().length() > 0) {
                            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getProvince(iHoveredProvinceID).getName(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                            nElements.add(new MenuElement_Hover_v2_Element2(nData));
                            nData.clear();
                        }
                        MenuManager.this.provinceHover_Informations = new MenuElement_Hover_v2(nElements);
                    }
                }
                catch (IndexOutOfBoundsException ex) {
                    MenuManager.this.provinceHover_Informations = null;
                }
            }
        } : (this.getInMapEditor_GrowthRate() ? new BuildProvinceHover_Informations(){

            @Override
            public void build() {
                try {
                    ArrayList<MenuElement_Hover_v2_Element2> nElements = new ArrayList<MenuElement_Hover_v2_Element2>();
                    ArrayList<MenuElement_Hover_v2_Element_Type> nData = new ArrayList<MenuElement_Hover_v2_Element_Type>();
                    if (CFG.game.getProvince(iHoveredProvinceID).getSeaProvince()) {
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Terrain") + ": "));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.terrainTypesManager.getName(CFG.game.getProvince(iHoveredProvinceID).getTerrainTypeID()), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Terrain(CFG.game.getProvince(iHoveredProvinceID).getTerrainTypeID(), CFG.PADDING, 0));
                        nElements.add(new MenuElement_Hover_v2_Element2(nData));
                        nData.clear();
                        if (CFG.game.getProvince(iHoveredProvinceID).getName().length() > 0) {
                            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getProvince(iHoveredProvinceID).getName(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                            nElements.add(new MenuElement_Hover_v2_Element2(nData));
                            nData.clear();
                        }
                    } else {
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Terrain") + ": "));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.terrainTypesManager.getName(CFG.game.getProvince(iHoveredProvinceID).getTerrainTypeID()), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                        nData.add(new MenuElement_Hover_v2_Element_Type_Terrain(CFG.game.getProvince(iHoveredProvinceID).getTerrainTypeID(), CFG.PADDING, 0));
                        nElements.add(new MenuElement_Hover_v2_Element2(nData));
                        nData.clear();
                        if (CFG.game.getProvince(iHoveredProvinceID).getName().length() > 0) {
                            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getProvince(iHoveredProvinceID).getName(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                            nElements.add(new MenuElement_Hover_v2_Element2(nData));
                            nData.clear();
                        }
                    }
                    MenuManager.this.provinceHover_Informations = new MenuElement_Hover_v2(nElements);
                }
                catch (IndexOutOfBoundsException ex) {
                    MenuManager.this.provinceHover_Informations = null;
                }
            }
        } : (this.getInGameEditor_Regions() ? new BuildProvinceHover_Informations(){

            @Override
            public void build() {
                try {
                    ArrayList<MenuElement_Hover_v2_Element2> nElements = new ArrayList<MenuElement_Hover_v2_Element2>();
                    ArrayList<MenuElement_Hover_v2_Element_Type> nData = new ArrayList<MenuElement_Hover_v2_Element_Type>();
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Region") + ": "));
                    nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + CFG.game.getRegionID(iHoveredProvinceID), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                    nElements.add(new MenuElement_Hover_v2_Element2(nData));
                    nData.clear();
                    MenuManager.this.provinceHover_Informations = new MenuElement_Hover_v2(nElements);
                }
                catch (IndexOutOfBoundsException ex) {
                    MenuManager.this.provinceHover_Informations = null;
                }
            }
        } : new BuildProvinceHover_Informations(){

            @Override
            public void build() {
            }
        }))))))))))))))))))));
        this.provinceHover_Informations = null;
    }

    protected final void updateHoveredProvince_Hover(int nPosX, int nPosY) {
        //moved try up one 'if' statement, bugfix change//
        try {
            if (this.getDialogMenu().getVisible()) {
                this.provinceHover_Informations = null;
                return;
            }
            if (this.hoverActiveMenuElementID < 0 && this.hoverActiveSliderMenuID < 0) {
                if (iHoveredProvinceID >= 0) {
                    if (CFG.game.setProvinceID_IsMouseOverAProvinceID((int)((float)nPosX / CFG.map.getMapScale().getCurrentScale()), (int)((float)nPosY / CFG.map.getMapScale().getCurrentScale()), iHoveredProvinceID)) {
                        return;
                    }
                    iHoveredProvinceID = CFG.game.setProvinceID_HoverAProvince((int)((float)nPosX / CFG.map.getMapScale().getCurrentScale()), (int)((float)nPosY / CFG.map.getMapScale().getCurrentScale()));
                    CFG.setRender_3(true);
                    if (iHoveredProvinceID < 0) {
                        this.provinceHover_Informations = null;
                    }
                } else {
                    iHoveredProvinceID = CFG.game.setProvinceID_HoverAProvince((int)((float)nPosX / CFG.map.getMapScale().getCurrentScale()), (int)((float)nPosY / CFG.map.getMapScale().getCurrentScale()));
                    CFG.setRender_3(true);
                    if (iHoveredProvinceID >= 0) {
                        MenuElement_Hover_v2.resetAnimation();
                    } else {
                        this.provinceHover_Informations = null;
                    }
                }
                buildProvinceHover_Informations.build();
            } else {
                this.provinceHover_Informations = null;
                iHoveredProvinceID = -1;
            }
        }
        catch (NullPointerException ex) {
            this.provinceHover_Informations = null;
            iHoveredProvinceID = -1;
        }
        catch (IndexOutOfBoundsException ex) {
            this.provinceHover_Informations = null;
            iHoveredProvinceID = -1;
        }
    }

    protected final boolean isSomethingHovered() {
        try {
            return this.hoverActiveMenuElementID >= 0 && this.hoverActiveSliderMenuID >= 0 && this.getActiveMenu().get((int)this.hoverActiveSliderMenuID).getMenuElement((int)this.hoverActiveMenuElementID).menuElementHover != null;
        }
        catch (IndexOutOfBoundsException ex) {
            return false;
        }
        catch (NullPointerException ex) {
            return false;
        }
    }

    protected final void resetHoverActive() {
        block5: {
            try {
                if (this.hoverActiveSliderMenuID >= 0 && this.hoverActiveMenuElementID >= 0) {
                    this.getActiveMenu().get(this.hoverActiveSliderMenuID).getMenuElement(this.hoverActiveMenuElementID).setIsHovered(false);
                    this.getActiveMenu().get(this.hoverActiveSliderMenuID).getMenuElement(this.hoverActiveMenuElementID).resetElementHover();
                    this.hoverActiveSliderMenuID = -1;
                    this.hoverActiveMenuElementID = -1;
                    CFG.setRender_3(true);
                }
            }
            catch (IndexOutOfBoundsException ex) {
                if (CFG.LOGS) {
                    CFG.exceptionStack(ex);
                }
            }
            catch (NullPointerException ex) {
                if (!CFG.LOGS) break block5;
                CFG.exceptionStack(ex);
            }
        }
    }

    protected final boolean actionMove_Hover(int nPosX, int nPosY) {
        block19: {
            try {
                if (this.getDialogMenu().getVisible()) {
                    this.resetHoverActive();
                    return true;
                }
                if (this.hoverActiveMenuElementID >= 0 && this.hoverActiveSliderMenuID >= 0) {
                    try {
                        if (this.getActiveMenu().get(this.hoverActiveSliderMenuID).getVisible() && this.getActiveMenu().get((int)this.hoverActiveSliderMenuID).getMenuElement((int)this.hoverActiveMenuElementID).typeOfElement != MenuElement.TypeOfElement.BUTTON_TRANSPARENT) {
                            if (this.getActiveMenu().get(this.hoverActiveSliderMenuID).getMenuElement(this.hoverActiveMenuElementID).getClickable() && nPosX >= this.getActiveMenu().get(this.hoverActiveSliderMenuID).getMenuElement(this.hoverActiveMenuElementID).getPosX() + this.getActiveMenu().get(this.hoverActiveSliderMenuID).getMenuPosX() && nPosX <= this.getActiveMenu().get(this.hoverActiveSliderMenuID).getMenuElement(this.hoverActiveMenuElementID).getPosX() + this.getActiveMenu().get(this.hoverActiveSliderMenuID).getMenuPosX() + this.getActiveMenu().get(this.hoverActiveSliderMenuID).getMenuElement(this.hoverActiveMenuElementID).getWidth() && nPosY >= this.getActiveMenu().get(this.hoverActiveSliderMenuID).getMenuElement(this.hoverActiveMenuElementID).getPosY() + this.getActiveMenu().get(this.hoverActiveSliderMenuID).getMenuPosY() && nPosY <= this.getActiveMenu().get(this.hoverActiveSliderMenuID).getMenuElement(this.hoverActiveMenuElementID).getPosY() + this.getActiveMenu().get(this.hoverActiveSliderMenuID).getMenuElement(this.hoverActiveMenuElementID).getHeight() + this.getActiveMenu().get(this.hoverActiveSliderMenuID).getMenuPosY()) {
                                return true;
                            }
                        } else {
                            this.resetHoverActive();
                        }
                    }
                    catch (IndexOutOfBoundsException ex) {
                        this.resetHoverActive();
                    }
                }
                try {
                    for (int i = 0; i < this.getActiveMenu().size(); ++i) {
                        if (!this.getActiveMenu().get(this.getActiveOrder(i)).getVisible() || nPosX < this.getActiveMenu().get(this.getActiveOrder(i)).getPosX() || nPosX > this.getActiveMenu().get(this.getActiveOrder(i)).getPosX() + this.getActiveMenu().get(this.getActiveOrder(i)).getWidth() || nPosY < this.getActiveMenu().get(this.getActiveOrder(i)).getPosY() || nPosY > this.getActiveMenu().get(this.getActiveOrder(i)).getPosY() + this.getActiveMenu().get(this.getActiveOrder(i)).getHeight()) continue;
                        for (int j = 0; j < this.getActiveMenu().get(this.getActiveOrder(i)).getMenuElementsSize(); ++j) {
                            if (!this.getActiveMenu().get(this.getActiveOrder(i)).getMenuElement(j).getVisible() || nPosX < this.getActiveMenu().get(this.getActiveOrder(i)).getMenuElement(j).getPosX() + this.getActiveMenu().get(this.getActiveOrder(i)).getMenuPosX() || nPosX > this.getActiveMenu().get(this.getActiveOrder(i)).getMenuElement(j).getPosX() + this.getActiveMenu().get(this.getActiveOrder(i)).getMenuPosX() + this.getActiveMenu().get(this.getActiveOrder(i)).getMenuElement(j).getWidth() || nPosY < this.getActiveMenu().get(this.getActiveOrder(i)).getMenuElement(j).getPosY() + this.getActiveMenu().get(this.getActiveOrder(i)).getMenuPosY() || nPosY > this.getActiveMenu().get(this.getActiveOrder(i)).getMenuElement(j).getPosY() + this.getActiveMenu().get(this.getActiveOrder(i)).getMenuElement(j).getHeight() + this.getActiveMenu().get(this.getActiveOrder(i)).getMenuPosY()) continue;
                            if (this.hoverActiveSliderMenuID != this.getActiveOrder(i) || this.hoverActiveMenuElementID != j) {
                                if (this.hoverActiveMenuElementID >= 0 && this.hoverActiveSliderMenuID >= 0) {
                                    this.resetHoverActive();
                                } else if (this.provinceHover_Informations != null) {
                                    MenuElement_Hover_v2.resetAnimation();
                                }
                                this.hoverActiveSliderMenuID = this.getActiveOrder(i);
                                this.hoverActiveMenuElementID = j;
                                this.getActiveMenu().get(this.hoverActiveSliderMenuID).getMenuElement(this.hoverActiveMenuElementID).setIsHovered(true);
                                this.getActiveMenu().get(this.hoverActiveSliderMenuID).getMenuElement(this.hoverActiveMenuElementID).buildElementHover();
                                CFG.setRender_3(true);
                            }
                            return true;
                        }
                    }
                }
                catch (IndexOutOfBoundsException ex) {
                    this.resetHoverActive();
                    CFG.setRender_3(true);
                }
                this.resetHoverActive();
            }
            catch (GdxRuntimeException ex) {
                if (CFG.LOGS) {
                    CFG.exceptionStack(ex);
                }
            }
            catch (NullPointerException ex) {
                if (!CFG.LOGS) break block19;
                CFG.exceptionStack(ex);
            }
        }
        return false;
    }

    protected final void updateHoveredMenuElement_Hover(int nPosX, int nPosY) {
        block5: {
            try {
                if (this.hoverActiveSliderMenuID >= 0 && this.hoverActiveMenuElementID >= 0) {
                    this.getActiveMenu().get(this.hoverActiveSliderMenuID).getMenuElement(this.hoverActiveMenuElementID).updateHover(nPosX, nPosY, this.getActiveMenu().get(this.hoverActiveSliderMenuID).getMenuPosX(), this.getActiveMenu().get(this.hoverActiveSliderMenuID).getMenuPosY());
                }
            }
            catch (IndexOutOfBoundsException ex) {
                if (CFG.LOGS) {
                    CFG.exceptionStack(ex);
                }
            }
            catch (NullPointerException ex) {
                if (!CFG.LOGS) break block5;
                CFG.exceptionStack(ex);
            }
        }
    }

    protected final boolean getIsScrollableY_MenuHovered() {
        try {
            return this.getActiveMenu().get(this.hoverActiveSliderMenuID).getScrollableY();
        }
        catch (IndexOutOfBoundsException ex) {
            return false;
        }
        catch (NullPointerException ex) {
            return false;
        }
    }

    protected final boolean getIsScrollableX_MenuHovered() {
        try {
            return this.getActiveMenu().get(this.hoverActiveSliderMenuID).getScrollableX();
        }
        catch (IndexOutOfBoundsException ex) {
            return false;
        }
        catch (NullPointerException ex) {
            return false;
        }
    }

    protected final boolean getIsScrollable_Hovered_MenuElement() {
        try {
            return this.getActiveMenu().get(this.hoverActiveSliderMenuID).getMenuElement(this.hoverActiveMenuElementID).getIsScrollable();
        }
        catch (IndexOutOfBoundsException ex) {
            return false;
        }
        catch (NullPointerException ex) {
            return false;
        }
    }

    protected final void scrollHoveredMenuElement(int nChange) {
        block4: {
            try {
                this.getActiveMenu().get(this.hoverActiveSliderMenuID).getMenuElement(this.hoverActiveMenuElementID).srollByWheel(nChange);
            }
            catch (IndexOutOfBoundsException ex) {
                if (CFG.LOGS) {
                    CFG.exceptionStack(ex);
                }
            }
            catch (NullPointerException ex) {
                if (!CFG.LOGS) break block4;
                CFG.exceptionStack(ex);
            }
        }
    }

    protected final void scrollHoveredMenu_Y(int nChange) {
        block4: {
            try {
                this.getActiveMenu().get(this.hoverActiveSliderMenuID).stopScrolling();
                this.getActiveMenu().get(this.hoverActiveSliderMenuID).updateMenuPosY(this.getActiveMenu().get(this.hoverActiveSliderMenuID).getNewMenuPosY() + nChange);
            }
            catch (IndexOutOfBoundsException ex) {
                if (CFG.LOGS) {
                    CFG.exceptionStack(ex);
                }
            }
            catch (NullPointerException ex) {
                if (!CFG.LOGS) break block4;
                CFG.exceptionStack(ex);
            }
        }
    }

    protected final void scrollHoveredMenu_X(int nChange) {
        block4: {
            try {
                this.getActiveMenu().get(this.hoverActiveSliderMenuID).stopScrolling();
                this.getActiveMenu().get(this.hoverActiveSliderMenuID).updateMenuPosX(this.getActiveMenu().get(this.hoverActiveSliderMenuID).getNewMenuPosX() + nChange);
            }
            catch (IndexOutOfBoundsException ex) {
                if (CFG.LOGS) {
                    CFG.exceptionStack(ex);
                }
            }
            catch (NullPointerException ex) {
                if (!CFG.LOGS) break block4;
                CFG.exceptionStack(ex);
            }
        }
    }

    protected final boolean actionDown(int nPosX, int nPosY) {
        block46: {
            try {
                if (this.dialogMenu.getVisible()) {
                    for (int i = 0; i < this.dialogMenu.getMenuElementsSize(); ++i) {
                        if (nPosX < this.dialogMenu.getMenuElement(i).getPosX() || nPosX > this.dialogMenu.getMenuElement(i).getPosX() + this.dialogMenu.getMenuElement(i).getWidth() || nPosY < this.dialogMenu.getMenuElement(i).getPosY() || nPosY > this.dialogMenu.getMenuElement(i).getPosY() + this.dialogMenu.getMenuElement(i).getHeight()) continue;
                        this.activeMenuElementID = i;
                        return true;
                    }
                    return true;
                }
                if (this.keyboard.getVisible()) {
                    for (int i = 0; i < this.keyboard.getMenuElementsSize(); ++i) {
                        if (nPosX < this.keyboard.getMenuElement(i).getPosX() + this.keyboard.getPosX() || nPosX > this.keyboard.getMenuElement(i).getPosX() + this.keyboard.getPosX() + this.keyboard.getMenuElement(i).getWidth() || nPosY < this.keyboard.getMenuElement(i).getPosY() + this.keyboard.getPosY() || nPosY > this.keyboard.getMenuElement(i).getPosY() + this.keyboard.getPosY() + this.keyboard.getMenuElement(i).getHeight()) continue;
                        this.keyboardMode = true;
                        this.activeMenuElementID = i;
                        return true;
                    }
                    if (nPosX >= this.keyboard.getPosX() && nPosX <= this.keyboard.getPosX() + this.keyboard.getWidth() && nPosY >= this.keyboard.getPosY() && nPosY <= this.keyboard.getPosY() + this.keyboard.getHeight()) {
                        this.keyboardMode = true;
                        return true;
                    }
                }
                if (this.colorPickerMode) {
                    this.colorPicker.touch(nPosX, nPosY);
                    return true;
                }
                if (this.colorPicker.getVisible() && nPosX >= this.colorPicker.getPosX() && nPosX <= this.colorPicker.getPosX() + this.colorPicker.getWidth() && nPosY >= this.colorPicker.getPosY() && nPosY <= this.colorPicker.getPosY() + this.colorPicker.getHeight()) {
                    this.colorPickerMode = true;
                    this.colorPicker.touch(nPosX, nPosY);
                    return true;
                }
                if (this.actionDownClose(nPosX, nPosY)) {
                    return true;
                }
                if (this.actionDownSliderMenuResize(nPosX, nPosY)) {
                    return true;
                }
                if (this.actionDownSliderMenuTitle(nPosX, nPosY)) {
                    return true;
                }
            }
            catch (IndexOutOfBoundsException i) {
            }
            catch (NullPointerException i) {
                // empty catch block
            }
            try {
                for (int i = 0; i < this.getActiveMenu().size(); ++i) {
                    if (!this.getActiveMenu().get(this.getActiveOrder(i)).getVisible() || nPosX < this.getActiveMenu().get(this.getActiveOrder(i)).getPosX() || nPosX > this.getActiveMenu().get(this.getActiveOrder(i)).getPosX() + this.getActiveMenu().get(this.getActiveOrder(i)).getWidth() || nPosY < this.getActiveMenu().get(this.getActiveOrder(i)).getPosY() || nPosY > this.getActiveMenu().get(this.getActiveOrder(i)).getPosY() + this.getActiveMenu().get(this.getActiveOrder(i)).getHeight()) continue;
                    this.activeSliderMenuID = this.getActiveOrder(i);
                    this.getActiveMenu().get(this.activeSliderMenuID).stopScrolling();
                    if (this.getActiveMenu().get(this.getActiveOrder(i)).getScrollableY() || this.getActiveMenu().get(this.getActiveOrder(i)).getScrollableX()) {
                        this.sliderMenuMode = true;
                        this.iSliderMenuStartPosY = this.getActiveMenu().get(this.getActiveOrder(i)).getMenuPosY() - nPosY;
                        this.iSliderMenuActionDownPosY = nPosY;
                        this.iSliderMenuStartPosX = this.getActiveMenu().get(this.getActiveOrder(i)).getMenuPosX() - nPosX;
                        this.iSliderMenuActionDownPosX = nPosX;
                    }
                    for (int j = 0; j < this.getActiveMenu().get(this.getActiveOrder(i)).getMenuElementsSize(); ++j) {
                        if (!this.getActiveMenu().get(this.getActiveOrder(i)).getMenuElement(j).getVisible() || nPosX < this.getActiveMenu().get(this.getActiveOrder(i)).getMenuElement(j).getPosX() + this.getActiveMenu().get(this.getActiveOrder(i)).getMenuPosX() || nPosX > this.getActiveMenu().get(this.getActiveOrder(i)).getMenuElement(j).getPosX() + this.getActiveMenu().get(this.getActiveOrder(i)).getMenuPosX() + this.getActiveMenu().get(this.getActiveOrder(i)).getMenuElement(j).getWidth() || nPosY < this.getActiveMenu().get(this.getActiveOrder(i)).getMenuElement(j).getPosY() + this.getActiveMenu().get(this.getActiveOrder(i)).getMenuPosY() || nPosY > this.getActiveMenu().get(this.getActiveOrder(i)).getMenuElement(j).getPosY() + this.getActiveMenu().get(this.getActiveOrder(i)).getMenuElement(j).getHeight() + this.getActiveMenu().get(this.getActiveOrder(i)).getMenuPosY()) continue;
                        this.activeMenuElementID = j;
                        if (CFG.isAndroid()) {
                            if (this.hoverActiveSliderMenuID != this.activeSliderMenuID || this.hoverActiveMenuElementID != j) {
                                if (this.hoverActiveMenuElementID >= 0 && this.hoverActiveSliderMenuID >= 0) {
                                    this.resetHoverActive();
                                } else if (this.provinceHover_Informations != null) {
                                    MenuElement_Hover_v2.resetAnimation();
                                }
                                this.hoverActiveSliderMenuID = this.activeSliderMenuID;
                                this.hoverActiveMenuElementID = this.activeMenuElementID;
                                this.getActiveMenu().get(this.hoverActiveSliderMenuID).getMenuElement(this.hoverActiveMenuElementID).setIsHovered(true);
                                this.getActiveMenu().get(this.hoverActiveSliderMenuID).getMenuElement(this.hoverActiveMenuElementID).buildElementHover();
                                hoverMobileTime = System.currentTimeMillis();
                                CFG.setRender_3(true);
                            } else {
                                hoverMobileTime = System.currentTimeMillis();
                            }
                        }
                        this.setOrderOfMenu(this.getActiveOrder(i));
                        this.getActiveMenu().get(this.activeSliderMenuID).onHovered();
                        switch (this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(this.activeMenuElementID).getTypeOfElement()) {
                            case SLIDER: {
                                if (!this.getActiveMenuElement().getClickable()) break;
                                this.sliderMode = true;
                                this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(this.activeMenuElementID).updateSlider(nPosX - this.getActiveMenu().get(this.activeSliderMenuID).getPosX());
                                try {
                                    CFG.soundsManager.playSound(this.getActiveMenuElement().getSFX());
                                }
                                catch (IndexOutOfBoundsException ex) {
                                    CFG.soundsManager.playSound(SoundsManager.SOUND_CLICK);
                                }
                                break;
                            }
                            case SLIDE: {
                                this.slideMapMode = true;
                                this.slidePercent = 0.15f;
                                break;
                            }
                            case FLAG_PIXEL: {
                                this.flagEditorMode = true;
                                if (CFG.flagEditorMode != CFG.FlagEditorMode.PENCIL) break;
                                this.actionElement(this.activeSliderMenuID, this.activeMenuElementID);
                                break;
                            }
                            case TEXT_SLIDER: {
                                if (!this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(this.activeMenuElementID).getMoveable()) break;
                                this.sliderMenuMode = false;
                                this.textSliderMode = true;
                                this.iSliderMenuStartPosY = this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(this.activeMenuElementID).getCurrent() - nPosY;
                                this.iSliderMenuActionDownPosY = nPosY;
                                break;
                            }
                            case GRAPH: {
                                if (nPosX >= this.getActiveMenu().get(this.getActiveOrder(i)).getMenuElement(j).getPosX() + this.getActiveMenu().get(this.getActiveOrder(i)).getMenuPosX() + this.getActiveMenu().get(this.getActiveOrder(i)).getMenuElement(j).getWidth() - Graph.getGraphButtonWidth() && nPosX <= this.getActiveMenu().get(this.getActiveOrder(i)).getMenuElement(j).getPosX() + this.getActiveMenu().get(this.getActiveOrder(i)).getMenuPosX() + this.getActiveMenu().get(this.getActiveOrder(i)).getMenuElement(j).getWidth()) {
                                    this.iSliderMenuStartPosY = this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(this.activeMenuElementID).getCurrent() - nPosY;
                                    this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(this.activeMenuElementID).setScrollPosY(-this.getActiveMenu().get(this.activeSliderMenuID).getPosY() + nPosY);
                                    this.graphButtonMode2 = true;
                                } else {
                                    this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(this.activeMenuElementID).updateSlider(-this.getActiveMenu().get(this.activeSliderMenuID).getPosX() + nPosX);
                                    this.graphMode = true;
                                }
                                this.iSliderMenuActionDownPosY = nPosY;
                                this.iSliderMenuActionDownPosX = nPosX;
                                this.sliderMenuMode = false;
                                break;
                            }
                            case GRAPH_VERTICAL: {
                                if (this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(this.activeMenuElementID).getMoveable()) {
                                    if (this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(this.activeMenuElementID).getAnotherView()) {
                                        this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(this.activeMenuElementID).setTypeOfButton(null);
                                        this.iSliderMenuStartPosY = this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(this.activeMenuElementID).getCurrent() - nPosY;
                                        this.graphButtonMode = true;
                                    } else {
                                        this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(this.activeMenuElementID).setTypeOfButton(null);
                                        this.iSliderMenuStartPosX = this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(this.activeMenuElementID).getCurrent() - nPosX;
                                        this.graphButtonModeX = true;
                                    }
                                    this.iSliderMenuActionDownPosY = nPosY;
                                    this.iSliderMenuActionDownPosX = nPosX;
                                } else {
                                    this.graphButtonModeX = true;
                                    this.iSliderMenuActionDownPosY = nPosY;
                                    this.iSliderMenuActionDownPosX = nPosX;
                                }
                                this.iSliderMenuStartPosX = this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(this.activeMenuElementID).getCurrent() - nPosX;
                                this.iSliderMenuActionDownPosX = nPosX;
                                this.sliderMenuMode = false;
                                try {
                                    CFG.soundsManager.playSound(this.getActiveMenuElement().getSFX());
                                }
                                catch (IndexOutOfBoundsException ex) {
                                    CFG.soundsManager.playSound(SoundsManager.SOUND_CLICK);
                                }
                                break;
                            }
                            case DIPLOMACY_INFO: {
                                if (nPosX <= this.getActiveMenu().get(this.activeSliderMenuID).getPosX() + this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(this.activeMenuElementID).getPosX() + Button_Diplomacy.iDiploWidth) break;
                                this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(this.activeMenuElementID).setTypeOfButton(null);
                                this.iSliderMenuStartPosX = this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(this.activeMenuElementID).getCurrent() - nPosX;
                                this.graphButtonModeX = true;
                                this.iSliderMenuActionDownPosY = nPosY;
                                this.iSliderMenuActionDownPosX = nPosX;
                                if (!this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(this.activeMenuElementID).getMoveable()) break;
                                this.sliderMenuMode = false;
                                break;
                            }
                            case GRAPH_CIRCLE: {
                                this.graphButtonMode = true;
                                this.iSliderMenuStartPosY = this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(this.activeMenuElementID).getCurrent() - nPosY;
                                this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(this.activeMenuElementID).setScrollPosY(nPosY);
                                this.iSliderMenuActionDownPosY = nPosY;
                                this.iSliderMenuActionDownPosX = nPosX;
                                this.sliderMenuMode = false;
                                break;
                            }
                        }
                        return true;
                    }
                }
            }
            catch (IndexOutOfBoundsException ex) {
                CFG.setRender_3(true);
                if (CFG.LOGS) {
                    CFG.exceptionStack(ex);
                }
            }
            catch (NullPointerException ex) {
                CFG.setRender_3(true);
                if (!CFG.LOGS) break block46;
                CFG.exceptionStack(ex);
            }
        }
        return false;
    }

    private final boolean actionDownClose(int nPosX, int nPosY) {
        block5: {
            try {
                for (int i = 0; i < this.getActiveMenu().size(); ++i) {
                    if (!this.getActiveMenu().get(this.getActiveOrder(i)).getVisible() || !this.getActiveMenu().get(this.getActiveOrder(i)).getCloseable() || nPosX < this.getActiveMenu().get(this.getActiveOrder(i)).getPosX() + this.getActiveMenu().get(this.getActiveOrder(i)).getWidth() - ImageManager.getImage(Images.btn_close).getWidth() || nPosX > this.getActiveMenu().get(this.getActiveOrder(i)).getPosX() + this.getActiveMenu().get(this.getActiveOrder(i)).getWidth()) continue;
                    if (this.getActiveMenu().get(this.getActiveOrder(i)).getTitle() == null) {
                        if (nPosY < this.getActiveMenu().get(this.getActiveOrder(i)).getPosY() || nPosY > this.getActiveMenu().get(this.getActiveOrder(i)).getPosY() + ImageManager.getImage(Images.btn_close).getHeight()) continue;
                        this.activeSliderMenuID = i;
                        this.sliderMenuCloseMode = true;
                        return true;
                    }
                    if (nPosY < this.getActiveMenu().get(this.getActiveOrder(i)).getPosY() - this.getActiveMenu().get(this.getActiveOrder(i)).getTitle().getHeight() || nPosY > this.getActiveMenu().get(this.getActiveOrder(i)).getPosY() + ImageManager.getImage(Images.btn_close).getHeight() - this.getActiveMenu().get(this.getActiveOrder(i)).getTitle().getHeight()) continue;
                    this.activeSliderMenuID = i;
                    this.sliderMenuCloseMode = true;
                    return true;
                }
            }
            catch (IndexOutOfBoundsException ex) {
                CFG.setRender_3(true);
            }
            catch (NullPointerException ex) {
                CFG.setRender_3(true);
                if (!CFG.LOGS) break block5;
                CFG.exceptionStack(ex);
            }
        }
        return false;
    }

    private final boolean actionDownSliderMenuResize(int nPosX, int nPosY) {
        block6: {
            try {
                if (!this.getInGameView_Options() && !this.getInGameView_EndOfGame()) {
                    for (int i = 0; i < this.getActiveMenu().size(); ++i) {
                        if (!this.getActiveMenu().get(this.getActiveOrder(i)).getVisible() || !this.getActiveMenu().get(this.getActiveOrder(i)).getResizable()) continue;
                        if (nPosX >= this.getActiveMenu().get(this.getActiveOrder(i)).getPosX() + this.getActiveMenu().get(this.getActiveOrder(i)).getWidth() - CFG.PADDING * 6 && nPosX <= this.getActiveMenu().get(this.getActiveOrder(i)).getPosX() + this.getActiveMenu().get(this.getActiveOrder(i)).getWidth() && nPosY >= this.getActiveMenu().get(this.getActiveOrder(i)).getPosY() + this.getActiveMenu().get(this.getActiveOrder(i)).getHeight() - CFG.PADDING * 6 && nPosY <= this.getActiveMenu().get(this.getActiveOrder(i)).getPosY() + this.getActiveMenu().get(this.getActiveOrder(i)).getHeight()) {
                            this.startSliderMenuResizeMode(this.getActiveOrder(i), nPosX, nPosY, false);
                            this.setOrderOfMenu(this.getActiveOrder(i));
                            return true;
                        }
                        if (nPosX < this.getActiveMenu().get(this.getActiveOrder(i)).getPosX() || nPosX > this.getActiveMenu().get(this.getActiveOrder(i)).getPosX() + CFG.PADDING * 6 || nPosY < this.getActiveMenu().get(this.getActiveOrder(i)).getPosY() + this.getActiveMenu().get(this.getActiveOrder(i)).getHeight() - CFG.PADDING * 6 || nPosY > this.getActiveMenu().get(this.getActiveOrder(i)).getPosY() + this.getActiveMenu().get(this.getActiveOrder(i)).getHeight()) continue;
                        this.startSliderMenuResizeMode(this.getActiveOrder(i), nPosX, nPosY, true);
                        this.iSliderMenuActionDownPosX = nPosX;
                        this.setOrderOfMenu(this.getActiveOrder(i));
                        return true;
                    }
                }
            }
            catch (IndexOutOfBoundsException ex) {
                CFG.setRender_3(true);
            }
            catch (NullPointerException ex) {
                CFG.setRender_3(true);
                if (!CFG.LOGS) break block6;
                CFG.exceptionStack(ex);
            }
        }
        return false;
    }

    private final boolean actionDownSliderMenuTitle(int nPosX, int nPosY) {
        block5: {
            try {
                if (!this.getInGameView_Options() && !this.getInGameView_EndOfGame()) {
                    for (int i = 0; i < this.getActiveMenu().size(); ++i) {
                        if (!this.getActiveMenu().get(this.getActiveOrder(i)).getVisible() || !this.getActiveMenu().get(this.getActiveOrder(i)).getMoveable() || nPosX < this.getActiveMenu().get(this.getActiveOrder(i)).getPosX() || nPosX > this.getActiveMenu().get(this.getActiveOrder(i)).getPosX() + this.getActiveMenu().get(this.getActiveOrder(i)).getWidth() || nPosY < this.getActiveMenu().get(this.getActiveOrder(i)).getPosY() - this.getActiveMenu().get(this.getActiveOrder(i)).getTitle().getHeight() || nPosY > this.getActiveMenu().get(this.getActiveOrder(i)).getPosY()) continue;
                        this.sliderMenuTitleMode = true;
                        this.activeSliderMenuID = this.getActiveOrder(i);
                        this.iSliderMenuStartPosX = this.getActiveMenu().get(this.activeSliderMenuID).getPosX() - nPosX;
                        this.iSliderMenuStartPosY = this.getActiveMenu().get(this.activeSliderMenuID).getPosY() - nPosY;
                        this.iSliderMenuActionDownPosX = nPosX;
                        this.iSliderMenuActionDownPosY = nPosY;
                        CFG.setRender_3(true);
                        this.setOrderOfMenu(this.getActiveOrder(i));
                        return true;
                    }
                }
            }
            catch (IndexOutOfBoundsException ex) {
                CFG.setRender_3(true);
            }
            catch (NullPointerException ex) {
                CFG.setRender_3(true);
                if (!CFG.LOGS) break block5;
                CFG.exceptionStack(ex);
            }
        }
        return false;
    }

    protected final boolean actionMove(int nPosX, int nPosY) {
        try {
            if (this.activeSliderMenuID >= 0 && this.activeMenuElementID >= 0) {
                hoverMobileTime = System.currentTimeMillis();
            }
            if (this.colorPickerMode) {
                this.colorPicker.touch(nPosX, nPosY);
                CFG.setRender_3(true);
                return true;
            }
            if (this.textSliderMode) {
                this.actionMoveTextSliderY(nPosX, nPosY);
                this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(this.activeMenuElementID).setScrollPosY(nPosY);
            } else {
                if (this.sliderMode) {
                    this.actionMoveSlider(nPosX, nPosY);
                    return true;
                }
                if (this.slideMapMode) {
                    CFG.slidePosX = nPosX;
                    CFG.slidePosY = nPosY;
                } else {
                    if (this.sliderMenuResizeMode) {
                        this.actionMoveResize(nPosX, nPosY);
                        return true;
                    }
                    if (this.sliderMenuTitleMode) {
                        this.actionMoveTitle(nPosX, nPosY);
                        return true;
                    }
                    if (this.sliderMenuMode) {
                        this.actionMoveSliderMenu(nPosX, nPosY);
                        this.getActiveMenu().get(this.activeSliderMenuID).setScrollPosY(nPosY);
                        this.getActiveMenu().get(this.activeSliderMenuID).setScrollPosX(nPosX);
                        return true;
                    }
                }
            }
            if (this.flagEditorMode) {
                if (CFG.flagEditorMode == CFG.FlagEditorMode.PENCIL) {
                    for (int i = 0; i < this.getActiveMenu().get(this.activeSliderMenuID).getMenuElementsSize(); ++i) {
                        if (nPosX < this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(i).getPosX() + this.getActiveMenu().get(this.activeSliderMenuID).getPosX() || nPosX > this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(i).getPosX() + this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(i).getWidth() + this.getActiveMenu().get(this.activeSliderMenuID).getPosX() || nPosY < this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(i).getPosY() + this.getActiveMenu().get(this.activeSliderMenuID).getPosY() || nPosY > this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(i).getPosY() + this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(i).getHeight() + this.getActiveMenu().get(this.activeSliderMenuID).getPosY()) continue;
                        this.actionElement(this.activeSliderMenuID, i);
                        return true;
                    }
                }
                return true;
            }
            if (this.graphMode) {
                this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(this.activeMenuElementID).updateSlider(-this.getActiveMenu().get(this.activeSliderMenuID).getPosX() + nPosX);
                return true;
            }
            if (this.graphButtonMode) {
                if (this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(this.activeMenuElementID).getMoveable()) {
                    this.actionMoveTextSliderY(nPosX, nPosY);
                    this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(this.activeMenuElementID).setScrollPosY(nPosY);
                    if (this.updateSliderMenuPosY) {
                        this.iSliderMenuStartPosY = this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(this.activeMenuElementID).getCurrent() - nPosY;
                        this.updateSliderMenuPosY = false;
                    } else {
                        this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(this.activeMenuElementID).setCurrent(this.iSliderMenuStartPosY + nPosY);
                    }
                }
                return true;
            }
            if (this.graphButtonMode2) {
                if (this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(this.activeMenuElementID).getMoveable()) {
                    if (this.updateSliderMenuPosY) {
                        this.iSliderMenuStartPosY = this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(this.activeMenuElementID).getCurrent() - nPosY;
                        this.updateSliderMenuPosY = false;
                    } else {
                        this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(this.activeMenuElementID).setCurrent(this.iSliderMenuStartPosY + nPosY);
                    }
                }
                return true;
            }
            if (this.graphButtonModeX) {
                if (this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(this.activeMenuElementID).getMoveable()) {
                    this.actionMoveTextSliderX(nPosX, nPosY);
                    this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(this.activeMenuElementID).setScrollPosY(nPosX);
                    if (this.updateSliderMenuPosX) {
                        this.iSliderMenuStartPosX = this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(this.activeMenuElementID).getCurrent() - nPosX;
                        this.updateSliderMenuPosX = false;
                    } else {
                        this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(this.activeMenuElementID).setCurrent(this.iSliderMenuStartPosX + nPosX);
                    }
                }
                return true;
            }
            if (this.activeMenuElementID >= 0) {
                return true;
            }
            if (this.sliderMenuCloseMode) {
                return true;
            }
            if (this.dialogMenu.getVisible()) {
                return true;
            }
            if (this.keyboardMode) {
                return true;
            }
        }
        catch (IndexOutOfBoundsException ex) {
            CFG.exceptionStack(ex);
        }
        catch (NullPointerException ex) {
            CFG.exceptionStack(ex);
        }
        return false;
    }

    protected final void actionMoveTextSliderX(int nPosX, int nPosY) {
        if (this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(this.activeMenuElementID).getMoveable()) {
            if (this.updateSliderMenuPosX) {
                this.iSliderMenuStartPosX = this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(this.activeMenuElementID).getCurrent() - nPosX;
                this.updateSliderMenuPosX = false;
            } else {
                this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(this.activeMenuElementID).updateSlider(this.iSliderMenuStartPosX + nPosX);
            }
        }
    }

    protected final void actionMoveTextSliderY(int nPosX, int nPosY) {
        if (this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(this.activeMenuElementID).getMoveable()) {
            if (this.updateSliderMenuPosY) {
                this.iSliderMenuStartPosY = this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(this.activeMenuElementID).getCurrent() - nPosY;
                this.updateSliderMenuPosY = false;
            } else {
                this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(this.activeMenuElementID).updateSlider(this.iSliderMenuStartPosY + nPosY);
            }
        }
    }

    protected final void actionMoveGraphVertical(int nPosX, int nPosY) {
        if (this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(this.activeMenuElementID).getMoveable()) {
            if (this.updateSliderMenuPosY) {
                this.iSliderMenuStartPosY = this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(this.activeMenuElementID).getCurrent() - nPosX;
                this.updateSliderMenuPosY = false;
            } else {
                this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(this.activeMenuElementID).updateSlider(this.iSliderMenuStartPosY + nPosX);
            }
        }
    }

    private final void actionMoveSlider(int nPosX, int nPosY) {
        if (this.getActiveMenuElement().getClickable()) {
            if (nPosX >= this.getActiveMenuElement().getPosX() + this.getActiveMenu().get(this.activeSliderMenuID).getPosX() && nPosX <= this.getActiveMenuElement().getPosX() + this.getActiveMenuElement().getWidth() + this.getActiveMenu().get(this.activeSliderMenuID).getPosX()) {
                this.getActiveMenuElement().updateSlider(nPosX - this.getActiveMenu().get(this.activeSliderMenuID).getPosX());
            } else if (nPosX < this.getActiveMenuElement().getPosX() + this.getActiveMenu().get(this.activeSliderMenuID).getPosX()) {
                this.getActiveMenuElement().updateSlider(this.getActiveMenuElement().getPosX());
            } else {
                this.getActiveMenuElement().updateSlider(this.getActiveMenuElement().getPosX() + this.getActiveMenuElement().getWidth());
            }
            CFG.setRender_3(true);
            this.getActiveMenu().get(this.activeSliderMenuID).actionElement(this.activeMenuElementID);
        }
    }

    private final void actionMoveSlideMap(int nPosX, int nPosY) {
        boolean moveFaster = false;
        if (nPosX > this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(this.activeMenuElementID).getPosX() + this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(this.activeMenuElementID).getWidth()) {
            nPosX = this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(this.activeMenuElementID).getPosX() + this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(this.activeMenuElementID).getWidth();
            moveFaster = true;
        } else if (nPosX < this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(this.activeMenuElementID).getPosX()) {
            nPosX = this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(this.activeMenuElementID).getPosX();
            moveFaster = true;
        }
        nPosX -= this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(this.activeMenuElementID).getPosX();
        nPosX = -this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(this.activeMenuElementID).getWidth() / 2 + nPosX;
        CFG.map.getMapCoordinates().setNewPosX(CFG.map.getMapCoordinates().getPosX() - (int)((float)nPosX * this.slidePercent));
        if (nPosY > this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(this.activeMenuElementID).getPosY() + this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(this.activeMenuElementID).getHeight()) {
            nPosY = this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(this.activeMenuElementID).getPosY() + this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(this.activeMenuElementID).getHeight();
            moveFaster = true;
        } else if (nPosY < this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(this.activeMenuElementID).getPosY()) {
            nPosY = this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(this.activeMenuElementID).getPosY();
            moveFaster = true;
        }
        nPosY -= this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(this.activeMenuElementID).getPosY();
        nPosY = -this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(this.activeMenuElementID).getHeight() / 2 + nPosY;
        CFG.map.getMapCoordinates().setNewPosY(CFG.map.getMapCoordinates().getPosY() - (int)((float)nPosY * this.slidePercent));
        if (moveFaster) {
            this.slidePercent += 0.02f;
            if (this.slidePercent > 0.6f) {
                this.slidePercent = 0.6f;
            }
        }
    }

    private final void actionMoveResize(int nPosX, int nPosY) {
        this.getActiveMenu().get(this.activeSliderMenuID).setHeight(nPosY + this.iSliderMenuStartPosY);
        this.getActiveMenu().get(this.activeSliderMenuID).setMenuPosY(this.getActiveMenu().get(this.activeSliderMenuID).getMenuPosY());
        this.getActiveMenu().get(this.activeSliderMenuID).updateScrollable();
        this.getActiveMenu().get(this.activeSliderMenuID).updateMenuElements_IsInView();
        for (int i = 0; i < this.getActiveMenu().get(this.activeSliderMenuID).getMenuElementsSize(); ++i) {
            if (this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(i).getTypeOfElement() == MenuElement.TypeOfElement.SLIDER) {
                this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(i).setCurrent(this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(i).getCurrent());
                continue;
            }
            if (this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(i).getTypeOfElement() != MenuElement.TypeOfElement.GRAPH) continue;
            this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(i).setCheckboxState(true);
        }
        if (this.sliderMenuResizeLEFT) {
            if (this.getActiveMenu().get(this.activeSliderMenuID).setWidth(this.getActiveMenu().get(this.activeSliderMenuID).getWidth() - (nPosX - this.iSliderMenuActionDownPosX))) {
                this.getActiveMenu().get(this.activeSliderMenuID).setPosX(this.getActiveMenu().get(this.activeSliderMenuID).getPosX() + (nPosX - this.iSliderMenuActionDownPosX));
                this.iSliderMenuActionDownPosX = nPosX;
            }
            if (this.getActiveMenu().get(this.activeSliderMenuID).getPosX() < 0) {
                this.getActiveMenu().get(this.activeSliderMenuID).setWidth(this.getActiveMenu().get(this.activeSliderMenuID).getWidth() + this.getActiveMenu().get(this.activeSliderMenuID).getPosX());
                this.getActiveMenu().get(this.activeSliderMenuID).setPosX(0);
            }
        } else {
            this.getActiveMenu().get(this.activeSliderMenuID).setWidth(nPosX + this.iSliderMenuStartPosX);
            if (this.getActiveMenu().get(this.activeSliderMenuID).getPosX() + this.getActiveMenu().get(this.activeSliderMenuID).getWidth() > CFG.GAME_WIDTH) {
                this.getActiveMenu().get(this.activeSliderMenuID).setWidth(CFG.GAME_WIDTH - this.getActiveMenu().get(this.activeSliderMenuID).getPosX());
            }
        }
        CFG.setRender_3(true);
    }

    private final void actionMoveTitle(int nPosX, int nPosY) {
        if (nPosX + this.iSliderMenuStartPosX + this.getActiveMenu().get(this.activeSliderMenuID).getWidth() - this.getActiveMenu().get(this.activeSliderMenuID).getMinWidth() > 0 && nPosX + this.iSliderMenuStartPosX < CFG.GAME_WIDTH - this.getActiveMenu().get(this.activeSliderMenuID).getMinWidth()) {
            this.getActiveMenu().get(this.activeSliderMenuID).setPosX(nPosX + this.iSliderMenuStartPosX);
            this.getActiveMenu().get(this.activeSliderMenuID).updateMenuPosX(this.getActiveMenu().get(this.activeSliderMenuID).getMenuPosX() + (nPosX - this.iSliderMenuActionDownPosX));
            this.iSliderMenuActionDownPosX = nPosX;
        }
        if (nPosY + this.iSliderMenuStartPosY > this.getActiveMenu().get(this.activeSliderMenuID).getMinHeight() && nPosY + this.iSliderMenuStartPosY < CFG.GAME_HEIGHT - this.getActiveMenu().get(this.activeSliderMenuID).getMinHeight()) {
            this.getActiveMenu().get(this.activeSliderMenuID).setPosY(nPosY + this.iSliderMenuStartPosY);
            this.getActiveMenu().get(this.activeSliderMenuID).updateMenuPosY(this.getActiveMenu().get(this.activeSliderMenuID).getMenuPosY() + (nPosY - this.iSliderMenuActionDownPosY));
            this.iSliderMenuActionDownPosY = nPosY;
        }
        CFG.setRender_3(true);
    }

    private final void actionMoveSliderMenu(int nPosX, int nPosY) {
        block10: {
            try {
                if (this.getActiveMenu().get(this.activeSliderMenuID).getScrollableY()) {
                    if (this.updateSliderMenuPosY) {
                        this.iSliderMenuStartPosY = this.getActiveMenu().get(this.activeSliderMenuID).getMenuPosY() - nPosY;
                        this.updateSliderMenuPosY = false;
                    } else {
                        this.getActiveMenu().get(this.activeSliderMenuID).setMenuPosY(this.iSliderMenuStartPosY + nPosY);
                    }
                }
                if (this.getActiveMenu().get(this.activeSliderMenuID).getScrollableX()) {
                    if (this.updateSliderMenuPosX) {
                        this.iSliderMenuStartPosX = this.getActiveMenu().get(this.activeSliderMenuID).getMenuPosX() - nPosX;
                        this.updateSliderMenuPosX = false;
                    } else {
                        this.getActiveMenu().get(this.activeSliderMenuID).setMenuPosX(this.iSliderMenuStartPosX + nPosX);
                    }
                }
            }
            catch (IndexOutOfBoundsException ex) {
                if (CFG.LOGS) {
                    CFG.exceptionStack(ex);
                }
            }
            catch (NullPointerException ex) {
                if (!CFG.LOGS) break block10;
                CFG.exceptionStack(ex);
            }
        }
    }

    protected final boolean actionUp(int nPosX, int nPosY) {
        try {
            if (this.dialogMenu.getVisible()) {
                if (this.activeMenuElementID >= 0 && this.dialogMenu.getMenuElement(this.activeMenuElementID).getClickable() && nPosX >= this.dialogMenu.getMenuElement(this.activeMenuElementID).getPosX() && nPosX <= this.dialogMenu.getMenuElement(this.activeMenuElementID).getPosX() + this.dialogMenu.getMenuElement(this.activeMenuElementID).getWidth() && nPosY >= this.dialogMenu.getMenuElement(this.activeMenuElementID).getPosY() && nPosY <= this.dialogMenu.getMenuElement(this.activeMenuElementID).getPosY() + this.dialogMenu.getMenuElement(this.activeMenuElementID).getHeight()) {
                    CFG.soundsManager.playSound(SoundsManager.SOUND_CLICK2);
                    this.dialogMenu.actionElement(this.activeMenuElementID);
                }
                return true;
            }
            if (this.keyboardMode) {
                if (this.activeMenuElementID >= 0 && this.keyboard.getMenuElement(this.activeMenuElementID).getClickable() && nPosX >= this.keyboard.getMenuElement(this.activeMenuElementID).getPosX() + this.keyboard.getPosX() && nPosX <= this.keyboard.getMenuElement(this.activeMenuElementID).getPosX() + this.keyboard.getPosX() + this.keyboard.getMenuElement(this.activeMenuElementID).getWidth() && nPosY >= this.keyboard.getMenuElement(this.activeMenuElementID).getPosY() + this.keyboard.getPosY() && nPosY <= this.keyboard.getMenuElement(this.activeMenuElementID).getPosY() + this.keyboard.getPosY() + this.keyboard.getMenuElement(this.activeMenuElementID).getHeight()) {
                    try {
                        CFG.soundsManager.playSound(this.keyboard.getMenuElement(this.activeMenuElementID).getSFX());
                    }
                    catch (IndexOutOfBoundsException ex) {
                        CFG.soundsManager.playSound(SoundsManager.SOUND_CLICK);
                    }
                    this.keyboard.actionElement(this.activeMenuElementID);
                }
                return true;
            }
            if (this.colorPickerMode) {
                this.colorPicker.touch(nPosX, nPosY);
                this.colorPicker.touchUp();
                CFG.soundsManager.playSound(SoundsManager.SOUND_CLICK);
                return true;
            }
            if (this.sliderMenuCloseMode) {
                this.actionUpClose(nPosX, nPosY);
                CFG.soundsManager.playSound(SoundsManager.SOUND_CLICK3);
                return true;
            }
            if (this.sliderMode) {
                this.sliderMode = false;
                if (this.getActiveMenuElement().getClickable()) {
                    this.actionElement(this.activeSliderMenuID, this.activeMenuElementID);
                }
                return true;
            }
            if (this.graphMode) {
                this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(this.activeMenuElementID).updateSlider(-this.getActiveMenu().get(this.activeSliderMenuID).getPosX() + nPosX);
                return true;
            }
            if (this.graphButtonMode) {
                if (this.iSliderMenuActionDownPosY >= nPosY - CFG.PADDING && this.iSliderMenuActionDownPosY <= nPosY + CFG.PADDING && this.iSliderMenuActionDownPosX >= nPosX - CFG.PADDING && this.iSliderMenuActionDownPosX <= nPosX + CFG.PADDING) {
                    this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(this.activeMenuElementID).updateSlider(nPosY);
                    this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(this.activeMenuElementID).setAnotherView(!this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(this.activeMenuElementID).getAnotherView());
                    CFG.soundsManager.playSound(this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(this.activeMenuElementID).getSFX());
                } else {
                    this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(this.activeMenuElementID).scrollTheMenu();
                }
                if (this.getActiveMenuElement().getClickable()) {
                    this.actionElement(this.activeSliderMenuID, this.activeMenuElementID);
                }
                return true;
            }
            if (this.graphButtonMode2) {
                if ((float)nPosY - (float)CFG.PADDING * CFG.GUI_SCALE <= (float)this.iSliderMenuActionDownPosY && (float)nPosY + (float)CFG.PADDING * CFG.GUI_SCALE >= (float)this.iSliderMenuActionDownPosY && (float)nPosX - (float)CFG.PADDING * CFG.GUI_SCALE <= (float)this.iSliderMenuActionDownPosX && (float)nPosX + (float)CFG.PADDING * CFG.GUI_SCALE >= (float)this.iSliderMenuActionDownPosX) {
                    this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(this.activeMenuElementID).updateSlider(-this.getActiveMenu().get(this.activeSliderMenuID).getPosY() + nPosY);
                }
                return true;
            }
            if (this.graphButtonModeX) {
                if ((float)this.iSliderMenuActionDownPosY >= (float)nPosY - (float)CFG.PADDING * CFG.GUI_SCALE && (float)this.iSliderMenuActionDownPosY <= (float)nPosY + (float)CFG.PADDING * CFG.GUI_SCALE && (float)this.iSliderMenuActionDownPosX >= (float)nPosX - (float)CFG.PADDING * CFG.GUI_SCALE && (float)this.iSliderMenuActionDownPosX <= (float)nPosX + (float)CFG.PADDING * CFG.GUI_SCALE) {
                    this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(this.activeMenuElementID).setAnotherView(!this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(this.activeMenuElementID).getAnotherView());
                } else if (this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(this.activeMenuElementID).getMoveable()) {
                    this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(this.activeMenuElementID).scrollTheMenu();
                }
                return true;
            }
            if (this.textSliderMode) {
                this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(this.activeMenuElementID).scrollTheMenu();
            }
            if (this.activeMenuElementID >= 0 && this.activeSliderMenuID >= 0 && (!this.sliderMenuMode || this.iSliderMenuActionDownPosY >= nPosY - CFG.PADDING && this.iSliderMenuActionDownPosY <= nPosY + CFG.PADDING && this.iSliderMenuActionDownPosX >= nPosX - CFG.PADDING && this.iSliderMenuActionDownPosX <= nPosX + CFG.PADDING) && this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(this.activeMenuElementID).getClickable() && nPosX >= this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(this.activeMenuElementID).getPosX() + this.getActiveMenu().get(this.activeSliderMenuID).getMenuPosX() && nPosX <= this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(this.activeMenuElementID).getPosX() + this.getActiveMenu().get(this.activeSliderMenuID).getMenuPosX() + this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(this.activeMenuElementID).getWidth() && nPosY >= this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(this.activeMenuElementID).getPosY() + this.getActiveMenu().get(this.activeSliderMenuID).getMenuPosY() && nPosY <= this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(this.activeMenuElementID).getPosY() + this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(this.activeMenuElementID).getHeight() + this.getActiveMenu().get(this.activeSliderMenuID).getMenuPosY()) {
                try {
                    CFG.soundsManager.playSound(this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(this.activeMenuElementID).getSFX());
                }
                catch (IndexOutOfBoundsException ex) {
                    CFG.soundsManager.playSound(SoundsManager.SOUND_CLICK);
                }
                this.actionElement(this.activeSliderMenuID, this.activeMenuElementID);
                return true;
            }
            if (this.sliderMenuMode) {
                this.getActiveMenu().get(this.activeSliderMenuID).scrollTheMenu();
            }
        }
        catch (IndexOutOfBoundsException indexOutOfBoundsException) {
        }
        catch (NullPointerException nullPointerException) {
            // empty catch block
        }
        return false;
    }

    private final void actionUpClose(int nPosX, int nPosY) {
        if (this.getActiveMenu().get(this.getActiveOrder(this.activeSliderMenuID)).getVisible() && this.getActiveMenu().get(this.getActiveOrder(this.activeSliderMenuID)).getCloseable() && nPosX >= this.getActiveMenu().get(this.getActiveOrder(this.activeSliderMenuID)).getPosX() + this.getActiveMenu().get(this.getActiveOrder(this.activeSliderMenuID)).getWidth() - ImageManager.getImage(Images.btn_close).getWidth() && nPosX <= this.getActiveMenu().get(this.getActiveOrder(this.activeSliderMenuID)).getPosX() + this.getActiveMenu().get(this.getActiveOrder(this.activeSliderMenuID)).getWidth()) {
            if (this.getActiveMenu().get(this.getActiveOrder(this.activeSliderMenuID)).getTitle() == null) {
                if (nPosY >= this.getActiveMenu().get(this.getActiveOrder(this.activeSliderMenuID)).getPosY() && nPosY <= this.getActiveMenu().get(this.getActiveOrder(this.activeSliderMenuID)).getPosY() + ImageManager.getImage(Images.btn_close).getHeight()) {
                    this.getActiveMenu().get(this.getActiveOrder(this.activeSliderMenuID)).actionClose();
                }
            } else if (nPosY >= this.getActiveMenu().get(this.getActiveOrder(this.activeSliderMenuID)).getPosY() - this.getActiveMenu().get(this.getActiveOrder(this.activeSliderMenuID)).getTitle().getHeight() && nPosY <= this.getActiveMenu().get(this.getActiveOrder(this.activeSliderMenuID)).getPosY() + ImageManager.getImage(Images.btn_close).getHeight() - this.getActiveMenu().get(this.getActiveOrder(this.activeSliderMenuID)).getTitle().getHeight()) {
                this.getActiveMenu().get(this.getActiveOrder(this.activeSliderMenuID)).actionClose();
            }
        }
    }

    protected final List<SliderMenu> getActiveMenu() {
        try {
            return this.menus.get(this.viewID);
        }
        catch (IndexOutOfBoundsException ex) {
            return this.menus.get(0);
        }
        catch (NullPointerException ex) {
            return this.menus.get(0);
        }
    }

    protected final MenuElement getActiveMenuElement() {
        try {
            return this.getActiveMenu().get(this.activeSliderMenuID).getMenuElement(this.activeMenuElementID);
        }
        catch (IndexOutOfBoundsException ex) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex);
            }
            return this.menus.get(0).get(0).getMenuElement(0);
        }
        catch (NullPointerException ex) {
            return new Button();
        }
    }

    protected final MenuElement getMenuElement(int nSliderMenuID, int nMenuElementID) {
        return this.getActiveMenu().get(nSliderMenuID).getMenuElement(nMenuElementID);
    }

    protected final int getActiveOrder(int i) {
        try {
            return this.orderOfMenu.get(this.viewID).get(i);
        }
        catch (IndexOutOfBoundsException | NullPointerException ex) {
            if (CFG.LOGS) {
                CFG.exceptionStack(ex);
            }
            return 0;
        }
    }

    protected final void setViewID(Menu eMenu) {
        /*for (StackTraceElement i: Thread.currentThread().getStackTrace()) {
            Gdx.app.log("MENU REQUEST:", eMenu.name());
            Gdx.app.log("CLASS:", i.getClassName());
            Gdx.app.log("METHOD:", i.getMethodName());
            Gdx.app.log("LINE:", String.valueOf(i.getLineNumber()));
            Gdx.app.log("MENU REQUEST", "ABOVE");
        }*/

        this.resetHoverActive();
        this.keyboard.setVisible(false);
        this.fromViewID = this.viewID;
        this.toViewID = this.viewID = this.getViewID(eMenu);
        CFG.setRender_3(true);
        this.updateViewID();
    }

    protected final void fixOrderOfMenuBug() {
    }

    protected final void setViewIDWithoutAnimation(Menu eMenu) {
        /*for (StackTraceElement i: Thread.currentThread().getStackTrace()) {
            Gdx.app.log("MENU REQUEST:", eMenu.name());
            Gdx.app.log("CLASS:", i.getClassName());
            Gdx.app.log("METHOD:", i.getMethodName());
            Gdx.app.log("LINE:", String.valueOf(i.getLineNumber()));
            Gdx.app.log("MENU REQUEST", "ABOVE");
        }*/

        this.resetHoverActive();
        this.keyboard.setVisible(false);
        this.viewID = this.getViewID(eMenu);
        this.resetChangeViewMode();
        CFG.setRender_3(true);
        this.updateViewID();
    }

    private final void updateViewID() {
        block44: {
            block43: {
                block42: {
                    block41: {
                        block40: {
                            block39: {
                                block38: {
                                    block37: {
                                        block36: {
                                            try {
                                                Game_Render.updateRenderer();
                                            }
                                            catch (IndexOutOfBoundsException ex) {
                                                if (CFG.LOGS) {
                                                    CFG.exceptionStack(ex);
                                                }
                                            }
                                            catch (NullPointerException ex) {
                                                if (!CFG.LOGS) break block36;
                                                CFG.exceptionStack(ex);
                                            }
                                        }
                                        try {
                                            this.updateBuildProvinceHoverInformation();
                                        }
                                        catch (IndexOutOfBoundsException ex) {
                                            if (CFG.LOGS) {
                                                CFG.exceptionStack(ex);
                                            }
                                        }
                                        catch (NullPointerException ex) {
                                            if (!CFG.LOGS) break block37;
                                            CFG.exceptionStack(ex);
                                        }
                                    }
                                    try {
                                        CFG.map.getMapTouchManager().updateEnableScaling();
                                    }
                                    catch (IndexOutOfBoundsException ex) {
                                        if (CFG.LOGS) {
                                            CFG.exceptionStack(ex);
                                        }
                                    }
                                    catch (NullPointerException ex) {
                                        if (!CFG.LOGS) break block38;
                                        CFG.exceptionStack(ex);
                                    }
                                }
                                try {
                                    CFG.map.getMapCoordinates().updateMinMaxPosY();
                                }
                                catch (IndexOutOfBoundsException ex) {
                                    if (CFG.LOGS) {
                                        CFG.exceptionStack(ex);
                                    }
                                }
                                catch (NullPointerException ex) {
                                    if (!CFG.LOGS) break block39;
                                    CFG.exceptionStack(ex);
                                }
                            }
                            try {
                                CFG.map.getMapScroll().updateEnableBackroundAnimation();
                            }
                            catch (IndexOutOfBoundsException ex) {
                                if (CFG.LOGS) {
                                    CFG.exceptionStack(ex);
                                }
                            }
                            catch (NullPointerException ex) {
                                if (!CFG.LOGS) break block40;
                                CFG.exceptionStack(ex);
                            }
                        }
                        try {
                            CFG.map.getMapTouchManager().update_ExtraAction();
                        }
                        catch (IndexOutOfBoundsException ex) {
                            if (CFG.LOGS) {
                                CFG.exceptionStack(ex);
                            }
                        }
                        catch (NullPointerException ex) {
                            if (!CFG.LOGS) break block41;
                            CFG.exceptionStack(ex);
                        }
                    }
                    try {
                        CFG.game.updateSetActiveProvinceID_ExtraAction();
                    }
                    catch (IndexOutOfBoundsException ex) {
                        if (CFG.LOGS) {
                            CFG.exceptionStack(ex);
                        }
                    }
                    catch (NullPointerException ex) {
                        if (!CFG.LOGS) break block42;
                        CFG.exceptionStack(ex);
                    }
                }
                try {
                    this.updateSlideMap_ActionMove();
                }
                catch (IndexOutOfBoundsException ex) {
                    if (CFG.LOGS) {
                        CFG.exceptionStack(ex);
                    }
                }
                catch (NullPointerException ex) {
                    if (!CFG.LOGS) break block43;
                    CFG.exceptionStack(ex);
                }
            }
            try {
                CFG.game.updateActiveProvinceBorderStyle();
            }
            catch (IndexOutOfBoundsException ex) {
                if (CFG.LOGS) {
                    CFG.exceptionStack(ex);
                }
            }
            catch (NullPointerException ex) {
                if (!CFG.LOGS) break block44;
                CFG.exceptionStack(ex);
            }
        }
    }

    private final void resetChangeViewMode() {
        this.toViewID = -1;
        this.fromViewID = -1;
        this.animationChangeViewPosX = 0.0f;
        this.backAnimation = false;
        this.animationStepID = 0;
        CFG.setRender_3(true);
    }

    protected final void startSliderMenuResizeMode(int i, int nPosX, int nPosY, boolean sliderMenuResizeLEFT) {
        this.sliderMenuResizeMode = true;
        this.activeSliderMenuID = i;
        this.iSliderMenuStartPosX = this.getActiveMenu().get(this.activeSliderMenuID).getWidth() - nPosX;
        this.iSliderMenuStartPosY = this.getActiveMenu().get(this.activeSliderMenuID).getHeight() - nPosY;
        this.sliderMenuResizeLEFT = sliderMenuResizeLEFT;
        CFG.setRender_3(true);
    }

    protected final void actionElement(int sliderMenuID, int nMenuElementID) {
        this.getActiveMenu().get(sliderMenuID).actionElement(nMenuElementID);
    }

    protected final void onBackPressed() {
        if (this.dialogMenu.getVisible()) {
            this.dialogMenu.onBackPressed();
        } else if (this.keyboard.getVisible()) {
            this.keyboard.onBackPressed();
        } else {
            this.getActiveMenu().get(0).onBackPressed();
        }
        Touch.resetAllModes();
    }

    protected final void onMenuPressed() {
        this.getActiveMenu().get(0).onMenuPressed();
    }

    protected final void updateLanguage() {
        for (int i = 0; i < this.menus.size(); ++i) {
            for (int j = 0; j < this.menus.get(i).size(); ++j) {
                this.menus.get(i).get(j).updateLanguage();
            }
        }
        CFG.ideologiesManager.loadIdeologies();
        this.getDialogMenu().updateLanguage();
        CFG.gameAges.updateLanguage();
        CFG.historyManager.updateLanguage();
        CFG.map.initMapContinents();
    }

    protected final int getActiveMenuElementID() {
        return this.activeMenuElementID;
    }

    protected final void setActiveSliderMenuID(int activeSliderMenuID) {
        this.activeSliderMenuID = activeSliderMenuID;
    }

    protected final void setActiveMenuElementID(int activeMenuElementID) {
        this.activeMenuElementID = activeMenuElementID;
    }

    protected final void setUpdateSliderMenuPosY(boolean updateSliderMenuPosY) {
        this.updateSliderMenuPosY = updateSliderMenuPosY;
    }

    protected final void setUpdateSliderMenuPosX(boolean updateSliderMenuPosX) {
        this.updateSliderMenuPosX = updateSliderMenuPosX;
    }

    protected final boolean getSlideMapMode() {
        return this.slideMapMode;
    }

    protected final boolean getSliderMenuMode() {
        return this.sliderMenuMode;
    }

    protected final boolean getSliderMode() {
        return this.sliderMode;
    }

    protected final void setGraphMode(boolean graphMode) {
        this.graphMode = graphMode;
    }

    protected final boolean getGraphButtonMode() {
        return this.graphButtonMode;
    }

    protected final void setGraphButtonMode(boolean graphButtonMode) {
        this.graphButtonMode = graphButtonMode;
    }

    protected final boolean getGraphButtonMode2() {
        return this.graphButtonMode2;
    }

    protected final void setGraphButtonMode2(boolean graphButtonMode2) {
        this.graphButtonMode2 = graphButtonMode2;
    }

    protected final void setGraphButtonModeX(boolean graphButtonModeX) {
        this.graphButtonModeX = graphButtonModeX;
    }

    protected final boolean getTextSliderMode() {
        return this.textSliderMode;
    }

    protected final void setSliderMenuMode(boolean sliderMenuMode) {
        this.sliderMenuMode = sliderMenuMode;
    }

    protected final void setBackAnimation(boolean backAnimation) {
        this.backAnimation = backAnimation;
    }

    protected final boolean getSliderMenuTitleMode() {
        return this.sliderMenuTitleMode;
    }

    protected final void setSliderMenuTitleMode(boolean sliderMenuTitleMode) {
        this.sliderMenuTitleMode = sliderMenuTitleMode;
    }

    protected final boolean getSliderMenuResizeMode() {
        return this.sliderMenuResizeMode;
    }

    protected final void setSliderMenuResizeMode(boolean sliderMenuResizeMode) {
        this.sliderMenuResizeMode = sliderMenuResizeMode;
    }

    protected final boolean getSliderMenuResizeLEFT() {
        return this.sliderMenuResizeLEFT;
    }

    protected final void setSliderMode(boolean sliderMode) {
        this.sliderMode = sliderMode;
    }

    protected final void setSlideMapMode(boolean slideMapMode) {
        this.slideMapMode = slideMapMode;
    }

    protected final SliderMenu getDialogMenu() {
        return this.dialogMenu;
    }

    protected final boolean getSliderMenuCloseMode() {
        return this.sliderMenuCloseMode;
    }

    protected final void setSliderMenuCloseMode(boolean sliderMenuCloseMode) {
        this.sliderMenuCloseMode = sliderMenuCloseMode;
    }

    protected final void setKeyboardMode(boolean keyboardMode) {
        this.keyboardMode = keyboardMode;
    }

    protected final void setColorPickerMode(boolean colorPickerMode) {
        this.colorPickerMode = colorPickerMode;
    }

    protected final SliderMenu getInGame() {
        return this.menus.get(this.INGAME).get(0);
    }

    protected final SliderMenu getInGame_ProvinceInfo() {
        return this.menus.get(this.INGAME).get(this.INGAME_PROVINCE_INFO);
    }

    protected final void updateInGame_ProvinceInfoGraph(int nProvinceID) {
        block9: {
            try {
                if (nProvinceID >= 0 && !CFG.game.getProvince(nProvinceID).getSeaProvince()) {
                    if (CFG.game.getProvince(nProvinceID).getWasteland() < 0) {
                        int i;
                        ArrayList<Integer> nData = new ArrayList<Integer>();
                        ArrayList<Integer> nCivs = new ArrayList<Integer>();
                        if (CFG.FOG_OF_WAR == 2) {
                            for (i = 0; i < CFG.game.getProvince(nProvinceID).getPopulationData().getNationalitiesSize(); ++i) {
                                nData.add(CFG.game.getProvince(nProvinceID).getPopulationData().getPopulationID(i));
                                nCivs.add(CFG.getMetCiv(CFG.game.getProvince(nProvinceID).getPopulationData().getCivID(i)) ? CFG.game.getProvince(nProvinceID).getPopulationData().getCivID(i) : -(i + 1));
                            }
                        } else {
                            for (i = 0; i < CFG.game.getProvince(nProvinceID).getPopulationData().getNationalitiesSize(); ++i) {
                                nData.add(CFG.game.getProvince(nProvinceID).getPopulationData().getPopulationID(i));
                                nCivs.add(CFG.game.getProvince(nProvinceID).getPopulationData().getCivID(i));
                            }
                        }
                        boolean showDetails = this.menus.get(this.INGAME).get(this.INGAME_PROVINCE_INFO).getMenuElement(5).getAnotherView();
                        this.menus.get(this.INGAME).get(this.INGAME_PROVINCE_INFO).setMenuElement(5, new Graph_Circle(CFG.GAME_WIDTH - CFG.map.getMapBG().getMinimapWidth() - this.menus.get(this.INGAME).get(this.INGAME_PROVINCE_INFO).getMenuElement(0).getWidth() - CFG.PADDING * 2 - CFG.terrainTypesManager.getIcon(0).getWidth() - CFG.PADDING * 2 - CFG.PADDING, CFG.PADDING, nData, nCivs, null){

                            @Override
                            protected int getPosX() {
                                return super.getPosX() - this.getWidth();
                            }

                            @Override
                            protected void buildElementHover() {
                                try {
                                    ArrayList<MenuElement_Hover_v2_Element2> nElements = new ArrayList<MenuElement_Hover_v2_Element2>();
                                    ArrayList<MenuElement_Hover_v2_Element_Type> nData = new ArrayList<MenuElement_Hover_v2_Element_Type>();
                                    if (CFG.ACTIVE_PROVINCE_INFO >= 0) {
                                        int i;
                                        if (CFG.game.getProvince(CFG.ACTIVE_PROVINCE_INFO).getName().length() > 0) {
                                            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getProvince(CFG.ACTIVE_PROVINCE_INFO).getName(), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                                            nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getProvince(CFG.ACTIVE_PROVINCE_INFO).getCivID(), CFG.PADDING, 0));
                                            nElements.add(new MenuElement_Hover_v2_Element2(nData));
                                            nData.clear();
                                        }
                                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Population") + ": "));
                                        nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.getNumberWithSpaces("" + CFG.game.getProvince(CFG.ACTIVE_PROVINCE_INFO).getPopulationData().getPopulation()), CFG.COLOR_TEXT_POPULATION));
                                        if (CFG.game.showTurnChangesInformation(CFG.game.getProvince(CFG.ACTIVE_PROVINCE_INFO).getCivID())) {
                                            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.population, CFG.PADDING, CFG.PADDING));
                                            if (CFG.game.getProvince((int)CFG.ACTIVE_PROVINCE_INFO).saveProvinceData.turnChange_Population > 0) {
                                                nData.add(new MenuElement_Hover_v2_Element_Type_Text("+" + CFG.getNumberWithSpaces("" + CFG.game.getProvince((int)CFG.ACTIVE_PROVINCE_INFO).saveProvinceData.turnChange_Population), CFG.COLOR_TEXT_MODIFIER_POSITIVE));
                                            } else if (CFG.game.getProvince((int)CFG.ACTIVE_PROVINCE_INFO).saveProvinceData.turnChange_Population < 0) {
                                                nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.getNumberWithSpaces("" + CFG.game.getProvince((int)CFG.ACTIVE_PROVINCE_INFO).saveProvinceData.turnChange_Population), CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                                            } else {
                                                nData.add(new MenuElement_Hover_v2_Element_Type_Text("+" + CFG.game.getProvince((int)CFG.ACTIVE_PROVINCE_INFO).saveProvinceData.turnChange_Population, CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
                                            }
                                            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.time, CFG.PADDING, 0));
                                        } else {
                                            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.population, CFG.PADDING, 0));
                                        }
                                        nElements.add(new MenuElement_Hover_v2_Element2(nData));
                                        nData.clear();
                                        ArrayList<Integer> tSortedCivs = new ArrayList<Integer>();
                                        ArrayList<Integer> tSortedPop = new ArrayList<Integer>();
                                        for (i = 0; i < CFG.game.getProvince(CFG.ACTIVE_PROVINCE_INFO).getPopulationData().getNationalitiesSize(); ++i) {
                                            tSortedCivs.add(CFG.game.getProvince(CFG.ACTIVE_PROVINCE_INFO).getPopulationData().getCivID(i));
                                            tSortedPop.add(CFG.game.getProvince(CFG.ACTIVE_PROVINCE_INFO).getPopulationData().getPopulationID(i));
                                        }
                                        for (i = 0; i < tSortedCivs.size() - 1; ++i) {
                                            for (int j = i + 1; j < tSortedCivs.size(); ++j) {
                                                if ((Integer)tSortedPop.get(i) >= (Integer)tSortedPop.get(j)) continue;
                                                int tempD = (Integer)tSortedCivs.get(i);
                                                tSortedCivs.set(i, (Integer)tSortedCivs.get(j));
                                                tSortedCivs.set(j, tempD);
                                                tempD = (Integer)tSortedPop.get(i);
                                                tSortedPop.set(i, (Integer)tSortedPop.get(j));
                                                tSortedPop.set(j, tempD);
                                            }
                                        }
                                        if (CFG.FOG_OF_WAR == 2) {
                                            for (i = 0; i < tSortedCivs.size(); ++i) {
                                                nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.getMetCiv((Integer)tSortedCivs.get(i)) ? (Integer)tSortedCivs.get(i) : -(i + 1)));
                                                nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + CFG.getNumberWithSpaces("" + tSortedPop.get(i)), CFG.COLOR_TEXT_POPULATION));
                                                nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.population, CFG.PADDING, CFG.PADDING));
                                                nData.add(new MenuElement_Hover_v2_Element_Type_Text("[" + CFG.getPercentage((Integer)tSortedPop.get(i), CFG.game.getProvince(CFG.ACTIVE_PROVINCE_INFO).getPopulationData().getPopulation(), 5) + "%]", CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
                                                nData.add(new MenuElement_Hover_v2_Element_Type_Text(" " + (CFG.getMetCiv((Integer)tSortedCivs.get(i)) ? CFG.game.getCiv((Integer)tSortedCivs.get(i)).getCivName() : CFG.langManager.get("Undiscovered")), CFG.COLOR_TEXT_RANK_HOVER));
                                                nElements.add(new MenuElement_Hover_v2_Element2(nData));
                                                nData.clear();
                                            }
                                        } else {
                                            for (i = 0; i < tSortedCivs.size(); ++i) {
                                                nData.add(new MenuElement_Hover_v2_Element_Type_Flag((Integer)tSortedCivs.get(i)));
                                                nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + CFG.getNumberWithSpaces("" + tSortedPop.get(i)), CFG.COLOR_TEXT_POPULATION));
                                                nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.population, CFG.PADDING, CFG.PADDING));
                                                nData.add(new MenuElement_Hover_v2_Element_Type_Text("[" + CFG.getPercentage((Integer)tSortedPop.get(i), CFG.game.getProvince(CFG.ACTIVE_PROVINCE_INFO).getPopulationData().getPopulation(), 5) + "%]", CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
                                                nData.add(new MenuElement_Hover_v2_Element_Type_Text(" " + CFG.game.getCiv((Integer)tSortedCivs.get(i)).getCivName(), CFG.COLOR_TEXT_RANK_HOVER));
                                                nElements.add(new MenuElement_Hover_v2_Element2(nData));
                                                nData.clear();
                                            }
                                        }
                                    } else {
                                        this.menuElementHover = null;
                                        return;
                                    }
                                    this.menuElementHover = new MenuElement_Hover_v2(nElements);
                                }
                                catch (IndexOutOfBoundsException ex) {
                                    this.menuElementHover = null;
                                }
                            }

                            @Override
                            protected void drawMenuElementHover2(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
                                if (this.menuElementHover != null) {
                                    this.menuElementHover.drawAlwaysOver(oSB, Touch.getMousePosX(), CFG.GAME_HEIGHT - CFG.map.getMapBG().getMinimapHeight());
                                }
                            }
                        });
                        this.menus.get(this.INGAME).get(this.INGAME_PROVINCE_INFO).getMenuElement(5).setVisible(false);
                        this.menus.get(this.INGAME).get(this.INGAME_PROVINCE_INFO).getMenuElement(5).setAnotherView(showDetails);
                        this.menus.get(this.INGAME).get(this.INGAME_PROVINCE_INFO).getMenuElement(5).setIsInView(true);
                        this.menus.get(this.INGAME).get(this.INGAME_PROVINCE_INFO).getMenuElement(5).setVisible(true);
                        break block9;
                    }
                    this.menus.get(this.INGAME).get(this.INGAME_PROVINCE_INFO).getMenuElement(5).setVisible(false);
                    break block9;
                }
                this.menus.get(this.INGAME).get(this.INGAME_PROVINCE_INFO).getMenuElement(5).setVisible(false);
            }
            catch (IndexOutOfBoundsException ex) {
                CFG.exceptionStack(ex);
            }
            catch (NullPointerException ex) {
                CFG.exceptionStack(ex);
            }
        }
    }

    protected final SliderMenu getKeyboard() {
        return this.keyboard;
    }

    protected final int getKeyboardActiveSliderMenuID() {
        return this.keyboardActiveSliderMenuID;
    }

    protected final void setKeyboardActiveSliderMenuID(int keyboardActiveSliderMenuID) {
        this.keyboardActiveSliderMenuID = keyboardActiveSliderMenuID;
    }

    protected final int getKeyboardActiveMenuElementID() {
        return this.keyboardActiveMenuElementID;
    }

    protected final void setKeyboardActiveMenuElementID(int keyboardActiveMenuElementID) {
        this.keyboardActiveMenuElementID = keyboardActiveMenuElementID;
    }

    protected final int getActiveSliderMenuID() {
        return this.activeSliderMenuID;
    }

    protected final void setFlagEditorMode(boolean flagEditorMode) {
        this.flagEditorMode = flagEditorMode;
    }

    protected final void setTextSliderMode(boolean textSliderMode) {
        this.textSliderMode = textSliderMode;
    }

    protected final int getViewID() {
        return this.viewID;
    }

    protected final void rebuildCreateScenario_Civilizations_Suggest() {
        if (CFG.game.getActiveProvinceID() >= 0 && CFG.game.getProvince(CFG.game.getActiveProvinceID()).getWasteland() < 0) {
            this.menus.get(this.CREATE_SCENARIO_CIVILIZATIONS).get(this.CREATE_SCENARIO_CIVILIZATIONS_SUGGEST).setVisible(false);
            this.menus.get(this.CREATE_SCENARIO_CIVILIZATIONS).set(this.CREATE_SCENARIO_CIVILIZATIONS_SUGGEST, new Menu_CreateScenario_Civilizations_Suggest());
        }
    }

    protected final void rebuildCreateScenario_Civilizations_Ideologies() {
        if (CFG.game.getActiveProvinceID() >= 0 && CFG.game.getProvince(CFG.game.getActiveProvinceID()).getWasteland() < 0) {
            this.menus.get(this.CREATE_SCENARIO_CIVILIZATIONS).get(this.CREATE_SCENARIO_CIVILIZATIONS_IDEOLOGIES).setVisible(false);
            this.menus.get(this.CREATE_SCENARIO_CIVILIZATIONS).set(this.CREATE_SCENARIO_CIVILIZATIONS_IDEOLOGIES, new Menu_CreateScenario_Civilizations_Ideologies());
        }
    }

    protected final void rebuildCivilizations_Info_Players() {
        block6: {
            try {
                if (this.getInCreateNewGame() && this.CREATE_NEW_GAME_CIV_INFO_PLAYERS >= 0) {
                    int nPosY = this.menus.get(this.CREATE_NEW_GAME).get(this.CREATE_NEW_GAME_CIV_INFO_PLAYERS).getPosY();
                    boolean nVisible = this.menus.get(this.CREATE_NEW_GAME).get(this.CREATE_NEW_GAME_CIV_INFO_PLAYERS).getVisible();
                    this.menus.get(this.CREATE_NEW_GAME).set(this.CREATE_NEW_GAME_CIV_INFO_PLAYERS, new Menu_Civilizations_Info_Players());
                    this.menus.get(this.CREATE_NEW_GAME).get(this.CREATE_NEW_GAME_CIV_INFO_PLAYERS).setPosY(nPosY);
                    this.menus.get(this.CREATE_NEW_GAME).get(this.CREATE_NEW_GAME_CIV_INFO_PLAYERS).setVisible(nVisible);
                    if (this.menus.get(this.CREATE_NEW_GAME).get(this.CREATE_NEW_GAME_CIV_INFO_PLAYERS).getPosY() + this.menus.get(this.CREATE_NEW_GAME).get(this.CREATE_NEW_GAME_CIV_INFO_PLAYERS).getHeight() > CFG.GAME_HEIGHT) {
                        this.menus.get(this.CREATE_NEW_GAME).get(this.CREATE_NEW_GAME_CIV_INFO_PLAYERS).setHeight(Math.max(CFG.GAME_HEIGHT - this.menus.get(this.CREATE_NEW_GAME).get(this.CREATE_NEW_GAME_CIV_INFO_PLAYERS).getPosY(), CFG.BUTTON_HEIGHT / 2));
                        this.menus.get(this.CREATE_NEW_GAME).get(this.CREATE_NEW_GAME_CIV_INFO_PLAYERS).updateScrollable();
                        this.menus.get(this.CREATE_NEW_GAME).get(this.CREATE_NEW_GAME_CIV_INFO_PLAYERS).updateMenuElements_IsInView();
                    }
                }
            }
            catch (NullPointerException ex) {
                if (CFG.LOGS) {
                    CFG.exceptionStack(ex);
                }
            }
            catch (IndexOutOfBoundsException ex) {
                if (!CFG.LOGS) break block6;
                CFG.exceptionStack(ex);
            }
        }
    }

    protected final void hideCivilizations_Info_Players() {
        try {
            if (this.CREATE_NEW_GAME_CIV_INFO_PLAYERS >= 0) {
                this.menus.get(this.CREATE_NEW_GAME).get(this.CREATE_NEW_GAME_CIV_INFO_PLAYERS).setVisible(false);
                this.menus.get(this.CREATE_NEW_GAME).get(this.CREATE_NEW_GAME_CIV_INFO_STATS).setVisible(false);
                this.menus.get(this.CREATE_NEW_GAME).get(this.CREATE_NEW_GAME_CIV_INFO_DIPLOMACY).setVisible(false);
                this.menus.get(this.CREATE_NEW_GAME).get(this.CREATE_NEW_GAME_CIV_INFO_SHOW_MENU).setVisible(true);
            }
        }
        catch (NullPointerException nullPointerException) {
        }
        catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            // empty catch block
        }
    }

    protected final void hidePeaceTreatyProvinces() {
        if (this.INGAME_PEACE_TREATY >= 0) {
            this.menus.get(this.INGAME_PEACE_TREATY).get(this.INGAME_PEACE_TREATY_PROVINCES).setVisible(false);
            this.menus.get(this.INGAME_PEACE_TREATY).get(this.INGAME_PEACE_TREATY_PROVINCES_SHOW).setVisible(true);
        }
    }

    protected final void hidePeaceTreaty_ResponseProvinces() {
        if (this.INGAME_PEACE_TREATY_RESPONSE >= 0) {
            this.menus.get(this.INGAME_PEACE_TREATY_RESPONSE).get(this.INGAME_PEACE_TREATY_RESPONSE_PROVINCES).setVisible(false);
            this.menus.get(this.INGAME_PEACE_TREATY_RESPONSE).get(this.INGAME_PEACE_TREATY_RESPONSE_PROVINCES_SHOW).setVisible(true);
        }
    }

    protected final void updateCreateNewGame_Top() {
        try {
            if (this.CREATE_NEW_GAME_TOP >= 0) {
                this.menus.get(this.CREATE_NEW_GAME).get(this.CREATE_NEW_GAME_TOP).updateLanguage();
                this.menus.get(this.CREATE_NEW_GAME).set(this.CREATE_NEW_GAME_TOP_VIEWS, new Menu_CreateNewGame_Top_Views());
            }
        }
        catch (IndexOutOfBoundsException indexOutOfBoundsException) {
        }
        catch (NullPointerException nullPointerException) {
            // empty catch block
        }
    }

    protected final void rebuildManageDiplomacy_Alliances() {
        if (this.MANAGE_DIPLOMACY >= 0) {
            this.menus.get(this.MANAGE_DIPLOMACY).set(this.MANAGE_DIPLOMACY_ALLIANCES, new Menu_ManageDiplomacy_Alliances());
            this.menus.get(this.MANAGE_DIPLOMACY).get(1).getMenuElement(0).setCheckboxState(true);
            for (int i = 1; i < this.menus.get(this.MANAGE_DIPLOMACY).get(1).getMenuElementsSize(); ++i) {
                this.menus.get(this.MANAGE_DIPLOMACY).get(1).getMenuElement(i).setCheckboxState(false);
            }
            this.menus.get(this.MANAGE_DIPLOMACY).get(this.MANAGE_DIPLOMACY_RELATIONS_INTERACTIVE).setVisible(false);
            this.menus.get(this.MANAGE_DIPLOMACY).get(this.MANAGE_DIPLOMACY_PACTS2).setVisible(false);
            this.menus.get(this.MANAGE_DIPLOMACY).get(this.MANAGE_DIPLOMACY_PACTS_LIST).setVisible(false);
            this.menus.get(this.MANAGE_DIPLOMACY).get(this.MANAGE_DIPLOMACY_TRUCES).setVisible(false);
            this.menus.get(this.MANAGE_DIPLOMACY).get(this.MANAGE_DIPLOMACY_TRUCES_LIST).setVisible(false);
            this.menus.get(this.MANAGE_DIPLOMACY).get(this.MANAGE_DIPLOMACY_GUARANTEE).setVisible(false);
            this.menus.get(this.MANAGE_DIPLOMACY).get(this.MANAGE_DIPLOMACY_GUARANTEE_LIST).setVisible(false);
            this.menus.get(this.MANAGE_DIPLOMACY).get(this.MANAGE_DIPLOMACY_DEFENSIVE).setVisible(false);
            this.menus.get(this.MANAGE_DIPLOMACY).get(this.MANAGE_DIPLOMACY_DEFENSIVE_LIST).setVisible(false);
            this.menus.get(this.MANAGE_DIPLOMACY).get(this.MANAGE_DIPLOMACY_MILITARY_ACCESS).setVisible(false);
            this.menus.get(this.MANAGE_DIPLOMACY).get(this.MANAGE_DIPLOMACY_MILITARY_ACCESS_LIST).setVisible(false);
            this.menus.get(this.MANAGE_DIPLOMACY).get(this.MANAGE_DIPLOMACY_VASSALS).setVisible(false);
            this.menus.get(this.MANAGE_DIPLOMACY).get(this.MANAGE_DIPLOMACY_VASSALS_LIST).setVisible(false);
            this.menus.get(this.MANAGE_DIPLOMACY).get(this.MANAGE_DIPLOMACY_VASSALS_AUTONOMY).setVisible(false);
        }
    }

    protected final void getCreateCity_UpdateSaveButton() {
        this.menus.get(this.CREATE_CITY).get(0).getMenuElement(2);
        if (CFG.game.getActiveProvinceID() < 0 || CFG.game.getActiveProvinceID() >= 0 && CFG.game.getProvince(CFG.game.getActiveProvinceID()).getSeaProvince() || CFG.editorCity.getPosX() < 0 || CFG.editorCity.getPosY() < 0 || CFG.editorCity.getCityName().length() <= 0) {
            this.menus.get(this.CREATE_CITY).get(0).getMenuElement(2).setClickable(false);
        } else {
            this.menus.get(this.CREATE_CITY).get(0).getMenuElement(2).setClickable(true);
        }
        if (CFG.editorCity.getPosX() >= 0 && CFG.editorCity.getPosY() >= 0) {
            this.menus.get(this.CREATE_CITY).get(0).getMenuElement(6).setClickable(true);
            this.menus.get(this.CREATE_CITY).get(0).getMenuElement(7).setClickable(true);
            this.menus.get(this.CREATE_CITY).get(0).getMenuElement(8).setClickable(true);
            this.menus.get(this.CREATE_CITY).get(0).getMenuElement(9).setClickable(true);
        } else {
            this.menus.get(this.CREATE_CITY).get(0).getMenuElement(6).setClickable(false);
            this.menus.get(this.CREATE_CITY).get(0).getMenuElement(7).setClickable(false);
            this.menus.get(this.CREATE_CITY).get(0).getMenuElement(8).setClickable(false);
            this.menus.get(this.CREATE_CITY).get(0).getMenuElement(9).setClickable(false);
        }
    }

    protected final SliderMenu getManageDiplomacy_Alliances() {
        return this.menus.get(this.MANAGE_DIPLOMACY).get(this.MANAGE_DIPLOMACY_ALLIANCES);
    }

    protected final SliderMenu getManageDiplomacy_Relations_Interactive() {
        return this.menus.get(this.MANAGE_DIPLOMACY).get(this.MANAGE_DIPLOMACY_RELATIONS_INTERACTIVE);
    }

    protected final SliderMenu getManageDiplomacy_Pacts3() {
        return this.menus.get(this.MANAGE_DIPLOMACY).get(this.MANAGE_DIPLOMACY_PACTS2);
    }

    protected final SliderMenu getManageDiplomacy_Truces() {
        return this.menus.get(this.MANAGE_DIPLOMACY).get(this.MANAGE_DIPLOMACY_TRUCES);
    }

    protected final SliderMenu getManageDiplomacy_Guarantee() {
        return this.menus.get(this.MANAGE_DIPLOMACY).get(this.MANAGE_DIPLOMACY_GUARANTEE);
    }

    protected final SliderMenu getManageDiplomacy_Defensive() {
        return this.menus.get(this.MANAGE_DIPLOMACY).get(this.MANAGE_DIPLOMACY_DEFENSIVE);
    }

    protected final SliderMenu getManageDiplomacy_MilitaryAccess() {
        return this.menus.get(this.MANAGE_DIPLOMACY).get(this.MANAGE_DIPLOMACY_MILITARY_ACCESS);
    }

    protected final void rebuildManageDiplomacy_Vassals() {
        if (this.MANAGE_DIPLOMACY >= 0) {
            this.menus.get(this.MANAGE_DIPLOMACY).set(this.MANAGE_DIPLOMACY_VASSALS, new Menu_ManageDiplomacy_Vassals());
            this.menus.get(this.MANAGE_DIPLOMACY).get(this.MANAGE_DIPLOMACY_VASSALS).setVisible(true);
            this.rebuildManageDiplomacy_Vassals_List();
        }
    }

    protected final void rebuildManageDiplomacy_Vassals_List() {
        if (this.MANAGE_DIPLOMACY >= 0) {
            this.menus.get(this.MANAGE_DIPLOMACY).set(this.MANAGE_DIPLOMACY_VASSALS_LIST, new Menu_ManageDiplomacy_Vassals_List());
            this.menus.get(this.MANAGE_DIPLOMACY).get(this.MANAGE_DIPLOMACY_VASSALS_LIST).setVisible(true);
        }
    }

    //new rebuild vassals menu
    protected final void rebuildManageDiplomacy_Vassals_Autonomy(int iCivID) {
        if (this.MANAGE_DIPLOMACY >= 0) {
            this.menus.get(this.MANAGE_DIPLOMACY).set(this.MANAGE_DIPLOMACY_VASSALS_AUTONOMY, new Menu_InGame_ChangeAutonomy(iCivID));
            this.menus.get(this.MANAGE_DIPLOMACY).get(this.MANAGE_DIPLOMACY_VASSALS_AUTONOMY).setVisible(true);
        } else if (this.INGAME >= 0) {
            this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(false);
            int tPosX = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosX();
            int tPosY = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosY();
            int tWidth = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getWidth();
            int tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getHeight();
            this.menus.get(this.INGAME).set(this.INGAME_SEND_MESSAGE, new Menu_InGame_ChangeAutonomy(iCivID));
            if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
                tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
            } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
                tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
            }
            this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosX(tPosX);
            this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosY(tPosY);
            this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setWidth(tWidth);
            this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setHeight(tHeight);
            this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(true);
            this.setOrderOfMenu(this.INGAME_SEND_MESSAGE);
            this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).updateMenuElements_IsInView();
        }
    }

    protected final void rebuildManageDiplomacy_Pacts3() {
        if (this.MANAGE_DIPLOMACY >= 0) {
            this.menus.get(this.MANAGE_DIPLOMACY).set(this.MANAGE_DIPLOMACY_PACTS2, new Menu_ManageDiplomacy_Pacts3());
            this.menus.get(this.MANAGE_DIPLOMACY).get(this.MANAGE_DIPLOMACY_PACTS2).setVisible(true);
            this.rebuildManageDiplomacy_Pacts_List();
        }
    }

    protected final void rebuildManageDiplomacy_Truces() {
        if (this.MANAGE_DIPLOMACY >= 0) {
            this.menus.get(this.MANAGE_DIPLOMACY).set(this.MANAGE_DIPLOMACY_TRUCES, new Menu_ManageDiplomacy_Truces());
            this.menus.get(this.MANAGE_DIPLOMACY).get(this.MANAGE_DIPLOMACY_TRUCES).setVisible(true);
            this.rebuildManageDiplomacy_Trcues_List();
        }
    }

    protected final void rebuildManageDiplomacy_Guarantee() {
        if (this.MANAGE_DIPLOMACY >= 0) {
            this.menus.get(this.MANAGE_DIPLOMACY).set(this.MANAGE_DIPLOMACY_GUARANTEE, new Menu_ManageDiplomacy_Guarantee());
            this.menus.get(this.MANAGE_DIPLOMACY).get(this.MANAGE_DIPLOMACY_GUARANTEE).setVisible(true);
            this.rebuildManageDiplomacy_Guarantee_List();
        }
    }

    protected final void rebuildManageDiplomacy_Defensive() {
        if (this.MANAGE_DIPLOMACY >= 0) {
            this.menus.get(this.MANAGE_DIPLOMACY).set(this.MANAGE_DIPLOMACY_DEFENSIVE, new Menu_ManageDiplomacy_DefensivePact());
            this.menus.get(this.MANAGE_DIPLOMACY).get(this.MANAGE_DIPLOMACY_DEFENSIVE).setVisible(true);
            this.rebuildManageDiplomacy_DefensivePacts_List();
        }
    }

    protected final void rebuildManageDiplomacy_MilitaryAccess() {
        if (this.MANAGE_DIPLOMACY >= 0) {
            this.menus.get(this.MANAGE_DIPLOMACY).set(this.MANAGE_DIPLOMACY_MILITARY_ACCESS, new Menu_ManageDiplomacy_MilitaryAccess());
            this.menus.get(this.MANAGE_DIPLOMACY).get(this.MANAGE_DIPLOMACY_MILITARY_ACCESS).setVisible(true);
            this.rebuildManageDiplomacy_MilitaryAccess_List();
        }
    }

    protected final void rebuildManageDiplomacy_Pacts_List() {
        if (this.MANAGE_DIPLOMACY >= 0) {
            this.menus.get(this.MANAGE_DIPLOMACY).set(this.MANAGE_DIPLOMACY_PACTS_LIST, new Menu_ManageDiplomacy_Pacts_List());
            this.menus.get(this.MANAGE_DIPLOMACY).get(this.MANAGE_DIPLOMACY_PACTS_LIST).setVisible(true);
        }
    }

    protected final void rebuildManageDiplomacy_Trcues_List() {
        if (this.MANAGE_DIPLOMACY >= 0) {
            this.menus.get(this.MANAGE_DIPLOMACY).set(this.MANAGE_DIPLOMACY_TRUCES_LIST, new Menu_ManageDiplomacy_Truces_List());
            this.menus.get(this.MANAGE_DIPLOMACY).get(this.MANAGE_DIPLOMACY_TRUCES_LIST).setVisible(true);
        }
    }

    protected final void rebuildManageDiplomacy_MilitaryAccess_List() {
        if (this.MANAGE_DIPLOMACY >= 0) {
            this.menus.get(this.MANAGE_DIPLOMACY).set(this.MANAGE_DIPLOMACY_MILITARY_ACCESS_LIST, new Menu_ManageDiplomacy_MilitaryAccess_List());
            this.menus.get(this.MANAGE_DIPLOMACY).get(this.MANAGE_DIPLOMACY_MILITARY_ACCESS_LIST).setVisible(true);
        }
    }

    protected final void rebuildManageDiplomacy_Guarantee_List() {
        if (this.MANAGE_DIPLOMACY >= 0) {
            this.menus.get(this.MANAGE_DIPLOMACY).set(this.MANAGE_DIPLOMACY_GUARANTEE_LIST, new Menu_ManageDiplomacy_Guarantee_List());
            this.menus.get(this.MANAGE_DIPLOMACY).get(this.MANAGE_DIPLOMACY_GUARANTEE_LIST).setVisible(true);
        }
    }

    protected final void rebuildManageDiplomacy_DefensivePacts_List() {
        if (this.MANAGE_DIPLOMACY >= 0) {
            this.menus.get(this.MANAGE_DIPLOMACY).set(this.MANAGE_DIPLOMACY_DEFENSIVE_LIST, new Menu_ManageDiplomacy_DefensivePact_List());
            this.menus.get(this.MANAGE_DIPLOMACY).get(this.MANAGE_DIPLOMACY_DEFENSIVE_LIST).setVisible(true);
        }
    }

    protected final SliderMenu getManageDiplomacy_Vassals() {
        return this.menus.get(this.MANAGE_DIPLOMACY).get(this.MANAGE_DIPLOMACY_VASSALS);
    }

    protected final void setVisible_ManageDiplomacy_Relations(boolean visible) {
        this.menus.get(this.MANAGE_DIPLOMACY).get(this.MANAGE_DIPLOMACY_RELATIONS_INTERACTIVE).setVisible(visible);
    }

    protected final void setVisible_ManageDiplomacy_Pacts3(boolean visible) {
        this.menus.get(this.MANAGE_DIPLOMACY).get(this.MANAGE_DIPLOMACY_PACTS2).setVisible(visible);
    }

    protected final void setVisible_ManageDiplomacy_Truces(boolean visible) {
        this.menus.get(this.MANAGE_DIPLOMACY).get(this.MANAGE_DIPLOMACY_TRUCES).setVisible(visible);
    }

    protected final void setVisible_ManageDiplomacy_Guarantee(boolean visible) {
        this.menus.get(this.MANAGE_DIPLOMACY).get(this.MANAGE_DIPLOMACY_GUARANTEE).setVisible(visible);
    }

    protected final void setVisible_ManageDiplomacy_DefensivePact(boolean visible) {
        this.menus.get(this.MANAGE_DIPLOMACY).get(this.MANAGE_DIPLOMACY_DEFENSIVE).setVisible(visible);
    }

    protected final void setVisible_ManageDiplomacy_MilitaryAccess(boolean visible) {
        this.menus.get(this.MANAGE_DIPLOMACY).get(this.MANAGE_DIPLOMACY_MILITARY_ACCESS).setVisible(visible);
    }

    protected final void setVisible_ManageDiplomacy_Pacts_List(boolean visible) {
        this.menus.get(this.MANAGE_DIPLOMACY).get(this.MANAGE_DIPLOMACY_PACTS_LIST).setVisible(visible);
    }

    protected final void setVisible_ManageDiplomacy_Guarantee_List(boolean visible) {
        this.menus.get(this.MANAGE_DIPLOMACY).get(this.MANAGE_DIPLOMACY_GUARANTEE_LIST).setVisible(visible);
    }

    protected final void setVisible_ManageDiplomacy_DefensivePacts_List(boolean visible) {
        this.menus.get(this.MANAGE_DIPLOMACY).get(this.MANAGE_DIPLOMACY_DEFENSIVE_LIST).setVisible(visible);
    }

    protected final void setVisible_ManageDiplomacy_MilitaryAccess_List(boolean visible) {
        this.menus.get(this.MANAGE_DIPLOMACY).get(this.MANAGE_DIPLOMACY_MILITARY_ACCESS_LIST).setVisible(visible);
    }

    protected final void setVisible_ManageDiplomacy_Vassals(boolean visible) {
        this.menus.get(this.MANAGE_DIPLOMACY).get(this.MANAGE_DIPLOMACY_VASSALS).setVisible(visible);
    }

    protected final void setVisible_ManageDiplomacy_Vassals_List(boolean visible) {
        this.menus.get(this.MANAGE_DIPLOMACY).get(this.MANAGE_DIPLOMACY_VASSALS_LIST).setVisible(visible);
    }

    //setvisible vassals menu
    protected final void setVisible_ManageDiplomacy_Vassals_Autonomy(boolean visible) {
        this.menus.get(this.MANAGE_DIPLOMACY).get(this.MANAGE_DIPLOMACY_VASSALS_AUTONOMY).setVisible(visible);
    }

    protected final boolean getVisible_InGame_FlagAction() {
        return this.menus.get(this.INGAME).get(this.INGAME_FLAG_ACTION).getVisible();
    }

    protected final boolean getVisible_InGame_FlagAction_Console() {
        return this.menus.get(this.INGAME).get(this.INGAME_FLAG_ACTION_CONSOLE).getVisible();
    }

    protected final void setVisible_InGame_FlagAction(boolean visible) {
        this.menus.get(this.INGAME).get(this.INGAME_FLAG_ACTION).setVisible(visible);
        this.menus.get(this.INGAME).get(this.INGAME_FLAG_ACTION_BOT).setVisible(visible);
        this.menus.get(this.INGAME).get(this.INGAME_FLAG_ACTION_BOT_RIGHT_LEFT).setVisible(visible);
        this.menus.get(this.INGAME).get(this.INGAME_FLAG_ACTION_GRAPH_MODES).setVisible(visible);
        if (visible) {
            this.menus.get(this.INGAME).set(this.INGAME_FLAG_ACTION_BOT_LEFT, new Menu_InGame_FlagAction_Bot_Left());
            this.menus.get(this.INGAME).set(this.INGAME_FLAG_ACTION_BOT_RIGHT_RIGHT, new Menu_InGame_FlagAction_Bot_Right_Right());
            this.menus.get(this.INGAME).set(this.INGAME_FLAG_ACTION_STATS, new Menu_InGame_FlagAction_Stats());
            this.menus.get(this.INGAME).set(this.INGAME_FLAG_ACTION_BOT_RIGHT_LEFT, new Menu_InGame_FlagAction_Bot_Right_Left());
            this.setVisible_InGame_Budget(false);
        }
        this.menus.get(this.INGAME).get(this.INGAME_FLAG_ACTION_BOT_LEFT).setVisible(visible);
        this.menus.get(this.INGAME).get(this.INGAME_FLAG_ACTION_BOT_RIGHT_RIGHT).setVisible(visible);
        this.menus.get(this.INGAME).get(this.INGAME_FLAG_ACTION_STATS).setVisible(visible);
        this.menus.get(this.INGAME).get(this.INGAME_FLAG_ACTION_BOT_RIGHT_LEFT).setVisible(visible);
        this.menus.get(this.INGAME).get(this.INGAME_FLAG_ACTION_CONSOLE).setVisible(false);
        if (SoundsManager.isPlayingConsoleMusic) {
            CFG.soundsManager.randomizePlayList();
            CFG.soundsManager.loadNextMusic();
        }
        this.setOrderOfMenu_InGame_FlagAction();
        Game_Render.updateRenderer();
    }

    protected final boolean getVisible_InGame_Budget() {
        return this.menus.get(this.INGAME).get(this.INGAME_BUDGET).getVisible();
    }

    protected final SliderMenu getInGame_Budget() {
        return this.menus.get(this.INGAME).get(this.INGAME_BUDGET);
    }

    protected final void setVisible_InGame_Budget(boolean visible) {
        if (!visible) {
            this.menus.get(this.INGAME).get(this.INGAME_BUDGET).setVisible(visible);
            //also rescind vassals budget just for refreshes
            this.menus.get(this.INGAME).get(this.INGAME_BUDGET_VASSALS).setVisible(visible);
        } else {
            if (this.getVisible_InGame_FlagAction()) {
                this.setVisible_InGame_FlagAction(false);
            }
            int tPosX = this.menus.get(this.INGAME).get(this.INGAME_BUDGET).getPosX();
            int tPosY = this.menus.get(this.INGAME).get(this.INGAME_BUDGET).getPosY();
            int tWidth = this.menus.get(this.INGAME).get(this.INGAME_BUDGET).getWidth();
            this.menus.get(this.INGAME).set(this.INGAME_BUDGET, new Menu_InGame_FlagAction_Budget());
            this.menus.get(this.INGAME).get(this.INGAME_BUDGET).setPosX(tPosX);
            this.menus.get(this.INGAME).get(this.INGAME_BUDGET).setPosY(tPosY);
            this.menus.get(this.INGAME).get(this.INGAME_BUDGET).setWidth(tWidth);
            this.menus.get(this.INGAME).get(this.INGAME_BUDGET).setVisible(true);
            this.setOrderOfMenu(this.INGAME_BUDGET);
        }
    }

    protected final void setVisible_InGame_Budget_Vassals(boolean visible, int nCivID) {
        if (!visible) {
            this.menus.get(this.INGAME).get(this.INGAME_BUDGET_VASSALS).setVisible(visible);
        } else {
            if (this.getVisible_InGame_FlagAction()) {
                this.setVisible_InGame_FlagAction(false);
            }
            int tPosX = this.menus.get(this.INGAME).get(this.INGAME_BUDGET_VASSALS).getPosX();
            int tPosY = this.menus.get(this.INGAME).get(this.INGAME_BUDGET_VASSALS).getPosY();
            int tWidth = this.menus.get(this.INGAME).get(this.INGAME_BUDGET_VASSALS).getWidth();
            this.menus.get(this.INGAME).set(this.INGAME_BUDGET_VASSALS, new Menu_InGame_FlagAction_Budget_Vassals(nCivID));
            this.menus.get(this.INGAME).get(this.INGAME_BUDGET_VASSALS).setPosX(tPosX);
            this.menus.get(this.INGAME).get(this.INGAME_BUDGET_VASSALS).setPosY(tPosY);
            this.menus.get(this.INGAME).get(this.INGAME_BUDGET_VASSALS).setWidth(tWidth);
            this.menus.get(this.INGAME).get(this.INGAME_BUDGET_VASSALS).setVisible(true);
            this.setOrderOfMenu(this.INGAME_BUDGET_VASSALS);
        }
    }

    protected final void setOrderOfTechPoints() {
        this.setOrderOfMenu(this.INGAME_TECHNOLOGY);
    }

    protected final void setOrderOfWonders() {
        this.setOrderOfMenu(this.INGAME_WONDERS);
    }

    protected final void rebuildInGame_FlagActionRightBoth() {
        this.menus.get(this.INGAME).set(this.INGAME_FLAG_ACTION_BOT_RIGHT_LEFT, new Menu_InGame_FlagAction_Bot_Right_Left());
        this.menus.get(this.INGAME).get(this.INGAME_FLAG_ACTION_BOT_RIGHT_LEFT).setVisible(true);
        this.menus.get(this.INGAME).set(this.INGAME_FLAG_ACTION_BOT_RIGHT_RIGHT, new Menu_InGame_FlagAction_Bot_Right_Right());
        this.menus.get(this.INGAME).get(this.INGAME_FLAG_ACTION_BOT_RIGHT_RIGHT).setVisible(true);
    }

    protected final void rebuildInGame_FlagActionRightLeft() {
        this.menus.get(this.INGAME).set(this.INGAME_FLAG_ACTION_BOT_RIGHT_LEFT, new Menu_InGame_FlagAction_Bot_Right_Left());
        this.menus.get(this.INGAME).get(this.INGAME_FLAG_ACTION_BOT_RIGHT_LEFT).setVisible(true);
    }

    protected final void rebuildInGame_FlagActionLeft() {
        this.menus.get(this.INGAME).set(this.INGAME_FLAG_ACTION_BOT_LEFT, new Menu_InGame_FlagAction_Bot_Left());
        this.menus.get(this.INGAME).get(this.INGAME_FLAG_ACTION_BOT_LEFT).setVisible(true);
    }

    protected final void rebuildInGame_FlagActionRightRight() {
        this.menus.get(this.INGAME).set(this.INGAME_FLAG_ACTION_BOT_RIGHT_RIGHT, new Menu_InGame_FlagAction_Bot_Right_Right());
        this.menus.get(this.INGAME).get(this.INGAME_FLAG_ACTION_BOT_RIGHT_RIGHT).setVisible(true);
    }

    protected final void menuInGame_FlagActionBotRightLeft_LoadData(int nCivID) {
        if (this.menus.get(this.INGAME).get(this.INGAME_FLAG_ACTION_BOT_RIGHT_LEFT).getVisible()) {
            this.menus.get(this.INGAME).get(this.INGAME_FLAG_ACTION_BOT_RIGHT_LEFT).getMenuElement(0).setMin(nCivID);
        }
    }

    protected final void setVisible_InGame_FlagAction_Console(boolean visible) {
        this.menus.get(this.INGAME).get(this.INGAME_FLAG_ACTION).setVisible(true);
        this.menus.get(this.INGAME).get(this.INGAME_FLAG_ACTION_STATS).setVisible(!visible);
        this.menus.get(this.INGAME).get(this.INGAME_FLAG_ACTION_BOT).setVisible(!visible);
        this.menus.get(this.INGAME).get(this.INGAME_FLAG_ACTION_BOT_RIGHT_LEFT).setVisible(!visible);
        this.menus.get(this.INGAME).get(this.INGAME_FLAG_ACTION_BOT_RIGHT_RIGHT).setVisible(!visible);
        this.menus.get(this.INGAME).get(this.INGAME_FLAG_ACTION_BOT_LEFT).setVisible(!visible);
        this.menus.get(this.INGAME).get(this.INGAME_FLAG_ACTION_GRAPH_MODES).setVisible(!visible);
        this.menus.get(this.INGAME).get(this.INGAME_FLAG_ACTION_CONSOLE).setVisible(visible);
        if (visible) {
            //dont play troll music console change//
            //CFG.soundsManager.playConsoleMusic();
            Game_Render.updateRenderer();
        } else {
            //CFG.soundsManager.randomizePlayList();
            //CFG.soundsManager.loadNextMusic();
        }
        this.setOrderOfMenu_InGame_FlagAction();
        Game_Render.updateRenderer();
    }

    protected final void disposeChooseScenarioFlags() {
        this.menus.get(this.CHOOSE_SCENARIO).get(1).onBackPressed();
    }

    protected final int getCreateNewGame() {
        return this.CREATE_NEW_GAME;
    }

    protected final boolean getVisible_CreateNewGame_Options() {
        return this.menus.get(this.CREATE_NEW_GAME).get(this.CREATE_NEW_GAME_OPTIONS).getVisible();
    }

    protected final boolean getVisible_CreateRandomGame_Options() {
        return this.menus.get(this.CREATE_RANDOM_GAME).get(this.CREATE_RANDOM_GAME_OPTIONS).getVisible();
    }

    protected final boolean getVisible_CreateRandomGame_Players() {
        return this.menus.get(this.CREATE_RANDOM_GAME).get(this.CREATE_RANDOM_GAME_PLAYERS).getVisible();
    }

    protected final boolean getVisible_CreateRandomGame_Settings() {
        return this.menus.get(this.CREATE_RANDOM_GAME).get(this.CREATE_RANDOM_GAME_SETTINGS).getVisible();
    }

    protected final boolean getVisible_CreateRandomGame_WastelandMaps() {
        return this.menus.get(this.CREATE_RANDOM_GAME).get(this.CREATE_RANDOM_GAME_WASTELANDMAPS).getVisible();
    }

    protected final boolean getVisible_CreateNewGame_Options_Pallets() {
        return this.menus.get(this.CREATE_NEW_GAME).get(this.CREATE_NEW_GAME_PALLETS).getVisible();
    }

    protected final boolean getVisible_CreateNewGame_Options_Scenarios() {
        return this.menus.get(this.CREATE_NEW_GAME).get(this.CREATE_NEW_GAME_SCENARIOS).getVisible();
    }

    protected final void setVisible_CreateNewGame_Options_Scenarios(boolean visible) {
        this.menus.get(this.CREATE_NEW_GAME).get(this.CREATE_NEW_GAME_OPTIONS).setVisible(!visible);
        this.menus.get(this.CREATE_NEW_GAME).get(this.CREATE_NEW_GAME_SCENARIOS).setVisible(visible);
    }

    protected final void setVisible_CreateNewGame_Options_Pallets(boolean visible) {
        this.menus.get(this.CREATE_NEW_GAME).get(this.CREATE_NEW_GAME_OPTIONS).setVisible(!visible);
        this.menus.get(this.CREATE_NEW_GAME).get(this.CREATE_NEW_GAME_PALLETS).setVisible(visible);
    }

    protected final void setVisible_CreateNewGame_Options(boolean visible) {
        this.menus.get(this.CREATE_NEW_GAME).get(this.CREATE_NEW_GAME_OPTIONS).setVisible(visible);
        this.menus.get(this.CREATE_NEW_GAME).get(this.CREATE_NEW_GAME_PALLETS).setVisible(false);
        this.menus.get(this.CREATE_NEW_GAME).get(this.CREATE_NEW_GAME_SCENARIOS).setVisible(false);
        if (!this.menus.get(this.CREATE_NEW_GAME).get(this.CREATE_NEW_GAME_TOP).getVisible()) {
            this.menus.get(this.CREATE_NEW_GAME).get(this.CREATE_NEW_GAME_TOP).setVisible(true);
        }
    }

    protected final void setVisible_CreateRandomGame_Options(boolean visible) {
        this.menus.get(this.CREATE_RANDOM_GAME).get(this.CREATE_RANDOM_GAME_OPTIONS).setVisible(visible);
        this.menus.get(this.CREATE_RANDOM_GAME).get(this.CREATE_RANDOM_GAME_WASTELANDMAPS).setVisible(false);
    }

    protected final void setVisible_CreateRandomGame_Setings(boolean visible) {
        this.menus.get(this.CREATE_RANDOM_GAME).get(this.CREATE_RANDOM_GAME_SETTINGS).setVisible(visible);
        if (this.getVisible_CreateRandomGame_Settings()) {
            this.setOrderOfMenu(this.CREATE_RANDOM_GAME_SETTINGS);
        }
    }

    protected final void setVisible_CreateRandomGame_Players(boolean visible) {
        this.menus.get(this.CREATE_RANDOM_GAME).get(this.CREATE_RANDOM_GAME_PLAYERS).setVisible(visible);
        if (this.getVisible_CreateRandomGame_Players()) {
            this.setOrderOfMenu(this.CREATE_RANDOM_GAME_PLAYERS);
        }
    }

    protected final void rebuildCreateRandomGame_Settings() {
        if (this.CREATE_RANDOM_GAME_SETTINGS != -1) {
            this.menus.get(this.CREATE_RANDOM_GAME).set(this.CREATE_RANDOM_GAME_SETTINGS, new Menu_RandomGame_Settings());
        }
    }

    protected final void rebuildCreateRandomGame_Players() {
        this.menus.get(this.CREATE_RANDOM_GAME).set(this.CREATE_RANDOM_GAME_PLAYERS, new Menu_RandomGame_Players());
    }

    protected final void setVisible_CreateRandomGame_WastelandMaps(boolean visible) {
        this.menus.get(this.CREATE_RANDOM_GAME).get(this.CREATE_RANDOM_GAME_WASTELANDMAPS).setVisible(visible);
    }

    protected final void setVisible_CreateNewGame_CivInfo(boolean visible) {
        try {
            this.menus.get(this.CREATE_NEW_GAME).get(this.CREATE_NEW_GAME_TOP_VIEWS).setVisible(true);
            this.menus.get(this.CREATE_NEW_GAME).get(this.CREATE_NEW_GAME_CIV_INFO).setVisible(true);
            this.menus.get(this.CREATE_NEW_GAME).get(this.CREATE_NEW_GAME_CIV_INFO_STATS).setVisible(true);
            this.menus.get(this.CREATE_NEW_GAME).get(this.CREATE_NEW_GAME_CIV_INFO_DIPLOMACY).setVisible(true);
            this.menus.get(this.CREATE_NEW_GAME).get(this.CREATE_NEW_GAME_CIV_INFO_PLAYERS).setVisible(true);
            this.menus.get(this.CREATE_NEW_GAME).get(this.CREATE_NEW_GAME_CIV_INFO_SHOW_MENU).setVisible(false);
        }
        catch (IndexOutOfBoundsException indexOutOfBoundsException) {
        }
        catch (NullPointerException nullPointerException) {
            // empty catch block
        }
    }

    protected final void setVisible_InGamePeaceTreatyProvinces(boolean visible) {
        this.menus.get(this.INGAME_PEACE_TREATY).get(this.INGAME_PEACE_TREATY_PROVINCES).setVisible(visible);
        this.menus.get(this.INGAME_PEACE_TREATY).get(this.INGAME_PEACE_TREATY_PROVINCES_SHOW).setVisible(!visible);
    }

    protected final void setVisible_InGamePeaceTreaty_ResponseProvinces(boolean visible) {
        this.menus.get(this.INGAME_PEACE_TREATY_RESPONSE).get(this.INGAME_PEACE_TREATY_RESPONSE_PROVINCES).setVisible(visible);
        this.menus.get(this.INGAME_PEACE_TREATY_RESPONSE).get(this.INGAME_PEACE_TREATY_RESPONSE_PROVINCES_SHOW).setVisible(!visible);
    }

    protected final SliderMenu getInGame_FlagActionGraph() {
        return this.menus.get(this.INGAME).get(this.INGAME_FLAG_ACTION_BOT);
    }

    protected final SliderMenu getCreate_NewGame_Civ_Info() {
        return this.menus.get(this.CREATE_NEW_GAME).get(this.CREATE_NEW_GAME_CIV_INFO);
    }

    protected final SliderMenu getCreate_NewGame_Civ_Info_Stats() {
        return this.menus.get(this.CREATE_NEW_GAME).get(this.CREATE_NEW_GAME_CIV_INFO_STATS);
    }

    protected final SliderMenu getCreateAVassal_Info() {
        return this.menus.get(this.INGAME_CREATE_VASSAL).get(this.INGAME_CREATE_VASSAL_INFO);
    }

    protected final SliderMenu getCreateAVassal_Stats() {
        return this.menus.get(this.INGAME_CREATE_VASSAL).get(this.INGAME_CREATE_VASSAL_INFO_STATS);
    }

    protected final SliderMenu getCreateAVassal_ChangeAutonomy() {
        return this.menus.get(this.INGAME_CREATE_VASSAL).get(this.INGAME_CREATE_VASSAL_CHANGEAUTONOMY);
    }

    //new rebuild vassals menu
    protected final void rebuildCreateAVassal_ChangeAutonomy() {
        if (this.INGAME_CREATE_VASSAL >= 0) {
            this.menus.get(this.INGAME_CREATE_VASSAL).set(this.INGAME_CREATE_VASSAL_CHANGEAUTONOMY, new Menu_InGame_CreateAVassal_ChangeAutonomy(CFG.createVassal_Data.sCivTag));
            this.menus.get(this.INGAME_CREATE_VASSAL).get(this.INGAME_CREATE_VASSAL_CHANGEAUTONOMY).setVisible(true);
        }
    }

    protected final SliderMenu getCreateAVassal_ChangeIdeology() {
        return this.menus.get(this.INGAME_CREATE_VASSAL).get(this.INGAME_CREATE_VASSAL_CHANGEGOVERNMENT);
    }

    //new rebuild vassals menu
    protected final void rebuildCreateAVassal_ChangeIdeology() {
        if (this.INGAME_CREATE_VASSAL >= 0) {
            this.menus.get(this.INGAME_CREATE_VASSAL).set(this.INGAME_CREATE_VASSAL_CHANGEGOVERNMENT, new Menu_InGame_CreateAVassal_ChangeGovernment(CFG.createVassal_Data.sCivTag));
            this.menus.get(this.INGAME_CREATE_VASSAL).get(this.INGAME_CREATE_VASSAL_CHANGEGOVERNMENT).setVisible(true);
        }
    }

    protected final void rebuildCreate_NewGame_Civ_Info_Diplomacy() {
        try {
            boolean tVisible = this.menus.get(this.CREATE_NEW_GAME).get(this.CREATE_NEW_GAME_CIV_INFO_DIPLOMACY).getVisible();
            this.menus.get(this.CREATE_NEW_GAME).set(this.CREATE_NEW_GAME_CIV_INFO_DIPLOMACY, new Menu_Civilization_Info_Diplomacy());
            this.menus.get(this.CREATE_NEW_GAME).get(this.CREATE_NEW_GAME_CIV_INFO_DIPLOMACY).setPosY(2 + this.menus.get(this.CREATE_NEW_GAME).get(this.CREATE_NEW_GAME_CIV_INFO_STATS).getPosY() + this.menus.get(this.CREATE_NEW_GAME).get(this.CREATE_NEW_GAME_CIV_INFO_STATS).getHeight() + this.menus.get(this.CREATE_NEW_GAME).get(this.CREATE_NEW_GAME_CIV_INFO_DIPLOMACY).getTitle().getHeight());
            this.menus.get(this.CREATE_NEW_GAME).get(this.CREATE_NEW_GAME_CIV_INFO_DIPLOMACY).setVisible(tVisible);
        }
        catch (NullPointerException nullPointerException) {
        }
        catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            // empty catch block
        }
    }

    protected final void rebuildInGame_Civ_Info_Diplomacy() {
        boolean tVisible = this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_DIPLOMACY).getVisible();
        this.menus.get(this.INGAME).set(this.INGAME_CIV_INFO_DIPLOMACY, new Menu_InGame_CivInfo_Stats_Diplomacy());
        this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_DIPLOMACY).setPosY(2 + this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO2).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO2).getHeight() + this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_DIPLOMACY).getTitle().getHeight());
        this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_DIPLOMACY).setVisible(tVisible);
        this.rebuildInGame_Civ_Info_Actions();
    }

    protected final SliderMenu getInGame_Civ_Info_Diplomacy() {
        return this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_DIPLOMACY);
    }

    protected final SliderMenu getInGame_Civ_Info() {
        return this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO2);
    }

    protected final SliderMenu getInGame_Civ_Info_Actions() {
        return this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_ACTIONS);
    }

    protected final void rebuildInGame_Civ_Info_Decisions() {
        int tNumOfElements = this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_DECISIONS).getMenuElementsSize();
        int tY = this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_DECISIONS).getMenuPosY();
        boolean tVisible = this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_DECISIONS).getVisible();
        this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_DECISIONS).actionClose();
        this.menus.get(this.INGAME).set(this.INGAME_CIV_INFO_DECISIONS, new Menu_InGame_CivInfo_Stats_Decisions());
        this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_DECISIONS).setPosY(2 + this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_DIPLOMACY).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_DIPLOMACY).getHeight() + this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_DECISIONS).getTitle().getHeight());
        this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_DECISIONS).setVisible(tVisible);
        if (tNumOfElements == this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_DECISIONS).getMenuElementsSize()) {
            this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_DECISIONS).setMenuPosY(tY);
        }
    }

    protected final boolean getVisibleInGame_TurnSummary() {
        return this.menus.get(this.INGAME).get(this.INGAME_TURN_SUMMARY).getVisible();
    }

    protected final void setVisibleInGame_TurnSummary(boolean visible) {
        this.menus.get(this.INGAME).get(this.INGAME_TURN_SUMMARY).setVisible(visible);
    }

    protected final void rebuildInGame_TurnSummary() {
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_TURN_SUMMARY).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_TURN_SUMMARY).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_TURN_SUMMARY).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_TURN_SUMMARY).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_TURN_SUMMARY, new Menu_InGame_TurnSummary());
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_TURN_SUMMARY).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_TURN_SUMMARY).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_TURN_SUMMARY).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_TURN_SUMMARY).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_TURN_SUMMARY).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_TURN_SUMMARY).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_TURN_SUMMARY).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_TURN_SUMMARY).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_TURN_SUMMARY).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_TURN_SUMMARY).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_TURN_SUMMARY).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_TURN_SUMMARY).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_TURN_SUMMARY).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_TURN_SUMMARY).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_TURN_SUMMARY).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_TURN_SUMMARY).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_TURN_SUMMARY).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_TURN_SUMMARY).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_TURN_SUMMARY).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_TURN_SUMMARY).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_TURN_SUMMARY).setVisible(true);
        this.setOrderOfMenu(this.INGAME_TURN_SUMMARY);
        this.menus.get(this.INGAME).get(this.INGAME_TURN_SUMMARY).updateMenuElements_IsInView();
    }

    protected final boolean getVisibleInGame_History() {
        return this.menus.get(this.INGAME).get(this.INGAME_HISTORY).getVisible();
    }

    protected final void setVisibleInGame_History(boolean visible) {
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_HISTORY).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_HISTORY).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_HISTORY).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_HISTORY).getHeight();
        this.menus.get(this.INGAME).get(this.INGAME_HISTORY).setVisible(visible);
        if (!visible) {
            this.menus.get(this.INGAME).set(this.INGAME_HISTORY, new Menu_InGame_History(0));
            HistoryManager.clearHistoryDates();
        }
        this.menus.get(this.INGAME).get(this.INGAME_HISTORY).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_HISTORY).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_HISTORY).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_HISTORY).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_HISTORY).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_History() {
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_HISTORY).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_HISTORY).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_HISTORY).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_HISTORY).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_HISTORY, new Menu_InGame_History());
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_HISTORY).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_HISTORY).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_HISTORY).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_HISTORY).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_HISTORY).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_HISTORY).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_HISTORY).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_HISTORY).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_HISTORY).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_HISTORY).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_HISTORY).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_HISTORY).getMenuElementsSize() - 1).getHeight() + CFG.PADDING && (tHeight = this.menus.get(this.INGAME).get(this.INGAME_HISTORY).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_HISTORY).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_HISTORY).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_HISTORY).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) > (CFG.TEXT_HEIGHT + CFG.PADDING * 2) * 8) {
            tHeight = (CFG.TEXT_HEIGHT + CFG.PADDING * 2) * 8;
        }
        this.menus.get(this.INGAME).get(this.INGAME_HISTORY).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_HISTORY).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_HISTORY).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_HISTORY).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_HISTORY).setVisible(true);
        this.setOrderOfMenu(this.INGAME_HISTORY);
        this.menus.get(this.INGAME).get(this.INGAME_HISTORY).updateMenuElements_IsInView();
    }

    protected final boolean getVisibleInGame_MilitaryAlliances() {
        return this.menus.get(this.INGAME).get(this.INGAME_MILITARY_ALLIANCES).getVisible();
    }

    protected final void setVisibleInGame_MilitaryAlliances(boolean visible) {
        this.menus.get(this.INGAME).get(this.INGAME_MILITARY_ALLIANCES).setVisible(visible);
    }

    protected final void rebuildInGame_MilitaryAlliances() {
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_MILITARY_ALLIANCES).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_MILITARY_ALLIANCES).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_MILITARY_ALLIANCES).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_MILITARY_ALLIANCES).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_MILITARY_ALLIANCES, new Menu_InGame_MilitaryAlliances());
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_MILITARY_ALLIANCES).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MILITARY_ALLIANCES).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_MILITARY_ALLIANCES).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MILITARY_ALLIANCES).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_MILITARY_ALLIANCES).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MILITARY_ALLIANCES).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_MILITARY_ALLIANCES).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MILITARY_ALLIANCES).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_MILITARY_ALLIANCES).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MILITARY_ALLIANCES).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_MILITARY_ALLIANCES).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MILITARY_ALLIANCES).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_MILITARY_ALLIANCES).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MILITARY_ALLIANCES).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_MILITARY_ALLIANCES).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MILITARY_ALLIANCES).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_MILITARY_ALLIANCES).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_MILITARY_ALLIANCES).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_MILITARY_ALLIANCES).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_MILITARY_ALLIANCES).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_MILITARY_ALLIANCES).setVisible(true);
        this.setOrderOfMenu(this.INGAME_MILITARY_ALLIANCES);
        this.menus.get(this.INGAME).get(this.INGAME_MILITARY_ALLIANCES).updateMenuElements_IsInView();
    }

    protected final boolean getVisibleInGame_WorldPopulation() {
        return this.menus.get(this.INGAME).get(this.INGAME_WORLD_POPULATION).getVisible();
    }

    protected final void setVisibleInGame_WorldPopulation(boolean visible) {
        this.menus.get(this.INGAME).get(this.INGAME_WORLD_POPULATION).setVisible(visible);
    }

    protected final boolean getVisibleInGame_Wars() {
        return this.menus.get(this.INGAME).get(this.INGAME_WARS).getVisible();
    }

    protected final boolean getVisibleInGame_Event() {
        return this.menus.get(this.INGAME).get(this.INGAME_EVENT).getVisible();
    }

    protected final boolean getVisibleInGame_SendMessage() {
        return this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getVisible();
    }

    //close in game events
    protected final boolean closeInGame_Event() {
        if (this.INGAME_EVENT > 0 && CFG.eventsManager.getEvent(Menu_InGame_Event.EVENT_ID).lDecisions.size() == 1 && !CFG.eventsManager.getEvent(Menu_InGame_Event.EVENT_ID).superEvent) {
            //default to 0 decision if only one
            try {
                if (CFG.eventsManager.getEvent(Menu_InGame_Event.EVENT_ID).getCivID() >= 0) {
                    CFG.game.getCiv(CFG.eventsManager.getEvent(Menu_InGame_Event.EVENT_ID).getCivID()).addEvent_DecisionTaken(CFG.eventsManager.getEvent(Menu_InGame_Event.EVENT_ID).getEventTag() + "_" + 0);
                }
                ((Event_Decision)CFG.eventsManager.getEvent(Menu_InGame_Event.EVENT_ID).lDecisions.get(0)).executeDecision();
            } catch (IndexOutOfBoundsException var3) {
            }

            CFG.menuManager.setVisibleInGame_Event(false);

            //check events, run if index
            //if not return false
            if (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getEventsToRunSize() > 0) {
                Menu_InGame_Event.EVENT_ID = CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getEventsToRun(0);
                CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).removeEventToRun(0);
                CFG.menuManager.rebuildInGame_Event();
            } else {
                return false;
            }
            return true;
        }

        return false;
    }

    protected final void centerInGame_Event() {
        CFG.toast.setInView(CFG.langManager.get("Event") + ": " + this.menus.get(this.INGAME).get(this.INGAME_EVENT).getTitle().getText(), CFG.COLOR_TEXT_NUM_OF_PROVINCES);
        this.menus.get(this.INGAME).get(this.INGAME_EVENT).setPosX(CFG.GAME_WIDTH / 2 - this.menus.get(this.INGAME).get(this.INGAME_EVENT).getWidth() / 2);
        this.menus.get(this.INGAME).get(this.INGAME_EVENT).setPosY(CFG.GAME_HEIGHT / 2 - (int)((float)this.menus.get(this.INGAME).get(this.INGAME_EVENT).getHeight() * 0.75f));
        this.setOrderOfMenu(this.INGAME_EVENT);
    }

    protected final void setVisibleInGame_Wars(boolean visible) {
        this.menus.get(this.INGAME).get(this.INGAME_WARS).setVisible(visible);
    }

    protected final void setVisibleInGame_Event(boolean visible) {
        try {
            this.menus.get(this.INGAME).get(this.INGAME_EVENT).setVisible(visible);
        }
        catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            // empty catch block
        }
    }

    protected final void setVisibleInGame_SendMessage(boolean visible) {
        try {
            this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(visible);
        }
        catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            // empty catch block
        }
    }

    protected final void setVisibleInGame_Plunder(boolean visible) {
        try {
            this.menus.get(this.INGAME).get(this.INGAME_PLUNDER).setVisible(visible);
        }
        catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            // empty catch block
        }
    }

    protected final boolean getVisibleInGame_WarDetails() {
        return this.menus.get(this.INGAME).get(this.INGAME_WAR_DETAILS).getVisible();
    }

    protected final void setVisibleInGame_WarDetails(boolean visible) {
        this.menus.get(this.INGAME).get(this.INGAME_WAR_DETAILS).setVisible(visible);
    }

    protected final boolean getVisibleInGame_Rank() {
        return this.menus.get(this.INGAME).get(this.INGAME_RANK).getVisible();
    }

    protected final void setVisibleInGame_Rank(boolean visible) {
        this.menus.get(this.INGAME).get(this.INGAME_RANK).setVisible(visible);
    }

    protected final boolean getVisibleInGame_ConquredProvinces() {
        return this.menus.get(this.INGAME).get(this.INGAME_CONQURED_PROVINCES).getVisible();
    }

    protected final void setVisibleInGame_ConquredProvinces(boolean visible) {
        this.menus.get(this.INGAME).get(this.INGAME_CONQURED_PROVINCES).setVisible(visible);
    }

    protected final boolean getVisibleInGame_VictoryConditions() {
        return this.menus.get(this.INGAME).get(this.INGAME_VICOTORY_CONDITIONS).getVisible();
    }

    protected final void setVisibleInGame_VictoryConditions(boolean visible) {
        this.menus.get(this.INGAME).get(this.INGAME_VICOTORY_CONDITIONS).setVisible(visible);
    }

    protected final boolean getVisibleInGame_BuildingsConstructed() {
        return this.menus.get(this.INGAME).get(this.INGAME_BUILDINGS_CONSTRUCTED).getVisible();
    }

    protected final void setVisibleInGame_BuildingsConstructed(boolean visible) {
        this.menus.get(this.INGAME).get(this.INGAME_BUILDINGS_CONSTRUCTED).setVisible(visible);
    }

    protected final boolean getVisibleInGame_RecruitedArmy() {
        return this.menus.get(this.INGAME).get(this.INGAME_RECRUITED_ARMY).getVisible();
    }

    protected final void setVisibleInGame_RecruitedArmy(boolean visible) {
        this.menus.get(this.INGAME).get(this.INGAME_RECRUITED_ARMY).setVisible(visible);
    }

    protected final boolean getVisibleInGame_Tribute() {
        return this.menus.get(this.INGAME).get(this.INGAME_TRIBUTE).getVisible();
    }

    protected final void setVisibleInGame_Tribute(boolean visible) {
        this.menus.get(this.INGAME).get(this.INGAME_TRIBUTE).setVisible(visible);
    }

    protected final boolean getVisibleInGame_Technology() {
        return this.menus.get(this.INGAME).get(this.INGAME_TECHNOLOGY).getVisible();
    }

    protected final void setVisibleInGame_Technology(boolean visible) {
        this.menus.get(this.INGAME).get(this.INGAME_TECHNOLOGY).setVisible(visible);
    }

    protected final boolean getVisibleInGame_Wonders() {
        return this.menus.get(this.INGAME).get(this.INGAME_WONDERS).getVisible();
    }

    protected final void setVisibleInGame_Wonders(boolean visible) {
        this.menus.get(this.INGAME).get(this.INGAME_WONDERS).setVisible(visible);
    }

    protected final boolean getVisibleInGame_Army() {
        return this.menus.get(this.INGAME).get(this.INGAME_ARMY).getVisible();
    }

    protected final void setVisibleInGame_Army(boolean visible) {
        this.menus.get(this.INGAME).get(this.INGAME_ARMY).setVisible(visible);
    }

    protected final boolean getVisibleInGame_Playlist() {
        return this.menus.get(this.INGAME).get(this.INGAME_PLAYLIST).getVisible();
    }

    protected final void setVisibleInGame_Playlist(boolean visible) {
        this.menus.get(this.INGAME).get(this.INGAME_PLAYLIST).setVisible(visible);
        if (visible) {
            this.setOrderOfMenu(this.INGAME_PLAYLIST);
        }
    }

    protected final boolean getVisibleInGame_WarPreparations() {
        return this.menus.get(this.INGAME).get(this.INGAME_WAR_PREPARATIONS).getVisible();
    }

    protected final void setVisibleInGame_WarPreparations(boolean visible) {
        this.menus.get(this.INGAME).get(this.INGAME_WAR_PREPARATIONS).setVisible(visible);
        if (visible) {
            this.setOrderOfMenu(this.INGAME_WAR_PREPARATIONS);
        }
    }

    protected final boolean getVisibleInGame_GraphMovements() {
        return this.menus.get(this.INGAME).get(this.INGAME_GRAPH_MOVEMENTS).getVisible();
    }

    protected final void setVisibleInGame_GraphMovements(boolean visible) {
        this.menus.get(this.INGAME).get(this.INGAME_GRAPH_MOVEMENTS).setVisible(visible);
    }

    protected final boolean getVisibleInGame_CensusOfProvince() {
        return this.menus.get(this.INGAME).get(this.INGAME_MAP_CENSUS_OF_PROVINCE).getVisible();
    }

    protected final void setVisibleInGame_CensusOfProvince(boolean visible) {
        this.menus.get(this.INGAME).get(this.INGAME_MAP_CENSUS_OF_PROVINCE).setVisible(visible);
    }

    protected final void rebuildInGame_Rank() {
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_RANK).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_RANK).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_RANK).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_RANK).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_RANK, new Menu_InGame_Rank());
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_RANK).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_RANK).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_RANK).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_RANK).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_RANK).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_RANK).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_RANK).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_RANK).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_RANK).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_RANK).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_RANK).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_RANK).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_RANK).setVisible(true);
        this.setOrderOfMenu(this.INGAME_RANK);
        this.menus.get(this.INGAME).get(this.INGAME_RANK).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_ConqueredProvinces() {
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_CONQURED_PROVINCES).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_CONQURED_PROVINCES).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_CONQURED_PROVINCES).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_CONQURED_PROVINCES).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_CONQURED_PROVINCES, new Menu_InGame_ConqueredProvinces());
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_CONQURED_PROVINCES).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_CONQURED_PROVINCES).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_CONQURED_PROVINCES).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_CONQURED_PROVINCES).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_CONQURED_PROVINCES).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_CONQURED_PROVINCES).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_CONQURED_PROVINCES).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_CONQURED_PROVINCES).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_CONQURED_PROVINCES).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_CONQURED_PROVINCES).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_CONQURED_PROVINCES).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_CONQURED_PROVINCES).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_CONQURED_PROVINCES).setVisible(true);
        this.setOrderOfMenu(this.INGAME_CONQURED_PROVINCES);
        this.menus.get(this.INGAME).get(this.INGAME_CONQURED_PROVINCES).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_VictoryConditions() {
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_VICOTORY_CONDITIONS).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_VICOTORY_CONDITIONS).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_VICOTORY_CONDITIONS).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_VICOTORY_CONDITIONS).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_VICOTORY_CONDITIONS, new Menu_InGame_VicotryConditions());
        tHeight = Math.max(this.menus.get(this.INGAME).get(this.INGAME_VICOTORY_CONDITIONS).getHeight(), tHeight);
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_VICOTORY_CONDITIONS).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_VICOTORY_CONDITIONS).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_VICOTORY_CONDITIONS).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_VICOTORY_CONDITIONS).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_VICOTORY_CONDITIONS).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_VICOTORY_CONDITIONS).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_VICOTORY_CONDITIONS).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_VICOTORY_CONDITIONS).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_VICOTORY_CONDITIONS).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_VICOTORY_CONDITIONS).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_VICOTORY_CONDITIONS).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_VICOTORY_CONDITIONS).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_VICOTORY_CONDITIONS).setVisible(true);
        this.setOrderOfMenu(this.INGAME_VICOTORY_CONDITIONS);
        this.menus.get(this.INGAME).get(this.INGAME_VICOTORY_CONDITIONS).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_BuildingsConstrcuted() {
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_BUILDINGS_CONSTRUCTED).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_BUILDINGS_CONSTRUCTED).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_BUILDINGS_CONSTRUCTED).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_BUILDINGS_CONSTRUCTED).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_BUILDINGS_CONSTRUCTED, new Menu_InGame_BuildingsConstructed());
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_BUILDINGS_CONSTRUCTED).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_BUILDINGS_CONSTRUCTED).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_BUILDINGS_CONSTRUCTED).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_BUILDINGS_CONSTRUCTED).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_BUILDINGS_CONSTRUCTED).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_BUILDINGS_CONSTRUCTED).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_BUILDINGS_CONSTRUCTED).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_BUILDINGS_CONSTRUCTED).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_BUILDINGS_CONSTRUCTED).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_BUILDINGS_CONSTRUCTED).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_BUILDINGS_CONSTRUCTED).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_BUILDINGS_CONSTRUCTED).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_BUILDINGS_CONSTRUCTED).setVisible(true);
        this.setOrderOfMenu(this.INGAME_BUILDINGS_CONSTRUCTED);
        this.menus.get(this.INGAME).get(this.INGAME_BUILDINGS_CONSTRUCTED).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_RecruitedArmy() {
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_RECRUITED_ARMY).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_RECRUITED_ARMY).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_RECRUITED_ARMY).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_RECRUITED_ARMY).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_RECRUITED_ARMY, new Menu_InGame_RecruitedArmy());
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_RECRUITED_ARMY).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_RECRUITED_ARMY).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_RECRUITED_ARMY).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_RECRUITED_ARMY).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_RECRUITED_ARMY).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_RECRUITED_ARMY).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_RECRUITED_ARMY).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_RECRUITED_ARMY).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_RECRUITED_ARMY).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_RECRUITED_ARMY).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_RECRUITED_ARMY).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_RECRUITED_ARMY).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_RECRUITED_ARMY).setVisible(true);
        this.setOrderOfMenu(this.INGAME_RECRUITED_ARMY);
        this.menus.get(this.INGAME).get(this.INGAME_RECRUITED_ARMY).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_Army() {
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_ARMY).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_ARMY).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_ARMY).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_ARMY).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_ARMY, new Menu_InGame_Army());
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_ARMY).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_ARMY).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_ARMY).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_ARMY).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_ARMY).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_ARMY).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_ARMY).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_ARMY).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_ARMY).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_ARMY).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_ARMY).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_ARMY).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_ARMY).setVisible(true);
        this.setOrderOfMenu(this.INGAME_ARMY);
        this.menus.get(this.INGAME).get(this.INGAME_ARMY).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_WorldPopulation() {
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_WORLD_POPULATION).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_WORLD_POPULATION).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_WORLD_POPULATION).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_WORLD_POPULATION).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_WORLD_POPULATION, new Menu_InGame_WorldPopulation());
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_WORLD_POPULATION).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_WORLD_POPULATION).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_WORLD_POPULATION).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_WORLD_POPULATION).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_WORLD_POPULATION).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_WORLD_POPULATION).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_WORLD_POPULATION).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_WORLD_POPULATION).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_WORLD_POPULATION).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_WORLD_POPULATION).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_WORLD_POPULATION).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_WORLD_POPULATION).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_WORLD_POPULATION).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_WORLD_POPULATION).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_WORLD_POPULATION).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_WORLD_POPULATION).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_WORLD_POPULATION).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_WORLD_POPULATION).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_WORLD_POPULATION).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_WORLD_POPULATION).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_WORLD_POPULATION).setVisible(true);
        this.setOrderOfMenu(this.INGAME_WORLD_POPULATION);
        this.menus.get(this.INGAME).get(this.INGAME_WORLD_POPULATION).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_ContinentPopulation() {
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_WORLD_POPULATION).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_WORLD_POPULATION).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_WORLD_POPULATION).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_WORLD_POPULATION).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_WORLD_POPULATION, new Menu_InGame_ContinentPopulation());
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_WORLD_POPULATION).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_WORLD_POPULATION).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_WORLD_POPULATION).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_WORLD_POPULATION).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_WORLD_POPULATION).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_WORLD_POPULATION).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_WORLD_POPULATION).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_WORLD_POPULATION).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_WORLD_POPULATION).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_WORLD_POPULATION).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_WORLD_POPULATION).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_WORLD_POPULATION).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_WORLD_POPULATION).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_WORLD_POPULATION).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_WORLD_POPULATION).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_WORLD_POPULATION).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        if ((float)tHeight > (float)CFG.GAME_HEIGHT * 0.6f) {
            tHeight = (int)((float)CFG.GAME_HEIGHT * 0.6f);
        }
        this.menus.get(this.INGAME).get(this.INGAME_WORLD_POPULATION).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_WORLD_POPULATION).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_WORLD_POPULATION).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_WORLD_POPULATION).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_WORLD_POPULATION).setVisible(true);
        this.setOrderOfMenu(this.INGAME_WORLD_POPULATION);
        this.menus.get(this.INGAME).get(this.INGAME_WORLD_POPULATION).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_Wars() {
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_WARS).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_WARS).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_WARS).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_WARS).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_WARS, new Menu_InGame_Wars());
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_WARS).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_WARS).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_WARS).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_WARS).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_WARS).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_WARS).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_WARS).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_WARS).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_WARS).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_WARS).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_WARS).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_WARS).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_WARS).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_WARS).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_WARS).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_WARS).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_WARS).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_WARS).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_WARS).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_WARS).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_WARS).setVisible(true);
        this.setOrderOfMenu(this.INGAME_WARS);
        this.menus.get(this.INGAME).get(this.INGAME_WARS).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_Event() {
        this.menus.get(this.INGAME).get(this.INGAME_EVENT).setVisible(false);
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_EVENT).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_EVENT).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_EVENT).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_EVENT).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_EVENT, new Menu_InGame_Event());
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_EVENT).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_EVENT).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_EVENT).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_EVENT).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_EVENT).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_EVENT).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_EVENT).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_EVENT).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_EVENT).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_EVENT).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_EVENT).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_EVENT).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_EVENT).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_EVENT).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_EVENT).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_EVENT).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_EVENT).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_EVENT).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_EVENT).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_EVENT).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_EVENT).setVisible(true);
        this.setOrderOfMenu(this.INGAME_EVENT);
        this.menus.get(this.INGAME).get(this.INGAME_EVENT).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_DeclareWar(int onCivID) {
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(false);
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_SEND_MESSAGE, new Menu_InGame_DeclareWar(onCivID));
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(true);
        this.setOrderOfMenu(this.INGAME_SEND_MESSAGE);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_SendUltimatum(int onCivID) {
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(false);
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_SEND_MESSAGE, new Menu_InGame_Ultimatum(onCivID));
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(true);
        this.setOrderOfMenu(this.INGAME_SEND_MESSAGE);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_NonAggressionPact(int onCivID) {
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(false);
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_SEND_MESSAGE, new Menu_InGame_NonAggressionPact(onCivID));
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(true);
        this.setOrderOfMenu(this.INGAME_SEND_MESSAGE);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_SendInsult(int onCivID) {
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(false);
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_SEND_MESSAGE, new Menu_InGame_SendInsult(onCivID));
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(true);
        this.setOrderOfMenu(this.INGAME_SEND_MESSAGE);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_MoveCapital(int toProvinceID) {
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(false);
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_SEND_MESSAGE, new Menu_InGame_MoveCapital(toProvinceID));
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(true);
        this.setOrderOfMenu(this.INGAME_SEND_MESSAGE);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_ImproveRelations(int onCivID) {
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(false);
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_SEND_MESSAGE, new Menu_InGame_ImproveRelations(onCivID));
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(true);
        this.setOrderOfMenu(this.INGAME_SEND_MESSAGE);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_OfferAlliance(int onCivID) {
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(false);
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_SEND_MESSAGE, new Menu_InGame_OfferAlliance(onCivID));
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(true);
        this.setOrderOfMenu(this.INGAME_SEND_MESSAGE);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_Migrate(int fromProvinceID, int toProvinceID) {
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(false);
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_SEND_MESSAGE, new Menu_InGame_Migrate(fromProvinceID, toProvinceID));
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(true);
        this.setOrderOfMenu(this.INGAME_SEND_MESSAGE);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_Civilize(int onCivID) {
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(false);
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_SEND_MESSAGE, new Menu_InGame_Civilize(onCivID));
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(true);
        this.setOrderOfMenu(this.INGAME_SEND_MESSAGE);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_JoinAWar(int onCivID, int warAgainst) {
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(false);
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_SEND_MESSAGE, new Menu_InGame_JoinAWar(onCivID, warAgainst));
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(true);
        this.setOrderOfMenu(this.INGAME_SEND_MESSAGE);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_CallToArms(int onCivID) {
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(false);
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_SEND_MESSAGE, new Menu_InGame_CallToArms(onCivID));
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(true);
        this.setOrderOfMenu(this.INGAME_SEND_MESSAGE);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_WarPreparations(int onCivID) {
        this.menus.get(this.INGAME).get(this.INGAME_WAR_PREPARATIONS).setVisible(false);
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_WAR_PREPARATIONS).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_WAR_PREPARATIONS).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_WAR_PREPARATIONS).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_WAR_PREPARATIONS).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_WAR_PREPARATIONS, new Menu_InGame_PrepareForWar(onCivID));
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_WAR_PREPARATIONS).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_WAR_PREPARATIONS).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_WAR_PREPARATIONS).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_WAR_PREPARATIONS).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_WAR_PREPARATIONS).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_WAR_PREPARATIONS).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_WAR_PREPARATIONS).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_WAR_PREPARATIONS).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_WAR_PREPARATIONS).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_WAR_PREPARATIONS).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_WAR_PREPARATIONS).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_WAR_PREPARATIONS).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_WAR_PREPARATIONS).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_WAR_PREPARATIONS).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_WAR_PREPARATIONS).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_WAR_PREPARATIONS).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_WAR_PREPARATIONS).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_WAR_PREPARATIONS).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_WAR_PREPARATIONS).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_WAR_PREPARATIONS).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_WAR_PREPARATIONS).setVisible(true);
        this.setOrderOfMenu(this.INGAME_WAR_PREPARATIONS);
        this.menus.get(this.INGAME).get(this.INGAME_WAR_PREPARATIONS).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_Abadon(int onCivID, int nProvinceID) {
        if (CFG.game.getCiv(onCivID).getCapitalProvinceID() != nProvinceID) {
            this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(false);
            int tPosX = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosX();
            int tPosY = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosY();
            int tWidth = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getWidth();
            int tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getHeight();
            this.menus.get(this.INGAME).set(this.INGAME_SEND_MESSAGE, new Menu_InGame_Abadon(onCivID, nProvinceID));
            if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
                tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
            } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
                tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
            }
            this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosX(tPosX);
            this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosY(tPosY);
            this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setWidth(tWidth);
            this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setHeight(tHeight);
            this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(true);
            this.setOrderOfMenu(this.INGAME_SEND_MESSAGE);
            this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).updateMenuElements_IsInView();
        }
    }

    protected final void rebuildInGame_SupportRebels(int onCivID, int iRebelsID) {
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(false);
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_SEND_MESSAGE, new Menu_InGame_SupportRebels(onCivID, iRebelsID));
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(true);
        this.setOrderOfMenu(this.INGAME_SEND_MESSAGE);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).updateMenuElements_IsInView();
    }

    //annexterritory added change//
    protected final void rebuildInGame_AnnexTerritory(int onCivID) {
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(false);
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_SEND_MESSAGE, new Menu_InGame_AnnexTerritory(onCivID));
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(true);
        this.setOrderOfMenu(this.INGAME_SEND_MESSAGE);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).updateMenuElements_IsInView();
    }



    protected final void rebuildInGame_DefensivePact(int onCivID) {
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(false);
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_SEND_MESSAGE, new Menu_InGame_DefensivePact(onCivID));
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(true);
        this.setOrderOfMenu(this.INGAME_SEND_MESSAGE);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_Plunder(int nProvinceID) {
        this.menus.get(this.INGAME).get(this.INGAME_PLUNDER).setVisible(false);
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_PLUNDER).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_PLUNDER).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_PLUNDER).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_PLUNDER).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_PLUNDER, new Menu_InGame_Plunder(nProvinceID));
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_PLUNDER).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_PLUNDER).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_PLUNDER).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_PLUNDER).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_PLUNDER).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_PLUNDER).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_PLUNDER).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_PLUNDER).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_PLUNDER).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_PLUNDER).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_PLUNDER).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_PLUNDER).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_PLUNDER).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_PLUNDER).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_PLUNDER).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_PLUNDER).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_PLUNDER).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_PLUNDER).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_PLUNDER).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_PLUNDER).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_PLUNDER).setVisible(true);
        this.setOrderOfMenu(this.INGAME_PLUNDER);
        this.menus.get(this.INGAME).get(this.INGAME_PLUNDER).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_FormUnion(int onCivID) {
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(false);
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_SEND_MESSAGE, new Menu_InGame_FormUnion(onCivID));
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(true);
        this.setOrderOfMenu(this.INGAME_SEND_MESSAGE);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_LeaveAllinace(int onCivID) {
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(false);
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_SEND_MESSAGE, new Menu_InGame_LeaveAlliance(onCivID));
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(true);
        this.setOrderOfMenu(this.INGAME_SEND_MESSAGE);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_KickFromAlliance(int onCivID) {
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(false);
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_SEND_MESSAGE, new Menu_InGame_KickFromAlliance(onCivID));
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(true);
        this.setOrderOfMenu(this.INGAME_SEND_MESSAGE);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_Festival(int nProvinceID) {
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(false);
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_SEND_MESSAGE, new Menu_InGame_Festival(nProvinceID));
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(true);
        this.setOrderOfMenu(this.INGAME_SEND_MESSAGE);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_Assimilate(int nProvinceID) {
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(false);
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_SEND_MESSAGE, new Menu_InGame_Assimilate(nProvinceID));
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(true);
        this.setOrderOfMenu(this.INGAME_SEND_MESSAGE);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_Invest(int nProvinceID) {
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(false);
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_SEND_MESSAGE, new Menu_InGame_Invest(nProvinceID));
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(true);
        this.setOrderOfMenu(this.INGAME_SEND_MESSAGE);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_InvestDevelopment(int nProvinceID) {
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(false);
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_SEND_MESSAGE, new Menu_InGame_Invest_Development(nProvinceID));
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(true);
        this.setOrderOfMenu(this.INGAME_SEND_MESSAGE);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_TransferControl(int nProvinceID) {
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(false);
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_SEND_MESSAGE, new Menu_InGame_TransferControl(nProvinceID));
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(true);
        this.setOrderOfMenu(this.INGAME_SEND_MESSAGE);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_BuildPort(int nProvinceID) {
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(false);
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_SEND_MESSAGE, new Menu_InGame_Build_Port(nProvinceID));
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(true);
        this.setOrderOfMenu(this.INGAME_SEND_MESSAGE);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_BuildTower(int nProvinceID) {
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(false);
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_SEND_MESSAGE, new Menu_InGame_Build_Tower(nProvinceID));
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(true);
        this.setOrderOfMenu(this.INGAME_SEND_MESSAGE);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_BuildFort(int nProvinceID) {
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(false);
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_SEND_MESSAGE, new Menu_InGame_Build_Fort(nProvinceID));
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(true);
        this.setOrderOfMenu(this.INGAME_SEND_MESSAGE);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_DestroyFort(int nProvinceID) {
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(false);
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_SEND_MESSAGE, new Menu_InGame_Destroy_Fort(nProvinceID));
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(true);
        this.setOrderOfMenu(this.INGAME_SEND_MESSAGE);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_DestroyTower(int nProvinceID) {
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(false);
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_SEND_MESSAGE, new Menu_InGame_Destroy_Tower(nProvinceID));
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(true);
        this.setOrderOfMenu(this.INGAME_SEND_MESSAGE);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_DestroyFarm(int nProvinceID) {
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(false);
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_SEND_MESSAGE, new Menu_InGame_Destroy_Farm(nProvinceID));
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(true);
        this.setOrderOfMenu(this.INGAME_SEND_MESSAGE);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_DestroyWorkshop(int nProvinceID) {
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(false);
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_SEND_MESSAGE, new Menu_InGame_Destroy_Workshop(nProvinceID));
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(true);
        this.setOrderOfMenu(this.INGAME_SEND_MESSAGE);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_DestroyLibrary(int nProvinceID) {
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(false);
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_SEND_MESSAGE, new Menu_InGame_Destroy_Library(nProvinceID));
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(true);
        this.setOrderOfMenu(this.INGAME_SEND_MESSAGE);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_DestroyArmoury(int nProvinceID) {
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(false);
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_SEND_MESSAGE, new Menu_InGame_Destroy_Armoury(nProvinceID));
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(true);
        this.setOrderOfMenu(this.INGAME_SEND_MESSAGE);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_DestroySupply(int nProvinceID) {
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(false);
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_SEND_MESSAGE, new Menu_InGame_Destroy_Supply(nProvinceID));
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(true);
        this.setOrderOfMenu(this.INGAME_SEND_MESSAGE);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_DestroyPort(int nProvinceID) {
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(false);
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_SEND_MESSAGE, new Menu_InGame_Destroy_Port(nProvinceID));
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(true);
        this.setOrderOfMenu(this.INGAME_SEND_MESSAGE);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_BuildFarm(int nProvinceID) {
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(false);
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_SEND_MESSAGE, new Menu_InGame_Build_Farm(nProvinceID));
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(true);
        this.setOrderOfMenu(this.INGAME_SEND_MESSAGE);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_BuildWorkshop(int nProvinceID) {
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(false);
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_SEND_MESSAGE, new Menu_InGame_Build_Workshop(nProvinceID));
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(true);
        this.setOrderOfMenu(this.INGAME_SEND_MESSAGE);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_ChangeGovernment() {
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(false);
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_SEND_MESSAGE, new Menu_InGame_ChangeGovernment(0));
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(true);
        this.setOrderOfMenu(this.INGAME_SEND_MESSAGE);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_Tribute() {
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_TRIBUTE).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_TRIBUTE).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_TRIBUTE).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_TRIBUTE).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_TRIBUTE, new Menu_InGame_Tribute(0));
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_TRIBUTE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_TRIBUTE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_TRIBUTE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_TRIBUTE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_TRIBUTE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_TRIBUTE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_TRIBUTE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_TRIBUTE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_TRIBUTE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_TRIBUTE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_TRIBUTE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_TRIBUTE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_TRIBUTE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_TRIBUTE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_TRIBUTE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_TRIBUTE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_TRIBUTE).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_TRIBUTE).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_TRIBUTE).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_TRIBUTE).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_TRIBUTE).setVisible(true);
        this.setOrderOfMenu(this.INGAME_TRIBUTE);
        this.menus.get(this.INGAME).get(this.INGAME_TRIBUTE).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_BuildSupply(int nProvinceID) {
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(false);
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_SEND_MESSAGE, new Menu_InGame_Build_Supply(nProvinceID));
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(true);
        this.setOrderOfMenu(this.INGAME_SEND_MESSAGE);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_BuildLibrary(int nProvinceID) {
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(false);
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_SEND_MESSAGE, new Menu_InGame_Build_Library(nProvinceID));
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(true);
        this.setOrderOfMenu(this.INGAME_SEND_MESSAGE);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_BuildArmoury(int nProvinceID) {
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(false);
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_SEND_MESSAGE, new Menu_InGame_Build_Armoury(nProvinceID));
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(true);
        this.setOrderOfMenu(this.INGAME_SEND_MESSAGE);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_TradeRequest_Just() {
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_SEND_MESSAGE, new Menu_InGame_TradeRequest(Menu_InGame_TradeRequest.iOnCivID));
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(true);
        this.setOrderOfMenu_InGame_TradeRequest();
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).updateMenuElements_IsInView();
    }

    protected final void setOrderOfMenu_InGame_TradeRequest() {
        if (this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getVisible()) {
            this.setOrderOfMenu(this.INGAME_SEND_MESSAGE_TRADE_LEFT);
            this.setOrderOfMenu(this.INGAME_SEND_MESSAGE_TRADE_RIGHT);
            this.setOrderOfMenu(this.INGAME_SEND_MESSAGE);
        }
    }

    protected final void setOrderOfMenu_InGame() {
        if (this.menus.get(this.INGAME).get(0).getVisible()) {
            if (this.getVisible_InGame_ProvinceAction()) {
                this.setOrderOfMenu(this.INGAME_PROVINCE_ACTION);
            }
            this.setOrderOfMenu(0);
            this.setOrderOfMenu(this.INGAME_PROVINCE_INFO);
        }
    }

    protected final void rebuildInGame_TradeRequest(int onCivID) {
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(false);
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_SEND_MESSAGE, new Menu_InGame_TradeRequest(onCivID));
        if (tHeight < (int)((float)CFG.BUTTON_HEIGHT * 0.6f) * 6) {
            tHeight = (int)((float)CFG.BUTTON_HEIGHT * 0.6f) * 6;
        }
        if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(true);
        this.menus.get(this.INGAME).set(this.INGAME_SEND_MESSAGE_TRADE_LEFT, new Menu_InGame_TradeRequest_Side(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), true));
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE_TRADE_LEFT).setVisible(true);
        this.menus.get(this.INGAME).set(this.INGAME_SEND_MESSAGE_TRADE_RIGHT, new Menu_InGame_TradeRequest_Side(onCivID, false));
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE_TRADE_RIGHT).setVisible(true);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setHeight(tHeight);
        this.setOrderOfMenu_InGame_TradeRequest();
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_LiberateAVassal(int onCivID) {
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(false);
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_SEND_MESSAGE, new Menu_InGame_LiberateAVassal(onCivID));
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(true);
        this.setOrderOfMenu(this.INGAME_SEND_MESSAGE);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_DeclarationOfIndependence(int onCivID) {
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(false);
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_SEND_MESSAGE, new Menu_InGame_DeclarationOfIndependence(onCivID));
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(true);
        this.setOrderOfMenu(this.INGAME_SEND_MESSAGE);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_MilitartAccess_Give(int onCivID) {
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(false);
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_SEND_MESSAGE, new Menu_InGame_MilitaryAccess_Give(onCivID));
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(true);
        this.setOrderOfMenu(this.INGAME_SEND_MESSAGE);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_MilitartAccess_Ask(int onCivID) {
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(false);
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_SEND_MESSAGE, new Menu_InGame_MilitaryAccess_Ask(onCivID));
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(true);
        this.setOrderOfMenu(this.INGAME_SEND_MESSAGE);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_SendGift(int onCivID) {
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(false);
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_SEND_MESSAGE, new Menu_InGame_SendGift(onCivID));
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(true);
        this.setOrderOfMenu(this.INGAME_SEND_MESSAGE);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_TakeLoan(int onCivID) {
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(false);
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_SEND_MESSAGE, new Menu_InGame_TakeLoan(onCivID));
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(true);
        this.setOrderOfMenu(this.INGAME_SEND_MESSAGE);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_Loans(int onCivID) {
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(false);
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_SEND_MESSAGE, new Menu_InGame_Loans(onCivID));
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(true);
        this.setOrderOfMenu(this.INGAME_SEND_MESSAGE);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_Technology(int onCivID) {
        this.menus.get(this.INGAME).get(this.INGAME_TECHNOLOGY).setVisible(false);
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_TECHNOLOGY).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_TECHNOLOGY).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_TECHNOLOGY).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_TECHNOLOGY).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_TECHNOLOGY, new Menu_InGame_Technology(onCivID));
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_TECHNOLOGY).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_TECHNOLOGY).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_TECHNOLOGY).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_TECHNOLOGY).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_TECHNOLOGY).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_TECHNOLOGY).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_TECHNOLOGY).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_TECHNOLOGY).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_TECHNOLOGY).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_TECHNOLOGY).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_TECHNOLOGY).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_TECHNOLOGY).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_TECHNOLOGY).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_TECHNOLOGY).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_TECHNOLOGY).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_TECHNOLOGY).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_TECHNOLOGY).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_TECHNOLOGY).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_TECHNOLOGY).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_TECHNOLOGY).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_TECHNOLOGY).setVisible(true);
        this.setOrderOfMenu(this.INGAME_TECHNOLOGY);
        this.menus.get(this.INGAME).get(this.INGAME_TECHNOLOGY).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_Wonders() {
        this.menus.get(this.INGAME).get(this.INGAME_WONDERS).setVisible(false);
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_WONDERS).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_WONDERS).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_WONDERS).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_WONDERS).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_WONDERS, new Menu_InGameWonders());
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_WONDERS).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_WONDERS).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_WONDERS).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_WONDERS).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_WONDERS).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_WONDERS).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_WONDERS).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_WONDERS).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_WONDERS).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_WONDERS).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_WONDERS).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_WONDERS).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_WONDERS).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_WONDERS).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_WONDERS).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_WONDERS).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_WONDERS).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_WONDERS).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_WONDERS).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_WONDERS).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_WONDERS).setVisible(true);
        this.setOrderOfMenu(this.INGAME_WONDERS);
        this.menus.get(this.INGAME).get(this.INGAME_WONDERS).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_RepayLoan(int onCivID, int iLoanID) {
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(false);
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_SEND_MESSAGE, new Menu_InGame_RepayLoan(onCivID, iLoanID));
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(true);
        this.setOrderOfMenu(this.INGAME_SEND_MESSAGE);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_OfferVassalization(int onCivID) {
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(false);
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_SEND_MESSAGE, new Menu_InGame_OfferVasalization(onCivID));
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(true);
        this.setOrderOfMenu(this.INGAME_SEND_MESSAGE);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_ProclaimIndependence(int onCivID) {
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(false);
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_SEND_MESSAGE, new Menu_InGame_ProclaimIndependence(onCivID));
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).setVisible(true);
        this.setOrderOfMenu(this.INGAME_SEND_MESSAGE);
        this.menus.get(this.INGAME).get(this.INGAME_SEND_MESSAGE).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_WarDetails() {
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_WAR_DETAILS).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_WAR_DETAILS).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_WAR_DETAILS).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_WAR_DETAILS).getHeight();
        this.menus.get(this.INGAME).get(this.INGAME_WAR_DETAILS).setVisible(false);
        this.menus.get(this.INGAME).set(this.INGAME_WAR_DETAILS, new Menu_InGame_WarDetails());
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_WAR_DETAILS).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_WAR_DETAILS).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_WAR_DETAILS).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_WAR_DETAILS).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_WAR_DETAILS).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_WAR_DETAILS).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_WAR_DETAILS).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_WAR_DETAILS).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_WAR_DETAILS).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_WAR_DETAILS).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_WAR_DETAILS).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_WAR_DETAILS).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_WAR_DETAILS).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_WAR_DETAILS).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_WAR_DETAILS).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_WAR_DETAILS).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_WAR_DETAILS).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_WAR_DETAILS).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_WAR_DETAILS).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_WAR_DETAILS).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_WAR_DETAILS).setVisible(true);
        this.setOrderOfMenu(this.INGAME_WAR_DETAILS);
        this.menus.get(this.INGAME).get(this.INGAME_WAR_DETAILS).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_PeaceTreaty_Scores() {
        int tY = this.menus.get(this.INGAME_PEACE_TREATY).get(this.INGAME_PEACE_TREATY_SCORES).getMenuPosY();
        this.menus.get(this.INGAME_PEACE_TREATY).set(this.INGAME_PEACE_TREATY_SCORES, new Menu_PeaceTreaty_Scores());
        this.menus.get(this.INGAME_PEACE_TREATY).get(this.INGAME_PEACE_TREATY_SCORES).setMenuPosY(tY);
    }

    protected final void rebuildInGame_PeaceTreaty_Provinces() {
        int tY = this.menus.get(this.INGAME_PEACE_TREATY).get(this.INGAME_PEACE_TREATY_PROVINCES).getMenuPosY();
        this.menus.get(this.INGAME_PEACE_TREATY).set(this.INGAME_PEACE_TREATY_PROVINCES, new Menu_PeaceTreaty_Provinces());
        this.menus.get(this.INGAME_PEACE_TREATY).get(this.INGAME_PEACE_TREATY_PROVINCES).setMenuPosY(tY);
    }

    protected final void rebuildInGame_CensusOfProvince(int nProvinceID) {
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_MAP_CENSUS_OF_PROVINCE).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_MAP_CENSUS_OF_PROVINCE).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_MAP_CENSUS_OF_PROVINCE).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_MAP_CENSUS_OF_PROVINCE).getHeight();
        this.menus.get(this.INGAME).set(this.INGAME_MAP_CENSUS_OF_PROVINCE, new Menu_InGame_CensusOfProvince(nProvinceID));
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_MAP_CENSUS_OF_PROVINCE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MAP_CENSUS_OF_PROVINCE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_MAP_CENSUS_OF_PROVINCE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MAP_CENSUS_OF_PROVINCE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_MAP_CENSUS_OF_PROVINCE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MAP_CENSUS_OF_PROVINCE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_MAP_CENSUS_OF_PROVINCE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MAP_CENSUS_OF_PROVINCE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_MAP_CENSUS_OF_PROVINCE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MAP_CENSUS_OF_PROVINCE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_MAP_CENSUS_OF_PROVINCE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MAP_CENSUS_OF_PROVINCE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_MAP_CENSUS_OF_PROVINCE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MAP_CENSUS_OF_PROVINCE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_MAP_CENSUS_OF_PROVINCE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_MAP_CENSUS_OF_PROVINCE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        }
        this.menus.get(this.INGAME).get(this.INGAME_MAP_CENSUS_OF_PROVINCE).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_MAP_CENSUS_OF_PROVINCE).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_MAP_CENSUS_OF_PROVINCE).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_MAP_CENSUS_OF_PROVINCE).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_MAP_CENSUS_OF_PROVINCE).setVisible(true);
        this.setOrderOfMenu(this.INGAME_MAP_CENSUS_OF_PROVINCE);
        this.menus.get(this.INGAME).get(this.INGAME_MAP_CENSUS_OF_PROVINCE).updateMenuElements_IsInView();
    }

    protected final void rebuildInGame_Alliance(int nAllianceID) {
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_ALLIANCE).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_ALLIANCE).getPosY();
        int tWidth = this.menus.get(this.INGAME).get(this.INGAME_ALLIANCE).getWidth();
        int tHeight = this.menus.get(this.INGAME).get(this.INGAME_ALLIANCE).getHeight();
        if (nAllianceID >= 0) {
            Menu_InGame_Alliance.ALLIANCE_ID = nAllianceID;
        }
        this.menus.get(this.INGAME).set(this.INGAME_ALLIANCE, new Menu_InGame_Alliance());
        if (tHeight > this.menus.get(this.INGAME).get(this.INGAME_ALLIANCE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_ALLIANCE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_ALLIANCE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_ALLIANCE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) {
            tHeight = this.menus.get(this.INGAME).get(this.INGAME_ALLIANCE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_ALLIANCE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_ALLIANCE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_ALLIANCE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING;
        } else if (tHeight < this.menus.get(this.INGAME).get(this.INGAME_ALLIANCE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_ALLIANCE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_ALLIANCE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_ALLIANCE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING && (tHeight = this.menus.get(this.INGAME).get(this.INGAME_ALLIANCE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_ALLIANCE).getMenuElementsSize() - 1).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_ALLIANCE).getMenuElement(this.menus.get(this.INGAME).get(this.INGAME_ALLIANCE).getMenuElementsSize() - 1).getHeight() + CFG.PADDING) > (CFG.TEXT_HEIGHT + CFG.PADDING * 2) * 8) {
            tHeight = (CFG.TEXT_HEIGHT + CFG.PADDING * 2) * 8;
        }
        this.menus.get(this.INGAME).get(this.INGAME_ALLIANCE).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_ALLIANCE).setPosY(tPosY);
        this.menus.get(this.INGAME).get(this.INGAME_ALLIANCE).setWidth(tWidth);
        this.menus.get(this.INGAME).get(this.INGAME_ALLIANCE).setHeight(tHeight);
        this.menus.get(this.INGAME).get(this.INGAME_ALLIANCE).setVisible(true);
        this.setOrderOfMenu(this.INGAME_ALLIANCE);
        this.menus.get(this.INGAME).get(this.INGAME_ALLIANCE).updateMenuElements_IsInView();
    }

    protected final void setVisible_InGame_Alliance(boolean visible) {
        this.menus.get(this.INGAME).get(this.INGAME_ALLIANCE).setVisible(visible);
    }

    protected final boolean getVisible_InGame_Alliance() {
        return this.menus.get(this.INGAME).get(this.INGAME_ALLIANCE).getVisible();
    }

    protected final void rebuildInGame_Civ_Info_Actions() {
        int tNumOfElements = this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_ACTIONS).getMenuElementsSize();
        int tY = this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_ACTIONS).getMenuPosY();
        boolean tVisible = this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_ACTIONS).getVisible();
        this.menus.get(this.INGAME).set(this.INGAME_CIV_INFO_ACTIONS, new Menu_InGame_CivInfo_Stats_Actions());
        this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_ACTIONS).setPosY(2 + this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_DIPLO_ACTIONS).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_DIPLO_ACTIONS).getHeight());
        this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_ACTIONS).setVisible(tVisible);
        if (tNumOfElements == this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_ACTIONS).getMenuElementsSize()) {
            this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_ACTIONS).setMenuPosY(tY);
        }
    }

    protected final boolean getVisible_InGame_CivInfo_Stats_Opinions() {
        return this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_OPINIONS).getVisible();
    }

    protected final void rebuildInGame_Civ_Info_Opinions() {
        boolean tVisible = this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_OPINIONS).getVisible();
        this.menus.get(this.INGAME).set(this.INGAME_CIV_INFO_OPINIONS, new Menu_InGame_CivInfo_Stats_Opinions());
        this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_OPINIONS).setPosY(2 + this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_DIPLO_ACTIONS).getPosY() + this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_DIPLO_ACTIONS).getHeight());
        this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_OPINIONS).setVisible(tVisible);
    }

    protected final void setVisible_InGame_CivInfo_Stats_Opinions(boolean visible) {
        if (visible) {
            this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_ACTIONS).actionClose();
            this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_OPINIONS).setVisible(visible);
            this.INGAME_CIV_INFO_ACTIONS_VISIBLE = false;
        } else {
            this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_OPINIONS).actionClose();
            this.menus.get(this.INGAME).get(this.INGAME_CIV_INFO_ACTIONS).setVisible(!visible);
            this.INGAME_CIV_INFO_ACTIONS_VISIBLE = true;
        }
        this.rebuildInGame_Civ_Info_Opinions();
    }

    protected final boolean getVisible_InGame_ProvinceAction() {
        return this.menus.get(this.INGAME).get(this.INGAME_PROVINCE_ACTION).getVisible();
    }

    protected final SliderMenu getInGame_ProvinceAction() {
        return this.menus.get(this.INGAME).get(this.INGAME_PROVINCE_ACTION);
    }

    protected final void setVisible_InGame_ProvinceAction(boolean visible) {
        //visible = true;
        boolean order = !this.menus.get(this.INGAME).get(this.INGAME_PROVINCE_ACTION).getVisible();
        this.menus.get(this.INGAME).get(this.INGAME_PROVINCE_ACTION).setVisible(visible);
        if (visible && order) {
            CFG.menuManager.setOrderOfMenu_InGame();
            if (CFG.viewsManager.getActiveViewID() == ViewsManager.VIEW_DIPLOMACY_MODE) {
                this.setOrderOfMenu_InGame_CivInfo();
            }
        }
    }

    protected final void setVisible_InGame_ProvinceAction_Colonize(boolean visible) {
        if (visible) {
            this.menus.get(this.INGAME).set(this.INGAME_PROVINCE_ACTION_COLONIZE, new Menu_InGame_ProvinceAction_Colonize());
        }
        this.menus.get(this.INGAME).get(this.INGAME_PROVINCE_ACTION_COLONIZE).setVisible(visible);
    }

    protected final void setVisible_InGame_ProvinceAction_Colonize_TechRequired(boolean visible) {
        if (visible) {
            this.menus.get(this.INGAME).set(this.INGAME_PROVINCE_ACTION_COLONIZE_TECH_REQUIRED, new Menu_InGame_Uncolonized());
            this.menus.get(this.INGAME).get(this.INGAME_PROVINCE_ACTION_COLONIZE_TECH_REQUIRED).setVisible(visible);
            this.setOrderOfMenu(this.INGAME_PROVINCE_ACTION_COLONIZE_TECH_REQUIRED);
        } else {
            this.menus.get(this.INGAME).get(this.INGAME_PROVINCE_ACTION_COLONIZE_TECH_REQUIRED).setVisible(visible);
        }
    }

    protected final void setVisible_InGame_Dices(boolean visible) {
        if (visible) {
            this.menus.get(this.INGAME).get(this.INGAME_DICES).updateLanguage();
            this.menus.get(this.INGAME).get(this.INGAME_DICES).setVisible(visible);
            this.setOrderOfMenu(this.INGAME_DICES);
        } else if (this.menus.get(this.INGAME).get(this.INGAME_DICES).getVisible()) {
            this.menus.get(this.INGAME).get(this.INGAME_DICES).setVisible(visible);
        }
    }

    protected final void setVisible_InGame_ProvinceAction_Colonize_BorderOrArmy(boolean visible) {
        if (visible) {
            this.menus.get(this.INGAME).set(this.INGAME_PROVINCE_ACTION_COLONIZE_TECH_REQUIRED, new Menu_InGame_Uncolonized_BorderOrArmy());
            this.menus.get(this.INGAME).get(this.INGAME_PROVINCE_ACTION_COLONIZE_TECH_REQUIRED).setVisible(visible);
            this.setOrderOfMenu(this.INGAME_PROVINCE_ACTION_COLONIZE_TECH_REQUIRED);
        } else {
            this.menus.get(this.INGAME).get(this.INGAME_PROVINCE_ACTION_COLONIZE_TECH_REQUIRED).setVisible(visible);
        }
    }

    protected final void setVisible_InGame_ProvinceAction_Colonize_Just(boolean visible) {
        if (visible) {
            this.menus.get(this.INGAME).set(this.INGAME_PROVINCE_ACTION_COLONIZE_TECH_REQUIRED, new Menu_InGame_UncolonizedJust());
            this.menus.get(this.INGAME).get(this.INGAME_PROVINCE_ACTION_COLONIZE_TECH_REQUIRED).setVisible(visible);
            this.setOrderOfMenu(this.INGAME_PROVINCE_ACTION_COLONIZE_TECH_REQUIRED);
        } else {
            this.menus.get(this.INGAME).get(this.INGAME_PROVINCE_ACTION_COLONIZE_TECH_REQUIRED).setVisible(visible);
        }
    }

    protected final void setVisible_InGame_ProvinceAction_Uncivilize2(boolean visible) {
        this.menus.get(this.INGAME).get(this.INGAME_PROVINCE_ACTION_UNCIVILIZE).setVisible(visible);
        if (visible) {
            this.setOrderOfMenu(this.INGAME_PROVINCE_ACTION_UNCIVILIZE);
        }
    }

    protected final SliderMenu getInGame_MapModes() {
        return this.menus.get(this.INGAME).get(this.INGAME_MAP_MODES);
    }

    protected final void setVisible_InGame_MapModes(boolean visible) {
        this.menus.get(this.INGAME).get(this.INGAME_MAP_MODES).setVisible(visible);
        if (visible) {
            this.setOrderOfMenu(this.INGAME_MAP_MODES);
        }
    }

    protected final boolean getVisible_InGame_MapModes() {
        return this.menus.get(this.INGAME).get(this.INGAME_MAP_MODES).getVisible();
    }

    protected final SliderMenu getCreateNewGame_MapModes() {
        return this.menus.get(this.CREATE_NEW_GAME).get(this.CREATE_NEW_GAME_MAPMODES);
    }

    protected final SliderMenu getInGame_CreateAVassal_MapModes() {
        return this.menus.get(this.INGAME_CREATE_VASSAL).get(this.INGAME_CREATE_VASSAL_MAPMODES);
    }

    protected final boolean getInGame_ProvinceMoveUnits_Visible() {
        return this.menus.get(this.INGAME).get(this.INGAME_PROVINCE_MOVE_UNITS).getVisible();
    }

    protected final SliderMenu getInGame_ProvinceMoveUnits() {
        return this.menus.get(this.INGAME).get(this.INGAME_PROVINCE_MOVE_UNITS);
    }

    protected final void getInGame_ProvinceMoveUnits_Confrim() {
        this.menus.get(this.INGAME).get(this.INGAME_PROVINCE_MOVE_UNITS).extraAction();
    }

    protected final boolean getInGame_ProvinceRecruit_Visible() {
        return this.menus.get(this.INGAME).get(this.INGAME_PROVINCE_RECRUIT_ARMY).getVisible();
    }

    protected final SliderMenu getInGame_ProvinceRecruit() {
        return this.menus.get(this.INGAME).get(this.INGAME_PROVINCE_RECRUIT_ARMY);
    }

    protected final SliderMenu getInGame_ProvinceRecruit_Instantly() {
        return this.menus.get(this.INGAME).get(this.INGAME_PROVINCE_RECRUIT_ARMY_INSTANTLY);
    }

    protected final void getInGame_ProvinceRecruit_Confrim() {
        this.menus.get(this.INGAME).get(this.INGAME_PROVINCE_RECRUIT_ARMY).extraAction();
    }

    protected final boolean getInGame_ProvinceRecruitInstantly_Visible() {
        return this.menus.get(this.INGAME).get(this.INGAME_PROVINCE_RECRUIT_ARMY_INSTANTLY).getVisible();
    }

    protected final void getInGame_ProvinceRecruitInstantly_Confrim() {
        this.menus.get(this.INGAME).get(this.INGAME_PROVINCE_RECRUIT_ARMY_INSTANTLY).extraAction();
    }

    protected final boolean getInGame_ProvinceDisband_Visible() {
        return this.menus.get(this.INGAME).get(this.INGAME_PROVINCE_DISBAND_ARMY).getVisible();
    }

    protected final void getInGame_ProvinceDisband_Confrm() {
        this.menus.get(this.INGAME).get(this.INGAME_PROVINCE_DISBAND_ARMY).extraAction();
    }

    protected final boolean getInGame_ProvinceRegroupArmy_Visible() {
        return this.menus.get(this.INGAME).get(this.INGAME_PROVINCE_REGROUP_ARMY).getVisible();
    }

    protected final void getInGame_ProvinceRegroupArmy_ConfirmMove() {
        block4: {
            try {
                this.menus.get(this.INGAME).get(this.INGAME_PROVINCE_REGROUP_ARMY).extraAction();
            }
            catch (IndexOutOfBoundsException ex) {
                if (CFG.LOGS) {
                    CFG.exceptionStack(ex);
                }
            }
            catch (NullPointerException ex) {
                if (!CFG.LOGS) break block4;
                CFG.exceptionStack(ex);
            }
        }
    }

    protected final boolean getInGame_ProvinceChooseProvince_Visible() {
        return this.menus.get(this.INGAME).get(this.INGAME_ACTION_INFO_CHOOSE_PROVINCE).getVisible();
    }

    protected final MenuElement getInGame_ProvinceMoveUnits_Slider() {
        return this.menus.get(this.INGAME).get(this.INGAME_PROVINCE_MOVE_UNITS).getMenuElement(2);
    }

    protected final void updateInGame_ActionInfo_Move() {
        this.menus.get(this.INGAME).get(this.INGAME_ACTION_INFO_CHOOSE_PROVINCE).getMenuElement(0).setWidth(1);
        this.menus.get(this.INGAME).get(this.INGAME_ACTION_INFO_CHOOSE_PROVINCE).getMenuElement(0).setText(CFG.langManager.get("Move") + " " + this.getInGame_ProvinceMoveUnits_Slider().getCurrent() + " " + CFG.langManager.get("Units") + ". [" + this.getInGame_ProvinceMoveUnits_Slider().getCurrent() + " | " + (this.getInGame_ProvinceMoveUnits_Slider().getTextPos() - this.getInGame_ProvinceMoveUnits_Slider().getCurrent()) + "]");
    }

    protected final void updateInGame_ActionInfo_Regroup() {
        this.menus.get(this.INGAME).get(this.INGAME_ACTION_INFO_CHOOSE_PROVINCE).getMenuElement(0).setWidth(1);
        this.menus.get(this.INGAME).get(this.INGAME_ACTION_INFO_CHOOSE_PROVINCE).getMenuElement(0).setText(CFG.langManager.get("Move") + " " + this.getInGame_RegroupArmy_Slider().getCurrent() + " " + CFG.langManager.get("Units") + ". [" + this.getInGame_RegroupArmy_Slider().getCurrent() + " | " + (this.getInGame_RegroupArmy_Slider().getTextPos() - this.getInGame_RegroupArmy_Slider().getCurrent()) + "]");
        if (CFG.game.currentRegroupArmy.getRouteSize() > 0) {
            this.menus.get(this.INGAME).get(this.INGAME_ACTION_INFO_CHOOSE_PROVINCE).getMenuElement(2).setWidth(1);
            this.menus.get(this.INGAME).get(this.INGAME_ACTION_INFO_CHOOSE_PROVINCE).getMenuElement(2).setText(CFG.langManager.get("TurnsX", CFG.game.currentRegroupArmy.getRouteSize()));
            this.menus.get(this.INGAME).get(this.INGAME_ACTION_INFO_CHOOSE_PROVINCE).getMenuElement(2).setVisible(true);
        } else {
            this.menus.get(this.INGAME).get(this.INGAME_ACTION_INFO_CHOOSE_PROVINCE).getMenuElement(2).setVisible(false);
        }
    }

    protected final void updateInGame_ActionInfo_Recruit() {
        this.menus.get(this.INGAME).get(this.INGAME_ACTION_INFO_CHOOSE_PROVINCE).getMenuElement(0).setWidth(1);
        this.menus.get(this.INGAME).get(this.INGAME_ACTION_INFO_CHOOSE_PROVINCE).getMenuElement(0).setText(CFG.langManager.get("Recruit") + " " + this.getInGame_ProvinceRecruit_Slider().getCurrent() + " " + CFG.langManager.get("Units") + ". [" + (CFG.game.getProvince(CFG.game.getActiveProvinceID()).getArmy(0) + this.getInGame_ProvinceRecruit_Slider().getCurrent()) + "]");
        this.menus.get(this.INGAME).get(this.INGAME_ACTION_INFO_CHOOSE_PROVINCE).getMenuElement(2).setWidth(1);
        this.menus.get(this.INGAME).get(this.INGAME_ACTION_INFO_CHOOSE_PROVINCE).getMenuElement(2).setText(CFG.langManager.get("Cost") + ": " + this.getInGame_ProvinceRecruit_Slider().getCurrent() * CFG.getCostOfRecruitArmyMoney(CFG.game.getActiveProvinceID()));
        this.menus.get(this.INGAME).get(this.INGAME_ACTION_INFO_CHOOSE_PROVINCE).getMenuElement(5).setWidth(1);
        this.menus.get(this.INGAME).get(this.INGAME_ACTION_INFO_CHOOSE_PROVINCE).getMenuElement(5).setCurrent(this.getInGame_ProvinceRecruit_Slider().getCurrent());
    }

    protected final void updateInGame_ActionInfo_RecruitInstantly() {
        this.menus.get(this.INGAME).get(this.INGAME_ACTION_INFO_CHOOSE_PROVINCE).getMenuElement(0).setWidth(1);
        this.menus.get(this.INGAME).get(this.INGAME_ACTION_INFO_CHOOSE_PROVINCE).getMenuElement(0).setText(CFG.langManager.get("Conscript") + " " + this.getInGame_ProvinceRecruitInstantly_Slider().getCurrent() + " " + CFG.langManager.get("Units") + ". [" + (CFG.game.getProvince(CFG.game.getActiveProvinceID()).getArmy(0) + this.getInGame_ProvinceRecruitInstantly_Slider().getCurrent()) + "]");
        this.menus.get(this.INGAME).get(this.INGAME_ACTION_INFO_CHOOSE_PROVINCE).getMenuElement(2).setWidth(1);
        this.menus.get(this.INGAME).get(this.INGAME_ACTION_INFO_CHOOSE_PROVINCE).getMenuElement(2).setText(CFG.langManager.get("Cost") + ": " + this.getInGame_ProvinceRecruitInstantly_Slider().getCurrent() * CFG.getCostOfRecruitArmyMoney_Instantly(CFG.game.getActiveProvinceID()));
        this.menus.get(this.INGAME).get(this.INGAME_ACTION_INFO_CHOOSE_PROVINCE).getMenuElement(4).setWidth(1);
        this.menus.get(this.INGAME).get(this.INGAME_ACTION_INFO_CHOOSE_PROVINCE).getMenuElement(4).setCurrent(this.getInGame_ProvinceRecruitInstantly_Slider().getCurrent());
    }

    protected final void updateInGame_ActionInfo_Disband() {
        this.menus.get(this.INGAME).get(this.INGAME_ACTION_INFO_CHOOSE_PROVINCE).getMenuElement(0).setWidth(1);
        this.menus.get(this.INGAME).get(this.INGAME_ACTION_INFO_CHOOSE_PROVINCE).getMenuElement(0).setText(CFG.langManager.get("Disband") + " " + this.getInGame_ProvinceDisband_Slider().getCurrent() + " [" + (this.getInGame_ProvinceDisband_Slider().getTextPos() - this.getInGame_ProvinceDisband_Slider().getCurrent()) + "] " + CFG.langManager.get("Units") + ".");
        this.menus.get(this.INGAME).get(this.INGAME_ACTION_INFO_CHOOSE_PROVINCE).getMenuElement(2).setWidth(1);
        this.menus.get(this.INGAME).get(this.INGAME_ACTION_INFO_CHOOSE_PROVINCE).getMenuElement(2).setCurrent(this.getInGame_ProvinceDisband_Slider().getCurrent());
    }

    protected final MenuElement getInGame_ProvinceRecruit_Slider() {
        return this.menus.get(this.INGAME).get(this.INGAME_PROVINCE_RECRUIT_ARMY).getMenuElement(2);
    }

    protected final void setVisible_InGame_ProvinceRecruit(boolean visible) {
        this.menus.get(this.INGAME).get(this.INGAME_PROVINCE_RECRUIT_ARMY).setVisible(visible);
        if (!visible) {
            this.menus.get(this.INGAME).get(this.INGAME_ACTION_INFO_CHOOSE_PROVINCE).setVisible(visible);
        } else {
            this.setOrderOfMenu(this.INGAME_PROVINCE_RECRUIT_ARMY);
        }
        AoCGame.resetTypeNumber();
    }

    protected final MenuElement getInGame_ProvinceRecruitInstantly_Slider() {
        return this.menus.get(this.INGAME).get(this.INGAME_PROVINCE_RECRUIT_ARMY_INSTANTLY).getMenuElement(2);
    }

    protected final void setVisible_InGame_ProvinceRecruitInstantly(boolean visible) {
        this.menus.get(this.INGAME).get(this.INGAME_PROVINCE_RECRUIT_ARMY_INSTANTLY).setVisible(visible);
        if (!visible) {
            this.menus.get(this.INGAME).get(this.INGAME_ACTION_INFO_CHOOSE_PROVINCE).setVisible(visible);
        } else {
            this.setOrderOfMenu(this.INGAME_PROVINCE_RECRUIT_ARMY);
        }
        AoCGame.resetTypeNumber();
    }

    protected final MenuElement getInGame_ProvinceDisband_Slider() {
        return this.menus.get(this.INGAME).get(this.INGAME_PROVINCE_DISBAND_ARMY).getMenuElement(2);
    }

    protected final MenuElement getInGame_RegroupArmy_Slider() {
        return this.menus.get(this.INGAME).get(this.INGAME_PROVINCE_REGROUP_ARMY).getMenuElement(2);
    }

    protected final boolean getVisible_InGame_ProvinceRegroupArmy() {
        return this.menus.get(this.INGAME).get(this.INGAME_PROVINCE_REGROUP_ARMY).getVisible();
    }

    protected final void setVisible_InGame_ProvinceDisband(boolean visible) {
        this.menus.get(this.INGAME).get(this.INGAME_PROVINCE_DISBAND_ARMY).setVisible(visible);
        if (!visible) {
            this.menus.get(this.INGAME).get(this.INGAME_ACTION_INFO_CHOOSE_PROVINCE).setVisible(visible);
        } else {
            this.setOrderOfMenu(this.INGAME_PROVINCE_DISBAND_ARMY);
        }
        AoCGame.resetTypeNumber();
    }

    protected final void setVisible_InGame_ProvinceRegroupArmy(boolean visible) {
        this.menus.get(this.INGAME).get(this.INGAME_PROVINCE_REGROUP_ARMY).setVisible(visible);
        if (!visible) {
            this.menus.get(this.INGAME).get(this.INGAME_ACTION_INFO_CHOOSE_PROVINCE).setVisible(visible);
        } else {
            this.setOrderOfMenu(this.INGAME_PROVINCE_REGROUP_ARMY);
        }
        AoCGame.resetTypeNumber();
    }

    protected final void setCreate_Scenario_Assign_UndoButton(boolean isClickable) {
        this.menus.get(this.CREATE_SCENARIO_ASSIGN).get(0).getMenuElement(6).setClickable(isClickable);
    }

    protected final void disposeFlagsCreate_Scenario_Assign() {
    }

    protected final void setCreate_Scenario_AvailableProvinces_UndoButton(boolean isClickable) {
        this.menus.get(this.CREATE_SCENARIO_AVAILABLE_PROVINCES).get(0).getMenuElement(6).setClickable(isClickable);
    }

    protected final void setMapEditor_WastelandMaps_Edit_UndoButton(boolean isClickable) {
        this.menus.get(this.MAP_EDITOR_WASTELAND_MAPS_EDIT).get(0).getMenuElement(5).setClickable(isClickable);
    }

    protected final void setVisible_InGame_ProvinceMoveUnits(boolean visible) {
        this.menus.get(this.INGAME).get(this.INGAME_PROVINCE_MOVE_UNITS).setVisible(visible);
        if (visible) {
            this.setOrderOfMenu(this.INGAME_PROVINCE_MOVE_UNITS);
        }
        AoCGame.resetTypeNumber();
    }

    protected final void setVisible_InGame_View_HideViews() {
        if (this.getVisible_InGame_CivInfo()) {
            this.setVisible_InGame_CivInfo(false);
        }
        if (this.getInGame_ProvinceBuild_Visible()) {
            this.setVisible_InGame_ProvinceBuild(false, false);
        }
    }

    protected final void setVisible_InGame_View(boolean visible) {
        if (visible) {
            int tElementsBefore = this.menus.get(this.INGAME).get(this.INGAME_VIEW_STATS).getMenuElementsSize();
            int tY = this.menus.get(this.INGAME).get(this.INGAME_VIEW_STATS).getMenuPosY();
            this.menus.get(this.INGAME).set(this.INGAME_VIEW_STATS, new Menu_InGame_View_Population());
            this.menus.get(this.INGAME).get(this.INGAME_VIEW_STATS).setVisible(visible);
            if (tElementsBefore == this.menus.get(this.INGAME).get(this.INGAME_VIEW_STATS).getMenuElementsSize()) {
                this.menus.get(this.INGAME).get(this.INGAME_VIEW_STATS).setMenuPosY(tY);
            }
            this.setVisible_InGame_View_HideViews();
        } else {
            this.menus.get(this.INGAME).get(this.INGAME_VIEW_STATS).setVisible(visible);
        }
        if (visible) {
            this.setOrderOfMenu(this.INGAME_VIEW_STATS);
        }
    }

    protected final void setVisible_InGame_View_ProvinceValue(boolean visible) {
        if (visible) {
            int tElementsBefore = this.menus.get(this.INGAME).get(this.INGAME_VIEW_STATS).getMenuElementsSize();
            int tY = this.menus.get(this.INGAME).get(this.INGAME_VIEW_STATS).getMenuPosY();
            this.menus.get(this.INGAME).set(this.INGAME_VIEW_STATS, new Menu_InGame_View_ProvinceValue());
            this.menus.get(this.INGAME).get(this.INGAME_VIEW_STATS).setVisible(visible);
            if (tElementsBefore == this.menus.get(this.INGAME).get(this.INGAME_VIEW_STATS).getMenuElementsSize()) {
                this.menus.get(this.INGAME).get(this.INGAME_VIEW_STATS).setMenuPosY(tY);
            }
            this.setVisible_InGame_View_HideViews();
        } else {
            this.menus.get(this.INGAME).get(this.INGAME_VIEW_STATS).setVisible(visible);
        }
        if (visible) {
            this.setOrderOfMenu(this.INGAME_VIEW_STATS);
        }
    }

    protected final void setVisible_InGame_View_Buildings(boolean visible) {
        if (visible) {
            int tElementsBefore = this.menus.get(this.INGAME).get(this.INGAME_VIEW_STATS).getMenuElementsSize();
            int tY = this.menus.get(this.INGAME).get(this.INGAME_VIEW_STATS).getMenuPosY();
            this.menus.get(this.INGAME).set(this.INGAME_VIEW_STATS, new Menu_InGame_View_Buildings());
            this.menus.get(this.INGAME).get(this.INGAME_VIEW_STATS).setVisible(visible);
            if (tElementsBefore == this.menus.get(this.INGAME).get(this.INGAME_VIEW_STATS).getMenuElementsSize()) {
                this.menus.get(this.INGAME).get(this.INGAME_VIEW_STATS).setMenuPosY(tY);
            }
            this.setVisible_InGame_View_HideViews();
        } else {
            this.menus.get(this.INGAME).get(this.INGAME_VIEW_STATS).setVisible(visible);
        }
        if (visible) {
            this.setOrderOfMenu(this.INGAME_VIEW_STATS);
        }
    }

    protected final void setVisible_InGame_View_Diseases(boolean visible) {
        if (visible) {
            int tElementsBefore = this.menus.get(this.INGAME).get(this.INGAME_VIEW_STATS).getMenuElementsSize();
            int tY = this.menus.get(this.INGAME).get(this.INGAME_VIEW_STATS).getMenuPosY();
            this.menus.get(this.INGAME).set(this.INGAME_VIEW_STATS, new Menu_InGame_View_Diseases());
            this.menus.get(this.INGAME).get(this.INGAME_VIEW_STATS).setVisible(visible);
            if (tElementsBefore == this.menus.get(this.INGAME).get(this.INGAME_VIEW_STATS).getMenuElementsSize()) {
                this.menus.get(this.INGAME).get(this.INGAME_VIEW_STATS).setMenuPosY(tY);
            }
            this.setVisible_InGame_View_HideViews();
        } else {
            this.menus.get(this.INGAME).get(this.INGAME_VIEW_STATS).setVisible(visible);
        }
        if (visible) {
            this.setOrderOfMenu(this.INGAME_VIEW_STATS);
        }
    }

    protected final void setVisible_InGame_ViewProvinceStability(boolean visible) {
        if (visible) {
            int tElementsBefore = this.menus.get(this.INGAME).get(this.INGAME_VIEW_STATS).getMenuElementsSize();
            int tY = this.menus.get(this.INGAME).get(this.INGAME_VIEW_STATS).getMenuPosY();
            this.menus.get(this.INGAME).set(this.INGAME_VIEW_STATS, new Menu_InGame_View_ProvinceStability());
            this.menus.get(this.INGAME).get(this.INGAME_VIEW_STATS).setVisible(visible);
            if (tElementsBefore == this.menus.get(this.INGAME).get(this.INGAME_VIEW_STATS).getMenuElementsSize()) {
                this.menus.get(this.INGAME).get(this.INGAME_VIEW_STATS).setMenuPosY(tY);
            }
            this.setVisible_InGame_View_HideViews();
        } else {
            this.menus.get(this.INGAME).get(this.INGAME_VIEW_STATS).setVisible(visible);
        }
        if (visible) {
            this.setOrderOfMenu(this.INGAME_VIEW_STATS);
        }
    }

    protected final void setVisible_InGame_ViewEconomy(boolean visible) {
        if (visible) {
            int tElementsBefore = this.menus.get(this.INGAME).get(this.INGAME_VIEW_STATS).getMenuElementsSize();
            int tY = this.menus.get(this.INGAME).get(this.INGAME_VIEW_STATS).getMenuPosY();
            this.menus.get(this.INGAME).set(this.INGAME_VIEW_STATS, new Menu_InGame_View_Economy());
            this.menus.get(this.INGAME).get(this.INGAME_VIEW_STATS).setVisible(visible);
            if (tElementsBefore == this.menus.get(this.INGAME).get(this.INGAME_VIEW_STATS).getMenuElementsSize()) {
                this.menus.get(this.INGAME).get(this.INGAME_VIEW_STATS).setMenuPosY(tY);
            }
            this.setVisible_InGame_View_HideViews();
        } else {
            this.menus.get(this.INGAME).get(this.INGAME_VIEW_STATS).setVisible(visible);
        }
        if (visible) {
            this.setOrderOfMenu(this.INGAME_VIEW_STATS);
        }
    }

    protected final void setVisible_InGame_ViewArmy(boolean visible) {
        if (visible) {
            this.menus.get(this.INGAME).set(this.INGAME_VIEW_STATS, new Menu_InGame_View_Army());
            this.menus.get(this.INGAME).get(this.INGAME_VIEW_STATS).setVisible(visible);
            this.setVisible_InGame_View_HideViews();
        } else {
            this.menus.get(this.INGAME).get(this.INGAME_VIEW_STATS).setVisible(visible);
        }
        if (visible) {
            this.setOrderOfMenu(this.INGAME_VIEW_STATS);
        }
    }

    protected final void setVisible_InGame_ViewTerrain(boolean visible) {
        if (visible) {
            this.menus.get(this.INGAME).set(this.INGAME_VIEW_STATS, new Menu_InGame_View_Terrain());
            this.menus.get(this.INGAME).get(this.INGAME_VIEW_STATS).setVisible(visible);
            this.setVisible_InGame_View_HideViews();
        } else {
            this.menus.get(this.INGAME).get(this.INGAME_VIEW_STATS).setVisible(visible);
        }
        if (visible) {
            this.setOrderOfMenu(this.INGAME_VIEW_STATS);
        }
    }

    protected final void setVisible_InGame_ViewTechnology(boolean visible, boolean allCivs) {
        if (visible) {
            int tElementsBefore = this.menus.get(this.INGAME).get(this.INGAME_VIEW_STATS).getMenuElementsSize();
            int tY = this.menus.get(this.INGAME).get(this.INGAME_VIEW_STATS).getMenuPosY();
            this.menus.get(this.INGAME).set(this.INGAME_VIEW_STATS, new Menu_InGame_View_Technology(allCivs));
            this.menus.get(this.INGAME).get(this.INGAME_VIEW_STATS).setVisible(visible);
            if (tElementsBefore == this.menus.get(this.INGAME).get(this.INGAME_VIEW_STATS).getMenuElementsSize()) {
                this.menus.get(this.INGAME).get(this.INGAME_VIEW_STATS).setMenuPosY(tY);
            }
            this.setVisible_InGame_View_HideViews();
        } else {
            this.menus.get(this.INGAME).get(this.INGAME_VIEW_STATS).setVisible(visible);
        }
        if (visible) {
            this.setOrderOfMenu(this.INGAME_VIEW_STATS);
        }
    }

    protected final void setVisible_InGame_ViewGovernments(boolean visible) {
        if (visible) {
            int tElementsBefore = this.menus.get(this.INGAME).get(this.INGAME_VIEW_STATS).getMenuElementsSize();
            int tY = this.menus.get(this.INGAME).get(this.INGAME_VIEW_STATS).getMenuPosY();
            this.menus.get(this.INGAME).set(this.INGAME_VIEW_STATS, new Menu_InGame_View_Goverments());
            this.menus.get(this.INGAME).get(this.INGAME_VIEW_STATS).setVisible(visible);
            if (tElementsBefore == this.menus.get(this.INGAME).get(this.INGAME_VIEW_STATS).getMenuElementsSize()) {
                this.menus.get(this.INGAME).get(this.INGAME_VIEW_STATS).setMenuPosY(tY);
            }
            this.setVisible_InGame_View_HideViews();
        } else {
            this.menus.get(this.INGAME).get(this.INGAME_VIEW_STATS).setVisible(visible);
        }
        if (visible) {
            this.setOrderOfMenu(this.INGAME_VIEW_STATS);
        }
    }

    protected final void setVisible_InGame_ViewGrowthRate(boolean visible) {
        if (visible) {
            int tElementsBefore = this.menus.get(this.INGAME).get(this.INGAME_VIEW_STATS).getMenuElementsSize();
            int tY = this.menus.get(this.INGAME).get(this.INGAME_VIEW_STATS).getMenuPosY();
            this.menus.get(this.INGAME).set(this.INGAME_VIEW_STATS, new Menu_InGame_View_GrowthRate());
            this.menus.get(this.INGAME).get(this.INGAME_VIEW_STATS).setVisible(visible);
            if (tElementsBefore == this.menus.get(this.INGAME).get(this.INGAME_VIEW_STATS).getMenuElementsSize()) {
                this.menus.get(this.INGAME).get(this.INGAME_VIEW_STATS).setMenuPosY(tY);
            }
            this.setVisible_InGame_View_HideViews();
        } else {
            this.menus.get(this.INGAME).get(this.INGAME_VIEW_STATS).setVisible(visible);
        }
        if (visible) {
            this.setOrderOfMenu(this.INGAME_VIEW_STATS);
        }
    }

    protected final void setVisible_InGame_ViewDevelopment(boolean visible) {
        if (visible) {
            int tElementsBefore = this.menus.get(this.INGAME).get(this.INGAME_VIEW_STATS).getMenuElementsSize();
            int tY = this.menus.get(this.INGAME).get(this.INGAME_VIEW_STATS).getMenuPosY();
            this.menus.get(this.INGAME).set(this.INGAME_VIEW_STATS, new Menu_InGame_View_Development());
            this.menus.get(this.INGAME).get(this.INGAME_VIEW_STATS).setVisible(visible);
            if (tElementsBefore == this.menus.get(this.INGAME).get(this.INGAME_VIEW_STATS).getMenuElementsSize()) {
                this.menus.get(this.INGAME).get(this.INGAME_VIEW_STATS).setMenuPosY(tY);
            }
            this.setVisible_InGame_View_HideViews();
        } else {
            this.menus.get(this.INGAME).get(this.INGAME_VIEW_STATS).setVisible(visible);
        }
        if (visible) {
            this.setOrderOfMenu(this.INGAME_VIEW_STATS);
        }
    }

    protected final void setVisible_InGame_ViewHappiness(boolean visible) {
        if (visible) {
            int tElementsBefore = this.menus.get(this.INGAME).get(this.INGAME_VIEW_STATS).getMenuElementsSize();
            int tY = this.menus.get(this.INGAME).get(this.INGAME_VIEW_STATS).getMenuPosY();
            this.menus.get(this.INGAME).set(this.INGAME_VIEW_STATS, new Menu_InGame_View_Happiness());
            this.menus.get(this.INGAME).get(this.INGAME_VIEW_STATS).setVisible(visible);
            if (tElementsBefore == this.menus.get(this.INGAME).get(this.INGAME_VIEW_STATS).getMenuElementsSize()) {
                this.menus.get(this.INGAME).get(this.INGAME_VIEW_STATS).setMenuPosY(tY);
            }
            this.setVisible_InGame_View_HideViews();
        } else {
            this.menus.get(this.INGAME).get(this.INGAME_VIEW_STATS).setVisible(visible);
        }
        if (visible) {
            this.setOrderOfMenu(this.INGAME_VIEW_STATS);
        }
    }

    protected final void setVisible_InGame_ViewRecruitable(boolean visible) {
        if (visible) {
            int tElementsBefore = this.menus.get(this.INGAME).get(this.INGAME_VIEW_STATS).getMenuElementsSize();
            int tY = this.menus.get(this.INGAME).get(this.INGAME_VIEW_STATS).getMenuPosY();
            this.menus.get(this.INGAME).set(this.INGAME_VIEW_STATS, new Menu_InGame_View_Recruitable());
            this.menus.get(this.INGAME).get(this.INGAME_VIEW_STATS).setVisible(visible);
            if (tElementsBefore == this.menus.get(this.INGAME).get(this.INGAME_VIEW_STATS).getMenuElementsSize()) {
                this.menus.get(this.INGAME).get(this.INGAME_VIEW_STATS).setMenuPosY(tY);
            }
            this.setVisible_InGame_View_HideViews();
        } else {
            this.menus.get(this.INGAME).get(this.INGAME_VIEW_STATS).setVisible(visible);
        }
        if (visible) {
            this.setOrderOfMenu(this.INGAME_VIEW_STATS);
        }
    }

    protected final void setVisible_InGame_ViewUnrest(boolean visible) {
        if (visible) {
            this.menus.get(this.INGAME).set(this.INGAME_VIEW_STATS, new Menu_InGame_View_Unrest());
            this.menus.get(this.INGAME).get(this.INGAME_VIEW_STATS).setVisible(visible);
            this.setVisible_InGame_View_HideViews();
        } else {
            this.menus.get(this.INGAME).get(this.INGAME_VIEW_STATS).setVisible(visible);
        }
        if (visible) {
            this.setOrderOfMenu(this.INGAME_VIEW_STATS);
        }
    }

    protected final void setVisible_InGame_ViewIncome(boolean visible) {
        if (visible) {
            this.menus.get(this.INGAME).set(this.INGAME_VIEW_STATS, new Menu_InGame_View_Income());
            this.menus.get(this.INGAME).get(this.INGAME_VIEW_STATS).setVisible(visible);
            this.setVisible_InGame_View_HideViews();
        } else {
            this.menus.get(this.INGAME).get(this.INGAME_VIEW_STATS).setVisible(visible);
        }
        if (visible) {
            this.setOrderOfMenu(this.INGAME_VIEW_STATS);
        }
    }

    protected final void setVisible_InGame_ProvinceBuild(boolean visible, boolean savePosY) {
        try {
            if (visible && BuildingsManager.iBuildInProvinceID >= 0 && CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getCivID() == CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()) {
                int tY = this.menus.get(this.INGAME).get(this.INGAME_PROVINCE_BUILD).getMenuPosY();
                this.menus.get(this.INGAME).set(this.INGAME_PROVINCE_BUILD, new Menu_InGame_Build());
                this.menus.get(this.INGAME).get(this.INGAME_PROVINCE_BUILD).setVisible(visible);
                if (savePosY) {
                    this.menus.get(this.INGAME).get(this.INGAME_PROVINCE_BUILD).setMenuPosY(tY);
                }
                if (this.getVisible_InGame_CivInfo()) {
                    this.setVisible_InGame_CivInfo(false);
                }
                if (this.getVisible_InGame_View_Stats()) {
                    this.setVisible_InGame_View(false);
                }
            } else {
                this.menus.get(this.INGAME).get(this.INGAME_PROVINCE_BUILD).setVisible(visible);
            }
            if (visible) {
                this.setOrderOfMenu(this.INGAME_PROVINCE_BUILD);
            }
        }
        catch (IndexOutOfBoundsException ex) {
            this.menus.get(this.INGAME).get(this.INGAME_PROVINCE_BUILD).setVisible(visible);
        }
    }

    protected final void rebuildInGameProvinceBuildConfirm() {
        int tPosX = this.menus.get(this.INGAME).get(this.INGAME_PROVINCE_BUILD_CONFIRM).getPosX();
        int tPosY = this.menus.get(this.INGAME).get(this.INGAME_PROVINCE_BUILD_CONFIRM).getPosY();
        this.menus.get(this.INGAME).set(this.INGAME_PROVINCE_BUILD_CONFIRM, new Menu_InGame_BuildConfirm());
        this.menus.get(this.INGAME).get(this.INGAME_PROVINCE_BUILD_CONFIRM).setVisible(true);
        this.menus.get(this.INGAME).get(this.INGAME_PROVINCE_BUILD_CONFIRM).setPosX(tPosX);
        this.menus.get(this.INGAME).get(this.INGAME_PROVINCE_BUILD_CONFIRM).setPosY(tPosY);
        this.setOrderOfMenu(this.INGAME_PROVINCE_BUILD_CONFIRM);
    }

    protected final boolean getInGame_ProvinceBuild_Visible() {
        return this.menus.get(this.INGAME).get(this.INGAME_PROVINCE_BUILD).getVisible();
    }

    protected final boolean getVisible_InGame_View_Stats() {
        return this.menus.get(this.INGAME).get(this.INGAME_VIEW_STATS).getVisible();
    }

    protected final void setVisible_InGame_View_Stats() {
        this.menus.get(this.INGAME).get(this.INGAME_VIEW_STATS).setVisible(false);
    }

    protected final boolean getInGame_ProvinceBuildConfirm_Visible() {
        return this.menus.get(this.INGAME).get(this.INGAME_PROVINCE_BUILD_CONFIRM).getVisible();
    }

    protected final void setVisible_InGame_ProvinceBuildConfirm(boolean visible) {
        this.menus.get(this.INGAME).get(this.INGAME_PROVINCE_BUILD_CONFIRM).setVisible(visible);
    }

    protected final void setVisible_CreateScenario_Civilizations_Suggest(boolean visible) {
        this.menus.get(this.CREATE_SCENARIO_CIVILIZATIONS).get(this.CREATE_SCENARIO_CIVILIZATIONS_SUGGEST).setVisible(visible);
    }

    protected final void setVisible_CreateScenario_Civilizations_Ideologies(boolean visible) {
        this.menus.get(this.CREATE_SCENARIO_CIVILIZATIONS).get(this.CREATE_SCENARIO_CIVILIZATIONS_IDEOLOGIES).setVisible(visible);
    }

    protected final void setVisible_InGame_ActionInfo(boolean visible) {
        this.menus.get(this.INGAME).get(this.INGAME_ACTION_INFO_CHOOSE_PROVINCE).setVisible(visible);
        if (visible) {
            this.setOrderOfMenu(this.INGAME_ACTION_INFO_CHOOSE_PROVINCE);
        }
    }

    protected final void setVisible_InGame_ActionInfo_ChooseProvince() {
        CFG.fMOVE_MENU_PERCENTAGE = 100.0f;
        this.menus.get(this.INGAME).set(this.INGAME_ACTION_INFO_CHOOSE_PROVINCE, new Menu_InGame_ActionInfo());
        this.menus.get(this.INGAME).get(this.INGAME_ACTION_INFO_CHOOSE_PROVINCE).setVisible(true);
        this.setOrderOfMenu(this.INGAME_ACTION_INFO_CHOOSE_PROVINCE);
    }

    protected final void setVisible_InGame_ActionInfo_Move() {
        this.menus.get(this.INGAME).set(this.INGAME_ACTION_INFO_CHOOSE_PROVINCE, new Menu_InGame_ActionInfo_Move());
        this.menus.get(this.INGAME).get(this.INGAME_ACTION_INFO_CHOOSE_PROVINCE).setVisible(true);
        CFG.menuManager.updateInGame_ActionInfo_Move();
        this.setOrderOfMenu(this.INGAME_ACTION_INFO_CHOOSE_PROVINCE);
    }

    protected final void setVisible_InGame_ActionInfo_RegroupArmy_Move() {
        this.menus.get(this.INGAME).set(this.INGAME_ACTION_INFO_CHOOSE_PROVINCE, new Menu_InGame_ActionInfo_RegroupArmy_Move());
        this.menus.get(this.INGAME).get(this.INGAME_ACTION_INFO_CHOOSE_PROVINCE).setVisible(true);
        CFG.menuManager.updateInGame_ActionInfo_Regroup();
        this.setOrderOfMenu(this.INGAME_ACTION_INFO_CHOOSE_PROVINCE);
    }

    protected final void setVisible_InGame_ActionInfo_RegroupArmy() {
        this.menus.get(this.INGAME).set(this.INGAME_ACTION_INFO_CHOOSE_PROVINCE, new Menu_InGame_ActionInfo_RegroupArmy());
        this.menus.get(this.INGAME).get(this.INGAME_ACTION_INFO_CHOOSE_PROVINCE).setVisible(true);
        this.setOrderOfMenu(this.INGAME_ACTION_INFO_CHOOSE_PROVINCE);
    }

    protected final void setVisible_InGame_ActionInfo_Recruit() {
        this.menus.get(this.INGAME).set(this.INGAME_ACTION_INFO_CHOOSE_PROVINCE, new Menu_InGame_ActionInfo_Recruit());
        this.menus.get(this.INGAME).get(this.INGAME_ACTION_INFO_CHOOSE_PROVINCE).setVisible(true);
        CFG.menuManager.updateInGame_ActionInfo_Recruit();
        this.setOrderOfMenu(this.INGAME_ACTION_INFO_CHOOSE_PROVINCE);
    }

    protected final SliderMenu getInGame_ActionInfo_Province() {
        return this.menus.get(this.INGAME).get(this.INGAME_ACTION_INFO_CHOOSE_PROVINCE);
    }

    protected final void setVisible_InGame_ActionInfo_RecruitInstantly() {
        this.menus.get(this.INGAME).set(this.INGAME_ACTION_INFO_CHOOSE_PROVINCE, new Menu_InGame_ActionInfo_RecruitInstantly());
        this.menus.get(this.INGAME).get(this.INGAME_ACTION_INFO_CHOOSE_PROVINCE).setVisible(true);
        CFG.menuManager.updateInGame_ActionInfo_RecruitInstantly();
        this.setOrderOfMenu(this.INGAME_ACTION_INFO_CHOOSE_PROVINCE);
    }

    protected final void setVisible_InGame_ActionInfo_TreasuryIsEmpty() {
        CFG.fMOVE_MENU_PERCENTAGE = 100.0f;
        this.menus.get(this.INGAME).set(this.INGAME_ACTION_INFO_CHOOSE_PROVINCE, new Menu_InGame_ActionInfo_TreasuryIsEmpty());
        this.menus.get(this.INGAME).get(this.INGAME_ACTION_INFO_CHOOSE_PROVINCE).setVisible(true);
        this.setOrderOfMenu(this.INGAME_ACTION_INFO_CHOOSE_PROVINCE);
    }

    protected final void setVisible_InGame_ActionInfo_NoMovementPoints() {
        CFG.fMOVE_MENU_PERCENTAGE = 100.0f;
        this.menus.get(this.INGAME).set(this.INGAME_ACTION_INFO_CHOOSE_PROVINCE, new Menu_InGame_ActionInfo_NoMovementPoints());
        this.menus.get(this.INGAME).get(this.INGAME_ACTION_INFO_CHOOSE_PROVINCE).setVisible(true);
        this.setOrderOfMenu(this.INGAME_ACTION_INFO_CHOOSE_PROVINCE);
    }

    protected final void setVisible_InGame_ActionInfo_RecruitOccupied() {
        CFG.fMOVE_MENU_PERCENTAGE = 100.0f;
        this.menus.get(this.INGAME).set(this.INGAME_ACTION_INFO_CHOOSE_PROVINCE, new Menu_InGame_ActionInfo_RecruitOccupied());
        this.menus.get(this.INGAME).get(this.INGAME_ACTION_INFO_CHOOSE_PROVINCE).setVisible(true);
        this.setOrderOfMenu(this.INGAME_ACTION_INFO_CHOOSE_PROVINCE);
    }

    protected final void setVisible_InGame_ActionInfo_NoUnits() {
        CFG.fMOVE_MENU_PERCENTAGE = 100.0f;
        this.menus.get(this.INGAME).set(this.INGAME_ACTION_INFO_CHOOSE_PROVINCE, new Menu_InGame_ActionInfo_NoUnits());
        this.menus.get(this.INGAME).get(this.INGAME_ACTION_INFO_CHOOSE_PROVINCE).setVisible(true);
        this.setOrderOfMenu(this.INGAME_ACTION_INFO_CHOOSE_PROVINCE);
    }

    protected final void setVisible_InGame_ActionInfo_Disband() {
        this.menus.get(this.INGAME).set(this.INGAME_ACTION_INFO_CHOOSE_PROVINCE, new Menu_InGame_ActionInfo_Disband());
        this.menus.get(this.INGAME).get(this.INGAME_ACTION_INFO_CHOOSE_PROVINCE).setVisible(true);
        CFG.menuManager.updateInGame_ActionInfo_Disband();
        this.setOrderOfMenu(this.INGAME_ACTION_INFO_CHOOSE_PROVINCE);
    }

    protected final SliderMenu getEditor_GameCivs_Edit() {
        return this.menus.get(this.EDITOR_GAME_CIVS_EDIT).get(0);
    }

    protected final void getCreateNewGame_CivInfo_updateLanguage() {
        try {
            this.menus.get(this.CREATE_NEW_GAME).get(this.CREATE_NEW_GAME_CIV_INFO).updateLanguage();
            this.menus.get(this.CREATE_NEW_GAME).get(this.CREATE_NEW_GAME_CIV_INFO_STATS).updateLanguage();
        }
        catch (IndexOutOfBoundsException indexOutOfBoundsException) {
        }
        catch (NullPointerException nullPointerException) {
            // empty catch block
        }
    }

    protected final SliderMenu getCreateScenario_SetUpArmy() {
        return this.menus.get(this.CREATE_SCENARIO_SET_UP_ARMY).get(0);
    }

    protected final SliderMenu getSelectAvailableCivilizations() {
        return this.menus.get(this.SELECT_AVAILABLE_CIVILIZATIONS).get(0);
    }

    protected final SliderMenu getCreateScenario_Civilizations() {
        return this.menus.get(this.CREATE_SCENARIO_CIVILIZATIONS).get(0);
    }

    protected final SliderMenu getCreateScenario_WastelandContinents() {
        return this.menus.get(this.CREATE_SCENARIO_WASTELAND).get(2);
    }

    protected final ColorPicker_AoC getColorPicker() {
        return this.colorPicker;
    }

    protected final Drag_Civilization getDrawCivilization() {
        return this.dragCivilization;
    }

    protected final void setVisible_InGame_Options(boolean visible) {
        this.menus.get(this.INGAME).get(this.INGAME_OPTIONS).setVisible(visible);
        if (visible) {
            this.setOrderOfMenu(this.INGAME_OPTIONS);
        }
        CFG.map.getMapBG().updateWorldMap_Shaders();
    }

    protected final void setVisible_InGame_EndOfGame(boolean visible) {
        if (visible) {
            this.menus.get(this.INGAME).set(this.INGAME_ENDOFGAME, new Menu_InGame_EndOfGame());
        }
        this.menus.get(this.INGAME).get(this.INGAME_ENDOFGAME).setVisible(visible);
        if (visible) {
            this.setOrderOfMenu(this.INGAME_ENDOFGAME);
        }
        CFG.map.getMapBG().updateWorldMap_Shaders();
    }

    protected final int getFromViewID() {
        return this.fromViewID;
    }

    static interface SlideMap_ActionMove {
        public void update_ActionMove_SlideMap();
    }

    static interface BuildProvinceHover_Informations {
        public void build();
    }
}
