package cn.com.geovis.geoiot.sender.pojo;

import lombok.Data;

@Data
public class GvEvent {
    //数据来源
    private String source_id;
    //数据格式
    private String target_type;
    //目标id
    private String target_id;
    //目标名称
    private String target_name;
    //数据采集时间
    private Long target_time;
    //位置经度
    private Double lon;
    //位置维度
    private Double lat;
    //位置高度
    private Double alt;
    //方位角
    private Double heading;
    //扩展字段
    private String extend;


    /**
     * 使用遍历的校验方式，如果校验的数据类型比较多，可以考虑使用反射封装程统一的方法
     *
     * @return
     */
    public ValidateField checkValidate() {
        ValidateField validateField;

        if (null == this.getSource_id() || "" == this.getSource_id()) {
            validateField = ValidateField.source_id;
        } else if (null == this.getTarget_type() ) {
            validateField = ValidateField.target_type;
        } else if (null == this.getTarget_id() || "" == this.getTarget_id()) {
            validateField = ValidateField.target_id;
        } else if (null == this.getTarget_name() ) {
            validateField = ValidateField.target_name;
        } else if (null == this.getTarget_time() || this.getTarget_time() == 0L) {
            validateField = ValidateField.target_time;
        }  else if (null == this.getLon() || this.getLon() < -180.0 || this.getLon() > 180.0) {
            validateField = ValidateField.lon;
        } else if (null == this.getLat() || this.getLat() < -90.0 || this.getLat() > 90.0) {
            validateField = ValidateField.lat;
        } else if (this.getAlt() == null) {
            validateField = ValidateField.alt;
        } else if (this.getHeading() == null) {
            validateField = ValidateField.heading;
        } else if (null == this.getExtend() ) {
            validateField = ValidateField.extend;
        } else {
            validateField = ValidateField.pass;
        }
        return validateField;
    }

    public enum ValidateField {
        pass(0, "验证通过"),
        source_id(1, "source_id 数据来源不能为空"),
        target_type(2, "target_type 数据格式不能为空"),
        target_id(3, " target_id 目标id不能为空"),
        target_name(4, "target_name 目标名称不能为空"),
        target_time(5, "target_time 数据采集时间不能为空"),
        lon(6, "lon 位置经度超出合法范围（-180~180）"),
        lat(7, "lat 位置维度超出合法范围（-90~90）"),
        alt(8, "alt 高程不能为空"),
        heading(8, "heading 高程方位角不能为空"),
        extend(8, "扩展字段不能为空");

        private ValidateField(int fieldNum, String fieldMsg) {
            this.fieldNum = fieldNum;
            this.fieldMsg = fieldMsg;
        }

        private int fieldNum;
        private String fieldMsg;


        public int getFieldNum() {
            return fieldNum;
        }

        public void setFieldNum(int fieldNum) {
            this.fieldNum = fieldNum;
        }

        public String getFieldMsg() {
            return fieldMsg;
        }

        public void setFieldMsg(String fieldMsg) {
            this.fieldMsg = fieldMsg;
        }
    }


}
