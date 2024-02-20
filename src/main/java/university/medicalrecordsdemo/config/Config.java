package university.medicalrecordsdemo.config;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.modelmapper.Converter;

@Configuration
public class Config {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());

       // LocalDate to String mapping
        Converter<LocalDate, String> localDateToStringConverter = context ->
                context.getSource() == null ? null : context.getSource().format(DateTimeFormatter.ISO_LOCAL_DATE);
        modelMapper.addConverter(localDateToStringConverter);

        // String to LocalDate mapping
        Converter<String, LocalDate> stringToLocalDateConverter = context ->
                context.getSource() == null || context.getSource().isEmpty() ? null :
                        LocalDate.parse(context.getSource(), DateTimeFormatter.ISO_LOCAL_DATE);
        modelMapper.addConverter(stringToLocalDateConverter);
        
        return modelMapper;
    }
}
