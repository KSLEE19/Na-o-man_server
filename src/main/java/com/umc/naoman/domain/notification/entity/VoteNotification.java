package com.umc.naoman.domain.notification.entity;

import com.umc.naoman.domain.agenda.entity.Agenda;
import com.umc.naoman.domain.member.entity.Member;
import com.umc.naoman.domain.notification.service.JosamoaSingleton;
import com.umc.naoman.domain.vote.entity.Vote;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "votes_notifications")
@SQLRestriction("deleted_at is NULL")
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class VoteNotification extends Notification {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agenda_id")
    private Agenda agenda;

    /*
     *[유저명]이 [안경명]에 투표했습니다.
     */
    @Override
    public void postMessage(){
        StringBuilder sb = new StringBuilder();
        sb.append(JosamoaSingleton.setJosa(getActor().getName(), "이가"));
        sb.append(agenda.getTitle());
        sb.append("에 투표했습니다.");
        message =  sb.toString();
    }

    public Notification makeOtherNotification(Member member){
        return VoteNotification.builder()
                .id(this.getId())
                .message(this.getMessage())
                .member(member)
                .isChecked(false)
                .actor(this.getActor())
                .agenda(getAgenda())
                .build();
    }
    @Override
    public String makeNotificationInfoURL() {
        //해당 투표로 이동
        // todo 아젠다 컨틀롤러 만들어 지면 MvcUriComponentsBuilder 로 리팩토링 예정
        //해당 api 미계획으로 인해 null 처리 -> 업데이트 되면 개발 예정
        return null;
    }
}
