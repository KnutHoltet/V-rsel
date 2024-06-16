# Værsel 
<img align="center" src="https://media.github.uio.no/user/9669/files/3e8e1f80-55dc-47d7-b6d7-9fe3dfde356f">

## Om prosjektet!
En enkel intuitiv android vær-applikasjon for barn mellom ni til fjorten!

Et prosjekt av 
- Birk Bergerud-Nygård (birkb@uio.no)
- Jeenu Mahadevan (jeenum@uio.no)
- An Vo (anvo@uio.no)
- Knut Holtet(knuho@uio.no)
- Zehra Tien Ayden (zehrata@uio.no)
- Jannina Veluppillai (janninav@uio.no)

---
## Dokumentasjon
Diverse informasjon om bibloteker som er brukt ligger i `DOCUMENTATION.md`.
Applikasjonens arkitektur og diverse forklaringer om MVVM er beskrevet i `ARCHITECTURE.md` og diagrammer som visuelt beskriver løsningen finnes i `MODELING.md`

## Setup
![Minimum required Gradle version](https://developer.android.com/build/releases/gradle-plugin#updating-gradle) er 8.2. 

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
