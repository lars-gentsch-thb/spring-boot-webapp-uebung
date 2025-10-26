package cd.pipeline.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	@Value("${spring.application.name}")
	String name;

	@GetMapping("/")
	public String index() {
		return "Greetings from " + name + "!";
	}


	@GetMapping("/something")
	public ResponseEntity<String> createLogs() {
		return ResponseEntity.ok().body("All Ok");
	}
}
