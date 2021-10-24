package eu.kowalcze.michal.arch.clean.example.api

import eu.kowalcze.michal.arch.clean.example.domain.usecase.UseCase

class UseCaseExecutor(private val notificationGateway: NotificationGateway) {
    fun <INPUT, OUTPUT> execute(useCase: UseCase<INPUT, OUTPUT>, input: INPUT): OUTPUT {
        try {
            return useCase.apply(input)
        } catch (e: Exception) {
            notificationGateway.notify(useCase, e)
            throw e
        }
    }
}

interface NotificationGateway {
    fun <INPUT, OUTPUT> notify(useCase: UseCase<INPUT, OUTPUT>, exception: Exception)
}