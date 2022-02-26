package com.lambakean.domain.service;

import com.lambakean.data.model.Subscription;
import com.lambakean.data.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    @Autowired
    public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    public void saveAndFlush(Subscription subscription) {
        subscriptionRepository.saveAndFlush(subscription);
    }
}
