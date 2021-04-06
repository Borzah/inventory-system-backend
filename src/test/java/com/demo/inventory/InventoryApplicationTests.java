package com.demo.inventory;

import com.demo.inventory.configuration.setup.StartDataRunner;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class InventoryApplicationTests {

	// Added to avoid errors
	@MockBean
	private StartDataRunner startDataRunner;

	@Test
	void contextLoads() {
	}

}
