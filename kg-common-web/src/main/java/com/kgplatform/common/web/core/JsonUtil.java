package com.kgplatform.common.web.core;

/**
 *
 * JsonUtil
 *
 * @author chen
 * @since 2026-04-23 17:09:11
 */

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kgplatform.common.web.core.AppConstant;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.*;

/**
 * Jackson工具类
 *
 * @author Chill
 */
@Slf4j
public final class JsonUtil {
    private JsonUtil() {
    }

    private static final String LEFT_SQ_BRACKET = "[";
    private static final String RIGHT_SQ_BRACKET = "]";

    /**
     * 将对象序列化成json字符串
     *
     * @param value javaBean
     * @param <T>   T 泛型标记
     * @return jsonString json字符串
     */
    public static <T> String toJson(T value) {
        try {
            return getInstance().writeValueAsString(value);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 将对象序列化成 json byte 数组
     *
     * @param object javaBean
     * @return jsonString json字符串
     */
    @SneakyThrows
    public static byte[] toJsonAsBytes(Object object) {
        return getInstance().writeValueAsBytes(object);
    }

    /**
     * 将json反序列化成对象
     *
     * @param content   content
     * @param valueType class
     * @param <T>       T 泛型标记
     * @return Bean
     */
    public static <T> T parse(String content, Class<T> valueType) {
        try {
            return getInstance().readValue(content, valueType);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 将json反序列化成对象
     *
     * @param content       content
     * @param typeReference 泛型类型
     * @param <T>           T 泛型标记
     * @return Bean
     */
    @SneakyThrows
    public static <T> T parse(String content, TypeReference<T> typeReference) {
        return getInstance().readValue(content, typeReference);
    }

    /**
     * 将json byte 数组反序列化成对象
     *
     * @param bytes     json bytes
     * @param valueType class
     * @param <T>       T 泛型标记
     * @return Bean
     */
    @SneakyThrows
    public static <T> T parse(byte[] bytes, Class<T> valueType) {
        return getInstance().readValue(bytes, valueType);
    }

    /**
     * 将json反序列化成对象
     *
     * @param bytes         bytes
     * @param typeReference 泛型类型
     * @param <T>           T 泛型标记
     * @return Bean
     */
    @SneakyThrows
    public static <T> T parse(byte[] bytes, TypeReference<T> typeReference) {
        return getInstance().readValue(bytes, typeReference);
    }

    /**
     * 将json反序列化成对象
     *
     * @param in        InputStream
     * @param valueType class
     * @param <T>       T 泛型标记
     * @return Bean
     */
    @SneakyThrows
    public static <T> T parse(InputStream in, Class<T> valueType) {
        return getInstance().readValue(in, valueType);
    }

    /**
     * 将json反序列化成对象
     *
     * @param in            InputStream
     * @param typeReference 泛型类型
     * @param <T>           T 泛型标记
     * @return Bean
     */
    @SneakyThrows
    public static <T> T parse(InputStream in, TypeReference<T> typeReference) {
        return getInstance().readValue(in, typeReference);
    }

    /**
     * 将json反序列化成List对象
     *
     * @param content      content
     * @param valueTypeRef class
     * @param <T>          T 泛型标记
     * @return List
     */
    public static <T> List<T> parseArray(String content, Class<T> valueTypeRef) {
        try {

            if (!StringUtils.startsWithIgnoreCase(content, LEFT_SQ_BRACKET)) {
                content = LEFT_SQ_BRACKET + content + RIGHT_SQ_BRACKET;
            }

            List<Map<String, Object>> list = getInstance().readValue(content,
                    new TypeReference<List<Map<String, Object>>>() {
                    });
            List<T> result = new ArrayList<>();
            for (Map<String, Object> map : list) {
                result.add(toPojo(map, valueTypeRef));
            }
            return result;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    /**
     * 转成{@code List<T> }
     *
     * @param content      json串
     * @param valueTypeRef 类型
     * @param <T>          类型
     * @return List
     */
    @SneakyThrows
    public static <T> List<T> toList(String content, Class<T> valueTypeRef) {
        return getInstance().readValue(content, new TypeReference<List<T>>() {
        });
    }

    /**
     * 将字符串转换成 map
     *
     * @param content 字符串
     * @return map对象
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> toMap(String content) {
        try {
            return getInstance().readValue(content, Map.class);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 将字符串转换成Map，value指定对象
     *
     * @param content      字符串
     * @param valueTypeRef 对象 类型
     * @param <T>          对象类型
     * @return 转换成Map
     */
    public static <T> Map<String, T> toMap(String content, Class<T> valueTypeRef) {
        try {
            Map<String, Map<String, Object>> map = getInstance().readValue(content,
                    new TypeReference<Map<String, Map<String, Object>>>() {
                    }
            );
            Map<String, T> result = new HashMap<>(16);
            for (Map.Entry<String, Map<String, Object>> entry : map.entrySet()) {
                result.put(entry.getKey(), toPojo(entry.getValue(), valueTypeRef));
            }
            return result;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public static <T> T toPojo(Map<?, ?> fromValue, Class<T> toValueType) {
        return getInstance().convertValue(fromValue, toValueType);
    }

    /**
     * 将json字符串转成 JsonNode
     *
     * @param jsonString jsonString
     * @return jsonString json字符串
     */
    @SneakyThrows
    public static JsonNode readTree(String jsonString) {
        return getInstance().readTree(jsonString);
    }

    /**
     * 将json字符串转成 JsonNode
     *
     * @param in InputStream
     * @return jsonString json字符串
     */
    @SneakyThrows
    public static JsonNode readTree(InputStream in) {
        return getInstance().readTree(in);
    }

    /**
     * 将json字符串转成 JsonNode
     *
     * @param content content
     * @return jsonString json字符串
     */
    @SneakyThrows
    public static JsonNode readTree(byte[] content) {
        return getInstance().readTree(content);
    }

    /**
     * 将json字符串转成 JsonNode
     *
     * @param jsonParser JsonParser
     * @return jsonString json字符串
     */
    @SneakyThrows
    public static JsonNode readTree(JsonParser jsonParser) {
        return getInstance().readTree(jsonParser);
    }

    public static ObjectMapper getInstance() {
        return JacksonHolder.INSTANCE;
    }

    private static class JacksonHolder {
        private static final ObjectMapper INSTANCE = new JacksonObjectMapper();
    }

    public static class JacksonObjectMapper extends ObjectMapper {
        private static final long serialVersionUID = 4288193147502386170L;

        private static final Locale CHINA = Locale.CHINA;

        /**
         * JacksonObjectMapper
         */
        public JacksonObjectMapper() {
            super();
            //设置地点为中国
            super.setLocale(CHINA);
            //去掉默认的时间戳格式
            super.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            //设置为中国上海时区
            super.setTimeZone(TimeZone.getTimeZone(ZoneId.systemDefault()));
            //序列化时，日期的统一格式
            super.setDateFormat(new SimpleDateFormat(AppConstant.FORMAT_PATTERN_DATE_TIME, Locale.CHINA));
            super.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            //序列化处理
            super.configure(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature(), true);
            super.configure(JsonReadFeature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER.mappedFeature(), true);
            super.findAndRegisterModules();
            //失败处理
            super.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            super.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            //单引号处理
            super.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
            super.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT,
                    DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
            //反序列化时，属性不存在的兼容处理s
            super.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                    DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES);
            //日期格式化
            super.registerModule(new JavaTimeModule());
            super.findAndRegisterModules();
        }
    }

}
