package com.example.leavemanagement.Position;

import com.example.leavemanagement.Dto.PositionDto;
import com.example.leavemanagement.common.CommonResponse;
import com.example.leavemanagement.model.Position;

public interface PositionService {

    public CommonResponse<PositionDto> savePosition(Position position);

    public CommonResponse<PositionDto> getAllPosition(Integer pageSize, Integer pageNo);

}
