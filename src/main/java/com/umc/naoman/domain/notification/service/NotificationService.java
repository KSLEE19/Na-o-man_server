package com.umc.naoman.domain.notification.service;

import com.umc.naoman.domain.member.entity.Member;
import com.umc.naoman.domain.notification.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NotificationService {
    Page<Notification> getNotificationList(Member member, Pageable pageable);
    List<Notification> isUnreadNotification(Member member);
    List<Notification>  setMyNotificationRead(Member member);
    long deleteNotificationAll(Member member);
    long deleteNotification(Member member, Long notificationId);
    void makeNewNotification(Notification notification, Long shareGroupId);
}
