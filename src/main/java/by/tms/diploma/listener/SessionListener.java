package by.tms.diploma.listener;


import by.tms.diploma.entity.Tour;
import lombok.extern.slf4j.Slf4j;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.ArrayList;

@Slf4j
public class SessionListener implements HttpSessionListener{
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        session.setAttribute("wishes", new ArrayList<Tour>());
        log.info("Wishes was created");
    }
}
