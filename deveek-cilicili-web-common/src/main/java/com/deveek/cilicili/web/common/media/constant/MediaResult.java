package com.deveek.cilicili.web.common.media.constant;

import com.deveek.common.constant.Result;

/**
 * @author harvey
 */
public class MediaResult {
    public static final Result UPLOAD_FAILURE = new Result(3000, "upload failure");
    
    public static final Result DIRNAME_IS_BLANK = new Result(3000, "dirname is blank");
    
    public static final Result FILENAME_IS_BLANK = new Result(3000, "filename is blank");
    
    public static final Result FiLE_IS_EMPTY = new Result(3000, "file is empty");
    
    public static final Result FILE_IS_NOT_VIDEO = new Result(3000, "file is not video");
    
    public static final Result FILE_IS_NOT_IMAGE = new Result(3000, "file is not image");
    
    public static final Result TITLE_INVALID = new Result(3000, "title is invalid");
    
    public static final Result THUMBNAIL_URL_INVALID = new Result(3000, "thumbnail url is invalid");
    
    public static final Result VIDEO_URL_INVALID = new Result(3000, "video ulr is invalid");
    
    public static final Result CREATE_TEMP_LOCAL_FILE_FAILURE = new Result(3000, "create local file failure");
    
    public static final Result DELETE_TEMP_LOCAL_FILE_FAILURE = new Result(3000, "delete local file failure");
}
