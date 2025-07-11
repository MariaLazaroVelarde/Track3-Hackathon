package pe.edu.vallegrande.marialazaro.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import pe.edu.vallegrande.marialazaro.model.Ruc;
import reactor.core.publisher.Mono;

@Component
public class RucApiClient {
    
    private final WebClient webClient;
    
    @Value("${api.ruc.base-url:https://dniruc.apisperu.com/api/v1}")
    private String baseUrl;
    
    @Value("${api.ruc.token:eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Im1hcmlhLmxhemFyb0B2YWxsZWdyYW5kZS5lZHUucGUifQ.pwDYAIkONqeiktGsiTSl_FTMsUMOjdroEdlOX8Gy3nQ}")
    private String token;
    
    public RucApiClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }
    
    public Mono<Ruc> fetchRucData(String ruc) {
        return webClient.get()
                .uri(baseUrl + "/ruc/{ruc}?token={token}", ruc, token)
                .retrieve()
                .bodyToMono(Ruc.class)
                .onErrorResume(throwable -> {
                    // Log del error
                    System.err.println("Error fetching RUC data for: " + ruc + " - " + throwable.getMessage());
                    return Mono.empty();
                });
    }
}
