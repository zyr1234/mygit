package cn.com.geovis.geoiot.other;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Sb {
    Boolean nullable;
    int length;
    String type;
    List<String> value;
}
