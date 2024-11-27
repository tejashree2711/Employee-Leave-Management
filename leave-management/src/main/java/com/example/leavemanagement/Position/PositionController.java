package com.example.leavemanagement.Position;

import com.example.leavemanagement.Dto.PositionDto;
import com.example.leavemanagement.common.CommonResponse;
import com.example.leavemanagement.model.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class PositionController {

    @Autowired
    PositionService positionService;

    @PostMapping("savePosition")
    public CommonResponse<PositionDto> savePosition(@RequestBody Position position) {
        return positionService.savePosition(position);
    }

    @GetMapping("getAllPosition")
    public CommonResponse<PositionDto> getAllPosition(@RequestParam(required = false,defaultValue = "3") Integer pageSize,
                                                      @RequestParam(required = false,defaultValue = "0") Integer pageNo) {
        return positionService.getAllPosition(pageSize, pageNo);
    }
}

