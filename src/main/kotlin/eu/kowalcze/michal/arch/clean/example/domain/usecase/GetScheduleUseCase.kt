package eu.kowalcze.michal.arch.clean.example.domain.usecase

import eu.kowalcze.michal.arch.clean.example.domain.schedule.DaySchedule
import eu.kowalcze.michal.arch.clean.example.domain.schedule.DayScheduleCreator
import java.time.LocalDate

class GetScheduleUseCase(
    private val daySchedulerRepository: DayScheduleRepository,
    private val dayScheduleCreator: DayScheduleCreator,
) {

    fun getSchedule(scheduleDay: LocalDate): DaySchedule {
        val daySchedule = daySchedulerRepository.get(scheduleDay)
        if (daySchedule != null) {
            return daySchedule
        }

        val newSchedule = dayScheduleCreator.create(scheduleDay)
        return daySchedulerRepository.save(newSchedule)
    }
}

