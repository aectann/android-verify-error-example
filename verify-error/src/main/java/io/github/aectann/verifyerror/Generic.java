package io.github.aectann.verifyerror;

/**
 * Created by aectann on 9/05/14.
 */
public class Generic extends BaseGeneric<String> {

  public String assign() {
    return data = new Convertor().convert(String.class);
  }

}
