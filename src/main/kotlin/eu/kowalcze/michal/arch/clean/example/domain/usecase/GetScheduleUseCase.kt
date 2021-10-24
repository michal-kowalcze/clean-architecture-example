package eu.kowalcze.michal.arch.clean.example.domain.usecase

import eu.kowalcze.michal.arch.clean.example.domain.model.DaySchedule
import eu.kowalcze.michal.arch.clean.example.domain.model.DayScheduleCreator
import eu.kowalcze.michal.arch.clean.example.domain.repository.DayScheduleRepository
import java.time.LocalDate

class GetScheduleUseCase(
    private val daySchedulerRepository: DayScheduleRepository,
    private val dayScheduleCreator: DayScheduleCreator,
) : UseCase<LocalDate, DaySchedule> {

    override fun apply(input: LocalDate): DaySchedule {
        val daySchedule = daySchedulerRepository.get(input)
        if (daySchedule != null) {
            return daySchedule
        }

        val newSchedule = dayScheduleCreator.create(input)
        return daySchedulerRepository.save(newSchedule)
    }
}

