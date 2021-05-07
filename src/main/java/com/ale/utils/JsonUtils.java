package com.ale.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Slf4j
public class JsonUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        // 1、不序列化字段值为null的字段。 如：{name:zs,age:null} --> {name:zs}
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //2、配置为true表示mapper允许接受只有一个元素的数组的反序列化
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
//        //3、配置为false表示mapper在遇到mapper对象中存在json对象中没有的数据变量时不报错，可以进行反序列化
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //4、定义针对日期类型的反序列化时的数据格式
        //objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        // 允许使用不带引号的字段名称。 如：{name:zs,age:26,sex:1}
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
    }

    /**
     * Object 转 json
     *
     * @param obj
     * @return
     */
    public static String parse2Json(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error(String.format("对象转json字符串失败！%s", e.getMessage()), e);
        }
        return null;
    }


    /**
     * json 转 Object
     *
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T parse2Object(String json, Class<T> clazz) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        try {
            return objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * JSON Array字符串反序列化对象
     *
     * @param json
     * @param typeRef
     * @param <T>
     * @return
     */
    public static <T> T getObjectFromJson(String json, TypeReference<T> typeRef) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        try {
            return objectMapper.readValue(json, typeRef);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

}
