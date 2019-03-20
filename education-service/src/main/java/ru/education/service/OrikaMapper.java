package ru.education.service;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import ru.education.service.user.IntegerAndUserStateTypeConverter;

import java.util.List;

public class OrikaMapper extends ConfigurableMapper {

    private MapperFactory factory;

    @Override
    protected void configureFactoryBuilder(final DefaultMapperFactory.Builder factoryBuilder) {
        factory = factoryBuilder.mapNulls(false).build();
    }

    @Override
    protected void configure(final MapperFactory factory) {
        factory.getConverterFactory().registerConverter(new IntegerAndUserStateTypeConverter());
    }

    public MapperFactory getFactory() {
        return factory;
    }

    public <S, D> Page<D> mapAsPage(Page<S> source, Class<D> destination, Pageable pageable) {
        List<D> content = mapAsList(source.getContent(), destination);
        return new PageImpl<>(content, pageable, source.getTotalElements());
    }
}
