package com.dmdev.mapper;

import com.dmdev.dto.CreateSubscriptionDto;
import com.dmdev.entity.Provider;
import com.dmdev.entity.Status;
import com.dmdev.entity.Subscription;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.*;

class CreateSubscriptionMapperTest {

    private final CreateSubscriptionMapper mapper = CreateSubscriptionMapper.getInstance();
    private static final LocalDateTime DATE_TIME = LocalDateTime.of(2025, Month.JUNE, 15, 12, 39, 40);

    @Test
    void map() {
        CreateSubscriptionDto dto = CreateSubscriptionDto.builder()
                .userId(1)
                .name("Fedor")
                .provider(Provider.GOOGLE.name())
                .expirationDate(DATE_TIME.atZone(ZoneId.of("Europe/Moscow")).toInstant())
                .build();

        Subscription actualResult = mapper.map(dto);

        Subscription expectedResult = Subscription.builder()
                .userId(1)
                .name("Fedor")
                .provider(Provider.GOOGLE)
                .expirationDate(DATE_TIME.atZone(ZoneId.of("Europe/Moscow")).toInstant())
                .status(Status.ACTIVE)
                .build();
        Assertions.assertThat(actualResult).isEqualTo(expectedResult);

    }
}