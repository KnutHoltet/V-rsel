# Værsel 
<img align="center" src="/bilder/logo/SelVær_v4_blå_alternative.png" width="250" height="250">

## License / Lisens
**English**
- The code in this repository is licensed under the MIT License. See the [LICENSE](./LICENSE.md) file for details.
- The assets in the `/bilder` folder are not licensed for free use. Please refer to [LICENSE-Assets.md](./LICENSE-Assets.md) for details on asset usage.

**Norwegian**
- Koden i dette repositoriet er lisensiert under MIT-lisensen. Se [LICENSE](./LICENSE.md) filen for detaljer.
- Eiendelene i `/bilder`-mappen og/eller andre mapper er ikke lisensiert for fri bruk. Vennligst se [LICENSE-Assets.md](./LICENSE-Assets.md) for detaljer om bruken av eiendelene.


## Om prosjektet!
En enkel intuitiv android vær-applikasjon for barn mellom ni til fjorten!


Et prosjekt av 
- Birk Bergerud-Nygård (birkb@uio.no)
- Jeenu Mahadevan (jeenum@uio.no)
- An Vo (anvo@uio.no)
- Knut Holtet(knutholtet@live.no)
- Zehra Tien Ayden (zehratieen@gmail.com)
- Jannina Veluppillai (janninav@uio.no)

---
## Dokumentasjon
Diverse informasjon om bibloteker som er brukt ligger i `DOCUMENTATION.md`.
Applikasjonens arkitektur og diverse forklaringer om MVVM er beskrevet i `ARCHITECTURE.md` og diagrammer som visuelt beskriver løsningen finnes i `MODELING.md`

## Setup
[Minimum required Gradle version](https://developer.android.com/build/releases/gradle-plugin#updating-gradle) er 8.2. 

API-nivået er optimalisert for 34 men krever et minimum nivå på 26.

Du må ha en av disse Android Studio versjonene:

- Koala | 2024.1.1 | 3.2-8.5
- Jellyfish | 2023.3.1 | 3.2-8.4
- Iguana | 2023.2.1 | 3.2-8.3
- Hedgehog | 2023.1.1 | 3.2-8.

🚨 NB! For at applikasjonen skal fungere hensiktsmessig på enheten du kjører på, må lokasjonkallet fra enheten gi faktiske koordinater og ikke 0.0. Dette vil ikke være et problem dersom appen blir testet på en android enhet, men emulator kan slite med å hente brukerkoordinater. 

🚨 Hvis du tester på emulator og dette skjer vil du få en permanent loading-skjerm. Da må du avinnstallere applikasjonen, kjøre den på nytt og ikke gi lokasjons-tilgang. Du vil da få et forslag om å bli satt til Oslo. 

Dersom du får opp `Sync Android SDKs` for SDK pathen kan du bare trykke OK.

For å kjøre applikasjonen, klon repoet og åpne prosjektet i Android Studio.

`git clone https://github.uio.no/IN2000-V24/team-6.git`

Deretter velg android enhet (enten emulator eller fysisk) og se vær magien!


## Bilder av app
Ligger under `bilder/app_screenshots/`
