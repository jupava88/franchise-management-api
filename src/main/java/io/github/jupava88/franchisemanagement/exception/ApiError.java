package io.github.jupava88.franchisemanagement.exception;

import java.util.Map;

public record ApiError(
        int status,
        String message,
        String path,
        Map<String, String> errors
) {
}
