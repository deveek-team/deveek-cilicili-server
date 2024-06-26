package com.deveek.cilicili.web.media.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deveek.cilicili.web.common.media.model.po.VideoTagPo;
import com.deveek.cilicili.web.media.service.VideoTagService;
import com.deveek.cilicili.web.media.mapper.VideoTagMapper;
import org.springframework.stereotype.Service;

/**
 * @author harvey
 */
@Service
public class VideoTagServiceImpl extends ServiceImpl<VideoTagMapper, VideoTagPo> implements VideoTagService {
}
