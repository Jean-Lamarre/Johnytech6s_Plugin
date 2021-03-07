package io.github.johnytech6.dm;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

@Tag("fast")
class FirstTest {

	@Test
	@DisplayName("")
	void myFirstTest(TestInfo testInfo) {
		assertEquals("text", "text");
	}

}
