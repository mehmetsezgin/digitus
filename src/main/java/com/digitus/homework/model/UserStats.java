package com.digitus.homework.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserStats {

    private Long totalUsers;

    private Long loggedInUsers;

    private Long loggedOutUsers;

}
