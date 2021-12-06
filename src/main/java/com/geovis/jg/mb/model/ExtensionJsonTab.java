package com.geovis.jg.mb.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExtensionJsonTab {
    private Long id;

    private String mbnm;

    private Byte type;

    private String jsonContent;

    private List<ExtensionJson> jsonList;

    private String jsonListString;

}