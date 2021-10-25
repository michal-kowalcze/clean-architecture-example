package eu.kowalcze.michal.arch.clean.example.api

import eu.kowalcze.michal.arch.clean.example.domain.usecase.UseCase
import org.springframework.http.ResponseEntity

class UseCaseExecutor(private val notificationGateway: NotificationGateway) {
    fun <DOMAIN_INPUT, DOMAIN_OUTPUT, API_OUTPUT> execute(
        useCase: UseCase<DOMAIN_INPUT, DOMAIN_OUTPUT>,
        input: DOMAIN_INPUT,
        toApiConversion: (domainOutput: DOMAIN_OUTPUT) -> UseCaseApiResult<API_OUTPUT>
    ): UseCaseApiResult<API_OUTPUT> {

        try {
            val domainOutput = useCase.apply(input)
            return toApiConversion(domainOutput)
        } catch (e: Exception) {
            notificationGateway.notify(useCase, e)
            throw e
        }
    }
}

private fun <API_OUTPUT> UseCaseApiResult<API_OUTPUT>.toSpringResponse(): ResponseEntity<API_OUTPUT> =
    ResponseEntity.status(responseCode).body(output)
data class UseCaseApiResult<API_OUTPUT>(
    val responseCode: Int,
    val output: API_OUTPUT,
)

