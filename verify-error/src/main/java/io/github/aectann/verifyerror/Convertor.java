package io.github.aectann.verifyerror;

/**
 * Created by aectann on 9/05/14.
 */
public class Convertor {

  public <T> T convert(Class<T> clazz) {
    try {
      return clazz.newInstance();
    } catch (Exception e) {
      return null;
    }
  };

}
