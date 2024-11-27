package com.example.leavemanagement.Position;
import com.example.leavemanagement.Dto.PositionDto;
import com.example.leavemanagement.common.CommonResponse;
import com.example.leavemanagement.common.PositionResponse;
import com.example.leavemanagement.model.Employee;
import com.example.leavemanagement.model.Position;
import com.example.leavemanagement.repository.PositionRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PositionServiceImpl implements PositionService {

    @Autowired
    PositionRepo positionRepo;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public CommonResponse<PositionDto> savePosition(Position position) {
        CommonResponse<PositionDto> response = new CommonResponse<>();

        if(position==null){
            response.setSuccess(false);
            response.setResponseCode(HttpStatus.BAD_REQUEST.value());
            response.setResponseMessage(PositionResponse.POSITION_NOT_BE_NULL);
            return response;
        }
        Position position1=null;
        if(position.getP_id()!=0){
            Optional<Position> optional = positionRepo.findById(position.getP_id());
            if(optional.isPresent()){
                position1=optional.get();
            }
        }
        if(position1==null){
            if(position.getP_name()==null){
                response.setSuccess(false);
                response.setResponseCode(HttpStatus.BAD_REQUEST.value());
                response.setResponseMessage(PositionResponse.POSITION_NAME_REQUIRED);
                return response;
            }
             position1= modelMapper.map(position, Position.class);
             response.setResponseMessage(PositionResponse.POSITION_CREATED);
        }
        else {
            position1=modelMapper.map(position,Position.class);
            response.setResponseMessage(PositionResponse.POSITION_UPDATED);
        }
        PositionDto positionDto = modelMapper.map(position1, PositionDto.class);
        response.setData(positionDto);
        response.setResponseCode(HttpStatus.OK.value());
        response.setSuccess(true);
        return response;
    }

    @Override
    public CommonResponse<PositionDto> getAllPosition(Integer pageSize, Integer pageNo) {
        CommonResponse<PositionDto> response = new CommonResponse<>();

        PageRequest pageRequest = PageRequest.of(pageNo, pageSize);

        Page<Position> positionPage = positionRepo.findAll(pageRequest);

        List<PositionDto> positionDtoList = positionPage.getContent().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        response.setData(positionDtoList);
        response.setResponseCode(HttpStatus.OK.value());
        response.setSuccess(true);
        response.setResponseMessage(PositionResponse.POSITION_RETRIEVED);
        return response;
    }

    private PositionDto convertToDto(Position position) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(position, PositionDto.class);

    }
}
