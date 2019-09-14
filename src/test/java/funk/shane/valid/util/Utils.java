package funk.shane.valid.util;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Utils {
  private static final ObjectMapper mapper = 
    new ObjectMapper().disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

	public static String readJson(final String fileName) throws IOException {
		return FileUtils.readFileToString(new ClassPathResource("/funk/shane/pojo/" + fileName, Utils.class).getFile(),
			StandardCharsets.UTF_8);
	}

  public static <T> T getClassFromJsonResource(Class<T> klass, String jsonFile) {
		try {
			final String json = readJson(jsonFile);
			return mapper.readValue(json, klass);
		} catch (IOException e) {
			log.error("readJson failed for file [{}]", jsonFile, e);
			fail(String.format("readJson failed for file [%s]: go fix it", jsonFile));
			return null;
		}
  }
}