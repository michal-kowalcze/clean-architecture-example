package eu.kowalcze.michal.arch.clean.example.api

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
open class BaseIntegrationTest {
    @Autowired
    lateinit var restTemplate: TestRestTemplate

    @LocalServerPort
    val port = 0

    fun localUrl(request: String) = "http://localhost:${port}${request}"
}