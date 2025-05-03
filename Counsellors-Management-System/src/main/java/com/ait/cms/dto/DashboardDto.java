package com.ait.cms.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DashboardDto
{
    private Integer totalEnquiries;
    private Integer openEnquiries;
    private Integer enrolledEnquiries;
    private Integer lostEnquiries;

}
