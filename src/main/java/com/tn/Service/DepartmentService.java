package com.tn.Service;

import com.tn.Entity.Department;

import java.util.List;

public interface DepartmentService {
    List<Department> getAll();

    boolean save(Department department);

    Department getById(int id);
    boolean delete(int id);
}
