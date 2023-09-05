package com.aspira.yrakovets.parser;

import com.aspira.yrakovets.data.League;
import com.aspira.yrakovets.data.Region;
import com.aspira.yrakovets.data.Sport;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SportParser {

    //TODO refactor with DRY principle


    private final InputStream inputStream;

    public SportParser(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public List<Sport> getSportList() {
        List<Sport> sports = new ArrayList<>();
        try (InputStreamReader inputReader = new InputStreamReader(inputStream);
                JsonReader reader = new JsonReader(inputReader)) {
            reader.beginArray();
            while (reader.hasNext()) {
                Sport sport = new Sport();
                reader.beginObject();
                while (reader.hasNext()) {
                    switch (reader.nextName()) {
                        case "id" -> sport.setId(reader.nextLong());
                        case "name" -> sport.setName(reader.nextString());
                        case "regions" -> sport.setRegions(readRegions(reader));
                        default -> reader.skipValue();
                    }
                }
                reader.endObject();
                sports.add(sport);
            }
            reader.endArray();
        } catch (IOException ignored) {
        }
        return sports;
    }

    private List<Region> readRegions(JsonReader reader) throws IOException {
        ArrayList<Region> regions = new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()) {
            regions.add(readRegion(reader));
        }
        reader.endArray();
        return regions;
    }

    private Region readRegion(JsonReader reader) throws IOException {
        Region region = new Region();
        reader.beginObject();
        while (reader.hasNext()) {
            switch (reader.nextName()) {
                case "id" -> region.setId(reader.nextLong());
                case "leagues" -> region.setLeagues(readLeagues(reader));
                default -> reader.skipValue();
            }
        }
        reader.endObject();
        return region;
    }

    private List<League> readLeagues(JsonReader reader) throws IOException {
        ArrayList<League> leagues = new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()) {
            leagues.add(readLeague(reader));
        }
        reader.endArray();
        return leagues;
    }

    private League readLeague(JsonReader reader) throws IOException {
        League league = new League();
        reader.beginObject();
        while (reader.hasNext()) {
            switch (reader.nextName()) {
                case "id" -> league.setId(reader.nextLong());
                case "name" -> league.setName(reader.nextString());
                case "top" -> league.setTop(reader.nextBoolean());
                default -> reader.skipValue();
            }
        }
        reader.endObject();
        return league;
    }
}
