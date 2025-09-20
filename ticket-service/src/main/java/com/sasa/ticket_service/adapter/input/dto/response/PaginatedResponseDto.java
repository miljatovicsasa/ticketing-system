package com.sasa.ticket_service.adapter.input.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;

@Schema(description = "Paginated response wrapper for listing resources")
public record PaginatedResponseDto<T>(
        @Schema(description = "List of items in the current page")
        List<T> content,

        @Schema(description = "Current page number (0-based index)", example = "0")
        int page,

        @Schema(description = "Number of items per page", example = "20")
        int size,

        @Schema(description = "Total number of elements across all pages", example = "150")
        long totalElements,

        @Schema(description = "Total number of pages", example = "8")
        int totalPages,

        @Schema(description = "Indicates if this is the first page", example = "true")
        boolean first,

        @Schema(description = "Indicates if this is the last page", example = "false")
        boolean last,

        @Schema(description = "Number of elements in the current page", example = "20")
        int numberOfElements
) {
    public static <T, U> PaginatedResponseDto<U> fromPage(Page<T> page, Function<T, U> mapper) {
        return new PaginatedResponseDto<>(
                page.getContent().stream().map(mapper).toList(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast(),
                page.getNumberOfElements()
        );
    }
}
