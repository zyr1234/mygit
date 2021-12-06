package com.geovis.jg.mb.mapper;

import com.geovis.jg.common.mapper.BaseMapper;
import com.geovis.jg.mb.model.SzdqMbZxdzbPoint;

import java.util.List;


public interface SzdqMbZxdzbPointMapper extends BaseMapper<SzdqMbZxdzbPoint> {

	List<SzdqMbZxdzbPoint> queryMbByPoly(SzdqMbZxdzbPoint t) throws Exception;

}
