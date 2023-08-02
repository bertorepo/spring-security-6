package com.hubert.customer;

import java.util.List;

public record CustomerDTO(
        Integer id,
        String username,
        String email,
        List<String> roles
) {
}
