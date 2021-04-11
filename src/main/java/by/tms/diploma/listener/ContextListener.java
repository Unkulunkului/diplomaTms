package by.tms.diploma.listener;


import by.tms.diploma.entity.ClientRequest;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.ArrayList;


@Slf4j
public class ContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().setAttribute("basketWithClients", new ArrayList<ClientRequest>());
    }
}
