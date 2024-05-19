package com.example.discord.src.controller;

import com.example.discord.src.controller.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class WebRTCController {


    /** 신규접속자가 기존의 접속자들의 key를 요청하면 이를 publish한다 */
    @MessageMapping("/channels/{channelId}/call-members")
    @SendTo("/sub/channels/{channelId}/call-members")
    public CallMembersRes callMembers(CallMembersReq callMembersReq,
                                      @DestinationVariable(value = "channelId") String channelId){

        log.info("callMembers : channelId = {}, sender = {} ", callMembersReq.getChannelId(), callMembersReq.getSender());

        CallMembersRes callMembersRes = CallMembersRes.builder()
                        .sender(callMembersReq.getSender())
                        .channelId(callMembersReq.getChannelId())
                        .build();

        return callMembersRes;
    }

    /** 채널의 접속자가 자신의 식별키를 현재 채널접속자들 모두에게 보낸다.*/
    @MessageMapping("/channels/{channelId}/send-me")
    @SendTo("/sub/channels/{channelId}/receive-other")
    public SendUserKeyRes sendMeToOther(SendUserKeyReq sendUserKeyReq,
                                        @DestinationVariable(value = "channelId") String channelId){

        log.info("sendMeToOthers : channelId = {}, sender = {}, userKey = {}", sendUserKeyReq.getChannelId(), sendUserKeyReq.getSender(), sendUserKeyReq.getUserKey());

        SendUserKeyRes sendUserKeyRes = SendUserKeyRes.builder()
                .sender(sendUserKeyReq.getSender())
                .channelId(sendUserKeyReq.getChannelId())
                .userKey(sendUserKeyReq.getUserKey())
                .build();
        return sendUserKeyRes;
    }

    /** 신규 접속자가 대상 userKey로 offer를 전송하면, 해당 userKey를 가진 접속자는 신규접속자의 키, offer를 받는다 */
    @MessageMapping("/channels/{channelId}/video/offer/{userKey}")
    @SendTo("/sub/channels/{channelId}/video/offer/{userKey}")
    public OfferRes offer(OfferReq offerReq,
                          @DestinationVariable(value = "channelId") String channelId,
                          @DestinationVariable(value = "userKey") String userKey){

        log.info("offer : channelId  = {}, sender = {}, userKey = {}, offer = {}", offerReq.getChannelId(), offerReq.getSender(), offerReq.getUserKey(), offerReq.getOffer());

        OfferRes offerRes = OfferRes.builder()
                .sender(offerReq.getSender())
                .channelId(offerReq.getChannelId())
                .userKey(offerReq.getUserKey())
                .offer(offerReq.getOffer())
                .build();

        return offerRes;
    }

    /** offer를 받은 접속자가 offer제공자에게 answer를 전송하면, 해당 userKey를 가진 접속자는 answer제공자의 키와 answer를 받는다*/
    @MessageMapping("/channels/{channelId}/video/answer/{userKey}")
    @SendTo("/sub/channels/{channelId}/video/answer/{userKey}")
    public AnswerRes answer(AnswerReq answerReq,
                            @DestinationVariable(value = "channelId") String channelId,
                            @DestinationVariable(value = "userKey") String userKey){

        log.info("answer : channelId  = {}, sender = {}, userKey = {}, answer = {}", answerReq.getChannelId(), answerReq.getSender(), answerReq.getUserKey(), answerReq.getAnswer());

        AnswerRes answerRes = AnswerRes.builder()
                .sender(answerReq.getSender())
                .channelId(answerReq.getChannelId())
                .userKey(answerReq.getUserKey())
                .answer(answerReq.getAnswer())
                .build();

        return answerRes;
    }

    /** ice candidate 이벤트가 발생한 user는 자신의 userKey와 ice candidate를 다른 접속자 userKey에게 전송한다. */
    @MessageMapping("/channels/{channelId}/video/ice-candidate/{userKey}")
    @SendTo("/sub/channels/{channelId}/video/ice-candidate/{userKey}")
    public IceCandidateRes iceCandidate(IceCandidateReq iceCandidateReq,
                                        @DestinationVariable(value = "channelId") String channelId,
                                        @DestinationVariable(value = "userKey") String userKey){

        log.info("iceCandidate : channelId  = {}, sender = {}, userKey = {}, iceCandidate = {}", iceCandidateReq.getChannelId(), iceCandidateReq.getSender(), iceCandidateReq.getUserKey(), iceCandidateReq.getIceCandidate());

        IceCandidateRes iceCandidateRes = IceCandidateRes.builder()
                .sender(iceCandidateReq.getSender())
                .channelId(iceCandidateReq.getChannelId())
                .userKey(iceCandidateReq.getUserKey())
                .iceCandidate(iceCandidateReq.getIceCandidate())
                .build();

        return iceCandidateRes;
    }
}
