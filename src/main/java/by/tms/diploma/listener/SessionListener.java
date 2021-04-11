package by.tms.diploma.listener;


import by.tms.diploma.entity.Tour;
import by.tms.diploma.entity.User;
import by.tms.diploma.entity.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.ArrayList;

@Slf4j
public class SessionListener implements HttpSessionListener{
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        session.setAttribute("basketWithTour", new ArrayList<Tour>());
        log.info(session.getAttribute("basketWithTour").toString());
    }
}
