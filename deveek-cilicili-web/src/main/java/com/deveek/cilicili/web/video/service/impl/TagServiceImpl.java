package com.deveek.cilicili.web.video.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deveek.cilicili.web.common.video.model.po.TagPo;
import com.deveek.cilicili.web.video.service.TagService;
import com.deveek.cilicili.web.video.mapper.TagMapper;
import org.springframework.stereotype.Service;

/**
 * @author harvey
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, TagPo> implements TagService {
}
