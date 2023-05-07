# Dopamine detox app

Gamified app for detoxication purposes.

Has ranking system to maintain user's motivation,
task list with tasks designed for user to stay healthy,
teaches theory for the user to better understand the point of detoxing,
detoxication timer with custom detox difficulty,
and you can even get some achievements for your efforts.


**Dokumentácia k aplikácii Detox Rank**
Popis aplikácie:
Aplikácia slúži na pomoc so zbavením závislosti na dopamíne. Využíva gamifikáciu na motivovanie užívateľa pri plnení 
rôznych úloh v aplikácii a tvorení zdravých zvykov. 

Hlavné použité knižnice:
```
Hilt, Dagger, MaterialDesign3, Navigation, Material Icons, Room
```

Odporúčaný postup po spustení aplikácie (.apk):
1. Naštudovanie sekcie s teóriou
2. Nastavenie obtiažnosti časovača
3. Spustenie časovača
4. Plnenie denných -> týždenných -> mesačných úloh
5. Plnenie achievementov a vylepšovanie osobného hodnotenia - ranku

Spustenie bez .apk súboru: 

0. Odporúčaný software na spustenie aplikácie je Android Studio. 
Preto je postup spustenia aplikácie popísaný výhradne pomocou tohto softwaru.
1. Otvoriť priečinok `main_app` priamo z Android Studia (File -> Open.. -> vybrať main_app)
2. Ak je to potrebné, v súbore build.gradle stlačiť synchronizáciu gradle pluginu
3. Ak nie je nastavená konfigurácia, vytvoriť novú (vpravo hore, add configuration -> Android App) 
a vyplniť potrebné parametre, ako je napríklad SDK.
4. Nakonfigurovať mobilné zariadenie, aby sa na ňom dala spustiť aplikácia, viac na https://developer.android.com/studio/run/device
Prípadne vytvoriť virtuálne zariadenie pomocou vbudovaného emulátora.
5. Build -> Make Project
6. Run
