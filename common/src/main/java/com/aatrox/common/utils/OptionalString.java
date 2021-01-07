package com.aatrox.common.utils;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author aatrox
 * @desc 针对于optional对String的处理
 * @date 2020/12/24
 */
public class OptionalString {
    private static final OptionalString EMPTY = new OptionalString();

    private final boolean isPresent;
    private final String value;

    private OptionalString() {
        this.isPresent = false;
        this.value = "";
    }

    private OptionalString(String value) {
        this.isPresent = true;
        this.value = value;
    }

    private static OptionalString empty() {
        return EMPTY;
    }

    public static OptionalString of(String value) {
        return value == null || value.isEmpty() ? OptionalString.empty() : new OptionalString(value);
    }

    private boolean isPresent() {
        return isPresent;
    }

    public OptionalString map(Function<? super String, ? extends String> mapper) {
        return !isPresent() ? OptionalString.empty() : OptionalString.of(mapper.apply(this.value));
    }

    public OptionalString or(Supplier<String> supplier) {
        return isPresent() ? this : OptionalString.of(supplier.get());
    }

   public String orElse(String other) {
        return isPresent ? value : other;
    }

    public String getAsString() {
        return Optional.of(value).orElseThrow(() -> new NoSuchElementException("No value present"));
    }
}
