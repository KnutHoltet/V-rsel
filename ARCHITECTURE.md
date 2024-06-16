Arkitektur
============================

> Arkitekturen, MVVM, mappestrukturen og konvensjoner for prosjektet.

![Document systems(1)](https://media.github.uio.no/user/9669/files/ae5939d0-acc8-47d6-9c3a-727e04d00120)


### Mappe struktur


    ~/team-6/app/src/main/java/com/example/vaersel/
    ├── data                   # Data Laget (Div repositories)
    ├── hamburgerbar           # Ligger i hovedsak her pga refaktorerings komplikasjoner
    ├── model                  # Data klasser (`WeatherInfo.kt`)
    ├── ui                     # Ui Laget (`HomeScreen.kt` eller `HomeScreenViewModel.kt`)
    ├── usecases               # Domene Laget
    ├── AppScaffold.kt
    └── MainActivity


## Generell beskrivelse

Selv om HomeScreen står for hovedbolken av elementene i UI laget, har vi alltid prøvd å implementere en kode abstrahering og arkitektur som bygger under følgende grunnleggende prinsipper.

- Enkle å forstå moduler av kode med så spesifikk logikk som kompetansen gjør det mulig.
- Gjenbrukbare funksjoner bør være selvstendige, og @Composable funksjoner bør være fleksible og designes for lav avhengighet til andre komponenter.

For eksempel har logikk for å håndtere værdata blitt lagt til som hjelpefunksjoner.

    ~/team-6/app/src/main/java/com/example/vaersel/ui/home/animation/helpfunctions/
    ├── helpfunctions               
    │   ├── AnimationMap.kt         
    │   ├── AvgTemp.kt              
    │   ├── BuildClothesString.kt 
    |   └── ...                         # osv.
    └── ...
  
 Deretter blir alt håndert av `AppScaffold.kt`, navigasjon mellom skjermer og oppbygning av topbar.

```Kotlin
fun AppScaffold(
    homeScreenViewModel: HomeScreenViewModel,
    settingsScreenViewModel: SettingsScreenViewModel,
    streakViewModel: StreakViewModel,
    animationViewModel: AnimationViewModel,
    alertsViewModel: AlertsViewModel,
    modifier: Modifier = Modifier,
    activity: ComponentActivity,
    location: UserLocation
) { .. }
```

Pop up kortene med informasjon til de ulike ikonene blir også håndert i Scaffoldet, men selve composable funksjonen er abstrahert i egne mapper og filer.

### API nivå
`minSdk`26 og `tragetSdk`34. 

```Kotlin
fun GifImage(animationCode: String, viewModel: AnimationViewModel) {}
```
Denne funksjonen ble innført for å forbedre visningen av animasjoner. Dermed ble det nødvendig å oppgradere til `minSdk` 26. 

* Fordeler 
Oppgraderingen gjør det mulig å forbedre brukeropplevelsen med mer avansert UI-animasjon.

* Eventuelle Fremtidige Mål 
Bruke mer avansert animasjonsteknikker, og kanskje integrere mer sofistikerte animasjonsbiblioteker.


## Ny utvikler kræsjkurs?
Som nevnt i generell beskrivelse om arkitektur over, står `HomeScreen.kt` og `HomeScreenViewModel` for mye av applikasjonen. Dermed blir det naturlig å forklare hvordan `HomeScreen.kt` fungerer.

    ~/team-6/app/src/main/java/com/example/vaersel/ui/home/
    ├── animation                
    ├── components               
    ├── HomeScreen.kt            
    └── HomeScreenViewModel.kt  
    
`HomeScreenViewModel.kt` har et relativt hovedanvar for nettverkskallet. UI-elementer er lagret i ![StateFlows](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow) og blir aksessert gjennom `sealed`-klasser som inneholder et `data` objekt `Success`. Dersom nettverkskallet var vellykket kan man gjennbruke verdien til variablene. 

Eksempel i `~/team-6/app/src/main/java/com/example/vaersel/ui/home/HomeScreenViewModel.kt/`

```Kotlin
sealed class WeekForecastUiState {
    data class Success(val sevenNextDays: List<List<TimeSeriesEntry>>): WeekForecastUiState()
}
```
Her vurderte vi å legge inn en `Error`-class, men siden vi ikke bruker det på noe vis, var det unaturlig, og vi fjernet det -> men dersom det er behov for det kan det legges inn igjen.
```Kotlin 
data class Error(val exception: Throwable) 
```
ViewModelen har abstrahert spesefikk logikk i et ![domene lag](https://developer.android.com/topic/architecture/domain-layer), men henter repositories inn som parameter for å spare API-kall, og fetcher en gang.

```Kotlin
fun initilize()  {
    viewModelScope.launch {
        weatherRepository.fetchWeatherData(coords.lat, coords.lon)
        locationRepository.fetchLocation(coords.lat, coords.lon)
    }
}
```


For å holde den atomære konvnesjonen anbefaler vi å sette opp `invoke()` funksjoner for å hente data fra repositories.

    ~/team-6/app/src/main/java/com/example/vaersel/usecases/
    ├── alerts                              
    │   ├── GetAlertInformationUseCase      # Slik navn konvensjon
    ├── location
    └── weather



`PullToRefreshColumn` har de fleste vær elementene for `HomeScreen.kt`. Dersom du ønsker å legge inn flere @Composable funksjoner må det legges inn i listen for parameteret.

```Kotlin
    val composable = listOf<@Composable () -> Unit>(
        {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // current day's information
                HomeCard(
                    weatherForTheNextHoursUiState = weatherForTheNextHoursUiState,
                    locationWeatherUiState = locationWeatherUiState,
                    settingsScreenViewModel = settingsScreenViewModel,
                    animationViewModel = animationViewModel
                )
            }
        },
        {
            Spacer(Modifier.height(16.dp))
        },
        {
            // information for the coming week
            PrivateNextDaysTableCard(
                weatherForTheWeekUiState = weatherForTheWeekUiState,
                locationWeatherUiState = locationWeatherUiState,
                onClickNavigate = onClickNavigate,
                activateNoDetailsSnackbar = {viewModel.showSnackbar()},
                settingsScreenViewModel = settingsScreenViewModel
            )
        }
    )
```

For å forhindre latency og unødvendig rendring av ui elementer i LazyColumn har vi valgt å gjøre det på denne måten for å kun bruke ett `item{}`element. 

Skal mer data hentes, følger du bare strukturen til MVVM. Setter opp en source, lagrer relevant data i repo og velger spesefikk informasjon i en use case for å unngå store klasser og setter opp StateFlows i viewmodel.
