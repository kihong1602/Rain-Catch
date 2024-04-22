package oo.kr.shared.global.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CodeCreator {

  private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

  public static String createMerchantUid(String email) {
    String shuffledEmail = shuffleEmail(email);
    String today = LocalDateTime.now()
                                .format(DATE_TIME_FORMATTER);
    return """
        umbrella_%s%s""".formatted(shuffledEmail, today);
  }

  private static String shuffleEmail(String email) {
    StringBuilder sb = new StringBuilder();
    char[] chars = email.substring(0, email.indexOf("@"))
                        .toCharArray();
    List<Character> parseEmail = new ArrayList<>();
    for (char token : chars) {
      parseEmail.add(token);
    }
    Collections.shuffle(parseEmail);
    parseEmail.forEach(sb::append);
    return sb.toString();
  }
}
