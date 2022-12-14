package com.slowerror.asteroidradar.network

import com.slowerror.asteroidradar.Constants.API_QUERY_DATE_FORMAT
import com.slowerror.asteroidradar.Constants.DEFAULT_END_DATE_DAYS
import com.slowerror.asteroidradar.models.Asteroid
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

fun parseJsonResultToAsteroids(jsonObject: JSONObject): List<Asteroid> {
    val nearEarthObjectJson = jsonObject.getJSONObject("near_earth_objects")

    val asteroids = mutableListOf<Asteroid>()
    val nextSevenDaysFormat = getNextSevenDaysFormattedDates()

    for (date in nextSevenDaysFormat) {
        if (nearEarthObjectJson.has(date)) {
            val dateAsteroidJsonArray = nearEarthObjectJson.getJSONArray(date)

            for (i in 0 until dateAsteroidJsonArray.length()) {
                val asteroidJson = dateAsteroidJsonArray.getJSONObject(i)

                val id = asteroidJson.getLong("id")
                val codename = asteroidJson.getString("name")
                val closeApproachData = asteroidJson
                    .getJSONArray("close_approach_data").getJSONObject(0)
                val absoluteMagnitude = asteroidJson.getDouble("absolute_magnitude_h")
                val estimatedDiameter = asteroidJson.getJSONObject("estimated_diameter")
                    .getJSONObject("kilometers").getDouble("estimated_diameter_max")
                val relativeVelocity = closeApproachData.getJSONObject("relative_velocity")
                    .getDouble("kilometers_per_second")
                val missDistance = closeApproachData.getJSONObject("miss_distance")
                    .getDouble("astronomical")
                val isPotentiallyHazardous = asteroidJson
                    .getBoolean("is_potentially_hazardous_asteroid")


                val asteroid = Asteroid(id, codename, date, absoluteMagnitude,
                    estimatedDiameter, relativeVelocity, missDistance, isPotentiallyHazardous)

                asteroids.add(asteroid)
            }
        }
    }

    return asteroids.toList()
}

private fun getNextSevenDaysFormattedDates(): List<String> {
    val formattedDateList = mutableListOf<String>()

    val calendar = Calendar.getInstance()
    for (i in 0..DEFAULT_END_DATE_DAYS) {
        val currentTime = calendar.time
        val dateFormat = SimpleDateFormat(API_QUERY_DATE_FORMAT, Locale.getDefault())
        formattedDateList.add(dateFormat.format(currentTime))
        calendar.add(Calendar.DAY_OF_YEAR, 1)
    }

    return formattedDateList.toList()
}

fun getToday(): String {
    val calendar = Calendar.getInstance()
    val dateFormat = SimpleDateFormat(API_QUERY_DATE_FORMAT, Locale.getDefault())

    return dateFormat.format(calendar.time)
}

fun getEndDay(): String {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_YEAR, DEFAULT_END_DATE_DAYS)
    val dateFormat = SimpleDateFormat(API_QUERY_DATE_FORMAT, Locale.getDefault())

    return dateFormat.format(calendar.time)
}