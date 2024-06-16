package com.example.vaersel.data.alerts.icons

import com.example.vaersel.R

val icons = hashMapOf(
    /* Wind */
    "wind_orange" to R.drawable.icon_warning_wind_orange,
    "wind_red" to R.drawable.icon_warning_wind_red,
    "wind_yellow" to R.drawable.icon_warning_wind_yellow,

    /* Gale */
    "gale_orange" to R.drawable.icon_warning_wind_orange,
    "gale_red" to R.drawable.icon_warning_wind_red,
    "gale_yellow" to R.drawable.icon_warning_wind_yellow,

    /* Rain */
    "rain_orange" to R.drawable.icon_warning_rain_orange,
    "rain_red" to R.drawable.icon_warning_rain_red,
    "rain_yellow" to R.drawable.icon_warning_rain_yellow,

    /* Snow */
    "snow_orange" to R.drawable.icon_warning_snow_orange,
    "snow_red" to R.drawable.icon_warning_snow_red,
    "snow_yellow" to R.drawable.icon_warning_snow_yellow,

    /* Ice */
    "ice_orange" to R.drawable.icon_warning_ice_orange,
    "ice_red" to R.drawable.icon_warning_ice_red,
    "ice_yellow" to R.drawable.icon_warning_ice_yellow,

    /* Rain Floow */
    "rainFlood_orange" to R.drawable.icon_warning_rainflood_orange,
    "rainFlood_red" to R.drawable.icon_warning_rainflood_red,
    "rainFlood_yellow" to R.drawable.icon_warning_rainflood_yellow,

    /* Blowing Snow */
    "blowingSnow_orange" to R.drawable.icon_warning_snow_orange,
    "blowingSnow_red" to R.drawable.icon_warning_snow_red,
    "blowingSnow_yellow" to R.drawable.icon_warning_snow_yellow,

    /* Storm Surge */
    "stormSurge_orange" to R.drawable.icon_warning_stormsurge_orange,
    "stormSurge_red" to R.drawable.icon_warning_stormsurge_red,
    "stormSurge_yellow" to R.drawable.icon_warning_stormsurge_yellow,

    /* Polar Low */
    "polarLow_orange" to R.drawable.icon_warning_polarlow_orange,
    "polarLow_red" to R.drawable.icon_warning_polarlow_red,
    "polarLow_yellow" to R.drawable.icon_warning_polarlow_yellow,

    /* Forest Fire */
    "forestFire_orange" to R.drawable.icon_warning_forestfire_orange,
    "forestFire_red" to R.drawable.icon_warning_forestfire_red,
    "forestFire_yellow" to R.drawable.icon_warning_forestfire_yellow,

    /* Icing */
    "icing_orange" to R.drawable.icon_warning_generic_orange,
    "icing_red" to R.drawable.icon_warning_extreme,
    "icing_yellow" to R.drawable.icon_warning_generic_yellow,

    /* Lightning */
    "lightning_orange" to R.drawable.icon_warning_lightning_orange,
    "lightning_red" to R.drawable.icon_warning_lightning_red,
    "lightning_yellow" to R.drawable.icon_warning_lightning_yellow
)
fun alertIcon(stringToId: String): Int = icons[stringToId] ?: R.drawable.icon_warning_generic_yellow
