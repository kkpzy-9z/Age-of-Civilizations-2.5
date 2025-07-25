package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

class SoundsManager {
   //private static final String CONSOLE_MUSIC_TITLE = "Scheming_Weasel." + (CFG.isIOS() ? "mp3" : "ogg");
   protected static boolean isPlayingConsoleMusic = false;
   protected static String START_MUSIC = "";
   private static final String DEFEAT_MUSIC = "Sudden_Defeat." + (CFG.isIOS() ? "mp3" : "ogg");
   private List<String> lTitles;
   private Music currentMusic = null;
   private float musicVolume = 0.4f;
   private int iCurrentMusicID = 0;
   private List<Sound> lSounds;
   private float soundsVolume = 0.55F;
   private float masterVolume = 1.0F;
   protected static int SOUND_CLICK;
   protected static int SOUND_CLICK2;
   protected static int SOUND_CLICK3;
   protected static int SOUND_PROVINCE;
   protected static int SOUND_ACTION_MOVE;
   protected static int SOUND_MOVE_ARMY;
   protected static int SOUND_MOVE_ARMY2;
   protected static int SOUND_MOVE_REGROUP;
   protected static int SOUND_INVADE;
   protected static int SOUND_RECRUIT;
   protected static int SOUND_GOLD;
   protected static int SOUND_DIPLOMACY;
   protected static int SOUND_TECHNOLOGY;
   protected static int SOUND_WAR;
   protected static int SOUND_WAR2;
   protected static int SOUND_BUILD;
   protected static int SOUND_PLUNDER;
   protected static int SOUND_CROW;
   protected static int SOUND_SEND;
   protected static int SOUND_SEND2;
   protected static int SOUND_SEND3;
   protected static int SOUND_SEND4;
   protected static int SOUND_ASSIMILATE;
   protected static int SOUND_WORKSHOP;
   protected static int SOUND_FARM;
   protected static int SOUND_PORT;
   protected static int SOUND_SUPPLY;
   protected static int SOUND_LIBRARY;
   protected static int SOUND_RANDOM;
   protected static int SOUND_COINS;
   protected static float PERC_VOLUME_SELECT_PROVINCE = 0.95F;
   protected static float PERC_VOLUME_KEYBOARD = 0.9F;
   private final Random oR = new Random();

   protected SoundsManager() {
      //stop auto-sound play
      this.masterVolume = CFG.settingsManager.VOLUME_MASTER;
      this.setSoundsVolume(CFG.settingsManager.VOLUME_SOUNDS);
      this.setMusicVolume(CFG.settingsManager.VOLUME_MUSIC);

      this.lTitles = new ArrayList<String>();
      this.lSounds = new ArrayList<Sound>();
      ArrayList menuElements = new ArrayList();
      SOUND_CLICK = this.addSound("click." + (CFG.isIOS() ? "mp3" : "ogg"));
      SOUND_CLICK2 = this.addSound("click2." + (CFG.isIOS() ? "mp3" : "ogg"));
      SOUND_CLICK3 = this.addSound("click3." + (CFG.isIOS() ? "mp3" : "ogg"));
      SOUND_PROVINCE = SOUND_CLICK;

      try {
         FileHandle tempFileT = Gdx.files.internal("music/Age_of_Civilizations");
         String tempT = tempFileT.readString();
         String[] tagsSPLITED = tempT.split(";");

         for(int i = 0; i < tagsSPLITED.length; ++i) {
            //if starts with start tag set to start music, remove start tag
            if (tagsSPLITED[i].startsWith("start:")) {
               tagsSPLITED[i] = tagsSPLITED[i].replaceAll("start:", "");
               if (Objects.equals(START_MUSIC, "")) {
                  START_MUSIC = tagsSPLITED[i];
                  Gdx.app.log("AoC2.5 StartMusic", tagsSPLITED[i]);
               }
            }

            this.lTitles.add(tagsSPLITED[i]);
         }
      } catch (GdxRuntimeException var8) {
         try {
            FileHandle tempFileT = Gdx.files.local("music/Age_of_Civilizations");
            String tempT = tempFileT.readString();
            String[] tagsSPLITED = tempT.split(";");

            for(int i = 0; i < tagsSPLITED.length; ++i) {
               //if starts with start tag set to start music, remove start tag
               tagsSPLITED[i] = tagsSPLITED[i].replaceAll("start:", "");
               if (Objects.equals(START_MUSIC, "")) {
                  START_MUSIC = tagsSPLITED[i];
                  Gdx.app.log("AoC2.5 StartMusic", tagsSPLITED[i]);
               }

               this.lTitles.add(tagsSPLITED[i]);
            }
         } catch (GdxRuntimeException var7) {
         }
      }

      //if no music, return
      if (this.lTitles.size() < 1) return;

      this.randomizePlayList();

      SOUND_MOVE_ARMY = this.addSound("move_army." + (CFG.isIOS() ? "mp3" : "ogg"));
      SOUND_MOVE_ARMY2 = this.addSound("move_army2." + (CFG.isIOS() ? "mp3" : "ogg"));
      SOUND_MOVE_REGROUP = this.addSound("move_army_re." + (CFG.isIOS() ? "mp3" : "ogg"));
      SOUND_GOLD = this.addSound("gold2." + (CFG.isIOS() ? "mp3" : "ogg"));
      SOUND_DIPLOMACY = this.addSound("diplomacy." + (CFG.isIOS() ? "mp3" : "ogg"));
      SOUND_TECHNOLOGY = this.addSound("technology." + (CFG.isIOS() ? "mp3" : "ogg"));
      SOUND_ACTION_MOVE = this.addSound("action_move." + (CFG.isIOS() ? "mp3" : "ogg"));
      SOUND_RECRUIT = this.addSound("metal." + (CFG.isIOS() ? "mp3" : "ogg"));
      SOUND_WAR = this.addSound("war." + (CFG.isIOS() ? "mp3" : "ogg"));
      SOUND_WAR2 = this.addSound("war2." + (CFG.isIOS() ? "mp3" : "ogg"));
      SOUND_BUILD = this.addSound("build." + (CFG.isIOS() ? "mp3" : "ogg"));
      SOUND_SEND = this.addSound("send." + (CFG.isIOS() ? "mp3" : "ogg"));
      SOUND_SEND2 = this.addSound("send2." + (CFG.isIOS() ? "mp3" : "ogg"));
      SOUND_SEND3 = this.addSound("send3." + (CFG.isIOS() ? "mp3" : "ogg"));
      SOUND_SEND4 = this.addSound("send4." + (CFG.isIOS() ? "mp3" : "ogg"));
      SOUND_PLUNDER = this.addSound("plunder." + (CFG.isIOS() ? "mp3" : "ogg"));
      SOUND_CROW = this.addSound("crow." + (CFG.isIOS() ? "mp3" : "ogg"));
      SOUND_ASSIMILATE = this.addSound("assimilate." + (CFG.isIOS() ? "mp3" : "ogg"));
      SOUND_WORKSHOP = this.addSound("workshop." + (CFG.isIOS() ? "mp3" : "ogg"));
      SOUND_FARM = this.addSound("farm." + (CFG.isIOS() ? "mp3" : "ogg"));
      SOUND_PORT = this.addSound("port." + (CFG.isIOS() ? "mp3" : "ogg"));
      SOUND_SUPPLY = this.addSound("supply." + (CFG.isIOS() ? "mp3" : "ogg"));
      SOUND_LIBRARY = this.addSound("library." + (CFG.isIOS() ? "mp3" : "ogg"));
      SOUND_RANDOM = this.addSound("random." + (CFG.isIOS() ? "mp3" : "ogg"));
      SOUND_COINS = this.addSound("coins." + (CFG.isIOS() ? "mp3" : "ogg"));
      this.masterVolume = CFG.settingsManager.VOLUME_MASTER;
      this.setSoundsVolume(CFG.settingsManager.VOLUME_SOUNDS);
      this.setMusicVolume(CFG.settingsManager.VOLUME_MUSIC);
   }

   protected final void randomizePlayList() {
      Random oR = new Random();
      List tempList = new ArrayList();

      int tempR;
      for(tempR = 0; tempR < this.lTitles.size(); ++tempR) {
         tempList.add((String)this.lTitles.get(tempR));
      }

      this.lTitles.clear();

      while(tempList.size() > 0) {
         tempR = oR.nextInt(tempList.size());
         this.lTitles.add((String)tempList.get(tempR));
         tempList.remove(tempR);
      }

   }

   protected final void loadNextMusic() {
      this.disposeCurrentMusic();
      ++this.iCurrentMusicID;
      if (this.iCurrentMusicID >= this.lTitles.size()) {
         this.iCurrentMusicID = 0;
         this.randomizePlayList();
      }

      try {
         this.currentMusic = Gdx.audio.newMusic(Gdx.files.internal("music/" + (String)this.lTitles.get(this.iCurrentMusicID)));
         this.currentMusic.setLooping(false);
         this.currentMusic.setVolume(this.musicVolume * this.masterVolume);
         this.currentMusic.play();
         this.currentMusic.setOnCompletionListener(new Music.OnCompletionListener() {
            public void onCompletion(Music music) {
               SoundsManager.this.loadNextMusic();
            }
         });
         isPlayingConsoleMusic = false;

         try {
            CFG.toast.setInView(this.getCurrentMusicTittle(), CFG.COLOR_TEXT_MODIFIER_NEUTRAL2);
            CFG.toast.setTimeInView(4500);
         } catch (NullPointerException var5) {
         }
      } catch (GdxRuntimeException var6) {
         try {
            this.currentMusic = Gdx.audio.newMusic(Gdx.files.local("music/" + (String)this.lTitles.get(this.iCurrentMusicID)));
            this.currentMusic.setLooping(false);
            this.currentMusic.setVolume(this.musicVolume * this.masterVolume);
            this.currentMusic.play();
            this.currentMusic.setOnCompletionListener(new Music.OnCompletionListener() {
               public void onCompletion(Music music) {
                  SoundsManager.this.loadNextMusic();
               }
            });
            isPlayingConsoleMusic = false;

            try {
               CFG.toast.setInView(this.getCurrentMusicTittle(), CFG.COLOR_TEXT_MODIFIER_NEUTRAL2);
               CFG.toast.setTimeInView(4500);
            } catch (NullPointerException var3) {
            }
         } catch (GdxRuntimeException var4) {
         }
      } catch (NullPointerException var7) {
      }

   }

   /*protected final void playConsoleMusic() {
      try {
         this.disposeCurrentMusic();
         this.currentMusic = Gdx.audio.newMusic(Gdx.files.internal("music/" + CONSOLE_MUSIC_TITLE));
         this.currentMusic.setLooping(false);
         this.currentMusic.play();
         this.currentMusic.setVolume(this.musicVolume * this.masterVolume);
         this.currentMusic.setOnCompletionListener(new Music.OnCompletionListener() {
            public void onCompletion(Music music) {
               SoundsManager.this.loadNextMusic();
            }
         });
         isPlayingConsoleMusic = true;
      } catch (NullPointerException var2) {
      } catch (GdxRuntimeException var3) {
      }
   }*/

   protected final void playStartMusic() {
      try {
         this.disposeCurrentMusic();
         this.currentMusic = Gdx.audio.newMusic(Gdx.files.internal("music/" + START_MUSIC));
         this.currentMusic.setLooping(false);
         this.currentMusic.setVolume(this.musicVolume * this.masterVolume);
         this.currentMusic.play();
         this.currentMusic.setOnCompletionListener(new Music.OnCompletionListener() {
            public void onCompletion(Music music) {
               SoundsManager.this.loadNextMusic();
            }
         });
      } catch (NullPointerException | GdxRuntimeException var2) {
         loadNextMusic();
      }
   }

    protected final void playEventMusic(String fileName) {
        this.playEventMusic(fileName, false);
    }

   protected final void playEventMusic(String fileName, boolean ignoreVolume) {
      try {
         Music loaded = Gdx.audio.newMusic(Gdx.files.internal(fileName));
         this.disposeCurrentMusic();
         this.currentMusic = loaded;
         this.currentMusic.setLooping(false);
         this.currentMusic.play();
         if (ignoreVolume) {
            this.currentMusic.setVolume(1.0F);
         } else {
             this.currentMusic.setVolume(this.musicVolume * this.masterVolume);
         }
         this.currentMusic.setOnCompletionListener(new Music.OnCompletionListener() {
            public void onCompletion(Music music) {
               SoundsManager.this.loadNextMusic();
            }
         });

         isPlayingConsoleMusic = false;
      } catch (NullPointerException | GdxRuntimeException var1) {
         try {
            Music loaded = Gdx.audio.newMusic(Gdx.files.local(fileName));
            this.disposeCurrentMusic();
            this.currentMusic = loaded;
            this.currentMusic.setLooping(false);
            this.currentMusic.play();
            if (ignoreVolume) {
               this.currentMusic.setVolume(1.0F);
            } else {
               this.currentMusic.setVolume(this.musicVolume * this.masterVolume);
            }
            this.currentMusic.setOnCompletionListener(new Music.OnCompletionListener() {
               public void onCompletion(Music music) {
                  SoundsManager.this.loadNextMusic();
               }
            });

            isPlayingConsoleMusic = false;
         } catch (NullPointerException | GdxRuntimeException var2) {
            if (CFG.LOGS) {
               CFG.exceptionStack(var2);
            }
         }
      }
   }

   protected final void disposeCurrentMusic() {
      if (this.currentMusic != null) {
         this.currentMusic.stop();
         this.currentMusic.dispose();
      }

   }

   protected final int addSound(String fileName) {
      try {
         this.lSounds.add(Gdx.audio.newSound(Gdx.files.internal("sounds/" + fileName)));
      } catch (GdxRuntimeException var5) {
         var5.printStackTrace();

         try {
            this.lSounds.add(Gdx.audio.newSound(Gdx.files.local("sounds/" + fileName)));
         } catch (GdxRuntimeException var4) {
            var5.printStackTrace();
         }
      }

      return this.lSounds.size() - 1;
   }

   protected final void playSound(int id) {
      this.playSound(id, 1.0F);
   }

   protected final void playSound(int id, float fPercOfVolume) {
      ((Sound)this.lSounds.get(id)).stop();
      ((Sound)this.lSounds.get(id)).play(this.soundsVolume * this.masterVolume * fPercOfVolume);
   }

   protected final int playMoveArmy() {
      int tID = this.oR.nextInt(174) % 2;
      switch (tID) {
         case 1:
            return SOUND_MOVE_ARMY2;
         default:
            return SOUND_MOVE_ARMY;
      }
   }

   protected final String getCurrentMusicTittle() {
      return ((String)this.lTitles.get(this.iCurrentMusicID)).substring(0, ((String)this.lTitles.get(this.iCurrentMusicID)).indexOf("." + (CFG.isIOS() ? "mp3" : "ogg")) > 0 ? ((String)this.lTitles.get(this.iCurrentMusicID)).indexOf("." + (CFG.isIOS() ? "mp3" : "ogg")) : ((String)this.lTitles.get(this.iCurrentMusicID)).length()).replace("_", " ");
   }

   protected final void setMusicVolume(float nMusicVolume) {
      this.musicVolume = nMusicVolume;

      try {
         this.currentMusic.setVolume(this.musicVolume * this.masterVolume);
         if (this.musicVolume < 0.01F) {
            this.currentMusic.pause();
         } else if (!this.currentMusic.isPlaying()) {
            this.currentMusic.play();
         }
      } catch (NullPointerException var3) {
      }

   }

   protected final float getMusicVolume() {
      return this.musicVolume;
   }

   protected final void setSoundsVolume(float soundsVolume) {
      this.soundsVolume = soundsVolume;
   }

   protected final float getSoundsVolume() {
      return this.soundsVolume;
   }

   protected final void setMasterVolume(float masterVolume) {
      this.masterVolume = masterVolume;
      this.setMusicVolume(this.getMusicVolume());
   }

   protected final float getMasterVolume() {
      return this.masterVolume;
   }

   protected final void dispose() {
      for(int i = 0; i < this.lSounds.size(); ++i) {
         ((Sound)this.lSounds.get(i)).dispose();
      }

      this.currentMusic.dispose();
   }

   protected static final int getSend() {
      switch (CFG.oR.nextInt(4)) {
         case 0:
            return SOUND_SEND;
         case 1:
            return SOUND_SEND2;
         case 2:
            return SOUND_SEND3;
         default:
            return SOUND_SEND4;
      }
   }
}
