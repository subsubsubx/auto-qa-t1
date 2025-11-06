package qa.auto.innotech.ui.assertions;

import lombok.SneakyThrows;

import java.lang.reflect.ParameterizedType;

public interface Assertable<ASSERT extends AbstractAssertions<?>> {

    @SuppressWarnings("unchecked")
    @SneakyThrows
    default ASSERT check() {
        ParameterizedType type = (ParameterizedType) getClass().getGenericInterfaces()[0];
        Class<ASSERT> cls = (Class<ASSERT>) type.getActualTypeArguments()[0];

            return cls.getDeclaredConstructor(getClass()).newInstance(this);
    }
}