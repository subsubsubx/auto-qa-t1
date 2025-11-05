package qa.auto.innotech.ui.assertions;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;

public interface Assertable<ASSERT extends AbstractAssertions<?>> {

    @SuppressWarnings("unchecked")
    default ASSERT check() {
        ParameterizedType type = (ParameterizedType) getClass().getGenericInterfaces()[0];
        Class<ASSERT> cls = (Class<ASSERT>) type.getActualTypeArguments()[0];

        try {
            return cls.getDeclaredConstructor(getClass()).newInstance(this);
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}