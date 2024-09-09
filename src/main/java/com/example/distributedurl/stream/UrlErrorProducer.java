package com.example.distributedurl.stream;

import com.example.distributedurl.model.UrlError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UrlErrorProducer {
    private final StreamBridge streamBridge;

    public void send(UrlError urlError) {
        streamBridge.send("url-error", urlError);
    }
}
