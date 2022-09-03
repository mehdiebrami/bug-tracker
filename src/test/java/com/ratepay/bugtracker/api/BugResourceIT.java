package com.ratepay.bugtracker.api;

import java.util.Date;

import com.ratepay.bugtracker.api.dto.BugInput;
import com.ratepay.bugtracker.api.dto.PriorityTypeDto;
import com.ratepay.bugtracker.api.dto.UpdateBugInput;
import com.ratepay.bugtracker.exception.NotFoundBugException;
import com.ratepay.bugtracker.exception.NotFoundProjectException;
import com.ratepay.bugtracker.service.BugService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BugResourceIT {
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@MockBean
	private BugService bugService;


	@Test
	void addBug_success() throws NotFoundBugException, NotFoundProjectException {
		Mockito.when(bugService.addBug(Mockito.any())).thenReturn("62fd44ad63ad587a21dbe7d8");
		long time = new Date().getTime();
		BugInput bugInput = new BugInput();
		bugInput.setAssignee("MEHDI_DEV");
		bugInput.setDueDate(time);
		bugInput.setPriority(PriorityTypeDto.CRITICAL);
		bugInput.setProjectId("RatePay");
		bugInput.setSummary("Wrong balance!");
		bugInput.setReporter("MR. EBRAHIMI");
		bugInput.setDescription("Wrong balance! after pay");

		String url = String.format("http://localhost:%d/bug-tracker/api/bugs", port);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(bugInput),
				String.class);
		assertThat(response).isNotNull();
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();
		Assertions.assertThat(response.getBody()).isNotNull();
		Assertions.assertThat(response.getBody()).isEqualTo("62fd44ad63ad587a21dbe7d8");
	}

	@Test
	void addBug_validation_failed() {
		long time = new Date().getTime();
		BugInput bugInput = new BugInput();
		bugInput.setAssignee("MEHDI_DEV");
		bugInput.setDueDate(time);
		bugInput.setPriority(PriorityTypeDto.LOW);
		bugInput.setProjectId("RatePay");


		String url = String.format("http://localhost:%d/bug-tracker/api/bugs", port);
		ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(bugInput),
				Void.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}

	@Test
	void updateBug_success() {
		long time = new Date().getTime();
		BugInput bugInput = new BugInput();
		bugInput.setAssignee("MEHDI_DEV");
		bugInput.setDueDate(time);
		bugInput.setPriority(PriorityTypeDto.CRITICAL);

		String url = String.format("http://localhost:%d/bug-tracker/api/bugs", port);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(bugInput),
				String.class);
		assertThat(response).isNotNull();
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	void updateBug_validation_failed() {
		long time = new Date().getTime();
		BugInput bugInput = new BugInput();
		bugInput.setAssignee("MEHDI_DEV");
		bugInput.setDueDate(time);

		String url = String.format("http://localhost:%d/bug-tracker/api/bugs", port);
		ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(bugInput),
				Object.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}


	@Test
	void updateBug_notFound() throws NotFoundBugException, NotFoundProjectException {
		doThrow(new NotFoundBugException("62fd44ad63ad587a21dbe7d9")).when(bugService).updateBug(any());
		long time = new Date().getTime();
		UpdateBugInput updateBugInput = new UpdateBugInput();
		updateBugInput.setId("62fd44ad63ad587a21dbe7d9");
		updateBugInput.setAssignee("MEHDI_DEV");
		updateBugInput.setDueDate(time);
		updateBugInput.setPriority(PriorityTypeDto.CRITICAL);
		updateBugInput.setProjectId("RatePay");
		updateBugInput.setSummary("Wrong balance!");
		updateBugInput.setReporter("MR. EBRAHIMI");
		updateBugInput.setDescription("Wrong balance! after pay");

		String url = String.format("http://localhost:%d/bug-tracker/api/bugs", port);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(updateBugInput),
				String.class);
		assertThat(response).isNotNull();
		assertThat(response.getBody()).isEqualTo("This bug does not exist: 62fd44ad63ad587a21dbe7d9");
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

}
