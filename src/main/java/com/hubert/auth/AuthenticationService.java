package com.hubert.auth;

import com.hubert.customer.Customer;
import com.hubert.customer.CustomerDTO;
import com.hubert.customer.CustomerDTOMapper;
import com.hubert.jwt.JWTUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final CustomerDTOMapper customerDTOMapper;

    public AuthenticationService(AuthenticationManager authenticationManager, JWTUtil jwtUtil, CustomerDTOMapper customerDTOMapper) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.customerDTOMapper = customerDTOMapper;
    }

    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.username(), authenticationRequest.password())
        );
        Customer principal = (Customer) authentication.getPrincipal();
        CustomerDTO customerDTO = customerDTOMapper.apply(principal);
        String token = jwtUtil.issueToken(customerDTO.username(), customerDTO.roles());

        return new AuthenticationResponse(token);
    }
}
