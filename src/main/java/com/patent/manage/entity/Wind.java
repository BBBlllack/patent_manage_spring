package com.patent.manage.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@TableName("patent_wind")
public class Wind implements Serializable {
    private static final long serialVersionUID = 1L;
    private String publicNum;
    private String legalStatus;
    private String latestLegalStatus;
    private String status;
    private String title;
    private String type;
    @TableField("abstract")
    private String abstractText;
    private String appliNum;
    private String appliDate;
    private String publicDate;
    private String applicant;
    private String applicantAddress;
    private String patentee;
    private String patenteeAddress;
    private String inventor;
    private String agent;
    @TableField("ipc")
    private String IPC;
    @TableField("cpc")
    private String CPC;
    @TableField("nec")
    private String NEC;
    private String patentDetails;
}

