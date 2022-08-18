package com.ratepay.bugtracker.api;

import java.util.Date;

import com.ratepay.bugtracker.api.dto.BugRequest;
import com.ratepay.bugtracker.api.dto.PriorityTypeDto;
import com.ratepay.bugtracker.api.dto.UpdateBugRequest;
import com.ratepay.bugtracker.exception.NotFoundBugException;
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
public class BugResourceIT {
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@MockBean
	private BugService bugService;


	@Test
	public void addBug_success() {
		Mockito.when(bugService.addBug(Mockito.any())).thenReturn("62fd44ad63ad587a21dbe7d8");
		long time = new Date().getTime();
		BugRequest bugRequest = new BugRequest();
		bugRequest.setAssignee("MEHDI_DEV");
		bugRequest.setDueDate(time);
		bugRequest.setPriority(PriorityTypeDto.CRITICAL);
		bugRequest.setProject("RatePay");
		bugRequest.setSummary("Wrong balance!");
		bugRequest.setReporter("MR. EBRAHIMI");
		bugRequest.setDescription("Wrong balance! after pay");

		String url = String.format("http://localhost:%d/bug-tracker/api/bugs", port);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(bugRequest),
				String.class);
		assertThat(response).isNotNull();
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();
		Assertions.assertThat(response.getBody()).isNotNull();
		Assertions.assertThat(response.getBody()).isEqualTo("62fd44ad63ad587a21dbe7d8");
	}

	@Test
	public void addBug_validation_failed() {
 		long time = new Date().getTime();
		BugRequest bugRequest = new BugRequest();
		bugRequest.setAssignee("MEHDI_DEV");
		bugRequest.setDueDate(time);
		bugRequest.setPriority(PriorityTypeDto.LOW);
		bugRequest.setProject("RatePay");


		String url = String.format("http://localhost:%d/bug-tracker/api/bugs", port);
		ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(bugRequest),
				Void.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}

	@Test
	public void updateBug_success() {
		long time = new Date().getTime();
		BugRequest bugRequest = new BugRequest();
		bugRequest.setAssignee("MEHDI_DEV");
		bugRequest.setDueDate(time);
		bugRequest.setPriority(PriorityTypeDto.CRITICAL);

		String url = String.format("http://localhost:%d/bug-tracker/api/bugs", port);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(bugRequest),
				String.class);
		assertThat(response).isNotNull();
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
   	}

	@Test
	public void updateBug_validation_failed() {
		long time = new Date().getTime();
		BugRequest bugRequest = new BugRequest();
		bugRequest.setAssignee("MEHDI_DEV");
		bugRequest.setDueDate(time);

		String url = String.format("http://localhost:%d/bug-tracker/api/bugs", port);
		ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(bugRequest),
				Object.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}


	@Test
	public void updateBug_notFound() throws NotFoundBugException {
		doThrow(new NotFoundBugException("62fd44ad63ad587a21dbe7d9")).when(bugService).updateBug(any());
		long time = new Date().getTime();
		UpdateBugRequest bugRequest = new UpdateBugRequest();
		bugRequest.setId("62fd44ad63ad587a21dbe7d9");
		bugRequest.setAssignee("MEHDI_DEV");
		bugRequest.setDueDate(time);
		bugRequest.setPriority(PriorityTypeDto.CRITICAL);
		bugRequest.setProject("RatePay");
		bugRequest.setSummary("Wrong balance!");
		bugRequest.setReporter("MR. EBRAHIMI");
		bugRequest.setDescription("Wrong balance! after pay");

		String url = String.format("http://localhost:%d/bug-tracker/api/bugs", port);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(bugRequest),
				String.class);
		assertThat(response).isNotNull();
		assertThat(response.getBody()).isEqualTo("This bug does not exist: 62fd44ad63ad587a21dbe7d9");
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

}
