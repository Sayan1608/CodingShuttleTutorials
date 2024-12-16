package com.codingshuttle.securityapp.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private Long id;
    @NotBlank(message = "Post title cannot be empty")
    private String title;
    @NotBlank(message = "Post description cannot be empty")
    private String description;
}
