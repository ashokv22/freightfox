package com.freightfox.demo.feign;

import feign.FeignException;
import feign.Logger;
import feign.Response;
import feign.codec.Decoder;
import feign.gson.GsonDecoder;
import feign.okhttp.OkHttpClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.lang.reflect.Type;

@Slf4j
public class DefaultConfiguration {

    @Bean
    public OkHttpClient client() {
        return new OkHttpClient();
    }
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
    @Bean
    public Decoder feignDecoder() {
        return new CustomDecoder(new GsonDecoder());
    }

    static class CustomDecoder implements Decoder {

        private final Decoder jsonDecoder;

        CustomDecoder(Decoder jsonDecoder) {
            this.jsonDecoder = jsonDecoder;
        }

        @Override
        public Object decode(Response response, Type type) throws IOException, FeignException {
            if (response.status() == HttpStatus.OK.value()) {
                log.debug("Received response: " + response.body() + " of type: "+ response.getClass());
                String contentType = response.headers().get("Content-Type").iterator().next();
                if (contentType.contains("json")) {
                    return jsonDecoder.decode(response, type);
                } else {
                    // Handle XML decoding here if needed
                    // You can throw an exception if the response type is not supported
                    log.info("Received Unsupported response type: " + contentType);
                    throw new UnsupportedOperationException("XML decoding is not supported yet");
                }
            } else {
                throw new RuntimeException(response.status() + ", Error decoding response: " + response.request());
            }
        }
    }

}
