package com.example.delay.configuration;

import com.example.delay.common.UserDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * @author dewey
 */
@Component
public class TestConver implements Converter<String, UserDto> {

    @Override
    public UserDto convert(String source) {
        UserDto result = new UserDto();
        result.setAge(1);
        result.setName("test");
        return result;
    }
}
