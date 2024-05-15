package com.amalitech.securesail.amalisecuresail.logging.services;

import com.amalitech.securesail.amalisecuresail.logging.constants.EventType;
import com.amalitech.securesail.amalisecuresail.global.constants.OperationResultConstants;
import com.amalitech.securesail.amalisecuresail.global.context_holders.HttpRequestContextHolder;
import com.amalitech.securesail.amalisecuresail.logging.UserLog;
import com.amalitech.securesail.amalisecuresail.logging.repositories.UserLogRepository;
import com.amalitech.securesail.amalisecuresail.user_management.models.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserLogService {
//    TODO: add location
    private final UserLogRepository userLogRepository;

    public void logUserEvent(User user, OperationResultConstants constant, EventType eventType) {
        HttpServletRequest request = HttpRequestContextHolder.getRequest();
        UserLog userLog = new UserLog();
        userLog.setDateOccurred(LocalDateTime.now());
        userLog.setPerformedBy(user);
        userLog.setStatus(constant);
        userLog.setEventType(eventType);
        userLog.setReferrer(request.getHeader("referer"));
        userLog.setIpAddress(request.getRemoteAddr());
        userLog.setUserAgent(request.getHeader("User-Agent"));
        userLog.setLocation(request.getRemoteAddr());
        try {
            userLogRepository.save(userLog);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
    }
}
