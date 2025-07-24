package com.ssafy.tooguisquare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ssafy.GeniusOfInvestment._common.entity.Channel;

public interface ChannelRepository extends JpaRepository<Channel, Long>, ChannelRepositoryCustom{

}
