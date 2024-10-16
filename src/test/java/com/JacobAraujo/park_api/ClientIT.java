package com.JacobAraujo.park_api;

import com.JacobAraujo.park_api.web.dto.ClientCreateDto;
import com.JacobAraujo.park_api.web.dto.ClientResponseDto;
import com.JacobAraujo.park_api.web.exception.ErrorMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/clients/clients-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/clients/clients-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ClientIT {

    @Autowired
    WebTestClient testClient;

    @Test
    public void createClient_validData_returnClientStatus201() {
        ClientResponseDto responseBody = testClient
                .post()
                .uri("api/v1/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "tob@email.com", "123456"))
                .bodyValue(new ClientCreateDto("Tobias Ferreira", "61461186056"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(ClientResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getName()).isEqualTo("Tobias Ferreira");
        org.assertj.core.api.Assertions.assertThat(responseBody.getCpf()).isEqualTo("61461186056");

    }

    @Test
    public void createClient_cpfAlreadyExists_returnErrorMessageStatus409() {
        ErrorMessage responseBody = testClient
                .post()
                .uri("api/v1/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "tob@email.com", "123456"))
                .bodyValue(new ClientCreateDto("Tobias Ferreira", "28072892070"))
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(409);
    }

    @Test
    public void createClient_invalidData_returnErrorMessageStatus422() {
        ErrorMessage responseBody = testClient
                .post()
                .uri("api/v1/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "tob@email.com", "123456"))
                .bodyValue(new ClientCreateDto("", ""))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody = testClient
                .post()
                .uri("api/v1/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "tob@email.com", "123456"))
                .bodyValue(new ClientCreateDto("Bobb", "00000000000"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody = testClient
                .post()
                .uri("api/v1/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "tob@email.com", "123456"))
                .bodyValue(new ClientCreateDto("Tobias Ferreira", "280.728.920-70"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
    }

    @Test
    public void createClient_noPermissionUser_returnErrorMessageStatus403() {
        ErrorMessage responseBody = testClient
                .post()
                .uri("api/v1/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .bodyValue(new ClientCreateDto("Tobias Ferreira", "28072892070"))
                .exchange()
                .expectStatus().isEqualTo(403)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);
    }

    @Test
    public void searchClient_existingIdByAdmin_returnClientStatus200() {
        ClientResponseDto responseBody = testClient
                .get()
                .uri("api/v1/clients/10")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(ClientResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(10);
    }

    @Test
    public void searchClient_nonExistentIdByAdmin_returnErrorMessageStatus404() {
        ErrorMessage responseBody = testClient
                .get()
                .uri("api/v1/clients/0")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
    }

    @Test
    public void searchClient_existingIdByClient_returnErrorMessageStatus403() {
        ErrorMessage responseBody = testClient
                .get()
                .uri("api/v1/clients/10")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "bia@email.com", "123456"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);
    }
}
