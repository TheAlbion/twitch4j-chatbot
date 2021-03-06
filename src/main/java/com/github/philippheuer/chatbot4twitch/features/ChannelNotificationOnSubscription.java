package com.github.philippheuer.chatbot4twitch.features;

import me.philippheuer.twitch4j.events.EventSubscriber;
import me.philippheuer.twitch4j.events.event.FollowEvent;
import me.philippheuer.twitch4j.events.event.SubscriptionEvent;

public class ChannelNotificationOnSubscription {

    /**
     * Subscribe to the Subscription Event
     */
    @EventSubscriber
    public void onSubscription(SubscriptionEvent event) {
        String message = "";

        // New Subscription
        if(event.getSubscription().getStreak().isPresent() && event.getSubscription().getStreak().get() <= 1) {
            message = String.format("%s has subscribed to %s!", event.getUser().getDisplayName(), event.getChannel().getDisplayName());
        }
        // Resubscription
        if(event.getSubscription().getStreak().isPresent() && event.getSubscription().getStreak().get() > 1) {
            message = String.format("%s has subscribed to %s in his %s month!", event.getUser().getDisplayName(), event.getChannel().getDisplayName(), event.getSubscription().getStreak().get());
        }

        // Send Message
        event.getClient().getIrcClient().sendMessage(event.getChannel().getName(), message);
    }

}
