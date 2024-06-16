package com.deveek.cilicili.web.video.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deveek.cilicili.web.common.video.model.po.VideoTagPo;
import com.deveek.cilicili.web.video.service.VideoTagService;
import com.deveek.cilicili.web.video.mapper.VideoTagMapper;
import org.springframework.stereotype.Service;

/**
 * @author harvey
 */
@Service
public class VideoTagServiceImpl extends ServiceImpl<VideoTagMapper, VideoTagPo> implements VideoTagService {
}
