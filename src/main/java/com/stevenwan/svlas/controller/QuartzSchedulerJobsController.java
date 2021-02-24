package com.stevenwan.svlas.controller;


import com.stevenwan.svlas.common.ResultData;
import com.stevenwan.svlas.dto.stock.QuartzAddJobDTO;
import com.stevenwan.svlas.dto.stock.QuartzUpdateJobDTO;
import com.stevenwan.svlas.service.QuartzSchedulerJobsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 任务表 前端控制器
 * </p>
 *
 * @author steven.wan
 * @since 2021-02-23
 */
@RestController
@RequestMapping("/quartzSchedulerJobs")
@RequiredArgsConstructor
public class QuartzSchedulerJobsController {

    private final QuartzSchedulerJobsService quartzSchedulerJobsService;

    @PostMapping("/addTimesJob")
    public ResultData addTimesJob(@RequestBody QuartzAddJobDTO quartzAddJobDTO) {
        return ResultData.data(quartzSchedulerJobsService.addTimesJob(quartzAddJobDTO));
    }

    @PostMapping("/updateTimesJob")
    public ResultData updateTimesJob(@RequestBody QuartzUpdateJobDTO quartzUpdateJobDTO) {
        return ResultData.data(quartzSchedulerJobsService.updateTimesJob(quartzUpdateJobDTO));
    }

    @GetMapping("/deleteTimesJob")
    public ResultData deleteTimesJob(@RequestParam("jobId") Long jobId,@RequestParam("userId") Long userId) {
        return ResultData.data(quartzSchedulerJobsService.deleteTimesJob(jobId,userId));
    }
}

