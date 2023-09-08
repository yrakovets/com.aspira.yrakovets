package com.aspira.yrakovets.parser;

import com.aspira.yrakovets.data.*;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LeagueParser extends AbstractParser {

    public LeagueParser(InputStream inputStream) {
        super(inputStream);
    }

    public EventContainer getEvents() {
        try (InputStreamReader inputReader = new InputStreamReader(inputStream);
             JsonReader reader = new JsonReader(inputReader)) {

            Map<String, ReadingConsumer<EventContainer>> map = new HashMap<>();
            map.put("events", (passedReader, container) -> container.setEvents(readEvents(passedReader)));

            return readEntity(reader, EventContainer::new, map);
        } catch (IOException ignored) {
        }
        return new EventContainer();
    }

    private List<Event> readEvents(JsonReader reader) throws IOException {

        Map<String, ReadingConsumer<Event>> map = new HashMap<>();
        map.put("id", (passedReader, event) -> event.setId(reader.nextLong()));
        map.put("name", (passedReader, event) -> event.setName(reader.nextString()));
        map.put("kickoff", (passedReader, event) -> event.setKickoff(reader.nextLong()));
        map.put("markets", (passedReader, event) -> event.setMarkets(readMarkets(reader)));

        return readList(reader, Event::new, map);
    }

    private List<Market> readMarkets(JsonReader reader) throws IOException {

        Map<String, ReadingConsumer<Market>> map = new HashMap<>();
        map.put("id", (passedReader, market) -> market.setId(reader.nextLong()));
        map.put("name", (passedReader, market) -> market.setName(reader.nextString()));
        map.put("runners", (passedReader, market) -> market.setRunners(readRunners(reader)));

        return readList(reader, Market::new, map);
    }

    private List<Runner> readRunners(JsonReader reader) throws IOException {

        Map<String, ReadingConsumer<Runner>> map = new HashMap<>();
        map.put("id", (passedReader, runner) -> runner.setId(reader.nextLong()));
        map.put("name", (passedReader, runner) -> runner.setName(reader.nextString()));
        map.put("price", (passedReader, runner) -> runner.setPrice(reader.nextDouble()));

        return readList(reader, Runner::new, map);
    }
}
