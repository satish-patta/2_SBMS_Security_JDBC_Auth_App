package in.satish.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestController {
	@GetMapping(value = "/admin")
	public String admin() {
		return "<h1>Welcome Admin :)</h1>";
	}

	@GetMapping(value = "/user")
	public String user() {
		return "<h1>Hello User :)</h1>";
	}

	@GetMapping(value = "/welcome")
	public String welcome() {
		return "<h1>Welcome :)</h1>";
	}
}
