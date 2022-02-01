package ua.balu.toyshop.converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.balu.toyshop.dto.marker.Convertible;

import java.lang.reflect.Type;

@Component
public class DtoConverter {
    private final  ModelMapper modelMapper;

    @Autowired
    public DtoConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public <D,E extends Convertible> E convertToEntity(D dto, E entity){
        return modelMapper.map(dto, (Type) entity.getClass());
    }

    public <E extends Convertible,D> D ConvertToDto(E entity,Type dto){
        return modelMapper.map(entity,dto);
    }

    public <E,D extends Convertible> D convertFromDtoToDto(E dtoConvertFrom,D dtoConvertTo){
        return modelMapper.map(dtoConvertFrom,(Type) dtoConvertTo.getClass());
    }


}
