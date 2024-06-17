package com.deveek.cilicili.web.media.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deveek.cilicili.web.common.media.constant.FileType;
import com.deveek.cilicili.web.common.media.model.dto.ImageUploadDto;
import com.deveek.cilicili.web.common.media.model.dto.TopVideoPageDto;
import com.deveek.cilicili.web.common.media.model.dto.VideoAddDto;
import com.deveek.cilicili.web.common.media.model.dto.VideoUploadDto;
import com.deveek.cilicili.web.common.media.model.po.TopVideoPageVo;
import com.deveek.cilicili.web.common.media.model.po.VideoPo;
import com.deveek.cilicili.web.common.media.model.vo.TopVideoVo;
import com.deveek.cilicili.web.media.service.CosService;
import com.deveek.cilicili.web.media.service.VideoService;
import com.deveek.common.constant.Result;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author harvey
 */
@RestController
@PreAuthorize("hasAuthority('MEDIA_R')")
public class MediaController {
    @Resource
    private VideoService videoService;
    
    @Resource
    private CosService cosService;
    
    @PostMapping("/api/v1/media/upload_image")
    public Result<Void> uploadImage(@ModelAttribute ImageUploadDto imageUploadDto) {
        MultipartFile file = imageUploadDto.getFile();
        
        cosService.uploadFile(file, FileType.IMAGE);
        
        return Result.success();
    }
    
    @PostMapping("/api/v1/media/upload_video")
    public Result<Void> uploadVideo(@ModelAttribute VideoUploadDto videoUploadDto) {
        MultipartFile file = videoUploadDto.getFile();
        
        cosService.uploadFile(file, FileType.VIDEO);
        
        return Result.success();
    }
    
    @GetMapping("/api/v1/media/page_top_video")
    public Result<TopVideoPageVo> pageTopVideo(@ModelAttribute TopVideoPageDto topVideoPageDto) {
        Integer pageNo = topVideoPageDto.getPageNo();
        Integer pageSize = topVideoPageDto.getPageSize();
        
        Page<VideoPo> videoPoPage = new Page<>(pageNo, pageSize);
        
        videoService.lambdaQuery()
            .select(
                VideoPo::getId,
                VideoPo::getTitle,
                VideoPo::getDescription,
                VideoPo::getThumbnailUrl,
                VideoPo::getVideoUrl,
                VideoPo::getViewCount,
                VideoPo::getLikeCount
            )
            .page(videoPoPage);
        
        List<VideoPo> videoPoList = videoPoPage.getRecords();
        
        long totalSize = videoPoPage.getTotal();
        
        List<TopVideoVo> topVideoVoList = BeanUtil.copyToList(videoPoList, TopVideoVo.class);
        
        TopVideoPageVo topVideoPageVo = new TopVideoPageVo();
        topVideoPageVo.setTopVideoVoList(topVideoVoList);
        topVideoPageVo.setTotalSize(totalSize);
        
        return Result.success(topVideoPageVo);
    }
    
    @PreAuthorize("hasAuthority('MEDIA_W')")
    @PutMapping("/api/v1/media/add_video")
    public Result<Void> addVideo(@ModelAttribute VideoAddDto videoAddDto) {
        VideoPo videoPo = BeanUtil.copyProperties(videoAddDto, VideoPo.class);
        
        videoService.saveOrUpdate(videoPo);
        
        return Result.success();
    }
}
