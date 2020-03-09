package com.aatrox.apilist.form;

import java.io.Serializable;

public interface JsonReturnCodeEnum extends Serializable {
    String getMessage();

    String getStatus();
}
