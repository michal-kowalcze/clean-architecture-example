package eu.kowalcze.michal.arch.clean.example.api

import eu.kowalcze.michal.arch.clean.example.domain.usecase.UseCase
import org.springframework.http.ResponseEntity


class SpringUseCaseExecutor(private val useCaseExecutor: UseCaseExecutor) {
    fun <DOMAIN_INPUT, DOMAIN_OUTPUT, API_OUTPUT> execute(
        useCase: UseCase<DOMAIN_INPUT, DOMAIN_OUTPUT>,
        input: DOMAIN_INPUT,
        toApiConversion: (domainOutput: DOMAIN_OUTPUT) -> UseCaseApiResult<API_OUTPUT>
    ): ResponseEntity<API_OUTPUT> {
        return useCaseExecutor.execute(useCase, input, toApiConversion).toSpringResponse()
    }
}

private fun <API_OUTPUT> UseCaseApiResult<API_OUTPUT>.toSpringResponse(): ResponseEntity<API_OUTPUT> =
    ResponseEntity.status(responseCode).body(output)