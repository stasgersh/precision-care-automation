package com.ge.onchron.verif.utils;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import lombok.SneakyThrows;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class JsonUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger( JsonUtils.class );

    @SneakyThrows
    public static String setFieldValue( String json, String jsonPath, String value ) {
        List<String> pathArray = Arrays.stream( jsonPath.split( "(?<!\\\\)[\\[\\].]" ) ).filter( s -> !s.isBlank() ).map( s -> s.replace( "\\", "" ) ).toList();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree( json );
        if ( rootNode == null ) return null;
        JsonNode currNode = rootNode;
        for ( int i = 0; i < pathArray.size() - 1; i++ ) {
            String pathElement = pathArray.get( i );
            if ( pathElement.matches( "\\d+" ) ) {
                currNode = currNode.get( Integer.parseInt( pathElement ) );
            } else {
                currNode = currNode.get( pathElement );
            }
            if ( currNode == null ) return rootNode.toPrettyString();
        }
        if ( value != null ) {
            ((ObjectNode) currNode).set( pathArray.getLast(), mapper.readTree( value ) );
        } else {
            ((ObjectNode) currNode).remove( pathArray.getLast() );
        }

        return rootNode.toPrettyString();
    }

    @SneakyThrows
    public static String getFieldValue( String json, String jsonPath ) {
        List<String> pathArray = Arrays.stream( jsonPath.split( "(?<!\\\\)[\\[\\].]" ) ).filter( s -> !s.isBlank() ).map( s -> s.replace( "\\", "" ) ).toList();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode currNode = mapper.readTree( json );
        for ( int i = 0; i < pathArray.size(); i++ ) {
            String pathElement = pathArray.get( i );
            if ( pathElement.matches( "\\d+" ) ) {
                currNode = currNode.get( Integer.parseInt( pathElement ) );
            } else {
                currNode = currNode.get( pathElement );
            }
            if ( currNode == null && i < pathArray.size() - 1 ) {
                LOGGER.warn( STR."Path \{jsonPath} not found! \{pathElement} is null" );
                return null;
            }
        }
        return currNode == null ? null : currNode instanceof TextNode ? currNode.textValue() : currNode.toString();
    }

    public static Object getJsonByType( String json ) {
        if ( isJsonArray( json ) ) {
            return new JSONArray( json );
        } else if ( isJsonObject( json ) ) {
            return new JSONObject( json );
        } else {
            throw new IllegalArgumentException( "Invalid JSON format." );
        }
    }

    private static boolean isJsonArray( String json ) {
        try {
            new JSONArray( json );
            return true;
        } catch ( Exception e ) {
            return false;
        }
    }

    private static boolean isJsonObject( String json ) {
        try {
            new JSONObject( json );
            return true;
        } catch ( Exception e ) {
            return false;
        }
    }

    public static JsonNode removeKeys(JsonNode node, List<String> keysToRemove) {

        if (node.isObject()) {
            ObjectNode objectNode = (ObjectNode) node;
            List<String> fieldNames = new ArrayList<>();
            node.fieldNames().forEachRemaining(fieldNames::add);

            for (String field : fieldNames) {
                JsonNode childNode = objectNode.get(field);
                if (keysToRemove.contains(field)) {
                    objectNode.remove(field);
                } else {
                    removeKeys(childNode, keysToRemove);
                }
            }
            return objectNode;

        } else if (node.isArray()) {
            for (JsonNode item : node) {
                removeKeys(item, keysToRemove);
            }
        }
        return node;
    }

    public static OffsetDateTime parseToOffsetDateTime(String s) {
        if (s == null) {
            throw new IllegalArgumentException("date string is null");
        }
        String trimmed = s.trim();
        // ISO_OFFSET_DATE_TIME handles offsets and 'Z'
        return OffsetDateTime.parse(trimmed, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }
}