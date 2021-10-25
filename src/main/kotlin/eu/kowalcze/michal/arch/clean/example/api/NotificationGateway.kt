package eu.kowalcze.michal.arch.clean.example.api

import eu.kowalcze.michal.arch.clean.example.domain.usecase.UseCase


interface NotificationGateway {
    fun <INPUT, OUTPUT> notify(useCase: UseCase<INPUT, OUTPUT>, exception: Exception)
}