package com.aspira.yrakovets.parser;

import com.aspira.yrakovets.data.*;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class LeagueParser {
    //TODO refactor with DRY principle
    private final InputStream inputStream;

    public LeagueParser(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public EventContainer getEvents() {
        EventContainer eventContainer = new EventContainer();

        try (InputStreamReader inputReader = new InputStreamReader(inputStream);
             JsonReader reader = new JsonReader(inputReader)) {
            reader.beginObject();
            while (reader.hasNext()) {
                if ("events".equals(reader.nextName())) {
                    eventContainer.setEvents(readEvents(reader));
                } else {
                    reader.skipValue();
                }
            }
            reader.endObject();
        } catch (IOException ignored) {
        }
        return eventContainer;
    }

    private List<Event> readEvents(JsonReader reader) throws IOException {
        List<Event> events = new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()) {
            Event event = new Event();
            reader.beginObject();
            while (reader.hasNext()) {
                switch (reader.nextName()) {
                    case "id" -> event.setId(reader.nextLong());
                    case "name" -> event.setName(reader.nextString());
                    case "kickoff" -> event.setKickoff(reader.nextLong());
                    case "markets" -> event.setMarkets(readMarkets(reader));
                    default -> reader.skipValue();
                }
            }
            reader.endObject();
            events.add(event);
        }
        reader.endArray();
        return events;
    }

    private List<Market> readMarkets(JsonReader reader) throws IOException {
        List<Market> markets = new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()) {
            Market market = new Market();
            reader.beginObject();
            while (reader.hasNext()) {
                switch (reader.nextName()) {
                    case "id" -> market.setId(reader.nextLong());
                    case "name" -> market.setName(reader.nextString());
                    case "runners" -> market.setRunners(readRunners(reader));
                    default -> reader.skipValue();
                }
            }
            reader.endObject();
            markets.add(market);
        }
        reader.endArray();
        return markets;
    }

    private List<Runner> readRunners(JsonReader reader) throws IOException {
        List<Runner> runners = new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()) {
            Runner runner = new Runner();
            reader.beginObject();
            while (reader.hasNext()) {
                switch (reader.nextName()) {
                    case "id" -> runner.setId(reader.nextLong());
                    case "name" -> runner.setName(reader.nextString());
                    case "price" -> runner.setPrice(reader.nextDouble());
                    default -> reader.skipValue();
                }
            }
            reader.endObject();
            runners.add(runner);
        }
        reader.endArray();
        return runners;
    }
}
