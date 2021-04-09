package by.tms.diploma.listener;

import by.tms.diploma.entity.Basket;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
@Slf4j
public class Listener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        se.getSession().setAttribute("basket", new Basket());
        log.info((se.getSession().getAttribute("basket")).toString());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
    }
}
