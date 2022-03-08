package com.lambakean.domain.service;

import com.lambakean.data.model.Subscription;

public interface SubscriptionService {

    void saveAndFlush(Subscription subscription);
}
