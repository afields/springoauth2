package net.aaronfields.springoauth2.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
public class OAuth2Config {
	
	private static final String RESOURCE_ID = "springoauth2-resources";
	
	private static InMemoryTokenStore tokenStore = new InMemoryTokenStore();

	@Configuration
	@EnableAuthorizationServer
	protected static class AuthServerConfig extends AuthorizationServerConfigurerAdapter {
		
		@Autowired
		private AuthenticationManager authenticationManager;
		
		@Override
		public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
			clients
				.inMemory()
					.withClient("springoauth2-client")
						.authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
						.authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit")
						.resourceIds(RESOURCE_ID)
						.scopes("read", "write", "trust")
						.secret("springoauth2-secret");
		}
		
		@Override
		public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
			endpoints
				.authenticationManager(authenticationManager)
				.tokenStore(tokenStore);
		}
		
	}
	
	@Configuration
	@EnableResourceServer
	protected static class ResourceServerConfig extends ResourceServerConfigurerAdapter {
		
		@Override
		public void configure(HttpSecurity http) throws Exception {
			http
				.authorizeRequests()
					.anyRequest().authenticated();
		}
		
		@Override
		public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
			resources
				.resourceId(RESOURCE_ID)
				.tokenStore(tokenStore);
		}
		
	}
}
