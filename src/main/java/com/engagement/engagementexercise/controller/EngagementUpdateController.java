package com.engagement.engagementexercise.controller;

import com.engagement.engagementexercise.record.EngagementUpdate;
import com.engagement.engagementexercise.service.EngagementUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("/engagement/v1/updates")
@RequiredArgsConstructor
public class EngagementUpdateController {

    public final EngagementUpdateService engagementUpdateService;

    @GetMapping
    public List<EngagementUpdate> getUsedEngagementFiles(@RequestParam(name = "firmId") String firmId) {
        return engagementUpdateService.listPendingEngagementUpdates(firmId);
    }

}
