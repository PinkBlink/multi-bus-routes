package org.multi.routes.service.impl;

import org.apache.logging.log4j.core.appender.routing.Routes;
import org.multi.routes.model.Bus;

import java.util.List;

public class DataBusBuilder {
    private List<Bus> buses;

    public DataBusBuilder(List<Bus> buses) {
        this.buses = buses;
    }
    public List<Bus> getBuses(){
        return buses;
    }
    public List<Bus> setRoutes(List<Routes> routes){

    }
}
