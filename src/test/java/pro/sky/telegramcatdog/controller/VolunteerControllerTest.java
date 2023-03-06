package pro.sky.telegramcatdog.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pro.sky.telegramcatdog.model.Volunteer;

import static pro.sky.telegramcatdog.constants.Constants.LOCALHOST_URL;
import static pro.sky.telegramcatdog.constants.Constants.VOLUNTEER_URL;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class VolunteerControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void getVolunteer() {
        // Create new volunteer and check that it was created OK
        Volunteer volunteer = new Volunteer(1, "Vasya", 1234567809, "https://t.me/vasyapupkin");
        ResponseEntity<Volunteer> responseCreated = getCreateVolunteerResponse(volunteer);
        assertCreatedVolunteer(volunteer, responseCreated);

        // Try to get the created volunteer by its id.
        Volunteer createdVolunteer = responseCreated.getBody();
        ResponseEntity<Volunteer> response = restTemplate.getForEntity(
                LOCALHOST_URL + port + VOLUNTEER_URL + '/' + createdVolunteer.getId(),
                Volunteer.class);

        // Check that the created and selected by id volunteers are the same
        Assertions.assertThat(response.getBody()).isEqualTo(createdVolunteer);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void createVolunteer() {
        Volunteer volunteer = new Volunteer(1, "Vasya", 1234567809, "https://t.me/vasyapupkin");;
        ResponseEntity<Volunteer> response = getCreateVolunteerResponse(volunteer);
        assertCreatedVolunteer(volunteer, response);
    }

    @Test
    void editVolunteer() {
        String oldName = "Vasya";
        long oldChatId = 1234567809;
        String newName = "Vasya Pupkin";
        long newChatId = 1122334455;

        // Create new volunteer first and check that it was created OK
        Volunteer volunteer = new Volunteer(1, oldName, oldChatId, "https://t.me/vasyapupkin");
        ResponseEntity<Volunteer> responseCreated = getCreateVolunteerResponse(volunteer);
        assertCreatedVolunteer(volunteer, responseCreated);

        // Modify the created volunteer
        Volunteer createdVolunteer = responseCreated.getBody();
        createdVolunteer.setName(newName);
        createdVolunteer.setTelegramChatId(newChatId);

        // Update the modified volunteer in db
        restTemplate.put(
                LOCALHOST_URL + port + VOLUNTEER_URL,
                createdVolunteer);

        // Try to get the updated volunteer by its id.
        ResponseEntity<Volunteer> response = restTemplate.getForEntity(
                LOCALHOST_URL + port + VOLUNTEER_URL + '/' + createdVolunteer.getId(),
                Volunteer.class);

        // Check that the updated volunteer has the same newName, newChatId.
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody().getName()).isEqualTo(newName);
        Assertions.assertThat(response.getBody().getTelegramChatId()).isEqualTo(newChatId);
    }

    private ResponseEntity<Volunteer> getCreateVolunteerResponse(Volunteer volunteer) {
        return restTemplate.postForEntity(
                LOCALHOST_URL + port + VOLUNTEER_URL,
                volunteer,
                Volunteer.class);
    }

    private void assertCreatedVolunteer(Volunteer volunteer, ResponseEntity<Volunteer> response) {
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody().getId()).isNotNull();
        Assertions.assertThat(response.getBody().getId()).isEqualTo(volunteer.getId());
    }
}