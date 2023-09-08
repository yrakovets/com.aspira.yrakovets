package com.aspira.yrakovets.parser;

import com.aspira.yrakovets.data.League;
import com.aspira.yrakovets.data.Region;
import com.aspira.yrakovets.data.Sport;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SportParser extends AbstractParser {

    public SportParser(InputStream inputStream) {
        super(inputStream);
    }

    public List<Sport> getSportList() {
        try (InputStreamReader inputReader = new InputStreamReader(inputStream);
                JsonReader reader = new JsonReader(inputReader)) {

            Map<String, ReadingConsumer<Sport>> map = new HashMap<>();

            map.put("id", (passedReader, sport) -> sport.setId(reader.nextLong()));
            map.put("name", (passedReader, sport) -> sport.setName(reader.nextString()));
            map.put("regions", (passedReader, sport) -> sport.setRegions(readRegions(reader)));

            return readList(reader, Sport::new, map);
        } catch (IOException ignored) {
        }
        return new ArrayList<>();
    }

    private List<Region> readRegions(JsonReader reader) throws IOException {

        Map<String, ReadingConsumer<Region>> map = new HashMap<>();
        map.put("id", (passedReader, region) -> region.setId(reader.nextLong()));
        map.put("leagues", (passedReader, region) -> region.setLeagues(readLeagues(reader)));

        return readList(reader, Region::new, map);
    }

    private List<League> readLeagues(JsonReader reader) throws IOException {

        Map<String, ReadingConsumer<League>> map = new HashMap<>();
        map.put("id", (passedReader, league) -> league.setId(reader.nextLong()));
        map.put("name", (passedReader, league) -> league.setName(reader.nextString()));
        map.put("top", (passedReader, league) -> league.setTop(reader.nextBoolean()));

        return readList(reader, League::new, map);
    }
}
