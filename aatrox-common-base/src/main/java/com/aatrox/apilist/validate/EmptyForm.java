package com.aatrox.apilist.validate;

import com.aatrox.apilist.form.ValidationForm;

public class EmptyForm implements ValidationForm {
    private String key;

    public EmptyForm() {
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
