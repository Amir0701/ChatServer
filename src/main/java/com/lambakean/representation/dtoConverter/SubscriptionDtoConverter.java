package com.lambakean.representation.dtoConverter;

import com.lambakean.data.model.Subscription;
import com.lambakean.representation.dto.SubscriptionDto;

public interface SubscriptionDtoConverter {

    Subscription toSubscription(SubscriptionDto subscriptionDto);

    SubscriptionDto toSubscriptionDto(Subscription subscription);
}
