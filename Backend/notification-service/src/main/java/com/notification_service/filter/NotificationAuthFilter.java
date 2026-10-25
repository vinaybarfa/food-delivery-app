package com.notification_service.filter;

import com.notification_service.constants.ApplicationConstants;
import com.notification_service.model.context.NotificationContext;
import com.notification_service.model.context.NotificationContextHolder;
import com.notification_service.utils.CommonUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class NotificationAuthFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (isValidApi(request.getRequestURI())) {
            String xTenantId = request.getHeader(ApplicationConstants.X_TENANT_ID);
            if (CommonUtils.isEmpty(xTenantId)) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.getWriter().write("UnAuthorize! API Key is required");
            }
            NotificationContextHolder.setContext(new NotificationContext(xTenantId, false));
        }
        filterChain.doFilter(request, response);

        if (request.getRequestURI().startsWith(request.getRequestURI())) {
            NotificationContextHolder.clear();
        }
    }

    static boolean isValidApi(final String apiPath) {
        return apiPath.startsWith("/api");
    }


}
