package oo.kr.shared.global.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import lombok.RequiredArgsConstructor;
import oo.kr.shared.global.exception.type.json.JsonDeserializationFailedException;
import oo.kr.shared.global.exception.type.json.JsonSerializationFailedException;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JsonUtils {

  private final ObjectMapper objectMapper;

  public String serializeObjectToJson(Object object) {
    try {
      return objectMapper.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      throw new JsonSerializationFailedException();
    }
  }

  public <T> T convertValue(Object object, Class<T> clazz) {
    try {
      return objectMapper.convertValue(object, clazz);

    } catch (IllegalArgumentException e) {
      throw new JsonDeserializationFailedException(e);
    }
  }

  public <T> T deserializeJsonToObject(InputStream inputStream, Class<T> clazz) {
    try {
      return objectMapper.readValue(inputStream, clazz);
    } catch (IOException e) {
      throw new JsonDeserializationFailedException(e);
    }
  }
}