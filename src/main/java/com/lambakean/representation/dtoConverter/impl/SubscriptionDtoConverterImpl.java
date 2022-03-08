package com.lambakean.representation.dtoConverter.impl;

import com.lambakean.data.model.Chat;
import com.lambakean.data.model.Subscription;
import com.lambakean.data.model.User;
import com.lambakean.representation.dto.SubscriptionDto;
import com.lambakean.representation.dtoConverter.RoleDtoConverter;
import com.lambakean.representation.dtoConverter.SubscriptionDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionDtoConverterImpl implements SubscriptionDtoConverter {

    private final RoleDtoConverter roleDtoConverter;

    @Autowired
    public SubscriptionDtoConverterImpl(RoleDtoConverter roleDtoConverter) {
        this.roleDtoConverter = roleDtoConverter;
    }

    @Override
    public Subscription toSubscription(SubscriptionDto subscriptionDto) {

        Subscription subscription = new Subscription();

        subscription.setId(subscriptionDto.getId());
        subscription.setChat(new Chat(subscriptionDto.getChatId()));
        subscription.setUser(new User(subscriptionDto.getUserId()));
        subscription.setUserRole(roleDtoConverter.toRole(subscriptionDto.getRole()));

        return subscription;
    }

    @Override
    public SubscriptionDto toSubscriptionDto(Subscription subscription) {

        SubscriptionDto subscriptionDto = new SubscriptionDto();

        subscriptionDto.setId(subscription.getId());
        subscriptionDto.setChatId(subscription.getChatId());
        subscriptionDto.setUserId(subscription.getUserId());
        subscriptionDto.setRole(roleDtoConverter.toRoleDto(subscription.getUserRole()));

        return subscriptionDto;
    }
}
