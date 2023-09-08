package com.aspira.yrakovets.parser;

import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class AbstractParser {

    protected final InputStream inputStream;

    public AbstractParser(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    protected <T> List<T> readList(JsonReader reader, Supplier<T> instanceConstructor,
                                   Map<String, ReadingConsumer<T>> map) throws IOException {
        List<T> list = new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()) {

            T entity = readEntity(reader, instanceConstructor, map);

            list.add(entity);
        }
        reader.endArray();
        return list;
    }

    protected  <T> T readEntity(JsonReader reader, Supplier<T> instanceConstructor, Map<String, ReadingConsumer<T>> map) throws IOException {
        T entity = instanceConstructor.get();
        reader.beginObject();
        while (reader.hasNext()) {
            String fieldName = reader.nextName();
            if (map.containsKey(fieldName)){
                map.get(fieldName).accept(reader, entity);
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return entity;
    }

    protected interface ReadingConsumer<T>{
        void accept(JsonReader jsonReader, T t) throws IOException;
    }
}
