package qa.auto.innotech.ui.assertions;

import org.assertj.core.api.AbstractAssert;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;

public interface Assertable<SELF, ASSERT extends AbstractAssert<ASSERT, SELF>> {

    default ASSERT check() {
        ParameterizedType type = (ParameterizedType) getClass().getGenericInterfaces()[0];
        Class<ASSERT> cls = (Class<ASSERT>) type.getActualTypeArguments()[1];

        try {
            return cls.getConstructor(this.getClass()).newInstance(this);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
