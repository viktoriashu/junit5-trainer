package com.dmdev.dao;

import com.dmdev.dto.CreateSubscriptionDto;
import com.dmdev.entity.Provider;
import com.dmdev.entity.Status;
import com.dmdev.entity.Subscription;
import com.dmdev.integration.IntegrationTestBase;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class SubscriptionDaoIT extends IntegrationTestBase {

    private final SubscriptionDao subscriptionDao = SubscriptionDao.getInstance();
    private static final LocalDateTime DATE_TIME = LocalDateTime.of(2025, Month.JUNE, 15, 12, 39, 40);

    @Test
    void findAll() {
        Subscription subscription1 = subscriptionDao.insert(getSubscription("Fedor"));
        Subscription subscription2 = subscriptionDao.insert(getSubscription("Peter"));
        Subscription subscription3 = subscriptionDao.insert(getSubscription("Semyon"));

        List<Subscription> actualResult = subscriptionDao.findAll();


        assertThat(actualResult).hasSize(3);
        List<Integer> subscriptionIds = actualResult.stream()
                .map(Subscription::getId)
                .toList();
        assertThat(subscriptionIds)
                .contains(subscription1.getId(), subscription2.getId(), subscription3.getId());
    }

    @Test
    void findById() {
        Subscription subscription = subscriptionDao.insert(getSubscription("Fedor"));

        Optional<Subscription> actualResult = subscriptionDao.findById(subscription.getId());

        assertThat(actualResult).isPresent();
        assertThat(actualResult.get()).isEqualTo(subscription);
    }

    @Test
    void deleteExistEntity() {
        Subscription subscription = subscriptionDao.insert(getSubscription("Fedor"));

        boolean actualResult = subscriptionDao.delete(subscription.getId());

        assertTrue(actualResult);
    }

    @Test
    void deleteNotExistingEntity() {
        Subscription subscription = subscriptionDao.insert(getSubscription("Fedor"));

        boolean actualResult = subscriptionDao.delete(0);

        assertFalse(actualResult);
    }

    @Test
    void update() {
        Subscription subscription = getSubscription("Fedor");
        subscriptionDao.insert(subscription);
        subscription.setStatus(Status.CANCELED);

        subscriptionDao.update(subscription);

        Subscription updateSubscription = subscriptionDao.findById(subscription.getId()).get();
        assertThat(updateSubscription).isEqualTo(subscription);
    }

    @Test
    void insert() {
        Subscription subscription = getSubscription("Fedor");

        Subscription actualResult = subscriptionDao.insert(subscription);

        assertNotNull(actualResult.getId());
    }

    @Test
    void shouldNotFindByUserIdDoesNotExist() {
        subscriptionDao.insert(getSubscription("Fedor"));

        List<Subscription> actualResult = subscriptionDao.findByUserId(null);

        assertThat(actualResult).isEmpty();
    }

    @Test
    void findByUserId() {
        Subscription subscription = subscriptionDao.insert(getSubscription("Fedor"));

        Optional<Subscription> actualResult = subscriptionDao.findByUserId(subscription.getUserId()).stream()
                .filter(existingSubscription -> existingSubscription.getName().equals(subscription.getName()))
                .filter(existingSubscription -> existingSubscription.getProvider() == Provider.findByName(subscription.getProvider().name()))
                .findFirst()
                .map(existingSubscription -> existingSubscription
                        .setExpirationDate(subscription.getExpirationDate())
                        .setStatus(Status.ACTIVE));

        assertThat(actualResult).isPresent();
        assertThat(actualResult.get()).isEqualTo(subscription);
    }

    private Subscription getSubscription(String name) {
        return Subscription.builder()
                .userId(1)
                .name(name)
                .provider(Provider.GOOGLE)
                .expirationDate(DATE_TIME.atZone(ZoneId.of("Europe/Moscow")).toInstant())
                .status(Status.ACTIVE)
                .build();
    }


}