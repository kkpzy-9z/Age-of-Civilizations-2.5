package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.I18NBundleLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.I18NBundle;
import org.omg.SendingContext.RunTime;

import java.util.Locale;
import java.util.MissingResourceException;

class LanguageManager {
   protected static boolean translationsKeysMode = false;
   private FileHandle fileHandle;
   private Locale locale;
   private I18NBundle bundle;
   private FileHandle fileHandleCivs;
   private Locale localeCivs;
   private I18NBundle bundleCivs;
   private FileHandle fileHandleLoading;
   private FileHandle fileHandleExtended;
   private Locale localeLoading;
   private I18NBundle bundleLoading;
   protected int iLoading_NumOfTexts = 0;
   private KeyOutput keyOutput;

   protected final void updateKeyOutput() {
      if (translationsKeysMode) {
         this.keyOutput = new KeyOutput() {
            public String get(String key) {
               return "[" + key + "]";
            }

            public String get(String key, int iValue) {
               return "[" + key + "]";
            }

            public String get(String key, String sValue) {
               return "[" + key + "]";
            }

            public String get(String key, String sValue, String sValue2) {
               return "[" + key + "]";
            }
         };
      } else {
         this.keyOutput = new KeyOutput() {
            public String get(String key) {
               try {
                  return LanguageManager.this.bundle.get(key);
               } catch (MissingResourceException var3) {
                  Gdx.app.log("AoC", "MissingResourceException: " + key);
                  return key;
               }
            }

            public String get(String key, int iValue) {
               try {
                  return LanguageManager.this.bundle.format(key, iValue);
               } catch (MissingResourceException var4) {
                  Gdx.app.log("AoC", "MissingResourceException: " + key);
                  return key;
               }
            }

            public String get(String key, String sValue) {
               try {
                  return LanguageManager.this.bundle.format(key, sValue);
               } catch (MissingResourceException var4) {
                  Gdx.app.log("AoC", "MissingResourceException: " + key);
                  return key;
               }
            }

            public String get(String key, String sValue, String sValue2) {
               try {
                  return LanguageManager.this.bundle.format(key, sValue, sValue2);
               } catch (MissingResourceException var5) {
                  Gdx.app.log("AoC", "MissingResourceException: " + key);
                  return key;
               }
            }
         };
      }

   }

   protected LanguageManager(String nTag) {
      this.fileHandle = Gdx.files.internal("game/languages/Bundle");
      this.locale = new Locale(nTag);
      this.bundle = I18NBundle.createBundle(this.fileHandle, this.locale);
      this.initCivsBundle(nTag);
      this.initLoadingBundle(nTag);

      //new bundle init
      this.initExtendedBundle(nTag);

      this.updateKeyOutput();
   }

   protected final void initCivsBundle(String nTag) {
      this.fileHandleCivs = Gdx.files.internal("game/languages/civilizations/Bundle");
      this.localeCivs = new Locale(nTag);
      this.bundleCivs = I18NBundle.createBundle(this.fileHandleCivs, this.localeCivs);
   }

   protected final void initLoadingBundle(String nTag) {
      this.fileHandleLoading = Gdx.files.internal("game/languages/loading/Bundle");
      this.localeLoading = new Locale(nTag);
      this.bundleLoading = I18NBundle.createBundle(this.fileHandleLoading, this.localeLoading);
      Gdx.app.log("", "num: " + this.getLoading("NumOfTexts"));

      try {
         this.iLoading_NumOfTexts = Integer.parseInt(this.getLoading("NumOfTexts"));
      } catch (IllegalArgumentException var3) {
         this.iLoading_NumOfTexts = 0;
      }

   }

   protected final void initExtendedBundle(String nTag) {
      try {
         this.fileHandleExtended = Gdx.files.internal("game/languages/twopointfive/Bundle");
         this.bundle.setParent(I18NBundle.createBundle(this.fileHandleExtended, this.locale));
         Gdx.app.log("AoC2.5", "Loaded Languages: " + this.getLoading("NumOfTexts"));
      } catch (RuntimeException e) {
         Gdx.app.log("AoC2.5", "Language File Errors!");
      }
   }

   protected final void dispose() {
      this.fileHandle = null;
      this.locale = null;
      this.bundle.dump();
      this.bundle = null;
      this.fileHandleCivs = null;
      this.localeCivs = null;
      this.bundleCivs.dump();
      this.bundleCivs = null;
      this.fileHandleExtended = null;
      this.fileHandleLoading = null;
      this.localeLoading = null;
      this.bundleLoading.dump();
      this.bundleLoading = null;
   }

   protected String get(String key) {
      return this.keyOutput.get(key);
   }

   protected String get(String key, int nValue) {
      return this.keyOutput.get(key, nValue);
   }

   protected String get(String key, String nValue) {
      return this.keyOutput.get(key, nValue);
   }

   protected String get(String key, String nValue, String nValue2) {
      return this.keyOutput.get(key, nValue, nValue2);
   }

   protected String getCiv(String key) {
      try {
         return this.bundleCivs.get(key);
      } catch (MissingResourceException var8) {
         if (key.indexOf(95) > 0) {
            try {
               return this.bundleCivs.get(key.substring(0, key.indexOf(95)));
            } catch (MissingResourceException var7) {
            }
         }

         try {
            FileHandle file;
            if (CFG.isAndroid()) {
               try {
                  file = Gdx.files.local("game/civilizations_editor/" + CFG.ideologiesManager.getRealTag(key) + "/" + CFG.ideologiesManager.getRealTag(key) + "_NM");
                  return file.readString();
               } catch (GdxRuntimeException var5) {
                  file = Gdx.files.internal("game/civilizations_editor/" + CFG.ideologiesManager.getRealTag(key) + "/" + CFG.ideologiesManager.getRealTag(key) + "_NM");
                  return file.readString();
               }
            } else {
               file = Gdx.files.internal("game/civilizations_editor/" + CFG.ideologiesManager.getRealTag(key) + "/" + CFG.ideologiesManager.getRealTag(key) + "_NM");
               return file.readString();
            }
         } catch (GdxRuntimeException var6) {
            Gdx.app.log("AoC", "CivMissingResourceException: " + key);
            return key;
         }
      }
   }

   protected String getLoading(String key) {
      try {
         return this.bundleLoading.get(key);
      } catch (MissingResourceException var3) {
         return "";
      } catch (NullPointerException var4) {
         CFG.loadingTime = 0L;
         return "";
      }
   }

   interface KeyOutput {
      String get(String var1);

      String get(String var1, int var2);

      String get(String var1, String var2);

      String get(String var1, String var2, String var3);
   }
}
