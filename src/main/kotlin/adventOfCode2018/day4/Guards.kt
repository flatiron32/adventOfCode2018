/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package adventOfCode2018.day4

import java.io.File

fun main(args: Array<String>) {
    val guardStarts = Regex(".* Guard #([0-9]+) begins shift")
    val fallsAsleep = Regex("\\[\\d{4}-\\d{2}-\\d{2} \\d{2}:(\\d{2})] falls asleep")
    val wakesUp = Regex("\\[\\d{4}-\\d{2}-\\d{2} \\d{2}:(\\d{2})] wakes up")
    val fileName = "src/main/resources/guardSchedule.txt"
    File(fileName).inputStream().bufferedReader().useLines { lines ->
        val guards = mutableMapOf<String, MutableMap<Int, Int>>()
        var activeGuard = ""
        var sleepMin = 0
        lines.sorted().forEach { line ->
            when {
                line.matches(guardStarts) -> activeGuard = guardStarts.matchEntire(line)?.groupValues?.get(1)!!
                line.matches(fallsAsleep) -> sleepMin = fallsAsleep.matchEntire(line)?.groupValues?.get(1)?.toInt()!!
                line.matches(wakesUp) -> {
                    val wakeMin: Int = wakesUp.matchEntire(line)?.groupValues?.get(1)?.toInt()!!
                    val timeMap = guards.getOrDefault(activeGuard, mutableMapOf())
                    for (min in sleepMin..(wakeMin - 1)) {
                        timeMap[min] = timeMap.getOrDefault(min, 0) + 1
                    }
                    guards[activeGuard] = timeMap
                }
            }
        }
        val maxSleepTime = guards.values.maxBy { timeMap -> timeMap.values.sum() }
        val sleepiest = guards.filter { it.value == maxSleepTime }
        val mrSleepy = sleepiest.keys.first()
        val sleepyMin = guards[mrSleepy]!!.filter { it.value == guards[mrSleepy]!!.values.max()}.keys.first()
        println(mrSleepy)
        println(sleepyMin)
        println(mrSleepy.toInt() * sleepyMin)
        println(guards)
        val maxMins = guards.mapValues { guard  ->  guard.value.maxBy { mins -> mins.value  }}
        val mrConsistent = maxMins.maxBy { it.value!!.value }
        println(maxMins)
        print(mrConsistent)
    }
}
