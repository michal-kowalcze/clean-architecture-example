package eu.kowalcze.michal.arch.clean.example.config

import eu.kowalcze.michal.arch.clean.example.domain.repository.DayScheduleRepository
import eu.kowalcze.michal.arch.clean.example.infrastructure.InMemoryDayScheduleRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class InfrastructureConfig {
    @Bean
    open fun dayScheduleRepository(): DayScheduleRepository = InMemoryDayScheduleRepository()
}