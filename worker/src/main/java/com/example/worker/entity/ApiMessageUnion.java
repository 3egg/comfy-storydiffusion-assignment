package com.example.worker.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiMessageUnion {

    private String type;
    private ApiMessageData data;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ApiMessageData {
        private String sid;
        private Status status;
        private String node;
        private String display_node;
        private String prompt_id;
        //"output": {"images": [{"filename": "ComfyUI_00033_.png", "subfolder": "", "type": "output"}]}
        private Output output;
        // Add other fields as needed
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Output {
        private List<Image> images;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Image {
        private String filename;
        private String subfolder;
        private String output;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProgressTextData {
        private String nodeId;
        private String text;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Status {
        private ExecInfo exec_info;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ExecInfo {
        private int queue_remaining;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ImagePreviewData {
        private String mimeType;
        private byte[] data;
    }
}
