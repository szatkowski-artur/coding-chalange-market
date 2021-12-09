package com.szatkowskiartur.utlis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.SneakyThrows;

public class Utils {

    @SneakyThrows
    public static String createJsonWithMessage(String message) {

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode root = mapper.createObjectNode();
        root.put("message", message);

        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(root);
    }

}
