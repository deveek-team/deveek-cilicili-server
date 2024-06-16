package com.deveek.cilicili.web.media.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deveek.cilicili.web.common.media.model.po.VideoPo;
import com.deveek.cilicili.web.media.service.VideoService;
import com.deveek.cilicili.web.media.mapper.VideoMapper;
import org.springframework.stereotype.Service;

/**
 * @author harvey
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, VideoPo> implements VideoService {
}
