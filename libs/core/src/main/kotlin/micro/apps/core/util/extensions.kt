package micro.apps.core.util

import com.fatboyindustrial.gsonjavatime.Converters
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters


/**
 * Format an Instant as an ISO8601 timestamp
 */
fun Instant.toISO8601(): String = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
        .withZone(ZoneId.of("UTC")).format(this)

/**
 * Format a LocalDate in ISO8601 format
 */
fun LocalDate.toISO8601(): String = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        .withZone(ZoneId.of("UTC")).format(this)

/**
 * Find the day of week of an Instant
 */
fun Instant.firstDayOfWeek(): LocalDate = this.atZone(ZoneId.of("UTC")).toLocalDate()
        .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))

/**
 * Custom GSON configuration with support for serializing java.time classes into ISO8601 format
 */
fun gsonBuilder(): Gson = Converters.registerAll(GsonBuilder())
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
        .create()

/**
 * Execute a block of code when a variable is not null
 */
fun <T : Any> T?.whenNotNull(callback: (it: T) -> Unit) {
    if (this != null) callback(this)
}

/**
 * Execute a block of code when a variable is null
 */
fun <T : Any> T?.whenNull(callback: () -> Unit) {
    this ?: callback()
}

