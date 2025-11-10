package cd.pipeline.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	@Value("${spring.application.name}")
	String name;
	@Value("${test.value}")
	String testValue;

	@GetMapping("/")
	public String index() {
		return "Greetings from " + name + " " + testValue + "!";
	}

	public void thisIsDeadCode() {
		// This is dead code to test code coverage tools
		int a = 1;
		int b = 2;
		int c = a + b;
		System.out.println(c);
	}

	@GetMapping("/mailiucous-code")
	public String mailiucousCode() {
		// This is another dead code to test code coverage tools
		for (int i = 0; i < 10; i++) {
			if (i % 2 == 0) {
				System.out.println(i + " is even");
			} else {
				System.out.println(i + " is odd");
			}
		}
		return "This is mailiucous code";
	}

	@GetMapping("/something")
	public ResponseEntity<String> createLogs() {
				String badFormatatedCode = "This is bad formatated code";
		if (badFormatatedCode == null) { // useless Null check
			System.out.println(badFormatatedCode);
			return ResponseEntity.ok().body("All Ok"); // double return
		}
		return ResponseEntity.ok().body("All Ok");
	}
}
