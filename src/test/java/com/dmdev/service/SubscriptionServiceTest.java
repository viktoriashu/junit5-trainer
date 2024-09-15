package com.dmdev.service;

import com.dmdev.dao.SubscriptionDao;
import com.dmdev.dto.CreateSubscriptionDto;
import com.dmdev.entity.Provider;
import com.dmdev.entity.Status;
import com.dmdev.entity.Subscription;
import com.dmdev.mapper.CreateSubscriptionMapper;
import com.dmdev.validator.CreateSubscriptionValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SubscriptionServiceTest {

    @Mock
    private SubscriptionDao subscriptionDao;
    @Mock
    private CreateSubscriptionMapper createSubscriptionMapper;
    @Mock
    private CreateSubscriptionValidator createSubscriptionValidator;
    @Mock
    private Clock clock = Clock.systemDefaultZone();
    @InjectMocks
    private SubscriptionService subscriptionService;


    private static final LocalDateTime DATE_TIME = LocalDateTime.of(2025, Month.JUNE, 15, 12, 39, 40);


    @Test
    void upsertSuccess() {
        Subscription subscription = getSubscription(1, Status.ACTIVE);
        CreateSubscriptionDto createSubscriptionDto = getCreateSubscriptionDto();



    }



    private static CreateSubscriptionDto getCreateSubscriptionDto() {
        return CreateSubscriptionDto.builder()
                .userId(1)
                .name("Fedor")
                .provider(Provider.GOOGLE.name())
                .expirationDate(DATE_TIME.atZone(ZoneId.of("Europe/Moscow")).toInstant())
                .build();
    }


    private static Subscription getSubscription(Integer userId, Status status) {
        return Subscription.builder()
                .id(1)
                .userId(userId)
                .name("Fedor")
                .provider(Provider.GOOGLE)
                .expirationDate(DATE_TIME.atZone(ZoneId.of("Europe/Moscow")).toInstant())
                .status(status)
                .build();
    }
}