package com.deveek.cilicili.web.media.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deveek.cilicili.web.common.media.model.po.TagPo;
import com.deveek.cilicili.web.media.service.TagService;
import com.deveek.cilicili.web.media.mapper.TagMapper;
import org.springframework.stereotype.Service;

/**
 * @author harvey
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, TagPo> implements TagService {
}
