package eu.kowalcze.michal.arch.clean.example.config

import eu.kowalcze.michal.arch.clean.example.domain.model.DayScheduleCreator
import eu.kowalcze.michal.arch.clean.example.domain.repository.DayScheduleRepository
import eu.kowalcze.michal.arch.clean.example.domain.usecase.GetScheduleUseCase
import eu.kowalcze.michal.arch.clean.example.domain.usecase.ReserveSlotUseCase
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class DomainConfig {
    @Bean
    open fun getScheduleUseCase(dayScheduleRepository: DayScheduleRepository, dayScheduleCreator: DayScheduleCreator) =
        GetScheduleUseCase(dayScheduleRepository, dayScheduleCreator)

    @Bean
    open fun reserveSlotUseCase(dayScheduleRepository: DayScheduleRepository, getScheduleUseCase: GetScheduleUseCase) =
        ReserveSlotUseCase(dayScheduleRepository, getScheduleUseCase)

    @Bean
    open fun dayScheduleCreator() = DayScheduleCreator()
}