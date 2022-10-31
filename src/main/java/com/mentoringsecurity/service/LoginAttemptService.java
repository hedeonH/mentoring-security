package com.mentoringsecurity.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;


@Service
public class LoginAttemptService {

    private static final int MAX_ATTEMPT = 3;

    private final LoadingCache<String, Integer> attemptsCache;

    public LoginAttemptService() {
        // TODO: ??? looks like a left-over after refactoring
        attemptsCache = CacheBuilder.newBuilder().
                expireAfterWrite(3, TimeUnit.MINUTES).build(new CacheLoader<>() {
                    public Integer load(String key) {
                        return 0;
                    }
                });
    }

    public void loginSucceeded(String key) {
        attemptsCache.invalidate(key);
    }

    public synchronized void loginFailed(String key) {
        int attempts = 0;
        try {
            attempts = attemptsCache.get(key);
        } catch (ExecutionException e) {
            LoggerFactory.getILoggerFactory().getLogger(this.toString()).debug(e.toString());
        }
        attempts++;
        attemptsCache.put(key, attempts);
    }

    public boolean isBlocked(String key) {
        try {
            return attemptsCache.get(key) >= MAX_ATTEMPT;
        } catch (ExecutionException e) {
            return false;
        }
    }

    public synchronized List<String> getBlockedUsers() {
        return attemptsCache.asMap()
                .keySet()
                .stream()
                .filter(this::isBlocked)
                .toList();
    }
}