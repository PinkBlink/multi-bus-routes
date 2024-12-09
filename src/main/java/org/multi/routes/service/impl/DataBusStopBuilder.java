package org.multi.routes.service.impl;

import org.multi.routes.model.BusStop;
import org.multi.routes.service.DataEntityParser;

import java.util.ArrayList;
import java.util.List;

public class DataBusStopBuilder {
    private List<BusStop> stops = new ArrayList<>();
    private DataEntityParser dataEntityParser = new DataEntityParserImpl();
    public List<BusStop> getBusStopsFromData(){
        return dataEntityParser.getBusStopsFromData();
    }
    public List<BusStop> getStopsFromData(){
        return stops;
    }
}