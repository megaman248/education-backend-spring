package ru.education.service.user;

import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;
import ru.education.rest.api.user.state.UserStateType;

public class IntegerAndUserStateTypeConverter extends BidirectionalConverter<Integer, UserStateType> {

    @Override
    public UserStateType convertTo(Integer source, Type<UserStateType> type, MappingContext mappingContext) {
        return UserStateType.typeFromCode(source);
    }

    @Override
    public Integer convertFrom(UserStateType source, Type<Integer> type, MappingContext mappingContext) {
        return source.getCode();
    }
}
