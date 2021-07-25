package com.getir.onlinebooks.store.common.audit;

import com.getir.onlinebooks.store.security.model.CustomUserDetails;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        String username =
                ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                        .getUser()
                        .getUsername();

        if(username != null) {
            return (Optional.ofNullable(username));
        }else {
            return Optional.empty();
        }
    }
}

