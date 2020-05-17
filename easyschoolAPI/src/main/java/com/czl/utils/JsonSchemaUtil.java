package com.czl.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import com.alibaba.fastjson.JSONObject;
import com.czl.config.MessageListenerConfig;
import com.czl.entity.ResultEntity;
import com.czl.entity.ValidEntity;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jackson.JsonNodeReader;
import com.github.fge.jsonschema.core.report.ProcessingMessage;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.google.common.base.Preconditions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonSchemaUtil {

    public static HashMap<String, JsonNode> JSON_VALID;

    /**
     * 把Json Schema加载到内存
     *
     * @throws FileNotFoundException
     */
    public static void loadJsonSchema() {
        JSON_VALID = new HashMap<String, JsonNode>();
    }

    /**
     * 读取Json文件
     *
     * @param filePath
     * @return
     */
    public static JsonNode readJsonFile(String filePath) {
        JsonNode instance = null;
        try {
            instance = new JsonNodeReader().fromReader(new FileReader(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return instance;
    }

    public static JsonNode readJsonFile(File file) {
        JsonNode instance = null;
        try {
            instance = new JsonNodeReader().fromReader(new FileReader(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return instance;
    }

    /**
     * Json字符串转换为JsonNode
     *
     * @param jsonString
     * @return
     */
    public static JsonNode loadJsonNode(String jsonString) {
        JsonNode instance = null;
        try {
            instance = JsonLoader.fromString(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return instance;
    }

    public static String validJson(String schemaName, String dataString) {
        JsonNode schema = (JsonNode) JSON_VALID.get(schemaName);
        if (schema == null) {
            ResourceLoader resourceLoader = new DefaultResourceLoader();
            Resource resource = resourceLoader.getResource("classpath:valid/" + schemaName + ".json");
            StringBuffer strBuff = new StringBuffer();
            try (InputStream inputStream = resource.getInputStream()) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                String string = null;
                while ((string = bufferedReader.readLine()) != null) {
                    strBuff.append(string);
                }
                schema = loadJsonNode(strBuff.toString());
                JSON_VALID.put(schemaName, schema);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        JsonNode data = loadJsonNode(dataString);
        return validJson(schema, data);
    }

    /**
     * 对json内容做检查，是否符合要求。
     *
     * @param schema
     * @param data
     * @return
     */
    public static String validJson(JsonNode schema, JsonNode data) {
        try {
            Preconditions.checkNotNull(schema, "未定义数据模板");
            Preconditions.checkNotNull(data, "缺少配置信息");
            log.debug("Json Check, data = " + data + ", schema=" + schema);

            ProcessingReport report = JsonSchemaFactory.byDefault().getValidator().validateUnchecked(schema, data);
            convertMessage(report, schema);
            return null;
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    /***
     * 根据 report里面的错误字段，找到schema对应字段定义的中文提示，显示都前端
     *
     * @param report
     *            校验json 的结果,里面包含错误字段，错误信息。
     * @param schema
     *            原始的schema文件。主要用来读取empty_message,error_message中文信息
     */
    private static void convertMessage(ProcessingReport report, JsonNode schema) {
        Iterator<ProcessingMessage> iter = report.iterator();
        ProcessingMessage processingMessage = null;
        // 保存校验失败字段的信息
        JsonNode schemaErrorFieldJson = null;
        // 原始校验返回的信息
        JsonNode validateResult = null;

        while (iter.hasNext()) {
            processingMessage = iter.next();
            validateResult = processingMessage.asJson();
            // keyword表示 一定是不符合schema规范
            JsonNode keywordNode = validateResult.get("keyword");
            JsonNode levelNode = validateResult.get("level");

            // 排除不是检查错误的情况
            if (null == keywordNode || !"error".equals(levelNode.textValue())) {
                continue;
            }

            // 说明json validate 失败
            String keyword = keywordNode.textValue();
            String item = validateResult.get("instance").get("pointer").textValue();
            String item_title = null;
            String error_message = null;

            schemaErrorFieldJson = findErrorField(schema, validateResult);
            String error_node = null;

            if ("required".equalsIgnoreCase(keyword)) {
                // 如果是require,找到哪个字段缺少了
                if (null == schemaErrorFieldJson) {
                    error_node = validateResult.get("required").get(0).textValue();
                    schemaErrorFieldJson = schema.get("properties").get(error_node);
                    item_title = schemaErrorFieldJson.get("title").textValue();
                } else {
                    error_node = validateResult.get("missing").get(0).textValue();
                    item = error_node;
                    item_title = error_node;
                }
                error_message = MessageListenerConfig.getProperty("ChkMsg101", new String[] { item_title });

            } else {
                if (null != schemaErrorFieldJson) {
                    item_title = schemaErrorFieldJson.get("title").textValue();
                }

                if ("minLength".equalsIgnoreCase(keyword)) {
                    String length = validateResult.get("minLength").toString();
                    error_message = MessageListenerConfig.getProperty("ChkMsg122", new String[] { item_title, length });

                } else if ("maxLength".equalsIgnoreCase(keyword)) {
                    String length = validateResult.get("maxLength").toString();
                    error_message = MessageListenerConfig.getProperty("ChkMsg114", new String[] { item_title, length });

                } else if ("type".equalsIgnoreCase(keyword)) {
                    // 数据类型错误
                    String expected_type = validateResult.get("expected").get(0).textValue();
                    if ("integer".equals(expected_type)) {
                        // ChkMsg107=请在%中输入数字。
                        error_message = MessageListenerConfig.getProperty("ChkMsg107", new String[] { item_title });
                    } else if ("string".equals(expected_type)) {
                        // ChkMsg123=请在%中输入文字。
                        error_message = MessageListenerConfig.getProperty("ChkMsg123", new String[] { item_title });
                    } else {
                        // // 其他失败
                        error_message = MessageListenerConfig.getProperty("ChkMsg102", new String[] { item_title });
                    }
                } else if ("format".equalsIgnoreCase(keyword)) {
                    String expected_type = validateResult.get("expected").get(0).textValue();
                    // ChkMsg105=请在%中输入%格式的日期。
                    error_message = MessageListenerConfig.getProperty("ChkMsg105", new String[] { item_title, expected_type });
                } else if ("pattern".equalsIgnoreCase(keyword)) {
                    JsonNode patternDesc = schemaErrorFieldJson.get("patternDesc");
                    if (null == patternDesc) {
                        // ChkMsg111=请在%中输入正确的数据，格式=%。
                        error_message = MessageListenerConfig.getProperty("ChkMsg102", new String[] { item_title });
                    } else {
                        // ChkMsg111=请在%中输入正确的数据，格式=%。
                        error_message = MessageListenerConfig.getProperty("ChkMsg111", new String[] { item_title, patternDesc.textValue() });
                    }
                } else {
                    // 其他失败
                    error_message = MessageListenerConfig.getProperty("ChkMsg102", new String[] { item_title });
                }
            }

            // 设定检查结果，检查不通过时抛出异常。
            if (!StringUtil.isEmpty(error_message)) {
                ValidEntity validEntity = new ValidEntity(item, item_title, error_message);
                String message = JSONObject.toJSONString(validEntity);
                Preconditions.checkArgument(false, message);
                log.error("Json校验错误=[" + message + "]");
            }

        }
    }

    /***
     * 根据校验结果的 schema pointer 中的url递归寻找JsonNode
     *
     * @param schema
     * @param validateResult
     * @return
     */
    private static JsonNode findErrorField(JsonNode schema, JsonNode validateResult) {
        // 取到的数据是
        String[] split = validateResult.get("schema").get("pointer").textValue().split("/");
        JsonNode tempNode = null;
        if (split != null && split.length > 0) {
            for (int i = 1; i < split.length; i++) {
                if (i == 1) {
                    tempNode = read(schema, validateResult, split[i]);
                } else {
                    tempNode = read(tempNode, validateResult, split[i]);
                }
            }
        }
        return tempNode;
    }

    private static JsonNode read(JsonNode jsonNode, JsonNode validateResult, String fieldName) {
        return jsonNode.get(fieldName);
    }

    public static ResultEntity convertValidResult(String checkResult) {
        Object obj = JSONObject.parseObject(checkResult);
        ResultEntity entity = new ResultEntity("7026", "ApiMsg7026", null, obj);
        return entity;
    }
}
