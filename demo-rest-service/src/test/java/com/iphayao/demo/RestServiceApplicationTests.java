package com.iphayao.demo;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RestServiceApplicationTests {
	@Autowired
	private MockMvc mvc;

	@Test
	@Order(1)
	void test_greeting_expect_greeting_message_hello_world() throws Exception {
		String expectContent = "{'id': 1, 'content': 'Hello World'}";

		mvc.perform(get("/greeting"))
				.andExpect(status().isOk())
				.andExpect(content().json(expectContent));
	}

	@Test
	@Order(2)
	void test_greeting_expect_greeting_message_increase_id_when_second_call() throws Exception {
		String expectContent = "{'id': 2, 'content': 'Hello World'}";

		mvc.perform(get("/greeting"))
				.andExpect(status().isOk())
				.andExpect(content().json(expectContent));
	}

	@Test
	@Order(3)
	void test_greeting_expect_greeting_message_hello_john_when_name_is_john() throws Exception {
		String expectContent = "{'id': 3, 'content': 'Hello John'}";

		mvc.perform(get("/greeting?name=John"))
				.andExpect(status().isOk())
				.andExpect(content().json(expectContent));
	}
}
