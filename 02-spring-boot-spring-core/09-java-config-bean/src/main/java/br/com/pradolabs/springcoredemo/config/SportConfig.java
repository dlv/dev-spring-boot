package br.com.pradolabs.springcoredemo.config;

import br.com.pradolabs.springcoredemo.common.Coach;
import br.com.pradolabs.springcoredemo.common.SwimCoach;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SportConfig {

    @Bean("aquatic")
    public Coach swimCoach() {
        return new SwimCoach();
    }
}
