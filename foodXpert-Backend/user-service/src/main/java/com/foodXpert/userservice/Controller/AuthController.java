package com.foodXpert.userservice.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminInitiateAuthRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminInitiateAuthResponse;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AuthFlowType;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private CognitoIdentityProviderClient cognitoClient;


    private final String clientId = "45ubhp827np6chub7e38fumplt";

    private String userPoolId = "us-east-2_dcVYR5SSi";

    @PostMapping("/login")
    public AdminInitiateAuthResponse login(@RequestParam String username, @RequestParam String password) {
        AdminInitiateAuthRequest authRequest = AdminInitiateAuthRequest.builder()
                .userPoolId(userPoolId)
                .clientId(clientId)
                .authFlow(AuthFlowType.ADMIN_NO_SRP_AUTH)
                .authParameters(Map.of(
                        "USERNAME", username,
                        "PASSWORD", password
                ))
                .build();

        return cognitoClient.adminInitiateAuth(authRequest);
    }
}
