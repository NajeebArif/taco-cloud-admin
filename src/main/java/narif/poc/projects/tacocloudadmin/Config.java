package narif.poc.projects.tacocloudadmin;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.context.annotation.RequestScope;

@Configuration
public class Config {

    @Bean
    public SecurityFilterChain defSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                authorizationManagerRequestMatcherRegistry.anyRequest().authenticated())
                .oauth2Login(httpSecurityOAuth2LoginConfigurer ->
                        httpSecurityOAuth2LoginConfigurer.loginPage("/oauth2/authorization/taco-admin-client"))
                .oauth2Client(Customizer.withDefaults());
        return httpSecurity.build();
    }

    @Bean
    @RequestScope
    public RestIngredientService restIngredientService(OAuth2AuthorizedClientService clientService){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String accessToken = null;
        if(authentication.getClass().isAssignableFrom(OAuth2AuthenticationToken.class)) {
            OAuth2AuthenticationToken authenticationToken = (OAuth2AuthenticationToken) authentication;
            String authorizedClientRegistrationId = authenticationToken.getAuthorizedClientRegistrationId();
            if(authorizedClientRegistrationId.equals("taco-admin-client")){
                OAuth2AuthorizedClient oAuth2AuthorizedClient = clientService.loadAuthorizedClient(authorizedClientRegistrationId, authenticationToken.getName());
                accessToken = oAuth2AuthorizedClient.getAccessToken().getTokenValue();
            }
        }
        return new RestIngredientService(accessToken);
    }
}
