package com.deveek.cilicili.web.video.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deveek.cilicili.web.common.video.model.po.VideoPo;
import com.deveek.cilicili.web.video.service.VideoService;
import com.deveek.cilicili.web.video.mapper.VideoMapper;
import org.springframework.stereotype.Service;

/**
 * @author harvey
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, VideoPo> implements VideoService {
}
