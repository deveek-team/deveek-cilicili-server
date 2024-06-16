package com.deveek.cilicili.web.video.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deveek.cilicili.web.common.video.model.dto.TopVideoPageDto;
import com.deveek.cilicili.web.common.video.model.po.TopVideoPageVo;
import com.deveek.cilicili.web.common.video.model.po.VideoPo;
import com.deveek.cilicili.web.common.video.model.vo.TopVideoVo;
import com.deveek.cilicili.web.video.service.VideoService;
import com.deveek.common.constant.Result;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author harvey
 */
@RestController
@PreAuthorize("hasAuthority('VIDEO_R')")
public class VideoController {
    @Resource
    private VideoService videoService;
    
    @GetMapping("/api/v1/video/top/page")
    public Result<TopVideoPageVo> pageTopVideo(@ModelAttribute TopVideoPageDto topVideoPageDto) {
        Integer pageNo = topVideoPageDto.getPageNo();
        Integer pageSize = topVideoPageDto.getPageSize();
        
        Page<VideoPo> videoPoPage = new Page<>(pageNo, pageSize);
        
        videoService.page(videoPoPage);
        
        List<VideoPo> videoPoList = videoPoPage.getRecords();
        
        long totalSize = videoPoPage.getTotal();
        
        List<TopVideoVo> topVideoVoList = videoPoList.stream()
            .map(videoPo -> {
                TopVideoVo topVideoVo = BeanUtil.copyProperties(videoPo, TopVideoVo.class);
                return topVideoVo;
            })
            .toList();
        
        TopVideoPageVo topVideoPageVo = new TopVideoPageVo();
        topVideoPageVo.setTopVideoVoList(topVideoVoList);
        topVideoPageVo.setTotalSize(totalSize);
        
        return Result.success(topVideoPageVo);
    }
}
