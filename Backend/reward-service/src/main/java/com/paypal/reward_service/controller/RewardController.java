package com.paypal.reward_service.controller;

import com.paypal.reward_service.entity.Reward;
import com.paypal.reward_service.service.RewardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reward")
public class RewardController{

    private final RewardService rewardService;

    @PostMapping("/send-reward")
    public ResponseEntity<Reward> sendReward(@RequestBody Reward reward) {
        return ResponseEntity.status(HttpStatus.OK).body(rewardService.sendReward(reward));
    }

    @GetMapping("/getByUserId/{userId}")
    public ResponseEntity<List<Reward>> getRewardByUserId(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(rewardService.getRewardByUserId(userId));
    }


}
