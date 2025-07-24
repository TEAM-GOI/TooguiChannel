package com.ssafy.tooguisquare.service;

import com.ssafy.tooguisquare.dto.response.ChannelInfo;
import com.ssafy.tooguisquare.entity.Channel;
import com.ssafy.tooguisquare.exception.CustomBadRequestException;
import com.ssafy.tooguisquare.repository.ChannelRepository;
import com.ssafy.tooguisquare.response.ErrorType;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChannelService {

    private final ChannelRepository channelRepository;
    private final UserRepository userRepository;
    private final EntityManager entityManager;
    private final RedissonClient redissonClient;

    //채널목록
    public List<ChannelInfo> listAllChannel() {

        List<ChannelInfo> list = new ArrayList<>();

        List<Channel> clist = channelRepository.findAll();
        if(clist.isEmpty())
            throw new CustomBadRequestException(ErrorType.CHANNEL_NOT_FOUND);

        for(Channel c : clist){
            if(c.getId() >=1 && c.getId()<=8) {
                list.add(
                        ChannelInfo
                                .builder()
                                .id(c.getId())
                                .channelName(channelRepository.findById(c.getId()).get().getChName())
                                .userCount(userRepository.countByChannel(c))
                                .build());
            }
        }

        return list;
    }

    //채널 들어가기
    //동시성 처리 완료
    @Transactional
    public void enterChannel(User user, Long channelId) {

        Optional<Channel> ochannel = channelRepository.findById(channelId);
        Channel channel;
        if(ochannel.isEmpty()){
            throw new CustomBadRequestException(ErrorType.CHANNEL_NOT_FOUND);
        }
        channel = ochannel.get();

        String lockName = "Member-" + user.getId();
        RLock rLock = redissonClient.getLock(lockName);
        long waitTime = 5L; //락 획득을 위해 기다리는 시간
        long leaseTime = 3L; //락을 최대 임대하는 시간
        try {
            boolean available = rLock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS);
            if(!available){
                throw new CustomBadRequestException(ErrorType.LOCK_NOT_AVAILABLE);
            }
            //=== 락 획득 후 로직 수행 ===
            log.info("락 획득 로직 수행 시작");
            if (channel.getParticipants().size() >= 100)
                throw new CustomBadRequestException(ErrorType.CHANNEL_IS_FULL);

            channel.addParticipants(user);
            userRepository.save(user);
        }catch(InterruptedException e) {
            log.error(e.getMessage());
            throw new CustomBadRequestException(ErrorType.LOCK_INTERRUPTED_ERROR);
        }finally {
            if(rLock.isLocked()){
                rLock.unlock();
            }else{
                throw new CustomBadRequestException(ErrorType.UNLOCKING_A_LOCK_WHICH_IS_NOT_LOCKED);
            }
        }

          //채널을 들어갈 수 있는지 부터 확인해야함
//        if (channel.getParticipants().size() > 100)
//            throw new CustomBadRequestException(ErrorType.CHANNEL_IS_FULL);

//        User enterUser;
//        // 채널을 들어가게되면 DB USER 수정 ch
//        Optional<User> u = userRepository.findById(user.getId());
//        if(u.isPresent()) {
//             enterUser = u.get();
//        }else{
//            throw new CustomBadRequestException(ErrorType.NOT_FOUND_USER);
//        }
    }

    public void exitChannel(User user) {

        Optional<User> byId = userRepository.findById(user.getId());
        if(!byId.isEmpty()) {
            byId.get().deleteChannel();
            User u = byId.get();
            // 유저DB에서 channel 삭제
            userRepository.save(u);
        }
        else{
            throw new CustomBadRequestException(ErrorType.NOT_FOUND_CHANNEL);
        }
    }
}
