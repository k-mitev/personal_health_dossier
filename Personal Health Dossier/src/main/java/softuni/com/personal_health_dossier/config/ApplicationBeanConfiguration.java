package softuni.com.personal_health_dossier.config;

import com.google.gson.*;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import softuni.com.personal_health_dossier.util.ValidationUtil;
import softuni.com.personal_health_dossier.util.ValidationUtilImpl;


import javax.validation.Validation;
import javax.validation.Validator;



@Configuration
public class ApplicationBeanConfiguration {


    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public Gson gson() {
        return new GsonBuilder()
                //.excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
//                .registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
//                    @Override
//                    public LocalDateTime deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
//                        return LocalDateTime
//                                .parse(jsonElement.getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//                    }
//                }).registerTypeAdapter(LocalDateTime.class, new JsonSerializer<LocalDateTime>() {
//                    @Override
//                    public JsonElement serialize(LocalDateTime localDateTime, Type type, JsonSerializationContext jsonSerializationContext) {
//                        return jsonSerializationContext
//                                .serialize(localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));
//                    }
//                }).registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {
//                    @Override
//                    public LocalDate deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
//                        return LocalDate
//                                .parse(jsonElement.getAsString(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
//                    }
//                })
                .create();
    }

    @Bean
    public PasswordEncoder create() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public Validator validator() {
        return Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Bean
    public ValidationUtil validationUtil() {
        return new ValidationUtilImpl(validator());
    }


}
