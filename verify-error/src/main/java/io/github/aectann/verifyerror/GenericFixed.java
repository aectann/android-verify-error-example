package io.github.aectann.verifyerror;

/**
 * Created by aectann on 9/05/14.
 */
public class GenericFixed extends BaseGeneric<String> {

  public String assign() {
    String convert = new Convertor().convert(String.class);
    return data = convert;
  }

}
