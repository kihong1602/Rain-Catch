package oo.kr.shared.global.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import lombok.RequiredArgsConstructor;
import oo.kr.shared.global.exception.type.json.JsonDeserializationFailedException;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JsonUtils {

  private final ObjectMapper objectMapper;

  public <T> T deserializeJsonToObject(InputStream inputStream, Class<T> clazz) {
    try {
      return objectMapper.readValue(inputStream, clazz);
    } catch (IOException e) {
      throw new JsonDeserializationFailedException(e);
    }
  }
}