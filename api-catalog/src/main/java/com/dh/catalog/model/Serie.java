package com.dh.catalog.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "CatalogSeries")
public class Serie {
    @Id
    public String id;
    public String name;
    public String genre;
    public List<Season> seasons;

    @Data
    public static class Season{
        public Integer seasonNumber;
        public List<Chapter> chapters;

        @Data
        public static class Chapter{
            public String name;
            public Integer number;
            public String urlStream;
        }
    }
}
