package net.aaronfields.springoauth2.api.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public ResponseEntity<String> getTestResource() {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return ResponseEntity.ok().body(auth.getName());
		
	}
	
}
