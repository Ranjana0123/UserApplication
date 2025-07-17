package com.microservice.user.entity; // or dto, depending on your package

import lombok.Data;

@Data
public class ResponseDto {
    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public DepartmentDto getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentDto department) {
        this.department = department;
    }

    private UserDto user;
    private DepartmentDto department;
}