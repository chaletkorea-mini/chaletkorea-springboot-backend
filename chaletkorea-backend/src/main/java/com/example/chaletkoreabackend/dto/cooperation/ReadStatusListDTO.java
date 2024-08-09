package com.example.chaletkoreabackend.dto.cooperation;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReadStatusListDTO {
    private String name;
    private String position;
    private LocalDateTime firstRead;
    private LocalDateTime finalRead;
}
