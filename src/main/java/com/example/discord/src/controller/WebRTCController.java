package com.example.discord.src.controller;

import com.example.discord.common.response.BaseResponse;
import com.example.discord.src.controller.dto.*;
import com.example.discord.src.service.ParticipantManageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
//@Tag(name = "webRTC", description = "webRTC 시그널링 소켓 통신 api")
public class WebRTCController {

    private final ParticipantManageService participantManageService;

//    /** 신규접속자가 기존의 접속자들의 key를 요청하면 이를 publish한다 */
////    @Operation(summary = "접속자 리스트 요청", description = "신규접속자가 기존의 접속자들의 key를 요청하면 이를 publish한다")
//    @MessageMapping("/channels/{channelId}/call-members")
//    @SendTo("/sub/channels/{channelId}/call-members")
//    public CallMembersRes callMembers(CallMembersReq callMembersReq,
//                                      @DestinationVariable(value = "channelId") String channelId){
//
//        log.info("callMembers : channelId = {}, sender = {} ", callMembersReq.getChannelId(), callMembersReq.getSender());
//
//        CallMembersRes callMembersRes = CallMembersRes.builder()
//                        .sender(callMembersReq.getSender())
//                        .channelId(callMembersReq.getChannelId())
//                        .build();
//
//        return callMembersRes;
//    }

    /** 채널의 접속자가 자신의 식별키를 보내면 서버에서는 참석자 정보를 갱신하고 현재 채널접속자들 모두에게 현재 참석자 정보를 보낸다.*/
//    @Operation(summary = "유저 식별키 전송", description = "채널의 접속자가 자신의 식별키를 현재 채널접속자들 모두에게 보낸다.")
    @MessageMapping("/channels/{channelId}/send-me")
    @SendTo("/sub/channels/{channelId}/receive-other")
    public SendUserKeyRes sendMeToOther(SendUserKeyReq sendUserKeyReq,
                                        @DestinationVariable(value = "channelId") String channelId){

        log.info("sendMeToOthers : channelId = {}, sender = {}, userKey = {}, state = {}", sendUserKeyReq.getChannelId(), sendUserKeyReq.getSender(), sendUserKeyReq.getUserKey(), sendUserKeyReq.getState());
        List<String> participants = null;
        if(sendUserKeyReq.getState().equals("join")){
            participants = participantManageService.addParticipants(sendUserKeyReq.getChannelId(), sendUserKeyReq.getUserKey());
        }else if(sendUserKeyReq.getState().equals("out")) {
            participants = participantManageService.removeParticipants(sendUserKeyReq.getChannelId(), sendUserKeyReq.getUserKey());
        }

        SendUserKeyRes sendUserKeyRes = SendUserKeyRes.builder()
                .sender(sendUserKeyReq.getSender())
                .channelId(sendUserKeyReq.getChannelId())
                .userKeys(participants)
                .build();


        return sendUserKeyRes;
    }

    /** 신규 접속자가 대상 userKey로 offer를 전송하면, 해당 userKey를 가진 접속자는 신규접속자의 키, offer를 받는다 */
//    @Operation(summary = "offer 전송", description = "신규 접속자가 대상 userKey로 offer를 전송한다")
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
//    @Operation(summary = "answer 전송", description = "offer제공자에게 자신의 키와 생성한 answer를 전송한다")
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
//    @Operation(summary = "ice-candidate 전송", description = "채널 접속자에게 자신의 key와 ice-canddiate 를 전송한다")
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
